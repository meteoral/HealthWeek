package com.bit_health.android.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.util.HeadImageUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类描述：MyFamilyAdapter
 * 
 * 主要功能："我的家庭"ListView的适配器
 * 
 * @author 梁才学
 **********************************************************************/
public class MyFamilyAdapter extends BaseAdapter {

	private Context mActivity;
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;
	private List<RoleInfoBean> mRoles = new ArrayList<RoleInfoBean>();

	public MyFamilyAdapter(Context context, List<RoleInfoBean> roles) {
		// TODO Auto-generated constructor stub
		mActivity = context;
		inflater = LayoutInflater.from(context);
		this.mRoles = roles;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRoles.size() + 1;
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
			convertView = inflater.inflate(R.layout.my_family_fragment_item,
					null);
			mViewHolder = new ViewHolder();

			mViewHolder.familyHeadImage = (ImageView) convertView
					.findViewById(R.id.my_family_head_image);
			mViewHolder.familyNameText = (TextView) convertView
					.findViewById(R.id.my_family_name);
			mViewHolder.familyEnglishNameText = (TextView) convertView
					.findViewById(R.id.my_family_name_pinyin);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		if (position == mRoles.size()) {
			mViewHolder.familyHeadImage.setImageResource(R.drawable.home_page_add);
			mViewHolder.familyNameText.setText("添加");
			mViewHolder.familyEnglishNameText.setVisibility(View.GONE);
		} else {
			Bitmap bmp = null;
			if ((bmp = HeadImageUtil.getHeadCatchImage(mRoles.get(position).mId)) != null) {
				mViewHolder.familyHeadImage.setImageBitmap(bmp);
			}else{
				mViewHolder.familyHeadImage.setImageResource(HeadImageUtil
						.getLocalHeadimage(mRoles.get(position).mFullName));
			}
			mViewHolder.familyEnglishNameText.setVisibility(View.VISIBLE);
			mViewHolder.familyNameText.setText(mRoles.get(position).mFullName);
			mViewHolder.familyEnglishNameText
					.setText(mRoles.get(position).mName);
		}
		setListViewTextSize();
		return convertView;
	}

	// 设置listview的字体大小
	private void setListViewTextSize() {
		
		SharedPreferences preference = mActivity.getSharedPreferences(mActivity.getPackageName(), Context.MODE_PRIVATE);		
		int currentIndex = preference.getInt("currentIndex", 0);
		
		if (currentIndex == 2) {
			mViewHolder.familyNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			mViewHolder.familyEnglishNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {
			mViewHolder.familyNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			mViewHolder.familyEnglishNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {
			mViewHolder.familyNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			mViewHolder.familyEnglishNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_25px));
		}
	}

	public static class ViewHolder {
		public ImageView familyHeadImage;
		public TextView familyNameText;
		public TextView familyEnglishNameText;
	}
}
