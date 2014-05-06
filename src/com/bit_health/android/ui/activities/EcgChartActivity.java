package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.ui.adapter.ReportModule_Adapter;
import com.bit_health.android.ui.framelayout.EcgChart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：EcgChartActivity
 * 
 * 主要功能：心电报告详情中的心电图
 * 
 * @author 梁才学 创建日期：2014.2.9
 **********************************************************************/
public class EcgChartActivity extends BaseActivity {

	public static final String INTENT_PPG_INFO = "INTENT_PPG_INFO";
	public static final String INTENT_ECG_INFO = "INTENT_ECG_INFO";
	public static final String CURRENT_PAGE = "CURRENT_PAGE";
	
	private View view;
	private LayoutInflater inflater;

	private EcgChart mPpgChart;// 脉搏图
	private EcgChart mShunshiPpgChart;// 脉搏图
	private EcgChart mEcgChart;// 心电图
	private EcgChart mAbnormalEcgChart;// 异常心电图
	private EcgChart mShunshiEcgChart;// 瞬时心率图

	// ViewPager 相关变量
	private ViewPager mViewPager;// 页卡内容
	private ReportModule_Adapter adapter;
	private List<View> mViewPager_views = new ArrayList<View>();;// Tab页面列表
	private ImageView cursor_imageView;// 在选项卡中滑动的图片
	private int offset = 0;// 动画图片偏移量
	private int bmpWidth;// 动画图片宽度
	private int bmpWidthOfBackImage;// 标题栏返回的图片的宽度
	private int curIndex;
	private int displacement;// 页卡1 -> 页卡2 偏移量，即从1到2要滑动这么多的位移

	private EcgInfoBean mEcgInfo;
	private PpgInfoBean mPpgInfo;
	private int currentPage;
	private int screenW;// 屏幕分辨率宽度

	private LinearLayout layoutTitleContainer;
	private View layoutEcgChart;
	private View layoutAbnormalEcgChart;
	private View layoutShunshiEcgChart;

