package com.rain.chat.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.rain.chat.R;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 18:10
 * @Version : 1.0
 * @Descroption :
 */
public class ContactAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ContactAdapter(@Nullable List<String> data) {
        super(R.layout.item_contact,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.txv_name, item);
    }
}
