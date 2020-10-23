package com.ycbl.im.uikit.api.model.main;


import java.util.Map;

/**
 *  @Author : Rain
 *  @Version : 1.0
 *  @CreateDate :  2020/8/10 16:12
 *  @Description : 用户自定义推送 content 以及 payload 的接口
 */

public interface CustomPushContentProvider<T> {

    /**
     * 在消息发出去之前，回调此方法，用户需实现自定义的推送文案
     *
     * @param message
     */
    String getPushContent(T message);

    /**
     * 在消息发出去之前，回调此方法，用户需实现自定义的推送payload，它可以被消息接受者在通知栏点击之后得到
     *
     * @param message
     */
    Map<String, Object> getPushPayload(T message);

}
