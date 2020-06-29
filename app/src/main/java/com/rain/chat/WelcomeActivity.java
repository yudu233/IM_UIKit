package com.rain.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.rain.chat.config.Preferences;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 10:05
 * @Version : 1.0
 * @Descroption :
 */
public class WelcomeActivity extends AppCompatActivity {


    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        Log.e(TAG, "onCreate: " + getPackageName());
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode>() {
                    public void onEvent(StatusCode status) {
                        Log.e(TAG, "onEvent: " + status.getValue() + status.getDesc() );
                        //获取状态的描述
                        String desc = status.getDesc();
                        if (status.wontAutoLogin()) {
                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                        }
                    }
                }, true);
        if (canAutoLogin()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            findViewById(R.id.btn_login01).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_login02).setVisibility(View.VISIBLE);
        }


        LoginInfo info1 = new LoginInfo(
                "2018cytx2".toLowerCase(), "cJEaWpV28bLqZJMCTf0Gxj5aeu0koel0",
                "25d65de84a9bfc727544792606dd1eec"
        );
        LoginInfo info2 = new LoginInfo(
                "2018cytx3".toLowerCase(), "LcMe_tYbjvew8kQSFuICsffM4Hgov1u0",
                "25d65de84a9bfc727544792606dd1eec"
        );
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        Toast.makeText(WelcomeActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        Preferences.saveUserAccount(param.getAccount());
                        Preferences.saveUserToken(param.getToken());
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.e(TAG, "onFailed: " + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                };

        findViewById(R.id.btn_login01).setOnClickListener(v ->
                NIMClient.getService(AuthService.class).login(info1)
                        .setCallback(callback));

        findViewById(R.id.btn_login02).setOnClickListener(v ->
                NIMClient.getService(AuthService.class).login(info2)
                        .setCallback(callback));
    }


    /**
     * 已经登陆过，自动登陆
     */
    private boolean canAutoLogin() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        Log.i(TAG, "get local sdk token =" + token);
        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
    }

}
