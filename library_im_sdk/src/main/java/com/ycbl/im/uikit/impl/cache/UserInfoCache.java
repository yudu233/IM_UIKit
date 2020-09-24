package com.ycbl.im.uikit.impl.cache;

import android.text.TextUtils;

import com.netease.nimlib.sdk.RequestCallback;
import com.ycbl.im.uikit.common.log.LogUtil;
import com.ycbl.im.uikit.common.log.UIKitLogTag;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/23 14:17
 * @Version : 1.0
 * @Descroption : 用户资料数据缓存
 */
public abstract class UserInfoCache<T> {

    private Map<String, T> account2UserMap = new ConcurrentHashMap<>();

    private Map<String, List<RequestCallback<T>>> requestUserInfoMap = new ConcurrentHashMap<>(); // 重复请求处理

    /**
     * ******************************* 业务接口（获取缓存的用户信息） *********************************
     */

    /**
     * 获取用户信息
     *
     * @param account
     * @return
     */
    public T getUserInfo(String account) {
        if (TextUtils.isEmpty(account) || account2UserMap == null) {
            LogUtil.e(UIKitLogTag.USER_CACHE, "getUserInfo null, account=" + account + ", account2UserMap=" + account2UserMap);
            return null;
        }

        return account2UserMap.get(account);
    }

    /**
     * 该用户是否存在
     *
     * @param account
     * @return
     */
    private boolean hasUser(String account) {
        if (TextUtils.isEmpty(account) || account2UserMap == null) {
            LogUtil.e(UIKitLogTag.USER_CACHE, "hasUser null, account=" + account + ", account2UserMap=" + account2UserMap);
            return false;
        }

        return account2UserMap.containsKey(account);
    }


    private void clearUserCache() {
        account2UserMap.clear();
    }


    /**
     * 构建缓存
     */
    public abstract void buildCache();

    /**
     * 清除缓存
     */
    public abstract void clearCache();

    /**
     * 注册用户资料变更观察者
     */
    public abstract void registerObservers(boolean register);

}
