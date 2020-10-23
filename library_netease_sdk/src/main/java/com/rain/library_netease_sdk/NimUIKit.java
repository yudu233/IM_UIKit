package com.rain.library_netease_sdk;

import android.content.Context;

import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.rain.library_netease_sdk.config.NeteaseUIKitOptions;
import com.rain.library_netease_sdk.user.NimUserInfo;
import com.ycbl.im.uikit.NimUIKitImpl;
import com.ycbl.im.uikit.api.UIKitOptions;
import com.ycbl.im.uikit.api.model.ObservableManager;
import com.ycbl.im.uikit.api.model.ProviderManager;
import com.ycbl.im.uikit.api.model.chatroom.ChatRoomMemberChangedObservable;
import com.ycbl.im.uikit.api.model.contact.ContactProvider;
import com.ycbl.im.uikit.api.model.location.LocationProvider;
import com.ycbl.im.uikit.api.model.main.SystemMessageObservable;
import com.ycbl.im.uikit.api.model.team.TeamChangedObservable;
import com.ycbl.im.uikit.api.model.team.TeamProvider;
import com.ycbl.im.uikit.api.model.user.IUserInfoProvider;
import com.ycbl.im.uikit.api.model.user.UserInfoObservable;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:40
 * @Version : 2.0
 * @Descroption : 云信UI组件接口/定制化入口
 */
public class NimUIKit {


    public static void init(Context context, UIKitOptions sdkOptions, IUserInfoProvider userInfoProvider,
                            ObservableManager observableManager, ProviderManager providerManager) {
        NimUIKitImpl.init(context, sdkOptions, userInfoProvider, observableManager, providerManager);

    }

    /**
     * 获取上下文
     *
     * @return 必须初始化后才有值
     */
    public static Context getContext() {
        return NimUIKitImpl.getContext();
    }


    /**
     * 设置当前登录用户的帐号
     *
     * @param account 帐号
     */
    public static void setAccount(String account) {
        NimUIKitImpl.setAccount(account);
    }

    /**
     * 获取当前登录的账号
     *
     * @return 必须登录成功后才有值
     */
    public static String getAccount() {
        return NimUIKitImpl.getAccount();
    }

    public static NeteaseUIKitOptions getUIKitOptions() {
        return (NeteaseUIKitOptions) NimUIKitImpl.getUiKitOptions();
    }

    /**
     * SDK提供状态栏提醒的配置
     */
    private static StatusBarNotificationConfig notificationConfig;

    public static void setNotificationConfig(StatusBarNotificationConfig notificationConfig) {
        NimUIKit.notificationConfig = notificationConfig;
    }

    /**
     * 设置位置信息提供者
     *
     * @param locationProvider 位置信息提供者
     */
    public static void setLocationProvider(LocationProvider locationProvider) {
        NimUIKitImpl.setLocationProvider(locationProvider);
    }

    /**
     * 获取 “用户资料” 提供者
     *
     * @return 必须在初始化后获取
     */
    public static IUserInfoProvider<NimUserInfo> getUserInfoProvider() {
        return NimUIKitImpl.getUserInfoProvider();
    }

    /**
     * 获取通讯录资料提供者
     *
     * @return 必须在初始化后获取
     */
    public static ContactProvider getContactProvider() {
        return NimUIKitImpl.getContactProvider();
    }

    /**
     * 获取 “用户资料” 变更监听管理者
     * UIKit 与 app 之间 userInfo 数据更新通知接口
     *
     * @return UserInfoObservable
     */
    public static UserInfoObservable getUserInfoObservable() {
        return NimUIKitImpl.getUserInfoObservable();
    }

    /**
     * 获取系统消息通知观察者
     *
     * @return
     */
    public static SystemMessageObservable getSystemMessageObservable() {
        return NimUIKitImpl.getSystemMessageObservable();
    }

    /**
     * 获取聊天室成员变更观察者
     *
     * @return
     */
    public static ChatRoomMemberChangedObservable getChatRoomMemberChangedObservable() {
        return NimUIKitImpl.getChatRoomMemberChangedObservable();
    }

    /**
     * 获取群、群成员变更通知观察者
     *
     * @return
     */
    public static TeamChangedObservable getTeamChangedObservable() {
        return NimUIKitImpl.getTeamChangedObservable();
    }

    public static TeamProvider<Team, TeamMember> getTeamProvider() {
        return NimUIKitImpl.getTeamProvider();
    }

}

