package com.bit_health.android.ui.framelayout;

import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.ui.activities.AbnormalReportActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.util.ChartOfXindian;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：XueTangReport
 * 
 * @author 梁才学 主要功能：血糖报告的界面内容 创建日期：2013.12.23
 **********************************************************************/
public class XueTangReport extends FrameLayout {

	private Activity mActivity;
	private AndroidActivityMananger mMananger;
	private View layoutSuggestion;// 小E建议布局
	private View layoutAbnormal;// 异常汇总布局
	private View layoutFoundAbnormal;// 发现异常布局
	public View layoutXueTangMoreDialog;// 右上角的"更多"布局
	private View layoutKongFuXueTang;// 血糖布局
	private LinearLayout layoutOfChart;// 血糖的图表布局
	private View layoutAbnormalContents;// 异常的具体内容
	private TextView suggestionContentText; // 小E建议布局的内容
	private TextView xuetangTitleNameText;
	private View show_kf_chart;
	private View show_bl_chart;
	private View show_al_chart;
	private View show_bs_chart;
	private ImageView suggestionPointerImage;// 小E建议布局中的箭头图片
	private ImageView abnormalPointerImage;// 发现异常布局中的箭头图片

	private int lableOfSuggestion = 0;// 小E建议的标签
	private int lableOfFoundAbnormal = 0;// 发现异常的标签

	private ChartOfXindian mChartOfXuetang;
	private double[] dataOfXindianReport;// 显示在图表柱状的最后十项的血糖值
	private List<JsonBase> mBsInfoBean;
	
	private TextView digitDi;
	private TextView digitPianDi;
	private TextView digitPianGao;

