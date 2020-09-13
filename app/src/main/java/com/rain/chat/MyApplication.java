package com.rain.chat;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.rain.chat.base.NimHelper;
import com.rain.chat.config.Preferences;
import com.rain.chat.session.viewholder.MsgViewHolderLocation;
import com.rain.chat.session.viewholder.MsgViewHolderTranslateAudio;
import com.rain.crow.PhotoPick;
import com.rain.crow.PhotoPickOptions;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.message.MsgViewHolderFactory;

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
        PhotoPick.init(getApplicationContext(), getPhotoPickOptions(this));
        INSTANCE = this;
        //云信初始化
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), null);
        
        MsgViewHolderFactory.register(MessageType.audio, MsgViewHolderTranslateAudio.class);
        MsgViewHolderFactory.register(MessageType.location, MsgViewHolderLocation.class);

        NimHelper.initUIKit(this);
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            return new LoginInfo(account, token);
        } else {
            return null;
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
