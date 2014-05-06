package com.bit_health.android.ui.adapter;

import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.util.TimeFormatUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类描述：ReportModuleListViewAdapter
 * 
 * 主要功能：报告模块中ListView的适配器 创建时间：2013.12.25
 * 
 * @author 梁才学
 **********************************************************************/
public class ReportModuleListViewAdapter extends BaseAdapter {

	private Context mActivity;
	private LayoutInflater inflater;
	private ViewHolder mViewHolder;

	private List<JsonBase> mMeasuresinfoes;

	public ReportModuleListViewAdapter(Context context, List<JsonBase> infoes) {
		// TODO Auto-generated constructor stub
		mActivity = context;
		inflater = LayoutInflater.from(context);
		mMeasuresinfoes = infoes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMeasuresinfoes.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.report_module_listview_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.name_text = (TextView) convertView
					.findViewById(R.id.name);
			mViewHolder.content_text = (TextView) convertView
					.findViewById(R.id.content);
			mViewHolder.value_text = (TextView) convertView
					.findViewById(R.id.value);
			mViewHolder.unit_text = (TextView) convertView
					.findViewById(R.id.unit);
			mViewHolder.stateImage = (ImageView) convertView
					.findViewById(R.id.state_image);
			mViewHolder.time_text = (TextView) convertView
					.findViewById(R.id.time);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		JsonBase measureinfo = mMeasuresinfoes.get(position);
		String timeStamp = "";
		String content = "";
		String tittle = "";
		String value = "";
		String unit = "";
		boolean bIsAbnormal = false;
		if (measureinfo instanceof EcgInfoBean) {
			EcgInfoBean ecgInfo = (EcgInfoBean) measureinfo;
			timeStamp = TimeFormatUtil.turnDateFormat(ecgInfo.mTimeStamp
					.substring(0, 8));
			timeStamp = timeStamp
					+ " "
					+ TimeFormatUtil.turnTimeFormat(ecgInfo.mTimeStamp
							.substring(8, 12));			
			timeStamp = TimeFormatUtil.ecgOrBppShowTime(timeStamp);
			
			if (!ecgInfo.bIsNormal) {
				bIsAbnormal = true;
			}
			tittle = mActivity.getResources().getString(
					R.string.title_xindian_report);
			content = "平均心率: ";
			value = "" + ecgInfo.mHeartRate;
			unit = "bpm";
		}
		if (measureinfo instanceof PpgInfoBean) {
			PpgInfoBean ppgInfo = (PpgInfoBean) measureinfo;

			timeStamp = TimeFormatUtil.turnDateFormat(ppgInfo.mTimeStamp
					.substring(0, 8));
			timeStamp = timeStamp
					+ " "
					+ TimeFormatUtil.turnTimeFormat(ppgInfo.mTimeStamp
							.substring(8, 12));			
			timeStamp = TimeFormatUtil.ecgOrBppShowTime(timeStamp);
			
			if (!ppgInfo.bIsNormal) {
				bIsAbnormal = true;
			}
			tittle = mActivity.getResources().getString(
					R.string.title_maibo_report);
			content = "平均脉率: ";
			value = "" + ppgInfo.mPulserate;// 平均脉率的数值
			unit = "bpm";
		}
		if (measureinfo instanceof BsInfoBean) {
			BsInfoBean bsInfo = (BsInfoBean) measureinfo;
			timeStamp = bsInfo.mTimeStamp;			
			timeStamp = TimeFormatUtil.bsOrBpShowTime(timeStamp);
			tittle = mActivity.getResources().getString(
					R.string.title_xuetang_report);
			content = "血糖: ";
			value = "" + bsInfo.mLimosis;
			unit = "mmol/L";
			if (!bsInfo.bIsNormal) {
				bIsAbnormal = true;
			}
		}
		if (measureinfo instanceof BpInfoBean) {
			BpInfoBean bpInfo = (BpInfoBean) measureinfo;
			timeStamp = bpInfo.mTimeStamp;
			timeStamp = TimeFormatUtil.bsOrBpShowTime(timeStamp);
			tittle = mActivity.getResources().getString(
					R.string.title_xueya_report);
			content = "血压: ";
			value = "" + bpInfo.mSleepSpb;
			unit = "mmHg";
			if (!bpInfo.bIsNormal) {
				bIsAbnormal = true;
			}
		}

//		// 因为第一根线超过了圆圈头部，所以需要将它的纵坐标设置一下
//		if (position == 0) {
//			float yValue = mActivity.getResources().getDimensionPixelSize (
//					R.dimen.line_image_set_Y);
//			mViewHolder.lineImage.setY(yValue);
//		} else {
//			mViewHolder.lineImage.setY(0);
//		}
		mViewHolder.name_text.setText(tittle);
		mViewHolder.content_text.setText(content);
		mViewHolder.value_text.setText(value);
		mViewHolder.unit_text.setText(unit);
		if (bIsAbnormal) {
			mViewHolder.stateImage.setImageResource(R.drawable.abnormal);
		} else {
			mViewHolder.stateImage.setImageResource(R.drawable.normal);
		}
		mViewHolder.time_text.setText(timeStamp);
		setListViewTextSize();
		return convertView;
	}

	// 设置listview的字体大小
	private void setListViewTextSize() {

		SharedPreferences preference = mActivity.getSharedPreferences(
				mActivity.getPackageName(), Context.MODE_PRIVATE);
		int currentIndex = preference.getInt("currentIndex", 0);

		if (currentIndex == 2) {// 大号字体
			mViewHolder.name_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			mViewHolder.content_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			mViewHolder.value_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			mViewHolder.unit_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			mViewHolder.time_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {// 中号字体
			mViewHolder.name_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			mViewHolder.content_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			mViewHolder.value_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			mViewHolder.unit_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			mViewHolder.time_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {// 小号字体
			mViewHolder.name_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			mViewHolder.content_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			mViewHolder.value_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			mViewHolder.unit_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			mViewHolder.time_text.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					mActivity.getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
		}
	}

	public static class ViewHolder {
		public TextView content_text;
		public TextView value_text;
		public TextView unit_text;
		public TextView name_text;
		public TextView time_text;
		public ImageView stateImage;
	}
}
