package com.bit_health.android.ui.framelayout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.ui.activities.AbnormalReportActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.util.ChartOfXindian;
import com.bit_health.android.util.CustomDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：XinDianReport
 * 
 * 主要功能：心电测试中的心电报告布局
 * 
 * @author 梁才学 创建日期：2013.12.9
 **********************************************************************/
public class XinDianReport extends FrameLayout {

	private Activity mActivity;
	private AndroidActivityMananger mMananger;
	private View layoutSuggestion;// 小E建议布局
	private View layoutAbnormal;// 异常汇总布局
	private View layoutFoundAbnormal;// 发现异常布局
	private View layoutAbnormalContents;// 异常的具体内容
	private TextView suggestionContentText; // 小E建议布局的内容
	private ImageView suggestionPointerImage;// 小E建议布局中的箭头图片
	private ImageView abnormalPointerImage;// 发现异常布局中的箭头图片

	private int lableOfSuggestion = 0;// 小E建议的标签
	private int lableOfFoundAbnormal = 0;// 发现异常的标签
	private LinearLayout layout;// 画图表布局

	private ArrayList<View> items = new ArrayList<View>();
	// 发现异常内容的按钮相关变量
	private View digit11;
	private View digit12;
	private View digit13;
	private View digit21;
	private View digit22;
	private View digit23;
	private View digit31;
	private View digit32;
	private View digit33;
	private View digit41;
	private View digit42;
	private View digit43;

	private TextView TextViewdigit11;
	private TextView TextViewdigit12;
	private TextView TextViewdigit13;
	private TextView TextViewdigit21;
	private TextView TextViewdigit22;
	private TextView TextViewdigit23;
	private TextView TextViewdigit31;
	private TextView TextViewdigit32;
	private TextView TextViewdigit33;
	private TextView TextViewdigit41;
	private TextView TextViewdigit42;
	private TextView TextViewdigit43;

	private int[] digit11_textcolor;
	private int[] digit12_textcolor;
	private int[] digit13_textcolor;
	private int[] digit21_textcolor;
	private int[] digit22_textcolor;
	private int[] digit23_textcolor;
	private int[] digit31_textcolor;
	private int[] digit32_textcolor;
	private int[] digit33_textcolor;
	private int[] digit41_textcolor;
	private int[] digit42_textcolor;
	private int[] digit43_textcolor;

	private TextView text11;
	private TextView text12;
	private TextView text13;
	private TextView text21;
	private TextView text22;
	private TextView text23;
	private TextView text31;
	private TextView text32;
	private TextView text33;
	private TextView text41;
	private TextView text42;
	private TextView text43;

	private List<JsonBase> mEcgInfos;
	private double[] dataOfXindianReport;// 显示在图表柱状的最后十项的心电值
	private CustomDialog mCustomDialog;

