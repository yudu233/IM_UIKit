package com.ycbl.im.uikit.api.model.main;

import android.content.Context;
import android.os.Handler;

import com.netease.nimlib.sdk.msg.model.SystemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:duyu
 * @org :   www.yudu233.com
 * @email : yudu233@gmail.com
 * @date :  2019/6/18 14:21
 * @filename : SystemMessageObservable
 * @describe :
 */
public class SystemMessageObservable {

    private Handler uiHandler;

    private List<SystemMessageObserver> systemMessageObservers = new ArrayList<>();

    public SystemMessageObservable(Context context) {
        uiHandler = new Handler(context.getMainLooper());
    }

    public synchronized void registerSystemMessageObserver(SystemMessageObserver o, boolean register) {
        if (register) {
            if (systemMessageObservers.contains(o)) {
                return;
            }
            systemMessageObservers.add(o);
        } else {
            systemMessageObservers.remove(o);
        }
    }

    public synchronized void setFriendSystemMessage(final SystemMessage message) {
        uiHandler.post(() -> {
            for (SystemMessageObserver observer : systemMessageObservers) {
                observer.friendSystemMsg(message);
            }
        });
    }

    public synchronized void setGroupVerifySystemMessage(final SystemMessage message) {
        uiHandler.post(() -> {
            for (SystemMessageObserver observer : systemMessageObservers) {
                observer.groupVerifyMsg(message);
            }
        });
    }

}
