package com.youxi912.yule912.adapter;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vondear.rxtool.view.RxToast;
import com.youxi912.yule912.R;

import java.math.BigDecimal;

import com.youxi912.yule912.home.GameDetailActivity;
import com.youxi912.yule912.home.LiveListActivity;
import com.youxi912.yule912.model.DataBean;
import com.youxi912.yule912.view.GlideRoundTransform;
import com.youxi912.yule912.web.H5Activity;

public class HotGameListAdapter extends BaseQuickAdapter<DataBean, BaseViewHolder> {

    public HotGameListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final DataBean item) {
        helper.setText(R.id.tv_name_item_game_list, item.getName());
        helper.setText(R.id.size_item_game_list, item.getSize() + "MB");
        helper.setText(R.id.tv_description_item_game_list, TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());
        helper.setText(R.id.tv_playerNum_item_game_list, change(item.getDownload_times()));
        AppCompatImageView imageView = helper.getView(R.id.icon_item_game_list);
        if (!TextUtils.isEmpty(item.getLogo()))
            Glide.with(mContext).load(item.getLogo()).apply(
                    new RequestOptions().transform(new GlideRoundTransform(mContext, 5)).centerCrop())
                    .into(imageView);
        else
            Glide.with(mContext).load(R.mipmap.placeholder).into(imageView);
        if (item.getPrice().startsWith("0.") || item.getPay_status() == 1)
            helper.setText(R.id.btn_get_item_game_list, "打开");
        else
            helper.setText(R.id.btn_get_item_game_list, "获取");
        helper.getView(R.id.btn_get_item_game_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(18 == item.getCate_id())
                {
                    //直播
                    mContext.startActivity(new Intent(mContext, LiveListActivity.class));
                    return;
                }
                if (item.getPrice().startsWith("0.") || item.getPay_status() == 1)
                    if (item.getVersion() == null || item.getVersion().getPc() == null || TextUtils.isEmpty(item.getVersion().getPc().getResource_url()))
                        RxToast.error("获取游戏配置信息出错,晚点再来哦");
                    else
                        H5Activity.toH5Activity(mContext, item.getVersion().getPc().getResource_url());
                else
                    GameDetailActivity.ToGameDetail(mContext, item.getId());
            }
        });
    }

    private String change(int value) {
        String s = "";
        if (value < 1000) {
            s = String.valueOf(value) + "   在玩";
        } else if (value < 10000) {
            double val = value * 1.0 / 1000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "K  人在玩";
        } else if (value < 100000) {
            double val = value * 1.0 / 10000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        } else if (value < 1000000) {
            double val = value * 1.0 / 100000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        } else if (value < 10000000) {
            double val = value * 1.0 / 1000000;
            BigDecimal bigDecimal = new BigDecimal(val);
            s = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "W   人在玩";
        }
        return s;
    }
}
