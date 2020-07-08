package com.rain.inputpanel.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.rain.inputpanel.R;
import com.rain.inputpanel.adapter.ActionsPagerAdapter;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/7/7 19:04
 * @Description : 更多操作模块
 */
public class ActionsPanel extends RelativeLayout {

    protected View view;


    public ActionsPanel(Context context) {
        this(context,null);
    }

    public ActionsPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_message_actions, this);
        init();
    }

    // 初始化更多布局adapter
    private void init() {
        final ViewPager viewPager = view.findViewById(R.id.viewPager);
        final ViewGroup indicator = view.findViewById(R.id.actions_page_indicator);

        ActionsPagerAdapter adapter = new ActionsPagerAdapter(viewPager,
                ActionController.getInstance().getActions());
        viewPager.setAdapter(adapter);
        initPageListener(indicator, adapter.getCount(), viewPager);
    }

    // 初始化更多布局PageListener
    private static void initPageListener(final ViewGroup indicator, final int count, final ViewPager viewPager) {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                setIndicator(indicator, count, position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setIndicator(indicator, count, 0);
    }

    /**
     * 设置页码
     */
    private static void setIndicator(ViewGroup indicator, int total, int current) {
        if (total <= 1) {
            indicator.removeAllViews();
        } else {
            indicator.removeAllViews();
            for (int i = 0; i < total; i++) {
                ImageView imgCur = new ImageView(indicator.getContext());
                imgCur.setId(i);
                // 判断当前页码来更新
                if (i == current) {
                    imgCur.setBackgroundResource(R.drawable.icon_moon_page_selected);
                } else {
                    imgCur.setBackgroundResource(R.drawable.icon_moon_page_unselected);
                }

                indicator.addView(imgCur);
            }
        }
    }
}
