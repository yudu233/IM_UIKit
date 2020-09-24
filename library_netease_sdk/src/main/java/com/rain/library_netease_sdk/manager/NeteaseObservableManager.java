package com.rain.library_netease_sdk.manager;

import android.content.Context;

import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.ycbl.im.uikit.api.model.ObservableManager;
import com.ycbl.im.uikit.api.model.chatroom.ChatRoomMemberChangedObservable;
import com.ycbl.im.uikit.api.model.main.SystemMessageObservable;
import com.ycbl.im.uikit.api.model.team.TeamChangedObservable;
import com.ycbl.im.uikit.api.model.user.UserInfoObservable;
import com.ycbl.im.uikit.common.log.LogUtil;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/22 11:18
 * @Version : 1.0
 * @Descroption :
 */
public class NeteaseObservableManager extends ObservableManager {

    public NeteaseObservableManager() {
    }

    //系统消息监听
    private static SystemMessageObservable<SystemMessage> systemMessageObservable;

    @Override
    public SystemMessageObservable<SystemMessage> getSystemMessageObservable(Context context) {

        if (systemMessageObservable == null) {
            systemMessageObservable = new SystemMessageObservable<>(context);
        }
        return systemMessageObservable;
    }

    //用户信息变动观察者
    private static UserInfoObservable userInfoObservable;

    @Override
    public UserInfoObservable getUserInfoObservable(Context context) {
        if (userInfoObservable == null) {
            userInfoObservable = new UserInfoObservable(context);
        }
        return userInfoObservable;
    }

    //群、群成员变更通知观察者
    private static TeamChangedObservable teamChangedObservable;

    @Override
    public TeamChangedObservable<Team, TeamMember> getTeamChangedObservable(Context context) {
        if (teamChangedObservable == null) {
            teamChangedObservable = new TeamChangedObservable<Team, TeamMember>(context);
        }
        return teamChangedObservable;
    }

    // 聊天室成员变更通知
    private static ChatRoomMemberChangedObservable<ChatRoomMember> chatRoomMemberChangedObservable;

    @Override
    public ChatRoomMemberChangedObservable<ChatRoomMember> getChatRoomMemberChangedObservable(Context context) {
        if (chatRoomMemberChangedObservable == null) {
            chatRoomMemberChangedObservable = new ChatRoomMemberChangedObservable<>(context);
        }
        return chatRoomMemberChangedObservable;
    }
}
