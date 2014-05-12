package com.siat.healthweek;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BlueToothClient extends Activity {
	// static final String SPP_UUID = "00001101-0000-1000-8000-00805f9b34fb";
	private static final String SPP_UUID = "00000004-0000-1000-8000-00805f9b34fb";
	// static final String SPP_UUID="e0cbf06c-cd8b-4647-bb8a-263b43f0f974";
	// static final String SPP_UUID ="00000003-0000-1000-8000-00805f9b34fb";
	// static final String SPP_UUID = "00001201-0000-1000-8000-00805F9B34FB";
	private Button btnSearch, btnDis, btnListen;
	private ToggleButton tbtnSwitch;
	private ListView lvBTDevices;
	private ArrayAdapter<String> adtDevices;
	private List<String> lstDevices = new ArrayList<String>();
	private ArrayList<String> recvArrayList=new ArrayList<String>();
	private BluetoothAdapter btAdapt = null;
	private static BluetoothSocket btSocket=null;
	private PrintStream mPrintStream = null;
	private BluetoothServerSocket mBThServerSocket = null;
	private BluetoothSocket mBThSocket = null;
	private BluetoothDevice device=null;
	private BroadcastReceiver ListenDevices = null;
	private BluetoothAcceptThread bluetoothAcceptThread=null;
	//private BufferedReader mBufferReader=null;
	private boolean isConnected=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Button 设置
		btnSearch = (Button) this.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new ClickEvent());
		btnListen = (Button) this.findViewById(R.id.btnListen);
		btnListen.setOnClickListener(new ClickEvent());
		btnDis = (Button) this.findViewById(R.id.btnDis);
		btnDis.setOnClickListener(new ClickEvent());

		// ToogleButton设置
		tbtnSwitch = (ToggleButton) this.findViewById(R.id.tbtnSwitch);
		tbtnSwitch.setOnClickListener(new ClickEvent());

		// ListView及其数据源 适配器
		lvBTDevices = (ListView) this.findViewById(R.id.lvDevices);
		adtDevices = new ArrayAdapter<String>(BlueToothClient.this,
				android.R.layout.simple_list_item_1, lstDevices);
//		lvBTDevices.setAdapter(adtDevices);
//		lvBTDevices.setOnItemClickListener(new ItemClickEvent());

		btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能
		btAdapt.enable();
		if (btAdapt.getState() == BluetoothAdapter.STATE_OFF)// 读取蓝牙状态并显示
			tbtnSwitch.setChecked(false);
		else if (btAdapt.getState() == BluetoothAdapter.STATE_ON)
			tbtnSwitch.setChecked(true);

		// 注册Receiver来获取蓝牙设备相关的结果
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(searchDevices, intent);
        //注册receiver监听配对
		ListenDevices = new PairingRequest();
		IntentFilter intent2 = new IntentFilter();
		intent2.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
		registerReceiver(ListenDevices, intent2);
	}

	private BroadcastReceiver searchDevices = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Bundle b = intent.getExtras();
			Object[] lstName = b.keySet().toArray();

			// 显示所有收到的消息及其细节
			for (int i = 0; i < lstName.length; i++) {
				String keyName = lstName[i].toString();
				Log.e(keyName, String.valueOf(b.get(keyName)));
			}
			// BluetoothOppManager
			// 搜索设备时，取得设备的MAC地址
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String str = device.getName() + "|" + device.getAddress();
				if (lstDevices.indexOf(str) == -1)// 防止重复添加
					lstDevices.add(str); // 获取设备名称和mac地址
				adtDevices.notifyDataSetChanged();
			}
		}
	};


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
				Toast.makeText(BlueToothClient.this,device.getName()+" is connected!", 1000).show();
			}
		}
	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(searchDevices);
		this.unregisterReceiver(ListenDevices);

		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	// viewList 触控事件
