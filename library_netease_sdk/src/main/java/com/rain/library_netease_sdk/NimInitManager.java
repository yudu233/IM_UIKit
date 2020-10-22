package com.rain.library_netease_sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimStrings;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.BroadcastMessage;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.model.IMMessageFilter;
import com.netease.nimlib.sdk.team.model.UpdateTeamAttachment;
import com.rain.library_netease_sdk.config.preference.NeteaseUserPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * @author : duyu
 * @date : 2019/1/3 17:36
 * @filename : NimInitManager
 * @describe : 用于初始化时，注册全局的广播、云信观察者等等云信相关业务
 */
public class NimInitManager {
    private static final String TAG = "NIMInitManager";

    private NimInitManager() {
    }

    private static class InstanceHolder {
        static NimInitManager receivers = new NimInitManager();
    }

    public static NimInitManager getInstance() {
        return InstanceHolder.receivers;
    }

    public void init(boolean register) {
        // 注册通知消息过滤器
        registerIMMessageFilter();

        // 注册语言变化监听广播
        //registerLocaleReceiver(register);

        // 注册全局云信sdk 观察者
        registerGlobalObservers(register);

        // 初始化在线状态事件
        // OnlineStateEventManager.init();
    }

    private void registerGlobalObservers(boolean register) {
        // 注册云信全员广播
        registerBroadcastMessages(register);
    }

    private void registerLocaleReceiver(boolean register) {
        if (register) {
            updateLocale();
            IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
            NimUIKit.getContext().registerReceiver(localeReceiver, filter);
        } else {
            NimUIKit.getContext().unregisterReceiver(localeReceiver);
        }
    }

    private BroadcastReceiver localeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
                updateLocale();
            }
        }
    };

    private void updateLocale() {
        Context context = NimUIKit.getContext();
        NimStrings strings = new NimStrings();
        strings.status_bar_multi_messages_incoming = context.getString(R.string.im_nim_status_bar_multi_messages_incoming);
        strings.status_bar_image_message = context.getString(R.string.im_nim_status_bar_image_message);
        strings.status_bar_audio_message = context.getString(R.string.im_nim_status_bar_audio_message);
        strings.status_bar_custom_message = context.getString(R.string.im_nim_status_bar_custom_message);
        strings.status_bar_file_message = context.getString(R.string.im_nim_status_bar_file_message);
        strings.status_bar_location_message = context.getString(R.string.im_nim_status_bar_location_message);
        strings.status_bar_notification_message = context.getString(R.string.im_nim_status_bar_notification_message);
        strings.status_bar_ticker_text = context.getString(R.string.im_nim_status_bar_ticker_text);
        strings.status_bar_unsupported_message = context.getString(R.string.im_nim_status_bar_unsupported_message);
        strings.status_bar_video_message = context.getString(R.string.im_nim_status_bar_video_message);
        strings.status_bar_hidden_message_content = context.getString(R.string.im_nim_status_bar_hidden_msg_content);
        NIMClient.updateStrings(strings);
    }

    /**
     * 通知消息过滤器（如果过滤则该消息不存储不上报）
     */
    private void registerIMMessageFilter() {
        NIMClient.getService(MsgService.class).registerIMMessageFilter(new IMMessageFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                if (NeteaseUserPreferences.getMsgIgnore() && message.getAttachment() != null) {
                    if (message.getAttachment() instanceof UpdateTeamAttachment) {
                        UpdateTeamAttachment attachment = (UpdateTeamAttachment) message.getAttachment();
                        for (Map.Entry<TeamFieldEnum, Object> field : attachment.getUpdatedFields().entrySet()) {
                            if (field.getKey() == TeamFieldEnum.ICON) {
                                return true;
                            }
                        }
                    }
                    // TODO: 2020/10/9 音视频通话类的消息过滤
//                    else return message.getAttachment() instanceof AVChatAttachment;
                }
                return false;
            }
        });
    }

    /**
     * 注册云信全服广播接收器
     *
     * @param register
     */
    private List<BroadcastMessage> broadcastMessages = new ArrayList<>();

    public void registerBroadcastMessages(boolean register) {
        NIMClient.getService(MsgServiceObserve.class)
                .observeBroadcastMessage(new Observer<BroadcastMessage>() {
                    @Override
                    public void onEvent(BroadcastMessage broadcastMessage) {

                    }
                }, register);
    }

    private void showDialog() {
        Collections.sort(broadcastMessages, new Comparator<BroadcastMessage>() {
            @Override
            public int compare(BroadcastMessage o1, BroadcastMessage o2) {
                return o1.getTime() - o2.getTime() > 0 ? 1 : -1;
            }
        });
    }

    class CustomerBroadcastMessage extends BroadcastMessage implements Comparator<CustomerBroadcastMessage> {

        @Override
        public int compare(CustomerBroadcastMessage o1, CustomerBroadcastMessage o2) {
            return o1.getTime() - o2.getTime() > 0 ? 1 : 0;
        }
    }
}
