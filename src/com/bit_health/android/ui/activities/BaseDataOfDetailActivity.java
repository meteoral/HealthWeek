package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.PpgInfoBean;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：BaseDataOfDetailActivity
 * 
 * 主要功能：详情报告中的"基本数据"
 * 
 * @author 梁才学 创建日期：2014.2.19
 **********************************************************************/
public class BaseDataOfDetailActivity extends BaseActivity {

	private LayoutInflater inflater;
	private View view;
	private LinearLayout mLinearLayout;
	private TextView pageTitleText;
	private ImageView itemStateImage;

	private EcgInfoBean mEcgInfoId;
	private PpgInfoBean mPpgInfoId;
	private String testTime;
	private int testValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.base_data_of_detail, null);
		setContentView(view);
		initView();

		Intent intent = getIntent();
		mEcgInfoId = (EcgInfoBean) intent.getExtras().getSerializable(
				"basedata_ecgInfo_id");// 通过序列化的方式，使用intent传递 EcgInfoBean 对象过来
		if (mEcgInfoId != null) {
			testTime = intent.getExtras().getString("test_time");
			testValue = intent.getExtras().getInt("test_value");
			ecgBaseData();
			pageTitleText.setText("心电基本数据");
		}
		mPpgInfoId = (PpgInfoBean) intent.getExtras().getSerializable(
				"ppg_basedata_id");
		if (mPpgInfoId != null) {
			testTime = intent.getExtras().getString("ppg_test_time");
			testValue = intent.getExtras().getInt("ppg_test_value");
			ppgBaseData();
			pageTitleText.setText("脉率基本数据");
		}
		mPpgInfoId = (PpgInfoBean) intent.getExtras().getSerializable(
				"ppg_heart_function_id");
		if (mPpgInfoId != null) {
			testValue = intent.getExtras().getInt("ppg_test_value");
			ppgHeartFunctionData();
			pageTitleText.setText("心功能参数");
		}
		mPpgInfoId = (PpgInfoBean) intent.getExtras().getSerializable(
				"ppg_blood_vessel_id");
		if (mPpgInfoId != null) {
			ppgBloodVesselData();
			pageTitleText.setText("外周血管参数");
		}

		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	private void initView() {
		// TODO Auto-generated method stub
		MyViewListener listener = new MyViewListener();
		view.findViewById(R.id.base_data_back_image).setOnClickListener(
				listener);
		mLinearLayout = (LinearLayout) view
				.findViewById(R.id.basedata_layout_id);
		pageTitleText = (TextView) view
				.findViewById(R.id.basedata_page_title_text);
	}

	/**********************************************************************
	 * 方法描述：心电的基本数据
	 *********************************************************************/
	private void ecgBaseData() {
		addItem(R.string.basedata_test_device,
				getDeviceName(mEcgInfoId.mECGDeviceCode));
		addItem(R.string.basedata_test_time, testTime);
		addItem(R.string.basedata_celiang_shichang,
				getTestTime(mEcgInfoId.mTimeLength));
		addItem(R.string.basedata_zongxin_boshu, mEcgInfoId.mEcgImgCount + "");
		addItem(R.string.basedata_zuixiao_xinlv, mEcgInfoId.mSlowestBeat + "");
		addItem(R.string.basedata_zuixiao_time,
				getTestTime(mEcgInfoId.mSlowestTime));
		addItem(R.string.basedata_zuida_xinlv, mEcgInfoId.mFastestBeat + "");
		addItem(R.string.basedata_zuida_time,
				getTestTime(mEcgInfoId.mFastestTime));
		addItemWithComparison(R.string.basedata_pingjun_xinlv, 55, 100,
				testValue);
	}

	/**********************************************************************
	 * 方法描述：计算总脉搏数: 总脉搏数 = 脉率*（时长/60）
	 *********************************************************************/
	private int calculatePpgCount() {
		return (int) testValue * (mPpgInfoId.mTimeLength / 60);
	}

	/**********************************************************************
	 * 方法描述：脉率的基本数据
	 *********************************************************************/
	private void ppgBaseData() {
		addItem(R.string.basedata_test_device,
				getDeviceName(mPpgInfoId.mDeviceCode));
		addItem(R.string.basedata_test_time, testTime);
		addItem(R.string.basedata_celiang_shichang,
				getTestTime(mPpgInfoId.mTimeLength));
		addItem(R.string.basedata_count_ppg, calculatePpgCount() + "");
		addItem(R.string.basedata_zuixiao_ppg, mPpgInfoId.mSlowPulse + "");
		addItem(R.string.basedata_zuixiao_ppg_time,
				getTestTime(mPpgInfoId.mSlowTime));
		addItem(R.string.basedata_zuida_ppg, mPpgInfoId.mQuickPulse + "");
		addItem(R.string.basedata_zuida_ppg_time,
				getTestTime(mPpgInfoId.mQuickTime));
		addItemWithComparison(R.string.basedata_pingjun_ppg, 55, 100, testValue);
		addItemWithComparison(R.string.basedata_xueyang_baohedu, 95, 100,
				mPpgInfoId.mSPO);
	}

	/**********************************************************************
	 * 方法描述：脉搏的心功能参数
	 *********************************************************************/
	private void ppgHeartFunctionData() {
		addItemWithComparison(R.string.function_ppg_PR, 55, 100, testValue);
		addItemWithComparison(R.string.function_ppg_CO, 3.5, 7, mPpgInfoId.mCO);
		addItemWithComparison(R.string.function_ppg_SV, 60, 100, mPpgInfoId.mSV);
		addItemWithComparison(R.string.function_ppg_SO, 95, 100,
				mPpgInfoId.mSPO);
		addItemWithComparison(R.string.function_ppg_CI, 2.5, 10000,
				mPpgInfoId.mCI);
		addItemWithComparison(R.string.function_ppg_SPI, 35, 10000,
				mPpgInfoId.mSPI);

		addItem(R.string.reference_standard,
				getResources().getString(R.string.reference_standard));
	}

	/**********************************************************************
	 * 方法描述：脉搏的外周血管参数
	 *********************************************************************/
	private void ppgBloodVesselData() {
		addItemWithComparison(R.string.BloodVessel_ppg_K, 0.3, 0.45,
				mPpgInfoId.mK);
		addItemWithComparison(R.string.BloodVessel_ppg_V, 3, 5, mPpgInfoId.mV);
		addItemWithComparison(R.string.BloodVessel_ppg_TPR, 800, 1400,
				mPpgInfoId.mTpr);
		addItemWithComparison(R.string.BloodVessel_ppg_AC, 1.2, 3,
				mPpgInfoId.mAC);
		addItemWithComparison(R.string.BloodVessel_ppg_PM, 70, 105,
				mPpgInfoId.mPm);
		addItem(R.string.BloodVessel_ppg_SI, mPpgInfoId.mSI + "");
		addItem(R.string.reference_standard,
				getResources().getString(R.string.reference_standard));
	}

	private View addItem(int resid, String value) {
		String dataFormat = getResources().getString(resid);
		String showData = String.format(dataFormat, value);
		View view = inflater.inflate(R.layout.item_base_data_layout, null);
		mLinearLayout.addView(view);
		itemStateImage = (ImageView) view.findViewById(R.id.item_state_image);

		((TextView) view.findViewById(R.id.item_title_name)).setText(Html
				.fromHtml(showData));

		return view;
	}

	public String getDeviceName(String device_name) {
		if (device_name != null) {
			if (device_name.equals("SIAT3IN1_E")) {
				device_name = "三合一";
			} else if (device_name.equals("SIATELECECG")) {
				device_name = "MiniHolter";
			}
		}

		return device_name;
	}

	/**********************************************************************
	 * 方法描述：将秒钟转换为几小时几分几秒，比如：70s转换为 1分10秒
	 * 
	 * @param second
	 *            : 所需要转换的秒数
	 **********************************************************************/
	public String getTestTime(int second) {
		int hourResult = second / 3600;
		int minResult = (second % 3600) / 60;
		int secondResult = second % 60;

		if (hourResult == 0) {
			if (minResult == 0) {
				return secondResult + "秒";
			} else if (secondResult == 0 || secondResult == 1
					|| secondResult == 2 || secondResult == 3
					|| secondResult == 4 || secondResult == 5
					|| secondResult == 6 || secondResult == 7
					|| secondResult == 8 || secondResult == 9) {
				return minResult + "分" + "0" + secondResult + "秒";
			} else {
				return minResult + "分" + secondResult + "秒";
			}
		} else {
			return hourResult + "小时" + minResult + "分" + secondResult + "秒";
		}
	}

	private View addItemWithComparison(int resid, double beginNum,
			double endNum, double valueNum) {
		String value = String.valueOf(valueNum);
		String dataFormat = getResources().getString(resid);
		String showData = String.format(dataFormat, value);
		View view = inflater.inflate(R.layout.item_base_data_layout, null);
		mLinearLayout.addView(view);
		itemStateImage = (ImageView) view.findViewById(R.id.item_state_image);

		if (valueNum < beginNum) {
			itemStateImage.setVisibility(View.VISIBLE);
			itemStateImage.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.arrow_down));
		} else if (valueNum > endNum) {
			itemStateImage.setVisibility(View.VISIBLE);
			itemStateImage.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.arrow_up));
		}

		((TextView) view.findViewById(R.id.item_title_name)).setText(Html
				.fromHtml(showData));

		return view;
	}

	// /***************************************************************
	// * 方法描述：设置部分字体的颜色
	// *
	// **************************************************************/
	// private void setTextColor(String termName, String instruction) {
	// termInstruction.setText(Html.fromHtml("<font color=\"blue\">"
	// + termName + ": " + "</font>" + instruction));
	// }

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.base_data_back_image:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			default:

			}

		}
	}
}
