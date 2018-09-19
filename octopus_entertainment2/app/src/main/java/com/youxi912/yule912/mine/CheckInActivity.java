package com.youxi912.yule912.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.vondear.rxtool.RxImageTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialog;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.CheckInModel;
import com.youxi912.yule912.model.SignListModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInActivity extends BaseActivity implements CalendarView.OnDateSelectedListener {
    private Map<String, Calendar> hashMap = new HashMap<>();
    private CalendarView calendarView;
//    private ProgressDialog progressDialog;
    private UserLoginModel.DataBean dataBean;
    private boolean isFirdtSelected = true;
    private boolean signed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_in;
    }

    @Override
    public void initView() {
        calendarView = findViewById(R.id.calendar_checkIn);
        calendarView.setOnDateSelectedListener(this);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("正在加载数据,请稍候...");
//        progressDialog.show();
        getCheckData();
        findViewById(R.id.tv_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getCheckData() {
        RetrofitManager.getInstance(this).createReq(API.class).getSignList(SpUtil.getInstance(this).getString(Constant.TOKEN))
                .enqueue(new Callback<SignListModel>() {
                    @Override
                    public void onResponse(Call<SignListModel> call, Response<SignListModel> response) {
//                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            SignListModel model = response.body();
                            if (model == null)
                                return;
                            if (model.getCode() == Constant.OK) {
                                if (model.getData() == null || model.getData().size() == 0)
                                    return;
                                List<SignListModel.DataBean> datas = model.getData();
                                int i = 0;
                                for (; i < datas.size(); i++) {
                                    String date = datas.get(i).getDay().substring(0, 10).trim();
                                    int year = Integer.parseInt(date.substring(0, 4));
                                    int month = Integer.parseInt(date.substring(5, date.lastIndexOf("/")));
                                    int day = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1, date.length()));
                                    if ("1".equals(datas.get(i).getJr())) {
                                        hashMap.put(getSchemeCalendar(year, month, day, 0xff7EB414, "签到").toString(),
                                                getSchemeCalendar(year, month, day, 0xff7EB414, "签到"));
                                    } else {
                                        if (month == calendarView.getCurMonth() && year == calendarView.getCurYear()
                                                && day == calendarView.getCurDay()) {
                                            hashMap.put(getSchemeCalendar(year, month, day, 0xffff4c4c, "未签到").toString(),
                                                    getSchemeCalendar(year, month, day, 0xffff4c4c, "未签到"));
                                        }
                                    }

                                }
                                if (calendarView != null)
                                    calendarView.setSchemeDate(hashMap);
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
                    public void onFailure(Call<SignListModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        if (!isFirdtSelected && isClick) {
            if (calendar.getMonth() == calendarView.getCurMonth() && calendar.getYear() == calendarView.getCurYear()
                    && calendar.getDay() == calendarView.getCurDay()) {
                if ("0".equals(dataBean.getIsSigned())) {
                    checkIn();
                } else {
                    RxToast.showToast("您今日已经签到");
                }
            }
        } else {
            isFirdtSelected = false;
        }
    }

    private void checkIn() {
//        progressDialog.setMessage("正在签到,请稍候...");
        RetrofitManager.getInstance(this).createReq(API.class).checkIn(SpUtil.getInstance(this).getString(Constant.TOKEN))
                .enqueue(new Callback<CheckInModel>() {
                    @Override
                    public void onResponse(Call<CheckInModel> call, Response<CheckInModel> response) {
//                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            CheckInModel model = response.body();
                            if (model.getCode() == Constant.OK) {
                                hashMap.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), calendarView.getCurDay(), 0xffFF4C4D, "签到").toString(),
                                        getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), calendarView.getCurDay(), 0xffFF4C4D, "签到"));
                                calendarView.setSchemeDate(hashMap);
                                //会员签到，奖励为0个时，仍然提示签到成功页面。非会员不提示。
                                if ("5".equals(dataBean.getMemberOrder()) || (model.getData().get(0).getNowCount() != "0" && "5" != dataBean.getMemberOrder())) {
                                    final RxDialog dialog = new RxDialog(CheckInActivity.this);
                                    signed = true;
                                    View view = LayoutInflater.from(CheckInActivity.this).inflate(R.layout.check_in_success, null, false);
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(RxImageTool.dp2px(300), ConstraintLayout.LayoutParams.WRAP_CONTENT);
                                    AppCompatTextView tv = view.findViewById(R.id.tv_check_new);
                                    AppCompatTextView sum = view.findViewById(R.id.tv_check_sum);
                                    Intent intent = new Intent("com.yule912.sign_success");
                                    intent.putExtra(Constant.SIGN_AMOUNT, model.getData().get(0).getNowCount());
                                    sendBroadcast(intent);
                                    SpannableString new_str = new SpannableString("恭喜获得" + model.getData().get(0).getNowCount() + "个章鱼丸");
                                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FE6900"));
                                    new_str.setSpan(colorSpan, 4, new_str.length() - 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    tv.setText(new_str);
                                    String sum_str = "已累计获得" + model.getData().get(0).getSumCount() + "个";
                                    sum.setText(sum_str);
                                    AppCompatButton sure = view.findViewById(R.id.btn_check_success);
                                    sure.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setContentView(view, params);
                                    dialog.setCanceledOnTouchOutside(true);
                                    if (!isFinishing()) {
                                        dialog.show();
                                    }
                                } else {
                                    RxToast.success("您已成功签到!");
                                }
                            } else {
                                RxToast.error(model.getMsg());
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckInModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }

    @Override
    public void initData() {
        dataBean = SpUtil.getInstance(this).getObject(Constant.USER_INFO);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onBackPressed() {
        if (signed) {
            setResult(RESULT_OK);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
