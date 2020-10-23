package com.rain.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.rain.chat.adapter.ConversationAdapter;
import com.rain.chat.base.NimHelper;
import com.rain.messagelist.message.SessionType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 17:53
 * @Version : 1.0
 * @Descroption :
 */
public class ConversationFragment extends Fragment {

    private static final String TAG = "ConversationFragment";
    private RecyclerView mRecyclerView;
    private ConversationAdapter adapter;

    public static Fragment newInstance() {
        return new ConversationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
        NimHelper.getIMessageBuilder().createEmptyMessage("222", SessionType.P2P,0);
    }

    private void initListener() {
        //  注册/注销观察者
        NIMClient.getService(MsgServiceObserve.class)
                .observeRecentContact(new Observer<List<RecentContact>>() {
                    @Override
                    public void onEvent(List<RecentContact> recentContacts) {
                        int index;
                        for (RecentContact recentContact : recentContacts) {

                            index = -1;
                            for (int i = 0; i < data.size(); i++) {
                                if (recentContact.getContactId().equals(data.get(i).getContactId())) {
                                    index = i;
                                }
                            }
                            if (index >= 0) {
                                data.set(index, recentContact);
                            } else {
                                data.add(recentContact);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, true);
    }

    private List<RecentContact> data;

    private void initView() {
        adapter = new ConversationAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        data = new ArrayList<>();
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        if (code != ResponseCode.RES_SUCCESS || recents == null) {
                            return;
                        }
                        data.addAll(recents);
                        adapter.setNewData(data);
                    }
                });
    }
}
