package com.rain.messagelist.Deprecated;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.rain.messagelist.model.IMessage;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MultipleItem
 * @CreateDate: 2020/6/6 10:48
 * @Describe:
 */
public class MessageMultipleItem implements MultiItemEntity {

    public static final int MESSAGE_UNKNOWN = -1;
    public static final int MESSAGE_TIP = 0;
    public static final int MESSAGE_TEXT = 1;
    public static final int MESSAGE_IMAGE = 2;


    private int itemType;
    private IMessage message;

    public MessageMultipleItem(IMessage message) {
        this.itemType = message.getMsgType().getValue();
        this.message = message;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public IMessage getMessage() {
        return message;
    }
}
