package com.siat.healthweek;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HealthPaper extends HaoDou {

	/** Called when the activity is first created. */
	WebView webView;
	WebViewClient webViewClient;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_haodou);
		webView = (WebView) findViewById(R.id.webview_haodou);
		webView.setWebViewClient(new WebViewClient(){
            @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
});
		webView.getSettings().setJavaScriptEnabled(true);
		if(isNetConnected()||is3gConnected()||isWifiConnected())
		{
			webView.loadUrl("http://m.haodou.com/app/recipe/topic/view.php?id=274695");
		}
		else{
			webView.loadUrl("file:///android_asset/health.html");
		}
	}
}
