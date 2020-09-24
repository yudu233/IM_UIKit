package com.ycbl.im.uikit;

import android.content.Context;

import com.ycbl.im.uikit.api.model.ObservableManager;
import com.ycbl.im.uikit.api.model.ProviderManager;
import com.ycbl.im.uikit.api.model.chatroom.ChatRoomMemberChangedObservable;
import com.ycbl.im.uikit.api.model.location.LocationProvider;
import com.ycbl.im.uikit.api.model.main.SystemMessageObservable;
import com.ycbl.im.uikit.api.model.team.TeamChangedObservable;
import com.ycbl.im.uikit.api.model.team.TeamProvider;
import com.ycbl.im.uikit.api.model.user.IUserInfoProvider;
import com.ycbl.im.uikit.api.model.user.UserInfoObservable;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:42
 * @Version : 2.0
 * @Descroption : UIKit能力实现类
 */
public class NimUIKitImpl {

    //context
    private static Context mContext;

    private static ObservableManager observableManager;

    private static ProviderManager providerManager;

    // IMSDKOptions
    private static IMSDKOptions imsdkOptions;

    // 地理位置信息提供者
    private static LocationProvider locationProvider;

    // 用户信息提供者
    private static IUserInfoProvider<?> userInfoProvider;


    // userInfo 变更监听
    private static UserInfoObservable userInfoObservable;

    //系统消息监听
    private static SystemMessageObservable systemMessageObservable;

    //群、群成员信息提供者
    private static TeamProvider teamProvider;

    //群、群成员变化监听
    private static TeamChangedObservable teamChangedObservable;


    /**
     * 获取上下文
     *
     * @return 必须初始化后才有值
     */
    public static Context getContext() {
        return mContext;
    }

    /*
     * ****************************** 初始化 ******************************
     */
    public static void init(Context context) {
        init(context, new IMSDKOptions());
    }

    public static void init(Context context, IMSDKOptions options) {
        init(context, options);
    }

    public static void init(Context context, IMSDKOptions options, IUserInfoProvider userInfoProvider) {
        init(context, options, userInfoProvider);
    }

    public static void init(Context context, IMSDKOptions options, IUserInfoProvider userInfoProvider,
                            ObservableManager observableManager, ProviderManager providerManager) {
        NimUIKitImpl.mContext = context;
        NimUIKitImpl.imsdkOptions = options;
        NimUIKitImpl.observableManager = observableManager;
        NimUIKitImpl.providerManager = providerManager;
        initUserInfoProvider(userInfoProvider);
    }

    // 初始化用户信息提供者
    private static void initUserInfoProvider(IUserInfoProvider userInfoProvider) {
        if (userInfoProvider == null) {
            throw new IllegalStateException("userInfoProvider is must not be null");
        }
        NimUIKitImpl.userInfoProvider = userInfoProvider;
    }

    /*
     * ****************************** 定制化 ******************************
     */

    public static void setLocationProvider(LocationProvider locationProvider) {
        NimUIKitImpl.locationProvider = locationProvider;
    }

    public static LocationProvider getLocationProvider() {
        return locationProvider;
    }

    public static IUserInfoProvider getUserInfoProvider() {
        return userInfoProvider;
    }

    public static void setTeamProvider(TeamProvider provider) {
        teamProvider = provider;
    }

    public static TeamProvider getTeamProvider() {
        if (teamProvider == null) {
            if (imsdkOptions.imsdkType == IMSDKType.NETEASE_IM) {
                teamProvider = providerManager.getTeamProvider();
            }
        }
        return teamProvider;
    }

    /**
     * 获取用户信息变更观察者
     * 根据{@link IMSDKType} 设置观察者来源
     *
     * @return
     */
    public static UserInfoObservable getUserInfoObservable() {
        if (imsdkOptions.imsdkType == IMSDKType.NETEASE_IM) {
            return observableManager.getUserInfoObservable(mContext);
        }
        return null;
    }

    /**
     * 获取系统消息观察者
     * 根据{@link IMSDKType} 设置观察者来源
     *
     * @return
     */
    public static SystemMessageObservable getSystemMessageObservable() {
        if (systemMessageObservable == null) {
            if (imsdkOptions.imsdkType == IMSDKType.NETEASE_IM) {
                systemMessageObservable = observableManager.getSystemMessageObservable(mContext);
            }
        }
        return systemMessageObservable;
    }

    /**
     * 获取聊天室成员变更观察者
     * 根据{@link IMSDKType} 设置观察者来源
     *
     * @return
     */
    public static ChatRoomMemberChangedObservable getChatRoomMemberChangedObservable() {
        if (imsdkOptions.imsdkType == IMSDKType.NETEASE_IM) {
            return observableManager.getChatRoomMemberChangedObservable(mContext);
        }
        return null;
    }

    /**
     * 获取群、群成员变更通知观察者
     * 根据{@link IMSDKType} 设置观察者来源
     *
     * @return
     */
    public static TeamChangedObservable getTeamChangedObservable() {
        if (imsdkOptions.imsdkType == IMSDKType.NETEASE_IM) {
            return observableManager.getTeamChangedObservable(mContext);
        }
        return null;
    }
}
