package com.rain.chat.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * @Author : Rain
 * @CreateDate : 2020/10/9 10:27
 * @Version : 1.0
 * @Descroption :
 */
@GlideModule
public class CytxGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSizeBytes()))
                .setDiskCache(new InternalCacheDiskCacheFactory(context,
                        diskCacheFolderName(),
                        diskCacheSizeBytes()))
                .setMemoryCache(new LruResourceCache(memoryCacheSizeBytes()))
                .setDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE));
        LogUtils.e("GlideModule apply options, disk cached path=" +
                context.getExternalCacheDir() + File.pathSeparator + diskCacheFolderName());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }


    /**
     * 内存缓存
     * set the memory cache size, unit is the [Byte].
     */
    private int memoryCacheSizeBytes() {
        return 1024 * 1024 * 20;// 20 MB
    }

    /**
     * 磁盘缓存
     * set the disk cache size, unit is the [Byte].
     */
    private long diskCacheSizeBytes() {
        return 1024 * 1024 * 512;// 512 MB
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
    private long bitmapPoolSizeBytes() {
        return 1024 * 1024 * 30;// 30mb
    }
}
