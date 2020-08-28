package com.rain.crow.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.rain.crow.PhotoPickOptions;
import com.rain.crow.R;
import com.rain.crow.bean.PhotoPickBean;
import com.rain.crow.impl.PhotoSelectCallback;
import com.rain.crow.ui.activity.CameraActivity;
import com.rain.crow.ui.activity.PhotoPickActivity;
import com.rain.crow.utils.Rlog;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/26 11:12
 * @Version : 1.0
 * @Descroption :
 */
public class CameraConfig {

    public static boolean DEFAULT_SHOW_CLIP = false;   //默认开启裁剪图片功能

    public static boolean DEFAULT_START_COMPRESSION = false; //默认开启图片压缩

    public static boolean CLIP_CIRCLE = false;         //裁剪方式 圆形

    public static PhotoPickBean photoPickBean = new PhotoPickBean();

    public static final int CAMERA_REQUEST_CODE = 10001;


    public CameraConfig(Activity activity,Builder builder){
        if (builder.pickBean == null) {
            throw new NullPointerException("builder#pickBean is null");
        } else {
            photoPickBean = builder.pickBean;
        }
        int requestCode = CAMERA_REQUEST_CODE;
        startCamera(activity, requestCode);
    }


    private void startCamera(Activity activity, int requestCode) {

        Intent intent1 = new Intent(activity,CameraActivity.class);
        activity.startActivity(intent1);


    }

    public static class Builder{
        private WeakReference<Activity> activity;
        private PhotoPickBean pickBean;

        public Builder(Activity activity){
            if (activity == null) {
                throw new NullPointerException("context is null");
            }
            this.activity = new WeakReference<>(activity);;
            this.pickBean = new PhotoPickBean();
            pickBean.setClipPhoto(DEFAULT_SHOW_CLIP);
            pickBean.setClipMode(CLIP_CIRCLE);
            pickBean.setStartCompression(DEFAULT_START_COMPRESSION);
        }

        /**
         * 是否开启选择照片后开启裁剪功能
         * 默认关闭
         *
         * @param clipPhoto
         * @return
         */
        public Builder clipPhoto(boolean clipPhoto) {
            pickBean.setClipPhoto(clipPhoto);
            return this;
        }

        /**
         * 设置裁剪方式（圆形，矩形）
         * 默认矩形
         *
         * @param showClipCircle
         * @return
         */
        public Builder clipCircle(boolean showClipCircle) {
            pickBean.setClipMode(showClipCircle);
            return this;
        }

        /**
         * 启动图片压缩功能
         * 默认开启
         *
         * @param compression
         * @return
         */
        public Builder startCompression(boolean compression) {
            pickBean.setStartCompression(compression);
            return this;
        }

        public Builder setCallback(PhotoSelectCallback callback) {
            pickBean.setCallback(callback);
            return this;
        }

        public CameraConfig build(){
            return new CameraConfig(activity.get(),this);
        }
    }
}
