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

    public MsgAdapter getMsgAdapter() {
        return msgAdapter;
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

        registerObservers(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView(MyMessage anchor) {
        List<MyMessage> data = new ArrayList<>();
        refreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.activity));
        msgAdapter = new MsgAdapter(data, container.activity);
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

    private void registerObservers(boolean register) {

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
    public void loadMessageComplete() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void loadMessageError() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
