package com.rain.messagelist.viewholder;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.rain.messagelist.R;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 16:33
 * @Version : 1.0
 * @Descroption : 视频消息ViewHolder
 */
public class MsgViewHolderVideo extends MsgViewHolderThumbBase {
    public MsgViewHolderVideo(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.im_message_item_video;
    }

    @Override
    public int viewType() {
        return MessageType.video.getValue();
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);
    }

    @Override
    protected void onItemClick() {
        getMsgAdapter().getViewHolderEventListener().onVideoViewHolderClick(mImageView, message);
    }
}
