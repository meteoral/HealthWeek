package com.bit_health.android.ui.adapter;

import java.util.ArrayList;

import com.siat.healthweek.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*******************************************************************
 * 类描述：GridView表格布局的适配器
 *
 ******************************************************************/
public class GridViewAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> dataList;
	
	private LayoutInflater inflater;

	public GridViewAdapter(Context context,
			ArrayList<String> list) {
		this.mContext = context;
		this.dataList = list;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= getCount()) {
			return null;
		}
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder mViewHolder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.table_items, null);
			mViewHolder = new ViewHolder();
			mViewHolder.textView = (TextView) convertView
					.findViewById(R.id.ItemText);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		String source = dataList.get(position);
		mViewHolder.textView.setText(source);

		String numString[] = source.split("：");// 将"心动过速：2"截取出数字"2"
		if(Integer.valueOf(numString[1]) != null){
			if(Integer.valueOf(numString[1]) > 0){
				mViewHolder.textView.setTextColor(Color.RED);
			}else{
				mViewHolder.textView.setTextColor(Color.BLACK);
			}		
		}
		
		return convertView;
	}

	public class ViewHolder {
		public TextView textView;
	}
}