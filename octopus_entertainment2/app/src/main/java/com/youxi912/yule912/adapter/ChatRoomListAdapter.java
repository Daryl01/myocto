package com.youxi912.yule912.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.holder.BaseViewHolder;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.youxi912.yule912.R;

/**
 * 聊天室列表数据适配器
 * Created by huangjun on 2016/12/9.
 */
public class ChatRoomListAdapter extends BaseQuickAdapter<ChatRoomInfo, BaseViewHolder> {
    private final static int COUNT_LIMIT = 10000;

    public ChatRoomListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_chatroom_list, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, ChatRoomInfo room, int position, boolean isScrolling) {
//        // bg
//        holder.getConvertView().setBackgroundResource(R.drawable.nim_list_item_bg_selecter);
//        // cover
//        AppCompatImageView coverImage = holder.getView(R.id.cover_image);
//        Glide.with(mContext).load(room.getBroadcastUrl()).apply(new RequestOptions().centerCrop()).into(coverImage);
//        // name
//        holder.setText(R.id.tv_name, room.getName());
//        // online count
//        TextView onlineCountText = holder.getView(R.id.tv_online_count);
//        setOnlineCount(onlineCountText, room);

        holder.setText(R.id.tv_item_title, room.getName());
    }

    private void setOnlineCount(TextView onlineCountText, ChatRoomInfo room) {
        if (room.getOnlineUserCount() < COUNT_LIMIT) {
            onlineCountText.setText(String.valueOf(room.getOnlineUserCount()));
        } else if (room.getOnlineUserCount() >= COUNT_LIMIT) {
            onlineCountText.setText(String.format("%.1f", room.getOnlineUserCount() / (float) COUNT_LIMIT) + "万");
        }
    }
}