	public XinDianReport(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mMananger = AndroidActivityMananger.getInstatnce();
		mCustomDialog = new CustomDialog(mActivity);
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(List<JsonBase> ecgInfos) {
		this.mEcgInfos = ecgInfos;
		if (ecgInfos.size() < 11) {
			dataOfXindianReport = new double[ecgInfos.size()];
		} else {
			dataOfXindianReport = new double[ChartOfXindian.SHOW_COUNT_TOTLE];
		}
		dataToShowChart(ecgInfos);
		initLayout();
	}

	/******************************************************************
	 * 方法描述：从获取数据显示在图表中
	 * 
	 ******************************************************************/
	private void dataToShowChart(List<JsonBase> ecgInfos) {
		int arrayLength = ecgInfos.size();
		// 获取最后十项数据即可
		if (arrayLength < 11) {
			for (int i = 0; i < arrayLength; i++) {
				EcgInfoBean bean = (EcgInfoBean) ecgInfos.get(i);
				if (bean.mHeartRate != -1) {
					dataOfXindianReport[i] = bean.mHeartRate;
				}
			}
		} else {
			for (int i = arrayLength - 10; i < arrayLength; i++) {
				EcgInfoBean bean = (EcgInfoBean) ecgInfos.get(i);
				if (bean.mHeartRate != -1) {
					dataOfXindianReport[i] = bean.mHeartRate;
				}
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
		View view = inflater.inflate(R.layout.xin_dian_report, this);
		layoutSuggestion = findViewById(R.id.layout_suggestion);
		layoutAbnormal = findViewById(R.id.layout_month_abnormal);
		layoutFoundAbnormal = findViewById(R.id.layout_found_abnormal_of_xindian);
		layoutAbnormalContents = findViewById(R.id.layout_abnormal_contents);
		suggestionContentText = (TextView) findViewById(R.id.suggestion_content);
		suggestionPointerImage = (ImageView) findViewById(R.id.suggestion_pointer_image);
		abnormalPointerImage = (ImageView) findViewById(R.id.abnormal_pointer_image);
		suggestionPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);
		abnormalPointerImage
				.setBackgroundResource(R.drawable.abnormal_pointer_normal);

		digit11 = findViewById(R.id.digit11);
		digit12 = findViewById(R.id.digit12);
		digit13 = findViewById(R.id.digit13);
		digit21 = findViewById(R.id.digit21);
		digit22 = findViewById(R.id.digit22);
		digit23 = findViewById(R.id.digit23);
		digit31 = findViewById(R.id.digit31);
		digit32 = findViewById(R.id.digit32);
		digit33 = findViewById(R.id.digit33);
		digit41 = findViewById(R.id.digit41);
		digit42 = findViewById(R.id.digit42);
		digit43 = findViewById(R.id.digit43);

		TextViewdigit11 = (TextView) view.findViewById(R.id.TextViewdigit11);
		TextViewdigit12 = (TextView) view.findViewById(R.id.TextViewdigit12);
		TextViewdigit13 = (TextView) view.findViewById(R.id.TextViewdigit13);
		TextViewdigit21 = (TextView) view.findViewById(R.id.TextViewdigit21);
		TextViewdigit22 = (TextView) view.findViewById(R.id.TextViewdigit22);
		TextViewdigit23 = (TextView) view.findViewById(R.id.TextViewdigit23);
		TextViewdigit31 = (TextView) view.findViewById(R.id.TextViewdigit31);
		TextViewdigit32 = (TextView) view.findViewById(R.id.TextViewdigit32);
		TextViewdigit33 = (TextView) view.findViewById(R.id.TextViewdigit33);
		TextViewdigit41 = (TextView) view.findViewById(R.id.TextViewdigit41);
		TextViewdigit42 = (TextView) view.findViewById(R.id.TextViewdigit42);
		TextViewdigit43 = (TextView) view.findViewById(R.id.TextViewdigit43);

		text11 = (TextView) findViewById(R.id.text11);
		text12 = (TextView) findViewById(R.id.text12);
		text13 = (TextView) findViewById(R.id.text13);
		text21 = (TextView) findViewById(R.id.text21);
		text22 = (TextView) findViewById(R.id.text22);
		text23 = (TextView) findViewById(R.id.text23);
		text31 = (TextView) findViewById(R.id.text31);
		text32 = (TextView) findViewById(R.id.text32);
		text33 = (TextView) findViewById(R.id.text33);
		text41 = (TextView) findViewById(R.id.text41);
		text42 = (TextView) findViewById(R.id.text42);
		text43 = (TextView) findViewById(R.id.text43);

		MyViewListener listener = new MyViewListener();
		layoutFoundAbnormal.setOnClickListener(listener);
		layoutAbnormal.setOnClickListener(listener);
		layoutSuggestion.setOnClickListener(listener);
		digit11.setOnClickListener(listener);
		digit12.setOnClickListener(listener);
		digit13.setOnClickListener(listener);
		digit21.setOnClickListener(listener);
		digit22.setOnClickListener(listener);
		digit23.setOnClickListener(listener);
		digit31.setOnClickListener(listener);
		digit32.setOnClickListener(listener);
		digit33.setOnClickListener(listener);
		digit41.setOnClickListener(listener);
		digit42.setOnClickListener(listener);
		digit43.setOnClickListener(listener);

		items.add(digit11);
		items.add(digit12);
		items.add(digit13);
		items.add(digit21);
		items.add(digit22);
		items.add(digit23);
		items.add(digit31);
		items.add(digit32);
		items.add(digit33);
		items.add(digit41);
		items.add(digit42);
		items.add(digit43);
		
		// ****************************画图部分 start**************************
		layout = (LinearLayout) findViewById(R.id.chart_show);// 图表占用的布局
		ChartOfXindian chart = new ChartOfXindian(mActivity, layout,
				dataOfXindianReport, "bpm", "心电值");
		chart.drawChart();
		// ****************************画图部分 end*****************************

		mCustomDialog.initDialog();
		getAbnormalInfo();
		setAbnormalIndex();
		SharedPreferences preference = mActivity.getSharedPreferences(
				mActivity.getPackageName(), Context.MODE_PRIVATE);
		setAllTextSizeOfApp((ViewGroup) view,
				preference.getFloat("font_size_range", 0));
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
		int ecgInfosSize = mEcgInfos.size();
		int sum11 = 0, sum12 = 0, sum13 = 0, sum21 = 0, sum22 = 0, sum23 = 0, sum31 = 0, sum32 = 0, sum33 = 0, sum41 = 0, sum42 = 0, sum43 = 0;
		digit11_textcolor = new int[ecgInfosSize];
		digit12_textcolor = new int[ecgInfosSize];
		digit13_textcolor = new int[ecgInfosSize];
		digit21_textcolor = new int[ecgInfosSize];
		digit22_textcolor = new int[ecgInfosSize];
		digit23_textcolor = new int[ecgInfosSize];
		digit31_textcolor = new int[ecgInfosSize];
		digit32_textcolor = new int[ecgInfosSize];
		digit33_textcolor = new int[ecgInfosSize];
		digit41_textcolor = new int[ecgInfosSize];
		digit42_textcolor = new int[ecgInfosSize];
		digit43_textcolor = new int[ecgInfosSize];

		for (int i = 0; i < ecgInfosSize; i++) {
			EcgInfoBean ecgInfo = (EcgInfoBean) mEcgInfos.get(i);
			digit11_textcolor[i] = ecgInfo.mPolycardia;// 心动过速
			digit12_textcolor[i] = ecgInfo.mBradycardia;// 心动过缓
			digit13_textcolor[i] = ecgInfo.mVT;// 阵发性心动过速
			digit21_textcolor[i] = ecgInfo.mBigeminyNum; // 二联律
			digit22_textcolor[i] = ecgInfo.mTrigeminyNum; // 三联律
			digit23_textcolor[i] = ecgInfo.mWideNum; // 宽搏
			digit31_textcolor[i] = ecgInfo.mArrestNum; // 停搏
			digit32_textcolor[i] = ecgInfo.mMissedNum; // 漏搏
			digit33_textcolor[i] = ecgInfo.mPVBNum; // 室性早搏
			digit41_textcolor[i] = ecgInfo.mApbNum; // 房性早搏
			digit42_textcolor[i] = ecgInfo.mInsertPVBnum; // 插入性早搏
			digit43_textcolor[i] = ecgInfo.mArrhythmia; // 心率不齐

			sum11 = sum11 + digit11_textcolor[i];
			sum12 = sum12 + digit12_textcolor[i];
			sum13 = sum13 + digit13_textcolor[i];
			sum21 = sum21 + digit21_textcolor[i];
			sum22 = sum22 + digit22_textcolor[i];
			sum23 = sum23 + digit23_textcolor[i];
			sum31 = sum31 + digit31_textcolor[i];
			sum32 = sum32 + digit32_textcolor[i];
			sum33 = sum33 + digit33_textcolor[i];
			sum41 = sum41 + digit41_textcolor[i];
			sum42 = sum42 + digit42_textcolor[i];
			sum43 = sum43 + digit43_textcolor[i];
		}

		if (sum11 > 0) {
			TextViewdigit11.setTextColor(Color.RED);
			text11.setText(sum11 + "");
			text11.setVisibility(View.VISIBLE);
		}
		if (sum12 > 0) {
			TextViewdigit12.setTextColor(Color.RED);
			text12.setText(sum12 + "");
			text12.setVisibility(View.VISIBLE);
		}
		if (sum13 > 0) {
			TextViewdigit13.setTextColor(Color.RED);
			text13.setText(sum13 + "");
			text13.setVisibility(View.VISIBLE);
		}
		if (sum21 > 0) {
			TextViewdigit21.setTextColor(Color.RED);
			text21.setText(sum21 + "");
			text21.setVisibility(View.VISIBLE);
		}
		if (sum22 > 0) {
			TextViewdigit22.setTextColor(Color.RED);
			text22.setText(sum22 + "");
			text22.setVisibility(View.VISIBLE);
		}
		if (sum23 > 0) {
			TextViewdigit23.setTextColor(Color.RED);
			text23.setText(sum23 + "");
			text23.setVisibility(View.VISIBLE);
		}
		if (sum31 > 0) {
			TextViewdigit31.setTextColor(Color.RED);
			text31.setText(sum31 + "");
			text31.setVisibility(View.VISIBLE);
		}
		if (sum32 > 0) {
			TextViewdigit32.setTextColor(Color.RED);
			text32.setText(sum32 + "");
			text32.setVisibility(View.VISIBLE);
		}
		if (sum33 > 0) {
			TextViewdigit33.setTextColor(Color.RED);
			text33.setText(sum33 + "");
			text33.setVisibility(View.VISIBLE);
		}
		if (sum41 > 0) {
			TextViewdigit41.setTextColor(Color.RED);
			text41.setText(sum41 + "");
			text41.setVisibility(View.VISIBLE);
		}
		if (sum42 > 0) {
			TextViewdigit42.setTextColor(Color.RED);
			text42.setText(sum42 + "");
			text42.setVisibility(View.VISIBLE);
		}
		if (sum43 > 0) {
			TextViewdigit43.setTextColor(Color.RED);
			text43.setText(sum43 + "");
			text43.setVisibility(View.VISIBLE);
		}
	}

	private void setAbnormalIndex() {
		if (lableOfFoundAbnormal == 0) // 展开可见
		{
			abnormalPointerImage.setBackgroundResource(R.drawable.xindian_down);
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

	// 当点击某一项的时候，在该项设置背景
	private void setItemBg(int _id) {
		for (View view : items) {
			if (view.getId() == _id) {
				view.setBackgroundResource(R.drawable.btn_blue_press);
			} else {
				view.setBackgroundResource(R.drawable.actual_content_bg);
			}
		}
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.layout_found_abnormal_of_xindian:
				setAbnormalIndex();
				break;
			case R.id.layout_month_abnormal:

				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreMonthTime(1));
				String roleId = AndroidConfiguration.getInstance(
						mActivity.getApplicationContext()).getRoleId();
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, roleId);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.ECG_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.month_abnormal));
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
				mActivity.overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);

