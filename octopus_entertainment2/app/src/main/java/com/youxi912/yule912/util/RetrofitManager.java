package com.youxi912.yule912.util;

import android.content.Context;

import com.vondear.rxtool.RxTool;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.BuildConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 孙学通
 * 2018.5.31
 */
public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;
    public static Context myContext;
    private Map<String, String> map = new HashMap<>();


    public static synchronized RetrofitManager getInstance(Context context) {
        myContext = context.getApplicationContext();
        if (mRetrofitManager == null) {
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    public RetrofitManager() {
        initRetrofit();
    }

    private synchronized void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
            builder.addInterceptor(loggingInterceptor);
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //登录和上传头像接口不参与验签
                if (request.url().toString().contains("user/login") || request.url().toString().contains("uploadavatar"))
                    return chain.proceed(request);
                else {
                    Request.Builder requestBuilder = request.newBuilder();
                    if (request.body() instanceof FormBody) {
                        FormBody.Builder newBuilder = new FormBody.Builder();
                        FormBody formBody = (FormBody) request.body();
                        int i = 0;
                        map.clear();
                        for (; i < formBody.size(); i++) {
                            newBuilder.addEncoded(formBody.name(i), formBody.value(i));
                            map.put(formBody.name(i), formBody.value(i));
                        }
                        map.put("app_key", "com.youxi912.yule912");
                        String sign = sortMap();
                        newBuilder.addEncoded("app_key", "com.youxi912.yule912");
                        newBuilder.addEncoded("sign", sign);
                        requestBuilder.method(request.method(), newBuilder.build());
                    }
                    Request newRequest = requestBuilder.build();
                    return chain.proceed(newRequest);
                }
            }
        });
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder().client(client)
//                .baseUrl("http://octopus.anybind.com/api/")
                .baseUrl("http://v2.912cc.com/api/")
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private synchronized String sortMap() {
        List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            //升序排序
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> mapping : list) {
            stringBuilder.append(mapping.getKey() + "=" + mapping.getValue() + "&");
        }
        stringBuilder.append("app_secret=" + Constant.APP_SECRET);
        return RxTool.Md5(stringBuilder.toString());
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
