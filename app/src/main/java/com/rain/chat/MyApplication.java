package com.rain.chat;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.rain.chat.base.NimHelper;
import com.rain.crow.PhotoPick;
import com.rain.crow.PhotoPickOptions;
import com.rain.library_netease_sdk.NeteaseCache;
import com.rain.library_netease_sdk.config.NeteaseSDKOptionConfig;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/22 9:42
 * @Version : 1.0
 * @Descroption :
 */
public class MyApplication extends Application {


    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();


        INSTANCE = this;
        PhotoPick.init(getApplicationContext(), getPhotoPickOptions(this));
        NeteaseCache.setContext(this);
        NIMClient.init(this, NimHelper.getLoginInfo(), NeteaseSDKOptionConfig.getSDKOptions(this));

        if (NIMUtil.isMainProcess(this)) {
            NimHelper.init(this);
        }
    }


    public static PhotoPickOptions getPhotoPickOptions(Context context) {
        PhotoPickOptions options = new PhotoPickOptions();
        options.filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        options.imagePath = options.filePath + "im/";
        options.photoPickThemeColor = R.color.colorAccent;
        return options;
    }

    public static MyApplication getContext() {
        return INSTANCE;
    }
}