	public XueTangReport(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mMananger = AndroidActivityMananger.getInstatnce();
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(List<JsonBase> bsInfoBean) {
		this.mBsInfoBean = bsInfoBean;
		if (bsInfoBean.size() < 11) {
			dataOfXindianReport = new double[bsInfoBean.size()];
		} else {
			dataOfXindianReport = new double[ChartOfXindian.SHOW_COUNT_TOTLE];
		}
		dataToShowChart(bsInfoBean);
		initLayout();
	}

	private void dataToShowChart(List<JsonBase> bsInfoBean) {
		int arrayLength = bsInfoBean.size();
		// 获取最后十项数据即可
		if (arrayLength < 11) {
			for (int i = 0; i < arrayLength; i++) {
				BsInfoBean bean = (BsInfoBean) bsInfoBean.get(i);
				dataOfXindianReport[i] = Double.parseDouble(bean.mLimosis);
			}
		} else {
			for (int i = arrayLength - 10; i < arrayLength; i++) {
				BsInfoBean bean = (BsInfoBean) bsInfoBean.get(i);
				dataOfXindianReport[i] = Double.parseDouble(bean.mLimosis);
			}
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
		View view = inflater.inflate(R.layout.xue_tang_report, this);
		layoutSuggestion = (View) findViewById(R.id.layout_suggestion_of_xuetang);
		layoutAbnormal = (View) findViewById(R.id.layout_month_abnormal_of_xuetang);
		layoutFoundAbnormal = (View) findViewById(R.id.layout_found_abnormal_of_xuetang);
		layoutXueTangMoreDialog = (View) findViewById(R.id.show_more_chart_dialog_xuetang);
		layoutKongFuXueTang = (View) findViewById(R.id.layout_kongfu_xuetang);

		layoutAbnormalContents = (View) findViewById(R.id.layout_abnormal_contents_of_xuetang);
		show_kf_chart = (View) findViewById(R.id.show_kongfu_xuetang_chart);
		show_bl_chart = (View) findViewById(R.id.show_befort_lunch_xuetang_chart);
		show_al_chart = (View) findViewById(R.id.show_after_lunch_xuetang_chart);
		show_bs_chart = (View) findViewById(R.id.show_before_sleep_xuetang_chart);
		xuetangTitleNameText = (TextView) findViewById(R.id.xuetang_title_name_text);
		suggestionContentText = (TextView) findViewById(R.id.suggestion_content_of_xuetang);
		suggestionPointerImage = (ImageView) findViewById(R.id.suggestion_pointer_image_of_xuetang);
		abnormalPointerImage = (ImageView) findViewById(R.id.abnormal_pointer_image_of_xuetang);
		suggestionPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);
		abnormalPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);
		layoutKongFuXueTang
				.setBackgroundResource(R.drawable.chart_title_select);
		digitDi = (TextView) findViewById(R.id.digit_di);
		digitPianDi = (TextView) findViewById(R.id.digit_pian_di);
		digitPianGao = (TextView) findViewById(R.id.digit_pian_gao);

		MyViewListener listener = new MyViewListener();
		layoutFoundAbnormal.setOnClickListener(listener);
		layoutAbnormal.setOnClickListener(listener);
		layoutSuggestion.setOnClickListener(listener);
		layoutKongFuXueTang.setOnClickListener(listener);
		show_kf_chart.setOnClickListener(listener);
		show_bl_chart.setOnClickListener(listener);
		show_al_chart.setOnClickListener(listener);
		show_bs_chart.setOnClickListener(listener);

		// ****************************画图部分 start*****************************
		layoutOfChart = (LinearLayout) findViewById(R.id.chart_show_of_xuetang);// 图表占用的布局
		mChartOfXuetang = new ChartOfXindian(mActivity, layoutOfChart,
				dataOfXindianReport, "mmol/L", "血糖值");
		mChartOfXuetang.drawChart();
		// ****************************画图部分 end*****************************
		
		setAbnormalIndex();
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

	/******************************************************************
	 * 方法描述：刷新图表的数据
	 * 
	 ******************************************************************/
	private void refreshChart(String titleName) {
		Animation animation = AnimationUtils.loadAnimation(mActivity,
				R.anim.chart_dialog_show);
		layoutXueTangMoreDialog.startAnimation(animation);
		layoutXueTangMoreDialog.setVisibility(View.GONE);

		layoutOfChart.removeAllViews();
		mChartOfXuetang = new ChartOfXindian(mActivity, layoutOfChart,
				dataOfXindianReport, "mmol/L", "血糖值");
		mChartOfXuetang.drawChart();

		xuetangTitleNameText.setText(titleName);
	}

	/******************************************************************
	 * 方法描述：显示右上角对话框
	 * 
	 ******************************************************************/
	private void showChartDialog() {
		Animation animation = AnimationUtils.loadAnimation(mActivity,
				R.anim.chart_dialog_hidden);
		layoutXueTangMoreDialog.startAnimation(animation);
		layoutXueTangMoreDialog.setVisibility(View.VISIBLE);
	}

	/******************************************************************
	 * 方法描述：隐藏右上角对话框
	 * 
	 ******************************************************************/
	public void hiddenChartDialog() {
		Animation animation = AnimationUtils.loadAnimation(mActivity,
				R.anim.chart_dialog_show);
		layoutXueTangMoreDialog.startAnimation(animation);
		layoutXueTangMoreDialog.setVisibility(View.GONE);
	}

	private void setAbnormalIndex(){
		if (lableOfFoundAbnormal == 0) // 展开可见
		{
			abnormalPointerImage
					.setBackgroundResource(R.drawable.xindian_down);
			layoutAbnormalContents.setVisibility(View.VISIBLE);
			lableOfFoundAbnormal++;
		} else // 隐藏不可见
		{
			abnormalPointerImage
					.setBackgroundResource(R.drawable.abnormal_pointer_normal);
			layoutAbnormalContents.setVisibility(View.GONE);
			lableOfFoundAbnormal--;
		}
	}
	
	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			int arrayLength = mBsInfoBean.size();

			switch (v.getId()) {
			case R.id.layout_kongfu_xuetang:// 血糖图表的title

				if (layoutXueTangMoreDialog.getVisibility() == View.VISIBLE) {
					hiddenChartDialog();
				} else if (layoutXueTangMoreDialog.getVisibility() == View.GONE) {
					showChartDialog();
				}

				break;
			case R.id.layout_found_abnormal_of_xuetang:// 发现异常
				if (layoutXueTangMoreDialog.getVisibility() == View.VISIBLE) {
					hiddenChartDialog();
				}
				setAbnormalIndex();
				break;
			case R.id.layout_month_abnormal_of_xuetang:// 近一月异常汇总
				if (layoutXueTangMoreDialog.getVisibility() == View.VISIBLE) {
					hiddenChartDialog();
				}

				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreMonthTime(1));
				String roleId = AndroidConfiguration.getInstance(
						mActivity.getApplicationContext()).getRoleId();
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, roleId);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.BS_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.month_abnormal));	
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
				mActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

				break;

			case R.id.show_kongfu_xuetang_chart:// 空腹
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mLimosis);
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mLimosis);
					}
				}
				refreshChart("空腹血糖");
				break;

			case R.id.show_befort_lunch_xuetang_chart:// 午餐前
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mBLunch);
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mBLunch);
					}
				}
				refreshChart("午餐前血糖");
				break;

			case R.id.show_after_lunch_xuetang_chart:// 午餐后2小时
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mALunch);
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mALunch);
					}
				}
				refreshChart("餐后2小时血糖");
				break;

			case R.id.show_before_sleep_xuetang_chart:// 睡前
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mBSleep);
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BsInfoBean bean = (BsInfoBean) mBsInfoBean.get(i);
						dataOfXindianReport[i] = Double
								.parseDouble(bean.mBSleep);
					}
				}
				refreshChart("睡前血糖");
				break;

			case R.id.layout_suggestion_of_xuetang:// 小E建议
				if (layoutXueTangMoreDialog.getVisibility() == View.VISIBLE) {
					hiddenChartDialog();
				}
				if (lableOfSuggestion == 0) {// 展开可见
					suggestionPointerImage
							.setBackgroundResource(R.drawable.xindian_down);
					suggestionContentText.setVisibility(View.VISIBLE);
					lableOfSuggestion++;
				} else {// 隐藏不可见

					suggestionPointerImage
							.setBackgroundResource(R.drawable.abnormal_pointer_normal);
					suggestionContentText.setVisibility(View.GONE);
					lableOfSuggestion--;
				}
			default:

			}
		}
	}
}
