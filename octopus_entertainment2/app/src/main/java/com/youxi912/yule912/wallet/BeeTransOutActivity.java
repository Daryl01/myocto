package com.youxi912.yule912.wallet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.CoinDetailModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SecurityIrreversible;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideRoundTransform;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeeTransOutActivity extends BaseActivity {
    CoinDetailModel model;
    AppCompatEditText accountEt;
    AppCompatEditText amountEt;
    AppCompatEditText pwdEt;
    AppCompatTextView nameTv;
    AppCompatTextView balanceTv;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bee_trans_out;
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在请求数据,请稍候...");
        nameTv=(AppCompatTextView)findViewById(R.id.tv_name_trans_out);
        nameTv.setText(model.getData().getAvailable_balance());
        AppCompatImageView iconIv=(AppCompatImageView)findViewById(R.id.img_icon_trans_out);
        Glide.with(this).load(getIntent().getStringExtra("path")).apply(new RequestOptions().transform(new GlideRoundTransform(this, 5))).into(iconIv);
        AppCompatImageView back = findViewById(R.id.img_back_trans_out);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        balanceTv=(AppCompatTextView)findViewById(R.id.tv_amount_trans_out);
        balanceTv.setText(model.getData().getPs_amount());
        accountEt=(AppCompatEditText) findViewById(R.id.account);
        amountEt=(AppCompatEditText)findViewById(R.id.amount);
        pwdEt=(AppCompatEditText)findViewById(R.id.password);

        findViewById(R.id.transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transfer();
            }
        });
    }

    private void transfer() {
        progressDialog.show();
        String address=accountEt.getText().toString().trim();
        String amount=amountEt.getText().toString().trim();
        String pwd=new SecurityIrreversible().getMD5ofStr(pwdEt.getText().toString().trim());
        RetrofitManager.getInstance(this).createReq(API.class).beeTransferOut(
                SpUtil.getInstance(this).getString(Constant.TOKEN),model.getData().getCurrency_name(),address,amount,pwd).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    BaseModel model = response.body();
                    if (model == null) {
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
            public void onFailure(Call<BaseModel> call, Throwable t) {
                progressDialog.dismiss();
                RxToast.error(t.getMessage());
            }
        });

    }

    @Override
    public void initData() {
        model=(CoinDetailModel) getIntent().getSerializableExtra("data");
        if(model==null){
            finish();
        }
    }
}
