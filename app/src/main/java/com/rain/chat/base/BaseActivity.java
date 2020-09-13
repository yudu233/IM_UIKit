package com.rain.chat.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.KeyboardUtils;
import com.rain.chat.R;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 10:49
 * @Version : 1.0
 * @Descroption :
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private AppCompatTextView mTitle, backTextView;
    private AppCompatImageView mBackView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置Toolbar标题
     *
     * @param title
     */
    protected void setToolbarTitle(String title, boolean goBack) {
        setToolbarTitle(title, goBack, "");
    }

    protected void setToolbarTitle(String title, boolean goBack, String backText) {
        mToolBar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.txv_title);
        mBackView = findViewById(R.id.imv_back);
        backTextView = findViewById(R.id.tv_back);
        mToolBar.setTitle("");
        mTitle.setText(title);
        setSupportActionBar(mToolBar);
        if (goBack) {
            mBackView.setVisibility(View.VISIBLE);
        }
        if (!backText.isEmpty()) {
            mBackView.setVisibility(View.VISIBLE);
            backTextView.setText(backText);
        }
        mBackView.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            finish();
        });
    }

    protected Toolbar getToolBar() {
        return mToolBar;
    }
}
