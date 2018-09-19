package com.youxi912.yule912.wallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.Result;
import com.vondear.rxfeature.activity.ActivityScanerCode;
import com.vondear.rxfeature.module.scaner.OnRxScanerListener;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialog;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.CoinDetailModel;
import com.youxi912.yule912.model.StringBaseModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SecurityIrreversible;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideRoundTransform;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransOutActivity extends BaseActivity {
    CoinDetailModel model;
    AppCompatEditText accountEt;
    AppCompatEditText amountEt;
    AppCompatEditText pwdEt;
    AppCompatTextView nameTv;
    AppCompatTextView balanceTv;
    private ProgressDialog progressDialog;
    int typeIndex = 0;

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
        return R.layout.activity_trans_out;
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在请求数据,请稍候...");
        nameTv = (AppCompatTextView) findViewById(R.id.tv_name_trans_out);
        nameTv.setText(model.getData().getCurrency_name());
        AppCompatImageView scan = findViewById(R.id.img_scan_tran_out);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxActivityTool.skipActivity(TransOutActivity.this, ActivityScanerCode.class);
                ActivityScanerCode.setScanerListener(new OnRxScanerListener() {
                    @Override
                    public void onSuccess(String s, Result result) {
                        if (result != null && !TextUtils.isEmpty(result.getText())) {
                            String string = result.getText();
                            Intent intent = new Intent(TransOutActivity.this, TransOutActivity.class);
                            intent.putExtra("gameId", string);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFail(String s, String s1) {
                        RxToast.error(s1);
                    }
                });
            }
        });
        AppCompatImageView iconIv = (AppCompatImageView) findViewById(R.id.img_icon_trans_out);
        Glide.with(this).load(getIntent().getStringExtra("path")).apply(new RequestOptions().transform(new GlideRoundTransform(this, 5))).into(iconIv);
        balanceTv = (AppCompatTextView) findViewById(R.id.tv_amount_trans_out);
        balanceTv.setText(model.getData().getAvailable_balance());
        accountEt = (AppCompatEditText) findViewById(R.id.account);
        amountEt = (AppCompatEditText) findViewById(R.id.amount);
        pwdEt = (AppCompatEditText) findViewById(R.id.password);
        AppCompatImageView back = findViewById(R.id.img_back_trans_out);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = accountEt.getText().toString().trim();
                String amount = amountEt.getText().toString().trim();
                String pwd = new SecurityIrreversible().getMD5ofStr(pwdEt.getText().toString().trim());
                if (TextUtils.isEmpty(address) || TextUtils.isEmpty(address) || TextUtils.isEmpty(address)) {
                    RxToast.error("参数不全");
                    return;
                }
                if (typeIndex == 0) {
                    transfer("0");
                } else {
                    beeTransfer();
                }

            }
        });
        findViewById(R.id.id_selected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeIndex = 0;
                accountEt.setText("");
                findViewById(R.id.id_selected).setBackgroundResource(R.drawable.shape_blue_5);
                ((AppCompatTextView) findViewById(R.id.id_selected)).setTextColor(Color.WHITE);
                findViewById(R.id.address_selected).setBackgroundResource(0);
                ((AppCompatTextView) findViewById(R.id.address_selected)).setTextColor(getResources().getColor(R.color._8c));
            }
        });
        findViewById(R.id.address_selected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeIndex = 1;
                accountEt.setText("");
                findViewById(R.id.address_selected).setBackgroundResource(R.drawable.shape_blue_5);
                ((AppCompatTextView) findViewById(R.id.address_selected)).setTextColor(Color.WHITE);
                findViewById(R.id.id_selected).setBackgroundResource(0);
                ((AppCompatTextView) findViewById(R.id.id_selected)).setTextColor(getResources().getColor(R.color._8c));
            }
        });
    }

    private void beeTransfer() {
        progressDialog.show();
        String address = accountEt.getText().toString().trim();
        String amount = amountEt.getText().toString().trim();
        String pwd = new SecurityIrreversible().getMD5ofStr(pwdEt.getText().toString().trim());
        RetrofitManager.getInstance(this).createReq(API.class).beeTransferOut(
                SpUtil.getInstance(this).getString(Constant.TOKEN), model.getData().getCurrency_name(), address, amount, pwd).enqueue(new Callback<BaseModel>() {
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

    private void transfer(final String step) {
        progressDialog.show();
        String address = accountEt.getText().toString().trim();
        String amount = amountEt.getText().toString().trim();
        String pwd = new SecurityIrreversible().getMD5ofStr(pwdEt.getText().toString().trim());
        RetrofitManager.getInstance(this).createReq(API.class).transferOut(
                SpUtil.getInstance(this).getString(Constant.TOKEN), address, amount, pwd, step).enqueue(new Callback<StringBaseModel>() {
            @Override
            public void onResponse(Call<StringBaseModel> call, Response<StringBaseModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    StringBaseModel model = response.body();
                    if (model == null) {
                        RxToast.error("未请求到数据");
                        return;
                    }
                    if (model.getCode() == Constant.OK) {
                        if (step.equals("0")) {
                            final RxDialog dialog = new RxDialog(TransOutActivity.this);
                            View view = LayoutInflater.from(TransOutActivity.this).inflate(R.layout.confirm_transfer, null, false);
                            AppCompatTextView tv = view.findViewById(R.id.tv_num_charge);
                            tv.setText(model.getMsg());
                            AppCompatButton sure = view.findViewById(R.id.btn_charge_immediately);
                            sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    transfer("1");
                                }
                            });
                            dialog.setContentView(view);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                        } else {
                            RxToast.success(model.getMsg());
                            finish();
                        }

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
        model = (CoinDetailModel) getIntent().getSerializableExtra("data");
        if (model == null || model.getData() == null) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (!TextUtils.isEmpty(intent.getStringExtra("gameId"))) {
            accountEt.setText(intent.getStringExtra("gameId"));
            if (amountEt.getText().toString().trim().length() > 0 && pwdEt.getText().toString().trim().length() > 0) {
                if (typeIndex == 0)
                    transfer("0");
                else {
                    RxDialogSureCancel dialog = new RxDialogSureCancel(TransOutActivity.this);
                    dialog.setContent("您确定向游戏地址：" + intent.getStringExtra("gameId") + "/n转出" + amountEt.getText().toString() + "章鱼丸吗?");
                    dialog.setSureListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            beeTransfer();
                        }
                    });
                }
            }
        }
        super.onNewIntent(intent);
    }
}
