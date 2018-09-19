package com.youxi912.yule912.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.vondear.rxfeature.module.wechat.pay.WechatPayModel;
import com.vondear.rxfeature.module.wechat.pay.WechatPayTools;
import com.vondear.rxtool.RxEncodeTool;
import com.vondear.rxtool.RxImageTool;
import com.vondear.rxtool.RxPhotoTool;
import com.vondear.rxtool.interfaces.OnSuccessAndErrorListener;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialog;
import com.vondear.rxui.view.dialog.RxDialogChooseImage;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.home.QRImgActivity;
import com.youxi912.yule912.login_and_register.LoginInActivity;
import com.youxi912.yule912.mine.AccountDetailActivity;
import com.youxi912.yule912.mine.CheckInActivity;
import com.youxi912.yule912.mine.EveryDayActivity;
import com.youxi912.yule912.mine.MyPacketActivity;
import com.youxi912.yule912.mine.ShoppingMallActivity;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.MineItemModel;
import com.youxi912.yule912.model.OrderModel;
import com.youxi912.yule912.model.QRImgModel;
import com.youxi912.yule912.model.UploadAvaterModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.BmpUtils;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;
import com.youxi912.yule912.view.GlideCircleTransform;
import com.youxi912.yule912.view.StarBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.vondear.rxui.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

@RuntimePermissions
public class MineFragment extends Fragment {
    private List<MineItemModel> models;
    //    private ProgressDialog progressDialog;
    private UserLoginModel.DataBean dataBean;
    private RxDialog giftDialog, payDialog;
    private CommonAdapter adapter;
    private AppCompatImageView signStatus;
    private AppCompatTextView level, name;
    private AppCompatImageView portrait;
    private Uri resultUri;
    private AppCompatTextView sex;
    private AppCompatImageView img_sex;

    public void buySuccess() {
        payDialog.dismiss();
        giftDialog.dismiss();
        RxToast.success("微信支付成功!欢迎加入章鱼会员!");
        level.setText("章鱼矿工");
        level.setTextColor(Color.parseColor("#007AFF"));
        name.setTextColor(Color.parseColor("#007AFF"));
        //会员等级信息变更
        dataBean.setDJ("5");

    }

