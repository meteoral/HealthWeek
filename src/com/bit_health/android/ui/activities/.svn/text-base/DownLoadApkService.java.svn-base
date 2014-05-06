package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.IProgressCallback;
import com.bit_health.android.controllers.IServerBusiness;
import com.bit_health.android.ui.fragment.MoreInfoFragment;
import com.bit_health.android.util.FileCatchConfigUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.os.Process;

/***********************************************************
 * 类描述： 下载新版本apk
 * 
 * @author 梁才学
 ***********************************************************/
public class DownLoadApkService extends Service implements IProgressCallback {

	private NotificationManager manager;
	private Notification notif;
	private String downloadApkUrl;
	private long refreshTime = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**********************************************************************
	 * 方法描述：创建服务
	 * 
	 **********************************************************************/
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	/**********************************************************************
	 * 方法描述：启动服务
	 * 
	 **********************************************************************/
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			downloadApkUrl = bundle
					.getString(MoreInfoFragment.DOWN_LOAD_APK_URL);
		}

		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notif = new Notification();
		notif.icon = R.drawable.ic_launcher;
		notif.tickerText = "正在下载 ";
		// 通知栏显示所用到的布局文件
		notif.contentView = new RemoteViews(getPackageName(),
				R.layout.content_view);
		manager.notify(0, notif);
		new DownLoadThread().start();

		return START_NOT_STICKY;
	}

	class DownLoadThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
			refreshTime = System.currentTimeMillis();
			
			String filePath = FileCatchConfigUtil.getCatchPath() + "bithealth.apk";;
			
			IServerBusiness.getInstance(
					DownLoadApkService.this.getApplicationContext())
					.DownLoadFile(downloadApkUrl, filePath,DownLoadApkService.this);
		}
	}

	@Override
	public void notifyProgressChanged(int pos, int total, String message) {
		// TODO Auto-generated method stub

		// 每0.5秒中刷新一次，如果刷得太频繁，会很卡
		if (System.currentTimeMillis() - refreshTime > 500 || pos == total) {

			int progressNum = (int) ((pos*100) / total);// 计算百分比

			notif.contentView.setProgressBar(R.id.content_view_progress, total,
					pos, false);
			notif.contentView.setTextViewText(R.id.content_view_text1,
					progressNum + "%");
			manager.notify(0, notif);

			refreshTime = System.currentTimeMillis();
		}

	}

	@Override
	public void uploadError(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downloadError(String message) {
		// TODO Auto-generated method stub

		notif.flags = Notification.FLAG_AUTO_CANCEL; // 设置点击通知栏后消失
		notif.contentView.setTextViewText(R.id.content_view_text1, "下载失败");
		manager.notify(0, notif);

		stopSelf();
	}

	@Override
	public void downloadSuccess(String localPath) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + localPath),
				"application/vnd.android.package-archive");
		PendingIntent pIntent = PendingIntent.getActivity(
				DownLoadApkService.this, 0, intent, 0);
		notif.contentIntent = pIntent;
		notif.flags = Notification.FLAG_AUTO_CANCEL; // 设置点击通知栏后消失

		notif.contentView.setTextViewText(R.id.content_view_text1, "下载完成(100%)");
		manager.notify(0, notif);

		// 下载完毕后自动弹出安装对话框界面
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		DownLoadApkService.this.startActivity(intent);

		stopSelf();
	}

	@Override
	public void uploadSuccess(String message, String ecgid, String ppgid) {
		// TODO Auto-generated method stub

	}

}
