package com.youxi912.yule912.home.fragment.fragment_friend;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.decoration.SpacingDecoration;
import com.netease.nim.uikit.common.ui.recyclerview.listener.OnItemClickListener;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.adapter.ChatRoomListAdapter;
import com.youxi912.yule912.chatroom.activity.ChatRoomActivity;
import com.youxi912.yule912.model.ChatRoomModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiscoverFragment extends Fragment {
//    private ProgressDialog dialog;
    private ChatRoomListAdapter myAdapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        refreshLayout = view.findViewById(R.id.swipe_chat_room);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAdapter.getData().clear();
                myAdapter.notifyDataSetChanged();
                getRooms();
            }
        });

        recyclerView = view.findViewById(R.id.rv_chat_room);
        myAdapter = new ChatRoomListAdapter(recyclerView);
        myAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),OrientationHelper.VERTICAL, false));
        recyclerView.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(10), ScreenUtil.dip2px(10), true));
        recyclerView.addOnItemTouchListener(new OnItemClickListener<ChatRoomListAdapter>() {
            @Override
            public void onItemClick(ChatRoomListAdapter adapter, View view, int position) {
                ChatRoomInfo room = myAdapter.getItem(position);
                ChatRoomActivity.start(getActivity(), room.getRoomId());
            }
        });
        UserLoginModel.DataBean dataBean=SpUtil.getInstance(getContext()).getObject(Constant.USER_INFO);
        if ("5".equals(dataBean.getMemberOrder())){
            getRooms();
        }else {
            RxToast.showToast("该功能只对会员开放");
        }
        return view;
    }

    private void getRooms() {
        refreshLayout.setRefreshing(false);
        RetrofitManager.getInstance(getContext()).createReq(API.class).getChatRooms(
                SpUtil.getInstance(getContext()).getString(Constant.TOKEN))
                .enqueue(new Callback<ChatRoomModel>() {
                    @Override
                    public void onResponse(Call<ChatRoomModel> call, Response<ChatRoomModel> response) {
                        if (response.isSuccessful()) {
                            ChatRoomModel model = response.body();
                            if (model != null && model.getData() != null && model.getData().size() > 0) {
                                int i = 0;
                                List<ChatRoomInfo> datas = new ArrayList<ChatRoomInfo>();
                                for (; i < model.getData().size(); i++) {
                                    ChatRoomInfo info = new ChatRoomInfo();
                                    info.setName(model.getData().get(i).getName());
                                    info.setRoomId(model.getData().get(i).getRoomid() + "");
                                    info.setBroadcastUrl(TextUtils.isEmpty(model.getData().get(i).getCover()) ? "" : model.getData().get(i).getCover());
                                    datas.add(info);
                                }
                                onFetchDataDone(true,datas);
                            } else {
                                RxToast.error(model.getMsg());
                                onFetchDataDone(false, null);
                            }
                        } else {
                            RxToast.error(response.message());
                            onFetchDataDone(false, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<ChatRoomModel> call, Throwable t) {
                        RxToast.error(t.getMessage());
                        onFetchDataDone(false, null);
                    }
                });
    }

    private void onFetchDataDone(final boolean success, final List<ChatRoomInfo> data) {
        Activity context = getActivity();
        if (context != null) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false); // 刷新结束

                    if (success) {
                        myAdapter.addData(data); // 刷新数据源
                        myAdapter.loadMoreEnd();
                    }
                }
            });
        }
    }
    private static final Handler handler = new Handler();
    protected final void postRunnable(final Runnable runnable) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        });
    }

}
