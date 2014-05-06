package com.bit_health.android.ui.framelayout;

import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AnalyzeCath;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.OverScoreBean;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.activities.AbnormalReportActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.BasicInfoOfFamilyActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.activities.SeeDetailsActivity;
import com.bit_health.android.util.HeadImageUtil;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：HomePageContentAdditional
 * 
 * 主要功能：主页模块中的添加页内容
 * 
 * @author 梁才学 创建日期：2013.12.31
 **********************************************************************/
public class HomePageContentAdditional extends FrameLayout {
	public static final int From_HomePageContentAdditional = 1;

	private Activity mActivity;
	private LayoutInflater inflater;

	private boolean mSelectMode;
	// 角色信息
	private ImageView headImage;
	private TextView fullName;
	private TextView userName;
	private ImageView bottomDot;// 底部圆点
	private View homePageBackImage;// 返回侧滑菜单图标
	private String mRoleId;
	private AndroidActivityMananger mMananger;

	private TextView yesTextView;
	private TextView oneMonthTextView;
	private TextView oneYearTextView;
	private TextView yesTextTitle;
	private TextView oneMonthTextTitle;
	private TextView oneYearTextTitle;
	private TextView sheReportTextTitle;

	private TextView detailBtn;
	private TextView healthStateHead;
	private TextView healthStateText;
	private TextView analyzeTimeText;
	private TextView tempText;
	private boolean isFoundHealthData = false;// 是否有健康状况的数据

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (RoleInfoBean.ACTION_ROLEINFO_CHANGED.equals(intent.getAction())) {
				setRoleInfo(mRoleId);
			}
		}
	};

	public HomePageContentAdditional(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mMananger = AndroidActivityMananger.getInstatnce();
	}

	public void homePageAdditionalCreate(String roleId) {
		mRoleId = roleId;
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.content_module_page_additional, this);
		initView();
		setRoleInfo(roleId);
		setPageTextSize();
	}

	/****************************************************************
	 * 方法描述：通过 roleId 设置角色的信息
	 * 
	 *****************************************************************/
	private void setRoleInfo(String roleId) {
		// TODO Auto-generated method stub

		// String roleId = AndroidConfiguration.getInstance(
		// mActivity.getApplicationContext()).getRoleId();
		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(mActivity);
		RoleInfoBean mRoleInfoBean = mRoleCatchInfo.getRole(roleId);
		if (mRoleInfoBean != null) {
			fullName.setText(mRoleInfoBean.mFullName);
			userName.setText(mRoleInfoBean.mName);
			Bitmap bmp = null;
			if ((bmp = HeadImageUtil.getHeadCatchImage(mRoleInfoBean.mId)) != null) {
				headImage.setImageBitmap(bmp);
			} else {
				headImage.setImageResource(HeadImageUtil
						.getLocalHeadimage(mRoleInfoBean.mFullName));
			}

			yesTextView.setText(Integer.toString(mRoleInfoBean.mYesAbNumber));
			oneMonthTextView.setText(Integer
					.toString(mRoleInfoBean.mMonthAbNumber));
			oneYearTextView.setText(Integer
					.toString(mRoleInfoBean.m3MonthAbNumber));

		}
		List<OverScoreBean> mListOverScores = AnalyzeCath
				.getInstance(mActivity).mListOverScores;
		if (mListOverScores != null) {
			for (OverScoreBean overBean : mListOverScores) {
				if (roleId.equals(overBean.mUserId)) {
					isFoundHealthData = true;
					setDetailBtnText(overBean.mScore, overBean.mUptateTm);
					break;
				}
			}

			// 如果该角色没有健康状况的数据
			if (!isFoundHealthData) {
				setTextNoHealthData();
			}
		}
	}

	private void setTextNoHealthData() {
		// TODO Auto-generated method stub
		healthStateText.setText("数据不足，无法分析！");
		detailBtn.setVisibility(View.GONE);
		analyzeTimeText.setVisibility(View.GONE);
		tempText.setVisibility(View.GONE);
	}

	private void setDetailBtnText(int score, String analyzeTime) {
		// TODO Auto-generated method stub

		if (score != -1) {

			if (score >= 90) {
				healthStateText.setText("优秀");
			} else if (score >= 75) {
				healthStateText.setText("良好");
			} else if (score >= 60) {
				healthStateText.setText("健康");
			} else if (score >= 40 && score < 60) {
				healthStateText.setText("亚健康");
			} else if (score >= 0 && score < 40) {
				healthStateText.setText("糟糕");
			}

			detailBtn.setText(mActivity.getResources().getText(
					R.string.check_detail));
			analyzeTimeText.setVisibility(View.VISIBLE);
			tempText.setVisibility(View.VISIBLE);
			analyzeTimeText
					.setText(TimeFormatUtil.turnSimpleTime(analyzeTime));
		} else {
			setTextNoHealthData();
		}
	}

	/****************************************************************
	 * 方法描述：初始化数据
	 * 
	 *****************************************************************/
	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.home_page_bottom_dot_image, null);
		bottomDot = (ImageView) view.findViewById(R.id.dot_image);
		bottomDot.setEnabled(false);

		homePageBackImage = findViewById(R.id.additional_page_back_icon);
		headImage = (ImageView) findViewById(R.id.home_page_head_photo_of_additional);
		fullName = (TextView) findViewById(R.id.home_page_full_name_of_additional);
		userName = (TextView) findViewById(R.id.home_page_user_name_of_additional);

		yesTextView = (TextView) findViewById(R.id.left_up_count_text_of_additional);
		oneMonthTextView = (TextView) findViewById(R.id.right_up_count_text_of_additional);
		oneYearTextView = (TextView) findViewById(R.id.left_down_count_text_of_additional);
		yesTextTitle = (TextView) findViewById(R.id.yesterday_abnormal_text_of_additional);
		oneMonthTextTitle = (TextView) findViewById(R.id.month_abnormal_text_of_additional);
		oneYearTextTitle = (TextView) findViewById(R.id.year_abnormal_text_of_additional);
		sheReportTextTitle = (TextView) findViewById(R.id.right_down_text);

		healthStateText = (TextView) findViewById(R.id.page_additional_health_state_result);
		analyzeTimeText = (TextView) findViewById(R.id.page_additional_analyze_time);
		tempText = (TextView) findViewById(R.id.page_additional_analyze_time_text);
		healthStateHead = (TextView) findViewById(R.id.page_additional_health_state);
		detailBtn = (TextView) findViewById(R.id.page_additional_detail_btn);

		MyViewListener listener = new MyViewListener();
		homePageBackImage.setOnClickListener(listener);
		detailBtn.setOnClickListener(listener);
		headImage.setOnTouchListener(onTouchListener);

		findViewById(R.id.layout_left_up_of_additional).setOnClickListener(
				listener);
		findViewById(R.id.layout_right_up_of_additional).setOnClickListener(
				listener);
		findViewById(R.id.layout_left_down_of_additional).setOnClickListener(
				listener);
		findViewById(R.id.layout_right_down_of_additional).setOnClickListener(
				listener);

	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		IntentFilter filter = new IntentFilter();
		filter.addAction(RoleInfoBean.ACTION_ROLEINFO_CHANGED);
		mActivity.registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		if (mReceiver != null) {
			mActivity.unregisterReceiver(mReceiver);
			mReceiver = null;
		}
		super.onDetachedFromWindow();
	}

	/****************************************************************
	 * 方法描述：当进入本界面时，处理一些数据
	 * 
	 *****************************************************************/
	public void pageIn() {
		exitSelectMode();
	}

	/****************************************************************
	 * 方法描述：当离开本界面时，处理一些数据
	 * 
	 *****************************************************************/
	public void pageOut() {

	}

	/****************************************************************
	 * 方法描述：当退出本界面时，处理一些数据
	 * 
	 *****************************************************************/
	private void exitSelectMode() {
		if (mSelectMode) {
			// mSelectPanel.setVisibility(View.GONE);
			// mDrawingListAdapter.setCheckShow(false);
			// mGridViewItems.invalidateViews();
			mSelectMode = false;
		}
	}

	public ImageView getBottomDot() {

		return bottomDot;
	}

	public void setBottomDot(ImageView bottomDot) {
		this.bottomDot = bottomDot;
	}

	/****************************************************************
	 * 方法描述：设置字体大小
	 * 
	 *****************************************************************/
	private void setPageTextSize() {

		SharedPreferences preference = mActivity.getSharedPreferences(
				mActivity.getPackageName(), Context.MODE_PRIVATE);
		int currentIndex = preference.getInt("currentIndex", 0);

		if (currentIndex == 2) {
			setTextViewFontSize(R.dimen.set_font_size_31px);
		} else if (currentIndex == 1) {
			setTextViewFontSize(R.dimen.set_font_size_28px);
		} else if (currentIndex == 0) {
			setTextViewFontSize(R.dimen.set_font_size_25px);
		}
	}

	private void setTextViewFontSize(int source) {
		fullName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		userName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		yesTextTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		oneMonthTextTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		oneYearTextTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		sheReportTextTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		healthStateText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		analyzeTimeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		tempText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		healthStateHead.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		detailBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.additional_page_back_icon:
				((FourModuleManangerActivity) mActivity).getSlidingMenu()
						.toggle();
				break;
			case R.id.layout_left_up_of_additional: {
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreDaysTime(1));
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, mRoleId);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ALL_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.yesterday_abnormal));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
			}

				break;
			case R.id.layout_right_up_of_additional: {
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreMonthTime(1));
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, mRoleId);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ALL_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.month_abnormal));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
			}

				break;
			case R.id.layout_left_down_of_additional: {
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreMonthTime(3));
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, mRoleId);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ALL_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.year_abnormal));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
			}
				break;

			case R.id.layout_right_down_of_additional: {
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, mRoleId);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ALL_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.AF_ANLASY_SUCCESS);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.his_reports));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
			}
				break;

			case R.id.page_additional_detail_btn: {

				Intent seeDetailsIntent = new Intent();
				seeDetailsIntent.setClass(mActivity.getApplicationContext(),
						SeeDetailsActivity.class);
				seeDetailsIntent.putExtra(HomePageContentOne.CURRENT_ROLE_ID,
						mRoleId);
				mActivity.startActivity(seeDetailsIntent);

			}
				break;

			default:

			}

		}
	}
	
	private OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				changeLight(view, -50);
				break;

			case MotionEvent.ACTION_MOVE:
				break;

			case MotionEvent.ACTION_UP:
				changeLight(view, 0);

				Intent intent = new Intent();
				intent.putExtra(BasicInfoOfFamilyActivity.FLAG_INTENT_ROLE_ID,
						mRoleId);
				intent.setClass(mActivity.getApplicationContext(),
						BasicInfoOfFamilyActivity.class);
				mActivity.startActivityForResult(intent,
						From_HomePageContentAdditional);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				
				break;

			default:
				break;
			}
			return true;
		}
	};

	// 点击图片时，设置点击变暗的效果
	private void changeLight(View imageView, int brightness) {

		if (imageView instanceof ImageView) {
			ColorMatrix cMatrix = new ColorMatrix();
			cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
					brightness,// 改变亮度
					0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
			((ImageView) imageView).setColorFilter(new ColorMatrixColorFilter(
					cMatrix));
		}
	}
}
