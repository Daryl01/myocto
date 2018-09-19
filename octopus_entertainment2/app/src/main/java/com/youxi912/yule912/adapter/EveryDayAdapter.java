package com.youxi912.yule912.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.EveryDayModel;
import com.youxi912.yule912.view.GlideRoundTransform;

import java.util.List;

public class EveryDayAdapter extends BaseQuickAdapter<EveryDayModel, BaseViewHolder> {


    public EveryDayAdapter(@Nullable List<EveryDayModel> data) {
        super(R.layout.item_every, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EveryDayModel item) {
        helper.setText(R.id.item_tv_position_every, (helper.getAdapterPosition() + 1)+"");
        helper.setText(R.id.name_currency, item.getName());
        helper.setText(R.id.item_tv_value_every, item.getPrice());
        Glide.with(mContext).load(item.getResource())
                .apply(new RequestOptions().transform(new GlideRoundTransform(mContext, 5)))
                .into((AppCompatImageView) helper.getView(R.id.item_icon_every));
        AppCompatTextView textView = helper.getView(R.id.item_tv_change_every);
        textView.setText(item.getNum());
        if (item.isIncrease()) {
            textView.setBackgroundResource(R.drawable.shape_increase);
        } else {
            textView.setBackgroundResource(R.drawable.shape_reduce);
        }
    }

}
