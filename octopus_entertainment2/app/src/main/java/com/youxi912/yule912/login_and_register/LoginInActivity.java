package com.youxi912.yule912.login_and_register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.vondear.rxtool.RxRegTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialog;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.config.preference.Preferences;
import com.youxi912.yule912.home.HomeActivity;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.model.VerifyCodeModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.DemoCache;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SecurityIrreversible;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInActivity extends BaseActivity implements View.OnClickListener {
    private AppCompatEditText account;
    private AppCompatEditText password;
    private ProgressDialog dialog;
    private RxDialog forgetDialog;
    private AppCompatButton getCode;
    private CountDownTimer timer;
    private AppCompatEditText phone;
    private AppCompatEditText pass;
    private AppCompatEditText confirm_pass;
    private AppCompatEditText verifyCode;
    private ProgressDialog progressDialog;
    private AppCompatButton confirm;
    private String verifyId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_in;
    }

    @Override
    public void initView() {
        dialog = new ProgressDialog(this);
        account = findViewById(R.id.et_account_login);
        password = findViewById(R.id.et_password_login);
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                getCode.setText(l / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                getCode.setText("获取验证码");
                getCode.setClickable(true);
            }
        };
        progressDialog = new ProgressDialog(this);
        AppCompatButton login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        AppCompatTextView tv_forget = findViewById(R.id.tv_forget_pass_login);
        tv_forget.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pass_login:
                showForgetPass();
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(account.getText().toString().trim())) {
                    RxToast.showToast("请先输入账号!");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    RxToast.showToast("请输入密码!");
                    return;
                }
                dialog.setMessage("正在登录,请稍候...");
                dialog.show();
                SecurityIrreversible irreversible = new SecurityIrreversible();
                Log.e("password---", irreversible.getMD5ofStr(password.getText().toString().trim()));
                RetrofitManager.getInstance(this).createReq(API.class)
                        .userLogin(account.getText().toString().trim(),
                                irreversible.getMD5ofStr(password.getText().toString().trim()))
                        .enqueue(new Callback<UserLoginModel>() {
                            @Override
                            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                                if (isNotActive()) {
                                    return;
                                }
                                dialog.dismiss();
                                if (response.isSuccessful()) {
                                    // TODO: 2018/8/7 0007 token处理
                                    UserLoginModel model = response.body();
                                    if (model.getCode() == Constant.OK) {
                                        UserLoginModel.DataBean dataBean = model.getData();
                                        if (dataBean != null) {
                                            SpUtil.getInstance(LoginInActivity.this).putObject(Constant.USER_INFO, dataBean);
                                            SpUtil.getInstance(LoginInActivity.this).putBoolean(Constant.HAS_LOGIN, true);
                                        }
                                        if (dataBean.getYx_user() != null && !TextUtils.isEmpty(dataBean.getYx_user().getYx_accid())
                                                && !TextUtils.isEmpty(dataBean.getYx_user().getYx_token())) {
                                            LoginInfo loginInfo = new LoginInfo(dataBean.getYx_user().getYx_accid(), dataBean.getYx_user().getYx_token());
                                            NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                                                @Override
                                                public void onSuccess(LoginInfo param) {
                                                    SpUtil.getInstance(LoginInActivity.this).putString(Constant.ACCOUNT, account.getText().toString().trim());
                                                    SpUtil.getInstance(LoginInActivity.this).putString(Constant.PASSWORD, irreversible.getMD5ofStr(password.getText().toString().trim()));
                                                    SpUtil.getInstance(LoginInActivity.this).putString(Constant.TOKEN, TextUtils.isEmpty(dataBean.getToken()) ? "" : dataBean.getToken());
                                                    DemoCache.setAccount(dataBean.getYx_user().getYx_accid());
                                                    DemoCache.setVodtoken(dataBean.getYx_user().getYx_token());
                                                    DemoCache.setSid(dataBean.getYx_user().getYx_token());
                                                    Preferences.saveUserAccount(dataBean.getYx_user().getYx_accid());
                                                    Preferences.saveUserToken(dataBean.getYx_user().getYx_token());
                                                    Preferences.saveVodToken(dataBean.getYx_user().getYx_token());
                                                    //设置uikit
                                                    NimUIKit.loginSuccess(dataBean.getYx_user().getYx_accid());
                                                    SpUtil.getInstance(LoginInActivity.this).putString(Constant.YX_TOKEN, dataBean.getYx_user().getYx_token());
                                                    RxToast.success("登陆成功!");
                                                    startActivity(new Intent(LoginInActivity.this, HomeActivity.class));
                                                    finish();
                                                }

                                                @Override
                                                public void onFailed(int code) {
                                                    if (code == 415) {
                                                        RxToast.error("连接失败");
                                                    } else if (code == 416) {
                                                        RxToast.error("请求过于频繁");
                                                    } else if (code == 417) {
                                                        RxToast.error("您的账号已在另一台设备登录");
                                                    } else if (code == 302) {
                                                        RxToast.error("密码错误!");
                                                    }
                                                }

                                                @Override
                                                public void onException(Throwable exception) {

                                                }
                                            });
                                        } else {
                                            SpUtil.getInstance(LoginInActivity.this).putBoolean(Constant.HAS_LOGIN, true);
                                            SpUtil.getInstance(LoginInActivity.this).putString(Constant.ACCOUNT, account.getText().toString().trim());
                                            SpUtil.getInstance(LoginInActivity.this).putString(Constant.PASSWORD, irreversible.getMD5ofStr(password.getText().toString().trim()));
                                            SpUtil.getInstance(LoginInActivity.this).putString(Constant.TOKEN, TextUtils.isEmpty(dataBean.getToken()) ? "" : dataBean.getToken());
                                            RxToast.success("登陆成功!");
                                            startActivity(new Intent(LoginInActivity.this, HomeActivity.class));
                                            finish();
                                        }
                                    } else {
                                        RxToast.error(model.getMsg());
                                    }
                                } else {
                                    RxToast.error("请求失败了!" + response.message());
                                }

                            }

                            @Override
                            public void onFailure(Call<UserLoginModel> call, Throwable t) {
                                dialog.dismiss();
                                Log.e("failure", Utils.checkNotNull(t.getMessage()));
                            }
                        });
                break;
            case R.id.btn_reset_pass:
                if (TextUtils.isEmpty(verifyCode.getText().toString().trim())) {
                    RxToast.error("请先输入验证码");
                    return;
                }
                resetPassword();
                break;
            default:
                break;
        }
    }

    private void resetPassword() {
        progressDialog.setMessage("正在重置密码,请稍候...");
        progressDialog.show();
        SecurityIrreversible irreversible = new SecurityIrreversible();
        RetrofitManager.getInstance(this).createReq(API.class)
                .resetPass(phone.getText().toString().trim(),
                        irreversible.getMD5ofStr(pass.getText().toString().trim()), verifyId,
                        verifyCode.getText().toString().trim(), "1")
                .enqueue(new Callback<BaseModel>() {
                    @Override
                    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            BaseModel baseModel = response.body();
                            if (baseModel == null) {
                                RxToast.error("啊哦,服务器发生未知异常了");
                                return;
                            }
                            if (baseModel.getCode() == Constant.OK) {
                                RxToast.success("修改密码成功");
                                forgetDialog.dismiss();
                            } else {
                                RxToast.error(baseModel.getMsg());
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


    private void showForgetPass() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_forget_password, null, false);
        AppCompatImageView close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetDialog.dismiss();
            }
        });
        phone = view.findViewById(R.id.et_phone);
        pass = view.findViewById(R.id.et_new_pass);
        confirm_pass = view.findViewById(R.id.et_pass_confirm);
        verifyCode = view.findViewById(R.id.et_verifyCode);
        getCode = view.findViewById(R.id.btn_getVerifyCode);
        confirm = view.findViewById(R.id.btn_reset_pass);
        confirm.setOnClickListener(this);
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    RxToast.error("请先输入手机号码!");
                    return;
                }
                if (!RxRegTool.checkPhone(phone.getText().toString().trim())) {
                    RxToast.error("请输入正确的手机号格式");
                    return;
                }
                if (TextUtils.isEmpty(pass.getText().toString().trim())) {
                    RxToast.error("请先输入密码");
                    return;
                }
                if (TextUtils.isEmpty(confirm_pass.getText().toString().trim())) {
                    RxToast.error("请再次输入以确认您的密码");
                    return;
                }
                if (!pass.getText().toString().trim().equals(confirm_pass.getText().toString().trim())) {
                    RxToast.error("两次输入的密码不一致!");
                    return;
                }
                getSmsCode();
                timer.start();
                getCode.setClickable(false);
            }
        });
        forgetDialog = new RxDialog(this);
        forgetDialog.setContentView(view);
        forgetDialog.setCanceledOnTouchOutside(true);
        forgetDialog.show();
    }

    private void getSmsCode() {
        progressDialog.setMessage("正在请求服务,请稍候...");
        progressDialog.show();
        SecurityIrreversible irreversible = new SecurityIrreversible();
        Log.e("md5---", irreversible.getMD5ofStr("test111"));
        RetrofitManager.getInstance(this).createReq(API.class)
                .getVerifyCode(phone.getText().toString().trim(), irreversible.getMD5ofStr(pass.getText().toString().trim()), "0")
                .enqueue(new Callback<VerifyCodeModel>() {
                    @Override
                    public void onResponse(Call<VerifyCodeModel> call, Response<VerifyCodeModel> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            VerifyCodeModel model = response.body();
                            if (model == null) {
                                RxToast.error("未请求到数据!");
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                RxToast.success("验证码已发送,请注意查收");
                                verifyId = model.getData().getVerifyid();
                            } else {
                                RxToast.error(model.getMsg());
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyCodeModel> call, Throwable t) {
                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }
}
