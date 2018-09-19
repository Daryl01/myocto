package com.youxi912.yule912.home;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.BaseActivity;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.adapter.ChatRoomListAdapter;
import com.youxi912.yule912.adapter.LiveListAdapter;
import com.youxi912.yule912.chatroom.activity.ChatRoomActivity;
import com.youxi912.yule912.live.activity.EnterAudienceActivity;
import com.youxi912.yule912.live.activity.EnterLiveActivity;
import com.youxi912.yule912.live.activity.LiveRoomActivity;
import com.youxi912.yule912.live.server.DemoServerHttpClient;
import com.youxi912.yule912.live.server.entity.RoomInfoEntity;
import com.youxi912.yule912.model.GetRoomNameModel;
import com.youxi912.yule912.model.LiveListModel;
import com.youxi912.yule912.util.DemoCache;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveListActivity extends BaseActivity {
    private SwipeRefreshLayout refreshLayout;
    private LiveListAdapter myAdapter;
    private int curPage = 1;
    private int limit = 10;
    private boolean cancelEnterRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_list;
    }

    @Override
    public void initView() {
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        refreshLayout = findViewById(R.id.swipe_live);
        RecyclerView recyclerView = findViewById(R.id.rv_live);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        myAdapter = new LiveListAdapter();
        recyclerView.setAdapter(myAdapter);
        getData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                curPage = 1;
                myAdapter.getData().clear();
                myAdapter.notifyDataSetChanged();
                getData();
            }
        });
        myAdapter.setOnLoadMoreListener(new com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData();
            }
        }, recyclerView);
        myAdapter.disableLoadMoreIfNotFullPage();
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (myAdapter.getData().get(position).getStatus() == 0) {
//                    RxToast.showToast("该用户当前已关闭直播");
//                } else {
                if (myAdapter.getData().get(position).getStatus() == 2) {
                    RxToast.showToast("当前房间, 不在直播中");
                } else {
                    LiveRoomActivity.startAudience(LiveListActivity.this, myAdapter.getData().get(position).getRoomid() + "",
                            myAdapter.getData().get(position).getHttp_pullurl(), true);
                }
//                }
            }
        });
    }

    @Override
    public void initData() {

    }

    public static void ToLiveListActivity(Context context) {
        Intent intent = new Intent(context, LiveListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private void getData() {
        RetrofitManager.getInstance(this).createReq(API.class).getLiveList(curPage + "", limit + "", "ctime",
                0 + "", SpUtil.getInstance(LiveListActivity.this).getString(Constant.TOKEN))
                .enqueue(new Callback<LiveListModel>() {
                    @Override
                    public void onResponse(Call<LiveListModel> call, Response<LiveListModel> response) {
                        if (curPage == 1)
                            refreshLayout.setRefreshing(false);
                        if (response.isSuccessful()) {
                            LiveListModel model = response.body();
                            if (model == null || model.getData() == null || model.getData().getList() == null || model.getData().getList().size() == 0) {
                                RxToast.showToast("当前暂无直播哦,去其他分类看看吧");
                            } else if (model.getCode() == Constant.OK) {
                                myAdapter.addData(model.getData().getList());
                                if (curPage * 10 >= model.getData().getTotalPnum()) {
                                    myAdapter.loadMoreEnd();
                                    if (curPage == 1 && 10 > model.getData().getList().size())
                                        myAdapter.disableLoadMoreIfNotFullPage();
                                } else {
                                    myAdapter.loadMoreComplete();
                                    curPage++;
                                }
                            } else {
                                RxToast.showToast(model.getMsg());
                            }
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<LiveListModel> call, Throwable t) {
                        if (curPage == 1)
                            refreshLayout.setRefreshing(false);
                        RxToast.error(t.getMessage());
                    }
                });
    }
}
