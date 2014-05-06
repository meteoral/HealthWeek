/********************************************************
 * 类名：UsbUploadService.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午3:37:26
 ********************************************************/
package com.bit_health.android.device.usb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

/**
 * @author Administrator
 *
 */
public class UsbUploadService extends Service {
	public static final String INTENT_UPLOAD_DATA_ID = "INTENT_UPLOAD_DATA_ID";
	public static final String ACTION_START_USB_UPLOAD = "ACTION_START_USB_UPLOAD";
	public static final String ACTION_START_BLUETOOTH_UPLOAD = "ACTION_START_BLUETOOTH_UPLOAD";
	public static final String ACTION_START_HISTORY_UPLOAD = "ACTION_START_HISTORY_UPLOAD";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(intent != null){
			if(ACTION_START_USB_UPLOAD.equals(intent.getAction())){
				if(TextUtils.isEmpty(UsbUploadMeasureFile.checkSiatDevice())){
					stopSelf();
				}else{
					Intent intent1 = new Intent();  
					intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
					intent1.setClass(getApplicationContext(),UsbUploadMeasureFile.class);  
					startActivity(intent1);
				}
			}
			if(ACTION_START_BLUETOOTH_UPLOAD.equals(intent.getAction())){
				Intent intent1 = new Intent();  
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
				intent1.setClass(getApplicationContext(),BluetoothUploadMiniholterEcg.class);  
				startActivity(intent1);
			}
			if(ACTION_START_HISTORY_UPLOAD.equals(intent.getAction())){
				Intent intent1 = new Intent();  
				if(intent.getExtras() != null){
					int dataid = intent.getExtras().getInt(INTENT_UPLOAD_DATA_ID);
					if(dataid != -1){
						intent1.putExtra(HistoryUploadFile.INTENT_DATA_ID, dataid);
					}
				}
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
				intent1.setClass(getApplicationContext(),HistoryUploadFile.class);  
				startActivity(intent1);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
