package com.bit_health.android.device.bluetooth.ui;

import android.bluetooth.BluetoothDevice;

import com.bit_health.android.constants.AndroidDeviceConst;

public class BluetoothData {
	private static BluetoothData mData;

	// ecgppgview data
	static byte ecgDis = 2;
	static double ppgDis = 0.4;
	public static int SCREEN_WIDTH = AndroidDeviceConst.mScreenHeight > AndroidDeviceConst.mScreenWidth ? AndroidDeviceConst.mScreenHeight
			: AndroidDeviceConst.mScreenWidth;
	// 波形长度
	public static int ECG_POINT_LEN = SCREEN_WIDTH * ecgDis;
	public static int PPG_POINT_LEN = (int) (SCREEN_WIDTH * ppgDis);

	public int ecg_index = 0;
	public int ppg_index = 0;
	// 波形纵横坐标数组
	public float[] ecgView = new float[ECG_POINT_LEN];
	public float[] ecgData = new float[ECG_POINT_LEN];
	public float[] ppg_x = new float[PPG_POINT_LEN];
	public float[] ppgData = new float[PPG_POINT_LEN];
	public int mEcgppgMaxlen = 250;
	public float mfScale = 1;
	// end

	// EcgPpgAcvity data
	// 平滑用
	public int[] ecgtemp = new int[ECG_POINT_LEN];
	public int[] ppgtemp = new int[PPG_POINT_LEN];

	// 数据缓存数组和下标
	public int[] ECGbuffer = new int[ECG_POINT_LEN];
	public int[] PPGbuffer = new int[PPG_POINT_LEN];

	public int scale = 6;
	public long startTime = 0;
	public long endTime = 0;
	public int mCountPreSave = -1;
	public int mStoreMaxTime = 0;
	public boolean bIsScale = false;
	public boolean bIsComfirm = true;
	// end

	public BluetoothDevice mDevice;

	private BluetoothData() {
		// TODO Auto-generated constructor stub
	}

	public static BluetoothData getInsetance() {
		if (mData == null) {
			mData = new BluetoothData();
		}
		return mData;
	}

	public static void destroy() {
		mData = null;
	}

}
