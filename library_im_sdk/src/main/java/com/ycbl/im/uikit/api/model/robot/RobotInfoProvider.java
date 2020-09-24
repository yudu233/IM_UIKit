package com.ycbl.im.uikit.api.model.robot;

import com.ycbl.im.uikit.api.model.SimpleCallback;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/24 10:16
 * @Version : 1.0
 * @Descroption : 智能机器人信息提供者
 */
public interface RobotInfoProvider<T> {
    /**
     * 根据 id 获取智能机器人
     *
     * @param account 智能机器人id
     * @return NimRobotInfo
     */
    T getRobotByAccount(String account);

    /**
     * 获取所有的智能机器人
     *
     * @return 智能机器人列表
     */
    List<T> getAllRobotAccounts();

    /**
     * IM 模式下，获取(异步)智能机器人
     */
    void fetchRobotList(SimpleCallback<List<T>> callback);

    /**
     * 独立聊天室模式下，获取(异步)智能机器人
     *
     * @param roomId 聊天室id
     */
    void fetchRobotListIndependent(String roomId, SimpleCallback<List<T>> callback);
}
