package com.bit_health.android.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.RoleCatchInfo;
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
 * 类描述：TestModuleGV_Adapter
 * 
 * 主要功能：选择身份登陆中GridView的适配器
 * 
 * @author 梁才学
 **********************************************************************/
public class LoginIdentityGV_Adapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;
	// private ArrayList<String> headPhotoNameData;
	private List<RoleInfoBean> mRoleInfo = new ArrayList<RoleInfoBean>();

	public LoginIdentityGV_Adapter(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		inflater = LayoutInflater.from(context);
		mRoleInfo = RoleCatchInfo.getInstance(context).getRoles();
	}

	public void setHeadPhotoNameData(List<RoleInfoBean> headPhotoNameData) {
		this.mRoleInfo = headPhotoNameData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRoleInfo.size() + 1;
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
			convertView = inflater.inflate(R.layout.login_identity_grid_item,
					null);
			mViewHolder = new ViewHolder();
			mViewHolder.headPhotoImage = (ImageView) convertView
					.findViewById(R.id.login_identity_grid_image);
			mViewHolder.headPhotoNameText = (TextView) convertView
					.findViewById(R.id.login_identity_grid_text);
			mViewHolder.userNameText = (TextView) convertView
					.findViewById(R.id.login_identity_username);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		if (position == mRoleInfo.size()) {			
			mViewHolder.headPhotoImage.setImageResource(R.drawable.home_page_add);		
			mViewHolder.headPhotoNameText.setText("添加");
			mViewHolder.userNameText.setText("");
		} else {
			Bitmap bmp = null;
			if ((bmp = HeadImageUtil.getHeadCatchImage(mRoleInfo
					.get(position).mId)) != null) {
				mViewHolder.headPhotoImage.setImageBitmap(bmp);
			}else{
				mViewHolder.headPhotoImage.setImageResource(HeadImageUtil
						.getLocalHeadimage(mRoleInfo.get(position).mFullName));
			}
			mViewHolder.headPhotoNameText
					.setText(mRoleInfo.get(position).mFullName);
			mViewHolder.userNameText.setText(mRoleInfo.get(position).mName);
			setListViewTextSize();
		}
		return convertView;
	}

	// 设置listview的字体大小
	private void setListViewTextSize() {
		
		SharedPreferences preference = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);		
		int currentIndex = preference.getInt("currentIndex", 0);
		
		if (currentIndex == 2) {
			mViewHolder.headPhotoNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_31px));
			mViewHolder.userNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {
			mViewHolder.headPhotoNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_28px));
			mViewHolder.userNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {
			mViewHolder.headPhotoNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_25px));
			mViewHolder.userNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_25px));
		}
	}

	public static class ViewHolder {
		public ImageView headPhotoImage;
		public TextView headPhotoNameText;
		public TextView userNameText;
	}
}
