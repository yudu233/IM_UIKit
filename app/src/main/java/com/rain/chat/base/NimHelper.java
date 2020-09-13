package com.rain.chat.base;

import android.content.Context;

import com.rain.chat.session.location.NimLocationProvider;
import com.ycbl.im.uikit.api.NimUIKit;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:38
 * @Version : 1.0
 * @ChangeDate : 2019/1/3 14:34
 * @Version : 2.0
 * @Descroption : 云通信助手类
 */
public class NimHelper {


    /**
     * 初始化UIKit模块
     */
    public static void initUIKit(Context context) {

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(new NimLocationProvider());
    }

}
