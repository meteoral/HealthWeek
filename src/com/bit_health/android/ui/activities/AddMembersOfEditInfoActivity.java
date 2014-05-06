package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**********************************************************************
 * 类名：AddMembersOfEditInfoActivity
 * 
 * 主要功能：添加成员界面编辑"角色"、"头像"等信息共用的类
 * 
 * @author 梁才学 创建日期：2014.1.14
 **********************************************************************/
public class AddMembersOfEditInfoActivity extends BaseActivity {

	public static final int ROLE_FULL_NAME_AND_HEAD_PHOTO = 1;// 角色设置

	private LayoutInflater inflater;
	private View view;
	private RadioGroup choiceRoleHeadPhoto;
	private ImageView backImage;
	private String fullName;
	private int headPhotoChoose;

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
		
		choiceRoleHeadPhoto = (RadioGroup) view
				.findViewById(R.id.choice_role_head_photo_radiogroup);
		choiceRoleHeadPhoto
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkId) {
						// TODO Auto-generated method stub
						switch (checkId) {
						case R.id.choice_headphoto_father:
							setHeadPhotoChoose(R.drawable.father);
							setFullName("爸爸");
							exit();
							break;

						case R.id.choice_headphoto_mather:
							setHeadPhotoChoose(R.drawable.mather);
							setFullName("妈妈");
							exit();
							break;

						case R.id.choice_headphoto_grandpa:
							setHeadPhotoChoose(R.drawable.grandpa);
							setFullName("爷爷");
							exit();
							break;

						case R.id.choice_headphoto_grandma:
							setHeadPhotoChoose(R.drawable.grandma);
							setFullName("奶奶");
							exit();
							break;

						case R.id.choice_headphoto_sun:
							setHeadPhotoChoose(R.drawable.sun);
							setFullName("儿子");
							exit();
							break;

						case R.id.choice_headphoto_daughter:
							setHeadPhotoChoose(R.drawable.daughter);
							setFullName("女儿");
							exit();
							break;
						}
					}
				});
		// ((RadioButton) choiceRoleHeadPhoto.getChildAt(0)).toggle();
	}
	
	public int getHeadPhotoChoose() {
		return headPhotoChoose;
	}

	public void setHeadPhotoChoose(int headPhotoChoose) {
		this.headPhotoChoose = headPhotoChoose;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	public void exit() {
		Intent intent = new Intent();
		intent.putExtra("fullName", getFullName());
		intent.putExtra("headPhoto_id", getHeadPhotoChoose());
		setResult(ROLE_FULL_NAME_AND_HEAD_PHOTO, intent);

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
