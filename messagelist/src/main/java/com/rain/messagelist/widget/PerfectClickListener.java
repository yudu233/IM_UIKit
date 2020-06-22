package com.rain.messagelist.widget;

import android.view.View;

import java.util.Calendar;

/**
 * @author : duyu
 * @date : 2019/2/27 20:11
 * @filename : PerfectClickListener
 * @describe : 防止双击
 */
public abstract class PerfectClickListener implements View.OnClickListener {

    private static final String TAG = "PerfectClickListener";


    public static final int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;
    private int id = -1;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int mId = view.getId();
        if (id != mId) {
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(view);
            return;
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(view);
        }
    }

    protected abstract void onNoDoubleClick(View v);

}
