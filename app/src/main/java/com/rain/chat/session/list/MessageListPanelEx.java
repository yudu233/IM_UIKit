package com.rain.chat.session.list;

import android.view.View;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.chat.session.module.Container;
import com.ycbl.im.uikit.msg.models.MyMessage;


/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MessageListPanelEx
 * @CreateDate: 2020/6/29 22:02
 * @Describe: 基于RecyclerView的消息收发模块
 */
public class MessageListPanelEx {

    private Container container;
    private View rootView;


    // 仅显示消息记录，不接收和发送消息
    private boolean recordOnly;
    // 从服务器拉取消息记录
    private boolean remote;

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

    }
}
