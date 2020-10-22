package com.rain.library_netease_sdk.config;

import com.netease.nimlib.sdk.ServerAddresses;

/**
 * @author : duyu
 * @date : 2019/1/3 14:46
 * @filename : PrivatizationConfig
 * @describe : 网易云信私有化配置项
 */
public class PrivatizationConfig {
    public static ServerAddresses getServerAddresses() {
        return null;
    }

    public static String getAppKey() {
        return null;
    }

    private static ServerAddresses get() {
        ServerAddresses addresses = new ServerAddresses();
        addresses.nosDownload = "nos.netease.com";
        addresses.nosAccess = "{bucket}-nosdn.netease.im/{object}";
        return addresses;
    }
}
