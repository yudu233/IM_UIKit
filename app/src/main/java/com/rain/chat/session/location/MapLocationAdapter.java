package com.rain.chat.session.location;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.chat.R;

/**
 * @author : duyu
 * @date : 2019/1/24 11:02
 * @filename : MapLocationAdapter
 * @describe :
 */
public class MapLocationAdapter extends BaseQuickAdapter<LocationInfo, BaseViewHolder> {

    private int selected = 0;

    public MapLocationAdapter() {
        super(R.layout.nim_location_list_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LocationInfo item) {
        helper.setVisible(R.id.radioButton, selected == helper.getAdapterPosition());
        helper.setText(R.id.txv_address, item.getName());
        helper.setText(R.id.txv_addressDetails, item.getAddress());
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
