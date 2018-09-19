package com.youxi912.yule912.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxfeature.module.wechat.share.WechatShareTools;
import com.vondear.rxtool.RxImageTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialog;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.BmpUtils;
import com.youxi912.yule912.view.MyImageView;

import java.io.File;

//专属二维码展示页面
public class QRImgActivity extends BaseActivity {
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "qrcode.png";
    private Bitmap bitmap = null;
    private RxDialog dialog;
    private MyImageView qrImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
        WechatShareTools.init(this, Constant.APP_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr_image;
    }

    @Override
    public void initView() {
        findViewById(R.id.tv_packet_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bitmap = BmpUtils.getBitmap(IMAGE_PATH);
        if (bitmap != null) {
            qrImg = findViewById(R.id.qr_img);
            qrImg.setImageBitmap(bitmap);
        }
        findView(R.id.img_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new RxDialog(QRImgActivity.this);
                View customView = LayoutInflater.from(QRImgActivity.this).inflate(R.layout.layout_share, null, false);
                LinearLayoutCompat ll_friend = customView.findViewById(R.id.ll_friend);
                LinearLayoutCompat ll_circle = customView.findViewById(R.id.ll_circle);
                ll_circle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkWeChatInstalled() || isWechatAvailable()) {
                            if (bitmap != null && !bitmap.isRecycled()) {
                                WechatShareTools.shareImage(bitmap, WechatShareTools.SharePlace.Zone);
                            }
                        } else {
                            RxToast.showToast("请先安装微信");
                        }
                        dialog.dismiss();
                    }
                });
                ll_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isWechatAvailable() || checkWeChatInstalled()) {
                            if (bitmap != null && !bitmap.isRecycled()) {
                                WechatShareTools.shareImage(bitmap, WechatShareTools.SharePlace.Friend);
                            }
                        } else {
                            RxToast.showToast("请先安装微信");
                        }
                        dialog.dismiss();
                    }
                });
                LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(RxImageTool.dp2px(300f), LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                dialog.setContentView(customView, params);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
    }

    private boolean checkWeChatInstalled() {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        return iwxapi.isWXAppInstalled();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = BmpUtils.getBitmap(IMAGE_PATH);
            qrImg.setImageBitmap(bitmap);
        }
    }
}
