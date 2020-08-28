package com.rain.messagelist;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/6 22:46
 * @Version : 1.0
 * @Descroption :
 */
@GlideModule
public class MyAppGlideModule extends LibraryGlideModule {

    private static final String TAG = "MyAppGlideModule";

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        /**
         * 替换底层网络框架为okhttp
         * 原框架{@link GlideConfiguration#registerComponents(Context, Glide, Registry)}
         * 使用了整个应用的okhttp配置，故替换
         */
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
