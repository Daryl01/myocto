package com.youxi912.yule912.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.InviteRecordModel;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteDetailFragment extends Fragment {
    private String[] titles = new String[]{"所有成员(0)", "直推成员(0)"};
    private List<InviteFragment> fragments = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<InviteRecordModel.DataBean.AllBean> all = new ArrayList<>();
    private ArrayList<InviteRecordModel.DataBean.AllBean> zt = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invite_detail, container, false);
        tabLayout = view.findViewById(R.id.tab_invite);
        viewPager = view.findViewById(R.id.viewpager_invite);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    if (fragments.size() == 0) {
                        InviteFragment inviteFragment = InviteFragment.newInstance(all);
                        fragments.add(inviteFragment);
                    }
                } else if (position == 1) {
                    if (fragments.size() == 1) {
                        InviteFragment inviteFragment = InviteFragment.newInstance(zt);
                        fragments.add(inviteFragment);
                    }
                }
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        getData();
        return view;
    }


    private void getData() {
        RetrofitManager.getInstance(getContext()).createReq(API.class)
                .getInviteHistory(SpUtil.getInstance(getContext()).getString(Constant.TOKEN))
                .enqueue(new Callback<InviteRecordModel>() {
                    @Override
                    public void onResponse(Call<InviteRecordModel> call, Response<InviteRecordModel> response) {
                        if (response.isSuccessful()) {
                            InviteRecordModel model = response.body();
                            if (model == null || model.getData() == null || model.getData().getAll() == null || model.getData().getAll().size() == 0) {
                                titles[0] = "所有成员(0)";
                                return;
                            } else {
                                all.addAll(model.getData().getAll());
                                titles[0] = "所有成员(" + model.getData().getAll().size() + ")";
                            }
                            if (model.getData() == null || model.getData().getZt() == null || model.getData().getZt().size() == 0) {
                                titles[1] = "直推成员(0)";
                                return;
                            } else {
                                zt.addAll(model.getData().getZt());
                                titles[1] = "直推成员(" + model.getData().getZt().size() + ")";
                            }
                            viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                                @Override
                                public Fragment getItem(int position) {
                                    if (position == 0) {
                                        if (fragments.size() == 0) {
                                            InviteFragment inviteFragment = InviteFragment.newInstance(all);
                                            fragments.add(inviteFragment);
                                        }
                                    } else if (position == 1) {
                                        if (fragments.size() == 1) {
                                            InviteFragment inviteFragment = InviteFragment.newInstance(zt);
                                            fragments.add(inviteFragment);
                                        }
                                    }
                                    return fragments.get(position);
                                }

                                @Override
                                public int getCount() {
                                    return 2;
                                }

                                @Nullable
                                @Override
                                public CharSequence getPageTitle(int position) {
                                    return titles[position];
                                }
                            });
                            viewPager.setCurrentItem(0);
                            tabLayout.setupWithViewPager(viewPager);
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<InviteRecordModel> call, Throwable t) {
                        //RxToast.error(Utils.checkNotNull(t.getMessage()));
                        //Log.e("error---", Utils.checkNotNull(t.getMessage()));
                    }
                });
    }
}
