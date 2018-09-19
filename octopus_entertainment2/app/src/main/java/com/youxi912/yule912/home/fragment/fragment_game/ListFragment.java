package com.youxi912.yule912.home.fragment.fragment_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.adapter.HotGameListAdapter;
import com.youxi912.yule912.home.GameDetailActivity;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.model.DataBean;
import com.youxi912.yule912.model.IndexGameModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideRoundTransform;
import com.youxi912.yule912.web.H5Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment implements View.OnClickListener {
    private String token = "";
    private String root1, root2, root3;
    private int curPage = 1;
    private int PAGE_SIZE = 10;
    private boolean isFirst = true;
    private HotGameListAdapter gameListAdapter;
    private LinearLayoutCompat linearLayoutCompat1;
    private LinearLayoutCompat linearLayoutCompat2;
    private LinearLayoutCompat linearLayoutCompat3;
    private  RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        token = SpUtil.getInstance(getContext()).getString(Constant.TOKEN);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, container, false);
        linearLayoutCompat1 = view.findViewById(R.id.linear1);
        linearLayoutCompat2 = view.findViewById(R.id.linear2);
        linearLayoutCompat3 = view.findViewById(R.id.linear3);
        linearLayoutCompat1.setOnClickListener(this);
        linearLayoutCompat2.setOnClickListener(this);
        linearLayoutCompat3.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.rv_game_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL,false));

        gameListAdapter = new HotGameListAdapter(R.layout.item_layout_game_list);
        gameListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData();
            }
        }, recyclerView);
        gameListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GameDetailActivity.ToGameDetail(getContext(), gameListAdapter.getData().get(position).getId());
            }
        });
        recyclerView.setAdapter(gameListAdapter);
        return view;
    }

    private void getData() {
        RetrofitManager.getInstance(getContext())
            .createReq(API.class).getHotGames(token, curPage, PAGE_SIZE)
            .enqueue(new Callback<IndexGameModel>() {
                @Override
                public void onResponse(Call<IndexGameModel> call, Response<IndexGameModel> response) {
                    if (getActivity() == null) {
                        return;
                    }
                    if (response.isSuccessful()) {
                        IndexGameModel model = response.body();
                        if (model.getCode() == Constant.OK) {
                            if (model.getData().getData() != null && model.getData().getData().size() > 0) {
                                if (model.getData().getData().size() > 3) {
                                    gameListAdapter.addData(model.getData().getData().subList(3, model.getData().getData().size()));
                                    if (curPage * PAGE_SIZE >= model.getData().getTotal()) {
                                        gameListAdapter.loadMoreEnd();
                                    } else {
                                        curPage++;
                                        gameListAdapter.loadMoreComplete();
                                    }
                                }
                                if (model.getData().getData().size() > 0)
                                {
                                    DataBean dataBean = model.getData().getData().get(0);
                                    AppCompatImageView imageView = linearLayoutCompat1.findViewById(R.id.img_item_game_1);
                                    AppCompatTextView name = linearLayoutCompat1.findViewById(R.id.tv_gameName_item_game_1);
                                    AppCompatTextView tv_action_item_game = linearLayoutCompat1.findViewById(R.id.tv_action_item_game_1);
                                    Glide.with(getContext()).load(dataBean.getLogo())
                                         .apply(new RequestOptions().centerCrop()
                                         .transform(new GlideRoundTransform(getContext(), 5)))
                                         .into(imageView);
                                    name.setText(dataBean.getName());
                                    if (dataBean.getVersion() != null && dataBean.getVersion().getPc() != null)
                                        root1 = dataBean.getVersion().getPc().getResource_url();
                                    if (dataBean.getGame_level() == "1")
                                    {
                                        tv_action_item_game.setText("打开");
                                    }
                                    else
                                    {
                                        if (dataBean.getPrice().startsWith("0.") || dataBean.getPay_status() == 1) {
                                            tv_action_item_game.setText("打开");
                                        } else {
                                            tv_action_item_game.setText("获取");
                                        }
                                    }
                                }
                                if (model.getData().getData().size() > 1) {
                                    DataBean dataBean = model.getData().getData().get(1);
                                    AppCompatImageView imageView = linearLayoutCompat2.findViewById(R.id.img_item_game_2);
                                    AppCompatTextView name = linearLayoutCompat2.findViewById(R.id.tv_gameName_item_game_2);
                                    AppCompatTextView tv_action_item_game = linearLayoutCompat2.findViewById(R.id.tv_action_item_game_2);
                                    Glide.with(getContext()).load(dataBean.getLogo())
                                         .apply(new RequestOptions().centerCrop()
                                         .transform(new GlideRoundTransform(getContext(), 5)))
                                         .into(imageView);
                                    name.setText(dataBean.getName());
                                    if (dataBean.getVersion() != null && dataBean.getVersion().getPc() != null)
                                        root2 = dataBean.getVersion().getPc().getResource_url();
                                    if (dataBean.getGame_level() == "1")
                                    {
                                        tv_action_item_game.setText("打开");
                                    }
                                    else
                                    {
                                        if (dataBean.getPrice().startsWith("0.") || dataBean.getPay_status() == 1) {
                                            tv_action_item_game.setText("打开");
                                        } else {
                                            tv_action_item_game.setText("获取");
                                        }
                                    }
                                } else {
                                    linearLayoutCompat2.setVisibility(View.INVISIBLE);
                                }
                                if (model.getData().getData().size() > 2) {
                                    DataBean dataBean = model.getData().getData().get(2);
                                    AppCompatImageView imageView = linearLayoutCompat3.findViewById(R.id.img_item_game_3);
                                    AppCompatTextView name = linearLayoutCompat3.findViewById(R.id.tv_gameName_item_game_3);
                                    AppCompatTextView tv_action_item_game = linearLayoutCompat3.findViewById(R.id.tv_action_item_game_3);
                                    Glide.with(getContext()).load(dataBean.getLogo()).apply(new RequestOptions().centerCrop()
                                            .transform(new GlideRoundTransform(getContext(), 5))).into(imageView);
                                    name.setText(dataBean.getName());
                                    if (dataBean.getVersion() != null && dataBean.getVersion().getPc() != null)
                                        root3 = dataBean.getVersion().getPc().getResource_url();
                                    if (dataBean.getGame_level() == "1")
                                    {
                                        tv_action_item_game.setText("打开");
                                    }
                                    else
                                    {
                                        if (dataBean.getPrice().startsWith("0.") || dataBean.getPay_status() == 1) {
                                            tv_action_item_game.setText("打开");
                                        } else {
                                            tv_action_item_game.setText("获取");
                                        }
                                    }
                                } else {
                                    linearLayoutCompat3.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                RxToast.error("数据出错");
                            }
                        } else {
                            RxToast.error(model.getMsg());
                            if (model.getCode() == Constant.OUT_TIME) {
                                NIMClient.getService(AuthService.class).logout();
                                ActivityCollector.getInstance().finishAll();
                                SpUtil.getInstance(getContext()).clear();
                                startActivity(new Intent(getContext(), LoginInActivity.class));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<IndexGameModel> call, Throwable t) {
                    RxToast.error("listFragment:" + t.getMessage());
                    Log.e("list--fragment", t.getMessage());
                }
            });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear1:
                if (TextUtils.isEmpty(root1)) {
                    RxToast.error("游戏地址为空");
                } else {
                    H5Activity.toH5Activity(getContext(), root1);
                }
                break;
            case R.id.linear2:
                if (TextUtils.isEmpty(root2)) {
                    RxToast.error("游戏地址为空");
                } else {
                    H5Activity.toH5Activity(getContext(), root2);
                }
                break;
            case R.id.linear3:
                if (TextUtils.isEmpty(root3)) {
                    RxToast.error("游戏地址为空");
                } else {
                    H5Activity.toH5Activity(getContext(), root3);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirst && isVisibleToUser) {
            getData();
            isFirst = false;
        }
    }
}
