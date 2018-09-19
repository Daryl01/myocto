package com.youxi912.yule912.mine;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youxi912.yule912.R;

import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.adapter.EveryDayAdapter;
import com.youxi912.yule912.model.EveryDayModel;
import com.youxi912.yule912.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

/**
 * 每日行情
 **/
public class EveryDayActivity extends BaseActivity {
    private List<EveryDayModel> datas = new ArrayList<>();
    private ProgressDialog progressDialog;
    private EveryDayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_every_day;
    }

    @Override
    public void initView() {
//        progressDialog = new ProgressDialog(this);
        RecyclerView recyclerView = findViewById(R.id.rv_every);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new EveryDayAdapter(datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData();
            }
        }, recyclerView);
        adapter.loadMoreEnd();
        adapter.setEnableLoadMore(false);
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        EveryDayModel model1 = new EveryDayModel(R.mipmap.icon_currency_ps, "PS", "0.035", "+66.66%", true);
        EveryDayModel model2 = new EveryDayModel(R.mipmap.icon_currency_btc, "BTC", "", "+0.00%", true);
        EveryDayModel model3 = new EveryDayModel(R.mipmap.icon_currency_eth, "ETH", "", "+0.00%", true);
        EveryDayModel model4 = new EveryDayModel(R.mipmap.icon_currency_bch, "BCH", "", "+0.00%", true);
        EveryDayModel model5 = new EveryDayModel(R.mipmap.icon_currency_ltc, "LTC", "", "+0.00%", true);
        EveryDayModel model6 = new EveryDayModel(R.mipmap.icon_currency_ada, "ADA", "", "+0.00%", true);
        EveryDayModel model7 = new EveryDayModel(R.mipmap.icon_currency_xrp, "XRP", "", "+0.00%", true);
        EveryDayModel model8 = new EveryDayModel(R.mipmap.icon_currency_xlm, "XLM", "", "+0.00%", true);
        EveryDayModel model9 = new EveryDayModel(R.mipmap.icon_currency_trx, "TRX", "", "+0.00%", true);
        EveryDayModel model10 = new EveryDayModel(R.mipmap.icon_currency_eos, "EOS", "", "+0.00%", true);
        EveryDayModel model11 = new EveryDayModel(R.mipmap.icon_currency_iota, "IOTA", "", "+0.00%", true);
        datas.add(model1);
        datas.add(model2);
        datas.add(model3);
        datas.add(model4);
        datas.add(model5);
        datas.add(model6);
        datas.add(model7);
        datas.add(model8);
        datas.add(model9);
        datas.add(model10);
        datas.add(model11);
    }

    private void getData() {

//        adapter.addData(datas);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }
}
