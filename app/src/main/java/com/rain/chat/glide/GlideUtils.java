package com.rain.chat.glide;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.rain.chat.R;


/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/6/29 14:40
 * @Description :
 */
public class GlideUtils {

    /**
     * 加载头像
     *
     * @param context   上下文
     * @param avatarUrl 图片链接
     * @param radius    圆角
     * @param imageView 控件
     */
    public static void loadAvatar(Context context, String avatarUrl, int radius, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(avatarUrl)
                .error(R.drawable.im_avatar_default)
                .placeholder(R.drawable.im_avatar_default)
                .centerCrop()
                .dontAnimate()
                .transform(new RoundedCorners(radius))
                .into(imageView);
    }

    /**
     * 加载头像
     *
     * @param context   上下文
     * @param avatarUrl 图片链接
     * @param imageView 控件
     */
    public static void loadAvatar(Context context, String avatarUrl, ImageView imageView) {
        loadAvatar(context, avatarUrl, 10, imageView);
    }

    /**
     * 圆形图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 控件
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .dontAnimate()
                .circleCrop()
                .into(imageView);
    }

    /**
     * 加载正常图片（带宽高）
     *
     * @param context   上下文
     * @param url       图片链接
     * @param width     宽
     * @param height    高
     * @param imageView 控件
     * @param extension 文件后缀名
     */
    public static void loadImage(Context context, String url, int width, int height,
                                 ImageView imageView, String extension) {
        boolean isGif = isGif(extension);
        if (isGif) {
            Glide.with(context).asGif()
                    .override(width, height)
                    .centerCrop()
                    .load(url)
                    .into(imageView);
        } else {
            Glide.with(context).asBitmap()
                    .override(width, height)
                    .centerCrop()
                    .load(url)
                    .into(imageView);
        }
    }

    public static void loadImage(Context context, String url, AppCompatImageView imageView){
        Log.e("Rain",url);
        Glide.with(context).asBitmap()
                .centerCrop()
                .load(url)
                .into(imageView);
    }

    /**
     * 是否是Gif图
     *
     * @param extension
     * @return
     */
    public static boolean isGif(String extension) {
        return !TextUtils.isEmpty(extension) && extension.toLowerCase().equals("gif");
    }
}
