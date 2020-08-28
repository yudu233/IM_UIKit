package com.rain.chat.glide;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.InputStream;

import cc.shinichi.library.glide.progress.ProgressManager;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/28 17:37
 * @Version : 1.0
 * @Descroption :
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    private static final String TAG = "MyAppGlideModule";

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
                                   @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

        // 替换底层网络框架为okhttp3，这步很重要！
        // 如果您的app中已经存在了自定义的GlideModule，您只需要把这一行代码，添加到对应的重载方法中即可。
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSizeBytes()))
                .setDiskCache(new InternalCacheDiskCacheFactory(context,
                        diskCacheFolderName(),
                        diskCacheSizeBytes()))
                .setMemoryCache(new LruResourceCache(memoryCacheSizeBytes()))
                .setDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL));
        Log.i(TAG, "GlideModule apply options, disk cached path=" + context.getExternalCacheDir() +
                File.pathSeparator + diskCacheFolderName());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }


    /**
     * 内存缓存
     * set the memory cache size, unit is the [Byte].
     */
    private Integer memoryCacheSizeBytes() {
        return 1024 * 1024 * 20; // 20 MB
    }

    /**
     * 磁盘缓存
     * set the disk cache size, unit is the [Byte].
     */
    private Long diskCacheSizeBytes() {
        return 1024 * 1024 * 512L; // 512 MB
    }

    /**
     * 缓存文件夹名称
     * set the disk cache folder's name.
     */
    private String diskCacheFolderName() {
        return "glide";
    }

    /**
     * Bitmap 池
     * set the bitmap pool size
     */
    private Long bitmapPoolSizeBytes() {
        return 1024 * 1024 * 30L; // 30mb
    }
}