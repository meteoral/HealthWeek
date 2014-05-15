package com.mobilehealth.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {
	
	private static String sharedPrefName = "com.mobilehealth.preferences";

	public static void setPrefName(String name) {
		SharedPrefUtil.sharedPrefName = name;
	}

	public static String getPrefName() {
		return SharedPrefUtil.sharedPrefName;
	}

	public static String[] readoutValues(Context context, String[] keys) {
		SharedPreferences pre = context.getSharedPreferences(SharedPrefUtil.sharedPrefName, Context.MODE_PRIVATE);
		int size = keys.length;
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			ret[i] = pre.getString(keys[i], null);
		}
		return ret;
	}

	public static void writeValues(Context context, String[] keys,String[] values) {
		SharedPreferences.Editor preEditor = context.getSharedPreferences(SharedPrefUtil.sharedPrefName, Context.MODE_PRIVATE).edit();
		int size = keys.length;
		for (int i = 0; i < size; i++) {
			if (values[i] != null) {
				preEditor.putString(keys[i], values[i]);
			}
		}
		preEditor.commit();
	}

	public static boolean checkDataExist(Context context, String data_key) {
		SharedPreferences pre = context.getSharedPreferences(SharedPrefUtil.sharedPrefName, Context.MODE_PRIVATE);
		return pre.contains(data_key);
	}

	public static boolean checkDataValue(Context context, String data_key, String value) {
		SharedPreferences pref = context.getSharedPreferences(SharedPrefUtil.sharedPrefName, Context.MODE_PRIVATE);
		String result = pref.getString(data_key, null);
		if (result != null && result.equals(value) == true) {
			return true;
		} else {
			return false;
		}
	}
}
