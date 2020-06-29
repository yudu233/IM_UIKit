package com.rain.chat.glide;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
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
        GlideApp.with(context)
                .asBitmap()
                .load(avatarUrl)
                .error(R.drawable.nim_avatar_default)
                .placeholder(R.drawable.nim_avatar_default)
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
        GlideApp.with(context)
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
     */
    public static void loadImage(Context context, String url, int width, int height, ImageView imageView) {
        ImageUtils.ImageType imageType = ImageUtils.getImageType(url);
        boolean isGif = imageType.getValue().equals("gif");
        if (isGif) {
            GlideApp.with(context).asGif()
                    .override(width, height)
                    .centerCrop()
                    .load(url)
                    .into(imageView);
        } else {
            GlideApp.with(context).asBitmap()
                    .override(width, height)
                    .centerCrop()
                    .load(url)
                    .into(imageView);
        }
    }
}
