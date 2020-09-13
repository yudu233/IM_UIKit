package com.rain.chat.session.viewholder;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.rain.chat.R;
import com.rain.messagelist.model.IMessage;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/3 15:31
 * @Version : 1.0
 * @Descroption :
 */
public class MsgViewHolderTranslateAudio extends MsgViewHolderAudio {
    public MsgViewHolderTranslateAudio(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);
        View translateView = LayoutInflater.from(mContext).inflate(R.layout.nim_message_item_audio_translate, null);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.connect(translateView.getId(), ConstraintSet.TOP, mDuration.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(translateView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//        rootView.addView(translateView);
    }
}
