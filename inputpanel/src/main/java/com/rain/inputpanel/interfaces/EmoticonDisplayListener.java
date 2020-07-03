package com.rain.inputpanel.interfaces;

import android.view.ViewGroup;

import com.rain.inputpanel.adapter.EmoticonsAdapter;

/**
 * use XhsEmotionsKeyboard(https://github.com/w446108264/XhsEmoticonsKeyboard)
 */
public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
