package com.rain.library_netease_sdk.impl.cache;


import android.content.Context;

import com.ycbl.im.uikit.NimUIKitImpl;
import com.ycbl.im.uikit.api.UIKitOptions;
import com.ycbl.im.uikit.common.log.LogUtil;
import com.ycbl.im.uikit.common.thread.NimSingleThreadExecutor;

import java.util.List;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/10/9 11:08
 * @Description : UIKit缓存数据管理类
 */
public class DataCacheManager {

    private static final String TAG = DataCacheManager.class.getSimpleName();

    /**
     * App初始化时向SDK注册数据变更观察者
     */
    public static void observeSDKDataChanged(boolean register) {
        UIKitOptions options = NimUIKitImpl.getUiKitOptions();
        if (options.buildFriendCache) {
            NeteaseFriendCache.getInstance().registerObservers(register);
        }
        if (options.buildNimUserCache) {
            NeteaseUserInfoCache.getInstance().registerObservers(register);
        }
        if (options.buildTeamCache) {
            NeteaseTeamDataCache.getInstance().registerObservers(register);
        }
        if (options.buildRobotInfoCache) {
            NeteaseRobotInfoCache.getInstance().registerObservers(register);
        }
    }

    /**
     * 本地缓存构建(异步)
     */
    public static void buildDataCacheAsync(Context context) {
        NimSingleThreadExecutor.getInstance(context).execute(new Runnable() {
            @Override
            public void run() {
                buildDataCache();
                LogUtil.i(TAG, "build data cache completed");
                NimUIKitImpl.notifyCacheBuildComplete();
            }
        });
    }

    /**
     * 本地缓存构建（同步）
     */
    public static void buildDataCache() {
        // clear
        clearDataCache();

        UIKitOptions options = NimUIKitImpl.getUiKitOptions();
        // build user/friend/team data cache
        if (options.buildFriendCache) {
            NeteaseFriendCache.getInstance().buildCache();
        }
        if (options.buildNimUserCache) {
            NeteaseUserInfoCache.getInstance().buildCache();
        }
        if (options.buildTeamCache) {
            NeteaseTeamDataCache.getInstance().buildCache();
        }
        if (options.buildRobotInfoCache) {
            NeteaseRobotInfoCache.getInstance().buildCache();
        }

        // chat room member cache 在进入聊天室之后构建
    }

    /**
     * 清空缓存（同步）
     */
    public static void clearDataCache() {
        UIKitOptions options = NimUIKitImpl.getUiKitOptions();

        // clear user/friend/team data cache
        if (options.buildFriendCache) {
            NeteaseFriendCache.getInstance().clearCache();
        }
        if (options.buildNimUserCache) {
            NeteaseUserInfoCache.getInstance().clearCache();
        }
        if (options.buildTeamCache) {
            NeteaseTeamDataCache.getInstance().clearCache();
        }
        if (options.buildRobotInfoCache) {
            NeteaseRobotInfoCache.getInstance().clearCache();
        }
    }

    public static void buildRobotCacheIndependent(String roomId) {
        NeteaseRobotInfoCache.getInstance().pullRobotListIndependent(roomId, null);
    }

    /**
     * 输出缓存数据变更日志
     */
    public static void Log(List<String> accounts, String event, String logTag) {
        StringBuilder sb = new StringBuilder();
        sb.append(event);
        sb.append(" : ");
        for (String account : accounts) {
            sb.append(account);
            sb.append(" ");
        }
        sb.append(", total size=" + accounts.size());

        LogUtil.i(logTag, sb.toString());
    }
}
