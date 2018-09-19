package com.youxi912.yule912.mine;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.adapter.AwardAdapter;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.model.AwardModel;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsDetailFragment extends Fragment {
    private CommonAdapter<AwardModel.DataBean> adapter;
    private List<AwardModel.DataBean> datas = new ArrayList<>();
//    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ps_detail, container, false);
//        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_ps_detail);
//        dialog = new ProgressDialog(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_ps_detail);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new CommonAdapter<AwardModel.DataBean>(getContext(), datas, R.layout.item_ps_detail) {
            @Override
            public void convert(CommonViewHolder holder, AwardModel.DataBean item) {
                holder.setText(R.id.tv_time_item_ps_detail, item.getAddTime());
                holder.setText(R.id.item_tv_ps_detail, item.getMs());
                holder.setText(R.id.tv_amount_ps_detail, "+" + item.getJLCount());
            }
        };
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
//        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//
//            }
//        }, recyclerView);
        recyclerView.setAdapter(adapter);
        getData();
        return view;
    }

    private void getData() {
//        dialog.setMessage("正在加载数据,请稍候...");
//        dialog.show();
        RetrofitManager.getInstance(getContext()).createReq(API.class)
                .getAwardHistory(SpUtil.getInstance(getContext()).getString(Constant.TOKEN))
                .enqueue(new Callback<AwardModel>() {
                    @Override
                    public void onResponse(Call<AwardModel> call, Response<AwardModel> response) {
//                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            AwardModel model = response.body();
                            if (model == null || model.getData() == null || model.getData().size() == 0) {
                                RxToast.showToast("数据为空哦");
                                return;
                            }
                            datas.addAll(model.getData());
                            adapter.notifyDataSetChanged();
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AwardModel> call, Throwable t) {
//                        dialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }

}
