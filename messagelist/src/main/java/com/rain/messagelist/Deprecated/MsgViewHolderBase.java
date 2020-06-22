package com.rain.messagelist.Deprecated;

import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.R;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgViewHolderBase
 * @CreateDate: 2020/6/6 10:42
 * @Describe:
 */
public abstract class MsgViewHolderBase extends RecyclerViewHolder<BaseMultiItemQuickAdapter, BaseViewHolder, IMessage> {

    private View view;

    public MsgViewHolderBase(BaseMultiItemQuickAdapter adapter) {
        super(adapter);
    }

    // 根据layout id查找对应的控件
    protected <T extends View> T findViewById(int id) {
        return (T) view.findViewById(id);
    }


    @Override
    public void convert(BaseViewHolder holder, IMessage data, int position) {
        view = holder.itemView;

        FrameLayout contentContainer = findViewById(R.id.message_item_content);
        // 这里只要inflate出来后加入一次即可
        if (contentContainer.getChildCount() == 0) {
            View.inflate(view.getContext(), getContentResId(), contentContainer);
        }
    }

    abstract protected int getContentResId();
    // 在该接口中根据layout对各控件成员变量赋值
    abstract protected void inflateContentView();
}