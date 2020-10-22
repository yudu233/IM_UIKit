package com.rain.chat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.rain.crow.utils.Rlog;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 10:05
 * @Version : 1.0
 * @Descroption :
 */
public class WelcomeActivity extends AppCompatActivity {


    private static final String TAG = "WelcomeActivity";
    private Intent serviceIntent;


    //客户端的Messenger
    private Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 2) {
                //服务端发来的消息
                Bundle bundle = msg.getData();
                String title = (String) bundle.get("title");
                boolean isLogin = (Boolean) bundle.get("isLogin");

                Rlog.e(title + "---" + isLogin);
            }
        }
    });

    //服务端传来的Messenger
    private Messenger serverMessenger;
    private boolean isConnected = false;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Rlog.e("登录服务连接成功...");
            isConnected= true;
            serverMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Rlog.e("onServiceDisconnected");
            isConnected = false;
            serverMessenger = null;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
//
//        serviceIntent = new Intent(this, MessageService.class);
//        startService(serviceIntent);

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode>() {
                    public void onEvent(StatusCode status) {
                        //获取状态的描述
                        String desc = status.getDesc();
                        if (status.wontAutoLogin()) {
                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                        }
                    }
                }, true);
        if (canAutoLogin()) {
            startActivity(new Intent(this, HomeActivity.class));
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
                        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                        finish();
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


        serviceIntent = new Intent();
        serviceIntent.setAction("ycbl.cytx.login.MessengerService");
        serviceIntent.setPackage("ycbl.cytx.login");
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            Rlog.e(isConnected + "--------isConnected------");
            if (isConnected){
                sendMessage();
            }else {
                bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
                if (isConnected){
                    sendMessage();
                }
            }
        });
    }

    private void sendMessage() {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("message", "我要登录！ -> 客户端发出的消息");
        message.setData(bundle);
        message.what = 0;
        message.replyTo = clientMessenger;
        try {
            serverMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
//            intent1.setData(Uri.parse("login://com.rain.loginapp/login"));
        intent1.setData(Uri.parse("login://ycbl.cytx.login/login"));
        startActivity(intent1);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}
