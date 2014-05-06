package com.bit_health.android.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.siat.healthweek.R;

/**********************************************************************
 * 类名：TipsContentsFragment
 *
 * 主要功能：小贴士之内容模块
 *
 * @author 梁才学 创建日期：2014.2.8
 **********************************************************************/
public class TipsContentsFragment extends BaseFragment {

	public static final String ABOUT_ECG_URL = "http://210.75.252.106:4084/knowledge/list.php?type=ECG";
	public static final String ABOUT_PPG_URL = "http://210.75.252.106:4084/knowledge/list.php?type=PPG";
	public static final String ABOUT_BP_URL = "http://210.75.252.106:4084/knowledge/list.php?type=BP";
	public static final String ABOUT_BS_URL = "http://210.75.252.106:4084/knowledge/list.php?type=BS";

	private View view;
	private ImageView backIcon;
	private String titleName;
	private TextView layoutTtitleText;
	private WebView tipsWebView;
	private boolean isBackToTipsList = false;

	public TipsContentsFragment(String titleName) {
		this.titleName = titleName;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tips_contents_fragment, container,
				false);
		layoutTtitleText = (TextView) view
				.findViewById(R.id.tips_content_title_text);
		layoutTtitleText.setText(titleName);
		tipsWebView = (WebView) view.findViewById(R.id.tips_webView);
		tipsWebView.setWebViewClient(new TipsWebViewClient());// 设置Web视图
		tipsWebView.getSettings().setJavaScriptEnabled(true);// 设置WebView属性，能够执行Javascript脚本
		tipsWebView.getSettings().setPluginsEnabled(true);// 设置WebView可以播放Flash
		tipsWebView.getSettings().setSupportZoom(true);
		tipsWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		tipsWebView.getSettings().setBuiltInZoomControls(true);// support zoom
		tipsWebView.getSettings().setUseWideViewPort(true);//
		tipsWebView.getSettings().setLoadWithOverviewMode(true);

		if (titleName.equals("关于心电")) {
			tipsWebView.loadUrl(ABOUT_ECG_URL);
		} else if (titleName.equals("关于脉搏")) {
			tipsWebView.loadUrl(ABOUT_PPG_URL);
		} else if (titleName.equals("关于血压")) {
			tipsWebView.loadUrl(ABOUT_BP_URL);
		} else if (titleName.equals("关于血糖")) {
			tipsWebView.loadUrl(ABOUT_BS_URL);
		}
		backIcon = (ImageView) view.findViewById(R.id.tips_content_back_image);
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				exit();
			}
		});

		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private class TipsWebViewClient extends WebViewClient {

		/**********************************************************************
		 * 方法描述：点击浏览界面中的链接时，为了继续在WebView控件中显示网页， 而不跳到系统浏览器，需要重写这个方法
		 *
		 **********************************************************************/
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			isBackToTipsList = true;
			return true;
		}

		/**********************************************************************
		 * 方法描述：在页面加载开始时调用
		 *
		 * @param WebView
		 * @param String
		 *            当前网页的 url
		 * @param Bitmap
		 * @return 无
		 **********************************************************************/
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}

		/**********************************************************************
		 * 方法描述：在页面加载结束时调用
		 *
		 * @param WebView
		 * @param String
		 *            当前网页的 url
		 * @return 无
		 **********************************************************************/
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}
	}

	/**********************************************************************
	 * 方法描述：点击返回键，判断是退出当前界面，还是返回上一网页
	 *
	 **********************************************************************/
	public boolean exit() {
		if (isBackToTipsList) {
			isBackToTipsList = false;
			if (titleName.equals("关于心电")) {
				tipsWebView.loadUrl(ABOUT_ECG_URL);
			} else if (titleName.equals("关于脉搏")) {
				tipsWebView.loadUrl(ABOUT_PPG_URL);
			} else if (titleName.equals("关于血压")) {
				tipsWebView.loadUrl(ABOUT_BP_URL);
			} else if (titleName.equals("关于血糖")) {
				tipsWebView.loadUrl(ABOUT_BS_URL);
			}
		} else {
			getActivity().finish();
			getActivity().overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
		}
		return false;
	}
}