				break;

			case R.id.layout_suggestion:
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
			case R.id.digit11:
				mCustomDialog
						.showDialog(
								"心动过速",
								"\u3000\u3000术语说明：正常人可由运动或精神紧张引起，也可见于发热、甲状腺功能亢进、贫血、失血等情况。请您注意休息，保持情绪稳定，当心率＞160次/分时或感觉身体不适时，及时就医。");
				setItemBg(R.id.digit11);
				break;

			case R.id.digit12:
				mCustomDialog
						.showDialog(
								"心动过缓",
								"\u3000\u3000术语说明：常见于健康的青年人、运动员与睡眠状态，也可见于窦房结功能障碍、甲状腺功能低下、颅内压增高、服用某些药物等。请您注意休息，当心率＜45次/分时或感觉身体不适时，及时就医。");
				setItemBg(R.id.digit12);
				break;

			case R.id.digit13:
				mCustomDialog.showDialog("陈发性心动过速",
						"\u3000\u3000术语说明：由心肌应激性增加所致，常发生于各种器质性心脏病患者，请及时就医治疗。");
				setItemBg(R.id.digit13);
				break;

			case R.id.digit21:
				mCustomDialog
						.showDialog("二联律",
								"\u3000\u3000术语说明：提示心室兴奋性增高，易引发室速。请您及时就医，遵医嘱进行药物治疗或其他治疗，注意休息，避免剧烈运动。");
				setItemBg(R.id.digit21);
				break;

