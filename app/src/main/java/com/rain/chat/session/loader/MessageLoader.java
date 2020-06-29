package com.rain.chat.session.loader;


import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.rain.chat.session.module.Container;
import com.rain.messagelist.MsgAdapter;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;
import com.ycbl.im.uikit.msg.IMessageBuilder;
import com.ycbl.im.uikit.msg.models.DefaultUser;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MessageLoader
 * @CreateDate: 2020/6/29 22:48
 * @Describe: 历史消息加载器
 */
public class MessageLoader {
    private MyMessage anchor;
    private boolean remote;
    private Container container;
    private MsgAdapter adapter;
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


    public MessageLoader(Container container, MsgAdapter adapter, MyMessage message, boolean remote) {
        this.container = container;
        this.adapter = adapter;
        this.anchor = message;
        this.remote = remote;
        if (remote) {
            //从远端拉取历史消息
            //loadFromRemote();
        } else {
            //从本地加载历史消息
            if (anchor == null) {
                // loadFromLocal(QueryDirectionEnum.QUERY_OLD);
                mIsInitFetchingLocal = true;
            } else {
                // 加载指定anchor的上下文
                loadAnchorContext();
            }
        }
    }

    /**
     * 加载指定anchor的上下文
     */
    private void loadAnchorContext() {
        // query new, auto load old
        direction = QueryDirectionEnum.QUERY_NEW;
        NIMClient.getService(MsgService.class)
                .queryMessageListEx(anchor().getMessage(), direction, loadMsgCount, true)
                .setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
                    @Override
                    public void onResult(int code, List<IMMessage> messages, Throwable exception) {
                        if (code != ResponseCode.RES_SUCCESS || exception != null) {
                            return;
                        }

                        List<MyMessage> myMessages = new ArrayList<>();
                        for (IMMessage message : messages) {
                            MyMessage myMessage = new MyMessage(MessageType.valueOf(String.valueOf(message.getMsgType().getValue()))
                                    , message, new DefaultUser(message));
                            myMessages.add(myMessage);
                        }

                        onAnchorContextMessageLoaded(myMessages);
                    }
                });
    }

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
            adapter.addMessages(messages, true);
        }
        // TODO: 2020/6/30 删除用户更新的自定义消息
        for (int i = 0; i < adapter.getData().size(); i++) {
            MyMessage message = (MyMessage) adapter.getData().get(i);
//            if (message.getAttachment() instanceof UserProfileAttachment)
//                adapter.deleteItem(adapter.getData().get(i), true);
        }

        firstLoad = false;
    }


    /**
     * 获取锚点
     *
     * @return
     */
    private MyMessage anchor() {
        if (adapter.getData().size() == 0) {
            return anchor == null ? IMessageBuilder.createEmptyMessage(container.account, container.sessionType, 0) : anchor;
        } else {
            int index = (direction == QueryDirectionEnum.QUERY_NEW ? adapter.getData().size() - 1 : 0);
            MyMessage message = (MyMessage) adapter.getData().get(index);
            return message;
        }
    }

}
