package com.rain.library_netease_sdk.business.team;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.LocalAntiSpamResult;
import com.netease.nimlib.sdk.team.constant.TeamBeInviteModeEnum;
import com.netease.nimlib.sdk.team.constant.TeamInviteModeEnum;
import com.netease.nimlib.sdk.team.constant.TeamMemberType;
import com.netease.nimlib.sdk.team.constant.TeamMessageNotifyTypeEnum;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.constant.TeamUpdateModeEnum;
import com.netease.nimlib.sdk.team.constant.VerifyTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.rain.library_netease_sdk.NimUIKit;
import com.rain.library_netease_sdk.R;
import com.rain.library_netease_sdk.business.user.UserInfoHelper;
import com.ycbl.im.uikit.api.model.SimpleCallback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : Rain
 * @CreateDate : 2020/10/22 18:34
 * @Version : 1.0
 * @Descroption : 群组相关助手类
 */
public class TeamHelper {

    /**
     * 申请加入群组时的验证类型
     *
     * @param name
     * @return
     */
    public static VerifyTypeEnum getVerifyTypeEnum(String name) {
        VerifyTypeEnum type = null;

        if (name.equals(NimUIKit.getContext().getString(R.string.team_allow_anyone_join))) {
            type = VerifyTypeEnum.Free;
        } else if (name.equals(NimUIKit.getContext().getString(R.string.team_need_authentication))) {
            type = VerifyTypeEnum.Apply;
        } else if (name.equals(NimUIKit.getContext().getString(R.string.team_not_allow_anyone_join))) {
            type = VerifyTypeEnum.Private;
        }

        return type;
    }

    /**
     * 申请加入群组菜单项名称
     *
     * @return
     */
    public static List<String> createAuthenMenuStrings() {
        int[] res = new int[]{R.string.team_allow_anyone_join, R.string.team_need_authentication,
                R.string.team_not_allow_anyone_join};
        List<String> btnNames = new ArrayList<>();
        for (int r : res) {
            btnNames.add(NimUIKit.getContext().getString(r));
        }
        return btnNames;
    }

    /**
     * 获取验证显示文案
     *
     * @param type 验证类型
     * @return
     */
    public static int getVerifyString(VerifyTypeEnum type) {
        if (type == VerifyTypeEnum.Free) {
            return R.string.team_allow_anyone_join;
        } else if (type == VerifyTypeEnum.Apply) {
            return R.string.team_need_authentication;
        } else {
            return R.string.team_not_allow_anyone_join;
        }
    }

    /**
     * 群消息提醒类型菜单项名称
     *
     * @return
     */
    public static List<String> createNotifyMenuStrings() {
        int[] res = new int[]{
                R.string.team_notify_all,
                R.string.team_notify_manager,
                R.string.team_notify_mute
        };
        List<String> btnNames = new ArrayList<>();
        for (int r : res) {
            btnNames.add(NimUIKit.getContext().getString(r));
        }
        return btnNames;
    }

    /**
     * 群消息提醒类型菜单项名称--修改后的
     *
     * @return
     */
    public static List<String> customCreateNotifyMenuStrings() {
        int[] res = new int[]{
                R.string.team_notify_all,
                R.string.team_notify_mute,
                R.string.text_cancel
        };
        List<String> btnNames = new ArrayList<>();
        for (int r : res) {
            btnNames.add(NimUIKit.getContext().getString(r));
        }
        return btnNames;
    }

    /**
     * 获取提醒类型
     *
     * @param name
     * @return
     */
    public static TeamMessageNotifyTypeEnum getNotifyType(String name) {
        TeamMessageNotifyTypeEnum type = null;
        if (name.equals(NimUIKit.getContext().getString(R.string.team_notify_all))) {
            type = TeamMessageNotifyTypeEnum.All;
        } else if (name.equals(NimUIKit.getContext().getString(R.string.team_notify_manager))) {
            type = TeamMessageNotifyTypeEnum.Manager;
        } else if (name.equals(NimUIKit.getContext().getString(R.string.team_notify_mute))) {
            type = TeamMessageNotifyTypeEnum.Mute;
        }
        return type;
    }

