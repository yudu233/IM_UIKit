package com.ycbl.im.uikit.api.model;

import android.content.Context;

import com.ycbl.im.uikit.api.model.chatroom.ChatRoomMemberChangedObservable;
import com.ycbl.im.uikit.api.model.main.SystemMessageObservable;
import com.ycbl.im.uikit.api.model.team.TeamChangedObservable;
import com.ycbl.im.uikit.api.model.user.UserInfoObservable;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/23 14:39
 * @Version : 1.0
 * @Descroption : 观察者管理接口  下发到具体的业务类去实现
 */
public abstract class ObservableManager {
    public abstract ChatRoomMemberChangedObservable getChatRoomMemberChangedObservable(Context context);

    public abstract SystemMessageObservable getSystemMessageObservable(Context mContext);

    public abstract UserInfoObservable getUserInfoObservable(Context mContext);

    public abstract TeamChangedObservable getTeamChangedObservable(Context context);
}
