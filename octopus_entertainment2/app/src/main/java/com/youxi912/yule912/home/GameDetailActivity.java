package com.youxi912.yule912.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.DataBean;
import com.youxi912.yule912.model.GameModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.util.TimeUtils;
import com.youxi912.yule912.view.GlideRoundTransform;
import com.youxi912.yule912.view.StarBar;
import com.youxi912.yule912.web.H5Activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailActivity extends BaseActivity implements View.OnClickListener {
    private List<DataBean.PicBean> imgs = new ArrayList<>();
    private int id;
    private int gameid;
    private String token = "";
    private AppCompatImageView icon;
    private AppCompatTextView tv_name, tv_description;
    private AppCompatTextView tv_number, tv_size;
    private StarBar starBar;
    private AppCompatTextView tv_updateTime, tv_version;
    private AppCompatTextView tv_updateContent, tv_expand;
    private CommonAdapter<DataBean.PicBean> adapter;
    private boolean isExpanded = false;
    private ProgressDialog progressDialog;
    private boolean isFree = false;
    private String resourceUrl = "";
    private AppCompatButton btn_get;
    private accessTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_detail_game:
                if (isFree) {
                    if (TextUtils.isEmpty(resourceUrl)) {
                        RxToast.error("游戏地址为空!");
                    } else {
                        task = new accessTask();
                        task.execute();
                        H5Activity.toH5Activity(GameDetailActivity.this, resourceUrl);
                    }
                } else {
                    RxToast.showToast("暂未开通付费接口,敬请期待");
                }
                break;
            case R.id.btn_share_detail_game:
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_game_detail;
    }

    @Override
    public void initView() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("正在请求数据,请稍候...");
        icon = findViewById(R.id.icon_detail_game);
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_name = findViewById(R.id.tv_name_detail_game);
        tv_description = findViewById(R.id.tv_description_detail_game);
        tv_number = findViewById(R.id.tv_playerNum_detail_game);
        tv_size = findViewById(R.id.size_item_detail_game);
        tv_expand = findViewById(R.id.tv_more_detail_game);
        tv_version = findViewById(R.id.tv_version_detail_game);
        tv_updateTime = findViewById(R.id.tv_updateTime_detail_game);
        tv_updateContent = findViewById(R.id.tv_content_update_detail_game);
        starBar = findViewById(R.id.star_bar_game_detail);
        AppCompatButton btn_share = findViewById(R.id.btn_share_detail_game);
        btn_get = findViewById(R.id.btn_get_detail_game);
        RecyclerView recyclerView = findViewById(R.id.rv_detail_game);
        btn_get.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new CommonAdapter<DataBean.PicBean>(this, imgs, R.layout.item_detail_game) {
            @Override
            public void convert(CommonViewHolder holder, DataBean.PicBean item) {
                if (!TextUtils.isEmpty(item.getUrl()))
                    Glide.with(GameDetailActivity.this).load(item.getUrl())
                            .apply(new RequestOptions().centerCrop().dontAnimate()
                                    .placeholder(R.mipmap.placeholder_banner)
                                    .error(R.mipmap.placeholder_banner))
                            .into((AppCompatImageView) holder.getView(R.id.img_item_detail_game));
                else
                    Glide.with(GameDetailActivity.this).load(R.mipmap.placeholder_banner)
                            .into((AppCompatImageView) holder.getView(R.id.img_item_detail_game));
            }
        };
        recyclerView.setAdapter(adapter);
