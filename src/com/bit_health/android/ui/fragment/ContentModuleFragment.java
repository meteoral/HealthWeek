package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.adapter.ContentModuleVP_Adapter;
import com.bit_health.android.ui.framelayout.HomePageContentAdditional;
import com.bit_health.android.ui.framelayout.HomePageContentOne;
import com.bit_health.android.ui.framelayout.HomePageContentTwo;
import com.bit_health.android.util.BgSettingsImage;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**********************************************************************
 * 类名：ContentModuleFragment
 * 
 * @author 梁才学 主要功能：内容模块 创建日期：2013.12.9
 **********************************************************************/
public class ContentModuleFragment extends BaseFragment {

	private View view;
	private ViewPager mViewPager;// 页卡内容
	private List<View> mViewPager_views = new ArrayList<View>();// Tab页面列表
	private HomePageContentOne mHomePageContentOne;// 页卡1
	private HomePageContentTwo mHomePageContentTwo;// 页卡2
	private LayoutInflater inflater;
	private LinearLayout layout_bottom_dot;
	public ImageView bottom_icon_first;
	public ImageView bottom_icon_add;
	
	private ContentModuleVP_Adapter mContentAdapter;
	private boolean isOnCreate = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.content_module, container, false);
		isOnCreate = true;

		initViewPager();
		if (MenuFragment.isClickFromMenu()) {
			MenuFragment.setClickFromMenu(false);
			// 当点击菜单选项，等页面数据加载完毕之后，再滑动过去，就不出现滑动卡顿了
			((FourModuleManangerActivity) getActivity()).getSlidingMenu()
					.toggle();
		}
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	private void initViewPager() {
		inflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		layout_bottom_dot = (LinearLayout) view
				.findViewById(R.id.layout_home_page_bottom_dot);
		mViewPager = (ViewPager) view
				.findViewById(R.id.content_module_viewpager);
//		mViewPager.setBackgroundResource(FourModuleManangerActivity.homePageBgIndex);
		setBg();
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());		
		refreshViewPager();
	}

	// 添加关注角色到内容页面
	public void addAttentionRole(String roleId) {
		refreshViewPager();
		mViewPager.setCurrentItem(mViewPager_views.size()-2);
	}

	/******************************************************************
	 * 类描述：刷新本界面
	 * 
	 ******************************************************************/
	public void refreshViewPager() {
		// mViewPager.removeAllViews();// 清空ViewPager
		mViewPager_views.clear();// 清空集合
		layout_bottom_dot.removeAllViews();

		mHomePageContentOne = new HomePageContentOne(getActivity());
		mHomePageContentOne.homePageOneCreate();
		mViewPager_views.add(mHomePageContentOne);

		bottom_icon_first = (ImageView) inflater.inflate(
				R.layout.home_page_bottom_dot_image, null);
		layout_bottom_dot.addView(bottom_icon_first);

		// 循环遍历已关注的角色
		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(getActivity());
		List<RoleInfoBean> roles = mRoleCatchInfo.getRoles();
		String currentRoleId = AndroidConfiguration.getInstance(getActivity())
				.getRoleId();// 当前角色的id
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).mbCare && !currentRoleId.equals(roles.get(i).mId)) {
				HomePageContentAdditional homePageContentAdditianal = new HomePageContentAdditional(
						getActivity());
				homePageContentAdditianal
						.homePageAdditionalCreate(roles.get(i).mId);
				mViewPager_views.add(homePageContentAdditianal);
				ImageView iImage = homePageContentAdditianal.getBottomDot();
				iImage.setEnabled(false);
				layout_bottom_dot.addView(iImage);
			}
		}
		mHomePageContentTwo = new HomePageContentTwo(getActivity());
		mHomePageContentTwo.homePageTwoCreate();
		mViewPager_views.add(mHomePageContentTwo);

		bottom_icon_add = (ImageView) inflater.inflate(
				R.layout.home_page_bottom_dot_image, null);
		bottom_icon_add.setEnabled(false);
		layout_bottom_dot.addView(bottom_icon_add);

		mContentAdapter = new ContentModuleVP_Adapter(mViewPager_views);
		mViewPager.setAdapter(mContentAdapter);

		mViewPager.setCurrentItem(0);
	}

	/****************************************************************
	 * 方法描述：刷新 HomePageContentOne 界面
	 * 
	 *****************************************************************/
	public void refreshForHomePageContentOne() {
		mHomePageContentOne.refresh();
		setBg();
	}
	
	private void setBg(){
		// 如果是从设置背景回来，或者是从 onCreate 方法执行下来，就运行读取配置文件获取背景
		if (BgSettingsFragment.isSettingsBg || isOnCreate) {
			BgSettingsFragment.isSettingsBg = false;
			isOnCreate = false;			
			SharedPreferences preference = getActivity().getSharedPreferences(
					getActivity().getPackageName(), Context.MODE_PRIVATE);			
			BgSettingsImage mBgSettingsImage = new BgSettingsImage();
			int homePageBgIndex = mBgSettingsImage
					.getBgImage(preference.getInt("choiceBgIndex", 2));
			mViewPager.setBackgroundResource(
					homePageBgIndex);
		} 
	}
	
	/****************************************************************
	 * 方法描述：检查网络连接
	 * 
	 *****************************************************************/
	public void checkConnection(boolean isConnect) {
		mHomePageContentOne.connect(isConnect);
	}

	/******************************************************************
	 * 类描述：主要处理--监听ViewPager左右滑动的时候，界面底部的圆点跟着滑动
	 * 
	 ******************************************************************/
	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {

		}

		// 滑动过程调用
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		// 当页面滑动停止后调用，从效果也可以看到，当滑动停止之后，横线图片才开始滑动
		public void onPageSelected(int arg0) {

			if (arg0 == 0) {
				// 处理：ViewPager 与 SlidingMenu 之间的滑动冲突
				((FourModuleManangerActivity) getActivity()).getSlidingMenu()
						.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			} else {

				((FourModuleManangerActivity) getActivity()).getSlidingMenu()
						.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			}

			// 设置底部圆点 start
			for (int i = 0; i < mViewPager_views.size(); i++) {
				ImageView image = (ImageView) layout_bottom_dot.getChildAt(i);
				if (i == arg0) {
					image.setEnabled(true);
				} else {
					image.setEnabled(false);
				}
			}
			// 设置底部圆点 end
		}
	}
	
	public ViewPager getmViewPager() {
		return mViewPager;
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.ui.fragment.BaseFragment#getMainLayout()
	 */
	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mHomePageContentOne.setPageTextSize();
	}	
}
