package com.rain.messagelist.Deprecated;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgViewHolderUnknown
 * @CreateDate: 2020/6/6 11:48
 * @Describe:
 */
public class MsgViewHolderUnknown extends MsgViewHolderBase{

    public MsgViewHolderUnknown(BaseMultiItemQuickAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return 0;
    }

    @Override
    protected void inflateContentView() {

    }
}

