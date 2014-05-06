package com.bit_health.android.ui.framelayout;

import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AnalyzeCath;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.OverScoreBean;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.activities.AbnormalReportActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.BasicInfoOfFamilyActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.activities.MenuChildPageActivity;
import com.bit_health.android.ui.activities.OneKeyTestActivity;
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
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：HomePageContentOne
 * 
 * 主要功能：主页模块中的第一页内容
 * 
 * @author 梁才学 创建日期：2013.12.31
 **********************************************************************/
public class HomePageContentOne extends FrameLayout {

	public static final int GO_TO_BasicInfoOfFamilyActivity = 1;
	public static final String GO_TO_OneKeyTestActivity = "fromPath";
	public static final String TEST_RIGHT_NOW = "testRightNow";
	public static final String CURRENT_ROLE_ID = "current_roleId";
	public static boolean isConnected = false;

	private Activity mActivity;
	private AndroidActivityMananger mMananger;
	private LayoutInflater inflater;
	private ImageView homePageBackImage;// 返回菜单页的图标
	private View layoutOneKey;
	private View layoutRoleTitleLable;// 页面顶部角色头像的布局
	private boolean mSelectMode;
	private Button netWorkBtn;// 检查网络连接情况按钮
	private TextView detailBtn;// “查看详情”或“立即测试”
	private TextView healthStateText;
	private TextView analyzeTimeText;
	private TextView healthStateHead;
	private TextView tempText;
	private boolean isFoundHealthData = false;// 是否有健康状况的数据

