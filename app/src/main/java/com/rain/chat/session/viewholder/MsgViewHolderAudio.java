package com.rain.chat.session.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.rain.chat.R;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.viewholder.MsgViewHolderBase;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/3 11:02
 * @Version : 1.0
 * @Descroption : 音频消息
 */

public class MsgViewHolderAudio extends MsgViewHolderBase {

    public ConstraintLayout rootView;
    public AppCompatTextView mDuration;

    public MsgViewHolderAudio(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_audio;
    }

    @Override
    public int viewType() {
        return MessageType.audio.getValue();
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);
        rootView = holder.itemView.findViewById(R.id.containerView);
        mDuration = holder.itemView.findViewById(R.id.message_item_audio_duration);
    }
}
