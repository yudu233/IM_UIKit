package com.rain.chat.session.list;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rain.chat.R;
import com.rain.chat.session.loader.MessageLoader;
import com.rain.chat.session.module.Container;
import com.rain.inputpanel.utils.EmoticonsKeyboardUtils;
import com.rain.messagelist.MsgAdapter;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MessageListPanelEx
 * @CreateDate: 2020/6/29 22:02
 * @Describe: 基于RecyclerView的消息收发模块
 */
public class MessageListPanelEx implements MessageLoader.LoadMessagesListener {

    private static final String TAG = "MessageListPanelEx";
    private Container container;
    private View rootView;


    // 仅显示消息记录，不接收和发送消息
    private boolean recordOnly;
    // 从服务器拉取消息记录
    private boolean remote;
    private RecyclerView mRecyclerView;
    private MsgAdapter msgAdapter;
    private SwipeRefreshLayout refreshLayout;
    private List<MyMessage> messages;

    public MsgAdapter getMsgAdapter() {
        return msgAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public MessageListPanelEx(Container container, View rootView, boolean recordOnly, boolean remote) {
        this(container, rootView, null, recordOnly, remote);
    }

    public MessageListPanelEx(Container container, View rootView, MyMessage anchor, boolean recordOnly, boolean remote) {
        this.container = container;
        this.rootView = rootView;
        this.recordOnly = recordOnly;
        this.remote = remote;
        init(anchor);
    }

    private void init(MyMessage anchor) {
        initRecyclerView(anchor);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(MyMessage anchor) {
        messages = new ArrayList<>();
        refreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.activity));
        msgAdapter = new MsgAdapter(messages, container.activity);
        msgAdapter.setUpFetchEnable(true);
        mRecyclerView.setAdapter(msgAdapter);
        MessageLoader messageLoader = new MessageLoader(container, mRecyclerView, msgAdapter, anchor, remote, this);
        msgAdapter.setUpFetchListener(messageLoader);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EmoticonsKeyboardUtils.closeSoftKeyboard(container.activity);
                    }
                }, 500);
                return false;
            }
        });
        refreshLayout.setEnabled(false);
    }

    public void update(MyMessage message) {
        msgAdapter.addMessage(message);
        mRecyclerView.scrollToPosition(msgAdapter.getData().size() - 1);
    }


    @Override
    public void messageLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void loadMessageComplete(List<MyMessage> messages) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
//        this.messages.addAll(messages);
    }

    @Override
    public void loadMessageError() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    // 发送消息后，更新本地消息列表
    public void onMsgSend(MyMessage message) {
        //主动添加新消息
        List<MyMessage> addedListItems = new ArrayList<>(1);
        addedListItems.add(message);
        msgAdapter.updateShowTimeItem(addedListItems, false, true);

        msgAdapter.addMessage(message);
        // FIXME: 2020/8/28 领取红包发tips消息后不滚动到底部
        doScrollToBottom();
    }

    /**
     * 列表滚动到底部
     */
    public void doScrollToBottom() {
        mRecyclerView.scrollToPosition(msgAdapter.getData().size() - 1);
    }

    /**
     * 新消息接收后操作
     *
     * @param message
     */
    public void onIncomingMessage(MyMessage message) {
        boolean needScrollToBottom = isLastMessageVisible();
        //是否需要刷新
        boolean needRefresh = false;
        //是否有相同消息 -> 过滤
        boolean hasSameMsg = false;
        if (isMyMessage(message)) {
            List<MyMessage> messageList = msgAdapter.getMessageList();
            for (int i = 0; i < messageList.size(); i++) {
                if (messageList.get(i).getUuid().equals(message.getUuid())) {
                    hasSameMsg = true;
                    break;
                }
            }
            if (!hasSameMsg) {
                msgAdapter.addMessage(message);
            }
            needRefresh = true;
        }
        if (needRefresh) {
            sortMessages(messages);
            msgAdapter.notifyDataSetChanged();
        }
        msgAdapter.updateShowTimeItem(Arrays.asList(message), false, true);

        //是否滚动到底部
        MyMessage lastMsg = messages.get(messages.size() - 1);
        if (isMyMessage(lastMsg) && needScrollToBottom) {
            doScrollToBottom();
        }

    }

    /**
     * 判断最后一条消息是否在UI上展示
     *
     * @return
     */
    private boolean isLastMessageVisible() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
        return lastVisiblePosition >= msgAdapter.getItemCount() - 1;
    }

    /**
     * 判断是否是我的消息
     *
     * @param message
     * @return
     */
    private boolean isMyMessage(MyMessage message) {
        return message.getSessionType() == container.sessionType.getValue()
                && message.getSessionId() != null
                && message.getSessionId().equals(container.account);
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortMessages(List<MyMessage> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<MyMessage> comp = (o1, o2) -> {
        long time = o1.getTime() - o2.getTime();
        return time == 0 ? 0 : (time < 0 ? -1 : 1);
    };
}
