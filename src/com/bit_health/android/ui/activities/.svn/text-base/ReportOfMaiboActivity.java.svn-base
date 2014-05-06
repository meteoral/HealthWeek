package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.constants.DeviceConst;
import com.bit_health.android.ui.adapter.ReportModule_Adapter;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;
import com.bit_health.android.ui.framelayout.HistoryRecords;
import com.bit_health.android.ui.framelayout.MaiBoReport;

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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**********************************************************************
 * 类名：ReportOfMaiboActivity
 * 
 * 主要功能：脉搏分析模块
 * 
 * @author 梁才学 创建日期：2014.2.19
 **********************************************************************/
public class ReportOfMaiboActivity extends ReportActivity {

	private View view;
	private TextView reportMaiboText;
	private TextView historyMaiboText;
	private LayoutInflater inflater;
	private MaiBoReport mMaiBoReport;

	// ViewPager 相关变量
	private ViewPager mViewPager;// 页卡内容
	private ReportModule_Adapter adapter;
	private List<View> mViewPager_views;// Tab页面列表
	private ImageView cursor_imageView;// 在选项卡中滑动的图片
	private int offset = 0;// 动画图片偏移量
	private int bmpWidth;// 动画图片宽度
	private int bmpWidthOfBackImage;// 标题栏返回的图片的宽度
	private int curIndex;
	private int displacement;// 页卡1 -> 页卡2 偏移量，即从1到2要滑动这么多的位移

	private LinearLayout mPpgTest;
//	private AndroidActivityMananger mMananger;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

//		mMananger = AndroidActivityMananger.getInstatnce();
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.report_main_of_maibo, null);
		setContentView(view);
		mMesureType = BusinessConst.PPG_MESURE;
		getDataFromService(STATUS_INIT);

		mPpgTest = (LinearLayout) view.findViewById(R.id.layout_ppg_test);
		mPpgTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 直接跳入三合一进行测试脉搏
				ArrayList<String> devicesNames = new ArrayList<String>();
				devicesNames.add(DeviceConst.BT_3IN1E_NAME);
			
				Intent intent = new Intent(ReportOfMaiboActivity.this.getApplicationContext(),
						TestXinDianActivity.class);
				intent.putStringArrayListExtra(TestXinDianActivity.FLAG_INTENT_DEVCE_NAME, devicesNames);
				ReportOfMaiboActivity.this.startActivity(intent);
				
			}
		});
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	/******************************************************************
	 * 方法描述：初始化 ViewPager
	 * 
	 * 
	 ******************************************************************/
	protected void initViewPager() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) view.findViewById(R.id.maibo_viewpager);
		mViewPager_views = new ArrayList<View>();
		mMaiBoReport = new MaiBoReport(this);
		mMaiBoReport.reportCreate(mInfos);
		mHistoryRecords = new HistoryRecords(this);
		mHistoryRecords.historyRecordsCreate(mInfos);
		mViewPager_views.add(mMaiBoReport);
		mViewPager_views.add(mHistoryRecords);
		adapter = new ReportModule_Adapter(mViewPager_views);
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		reportMaiboText = (TextView) view.findViewById(R.id.report_maibo_text);
		historyMaiboText = (TextView) view
				.findViewById(R.id.history_maibo_text);

		// 处理切换选项卡时滑动的图片
		cursor_imageView = (ImageView) view.findViewById(R.id.maibo_cursor);
		bmpWidth = BitmapFactory
				.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
		bmpWidthOfBackImage = BitmapFactory.decodeResource(getResources(),
				R.drawable.cancer_button).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 没有这语句横线图片就不滑动
		int screenW = dm.widthPixels;// 获取分辨率宽度
		int i = ((screenW - bmpWidthOfBackImage) / 2 - bmpWidth) / 2;
		offset = bmpWidthOfBackImage + i;// 计算偏移量，即横线图片开始的位置
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor_imageView.setImageMatrix(matrix);// 设置动画初始位置
		displacement = (screenW - bmpWidthOfBackImage) / 2;// 需要滑动的位移
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
		 * 方法描述：当页面滑动停止之后，横线图片开始滑动
		 * 
		 * @param arg0
		 *            = 0, 1, 2 即当前页的位置
		 ******************************************************************/
		public void onPageSelected(int arg0) {

			Animation animation = new TranslateAnimation(displacement
					* curIndex, displacement * arg0, 0, 0);
			curIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(150);
			cursor_imageView.startAnimation(animation);

			if (curIndex == 0) {
				reportMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						getResources().getDimensionPixelSize(R.dimen.interface_title));
				historyMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						getResources().getDimensionPixelSize(R.dimen.normal_textsize));

				reportMaiboText.setAlpha(1.0f);
				historyMaiboText.setAlpha(0.4f);
			} else if (curIndex == 1) {
				reportMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						getResources().getDimensionPixelSize(R.dimen.normal_textsize));
				historyMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						getResources().getDimensionPixelSize(R.dimen.interface_title));

				reportMaiboText.setAlpha(0.4f);
				historyMaiboText.setAlpha(1.0f);
			}
		}
	}

	/******************************************************************
	 * 方法描述：响应脉搏分析选项卡的点击，触发 ViewPager 切换界面
	 * 
	 * @param
	 ******************************************************************/
	public void onClickMaiboReport(View view) {
		mViewPager.setCurrentItem(0);

		reportMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimensionPixelSize(R.dimen.interface_title));
		historyMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimensionPixelSize(R.dimen.normal_textsize));

		reportMaiboText.setAlpha(1.0f);
		historyMaiboText.setAlpha(0.4f);
	}

	/******************************************************************
	 * 方法描述：响应历史记录选项卡的点击，触发 ViewPager 切换界面
	 * 
	 * @param
	 ******************************************************************/
	public void onClickMaiboRecords(View view) {
		mViewPager.setCurrentItem(1);

		reportMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimensionPixelSize(R.dimen.normal_textsize));
		historyMaiboText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				getResources().getDimensionPixelSize(R.dimen.interface_title));

		reportMaiboText.setAlpha(0.4f);
		historyMaiboText.setAlpha(1.0f);
	}

	/******************************************************************
	 * 方法描述：标题栏上的返回图标
	 * 
	 * @param
	 ******************************************************************/
	public void onClickBackImageOfMaibo(View view) {
		finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
