package com.youxi912.yule912.home.fragment.fragment_game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
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
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.home.GameDetailActivity;
import com.youxi912.yule912.home.LiveListActivity;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.model.CombineGameModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideCircleTransform;
import com.youxi912.yule912.view.GlideRoundTransform;
import com.youxi912.yule912.web.H5Activity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 推荐
 */
public class RecommendFragment extends Fragment {
    private List<CombineGameModel.DataBeanXXXX.RecentlyGameBean.DataBeanX> recentlyGame = new ArrayList<>();
    private List<CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX> recommendGames = new ArrayList<>();
    private List<CombineGameModel.DataBeanXXXX.ExpenseGameBean.DataBeanXXX> payGame = new ArrayList<>();
    private List<CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX> miniGame = new ArrayList<>();
    private CommonAdapter<CombineGameModel.DataBeanXXXX.RecentlyGameBean.DataBeanX> recentlyAdapter;
    private CommonAdapter<CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX> recommendAdapter;
    private CommonAdapter<CombineGameModel.DataBeanXXXX.ExpenseGameBean.DataBeanXXX> payAdapter;
    private CommonAdapter<CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX> miniAdapter;
    private String token;
    private BGABanner banner;
    private boolean isFirstLoaded = true;
    private RecyclerView recently, recommend, pay, mini;
    private SwipeRefreshLayout refreshLayout;
    private UserLoginModel.DataBean userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = SpUtil.getInstance(getContext()).getString(Constant.TOKEN, "");
        userInfo = SpUtil.getInstance(getContext()).getObject(Constant.USER_INFO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recomend, container, false);
        banner = view.findViewById(R.id.home_banner);

        recently = view.findViewById(R.id.rv_recently_play);
        recently.setLayoutManager(new LinearLayoutManager(getContext(),OrientationHelper.HORIZONTAL,false));

        recommend = view.findViewById(R.id.rv_recommend);
        recommend.setLayoutManager(new GridLayoutManager(getContext(),4));

        pay = view.findViewById(R.id.rv_pay);
        pay.setLayoutManager(new GridLayoutManager(getContext(),4));