//        progressDialog.show();
        getDetail();
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt(Constant.ID, -1);
            token = SpUtil.getInstance(this).getString(Constant.TOKEN, "");
        }
    }

    private void dismiss() {
        if (!isFinishing() && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void getDetail() {
        RetrofitManager.getInstance(this).createReq(API.class)
                .getGameDetails(token, id)
                .enqueue(new Callback<GameModel>() {
                    @Override
                    public void onResponse(Call<GameModel> call, Response<GameModel> response) {
//                        dismiss();
                        if (response.isSuccessful()) {
                            GameModel gameModel = response.body();
                            if (gameModel != null && gameModel.getData() != null && gameModel.getCode() == Constant.OK) {
                                DataBean bean = gameModel.getData();
                                tv_name.setText(bean.getName());
                                if (bean.getPrice().startsWith("0.") || bean.getPay_status() == 1) {
                                    isFree = true;
                                    btn_get.setText("打开");
                                } else {
                                    isFree = false;
                                    btn_get.setText("获取");
                                }
                                if (isFinishing() || isDestroyed()) {
                                    return;
                                }
                                if (!TextUtils.isEmpty(bean.getLogo()))
                                    Glide.with(GameDetailActivity.this).load(bean.getLogo())
                                            .apply(new RequestOptions().centerCrop().transform(new GlideRoundTransform(GameDetailActivity.this, 5))
                                                    .placeholder(R.mipmap.placeholder)
                                                    .error(R.mipmap.placeholder))
                                            .into(icon);
                                else
                                    Glide.with(GameDetailActivity.this).load(R.mipmap.placeholder).into(icon);
                                BigDecimal bigDecimal1 = new BigDecimal(Float.parseFloat(bean.getRank()));
                                BigDecimal bigDecimal2 = new BigDecimal(20);
                                bigDecimal1 = bigDecimal1.divide(bigDecimal2, 1, BigDecimal.ROUND_HALF_UP);
                                starBar.setStarMark(bigDecimal1.floatValue());
                                tv_description.setText(bean.getContent());
                                tv_size.setText(bean.getSize() + "MB");
                                tv_number.setText(change(bean.getDownload_times()));
                                if (bean.getVersion() != null && bean.getVersion().getAndroid() != null)
                                    tv_version.setText("版本" + bean.getVersion().getAndroid().getPackage_version());
                                else if (bean.getVersion() != null && bean.getVersion().getPc() != null)
                                    tv_version.setText("版本" + bean.getVersion().getPc().getPackage_version());
                                tv_updateTime.setText(TimeUtils.getFriendlyTimeSpanByNow(bean.getUpdate_time() * 1000));
                                if (bean.getVersion() != null && bean.getVersion().getAndroid() != null) {
                                    if (!TextUtils.isEmpty(bean.getVersion().getAndroid().getResource_url()))
                                        resourceUrl = bean.getVersion().getAndroid().getResource_url();
                                    gameid = bean.getId();
                                    tv_updateContent.setText(TextUtils.isEmpty(bean.getVersion().getAndroid().getPackage_content()) ? "null" :
                                            bean.getVersion().getAndroid().getPackage_content());
                                    tv_updateContent.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("lineCount--", tv_updateContent.getLineCount() + "");
                                            if (tv_updateContent.getLineCount() > 1) {
                                                tv_updateContent.setMaxLines(1);
                                                tv_expand.setVisibility(View.VISIBLE);
                                                tv_expand.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if (isExpanded) {
                                                            tv_expand.setText("展开");
                                                            tv_updateContent.setMaxLines(1);
                                                        } else {
                                                            tv_expand.setText("收起");
                                                            tv_updateContent.setMaxLines(10);
                                                        }
                                                        isExpanded = !isExpanded;
                                                    }
                                                });
                                            } else {
                                                tv_expand.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                } else if (bean.getVersion() != null && bean.getVersion().getPc() != null) {
                                    if (!TextUtils.isEmpty(bean.getVersion().getPc().getResource_url()))
                                        resourceUrl = bean.getVersion().getPc().getResource_url();
                                    gameid = bean.getId();
                                    tv_updateContent.setText(TextUtils.isEmpty(bean.getVersion().getPc().getPackage_content()) ? "null" :
                                            bean.getVersion().getPc().getPackage_content());
                                    tv_updateContent.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("lineCount--", tv_updateContent.getLineCount() + "");
                                            if (tv_updateContent.getLineCount() > 1) {
                                                tv_updateContent.setMaxLines(1);
                                                tv_expand.setVisibility(View.VISIBLE);
                                                tv_expand.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if (isExpanded) {
                                                            tv_expand.setText("展开");
                                                            tv_updateContent.setMaxLines(1);
                                                        } else {
                                                            tv_expand.setText("收起");
                                                            tv_updateContent.setMaxLines(10);
                                                        }
                                                        isExpanded = !isExpanded;
                                                    }
                                                });
                                            } else {
                                                tv_expand.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                                if (bean.getPic() != null && bean.getPic().size() > 0) {
                                    imgs.addAll(bean.getPic());
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                if (gameModel.getCode() == Constant.OUT_TIME)
                                    reLogin();
                                RxToast.error(gameModel.getMsg());
                            }
                        } else {
                            RxToast.showToast(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<GameModel> call, Throwable t) {
//                        dismiss();
                    }
                });
    }

    private String change(int value) {
        String s = "";
        if (value < 1000) {
            s = String.valueOf(value) + "   人在玩";
        } else if (value < 10000) {
            double val = value * 1.0 / 1000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "K  人在玩";
        } else if (value < 100000) {
            double val = value * 1.0 / 10000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        } else if (value < 1000000) {
            double val = value * 1.0 / 100000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        } else if (value < 10000000) {
            double val = value * 1.0 / 1000000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        }
        return s;
    }

    public static void ToGameDetail(Context context, int id) {
        Intent intent = new Intent(context, GameDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.ID, id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    class accessTask extends AsyncTask<Void, Integer, Boolean> {


        /**
         * 这个方法会在后台任务开始执行之间调用，用于进行一些界面上的初始化操作，
         * 比如显示一个进度条对话框等。
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 这个方法中的所有代码都会在子线程中运行，我们应该在这里去处理所有的耗时任务。
         * 任务一旦完成就可以通过return语句来将任务的执行结果进行返回，如果AsyncTask的
         * 第三个泛型参数指定的是Void，就可以不返回任务执行结果。注意，在这个方法中是不
         * 可以进行UI操作的，如果需要更新UI元素，比如说反馈当前任务的执行进度，可以调用
         * publishProgress(Progress...)方法来完成。
         */
        @Override
        protected Boolean doInBackground(Void... params) {


            RetrofitManager.getInstance(GameDetailActivity.this).createReq(API.class)
                    .gameAccess(SpUtil.getInstance(GameDetailActivity.this).getString(Constant.TOKEN), gameid)
                    .enqueue(new Callback<BaseModel>() {
                        @Override
                        public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {

                        }

                        @Override
                        public void onFailure(Call<BaseModel> call, Throwable t) {

                        }
                    });

            // publishProgress();
            return null;
        }

        /**
         * 当在后台任务中调用了publishProgress(Progress...)方法后，这个方法就很快会被调用，
         * 方法中携带的参数就是在后台任务中传递过来的。在这个方法中可以对UI进行操作，利用参
         * 数中的数值就可以对界面元素进行相应的更新。
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用。返回的数据
         * 会作为参数传递到此方法中，可以利用返回的数据来进行一些UI操作，比如说提醒任务执行
         * 的结果，以及关闭掉进度条对话框等。
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }
}
