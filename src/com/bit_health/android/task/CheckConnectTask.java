package com.bit_health.android.task;

import com.bit_health.android.ui.fragment.ContentModuleFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**********************************************************************
 * 类名：CheckConnectTask
 * 
 * @author 梁才学 主要功能：检测网络连接的广播 创建日期：2014.3.19
 **********************************************************************/
public class CheckConnectTask extends BroadcastReceiver {

	private ContentModuleFragment mContentModuleFragment;

	public CheckConnectTask(ContentModuleFragment mContentModuleFragment) {
		// TODO Auto-generated constructor stub
		this.mContentModuleFragment = mContentModuleFragment;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// 这个做法是检查手机网络、WiFi网络，适合在手机上使用，但不适合在平板电脑上
		/*State wifiState = null;// wifi
		State mobileState = null;// 手机网络
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (wifiState != null && mobileState != null
				&& State.CONNECTED != wifiState
				&& State.CONNECTED == mobileState) {
			// 手机网络连接成功，即2G、3G网络
			mContentModuleFragment.checkConnection(true);

		} else if (wifiState != null && State.CONNECTED == wifiState) {
			// 无线网络连接成功，即WiFi
			mContentModuleFragment.checkConnection(true);

		} else if (wifiState != null && mobileState != null
				&& State.CONNECTED != wifiState
				&& State.CONNECTED != mobileState) {
			// 手机没有任何的网络
			mContentModuleFragment.checkConnection(false);
		}*/

		// 获取所有连接管理对象（包括对 Wi-Fi,net等连接的管理）
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			// 获取网络连接管理的对象
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			// 判断当前网络是否已经连接
			if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {// 已经连接				
				mContentModuleFragment.checkConnection(true);
			} else {// 未连接
				mContentModuleFragment.checkConnection(false);
			}
		}

	}
}
