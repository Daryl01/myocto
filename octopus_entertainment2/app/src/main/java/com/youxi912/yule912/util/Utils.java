package com.youxi912.yule912.util;

import android.text.TextUtils;

/**
 * @author mingsheng
 * @ClassName Utils
 * @Description
 * @date 2018/9/6
 * @history 2018/9/6 author: description:
 */
public class Utils {

    public static String checkNotNull(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        return "";
    }

    /**
     * 判断版本更新
     * @param version1 服务器应用版本
     * @param version2 应用本身版本
     * @return 0=>版本相同，1=>版本需要更新，-1版本不需要更新
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int minLen = Math.min(version1Array.length, version2Array.length);
        int index = 0;
        int diff = 0;
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }
}
