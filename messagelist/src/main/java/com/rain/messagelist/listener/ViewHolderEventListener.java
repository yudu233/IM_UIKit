package com.rain.messagelist.listener;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.rain.messagelist.model.IMessage;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/15 10:59
 * @Version : 1.0
 * @Descroption : ViewHolder的一些点击事件的响应处理函数
 */
public interface ViewHolderEventListener {
    // 长按事件响应处理
    boolean onViewHolderLongClick(View clickView, View viewHolderView, IMessage item);

    // 发送失败或者多媒体文件下载失败指示按钮点击响应处理
    void onFailedBtnClick(IMessage resendMessage);

    // 图片消息点击事件
    void onPictureViewHolderClick(AppCompatImageView imageView, IMessage message);

    //视频消息点击事件
    void onVideoViewHolderClick(AppCompatImageView imageView, IMessage message);

}
