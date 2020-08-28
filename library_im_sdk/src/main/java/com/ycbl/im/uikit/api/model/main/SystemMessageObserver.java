package com.ycbl.im.uikit.api.model.main;

import com.netease.nimlib.sdk.msg.model.SystemMessage;

/**
 * @author:duyu
 * @org :   www.yudu233.com
 * @email : yudu233@gmail.com
 * @date :  2019/6/18 14:34
 * @filename : SystemMessageObserver
 * @describe :
 */
public interface SystemMessageObserver {

    /**
     * 好友申请的系统消息
     *
     * @param message
     */
    void friendSystemMsg(SystemMessage message);

    /**
     * 群组的各种通知系统消息
     *
     * @param message
     */
    void groupVerifyMsg(SystemMessage message);
}
