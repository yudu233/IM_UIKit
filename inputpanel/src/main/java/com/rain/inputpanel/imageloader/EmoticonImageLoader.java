package com.rain.inputpanel.imageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * @Author : Rain
 * @CreateDate : 2020/9/25 11:18
 * @Version : 1.0
 * @Descroption :
 */
public class EmoticonImageLoader extends ImageLoader {


    public EmoticonImageLoader(Context context) {
        super(context);
    }

    @Override
    protected void displayImageFromFile(String imageUri, ImageView imageView) {
        String filePath = Scheme.FILE.crop(imageUri);
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(filePath)
                .into(imageView);
    }

    @Override
    protected void displayImageFromAssets(String imageUri, ImageView imageView) {
        String uri = Scheme.cropScheme(imageUri);
        ImageBase.Scheme.ofUri(imageUri).crop(imageUri);
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(Uri.parse("file:///android_asset/" + uri))
                .into(imageView);
    }
}
