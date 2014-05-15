package com.mobilehealth.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneUtil {
	
	public static String[] getPhoneIdentifiers(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return null;
		}
		String IMEI = tm.getDeviceId();
		String TEL = tm.getLine1Number();
		String ICCID = tm.getSimSerialNumber();
		String IMSI = tm.getSubscriberId();
		return new String[] { IMEI, TEL, ICCID, IMSI };
	}
	
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return null;
		}
		return tm.getDeviceId();
	}
}
