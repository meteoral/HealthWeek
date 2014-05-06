package com.bit_health.android.ui.framelayout;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：ReportDetailOfXinDian
 * 
 * 主要功能：血压报告详情
 * 
 * @author 梁才学 创建日期：2014.2.11
 **********************************************************************/
public class ReportDetailOfXueya extends FrameLayout {

	private Activity mActivity;

	private View layoutSuggestion;// 小E建议布局
	private TextView suggestionContentText; // 小E建议布局的内容
	private TextView showTimeText;// 时间
	private TextView sleepSpbText;// 睡觉前收缩压
	private TextView sleepDpbText;// 睡觉前舒张压
	private TextView sleepPRText;// 睡觉前脉率
	private TextView wakeSpbText;// 起床后收缩压
	private TextView wakeDpbText;// 起床后舒张压
	private TextView wakePRText;// 起床后脉率
	private TextView bpDetailDtateValue;// 血压状态的结果值

	// private TextView sleepSpbTextState;// 睡觉前收缩压状态
	// private TextView sleepDpbTextState;// 睡觉前舒张压状态
	// private TextView sleepPRTextState;// 睡觉前脉率状态
	// private TextView wakeSpbTextState;// 起床后收缩压状态
	// private TextView wakeDpbTextState;// 起床后舒张压状态
	// private TextView wakePRTextState;// 起床后脉率状态

	private ImageView suggestionPointerImage;// 小E建议布局中的箭头图片
	private ImageView bpStateImage;// 状态结果
	private int lableOfSuggestionDetail = 0;// 小E建议的标签

	public ReportDetailOfXueya(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(BpInfoBean bpInfo) {
		initLayout();
		initData(bpInfo);
	}

	private void initData(BpInfoBean bpInfo) {
		// TODO Auto-generated method stub
		showTimeText.setText(TimeFormatUtil.bsOrBpShowTime(bpInfo.mTimeStamp));
		sleepSpbText.setText(bpInfo.mSleepSpb + "");
		sleepDpbText.setText(bpInfo.mSleepDpb + "");
		sleepPRText.setText(bpInfo.mSleepPR + "");
		wakeSpbText.setText(bpInfo.mWakeSpb + "");
		wakeDpbText.setText(bpInfo.mWakeDpb + "");
		wakePRText.setText(bpInfo.mWakePR + "");

		// sleepSpbTextState.setText(bpInfo.mAbnormal + "");
		// sleepDpbTextState.setText(bpInfo.mAbnormal + "");
		// sleepPRTextState.setText(bpInfo.mAbnormal + "");
		// wakeSpbTextState.setText(bpInfo.mAbnormal + "");
		// wakeDpbTextState.setText(bpInfo.mAbnormal + "");
		// wakePRTextState.setText(bpInfo.mAbnormal + "");

		if (bpInfo.bIsNormal) {
			bpStateImage.setImageDrawable(getResources().getDrawable(
					R.drawable.normal));
			bpDetailDtateValue.setTextColor(Color.BLACK);
		} else {
			bpStateImage.setImageDrawable(getResources().getDrawable(
					R.drawable.abnormal));
			bpDetailDtateValue.setTextColor(Color.parseColor("#EC008C"));
		}
		bpDetailDtateValue.setText(bpInfo.mAbnormal);
	}

	/******************************************************************
	 * 方法描述：界面初始化
	 * 
	 ******************************************************************/
	private void initLayout() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.report_detail_of_xueya2, this);

		bpStateImage = (ImageView) findViewById(R.id.bp_detail_state_image);
		layoutSuggestion = findViewById(R.id.layout_suggestion_of_xueya_detail);
		bpDetailDtateValue = (TextView) findViewById(R.id.bp_detail_state_value);
		showTimeText = (TextView) findViewById(R.id.detail_report_time_of_xueya);
		sleepSpbText = (TextView) findViewById(R.id.shui_qian_shousuoya_value_text);
		sleepDpbText = (TextView) findViewById(R.id.shui_qian_shuzhangya_value_text);
		sleepPRText = (TextView) findViewById(R.id.shui_qian_mailv_value_text);
		wakeSpbText = (TextView) findViewById(R.id.qi_hou_shousuoya_value_text);
		wakeDpbText = (TextView) findViewById(R.id.qi_hou_shuzhangya_value_text);
		wakePRText = (TextView) findViewById(R.id.qi_hou_mailv_value_text);

		// sleepSpbTextState = (TextView)
		// findViewById(R.id.shui_qian_shousuoya_state_text);
		// sleepDpbTextState = (TextView)
		// findViewById(R.id.shui_qian_shuzhangya_state_text);
		// sleepPRTextState = (TextView)
		// findViewById(R.id.shui_qian_mailv_state_text);
		// wakeSpbTextState = (TextView)
		// findViewById(R.id.qi_hou_shousuoya_state_text);
		// wakeDpbTextState = (TextView)
		// findViewById(R.id.qi_hou_shuzhangya_state_text);
		// wakePRTextState = (TextView)
		// findViewById(R.id.qi_hou_mailv_state_text);

		suggestionContentText = (TextView) findViewById(R.id.suggestion_content_of_xueya_detail);
		suggestionPointerImage = (ImageView) findViewById(R.id.suggestion_pointer_image_of_xueya_detail);
		suggestionPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);

		MyViewListener listener = new MyViewListener();
		layoutSuggestion.setOnClickListener(listener);
		
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
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.layout_suggestion_of_xueya_detail:
				if (lableOfSuggestionDetail == 0) {// 展开可见
					suggestionPointerImage
							.setBackgroundResource(R.drawable.xindian_down);
					suggestionContentText.setVisibility(View.VISIBLE);
					lableOfSuggestionDetail++;
				} else {// 隐藏不可见

					suggestionPointerImage
							.setBackgroundResource(R.drawable.abnormal_pointer_normal);
					suggestionContentText.setVisibility(View.GONE);
					lableOfSuggestionDetail--;
				}
			default:

			}
		}
	}
}
