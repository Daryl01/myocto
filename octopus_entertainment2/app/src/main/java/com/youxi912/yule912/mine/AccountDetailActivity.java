package com.youxi912.yule912.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.R;
import com.youxi912.yule912.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class AccountDetailActivity extends BaseActivity {
    private String[] titles = new String[]{"我的章鱼丸", "邀请明细", "章鱼丸明细"};
    private MyPsFragment fragment1;
    private InviteDetailFragment fragment2;
    private PsDetailFragment fragment3;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_detail;
    }

    @Override
    public void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_account_detail);
        ViewPager viewPager = findViewById(R.id.viewpager_account_detail);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.img_back_account_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        fragment1 = new MyPsFragment();
        fragment2 = new InviteDetailFragment();
        fragment3 = new PsDetailFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }
}
