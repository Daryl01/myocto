package com.youxi912.yule912.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.team.model.CreateTeamResult;
import com.vondear.rxtool.RxAppTool;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.home.fragment.FriendFragment;
import com.youxi912.yule912.home.fragment.GameFragment;
import com.youxi912.yule912.home.fragment.MineFragment;
import com.youxi912.yule912.home.fragment.WalletFragment;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.model.UpgradeModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.DownloadManagerUtil;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.util.Utils;
import com.youxi912.yule912.session.TeamCreateHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {
    private DownloadManagerUtil downloadManagerUtil;
    long downloadId = 0;

    private GameFragment gameFragment;
    private FriendFragment friendFragment;
    private WalletFragment walletFragment;
    private MineFragment mineFragment;
    private BottomNavigationBar bottomNavigationBar;
    private Fragment[] fragments = new Fragment[4];
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.yule912.charge_success".equals(intent.getAction())) {
                if (walletFragment != null ) {
                    walletFragment.chargeSuccess(intent.getIntExtra(Constant.CHARGE_AMOUNT, 0));
                }
            } else if ("com.yule912.buy_success".equals(intent.getAction())) {
                if(mineFragment != null) {
                    mineFragment.buySuccess();
                }
            } else if ("com.yule912.sign_success".equals(intent.getAction())) {
                if (walletFragment != null )
                {
                    walletFragment.signSuccess(intent.getIntExtra(Constant.SIGN_AMOUNT, 0));
                }
                if(mineFragment != null)
                {
                    mineFragment.SignSuccess();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate--------------", "HomeActivity.onCreate");
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart--------------", "HomeActivity.onStart");
        initData();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.e("onRestart--------------", "HomeActivity.onRestart");
    }

    public static void logout(Context context, boolean quit) {
        Intent extra = new Intent();
        extra.putExtra("APP_QUIT", quit);
        start(context, extra);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, LoginInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        getUserInfor();
        bottomNavigationBar = findViewById(R.id.bottom_navigation);
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)   //固定模式：未选中的Item显示文字，无切换动画效果。
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)  //静态样式 点击无波纹效果
                .setActiveColor("#007AFF") //选中颜色
                .setInActiveColor("#A1A1A1") //未选中颜色
                .setBarBackgroundColor("#F5F5F5");
        gameFragment = new GameFragment();
        friendFragment = new FriendFragment();
        walletFragment = new WalletFragment();
        mineFragment = new MineFragment();
        fragments[0] = gameFragment;
        fragments[1] = friendFragment;
        fragments[2] = walletFragment;
        fragments[3] = mineFragment;
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (fragments[position].isAdded()) {
                    getSupportFragmentManager().beginTransaction().show(fragments[position]).commitAllowingStateLoss();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.container, fragments[position]).commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabUnselected(int position) {
                getSupportFragmentManager().beginTransaction().hide(fragments[position]).commit();
            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.bottom_icon_game, "游戏"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_icon_friend, "朋友"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_icon_purse, "钱包"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_icon_mine, "我的"))
                .setFirstSelectedPosition(0)
                .initialise();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragments[0]).commit();
        IntentFilter intentFilter = new IntentFilter("com.yule912.charge_success");
        intentFilter.addAction("com.yule912.sign_success");
        intentFilter.addAction("com.yule912.buy_success");
        registerReceiver(receiver, intentFilter);

        //检查更新
        RetrofitManager.getInstance(this).createReq(API.class).queryUpgrade("c65f01024c6f2c52e12cc3f0c9da9c0a", "android").enqueue(new Callback<UpgradeModel>() {
            @Override
            public void onResponse(Call<UpgradeModel> call, Response<UpgradeModel> response) {
                UpgradeModel upgradeModel = response.body();
                if (upgradeModel == null) {
                    return;
                }
                String leastVersion = upgradeModel.getVersionShort();//获取最新版本号
                String curVersion = RxAppTool.getAppVersionName(getApplicationContext());


                if (Utils.compareVersion(leastVersion, curVersion) > 1) {
                    showUpgradeDialog(upgradeModel);
                }
            }

            @Override
            public void onFailure(Call<UpgradeModel> call, Throwable t) {

            }
        });
    }

    private void showUpgradeDialog(UpgradeModel model) {
        new AlertDialog.Builder(this)
                .setTitle("发现新版本")
                .setCancelable(false)
                .setMessage(model.getChangelog())
                .setNegativeButton("取消", null)
                .setPositiveButton("更新", (dialog, which) -> downApk(model)).create().show();
    }

    private void downApk(UpgradeModel model) {
        downloadManagerUtil = new DownloadManagerUtil(HomeActivity.this);
        if (downloadId != 0) {
            downloadManagerUtil.clearCurrentTask(downloadId);
        }
        downloadId = downloadManagerUtil.download(model.getInstallUrl(),
                RxAppTool.getAppName(HomeActivity.this), model.getChangelog());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy--------------", "HomeActivity.onDestroy");
        unregisterReceiver(receiver);
    }

    @Override
    public void initData() {
//        //游戏模块数据获取
//        RetrofitManager.getInstance(this).createReq(API.class)
//                .getCombineGame(SpUtil.getInstance(this).getString(Constant.TOKEN))
//                .enqueue(new Callback<CombineGameModel>() {
//                    @Override
//                    public void onResponse(Call<CombineGameModel> call, Response<CombineGameModel> response) {
//                        if (response.isSuccessful()) {
//                            CombineGameModel model = response.body();
//                            if (model.getCode() == Constant.OK && (model != null || model.getData() != null)) {
//                                dataBean = model.getData();
//                                SpUtil.getInstance(HomeActivity.this).putObject(Constant.DATA_HOT_GAME, dataBean.getHotGameModel());
//                                SpUtil.getInstance(HomeActivity.this).putObject(Constant.DATA_EXPENSE_GAME, dataBean.getExpenseGameModel());
//                                SpUtil.getInstance(HomeActivity.this).putObject(Constant.DATA_SUGGEST_GAME, dataBean.getSuggestGameModel());
//                                SpUtil.getInstance(HomeActivity.this).putObject(Constant.DATA_RECENTLY_GAME, dataBean.getRecentyGameModel());
//                                SpUtil.getInstance(HomeActivity.this).putObject(Constant.DATA_MINI_GAME, dataBean.getMiniGameModel());
//                                SpUtil.getInstance(HomeActivity.this).putObject(Constant.DATA_BANNER, dataBean.getBannerModel());
//
//                                if (gameFragment != null) {
//                                    gameFragment.RefrashData();
//                                }
//                            } else {
//                                RxToast.error(model.getMsg());
//                                if (model.getCode() == Constant.OUT_TIME)
//                                    reLogin();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<CombineGameModel> call, Throwable t) {
//                        RxToast.error(t.getMessage());
//                    }
//                });

    }

    private void getUserInfor() {
        RetrofitManager.getInstance(this).createReq(API.class)
                .getUserInfo(SpUtil.getInstance(this).getString(Constant.TOKEN))
                .enqueue(new Callback<UserLoginModel>() {
                    @Override
                    public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                        if (response.isSuccessful()) {
                            UserLoginModel model = response.body();
                            if (model == null || model.getData() == null) {
                                RxToast.error("个人信息为空!");
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                UserLoginModel.DataBean bean = model.getData();
                                SpUtil.getInstance(HomeActivity.this).putObject(Constant.USER_INFO, bean);
                            } else {
                                RxToast.error(model.getMsg());
                                if (model.getCode() == Constant.OUT_TIME)
                                    reLogin();
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLoginModel> call, Throwable t) {
                        RxToast.error(t.getMessage());
                    }
                });
    }


    private long exitTime;

    /**
     * 双击返回键退出
     *
     * @param keyCode 触摸事件code
     * @param event   触摸事件
     * @return 返回值
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, R.string.click_again_will_exit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                if (selected != null && !selected.isEmpty()) {
                    TeamCreateHelper.createNormalTeam(HomeActivity.this, selected, true, new RequestCallback<CreateTeamResult>() {
                        @Override
                        public void onSuccess(CreateTeamResult param) {

                        }

                        @Override
                        public void onFailed(int code) {

                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
                } else {
                    RxToast.error("请选择至少一个联系人！");
                }
            }else if (requestCode == 2) {
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                TeamCreateHelper.createAdvancedTeam(HomeActivity.this, selected);
            }else if (requestCode == 3) {
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                if (selected != null && !selected.isEmpty()) {
                    TeamCreateHelper.createNormalTeam(HomeActivity.this, selected, false, null);
                } else {
                    RxToast.error("请选择至少一个联系人！");
                }
            }
        }
    }
}
