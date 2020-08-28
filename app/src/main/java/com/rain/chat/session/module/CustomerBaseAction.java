package com.rain.chat.session.module;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.rain.inputpanel.action.BaseAction;
import com.rain.messagelist.message.SessionType;

import java.lang.ref.WeakReference;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/13 17:10
 * @Version : 1.0
 * @Descroption :
 */
public abstract class CustomerBaseAction extends BaseAction {

    // Container持有activity ， 防止内存泄露
    private transient WeakReference<Container> containerRef;

    /**
     * 构造函数
     *
     * @param iconResId 图标 res id
     * @param titleId   图标标题的string res id
     */
    public CustomerBaseAction(int iconResId, int titleId) {
        super(iconResId, titleId);
    }

    public Container getContainer() {
        Container container = containerRef.get();
        if (container == null) {
            throw new RuntimeException("container be recycled by vm ");
        }
        return container;
    }

    public void setContainer(Container container) {
        this.containerRef = new WeakReference<>(container);
    }

    public String getAccount() {
        return getContainer().account;
    }

    public SessionType getSessionType(){
        return  getContainer().sessionType;
    }

    public FragmentActivity getActivity() {
        return getContainer().activity;
    }
}
