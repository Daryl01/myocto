package com.youxi912.yule912.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by starry on 2016/11/3.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    private LayoutInflater mInflater;
    public List<T> mDatas;
    private int mlayoutId;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mDatas = datas;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        if (mDatas != null && mDatas.size() > 0) {
            if (position!=mDatas.size())
                convert(holder, mDatas.get(position));
        }
        if (onItemClickListener != null) {
            final int layoutPosition = holder.getAdapterPosition();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
            //longclick
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                    return false;
                }
            });
        }
    }

    //刷新方法
    public void setList(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    //清空方法
    public void clear() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    //添加方法
    public void addData(List<T> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder holder = new CommonViewHolder(mInflater.inflate(mlayoutId, parent, false));
        return holder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public abstract void convert(CommonViewHolder holder, T item);
}
