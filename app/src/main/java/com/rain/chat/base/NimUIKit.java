package com.rain.chat.base;

import android.content.Context;

import com.rain.library_netease_sdk.impl.provider.NeteaseUserInfoProvider;
import com.rain.library_netease_sdk.manager.NeteaseObservableManager;
import com.rain.library_netease_sdk.manager.NeteaseProviderManager;
import com.ycbl.im.uikit.IMSDKOptions;
import com.ycbl.im.uikit.NimUIKitImpl;
import com.ycbl.im.uikit.api.model.ObservableManager;
import com.ycbl.im.uikit.api.model.ProviderManager;
import com.ycbl.im.uikit.api.model.chatroom.ChatRoomMemberChangedObservable;
import com.ycbl.im.uikit.api.model.location.LocationProvider;
import com.ycbl.im.uikit.api.model.main.SystemMessageObservable;
import com.ycbl.im.uikit.api.model.team.TeamChangedObservable;
import com.ycbl.im.uikit.api.model.user.IUserInfoProvider;
import com.ycbl.im.uikit.api.model.user.UserInfoObservable;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:40
 * @Version : 2.0
 * @Descroption : 云信UI组件接口/定制化入口
 */
public class NimUIKit {


    public static void init(Context context) {
        NimUIKitImpl.init(context,new IMSDKOptions(),new NeteaseUserInfoProvider(),
                new NeteaseObservableManager(),new NeteaseProviderManager());
    }


    public static void init(Context context, IMSDKOptions sdkOptions, IUserInfoProvider userInfoProvider,
                            ObservableManager observableManager, ProviderManager providerManager) {
        NimUIKitImpl.init(context, sdkOptions, userInfoProvider, observableManager,providerManager);

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
    public static IUserInfoProvider getUserInfoProvider() {
        return NimUIKitImpl.getUserInfoProvider();
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

}

