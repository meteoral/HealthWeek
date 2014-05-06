package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.activities.LoginIdentityActivity;
import com.bit_health.android.ui.activities.MenuChildPageActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：MenuFragment
 * 
 * 主要功能：显示menu菜单界面
 * 
 * @author 梁才学 创建日期：2013.12.9
 **********************************************************************/
public class MenuFragment extends Fragment {

	private static boolean isClickFromMenu = false;// 记录是否点击了菜单页中的"主页"选项
	private int mIndex = 0;// 用来标记第几个Fragment
	private AndroidActivityMananger mMananger;

	private View view;
	// SlidingMenu 中的各选
	private View reLoginIdentity, myCare, myFamily, reLogin, tips, moreInfo;
	private ImageView editImage, shareImage;

	private TextView re_login_identity_text;
	private TextView my_care_text;
	private TextView my_family_text;
	private TextView tips_text;
	private TextView more_info_text;
	private TextView re_login_text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.menu_fragment, container, false);

		reLoginIdentity = view
				.findViewById(R.id.layout_re_login_identity);
		myCare = view.findViewById(R.id.layout_my_care);
		myFamily = view.findViewById(R.id.layout_my_family);
		reLogin = view.findViewById(R.id.layout_re_login);
		tips = view.findViewById(R.id.layout_tips);
		moreInfo = view.findViewById(R.id.layout_more_info);
		editImage = (ImageView) view.findViewById(R.id.menu_page_edit_image);
		shareImage = (ImageView) view.findViewById(R.id.menu_page_share_image);
		re_login_identity_text = (TextView) view
				.findViewById(R.id.re_login_identity_text);
		my_care_text = (TextView) view.findViewById(R.id.my_care_text);
		my_family_text = (TextView) view.findViewById(R.id.my_family_text);
		tips_text = (TextView) view.findViewById(R.id.tips_text);
		more_info_text = (TextView) view.findViewById(R.id.more_info_text);
		re_login_text = (TextView) view.findViewById(R.id.re_login_text);

		MyViewListener listener = new MyViewListener();
		reLoginIdentity.setOnClickListener(listener);
		myCare.setOnClickListener(listener);
		myFamily.setOnClickListener(listener);
		reLogin.setOnClickListener(listener);
		tips.setOnClickListener(listener);
		moreInfo.setOnClickListener(listener);
		editImage.setOnClickListener(listener);
		shareImage.setOnClickListener(listener);
		setAllTextSize();
		return view;
	}

	/****************************************************************
	 * 方法描述：设置本界面的字体大小
	 ****************************************************************/
	public void setAllTextSize() {

		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		int currentIndex = preference.getInt("currentIndex", 0);
		
		if (currentIndex == 2) {
			re_login_identity_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			my_care_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			my_family_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			tips_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			more_info_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			re_login_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {
			re_login_identity_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			my_care_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			my_family_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			tips_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			more_info_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			re_login_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {
			re_login_identity_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			my_care_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			my_family_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			tips_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			more_info_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			re_login_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getActivity().getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		// 当设置了字体大小，返回主界面是，设置侧滑菜单中字体大小
//		if (FontSizeSetFragment.isSetFontSize) {
//			FontSizeSetFragment.isSetFontSize = false;
//			setAllTextSize();
//		}
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.layout_re_login_identity:
				// 返回选择身份登陆界面
				LoginIdentityActivity.isComeFromMenuFragment = true;
				mMananger = AndroidActivityMananger.getInstatnce();
				mMananger.switchActivity("FourModuleManangerActivity",
						LoginIdentityActivity.class);
				getActivity().overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;

			case R.id.layout_my_care:
				switchToMoreInfoActivity(MenuChildPageActivity.MY_ATTENTION);
				break;

			case R.id.layout_my_family:
				switchToMoreInfoActivity(MenuChildPageActivity.MY_FAMILY);
				break;

			case R.id.layout_re_login:
				// 返回登录界面
				Activity activity = getActivity();
				if (activity instanceof FourModuleManangerActivity) {
					FourModuleManangerActivity mainAct = (FourModuleManangerActivity) activity;
					mainAct.logOut();
				}
				break;

			case R.id.layout_tips:
				switchToMoreInfoActivity(MenuChildPageActivity.TIPS);
				break;

			case R.id.layout_more_info:
				switchToMoreInfoActivity(MenuChildPageActivity.MORE_INFO);
				break;

			case R.id.menu_page_edit_image:
				switchToMoreInfoActivity(MenuChildPageActivity.FEED_BACK);
				break;

			case R.id.menu_page_share_image:
				shareContent();
				break;

			default:

			}
		}
	}

	/**********************************************************************
	 * 方法描述：点击侧滑菜单选项时，跳转到的activity
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void switchToMoreInfoActivity(int fragment_index) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				MenuChildPageActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("menu_sliding_fragment_index", fragment_index);
		intent.putExtras(bundle);
		getActivity().startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_left_out);
	}

	/**********************************************************************
	 * 方法描述：分享功能
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	public void shareContent() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		// intent.setType("text/plain");

		// Intent.EXTRA_SUBJECT 即主题，只对分享中的邮件有作用，
		// 需要把主题去掉，否则使用分享中的蓝牙时，内容会被这个主题掩盖
		// intent.putExtra(Intent.EXTRA_SUBJECT, "分享");

		intent.putExtra(Intent.EXTRA_TEXT, "中科汇康，你值得信赖!!!");// 这里是分享的内容
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, "分享至"));

	}

	public static boolean isClickFromMenu() {
		return isClickFromMenu;
	}

	public static void setClickFromMenu(boolean isClickFromMenu) {
		MenuFragment.isClickFromMenu = isClickFromMenu;
	}

	public int getmIndex() {
		return mIndex;
	}

	public void setmIndex(int index) {
		this.mIndex = index;
	}
}
