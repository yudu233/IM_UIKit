package com.rain.messagelist.Deprecated;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.R;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgViewHolderText
 * @CreateDate: 2020/6/6 11:45
 * @Describe:
 */
public class MsgViewHolderText extends MsgViewHolderBase {


    public MsgViewHolderText(BaseMultiItemQuickAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.im_message_item_text;
    }

    @Override
    protected void inflateContentView() {

    }

    @Override
    public void convert(BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);
    }
    
}
