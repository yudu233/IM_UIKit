package com.rain.library_netease_sdk;

import android.annotation.SuppressLint;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.messagelist.message.MessageType;
import com.ycbl.im.uikit.impl.MessageObserver;
import com.rain.library_netease_sdk.msg.models.DefaultUser;
import com.rain.library_netease_sdk.msg.models.MyMessage;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/2 10:59
 * @Version : 1.0
 * @Descroption : IM服务接口封装
 */
public class NimClientController {

    /**
     * 注册/注销消息接收观察者。
     * 文档：{@link MsgServiceObserve#observeReceiveMessage}
     *
     * @param register
     * @param observer
     */
    public static void registerMsgServiceObserve(MessageObserver<List<MyMessage>> observer, boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(new Observer<List<IMMessage>>() {
            @SuppressLint("CheckResult")
            @Override
            public void onEvent(List<IMMessage> imMessages) {
                Observable.fromIterable(imMessages)
                        .flatMap(new Function<IMMessage, ObservableSource<MyMessage>>() {
                            @Override
                            public ObservableSource<MyMessage> apply(IMMessage message) throws Exception {
                                MyMessage myMessage = new MyMessage(MessageType
                                        .valueOf(String.valueOf(message.getMsgType()))
                                        , message, new DefaultUser(message));
                                return Observable.just(myMessage);
                            }
                        })
                        .toList()
                        .subscribe(myMessages -> observer.onEvent(myMessages));
            }
        }, register);
    }



}
