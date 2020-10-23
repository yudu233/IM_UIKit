package com.ycbl.im.uikit.api.model.chatroom;

import com.ycbl.im.uikit.api.model.SimpleCallback;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/8/10 16:03
 * @Description : 聊天室成员提供者
 */
public interface ChatRoomProvider<T> {

    /**
     * 获取聊天室成员
     *
     * @param roomId  聊天室
     * @param account 账号
     * @return ChatRoomMember
     */
    T getChatRoomMember(String roomId, String account);

    /**
     * 异步获取聊天室成员
     *
     * @param roomId   聊天室
     * @param account  账号
     * @param callback 回调
     */
    void fetchMember(String roomId, String account, SimpleCallback<T> callback);

}
