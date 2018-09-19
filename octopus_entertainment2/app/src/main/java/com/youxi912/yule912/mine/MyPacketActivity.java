package com.youxi912.yule912.mine;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.R;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.view.SpacesItemDecoration;

public class MyPacketActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_packet;
    }

    @Override
    public void initView() {
        AppCompatTextView back = findViewById(R.id.tv_packet_back);
        AppCompatImageView img1 = findViewById(R.id.treasure_box_1);
        AppCompatImageView img2 = findViewById(R.id.treasure_box_2);
        AppCompatImageView img3 = findViewById(R.id.treasure_box_3);
        back.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.rv_packet);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        CommonAdapter adapter = new CommonAdapter<String>(this, null, R.layout.item_packet) {
            @Override
            public void convert(CommonViewHolder holder, String item) {

            }

            @Override
            public int getItemCount() {
                return 9;
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(14));
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_packet_back:
                onBackPressed();
                break;
            case R.id.treasure_box_1:
            case R.id.treasure_box_2:
            case R.id.treasure_box_3:
                RxToast.showToast("该功能暂未开放,敬请期待");
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }
}
