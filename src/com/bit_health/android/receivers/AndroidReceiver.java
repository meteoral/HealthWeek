package com.bit_health.android.receivers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bit_health.android.device.usb.UsbUploadService;

public class AndroidReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if (arg1 != null
				&& Intent.ACTION_MEDIA_MOUNTED.equals(arg1.getAction())) {
			Intent intent = new Intent(arg0.getApplicationContext(),
					UsbUploadService.class);
			intent.setAction(UsbUploadService.ACTION_START_USB_UPLOAD);
			arg0.startService(intent);
		}
	}
}
