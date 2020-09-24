package com.ycbl.im.uikit.api.model.main;

/**
 * @author:duyu
 * @org :   www.yudu233.com
 * @email : yudu233@gmail.com
 * @date :  2019/6/18 14:34
 * @filename : SystemMessageObserver
 * @describe : 系统消息观察者
 */
public interface SystemMessageObserver<T> {

    /**
     * 好友申请的系统消息
     *
     * @param message
     */
    void friendSystemMsg(T message);

    /**
     * 群组的各种通知系统消息
     *
     * @param message
     */
    void groupVerifyMsg(T message);
}
