package com.rain.library_netease_sdk.impl.provider;

import android.content.Context;
import android.graphics.Bitmap;

import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.rain.library_netease_sdk.NimUIKit;
import com.ycbl.im.uikit.NimUIKitImpl;

/**
 * @Author : Rain
 * @CreateDate : 2020/10/9 16:30
 * @Version : 1.0
 * @Descroption : 初始化sdk 需要的用户信息提供者，现主要用于内置通知提醒获取昵称和头像
 * 注意不要与 IUserInfoProvider 混淆，后者是 UIKit 与 demo 之间的数据共享接口
 */
public class NimUserInfoProvider implements UserInfoProvider {

    private Context context;

    public NimUserInfoProvider(Context context) {
        this.context = context;
    }

    @Override
    public UserInfo getUserInfo(String account) {
     return NimUIKit.getUserInfoProvider().getUserInfo(account);
    }

    @Override
    public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
        return null;
    }

    @Override
    public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
        return null;
    }
}
