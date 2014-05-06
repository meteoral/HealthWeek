package com.bit_health.android.ui.framelayout;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.ui.activities.BaseDataOfDetailActivity;
import com.bit_health.android.ui.activities.EcgChartActivity;
import com.bit_health.android.ui.activities.EcgRRActivity;
import com.bit_health.android.ui.activities.FoundAbnormalECGActivity;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：ReportDetailOfXinDian
 * 
 * 主要功能：心电报告详情
 * 
 * @author 梁才学 创建日期：2014.2.11
 **********************************************************************/
public class ReportDetailOfXinDian extends BaseFramelayout {
	private Activity mActivity;

	private View baseDataOfECG;
	private View foundAbnormalOfECG;
	private View layoutEcgChart;
	private View layoutAbnormalEcgChart;
	private View layoutShunshiEcgChart;
	private View mEcgAnlisysView;
	private View mEcgTongjiView;
	// private View mEcghundunView;

	private ImageView stateImage;// 状态图
	private TextView showTimeText;
	private TextView showValueText;
	private String showTimeString;
	private int xindianValue;
	private int mFlagState;// 计算是否出现错误
	private EcgInfoBean mEcgInfo;
	private String mRoleId;

	private int[] mPR;

	public ReportDetailOfXinDian(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(EcgInfoBean ecgInfo, String roleid) {
		mEcgInfo = ecgInfo;
		mRoleId = roleid;
		initLayout();
		initData(ecgInfo);
	}

	private void initData(EcgInfoBean ecgInfo) {
		// TODO Auto-generated method stub
		xindianValue = ecgInfo.mHeartRate;
		showTimeString = ecgInfo.mTimeStamp;
		showTimeString = turnTimeFormal(showTimeString);
		mFlagState = ecgInfo.mFlagError;// 1计算失败，0计算成功

		showTimeText.setText(TimeFormatUtil.ecgOrBppShowTime(showTimeString));
		showValueText.setText("" + xindianValue);

		if (mFlagState == 0) {
			if (!ecgInfo.bIsNormal) {
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

	private View addItem(LayoutInflater inflater, ViewGroup vgroup, int resid) {
		View view = inflater.inflate(R.layout.item_detail_relativelayout, null);
		vgroup.addView(view);
		((TextView) view.findViewById(R.id.item_text_show_id)).setText(resid);
		return view;
	}

	/******************************************************************
	 * 方法描述：界面初始化
	 * 
	 ******************************************************************/
	private void initLayout() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.xin_dian_report_detail2, this);

		LinearLayout linearContainer = (LinearLayout) view
				.findViewById(R.id.xin_dian_container_id);
		baseDataOfECG = addItem(inflater, linearContainer, R.string.base_data);
		foundAbnormalOfECG = addItem(inflater, linearContainer,
				R.string.found_abnormal);
		layoutEcgChart = addItem(inflater, linearContainer,
				R.string.xin_dian_chart);
		layoutAbnormalEcgChart = addItem(inflater, linearContainer,
				R.string.abnormal_xin_dian_chart);
		layoutShunshiEcgChart = addItem(inflater, linearContainer,
				R.string.shunshi_xinlv_chart);
		mEcgAnlisysView = addItem(inflater, linearContainer,
				R.string.xindian_fenxi);
		mEcgTongjiView = addItem(inflater, linearContainer,
				R.string.elh_statistic);

		// mEcghundunView = findViewById(R.id.layout_ecg_hundun);
		stateImage = (ImageView) findViewById(R.id.show_xindian_state_image);
		showTimeText = (TextView) findViewById(R.id.detail_report_time);
		showValueText = (TextView) findViewById(R.id.show_xindian_value_text);

		MyViewListener listener = new MyViewListener();
		baseDataOfECG.setOnClickListener(listener);
		foundAbnormalOfECG.setOnClickListener(listener);
		layoutEcgChart.setOnClickListener(listener);
		layoutAbnormalEcgChart.setOnClickListener(listener);
		layoutShunshiEcgChart.setOnClickListener(listener);

		mEcgAnlisysView.setOnClickListener(listener);
		mEcgTongjiView.setOnClickListener(listener);
		// mEcghundunView.setOnClickListener(listener);
		
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

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			if (baseDataOfECG == v) {
				Intent intent0 = new Intent(mActivity,
						BaseDataOfDetailActivity.class);
				intent0.putExtra("basedata_ecgInfo_id", mEcgInfo);
				intent0.putExtra("test_time", showTimeString);
				intent0.putExtra("test_value", xindianValue);
				mActivity.startActivity(intent0);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
			} else if (foundAbnormalOfECG == v) {
				Intent intent = new Intent(mActivity,
						FoundAbnormalECGActivity.class);
				intent.putExtra("detail_ecgInfo_id", mEcgInfo);
				mActivity.startActivity(intent);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
			} else if (layoutEcgChart == v) {
				Intent intent2 = new Intent(mActivity, EcgChartActivity.class);
				intent2.putExtra(EcgChartActivity.INTENT_ECG_INFO, mEcgInfo);
				intent2.putExtra(EcgChartActivity.CURRENT_PAGE, 0);
				mActivity.startActivity(intent2);
			} else if (layoutAbnormalEcgChart == v) {
				Intent intent3 = new Intent(mActivity, EcgChartActivity.class);
				intent3.putExtra(EcgChartActivity.INTENT_ECG_INFO, mEcgInfo);
				intent3.putExtra(EcgChartActivity.CURRENT_PAGE, 1);
				mActivity.startActivity(intent3);
			} else if (layoutShunshiEcgChart == v) {
				Intent intent4 = new Intent(mActivity, EcgChartActivity.class);
				intent4.putExtra(EcgChartActivity.INTENT_ECG_INFO, mEcgInfo);
				intent4.putExtra(EcgChartActivity.CURRENT_PAGE, 2);
				mActivity.startActivity(intent4);
			} else if (mEcgAnlisysView == v) {
				showResult();
			} else if (mEcgTongjiView == v) {
				if (mPR != null) {
					xinLvTongJi();
				} else {
					InterfaceService.getInstance(mActivity).getEcgAnlyseResult(
							mEcgInfo.mEventId, mRoleId,
							ReportDetailOfXinDian.this);
					showWaittingDialog("获取心率数据");
				}
			}
		}
	}

	private float get4dot(float data){
		float data1 = ((float)((int)(data*10000)))/10000;
		float data2 = ((float)((int)(data*100000)))/100000;
		if(data2 - data1 >= 0.00005){
			data1 = (float) (data1 +0.0001);
		}
		return data1;
	}
	private void showResult() {
		if (mEcgInfo.mHeartNum <= 20) {
			Log.d("ecgHistoryActi", "RR_sum=" + mEcgInfo.mHeartNum);
			// Toast.makeText(this, R.string.toast_message_illegal_data,
			// Toast.LENGTH_SHORT).show();
			return;
		}

//		byte standstill = (byte) (mEcgInfo.mArrestNum * 100 / mEcgInfo.mHeartNum);
//		byte boleakage = (byte) (mEcgInfo.mMissedNum * 100 / mEcgInfo.mHeartNum);
//		byte kuanbo = (byte) (mEcgInfo.mWideNum * 100 / mEcgInfo.mHeartNum);
//		byte pvc = (byte) (mEcgInfo.mPVBNum * 100 / mEcgInfo.mHeartNum);
//		byte apb = (byte) (mEcgInfo.mApbNum * 100 / mEcgInfo.mHeartNum);
//		byte iovp = (byte) (mEcgInfo.mInsertPVBnum * 100 / mEcgInfo.mHeartNum);
//		byte rr2 = (byte) (mEcgInfo.mBigeminyNum * 100 / mEcgInfo.mHeartNum);
//		byte rr3 = (byte) (mEcgInfo.mTrigeminyNum * 100 / mEcgInfo.mHeartNum);
//		byte ss = (byte) (mEcgInfo.mVT * 100 / mEcgInfo.mHeartNum);
//		byte normal = (byte) (100 - standstill - boleakage - kuanbo - pvc - apb
//				- iovp - rr2 - rr3 - ss);
		float standstill = get4dot(((float)mEcgInfo.mArrestNum  / mEcgInfo.mHeartNum)*100);
		float boleakage =  get4dot(((float)mEcgInfo.mMissedNum  / mEcgInfo.mHeartNum)*100);
		float kuanbo = get4dot(((float)mEcgInfo.mWideNum  / mEcgInfo.mHeartNum)*100);
		float pvc = get4dot(((float)mEcgInfo.mPVBNum  / mEcgInfo.mHeartNum)*100);
		float apb = get4dot(((float)mEcgInfo.mApbNum  / mEcgInfo.mHeartNum)*100);
		float iovp = get4dot(((float)mEcgInfo.mInsertPVBnum  / mEcgInfo.mHeartNum)*100);
		float rr2 = get4dot(((float)mEcgInfo.mBigeminyNum  / mEcgInfo.mHeartNum)*100);
		float rr3 = get4dot(((float)mEcgInfo.mTrigeminyNum  / mEcgInfo.mHeartNum)*100);
		float ss = get4dot(((float)mEcgInfo.mVT  / mEcgInfo.mHeartNum)*100);
		float normal = 100 - standstill - boleakage - kuanbo - pvc - apb
				- iovp - rr2 - rr3 - ss;

		LayoutInflater factory = LayoutInflater.from(mActivity);
		final View resultView = factory.inflate(R.layout.diagnosis, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle(mActivity.getString(R.string.EHA_result)); // 分析结果
		builder.setView(resultView).setPositiveButton(
//				mActivity.getString(R.string.common_OK),
				mActivity.getText(R.string.common_OK),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
					}
				});

		builder.setView(View.inflate(mActivity, R.layout.diagnosis,
				(ViewGroup) mActivity.getCurrentFocus()));
		AlertDialog alert = builder.create();
		alert.setCanceledOnTouchOutside(false);
		alert.show();

		TextView tv = new TextView(mActivity);
		if (normal != 0) {
			tv = (TextView) alert
					.findViewById(R.id.diagnosis_result_item_normal);
			tv.setText(normal + "%");
		}

		if (rr2 != 0) {
			tv = (TextView) alert
					.findViewById(R.id.diagnosis_result_item_erlian);
			tv.setText(rr2 + "%");
		}

		if (rr3 != 0) {
			tv = (TextView) alert
					.findViewById(R.id.diagnosis_result_item_sanlian);
			tv.setText(rr3 + "%");
		}

		if (standstill != 0) {
			tv = (TextView) alert.findViewById(R.id.diagnosis_result_item_sa);
			tv.setText(standstill + "%");
		}

		if (boleakage != 0) {
			tv = (TextView) alert.findViewById(R.id.diagnosis_result_item_mb);
			tv.setText(boleakage + "%");
		}

		if (kuanbo != 0) {
			tv = (TextView) alert.findViewById(R.id.diagnosis_result_item_ws);
			tv.setText(kuanbo + "%");
		}

		if (pvc != 0) {
			tv = (TextView) alert.findViewById(R.id.diagnosis_result_item_vpb);
			tv.setText(pvc + "%");
		}

		if (apb != 0) {
			tv = (TextView) alert.findViewById(R.id.diagnosis_result_item_apb);
			tv.setText(apb + "%");
		}

		if (iovp != 0) {
			tv = (TextView) alert.findViewById(R.id.diagnosis_result_item_ivbp);
			tv.setText(iovp + "%");
		}

		if (ss != 0) {
			tv = (TextView) alert
					.findViewById(R.id.diagnosis_result_item_shisu);
			tv.setText(ss + "%");
		}
	}

