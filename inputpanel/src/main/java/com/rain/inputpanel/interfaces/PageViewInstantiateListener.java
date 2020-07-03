package com.rain.inputpanel.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.rain.inputpanel.data.PageEntity;


public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
