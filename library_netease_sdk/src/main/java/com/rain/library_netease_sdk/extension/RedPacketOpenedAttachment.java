package com.rain.library_netease_sdk.extension;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.rain.library_netease_sdk.NimUIKit;
import com.rain.library_netease_sdk.business.team.TeamHelper;
import com.rain.library_netease_sdk.business.user.UserInfoHelper;

/**
 * @author : duyu
 * @date : 2019/2/20 11:07
 * @filename : RedPacketOpenedAttachment
 * @describe : 拆天下乐自定义消息
 */
public class RedPacketOpenedAttachment extends CustomAttachment {
    public int type;
    private String sendAccount;     //发送天下乐ID
    private String openAccount;     //打开天下乐ID
    private String redPacketId;     //天下乐ID
    private String redPacketType;   //天下乐类型
    private boolean isGetDone;      //是否被领完

    private static final String KEY_TYPE = "type";
    private static final String KEY_SEND = "sendPacketId";
    private static final String KEY_OPEN = "openPacketId";
    private static final String KEY_RP_ID = "packetId";
    private static final String KEY_RP_TYPE = "packetType";
    private static final String KEY_DONE = "isGetDone";

    public RedPacketOpenedAttachment() {
        super(CustomAttachmentType.OPEN_RED_PACKET);
    }

    public String getSendNickName(SessionTypeEnum sessionTypeEnum, String targetId) {
        if (TextUtils.isEmpty(sendAccount) || TextUtils.isEmpty(openAccount)) return "";
        if (NimUIKit.getAccount().equals(sendAccount) && NimUIKit.getAccount().equals(openAccount)) {
            return "自己";
        }
        return getDisplayName(sessionTypeEnum, targetId, sendAccount);
    }

    public String getOpenNickName(SessionTypeEnum sessionTypeEnum, String targetId) {
        if (TextUtils.isEmpty(targetId) || TextUtils.isEmpty(openAccount)) return "";
        return getDisplayName(sessionTypeEnum, targetId, openAccount);
    }

    // 我发的天下乐或者是我打开的天下乐
    public boolean belongTo(String account) {
        if (openAccount == null || sendAccount == null || account == null) {
            return false;
        }
        return openAccount.equals(account) || sendAccount.equals(account);
    }

    private String getDisplayName(SessionTypeEnum sessionTypeEnum, String targetId, String account) {
        if (sessionTypeEnum == SessionTypeEnum.Team) {
            return TeamHelper.getTeamMemberDisplayNameYou(targetId, account);
        } else if (sessionTypeEnum == SessionTypeEnum.P2P) {
            return UserInfoHelper.getUserDisplayNameEx(account, "你");
        } else {
            return "";
        }
    }

    public String getDesc(SessionTypeEnum sessionTypeEnum, String targetId) {
        String sender = getSendNickName(sessionTypeEnum, targetId);
        String opened = getOpenNickName(sessionTypeEnum, targetId);
        return String.format("%s领取了%s的天下乐", opened, sender);
    }

    public String getSendAccount() {
        return sendAccount;
    }

    private void setSendAccount(String sendAccount) {
        this.sendAccount = sendAccount;
    }

    public String getOpenAccount() {
        return openAccount;
    }

    private void setOpenAccount(String openAccount) {
        this.openAccount = openAccount;
    }

    public String getRedPacketId() {
        return redPacketId;
    }

    private void setRedPacketId(String redPacketId) {
        this.redPacketId = redPacketId;
    }

    public String getRedPacketType() {
        return redPacketType;
    }

    public void setRedPacketType(String redPacketType) {
        this.redPacketType = redPacketType;
    }

    public boolean isRpGetDone() {
        return isGetDone;
    }

    private void setIsGetDone(boolean isGetDone) {
        this.isGetDone = isGetDone;
    }

    private void setType(int type) {
        this.type = type;
    }

    @Override
    protected void parseData(JsonObject data) {
        this.type = data.get(KEY_TYPE).getAsInt();
        this.sendAccount = data.get(KEY_SEND).getAsString();
        this.openAccount = data.get(KEY_OPEN).getAsString();
        this.redPacketId = data.get(KEY_RP_ID).getAsString();
        this.redPacketType = data.get(KEY_RP_TYPE).getAsString();
        this.isGetDone = data.get(KEY_DONE).getAsBoolean();
    }

    @Override
    protected JsonObject packData() {
        JsonObject data = new JsonObject();
        data.addProperty(KEY_TYPE, this.type);
        data.addProperty(KEY_SEND, this.sendAccount);
        data.addProperty(KEY_OPEN, this.openAccount);
        data.addProperty(KEY_RP_ID, this.redPacketId);
        data.addProperty(KEY_RP_TYPE, this.redPacketType);
        data.addProperty(KEY_DONE, this.isGetDone);
        return data;
    }

    public static RedPacketOpenedAttachment obtain(String sendPacketId, String openPacketId, String packetType, String packetId, boolean isGetDone) {
        RedPacketOpenedAttachment model = new RedPacketOpenedAttachment();
        model.setType(CustomAttachmentType.OPEN_RED_PACKET);
        model.setRedPacketId(packetId);
        model.setRedPacketType(packetType);
        model.setSendAccount(sendPacketId);
        model.setOpenAccount(openPacketId);
        model.setIsGetDone(isGetDone);
        return model;
    }
}
