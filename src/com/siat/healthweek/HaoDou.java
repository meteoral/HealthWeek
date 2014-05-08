package com.siat.healthweek;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
            @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
});
		webView.getSettings().setJavaScriptEnabled(true);
		if(isNetConnected()||is3gConnected()||isWifiConnected())
		{
			webView.loadUrl("http://m.haodou.com/app/recipe/act/shenzhen.php");
		}
		else{
			webView.loadUrl("file:///android_asset/shenzhen.html");
		}
	}
    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean isNetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo ni : infos) {
                    if (ni.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检测wifi是否连接
     *
     * @return
     */
    private boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测3G是否连接
     *
     * @return
     */
    private boolean is3gConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }
}
