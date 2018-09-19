package com.youxi912.yule912.home.fragment.fragment_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.adapter.TypeListAdapter;
import com.youxi912.yule912.home.GameDetailActivity;
import com.youxi912.yule912.home.LiveListActivity;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.model.IndexGameModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RefreshUtil;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.util.Utils;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 不同分类下的游戏列表
 **/
public class TypeListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int mParam1;
    private int curPage = 1;
    private int PAGE_SIZE = 10;
    private String token = "";
    private TypeListAdapter dataBeanAdapter;
    private SwipeRefreshLayout refreshLayout;
    private boolean isFirst = true;

    public TypeListFragment() {

    }

    public static TypeListFragment newInstance(int id) {
        TypeListFragment fragment = new TypeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = SpUtil.getInstance(getContext()).getString(Constant.TOKEN, "");
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_list, container, false);
        refreshLayout = view.findViewById(R.id.refresh_game_type_list);
        RefreshUtil.initRefreshLayout(getContext(), refreshLayout);
        dataBeanAdapter = new TypeListAdapter(R.layout.item_layout_game_list, null, mParam1);
        RecyclerView recyclerView = view.findViewById(R.id.rv_game_type_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),OrientationHelper.VERTICAL,false));
        recyclerView.setAdapter(dataBeanAdapter);
        dataBeanAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerView);
        dataBeanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataBeanAdapter.getData().size() == 0 || position > dataBeanAdapter.getData().size()) {
                    return;
                }
                if (18 == mParam1) {
                    //直播
                    startActivity(new Intent(getContext(), LiveListActivity.class));
                    return;
                }
                GameDetailActivity.ToGameDetail(getContext(), dataBeanAdapter.getData().get(position).getId());
            }
        });
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                curPage = 1;
                dataBeanAdapter.getData().clear();
                addData();
            }
        });
        dataBeanAdapter.getData().clear();
        addData();
        return view;
    }


    private String change(int value) {
        String s = "";
        if (value < 1000) {
            s = String.valueOf(value) + "   在玩";
        } else if (value < 10000) {
            double val = value * 1.0 / 1000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "K  人在玩";
        } else if (value < 100000) {
            double val = value * 1.0 / 10000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        } else if (value < 1000000) {
            double val = value * 1.0 / 100000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        } else if (value < 10000000) {
            double val = value * 1.0 / 1000000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        }
        return s;
    }

    private void addData() {
        RetrofitManager.getInstance(getContext()).createReq(API.class)
                .getGamesByType(token, curPage, PAGE_SIZE, mParam1)
                .enqueue(new Callback<IndexGameModel>() {
                    @Override
                    public void onResponse(Call<IndexGameModel> call, Response<IndexGameModel> response) {
                        if (response.isSuccessful()) {
                            IndexGameModel model = response.body();
                            if (model.getCode() == Constant.OK) {
                                if (model.getData() != null && model.getData().getData() != null && model.getData().getData().size() > 0) {
                                    dataBeanAdapter.addData(model.getData().getData());
                                    if (curPage == 1) {
                                        refreshLayout.setRefreshing(false);
                                    }
                                    if (curPage * PAGE_SIZE >= model.getData().getTotal()) {
                                        dataBeanAdapter.loadMoreEnd();
                                    } else {
                                        curPage++;
                                        dataBeanAdapter.loadMoreComplete();
                                    }
                                } else {
                                    if (curPage == 1) {
                                        refreshLayout.setRefreshing(false);
                                    }
                                    RxToast.showToast(model.getMsg());
                                    if (model.getCode() == Constant.OUT_TIME) {
                                        NIMClient.getService(AuthService.class).logout();
                                        ActivityCollector.getInstance().finishAll();
                                        SpUtil.getInstance(getContext()).clear();
                                        startActivity(new Intent(getContext(), LoginInActivity.class));
                                    }
                                }
                            } else {
                                if (curPage == 1) {
                                    refreshLayout.setRefreshing(false);
                                }
                                if (model.getCode() == Constant.OUT_TIME) {
                                    NIMClient.getService(AuthService.class).logout();
                                    ActivityCollector.getInstance().finishAll();
                                    SpUtil.getInstance(getContext()).clear();
                                    startActivity(new Intent(getContext(), LoginInActivity.class));
                                }
                                RxToast.error(model.getMsg());
                            }
                        } else {
                            if (curPage == 1) {
                                refreshLayout.setRefreshing(false);
                            }
                            RxToast.showToast("请求失败" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<IndexGameModel> call, Throwable t) {
                        Log.e("error-zy", Utils.checkNotNull(t.getMessage()));
                        if (curPage == 1) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });
    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isFirst && isVisibleToUser){
//            addData();
//            isFirst=false;
//        }
//    }
}
