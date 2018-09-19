package com.youxi912.yule912.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.vondear.rxtool.RxImageTool;
import com.vondear.rxtool.RxRegTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialog;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.ChargeModel;
import com.youxi912.yule912.model.PhoneModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SecurityIrreversible;
import com.youxi912.yule912.util.SpUtil;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 充值中心
 */
public class ChargeCenterActivity extends BaseActivity {
    private List<ChargeModel.DataBean> datas = new ArrayList<>();
    private View dialogView;
    private AppCompatTextView num;
    private RxDialog dialog;
    private String phone;
    private AppCompatEditText tv_phone;
    private CommonAdapter<ChargeModel.DataBean> adapter;
    private String token;
    private AppCompatEditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_charge_center;
    }

    @Override
    public void initView() {
//        progressDialog = new ProgressDialog(this);
        dialog = new RxDialog(ChargeCenterActivity.this);
        tv_phone = findViewById(R.id.tv_phone);
        findViewById(R.id.tv_market_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.rv_charge_phone);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        dialogView = LayoutInflater.from(this).inflate(R.layout.confirm_charge, null, false);
        num = dialogView.findViewById(R.id.tv_num_charge);
        AppCompatButton charge = dialogView.findViewById(R.id.btn_charge_immediately);
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        adapter = new CommonAdapter<ChargeModel.DataBean>(this, datas, R.layout.item_charge_telephone) {
            @Override
            public void convert(CommonViewHolder holder, ChargeModel.DataBean item) {
                holder.setText(R.id.tv_item_RMB, item.getValue1() + "元");
                holder.setText(R.id.tv_item_ZYW, item.getValue2() + "PS");
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                if (TextUtils.isEmpty(tv_phone.getText().toString().trim())) {
                    RxToast.error("请先输入手机号码");
                } else if (!RxRegTool.checkPhone(tv_phone.getText().toString().trim())) {
                    RxToast.error("请输入正确的手机号码!");
                } else {
                    final RxDialog rxDialog = new RxDialog(ChargeCenterActivity.this);
                    View view1 = LayoutInflater.from(ChargeCenterActivity.this).inflate(R.layout.confirm_charge, null, false);
                    AppCompatImageView close = view1.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rxDialog.dismiss();
                        }
                    });
                    pass = view1.findViewById(R.id.et_pass_charge);
                    AppCompatTextView textView = view1.findViewById(R.id.tv_num_charge);
                    textView.setText("PS  " + datas.get(position).getValue2());
                    AppCompatButton confirm = view1.findViewById(R.id.btn_charge_immediately);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rxDialog.dismiss();
                            charge(position);
                        }
                    });
                    LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(RxImageTool.dp2px(300f), LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    rxDialog.setContentView(view1, params);
                    rxDialog.show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        getPhone();
        getFare();
    }

    private void getPhone() {
//        progressDialog.setMessage("正在加载数据,请稍候..");
        RetrofitManager.getInstance(this).createReq(API.class)
                .getUserPhone(token)
                .enqueue(new Callback<PhoneModel>() {
                    @Override
                    public void onResponse(Call<PhoneModel> call, Response<PhoneModel> response) {
//                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            PhoneModel model = response.body();
                            if (model == null)
                                RxToast.error("未请求到数据");
                            if (model.getCode() == Constant.OK) {
                                phone = model.getData().getPhone_no();
                                tv_phone.setText(phone);
                            } else {
                                if (model.getCode() == Constant.OUT_TIME)
                                    reLogin();
                                RxToast.error(model.getMsg());
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<PhoneModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }

    private void getFare() {
        RetrofitManager.getInstance(this).createReq(API.class)
                .getFaresInfor(token)
                .enqueue(new Callback<ChargeModel>() {
                    @Override
                    public void onResponse(Call<ChargeModel> call, Response<ChargeModel> response) {
                        if (response.isSuccessful()) {
                            ChargeModel model = response.body();
                            if (model == null || model.getData() == null || model.getData().size() == 0) {
                                RxToast.error("请求的数据为空");
                                finish();
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                datas.addAll(model.getData());
                                adapter.notifyDataSetChanged();
                            } else {
                                RxToast.error(model.getMsg());
                                if (model.getCode() == Constant.OUT_TIME)
                                    reLogin();
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ChargeModel> call, Throwable t) {
                        RxToast.error(t.getMessage());
                    }
                });
    }

    private void charge(int position) {
        if (TextUtils.isEmpty(pass.getText().toString().trim())){
            RxToast.error("请先输入登录密码!");
            return;
        }
        SecurityIrreversible irreversible = new SecurityIrreversible();
        RetrofitManager.getInstance(this).createReq(API.class)
                .charge(token, tv_phone.getText().toString().trim(),
                        datas.get(position).getId(),
                        irreversible.getMD5ofStr(pass.getText().toString().trim()))
                .enqueue(new Callback<BaseModel>() {
                    @Override
                    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                        if (response.isSuccessful()) {
                            BaseModel model = response.body();
                            if (model != null && model.getCode() == Constant.OK) {
                                RxToast.success(model.getMsg());
                                Intent intent = new Intent("com.yule912.charge_success");
                                intent.putExtra(Constant.CHARGE_AMOUNT, datas.get(position).getValue2());
                                sendBroadcast(intent);
                            } else {
                                if (model !=null && model.getCode() == Constant.OUT_TIME) {
                                    RxToast.error(model.getMsg());
                                    reLogin();
                                }
                                else
                                {
                                    RxToast.error(model.getMsg());
                                }
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel> call, Throwable t) {
                        RxToast.error(t.getMessage());
                    }
                });
    }

    @Override
    public void initData() {
        token = SpUtil.getInstance(this).getString(Constant.TOKEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }
}
