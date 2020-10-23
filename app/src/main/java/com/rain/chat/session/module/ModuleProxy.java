package com.rain.chat.session.module;

import com.rain.library_netease_sdk.msg.models.MyMessage;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: ModuleProxy
 * @CreateDate: 2020/6/29 22:29
 * @Describe: 会话窗口提供给子模块的代理接口。
 */
public interface ModuleProxy {
    // 发送消息
    boolean sendMessage(MyMessage msg);
//
//    // 消息输入区展开时候的处理
//    void onInputPanelExpand();
//
//    // 应当收起输入区
//    void shouldCollapseInputPanel();
//
//    // 是否正在录音
//    boolean isLongClickEnabled();
//
//    void onItemFooterClick(MyMessage message);
}
