package com.rain.chat.session.module;

import androidx.fragment.app.FragmentActivity;

import com.rain.messagelist.message.SessionType;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: Container
 * @CreateDate: 2020/6/29 22:27
 * @Describe:
 */
public class Container {
    public final FragmentActivity activity;
    public final String account;
    public final SessionType sessionType;
    public final ModuleProxy proxy;

    public Container(FragmentActivity activity, String account, SessionType sessionType, ModuleProxy proxy) {
        this.activity = activity;
        this.account = account;
        this.sessionType = sessionType;
        this.proxy = proxy;
    }
}
