package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.AboutInfoFragment;
import com.bit_health.android.ui.fragment.BgSettingsFragment;
import com.bit_health.android.ui.fragment.ContentFragment;
import com.bit_health.android.ui.fragment.HelpMenual3n1Fragment;
import com.bit_health.android.ui.fragment.HelpMenualMiniholdterFragment;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;
import com.bit_health.android.ui.fragment.RemindSetFragment;
import com.bit_health.android.ui.fragment.TipsContentsFragment;
import com.bit_health.android.ui.fragment.UnitSetFragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**********************************************************************
 * 类名：MoreInfoActivity
 * 
 * 主要功能："更多"选项的界面显示
 * 
 * @author 梁才学 创建日期：2013.12.26
 **********************************************************************/
public class MoreInfoActivity extends BaseActivity {

	public static final int REMIND_SET = 1;// 提醒设置
	public static final int UNIT_SET = 2;// 单位设置
	public static final int FONT_SET = 3;// 字体设置
	public static final int INFO_ABOUT = 4;// 关于
	public static final int HELP_MENUAL_3N1 = 5;// 三个一使用说明
	public static final int HELP_MENUAL_MINI_HOLTER = 6;// Mini-Holter佩戴方法
	public static final int TIPS_OF_XINDIAN = 7;// 小贴士之心电
	public static final int TIPS_OF_XUEYA = 8;// 小贴士之血压
	public static final int TIPS_OF_XUETANG = 9;// 小贴士之血糖
	public static final int TIPS_OF_MAIBO = 11;// 小贴士之血糖
	public static final int BACKGROUND_SET = 10;// 背景设置

	private LayoutInflater inflater;
	private View view;
	private TipsContentsFragment mTipsContentsFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.fragment_manager, null);
		setContentView(view);

		Bundle bundle = getIntent().getExtras();
		goToFragment(bundle.getInt("fragment_index"));
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	private void goToFragment(int fragment_index) {

		switch (fragment_index) {
		case REMIND_SET:

			FragmentManager fragmentManager1 = getFragmentManager();
			ContentFragment contentFragment1 = (ContentFragment) fragmentManager1
					.findFragmentByTag("REMIND_SET");
			fragmentManager1
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment1 == null ? new RemindSetFragment()
									: contentFragment1, "REMIND_SET").commit();

			break;

		case UNIT_SET:

			FragmentManager fragmentManager2 = getFragmentManager();
			ContentFragment contentFragment2 = (ContentFragment) fragmentManager2
					.findFragmentByTag("UNIT_SET");
			fragmentManager2
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment2 == null ? new UnitSetFragment()
									: contentFragment2, "UNIT_SET").commit();

			break;

		case FONT_SET:

			FragmentManager fragmentManager3 = getFragmentManager();
			ContentFragment contentFragment3 = (ContentFragment) fragmentManager3
					.findFragmentByTag("FONT_SET");
			fragmentManager3
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment3 == null ? new FontSizeSetFragment()
									: contentFragment3, "FONT_SET").commit();

			break;

		case INFO_ABOUT:

			FragmentManager fragmentManager4 = getFragmentManager();
			ContentFragment contentFragment4 = (ContentFragment) fragmentManager4
					.findFragmentByTag("ABOUT_INFO");
			fragmentManager4
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment4 == null ? new AboutInfoFragment()
									: contentFragment4, "ABOUT_INFO").commit();
			break;

		case HELP_MENUAL_3N1:
			FragmentManager fragmentManager5 = getFragmentManager();
			ContentFragment contentFragment5 = (ContentFragment) fragmentManager5
					.findFragmentByTag("HELP_MENUAL_3N1");
			fragmentManager5
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment5 == null ? new HelpMenual3n1Fragment()
									: contentFragment5, "HELP_MENUAL_3N1")
					.commit();
			break;

		case HELP_MENUAL_MINI_HOLTER:
			FragmentManager fragmentManager6 = getFragmentManager();
			ContentFragment contentFragment6 = (ContentFragment) fragmentManager6
					.findFragmentByTag("HELP_MENUAL_MINI");
			fragmentManager6
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment6 == null ? new HelpMenualMiniholdterFragment()
									: contentFragment6, "HELP_MENUAL_MINI")
					.commit();
			break;

		case TIPS_OF_XINDIAN:
			mTipsContentsFragment = new TipsContentsFragment("关于心电");
			FragmentManager fragmentManager7 = getFragmentManager();
			ContentFragment contentFragment7 = (ContentFragment) fragmentManager7
					.findFragmentByTag("TIPS_OF_XINDIAN");
			fragmentManager7
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment7 == null ? mTipsContentsFragment
									: contentFragment7, "TIPS_OF_XINDIAN")
					.commit();
			break;

		case TIPS_OF_XUEYA:
			mTipsContentsFragment = new TipsContentsFragment("关于血压");
			FragmentManager fragmentManager8 = getFragmentManager();
			ContentFragment contentFragment8 = (ContentFragment) fragmentManager8
					.findFragmentByTag("TIPS_OF_XUEYA");
			fragmentManager8
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment8 == null ? mTipsContentsFragment
									: contentFragment8, "TIPS_OF_XUEYA")
					.commit();
			break;

		case TIPS_OF_XUETANG:
			mTipsContentsFragment = new TipsContentsFragment("关于血糖");
			FragmentManager fragmentManager9 = getFragmentManager();
			ContentFragment contentFragment9 = (ContentFragment) fragmentManager9
					.findFragmentByTag("TIPS_OF_XUETANG");
			fragmentManager9
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment9 == null ? mTipsContentsFragment
									: contentFragment9, "TIPS_OF_XUETANG")
					.commit();
			break;

		case BACKGROUND_SET:

			FragmentManager fragmentManager10 = getFragmentManager();
			ContentFragment contentFragment10 = (ContentFragment) fragmentManager10
					.findFragmentByTag("BACKGROUND_SET");
			fragmentManager10
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment10 == null ? new BgSettingsFragment()
									: contentFragment10, "BACKGROUND_SET")
					.commit();

			break;

		case TIPS_OF_MAIBO:
			mTipsContentsFragment = new TipsContentsFragment("关于脉搏");
			FragmentManager fragmentManager11 = getFragmentManager();
			ContentFragment contentFragment11 = (ContentFragment) fragmentManager11
					.findFragmentByTag("TIPS_OF_MAIBO");
			fragmentManager11
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment11 == null ? mTipsContentsFragment
									: contentFragment11, "TIPS_OF_MAIBO")
					.commit();
			break;
		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			
			if(mTipsContentsFragment != null){
				return(mTipsContentsFragment.exit());
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
