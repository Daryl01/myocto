package com.youxi912.yule912.home.fragment.fragment_game;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.GameTypeModel;
import com.youxi912.yule912.util.Utils;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类别
 */
public class TypeFragment extends Fragment {

    private List<String> datas = new ArrayList<>();
    private List<TypeListFragment> fragments = new ArrayList<>();
    private String token;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<GameTypeModel.DataBean> types = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        tabLayout = view.findViewById(R.id.tab_type);
        viewPager = view.findViewById(R.id.viewpager_type);
        viewPager.setOffscreenPageLimit(0);
        RetrofitManager.getInstance(getContext()).createReq(API.class)
                .getGameTypes(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GameTypeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("type--fragment", Utils.checkNotNull(e.getMessage()));
                    }

                    @Override
                    public void onNext(GameTypeModel gameTypeModel) {
                        if (gameTypeModel.getCode() == Constant.OK) {
                            types.addAll(gameTypeModel.getData());
                            int i = 0;
                            for (; i < types.size(); i++) {
                                fragments.add(TypeListFragment.newInstance(types.get(i).getId()));
                            }
                            FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
                                @Override
                                public Fragment getItem(int position) {
                                    return fragments.get(position);
                                }

                                @Override
                                public int getCount() {
                                    return types.size();
                                }

                                @Nullable
                                @Override
                                public CharSequence getPageTitle(int position) {
                                    return types.get(position).getTypename();
                                }
                            };
                            viewPager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(viewPager);
                            viewPager.setCurrentItem(0);
                            i = 0;
                            for (; i < types.size(); i++) {
                                View customView = LayoutInflater.from(getContext()).inflate(R.layout.item_game_type_tab, tabLayout, false);
                                AppCompatImageView imageView = customView.findViewById(R.id.img_game_type_tab);
                                AppCompatTextView name = customView.findViewById(R.id.tv_tab_name);
                                name.setText(types.get(i).getTypename());
                                if (i == 0)
                                    name.setTextColor(Color.parseColor("#107FFD"));
                                if ("棋牌".equals(types.get(i).getTypename())) {
                                    Glide.with(getContext()).load(R.mipmap.icon_chess).into(imageView);
                                } else if ("付费".equals(types.get(i).getTypename())) {
                                    Glide.with(getContext()).load(R.mipmap.icon_nofree).into(imageView);
                                } else if ("小游戏".equals(types.get(i).getTypename())) {
                                    Glide.with(getContext()).load(R.mipmap.icon_mini_game).into(imageView);
                                } else if ("网游".equals(types.get(i).getTypename())) {
                                    Glide.with(getContext()).load(R.mipmap.icon_online_game).into(imageView);
                                } else if ("直播".equals(types.get(i).getTypename())) {
                                    Glide.with(getContext()).load(R.mipmap.icon_live_video).into(imageView);
                                } else {
                                    Glide.with(getContext()).load(types.get(i).getLitpic()).into(imageView);
                                }
                                tabLayout.getTabAt(i).setCustomView(customView);
                            }
                            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    View customView = tab.getCustomView();
                                    AppCompatTextView textView = customView.findViewById(R.id.tv_tab_name);
                                    textView.setTextColor(Color.parseColor("#107FFD"));
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {
                                    View customView = tab.getCustomView();
                                    AppCompatTextView textView = customView.findViewById(R.id.tv_tab_name);
                                    textView.setTextColor(Color.parseColor("#bdbdbd"));
                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }
                            });
                        } else {
                            RxToast.showToast(gameTypeModel.getMsg());
                        }
                    }
                });
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = SpUtil.getInstance(getContext()).getString(Constant.TOKEN, "");
        datas.add("棋牌");
        datas.add("付费");
        datas.add("网游");
        datas.add("小游戏");
        datas.add("直播");
    }
}
