package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**********************************************************************
 * 类名：ChangeRoleNameActivity
 * 
 * 主要功能："家庭成员"界面中的修改角色名
 * 
 * @author 梁才学 创建日期：2014.1.14
 **********************************************************************/
public class ChangeRoleNameActivity extends BaseActivity {

	public static final int CHANGE_ROLE_FULL_NAME = 1;// 修改角色名的标记

	private LayoutInflater inflater;
	private View view;
	private RadioGroup changeFullName_RG;
	private ImageView backImage;
	private String changeFullNameString;
	private int changeHeadPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.choice_headphoto_fragment, null);
		setContentView(view);

		initView();
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	private void initView() {
		// TODO Auto-generated method stub
		MyViewListener listener = new MyViewListener();
		backImage = (ImageView) view.findViewById(R.id.choice_role_back_iamge);
		backImage.setOnClickListener(listener);

		changeFullName_RG = (RadioGroup) view
				.findViewById(R.id.choice_role_head_photo_radiogroup);
		changeFullName_RG
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkId) {
						// TODO Auto-generated method stub
						switch (checkId) {
						case R.id.choice_headphoto_father:
							setChangeHeadPhoto(R.drawable.father);
							setChangeFullNameString("爸爸");
							exit();
							break;

						case R.id.choice_headphoto_mather:
							setChangeHeadPhoto(R.drawable.mather);
							setChangeFullNameString("妈妈");
							exit();
							break;

						case R.id.choice_headphoto_grandpa:
							setChangeHeadPhoto(R.drawable.grandpa);
							setChangeFullNameString("爷爷");
							exit();
							break;

						case R.id.choice_headphoto_grandma:
							setChangeHeadPhoto(R.drawable.grandma);
							setChangeFullNameString("奶奶");
							exit();
							break;

						case R.id.choice_headphoto_sun:
							setChangeHeadPhoto(R.drawable.sun);
							setChangeFullNameString("儿子");
							exit();
							break;

						case R.id.choice_headphoto_daughter:
							setChangeHeadPhoto(R.drawable.daughter);
							setChangeFullNameString("女儿");
							exit();
							break;
						}
					}
				});
	}

	public String getChangeFullNameString() {
		return changeFullNameString;
	}

	public void setChangeFullNameString(String changeFullNameString) {
		this.changeFullNameString = changeFullNameString;
	}

	public int getChangeHeadPhoto() {
		return changeHeadPhoto;
	}

	public void setChangeHeadPhoto(int changeHeadPhoto) {
		this.changeHeadPhoto = changeHeadPhoto;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	public void exit() {
		Intent intent = new Intent();
		intent.putExtra("changeFullName", getChangeFullNameString());
		intent.putExtra("changeHeadPhoto_id", getChangeHeadPhoto());
		setResult(CHANGE_ROLE_FULL_NAME, intent);

		finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	class MyViewListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.choice_role_back_iamge:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			default:
				break;
			}
		}
	}
}
