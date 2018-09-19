package com.youxi912.yule912.Base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.youxi912.yule912.R;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.SpUtil;

import java.util.List;

public abstract class BaseActivity extends UI {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        setContentView(getLayoutId());
        initData();
        initView();
        registerMultyPartLogin();
    }

    public boolean isNotActive() {
        return isFinishing() || isDestroyed();
    }

    public void showConfirmDialog(String title, String message, DialogInterface.OnClickListener okEvent, DialogInterface.OnClickListener cancelEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok,
                okEvent);
        builder.setNegativeButton(R.string.cancel,
                cancelEvent);
        builder.show();
    }

    public void reLogin() {
        ActivityCollector.getInstance().finishAll();
        SpUtil.getInstance(this).clear();
        NIMClient.getService(AuthService.class).logout();
        startActivity(new Intent(this, LoginInActivity.class));
    }

    public abstract int getLayoutId();

    //判断手机手否安装了微信
    public boolean isWechatAvailable() {
        //检测手机上是否安装了微信
//        try {
//            getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }

        final PackageManager packageManager = getPackageManager();                          // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);               // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract void initView();

    /**
     * intent中传递的值
     **/
    public abstract void initData();

    /**
     * 监听账号在其他设备登录
     * 踢出操作
     ***/
    private void registerMultyPartLogin() {
        Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
            @Override
            public void onEvent(List<OnlineClient> onlineClients) {
                if (onlineClients == null || onlineClients.size() == 0) {
                    return;
                }
                OnlineClient client = onlineClients.get(0);
                switch (client.getClientType()) {
                    case ClientType.Windows:
                        // PC端
                        break;
                    case ClientType.MAC:
                        // MAC端
                        break;
                    case ClientType.Web:
                        // Web端
                        break;
                    case ClientType.iOS:
                        // IOS端
                        break;
                    case ClientType.Android:
                        // Android端
                        break;
                    default:
                        break;
                }
            }
        };

        NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, true);
    }

}
