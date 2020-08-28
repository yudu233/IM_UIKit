package com.rain.inputpanel;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/7 15:39
 * @Version : 1.0
 * @Descroption :
 */
@GlideModule
public class GlideInput extends LibraryGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
