package com.mobilehealth.sensibelbed;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.ClsUtils;
import com.siat.healthweek.R;

public class FragmentHealthCheckMainPage extends Fragment{
	private LinearLayout llBreathFreq;
	private BluetoothDevice device = null;
	private BluetoothAdapter btAdapt = null;
	private BroadcastReceiver ListenDevices = null;
	private BluetoothAcceptThread bluetoothAcceptThread = null;
	private BluetoothServerSocket mBThServerSocket = null;
	private BluetoothSocket mBThSocket = null;
	private PrintStream mPrintStream = null;
	private boolean isConnected = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能
		if(btAdapt.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
		{
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
			startActivityForResult(discoverableIntent, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
		}

		// 注册Receiver来获取蓝牙设备相关的结果
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		// 注册receiver监听配对
		ListenDevices = new PairingRequest();
		IntentFilter intent2 = new IntentFilter();
		intent2.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
		getActivity().registerReceiver(ListenDevices, intent2);
		bluetoothAcceptThread = new BluetoothAcceptThread(btAdapt, 29);
		bluetoothAcceptThread.start();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.page_health_check, container, false);

		//return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		init(view);

		super.onViewCreated(view, savedInstanceState);
	}

	private void init(View layout)
	{
		llBreathFreq = (LinearLayout) layout.findViewById(R.id.llBreathFreq);

		llBreathFreq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getParentFragment()).changeToPage(1);
			}
		});
	}

	public class PairingRequest extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					"android.bluetooth.device.action.PAIRING_REQUEST")) {
				device = intent
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
				Log.i("FrameHealthCheck", device.getName()+ " is connected!");
			}
		}
	}

	InputStream recvInputStream = null;
	private class BluetoothAcceptThread extends Thread {
		public BluetoothAcceptThread(BluetoothAdapter btAdapter, int port) {
			try {

				mBThServerSocket = ClsUtils.listenUsingRfcommOn(
						btAdapter.getClass(), btAdapter, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				mBThSocket = mBThServerSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				mBThServerSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Log.i("info", "it is connecting!");
			try {
				// mBufferReader = new BufferedReader(new
				// InputStreamReader(mBThSocket.getInputStream(),"utf8 "));
				recvInputStream = mBThSocket.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				mPrintStream = new PrintStream(mBThSocket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Message msg = new Message();
				msg.obj = "create recv thread...";
				mBThSocket = mBThServerSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			isConnected = true;
			Thread receiveThread = new recvThread();
			receiveThread.start();
			Thread sendThread = new sendThread();
			sendThread.start();
		}
	}
	private class recvThread extends Thread {
		public recvThread() {
			try {
				Message msg = new Message();
				msg.obj = "recv thread...";
				mBThSocket = mBThServerSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			byte[] buffer = new byte[1024 * 2];
			int bytes;
			while (isConnected) {
				try {
					// Read from the InputStream
					if ((bytes = recvInputStream.read(buffer, 0, 1024 * 2)) > 0) {
						byte[] buf_data = new byte[bytes];
						for (int i = 0; i < bytes; i++) {
							buf_data[i] = buffer[i];
						}
						String recvStr = new String(buf_data);
						//recvArrayList.add(recvStr);
						Log.i("info", "recvice---------!");
						Message msg = new Message();
						msg.obj = "recv:" + recvStr.toString().substring(0, 4)
								+ "...(" + recvStr.length() + ")";
					}
				} catch (IOException e) {
					try {
						isConnected = false;
						mBThSocket.close();
						recvInputStream.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				}
			}
		}
	}

	private class sendThread extends Thread {
		public sendThread() {
		}

		public void run() {
			byte[] buffer = "hello!this is a Android end!".getBytes();
			while (isConnected) {
				try {
					mPrintStream.write(buffer);
				} catch (IOException e) {
					isConnected = false;
					try {
						mBThSocket.close();
						mPrintStream.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					e.printStackTrace();
				}
				mPrintStream.flush();
				Log.i("info", "send---------!");
				// testBlueTooth.this.finish();
				Message msg = new Message();
				msg.obj = "hello!this is a Android end!";
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
