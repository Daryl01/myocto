package com.youxi912.yule912.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.wallet.fragment.TransOutFragment;
import com.youxi912.yule912.wallet.fragment.OtherFragment;

import java.util.ArrayList;
import java.util.List;

public class CurrencyHistoryActivity extends BaseActivity {
    private String[] titles = new String[]{"转出记录", "转入记录"};
    private String[] anotherTitle = new String[]{"转出记录", "兑换记录"};
    private String param;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_currency_history;
    }

    @Override
    public void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_currency_detail);
        ViewPager viewPager = findViewById(R.id.viewpager_currency_detail);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if ("PS".equals(param)){
                    return titles[position];
                }else {
                    return anotherTitle[position];
                }
            }
        });
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.img_back_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        param = getIntent().getStringExtra(Constant.CURRENCY_NAME);
        fragments.add(TransOutFragment.newInstance(param));
        fragments.add(OtherFragment.newInstance(param));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }
}
