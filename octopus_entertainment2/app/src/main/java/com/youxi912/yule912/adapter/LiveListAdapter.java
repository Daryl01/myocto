package com.youxi912.yule912.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.LiveListModel;

public class LiveListAdapter extends BaseQuickAdapter<LiveListModel.DataBean.ListBean, BaseViewHolder> {

    private final static int COUNT_LIMIT = 10000;

    public LiveListAdapter() {
        super(R.layout.item_chatroom_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveListModel.DataBean.ListBean item) {
        helper.setText(R.id.tv_item_title, item.getName());
        AppCompatTextView status=helper.getView(R.id.tv_item_status);
        status.setVisibility(View.VISIBLE);
        if (item.getStatus()==0){
            status.setText("空闲中");
            status.setTextColor(ContextCompat.getColor(mContext,R.color.darkgray));
        }else if (item.getStatus()==2){
            status.setText("已被禁用");
            status.setTextColor(ContextCompat.getColor(mContext,R.color.darkgray));
        }else {
            status.setText("直播中");
            status.setTextColor(ContextCompat.getColor(mContext,R.color.sj_theme_color));
        }
    }


    private void setOnlineCount(TextView onlineCountText, ChatRoomInfo room) {
        if (room.getOnlineUserCount() < COUNT_LIMIT) {
            onlineCountText.setText(String.valueOf(room.getOnlineUserCount()));
        } else if (room.getOnlineUserCount() >= COUNT_LIMIT) {
            onlineCountText.setText(String.format("%.1f", room.getOnlineUserCount() / (float) COUNT_LIMIT) + "万");
        }
    }
}