    /**
     * 邀请他人菜单项名称
     *
     * @return
     */
    public static List<String> createInviteMenuStrings() {
        int[] res = new int[]{R.string.team_admin_invite, R.string.team_everyone_invite};
        List<String> btnNames = new ArrayList<>();
        for (int r : res) {
            btnNames.add(NimUIKit.getContext().getString(r));
        }
        return btnNames;
    }

    /**
     * 获取邀请他人类型
     *
     * @param name
     * @return
     */
    public static TeamInviteModeEnum getInviteModeEnum(String name) {
        TeamInviteModeEnum type = null;

        if (name.equals(NimUIKit.getContext().getString(R.string.team_admin_invite))) {
            type = TeamInviteModeEnum.Manager;
        } else if (name.equals(NimUIKit.getContext().getString(R.string.team_everyone_invite))) {
            type = TeamInviteModeEnum.All;
        }

        return type;
    }

    /**
     * 获取邀请他人文本描述
     *
     * @param type
     * @return
     */
    public static int getInviteModeString(TeamInviteModeEnum type) {
        if (type == TeamInviteModeEnum.Manager) {
            return R.string.team_admin_invite;
        } else {
            return R.string.team_everyone_invite;
        }
    }

    /**
     * 群资料修改权限菜单名称
     *
     * @return
     */
    public static List<String> createTeamInfoUpdateMenuStrings() {
        int[] res = new int[]{R.string.team_admin_update, R.string.team_everyone_update};
        List<String> btnNames = new ArrayList<>();
        for (int r : res) {
            btnNames.add(NimUIKit.getContext().getString(r));
        }
        return btnNames;
    }

    /**
     * 获取群资料修改类型
     *
     * @param name
     * @return
     */
    public static TeamUpdateModeEnum getUpdateModeEnum(String name) {
        TeamUpdateModeEnum type = null;

        if (name.equals(NimUIKit.getContext().getString(R.string.team_admin_update))) {
            type = TeamUpdateModeEnum.Manager;
        } else if (name.equals(NimUIKit.getContext().getString(R.string.team_everyone_update))) {
            type = TeamUpdateModeEnum.All;
        }

        return type;
    }

    /**
     * 获取群资料修改类型文本描述
     *
     * @param type
     * @return
     */
    public static int getInfoUpdateModeString(TeamUpdateModeEnum type) {
        if (type == TeamUpdateModeEnum.Manager) {
            return R.string.team_admin_update;
        } else {
            return R.string.team_everyone_update;
        }
    }

    /**
     * 被邀请人身份验证名称
     *
     * @return
     */
    public static List<String> createTeamInviteeAuthenMenuStrings() {
        int[] res = new int[]{R.string.team_invitee_need_authen, R.string.team_invitee_not_need_authen};
        List<String> btnNames = new ArrayList<>();
        for (int r : res) {
            btnNames.add(NimUIKit.getContext().getString(r));
        }
        return btnNames;
    }

    /**
     * 获取被邀请人身份类型
     *
     * @param name
     * @return
     */
    public static TeamBeInviteModeEnum getBeInvitedModeEnum(String name) {
        TeamBeInviteModeEnum type = null;

        if (name.equals(NimUIKit.getContext().getString(R.string.team_invitee_need_authen))) {
            type = TeamBeInviteModeEnum.NeedAuth;
        } else if (name.equals(NimUIKit.getContext().getString(R.string.team_invitee_not_need_authen))) {
            type = TeamBeInviteModeEnum.NoAuth;
        }

        return type;
    }

    /**
     * 获取被邀请人类型文本描述
     *
     * @param type
     * @return
     */
    public static int getBeInvitedModeString(TeamBeInviteModeEnum type) {
        if (type == TeamBeInviteModeEnum.NeedAuth) {
            return R.string.team_invitee_need_authen;
        } else {
            return R.string.team_invitee_not_need_authen;
        }
    }

    private static Map<TeamMemberType, Integer> teamMemberLevelMap = new HashMap<>(4);

    static {
        teamMemberLevelMap.put(TeamMemberType.Owner, 0);
        teamMemberLevelMap.put(TeamMemberType.Manager, 1);
        teamMemberLevelMap.put(TeamMemberType.Normal, 2);
        teamMemberLevelMap.put(TeamMemberType.Apply, 3);
    }