	private List<TextView> titleTextView = new ArrayList<TextView>();
	private MyViewListener listener = new MyViewListener();;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.ecg_chart, null);
		setContentView(view);

		mViewPager = (ViewPager) view.findViewById(R.id.ecg_chart_viewpager);
		layoutTitleContainer = (LinearLayout) view
				.findViewById(R.id.layout_title_container);
		
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			mEcgInfo = (EcgInfoBean) intent.getExtras().getSerializable(
					INTENT_ECG_INFO);
			mPpgInfo = (PpgInfoBean) intent.getExtras().getSerializable(
					INTENT_PPG_INFO);
			if(mEcgInfo != null){
				currentPage = intent.getExtras().getInt(CURRENT_PAGE);
				ecgAddItem();		
				ecgViewPager();
				setCursorImage();	
				ecgCursorImage();
				setViewPager();
				
				layoutEcgChart.setOnClickListener(listener);
				layoutAbnormalEcgChart.setOnClickListener(listener);
				layoutShunshiEcgChart.setOnClickListener(listener);
			}
			if(mPpgInfo != null){
				currentPage = intent.getExtras().getInt(CURRENT_PAGE);
				ppgAddItem();		
				ppgViewPager();
				setCursorImage();
				ppgCursorImage();
				setViewPager();
				
				layoutEcgChart.setOnClickListener(listener);
				layoutAbnormalEcgChart.setOnClickListener(listener);
			}			
		}
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}
	
	private void setViewPager(){
		adapter = new ReportModule_Adapter(mViewPager_views);
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(currentPage);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		setCurrentPageView(currentPage);		
	}

	/******************************************************************
	 * 方法描述：关于心电图的ViewPager
	 ******************************************************************/
	private void ecgViewPager() {
		mEcgChart = new EcgChart(this, EcgChart.TYPE_ALL_CHART, mEcgInfo);
		mAbnormalEcgChart = new EcgChart(this, EcgChart.TYPE_ABNORMAL_CHART,
				mEcgInfo);
		mShunshiEcgChart = new EcgChart(this, EcgChart.TYPE_HR_ECG_CHART,
				mEcgInfo);
		mViewPager_views.add(mEcgChart);
		mViewPager_views.add(mAbnormalEcgChart);
		mViewPager_views.add(mShunshiEcgChart);
		mEcgChart.reportCreate();
		mAbnormalEcgChart.reportCreate();
		mShunshiEcgChart.reportCreate();
	}
	
	/******************************************************************
	 * 方法描述：关于脉搏图的ViewPager
	 ******************************************************************/
	private void ppgViewPager() {
		mPpgChart = new EcgChart(this,EcgChart.TYPE_PPG_CHART,mPpgInfo);
		mShunshiPpgChart = new EcgChart(this,EcgChart.TYPE_HR_PPG_CHART,mPpgInfo);
		mViewPager_views.add(mPpgChart);
		mViewPager_views.add(mShunshiPpgChart);
		mPpgChart.reportCreate();
		mShunshiPpgChart.reportCreate();
	}
	
	private void ecgAddItem() {
		titleTextView.clear();
		layoutEcgChart = addItem("心电图");
		layoutAbnormalEcgChart = addItem("异常心电图");		
		layoutShunshiEcgChart = addItem("瞬时心率图");
	}
	
	private View addItem(String titleName){
		View view = inflater.inflate(R.layout.viewpager_item_page, null);
		((TextView) view.findViewById(R.id.title_name)).setText(titleName);
		titleTextView.add((TextView) view.findViewById(R.id.title_name));
		layoutTitleContainer.addView(view);	
		return view;
	}

	/******************************************************************
	 * 方法描述： 处理切换选项卡时滑动的图片
	 ******************************************************************/
	private void setCursorImage() {
		cursor_imageView = (ImageView) view.findViewById(R.id.ecg_cursor);
		bmpWidth = BitmapFactory
				.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
		bmpWidthOfBackImage = BitmapFactory.decodeResource(getResources(),
				R.drawable.cancer_button).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 没有这语句横线图片就不滑动
		screenW = dm.widthPixels;// 获取分辨率宽度
	}

	/******************************************************************
	 * 方法描述： 关于心电图的选项卡滑动图片
	 ******************************************************************/
	private void ecgCursorImage() {
		int i = ((screenW - bmpWidthOfBackImage) / 3 - bmpWidth) / 2;
		offset = bmpWidthOfBackImage + i;// 计算偏移量，即横线图片开始的位置
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor_imageView.setImageMatrix(matrix);// 设置动画初始位置
		displacement = (screenW - bmpWidthOfBackImage) / 3;// 需要滑动的位移

		layoutEcgChart.setLayoutParams(new LinearLayout.LayoutParams(
				displacement, LayoutParams.WRAP_CONTENT));
		layoutAbnormalEcgChart.setLayoutParams(new LinearLayout.LayoutParams(
				displacement, LayoutParams.WRAP_CONTENT));
		layoutShunshiEcgChart.setLayoutParams(new LinearLayout.LayoutParams(
				displacement, LayoutParams.WRAP_CONTENT));
	}

	/******************************************************************
	 * 方法描述：关于脉搏图添加的项
	 ******************************************************************/
	private void ppgAddItem() {
		titleTextView.clear();		
		layoutEcgChart = addItem("脉搏图");
		layoutAbnormalEcgChart = addItem("瞬时脉率图");
	}

	/******************************************************************
	 * 方法描述： 关于脉搏图的选项卡滑动图片
	 ******************************************************************/
	private void ppgCursorImage() {
		int i = ((screenW - bmpWidthOfBackImage) / 2 - bmpWidth) / 2;
		offset = bmpWidthOfBackImage + i;// 计算偏移量，即横线图片开始的位置
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor_imageView.setImageMatrix(matrix);// 设置动画初始位置
		displacement = (screenW - bmpWidthOfBackImage) / 2;// 需要滑动的位移

		layoutEcgChart.setLayoutParams(new LinearLayout.LayoutParams(
				displacement, LayoutParams.WRAP_CONTENT));
		layoutAbnormalEcgChart.setLayoutParams(new LinearLayout.LayoutParams(
				displacement, LayoutParams.WRAP_CONTENT));
	}

	/******************************************************************
	 * 类描述：主要处理--监听ViewPager左右滑动的时候
	 * 
	 * @author 梁才学 创建日期：2013.12.19
	 ******************************************************************/
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		/******************************************************************
		 * 方法描述：当页面滑动停止之后，横线图片开始滑动
		 * 
		 * @param arg0
		 *            = 0, 1, 2 即当前页的位置
		 ******************************************************************/
		@Override
		public void onPageSelected(int arg0) {
			setCurrentPageView(arg0);
		}
	}

	/******************************************************************
	 * 方法描述：设置 ViewPager 当前页的一些view
	 * 
	 * @param arg0
	 *            = 0, 1, 2 即当前页的位置
	 ******************************************************************/
	private void setCurrentPageView(int arg0) {

		Animation animation = new TranslateAnimation(displacement * curIndex,
				displacement * arg0, 0, 0);
		curIndex = arg0;
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(150);
		cursor_imageView.startAnimation(animation);

		for (int i = 0; i < titleTextView.size(); i++) {
			if (i == curIndex) {
				titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
						getResources().getDimensionPixelSize(R.dimen.interface_title));
				titleTextView.get(i).setAlpha(1.0f);
			} else {
				titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
						getResources().getDimensionPixelSize(R.dimen.normal_textsize));
				titleTextView.get(i).setAlpha(0.4f);
			}
		}
	}

	/******************************************************************
	 * 方法描述：响应左上角图表的点击，关闭当前界面
	 * 
	 * @param
	 ******************************************************************/
	public void onClickBack(View view) {
		finish();
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (layoutEcgChart == v) {
				for (int i = 0; i < titleTextView.size(); i++) {
					if (i == 0) {
						mViewPager.setCurrentItem(i);
						titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								getResources().getDimensionPixelSize(R.dimen.interface_title));
						titleTextView.get(i).setAlpha(1.0f);
					} else {
						titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								getResources().getDimensionPixelSize(R.dimen.normal_textsize));
						titleTextView.get(i).setAlpha(0.4f);
					}
				}
			} else if (layoutAbnormalEcgChart == v) {
				for (int i = 0; i < titleTextView.size(); i++) {
					if (i == 1) {
						mViewPager.setCurrentItem(i);
						titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								getResources().getDimensionPixelSize(R.dimen.interface_title));
						titleTextView.get(i).setAlpha(1.0f);
					} else {
						titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								getResources().getDimensionPixelSize(R.dimen.normal_textsize));
						titleTextView.get(i).setAlpha(0.4f);
					}
				}

			} else if (layoutShunshiEcgChart == v) {
				mViewPager.setCurrentItem(2);
				for (int i = 0; i < titleTextView.size(); i++) {
					if (i == 2) {
						mViewPager.setCurrentItem(i);
						titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								getResources().getDimensionPixelSize(R.dimen.interface_title));
						titleTextView.get(i).setAlpha(1.0f);
					} else {
						titleTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								getResources().getDimensionPixelSize(R.dimen.normal_textsize));
						titleTextView.get(i).setAlpha(0.4f);
					}
				}
			}
		}
	}
}
