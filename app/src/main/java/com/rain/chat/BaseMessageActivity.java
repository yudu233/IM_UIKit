package com.rain.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.rain.chat.constant.Extras;
import com.rain.chat.weight.toolbar.ToolBarOptions;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: BaseMessageActivity
 * @CreateDate: 2020/6/29 21:25
 * @Describe:
 */
public abstract class BaseMessageActivity extends AppCompatActivity {

    //聊天对象id
    protected String sessionId;

    protected abstract MessageFragment fragment();

    protected abstract int getContentViewId();

    protected abstract void initToolBar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        parseIntent();
        initToolBar();
        addFragment();
    }

    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.message_fragment_container, fragment());
        transaction.commitAllowingStateLoss();
    }

    protected void setToolbar(ToolBarOptions options) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView titleView = findViewById(R.id.txv_title);
        if (options.titleId != 0) {
            titleView.setText(options.titleId);
        }
        if (!TextUtils.isEmpty(options.titleString)) {
            titleView.setText(options.titleString);
        }
        if (options.logoId != 0) {
            toolbar.setLogo(options.logoId);
        }
        setSupportActionBar(toolbar);

        if (options.isNeedNavigate) {
            toolbar.setNavigationIcon(options.navigateId);
            toolbar.setContentInsetStartWithNavigation(0);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void parseIntent() {
        sessionId = getIntent().getStringExtra(Extras.EXTRA_ACCOUNT);
//        NIMClient.getService(MsgService.class).clearChattingHistory(sessionId, SessionTypeEnum.P2P, true);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return isFullScreenFuncView(event) ? isFullScreenFuncView(event) : super.dispatchKeyEvent(event);

    }

    /**
     * 传递dispatchKeyEvent事件到Fragment，拦截处理返回键事件
     *
     * @param event
     * @return
     */
    abstract boolean isFullScreenFuncView(KeyEvent event);
}
