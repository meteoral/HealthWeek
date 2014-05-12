package com.siat.healthweek;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

public class ClsUtils {

	/**
	 * ���豸��� �ο�Դ�룺platform/packages/apps/Settings.git
	 * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
	 */
	static public boolean createBond(Class<?> btClass, BluetoothDevice btDevice)
			throws Exception {
		Method createBondMethod = btClass.getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}

	/**
	 * ���豸������ �ο�Դ�룺platform/packages/apps/Settings.git
	 * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
	 */
	static public boolean removeBond(Class<?> btClass, BluetoothDevice btDevice)
			throws Exception {
		Method removeBondMethod = btClass.getMethod("removeBond");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
	static public int getServiceChannel(Class<?> btClass,ParcelUuid uuid) throws Exception {
		Method getServiceChannelMethod = btClass
				.getMethod("getServiceChannel");
		Integer returnValue = (Integer) getServiceChannelMethod.invoke(uuid);
		return returnValue.intValue();
	}

	static public boolean setPairingConfirmation(Class<?> btClass,
			BluetoothDevice btDevice, boolean confirm) throws Exception {
		Method setPairingConfirmationMethod = btClass
				.getMethod("setPairingConfirmation");
		Boolean returnValue = (Boolean) setPairingConfirmationMethod.invoke(
				btDevice, confirm);
		return returnValue.booleanValue();

	}

	static public boolean setPin(Class<?> btClass, BluetoothDevice btDevice,
			String str) throws Exception {
		try {
			Method removeBondMethod = btClass.getDeclaredMethod("setPin",
					new Class[] { byte[].class });
			Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,
					new Object[] { str.getBytes() });
			Log.e("returnValue", "" + returnValue);
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	// ȡ���û�����
	static public BluetoothServerSocket listenUsingRfcommOn(Class<?> btClass,
			BluetoothAdapter btAdapter, int port) throws Exception {
		Method listenMethod = btClass.getMethod("listenUsingRfcommOn",
				new Class[] { int.class });
		// cancelBondProcess()
		BluetoothServerSocket returnValue = (BluetoothServerSocket) listenMethod
				.invoke(btAdapter, new Object[] { port });
		return returnValue;
	}
	
	static public BluetoothSocket createRfcommSocket(Class<?> btClass, int port) throws Exception {
		Method createRfcommSocketMethod = btClass.getMethod("createRfcommSocket",new Class[] { int.class });
		// cancelBondProcess()
		BluetoothSocket returnValue = (BluetoothSocket) createRfcommSocketMethod
				.invoke(new Object[] { port });
		return returnValue;
	}
	
	static public boolean cancelPairingUserInput(Class<?> btClass,
			BluetoothDevice device) throws Exception {
		Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
		// cancelBondProcess()
		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
		return returnValue.booleanValue();
	}

	// ȡ�����
	static public boolean cancelBondProcess(Class<?> btClass,
			BluetoothDevice device) throws Exception {
		Method createBondMethod = btClass.getMethod("cancelBondProcess");
		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
		return returnValue.booleanValue();
	}

	/**
	 * 
	 * @param clsShow
	 */
	static public void printAllInform(Class<?> clsShow) {
		try {
			// ȡ�����з���
			Method[] hideMethod = clsShow.getMethods();
			int i = 0;
			for (; i < hideMethod.length; i++) {
				Log.e("method name", hideMethod[i].getName() + ";and the i is:"
						+ i);
			}
			// ȡ�����г���
			Field[] allFields = clsShow.getFields();
			for (i = 0; i < allFields.length; i++) {
				Log.e("Field name", allFields[i].getName());
			}
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static byte[] convertPinToBytes(Class<?> btClass,
			BluetoothDevice device, String string) {
		// TODO �Զ���ɵķ������
		Method convertPinToBytes = null;
		try {

			convertPinToBytes = btClass.getMethod("convertPinToBytes");
		} catch (SecurityException e) {
			// TODO �Զ���ɵ� catch ��
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO �Զ���ɵ� catch ��
			e.printStackTrace();
		}
		byte[] returnValue = null;
		try {
			returnValue = (byte[]) convertPinToBytes.invoke(device);
		} catch (IllegalArgumentException e) {
			// TODO �Զ���ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ���ɵ� catch ��
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO �Զ���ɵ� catch ��
			e.printStackTrace();
		}

		return returnValue;
	}
}