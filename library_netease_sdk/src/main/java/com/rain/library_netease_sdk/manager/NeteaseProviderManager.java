package com.rain.library_netease_sdk.manager;

import com.rain.library_netease_sdk.impl.provider.NeteaseContactProvider;
import com.rain.library_netease_sdk.impl.provider.NeteaseTeamProvider;
import com.rain.library_netease_sdk.impl.provider.NeteaseUserInfoProvider;
import com.ycbl.im.uikit.api.model.ProviderManager;
import com.ycbl.im.uikit.api.model.contact.ContactProvider;
import com.ycbl.im.uikit.api.model.team.TeamProvider;
import com.ycbl.im.uikit.api.model.user.IUserInfoProvider;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/23 18:25
 * @Version : 1.0
 * @Descroption : 网易云信 -> 内容提供者管理
 */
public class NeteaseProviderManager extends ProviderManager {

    public NeteaseProviderManager() {
    }

    @Override
    public IUserInfoProvider getUserInfoProvider() {
        return new NeteaseUserInfoProvider();
    }

    @Override
    public TeamProvider getTeamProvider() {
        return new NeteaseTeamProvider();
    }

    @Override
    public ContactProvider getContactProvider() {
        return new NeteaseContactProvider();
    }
}
