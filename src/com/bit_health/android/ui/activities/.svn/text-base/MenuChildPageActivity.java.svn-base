package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.BgSettingsFragment;
import com.bit_health.android.ui.fragment.ContentFragment;
import com.bit_health.android.ui.fragment.FeedbackFragment;
import com.bit_health.android.ui.fragment.MoreInfoFragment;
import com.bit_health.android.ui.fragment.MyCareFragment;
import com.bit_health.android.ui.fragment.MyFamilyFragment;
import com.bit_health.android.ui.fragment.TipsFragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**********************************************************************
 * 类名：MenuChildPageActivity
 * 
 * 主要功能：点击侧滑菜单选项时，弹出来的界面
 * 
 * @author 梁才学 创建日期：2014.1.15
 **********************************************************************/
public class MenuChildPageActivity extends BaseActivity {

	public static MenuChildPageActivity mMenuChildPageActivity = null;
	public static final int MY_ATTENTION = 1;// 我的关注
	public static final int MY_FAMILY = 2;// 我的家庭
	public static final int MORE_INFO = 3;// 更多
	public static final int FEED_BACK = 4;// 标题栏上的反馈图标
	public static final int TIPS = 5;// 小贴士

	private LayoutInflater inflater;
	private View view;
	private MyFamilyFragment mMyFamilyFragment;
	private MyCareFragment mMyCareFragment;
	private AndroidActivityMananger mMananger;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mMenuChildPageActivity = this;
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.fragment_manager, null);
		setContentView(view);

		Bundle bundle = getIntent().getExtras();
		goToFragment(bundle.getInt("menu_sliding_fragment_index"));
		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	/**********************************************************************
	 * 方法描述：完成背景设置后，退出当前界面，显示内容主界面
	 * 
	 **********************************************************************/
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onResume();
		if (BgSettingsFragment.isSettingsBg) {
			this.finish();
		}
	}

	private void goToFragment(int fragment_index) {

		switch (fragment_index) {
		case MY_ATTENTION:

			mMyCareFragment = new MyCareFragment();
			FragmentManager fragmentManager1 = getFragmentManager();
			ContentFragment contentFragment1 = (ContentFragment) fragmentManager1
					.findFragmentByTag("MY_ATTENTION");
			fragmentManager1
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment1 == null ? mMyCareFragment
									: contentFragment1, "MY_ATTENTION")
					.commit();

			break;

		case MY_FAMILY:

			mMyFamilyFragment = new MyFamilyFragment();
			FragmentManager fragmentManager2 = getFragmentManager();
			ContentFragment contentFragment2 = (ContentFragment) fragmentManager2
					.findFragmentByTag("MY_FAMILY");
			fragmentManager2
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment2 == null ? mMyFamilyFragment
									: contentFragment2, "MY_FAMILY").commit();

			break;

		case MORE_INFO:

			FragmentManager fragmentManager3 = getFragmentManager();
			ContentFragment contentFragment3 = (ContentFragment) fragmentManager3
					.findFragmentByTag("MORE_INFO");
			fragmentManager3
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment3 == null ? new MoreInfoFragment()
									: contentFragment3, "MORE_INFO").commit();

			break;

		case FEED_BACK:

			FragmentManager fragmentManager4 = getFragmentManager();
			ContentFragment contentFragment4 = (ContentFragment) fragmentManager4
					.findFragmentByTag("EDIT_IMAGE");
			fragmentManager4
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment4 == null ? new FeedbackFragment()
									: contentFragment4, "EDIT_IMAGE").commit();
			break;

		case TIPS:

			FragmentManager fragmentManager5 = getFragmentManager();
			ContentFragment contentFragment5 = (ContentFragment) fragmentManager5
					.findFragmentByTag("TIPS");
			fragmentManager5
					.beginTransaction()
					.replace(
							R.id.fragment_container,
							contentFragment5 == null ? new TipsFragment()
									: contentFragment5, "TIPS").commit();

			break;
		}
	}

	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		super.callback(retCode, errorMsg);
		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			if (mMyFamilyFragment != null) {
				mMyFamilyFragment.refleshView();
			}
			break;

		default:
			showAlert(errorMsg);
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
			// 如果进行了删除角色的操作，重启主界面的activity
			if (mMyFamilyFragment != null && mMyFamilyFragment.isDelRole) {
				mMyFamilyFragment.isDelRole = false;
				mMananger = AndroidActivityMananger.getInstatnce();
				mMananger.switchActivity("MenuChildPageActivity",
						FourModuleManangerActivity.class);
				overridePendingTransition(R.anim.slide_left_in_2,
						R.anim.slide_right_out);
				return true;
			} else if (mMyCareFragment != null
					&& mMyCareFragment.isChangeCareList) {// 如果进行改变关注角色的操作，重启主界面的activity
				mMyCareFragment.isChangeCareList = false;
				mMananger = AndroidActivityMananger.getInstatnce();
				mMananger.switchActivity("MenuChildPageActivity",
						FourModuleManangerActivity.class);
				overridePendingTransition(R.anim.slide_left_in_2,
						R.anim.slide_right_out);
				return true;
			}
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
