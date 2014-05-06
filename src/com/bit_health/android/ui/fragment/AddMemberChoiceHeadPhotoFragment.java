package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**********************************************************************
 * 类名：AboutInfoFragment
 * 
 * 主要功能：添加成员中的选择头像界面
 * 
 * @author 梁才学 创建日期：2014.1.14
 **********************************************************************/
public class AddMemberChoiceHeadPhotoFragment extends BaseFragment {

	private View view;
	private ImageView backIcon;
	private RadioGroup choiceRoleHeadPhoto;
	private int headPhotoChoose;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.choice_headphoto_fragment, container,
				false);
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
							break;

						case R.id.choice_headphoto_mather:
							setHeadPhotoChoose(R.drawable.mather);
							break;

						case R.id.choice_headphoto_grandpa:
							setHeadPhotoChoose(R.drawable.grandpa);
							break;

						case R.id.choice_headphoto_grandma:
							setHeadPhotoChoose(R.drawable.grandma);
							break;

						case R.id.choice_headphoto_sun:
							setHeadPhotoChoose(R.drawable.sun);
							break;

						case R.id.choice_headphoto_daughter:
							setHeadPhotoChoose(R.drawable.daughter);
							break;
						}
					}
				});
		((RadioButton) choiceRoleHeadPhoto.getChildAt(0)).toggle();
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	public int getHeadPhotoChoose() {
		return headPhotoChoose;
	}

	public void setHeadPhotoChoose(int headPhotoChoose) {
		this.headPhotoChoose = headPhotoChoose;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
