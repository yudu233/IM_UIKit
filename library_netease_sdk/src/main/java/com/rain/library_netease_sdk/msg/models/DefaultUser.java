package com.rain.library_netease_sdk.msg.models;

import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.messagelist.model.IUser;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/19 10:29
 * @Version : 1.0
 * @Descroption :
 */
public class DefaultUser implements IUser {

    private IMMessage message;

    public DefaultUser(IMMessage message) {
        this.message = message;
    }

    @Override
    public String getId() {
        return message.getFromAccount();
    }

    @Override
    public String getName() {
        // TODO: 2020/6/19 群组成员昵称显示
        if (message.getSessionType() == SessionTypeEnum.Team) {
            return "";
        }
        return "";
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
