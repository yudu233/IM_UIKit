package com.rain.messagelist.viewholder;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.rain.messagelist.R;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: TextItemProvider
 * @CreateDate: 2020/6/6 16:33
 * @Describe: 文本消息
 */

public class MsgViewHolderText extends MsgViewHolderBase {

    public MsgViewHolderText(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.im_message_item_text;
    }

    @Override
    public int viewType() {
        return MessageType.text.getValue();
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);
        AppCompatTextView bodyTextView = findViewById(R.id.nim_message_item_text_body);
        bodyTextView.setText(message.getContent());
        bodyTextView.setTextColor(isReceivedMessage() ? Color.BLACK : Color.WHITE);
    }

    @Override
    protected int leftBackground() {
        return R.drawable.im_message_left_white_bg;
    }

    @Override
    protected int rightBackground() {
        return R.drawable.im_message_right_blue_bg;
    }
}
