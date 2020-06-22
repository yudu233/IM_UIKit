package com.ycbl.im.uikit.msg;

import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.message.SessionType;
import com.ycbl.im.uikit.msg.models.DefaultUser;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.io.File;

/**
 * @author duyu
 * @version 1.0
 * @desc IMMessage消息构造器
 * @filename IMessageBuilder.java
 * @date 2020/5/14 11:58
 */
public class IMessageBuilder {

    /**
     * 创建一条普通文本消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param text        文本消息内容
     * @return IMMessage 生成的消息对象
     */
    public static MyMessage createTextMessage(String sessionId, SessionType sessionType, String text) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage textMessage = MessageBuilder.createTextMessage(sessionId, sessionTypeEnum, text);
        MyMessage iMessage = new MyMessage(MessageType.text, textMessage, new DefaultUser(textMessage));
        return iMessage;
    }

    /**
     * 创建一条图片消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        图片文件
     * @param displayName 图片文件的显示名，可不同于文件名
     * @return IMMessage 生成的消息对象
     */
    public static MyMessage createImageMessage(String sessionId, SessionType sessionType,
                                               File file, String displayName) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage imageMessage = MessageBuilder.createImageMessage(sessionId, sessionTypeEnum, file, displayName);
        MyMessage iMessage = new MyMessage(MessageType.image, imageMessage, new DefaultUser(imageMessage));
        return iMessage;
    }

    /**
     * 创建一条音频消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        音频文件对象
     * @param duration    音频文件持续时间，单位是ms
     * @return IMMessage 生成的消息对象
     */
    public static MyMessage createAudioMessage(String sessionId, SessionType sessionType,
                                               File file, long duration) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage audioMessage = MessageBuilder.createAudioMessage(sessionId, sessionTypeEnum, file, duration);
        MyMessage iMessage = new MyMessage(MessageType.audio, audioMessage, new DefaultUser(audioMessage));
        return iMessage;
    }

    /**
     * 创建一条地理位置信息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param lat         纬度
     * @param lng         经度
     * @param addr        地理位置描述信息
     * @return IMMessage 生成的消息对象
     */
    public static MyMessage createLocationMessage(String sessionId, SessionType sessionType,
                                                  double lat, double lng, String addr) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage locationMessage = MessageBuilder.createLocationMessage(sessionId, sessionTypeEnum, lat, lng, addr);
        MyMessage iMessage = new MyMessage(MessageType.location, locationMessage, new DefaultUser(locationMessage));
        return iMessage;
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
    public static MyMessage createVideoMessage(String sessionId, SessionType sessionType,
                                               File file, long duration, int width, int height, String displayName) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage videoMessage = MessageBuilder.createVideoMessage(
                sessionId, sessionTypeEnum, file, duration, width, height, displayName);
        MyMessage iMessage = new MyMessage(MessageType.video, videoMessage, new DefaultUser(videoMessage));
        return iMessage;
    }

    /**
     * 创建一条文件消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        文件
     * @param displayName 文件的显示名，可不同于文件名
     * @return IMMessage 生成的消息对象
     */
    public static MyMessage createFileMessage(String sessionId, SessionType sessionType,
                                              File file, String displayName) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage fileMessage = MessageBuilder.createFileMessage(
                sessionId, sessionTypeEnum, file, displayName);
        MyMessage iMessage = new MyMessage(MessageType.file, fileMessage, new DefaultUser(fileMessage));
        return iMessage;
    }

    /**
     * 创建一条提醒消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @return IMMessage 生成的消息对象
     */
    public static MyMessage createTipMessage(String sessionId, SessionType sessionType) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage fileMessage = MessageBuilder.createTipMessage(
                sessionId, sessionTypeEnum);
        MyMessage iMessage = new MyMessage(MessageType.tip, fileMessage, new DefaultUser(fileMessage));
        return iMessage;
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
    public static MyMessage createCustomMessage(String sessionId, SessionType sessionType,
                                                String content, MsgAttachment attachment) {
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
    public static MyMessage createCustomMessage(String sessionId, SessionType sessionType,
                                                String content, MsgAttachment attachment, CustomMessageConfig config) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage customMessage = MessageBuilder.createCustomMessage(
                sessionId, sessionTypeEnum, content, attachment, config);
        MyMessage iMessage = new MyMessage(MessageType.custom, customMessage, new DefaultUser(customMessage));
        return iMessage;
    }

    /**
     * covert SessionType to SessionTypeEnum
     *
     * @param sessionType
     * @return SessionTypeEnum
     */
    protected static SessionTypeEnum covertSessionType(SessionType sessionType) {
        switch (sessionType) {
            case P2P:
                return SessionTypeEnum.P2P;
            case Team:
                return SessionTypeEnum.Team;
            case System:
                return SessionTypeEnum.System;
            case ChatRoom:
                return SessionTypeEnum.ChatRoom;
            case SUPER_TEAM:
                return SessionTypeEnum.SUPER_TEAM;
            default:
                return SessionTypeEnum.None;
        }
    }
}
