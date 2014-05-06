package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AnalyzeCath;
import com.bit_health.android.controllers.beans.BpScoreBean;
import com.bit_health.android.controllers.beans.BsScoreBean;
import com.bit_health.android.controllers.beans.EcgScoreBean;
import com.bit_health.android.controllers.beans.PpgScoreBean;
import com.bit_health.android.ui.adapter.ContentModuleVP_Adapter;
import com.bit_health.android.ui.framelayout.HealthStateDetails;
import com.bit_health.android.ui.framelayout.HomePageContentOne;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：SeeDetailsActivity
 * 
 * 主要功能：查看健康状况的详情界面
 * 
 * @author 梁才学 创建日期：2014.3.31
 **********************************************************************/
public class SeeDetailsActivity extends BaseActivity {

	private LayoutInflater inflater;
	private View view;
	private ImageView backIcon;
	private String roleId;
	private ViewPager mViewPager;
	private List<View> mViewPager_views = new ArrayList<View>();
	private ContentModuleVP_Adapter mContentAdapter;
	private HealthStateDetails healthStateDetails;
	private LinearLayout layoutBottomDot;
	public ImageView bottomIcon;
	private TextView titleNameText;// 标题栏的文本

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.login);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.see_details, null);
		setContentView(view);
		initView();
		initData();

		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	private void initData() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			roleId = bundle.getString(HomePageContentOne.CURRENT_ROLE_ID);

			List<EcgScoreBean> ecgScores = AnalyzeCath.getInstance(this).mListEcgScores;
			if (ecgScores != null) {
				for (EcgScoreBean ecgBean : ecgScores) {
					if (roleId.equals(ecgBean.mUserId)) {
						healthStateDetails = new HealthStateDetails(roleId, this);
						healthStateDetails.ecgHealthState(ecgBean);
						mViewPager_views.add(healthStateDetails);
						titleNameText.setText("心电整体状况");
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);

						break;
					}
				}
			}

			List<PpgScoreBean> ppgScores = AnalyzeCath.getInstance(this).mListPpgScores;
			if (ppgScores != null) {
				for (PpgScoreBean ppgBean : ppgScores) {
					if (roleId.equals(ppgBean.mUserId)) {
						healthStateDetails = new HealthStateDetails(roleId, this);
						healthStateDetails.ppgHealthState(ppgBean);
						mViewPager_views.add(healthStateDetails);
						titleNameText.setText("脉搏整体状况");
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);

						break;
					}
				}
			}

			List<BpScoreBean> bpScores = AnalyzeCath.getInstance(this).mListBpScores;
			if (bpScores != null) {
				for (BpScoreBean bpBean : bpScores) {
					if (roleId.equals(bpBean.mUserId)) {
						healthStateDetails = new HealthStateDetails(roleId, this);
						healthStateDetails.bpHealthState(bpBean);
						mViewPager_views.add(healthStateDetails);
						titleNameText.setText("血压整体状况");
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);

						break;
					}
				}
			}

			List<BsScoreBean> bsScores = AnalyzeCath.getInstance(this).mListBsScores;
			if (bsScores != null) {
				for (BsScoreBean bsBean : bsScores) {
					if (roleId.equals(bsBean.mUserId)) {
						healthStateDetails = new HealthStateDetails(roleId, this);
						healthStateDetails.bsHealthState(bsBean);
						mViewPager_views.add(healthStateDetails);
						titleNameText.setText("血糖整体状况");
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);

						break;
					}
				}
			}

			if (mViewPager_views.size() == 0) {
				healthStateDetails = new HealthStateDetails(roleId, this);
				healthStateDetails.noHealthData();
				mViewPager_views.add(healthStateDetails);
			}
			mContentAdapter = new ContentModuleVP_Adapter(mViewPager_views);
			mViewPager.setAdapter(mContentAdapter);
			mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
			setCurrentPageView(0);
		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		backIcon = (ImageView) view.findViewById(R.id.see_details_back_icon);
		mViewPager = (ViewPager) findViewById(R.id.health_details_vp);
		layoutBottomDot = (LinearLayout) findViewById(R.id.see_detail_bottom_dot);
		titleNameText = (TextView) findViewById(R.id.health_state_title);

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
	}

	/******************************************************************
	 * 类描述：主要处理--监听ViewPager左右滑动的时候
	 * 
	 * @author 梁才学 创建日期：2014.4.1
	 ******************************************************************/
	private class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		/******************************************************************
		 * 方法描述：当页面滑动停止之后，设置标题栏显示相应的内容
		 * 
		 * @param arg0
		 *            = 0, 1, 2 即当前页的位置
		 ******************************************************************/
		public void onPageSelected(int arg0) {
			setCurrentPageView(arg0);
		}
	}

	/******************************************************************
	 * 方法描述：设置 ViewPager 标题栏显示的内容
	 * 
	 * @param arg0
	 *            = 0, 1, 2,3 即当前页的位置
	 ******************************************************************/
	private void setCurrentPageView(int arg0) {

		if (mViewPager_views.get(arg0) instanceof HealthStateDetails) {
			HealthStateDetails hd = (HealthStateDetails) mViewPager_views
					.get(arg0);
			if (hd.getScoreType().equals("EcgScoreBean")) {
				titleNameText.setText("心电整体状况");
			} else if (hd.getScoreType().equals("BpScoreBean")) {
				titleNameText.setText("血压整体状况");
			} else if (hd.getScoreType().equals("BsScoreBean")) {
				titleNameText.setText("血糖整体状况");
			} else if (hd.getScoreType().equals("PpgScoreBean")) {
				titleNameText.setText("脉搏整体状况");
			}
		}

		// 设置底部圆点 start
		for (int i = 0; i < mViewPager_views.size(); i++) {
			ImageView image = (ImageView) layoutBottomDot.getChildAt(i);
			if (i == arg0) {
				image.setEnabled(true);
			} else {
				image.setEnabled(false);
			}
		}
		// 设置底部圆点 end
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.see_details_back_icon:
				SeeDetailsActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			default:

			}
		}
	}
}
