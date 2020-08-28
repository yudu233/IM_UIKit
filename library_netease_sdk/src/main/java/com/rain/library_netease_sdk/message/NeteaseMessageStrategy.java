package com.rain.library_netease_sdk.message;

import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.message.SessionType;
import com.ycbl.im.uikit.msg.models.DefaultUser;
import com.ycbl.im.uikit.msg.models.MyMessage;
import com.ycbl.im.uikit.msg.models.MyMsgAttachment;
import com.ycbl.im.uikit.msg.test.MessageStrategy;

import java.io.File;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/10 10:13
 * @Version : 1.0
 * @Descroption : IM消息体创建
 */
public class NeteaseMessageStrategy implements MessageStrategy {

    @Override
    public MyMessage createTextMessage(String sessionId, SessionType sessionType, String text) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage textMessage = MessageBuilder.createTextMessage(sessionId, sessionTypeEnum, text);
        MyMessage iMessage = new MyMessage(MessageType.text, textMessage, new DefaultUser(textMessage));
        return iMessage;
    }

    @Override
    public MyMessage createImageMessage(String sessionId, SessionType sessionType, File file, String displayName) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage imageMessage = MessageBuilder.createImageMessage(sessionId, sessionTypeEnum, file, displayName);
        MyMessage iMessage = new MyMessage(MessageType.image, imageMessage, new DefaultUser(imageMessage));
        return iMessage;
    }

    @Override
    public MyMessage createAudioMessage(String sessionId, SessionType sessionType, File file, long duration) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage audioMessage = MessageBuilder.createAudioMessage(sessionId, sessionTypeEnum, file, duration);
        MyMessage iMessage = new MyMessage(MessageType.audio, audioMessage, new DefaultUser(audioMessage));
        return iMessage;
    }

    @Override
    public MyMessage createLocationMessage(String sessionId, SessionType sessionType, double lat, double lng, String address) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage locationMessage = MessageBuilder.createLocationMessage(sessionId, sessionTypeEnum, lat, lng, address);
        MyMessage iMessage = new MyMessage(MessageType.location, locationMessage, new DefaultUser(locationMessage));
        return iMessage;
    }

    @Override
    public MyMessage createVideoMessage(String sessionId, SessionType sessionType, File file, long duration, int width, int height, String displayName) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage videoMessage = MessageBuilder.createVideoMessage(
                sessionId, sessionTypeEnum, file, duration, width, height, displayName);
        MyMessage iMessage = new MyMessage(MessageType.video, videoMessage, new DefaultUser(videoMessage));
        return iMessage;
    }

    @Override
    public MyMessage createFileMessage(String sessionId, SessionType sessionType, File file, String displayName) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage fileMessage = MessageBuilder.createFileMessage(
                sessionId, sessionTypeEnum, file, displayName);
        MyMessage iMessage = new MyMessage(MessageType.file, fileMessage, new DefaultUser(fileMessage));
        return iMessage;
    }

    @Override
    public MyMessage createTipMessage(String sessionId, SessionType sessionType) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage fileMessage = MessageBuilder.createTipMessage(
                sessionId, sessionTypeEnum);
        MyMessage iMessage = new MyMessage(MessageType.tip, fileMessage, new DefaultUser(fileMessage));
        return iMessage;
    }

    @Override
    public MyMessage createCustomMessage(String sessionId, SessionType sessionType,
                                         String content, MyMsgAttachment attachment, CustomMessageConfig config) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage customMessage = MessageBuilder.createCustomMessage(
                sessionId, sessionTypeEnum, content, attachment.getAttachment(), config);
        MyMessage iMessage = new MyMessage(MessageType.custom, customMessage, new DefaultUser(customMessage));
        return iMessage;
    }

    @Override
    public MyMessage createEmptyMessage(String sessionId, SessionType sessionType, long time) {
        SessionTypeEnum sessionTypeEnum = covertSessionType(sessionType);
        IMMessage emptyMessage = MessageBuilder.createEmptyMessage(sessionId, sessionTypeEnum, time);
        MyMessage iMessage = new MyMessage(MessageType.text, emptyMessage, new DefaultUser(emptyMessage));
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
