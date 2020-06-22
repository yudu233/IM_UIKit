package com.rain.messagelist.Deprecated;

import android.util.Log;

import com.rain.messagelist.model.IMessage;

import java.util.HashMap;

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

    private static HashMap<Integer, Class<? extends MsgViewHolderBase>> viewHolders = new HashMap<>();

    private static Class<? extends MsgViewHolderBase> tipMsgViewHolder;

    static {
        // 初始化默认加载
        register(MessageMultipleItem.MESSAGE_TEXT, MsgViewHolderText.class);
        register(MessageMultipleItem.MESSAGE_IMAGE, MsgViewHolderPicture.class);

    }

    /**
     * 注册消息ViewHolder
     *
     * @param messageType 消息类型
     * @param viewHolder  消息ViewHolder
     */
    public static void register(Integer messageType, Class<? extends MsgViewHolderBase> viewHolder) {
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
        if (message.getMsgType() == MessageMultipleItem.MESSAGE_TIP) {
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
    public static HashMap<Integer, Class<? extends MsgViewHolderBase>> getAllViewHolders() {
        return viewHolders;
    }
}
