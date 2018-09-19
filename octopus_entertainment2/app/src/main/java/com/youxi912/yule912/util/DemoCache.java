package com.youxi912.yule912.util;

import android.app.Activity;
import android.content.Context;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.youxi912.yule912.live.server.entity.RoomInfoEntity;

/**
 * Created by jezhee on 2/20/15.
 */
public class DemoCache {

    private static Activity visibleActivity; //处于 onResume~onPause生命周期内的Activity

    private static Context context;

    private static String account;
    private static String sid;
    //视频云点播服务 token
    private static String vodtoken;

    //云信服务 token
    private static String token;

    //视频云点播服务 token

    private static NimUserInfo userInfo;

    private static RoomInfoEntity roomInfoEntity;

    private static StatusBarNotificationConfig notificationConfig;



    public static void clear() {
        account = null;
    }

    public static String getAccount() {
        return account;
    }

    private static boolean mainTaskLaunching;

    public static void setAccount(String account) {
        DemoCache.account = account;
        NimUIKit.setAccount(account);
    }

    public static void setNotificationConfig(StatusBarNotificationConfig notificationConfig) {
        DemoCache.notificationConfig = notificationConfig;
    }

    public static StatusBarNotificationConfig getNotificationConfig() {
        return notificationConfig;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DemoCache.context = context.getApplicationContext();

    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        DemoCache.token = token;
    }

    public static NimUserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(NimUserInfo userInfo) {
        DemoCache.userInfo = userInfo;
    }

    public static RoomInfoEntity getRoomInfoEntity() {
        return roomInfoEntity;
    }

    public static void setRoomInfoEntity(RoomInfoEntity roomInfoEntity) {
        DemoCache.roomInfoEntity = roomInfoEntity;
    }

    public static void setMainTaskLaunching(boolean mainTaskLaunching) {
        DemoCache.mainTaskLaunching = mainTaskLaunching;

    }

    public static boolean isMainTaskLaunching() {
        return mainTaskLaunching;
    }

    public static String getSid() {
        return sid;
    }

    public static void setSid(String sid) {
        DemoCache.sid = sid;
    }

    public static String getVodtoken() {
        return vodtoken;
    }

    public static void setVodtoken(String vodtoken) {
        DemoCache.vodtoken = vodtoken;
    }
    public static Activity getVisibleActivity() {
        return visibleActivity;
    }

    public static void setVisibleActivity(Activity visibleActivity) {
        DemoCache.visibleActivity = visibleActivity;
    }

}
