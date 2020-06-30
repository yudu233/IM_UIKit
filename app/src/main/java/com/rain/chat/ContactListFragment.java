package com.rain.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.rain.chat.adapter.ContactAdapter;
import com.rain.chat.constant.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 17:54
 * @Version : 1.0
 * @Descroption :
 */
public class ContactListFragment extends Fragment {
    private static final String TAG = "ContactListFragment";
    private RecyclerView mRecyclerView;
    private ContactAdapter adapter;


    public static Fragment newInstance() {
        return new ContactListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private List<String> data;

    private void initView() {
        adapter = new ContactAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        data = new ArrayList<>();
        List<String> friends = NIMClient.getService(FriendService.class).getFriendAccounts();
        data.addAll(friends);
        adapter.setNewData(data);


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(getActivity(), P2PMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Extras.EXTRA_ACCOUNT, data.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
