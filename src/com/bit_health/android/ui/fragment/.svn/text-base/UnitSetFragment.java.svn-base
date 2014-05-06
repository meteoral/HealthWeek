package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;

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
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**********************************************************************
 * 类名：UnitSetFragment
 * 
 * @author 梁才学 主要功能：菜单中的 "单元设置"选项 创建日期：2013.12.10
 **********************************************************************/
public class UnitSetFragment extends BaseFragment {

	private View view;
	private ImageView backIcon;
	private RadioGroup mRadioGroup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.unit_set_fragment, container, false);
		mRadioGroup = (RadioGroup) view.findViewById(R.id.unit_radiogroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				case R.id.unit_mmHg:
					Toast.makeText(getActivity(), "选择单位 mmHg", Toast.LENGTH_SHORT).show();
					break;

				case R.id.unit_kPa:
					Toast.makeText(getActivity(), "选择单位  kPa", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		});

		((RadioButton) mRadioGroup.getChildAt(0)).toggle();// 设置默认RadioButton为第一个

		backIcon = (ImageView) view.findViewById(R.id.unit_set_back_icon);
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
			}
		});

		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
