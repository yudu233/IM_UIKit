package com.ycbl.im.uikit.api.model;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/8/10 16:08
 * @Description : 简单的回调接口
 */
public interface SimpleCallback<T> {

    /**
     * 回调函数返回结果
     *
     * @param success 是否成功，结果是否有效
     * @param result  结果
     * @param code    失败时错误码
     */
    void onResult(boolean success, T result, int code);
}
