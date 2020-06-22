package com.rain.messagelist.message;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/16 18:10
 * @Version : 1.0
 * @Descroption :
 */
public enum MessageType {

    /**
     * 未知消息类型
     */
    undef(-1, "Unknown"),

    /**
     * 文本消息类型
     */
    text(1, ""),

    /**
     * 图片消息
     */
    image(2, "图片"),

    /**
     * 音频消息
     */
    audio(3, "语音"),

    /**
     * 视频消息
     */
    video(4, "视频"),

    /**
     * 位置消息
     */
    location(5, "位置"),

    /**
     * 文件消息
     */
    file(6, "文件"),

    /**
     * 音视频通话
     */
    avchat(7, "音视频通话"),

    /**
     * 通知消息
     */
    notification(8, "通知消息"),

    /**
     * 提醒类型消息
     */
    tip(9, "提醒消息"),

    /**
     * 自定义消息
     */
    custom(10, "自定义消息");


    final private int value;
    final String sendMessageTip;

    MessageType(int value, String sendMessageTip) {
        this.value = value;
        this.sendMessageTip = sendMessageTip;
    }

    public final int getValue() {
        return value;
    }

    public final String getSendMessageTip() {
        return sendMessageTip;
    }

}
