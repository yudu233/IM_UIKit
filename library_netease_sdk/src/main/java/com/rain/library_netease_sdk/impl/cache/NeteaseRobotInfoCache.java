package com.rain.library_netease_sdk.impl.cache;

import android.text.TextUtils;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.robot.RobotService;
import com.netease.nimlib.sdk.robot.RobotServiceObserve;
import com.netease.nimlib.sdk.robot.model.NimRobotInfo;
import com.netease.nimlib.sdk.robot.model.RobotChangedNotify;
import com.ycbl.im.uikit.api.model.SimpleCallback;
import com.ycbl.im.uikit.common.log.LogUtil;
import com.ycbl.im.uikit.common.log.UIKitLogTag;
import com.ycbl.im.uikit.impl.cache.BaseCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/24 10:21
 * @Version : 1.0
 * @Descroption : 网易云信 -> 机器人缓存
 */
public class NeteaseRobotInfoCache implements BaseCache {


    public static NeteaseRobotInfoCache getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * 数据
     */

    private Map<String, NimRobotInfo> robotMap = new ConcurrentHashMap<>();

    @Override
    public void buildCache() {
        // 获取所有有效的机器人
        List<NimRobotInfo> robots = NIMClient.getService(RobotService.class).getAllRobots();
        if (robots != null && robots.size() > 0) {
            for (NimRobotInfo r : robots) {
                robotMap.put(r.getAccount(), r);
            }
        }
        LogUtil.i(UIKitLogTag.ROBOT_CACHE, "build RobotInfoCache completed, robots count = " + robotMap.size());
    }

    @Override
    public void clearCache() {
        robotMap.clear();
        lastTime = 0;
    }

    /**
     * ****************************** 聊天室独立模式，拉取机器人信息逻辑 ******************************
     */

    /**
     * 拉取机器人信息最短间隔 5min
     */
    private static final long MIN_PULL_ROBOT_INTERNAL = 5 * 60 * 1000;

    private static long lastTime = 0L;

    /**
     * 独立模式进入聊天室之后调用
     * <p>
     * 最短时间间隔 MIN_PULL_ROBOT_INTERNAL
     *
     * @param roomId
     */
    public void pullRobotListIndependent(String roomId, final SimpleCallback<List<NimRobotInfo>> callback) {
        if (System.currentTimeMillis() - lastTime < MIN_PULL_ROBOT_INTERNAL) {
            if (callback != null) {
                callback.onResult(true, getAllRobotAccounts(), 200);
            }
            return;
        }

        NIMClient.getService(ChatRoomService.class).pullAllRobots(roomId).setCallback(new RequestCallbackWrapper<List<NimRobotInfo>>() {
            @Override
            public void onResult(int code, List<NimRobotInfo> result, Throwable exception) {
                if (code == 200 && result != null) {
                    lastTime = System.currentTimeMillis();
                    robotMap.clear();
                    LogUtil.i(UIKitLogTag.ROBOT_CACHE, "pull RobotList Independent completed, robots count = " + robotMap.size());
                    for (NimRobotInfo r : result) {
                        robotMap.put(r.getAccount(), r);
                    }
                }
                if (callback != null) {
                    callback.onResult(code == 200, result, code);
                }
            }
        });
    }

    public void fetchRobotList(final SimpleCallback<List<NimRobotInfo>> callback) {
        if (System.currentTimeMillis() - lastTime < MIN_PULL_ROBOT_INTERNAL) {
            if (callback != null) {
                callback.onResult(true, getAllRobotAccounts(), 200);
            }
            return;
        }
        NIMClient.getService(RobotService.class).pullAllRobots().setCallback(new RequestCallbackWrapper<List<NimRobotInfo>>() {
            @Override
            public void onResult(int code, List<NimRobotInfo> result, Throwable exception) {
                if (code == 200 && result != null) {
                    lastTime = System.currentTimeMillis();
                    robotMap.clear();
                    LogUtil.i(UIKitLogTag.ROBOT_CACHE, "pull robot list completed, robots count = " + robotMap.size());
                    for (NimRobotInfo r : result) {
                        robotMap.put(r.getAccount(), r);
                    }
                }
                if (callback != null) {
                    callback.onResult(code == 200, result, code);
                }
            }
        });
    }

    /**
     * ****************************** 机器人信息查询接口 ******************************
     */

    public List<NimRobotInfo> getAllRobotAccounts() {
        return new ArrayList<>(robotMap.values());
    }

    public NimRobotInfo getRobotByAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            return null;
        }

        return robotMap.get(account);
    }

    /**
     * ****************************** 缓存机器人变更监听&通知 ******************************
     */

    /**
     * 缓存监听SDK
     */
    @Override
    public void registerObservers(boolean register) {
        NIMClient.getService(RobotServiceObserve.class).observeRobotChangedNotify(robotChangedNotifyObserver, register);
    }

    /**
     * 监听机器人变化
     */
    private Observer<RobotChangedNotify> robotChangedNotifyObserver = new Observer<RobotChangedNotify>() {
        @Override
        public void onEvent(RobotChangedNotify robotChangedNotify) {
            List<NimRobotInfo> addedOrUpdatedRobots = robotChangedNotify.getAddedOrUpdatedRobots();
            List<String> addedOrUpdateRobotAccounts = new ArrayList<>(addedOrUpdatedRobots.size());
            List<String> deletedRobotAccounts = robotChangedNotify.getDeletedRobots();

            String account;
            for (NimRobotInfo f : addedOrUpdatedRobots) {
                account = f.getAccount();
                robotMap.put(account, f);
                addedOrUpdateRobotAccounts.add(account);
            }

            // 通知机器人变更
            if (!addedOrUpdateRobotAccounts.isEmpty()) {
                // log
//                DataCacheManager.Log(addedOrUpdateRobotAccounts, "on add robot", UIKitLogTag.ROBOT_CACHE);
            }

            // 处理被删除的机器人
            if (!deletedRobotAccounts.isEmpty()) {
                // update cache
                for (String a : deletedRobotAccounts) {
                    robotMap.remove(a);
                }

                // log
//                DataCacheManager.Log(deletedRobotAccounts, "on delete robots", UIKitLogTag.FRIEND_CACHE);
            }
        }
    };

    /**
     * ************************************ 单例 **********************************************
     */

    static class InstanceHolder {
        final static NeteaseRobotInfoCache instance = new NeteaseRobotInfoCache();
    }
}
