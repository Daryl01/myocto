package com.youxi912.yule912.home.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vondear.rxtool.RxLocationTool;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.App;
import com.youxi912.yule912.R;
import com.youxi912.yule912.home.DownloadActivity;
import com.youxi912.yule912.home.fragment.fragment_game.ListFragment;
import com.youxi912.yule912.home.fragment.fragment_game.RecommendFragment;
import com.youxi912.yule912.home.fragment.fragment_game.TypeFragment;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class GameFragment extends Fragment {
    private RecommendFragment recommendFragment = new RecommendFragment();
    private ListFragment listFragment = new ListFragment();
    private TypeFragment typeFragment = new TypeFragment();
    private String[] titles = new String[]{"推荐", "分类", "榜单"};
    private Fragment[] fragments = new Fragment[3];
    private ViewPager viewPager;
    private AppCompatTextView tv1, tv2, tv3;
    private TabLayout tabLayout;
    private AppCompatTextView city;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_game, container, false);
        tabLayout = view.findViewById(R.id.tab_game);
        viewPager = view.findViewById(R.id.viewpager_game);
        viewPager.setOffscreenPageLimit(2);
        fragments[0] = recommendFragment;
        fragments[1] = typeFragment;
        fragments[2] = listFragment;
        city = view.findViewById(R.id.tv_city_game);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
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
        };
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        setUpTab();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tv1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(getContext(), R.drawable.tab_bottom));
                } else if (tab.getPosition() == 1) {
                    tv2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(getContext(), R.drawable.tab_bottom));
                } else if (tab.getPosition() == 2) {
                    tv3.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(getContext(), R.drawable.tab_bottom));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tv1.setCompoundDrawables(null, null, null, null);
                } else if (tab.getPosition() == 1) {
                    tv2.setCompoundDrawables(null, null, null, null);
                } else if (tab.getPosition() == 2) {
                    tv3.setCompoundDrawables(null, null, null, null);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        view.findViewById(R.id.img_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DownloadActivity.class));
            }
        });
        GameFragmentPermissionsDispatcher.getCityNameWithPermissionCheck(this);
        return view;
    }


    private void setUpTab() {
        View customView1 = LayoutInflater.from(getContext()).inflate(R.layout.tab_custome_game, tabLayout, false);
        tv1 = customView1.findViewById(R.id.tab_game_item_text);
        tv1.setText(titles[0]);
        tv1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(getContext(), R.drawable.tab_bottom));
        View customView2 = LayoutInflater.from(getContext()).inflate(R.layout.tab_custome_game, tabLayout, false);
        tv2 = customView2.findViewById(R.id.tab_game_item_text);
        tv2.setText(titles[1]);
        View customView3 = LayoutInflater.from(getContext()).inflate(R.layout.tab_custome_game, tabLayout, false);
        tv3 = customView3.findViewById(R.id.tab_game_item_text);
        tv3.setText(titles[2]);
        tv2.setCompoundDrawables(null, null, null, null);
        tv3.setCompoundDrawables(null, null, null, null);
        tabLayout.getTabAt(0).setCustomView(customView1);
        tabLayout.getTabAt(1).setCustomView(customView2);
        tabLayout.getTabAt(2).setCustomView(customView3);
    }

    /**
     * 通过GPS得到城市名
     *
     * @return 城市名
     */
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    public void getCityName() {
        city.post(new Runnable() {
            @Override
            public void run() {
                LocationManager locationManager;
                String contextString = Context.LOCATION_SERVICE;
                locationManager = (LocationManager) App.getAppContext().getSystemService(contextString);
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(false);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                // 取得效果最好的criteria
                String provider = locationManager.getBestProvider(criteria, true);
                if (provider == null) {
                    return;
                }
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null && getActivity() != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    city.setText(RxLocationTool.getLocality(getActivity(), latitude, longitude));
                }
            }
        });
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void denied() {
        RxToast.showToast("权限被拒绝了!");
        GameFragmentPermissionsDispatcher.getCityNameWithPermissionCheck(this);
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    void neverAsk() {
        RxToast.showToast("不在访问了!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GameFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
