package com.youxi912.yule912.live;


import com.youxi912.yule912.live.viewholder.TViewHolder;

public interface TAdapterDelegate {

	public int getViewTypeCount();

	public Class<? extends TViewHolder> viewHolderAtPosition(int position);

	public boolean enabled(int position);
}