package com.rain.library_netease_sdk.impl.provider;

import com.netease.nimlib.sdk.robot.model.NimRobotInfo;
import com.rain.library_netease_sdk.impl.cache.NeteaseRobotInfoCache;
import com.ycbl.im.uikit.api.model.SimpleCallback;
import com.ycbl.im.uikit.api.model.robot.RobotInfoProvider;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/24 10:19
 * @Version : 1.0
 * @Descroption : 网易云信 -> 智能机器人信息提供者
 */
public class NeteaseRobotInfoProvider implements RobotInfoProvider<NimRobotInfo> {
    @Override
    public NimRobotInfo getRobotByAccount(String account) {
        return NeteaseRobotInfoCache.getInstance().getRobotByAccount(account);

    }

    @Override
    public List<NimRobotInfo> getAllRobotAccounts() {
        return NeteaseRobotInfoCache.getInstance().getAllRobotAccounts();
    }

    @Override
    public void fetchRobotList(SimpleCallback<List<NimRobotInfo>> callback) {
        NeteaseRobotInfoCache.getInstance().fetchRobotList(callback);
    }

    @Override
    public void fetchRobotListIndependent(String roomId, SimpleCallback<List<NimRobotInfo>> callback) {
        NeteaseRobotInfoCache.getInstance().pullRobotListIndependent(roomId, callback);
    }
}
