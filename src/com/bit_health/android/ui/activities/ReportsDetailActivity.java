package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.DataCatchInterface;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.ui.adapter.ContentModuleVP_Adapter;
import com.bit_health.android.ui.framelayout.ReportDetailOfMaibo;
import com.bit_health.android.ui.framelayout.ReportDetailOfXinDian;
import com.bit_health.android.ui.framelayout.ReportDetailOfXuetang;
import com.bit_health.android.ui.framelayout.ReportDetailOfXueya;
import com.bit_health.android.util.TimeFormatUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：ReportsDetailActivity
 * 
 * 主要功能：各种报告详情的 Activity
 * 
 * @author 梁才学 创建日期：2014.2.11
 **********************************************************************/
public class ReportsDetailActivity extends BaseActivity {

	private LayoutInflater inflater;
	private View view;
	private ImageView backoutImage;
	private TextView titleNameText;// 标题栏的文本
	private ViewPager mViewPager;
	private List<View> mViewPager_views = new ArrayList<View>();
	private ReportDetailOfXinDian ecgDetail;
	private ReportDetailOfXueya bpDetail;
	private ReportDetailOfXuetang bsDetail;
	private ReportDetailOfMaibo ppgDetail;
	private ContentModuleVP_Adapter mContentAdapter;
	private List<String> beanId;
	private List<String> beanType;
	private EcgInfoBean mEcgInfo;
	private BpInfoBean mBpInfo;
	private BsInfoBean mBsInfo;
	private PpgInfoBean mPpgInfo;
	private String mAnlasyResultType = null;
	private String mRoleid = null;

