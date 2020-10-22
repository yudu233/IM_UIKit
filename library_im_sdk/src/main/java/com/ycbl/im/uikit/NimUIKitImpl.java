package com.ycbl.im.uikit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ycbl.im.uikit.api.UIKitOptions;
import com.ycbl.im.uikit.api.model.ObservableManager;
import com.ycbl.im.uikit.api.model.ProviderManager;
import com.ycbl.im.uikit.api.model.chatroom.ChatRoomMemberChangedObservable;
import com.ycbl.im.uikit.api.model.chatroom.ChatRoomProvider;
import com.ycbl.im.uikit.api.model.contact.ContactChangedObservable;
import com.ycbl.im.uikit.api.model.location.LocationProvider;
import com.ycbl.im.uikit.api.model.main.LoginSyncDataStatusObserver;
import com.ycbl.im.uikit.api.model.main.OnlineStateChangeObservable;
import com.ycbl.im.uikit.api.model.main.OnlineStateContentProvider;
import com.ycbl.im.uikit.api.model.main.SystemMessageObservable;
import com.ycbl.im.uikit.api.model.team.TeamChangedObservable;
import com.ycbl.im.uikit.api.model.team.TeamProvider;
import com.ycbl.im.uikit.api.model.user.IUserInfoProvider;
import com.ycbl.im.uikit.api.model.user.UserInfoObservable;
import com.ycbl.im.uikit.common.log.LogUtil;
import com.ycbl.im.uikit.common.storage.ExternalStorage;
import com.ycbl.im.uikit.common.storage.StorageType;
import com.ycbl.im.uikit.common.storage.StorageUtil;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:42
 * @Version : 2.0
 * @Descroption : UIKit能力实现类
 */
public class NimUIKitImpl {

    //context
    private static Context mContext;

    // 自己的用户帐号
    private static String account;

    //观察者管理器
    private static ObservableManager observableManager;

    //内容提供者管理器
    private static ProviderManager providerManager;

    // IMSDKOptions
    private static UIKitOptions uiKitOptions;

    // 地理位置信息提供者
    private static LocationProvider locationProvider;

    // 用户信息提供者
    private static IUserInfoProvider userInfoProvider;

    // 通讯录信息提供者


    // 在线状态展示内容
    private static OnlineStateContentProvider onlineStateContentProvider;

    // 在线状态变化监听
    private static OnlineStateChangeObservable onlineStateChangeObservable;

    // 用户信息变更监听
    private static UserInfoObservable userInfoObservable;

    //好友关系变更监听

    //系统消息监听
    private static SystemMessageObservable systemMessageObservable;

    //群、群成员信息提供者
    private static TeamProvider teamProvider;

    //群、群成员变化监听
    private static TeamChangedObservable teamChangedObservable;

    // 聊天室提供者
    private static ChatRoomProvider chatRoomProvider;

    // 聊天室成员变更通知
    private static ChatRoomMemberChangedObservable chatRoomMemberChangedObservable;

    // 缓存构建成功
    private static boolean buildCacheComplete = false;

    //初始化状态监听
//    private static UIKitInitStateListener initStateListener;


    /*
     * ****************************** 初始化 ******************************
     */

    public static void init(Context context, UIKitOptions options, IUserInfoProvider userInfoProvider,
                            ObservableManager observableManager, ProviderManager providerManager) {
        NimUIKitImpl.mContext = context;
        NimUIKitImpl.uiKitOptions = options;
        NimUIKitImpl.observableManager = observableManager;
        NimUIKitImpl.providerManager = providerManager;
        initUserInfoProvider(userInfoProvider);

        //存储目录创建
        ExternalStorage.getInstance().init(context, options.appCacheDir);

        //表情贴图初始化
//        if (options.loadSticker) {
//            StickerManager.getInstance().init();
//        }

        // init log
        String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
        LogUtil.init(path, Log.DEBUG);

        //非独立聊天室（正常IM业务）
        if (!options.independentChatRoom) {
            initUserInfoProvider(userInfoProvider);
            // init data cache
            // 监听登录同步数据完成通知
            LoginSyncDataStatusObserver.getInstance().registerLoginSyncDataStatus(true);
        }
    }


    public static void notifyCacheBuildComplete() {
        buildCacheComplete = true;
    }

    public static boolean isInitComplete() {
        return !uiKitOptions.initAsync || TextUtils.isEmpty(account) || buildCacheComplete;
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

    public static UIKitOptions getUiKitOptions() {
        return uiKitOptions;
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
            if (uiKitOptions.imsdkType == IMSDKType.NETEASE_IM) {
                teamProvider = providerManager.getTeamProvider();
            }
        }
        return teamProvider;
    }

    /**
     * 获取用户信息变更观察者
     *
     * @return
     */
    public static UserInfoObservable getUserInfoObservable() {
        if (userInfoObservable == null) {
            userInfoObservable = observableManager.getUserInfoObservable(mContext);
        }
        return userInfoObservable;
    }

    /**
     * 获取系统消息观察者
     *
     * @return
     */
    public static SystemMessageObservable getSystemMessageObservable() {
        return observableManager.getSystemMessageObservable(mContext);
    }

    /**
     * 获取聊天室成员变更观察者
     *
     * @return
     */
    public static ChatRoomMemberChangedObservable getChatRoomMemberChangedObservable() {

        return observableManager.getChatRoomMemberChangedObservable(mContext);
    }

    /**
     * 获取群、群成员变更通知观察者
     *
     * @return
     */
    public static TeamChangedObservable getTeamChangedObservable() {
        return observableManager.getTeamChangedObservable(mContext);
    }

    public static ContactChangedObservable getContactChangedObservable() {
        return observableManager.getContactChangedObservable(mContext);
    }

    /*
     * ****************************** basic ******************************
     */
    public static Context getContext() {
        return mContext;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        NimUIKitImpl.account = account;
    }
}
