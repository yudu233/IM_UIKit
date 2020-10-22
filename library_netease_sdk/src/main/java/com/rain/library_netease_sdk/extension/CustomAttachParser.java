package com.rain.library_netease_sdk.extension;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

import org.json.JSONObject;

/**
 * @author : duyu
 * @date : 2019/1/3 15:13
 * @filename : CustomAttachParser
 * @describe : 自定义消息解析器
 */
public class CustomAttachParser implements MsgAttachmentParser {
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";


    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            JsonObject data = new Gson().fromJson(json, JsonObject.class);
            int type = data.get(KEY_TYPE).getAsInt();

            switch (type) {
                case CustomAttachmentType.ADD_FRIEND:
                case CustomAttachmentType.AGREE_ADD_FRIEND:
                case CustomAttachmentType.DELETE_FRIEND:
                case CustomAttachmentType.CHANGE_FRIEND_INFO:
//                    attachment = new UserProfileAttachment();
                    break;
                case CustomAttachmentType.RECOMMEND_CARD:
//                    attachment = new RecommendCardAttachment();
                    break;
                case CustomAttachmentType.RED_PACKET:
//                    attachment = new RedPacketAttachment();
                    break;
                case CustomAttachmentType.OPEN_RED_PACKET:
                    attachment = new RedPacketOpenedAttachment();
                    break;
                case CustomAttachmentType.STICKER:
//                    attachment = new StickerAttachment();
                    break;
                case CustomAttachmentType.SHARE_ARTICLE:
                case CustomAttachmentType.SHARE_LIVE:
//                    attachment = new ShareAttachment(type);
                    break;
                case CustomAttachmentType.YUN_LIVE_ChAT:
//                    attachment = new CYCustomTextMsg();
                    break;
                case CustomAttachmentType.YUN_LIVE_GIFT:
//                    attachment = new CYLiveGiftAttachment();
                    break;
                case CustomAttachmentType.YUN_LIVE_GIFT_SCORE:
//                    attachment = new CYLivePkAttachment();
                    break;
                case CustomAttachmentType.YUN_LIVE_PK_ID:
//                    attachment = new CYLivePkIdAttachment();
                    break;
                case CustomAttachmentType.YUN_LIVE_MEMBER:
//                    attachment = new CYLiveMemberAttachment();
                    break;
                case CustomAttachmentType.YUN_LIVE_CONFIG:
//                    attachment = new CYLiveConfigAttachment();
                    break;
                case CustomAttachmentType.YUN_LIVE_USER_MUTE:
//                    attachment = new CYLiveUserMuteTextMsg();
                    break;
                case CustomAttachmentType.YUN_LIVE_USER_ADMIN:
//                    attachment = new CYLiveUserAdminTextMsg();
                    break;
                case CustomAttachmentType.SHARE_LESSON_LESSON:
//                    attachment = new LessonAttachment(type);
                    break;
                case CustomAttachmentType.SHARE_LESSON_GAME:
//                    attachment = new GameAttachment(type);
                    break;
                case CustomAttachmentType.SHARE_LESSON_UNICOM:
//                    attachment = new GameAttachment(type);
                    break;
                case CustomAttachmentType.SHARE_SHOP_UNICOM:
//                    attachment = new ShopAttachment(type);
                    break;
                case CustomAttachmentType.SHARE_WORLD_UNICOM:
//                    attachment = new WorldAttachment(type);
                    break;
                default:
//                    attachment = new DefaultCustomAttachment();
                    break;
            }

            if (attachment != null) {
                attachment.fromJson(data);
            }
        } catch (Exception e) {

        }

        return attachment;
    }

    @SuppressLint("LongLogTag")
    public static String packData(int type, JsonObject data) {
        if (type == CustomAttachmentType.YUN_LIVE_ChAT || type == CustomAttachmentType.YUN_LIVE_GIFT || type == CustomAttachmentType.YUN_LIVE_GIFT_SCORE
                || type == CustomAttachmentType.YUN_LIVE_PK_ID || type == CustomAttachmentType.YUN_LIVE_MEMBER || type == CustomAttachmentType.YUN_LIVE_CONFIG
                || type == CustomAttachmentType.YUN_LIVE_USER_MUTE || type == CustomAttachmentType.YUN_LIVE_USER_ADMIN) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(KEY_TYPE, type);
            jsonObject.add(KEY_DATA, data);
            return jsonObject.toString();
        } else {
            return data.toString();
        }
    }
}
