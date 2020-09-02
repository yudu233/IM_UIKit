package com.ycbl.im.uikit.msg.models;

import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.model.IMsgAttachment;
import com.rain.messagelist.model.IUser;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MyMessage
 * @CreateDate: 2020/6/6 14:20
 * @Describe: 消息体
 */
public class MyMessage implements IMessage, Serializable {

    private MessageType msgType;
    private IMMessage message;
    private IUser user;


    public MyMessage(MessageType msgType, IMMessage message, IUser user) {
        this.msgType = msgType;
        this.message = message;
        this.user = user;
    }

    public IMMessage getMessage() {
        return message;
    }

    @Override
    public IUser getUser() {
        if (user == null) {
            return new DefaultUser(message);
        }
        return user;
    }

    @Override
    public String getUuid() {
        return message.getUuid();
    }

    @Override
    public String getSessionId() {
        return message.getSessionId();
    }

    @Override
    public boolean isReceivedMessage() {
        return message.getDirect() == MsgDirectionEnum.In;
    }

    @Override
    public MessageType getMsgType() {
        return msgType;
    }


    @Override
    public int getSessionType() {
        return message.getSessionType().getValue();
    }

    @Override
    public boolean isTheSame(IMessage iMessage) {
        return false;
    }

    @Override
    public String getFromNick() {
        return message.getFromNick();
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public String getContent() {
        return message.getContent();
    }

    @Override
    public String getExtension() {
        FileAttachment attachment = (FileAttachment) message.getAttachment();
        return attachment.getExtension();
    }

    @Override
    public String getMediaPath() {
        if (message.getAttachment() instanceof ImageAttachment) {
            ImageAttachment attachment = (ImageAttachment) message.getAttachment();
            return attachment.getPath() == null ? attachment.getUrl() : attachment.getPath();
        } else if (message.getAttachment() instanceof VideoAttachment) {
            VideoAttachment attachment = (VideoAttachment) message.getAttachment();
            return attachment.getPath() == null ? attachment.getUrl() : attachment.getPath();
        }
        return null;
    }

    @Override
    public String getThumbPath() {
        if (message.getAttachment() instanceof ImageAttachment) {
            ImageAttachment attachment = (ImageAttachment) message.getAttachment();
            return attachment.getThumbPath() == null ? attachment.getThumbUrl() : attachment.getThumbPath();
        } else if (message.getAttachment() instanceof VideoAttachment) {
            VideoAttachment attachment = (VideoAttachment) message.getAttachment();
            return attachment.getThumbPath();
        }
        return null;
    }

    @Override
    public int getWidth() {
        if (message.getAttachment() instanceof ImageAttachment) {
            ImageAttachment attachment = (ImageAttachment) message.getAttachment();
            return attachment.getWidth();
        } else if (message.getAttachment() instanceof VideoAttachment) {
            VideoAttachment attachment = (VideoAttachment) message.getAttachment();
            return attachment.getWidth();
        }
        return 0;
    }

    @Override
    public int getHeight() {
        if (message.getAttachment() instanceof ImageAttachment) {
            ImageAttachment attachment = (ImageAttachment) message.getAttachment();
            return attachment.getHeight();
        } else if (message.getAttachment() instanceof VideoAttachment) {
            VideoAttachment attachment = (VideoAttachment) message.getAttachment();
            return attachment.getHeight();
        }
        return 0;
    }


    @Override
    public long getTime() {
        return message.getTime();
    }

    @Override
    public void setFromAccount(String account) {

    }

    @Override
    public String getFromAccount() {
        return message.getFromAccount();
    }

    @Override
    public String getAttachStr() {
        return message.getAttachStr();
    }

    @Override
    public Map<String, Object> getRemoteExtension() {
        return message.getRemoteExtension();
    }

    @Override
    public void setRemoteExtension(Map<String, Object> remoteExtension) {

    }

    @Override
    public Map<String, Object> getLocalExtension() {
        return message.getLocalExtension();
    }

    @Override
    public void setLocalExtension(Map<String, Object> localExtension) {

    }

    @Override
    public boolean isRemoteRead() {
        return message.isRemoteRead();
    }

    @Override
    public boolean needMsgAck() {
        return message.isChecked();
    }

    @Override
    public void setMsgAck() {

    }

    @Override
    public boolean hasSendAck() {
        return message.hasSendAck();
    }

    @Override
    public int getTeamMsgAckCount() {
        return message.getTeamMsgAckCount();
    }

    @Override
    public int getTeamMsgUnAckCount() {
        return message.getTeamMsgUnAckCount();
    }

    @Override
    public int getFromClientType() {
        return message.getFromClientType();
    }

    @Override
    public IMsgAttachment getAttachment() {
        return new MyMsgAttachment(message.getAttachment());
    }
}
