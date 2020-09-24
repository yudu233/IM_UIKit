package com.rain.library_netease_sdk.user;

import com.netease.nimlib.sdk.uinfo.model.UserInfo;

/**
 * @author:duyu
 * @org :   www.yudu233.com
 * @email : yudu233@gmail.com
 * @date :  2019/6/13 17:15
 * @filename : NimUserInfo
 * @describe : 定制化UserInfo
 */
public interface NimUserInfo extends UserInfo {
    /**
     * 返回用户帐号
     *
     * @return 用户帐号
     */
    @Override
    String getAccount();

    /**
     * 返回用户名
     *
     * @return 用户名
     */
    @Override
    String getName();

    /**
     * 返回用户头像链接地址
     *
     * @return 头像URL、URI
     */
    @Override
    String getAvatar();


    /*
     * ****************************** 定制化 ******************************
     */

    /**
     * 返回是否是铁粉
     *
     * @return true:是铁粉 false：不是铁粉
     */
    boolean isFans();

    /**
     * 是否是国外用户
     *
     * @return true:是国外用户 false：不是国外用户
     */
    boolean isForeignUser();
}
