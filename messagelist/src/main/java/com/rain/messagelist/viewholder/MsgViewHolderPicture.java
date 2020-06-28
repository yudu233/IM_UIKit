package com.rain.messagelist.viewholder;

import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.rain.messagelist.R;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: PictureItemProvider
 * @CreateDate: 2020/6/6 16:46
 * @Describe: 图片消息
 */
public class MsgViewHolderPicture extends MsgViewHolderBase {
    private static final String TAG = "MsgViewHolderPicture";

    private float density;
    private float MIN_WIDTH;
    private float MAX_WIDTH;
    private float MIN_HEIGHT;
    private float MAX_HEIGHT;
    private AppCompatImageView mImageView;


    public MsgViewHolderPicture(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.im_message_item_picture;
    }

    @Override
    public int viewType() {
        return MessageType.image.getValue();
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
        getMsgAdapter().getImageLoader().loadMessageImage(mImageView, data.getMediaPath());
    }

    public int getImageMaxWidth() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return (int) (165.0 / 320.0 * displayMetrics.widthPixels);
    }

    public int getImageMinWidth() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return (int) (76.0 / 320.0 * displayMetrics.widthPixels);
    }

    @Override
    protected void onItemClick() {
        getMsgAdapter().getViewHolderEventListener().onPictureViewHolderClick(mImageView, message);
    }
}
