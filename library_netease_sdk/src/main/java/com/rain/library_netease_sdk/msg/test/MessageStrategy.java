package com.rain.library_netease_sdk.msg.test;

import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.rain.messagelist.message.SessionType;
import com.rain.library_netease_sdk.msg.models.MyMessage;
import com.rain.library_netease_sdk.msg.models.MyMsgAttachment;

import java.io.File;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/10 10:12
 * @Version : 1.0
 * @Descroption : 消息创建接口
 */
public interface MessageStrategy {
    /**
     * 创建一条普通文本消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param text        文本消息内容
     * @return 文本消息
     */
    MyMessage createTextMessage(String sessionId, SessionType sessionType, String text);

    /**
     * 创建一条图片消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        图片文件
     * @param displayName 图片文件的显示名，可不同于文件名
     * @return 图片消息
     */
    MyMessage createImageMessage(String sessionId, SessionType sessionType,
                                 File file, String displayName);


    /**
     * 创建一条音频消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        音频文件对象
     * @param duration    音频文件持续时间，单位是ms
     * @return 音频消息
     */
    MyMessage createAudioMessage(String sessionId, SessionType sessionType,
                                 File file, long duration);

    /**
     * 创建一条地理位置消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param lat         纬度
     * @param lng         经度
     * @param address     地理位置描述信息
     * @return 位置消息
     */
    MyMessage createLocationMessage(String sessionId, SessionType sessionType,
                                    double lat, double lng, String address);

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
    MyMessage createVideoMessage(String sessionId, SessionType sessionType,
                                 File file, long duration, int width, int height, String displayName);

     /**
      * 创建一条文件消息
      *
      * @param sessionId   聊天对象ID
      * @param sessionType 会话类型
      * @param file        文件
      * @param displayName 文件的显示名，可不同于文件名
      * @return 文件消息
      */
     MyMessage createFileMessage(String sessionId, SessionType sessionType,
                                               File file, String displayName);

     /**
      * 创建一条提醒消息
      *
      * @param sessionId   聊天对象ID
      * @param sessionType 会话类型
      * @return 提醒消息
      */
     MyMessage createTipMessage(String sessionId, SessionType sessionType);

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
     MyMessage createCustomMessage(String sessionId, SessionType sessionType,
                                                 String content, MyMsgAttachment attachment,
                                   CustomMessageConfig config);

     /**
      * 创建一条空消息，仅设置了聊天对象以及时间点，用于记录查询
      *
      * @param sessionId   聊天对象ID
      * @param sessionType 会话类型
      * @param time        查询的时间起点信息
      * @return 空消息
      */
     MyMessage createEmptyMessage(String sessionId, SessionType sessionType, long time);
}
