package com.rain.chat.base;

import com.rain.library_netease_sdk.config.NeteaseUIKitOptions;
import com.rain.library_netease_sdk.message.NeteaseMessageStrategy;
import com.ycbl.im.uikit.IMSDKType;
import com.ycbl.im.uikit.msg.IMessageBuilder;
import com.ycbl.im.uikit.msg.test.MessageStrategy;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/10 11:45
 * @Version : 1.0
 * @Descroption :
 */
public class IM {


    private static IMessageBuilder iMessageBuilder;
    private static NeteaseUIKitOptions imsdkOptions;

    public static void init() {
        imsdkOptions = new NeteaseUIKitOptions();

    }

    /**
     * 设置消息体构建方式
     *
     * @return
     */
    public static MessageStrategy getMessageStrategy() {
        if (imsdkOptions.imsdkType == IMSDKType.NETEASE_IM) {
            return new NeteaseMessageStrategy();
        } else {
            return null;
        }
    }

    /**
     * 通过IMessageBuilder构建相应的消息体
     *
     * @return
     */
    public static IMessageBuilder getIMessageBuilder() {
        if (iMessageBuilder == null) {
            iMessageBuilder = new IMessageBuilder(IM.getMessageStrategy());
        }
        return iMessageBuilder;
    }
}
