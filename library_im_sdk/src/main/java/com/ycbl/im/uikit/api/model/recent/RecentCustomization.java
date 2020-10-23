package com.ycbl.im.uikit.api.model.recent;


import java.io.Serializable;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/24 10:15
 * @Version : 1.0
 * @Descroption : 会话项默认文案定制 
 */
public class RecentCustomization<T> implements Serializable {
    public String getDefaultDigest(T recent) {
        return "";
    }
}