	@Override
	public void getEcgPRCallback(int retCode, String errorMsg, int[] pr) {
		// TODO Auto-generated method stub
		super.getEcgPRCallback(retCode, errorMsg, pr);
		hideWaittingDialog();
		switch (retCode) {
		case 0:
			mPR = pr;
			xinLvTongJi();
			break;

		default:
			showAlert(errorMsg);
			break;
		}
	}

	// 心率统计
	private void xinLvTongJi() {
		// ECGSampleRate).RR;
		if (mPR.length > 10) {
			Intent it = new Intent(mActivity, EcgRRActivity.class);
			Bundle bundle = new Bundle();
			bundle.putIntArray("rrList", mPR);
			bundle.putDouble("PNN50", mEcgInfo.mPnn50);
			bundle.putDouble("SDNN", mEcgInfo.mSdnn);
			bundle.putDouble("HRVI", mEcgInfo.mHrvi);
			it.putExtras(bundle);
			mActivity.startActivity(it);
		}
	}

	// private void hunDunFenXi() {
	// Intent it = new Intent(mActivity,
	// EcgChaosActivity.class);
	// Bundle bundle = new Bundle();
	// bundle.putIntArray("ECG", ecgData);
	// bundle.putInt("NUM", ecgLength);
	// it.putExtras(bundle);
	// mActivity.startActivity(it);
	// }

}
