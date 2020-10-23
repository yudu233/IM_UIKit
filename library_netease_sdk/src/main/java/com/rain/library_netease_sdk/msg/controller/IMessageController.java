package com.rain.library_netease_sdk.msg.controller;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.rain.library_netease_sdk.msg.IMessageBuilder;
import com.rain.library_netease_sdk.msg.models.MyMessage;

/**
 * @author duyu
 * @version 1.0
 * @desc 消息体相关控制器
 * @filename IMessageController.java
 * @date 2020/5/14 14:51
 */
public class IMessageController {

    /**
     * 发送消息
     *
     * @param message 带发送的消息体，由{@link IMessageBuilder}构造
     */
    public static void sendMessage(MyMessage message) {
        sendMessage(message, false, null);

    }

    /**
     * 发送消息
     *
     * @param message  带发送的消息体，由{@link IMessageBuilder}构造
     * @param callback 可以设置回调函数。消息发送完成后才会调用，如果出错，会有具体的错误代码。
     */
    public static void sendMessage(MyMessage message, RequestCallback callback) {
        sendMessage(message, false, callback);
    }

    /**
     * 发送消息。<br>
     * 如果需要更新发送进度，请调用{@link MsgServiceObserve#observeMsgStatus(com.netease.nimlib.sdk.Observer, boolean)}
     *
     * @param message  带发送的消息体，由{@link IMessageBuilder}构造
     * @param resend   如果是发送失败后重发，标记为true，否则填false
     * @param callback 可以设置回调函数。消息发送完成后才会调用，如果出错，会有具体的错误代码。
     */
    public static void sendMessage(MyMessage message, boolean resend, RequestCallback callback) {
        NIMClient.getService(MsgService.class)
                .sendMessage(message.getMessage(), resend)
                .setCallback(callback);
    }
}
