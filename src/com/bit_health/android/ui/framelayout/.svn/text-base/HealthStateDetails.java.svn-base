package com.bit_health.android.ui.framelayout;

import java.util.ArrayList;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.beans.BpScoreBean;
import com.bit_health.android.controllers.beans.BsScoreBean;
import com.bit_health.android.controllers.beans.EcgScoreBean;
import com.bit_health.android.controllers.beans.PpgScoreBean;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.util.HeadImageUtil;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.SimpleTable;
import com.bit_health.android.util.TimeFormatUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：HealthStateDetails
 * 
 * 主要功能：健康状况的查看详情
 * 
 * @author 梁才学 创建日期：2014.4.1
 **********************************************************************/
public class HealthStateDetails extends BaseFramelayout {

	private Activity mActivity;
	private View view;
	private LayoutInflater inflater;
	private LinearLayout linearContainer;

	private String[] items;
	private ArrayList<String> dataList;
	private String scoreType;

	private ImageView headPhoto;
	private TextView fullName;
	private TextView userName;
	private TextView healthStateType;
	private TextView effectiveNum;
	private TextView uptateTime;

	public HealthStateDetails(String roleId, Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.health_state_details, this);
		linearContainer = (LinearLayout) view
				.findViewById(R.id.health_state_container_id);
		headPhoto = (ImageView) view.findViewById(R.id.health_state_detail_head);
		fullName = (TextView) view.findViewById(R.id.head_full_name);
		userName = (TextView) view.findViewById(R.id.head_user_name);
		healthStateType = (TextView) view.findViewById(R.id.health_state_type);
		effectiveNum = (TextView) view.findViewById(R.id.effective_num);
		uptateTime = (TextView) view.findViewById(R.id.uptate_time);
		
		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(mActivity);
		RoleInfoBean mRoleInfoBean = mRoleCatchInfo.getRole(roleId);
		if (mRoleInfoBean != null) {
			fullName.setText(mRoleInfoBean.mFullName);
			userName.setText(mRoleInfoBean.mName);
			Bitmap bmp = null;
			if ((bmp = HeadImageUtil.getHeadCatchImage(mRoleInfoBean.mId)) != null) {
				headPhoto.setImageBitmap(bmp);
			} else {
				headPhoto.setImageResource(HeadImageUtil
						.getLocalHeadimage(mRoleInfoBean.mFullName));
			}
		}
	}

	/******************************************************************
	 * 方法描述：ecg
	 * 
	 ******************************************************************/
	public void ecgHealthState(EcgScoreBean ecgBean) {

		healthStateType.setText("心电整体状况：" + stateOfScore(ecgBean.mScore));
		effectiveNum.setText("有效测量次数：" + String.valueOf(ecgBean.mEffectiveNum));
		uptateTime.setText("更新时间：" + TimeFormatUtil.turnSimpleTime(ecgBean.mUptateTm));
		
		items = new String[] { "first", "second" };
		initEcgDatas(ecgBean);
		SimpleTable simpleTable = new SimpleTable(mActivity, items, dataList);
		linearContainer.addView(simpleTable);
		setScoreType("EcgScoreBean");
	}

	/******************************************************************
	 * 方法描述：ppg
	 * 
	 ******************************************************************/
	public void ppgHealthState(PpgScoreBean ppgBean) {
		
		healthStateType.setText("脉搏整体状况：" + stateOfScore(ppgBean.mScore));
		effectiveNum.setText("有效测量次数：" + String.valueOf(ppgBean.mEffectiveNum));
		uptateTime.setText("更新时间：" + TimeFormatUtil.turnSimpleTime(ppgBean.mUptateTm));
		
		items = new String[] { "first", "second" };
		initPpgDatas(ppgBean);
		SimpleTable simpleTable = new SimpleTable(mActivity, items, dataList);
		linearContainer.addView(simpleTable);
		setScoreType("PpgScoreBean");
	}

	/******************************************************************
	 * 方法描述：bp
	 * 
	 ******************************************************************/
	public void bpHealthState(BpScoreBean bpBean) {
		
		healthStateType.setText("血压整体状况：" + stateOfScore(bpBean.mScore));
		effectiveNum.setText("有效测量次数：" + String.valueOf(bpBean.mEffectiveNum));
		uptateTime.setText("更新时间：" + TimeFormatUtil.turnSimpleTime(bpBean.mUptateTm));
				
		items = new String[] { "first", "second" };
		initBpDatas(bpBean);
		SimpleTable simpleTable = new SimpleTable(mActivity, items, dataList);
		linearContainer.addView(simpleTable);
		setScoreType("BpScoreBean");
	}

	/******************************************************************
	 * 方法描述：bs
	 * 
	 ******************************************************************/
	public void bsHealthState(BsScoreBean bsBean) {
		
		healthStateType.setText("血糖整体状况：" + stateOfScore(bsBean.mScore));
		effectiveNum.setText("有效测量次数：" + String.valueOf(bsBean.mEffectiveNum));
		uptateTime.setText("更新时间：" + TimeFormatUtil.turnSimpleTime(bsBean.mUptateTm));

		items = new String[] { "first", "second" };
		initBsDatas(bsBean);
		SimpleTable simpleTable = new SimpleTable(mActivity, items, dataList);
		linearContainer.addView(simpleTable);
		setScoreType("BsScoreBean");
	}

	/******************************************************************
	 * 方法描述：健康状况数据不足时
	 * 
	 ******************************************************************/
	public void noHealthData() {
		
	}

	private void initEcgDatas(EcgScoreBean ecgBean) {
		// TODO Auto-generated method stub
		dataList = new ArrayList<String>();		
		
		dataList.add("心动过速：" + ecgBean.mPolycardia);
		dataList.add("心动过缓：" + ecgBean.mBradycardia);
		dataList.add("阵发性心动过速：" + ecgBean.mVT);		
		dataList.add("二联律：" + ecgBean.mBigeminyNum);
		dataList.add("三联律：" + ecgBean.mTrigeminyNum);
		dataList.add("宽博：" + ecgBean.mWideNum);
		dataList.add("停搏：" + ecgBean.mArrestNum);
		dataList.add("漏搏：" + ecgBean.mMissedNum);
		dataList.add("室性早搏：" + ecgBean.mPVBNum);
		dataList.add("房性早搏：" + ecgBean.mAPBNum);
		dataList.add("插入性早搏：" + ecgBean.mInsertPVBnum);
		dataList.add("心律不齐：" + ecgBean.mArrhythmia);
	}

	private void initPpgDatas(PpgScoreBean ppgBean) {
		// TODO Auto-generated method stub
		dataList = new ArrayList<String>();

		dataList.add("心脏每搏射血量：" + ppgBean.mSVLevel);
		dataList.add("平均每分射血量：" + ppgBean.mCOLevel);
		dataList.add("血管顺应度：" + ppgBean.mACLevel);
		dataList.add("血液粘度：" + ppgBean.mVLevel);
		dataList.add("外周阻力：" + ppgBean.mTPRLevel);
		dataList.add("血氧饱和度：" + ppgBean.mSPOLevel);
		dataList.add("平均脉率：" + ppgBean.mPRLevel);
		dataList.add("心指数：" + ppgBean.mCILevel);
		dataList.add("心搏指数：" + ppgBean.mSPILevel);
	}

	private void initBpDatas(BpScoreBean bpBean) {
		// TODO Auto-generated method stub
		dataList = new ArrayList<String>();

		dataList.add("单纯收缩期高血压：" + bpBean.mISH);
		dataList.add("轻度高血压：" + bpBean.mMild_hypertension);
		dataList.add("中度高血压：" + bpBean.mModerate_hypertension);
		dataList.add("高度高血压：" + bpBean.mHighly_hypertension);
		dataList.add("低血压：" + bpBean.mHypotension);
	}

	private void initBsDatas(BsScoreBean bsBean) {
		// TODO Auto-generated method stub
		dataList = new ArrayList<String>();

		dataList.add("糖尿病：" + bsBean.mDiabetes);
		dataList.add("高血糖：" + bsBean.mHyperglycemia);
		dataList.add("低血糖：" + bsBean.mHypoglycemia);
	}

	private String stateOfScore(int score) {
		String state = null;

		if (score >= 90) {
			state = "优秀";
		} else if (score >= 75) {
			state = "良好";
		} else if (score >= 60) {
			state = "健康";
		} else if (score >= 40 && score < 60) {
			state = "亚健康";
		} else if (score >= 0 && score < 40) {
			state = "糟糕";
		} else {
			state = "数据不足！";
		}
		return state;
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

		}
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
}
