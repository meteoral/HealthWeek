package com.mobilehealth.sensibelbed;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.siat.healthweek.ClsUtils;

public class PairingRequest extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(
				"android.bluetooth.device.action.PAIRING_REQUEST")) {
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			try {
				ClsUtils.setPin(device.getClass(), device, "0000");
				ClsUtils.cancelPairingUserInput(device.getClass(), device);
				ClsUtils.setPairingConfirmation(device.getClass(), device,
						false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				ClsUtils.createBond(device.getClass(), device);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.i("FrameHealthCheck", device.getName() + " is connected!");
		}
	}
}
