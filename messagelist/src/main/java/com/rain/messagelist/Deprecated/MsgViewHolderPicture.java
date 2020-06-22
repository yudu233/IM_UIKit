package com.rain.messagelist.Deprecated;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.rain.messagelist.Deprecated.MsgViewHolderBase;
import com.rain.messagelist.R;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgViewHolderText
 * @CreateDate: 2020/6/6 11:45
 * @Describe:
 */
public class MsgViewHolderPicture extends MsgViewHolderBase {


    public MsgViewHolderPicture(BaseMultiItemQuickAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.im_message_item_picture;
    }

    @Override
    protected void inflateContentView() {

    }
}
