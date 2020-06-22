package com.rain.messagelist.viewholder;

import com.chad.library.adapter.base.MultipleItemRvAdapter;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/16 18:23
 * @Version : 1.0
 * @Descroption : 未知消息
 */
public class MsgViewHolderUnknown extends MsgViewHolderBase {

    public MsgViewHolderUnknown(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return 0;
    }

    @Override
    public int viewType() {
        return 0;
    }
}
