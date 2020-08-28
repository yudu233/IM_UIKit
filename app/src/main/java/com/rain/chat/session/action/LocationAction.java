package com.rain.chat.session.action;

import android.util.Log;

import com.rain.chat.R;
import com.rain.chat.session.module.CustomerBaseAction;
import com.rain.inputpanel.action.BaseAction;

/**
 * @Author : Rain
 * @CreateDate : 2020/7/7 18:40
 * @Version : 1.0
 * @Descroption : 位置消息功能按钮
 */
public class LocationAction extends CustomerBaseAction {

    public LocationAction() {
        super(R.drawable.action_location_selector, R.string.input_panel_location);
    }

    @Override
    public void onClick() {
        Log.e(TAG, "onClick: " + "位置");

    }
}
