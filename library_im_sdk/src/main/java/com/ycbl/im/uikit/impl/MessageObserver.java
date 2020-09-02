package com.ycbl.im.uikit.impl;

import java.io.Serializable;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/2 11:20
 * @Version : 1.0
 * @Descroption :
 */
public interface MessageObserver<T> extends Serializable {

    /**
     * 通知产生后的回调函数
     * @param t 事件参数
     */
    public void onEvent(T t);
}