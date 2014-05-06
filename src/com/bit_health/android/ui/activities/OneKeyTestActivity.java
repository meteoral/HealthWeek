package com.bit_health.android.ui.activities;

import java.util.Hashtable;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.ContentFragment;
import com.bit_health.android.ui.fragment.OneKeyStepOneFragment;
import com.bit_health.android.ui.fragment.OneKeyStepThreeFragment;
import com.bit_health.android.ui.fragment.OneKeyStepTwoFragment;
import com.bit_health.android.ui.framelayout.HomePageContentOne;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**********************************************************************
 * 类名：OneKeyTestActivity
 * 
 * 主要功能：一键测试主界面入口
 * 
 * @author 梁才学 创建日期：2013.12.5
 **********************************************************************/
public class OneKeyTestActivity extends BaseActivity {

	public static final int STEP_ONE_FRAGMENT = 1;
	// public static final int STEP_TWO_FRAGMENT = 2;
	public static final int STEP_THREE_FRAGMENT = 3;
	public static int currentFragment = 0;// 标记当前 fragment 是哪个

	private LayoutInflater inflater;
	private View view;
	private ImageView backIcon;
	String fromPath = null;

	public Hashtable<String, String> mHashTestItems = new Hashtable<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.one_key_test_fragment_manager, null);
		setContentView(view);
		initData();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			fromPath = bundle
					.getString(HomePageContentOne.GO_TO_OneKeyTestActivity);
		}
		if (HomePageContentOne.TEST_RIGHT_NOW.equals(fromPath)) {
			addStepThreeFragment();
		} else {
			addStepOneFragment();
		}

		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	/**********************************************************************
	 * 方法描述：点击“一键测试”按钮执行这里
	 * 
	 **********************************************************************/
	private void addStepOneFragment() {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();
		ContentFragment contentFragment = (ContentFragment) fragmentManager
				.findFragmentByTag("ONE_KEY_STEP_ONE");
		fragmentManager
				.beginTransaction()
				.replace(
						R.id.one_key_test_fragment_container,
						contentFragment == null ? new OneKeyStepOneFragment()
								: contentFragment, "ONE_KEY_STEP_ONE").commit();

		currentFragment = OneKeyTestActivity.STEP_ONE_FRAGMENT;
	}

	/**********************************************************************
	 * 方法描述：点击“立即测试”按钮直接跳转到第三步骤
	 * 
	 **********************************************************************/
	private void addStepThreeFragment() {
		FragmentManager fragmentManager = getFragmentManager();
		ContentFragment contentFragment = (ContentFragment) fragmentManager
				.findFragmentByTag("ONE_KEY_STEP_TWO");
		fragmentManager
				.beginTransaction()
				.replace(
						R.id.one_key_test_fragment_container,
						contentFragment == null ? new OneKeyStepThreeFragment()
								: contentFragment, "ONE_KEY_STEP_TWO").commit();

		OneKeyTestActivity.currentFragment = OneKeyTestActivity.STEP_THREE_FRAGMENT;
	}

	/**********************************************************************
	 * 方法描述：添加一键测试的步骤二的内容
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void addStepTwoFragment() {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();
		ContentFragment contentFragment = (ContentFragment) fragmentManager
				.findFragmentByTag("ONE_KEY_STEP_TWO");
		fragmentManager
				.beginTransaction()
				.replace(
						R.id.one_key_test_fragment_container,
						contentFragment == null ? new OneKeyStepTwoFragment()
								: contentFragment, "ONE_KEY_STEP_TWO").commit();

		// currentFragment = OneKeyTestActivity.STEP_TWO_FRAGMENT;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		backIcon = (ImageView) view.findViewById(R.id.one_key_test_back_icon);

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);

	}

	/**********************************************************************
	 * 方法描述：
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 **********************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if (currentFragment == STEP_ONE_FRAGMENT
					|| (currentFragment == STEP_THREE_FRAGMENT && HomePageContentOne.TEST_RIGHT_NOW
							.equals(fromPath))) {
				OneKeyTestActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
			} else if (currentFragment == STEP_THREE_FRAGMENT) {
				addStepOneFragment();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：一键测试界面中的控件监听
	 * 
	 * @author 梁才学 创建日期：2013.12.5
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.one_key_test_back_icon:

				if (currentFragment == STEP_ONE_FRAGMENT
						|| (currentFragment == STEP_THREE_FRAGMENT && HomePageContentOne.TEST_RIGHT_NOW
								.equals(fromPath))) {
					OneKeyTestActivity.this.finish();
					overridePendingTransition(R.anim.slide_left_in,
							R.anim.slide_right_out);
				} else if (currentFragment == STEP_THREE_FRAGMENT) {
					addStepOneFragment();
				}
				break;

			default:

			}

		}
	}
}
