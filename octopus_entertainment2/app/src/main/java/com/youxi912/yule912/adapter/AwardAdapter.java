package com.youxi912.yule912.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youxi912.yule912.R;
import com.youxi912.yule912.model.AwardModel;

public class AwardAdapter extends BaseQuickAdapter<AwardModel, BaseViewHolder> {

    public AwardAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AwardModel item) {
//        String str = "招募章鱼合伙人（" + item.getName() + "）的奖励";
//        helper.setText(R.id.tv_time_item_ps_detail, item.getData());
//        helper.setText(R.id.tv_time_item_ps_detail, str);
//        helper.setText(R.id.tv_amount_ps_detail, item.getAward());
    }
}
