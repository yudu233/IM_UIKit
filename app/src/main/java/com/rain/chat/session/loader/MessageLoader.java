package com.rain.chat.session.loader;


import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.rain.chat.base.IM;
import com.rain.chat.session.module.Container;
import com.rain.messagelist.MsgAdapter;
import com.rain.messagelist.message.MessageType;
import com.ycbl.im.uikit.msg.IMessageBuilder;
import com.ycbl.im.uikit.msg.models.DefaultUser;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 22:48
 * @Version : 1.0
 * @Descroption : 历史消息加载器
 */
public class MessageLoader implements BaseQuickAdapter.UpFetchListener {

    private static final String TAG = "MessageLoader";

    private MyMessage anchor;
    private boolean remote;
    private Container container;
    private MsgAdapter adapter;
    private RecyclerView recyclerView;
    private LoadMessagesListener loadMessagesListener;
    //如果在发需要拍照 的消息时，拍照回来时页面可能会销毁重建，重建时会在MessageLoader 的构造方法中调一次 loadFromLocal
    //而在发送消息后，list 需要滚动到底部，又会通过RequestFetchMoreListener 调用一次 loadFromLocal
    //所以消息会重复
    private boolean mIsInitFetchingLocal;

    //是否是第一次加载
    private boolean firstLoad = true;

    private QueryDirectionEnum direction = null;

//    private int loadMsgCount = NimUIKitImpl.getOptions().messageCountLoadOnce;

    //一次加载消息的数目
    private int loadMsgCount = 20;


    public MessageLoader(Container container, RecyclerView recyclerView,
                         MsgAdapter adapter, MyMessage message,
                         boolean remote, LoadMessagesListener loadMessagesListener) {
        this.container = container;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.anchor = message;
        this.remote = remote;
        this.loadMessagesListener = loadMessagesListener;
        if (remote) {
            //从远端拉取历史消息
            loadFromRemote();
        } else {
            //从本地加载历史消息
            if (anchor == null) {
                loadFromLocal();
                mIsInitFetchingLocal = true;
            } else {
                // 加载指定anchor的上下文
                loadAnchorContext();
            }
        }
    }

    /**
     * 从本地数据库加载历史消息
     */
    private void loadFromLocal() {
        this.direction = QueryDirectionEnum.QUERY_OLD;
        // TODO: 2020/6/30 消息丢失处理

//        IMMessage emptyMessage = MessageBuilder.createEmptyMessage(container.account,
//                container.sessionType, imLoginTime);
//        NIMClient.getService(MsgService.class).pullMessageHistoryEx
//                (items.size() == 0 ? emptyMessage : lastMessage, searchTime,
//                        100, QueryDirectionEnum.QUERY_OLD, true)
//                .setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
//                    @Override
//                    public void onResult(int code, List<IMMessage> result, Throwable exception) {
//                        if (code != ResponseCode.RES_SUCCESS || exception != null) {
//                            if (direction == QueryDirectionEnum.QUERY_OLD) {
//                                Log.e(TAG, "消息加载失败");
//                            } else if (direction == QueryDirectionEnum.QUERY_NEW) {
//                                adapter.loadMoreFail();
//                            }
//                            return;
//                        }
//                        searchMessageSize = param.size();
//                        if (param.size() == 0) {
//                            //当离线消息获取完成之后将上一次登录时间更新至本次登录时间
//                            HashMap<String, Object> extension = new HashMap<>(16);
//                            extension.put("searchTime", SharedPreferencesUtils.getData("IMLoginTime", 0L));
//                            recentContact.setExtension(extension);
//                            NIMClient.getService(MsgService.class).updateRecent(recentContact);
//                        } else {
//                            lastMessage = param.get(param.size() - 1);
//                        }
//                    }
//                });
    }

    /**
     * 从服务端拉取历史消息
     */
    private void loadFromRemote() {
        Log.e(TAG, "loadFromRemote: 服务端拉取消息");
        loadMessagesListener.messageLoading();
        this.direction = QueryDirectionEnum.QUERY_OLD;
        remote = true;
        NIMClient.getService(MsgService.class)
                .pullMessageHistory(anchor().getMessage(), loadMsgCount, false)
                .setCallback(callback);
    }

    private RequestCallbackWrapper<List<IMMessage>> callback = new RequestCallbackWrapper<List<IMMessage>>() {
        @Override
        public void onResult(int code, List<IMMessage> messages, Throwable exception) {
            Log.e(TAG, "message size: " + messages.size());
            mIsInitFetchingLocal = false;
            if (code != ResponseCode.RES_SUCCESS || exception != null) {
                if (direction == QueryDirectionEnum.QUERY_OLD) {
                    Log.e(TAG, "消息加载失败");
                } else if (direction == QueryDirectionEnum.QUERY_NEW) {
                    adapter.loadMoreFail();
                }
                loadMessagesListener.loadMessageError();
                return;
            }

            if (messages != null) {
                List<MyMessage> myMessages = new ArrayList<>();
                for (IMMessage message : messages) {
                    if (message.getMsgType() == MsgTypeEnum.custom) {
                        continue;
                    }
                    MyMessage myMessage = new MyMessage(MessageType
                            .valueOf(String.valueOf(message.getMsgType()))
                            , message, new DefaultUser(message));
                    myMessages.add(myMessage);
                }

                onMessageLoaded(myMessages);
            }
        }
    };

