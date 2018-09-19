package com.youxi912.yule912.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.UserInfor;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.ActivityCollector;
import com.youxi912.yule912.util.SpUtil;

public class H5Activity extends BaseActivity implements View.OnClickListener {
    private String TAG = "H5Activity";
    private WebView gameWebView;
    private String rootUrl = "";
    static Activity instance;
    private ProgressBar progress;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_h5;
    }

    @Override
    public void initView() {
        instance = this;
        AppCompatImageView more = findViewById(R.id.tv_more_h5);
        AppCompatTextView close = findViewById(R.id.tv_close_h5);
        constraintLayout = findViewById(R.id.constrain_h5);
        progress = findViewById(R.id.progressBar);
        more.setOnClickListener(this);
        close.setOnClickListener(this);
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_close_h5:
                finish();
                break;
            case R.id.tv_more_h5:
                break;
        }
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            rootUrl = bundle.getString(Constant.ROOT_URL);
        }
    }


    @SuppressLint("JavascriptInterface")
    private void init() {
        //com.tencent.smtt.sdk.WebView
        gameWebView = findViewById(R.id.game_context);
        gameWebView.loadUrl(rootUrl);
        WebSettings webSettings = gameWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
//        设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
//若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        gameWebView.addJavascriptInterface(new JSInterface(), "control");

        gameWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.e(TAG, "shouldOverrideUrlLoading: " + url);
                boolean isGameUrl = url.indexOf("http://h5.wuzhiyou.com/game/api") != -1;
                boolean isWxpay = url.indexOf("type=wxpay") != -1;
                boolean isAlipay = url.indexOf("type=alipay") != -1;
                if (isGameUrl && isAlipay) {
                    Log.i(TAG, "the url ->" + url);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (isGameUrl && isWxpay) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;

                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                Log.e(TAG, "onPageFinished: " + url);
                // 这个方法有可能会多次执行
                super.onPageFinished(view, url);
//                if(url.startsWith("http://h5.wuzhiyou.com/game/gameStart?"))
//                {
//                    Context context = H5Activity.this;
//                    String net=DeviceInfoTool.getNet(context);
//                    int version=DeviceInfoTool.getOS_version();
//                    String idString=DeviceInfoTool.getDeviceId(context);
//                    String typeString=DeviceInfoTool.getDeviceType();
//                    view.loadUrl("javascript:setNet('"+net+"','"+version+"','"+idString+"','"+typeString+"')");
//
//                }
            }


            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        gameWebView.setWebChromeClient(new WebChromeClient() {

            @Override

            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                if (newProgress == 100)
                    progress.setVisibility(View.GONE);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, JsPromptResult result) {
                // TODO Auto-generated method stub
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        gameWebView.loadUrl(rootUrl);
        gameWebView.requestFocus();
    }

    @Override
    protected void onDestroy() {
        gameWebView.removeAllViews();
        gameWebView.destroy();
        ActivityCollector.getInstance().removeActivity(this);
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && gameWebView.canGoBack()) {
            gameWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void toH5Activity(Context context, String url) {
        Intent intent = new Intent(context, H5Activity.class);
        Bundle bundle = new Bundle();
        UserLoginModel.DataBean dataBean = SpUtil.getInstance(context).getObject(Constant.USER_INFO);
        url = url + "?userid=" + dataBean.getUserID() + "&accounts=" + SpUtil.getInstance(context).getString(Constant.ACCOUNT)
                + "&password=" + SpUtil.getInstance(context).getString(Constant.PASSWORD)
                + "&score=" + dataBean.getGold();
        bundle.putString(Constant.ROOT_URL, url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public class JSInterface {

        @JavascriptInterface
        public void hide() {
            constraintLayout.setVisibility(View.GONE);
        }

        @JavascriptInterface
        public void pageFinish() {
            onBackPressed();
        }

        @JavascriptInterface
        public UserInfor getUserInfor() {
            UserLoginModel.DataBean bean = SpUtil.getInstance(H5Activity.this).getObject(Constant.ACCOUNT);
            return new UserInfor(bean.getGameID(), bean.getToken());
        }
    }


}
