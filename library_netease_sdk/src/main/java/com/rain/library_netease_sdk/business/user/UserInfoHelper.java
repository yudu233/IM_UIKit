package com.rain.library_netease_sdk.business.user;

import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.rain.library_netease_sdk.NimUIKit;
import com.rain.library_netease_sdk.business.team.TeamHelper;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/10/23 10:41
 * @Description : 用户信息助手类
 */
public class UserInfoHelper {

    /**
     * 获取用户显示在标题栏和最近联系人中的名字
     *
     * @param id
     * @param sessionType
     * @return
     */
    public static String getUserTitleName(String id, SessionTypeEnum sessionType) {
        if (sessionType == SessionTypeEnum.P2P) {
            if (StringUtils.isEmpty(NimUIKit.getAccount()) || NimUIKit.getAccount().equals(id)) {
                return "我的电脑";
            } else {
                return getUserDisplayName(id);
            }
        } else if (sessionType == SessionTypeEnum.Team) {
            return TeamHelper.getTeamName(id);
        }
        return id;
    }

    /**
     * 获取用户昵称
     *
     * @param account
     * @return
     */
    public static String getUserDisplayName(String account) {
        String alias = NimUIKit.getContactProvider().getAlias(account);
        if (!TextUtils.isEmpty(alias)) {
            return alias;
        } else {
            UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(account);
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getName())) {
                return userInfo.getName();
            } else {
                return account;
            }
        }
    }

    /**
     * 获取消息发送者的展示名称，优先级序列：备注>群昵称>用户名>账号
     *
     * @param account 用户账号
     * @param sessionType 所处会话的类型
     * @param sessionId 所处会话的ID
     * @return 发送者的展示名称
     */
    public static String getUserDisplayNameInSession(String account, SessionTypeEnum sessionType, String sessionId) {
        if (TextUtils.isEmpty(account)) {
            return "";
        }

        // 备注
        String alias = NimUIKit.getContactProvider().getAlias(account);
        if (!TextUtils.isEmpty(alias)) {
            return alias;
        }

        // 群昵称
        String teamNick = null;
        if (sessionType == SessionTypeEnum.Team) {
            teamNick = TeamHelper.getTeamNick(sessionId, account);
        } else if (sessionType == SessionTypeEnum.SUPER_TEAM) {
//            teamNick = SuperTeamHelper.getTeamNick(sessionId, account);
        }
        if (!TextUtils.isEmpty(teamNick)) {
            return teamNick;
        }

        // 用户名
        UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(account);
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getName())) {
            return userInfo.getName();
        }

        // 账号
        return account;

    }

    /**
     * 获取用户原本的昵称
     *
     * @param account
     * @return
     */
    public static String getUserName(String account) {
        UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(account);
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getName())) {
            return userInfo.getName();
        } else {
            return account;
        }
    }

    /**
     * @param account         账号
     * @param selfNameDisplay 如果是自己，则显示内容
     * @return
     */
    public static String getUserDisplayNameEx(String account, String selfNameDisplay) {
        if (account.equals(NimUIKit.getAccount())) {
            return selfNameDisplay;
        }

        return getUserDisplayName(account);
    }
}
