package com.gs.learn.group.widget;

import android.view.View;

public class RecyclerExtras {

	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	public interface OnItemLongClickListener {
		void onItemLongClick(View view, int position);
	}

	public interface OnItemDeleteClickListener {
		void onItemDeleteClick(View view, int position);
	}

}
