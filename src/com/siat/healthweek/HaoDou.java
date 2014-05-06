package com.siat.healthweek;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HaoDou extends Activity {

	/** Called when the activity is first created. */
	WebView webView;
	WebViewClient webViewClient;
	Context context;
	int screenHeight;
	int screenWidth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_haodou);

		context = this.getApplicationContext();

		webView = (WebView) findViewById(R.id.webview_haodou);
		webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
});
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://m.haodou.com/app/recipe/act/shenzhen.php");
	}
}
