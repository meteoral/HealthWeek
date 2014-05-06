package com.bit_health.android.ui.adapter;

import com.siat.healthweek.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类描述：MyRoleListAdapter
 * 
 * 主要功能："我的角色"ListView的适配器
 * 
 * @author 梁才学
 **********************************************************************/
public class MyRoleListAdapter extends BaseAdapter {

	private Context mActivity;
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;
	private String[] roleNameData;// 角色的姓名

	public MyRoleListAdapter(Context context, String[] names) {
		// TODO Auto-generated constructor stub
		mActivity = context;
		inflater = LayoutInflater.from(context);
		roleNameData = names;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return roleNameData.length;
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
			convertView = inflater.inflate(R.layout.my_role_list_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.roleNameText = (TextView) convertView
					.findViewById(R.id.role_name_text);
			mViewHolder.headPhotoImage = (ImageView) convertView
					.findViewById(R.id.role_head_photo_image);
			mViewHolder.roleLockImage = (ImageView) convertView
					.findViewById(R.id.role_lock_image);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		mViewHolder.roleNameText.setText(roleNameData[position]);

		if (position == 0) {
			mViewHolder.headPhotoImage.setImageDrawable(mActivity
					.getResources().getDrawable(R.drawable.tab_content_module));
		} else {
			mViewHolder.headPhotoImage.setImageDrawable(null);
		}

		return convertView;
	}

	public static class ViewHolder {
		public TextView roleNameText;
		public ImageView headPhotoImage;
		public ImageView roleLockImage;
	}
}
