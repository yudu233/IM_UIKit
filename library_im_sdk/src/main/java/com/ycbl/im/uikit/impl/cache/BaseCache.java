package com.ycbl.im.uikit.impl.cache;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/24 10:25
 * @Version : 1.0
 * @Descroption :
 */
public interface BaseCache {

    /**
     * 构建缓存
     */
    void buildCache();

    /**
     * 清除缓存
     */
    void clearCache();

    /**
     * 注册观察者
     */
    void registerObservers(boolean register);
}
