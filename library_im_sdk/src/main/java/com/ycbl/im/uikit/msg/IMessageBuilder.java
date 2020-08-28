package com.ycbl.im.uikit.msg;

import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.rain.messagelist.message.SessionType;
import com.ycbl.im.uikit.msg.models.MyMessage;
import com.ycbl.im.uikit.msg.models.MyMsgAttachment;
import com.ycbl.im.uikit.msg.test.MessageStrategy;

import java.io.File;

/**
 * @author duyu
 * @version 1.0
 * @desc IMMessage消息构造器
 * @filename IMessageBuilder.java
 * @date 2020/5/14 11:58
 */
public class IMessageBuilder {


    private MessageStrategy messageStrategy;

    public IMessageBuilder(MessageStrategy messageStrategy) {
        this.messageStrategy = messageStrategy;
    }

    /**
     * 创建一条普通文本消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param text        文本消息内容
     * @return 文本消息
     */
    public MyMessage createTextMessage(String sessionId, SessionType sessionType, String text) {
        return messageStrategy.createTextMessage(sessionId, sessionType, text);
    }

    /**
     * 创建一条图片消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        图片文件
     * @param displayName 图片文件的显示名，可不同于文件名
     * @return 图片消息
     */
    public MyMessage createImageMessage(String sessionId, SessionType sessionType,
                                        File file, String displayName) {
        return messageStrategy.createImageMessage(sessionId, sessionType, file, displayName);
    }

    /**
     * 创建一条音频消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        音频文件对象
     * @param duration    音频文件持续时间，单位是ms
     * @return 音频消息
     */
    public MyMessage createAudioMessage(String sessionId, SessionType sessionType,
                                        File file, long duration) {
        return messageStrategy.createAudioMessage(sessionId, sessionType, file, duration);
    }

    /**
     * 创建一条地理位置信息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param lat         纬度
     * @param lng         经度
     * @param addr        地理位置描述信息
     * @return 位置消息
     */
    public MyMessage createLocationMessage(String sessionId, SessionType sessionType,
                                           double lat, double lng, String addr) {
        return messageStrategy.createLocationMessage(sessionId, sessionType, lat, lng, addr);
    }

    /**
     * 创建一条视频消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        视频文件对象
     * @param duration    视频文件持续时间
     * @param width       视频宽度
     * @param height      视频高度
     * @param displayName 视频文件显示名，可以为空
     * @return 视频消息
     */
    public MyMessage createVideoMessage(String sessionId, SessionType sessionType,
                                        File file, long duration, int width, int height, String displayName) {
        return messageStrategy.createVideoMessage(sessionId, sessionType, file, duration, width, height, displayName);
    }

    /**
     * 创建一条文件消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        文件
     * @param displayName 文件的显示名，可不同于文件名
     * @return 文件消息
     */
    public MyMessage createFileMessage(String sessionId, SessionType sessionType,
                                       File file, String displayName) {
        return messageStrategy.createFileMessage(sessionId, sessionType, file, displayName);
    }

    /**
     * 创建一条提醒消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @return 提醒消息
     */
    public MyMessage createTipMessage(String sessionId, SessionType sessionType) {
        return messageStrategy.createTipMessage(sessionId, sessionType);
    }

    /**
     * 创建一条APP自定义类型消息, 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过IMMessage#getContent()获取，主要用户推送展示。
     * @param attachment  消息附件对象
     * @return 自定义消息
     */
    public MyMessage createCustomMessage(String sessionId, SessionType sessionType,
                                         String content, MyMsgAttachment attachment) {
        return createCustomMessage(sessionId, sessionType, content, attachment, null);
    }

    /**
     * 创建一条APP自定义类型消息, 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过IMMessage#getContent()获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     * @param config      自定义消息配置
     * @return 自定义消息
     */
    public MyMessage createCustomMessage(String sessionId, SessionType sessionType,
                                         String content, MyMsgAttachment attachment, CustomMessageConfig config) {
        return messageStrategy.createCustomMessage(sessionId, sessionType, content, attachment, config);
    }

    /**
     * 创建一条空消息，仅设置了聊天对象以及时间点，用于记录查询
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param time        查询的时间起点信息
     * @return 空消息
     */
    public MyMessage createEmptyMessage(String sessionId, SessionType sessionType, long time) {
        return messageStrategy.createEmptyMessage(sessionId, sessionType, time);
    }
}
