package com.rain.library_netease_sdk.impl.provider;

import com.rain.library_netease_sdk.user.NimUserInfo;
import com.ycbl.im.uikit.api.model.SimpleCallback;
import com.ycbl.im.uikit.api.model.user.IUserInfoProvider;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/23 11:04
 * @Version : 1.0
 * @Descroption : 获取用户信息
 */
public class NeteaseUserInfoProvider implements IUserInfoProvider<NimUserInfo> {
    @Override
    public NimUserInfo getUserInfo(String account) {
        return null;
    }

    @Override
    public List<NimUserInfo> getUserInfo(List<String> accounts) {
        return null;
    }

    @Override
    public void getUserInfoAsync(String account, SimpleCallback<NimUserInfo> callback) {

    }

    @Override
    public void getUserInfoAsync(List<String> accounts, SimpleCallback<List<NimUserInfo>> callback) {

    }
}
