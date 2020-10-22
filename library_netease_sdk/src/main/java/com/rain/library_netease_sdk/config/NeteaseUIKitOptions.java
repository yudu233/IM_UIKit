package com.rain.library_netease_sdk.config;

import com.netease.nimlib.sdk.media.record.RecordType;
import com.ycbl.im.uikit.api.UIKitOptions;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/28 11:10
 * @Version : 1.0
 * @Descroption : IM基本配置 -> 网易云信
 */
public class NeteaseUIKitOptions extends UIKitOptions {
    /**
     * 录音类型，默认aac
     */
    public RecordType audioRecordType;

    public NeteaseUIKitOptions() {
        //使用自身服务器好友关系
        useSdkFriends = false;
        audioRecordType = RecordType.AMR;
    }
}
