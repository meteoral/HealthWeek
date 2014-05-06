package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.MenuChildPageActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**********************************************************************
 * 类名：FontSizeSetFragment
 * 
 * @author 梁才学 主要功能："字体设置" 创建日期：2013.12.10
 **********************************************************************/
public class FontSizeSetFragment extends BaseFragment {
	
	public static boolean isSetFontSize = false;// 判断是否设置了字体
	private final float FONT_TEXT_SIZE = 3.0f;// 以 3.0 的幅度来改变字体大小
	private int currentIndex = 0;// 小号=0；中号=1；大号=2
	private float font_size_range = 0;// 设置字体的变化范围
	
	private RadioGroup mRadioGroup;
	private View view;
	private ImageView backIcon;
	
	private SharedPreferences preference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.home_page_set_fragment, container,
				false);
		backIcon = (ImageView) view.findViewById(R.id.home_page_set_back_icon);
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
			}
		});

		preference = getActivity().getSharedPreferences(
				getActivity().getPackageName(), Context.MODE_PRIVATE);
		currentIndex = preference.getInt("currentIndex", 0);
		font_size_range = preference.getFloat("font_size_range", 0);

		setRadioGroup();
		setAllTextSizeOfApp(font_size_range);
		return view;
	}

	private void setRadioGroup() {

		final SharedPreferences.Editor edit = preference.edit();
		
		mRadioGroup = (RadioGroup) view.findViewById(R.id.font_size_rg);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				case R.id.font_big_size:
					if (currentIndex == 2) {
						return;
					}
					if (currentIndex == 1) {
						setAllTextSizeOfApp(FONT_TEXT_SIZE);
					}
					if (currentIndex == 0) {
						setAllTextSizeOfApp(2 * FONT_TEXT_SIZE);
					}					
//					currentIndex = 2;
//					font_size_range = 2 * FONT_TEXT_SIZE;
					edit.putInt("currentIndex", 2);
					edit.putFloat("font_size_range", 2 * FONT_TEXT_SIZE);
					edit.commit();
					isSetFontSize = true;
					break;
				case R.id.font_middle_size:
					if (currentIndex == 1) {
						return;
					}
					if (currentIndex == 2) {
						setAllTextSizeOfApp(-FONT_TEXT_SIZE);
					}
					if (currentIndex == 0) {
						setAllTextSizeOfApp(FONT_TEXT_SIZE);
					}
//					currentIndex = 1;
//					font_size_range = FONT_TEXT_SIZE;
					edit.putInt("currentIndex", 1);
					edit.putFloat("font_size_range", FONT_TEXT_SIZE);
					edit.commit();
					isSetFontSize = true;
					break;
				case R.id.font_little_size:
					if (currentIndex == 0) {
						return;
					}
					if (currentIndex == 2) {
						setAllTextSizeOfApp(-2 * FONT_TEXT_SIZE);
					}
					if (currentIndex == 1) {
						setAllTextSizeOfApp(-FONT_TEXT_SIZE);
					}
//					currentIndex = 0;
//					font_size_range = 0;
					edit.putInt("currentIndex", 0);
					edit.putFloat("font_size_range", 0);
					edit.commit();
					isSetFontSize = true;
					break;
				default:
					break;
				}

				if (isSetFontSize) {
					getActivity().finish();
					MenuChildPageActivity.mMenuChildPageActivity.finish();
					getActivity().overridePendingTransition(
							R.anim.slide_left_in, R.anim.slide_right_out);
				}
			}
		});
		((RadioButton) mRadioGroup.getChildAt(currentIndex)).toggle();
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