	private String mRoleId;// 当前角色Id
	// 角色信息
	private ImageView headImage;
	private TextView fullName;
	private TextView userName;
	private TextView yesTextView;
	private TextView oneMonthTextView;
	private TextView oneYearTextView;
	private TextView yesTextTitle;
	private TextView oneMonthTextTitle;
	private TextView oneYearTextTitle;
	private TextView oneKeyText;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (RoleInfoBean.ACTION_ROLEINFO_CHANGED.equals(intent.getAction())) {
				setRoleInfo();
			}
		}
	};
	private SpannableString spanableInfo;
	private int start;
	private int end;

	public HomePageContentOne(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mMananger = AndroidActivityMananger.getInstatnce();
	}

	public void homePageOneCreate() {
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.content_module_page_1, this);
		initLayout();
		setRoleInfo();
		setPageTextSize();
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
	 * 方法描述：设置角色的信息
	 * 
	 *****************************************************************/
	private void setRoleInfo() {
		// TODO Auto-generated method stub

		mRoleId = AndroidConfiguration.getInstance(
				mActivity.getApplicationContext()).getRoleId();
		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(mActivity);
		RoleInfoBean mRoleInfoBean = mRoleCatchInfo.getRole(mRoleId);
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
				if (mRoleId.equals(overBean.mUserId)) {
					isFoundHealthData = true;
					setTextHealthData(overBean.mScore, overBean.mUptateTm);
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

		analyzeTimeText.setVisibility(View.GONE);
		tempText.setVisibility(View.GONE);
		detailBtn.setVisibility(View.GONE);

		healthStateText.setText(getClickableSpan());
		healthStateText.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private SpannableString getClickableSpan() {

		View.OnTouchListener listener = new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					spanableInfo
							.setSpan(
									new ForegroundColorSpan(Color
											.parseColor("#FF6D26")), start,
									end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
					healthStateText.setText(spanableInfo);

					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					spanableInfo
							.setSpan(
									new ForegroundColorSpan(Color
											.parseColor("#FFFFFF")), start,
									end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
					healthStateText.setText(spanableInfo);

					Intent intentDetailBtn = new Intent();
					intentDetailBtn.setClass(mActivity.getApplicationContext(),
							OneKeyTestActivity.class);
					intentDetailBtn.putExtra(GO_TO_OneKeyTestActivity,
							TEST_RIGHT_NOW);
					mActivity.startActivity(intentDetailBtn);

					break;

				case MotionEvent.ACTION_CANCEL:
					spanableInfo
							.setSpan(
									new ForegroundColorSpan(Color
											.parseColor("#FFFFFF")), start,
									end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
					healthStateText.setText(spanableInfo);

					break;
				}

				return false;
			}
		};

		spanableInfo = new SpannableString("数据不足，请立即测试！");
		start = 5;
		// int end = spanableInfo.length();
		end = 11;
		spanableInfo.setSpan(new Clickable(listener), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanableInfo.setSpan(new ForegroundColorSpan(Color.WHITE), start, end,
				Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

		return spanableInfo;
	}

	class Clickable extends ClickableSpan implements OnTouchListener {
		private final View.OnTouchListener mListener;

		public Clickable(View.OnTouchListener l) {
			mListener = l;
		}

		@Override
		public void onClick(View v) {
			v.setOnTouchListener(mListener);
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			mListener.onTouch(v, event);
			return false;
		}
	}

	// 设置健康状况的文字显示
	private void setTextHealthData(int score, String analyzeTime) {
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
			detailBtn.setVisibility(View.VISIBLE);
			analyzeTimeText.setVisibility(View.VISIBLE);
			tempText.setVisibility(View.VISIBLE);
			analyzeTimeText.setText(TimeFormatUtil.turnSimpleTime(analyzeTime));
		} else {
			setTextNoHealthData();
		}
	}

	/****************************************************************
	 * 方法描述：初始化数据
	 * 
	 *****************************************************************/
	private void initLayout() {
		// TODO Auto-generated method stub
		homePageBackImage = (ImageView) findViewById(R.id.home_page_back_icon);
		layoutOneKey = findViewById(R.id.layout_center_one_key);
		layoutRoleTitleLable = findViewById(R.id.layout_role_title_lable);
		headImage = (ImageView) findViewById(R.id.home_page_head_photo);
		fullName = (TextView) findViewById(R.id.home_page_full_name);
		userName = (TextView) findViewById(R.id.home_page_user_name);
		yesTextView = (TextView) findViewById(R.id.left_up_count_text);
		oneMonthTextView = (TextView) findViewById(R.id.right_up_count_text);
		oneYearTextView = (TextView) findViewById(R.id.left_down_count_text);
		yesTextTitle = (TextView) findViewById(R.id.yesterday_abnormal_text);
		oneMonthTextTitle = (TextView) findViewById(R.id.month_abnormal_text);
		oneYearTextTitle = (TextView) findViewById(R.id.year_abnormal_text);
		oneKeyText = (TextView) findViewById(R.id.one_key_text);
		healthStateText = (TextView) findViewById(R.id.page_1_health_state_result);
		analyzeTimeText = (TextView) findViewById(R.id.page_1_analyze_time);
		tempText = (TextView) findViewById(R.id.page_1_analyze_time_text);
		healthStateHead = (TextView) findViewById(R.id.page_1_health_state);
		detailBtn = (TextView) findViewById(R.id.page_1_detail_btn);
		netWorkBtn = (Button) findViewById(R.id.btn_network);
		if (isConnected) {
			netWorkBtn.setVisibility(View.GONE);
		} else {
			netWorkBtn.setVisibility(View.VISIBLE);
		}

		MyViewListener listener = new MyViewListener();
		homePageBackImage.setOnClickListener(listener);
		layoutOneKey.setOnClickListener(listener);
		layoutRoleTitleLable.setOnClickListener(listener);
		netWorkBtn.setOnClickListener(listener);
		detailBtn.setOnClickListener(listener);

		findViewById(R.id.layout_left_up).setOnClickListener(listener);
		findViewById(R.id.layout_right_up).setOnClickListener(listener);
		findViewById(R.id.layout_left_down).setOnClickListener(listener);
		findViewById(R.id.layout_right_down).setOnClickListener(listener);
	}

	/****************************************************************
	 * 方法描述：刷新本界面
	 * 
	 *****************************************************************/
	public void refresh() {
		setRoleInfo();
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

	/****************************************************************
	 * 方法描述：设置字体大小
	 * 
	 *****************************************************************/
	public void setPageTextSize() {

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
		oneKeyText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		detailBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		healthStateText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		analyzeTimeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		healthStateHead.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
		tempText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity
				.getResources().getDimensionPixelSize(source));
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * @author 梁才学 主要功能：界面上的各个控件的监听 创建日期：2014.1.2
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.home_page_back_icon:
				((FourModuleManangerActivity) mActivity).getSlidingMenu()
						.toggle();
				break;

			case R.id.layout_center_one_key:
				mMananger.switchActivityNoClose("FourModuleManangerActivity",
						OneKeyTestActivity.class);

				break;

			case R.id.layout_role_title_lable:

				Intent intent = new Intent();
				intent.setClass(mActivity.getApplicationContext(),
						BasicInfoOfFamilyActivity.class);
				mActivity.startActivityForResult(intent,
						GO_TO_BasicInfoOfFamilyActivity);

				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;
			case R.id.layout_left_up: {
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreDaysTime(1));
				String roleId = AndroidConfiguration.getInstance(
						mActivity.getApplicationContext()).getRoleId();
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, roleId);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ALL_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.yesterday_abnormal));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
			}

				break;
			case R.id.layout_right_up: {
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreMonthTime(1));
				String roleId = AndroidConfiguration.getInstance(
						mActivity.getApplicationContext()).getRoleId();
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, roleId);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ALL_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.month_abnormal));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
			}

				break;
			case R.id.layout_left_down: {
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreMonthTime(3));
				String roleId = AndroidConfiguration.getInstance(
						mActivity.getApplicationContext()).getRoleId();
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, roleId);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ALL_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.year_abnormal));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
			}
				break;

			case R.id.layout_right_down: {
				Intent intentRightDown = new Intent(
						mActivity.getApplicationContext(),
						MenuChildPageActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("menu_sliding_fragment_index",
						MenuChildPageActivity.TIPS);
				intentRightDown.putExtras(bundle);
				mActivity.startActivity(intentRightDown);
			}
				break;

			case R.id.btn_network: {
				// 打开设置网络界面
				if (android.os.Build.VERSION.SDK_INT > 10) {
					// 判断手机系统的版本 即API大于10 就是3.0或以上版本
					mActivity.startActivity(new Intent(
							android.provider.Settings.ACTION_SETTINGS));
				} else {
					mActivity
							.startActivity(new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}
			}
				break;

			case R.id.page_1_detail_btn: {

				Intent seeDetailsIntent = new Intent();
				seeDetailsIntent.setClass(mActivity.getApplicationContext(),
						SeeDetailsActivity.class);
				seeDetailsIntent.putExtra(CURRENT_ROLE_ID, mRoleId);
				mActivity.startActivity(seeDetailsIntent);
			}

			default:

			}

		}
	}

	public void connect(boolean isConnect) {
		if (!isConnect) {
			isConnected = false;
			// 网络没有连接时显示连接的按钮
			mActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (netWorkBtn.getVisibility() == View.GONE) {
						netWorkBtn.setVisibility(View.VISIBLE);
					}
				}
			});
		} else {
			isConnected = true;
			mActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (netWorkBtn.getVisibility() == View.VISIBLE) {
						netWorkBtn.setVisibility(View.GONE);
					}
				}
			});
		}
	}
}
