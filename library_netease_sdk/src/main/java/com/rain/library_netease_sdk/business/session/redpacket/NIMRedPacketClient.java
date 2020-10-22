package com.rain.library_netease_sdk.business.session.redpacket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.Team;
import com.rain.library_netease_sdk.NimUIKit;

import java.util.HashMap;
import java.util.Map;


/**
 * @author : duyu
 * @date : 2019/2/18 15:13
 * @filename : NIMRedPacketClient
 * @describe : 天下乐SDK接口封装
 */
public class NIMRedPacketClient {

    public static void init() {
        RpOpenedMessageFilter.startFilter();
    }

    /**
     * 打开天下乐发送界面
     *
     * @param sessionTypeEnum 会话类型，支持单聊和群聊
     * @param targetAccount   会话对象目标 account 云信id
     */
    public static void startSendRpActivity(SessionTypeEnum sessionTypeEnum, String targetAccount) {
        if (!checkValid()) {
            return;
        }
        if (sessionTypeEnum == SessionTypeEnum.Team) {   //群聊天下乐
            // 调用群聊天下乐接口
            Team team = NimUIKit.getTeamProvider().getTeamById(targetAccount);
            int count = team == null ? 0 : team.getMemberCount();
            //  RouterCenter.toGroupRedPacketPage(String.valueOf(count));
        } else {     //单聊天下乐
            //  RouterCenter.toC2CRedPacketPage(targetAccount);
        }
    }


    /**
     * 进入天下乐详情
     *
     * @param context      上下文
     * @param redPacketId  天下乐ID
     * @param packetIsSelf 是否是自己发出的天下乐
     * @param senderAccount  发送天下乐ID
     */

    public static void startRedPacketDetails(Context context, String redPacketId, String redPacketType,
                                             boolean packetIsSelf, String senderAccount,boolean isGrabDone) {
        try {
            Class clazz = Class.forName("dazhihui.dachun.com.module_yunxin.mvp.ui.session.redpacket.RedPacketDetailsActivity");
            Intent intent = new Intent(context, clazz);
            Bundle bundle = new Bundle();
            bundle.putString("redPacketId", redPacketId);
            bundle.putString("redPacketType", redPacketType);
            bundle.putString("senderAccount", senderAccount);
            bundle.putBoolean("packetIsSelf", packetIsSelf);
            bundle.putBoolean("isGrabDone",isGrabDone);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新本地消息扩展字段
     *
     * @param message
     * @param key
     * @param values
     */
    private static Map<String, Object> hashMap = new HashMap<>();

    public static void updateMessage(IMMessage message, String key, Boolean values) {
        Map<String, Object> extension = message.getLocalExtension();
        if (extension == null)extension = new HashMap<>();
            extension.put(key, values);

        message.setLocalExtension(extension );
        NIMClient.getService(MsgService.class).updateIMMessage(message);
    }

    /**
     * 天下乐是否已领取
     *
     * @return
     */
    public static boolean isGrab(IMMessage message) {
        Map<String, Object> extension = message.getLocalExtension();
        if (extension != null && !extension.isEmpty())
            return extension.get("isGrab") != null ? (Boolean) extension.get("isGrab") : false;
        else return false;
    }

    /**
     * 天下乐是否已过期
     *
     * @return
     */
    public static boolean isLate(IMMessage message) {
        Map<String, Object> extension = message.getLocalExtension();
        if (extension != null && !extension.isEmpty())
            return extension.get("isLate") != null ? (Boolean) extension.get("isLate") : false;
        else return false;
    }

    /**
     * 天下乐是否已领取完
     *
     * @param message
     * @return
     */
    public static boolean isGrabDone(IMMessage message) {
        Map<String, Object> extension = message.getLocalExtension();
        if (extension != null && !extension.isEmpty())
            return extension.get("isGrabDone") != null ? (Boolean) extension.get("isGrabDone") : false;
        else return false;
    }

    private static boolean checkValid() {
        return NimUIKit.getUserInfoProvider().getUserInfo(NimHelper.getAccount()) != null;
    }

}
