package com.rain.messagelist.viewholder;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.rain.messagelist.R;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.widget.MsgThumbImageView;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 16:41
 * @Version : 1.0
 * @Descroption :
 */
public  abstract class MsgViewHolderThumbBase extends MsgViewHolderBase {

    private float density;
    private float MIN_WIDTH;
    private float MAX_WIDTH;
    private float MIN_HEIGHT;
    private float MAX_HEIGHT;
    public MsgThumbImageView mImageView;

    public MsgViewHolderThumbBase(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);

        density = context.getResources().getDisplayMetrics().density;
        MIN_WIDTH = 100 * density;
        MAX_WIDTH = 200 * density;
        MIN_HEIGHT = 100 * density;
        MAX_HEIGHT = 200 * density;

        mImageView = findViewById(R.id.message_item_thumb_thumbnail);
        int imageWidth = data.getWidth();
        int imageHeight = data.getHeight();


        // 裁剪 bitmap
        float width;
        float height;
        if (imageWidth > imageHeight) {
            if (imageWidth > MAX_WIDTH) {
                float temp = MAX_WIDTH / imageWidth * imageHeight;
                height = temp > MIN_HEIGHT ? temp : MIN_HEIGHT;
                width = MAX_WIDTH;
            } else if (imageWidth < MIN_WIDTH) {
                float temp = MIN_WIDTH / imageWidth * imageHeight;
                height = temp < MAX_HEIGHT ? temp : MAX_HEIGHT;
                width = MIN_WIDTH;
            } else {
                float ratio = imageWidth / imageHeight;
                if (ratio > 3) {
                    ratio = 3;
                }
                height = imageHeight * ratio;
                width = imageWidth;
            }
        } else {
            if (imageHeight > MAX_HEIGHT) {
                float temp = MAX_HEIGHT / imageHeight * imageWidth;
                width = temp > MIN_WIDTH ? temp : MIN_WIDTH;
                height = MAX_HEIGHT;
            } else if (imageHeight < MIN_HEIGHT) {
                float temp = MIN_HEIGHT / imageHeight * imageWidth;
                width = temp < MAX_WIDTH ? temp : MAX_WIDTH;
                height = MIN_HEIGHT;
            } else {
                float ratio = imageHeight / imageWidth;
                if (ratio > 3) {
                    ratio = 3;
                }
                width = imageWidth * ratio;
                height = imageHeight;
            }
        }
        setLayoutParams((int) width, (int) height, mImageView);
        getMsgAdapter().getImageLoader().loadMessageImage(mImageView, (int) width, (int) height, data.getMediaPath());
    }

    public int getImageMaxWidth() {
        return (int) MAX_WIDTH;
    }

    public int getImageMinWidth() {
        return (int) MIN_WIDTH;
    }
}
