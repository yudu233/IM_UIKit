package com.rain.library_netease_sdk.extension;

import com.google.gson.JsonObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

import org.json.JSONObject;

/**
 * @author : duyu
 * @date : 2019/1/3 15:24
 * @filename : CustomAttachment
 * @describe :
 */
public abstract class CustomAttachment implements MsgAttachment {

    protected int type;

    protected CustomAttachment(int type) {
        this.type = type;
    }

    public void fromJson(JsonObject data) {
        if (data != null) {
            parseData(data);
        }
    }

    @Override
    public String toJson(boolean send) {
        return CustomAttachParser.packData(type, packData());
    }

    public int getType() {
        return type;
    }

    protected abstract void parseData(JsonObject data);

    protected abstract JsonObject packData();
}
