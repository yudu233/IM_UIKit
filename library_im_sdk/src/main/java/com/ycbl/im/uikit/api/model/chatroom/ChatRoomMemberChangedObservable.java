package com.ycbl.im.uikit.api.model.chatroom;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/8/10 16:02
 * @Description :UIKit 与 app 聊天室成员变化监听接口
 */

public class ChatRoomMemberChangedObservable<T> {

    private List<RoomMemberChangedObserver<T>> observers = new ArrayList<>();
    private Handler uiHandler;

    public ChatRoomMemberChangedObservable(Context context) {
        uiHandler = new Handler(context.getMainLooper());
    }

    public synchronized void registerObserver(RoomMemberChangedObserver<T> observer, boolean register) {
        if (observer == null) {
            return;
        }
        if (register) {
            observers.add(observer);
        } else {
            observers.remove(observer);
        }
    }

    public synchronized void notifyMemberChange(T member, final boolean in) {
        uiHandler.post(() -> {
            if (in) {
                for (RoomMemberChangedObserver<T> o : observers) {
                    o.onRoomMemberIn(member);
                }
            } else {
                for (RoomMemberChangedObserver<T> o : observers) {
                    o.onRoomMemberExit(member);
                }
            }
        });
    }
}