    public void SignSuccess() {
        signStatus.setImageResource(R.mipmap.icon_signed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("正在退出当前帐号,请稍候..");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, container, false);
        signStatus = view.findViewById(R.id.img_sign_status);
        signStatus.setImageResource(R.mipmap.icon_nosign);
        if ("0".equals(dataBean.getIsSigned())) {
            signStatus.setImageResource(R.mipmap.icon_nosign);
        } else {
            signStatus.setImageResource(R.mipmap.icon_signed);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_mine);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        StarBar starBar = view.findViewById(R.id.startBar_mine);
        starBar.setStarMark(Integer.parseInt(dataBean.getStartCount()));
        recyclerView.setLayoutManager(manager);
        adapter = new CommonAdapter<MineItemModel>(getContext(), models, R.layout.item_mine) {
            @Override
            public void convert(CommonViewHolder holder, MineItemModel item) {
                holder.setText(R.id.item_mine_tv, item.getText());
                Glide.with(getContext()).load(item.getResourceId())
                        .into((AppCompatImageView) holder.getView(R.id.item_mine_icon));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getContext(), AccountDetailActivity.class));
                        break;
                    case 1:
                        showGift();
                        break;
                    case 2:
                        startActivityForResult(new Intent(getContext(), CheckInActivity.class)
                                , 6666);
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), EveryDayActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getContext(), ShoppingMallActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getContext(), MyPacketActivity.class));
                        break;
                    case 6:
                        showQrCode();
                        break;
                    case 7:
                        selectFunc();
                        break;
                    case 8:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        dataBean = SpUtil.getInstance(getContext()).getObject(Constant.USER_INFO);
        if (dataBean != null) {
            name = view.findViewById(R.id.tv_nickName_mine);
            AppCompatTextView id = view.findViewById(R.id.tv_id_mine);
            AppCompatTextView recommend = view.findViewById(R.id.tv_recommend_mine);
            sex = view.findViewById(R.id.tv_sex);
            img_sex = view.findViewById(R.id.img_sex);
            level = view.findViewById(R.id.tv_level_mine);
            portrait = view.findViewById(R.id.img_head_portrait_mine);
            portrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MineFragmentPermissionsDispatcher.initDialogChooseImageWithPermissionCheck(MineFragment.this);
                }
            });
            LinearLayoutCompat linearLayoutCompat = view.findViewById(R.id.ll_sex_mine);
            linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectSex();
                }
            });
            if (!TextUtils.isEmpty(dataBean.getTxImg()))
                Glide.with(getContext()).load(dataBean.getTxImg())
                        .apply(new RequestOptions().centerCrop().transform(new GlideCircleTransform(getContext()))
                                .error(R.mipmap.icon_avatar))
                        .into(portrait);
            else
                Glide.with(getContext()).load(R.mipmap.icon_avatar).apply(new RequestOptions().centerCrop().transform(new GlideCircleTransform(getContext())))
                        .into(portrait);
            name.setText("昵称:" + dataBean.getNickName());
            if (TextUtils.isEmpty(dataBean.getIntroducer_name())) {
                recommend.setText("推荐人:未知");
            } else {
                recommend.setText("推荐人:" + dataBean.getIntroducer_name());
            }
            if (dataBean.getGender() == 1) {//---男
                sex.setText("男");
                img_sex.setImageResource(R.mipmap.icon_man);
            } else {
                sex.setText("女");
                img_sex.setImageResource(R.mipmap.icon_woman);
            }
            id.setText("ID: " + dataBean.getGameID());
            if ("0".equals(dataBean.getMemberOrder())) {
                level.setText("章鱼宝宝");
                level.setTextColor(Color.parseColor("#6CC166"));
                name.setTextColor(Color.parseColor("#6CC166"));
            } else {
                if ("0".equals(dataBean.getDJ())) {
                    level.setText("章鱼矿工");
                    level.setTextColor(Color.parseColor("#007AFF"));
                    name.setTextColor(Color.parseColor("#007AFF"));
                } else if ("1".equals(dataBean.getDJ())) {
                    level.setText("以太矿工");
                    level.setTextColor(Color.parseColor("#9162FF"));
                    name.setTextColor(Color.parseColor("#9162FF"));
                } else {
                    level.setText("比特矿工");
                    level.setTextColor(Color.parseColor("#FF8402"));
                    name.setTextColor(Color.parseColor("#FF8402"));
                }
            }
        }
        return view;
    }

    private void showGift() {
        giftDialog = new RxDialog(getContext());
        giftDialog.setFullScreen();
        View giftView = LayoutInflater.from(getContext()).inflate(R.layout.gift_layout, null, false);
        AppCompatImageView close = giftView.findViewById(R.id.img_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giftDialog.dismiss();
            }
        });
        AppCompatButton btn_get = giftView.findViewById(R.id.btn_get_gift);
        AppCompatTextView text_gift_paid = giftView.findViewById(R.id.gift_paid);

        //判断是否是会员
        if (!"5".equals(dataBean.getMemberOrder())) {
            //不是会员
            text_gift_paid.setVisibility(View.GONE);

            btn_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payDialog = new RxDialog(getContext());
                    View customView = LayoutInflater.from(getContext()).inflate(R.layout.pay_layout, null, true);
                    AppCompatImageView close = customView.findViewById(R.id.img_charge_close);
                    AppCompatImageView ZFB = customView.findViewById(R.id.img_charge_ZFB);
                    AppCompatImageView WX = customView.findViewById(R.id.img_charge_WX);
                    AppCompatTextView tv_agreement = customView.findViewById(R.id.tv_agreement);
                    final AppCompatCheckBox box = customView.findViewById(R.id.cb_agreement);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payDialog.dismiss();
                        }
                    });
                    ZFB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RxToast.showToast("该功能暂未开放,敬请期待");
                        }
                    });
                    WX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (box.isChecked()) {
                                payByWX();
                            } else {
                                RxToast.error("请先同意用户协议");
                            }
                        }
                    });
                    tv_agreement.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            View agreementView = LayoutInflater.from(getContext()).inflate(R.layout.agreement, null, false);
                            AppCompatButton buy = agreementView.findViewById(R.id.btn_buy);
                            final RxDialog agree = new RxDialog(getContext());
                            agree.setContentView(agreementView);
                            buy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    agree.dismiss();
                                }
                            });
                            agree.setCanceledOnTouchOutside(true);
                            agree.show();
                        }
                    });
                    //aileen 暂不支持 先隐藏
                    ZFB.setVisibility(View.GONE);
                    LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(RxImageTool.dp2px(300f), LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    payDialog.setContentView(customView, params);
                    payDialog.show();
                }
            });

        } else {//不是会员
            btn_get.setVisibility(View.GONE);
        }
        giftDialog.setContentView(giftView);
        giftDialog.setCanceledOnTouchOutside(true);
        giftDialog.show();
    }

    private void selectSex() {
        final RxDialog sexDialog = new RxDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pick_sex, null, false);
        AppCompatTextView sex_man = view.findViewById(R.id.tv_man);
        AppCompatTextView sex_woman = view.findViewById(R.id.tv_woman);
        AppCompatTextView cancel = view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexDialog.dismiss();
            }
        });
        sex_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexDialog.dismiss();
                setSex(1);
            }
        });
        sex_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexDialog.dismiss();
                setSex(0);
            }
        });
        sexDialog.setContentView(view);
        WindowManager.LayoutParams params = sexDialog.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        sexDialog.show();
    }

    private void setSex(final int gender) {
        RetrofitManager.getInstance(getContext()).createReq(API.class).setUserGender(
                SpUtil.getInstance(getContext()).getString(Constant.TOKEN), String.valueOf(gender))
                .enqueue(new Callback<BaseModel>() {
                    @Override
                    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
//                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            BaseModel model = response.body();
                            if (model == null) {
                                RxToast.error("啊哦,服务器发生未知异常");
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                RxToast.success("性别设置成功!");
                                if (gender == 0) {
                                    sex.setText("女");
                                    img_sex.setImageResource(R.mipmap.icon_woman);
                                    dataBean.setGender(0);
                                } else {
                                    sex.setText("男");
                                    img_sex.setImageResource(R.mipmap.icon_man);
                                    dataBean.setGender(1);
                                }
                                SpUtil.getInstance(getContext()).putObject(Constant.USER_INFO, dataBean);
                            } else {
                                RxToast.error(model.getMsg());
                                if (model.getCode() == Constant.OUT_TIME) {
                                    NIMClient.getService(AuthService.class).logout();
                                    SpUtil.getInstance(getContext()).clear();
                                    ActivityCollector.getInstance().finishAll();
                                    startActivity(new Intent(getContext(), LoginInActivity.class));
                                }
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }


    private void showQrCode() {
        if (!"5".equals(dataBean.getMemberOrder())) {
            RxToast.error("您还未成为章鱼矿工!");
        } else {
            RetrofitManager.getInstance(getContext()).createReq(API.class).getQRImg(
                    SpUtil.getInstance(getContext()).getString(Constant.TOKEN)).enqueue(new Callback<QRImgModel>() {
                @Override
                public void onResponse(Call<QRImgModel> call, Response<QRImgModel> response) {
                    QRImgModel qrImgModel = response.body();
                    if (qrImgModel == null) {
                        return;
                    }
                    if (qrImgModel.getCode() == 200) {
                        QRImgModel.Data data = qrImgModel.getData();
                        byte[] bytes = Base64.decode(data.image, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        BmpUtils.saveBitmap(QRImgActivity.IMAGE_PATH, bitmap);

                        if (getActivity() != null) {
                            Intent intent = new Intent(getActivity(), QRImgActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        RxToast.showToast(qrImgModel.getMsg());
                    }
                }

                @Override
                public void onFailure(Call<QRImgModel> call, Throwable t) {
//                    progressDialog.dismiss();
                }
            });
        }
    }

    private void selectFunc() {
        final RxDialog dialog = new RxDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pick_account, null, false);
        AppCompatTextView tv_change = view.findViewById(R.id.tv_change);
        AppCompatTextView tv_exit = view.findViewById(R.id.tv_exit);
        AppCompatTextView cancel = view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = SpUtil.getInstance(getContext()).getString(Constant.TOKEN);
                RetrofitManager.getInstance(getContext()).createReq(API.class)
                        .userLogout(token)
                        .enqueue(new Callback<BaseModel>() {
                            @Override
                            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                            }

                            @Override
                            public void onFailure(Call<BaseModel> call, Throwable t) {
                            }
                        });
                ActivityCollector.getInstance().finishAll();
                SpUtil.getInstance(getContext()).clear();
                NIMClient.getService(AuthService.class).logout();
                SpUtil.getInstance(getContext()).putBoolean(Constant.HAS_LOGIN, false);
                startActivity(new Intent(getContext(), LoginInActivity.class));
                dialog.dismiss();
            }
        });
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.getInstance().finishAll();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        dialog.show();
    }
    private void changeAccount(int position) {

        final RxDialogSureCancel dialog = new RxDialogSureCancel(getContext());
        dialog.setTitle("温馨提示");
        if (position==0){
            dialog.setContent("确认退出平台吗");
        }else {
            dialog.setContent("切换账号将退出当前帐号\n确认切换账号吗");
        }
        dialog.setCancel("取消");
        dialog.setSure("确认");
        dialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = SpUtil.getInstance(getContext()).getString(Constant.TOKEN);
                RetrofitManager.getInstance(getContext()).createReq(API.class)
                        .userLogout(token)
                        .enqueue(new Callback<BaseModel>() {
                            @Override
                            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                            }

                            @Override
                            public void onFailure(Call<BaseModel> call, Throwable t) {
                            }
                        });
                ActivityCollector.getInstance().finishAll();
                SpUtil.getInstance(getContext()).clear();
                NIMClient.getService(AuthService.class).logout();
                SpUtil.getInstance(getContext()).putBoolean(Constant.HAS_LOGIN, false);
                startActivity(new Intent(getContext(), LoginInActivity.class));
                dialog.dismiss();
            }
        });
        dialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private boolean isWechatAvailable() {
        //检测手机上是否安装了微信
//        try {
//            getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }

        final PackageManager packageManager = getContext().getPackageManager();                          // 获取packagemanager
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

    private void payByWX() {
        if (!isWechatAvailable()) {
            RxToast.error("请先安装微信");
            return;
        }
//        progressDialog.setMessage("正在生成订单,请稍候...");
//        progressDialog.show();
        RetrofitManager.getInstance(getContext()).createReq(API.class)
                .createOrder(SpUtil.getInstance(getContext()).getString(Constant.TOKEN), 0, 11)
                .enqueue(new Callback<OrderModel>() {
                    @Override
                    public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
//                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            final OrderModel model = response.body();
                            if (model == null) {
                                RxToast.error("生成订单失败");
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                payDialog.dismiss();
                                WechatPayModel wechatPayModel = new WechatPayModel(model.getData().getAppid(),
                                        model.getData().getMch_id(), model.getData().getPrepay_id(),
                                        "Sign=WXPay", model.getData().getNonce_str(),
                                        model.getData().getTimestamp(), model.getData().getSign());
                                WechatPayTools.doWXPay(getContext(), model.getData().getAppid(), (new Gson()).toJson(wechatPayModel), new OnSuccessAndErrorListener() {
                                    @Override
                                    public void onSuccess(String s) {
                                    }

                                    @Override
                                    public void onError(String s) {

                                    }
                                });
                            } else {
                                RxToast.error(model.getMsg());
                                if (model.getCode() == Constant.OUT_TIME) {
                                    SpUtil.getInstance(getContext()).clear();
                                    ActivityCollector.getInstance().finishAll();
                                    NIMClient.getService(AuthService.class).logout();
                                    startActivity(new Intent(getContext(), LoginInActivity.class));
                                }
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBean = SpUtil.getInstance(getContext()).getObject(Constant.USER_INFO);
        models = new ArrayList<>();
        MineItemModel model1 = new MineItemModel(R.mipmap.icon_account_detail, "账户明细");//1
        MineItemModel model3 = new MineItemModel(R.mipmap.icon_everyday_book, "每日签到");//3
        MineItemModel model4 = new MineItemModel(R.mipmap.icon_day_market, "每日行情");//4
        MineItemModel model5 = new MineItemModel(R.mipmap.icon_game_mall, "游戏商城");//5
        MineItemModel model6 = new MineItemModel(R.mipmap.icon_account_security, "账号安全");//
        MineItemModel model2 = new MineItemModel(R.mipmap.icon_welfare_center, "章鱼大礼包");//2
        MineItemModel model8 = new MineItemModel(R.mipmap.icon_mine_qrcode, "专属二维码");//7
        MineItemModel model7 = new MineItemModel(R.mipmap.icon_account_switch, "账号切换");//8
        MineItemModel model9 = new MineItemModel(R.mipmap.icon_backpack, "背包");//6

        models.add(model1);
        models.add(model2);
        models.add(model3);
        models.add(model4);
        models.add(model5);
//        models.add(model6);
        models.add(model9);
        models.add(model8);
        models.add(model7);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data.getData());
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.head_portrait)
                        //异常占位图(当加载异常的时候出现的图片)
                        .error(R.mipmap.head_portrait)
                        //禁止Glide硬盘缓存缓存
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                Glide.with(getContext()).
                        load(RxPhotoTool.cropImageUri).
                        apply(options).
                        thumbnail(0.5f).
                        into(portrait);
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, portrait);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                Throwable cropError = UCrop.getError(data);
                RxToast.error(cropError.getMessage());
                break;
            case 666:
                if (resultCode == RESULT_OK) {
                    signStatus.setImageResource(R.mipmap.icon_signed);
                    dataBean.setIsSigned("1");
                    SpUtil.getInstance(getContext()).putObject(Constant.USER_INFO, dataBean);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @NeedsPermission({Manifest.permission.CAMERA})
    void initDialogChooseImage() {
        RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(this, TITLE);
        dialogChooseImage.show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA})
    void denied() {
        RxToast.showToast("拍照权限被拒绝了!");
        MineFragmentPermissionsDispatcher.initDialogChooseImageWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MineFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA})
    void neverAsk() {
        RxToast.showToast("没有拍照权限!");
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private void roadImageView(Uri uri, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.head_portrait)
                //异常占位图(当加载异常的时候出现的图片)
                .error(R.mipmap.head_portrait)
                .transform(new CircleCrop())
                //禁止Glide硬盘缓存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(getContext()).
                load(uri).
                apply(options).
                thumbnail(0.5f).
                into(imageView);
        File file = new File(RxPhotoTool.getImageAbsolutePath(getContext(), uri));
        final File targetFile = CompressHelper.getDefault(getContext()).compressToFile(file);
        String avatarStr = exchange(targetFile);
        RetrofitManager.getInstance(getContext()).createReq(API.class).uploadAvatar(
                SpUtil.getInstance(getContext()).getString(Constant.TOKEN), avatarStr)
                .enqueue(new Callback<UploadAvaterModel>() {
                    @Override
                    public void onResponse(Call<UploadAvaterModel> call, Response<UploadAvaterModel> response) {
                        if (response.isSuccessful()) {
                            UploadAvaterModel model = response.body();
                            if (model == null) {
                                RxToast.error("啊哦,服务器发生未知异常了");
                                return;
                            }
                            if (model.getCode() == Constant.OK) {
                                RxToast.success("头像上传成功");
                                targetFile.delete();
                                dataBean.setTxImg(model.getData().getAvatar_url());
                                SpUtil.getInstance(getContext()).putObject(Constant.USER_INFO, dataBean);
                            } else {
                                RxToast.error(model.getMsg());
                                if (model.getCode() == Constant.OUT_TIME) {
                                    NIMClient.getService(AuthService.class).logout();
                                    SpUtil.getInstance(getContext()).clear();
                                    ActivityCollector.getInstance().finishAll();
                                    startActivity(new Intent(getContext(), LoginInActivity.class));
                                }
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadAvaterModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });

    }

    private String exchange(File file) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RxEncodeTool.base64Encode2String(data);
    }

    //裁剪图片
    private void initUCrop(Uri uri) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getContext().getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(getContext(), R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(getContext(), R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setCircleDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(getContext(), this);
    }
}
