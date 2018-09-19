package com.youxi912.yule912.wxapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtool.view.RxToast;

import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.SpUtil;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;

    @Override
    protected void onResume() {
        super.onResume();
        iwxapi = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //支付成功
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                RxToast.success("支付成功");
                UserLoginModel.DataBean dataBean = SpUtil.getInstance(this).getObject(Constant.USER_INFO);
                dataBean.setMemberOrder("5");
                dataBean.setDJ("0");
                SpUtil.getInstance(this).putObject(Constant.USER_INFO, dataBean);
            }
            //出现异常
            else if (baseResp.errCode == BaseResp.ErrCode.ERR_COMM) {
                RxToast.error("支付异常，原因：" + baseResp.errCode + baseResp.errStr);
            }
            //用户取消
            else if (baseResp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                RxToast.error("用户取消支付");
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
                RxToast.error("支付失败，原因："+ baseResp.errCode + baseResp.errStr);
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {
                RxToast.error("被拒绝了");
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_UNSUPPORT) {
                RxToast.error("不支持");
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_BAN) {
                RxToast.error("Bang！！挂了，原因："+ baseResp.errCode + baseResp.errStr);
            }else{
                RxToast.info("支付状态：" + baseResp.errCode + baseResp.errStr);
            }

            onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        iwxapi = null;
        super.onDestroy();
    }
}
