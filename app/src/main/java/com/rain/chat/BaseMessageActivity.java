package com.rain.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.rain.chat.constant.Extras;

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
}
