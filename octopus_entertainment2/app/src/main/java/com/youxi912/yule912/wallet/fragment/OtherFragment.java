package com.youxi912.yule912.wallet.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.Base.API;
import com.youxi912.yule912.Base.Constant;
import com.youxi912.yule912.R;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.model.RecordModel;
import com.youxi912.yule912.util.RetrofitManager;
import com.youxi912.yule912.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private CommonAdapter<RecordModel.DataBean.Bean> adapter;
    private List<RecordModel.DataBean.Bean> beans = new ArrayList<>();
//    private ProgressDialog progressDialog;

    public static OtherFragment newInstance(String param1) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_other);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
//        progressDialog = new ProgressDialog(getContext());
        adapter = new CommonAdapter<RecordModel.DataBean.Bean>(getContext(), beans, R.layout.item_record) {
            @Override
            public void convert(CommonViewHolder holder, RecordModel.DataBean.Bean item) {
                holder.setText(R.id.tv_record_item_record, item.getRemark());
                holder.setText(R.id.tv_time_item_record, item.getDatetime().substring(0, item.getDatetime().lastIndexOf(":") - 1));
            }
        };
        recyclerView.setAdapter(adapter);
        getData();
        return view;
    }

    private void getData() {
//        progressDialog.setMessage("正在加载数据,请稍候...");
//        progressDialog.show();
        RetrofitManager.getInstance(getContext()).createReq(API.class).getAccoutHistory(
                SpUtil.getInstance(getContext()).getString(Constant.TOKEN), mParam1)
                .enqueue(new Callback<RecordModel>() {
                    @Override
                    public void onResponse(Call<RecordModel> call, Response<RecordModel> response) {
//                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            RecordModel recordModel = response.body();
                            if (recordModel == null || recordModel.getData() == null ||
                                    recordModel.getData().getTrunin() == null || recordModel.getData().getTrunin().size() == 0) {
                                return;
                            }
                            beans.addAll(recordModel.getData().getTrunin());
                            adapter.notifyDataSetChanged();
                        } else {
                            RxToast.error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecordModel> call, Throwable t) {
//                        progressDialog.dismiss();
                        RxToast.error(t.getMessage());
                    }
                });
    }
}
