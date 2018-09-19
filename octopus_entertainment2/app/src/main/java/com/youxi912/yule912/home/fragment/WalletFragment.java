package com.youxi912.yule912.home.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.vondear.rxtool.RxLocationTool;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.home.MoneyDetailActivity;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.model.AccountModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.model.WalletModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideRoundTransform;
import com.youxi912.yule912.wallet.CurrencyHistoryActivity;

@RuntimePermissions
public class WalletFragment extends Fragment {
    //    private ProgressDialog progressDialog;
    private List<AccountModel.DataBean.WalletBean> walletModels = new ArrayList<>();
    private CommonAdapter adapter;
    private AppCompatTextView city;
    private AppCompatTextView money;
    private boolean isFirst = true;
    private SwipeRefreshLayout refreshLayout;

    public WalletFragment() {
        // Required empty public constructor
    }

    public void chargeSuccess(int amount) {
        if (amount != 0 && money != null) {
            int account = Integer.parseInt(money.getText().toString().replace("ps", ""));
            money.setText(account - amount);
        }
    }

    public void signSuccess(int amount) {
        if (amount != 0 && money != null) {
            int account = Integer.parseInt(money.getText().toString().replace("ps", ""));
            money.setText(account - amount);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        city = view.findViewById(R.id.tv_city_wallet);
        refreshLayout = view.findViewById(R.id.swipe_wallet);
        RecyclerView recyclerView = view.findViewById(R.id.rv_wallet);
        adapter = new CommonAdapter<AccountModel.DataBean.WalletBean>(getContext(), walletModels, R.layout.item_wallet) {
            @Override
            public void convert(CommonViewHolder holder, AccountModel.DataBean.WalletBean item) {
                holder.setText(R.id.tv_name_item_wallet, item.getCurrency());
                holder.setText(R.id.tv_money_item_wallet, item.getAmount());
                holder.setText(R.id.tv_ZYW_item_wallet, "≈" + item.getPs_amount() + "章鱼丸");
                AppCompatImageView imageView = holder.getView(R.id.img_icon_item_wallet);
                Glide.with(getContext()).load(item.getCurrency_img())
                        .apply(new RequestOptions().transform(new GlideRoundTransform(getContext(), 5)))
                        .into(imageView);
            }
        };
        AppCompatImageView record = view.findViewById(R.id.img_record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CurrencyHistoryActivity.class);
                intent.putExtra(Constant.CURRENCY_NAME, "PS");
                startActivity(intent);
            }
        });
        WalletFragmentPermissionsDispatcher.getCityNameWithPermissionCheck(this, getContext());
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MoneyDetailActivity.ToCurrencyDetail(getContext(), walletModels.get(position).getCurrency(),
                        walletModels.get(position).getCurrency_name(),
                        walletModels.get(position).getCurrency_img(), walletModels.get(position).getAmount(),
                        walletModels.get(position).getPs_amount() + "", walletModels.get(position).getRate());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        money = view.findViewById(R.id.tv_account_money);
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("正在加载数据,请稍候...");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                walletModels.clear();
                adapter.notifyDataSetChanged();
                requestInfo();
            }
        });
        refreshLayout.setRefreshing(true);
        requestInfo();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            requestInfo();
            isFirst = false;
        }
    }

    private void requestInfo() {
//        progressDialog.show();
        RetrofitManager.getInstance(getContext()).createReq(API.class)
                .getAccountInfor(SpUtil.getInstance(getContext()).getString(Constant.TOKEN))
                .enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
//                        progressDialog.dismiss();
                        if (refreshLayout.isRefreshing())
                            refreshLayout.setRefreshing(false);
                        if (response.isSuccessful()) {
                            AccountModel model = response.body();
                            if (model == null) {
                                RxToast.error("请求回来空数据");
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                String moneyStr = (TextUtils.isEmpty(model.getData().getAmount()) ? "0" : model.getData().getAmount()) + "ps";
                                SpannableString string = new SpannableString(moneyStr);
                                string.setSpan(new AbsoluteSizeSpan(25), moneyStr.lastIndexOf("p"), moneyStr.length(), 0);
                                money.setText(string);
                                if (model.getData().getWallet() != null && model.getData().getWallet().size() > 0) {
                                    walletModels.clear();
                                    walletModels.addAll(model.getData().getWallet());
                                    adapter.notifyDataSetChanged();
                                } else {
                                    RxToast.showToast("没有交易记录哦");
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

                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        if (refreshLayout.isRefreshing())
                            refreshLayout.setRefreshing(false);
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });

    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    public void getCityName(Context context) {
        LocationManager locationManager;
        String contextString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) context.getSystemService(contextString);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 取得效果最好的criteria
        String provider = locationManager.getBestProvider(criteria, true);
        // 得到坐标相关的信息
        if (!TextUtils.isEmpty(provider)) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                city.setText(RxLocationTool.getLocality(getContext(), latitude, longitude));
            }
        }
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void denied() {
        RxToast.showToast("权限被拒绝了!");
        WalletFragmentPermissionsDispatcher.getCityNameWithPermissionCheck(this, getContext());
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    void neverAsk() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WalletFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
