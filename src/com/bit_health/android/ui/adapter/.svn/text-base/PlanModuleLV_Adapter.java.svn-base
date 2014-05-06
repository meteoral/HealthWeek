package com.bit_health.android.ui.adapter;

import java.util.List;
import java.util.Map;

import com.siat.healthweek.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**********************************************************************
 * 类名：PlanModuleLV_Adapter
 * 
 * @author 梁才学 
 * 主要功能：添加模块中 ListView 的适配器 
 * 创建日期：2013.12.9
 **********************************************************************/
public class PlanModuleLV_Adapter extends BaseAdapter {
	
	private List<Map<String, Object>> dataList;
	private LayoutInflater listContainer;

	public PlanModuleLV_Adapter(Context context,
			List<Map<String, Object>> dataList) {
		listContainer = LayoutInflater.from(context);
		this.dataList = dataList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}	

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder mViewHolder = null;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.plan_module_list_item, null);
		
			mViewHolder.plan_name_text = (TextView) convertView
					.findViewById(R.id.plan_name);
			mViewHolder.plan_number_text = (TextView) convertView
					.findViewById(R.id.plan_number);			
			mViewHolder.total_day_text = (TextView) convertView
					.findViewById(R.id.total_day);
			mViewHolder.remaining_day_text = (TextView) convertView
					.findViewById(R.id.remaining_day);
			
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		// 设置文字
		
		mViewHolder.plan_name_text.setText((String) dataList.get(position)
				.get("key_planName"));
		mViewHolder.plan_number_text.setText((String) dataList.get(position).get(
				"key_planNumber"));		
		mViewHolder.total_day_text.setText((String) dataList.get(position)
				.get("key_totalDay"));
		mViewHolder.remaining_day_text.setText((String) dataList.get(position).get(
				"key_remainingDay"));
		
		return convertView;
	}
	
	class ViewHolder {
		public TextView plan_name_text;
		public TextView plan_number_text;
		public TextView total_day_text;
		public TextView remaining_day_text;
		
	}
}
