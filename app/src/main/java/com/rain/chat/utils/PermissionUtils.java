package com.rain.chat.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rain.crow.utils.Rlog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/13 16:19
 * @Version : 1.0
 * @Descroption : 权限申请
 * 介绍
 * 如果设备运行的是 Android 6.0（API 级别 23）或更高版本，并且应用的 targetSdkVersion 是 23 或更高版本，则应用在运行时向用户请求权限。
 * 如果设备运行的是 Android 5.1（API 级别 22）或更低版本，并且应用的 targetSdkVersion 是 22 或更低版本，则系统会在用户安装应用时要求用户授予权限。
 * 以下是需要单独申请的权限,共分为9组,每组只要有一个权限申请成功了,就默认整组权限都可以使用了
 * 1、关于日历的权限：
 * <uses-permission android:name="android.permission.READ_CALENDAR"/>
 * <uses-permission android:name="android.permission.WRITE_CALENDAR"/>
 * 2、关于相机的权限：
 * <uses-permission android:name="android.permission.CAMERA"/>
 * 3、关于联系人的权限：
 * <uses-permission android:name="android.permission.READ_CONTACTS"/>
 * <uses-permission android:name="android.permission.WRITE_CONTACTS"/>
 * <uses-permission android:name="android.permission.GET_CONTACTS"/>
 * 4、关于位置的权限：
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 * 5、关于电话的权限：
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * <uses-permission android:name="android.permission.CALL_PHONE"/>
 * <uses-permission android:name="android.permission.READ_CALL_LOG"/>
 * <uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
 * <uses-permission android:name="android.permission.USE_SIP"/>
 * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
 * 6、关于传感器的权限：
 * <uses-permission android:name="android.permission.BODY_SENSORS"/>
 * 7、关于短信的权限：
 * <uses-permission android:name="android.permission.SEND_SMS"/>
 * <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 * <uses-permission android:name="android.permission.READ_SMS"/>
 * <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>
 * <uses-permission android:name="android.permission.RECEIVE_MMS"/>
 * <uses-permission android:name="android.permission.READ_CELL_BROADCASTS"/>
 * 8、关于SD卡的权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * 9、关于录音的权限
 * <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 */
public class PermissionUtils {

    public static final String TAG = "Permission";


    private PermissionUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }


    public interface RequestPermission {
        /**
         * 权限请求成功
         */
        void onRequestPermissionSuccess();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailure(List<String> permissions);

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions);
    }

    public static void requestPermission(final RequestPermission requestPermission, RxPermissions rxPermissions, String... permissions) {
        if (permissions == null || permissions.length == 0) return;

        List<String> needRequest = new ArrayList<>();
        for (String permission : permissions) { //过滤调已经申请过的权限
            if (!rxPermissions.isGranted(permission)) {
                needRequest.add(permission);
            }
        }

        if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过,则开始申请
            rxPermissions
                    .requestEach(needRequest.toArray(new String[needRequest.size()]))
                    .buffer(permissions.length)
                    .subscribe(new RxErrorHandleSubscriber<List<Permission>>() {
                        @Override
                        public void onNext(List<Permission> permissions) {
                            List<String> failurePermissions = new ArrayList<>();
                            List<String> askNeverAgainPermissions = new ArrayList<>();
                            for (Permission p : permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        failurePermissions.add(p.name);
                                    } else {
                                        askNeverAgainPermissions.add(p.name);
                                    }
                                }
                            }
                            if (failurePermissions.size() > 0) {
                                Rlog.d(TAG, "Request permissions failure");
                                requestPermission.onRequestPermissionFailure(failurePermissions);
                            }

                            if (askNeverAgainPermissions.size() > 0) {
                                Rlog.d(TAG, "Request permissions failure with ask never again");
                                requestPermission.onRequestPermissionFailureWithAskNeverAgain(askNeverAgainPermissions);
                            }

                            if (failurePermissions.size() == 0 && askNeverAgainPermissions.size() == 0) {
                                Rlog.d(TAG, "Request permissions success");
                                requestPermission.onRequestPermissionSuccess();
                            }
                        }
                    });

        }
    }

    /**
     * 请求摄像头权限
     */
    public static void launchCamera(RequestPermission requestPermission, RxPermissions
            rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(RequestPermission requestPermission, RxPermissions
            rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 请求发送短信权限
     */
    public static void sendSms(RequestPermission requestPermission, RxPermissions
            rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.SEND_SMS);
    }

    /**
     * 请求打电话权限
     */
    public static void callPhone(RequestPermission requestPermission, RxPermissions
            rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.CALL_PHONE);
    }

    /**
     * 请求获取手机状态的权限
     */
    public static void readPhoneState(RequestPermission requestPermission, RxPermissions
            rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.READ_PHONE_STATE);
    }


    /**
     * 显示提示对话框(这个提示款适用于点击按钮请求权限的问题)
     * 如果需要强制获取权限，需要自定义对话框
     */
    public static void showTipsDialog(Activity context) {
        new MaterialDialog.Builder(context)
                .title("提示信息")
                .content("前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .negativeText("取消")
                .positiveText("设置")
                .onNegative((dialog, which) -> dialog.dismiss())
                .onPositive((dialog, which) -> startAppSettings(context)).show();
    }


    /**
     * 启动当前应用设置页面
     */
    private static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
