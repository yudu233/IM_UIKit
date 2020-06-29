package com.rain.messagelist.model;

import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/20 15:08
 * @Version : 1.0
 * @Descroption :
 */
public interface ImageLoader {

    //加载用户头像
    void loadAvatarImage(FrameLayout frameLayout, boolean isReceiveMessage, String account);

    //加载图片消息封面(可具体ViewHolder实现)
    void loadMessageImage(AppCompatImageView imageView, int width,int height,String path);

    //加载视频消息封面(可具体ViewHolder实现)
    void loadVideoImage(AppCompatImageView imageView, String path);

}
