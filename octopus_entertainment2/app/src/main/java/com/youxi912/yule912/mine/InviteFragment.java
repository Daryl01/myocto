package com.youxi912.yule912.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youxi912.yule912.R;
import com.youxi912.yule912.common.CommonAdapter;
import com.youxi912.yule912.common.CommonViewHolder;
import com.youxi912.yule912.model.InviteRecordModel;
import com.youxi912.yule912.view.GlideRoundTransform;

import java.util.ArrayList;

public class InviteFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private ArrayList<InviteRecordModel.DataBean.AllBean> mParam1;
    private CommonAdapter<InviteRecordModel.DataBean.AllBean> adapter;


    public static InviteFragment newInstance(ArrayList<InviteRecordModel.DataBean.AllBean> param1) {
        InviteFragment fragment = new InviteFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inviete, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_invite);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new CommonAdapter<InviteRecordModel.DataBean.AllBean>(getContext(), mParam1, R.layout.item_invite_record) {
            @Override
            public void convert(CommonViewHolder holder, InviteRecordModel.DataBean.AllBean item) {
                if (item.getAccounts().length() >= 4) {
                    String pre = item.getAccounts().substring(0, 3);
                    String end = item.getAccounts().substring(item.getAccounts().length() - 4, item.getAccounts().length());
                    holder.setText(R.id.tv_phone, "手机号 " + pre + "****" + end);
                }
                holder.setText(R.id.tv_register_time, item.getRegisterDate().substring(0, item.getRegisterDate().lastIndexOf(":") - 1));
                AppCompatTextView level = holder.getView(R.id.tv_level);
                if (TextUtils.isEmpty(item.getDj())) {
                    level.setText("章鱼宝宝");
                    level.setBackgroundResource(R.drawable.shape_no_level);
                } else if ("0".equals(item.getDj())) {
                    level.setText("章鱼矿工");
                    level.setBackgroundResource(R.drawable.shape_level_1);
                } else if ("1".equals(item.getDj())) {
                    level.setText("以太矿工");
                    level.setBackgroundResource(R.drawable.shape_level2);
                } else if ("2".equals(item.getDj())) {
                    level.setText("比特矿工");
                    level.setBackgroundResource(R.drawable.shape_level3);
                }
                holder.setText(R.id.tv_remark, item.getNickName());
                if (!TextUtils.isEmpty(item.getTx())) {
                    Glide.with(getContext()).load(item.getTx()).apply(new RequestOptions().error(R.mipmap.icon_avatar)
                            .transform(new GlideRoundTransform(getContext(), 5))).into((AppCompatImageView) holder.getView(R.id.img_avatar));
                }
            }
        };
        recyclerView.setAdapter(adapter);
        return view;
    }
}
