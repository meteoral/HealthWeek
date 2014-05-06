package com.bit_health.android.device.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import com.bit_health.android.constants.DeviceConst;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;


public class ConnectionDevice {
	private BluetoothSocket mSocket;
	private BluetoothDevice mDevice;
	private String mDeviceName;

	static private ConnectionDevice instance;
	
	private ConnectionDevice(){
		
	}

	public BluetoothDevice getDevice() {
		return mDevice;
	}

	public void setDevice(BluetoothDevice device) {
		this.mDevice = device;
	}
	
	public boolean connect(BluetoothDevice device) {
		boolean conneted = false;
		int icount = 0;
		while (!conneted && icount < 6) {
			// 重连六次
			icount++;
			try {
				System.out.println("BoundState:" + mDevice.getBondState());
				Method m = getMethodBySdk(Build.VERSION.RELEASE);
				mSocket = (BluetoothSocket) m.invoke(mDevice,
						Integer.valueOf(1));
				mSocket.connect();
				conneted = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conneted;
	}
	/**
	 * 
	 * @param release
	 *            you can get it by {@link android.os.Build.VERSION#RELEASE}
	 * @return sdk > 2.3.3 return insecure method means no pairing dialog,else
	 *         return secure method means pairing dialog
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private Method getMethodBySdk(String release) throws SecurityException,
			NoSuchMethodException {
		String[] ss = release.split("\\.");
		StringBuilder sNum = new StringBuilder();

		for (int i = 0; i < ss.length; i++) {

			sNum.append(ss[i]);

		}

		if (sNum.length() == 2) {
			sNum.append("0");

		}

		int mSdkInt = Integer.parseInt(sNum.toString());
		Method m = null;

		if (mSdkInt < 233) {
			m = BluetoothDevice.class.getMethod("createRfcommSocket",
					new Class[] { int.class });
		} else {
			m = BluetoothDevice.class.getMethod("createInsecureRfcommSocket",
					new Class[] { int.class });
		}

		return m;
	}


	public void disconnectSocket() {
		if (mSocket != null) {
			try {
				mSocket.close();
				mSocket = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	static public ConnectionDevice getInstance() {
		if (instance == null) {
			instance = new ConnectionDevice();
		}
		return instance;
	}
	
	public int getDeviceCode() {
		if (mDeviceName == null) {
			return -1;
		}
		if (mDeviceName.startsWith("LR-ECG1")) {
			return 0;
		} else if (mDeviceName.startsWith("SIATECG")) {// 老三合一
			return 1;
		} else if (mDeviceName.startsWith(DeviceConst.BT_MINIHOLTER_NAME)
				|| mDeviceName.startsWith(DeviceConst.BT_MINIHOLTER_NAME_NEW)) {// miniholter
			return 2;
		} else if (mDeviceName.startsWith("SIAT3IN1E"))// 新三合一
		{
			return 3;
		}
		return -1;
	}

	public void setSocket(BluetoothSocket mSocket) {
		this.mSocket = mSocket;
	}

	public BluetoothSocket getSocket() {
		return this.mSocket;
	}
	public InputStream getInputStream() {
		if (mSocket == null) {
			return null;
		}
		try {
			return mSocket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public OutputStream getOutputStream() {
		if (mSocket == null) {
			return null;
		}
		try {
			return mSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getDeviceName() {
		return mDeviceName;
	}
	public void setDeviceName(String name) {
		mDeviceName = name;
	}

	public void shutDownConnection() {
		if (mSocket != null) {
			try {
				mSocket.close();
				mSocket = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
