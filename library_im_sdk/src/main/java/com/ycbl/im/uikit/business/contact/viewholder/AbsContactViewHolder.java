package com.ycbl.im.uikit.business.contact.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ycbl.im.uikit.business.contact.item.AbsContactItem;

public abstract class AbsContactViewHolder<T extends AbsContactItem> {
    protected View view;

    protected Context context;

    public AbsContactViewHolder() {

    }

    // TODO: 2020/5/15  ContactDataAdapter没有
//    public abstract void refresh(ContactDataAdapter adapter, int position, T item);

    public abstract View inflate(LayoutInflater inflater);

    public final View getView() {
        return view;
    }

    public void create(Context context) {
        this.context = context;
        this.view = inflate(LayoutInflater.from(context));
    }
}