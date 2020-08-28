package com.rain.chat.utils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @Author : Rain
 * @CreateDate : 2020/8/13 16:51
 * @Version : 1.0
 * @Descroption :
 */
public abstract class RxErrorHandleSubscriber <T> implements Observer<T> {


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }


    @Override
    public void onComplete() {

    }


    @Override
    public void onError(@NonNull Throwable t) {
        t.printStackTrace();
    }
}

