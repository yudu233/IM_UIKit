package com.rain.chat.base;

import android.content.Context;
import android.text.TextUtils;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.rain.chat.session.location.NimLocationProvider;
import com.rain.chat.session.viewholder.MsgViewHolderLocation;
import com.rain.chat.session.viewholder.MsgViewHolderTranslateAudio;
import com.rain.library_netease_sdk.NeteaseCache;
import com.rain.library_netease_sdk.NimUIKit;
import com.rain.library_netease_sdk.config.NeteaseSDKOptionConfig;
import com.rain.library_netease_sdk.config.NeteaseUIKitOptions;
import com.rain.library_netease_sdk.config.preference.Preferences;
import com.rain.library_netease_sdk.impl.cache.DataCacheManager;
import com.rain.library_netease_sdk.impl.provider.NeteaseUserInfoProvider;
import com.rain.library_netease_sdk.manager.NeteaseObservableManager;
import com.rain.library_netease_sdk.manager.NeteaseProviderManager;
import com.rain.library_netease_sdk.message.NeteaseMessageStrategy;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.message.MsgViewHolderFactory;
import com.ycbl.im.uikit.IMSDKType;
import com.ycbl.im.uikit.NimUIKitImpl;
import com.ycbl.im.uikit.common.storage.ExternalStorage;
import com.ycbl.im.uikit.msg.IMessageBuilder;
import com.ycbl.im.uikit.msg.test.MessageStrategy;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:38
 * @Version : 1.0
 * @ChangeDate : 2019/1/3 14:34
 * @Version : 2.0
 * @Descroption : 云通信助手类
 */
public class NimHelper {
    //IMMessage消息构造器
    private static IMessageBuilder iMessageBuilder;
    // IM UIKit基本配置
    private static NeteaseUIKitOptions options;

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        options = new NeteaseUIKitOptions();
        // 云信初始化
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
       // NIMClient.init(context, NimHelper.getLoginInfo(), NeteaseSDKOptionConfig.getSDKOptions(context));

        msgViewHolderRegister();
        initUIKit();
    }

    /**
     * 注册多类型Message ViewHolder
     */
    private static void msgViewHolderRegister() {
        MsgViewHolderFactory.register(MessageType.audio, MsgViewHolderTranslateAudio.class);
        MsgViewHolderFactory.register(MessageType.location, MsgViewHolderLocation.class);
    }


    /**
     * 初始化UIKit模块
     */
    public static void initUIKit() {
        //初始化
        NimUIKit.init(mContext, options, new NeteaseUserInfoProvider(),
                new NeteaseObservableManager(), new NeteaseProviderManager());


        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(new NimLocationProvider());

        //缓存
        DataCacheManager.observeSDKDataChanged(true);
        if (!TextUtils.isEmpty(NimUIKit.getAccount())) {
            if (options.initAsync) {
                DataCacheManager.buildDataCacheAsync(mContext); // build data cache on auto login
            } else {
                DataCacheManager.buildDataCache(); // build data cache on auto login
                NimUIKitImpl.notifyCacheBuildComplete();
            }
        }
        //聊天室成员缓存

        //App缓存目录创建
        ExternalStorage.getInstance().init(mContext, options.appCacheDir);

    }

    /**
     * 设置消息体构建方式
     *
     * @return
     */
    public static MessageStrategy getMessageStrategy() {
        if (options.imsdkType == IMSDKType.NETEASE_IM) {
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
            iMessageBuilder = new IMessageBuilder(getMessageStrategy());
        }
        return iMessageBuilder;
    }

    /**
     * 获取用户登录信息
     *
     * @return
     */
    public static LoginInfo getLoginInfo() {
        String im_account = Preferences.getUserAccount();
        String im_token = Preferences.getUserToken();
        if (!TextUtils.isEmpty(im_account) && !TextUtils.isEmpty(im_token)) {
            NeteaseCache.setAccount(im_account);
            return new LoginInfo(im_account, im_token);
        } else {
            return null;
        }
    }

    public static Context getContext() {
        return mContext;
    }
}
