package com.bit_health.android.ui.adapter;

import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.IBusinessCallback;
import com.bit_health.android.controllers.InterfaceService;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类描述：MyRoleListAdapter
 * 
 * 主要功能："我的关注"ListView的适配器
 * 
 * @author 梁才学
 **********************************************************************/
public class MyCareListAdapter extends BaseAdapter {

	private Context mActivity;
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;
	private List<RoleInfoBean> mRoles;
	private String mRoleid;

	// private HashMap<Integer, Boolean> isSelect;// 记录关注状态

	public MyCareListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mActivity = context;
		inflater = LayoutInflater.from(context);
		this.mRoles = RoleCatchInfo
				.getInstance(context.getApplicationContext()).getRoles();
		mRoleid = AndroidConfiguration.getInstance(
				context.getApplicationContext()).getRoleId();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mRoles == null ? 0 : mRoles.size();
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
			convertView = inflater.inflate(R.layout.my_care_list_item, null);
			mViewHolder = new ViewHolder();

			mViewHolder.layout_item = convertView
					.findViewById(R.id.layout_my_attention_item);
			mViewHolder.attentionHeadImage = (ImageView) convertView
					.findViewById(R.id.my_attention_head_image);
			mViewHolder.attentionStateCheckBox = (CheckBox) convertView
					.findViewById(R.id.attention_state);

			mViewHolder.attentionNameText = (TextView) convertView
					.findViewById(R.id.my_attention_name);

			mViewHolder.attentionEnglishNameText = (TextView) convertView
					.findViewById(R.id.my_attention_english_name);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		RoleInfoBean role = mRoles.get(position);
		if (role.mId.equals(mRoleid)) {
			// 在“我的关注”列表中，是自己的角色就不显示
			// mViewHolder.attentionStateCheckBox.setVisibility(View.GONE);
			mViewHolder.layout_item.setVisibility(View.GONE);
		} else {
			// mViewHolder.attentionStateCheckBox.setVisibility(View.VISIBLE);
			mViewHolder.layout_item.setVisibility(View.VISIBLE);
			mViewHolder.attentionStateCheckBox.setChecked(role.mbCare);
			if (role.mbCare) {
				mViewHolder.attentionStateCheckBox.setText("已关注");
			} else {
				mViewHolder.attentionStateCheckBox.setText("关注");
			}

		}

		Bitmap bmp = null;
		if ((bmp = HeadImageUtil.getHeadCatchImage(role.mId)) != null) {
			mViewHolder.attentionHeadImage.setImageBitmap(bmp);
		}else{
			mViewHolder.attentionHeadImage.setImageResource(HeadImageUtil
					.getLocalHeadimage(mRoles.get(position).mFullName));
		}
	
		mViewHolder.attentionNameText.setText(role.mFullName);
		mViewHolder.attentionEnglishNameText.setText(role.mName);
		setListViewTextSize();
		return convertView;
	}

	// 设置listview的字体大小
	private void setListViewTextSize() {
		
		SharedPreferences preference = mActivity.getSharedPreferences(mActivity.getPackageName(), Context.MODE_PRIVATE);		
		int currentIndex = preference.getInt("currentIndex", 0);
		
		if (currentIndex == 2) {
			mViewHolder.attentionNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_31px));
			mViewHolder.attentionEnglishNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {
			mViewHolder.attentionNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_28px));
			mViewHolder.attentionEnglishNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {
			mViewHolder.attentionNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_25px));
			mViewHolder.attentionEnglishNameText.setTextSize(
					TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
							.getDimensionPixelSize(R.dimen.set_font_size_25px));
		}
	}

	public void setCheckCare(int position, boolean bCheck,
			IBusinessCallback iCallback) {
		RoleInfoBean role = this.mRoles.get(position);
		InterfaceService.getInstance(mActivity.getApplicationContext())
				.careRole(role.mId, bCheck, iCallback);
	}

	public static class ViewHolder {
		public View layout_item;
		public ImageView attentionHeadImage;
		public CheckBox attentionStateCheckBox;
		public TextView attentionNameText;
		public TextView attentionEnglishNameText;
	}
}
