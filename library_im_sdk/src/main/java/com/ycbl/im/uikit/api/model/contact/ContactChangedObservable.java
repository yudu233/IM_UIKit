package com.ycbl.im.uikit.api.model.contact;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/8/10 16:03
 * @Description : 好友关系变动观察者管理
 */

public class ContactChangedObservable {

    private List<ContactChangedObserver> observers = new ArrayList<>();
    private Handler uiHandler;

    public ContactChangedObservable(Context context) {
        uiHandler = new Handler(context.getMainLooper());
    }

    public synchronized void registerObserver(ContactChangedObserver observer, boolean register) {
        if (observer == null) {
            return;
        }
        if (register) {
            observers.add(observer);
        } else {
            observers.remove(observer);
        }
    }

    /**
     * 增加或者更新好友
     *
     * @param accounts 账号列表
     */
    public synchronized void notifyAddedOrUpdated(final List<String> accounts) {
        uiHandler.post(() -> {
            for (ContactChangedObserver observer : observers) {
                observer.onAddedOrUpdatedFriends(accounts);
            }
        });
    }

    /**
     * 删除好友
     *
     * @param accounts 账号列表
     */
    public synchronized void notifyDelete(final List<String> accounts) {
        uiHandler.post(() -> {
            for (ContactChangedObserver observer : observers) {
                observer.onDeletedFriends(accounts);
            }
        });
    }

    /**
     * 增加到黑名单
     *
     * @param accounts 账号列表
     */
    public synchronized void notifyAddToBlackList(final List<String> accounts) {
        uiHandler.post(() -> {
            for (ContactChangedObserver observer : observers) {
                observer.onAddUserToBlackList(accounts);
            }
        });
    }

    /**
     * 从黑名单移除
     *
     * @param accounts 账号列表
     */
    public synchronized void notifyRemoveFromBlackList(final List<String> accounts) {
        uiHandler.post(() -> {
            for (ContactChangedObserver observer : observers) {
                observer.onRemoveUserFromBlackList(accounts);
            }
        });
    }
}
