package com.youxi912.yule912.live.nim.login;

import com.netease.nim.uikit.api.model.main.LoginSyncDataStatusObserver;
import com.youxi912.yule912.live.fragment.NimUIKit;
import com.youxi912.yule912.live.nim.chatroom.helper.ChatRoomHelper;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.youxi912.yule912.util.DemoCache;

/**
 * 注销帮助类
 * Created by huangjun on 2015/10/8.
 */
public class LogoutHelper {
    public static void logout() {
        // 清理缓存&注销监听&清除状态
        NimUIKit.clearCache();
        ChatRoomHelper.logout();
        DemoCache.clear();
        LoginSyncDataStatusObserver.getInstance().reset();
        DropManager.getInstance().destroy();
    }
}