    /**
     * 根据锚点和方向从本地消息数据库中查询消息历史
     * 查询比锚点时间更晚的消息 使用{@link QueryDirectionEnum#QUERY_NEW}
     * 结果按照时间升序排列
     */
    private void loadAnchorContext() {
        // query new, auto load old
        loadMessagesListener.messageLoading();
        direction = QueryDirectionEnum.QUERY_NEW;
        NIMClient.getService(MsgService.class)
                .queryMessageListEx(anchor().getMessage(), direction, loadMsgCount, true)
                .setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
                    @Override
                    public void onResult(int code, List<IMMessage> messages, Throwable exception) {
                        if (code != ResponseCode.RES_SUCCESS || exception != null) {
                            loadMessagesListener.loadMessageError();
                            return;
                        }
                        List<MyMessage> myMessages = new ArrayList<>();
                        for (IMMessage message : messages) {
                            MyMessage myMessage = new MyMessage(MessageType
                                    .valueOf(String.valueOf(message.getMsgType()))
                                    , message, new DefaultUser(message));
                            myMessages.add(myMessage);
                        }
                        onAnchorContextMessageLoaded(myMessages);
                    }
                });
    }


    /**
     * 消息加载完成后处理
     *
     * @param messages
     */
    private void onAnchorContextMessageLoaded(final List<MyMessage> messages) {
        if (messages == null) {
            return;
        }

        if (remote) {
            Collections.reverse(messages);
        }

        int loadCount = messages.size();
        if (firstLoad && anchor != null) {
            messages.add(0, anchor);
        }

        // 在更新前，先确定一些标记
        adapter.updateShowTimeItem(messages, true, firstLoad); // 更新要显示时间的消息
//        updateReceipt(messages); // 更新已读回执标签

        // new data
        if (loadCount < loadMsgCount) {
            adapter.loadMoreEnd();
        } else {
            adapter.addMessages(messages);
        }
        // TODO: 2020/6/30 删除用户更新的自定义消息
        for (int i = 0; i < adapter.getData().size(); i++) {
            MyMessage message = (MyMessage) adapter.getData().get(i);
//            if (message.getAttachment() instanceof UserProfileAttachment)
//                adapter.deleteItem(adapter.getData().get(i), true);
        }

        firstLoad = false;
        loadMessagesListener.loadMessageComplete();
    }


    /**
     * 获取锚点
     *
     * @return
     */
    private MyMessage anchor() {
        if (adapter.getData().size() == 0) {

            return anchor == null ? IM.getIMessageBuilder().createEmptyMessage(container.account, container.sessionType, 0) : anchor;
        } else {
            int index = (direction == QueryDirectionEnum.QUERY_NEW ? adapter.getData().size() - 1 : 0);
            MyMessage message = (MyMessage) adapter.getData().get(index);
            return message;
        }
    }

    /**
     * 消息加载完成后的处理
     *
     * @param messages
     */
    private void onMessageLoaded(final List<MyMessage> messages) {

        if (messages == null) {
            return;
        }


        boolean noMoreMessage = messages.size() < loadMsgCount;

        if (remote) {
            Collections.reverse(messages);
        }

        // 在第一次加载的过程中又收到了新消息，做一下去重
        if (firstLoad && adapter.getData().size() > 0) {
            for (MyMessage message : messages) {
                int removeIndex = 0;
                for (int i = 0; i < adapter.getData().size(); i++) {
                    MyMessage myMessage = (MyMessage) adapter.getData().get(i);
                    if (myMessage.isTheSame(message)) {
                        adapter.remove(removeIndex);
                        break;
                    }
                    removeIndex++;
                }
            }
        }

        // 加入anchor
        if (firstLoad && anchor != null) {
            messages.add(anchor);
        }

        // 在更新前，先确定一些标记
        List<MyMessage> total = new ArrayList<>(adapter.getData());
        boolean isBottomLoad = direction == QueryDirectionEnum.QUERY_NEW;
        if (isBottomLoad) {
            total.addAll(messages);
        } else {
            total.addAll(0, messages);
        }
        // 更新要显示时间的消息
        adapter.updateShowTimeItem(total, true, firstLoad);
        // TODO: 2020/6/30  更新已读回执标签
//        updateReceipt(total);

        // 加载状态修改,刷新界面
        if (isBottomLoad) {
            // 底部加载
            if (noMoreMessage) {
                adapter.loadMoreEnd();
            } else {
                adapter.loadMoreComplete();
            }
        } else {
            // 顶部加载
            if (noMoreMessage) {
                adapter.setUpFetchEnable(false);
            } else {
                adapter.setUpFetching(false);
            }
        }
        adapter.addMessages(messages);
        loadMessagesListener.loadMessageComplete();
        //如果是UserProfileAttachment消息就删除
//        for (IMMessage message : messages) {
//            int removeIndex = 0;
//            if (message.getAttachment() instanceof UserProfileAttachment) {
//                if (((UserProfileAttachment) message.getAttachment()).type != UserProfileAttachment.MessageType.AGREE_ADD_FRIEND) {
//                    adapter.remove(removeIndex);
//                    continue;
//                }
//            }
//            removeIndex++;
//        }

        // 如果是第一次加载，updateShowTimeItem返回的就是lastShowTimeItem
        if (firstLoad) {
            recyclerView.scrollToPosition(adapter.getData().size() - 1);
//            sendReceipt(); // 发送已读回执

        }

        // TODO: 2020/6/30   通过历史记录加载的群聊消息，需要刷新一下已读未读最新数据
//        if (container.sessionType == SessionType.Team) {
//            NIMSDK.getTeamService().refreshTeamMessageReceipt(messages);
//        }


        firstLoad = false;
    }


    @Override
    public void onUpFetch() {
        Log.e(TAG, "onUpFetch: " );
        // 顶部加载历史数据
        if (remote) {
            loadFromRemote();
        } else {
            loadFromLocal();
        }
    }

    public interface LoadMessagesListener {
        //消息加载中
        void messageLoading();

        //消息加载完成
        void loadMessageComplete();

        //消息加载失败
        void loadMessageError();
    }
}
