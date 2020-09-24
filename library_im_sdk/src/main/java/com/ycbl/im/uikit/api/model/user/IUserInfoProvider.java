package com.ycbl.im.uikit.api.model.user;

import com.ycbl.im.uikit.api.model.SimpleCallback;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/23 10:55
 * @Version : 1.0
 * @Descroption : 用户信息提供者接口
 */
public interface IUserInfoProvider<T> {

    /**
     * 同步获取userInfo
     *
     * @param account 账号
     * @return userInfo
     */
    T getUserInfo(String account);

    /**
     * 同步获取userInfo列表
     *
     * @param accounts 账号
     * @return userInfo
     */
    List<T> getUserInfo(List<String> accounts);

    /**
     * 异步获取userInfo
     *
     * @param account  账号id
     * @param callback 回调
     */
    void getUserInfoAsync(String account, SimpleCallback<T> callback);

    /**
     * 异步获取userInfo列表
     *
     * @param accounts 账号id 集合
     * @param callback 回调
     */
    void getUserInfoAsync(List<String> accounts, final SimpleCallback<List<T>> callback);
}
