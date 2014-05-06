package com.bit_health.android.ui.framelayout;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.ui.activities.BaseDataOfDetailActivity;
import com.bit_health.android.ui.activities.EcgChartActivity;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：ReportDetailOfMaibo
 * 
 * 主要功能：脉搏报告详情
 * 
 * @author 梁才学 创建日期：2014.2.11
 **********************************************************************/
public class ReportDetailOfMaibo extends BaseFramelayout {
	private Activity mActivity;

	private View baseDataOfPPG;// 基本数据
	private View heartFunction;// 心功能参数
	private View bloodVessel;// 外周血管参数
	private View pulseChart;// 脉搏图
	private View shunshiMailvChart;// 瞬时脉率图

	private ImageView stateImage;// 状态图
	private TextView showTimeText;
	private TextView showValueText;
	private String showTimeString;
	private int maiboValue;
	private int mFlagState;// 计算是否出现错误
	private PpgInfoBean mPpgInfo;
	private String mRoleId;

	public ReportDetailOfMaibo(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(PpgInfoBean ppgInfo, String roleid) {
		mPpgInfo = ppgInfo;
		mRoleId = roleid;
		initLayout();
		initData(ppgInfo);
		
	}

	private void initData(PpgInfoBean ppgInfo) {
		// TODO Auto-generated method stub

		maiboValue = ppgInfo.mPulserate;// 平均脉率的数值
		showTimeString = ppgInfo.mTimeStamp;
		showTimeString = turnTimeFormal(showTimeString);
		mFlagState = ppgInfo.mFlagError;// 1计算失败，0计算成功
		showTimeText.setText(TimeFormatUtil.ecgOrBppShowTime(showTimeString));
		showValueText.setText("" + maiboValue);
		if (mFlagState == 0) {
			if (!ppgInfo.bIsNormal) {
				stateImage.setImageDrawable(getResources().getDrawable(
						R.drawable.abnormal));
			} else {
				stateImage.setImageDrawable(getResources().getDrawable(
						R.drawable.normal));
			}
		} else if (mFlagState == 1) {
			stateImage.setImageDrawable(getResources().getDrawable(
					R.drawable.analysis_fail));
		}
	}

	/***************************************************************
	 * 方法描述：转换时间格式，服务器端的格式形如：20140122120153 转换为：2014-01-22 12:01:53
	 * 
	 **************************************************************/
	private String turnTimeFormal(String time) {
		// TODO Auto-generated method stub
		StringBuffer showTimeValue = new StringBuffer();
		showTimeValue.append(time.substring(0, 4)).append("-")
				.append(time.substring(4, 6)).append("-")
				.append(time.substring(6, 8)).append(" ")
				.append(time.substring(8, 10)).append(":")
				.append(time.substring(10, 12)).append(":")
				.append(time.substring(12, 14));
		return showTimeValue.toString();
	}

	/******************************************************************
	 * 方法描述：界面初始化
	 * 
	 ******************************************************************/
	private void initLayout() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.maibo_report_detail2, this);
		LinearLayout linearContainer = (LinearLayout) view
				.findViewById(R.id.maibo_container_id);
		baseDataOfPPG = addItem(inflater, linearContainer, R.string.base_data);
		heartFunction = addItem(inflater, linearContainer,
				R.string.heart_function);
		bloodVessel = addItem(inflater, linearContainer, R.string.blood_vessel);
		pulseChart = addItem(inflater, linearContainer, R.string.pulse_chart);
		shunshiMailvChart = addItem(inflater, linearContainer,
				R.string.shunshi_ppg_chart);

		stateImage = (ImageView) findViewById(R.id.show_maibo_state_image);
		showTimeText = (TextView) findViewById(R.id.maibo_detail_report_time);
		showValueText = (TextView) findViewById(R.id.show_maibo_value_text);
		
		MyViewListener listener = new MyViewListener();
		baseDataOfPPG.setOnClickListener(listener);
		heartFunction.setOnClickListener(listener);
		bloodVessel.setOnClickListener(listener);
		pulseChart.setOnClickListener(listener);
		shunshiMailvChart.setOnClickListener(listener);
		
		SharedPreferences preference = mActivity.getSharedPreferences(mActivity.getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp((ViewGroup)view, preference.getFloat("font_size_range", 0));
	}	

	/****************************************************************
	 * 方法描述：设置本应用的字体大小
	 ****************************************************************/
	public void setAllTextSizeOfApp(ViewGroup vg, float font_size) {
		SetTextSizeClass mSetTextSizeClass = new SetTextSizeClass(mActivity);
		mSetTextSizeClass.setFontSizeOfApp(vg, font_size);
	}

	private View addItem(LayoutInflater inflater, ViewGroup vgroup, int resid) {
		View view = inflater.inflate(R.layout.item_detail_relativelayout, null);
		vgroup.addView(view);
		((TextView) view.findViewById(R.id.item_text_show_id)).setText(resid);
		return view;
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {
			if (baseDataOfPPG == v) {
				Intent intent0 = new Intent(mActivity,
						BaseDataOfDetailActivity.class);
				intent0.putExtra("ppg_basedata_id", mPpgInfo);
				intent0.putExtra("ppg_test_time", showTimeString);
				intent0.putExtra("ppg_test_value", maiboValue);
				mActivity.startActivity(intent0);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
			} else if (heartFunction == v) {
				Intent intent1 = new Intent(mActivity,
						BaseDataOfDetailActivity.class);
				intent1.putExtra("ppg_heart_function_id", mPpgInfo);
				intent1.putExtra("ppg_test_value", maiboValue);
				mActivity.startActivity(intent1);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
			} else if (bloodVessel == v) {
				Intent intent2 = new Intent(mActivity,
						BaseDataOfDetailActivity.class);
				intent2.putExtra("ppg_blood_vessel_id", mPpgInfo);
				mActivity.startActivity(intent2);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
			} else if (pulseChart == v) {
				Intent intent3 = new Intent(mActivity, EcgChartActivity.class);
				intent3.putExtra(EcgChartActivity.INTENT_PPG_INFO, mPpgInfo);
				intent3.putExtra(EcgChartActivity.CURRENT_PAGE, 0);
				mActivity.startActivity(intent3);
			} else if (shunshiMailvChart == v) {
				Intent intent4 = new Intent(mActivity, EcgChartActivity.class);
				intent4.putExtra(EcgChartActivity.INTENT_PPG_INFO, mPpgInfo);
				intent4.putExtra(EcgChartActivity.CURRENT_PAGE, 1);
				mActivity.startActivity(intent4);
			} 
		}
	}
}
