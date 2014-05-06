package com.bit_health.android.ui.framelayout;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

/**********************************************************************
 * 类名：EcgChart
 * 
 * 主要功能：心电测试中的心电报告布局
 * 
 * @author 梁才学 创建日期：2013.12.9
 **********************************************************************/
public class EcgChart extends BaseFramelayout {
	private View frontPagebtn;// 上一页
	private View nextPagebtn;// 下一页
	private View layoutFirstPage;// 第一页
	private TextView textFirstPage;// 第一页
	private EditText editInput;// 输入框
	private View goIntoPageText;// 跳转
	private View layoutLastPage;// 最后一页
	private TextView textLastPage;// 最后一页
	private View layoutMiddleNumbersParts;// 中间那些数据部分的整体，用来控制这个整体是显示还是隐藏
	private View layoutShunshiText;// 瞬时心率图的底部文字说明
	private View layoutNoAbnormalText;// 没有异常时的情况

	// 所有ECG心电图
	public static final int TYPE_ALL_CHART = 0;

	// 异常心电图
	public static final int TYPE_ABNORMAL_CHART = 1;

	// 瞬时心率图
	public static final int TYPE_HR_ECG_CHART = 2;
	
	// 脉搏图
	public static final int TYPE_PPG_CHART = 3;
	
	// 瞬时脉搏图
	public static final int TYPE_HR_PPG_CHART = 4;

	private WebView mWebView;
	private int mChartType;
	private EcgInfoBean mEcgInfo;
	private PpgInfoBean mPpgInfo;

	private int mPossition = 0;

	private List<String> abNormalVaules = new ArrayList<String>();

	public EcgChart(Activity activity, int chartType, JsonBase bean) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
		if(bean instanceof EcgInfoBean){
			mEcgInfo = (EcgInfoBean)bean;
		}
		
		if(bean instanceof PpgInfoBean){
			mPpgInfo = (PpgInfoBean)bean;
		}
		
		mChartType = chartType;
	}
	
