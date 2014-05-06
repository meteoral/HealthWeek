package com.bit_health.android.ui.framelayout;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：ReportDetailOfXinDian
 * 
 * 主要功能：血糖报告详情
 * 
 * @author 梁才学 创建日期：2014.2.11
 **********************************************************************/
public class ReportDetailOfXuetang extends FrameLayout {

	private Activity mActivity;

	private View layoutXueTangSuggestion;// 小E建议布局
	private TextView suggestionContentText; // 小E建议布局的内容
	private ImageView suggestionPointerImage;
	private ImageView cursorChartFutang;
	private ImageView cursorChartKongfu;
	private TextView showTimeText;// 时间

	private TextView showKongFuText;// 空腹
	private TextView showBeforeLunchText;// 午餐前
	private TextView showAfterLunchText;// 午餐后2小时
	private TextView showBeforeSleepText;// 睡前
	private TextView dialogTitle;
	private TextView left_number;
	private TextView mid_number;
	private TextView right_number;
	private String showTimeString;
	private String showKongFuString;
	private String showBeforeLunchString;
	private String showAfterLunchString;
	private String showBeforeSleepString;

	// 血糖状态相关变量
	private ImageView bsStateImage;// 状态结果
	private TextView showKongFuTextState;// 空腹状态
	private TextView showBeforeLunchTextState;// 午餐前状态
	private TextView showAfterLunchTextState;// 午餐后2小时状态
	private TextView showBeforeSleepTextState;// 睡前状态
	private String showKongFuState;
	private String showBeforeLunchState;
	private String showAfterLunchState;
	private String showBeforeSleepState;

	private int lable = 0;

	private LayoutInflater inflaterDialog;
	private Dialog dialog;
	private View dialogVeiw;

	public ReportDetailOfXuetang(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(BsInfoBean bsInfo) {
		initLayout();
		initData(bsInfo);
	}

	private void initData(BsInfoBean bsInfo) {
		// TODO Auto-generated method stub

		showTimeString = bsInfo.mTimeStamp;
		showKongFuString = bsInfo.mLimosis;
		showBeforeLunchString = bsInfo.mBLunch;
		showAfterLunchString = bsInfo.mALunch;
		showBeforeSleepString = bsInfo.mBSleep;

		// 血糖状态
		showKongFuState = bsInfo.mLimosis_value_ab;
		showBeforeLunchState = bsInfo.mBefore_lunch_value_ab;
		showAfterLunchState = bsInfo.mAfter_lunch_value_ab;
		showBeforeSleepState = bsInfo.mBefore_sleep_value_ab;

		showTimeText.setText(TimeFormatUtil.bsOrBpShowTime(showTimeString));
		showKongFuText.setText(showKongFuString);
		showBeforeLunchText.setText(showBeforeLunchString);
		showAfterLunchText.setText(showAfterLunchString);
		showBeforeSleepText.setText(showBeforeSleepString);

		// 血糖状态
		showKongFuTextState.setText(showKongFuState);
		showBeforeLunchTextState.setText(showBeforeLunchState);
		showAfterLunchTextState.setText(showAfterLunchState);
		showBeforeSleepTextState.setText(showBeforeSleepState);

		if (bsInfo.bIsNormal) {
			bsStateImage.setImageDrawable(getResources().getDrawable(
					R.drawable.normal));
		} else {
			bsStateImage.setImageDrawable(getResources().getDrawable(
					R.drawable.abnormal));
		}
	}

	/******************************************************************
	 * 方法描述：界面初始化
	 * 
	 ******************************************************************/
	private void initLayout() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.xue_tang_report_detail2, this);

		bsStateImage = (ImageView) findViewById(R.id.bs_detail_state_image);
		layoutXueTangSuggestion = findViewById(R.id.layout_xue_tang_suggestion);
		showTimeText = (TextView) findViewById(R.id.detail_report_time_of_xuetang);
		showKongFuText = (TextView) findViewById(R.id.kongfu_value_text);
		showBeforeLunchText = (TextView) findViewById(R.id.before_lunch_value_text);
		showAfterLunchText = (TextView) findViewById(R.id.after_lunch_value_text);
		showBeforeSleepText = (TextView) findViewById(R.id.before_sleep_value_text);

		showKongFuTextState = (TextView) findViewById(R.id.kongfu_state_text);
		showBeforeLunchTextState = (TextView) findViewById(R.id.before_lunch_state_text);
		showAfterLunchTextState = (TextView) findViewById(R.id.after_lunch_state_text);
		showBeforeSleepTextState = (TextView) findViewById(R.id.before_sleep_state_text);

		suggestionContentText = (TextView) findViewById(R.id.xue_tang_xiao_E_content);
		suggestionContentText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		suggestionPointerImage = (ImageView) findViewById(R.id.xue_tang_xiao_E_image);
		cursorChartFutang = (ImageView) findViewById(R.id.cursor_chart_futang);
		cursorChartKongfu = (ImageView) findViewById(R.id.cursor_chart_kongfu);
		suggestionPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);

		MyViewListener listener = new MyViewListener();
		layoutXueTangSuggestion.setOnClickListener(listener);
		cursorChartFutang.setOnClickListener(listener);
		cursorChartKongfu.setOnClickListener(listener);

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

	private void initDialog() {
		inflaterDialog = LayoutInflater.from(mActivity);
		dialogVeiw = inflaterDialog.inflate(
				R.layout.cursor_chart_futang_construction, null);
		dialogTitle = (TextView) dialogVeiw.findViewById(R.id.dialog_title);
		left_number = (TextView) dialogVeiw.findViewById(R.id.left_number);
		mid_number = (TextView) dialogVeiw.findViewById(R.id.mid_number);
		right_number = (TextView) dialogVeiw.findViewById(R.id.right_number);

		dialog = new Dialog(mActivity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// 通过下边的代码去掉对话框的title
		dialog.setContentView(dialogVeiw);
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setAttributes(lp);
		dialogWindow.setBackgroundDrawable(mActivity.getResources()
				.getDrawable(R.drawable.ic_preference_single_normal));
		dialog.setCanceledOnTouchOutside(true);
	}

	private void showDialog(String title, String leftNum, String midNum,
			String rightNum) {
		dialogTitle.setText(title);
		left_number.setText(leftNum);
		mid_number.setText(midNum);
		right_number.setText(rightNum);
		dialog.show();
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.layout_xue_tang_suggestion:

				if (lable == 0) {// 展开可见
					suggestionPointerImage
							.setBackgroundResource(R.drawable.xindian_down);
					suggestionContentText.setVisibility(View.VISIBLE);
					lable++;
				} else {// 隐藏不可见

					suggestionPointerImage
							.setBackgroundResource(R.drawable.abnormal_pointer_normal);
					suggestionContentText.setVisibility(View.GONE);
					lable--;
				}
				break;

			case R.id.cursor_chart_futang:
				initDialog();
				showDialog("服糖后两小时毛细血管采血参考", "3.9", "7.8", "11.1");
				break;

			case R.id.cursor_chart_kongfu:
				initDialog();
				showDialog("空腹毛细血管采血参考", "3.9", "5.6", "6.1");
				break;
			default:

			}
		}
	}
}
