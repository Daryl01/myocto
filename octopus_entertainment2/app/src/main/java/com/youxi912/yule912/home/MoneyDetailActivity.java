package com.youxi912.yule912.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.R;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.model.CoinDetailModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideRoundTransform;
import com.youxi912.yule912.wallet.BeeTransOutActivity;
import com.youxi912.yule912.wallet.CurrencyHistoryActivity;
import com.youxi912.yule912.wallet.ExchangeActivity;
import com.youxi912.yule912.wallet.TransInActivity;
import com.youxi912.yule912.wallet.TransOutActivity;

public class MoneyDetailActivity extends BaseActivity implements View.OnClickListener {
    private String currency, currencyName, path, total, zyw, rate;
    private ProgressDialog progressDialog;
    private AppCompatTextView usable, freeze, lock;
    private String usable_amount = "";
    CoinDetailModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_money_detail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_money_detail:
                startActivity(new Intent(MoneyDetailActivity.this, HomeActivity.class));
                finish();
                break;
            case R.id.tv_trans_in:
                transInClick();
                break;
            case R.id.tv_trans_out:
                transOutClick();
                break;
            case R.id.img_record:
                Intent intent = new Intent(MoneyDetailActivity.this, CurrencyHistoryActivity.class);
                intent.putExtra(Constant.CURRENCY_NAME, currency);
                startActivity(intent);
                break;
        }
    }

    private void transInClick() {
        if ("PS".equals(currency)) {
            Intent intent = new Intent(MoneyDetailActivity.this, TransOutActivity.class);
            intent.putExtra("data", model);
            intent.putExtra("path", path);
            startActivity(intent);
        } else if ("BTC".equals(currency)) {
            beeTransferOut();
        } else {
            //do nothing
        }
    }
    private void transOutClick() {
        if ("PS".equals(currency)) {//转出页面
            startActivity(new Intent(MoneyDetailActivity.this, TransInActivity.class));
        } else if ("BTC".equals(currency)) {
            btcExchange();
        } else {//兑换页面
            beeTransferOut();
        }
    }

    private void btcExchange() {
        ExchangeActivity.toExchange(MoneyDetailActivity.this, usable_amount, rate, path);
    }

    private void beeTransferOut() {
        Intent intent = new Intent(MoneyDetailActivity.this, BeeTransOutActivity.class);
        intent.putExtra("data", model);
        intent.putExtra("path", path);
        startActivity(intent);
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在请求数据,请稍候...");
        AppCompatImageView iconImg = findViewById(R.id.img_icon_money_detail);
        Glide.with(this).load(path).apply(new RequestOptions().transform(new GlideRoundTransform(this, 5))).into(iconImg);
        AppCompatTextView name = findViewById(R.id.tv_name_money_detail);
        name.setText(currency);
        AppCompatImageView back = findViewById(R.id.img_back_money_detail);
        back.setOnClickListener(this);
        AppCompatTextView amount = findViewById(R.id.tv_amount_money_detail);
        amount.setText(total);
        AppCompatImageView record = findViewById(R.id.img_record);
        record.setOnClickListener(this);
        usable = findViewById(R.id.tv_usable_money_detail);

        freeze = findViewById(R.id.tv_amount_freeze);
        lock = findViewById(R.id.tv_amount_locked);
        AppCompatTextView ZYW_amount = findViewById(R.id.tv_amount_ZYW);
        ZYW_amount.setText("≈" + zyw + "章鱼丸");
        AppCompatTextView trans_in = findViewById(R.id.tv_trans_in);
        AppCompatTextView trans_out = findViewById(R.id.tv_trans_out);
        if ("PS".equals(currency)) {
            ZYW_amount.setVisibility(View.INVISIBLE);
            trans_out.setText("转入");
            trans_in.setText("转出");
        } else if ("BTC".equals(currency)) {
            trans_out.setText("兑换");
            trans_in.setText("转出");
        } else {
            trans_out.setText("转出");
            trans_in.setVisibility(View.GONE);
        }
        trans_in.setOnClickListener(this);
        trans_out.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        RetrofitManager.getInstance(this).createReq(API.class).getCurrencyDetail(
                SpUtil.getInstance(this).getString(Constant.TOKEN), currency).enqueue(new Callback<CoinDetailModel>() {
            @Override
            public void onResponse(Call<CoinDetailModel> call, Response<CoinDetailModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    model = response.body();
                    if (model == null || model.getData() == null) {
                        RxToast.error("未请求到数据");
                        return;
                    }
                    if (model.getCode() == Constant.OK) {
                        total = model.getData().getPs_amount();
                        ((AppCompatTextView) findViewById(R.id.tv_amount_money_detail)).setText(total);
                        usable_amount = model.getData().getAvailable_balance();
                        usable.setText("可用余额" + usable_amount);
                        freeze.setText(model.getData().getFreezing_amount());
                        lock.setText(model.getData().getLocking_amount());
                    } else {
                        RxToast.error(model.getMsg());
                        if (model.getCode() == Constant.OUT_TIME) {
                            reLogin();
                        }
                    }
                } else {
                    RxToast.error(response.message());
                }
            }

            @Override
            public void onFailure(Call<CoinDetailModel> call, Throwable t) {
                progressDialog.dismiss();
                RxToast.error(t.getMessage());
            }
        });
    }

    @Override
    public void initData() {
        currency = getIntent().getStringExtra(Constant.CURRENCY);
        currencyName = getIntent().getStringExtra(Constant.CURRENCY_NAME);
        path = getIntent().getStringExtra(Constant.PATH);
        total = getIntent().getStringExtra(Constant.TOTAL);
        zyw = getIntent().getStringExtra(Constant.ZYW);
        rate = getIntent().getStringExtra(Constant.RATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }

    public static void ToCurrencyDetail(Context context, String currency, String currencyName, String iconPath, String total, String zyw_amount, String rate) {
        Intent intent = new Intent(context, MoneyDetailActivity.class);
        intent.putExtra(Constant.CURRENCY, currency);
        intent.putExtra(Constant.CURRENCY_NAME, currencyName);
        intent.putExtra(Constant.PATH, iconPath);
        intent.putExtra(Constant.TOTAL, total);
        intent.putExtra(Constant.ZYW, zyw_amount);
        intent.putExtra(Constant.RATE, rate);
        context.startActivity(intent);
    }
}
