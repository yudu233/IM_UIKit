package com.rain.messagelist.message;

import android.util.Log;

import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.viewholder.MsgViewHolderBase;
import com.rain.messagelist.viewholder.MsgViewHolderPicture;
import com.rain.messagelist.viewholder.MsgViewHolderText;
import com.rain.messagelist.viewholder.MsgViewHolderUnknown;
import com.rain.messagelist.viewholder.MsgViewHolderVideo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgViewHolderFactory
 * @CreateDate: 2020/6/6 11:00
 * @Describe: 消息项展示ViewHolder工厂类
 */
public class MsgViewHolderFactory {

    private static final String TAG = "MsgViewHolderFactory";

    private static HashMap<MessageType, Class<? extends MsgViewHolderBase>> viewHolders = new HashMap<>();

    private static Class<? extends MsgViewHolderBase> tipMsgViewHolder;

    static {
        // 初始化默认加载
        register(MessageType.text, MsgViewHolderText.class);
        register(MessageType.image, MsgViewHolderPicture.class);
        register(MessageType.video, MsgViewHolderVideo.class);
    }

    /**
     * 注册消息ViewHolder
     *
     * @param messageType 消息类型
     * @param viewHolder  消息ViewHolder
     */
    public static void register(MessageType messageType, Class<? extends MsgViewHolderBase> viewHolder) {
        viewHolders.put(messageType, viewHolder);
    }

    /**
     * 注册Tips消息ViewHolder
     *
     * @param viewHolder 消息ViewHolder
     */
    public static void registerTipMsgViewHolder(Class<? extends MsgViewHolderBase> viewHolder) {
        tipMsgViewHolder = viewHolder;
    }

    /**
     * 根据消息类型获取相应的ViewHolder
     *
     * @param message 消息体
     * @return
     */
    public static Class<? extends MsgViewHolderBase> getViewHolderByType(IMessage message) {
        if (message.getMsgType() == MessageType.tip) {
            return tipMsgViewHolder == null ? MsgViewHolderUnknown.class : tipMsgViewHolder;
        }
        Log.d(TAG, "getViewHolderByType() returned: " + viewHolders.get(message.getMsgType()));
        return viewHolders.get(message.getMsgType());
    }

    /**
     * 获取所有注册的ViewHolder
     *
     * @return
     */
    public static HashMap<MessageType, Class<? extends MsgViewHolderBase>> getAllViewHolders() {
        return viewHolders;
    }


    public static List<Class<? extends MsgViewHolderBase>> getAll() {
        List<Class<? extends MsgViewHolderBase>> list = new ArrayList<>();
        list.addAll(viewHolders.values());
        return list;
    }
}
