package com.rain.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.rain.chat.base.NimUIKit;
import com.rain.chat.config.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 17:35
 * @Version : 1.0
 * @Descroption :
 */
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private String[] titles = {"会话", "通讯录"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        NimUIKit.getUserInfoProvider();
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ConversationFragment.newInstance());
        fragments.add(ContactListFragment.newInstance());

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(titles[position])).attach();


        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                (Observer<StatusCode>) status -> {
                    if (status.wontAutoLogin()) {
                        // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                        Toast.makeText(HomeActivity.this, "账号被挤掉线", Toast.LENGTH_SHORT).show();
                        Preferences.saveUserAccount("");
                        Preferences.saveUserToken("");
                        startActivity(new Intent(HomeActivity.this,WelcomeActivity.class));
                        finish();
                    }
                }, true);
    }
}
