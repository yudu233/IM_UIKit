package com.ycbl.im.uikit.msg.models;

import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.rain.messagelist.model.IMsgAttachment;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: IMsgAttachment
 * @CreateDate: 2020/6/30 0:05
 * @Describe:
 */
public class MyMsgAttachment implements IMsgAttachment {
    private MsgAttachment attachment;

    public MyMsgAttachment(MsgAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toJson(boolean send) {
        return attachment.toJson(send);
    }
}
