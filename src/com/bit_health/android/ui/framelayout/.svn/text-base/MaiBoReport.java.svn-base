package com.bit_health.android.ui.framelayout;

import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.ui.activities.AbnormalReportActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;
import com.bit_health.android.util.ChartOfXindian;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**********************************************************************
 * 类名：MaiBoReport
 * 
 * 主要功能：脉搏分析中的脉搏报告布局
 * 
 * @author 梁才学 创建日期：2014.2.19
 **********************************************************************/
public class MaiBoReport extends FrameLayout {

	private Activity mActivity;
	private AndroidActivityMananger mMananger;
	private List<JsonBase> mPpgInfos;
	private LinearLayout layout;// 画图表布局
	private double[] dataOfMaiboReport;// 显示在图表柱状的最后十项的心电值

	public MaiBoReport(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mMananger = AndroidActivityMananger.getInstatnce();
	}

	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate(List<JsonBase> ppgInfos) {
		this.mPpgInfos = ppgInfos;
		if (ppgInfos.size() < 11) {
			dataOfMaiboReport = new double[ppgInfos.size()];
		} else {
			dataOfMaiboReport = new double[ChartOfXindian.SHOW_COUNT_TOTLE];
		}
		dataToShowChart(ppgInfos);
		initLayout();
	}

	/******************************************************************
	 * 方法描述：从获取数据显示在图表中
	 * 
	 ******************************************************************/
	private void dataToShowChart(List<JsonBase> ppgInfos) {
		int arrayLength = ppgInfos.size();
		// 获取最后十项数据即可
		if (arrayLength < 11) {
			for (int i = 0; i < arrayLength; i++) {
				PpgInfoBean bean = (PpgInfoBean) ppgInfos.get(i);
				if(bean.mPulserate != -1){
					dataOfMaiboReport[i] = bean.mPulserate;
				}				
			}
		} else {
			for (int i = arrayLength - 10; i < arrayLength; i++) {
				PpgInfoBean bean = (PpgInfoBean) ppgInfos.get(i);
				if(bean.mPulserate != -1){
					dataOfMaiboReport[i] = bean.mPulserate;
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
		View view = inflater.inflate(R.layout.maibo_report, this);

		// ****************************画图部分 start**************************
		layout = (LinearLayout) findViewById(R.id.ppgchart_show);// 图表占用的布局
		ChartOfXindian chart = new ChartOfXindian(mActivity, layout,
				dataOfMaiboReport, "bpm", "脉搏值");
		chart.drawChart();
		// ****************************画图部分 end*****************************

		MyViewListener listener = new MyViewListener();
		findViewById(R.id.layout_month_abnormal_of_ppg).setOnClickListener(
				listener);
		
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

			switch (v.getId()) {

			case R.id.layout_month_abnormal_of_ppg:
				Hashtable<String, String> vaules = new Hashtable<String, String>();
				vaules.put(AbnormalReportActivity.INTENT_AB_BEGIN,
						TimeFormatUtil.getPreMonthTime(1));
				String roleId = AndroidConfiguration.getInstance(
						mActivity.getApplicationContext()).getRoleId();
				vaules.put(AbnormalReportActivity.INTENT_AB_ROLE, roleId);
				vaules.put(AbnormalReportActivity.INTENT_MS_TYPE,
						BusinessConst.ANLASY_SUCCESS_ABNORMAL);
				vaules.put(AbnormalReportActivity.INTENT_SEARCH_TYPE,
						BusinessConst.PPG_MESURE);
				vaules.put(AbnormalReportActivity.INTENT_TYPE_TITLE, mActivity
						.getResources().getString(R.string.month_abnormal));	
				mMananger.switchActivityNoClose(
						FourModuleManangerActivity.class.getSimpleName(),
						AbnormalReportActivity.class, vaules);
				mActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

				break;
			default:

			}
		}
	}
}
