package com.bit_health.android.ui.framelayout;

import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.ui.activities.AbnormalReportActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.util.ChartOfXindian;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：XueYaReport
 * 
 * @author 梁才学 主要功能：血压报告布局 创建日期：2013.12.9
 **********************************************************************/
public class XueYaReport extends FrameLayout {

	private Activity mActivity;
	private AndroidActivityMananger mMananger;
	private View layoutXueYaChartTitle;// 血压图表栏标题的布局
	public View layoutXueYaMoreDialog;// 右上角的"更多"布局
	private View layoutSuggestion;// 小E建议布局
	private View layoutAbnormal;// 异常汇总布局
	private View layoutFoundAbnormal;// 发现异常布局
	private View layoutAbnormalContents;// 异常的具体内容
	private View showChart_bs_ssy;
	private View showChart_bs_szy;
	// private View showChart_bs_ml;
	private View showChart_aw_ssy;
	private View showChart_aw_szy;
	// private View showChart_aw_ml;
	private LinearLayout layoutChartOfXueya;// 血压的图表布局
	private TextView xueyaTitleNameText;// 血压的图表title
	private TextView suggestionContentText; // 小E建议布局的内容
	private ImageView suggestionPointerImage;// 小E建议布局中的箭头图片
	private ImageView abnormalPointerImage;// 发现异常布局中的箭头图片

	private int lableOfSuggestion = 0;// 小E建议的标签
	private int lableOfFoundAbnormal = 0;// 发现异常的标签

	private List<JsonBase> mBpInfoBean;
	private ChartOfXindian mChartOfXueya;
	private double[] dataOfXueyaReport;// 显示在图表柱状的最后十项的血压值

	// 发现异常内容的按钮相关变量
	private LayoutInflater inflaterDialog;
	private AlertDialog alertDialog;
	private View dialogVeiw;
	private TextView theTermName;
	private TextView termContent;
	private Button digit1;
	private Button digit2;
	private Button digit3;
	private Button digit4;
	private Button digit5;

	private int[] digit1_textcolor;
	private int[] digit2_textcolor;
	private int[] digit3_textcolor;
	private int[] digit4_textcolor;
	private int[] digit5_textcolor;

	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private TextView text5;

