package com.bit_health.android.ui.adapter;

import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.database.FileDbBean;
import com.bit_health.android.util.TimeFormatUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**********************************************************************
 * 类描述：BluetoothUploadAdapter
 * 
 * 主要功能："蓝牙一键上传"ListView的适配器
 * 
 * @author 梁才学
 **********************************************************************/
public class BluetoothUploadAdapter extends BaseAdapter {

	private Context mActivity;
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;
	private List<FileDbBean> listData;

	public BluetoothUploadAdapter(Context context, List<FileDbBean> listData) {
		// TODO Auto-generated constructor stub
		mActivity = context;
		inflater = LayoutInflater.from(context);
		this.listData = listData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
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
			convertView = inflater.inflate(R.layout.bluetooth_upload_list_item,
					null);
			mViewHolder = new ViewHolder();
			mViewHolder.fileMeasureTimeText = (TextView) convertView
					.findViewById(R.id.file_measure_time);
			mViewHolder.fileTimeLengthText = (TextView) convertView
					.findViewById(R.id.file_time_length);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.fileMeasureTimeText.setText(TimeFormatUtil.longToDate(listData.get(position).mTime));
		mViewHolder.fileTimeLengthText.setText(String.valueOf(listData.get(position).mTimeLength) + "个小时");
		
		setListViewTextSize();
		return convertView;
	}

	// 设置listview的字体大小
	private void setListViewTextSize() {

		SharedPreferences preference = mActivity.getSharedPreferences(
				mActivity.getPackageName(), Context.MODE_PRIVATE);
		int currentIndex = preference.getInt("currentIndex", 0);

		if (currentIndex == 2) {
			mViewHolder.fileMeasureTimeText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			mViewHolder.fileTimeLengthText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {
			mViewHolder.fileMeasureTimeText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			mViewHolder.fileTimeLengthText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {
			mViewHolder.fileMeasureTimeText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			mViewHolder.fileTimeLengthText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
		}
	}

	public static class ViewHolder {
		public TextView fileMeasureTimeText;// 测量时间
		public TextView fileTimeLengthText;// 测量时长
	}
}