    /**
     * 邀请的成员，所在群数量已经超限
     *
     * @param failedAccounts 超限的账号列表
     * @param context        context
     */
    public static void onMemberTeamNumOverrun(List<String> failedAccounts, Context context) {
        if (failedAccounts != null && !failedAccounts.isEmpty()) {
            StringBuilder tipContent = new StringBuilder("好友：");
            for (int i = 0; i < failedAccounts.size(); i++) {
                String name = UserInfoHelper.getUserDisplayName(failedAccounts.get(i));
                tipContent.append(name);
                if (i != failedAccounts.size() - 1) {
                    tipContent.append("、");
                }
            }
            tipContent.append("所在群组数量达到上限，邀请失败");
            Toast.makeText(context, tipContent.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static Comparator<TeamMember> teamMemberComparator = new Comparator<TeamMember>() {
        @Override
        public int compare(TeamMember l, TeamMember r) {
            if (l == null) {
                return 1;
            }

            if (r == null) {
                return -1;
            }

            return teamMemberLevelMap.get(l.getType()) - teamMemberLevelMap.get(r.getType());
        }
    };


    public static String getTeamName(String teamId) {
        Team team = NimUIKit.getTeamProvider().getTeamById(teamId);
        return team == null ? teamId : TextUtils.isEmpty(team.getName()) ? team.getId() : team
                .getName();
    }

    /**
     * 获取显示名称。用户本人显示“我”
     *
     * @param tid
     * @param account
     * @return
     */
    public static String getTeamMemberDisplayName(String tid, String account) {
        if (account.equals(NimUIKit.getAccount())) {
            return "我";
        }

        return getDisplayNameWithoutMe(tid, account);
    }

    public static String getTeamMemberDisplayName(Context context, String tid, String account) {
        if (account.equals(NimUIKit.getAccount())) {
            return "我";
        }

        return getDisplayNameWithoutMe(context, tid, account);
    }

    /**
     * 获取显示名称。用户本人显示“你”
     *
     * @param tid
     * @param account
     * @return
     */
    public static String getTeamMemberDisplayNameYou(String tid, String account) {
        if (account.equals(NimUIKit.getAccount())) {
            return "你";
        }

        return getDisplayNameWithoutMe(tid, account);
    }

    /**
     * 获取显示名称。用户本人也显示昵称
     * 备注>群昵称>昵称
     */
    public static String getDisplayNameWithoutMe(String tid, String account) {
        String alias = NimUIKit.getContactProvider().getAlias(account);
        if (!TextUtils.isEmpty(alias)) {
            return alias;
        }

        String memberNick = getTeamNick(tid, account);
        if (!TextUtils.isEmpty(memberNick)) {
            return memberNick;
        }

        return UserInfoHelper.getUserName(account);
    }

    public static String getDisplayNameWithoutMe(Context context, String tid, String account) {
        String alias = NimUIKit.getContactProvider().getAlias(account);
        if (!TextUtils.isEmpty(alias)) {
            return alias;
        }

        String memberNick = getTeamNick(tid, account);
        if (!TextUtils.isEmpty(memberNick)) {
            return memberNick;
        }

        return UserInfoHelper.getUserName(account);
    }

    public static String getTeamNick(String tid, String account) {
        Team team = NimUIKit.getTeamProvider().getTeamById(tid);
        if (team != null && team.getType() == TeamTypeEnum.Advanced) {
            TeamMember member = NimUIKit.getTeamProvider().getTeamMember(tid, account);
            if (member != null && !TextUtils.isEmpty(member.getTeamNick())) {
                return member.getTeamNick();
            }
        }
        return null;
    }

    public static void checkLocalAntiSpam(String content, SimpleCallback<String> callback) {
        LocalAntiSpamResult result = NIMClient.getService(MsgService.class).checkLocalAntiSpam(content, "**");
        int operator = result == null ? 0 : result.getOperator();

        switch (operator) {
            case 1: // 替换，允许发送
            case 3: // 允许发送，交给服务器
                callback.onResult(true, result.getContent(), 1001);
                break;
            case 2: // 拦截，不允许发送
                ToastUtils.showShort("包含敏感字，请重新提交！");
                break;
            case 0:
            default:
                callback.onResult(true, content, 1001);
                break;
        }
    }
}
