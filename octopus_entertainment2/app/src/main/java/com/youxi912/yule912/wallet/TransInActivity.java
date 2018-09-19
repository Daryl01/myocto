package com.youxi912.yule912.wallet;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.vondear.rxfeature.tool.RxQRCode;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.SpUtil;

public class TransInActivity extends BaseActivity {
    AppCompatImageView qrcodeIv;
    AppCompatTextView gameIDTv;
    UserLoginModel.DataBean dataBean;
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
        return R.layout.activity_trans_in;
    }

    @Override
    public void initView() {
        qrcodeIv=findViewById(R.id.img_qrcode_trans_in);
        qrcodeIv.setImageBitmap(RxQRCode.creatQRCode(dataBean.getGameID()));

        gameIDTv=findViewById(R.id.gameid);
        gameIDTv.setText(dataBean.getGameID());
        AppCompatImageView back = findViewById(R.id.img_back_trans_in);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        dataBean = SpUtil.getInstance(this).getObject(Constant.USER_INFO);
    }
}
