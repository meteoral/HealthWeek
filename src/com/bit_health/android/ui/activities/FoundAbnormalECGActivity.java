package com.bit_health.android.ui.activities;

import java.util.ArrayList;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：FoundAbnormalECGActivity
 * 
 * 主要功能：心电报告详情界面中，发现异常的内容界面
 * 
 * @author 梁才学 创建日期：2014.2.9
 **********************************************************************/
public class FoundAbnormalECGActivity extends BaseActivity {

	private LayoutInflater inflater;
	private View view;
	private ImageView backIcon;
	private TextView termInstruction;
	private TextView doctorSuggestion;

	private ArrayList<View> items = new ArrayList<View>();
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

	private int digit11_textcolor;
	private int digit12_textcolor;
	private int digit13_textcolor;
	private int digit21_textcolor;
	private int digit22_textcolor;
	private int digit23_textcolor;
	private int digit31_textcolor;
	private int digit32_textcolor;
	private int digit33_textcolor;
	private int digit41_textcolor;
	private int digit42_textcolor;
	private int digit43_textcolor;

	private EcgInfoBean mEcgInfoId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		mEcgInfoId = (EcgInfoBean) intent.getExtras().getSerializable(
				"detail_ecgInfo_id");// 通过序列化的方式，使用intent传递 EcgInfoBean 对象过来
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.found_abnormal_content_ecg, null);
		backIcon = (ImageView) view
				.findViewById(R.id.found_abnormal_content_back_image);
		setContentView(view);

		termInstruction = (TextView) view
				.findViewById(R.id.found_abnormal_term_instruction);
		doctorSuggestion = (TextView) view
				.findViewById(R.id.found_abnormal_doctor_suggestion);

		((TextView) view.findViewById(R.id.text_shuoming)).setText(Html
				.fromHtml(getResources().getString(R.string.backup_shuoming)));

		digit11 = view.findViewById(R.id.digit11);
		digit12 = view.findViewById(R.id.digit12);
		digit13 = view.findViewById(R.id.digit13);
		digit21 = view.findViewById(R.id.digit21);
		digit22 = view.findViewById(R.id.digit22);
		digit23 = view.findViewById(R.id.digit23);
		digit31 = view.findViewById(R.id.digit31);
		digit32 = view.findViewById(R.id.digit32);
		digit33 = view.findViewById(R.id.digit33);
		digit41 = view.findViewById(R.id.digit41);
		digit42 = view.findViewById(R.id.digit42);
		digit43 = view.findViewById(R.id.digit43);

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

		getEcgInfoDataById(mEcgInfoId);

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
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
		
		setFirstBlockState();
		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
				
	}

	/***************************************************************
	 * 方法描述：显示第一个按钮的数据
	 * 
	 **************************************************************/
	private void setFirstBlockState(){
		setTermInstructionTextColor(
				getResources().getString(R.string.xin_dong_guo_su),
				getResources().getString(
						R.string.Polycardia_instruction));
		// 如果是红色的，就显示医生建议
		if (TextViewdigit11.getTextColors().getDefaultColor() == Color.RED) {
			setDoctorSuggestionTextColor(getResources().getString(
					R.string.Polycardia_suggest));
		} else {
			doctorSuggestion.setVisibility(View.GONE);
		}
		
		setItemBg(R.id.digit11);
	}
	
	/***************************************************************
	 * 方法描述：根据id从服务器获取数据
	 * 
	 **************************************************************/
	private void getEcgInfoDataById(EcgInfoBean ecgInfo) {
		// TODO Auto-generated method stub

		digit11_textcolor = ecgInfo.mPolycardia;// 心动过速
		digit12_textcolor = ecgInfo.mBradycardia;// 心动过缓
		digit13_textcolor = ecgInfo.mVT;// 阵发性心动过速
		digit21_textcolor = ecgInfo.mBigeminyNum; // 二联律
		digit22_textcolor = ecgInfo.mTrigeminyNum; // 三联律
		digit23_textcolor = ecgInfo.mWideNum; // 宽搏
		digit31_textcolor = ecgInfo.mArrestNum; // 停搏
		digit32_textcolor = ecgInfo.mMissedNum; // 漏搏
		digit33_textcolor = ecgInfo.mPVBNum; // 室性早搏
		digit41_textcolor = ecgInfo.mApbNum; // 房性早搏
		digit42_textcolor = ecgInfo.mInsertPVBnum; // 插入性早搏
		digit43_textcolor = ecgInfo.mArrhythmia; // 心率不齐

		if (digit11_textcolor > 0) {
			TextViewdigit11.setTextColor(Color.RED);
		}
		if (digit12_textcolor > 0) {
			TextViewdigit12.setTextColor(Color.RED);
		}
		if (digit13_textcolor > 0) {
			TextViewdigit13.setTextColor(Color.RED);
		}
		if (digit21_textcolor > 0) {
			TextViewdigit21.setTextColor(Color.RED);
		}
		if (digit22_textcolor > 0) {
			TextViewdigit22.setTextColor(Color.RED);
		}
		if (digit23_textcolor > 0) {
			TextViewdigit23.setTextColor(Color.RED);
		}
		if (digit31_textcolor > 0) {
			TextViewdigit31.setTextColor(Color.RED);
		}
		if (digit32_textcolor > 0) {
			TextViewdigit32.setTextColor(Color.RED);
		}
		if (digit33_textcolor > 0) {
			TextViewdigit33.setTextColor(Color.RED);
		}
		if (digit41_textcolor > 0) {
			TextViewdigit41.setTextColor(Color.RED);
		}
		if (digit42_textcolor > 0) {
			TextViewdigit42.setTextColor(Color.RED);
		}
		if (digit43_textcolor > 0) {
			TextViewdigit43.setTextColor(Color.RED);
		}
	}

	@Override
	public void getMeasureDetailCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub
		super.getMeasureDetailCallback(retCode, errorMsg, bean);
		hideWaittingDialog();
		switch (retCode) {
		case 0:

			break;
		default:
			break;
		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/***************************************************************
	 * 方法描述：将"医生建议"这四个字设置成红色
	 * 
	 **************************************************************/
	private void setDoctorSuggestionTextColor(String string) {
		doctorSuggestion.setVisibility(View.VISIBLE);
		doctorSuggestion.setText(Html
				.fromHtml("<font color=\"red\">医生建议：</font>" + string));
	}

	/***************************************************************
	 * 方法描述：将术语说明的字设置成蓝色
	 * 
	 **************************************************************/
	private void setTermInstructionTextColor(String termName, String instruction) {
		termInstruction.setText(Html.fromHtml("<font color=\"blue\">"
				+ termName + ": " + "</font>" + instruction));
	}

	// 当点击某一项的时候，在该项设置背景
	private void setItemBg(int _id){
		for(View view : items){
			if(view.getId() == _id){
				view.setBackgroundResource(R.drawable.btn_blue_press);
			}else{
				view.setBackgroundResource(R.drawable.actual_content_bg);
			}
		}
	}
	
	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.found_abnormal_content_back_image:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.digit11:
				setFirstBlockState();
				break;

			case R.id.digit12:
				setTermInstructionTextColor(
						getResources().getString(R.string.xin_dong_guo_huan),
						getResources().getString(
								R.string.Bradycardia_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit12.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Bradycardia_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit12);
				break;

			case R.id.digit13:
				setTermInstructionTextColor(
						getResources().getString(R.string.zhen_fa_xing_guo_su),
						getResources().getString(R.string.VT_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit13.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.VT_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit13);
				break;

			case R.id.digit21:
				setTermInstructionTextColor(
						getResources().getString(R.string.er_lian_lv),
						getResources().getString(
								R.string.Bigeminy_Num_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit21.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Bigeminy_Num_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit21);
				break;

			case R.id.digit22:
				setTermInstructionTextColor(
						getResources().getString(R.string.san_lian_lv),
						getResources().getString(
								R.string.Trigeminy_Num_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit22.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Trigeminy_Num_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit22);
				break;

			case R.id.digit23:
				setTermInstructionTextColor(
						getResources().getString(R.string.kuan_bo),
						getResources().getString(R.string.Wide_Num_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit23.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Wide_Num_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit23);
				break;

			case R.id.digit31:
				setTermInstructionTextColor(
						getResources().getString(R.string.ting_bo),
						getResources().getString(
								R.string.Arrest_Num_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit31.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Arrest_Num_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit31);
				break;

			case R.id.digit32:
				setTermInstructionTextColor(
						getResources().getString(R.string.lou_bo),
						getResources().getString(
								R.string.Missed_Num_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit32.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Missed_Num_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit32);
				break;

			case R.id.digit33:
				setTermInstructionTextColor(
						getResources().getString(R.string.shi_xing_zao_bo),
						getResources().getString(R.string.PVB_Num_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit33.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.PVB_Num_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit33);
				break;

			case R.id.digit41:
				setTermInstructionTextColor(
						getResources().getString(R.string.fang_xing_zao_bo),
						getResources().getString(R.string.APB_Num_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit41.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.APB_Num_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit41);
				break;

			case R.id.digit42:
				setTermInstructionTextColor(
						getResources().getString(R.string.cha_ru_xing_zao_bo),
						getResources().getString(
								R.string.Insert_PVBnum_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit42.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Insert_PVBnum_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit42);
				break;

			case R.id.digit43:
				setTermInstructionTextColor(
						getResources().getString(R.string.xin_lv_bu_qi),
						getResources().getString(
								R.string.Arrhythmia_instruction));
				// 如果是红色的，就显示医生建议
				if (TextViewdigit43.getTextColors().getDefaultColor() == Color.RED) {
					setDoctorSuggestionTextColor(getResources().getString(
							R.string.Arrhythmia_suggest));
				} else {
					doctorSuggestion.setVisibility(View.GONE);
				}
				setItemBg(R.id.digit43);
				break;
			default:

			}
		}
	}
}