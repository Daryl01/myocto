package com.youxi912.yule912.session.action;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.SimpleCallback;
import com.netease.nim.uikit.business.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.business.contact.core.item.ContactItem;
import com.netease.nim.uikit.business.contact.core.item.ContactItemFilter;
import com.netease.nim.uikit.business.contact.core.model.IContact;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.business.team.model.TeamRequestCode;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.youxi912.yule912.R;
import com.youxi912.yule912.util.DemoCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzchenkang on 2017/5/3.
 */

public class TeamAVChatAction extends AVChatAction {

    private static final int MAX_INVITE_NUM = 8;

    // private String teamID;

    private LaunchTransaction transaction;

    public TeamAVChatAction(AVChatType avChatType) {
        super(avChatType);
    }

    @Override
    public void startAudioVideoCall(AVChatType avChatType) {



        if (transaction != null) {
            return;
        }

        final String tid = getAccount();
        if (TextUtils.isEmpty(tid)) {
            return;
        }
        transaction = new LaunchTransaction();
        transaction.setTeamID(tid);

        // load 一把群成员
        NimUIKit.getTeamProvider().fetchTeamMemberList(tid, new SimpleCallback<List<TeamMember>>() {
            @Override
            public void onResult(boolean success, List<TeamMember> result, int code) {
                // 检查下 tid 是否相等
                if (!checkTransactionValid()) {
                    return;
                }
                if (success && result != null) {
                    if (result.size() < 2) {
                        transaction = null;
                        Toast.makeText(getActivity(), "人数不足", Toast.LENGTH_SHORT).show();
                    } else {
                        NimUIKit.startContactSelector(getActivity(), getContactSelectOption(tid), TeamRequestCode.REQUEST_TEAM_VIDEO);
                    }
                }
            }
        });
    }

    public void onSelectedAccountFail() {
        transaction = null;
    }

    public void onSelectedAccountsResult(final ArrayList<String> accounts) {
        LogUtil.ui("start teamVideo " + getAccount() + " accounts = " + accounts);

        if (!checkTransactionValid()) {
            return;
        }

        final String roomName = StringUtil.get32UUID();
        LogUtil.ui("create room " + roomName);
        // 创建房间
        AVChatManager.getInstance().createRoom(roomName, null, new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                LogUtil.ui("create room " + roomName + " success !");
                if (!checkTransactionValid()) {
                    return;
                }
                onCreateRoomSuccess(roomName, accounts);
                transaction.setRoomName(roomName);

                String teamName = TeamHelper.getTeamName(transaction.getTeamID());

                transaction = null;
            }

            @Override
            public void onFailed(int code) {
                if (!checkTransactionValid()) {
                    return;
                }
                onCreateRoomFail();
            }

            @Override
            public void onException(Throwable exception) {
                if (!checkTransactionValid()) {
                    return;
                }
                onCreateRoomFail();
            }
        });
    }

    private boolean checkTransactionValid() {
        if (transaction == null) {
            return false;
        }
        if (transaction.getTeamID() == null || !transaction.getTeamID().equals(getAccount())) {
            transaction = null;
            return false;
        }
        return true;
    }

    //
    private ContactSelectActivity.Option getContactSelectOption(String teamId) {
        ContactSelectActivity.Option option = new ContactSelectActivity.Option();
        option.type = ContactSelectActivity.ContactSelectType.TEAM_MEMBER;
        option.teamId = teamId;
        option.maxSelectNum = MAX_INVITE_NUM;
        option.maxSelectNumVisible = true;
        option.title = NimUIKit.getContext().getString(com.netease.nim.uikit.R.string.invite_member);
        option.maxSelectedTip = NimUIKit.getContext().getString(com.netease.nim.uikit.R.string.reach_capacity);
        option.itemFilter = new ContactItemFilter() {
            @Override
            public boolean filter(AbsContactItem item) {
                IContact contact = ((ContactItem) item).getContact();
                // 过滤掉自己
                return contact.getContactId().equals(DemoCache.getAccount());
            }
        };
        return option;
    }

    private void onCreateRoomSuccess(String roomName, List<String> accounts) {
        String teamID = transaction.getTeamID();
        // 在群里发送tip消息
        IMMessage message = MessageBuilder.createTipMessage(teamID, SessionTypeEnum.Team);
        CustomMessageConfig tipConfig = new CustomMessageConfig();
        tipConfig.enableHistory = false;
        tipConfig.enableRoaming = false;
        tipConfig.enablePush = false;
        String teamNick = TeamHelper.getDisplayNameWithoutMe(teamID, DemoCache.getAccount());
        message.setContent(teamNick + "发起了视频聊天");
        message.setConfig(tipConfig);
        sendMessage(message);
        // 对各个成员发送点对点自定义通知
        String teamName = TeamHelper.getTeamName(transaction.getTeamID());
        CustomNotificationConfig config = new CustomNotificationConfig();
        config.enablePush = true;
        config.enablePushNick = false;
        config.enableUnreadCount = true;

        for (String account : accounts) {
            CustomNotification command = new CustomNotification();
            command.setSessionId(account);
            command.setSessionType(SessionTypeEnum.P2P);
            command.setConfig(config);
            command.setApnsText(teamNick);

            command.setSendToOnlineUserOnly(false);
            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }
    }

    private void onCreateRoomFail() {
        // 本地插一条tip消息
        IMMessage message = MessageBuilder.createTipMessage(transaction.getTeamID(), SessionTypeEnum.Team);
        message.setContent("创建失败");
        LogUtil.i("status", "team action set:" + MsgStatusEnum.success);
        message.setStatus(MsgStatusEnum.success);
        NIMClient.getService(MsgService.class).saveMessageToLocal(message, true);
    }

    private class LaunchTransaction implements Serializable {
        private String teamID;
        private String roomName;

        public String getRoomName() {
            return roomName;
        }

        public String getTeamID() {
            return teamID;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public void setTeamID(String teamID) {
            this.teamID = teamID;
        }
    }
}
