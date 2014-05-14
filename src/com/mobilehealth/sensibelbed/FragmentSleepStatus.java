package com.mobilehealth.sensibelbed;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.ClsUtils;
import com.siat.healthweek.R;

public class FragmentSleepStatus extends FragmentChildPage{

	private Timer timer = new Timer();
	private static final boolean D = true;
	private static final String TAG = "FragmentSleepStatus";
	private TimerTask task;
	private Handler handler;
	private Handler updateUI;
	private boolean isConnected = false;
	private TextView bodytext;
	public TextView bodytextShow;
	private String bodyInt;
	private TextView breathtext;
	public TextView breathtextShow;
	private String breathInt;
	private TextView heartstext;
	public TextView heartstextShow;
	private String heartsInt;
	private LinearLayout bodyTextlayout;
	private LinearLayout breathTextlayout;
	private LinearLayout heartsTextlayout;
	private LinearLayout linearView;
	private LayoutParams layout;

	private BluetoothDevice device = null;
	private BluetoothAdapter btAdapt = null;
	private BroadcastReceiver ListenDevices = null;
	private BluetoothAcceptThread bluetoothAcceptThread = null;
	private BluetoothServerSocket mBThServerSocket = null;
	private BluetoothSocket mBThSocket = null;
	private PrintStream mPrintStream = null;
	public FragmentSleepStatus() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.page_sleep_status;
	}

	@SuppressLint("HandlerLeak")
	@Override
	protected void init(View view) {
		// TODO Auto-generated method stub
		Context btContext = getActivity().getApplicationContext();

		LayoutParams textLayoutParamsOne = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		LayoutParams textLayoutParamsTwo = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		layout = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.weight = 1;
		textLayoutParamsOne.weight = 1;
		textLayoutParamsTwo.weight = 4;
		linearView = (LinearLayout) view.findViewById(R.id.chart_show);

		bodyTextlayout = new LinearLayout(btContext);
		bodyTextlayout.setOrientation(LinearLayout.VERTICAL);

		heartsTextlayout = new LinearLayout(btContext);
		heartsTextlayout.setOrientation(LinearLayout.VERTICAL);

		breathTextlayout = new LinearLayout(btContext);
		breathTextlayout.setOrientation(LinearLayout.VERTICAL);

		bodytext = new TextView(btContext);
		bodytext.setText("体动");
		bodytextShow = new TextView(btContext);
		bodytextShow.setText("");
		bodytextShow.setTextSize(42);
		bodytextShow.setTextColor(Color.RED);

		breathtext = new TextView(btContext);
		breathtext.setText("呼吸");
		breathtextShow = new TextView(btContext);
		breathtextShow.setText("");
		breathtextShow.setTextSize(42);
		breathtextShow.setTextColor(Color.CYAN);

		heartstext = new TextView(btContext);
		heartstext.setText("心跳脉搏");
		heartstextShow = new TextView(btContext);
		heartstextShow.setText("");
		heartstextShow.setTextSize(42);
		heartstextShow.setTextColor(Color.GREEN);

		bodyTextlayout.addView(bodytext, textLayoutParamsOne);
		bodyTextlayout.addView(bodytextShow, textLayoutParamsTwo);

		breathTextlayout.addView(breathtext, textLayoutParamsOne);
		breathTextlayout.addView(breathtextShow, textLayoutParamsTwo);

		heartsTextlayout.addView(heartstext, textLayoutParamsOne);
		heartsTextlayout.addView(heartstextShow, textLayoutParamsTwo);

		btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能
		if (btAdapt.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
			startActivityForResult(discoverableIntent,
					BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
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

		if (!isConnected) {
			linearView.removeAllViews();
			TextView alert = new TextView(btContext);
			alert.setText("等待电脑端数据传输！");
			alert.setTextSize(60);
			alert.setTextColor(Color.RED);
			ImageView alertImg = new ImageView(btContext);
			alertImg.setImageResource(R.drawable.bluetooth_status_right);
			linearView.addView(alertImg);
			linearView.addView(alert);
		} else {
			linearView.removeAllViews();
			linearView.addView(bodyTextlayout, layout);
			linearView.addView(breathTextlayout, layout);
			linearView.addView(heartsTextlayout, layout);
		}
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 刷新图表
				bodytextShow.setText(bodyInt);
				heartstextShow.setText(heartsInt);
				breathtextShow.setText(breathInt);
				super.handleMessage(msg);
			}
		};
		updateUI = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				linearView.removeAllViews();
				linearView.addView(bodyTextlayout, layout);
				linearView.addView(breathTextlayout, layout);
				linearView.addView(heartsTextlayout, layout);
			}
		};

		task = new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};

	}

	@Override
	public void onDestroy() {
		// 当结束程序时关掉Timer
		timer.cancel();
		cancel();
		super.onDestroy();
	}

	public void cancel() {
		if (mBThServerSocket != null) {
			if (D)
				Log.d(TAG, "Socket Type" + mBThServerSocket + "cancel " + this);
			try {
				mBThServerSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "Socket Type" + mBThServerSocket
						+ "close() of server failed", e);
			}
		}

		if (mBThSocket != null) {
			if (D)
				Log.d(TAG, "Socket Type" + mBThSocket + "cancel " + this);
			try {
				mBThSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "Socket Type" + mBThSocket
						+ "close() of server failed", e);
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
			Log.i("info", "computer is connected!");
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
			byte[] buffer = "10002181".getBytes();
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
			isConnected = true;
			Thread receiveThread = new recvThread();
			receiveThread.start();
		}
	}

	private class recvThread extends Thread {
		public recvThread() {
			Message message = new Message();
			message.what = 1;
			updateUI.sendMessage(message);
		}

		public void run() {
			byte[] buffer = new byte[1024 * 2];
			int bytes;
			timer.schedule(task, 1000, 1000);
			while (isConnected) {
				try {
					// Read from the InputStream
					if ((bytes = recvInputStream.read(buffer, 0, 1024 * 2)) > 0) {
						byte[] buf_data = new byte[bytes];
						for (int i = 0; i < bytes; i++) {
							buf_data[i] = buffer[i];
						}
						String recvStr = new String(buf_data);
						if (!recvStr.isEmpty()) {
							String[] recedata = recvStr.split("@");
							String[] bodydata = recedata[0].split(",");
							String[] breathdata = recedata[1].split(",");
							String[] heartdata = recedata[2].split(",");
							bodyInt = bodydata[1];
							breathInt = breathdata[1];
							heartsInt = heartdata[1];
						}
						Log.i("info", "recvice---------!" + recvStr);
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
}
