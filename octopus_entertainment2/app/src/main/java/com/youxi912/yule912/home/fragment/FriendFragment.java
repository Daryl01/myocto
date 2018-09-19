package com.youxi912.yule912.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.team.model.CreateTeamResult;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialog;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.contact.activity.AddFriendActivity;
import com.youxi912.yule912.home.AdvancedTeamSearchActivity;
import com.youxi912.yule912.home.fragment.fragment_friend.ContactListFragment;
import com.youxi912.yule912.home.fragment.fragment_friend.DiscoverFragment;
import com.youxi912.yule912.home.fragment.fragment_friend.MessageFragment;
import com.youxi912.yule912.live.activity.EnterLiveActivity;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.session.TeamCreateHelper;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private AppCompatTextView tv1, tv2, tv3;
    private TabLayout tabLayout;
    private ViewPager viewpager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        tabLayout = view.findViewById(R.id.tab_friend);
        viewpager = view.findViewById(R.id.viewpager_friend);
        viewpager.setOffscreenPageLimit(3);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.size();
            }
        };

        view.findViewById(R.id.icon_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLoginModel.DataBean dataBean = SpUtil.getInstance(getContext()).getObject(Constant.USER_INFO);
                if ("5".equals(dataBean.getMemberOrder())) {
                    selectFunc();
                } else {
                    RxToast.showToast("该功能只对会员开放");
                }
            }
        });
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewpager);
        setTabs();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tv1.setTextColor(Color.WHITE);
                    tv1.setBackgroundResource(R.drawable.tab_selected);
                } else if (tab.getPosition() == 1) {
                    tv3.setBackgroundResource(R.drawable.tab_selected);
                    tv3.setTextColor(Color.WHITE);
                } else if (tab.getPosition() == 2) {
                    tv2.setBackgroundResource(R.drawable.tab_selected);
                    tv2.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tv1.setTextColor(Color.parseColor("#B2B2B2"));
                    tv1.setBackgroundColor(Color.parseColor("#f5f5f5"));
                } else if (tab.getPosition() == 1) {
                    tv3.setTextColor(Color.parseColor("#B2B2B2"));
                    tv3.setBackgroundColor(Color.parseColor("#f5f5f5"));
                } else if (tab.getPosition() == 2) {
                    tv2.setTextColor(Color.parseColor("#B2B2B2"));
                    tv2.setBackgroundColor(Color.parseColor("#f5f5f5"));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private void selectFunc() {
        final RxDialog sexDialog = new RxDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pick_friend_add, null, false);

        AppCompatTextView item1 = view.findViewById(R.id.tv_add_friend); //添加好友  tv_fun2
        AppCompatTextView item2 = view.findViewById(R.id.tv_create_advanced_group);//创建高级群  tv_fun4
        AppCompatTextView item3 = view.findViewById(R.id.tv_create_talk_group);//创建讨论组   tv_fun5
        AppCompatTextView item4 = view.findViewById(R.id.tv_search_advanced_group);//搜索高级群 tv_fun6
        AppCompatTextView item5 = view.findViewById(R.id.tv_goto_live_telecast);//前往直播   tv_fun1
        AppCompatTextView cancel = view.findViewById(R.id.tv_cancel); //取消


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexDialog.dismiss();
            }
        });
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexDialog.dismiss();
                startActivity(new Intent(getContext(), AddFriendActivity.class));
            }
        });

		//创建高级群
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactSelectActivity.Option advancedOption = TeamHelper.getCreateContactSelectOption(null, 50);
                NimUIKit.startContactSelector(getContext(), advancedOption, 2);
            }
        });
		//创建讨论组
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactSelectActivity.Option option = TeamHelper.getCreateContactSelectOption(null, 50);
                NimUIKit.startContactSelector(getContext(), option, 3);
            }
        });
		//创建群聊
		item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdvancedTeamSearchActivity.start(getContext());
            }
        });
		//前往直播
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexDialog.dismiss();
                startActivity(new Intent(getContext(), EnterLiveActivity.class));
            }
        });
        sexDialog.setContentView(view);
        WindowManager.LayoutParams params = sexDialog.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        sexDialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles.add("消息");
        titles.add("聊天室");
        titles.add("联系人");
        fragments.add(new MessageFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new ContactListFragment());
    }


    private void createTeamMsg() {
        ArrayList<String> memberAccounts = new ArrayList<>();
        memberAccounts.add(SpUtil.getInstance(getContext()).getString(Constant.ACCOUNT));
        ContactSelectActivity.Option option = TeamHelper.getCreateContactSelectOption(memberAccounts, 50);
        NimUIKit.startContactSelector(getContext(), option, 1);// 创建群
    }

    private void setTabs() {
        View customView1 = LayoutInflater.from(getContext()).inflate(R.layout.tab_custome_game, tabLayout, false);
        tv1 = customView1.findViewById(R.id.tab_game_item_text);
        tv1.setText("消息");
        tv1.setTextColor(Color.WHITE);
        tv1.setBackgroundResource(R.drawable.tab_selected);
        View customView2 = LayoutInflater.from(getContext()).inflate(R.layout.tab_custome_game, tabLayout, false);
        tv2 = customView2.findViewById(R.id.tab_game_item_text);
        tv2.setText("联系人");
        tv2.setTextColor(Color.parseColor("#B2B2B2"));
        View customView3 = LayoutInflater.from(getContext()).inflate(R.layout.tab_custome_game, tabLayout, false);
        tv3 = customView3.findViewById(R.id.tab_game_item_text);
        tv3.setText("聊天室");
        tv3.setTextColor(Color.parseColor("#B2B2B2"));
        tabLayout.getTabAt(0).setCustomView(customView1);
        tabLayout.getTabAt(2).setCustomView(customView2);
        tabLayout.getTabAt(1).setCustomView(customView3);
    }
}
