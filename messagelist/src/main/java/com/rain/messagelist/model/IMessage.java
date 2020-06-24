package com.rain.messagelist.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: IMessage
 * @CreateDate: 2020/6/6 10:44
 * @Describe:
 */
public interface IMessage extends Serializable {


    /**
     * 获取用户信息
     *
     * @return
     */
    IUser getUser();

    /**
     * 获取消息的uuid, 该域在生成消息时即会填上
     *
     * @return 消息uuid
     */
    String getUuid();

    /**
     * 获取聊天对象的Id（好友帐号，群ID等）。
     *
     * @return 聊天对象ID
     */
    String getSessionId();

    /**
     * 判断与参数message是否是同一条消息。<br>
     * 先比较数据库记录ID，如果没有数据库记录ID，则比较{@link #getUuid()}
     *
     * @param message 消息对象
     * @return 两条消息是否相同
     */
    boolean isTheSame(IMessage message);

    /**
     * 是否是收到的消息
     *
     * @return true:收到的消息 false：發出的消息
     */
    boolean isReceivedMessage();

    /**
     * 获取消息的消息类型
     *
     * @return 消息类型
     */
    int getMsgType();

    /**
     * 获取消息所属会话类型
     *
     * @return 会话类型
     */
    int getSessionType();

    /**
     * 获取消息发送者的昵称
     *
     * @return 用户的昵称
     */
    String getFromNick();

    /**
     * 设置消息具体内容。<br>
     *
     * @param content 消息内容/推送文本
     */
    void setContent(String content);

    /**
     * 获取消息具体内容。<br>
     *
     * @return 消息内容/推送文本
     */
    String getContent();

    /**
     * 获取媒体资源路径
     *
     * @return 媒体资源路径
     */
    String getMediaPath();

    /**
     * 获取缩略图路径
     *
     * @return 缩略图路径
     */
    String getThumbPath();

    /**
     * 获取图片宽度
     * @return
     */
    int getWidth();

    /**
     * 获取图片高度
     * @return
     */
    int getHeight();

    /**
     * 获取消息时间，单位为ms
     *
     * @return 时间
     */
    long getTime();

    /**
     * @param account 帐号
     */
    void setFromAccount(String account);

    /**
     * 获取该条消息发送方的帐号
     */
    String getFromAccount();

    /**
     * 获取消息附件文本内容
     *
     * @return
     */
    String getAttachStr();

    /**
     * 获取扩展字段（该字段会发送到其他端）
     *
     * @return 扩展字段Map
     */
    Map<String, Object> getRemoteExtension();

    /**
     * 设置扩展字段（该字段会发送到其他端），最大长度1024字节。
     *
     * @param remoteExtension 扩展字段Map，开发者需要保证此Map能够转换为JsonObject
     */
    void setRemoteExtension(Map<String, Object> remoteExtension);

    /**
     * 获取本地扩展字段（仅本地有效）
     *
     * @return 扩展字段Map
     */
    Map<String, Object> getLocalExtension();

    /**
     * 设置本地扩展字段（该字段仅在本地使用有效，不会发送给其他端），最大长度1024字节
     *
     * @param localExtension
     */
    void setLocalExtension(Map<String, Object> localExtension);

    /**
     * 判断自己发送的消息对方是否已读
     *
     * @return true：对方已读；false：对方未读
     */
    boolean isRemoteRead();

    /**
     * 是否需要消息已读（主要针对群消息）
     *
     * @return 该消息是否需要发送已读确认
     */
    boolean needMsgAck();

    /**
     * 设置该消息为需要消息已读的
     */
    void setMsgAck();

    /**
     * 是否已经发送过群消息已读回执
     *
     * @return 是否已经发送过已读回执
     */
    boolean hasSendAck();

    /**
     * 返回群消息已读回执的已读数
     *
     * @return 群里多少人已读了该消息
     */
    int getTeamMsgAckCount();

    /**
     * 返回群消息已读回执的未读数
     *
     * @return 群里多少人还未读该消息
     */
    int getTeamMsgUnAckCount();

    /**
     * 获取消息发送方类型
     *
     * @return 发送方的客户端类型，与ClientType类比较
     */
    int getFromClientType();

}
