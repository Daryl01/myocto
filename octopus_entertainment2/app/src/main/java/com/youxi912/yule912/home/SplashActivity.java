package com.youxi912.yule912.home;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.CustomVideoView;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // TODO: 2018/8/9 0009 跳转逻辑处理
//                if (!SpUtil.getInstance(SplashActivity.this).getBoolean(Constant.HAS_LOGIN, false)) {
//                    startActivity(new Intent(SplashActivity.this, LoginInActivity.class));
//                } else {
//                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                }
//                finish();
//            }
//        }, 3000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_layout;
    }

    @Override
    public void initView() {
        CustomVideoView videoview = findViewById(R.id.customView);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                SplashActivityPermissionsDispatcher.jumpWithPermissionCheck(SplashActivity.this);
            }
        });
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void jump() {
        if (!SpUtil.getInstance(SplashActivity.this).getBoolean(Constant.HAS_LOGIN, false)) {
            startActivity(new Intent(SplashActivity.this, LoginInActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }
        finish();
    }

    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denied() {
        RxToast.showToast("权限被拒绝了!");
        SplashActivityPermissionsDispatcher.jumpWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void neverAsk() {
        RxToast.showToast("不在访问了!");
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
