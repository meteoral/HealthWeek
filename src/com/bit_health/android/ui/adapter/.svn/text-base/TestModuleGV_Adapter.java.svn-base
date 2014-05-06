package com.bit_health.android.ui.adapter;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类描述：TestModuleGV_Adapter
 * 
 * 主要功能："测试"模块中GridView的适配器
 * 
 * @author 梁才学
 **********************************************************************/
public class TestModuleGV_Adapter extends BaseAdapter {

	private Context mActivity;
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;
	private String[] headPhotoNameData;

	public TestModuleGV_Adapter(Context context, String[] names) {
		// TODO Auto-generated constructor stub
		mActivity = context;
		inflater = LayoutInflater.from(context);
		headPhotoNameData = names;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return headPhotoNameData.length;
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
			convertView = inflater
					.inflate(R.layout.test_module_grid_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.headPhotoImage = (ImageView) convertView
					.findViewById(R.id.test_module_grid_image);
			mViewHolder.headPhotoNameText = (TextView) convertView
					.findViewById(R.id.test_module_grid_text);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		if (headPhotoNameData[position].equals("心电测试")) {
			mViewHolder.headPhotoImage
					.setBackgroundResource(R.drawable.test_module_xindian_icon);
		} else if (headPhotoNameData[position].equals("血压测试")) {
			mViewHolder.headPhotoImage
					.setBackgroundResource(R.drawable.test_module_xueya_icon);
		} else if (headPhotoNameData[position].equals("血糖测试")) {
			mViewHolder.headPhotoImage
					.setBackgroundResource(R.drawable.test_module_xuetang_icon);
		} else if (headPhotoNameData[position].equals("帮助手册")) {
			mViewHolder.headPhotoImage
					.setBackgroundResource(R.drawable.test_module_help);
		} else if (headPhotoNameData[position].equals("蓝牙上传")) {
			mViewHolder.headPhotoImage
					.setBackgroundResource(R.drawable.bluetooth_upload);
		} else if (headPhotoNameData[position].equals("上传记录")) {
			mViewHolder.headPhotoImage
					.setBackgroundResource(R.drawable.bluetooth_upload_fail);
		} else {
			mViewHolder.headPhotoImage
					.setBackgroundResource(R.drawable.head_photo);
		}

		mViewHolder.headPhotoNameText.setText(headPhotoNameData[position]);
		setListViewTextSize();
		return convertView;
	}

	// 设置listview的字体大小
	private void setListViewTextSize() {

		SharedPreferences preference = mActivity.getSharedPreferences(
				mActivity.getPackageName(), Context.MODE_PRIVATE);
		int currentIndex = preference.getInt("currentIndex", 0);

		if (currentIndex == 2) {
			mViewHolder.headPhotoNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {
			mViewHolder.headPhotoNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {
			mViewHolder.headPhotoNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_25px));
		}
	}

	public static class ViewHolder {
		public ImageView headPhotoImage;
		public TextView headPhotoNameText;
	}
}
