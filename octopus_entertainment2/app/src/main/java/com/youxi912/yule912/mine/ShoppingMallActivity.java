package com.youxi912.yule912.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.youxi912.yule912.R;

import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.util.ActivityCollector;

public class ShoppingMallActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_mall;
    }

    @Override
    public void initView() {
        AppCompatImageView toCharge = findViewById(R.id.img_charge);
        toCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingMallActivity.this, ChargeCenterActivity.class));
            }
        });
        findViewById(R.id.img_back_shopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
    }
}