	public XueYaReport(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mMananger = AndroidActivityMananger.getInstatnce();
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(List<JsonBase> bpInfoBean) {
		this.mBpInfoBean = bpInfoBean;
		if (bpInfoBean.size() < 11) {
			dataOfXueyaReport = new double[bpInfoBean.size()];
		} else {
			dataOfXueyaReport = new double[ChartOfXindian.SHOW_COUNT_TOTLE];
		}
		dataToShowChart(bpInfoBean);
		initLayout();
	}

	/******************************************************************
	 * 方法描述：从获取数据显示在图表中
	 * 
	 ******************************************************************/
	private void dataToShowChart(List<JsonBase> bpInfoBean) {
		int arrayLength = bpInfoBean.size();
		// 获取最后十项数据即可
		if (arrayLength < 11) {
			for (int i = 0; i < arrayLength; i++) {
				BpInfoBean bean = (BpInfoBean) bpInfoBean.get(i);
				dataOfXueyaReport[i] = bean.mSleepSpb;
			}
		} else {
			for (int i = arrayLength - 10; i < arrayLength; i++) {
				BpInfoBean bean = (BpInfoBean) bpInfoBean.get(i);
				dataOfXueyaReport[i] = bean.mSleepSpb;
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
		View view = inflater.inflate(R.layout.xue_ya_report, this);
		layoutXueYaChartTitle = findViewById(R.id.layout_title_xueya);
		layoutXueYaMoreDialog = findViewById(R.id.show_more_chart_dialog_xueya);
		layoutSuggestion = findViewById(R.id.layout_suggestion_of_xueya);
		layoutAbnormal = findViewById(R.id.layout_month_abnormal_of_xueya);
		layoutFoundAbnormal = findViewById(R.id.layout_found_abnormal_of_xueya);
		layoutAbnormalContents = findViewById(R.id.layout_abnormal_contents_of_xueya);
		showChart_bs_ssy = findViewById(R.id.show_chart_bs_ssy);
		showChart_bs_szy = findViewById(R.id.show_chart_bs_szy);
		// showChart_bs_ml = (View) findViewById(R.id.show_chart_bs_ml);
		showChart_aw_ssy = findViewById(R.id.show_chart_aw_ssy);
		showChart_aw_szy = findViewById(R.id.show_chart_aw_szy);
		// showChart_aw_ml = (View) findViewById(R.id.show_chart_aw_ml);
		suggestionContentText = (TextView) findViewById(R.id.suggestion_content_of_xueya);
		xueyaTitleNameText = (TextView) findViewById(R.id.xueya_title_name_text);
		suggestionPointerImage = (ImageView) findViewById(R.id.suggestion_pointer_image_of_xueya);
		abnormalPointerImage = (ImageView) findViewById(R.id.abnormal_pointer_image_of_xueya);
		suggestionPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);
		abnormalPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);

		digit1 = (Button) findViewById(R.id.digit1);
		digit2 = (Button) findViewById(R.id.digit2);
		digit3 = (Button) findViewById(R.id.digit3);
		digit4 = (Button) findViewById(R.id.digit4);
		digit5 = (Button) findViewById(R.id.digit5);
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
		text5 = (TextView) findViewById(R.id.text5);

		MyViewListener listener = new MyViewListener();
		layoutXueYaChartTitle.setOnClickListener(listener);
		layoutFoundAbnormal.setOnClickListener(listener);
		layoutAbnormal.setOnClickListener(listener);
		layoutSuggestion.setOnClickListener(listener);
		showChart_bs_ssy.setOnClickListener(listener);
		showChart_bs_szy.setOnClickListener(listener);
		// showChart_bs_ml.setOnClickListener(listener);
		showChart_aw_ssy.setOnClickListener(listener);
		showChart_aw_szy.setOnClickListener(listener);
		// showChart_aw_ml.setOnClickListener(listener);

		digit1.setOnClickListener(listener);
		digit2.setOnClickListener(listener);
		digit3.setOnClickListener(listener);
		digit4.setOnClickListener(listener);
		digit5.setOnClickListener(listener);

		// ****************************画图部分 start*****************************
		layoutChartOfXueya = (LinearLayout) findViewById(R.id.chart_show_of_xueya);// 图表占用的布局
		mChartOfXueya = new ChartOfXindian(mActivity, layoutChartOfXueya,
				dataOfXueyaReport, "mmHg", "血压值");
		mChartOfXueya.drawChart();
		// ****************************画图部分 end*****************************

		initDialog();
		// getAbnormalInfo();
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

	private void getAbnormalInfo() {
		// TODO Auto-generated method stub
		int mBpInfoBeanSize = mBpInfoBean.size();
		int sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0;
		digit1_textcolor = new int[mBpInfoBeanSize];
		digit2_textcolor = new int[mBpInfoBeanSize];
		digit3_textcolor = new int[mBpInfoBeanSize];
		digit4_textcolor = new int[mBpInfoBeanSize];
		digit5_textcolor = new int[mBpInfoBeanSize];

		for (int i = 0; i < mBpInfoBeanSize; i++) {
			BpInfoBean bpInfo = (BpInfoBean) mBpInfoBean.get(i);
			// digit1_textcolor[i] = bpInfo.mPolycardia;// 低血压
			// digit2_textcolor[i] = bpInfo.mBradycardia;// 边缘性高血压
			// digit3_textcolor[i] = bpInfo.mVT;// 轻度高血压
			// digit4_textcolor[i] = bpInfo.mBigeminyNum; // 中度高血压
			// digit5_textcolor[i] = bpInfo.mTrigeminyNum; // 重度高血压

			sum1 = sum1 + digit1_textcolor[i];
			sum2 = sum2 + digit2_textcolor[i];
			sum3 = sum3 + digit3_textcolor[i];
			sum4 = sum4 + digit4_textcolor[i];
			sum5 = sum5 + digit5_textcolor[i];
		}

		if (sum1 > 0) {
			digit1.setTextColor(Color.RED);
			text1.setText(sum1 + "");
			text1.setVisibility(View.VISIBLE);
		}
		if (sum2 > 0) {
			digit2.setTextColor(Color.RED);
			text2.setText(sum2 + "");
			text2.setVisibility(View.VISIBLE);
		}
		if (sum3 > 0) {
			digit3.setTextColor(Color.RED);
			text3.setText(sum3 + "");
			text3.setVisibility(View.VISIBLE);
		}
		if (sum4 > 0) {
			digit4.setTextColor(Color.RED);
			text4.setText(sum4 + "");
			text4.setVisibility(View.VISIBLE);
		}
		if (sum5 > 0) {
			digit5.setTextColor(Color.RED);
			text5.setText(sum5 + "");
			text5.setVisibility(View.VISIBLE);
		}
	}

	/******************************************************************
	 * 方法描述：刷新图表的数据
	 * 
	 ******************************************************************/
	private void refreshChart(String titleName) {
		Animation animation = AnimationUtils.loadAnimation(mActivity,
				R.anim.chart_dialog_show);
		layoutXueYaMoreDialog.startAnimation(animation);
		layoutXueYaMoreDialog.setVisibility(View.GONE);

		layoutChartOfXueya.removeAllViews();
		mChartOfXueya = new ChartOfXindian(mActivity, layoutChartOfXueya,
				dataOfXueyaReport, "mmHg", "血压值");
		mChartOfXueya.drawChart();

		xueyaTitleNameText.setText(titleName);
	}

	/******************************************************************
	 * 方法描述：显示右上角对话框
	 * 
	 ******************************************************************/
	private void showChartDialog() {
		Animation animation = AnimationUtils.loadAnimation(mActivity,
				R.anim.chart_dialog_hidden);
		layoutXueYaMoreDialog.startAnimation(animation);
		layoutXueYaMoreDialog.setVisibility(View.VISIBLE);
	}

	/******************************************************************
	 * 方法描述：隐藏右上角对话框
	 * 
	 ******************************************************************/
	public void hiddenChartDialog() {
		Animation animation = AnimationUtils.loadAnimation(mActivity,
				R.anim.chart_dialog_show);
		layoutXueYaMoreDialog.startAnimation(animation);
		layoutXueYaMoreDialog.setVisibility(View.GONE);
	}

	private void initDialog() {
		inflaterDialog = LayoutInflater.from(mActivity);
		dialogVeiw = inflaterDialog.inflate(R.layout.xindian_abnormal_dialog,
				null);
		theTermName = (TextView) dialogVeiw.findViewById(R.id.the_term_name);
		termContent = (TextView) dialogVeiw.findViewById(R.id.term_content);
		alertDialog = new AlertDialog.Builder(mActivity).create();
		alertDialog.setView(dialogVeiw, 0, 0, 0, 0);
		alertDialog.setCanceledOnTouchOutside(true);
	}

	private void showDialog(String term_name, String term_content) {
		theTermName.setText(term_name);
		termContent.setText(term_content);
		alertDialog.show();
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
		@Override
		public void onClick(View v) {
			int arrayLength = mBpInfoBean.size();
			switch (v.getId()) {

			case R.id.layout_title_xueya:
				if (layoutXueYaMoreDialog.getVisibility() == View.VISIBLE) {
					hiddenChartDialog();
				} else if (layoutXueYaMoreDialog.getVisibility() == View.GONE) {
					showChartDialog();
				}
				break;

			case R.id.show_chart_bs_ssy:
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mSleepSpb;
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mSleepSpb;
					}
				}
				refreshChart("睡觉前收缩压");
				break;

			case R.id.show_chart_bs_szy:
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mSleepDpb;
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mSleepDpb;
					}
				}
				refreshChart("睡觉前舒张压");
				break;

			// case R.id.show_chart_bs_ml:
			// // 获取最后十项数据即可
			// if (arrayLength < 11) {
			// for (int i = 0; i < arrayLength; i++) {
			// BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
			// dataOfXueyaReport[i] = bean.mSleepPR;
			// }
			// } else {
			// for (int i = arrayLength - 10; i < arrayLength; i++) {
			// BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
			// dataOfXueyaReport[i] = bean.mSleepPR;
			// }
			// }
			// refreshChart("睡觉前脉率");
			// break;

			case R.id.show_chart_aw_ssy:
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mWakeSpb;
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mWakeSpb;
					}
				}
				refreshChart("起床后收缩压");
				break;

			case R.id.show_chart_aw_szy:
				// 获取最后十项数据即可
				if (arrayLength < 11) {
					for (int i = 0; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mWakeDpb;
					}
				} else {
					for (int i = arrayLength - 10; i < arrayLength; i++) {
						BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
						dataOfXueyaReport[i] = bean.mWakeDpb;
					}
				}
				refreshChart("起床后舒张压");
				break;

			// case R.id.show_chart_aw_ml:
			// // 获取最后十项数据即可
			// if (arrayLength < 11) {
			// for (int i = 0; i < arrayLength; i++) {
			// BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
			// dataOfXueyaReport[i] = bean.mWakePR;
			// }
			// } else {
			// for (int i = arrayLength - 10; i < arrayLength; i++) {
			// BpInfoBean bean = (BpInfoBean) mBpInfoBean.get(i);
			// dataOfXueyaReport[i] = bean.mWakePR;
			// }
			// }
			// refreshChart("起床后脉率");
			// break;

			case R.id.layout_found_abnormal_of_xueya:
				if (layoutXueYaMoreDialog.getVisibility() == View.VISIBLE) {
					hiddenChartDialog();
				}
				setAbnormalIndex();
				break;
			case R.id.layout_month_abnormal_of_xueya:
				if (layoutXueYaMoreDialog.getVisibility() == View.VISIBLE) {
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
						BusinessConst.BP_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.month_abnormal));	
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;

			case R.id.layout_suggestion_of_xueya:
				if (layoutXueYaMoreDialog.getVisibility() == View.VISIBLE) {
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
				break;
//			case R.id.digit1:
//				showDialog("低血压", "\u3000\u3000术语说明：");
//				break;
//
//			case R.id.digit2:
//				showDialog("边缘性高血压", "\u3000\u3000术语说明：");
//				break;
//
//			case R.id.digit3:
//				showDialog("轻度高血压", "\u3000\u3000术语说明：");
//				break;
//
//			case R.id.digit4:
//				showDialog("中度高血压", "\u3000\u3000术语说明：");
//				break;
//
//			case R.id.digit5:
//				showDialog("重度高血压", "\u3000\u3000术语说明：");
//				break;

			default:

			}
		}
	}
}