//	class ItemClickEvent implements AdapterView.OnItemClickListener {
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			btAdapt.cancelDiscovery();
//			String str = lstDevices.get(arg2);
//			String[] values = str.split("\\|");
//			String address = values[1];
//			UUID uuid = UUID.fromString(SPP_UUID);
//			BluetoothDevice btDev = btAdapt.getRemoteDevice(address);
//
//			try {
//				ClsUtils.setPin(btDev.getClass(), btDev, "0000");
//				ClsUtils.cancelPairingUserInput(btDev.getClass(), btDev);// 取消配对时pin输入
//				ClsUtils.setPairingConfirmation(device.getClass(), device, true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			try {
//				ClsUtils.createBond(btDev.getClass(), btDev);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			try {
//
//				btSocket = btDev.createRfcommSocketToServiceRecord(uuid);
//				btSocket.connect();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	// 本机蓝牙可见
	class ClickEvent implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == btnSearch)// 搜索蓝牙设备，在BroadcastReceiver显示结果
			{
				if (btAdapt.getState() == BluetoothAdapter.STATE_OFF) {// 如果蓝牙还没开启
					Toast.makeText(BlueToothClient.this, "请先打开蓝牙", 1000).show();
					return;
				}
				setTitle("本机蓝牙地址：" + btAdapt.getAddress());
				lstDevices.clear();
				adtDevices.clear();
				btAdapt.startDiscovery();
			} else if (v == tbtnSwitch) {// 本机蓝牙启动/关闭
				if (tbtnSwitch.isChecked() == false)
					btAdapt.enable();
				else if (tbtnSwitch.isChecked() == true)
					btAdapt.disable();
			} else if (v == btnDis)// 本机可以被搜索
			{
				Intent discoverableIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(
						BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
				startActivity(discoverableIntent);
			} else if (v == btnListen) {
				bluetoothAcceptThread =new BluetoothAcceptThread(btAdapt, 29);
				bluetoothAcceptThread.start();
			}
		}
	}

	  private class BluetoothAcceptThread extends Thread{
			public BluetoothAcceptThread(BluetoothAdapter btAdapter,int port) {
	    			try {
						Message msg = new Message();
						msg.obj = "listening...";
						mHandler.sendMessage(msg);
						mBThServerSocket=ClsUtils.listenUsingRfcommOn(btAdapter.getClass(), btAdapter, port);
					} catch (Exception e) {
						e.printStackTrace();
					}

	    	}
	    	public void run()
	    	{
	    		try {
					Message msg = new Message();
					msg.obj = "accepting...";
					mHandler.sendMessage(msg);
					mBThSocket=mBThServerSocket.accept();
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
				// mBufferReader = new BufferedReader(new InputStreamReader(mBThSocket.getInputStream(),"utf8 "));
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
					mHandler.sendMessage(msg);
					mBThSocket=mBThServerSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		isConnected=true;
				Thread receiveThread = new recvThread();
				receiveThread.start();
				Thread sendThread = new sendThread();
				sendThread.start();
	    	}
	    }
	InputStream recvInputStream = null;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Toast.makeText(BlueToothClient.this, msg.obj.toString(), 1000).show();
		}
	};

	 private class recvThread extends Thread{
		 public recvThread()
		 {
	    		try {
					Message msg = new Message();
					msg.obj = "recv thread...";
					mHandler.sendMessage(msg);
					mBThSocket=mBThServerSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
		 public void run() {
				byte[] buffer = new byte[1024*2];
				int bytes;
				while (isConnected) {
					try {
						// Read from the InputStream
						if ((bytes = recvInputStream.read(buffer,0,1024*2)) > 0) {
							byte[] buf_data = new byte[bytes];
							for (int i = 0; i < bytes; i++) {
								buf_data[i] = buffer[i];
							}
							String recvStr = new String(buf_data);
							recvArrayList.add(recvStr);
							Log.i("info", "recvice---------!");
							Message msg = new Message();
							msg.obj = "recv:"+ recvStr.toString().substring(0,4)+"...("+recvStr.length()+")";
							mHandler.sendMessage(msg);
						}
					} catch (IOException e) {
						try {
							isConnected=false;
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

	 private class sendThread extends Thread{
		 public sendThread(){}
		 public void run() {
				byte[] buffer =  "hello!this is a Android end!".getBytes();
				while (isConnected) {
					try {
						mPrintStream.write(buffer);
					} catch (IOException e) {
						isConnected=false;
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
					mHandler.sendMessage(msg);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
	 }

}