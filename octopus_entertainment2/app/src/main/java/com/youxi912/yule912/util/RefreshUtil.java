package com.youxi912.yule912.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.youxi912.yule912.R;


/**
 * <pre>
 *     author : Administrator
 *     time   : 2018/07/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RefreshUtil {

    /**
     * 初始化 SwipeRefreshLayout
     *
     * @param context
     * @param refreshLayout
     */
    public static void initRefreshLayout(Context context, SwipeRefreshLayout refreshLayout) {
        //设置进度条的颜色
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.colorPrimary));
        //设置手指在屏幕下拉多少距离会触发刷新
        refreshLayout.setDistanceToTriggerSync(300);
        //设定下拉圆圈的背景
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置圆圈的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
    }
}
