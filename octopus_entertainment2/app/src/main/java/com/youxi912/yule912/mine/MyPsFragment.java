package com.youxi912.yule912.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.model.PsModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPsFragment extends Fragment {
    private AppCompatTextView vip_level;
    private AppCompatTextView benefit_month, benefit_toady, benefit_total;
    private AppCompatTextView tv_amount;
//    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ps, container, false);
        tv_amount = view.findViewById(R.id.tv_amount_my_ps);
        vip_level = view.findViewById(R.id.tv_vip_level_my_ps);
        benefit_month = view.findViewById(R.id.tv_benefit_curMonth);
        benefit_toady = view.findViewById(R.id.tv_benefit_curDay);
        benefit_total = view.findViewById(R.id.tv_benefit_total);
        getData();
        return view;
    }

    private void getData() {
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("正在加载数据,请稍候...");
//        progressDialog.show();
        RetrofitManager.getInstance(getContext()).createReq(API.class).getPsInfor(SpUtil.getInstance(getContext()).getString(Constant.TOKEN))
                .enqueue(new Callback<PsModel>() {
                    @Override
                    public void onResponse(Call<PsModel> call, Response<PsModel> response) {
//                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            PsModel model = response.body();
                            if (model == null || model.getData() == null) {
                                RxToast.error("啊哦,服务器发生未知异常");
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                PsModel.DataBean dataBean = model.getData();
                                if (TextUtils.isEmpty(dataBean.getAmount()))
                                    tv_amount.setText("0");
                                else
                                    tv_amount.setText(dataBean.getAmount());

                                if (TextUtils.isEmpty(dataBean.getVip_level()))
                                    vip_level.setText("章鱼宝宝");
                                else
                                    vip_level.setText(dataBean.getVip_level());
                                if (TextUtils.isEmpty(dataBean.getReceipts_month()))
                                    benefit_month.setText("0");
                                else
                                    benefit_month.setText(dataBean.getReceipts_month());
                                if (TextUtils.isEmpty(dataBean.getReceipts_today()))
                                    benefit_toady.setText("0");
                                else
                                    benefit_toady.setText(dataBean.getReceipts_today());
                                if (TextUtils.isEmpty(dataBean.getReceipts_whole()))
                                    benefit_total.setText("0");
                                else
                                    benefit_total.setText(dataBean.getReceipts_whole());
                            } else {
                                RxToast.error(model.getMsg());
                                if (model.getCode() == Constant.OUT_TIME) {
                                    NIMClient.getService(AuthService.class).logout();
                                    SpUtil.getInstance(getContext()).clear();
                                    ActivityCollector.getInstance().finishAll();
                                    startActivity(new Intent(getContext(), LoginInActivity.class));
                                }
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<PsModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }

}