			case R.id.digit22:
				mCustomDialog
						.showDialog("三联律",
								"\u3000\u3000术语说明：提示心室兴奋性增高，易引发室速。请您及时就医，遵医嘱进行药物治疗或其他治疗，注意休息，避免剧烈运动。");
				setItemBg(R.id.digit22);
				break;

			case R.id.digit23:
				mCustomDialog
						.showDialog("宽博",
								"\u3000\u3000术语说明：多见于室内传导阻滞，若患者偶有发作但症状轻微者，可无需治疗，当心动过速发作频繁伴明显症状，请及时就医。");
				setItemBg(R.id.digit23);
				break;

			case R.id.digit31:
				mCustomDialog
						.showDialog("停搏",
								"\u3000\u3000术语说明：因迷走神经张力增大或窦房结功能障碍引起，如出现黑矇、短暂意识障碍或晕厥时，及时就医。");
				setItemBg(R.id.digit31);
				break;

			case R.id.digit32:
				mCustomDialog
						.showDialog(
								"漏博",
								"\u3000\u3000术语说明：与迷走神经张力增高有关，可见于正常人或运动员，也可见于急性心肌梗死、冠状动脉痉挛、心肌炎等情况。当有明显症状时，请及时就医。");
				setItemBg(R.id.digit32);
				break;

			case R.id.digit33:
				mCustomDialog
						.showDialog("室性早搏",
								"\u3000\u3000术语说明：室早出现在两个正常窦性心搏之前，当频发出现或有明显不适症状时，请及时就医。");
				setItemBg(R.id.digit33);
				break;

			case R.id.digit41:
				mCustomDialog
						.showDialog("房性早搏",
								"\u3000\u3000术语说明：由心房组织自律性增强引起，比较常见，健康人及各种器质性心脏病人均可发生，当有明显症状时，请及时就医。");
				setItemBg(R.id.digit41);
				break;

			case R.id.digit42:
				mCustomDialog
						.showDialog("插入性早搏",
								"\u3000\u3000术语说明：室早出现在两个正常窦性心搏之前，当频发出现或有明显不适症状时，请及时就医。");
				setItemBg(R.id.digit42);
				break;

			case R.id.digit43:
				mCustomDialog
						.showDialog("心律不齐",
								"\u3000\u3000术语说明：多与呼吸运动有关，常见于儿童、青年与老年人。一般不需要治疗，需判断它的轻重缓急，有明显不适请及时就医。");
				setItemBg(R.id.digit43);
				break;
			default:

			}
		}
	}
}
