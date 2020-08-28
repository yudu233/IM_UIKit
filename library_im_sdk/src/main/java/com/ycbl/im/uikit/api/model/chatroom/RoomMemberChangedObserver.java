package com.ycbl.im.uikit.api.model.chatroom;

import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/8/10 16:03
 * @Description : UIKit 与 app 聊天室成员数据变更监听接口
 */

public interface RoomMemberChangedObserver {

    /**
     * 聊天室新增成员
     *
     * @param member 成员
     */
    void onRoomMemberIn(ChatRoomMember member);

    /**
     * 聊天室退出成员
     *
     * @param member 成员
     */
    void onRoomMemberExit(ChatRoomMember member);
}