//	// 测试方法
//	public EcgChart(Activity activity) {
//		super(activity);
//		mActivity = activity;
//		initLayout();
//	}

	private void loadUrl(final String url) {
		if (mActivity != null && !mActivity.isFinishing()) {
			mActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mWebView.loadUrl(url);
				}
			});
		}
	}

	private void refreshBottomUi() {
		if (mActivity != null && !mActivity.isFinishing()) {
			mActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch (mChartType) {
					case TYPE_ABNORMAL_CHART:
						if (abNormalVaules.size() <= 1) {
							layoutMiddleNumbersParts.setVisibility(View.GONE);
						} else {
							editInput.setText(Integer.toString(mPossition + 1));
							textLastPage.setText(Integer
									.toString(abNormalVaules.size()));
						}
						break;
					case TYPE_ALL_CHART:
						layoutMiddleNumbersParts.setVisibility(View.VISIBLE);
						editInput.setText(Integer.toString(mPossition + 1));
						textLastPage.setText(Integer
								.toString(mEcgInfo.mEcgImgCount));
						break;
					case TYPE_HR_ECG_CHART:
						if (mEcgInfo.mHrImageCount <= 1) {
							layoutMiddleNumbersParts.setVisibility(View.GONE);
						} else {
							layoutMiddleNumbersParts
									.setVisibility(View.VISIBLE);
							editInput.setText(Integer.toString(mPossition + 1));
							textLastPage.setText(Integer
									.toString(mEcgInfo.mHrImageCount));
						}
						break;
					case TYPE_PPG_CHART:
						layoutMiddleNumbersParts.setVisibility(View.VISIBLE);
						editInput.setText(Integer.toString(mPossition + 1));
						textLastPage.setText(Integer
								.toString(mPpgInfo.mImageCount));
						break;
					case TYPE_HR_PPG_CHART:
						layoutMiddleNumbersParts.setVisibility(View.GONE);
						break;
					default:
						break;
					}
				}
			});
		}
	}

	private void desposeCallback(int retCode, String errorMsg, String url) {
		switch (retCode) {
		case 0:
			loadUrl(url);
			break;
		default:
			hideWaittingDialog();
			showAlert(errorMsg);
			break;
		}
		refreshBottomUi();
	}

	@Override
	public void getEcgHrImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		super.getEcgHrImageUrlCallback(retCode, errorMsg, imageUrl);
		desposeCallback(retCode, errorMsg, imageUrl);
	}

	@Override
	public void getEcgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		super.getEcgImageUrlCallback(retCode, errorMsg, imageUrl);
		desposeCallback(retCode, errorMsg, imageUrl);
	}

	@Override
	public void getABEcgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		super.getABEcgImageUrlCallback(retCode, errorMsg, imageUrl);
		desposeCallback(retCode, errorMsg, imageUrl);
	}

	@Override
	public void getPpgHrImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		super.getPpgHrImageUrlCallback(retCode, errorMsg, imageUrl);
		desposeCallback(retCode, errorMsg, imageUrl);
	}

	@Override
	public void getPpgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		super.getPpgImageUrlCallback(retCode, errorMsg, imageUrl);
		desposeCallback(retCode, errorMsg, imageUrl);
	}
	/******************************************************************
	 * 方法描述：创建界面
	 * 
	 ******************************************************************/
	public void reportCreate() {
		initLayout();
		if (mChartType == TYPE_PPG_CHART) {
			getPpgUrl(mPossition);
			layoutShunshiText.setVisibility(View.GONE);
		} else if (mChartType == TYPE_HR_PPG_CHART) {
			getHrPpgUrl();
			layoutShunshiText.setVisibility(View.VISIBLE);
		} else if (mChartType == TYPE_ALL_CHART) {
			getUrl(mPossition);
			layoutShunshiText.setVisibility(View.GONE);
		} else if (mChartType == TYPE_HR_ECG_CHART) {
			getHrEcgUrl(mPossition);
			layoutShunshiText.setVisibility(View.VISIBLE);
		} else if (mChartType == TYPE_ABNORMAL_CHART) {
			layoutShunshiText.setVisibility(View.GONE);
			if (mEcgInfo.mPolycardia > 0) {
				abNormalVaules.add(BusinessConst.ECG_Polycardia);
			}
			if (mEcgInfo.mBradycardia > 0) {
				abNormalVaules.add(BusinessConst.ECG_Bradycardia);
			}
			if (mEcgInfo.mVT > 0) {
				abNormalVaules.add(BusinessConst.ECG_VT);
			}
			if (mEcgInfo.mBigeminyNum > 0) {
				abNormalVaules.add(BusinessConst.ECG_Bigeminy);
			}
			if (mEcgInfo.mTrigeminyNum > 0) {
				abNormalVaules.add(BusinessConst.ECG_Trigeminy);
			}
			if (mEcgInfo.mWideNum > 0) {
				abNormalVaules.add(BusinessConst.ECG_Wide);
			}
			if (mEcgInfo.mArrestNum > 0) {
				abNormalVaules.add(BusinessConst.ECG_Arrest);
			}
			if (mEcgInfo.mMissedNum > 0) {
				abNormalVaules.add(BusinessConst.ECG_Missed);
			}
			if (mEcgInfo.mApbNum > 0) {
				abNormalVaules.add(BusinessConst.ECG_APB);
			}
			if (mEcgInfo.mPVBNum > 0) {
				abNormalVaules.add(BusinessConst.ECG_PVB);
			}
			if (mEcgInfo.mInsertPVBnum > 0) {
				abNormalVaules.add(BusinessConst.ECG_InsertPVB);
			}
			if (mEcgInfo.mArrhythmia > 0) {
				abNormalVaules.add(BusinessConst.ECG_Arrhythmia);
			}
			
			if(!mEcgInfo.bIsNormal){// 有异常的时候
				getAbUrl(mPossition);
				layoutNoAbnormalText.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
			}else{
				layoutNoAbnormalText.setVisibility(View.VISIBLE);
				mWebView.setVisibility(View.GONE);
				layoutMiddleNumbersParts.setVisibility(View.GONE);
			}
			
			
		}
	}

	private void getUrl(int pos) {
		InterfaceService.getInstance(mActivity).getEcgImageUrl(
				mEcgInfo.mEventId, pos, this);
		showWaittingDialog("加载中...");
	}

	private void getHrEcgUrl(int pos) {
		InterfaceService.getInstance(mActivity).getEcgHrImageUrl(
				mEcgInfo.mEventId, pos, this);
		showWaittingDialog("加载中...");
	}

	private void getAbUrl(int pos) {
		if (abNormalVaules.size() > 0) {
			InterfaceService.getInstance(mActivity).getABEcgImageUrl(
					mEcgInfo.mEventId, abNormalVaules.get(pos), this);
			showWaittingDialog("加载中...");
		} else {
			layoutMiddleNumbersParts.setVisibility(View.GONE);
		}
	}
	
	private void getPpgUrl(int pos) {
		InterfaceService.getInstance(mActivity).getPpgImageUrl(
				mPpgInfo.mEventId, pos, this);
		showWaittingDialog("加载中...");
	}

	private void getHrPpgUrl() {
		InterfaceService.getInstance(mActivity).getPpgHrImageUrl(
				mPpgInfo.mEventId, this);
		showWaittingDialog("加载中...");
	}

	/******************************************************************
	 * 方法描述：界面初始化
	 * 
	 ******************************************************************/
	private void initLayout() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.ecg_chart_framelayout, this);

		frontPagebtn = findViewById(R.id.layout_front_page);
		nextPagebtn = findViewById(R.id.layout_next_page);
		layoutFirstPage = findViewById(R.id.layout_first_page);
		textFirstPage = (TextView) findViewById(R.id.first_page_text);
		editInput = (EditText) findViewById(R.id.edit_input_id);
		goIntoPageText = findViewById(R.id.go_into_page_text);
		layoutLastPage = findViewById(R.id.layout_last_page);
		textLastPage = (TextView) findViewById(R.id.last_page_text);
		layoutMiddleNumbersParts = findViewById(R.id.layout_bottom_numbers);
		layoutNoAbnormalText = findViewById(R.id.layout_no_abnormal);
		layoutShunshiText = findViewById(R.id.layout_bottom_shunshi_text);
		
		if(mPpgInfo != null){
			((TextView)findViewById(R.id.show_alert_shunshi_id)).setText(R.string.ppg_hr_alert_string);
		}

		mWebView = (WebView) findViewById(R.id.ecg_chart_webview);

		MyViewListener listener = new MyViewListener();
		frontPagebtn.setOnClickListener(listener);
		nextPagebtn.setOnClickListener(listener);
		layoutFirstPage.setOnClickListener(listener);
		goIntoPageText.setOnClickListener(listener);
		layoutLastPage.setOnClickListener(listener);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setSupportZoom(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setBuiltInZoomControls(true);// support zoom
		webSettings.setPluginsEnabled(true);// support flash
		webSettings.setUseWideViewPort(true);//
		webSettings.setLoadWithOverviewMode(true);

		DisplayMetrics metrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;
		if (mDensity == 240) {
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		} else if (mDensity == 160) {
			webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
		} else if (mDensity == 120) {
			webSettings.setDefaultZoom(ZoomDensity.CLOSE);
		} else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		} else if (mDensity == DisplayMetrics.DENSITY_TV) {
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		}
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTouchIconUrl(WebView view, String url,
					boolean precomposed) {
				// TODO Auto-generated method stub
				super.onReceivedTouchIconUrl(view, url, precomposed);
			}

			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					hideWaittingDialog();
				}
				super.onProgressChanged(view, progress);
			}
		});

		// 监听软键盘上的"完成"按键
		editInput.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					goIntoChoicePage();
				}
				return false;
			}
		});
	}

	/*****************************************************************
	 * 方法描述：当点击"跳转"按钮，或者点击软键盘的"完成"按键时，进行心电图的跳转
	 * 
	 ****************************************************************/
	private void goIntoChoicePage(){
		int inputPos = Integer.valueOf(editInput.getText().toString());
		if (mChartType == TYPE_PPG_CHART) {
			if (inputPos < 1) {
				inputPos = 1;
			}
			if (inputPos > mPpgInfo.mImageCount) {
				inputPos = mPpgInfo.mImageCount;
			}
			editInput.setText(Integer.toString(inputPos));
			mPossition = inputPos - 1;
			getPpgUrl(mPossition);
		} else if (mChartType == TYPE_ALL_CHART) {
			if (inputPos < 1) {
				inputPos = 1;
			}
			if (inputPos > mEcgInfo.mEcgImgCount) {
				inputPos = mEcgInfo.mEcgImgCount;
			}
			editInput.setText(Integer.toString(inputPos));
			mPossition = inputPos - 1;
			getUrl(mPossition);
		} else if (mChartType == TYPE_HR_ECG_CHART) {
			if (inputPos < 1) {
				inputPos = 1;
			}
			if (inputPos > mEcgInfo.mHrImageCount) {
				inputPos = mEcgInfo.mHrImageCount;
			}
			editInput.setText(Integer.toString(inputPos));
			mPossition = inputPos - 1;
			getHrEcgUrl(mPossition);
		} else if (mChartType == TYPE_ABNORMAL_CHART) {
			if (inputPos < 1) {
				inputPos = 1;
			}
			if (inputPos > abNormalVaules.size()) {
				inputPos = abNormalVaules.size();
			}
			editInput.setText(Integer.toString(inputPos));
			mPossition = inputPos - 1;
			getAbUrl(mPossition);
		}
	}
	
	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.layout_front_page:
				if (mPossition == 0) {
					Toast.makeText(mActivity, "已经是第一页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					mPossition--;
					if (mChartType == TYPE_ALL_CHART) {
						getUrl(mPossition);
					} else if (mChartType == TYPE_HR_ECG_CHART) {
						getHrEcgUrl(mPossition);
					} else if (mChartType == TYPE_ABNORMAL_CHART) {
						getAbUrl(mPossition);
					} else if (mChartType == TYPE_PPG_CHART) {
						getPpgUrl(mPossition);
					}
					editInput.setText(Integer.toString(mPossition + 1));
				}
				break;

			case R.id.layout_next_page:
				if (mChartType == TYPE_PPG_CHART) {
					if (mPossition >= mPpgInfo.mImageCount - 1) {
						Toast.makeText(mActivity, "已经是最后一页了",
								Toast.LENGTH_SHORT).show();
					} else {
						mPossition++;
						getPpgUrl(mPossition);
						editInput.setText(Integer.toString(mPossition + 1));
					}
				} else if (mChartType == TYPE_ALL_CHART) {
					if (mPossition >= mEcgInfo.mEcgImgCount - 1) {
						Toast.makeText(mActivity, "已经是最后一页了",
								Toast.LENGTH_SHORT).show();
					} else {
						mPossition++;
						getUrl(mPossition);
						editInput.setText(Integer.toString(mPossition + 1));
					}
				} else if (mChartType == TYPE_HR_ECG_CHART) {
					if (mPossition >= mEcgInfo.mHrImageCount - 1) {
						Toast.makeText(mActivity, "已经是最后一页了",
								Toast.LENGTH_SHORT).show();
					} else {
						mPossition++;
						getHrEcgUrl(mPossition);
						editInput.setText(Integer.toString(mPossition + 1));
					}
				} else if (mChartType == TYPE_ABNORMAL_CHART) {
					if (mPossition >= abNormalVaules.size() - 1) {
						Toast.makeText(mActivity, "已经是最后一页了",
								Toast.LENGTH_SHORT).show();
					} else {
						mPossition++;
						getAbUrl(mPossition);
						editInput.setText(Integer.toString(mPossition + 1));
					}
				}
			case R.id.go_into_page_text:
				goIntoChoicePage();
				break;
			case R.id.layout_first_page:
				mPossition = 0;
				if (mChartType == TYPE_PPG_CHART) {
					editInput.setText(Integer.toString(mPossition + 1));
					getPpgUrl(mPossition);
				} else if (mChartType == TYPE_ALL_CHART) {
					editInput.setText(Integer.toString(mPossition + 1));
					getUrl(mPossition);
				} else if (mChartType == TYPE_HR_ECG_CHART) {
					editInput.setText(Integer.toString(mPossition + 1));
					getHrEcgUrl(mPossition);
				} else if (mChartType == TYPE_ABNORMAL_CHART) {
					editInput.setText(Integer.toString(mPossition + 1));
					getAbUrl(mPossition);
				}
				break;
			case R.id.layout_last_page:
				if (mChartType == TYPE_PPG_CHART) {
					mPossition = mPpgInfo.mImageCount - 1;
					editInput.setText(Integer.toString(mPossition + 1));
					getUrl(mPossition);
				} else if (mChartType == TYPE_ALL_CHART) {
					mPossition = mEcgInfo.mEcgImgCount - 1;
					editInput.setText(Integer.toString(mPossition + 1));
					getUrl(mPossition);
				} else if (mChartType == TYPE_HR_ECG_CHART) {
					mPossition = mEcgInfo.mHrImageCount - 1;
					editInput.setText(Integer.toString(mPossition + 1));
					getHrEcgUrl(mPossition);
				} else if (mChartType == TYPE_ABNORMAL_CHART) {
					mPossition = abNormalVaules.size() - 1;
					editInput.setText(Integer.toString(mPossition + 1));
					getAbUrl(mPossition);
				}
				break;
			default:

			}
		}
	}
}
