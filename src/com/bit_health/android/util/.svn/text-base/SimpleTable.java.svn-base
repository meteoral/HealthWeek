package com.bit_health.android.util;

import java.util.ArrayList;

import com.bit_health.android.ui.adapter.GridViewAdapter;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.LinearLayout;

public class SimpleTable extends LinearLayout {

	private Context context;
	private GridView gv_body;
	private String[] items;
	
	private ArrayList<String> dataList;

	public SimpleTable(Context context) {
		super(context);
		this.context = context;
	}

	public SimpleTable(Context context, String[] items,
			ArrayList<String> dataList) {
		super(context);
		this.context = context;
		this.items = items;
		this.dataList = dataList;

		setBody(); // 数据视图
		setOrientation(LinearLayout.VERTICAL);
		addView(gv_body);
	}

	/**
	 * 设置表格数据视图
	 */
	private void setBody() {
		gv_body = new GridView(context);
		gv_body.setNumColumns(items.length);
		getData();
	}

	public void getData() {
		// 设置适配器		
		GridViewAdapter adapter = new GridViewAdapter(context, dataList);		
		gv_body.setAdapter(adapter); // 重新添加适配器
	}

	/**
	 * 屏蔽该控件的触屏事件，否则 ViewPager 将无法左右滑动
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
//		return super.onInterceptTouchEvent(ev);
		return true;
	}
	
	
}