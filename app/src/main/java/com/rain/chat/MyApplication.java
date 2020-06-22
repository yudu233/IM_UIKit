package com.rain.chat;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.provider.MediaStore;

import com.rain.crow.PhotoPick;
import com.rain.crow.PhotoPickOptions;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/22 9:42
 * @Version : 1.0
 * @Descroption :
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        PhotoPick.init(getApplicationContext(), getPhotoPickOptions(this));

    }

    public static PhotoPickOptions getPhotoPickOptions(Context context) {
        PhotoPickOptions options = new PhotoPickOptions();
        options.filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        options.imagePath = options.filePath + "im/";
        options.photoPickAuthority = context.getString(R.string.file_provider_authorities);
        options.photoPickThemeColor = R.color.colorAccent;
        return options;
    }
}
