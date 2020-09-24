package com.rain.library_netease_sdk.impl.provider;

import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.rain.library_netease_sdk.impl.cache.NeteaseTeamDataCache;
import com.ycbl.im.uikit.api.model.SimpleCallback;
import com.ycbl.im.uikit.api.model.team.TeamProvider;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/23 18:14
 * @Version : 1.0
 * @Descroption : 网易 群、群成员信息提供者
 */
public class NeteaseTeamProvider implements TeamProvider<Team, TeamMember> {

    @Override
    public void fetchTeamById(String teamId, SimpleCallback<Team> callback) {
        NeteaseTeamDataCache.getInstance().fetchTeamById(teamId, callback);
    }

    @Override
    public Team getTeamById(String teamId) {
        return NeteaseTeamDataCache.getInstance().getTeamById(teamId);
    }

    @Override
    public List<Team> getAllTeams() {
        return NeteaseTeamDataCache.getInstance().getAllTeams();

    }

    @Override
    public List<Team> getAllTeamsByType(int teamType) {
        if (teamType == TeamTypeEnum.Advanced.getValue()) {
            return NeteaseTeamDataCache.getInstance().getAllAdvancedTeams();
        } else if (teamType == TeamTypeEnum.Normal.getValue()) {
            return NeteaseTeamDataCache.getInstance().getAllNormalTeams();
        } else {
            return null;
        }
    }

    @Override
    public void fetchTeamMemberList(String teamId, SimpleCallback<List<TeamMember>> callback) {
        NeteaseTeamDataCache.getInstance().fetchTeamMemberList(teamId, callback);
    }

    @Override
    public void fetchTeamMember(String teamId, String account, SimpleCallback<TeamMember> callback) {
        NeteaseTeamDataCache.getInstance().fetchTeamMember(teamId, account, callback);
    }

    @Override
    public List<TeamMember> getTeamMemberList(String teamId) {
        return NeteaseTeamDataCache.getInstance().getTeamMemberList(teamId);

    }

    @Override
    public TeamMember getTeamMember(String teamId, String account) {
        return NeteaseTeamDataCache.getInstance().getTeamMember(teamId, account);
    }
}
