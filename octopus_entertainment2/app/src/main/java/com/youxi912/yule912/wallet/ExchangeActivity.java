package com.youxi912.yule912.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.R;

import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.StringBaseModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SecurityIrreversible;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideRoundTransform;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeActivity extends BaseActivity {
    private String amount, rate;
    private AppCompatTextView hint;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_exchange;
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在请求数据,请稍候...");
        AppCompatImageView iconIv=findViewById(R.id.img_icon_money_detail);
        Glide.with(this).load(getIntent().getStringExtra("path")).apply(new RequestOptions().transform(new GlideRoundTransform(this, 5))).into(iconIv);
        final AppCompatTextView exchange = findViewById(R.id.tv_amount_exchange);
        exchange.setText(amount);
        AppCompatEditText input = findViewById(R.id.et_input_exchange);
        hint = findViewById(R.id.tv_hint_exchange);
        AppCompatTextView tv_rate = findViewById(R.id.tv_rate);
        String str = "今日兑换比例 1 ：" + rate + "章鱼丸";
        tv_rate.setText(str);
        AppCompatButton btnExchange = findViewById(R.id.btn_exchange);
        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exchange();
            }
        });
        AppCompatImageView back = findViewById(R.id.img_back_exchange);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString())) {
                    hint.setVisibility(View.VISIBLE);
                    BigDecimal bigDecimal = new BigDecimal(editable.toString());
                    int now_rate = Integer.parseInt(rate);
                    String string = bigDecimal.stripTrailingZeros().floatValue() * now_rate + "";
                    hint.setText("等于 " + string + " 章鱼丸");
                } else {
                    hint.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void exchange() {
        progressDialog.show();
        RetrofitManager.getInstance(this).createReq(API.class).exchange(
                SpUtil.getInstance(this).getString(Constant.TOKEN), amount).enqueue(new Callback<StringBaseModel>() {
            @Override
            public void onResponse(Call<StringBaseModel> call, Response<StringBaseModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    StringBaseModel model = response.body();
                    if (model == null || model.getData() == null) {
                        RxToast.error("未请求到数据");
                        return;
                    }
                    if (model.getCode() == Constant.OK) {
                        RxToast.success(model.getMsg());
                        finish();
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
            public void onFailure(Call<StringBaseModel> call, Throwable t) {
                progressDialog.dismiss();
                RxToast.error(t.getMessage());
            }
        });

    }

    @Override
    public void initData() {
        amount = getIntent().getStringExtra(Constant.TOTAL);
        rate = getIntent().getStringExtra(Constant.RATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }

    public static void toExchange(Context context, String amount, String rate,String path) {
        Intent intent = new Intent(context, ExchangeActivity.class);
        intent.putExtra(Constant.TOTAL, amount);
        intent.putExtra(Constant.RATE, rate);
        intent.putExtra(Constant.PATH,path);
        context.startActivity(intent);
    }
}
