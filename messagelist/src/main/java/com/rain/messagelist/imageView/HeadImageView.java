package com.rain.messagelist.imageView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/8 17:06
 * @Version : 1.0
 * @Descroption : 聊天头像
 */
public class HeadImageView extends FrameLayout {

    public HeadImageView(@NonNull Context context) {
        super(context);
    }

    public HeadImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeadImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void showAvatar(){

    }

}
