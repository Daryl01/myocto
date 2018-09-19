package com.youxi912.yule912.common;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by starry on 2016/11/3.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {


    private SparseArray<View> mViews;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public CommonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonViewHolder setTextColor(int viewId, String colors) {
        TextView tv = getView(viewId);
        tv.setTextColor(Color.parseColor(colors));
        return this;
    }

    public CommonViewHolder setTextBackground(int viewId, int shapeRes) {
        TextView tv = getView(viewId);
        tv.setBackgroundResource(shapeRes);
        return this;
    }


    public CommonViewHolder setVisible(int viewId, boolean isVisible) {
        if (!isVisible) {
            getView(viewId).setVisibility(View.GONE);
        } else {
            getView(viewId).setVisibility(View.VISIBLE);
        }
        return this;
    }


    public CommonViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public CommonViewHolder setImageURI(Context context, int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().centerCrop().dontAnimate())
                .into(view);
        return this;
    }

    public CommonViewHolder setImageURI(Context context, int viewId, String url, int width, int height) {
        ImageView view = getView(viewId);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().centerCrop().dontAnimate().override(width, height))
                .into(view);
        return this;
    }
}
