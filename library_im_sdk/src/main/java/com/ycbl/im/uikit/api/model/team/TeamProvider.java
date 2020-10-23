package com.ycbl.im.uikit.api.model.team;

import com.ycbl.im.uikit.api.model.SimpleCallback;

import java.util.List;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/9/23 17:59
 * @Description : 群、群成员信息提供者
 */

public interface TeamProvider<T, K> {
    /**
     * 根据teamId 异步获取群信息
     *
     * @param teamId   群id
     * @param callback 回调
     */
    void fetchTeamById(String teamId, SimpleCallback<T> callback);

    /**
     * 根据teamId 同步获取群信息
     *
     * @param teamId 群id
     * @return 群信息Team
     */
    T getTeamById(String teamId);

    /**
     * 获取当前账号所有的群
     *
     * @return 群列表
     */
    List<T> getAllTeams();

    /**
     * 获取当前账号所有的指定类型的群
     *
     * @return 群列表
     */
    List<T> getAllTeamsByType(int teamType);

    /**
     * 根据群id异步获取当前群所有的群成员信息
     *
     * @param teamId   群id
     * @param callback 回调
     */
    void fetchTeamMemberList(String teamId, SimpleCallback<List<K>> callback);

    /**
     * 根据群id、账号（异步）查询群成员资料
     */
    void fetchTeamMember(String teamId, String account, SimpleCallback<K> callback);

    /**
     * 根据群id 同步获取当前群所有的群成员信息
     *
     * @param teamId 群id
     * @return 群成员信息列表
     */
    List<K> getTeamMemberList(String teamId);

    /**
     * 获取群成员资料
     *
     * @param teamId  群id
     * @param account 成员账号
     * @return TeamMember
     */
    K getTeamMember(String teamId, String account);
}
