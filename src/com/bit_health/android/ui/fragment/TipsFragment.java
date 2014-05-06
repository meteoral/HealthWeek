package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.activities.MoreInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**********************************************************************
 * 类名：TipsFragment
 * 
 * 主要功能：菜单中的 "小贴士"选项
 * 
 * @author 梁才学  创建日期：2014.2.8
 **********************************************************************/
public class TipsFragment extends BaseFragment {

	private View view;
	private View layoutTipsXindian;// 心电小贴士	
	private View layoutTipsMaibo;// 脉搏小贴士	
	private View layoutTipsXueya;// 血压小贴士
	private View layoutTipsXuetang;// 血糖小贴士
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
		super.onCreateView(inflater, container, savedInstanceState);
		
		view = inflater.inflate(R.layout.tips_fragment, container, false);
		backIcon = (ImageView) view.findViewById(R.id.tips_back_icon);

		layoutTipsXindian = view.findViewById(R.id.tips_of_xindian);
		layoutTipsMaibo = view.findViewById(R.id.tips_of_maibo);
		layoutTipsXueya = view.findViewById(R.id.tips_of_xueya);
		layoutTipsXuetang = view.findViewById(R.id.tips_of_xuetang);

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
		layoutTipsXindian.setOnClickListener(listener);
		layoutTipsMaibo.setOnClickListener(listener);
		layoutTipsXueya.setOnClickListener(listener);
		layoutTipsXuetang.setOnClickListener(listener);
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.tips_back_icon:
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.tips_of_xindian:
				switchToMoreInfoActivity(MoreInfoActivity.TIPS_OF_XINDIAN);
				break;
				
			case R.id.tips_of_maibo:
				switchToMoreInfoActivity(MoreInfoActivity.TIPS_OF_MAIBO);
				break;
				
			case R.id.tips_of_xueya:
				switchToMoreInfoActivity(MoreInfoActivity.TIPS_OF_XUEYA);
				break;
				
			case R.id.tips_of_xuetang:
				switchToMoreInfoActivity(MoreInfoActivity.TIPS_OF_XUETANG);
				break;
				
			default:

			}

		}
	}

	private void switchToMoreInfoActivity(int fragment_index) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				MoreInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("fragment_index", fragment_index);
		intent.putExtras(bundle);
		getActivity().startActivity(intent);

		/*
		 * 注意：此方法只能在startActivity和finish方法之后调用。 第一个参数为所进入的Activity的动画效果,
		 * 第二参数为所离开的Activity的动画效果
		 */
		getActivity().overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_left_out);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
