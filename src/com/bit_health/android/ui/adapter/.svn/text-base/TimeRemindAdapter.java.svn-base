package com.bit_health.android.ui.adapter;

import java.util.HashMap;

import com.siat.healthweek.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**********************************************************************
 * 类描述：TimeRemindAdapter
 * 
 * 主要功能：提醒设置中"测试时间提醒"的适配器
 * @author 梁才学
 **********************************************************************/
public class TimeRemindAdapter extends BaseAdapter {
	
	private Context mActivity;	
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;
	private HashMap<Integer, Boolean> isSelect;// 记录复选框的状态
	private String[] timesData;// 时间提醒的时间数组
	
	public TimeRemindAdapter(Context context, String[] times) {
		// TODO Auto-generated constructor stub
		mActivity = context;
		timesData = times;
		inflater = LayoutInflater.from(context);
		isSelect = new HashMap<Integer, Boolean>();
		for(int i = 0; i < timesData.length; i++){
			isSelect.put(i, false);
		}		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return timesData.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.remind_set_list_item, null);
			mViewHolder = new ViewHolder();			
			mViewHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.alarm_clock_cb);
			mViewHolder.alarmTimeText = (TextView) convertView
					.findViewById(R.id.alarm_time_text);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		mViewHolder.alarmTimeText.setText(timesData[position]);
		mViewHolder.mCheckBox.setChecked(isSelect.get(position));
		
		return convertView;
	}
	
	public HashMap<Integer, Boolean> getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(HashMap<Integer, Boolean> isSelect) {
		this.isSelect = isSelect;
	}
	
	public static class ViewHolder {		
		public TextView alarmTimeText;
		public CheckBox mCheckBox;
	}
}
