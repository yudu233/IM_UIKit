package com.ycbl.im.uikit.api.model;

import com.ycbl.im.uikit.api.model.contact.ContactProvider;
import com.ycbl.im.uikit.api.model.team.TeamProvider;
import com.ycbl.im.uikit.api.model.user.IUserInfoProvider;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/23 18:22
 * @Version : 1.0
 * @Descroption :
 */
public abstract class ProviderManager {
    public abstract IUserInfoProvider getUserInfoProvider();

    public abstract TeamProvider getTeamProvider();

    public abstract ContactProvider getContactProvider();
}