        mini = view.findViewById(R.id.rv_mini);
        mini.setLayoutManager(new GridLayoutManager(getContext(),4));
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recentlyGame.clear();
                recommendGames.clear();
                payGame.clear();
                miniGame.clear();
                banner.removeAllViews();
                getGames();
            }
        });
        getGames();
        return view;
    }
	
    private void getGames() {
        RetrofitManager.getInstance(getContext()).createReq(API.class).getCombineGame(token)
            .enqueue(new Callback<CombineGameModel>() {
               @Override
               public void onResponse(Call<CombineGameModel> call, Response<CombineGameModel> response) {
                   if (refreshLayout.isRefreshing())
                       refreshLayout.setRefreshing(false);
                   if (response.isSuccessful()) {
                       CombineGameModel model = response.body();
                       if (model.getCode() == Constant.OK) {
                           //设置banner
                           List<CombineGameModel.DataBeanXXXX.BannerBean> dataBeans = model.getData().getBanner();
                           if (dataBeans != null && dataBeans.size() > 0) {
                               if (dataBeans.size() == 1)
                                   banner.setAutoPlayAble(false);
                               banner.setData(R.layout.banner, dataBeans, null);
                               BGABanner.Adapter bgaAdapter = new BGABanner.Adapter<CardView, CombineGameModel.DataBeanXXXX.BannerBean>() {
                                   @Override
                                   public void fillBannerItem(BGABanner banner, CardView itemView, @Nullable final CombineGameModel.DataBeanXXXX.BannerBean model, int position) {
                                       AppCompatImageView imageView = itemView.findViewById(R.id.img_banner);
                                       Glide.with(getContext())
                                               .load(model.getLitpic())
                                               .apply(new RequestOptions().centerCrop().error(R.mipmap.placeholder_banner))
                                               .into(imageView);
                                       if (model.getGame_id() != null) {
                                           itemView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   GameDetailActivity.ToGameDetail(getContext(), model.getGame_id());
                                               }
                                           });
                                       }
                                   }
                               };
                               banner.setAdapter(bgaAdapter);
                           }

                            //设置最近在玩
                            recentlyGame.addAll(model.getData().getRecently_game().getData());
                            if (recentlyAdapter != null) {
                                recentlyAdapter.notifyDataSetChanged();
                            } else {
                                recentlyAdapter = new CommonAdapter<CombineGameModel.DataBeanXXXX.RecentlyGameBean.DataBeanX>(getContext(), recentlyGame, R.layout.img_icon) {
                                    @Override
                                    public void convert(CommonViewHolder holder, final CombineGameModel.DataBeanXXXX.RecentlyGameBean.DataBeanX item) {
                                        if (!TextUtils.isEmpty(item.getLogo())) {
                                            Glide.with(getContext()).load(item.getLogo())
                                                    .apply(new RequestOptions().centerCrop()
                                                    .transform(new GlideCircleTransform(getContext()))
                                                    .placeholder(R.mipmap.icon_game_placeholder )
                                                    .error(R.mipmap.icon_game_placeholder).dontAnimate())
                                                    .into((AppCompatImageView) holder.getView(R.id.icon_img));
                                        } else {
                                            Glide.with(getContext()).load(R.mipmap.icon_game_placeholder)
                                                    .into((AppCompatImageView) holder.getView(R.id.icon_img));
                                        }
                                    }
                                };
                                recently.setAdapter(recentlyAdapter);
                            }
                            recentlyAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    CombineGameModel.DataBeanXXXX.RecentlyGameBean.DataBeanX.VersionBeanX version = recentlyGame.get(position).getVersion();
                                    if (version != null && version.getPc() != null
                                            && !TextUtils.isEmpty(version.getPc().getResource_url()))
                                        H5Activity.toH5Activity(getContext(), version.getPc().getResource_url());
                                    else {
                                        RxToast.error("游戏地址错误");
                                    }
                                }
                                @Override
                                public void onItemLongClick(View view, int position) {
                                }
                            });

                            //设置推荐游戏
                            if (model.getData().getSuggest_game().getData() != null && model.getData().getSuggest_game().getData().size() > 0) {
                                recommendGames.addAll(model.getData().getSuggest_game().getData().size() > 8 ? model.getData().getSuggest_game().getData().subList(0, 8) :
                                        model.getData().getSuggest_game().getData());
                                if (recommendAdapter != null) {
                                    recommendAdapter.notifyDataSetChanged();
                                } else {
                                    recommendAdapter = new CommonAdapter<CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX>(getContext(), recommendGames, R.layout.item_game) {
                                        @Override
                                        public void convert(CommonViewHolder holder, final CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX item) {
                                            holder.setText(R.id.tv_gameName_item_game, item.getName());
                                            final AppCompatTextView textView = holder.getView(R.id.tv_action_item_game);
                                            if (item.getGame_level() == "1")
                                            {
                                                textView.setText("打开");
                                                textView.setBackgroundResource(R.drawable.item_game_open);
                                            }
                                            else
                                            {
                                                if (item.getPrice().startsWith("0.") || item.getPay_status() == 1) {
                                                    textView.setText("打开");
                                                    textView.setBackgroundResource(R.drawable.item_game_open);
                                                } else {
                                                    textView.setText("获取");
                                                    textView.setBackgroundResource(R.drawable.item_download);
                                                }
                                            }
                                            if (!TextUtils.isEmpty(item.getLogo())) {
                                                Glide.with(getContext()).load(item.getLogo())
                                                        .apply(new RequestOptions().centerCrop().transform(new GlideRoundTransform(getContext(), 5))
                                                        .placeholder(R.mipmap.icon_game_placeholder)
                                                        .error(R.mipmap.icon_game_placeholder).dontAnimate())
                                                        .into((AppCompatImageView) holder.getView(R.id.img_item_game));
                                            } else {
                                                Glide.with(getContext()).load(R.mipmap.icon_game_placeholder)
                                                     .into((AppCompatImageView) holder.getView(R.id.img_item_game));
                                            }
                                            textView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if("18" == item.getCate_id())
                                                    {
                                                        //直播
                                                        startActivity(new Intent(getContext(), LiveListActivity.class));
                                                        return;
                                                    }
                                                    if (item.getGame_level() == "1" && userInfo.getMemberOrder()!="5")
                                                    {
                                                        RxToast.warning("您还未成为章鱼会员，不能进行游戏");
                                                        return;
                                                    }
                                                    if ("打开".equals(textView.getText().toString())) {
                                                        if (item.getVersion() != null && item.getVersion().getPc() != null
                                                                && !TextUtils.isEmpty(item.getVersion().getPc().getResource_url()))
                                                            H5Activity.toH5Activity(getContext(), item.getVersion().getPc().getResource_url());
                                                        else
                                                            RxToast.warning("未请求到游戏地址");
                                                    } else {
                                                        GameDetailActivity.ToGameDetail(getContext(), Integer.parseInt(item.getId()));
                                                    }
                                                }
                                            });
                                        }
                                    };
                                    recommend.setAdapter(recommendAdapter);
                                }
                                recommendAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        GameDetailActivity.ToGameDetail(getContext(), Integer.parseInt(recommendGames.get(position).getId()));
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {
                                    }
                                });
                            }

                            //设置付费游戏
                            if (model.getData().getExpense_game().getData() != null && model.getData().getExpense_game().getData().size() > 0) {
                                payGame.addAll(model.getData().getExpense_game().getData().size() > 8 ? model.getData().getExpense_game().getData().subList(0, 8) :
                                        model.getData().getExpense_game().getData());
                                if (payAdapter != null) {
                                    payAdapter.notifyDataSetChanged();
                                } else {
                                    payAdapter = new CommonAdapter<CombineGameModel.DataBeanXXXX.ExpenseGameBean.DataBeanXXX>(getContext(), payGame, R.layout.item_game) {
                                        @Override
                                        public void convert(CommonViewHolder holder, final CombineGameModel.DataBeanXXXX.ExpenseGameBean.DataBeanXXX item) {
                                            holder.setText(R.id.tv_gameName_item_game, item.getName());
                                            AppCompatTextView textView = holder.getView(R.id.tv_action_item_game);
                                            if (item.getGame_level() == "1")
                                            {
                                                textView.setText("打开");
                                                textView.setBackgroundResource(R.drawable.item_game_open);
                                            }
                                            else
                                            {
                                                if (item.getPrice().startsWith("0.") || item.getPay_status() == 1) {
                                                    textView.setText("打开");
                                                    textView.setBackgroundResource(R.drawable.item_game_open);
                                                } else {
                                                    textView.setText(item.getPrice().substring(0, item.getPrice().lastIndexOf(".")) +"PS");
                                                    textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_pay));
                                                    textView.setBackgroundResource(R.drawable.shape_blue_corner_20);
                                                }
                                            }
                                            if (!TextUtils.isEmpty(item.getLogo())) {
                                                Glide.with(getContext()).load(item.getLogo())
                                                        .apply(new RequestOptions().centerCrop().transform(new GlideRoundTransform(getContext(), 5))
                                                                .placeholder(R.mipmap.icon_game_placeholder)
                                                                .error(R.mipmap.icon_game_placeholder).dontAnimate())
                                                        .into((AppCompatImageView) holder.getView(R.id.img_item_game));
                                            } else {
                                                Glide.with(getContext()).load(R.mipmap.icon_game_placeholder)
                                                        .into((AppCompatImageView) holder.getView(R.id.img_item_game));
                                            }
                                            textView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if ("18" == item.getCate_id())
                                                    {
                                                        //直播
                                                        startActivity(new Intent(getContext(), LiveListActivity.class));
                                                        return;
                                                    }
                                                    if (item.getGame_level() == "1" && userInfo.getMemberOrder()!="5")
                                                    {
                                                        RxToast.warning("您还未成为章鱼会员，不能进行游戏");
                                                        return;
                                                    }
                                                    if ("打开".equals(textView.getText().toString())) {
                                                        if (item.getVersion() != null && item.getVersion().getPc() != null
                                                                && !TextUtils.isEmpty(item.getVersion().getPc().getResource_url()))
                                                            H5Activity.toH5Activity(getContext(), item.getVersion().getPc().getResource_url());
                                                        else
                                                            RxToast.error("未请求到游戏地址");
                                                    } else {
                                                        GameDetailActivity.ToGameDetail(getContext(), Integer.parseInt(item.getId()));
                                                    }
                                                }
                                            });
                                        }
                                    };
                                    pay.setAdapter(payAdapter);
                                }
                                payAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        GameDetailActivity.ToGameDetail(getContext(), Integer.parseInt(payGame.get(position).getId()));
                                    }
                                    @Override
                                    public void onItemLongClick(View view, int position) {
                                    }
                                });
                            }
                            //设置小游戏
                            if (model.getData().getMini_game().getData() != null && model.getData().getMini_game().getData().size() > 0) {
                                miniGame.addAll(model.getData().getMini_game().getData().size() > 8 ? model.getData().getMini_game().getData().subList(0, 8) :
                                        model.getData().getMini_game().getData());
                                if (miniAdapter != null) {
                                    miniAdapter.notifyDataSetChanged();
                                } else {
                                    miniAdapter = new CommonAdapter<CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX>(getContext(), miniGame, R.layout.item_game) {
                                        @Override
                                        public void convert(CommonViewHolder holder, final CombineGameModel.DataBeanXXXX.SuggestGameBean.DataBeanXX item) {
                                            holder.setText(R.id.tv_gameName_item_game, item.getName());
                                            final AppCompatTextView textView = holder.getView(R.id.tv_action_item_game);
                                            if (item.getGame_level() == "1")
                                            {
                                                textView.setText("打开");
                                                textView.setBackgroundResource(R.drawable.item_game_open);
                                            }
                                            else
                                            {
                                                if (item.getPrice().startsWith("0.") || item.getPay_status() == 1) {
                                                    textView.setText("打开");
                                                    textView.setBackgroundResource(R.drawable.item_game_open);
                                                } else {
                                                    textView.setText(item.getPrice().substring(0, item.getPrice().lastIndexOf(".")) +"PS");
                                                    textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_pay));
                                                    textView.setBackgroundResource(R.drawable.shape_blue_corner_20);
                                                }
                                            }
                                            if (!TextUtils.isEmpty(item.getLogo())) {
                                                Glide.with(getContext()).load(item.getLogo())
                                                        .apply(new RequestOptions().centerCrop().transform(new GlideRoundTransform(getContext(), 5))
                                                        .placeholder(R.mipmap.icon_game_placeholder)
                                                        .error(R.mipmap.icon_game_placeholder).dontAnimate())
                                                        .into((AppCompatImageView) holder.getView(R.id.img_item_game));
                                            } else {
                                                Glide.with(getContext()).load(R.mipmap.icon_game_placeholder)
                                                        .into((AppCompatImageView) holder.getView(R.id.img_item_game));
                                            }
                                            textView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if ("18" == item.getCate_id())
                                                    {
                                                        //直播
                                                        startActivity(new Intent(getContext(), LiveListActivity.class));
                                                        return;
                                                    }
                                                    if (item.getGame_level() == "1" && userInfo.getMemberOrder()!="5")
                                                    {
                                                        RxToast.warning("您还未成为章鱼会员，不能进行游戏");
                                                        return;
                                                    }
                                                    if ("打开".equals(textView.getText().toString())) {
                                                        if (item.getVersion() != null && item.getVersion().getPc() != null
                                                                && !TextUtils.isEmpty(item.getVersion().getPc().getResource_url()))
                                                            H5Activity.toH5Activity(getContext(), item.getVersion().getPc().getResource_url());
                                                        else
                                                            RxToast.error("未请求到游戏地址");
                                                    } else {
                                                        GameDetailActivity.ToGameDetail(getContext(), Integer.parseInt(item.getId()));
                                                    }
                                                }
                                            });
                                        }
                                    };
                                    mini.setAdapter(miniAdapter);
                                }
                                miniAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        GameDetailActivity.ToGameDetail(getContext(), Integer.parseInt(miniGame.get(position).getId()));
                                    }
                                    @Override
                                    public void onItemLongClick(View view, int position) {
                                    }
                                });
                            }
                        } else {
                            RxToast.error(model.getMsg());
                            if (model.getCode() == Constant.OUT_TIME) {
                                ActivityCollector.getInstance().finishAll();
                                startActivity(new Intent(getContext(), LoginInActivity.class));
                            }
                        }
                    } else {
                        RxToast.error(response.message());
                    }
                }

                @Override
                public void onFailure(Call<CombineGameModel> call, Throwable t) {
                    if (refreshLayout.isRefreshing())
                        refreshLayout.setRefreshing(false);
                    RxToast.error(t.getMessage());
                }
            });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoaded) {
            isFirstLoaded = false;
        }
    }
}