	private LinearLayout layoutBottomDot;
	public ImageView bottomIcon;
	private int lableIndex = 0;// 在一键测试中，标记第几个详情报告加入了mViewPager_views中
	private Handler handle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == 0) {

				if (lableIndex == 1) {
					mContentAdapter = new ContentModuleVP_Adapter(
							mViewPager_views);
					mViewPager.setAdapter(mContentAdapter);
					mViewPager
							.setOnPageChangeListener(new MyOnPageChangeListener());
				} else if (mViewPager_views.size() > 1
						&& lableIndex == mViewPager_views.size()) {
					mContentAdapter = new ContentModuleVP_Adapter(
							mViewPager_views);
					mViewPager.setAdapter(mContentAdapter);
					mViewPager
							.setOnPageChangeListener(new MyOnPageChangeListener());

					setCurrentPageView(0);
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		beanId = intent.getExtras().getStringArrayList("bean_id");
		beanType = intent.getExtras().getStringArrayList("bean_type");
		mAnlasyResultType = intent.getExtras().getString("anlasy_type");
		mRoleid = intent.getExtras().getString("role_id");

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.reports_detail, null);
		setContentView(view);

		initView();
		addChildViewForViewPager(beanId, beanType);
		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	private void initView() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.reports_detail_vp);
		layoutBottomDot = (LinearLayout) findViewById(R.id.detail_reports_bottom_dot);
		backoutImage = (ImageView) findViewById(R.id.reports_detail_backout);
		titleNameText = (TextView) findViewById(R.id.report_detail_title_name_text);

		MyViewListener listener = new MyViewListener();
		backoutImage.setOnClickListener(listener);
	}

	private String getCathTable(String measureType) {
		if (TextUtils.isEmpty(mAnlasyResultType)) {
			return null;
		}

		if (BusinessConst.AF_ANLASY_SUCCESS.equals(mAnlasyResultType)) {
			return DataCatchInterface.getInstance(this).getTableName(
					BusinessConst.ALL_MESURE, BusinessConst.AF_ANLASY_SUCCESS);
		}
		if (BusinessConst.ANLASY_SUCCESS_ABNORMAL.equals(mAnlasyResultType)) {
			return DataCatchInterface.getInstance(this).getTableName(
					BusinessConst.ALL_MESURE,
					BusinessConst.ANLASY_SUCCESS_ABNORMAL);
		}
		return DataCatchInterface.getInstance(this).getTableName(measureType,
				BusinessConst.ALL_ANLASY);
	}

	private JsonBase getDataFromCatch(String roleid, String table,
			String measureType, String id) {
		return DataCatchInterface.getInstance(this).getItemFromSid(roleid,
				table, id, measureType);
	}

	private JsonBase getDataFromCatch(String roleid, String table,
			String measureType, long time) {
		return DataCatchInterface.getInstance(this).getItemFromTime(roleid,
				table, time, measureType);
	}
	private void addViewForVP(JsonBase bean) {
		if (bean instanceof EcgInfoBean) {
			mEcgInfo = (EcgInfoBean) bean;
			if (ecgDetail == null) {
				ecgDetail = new ReportDetailOfXinDian(this);
			}
			ecgDetail.reportCreate(mEcgInfo, mRoleid);
			mViewPager_views.add(ecgDetail);
		}
		if (bean instanceof BpInfoBean) {
			mBpInfo = (BpInfoBean) bean;
			bpDetail.reportCreate(mBpInfo);
			mViewPager_views.add(bpDetail);
		}
		if (bean instanceof BsInfoBean) {
			mBsInfo = (BsInfoBean) bean;
			bsDetail.reportCreate(mBsInfo);
			mViewPager_views.add(bsDetail);
		}
		if (bean instanceof PpgInfoBean) {
			mPpgInfo = (PpgInfoBean) bean;
			if (ppgDetail == null) {
				ppgDetail = new ReportDetailOfMaibo(this);
			}
			ppgDetail.reportCreate(mPpgInfo, mRoleid);
			mViewPager_views.add(ppgDetail);
		}
		Message msg = new Message();
		msg.what = 0;
		handle.sendMessage(msg);
	}

	private void addChildViewForViewPager(List<String> beanIds,
			List<String> types) {
		// TODO Auto-generated method stub
		for (int i = 0; i < types.size(); i++) {
			lableIndex++;
			String table = getCathTable(types.get(i));
			if (BusinessConst.ECG_MESURE.equals(types.get(i))) {
				ecgDetail = new ReportDetailOfXinDian(this);
				if (!TextUtils.isEmpty(table)) {
					mEcgInfo = (EcgInfoBean) getDataFromCatch(mRoleid, table,
							types.get(i), beanIds.get(i));
					if (mEcgInfo != null && mEcgInfo.mStatus == 1
							&& mEcgInfo.mType == BusinessConst.DATA_DETAIL) {
						addViewForVP(mEcgInfo);
					} else {
						getInfoDataDetail(beanIds.get(i),
								BusinessConst.ECG_MESURE);
					}
				} else {
					getInfoDataDetail(beanIds.get(i), BusinessConst.ECG_MESURE);
				}
				titleNameText.setText("心电报告详情");

				if (types.size() > 1) {
					bottomIcon = (ImageView) inflater.inflate(
							R.layout.home_page_bottom_dot_image, null);
					bottomIcon.setEnabled(false);
					layoutBottomDot.addView(bottomIcon);
				}
				
				if(types.size() == 1){
					long  timeStamp = TimeFormatUtil.strToDateLong(mEcgInfo.mTimeStamp);
					mPpgInfo = (PpgInfoBean) getDataFromCatch(mRoleid, table,
							BusinessConst.PPG_MESURE,timeStamp);
					if (mPpgInfo != null && mPpgInfo.mStatus == 1
							&& mPpgInfo.mType == BusinessConst.DATA_DETAIL) {
						lableIndex++;
						addViewForVP(mPpgInfo);
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);
						// 两次
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);
						
					}else{
						if(mPpgInfo != null){
							lableIndex++;
							getInfoDataDetail(mPpgInfo.mId,
									BusinessConst.PPG_MESURE);
							bottomIcon = (ImageView) inflater.inflate(
									R.layout.home_page_bottom_dot_image, null);
							bottomIcon.setEnabled(false);
							layoutBottomDot.addView(bottomIcon);
							
							// 两次
							bottomIcon = (ImageView) inflater.inflate(
									R.layout.home_page_bottom_dot_image, null);
							bottomIcon.setEnabled(false);
							layoutBottomDot.addView(bottomIcon);
						}
					}
				}
			}
			if (BusinessConst.BP_MESURE.equals(types.get(i))) {
				bpDetail = new ReportDetailOfXueya(this);
				if (!TextUtils.isEmpty(table)) {
					mBpInfo = (BpInfoBean) getDataFromCatch(mRoleid, table,
							types.get(i), beanIds.get(i));
					if (mBpInfo != null
							&& mBpInfo.mType == BusinessConst.DATA_DETAIL) {
						addViewForVP(mBpInfo);
					} else {
						getInfoDataDetail(beanIds.get(i),
								BusinessConst.BP_MESURE);
					}
				} else {
					getInfoDataDetail(beanIds.get(i), BusinessConst.BP_MESURE);
				}
				titleNameText.setText("血压报告详情");
				if (types.size() > 1) {
					bottomIcon = (ImageView) inflater.inflate(
							R.layout.home_page_bottom_dot_image, null);
					bottomIcon.setEnabled(false);
					layoutBottomDot.addView(bottomIcon);
				}
			}
			if (BusinessConst.BS_MESURE.equals(types.get(i))) {
				bsDetail = new ReportDetailOfXuetang(this);
				if (!TextUtils.isEmpty(table)) {
					mBsInfo = (BsInfoBean) getDataFromCatch(mRoleid, table,
							types.get(i), beanIds.get(i));
					if (mBsInfo != null
							&& mBsInfo.mType == BusinessConst.DATA_DETAIL) {
						addViewForVP(mBsInfo);
					} else {
						getInfoDataDetail(beanIds.get(i),
								BusinessConst.BS_MESURE);
					}
				} else {
					getInfoDataDetail(beanIds.get(i), BusinessConst.BS_MESURE);
				}
				titleNameText.setText("血糖报告详情");
				if (types.size() > 1) {
					bottomIcon = (ImageView) inflater.inflate(
							R.layout.home_page_bottom_dot_image, null);
					bottomIcon.setEnabled(false);
					layoutBottomDot.addView(bottomIcon);
				}
			}
			if (BusinessConst.PPG_MESURE.equals(types.get(i))) {
				ppgDetail = new ReportDetailOfMaibo(this);
				if (!TextUtils.isEmpty(table)) {
					mPpgInfo = (PpgInfoBean) getDataFromCatch(mRoleid, table,
							types.get(i), beanIds.get(i));
					if (mPpgInfo != null && mPpgInfo.mStatus == 1
							&& mPpgInfo.mType == BusinessConst.DATA_DETAIL) {
						addViewForVP(mPpgInfo);
					} else {
						getInfoDataDetail(beanIds.get(i),
								BusinessConst.PPG_MESURE);
					}
				} else {
					getInfoDataDetail(beanIds.get(i), BusinessConst.PPG_MESURE);
				}
				titleNameText.setText("脉搏报告详情");
				if (types.size() > 1) {
					bottomIcon = (ImageView) inflater.inflate(
							R.layout.home_page_bottom_dot_image, null);
					bottomIcon.setEnabled(false);
					layoutBottomDot.addView(bottomIcon);
				}
				
				if(types.size() == 1){
					long  timeStamp = TimeFormatUtil.strToDateLong(mPpgInfo.mTimeStamp)+1;
//					if(mPpgInfo.mType == BusinessConst.DATA_DETAIL){
//						timeStamp = TimeFormatUtil.strToDateLong(mPpgInfo.mTimeStamp)+1;
//					}else{
//						timeStamp = TimeFormatUtil.strToDateLong(mPpgInfo.mTimeStamp);
//					}
					mEcgInfo = (EcgInfoBean) getDataFromCatch(mRoleid, table,
							BusinessConst.ECG_MESURE,timeStamp);
					if (mEcgInfo != null && mEcgInfo.mStatus == 1
							&& mEcgInfo.mType == BusinessConst.DATA_DETAIL) {
						addViewForVP(mEcgInfo);
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);
						
						// 两次
						bottomIcon = (ImageView) inflater.inflate(
								R.layout.home_page_bottom_dot_image, null);
						bottomIcon.setEnabled(false);
						layoutBottomDot.addView(bottomIcon);
					}else{
						if(mEcgInfo != null){
							getInfoDataDetail(mEcgInfo.mId,
									BusinessConst.ECG_MESURE);
							bottomIcon = (ImageView) inflater.inflate(
									R.layout.home_page_bottom_dot_image, null);
							bottomIcon.setEnabled(false);
							layoutBottomDot.addView(bottomIcon);
							
							// 两次
							bottomIcon = (ImageView) inflater.inflate(
									R.layout.home_page_bottom_dot_image, null);
							bottomIcon.setEnabled(false);
							layoutBottomDot.addView(bottomIcon);
						}
					}
				}
			}
		}
	}

	/***************************************************************
	 * 方法描述：根据id从服务器获取数据
	 * 
	 **************************************************************/
	private void getInfoDataDetail(String id, String type) {
		// TODO Auto-generated method stub
		showWaittingDialog("正在获取数据...");
		InterfaceService mBusiness = InterfaceService
				.getInstance(getApplicationContext());
		mBusiness.getMeasureDetail(id, type, this);
	}

	private void storeToCath(JsonBase bean) {
		String table = null;
		if (bean instanceof EcgInfoBean) {
			table = getCathTable(BusinessConst.ECG_MESURE);
		}
		
		if (bean instanceof PpgInfoBean) {
			table = getCathTable(BusinessConst.PPG_MESURE);
		}
		if (bean instanceof BpInfoBean) {
			table = getCathTable(BusinessConst.BP_MESURE);
		}
		if (bean instanceof BsInfoBean) {
			table = getCathTable(BusinessConst.BS_MESURE);
		}
		if (!TextUtils.isEmpty(table)) {
			DataCatchInterface.getInstance(this).updateData(mRoleid, table,
					bean);
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
			storeToCath(bean);
			addViewForVP(bean);
			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/******************************************************************
	 * 类描述：主要处理--监听ViewPager左右滑动的时候
	 * 
	 * @author 梁才学 创建日期：2013.12.19
	 ******************************************************************/
	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		/******************************************************************
		 * 方法描述：当页面滑动停止之后，设置标题栏显示相应的内容
		 * 
		 * @param arg0
		 *            = 0, 1, 2 即当前页的位置
		 ******************************************************************/
		public void onPageSelected(int arg0) {
			setCurrentPageView(arg0);
		}
	}

	/******************************************************************
	 * 方法描述：设置 ViewPager 标题栏显示的内容
	 * 
	 * @param arg0
	 *            = 0, 1, 2,3 即当前页的位置
	 ******************************************************************/
	private void setCurrentPageView(int arg0) {
		if (mViewPager_views.get(arg0) instanceof ReportDetailOfXinDian) {
			titleNameText.setText("心电报告详情");
		} else if (mViewPager_views.get(arg0) instanceof ReportDetailOfXueya) {
			titleNameText.setText("血压报告详情");
		} else if (mViewPager_views.get(arg0) instanceof ReportDetailOfXuetang) {
			titleNameText.setText("血糖报告详情");
		} else if (mViewPager_views.get(arg0) instanceof ReportDetailOfMaibo) {
			titleNameText.setText("脉搏报告详情");
		}

		// 设置底部圆点 start
		for (int i = 0; i < mViewPager_views.size(); i++) {
			ImageView image = (ImageView) layoutBottomDot.getChildAt(i);
			if (i == arg0) {
				image.setEnabled(true);
			} else {
				image.setEnabled(false);
			}
		}
		// 设置底部圆点 end
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.reports_detail_backout:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			default:

			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
