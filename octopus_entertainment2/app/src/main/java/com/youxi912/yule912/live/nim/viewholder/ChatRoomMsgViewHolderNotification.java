package com.youxi912.yule912.live.nim.viewholder;

import android.widget.TextView;

import com.youxi912.yule912.live.nim.chatroom.helper.ChatRoomNotificationHelper;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.youxi912.yule912.live.viewholder.MsgViewHolderBase;

public class ChatRoomMsgViewHolderNotification extends MsgViewHolderBase {

    protected TextView notificationTextView;

    @Override
    protected int getContentResId() {
        return com.netease.nim.uikit.R.layout.nim_message_item_notification;
    }

    @Override
    protected void inflateContentView() {
        notificationTextView = (TextView) view.findViewById(com.netease.nim.uikit.R.id.message_item_notification_label);
    }

    @Override
    protected void bindContentView() {
        notificationTextView.setText(ChatRoomNotificationHelper.getNotificationText((ChatRoomNotificationAttachment) message.getAttachment()));
    }

    @Override
    protected boolean isMiddleItem() {
        return false;
    }

    @Override
    protected boolean isShowBubble() {
        return false;
    }

    @Override
    protected boolean isShowHeadImage() {
        return false;
    }
}

