package com.rain.library_netease_sdk.impl.provider;

import android.text.TextUtils;

import com.netease.nimlib.sdk.friend.model.Friend;
import com.rain.library_netease_sdk.impl.cache.NeteaseFriendCache;
import com.ycbl.im.uikit.api.model.contact.ContactProvider;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/10/23 10:02
 * @Version : 1.0
 * @Descroption : 好友关系资料相关提供者
 */

// TODO: 2020/10/23 最后考虑如何绑定数据库
public class NeteaseContactProvider implements ContactProvider {
    @Override
    public List<String> getUserInfoOfMyFriends() {
        return NeteaseFriendCache.getInstance().getMyFriendAccounts();
    }

    @Override
    public int getMyFriendsCount() {
        return NeteaseFriendCache.getInstance().getMyFriendCounts();
    }

    @Override
    public String getAlias(String account) {
        Friend friend = NeteaseFriendCache.getInstance().getFriendByAccount(account);
        if (friend != null && !TextUtils.isEmpty(friend.getAlias())) {
            return friend.getAlias();
        }
        return null;
    }

    @Override
    public boolean isMyFriend(String account) {
        return NeteaseFriendCache.getInstance().isMyFriend(account);
    }
}
