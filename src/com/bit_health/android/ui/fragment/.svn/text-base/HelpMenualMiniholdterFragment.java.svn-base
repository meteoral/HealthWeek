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

/**********************************************************************
 * 类名：HelpMenual3n1Fragment
 * 
 * 主要功能：测试模块中的帮助说明
 * 
 * @author 梁才学 创建日期：2014.1.24
 **********************************************************************/
public class HelpMenualMiniholdterFragment extends BaseFragment {

	private View view;
	private ImageView backIcon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.help_menual_holter, container, false);		

		backIcon = (ImageView) view.findViewById(R.id.help_miniholter_back_icon);
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
