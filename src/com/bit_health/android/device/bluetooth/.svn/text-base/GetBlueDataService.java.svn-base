package com.bit_health.android.device.bluetooth;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.device.bluetooth.ui.BluetoothData;
import com.bit_health.android.device.bluetooth.ui.EcgPpgActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.TestXinDianActivity;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.Log;

/*
 * 该类用于后台与蓝牙通信服务，取得数据，继承Service
 */

public class GetBlueDataService extends Service {

	private HandleThread thread;
	// 心电采样率
	private static int ECGSampleRate = 250;
	// 脉搏采样率
	private static final int PPGSampleRate = 60;

	static public int xueyang = 127; // 血氧值默认127，表示无效数据

	public static final String ACTION_START_RECEIVER_DATA = "startReceiverBluetoothData";
	public static final String ACTION_STOP_RECEIVER_DATA = "stopReceiverBluetoothData";

	public static String mEcgFilename;
	public static String mPpgFilename;

	private boolean isFirstReadData = true;
	// 接受缓存队列长度
	public static int BufferSize = 10000;
	// 接受数据队列
	public static CircularQueue ECGQueue = new CircularQueue(BufferSize);
	public static CircularQueue PPGQueue = new CircularQueue(BufferSize);
	// 队列备份
	public static CircularQueue ECGQueue2 = new CircularQueue(BufferSize);
	public static CircularQueue PPGQueue2 = new CircularQueue(BufferSize);
	// 文件存储长度
	private static final int FILELEN = 1000000;
	public static int ecgFileSum = 0; // 100,000点
	public static int ppgFileSum = 0;
	// 数据存储到文件标志位
	public static boolean DATA_TOFILE_FLAG = false;// startflag
	// 数据存储到队列标志位
	public static boolean DATA_TOQUEUE_FLAG = false;// do flag

	static public String mStrEcgFs;

	private WakeLock wl;

	// private static final String PATH =
	// "/data/data/com.uih.mobile/files/ecg.txt";

	// 蓝牙数据包，包头标志位
	private int FF = 0xFF;
	// 生理参数
	public static int[] Data = new int[6];
	// public static int heartRate=0; Data[0]
	// public static int temperature; Data[1]
	// public static int pulseRate; Data[2]
	// public static int sop2; Data[3]
	// public static int signalLevel; Data[4]
	// public static int guangZhuTu; Data[5]

	// 23阶FIR滤波器，用于计算心率值所用，该滤波器更能滤除信号的毛刺，有利于计算R波峰。
	private static double fly0;
	private static double[] flx0 = new double[23];
	private double[] a = { 0.0008, 0.0025, 0.0057, 0.0109, 0.0183, 0.0280,
			0.0393, 0.0513, 0.0629, 0.0724, 0.0788, 0.0810, 0.0788, 0.0724,
			0.0629, 0.0513, 0.0393, 0.0280, 0.0183, 0.0109, 0.0057, 0.0025,
			0.0008 };

	int fir(int d) {
		int i;
		try {
			for (i = 0; i < 22; i++)
				flx0[22 - i] = flx0[21 - i];
			flx0[0] = d;
			fly0 = 0;
			for (i = 0; i < 23; i++)
				fly0 += a[i] * flx0[i];
		} catch (Exception e) {
			fly0 = 0;
		}

		return (int) fly0;
	}

	public class BlueBinder extends Binder {
		public GetBlueDataService getServiceObject() {
			return GetBlueDataService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private BlueBinder mBinder = new BlueBinder();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("startHandle", "in create");
		// 启动接受线程
		// startHandle();
		if (TextUtils.isEmpty(mEcgFilename)) {
			mEcgFilename = AndroidConfiguration.getInstance(
					this.getApplicationContext()).getRoleId()
					+ new String(".ecg.txt");
		}
		if (TextUtils.isEmpty(mPpgFilename)) {
			mPpgFilename = AndroidConfiguration.getInstance(
					this.getApplicationContext()).getRoleId()
					+ new String(".ppg.txt");
		}
	}

	// 退出
	public void stopHandle() {
		Log.d("Service", "stopHandle");
		if (thread != null) {
			thread.bRun = false;
		}
	}

	public void startHandle() {

		thread = new HandleThread();
		if (!thread.isAlive()) {
			Log.d("in start handle", "no alive");

			thread.start();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("Service", "Destory");
		// 关闭线程
		stopHandle();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (intent != null) {
			if (ACTION_START_RECEIVER_DATA.equals(intent.getAction())) {
				startHandle();
			}

			if (ACTION_STOP_RECEIVER_DATA.equals(intent.getAction())) {
				stopHandle();
				stopSelf();
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}

	// /////////////////////////////////////////////////////////////
	private class HandleThread extends Thread {
		private boolean bRun = false;
		// ecg缓存数据长度
		private int ECGBufferLength = 1000;
		// ppg缓存数据长度
		private int PPGBufferLength = 240;

		// 缓存数组
		private int[] ECGBuffer = new int[ECGBufferLength];
		private int[] PPGBuffer = new int[PPGBufferLength];
		// 缓存数组下标
		private int ecgBufferPointer = 0;
		private int ppgBufferPointer = 0;
		// 发送数据包序号
		private int transmit_no = 0;
		private byte[] ecgSendBuffer = new byte[2000];
		// 时间截字段数据
		private String[] time_stamp = new String[6];
		private String[] frame_time_stamp = new String[6];
		private String _USERNAME;
		private String _PASSWORD;
		// private String _ip = "http://mobile.bit-health.com";
		int packageLength = 0;

		// 启动
		@Override
		public void run() {
			this.setName("HandleThread");
			android.util.Log.i("HandleThread", "HandleThread start....");
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			init();
			InputStream input = null;
			int res;
			int ecgFlag = 0xA1;
			input = ConnectionDevice.getInstance().getInputStream();
			int deviceCode = ConnectionDevice.getInstance().getDeviceCode();
			if (deviceCode == 0) {
				packageLength = 5;
				ecgFlag = 0xE1;
				mStrEcgFs = "250";
				ECGSampleRate = 250;
			} else if (deviceCode == 1 || deviceCode == 3) // 新老三合一
			{
				packageLength = 2;
				ecgFlag = 0xA1;
				mStrEcgFs = "250";
				ECGSampleRate = 250;
			} else if (deviceCode == 2) // **By Liu 新的miniholter
			{
				packageLength = 2;
				ecgFlag = 0xB1;
				mStrEcgFs = "150";
				ECGSampleRate = 150;
			} else {
				// 无法识别
				android.util.Log.e("HandleThread", "deviceCode error->> "
						+ deviceCode);
				return;
			}
			bRun = true;
			isFirstReadData = true;
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,
					"GetBlueDataService");
			wl.acquire();

			boolean bNeedRecnnt = false;
			while (bRun) {
				// 读取数据包包头
				try {
					res = readHeader(input);
					if (res == ecgFlag) {
						// 读ECG数据包
						readECG(input);
						if (isFirstReadData) {
							isFirstReadData = false;
							AndroidActivityMananger
									.getInstatnce()
									.switchActivity(
											TestXinDianActivity.class
													.getSimpleName(),
											EcgPpgActivity.class);
						}
					} else if (res == 0xF3) {
						// 读PPG数据包
						readPPG(input);

						if (isFirstReadData) {
							isFirstReadData = false;
							AndroidActivityMananger
									.getInstatnce()
									.switchActivity(
											TestXinDianActivity.class
													.getSimpleName(),
											EcgPpgActivity.class);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("蓝牙数据读取错误！");
					bNeedRecnnt = true;
				}
				if (bNeedRecnnt) {
					ConnectionDevice.getInstance().disconnectSocket();
					if (!ConnectionDevice.getInstance().connect(
							BluetoothData.getInsetance().mDevice)) {
						// 没连上，退出
						break;
					}
					bNeedRecnnt = false;
					try {
						input.close();
						input = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					input = ConnectionDevice.getInstance().getInputStream();
				}

			}
			/**/

			ConnectionDevice.getInstance().disconnectSocket();
			BluetoothData.getInsetance().destroy();
			wl.release();
			android.util.Log.i("HandleThread", "HandleThread end....");
		}

		// 读取数据包包头
		public int readHeader(InputStream input) throws IOException {
			int des = 0;
			boolean flag = true;
			while (flag) {

				des = input.read();
				// Log.d(TAG, "des=" + des);

				if (des == FF) {

					return input.read();

				}
			}
			// 如果读出现异常，是否需要退出
			bRun = false;
			return 0;
		}

		// 读取数据
		public boolean readData(InputStream input, byte[] buf, int offset,
				int num) throws IOException {
			boolean flag = true;
			int len;
			while (num > 0) {

				len = 0;
				len = input.read(buf, offset, num);
				if (len > 0) {
					offset = offset + len;
					num = num - len;
				}

			}
			return flag;
		}

		// 读ECG数据包
		private void readECG(InputStream input) throws IOException {

			byte[] buffer = new byte[packageLength];
			boolean flag = readData(input, buffer, 0, packageLength);
			if (flag) {
				int ecg = 0;
				int deviceCode = ConnectionDevice.getInstance().getDeviceCode();
				if (deviceCode == 1 || deviceCode == 2 || deviceCode == 3) {
					// android.util.Log.i("GetBlueDataService", "aaaa-->>>"
					// + (buffer[0] & 0x000000FF) +"; -->>>"+(buffer[1] &
					// 0x000000FF));
					ecg = ((buffer[0] & 0x000000FF) << 8)
							| (buffer[1] & 0x000000FF);
				} else if (deviceCode == 0) {
					ecg = ((buffer[0] & 0x0000007F) << 7)
							| (buffer[1] & 0x0000007F);
				}
				/*
				 * int xinlv = (buffer[2] & 0x0000007f) | ((buffer[4] &
				 * 0x00000003) << 7); int tempp = (buffer[3] & 0x0000007f) |
				 * ((buffer[4] & 0x0000000c) << 5);
				 */
				if (deviceCode == 2) {
					// ecg = ecg / 2;
				}
				if (deviceCode == 3) {
					// ecg = ecg / 2;
				}
				int rate = 0;
				// Log.d(TAG, "ecg value=" + ecg + " xinlv=" + xinlv + " temp="
				// + tempp);
				if (DATA_TOQUEUE_FLAG) {
					synchronized (ECGQueue) {
						ECGQueue.in(ecg);
					}

				}
				if (DATA_TOQUEUE_FLAG) {
					synchronized (ECGQueue2) {
						ECGQueue2.in(ecg);
					}
				}

				ECGBuffer[ecgBufferPointer++] = ecg;
				if (ecgBufferPointer >= ECGBufferLength) {
					ecgBufferPointer = 0;
					if (DATA_TOFILE_FLAG) {
						if (ecgFileSum < FILELEN) {
							AddToECGFile();
							ecgFileSum += ECGBufferLength;

							// transmit_no++;
							// if(!sendDataToServer()){
							// transmit_no--;
							// }

						}
					}

					rate = countEcgRate(ECGBuffer, ECGBufferLength,
							ECGSampleRate);
					synchronized (Data) {
						Data[0] = rate;
						/*
						 * if (rate != 0) { Data[1] = (int) (Math.random() * 10)
						 * + 362; } else { Data[1] = 0; }
						 */
					}
				}
			}
		}

		// 读PPG数据包
		private void readPPG(InputStream input) throws IOException {

			byte[] buffer = new byte[5];
			boolean flag = readData(input, buffer, 0, 5);
			if (flag) {
				int ppg = buffer[1] & 0x0000007F;
				xueyang = buffer[4] & 0x0000007F;
				if (DATA_TOQUEUE_FLAG) {
					synchronized (PPGQueue) {
						PPGQueue.in(ppg);

					}
					synchronized (PPGQueue2) {
						PPGQueue2.in(ppg);
					}
				}

				PPGBuffer[ppgBufferPointer++] = ppg;
				if (ppgBufferPointer >= PPGBufferLength) {
					ppgBufferPointer = 0;
					if (DATA_TOQUEUE_FLAG) {
						if (ppgFileSum < FILELEN) {
							AddToPPGFile();
							ppgFileSum += PPGBufferLength;
						}
					}

					int res = countPulseRate(PPGBuffer, PPGBufferLength,
							PPGSampleRate);
					synchronized (Data) {
						if (127 == xueyang) {
							Data[1] = 0;
						} else {
							Data[1] = xueyang;
						}
						Data[2] = res;
					}
				}
			}
		}

		// 清除历史数据
		private void init() {
			ecgFileSum = 0;
			ppgFileSum = 0;
			// clearFile();
		}

		/*
		 * private void clearFile(){ deleteFile(ecgFile); deleteFile(ppgFile); }
		 */
		// private void AddToECGFile(){
		// File write = new File(PATH);
		// try {
		// BufferedWriter bw = new BufferedWriter( new FileWriter(write));
		// for(int i=0; i<ECGBufferLength; i++){
		// bw.write(Integer.toString(ECGBuffer[i++])+"\r\n");
		// }
		// bw.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		// 存入心电文件
		private void AddToECGFile() {
			FileOutputStream fos = null;
			try {
				int deviceCode = ConnectionDevice.getInstance().getDeviceCode();
				fos = openFileOutput(mEcgFilename, Context.MODE_APPEND);
				DataOutputStream out = new DataOutputStream(fos);
				try {

					// 每次读四个数
					for (int i = 0; i < ECGBufferLength; i++) {
						if (deviceCode == 0) {

							out.write((4096 - ECGBuffer[i]) >> 4);

						} else if (deviceCode == 1 || deviceCode == 3) // 新老三合一
						{
							out.write(ECGBuffer[i] >> 8);
							out.write(ECGBuffer[i] & 0x000000ff);
						} else if (deviceCode == 2) {
							out.write((4096 - ECGBuffer[i]) >> 4);
						}
					}

					out.close();
					fos.close();
				} catch (IOException e) {
				}
			} catch (FileNotFoundException e) {
			}
		}

		// 存入脉搏文件
		private void AddToPPGFile() {
			FileOutputStream fos = null;
			try {
				fos = openFileOutput(mPpgFilename, Context.MODE_APPEND);
				DataOutputStream out = new DataOutputStream(fos);
				try {
					for (int i = 0; i < PPGBufferLength; i++) {
						// out.writeInt(PPGBuffer[i]);
						out.writeBytes(Integer.toString(PPGBuffer[i]) + "\r\n");
					}
					out.close();
					fos.close();
				} catch (IOException e) {
				}
			} catch (FileNotFoundException e) {
			}
		}

		// 计算脉率
		private int countPulseRate(int[] ppg, int len, int s_rate) {
			int result = 0;
			List<Integer> P = new ArrayList<Integer>();
			int[] diff = new int[len - 1];
			int[] soc = new int[len - 1];

			try {
				for (int i = 0; i < len - 1; i++) {
					diff[i] = ppg[i + 1] - ppg[i];
				}

				int max = diff[0];
				for (int i = 0; i < len - 1; i++) {
					if (max < diff[i])
						max = diff[i];
				}

				float threshold = (float) (max * 0.6);

				int n = 0;
				for (int i = 0; i < len - 3; i++) {
					if (diff[i] < threshold && diff[i + 1] < threshold
							&& diff[i + 2] >= threshold
							&& diff[i + 3] >= threshold)
						soc[n++] = i;
				}

				for (int i = 0; i < n; i++) {
					int p = soc[i];
					int res = ppg[p];
					P.add(p);
					for (int j = p - 20; j < p && j > 0; j++) {
						if (res > ppg[j]) {
							res = ppg[j];
							P.set(P.size() - 1, j);
						}
					}
				}
				int[] PP = new int[P.size() - 1];
				for (int i = 0; i < PP.length; i++) {
					PP[i] = P.get(i + 1) - P.get(i);
				}

				int size = PP.length;
				int sum = 0;
				for (int j = 0; j < size; j++) {
					sum = sum + PP[j];
				}
				int avel = sum / size;
				int rate = 60 * s_rate / avel;

				if (rate > 40 && rate < 150) {
					result = rate;
				} else {
					result = 0;
				}

			} catch (Exception e) {
				result = 0;
			}
			return result;
		}

		// 计算心率
		private int countEcgRate(int[] ecg, int len, int s_rate) {
			int result;
			try {
				for (int i = 0; i < len; i++) {
					ecg[i] = fir(ecg[i]);
				}

				List<Integer> R = new ArrayList<Integer>();
				int[] soc = new int[len];
				int[] diff = new int[len];

				diff[0] = 0;
				diff[1] = 0;
				for (int j = 2; j < len - 2; j++) {
					diff[j] = (ecg[j - 2] - 2 * ecg[j] + ecg[j + 2]);
				}
				diff[len - 1] = 0;
				diff[len - 2] = 0;

				int num = len / s_rate;
				int[] min = new int[num];

				for (int i = 0; i < num; i++) {
					min[i] = diff[s_rate * i];
					for (int j = 0; j < s_rate; j++) {
						if (min[i] > diff[s_rate * i + j])
							min[i] = diff[s_rate * i + j];
					}
				}

				float[] threshold = new float[num];
				for (int j = 0; j < num; j++)
					threshold[j] = (float) ((min[j]) * 0.6);

				int n = 0;
				for (int i = 0; i < num; i++) {
					for (int j = 0; j < s_rate && (s_rate * i + j) < len - 3; j++) {
						if (diff[s_rate * i + j] > threshold[i]
								&& diff[s_rate * i + j + 1] > threshold[i]
								&& diff[s_rate * i + j + 2] <= threshold[i]
								&& diff[s_rate * i + j + 3] <= threshold[i])
							soc[n++] = s_rate * i + j;
					}
				}
				for (int i = 0; i < n; i++) {
					int p = soc[i];
					int res = ecg[p];
					R.add(p);
					for (int j = p - 5; j < p + 5 && p > 5 && j < len; j++) {
						if (res < ecg[j]) {
							res = ecg[j];
							R.set(R.size() - 1, j);
						}
					}
				}

				for (int j = 0; j < n - 1; j++) {
					if ((R.get(j + 1) - R.get(j)) < (s_rate / 5)) {
						if (ecg[R.get(j + 1)] > ecg[R.get(j)]) {
							R.remove(j);
							n--;
							j--;
						} else {
							R.remove(j + 1);
							n--;
						}

					} else if ((R.get(j + 1) - R.get(j)) > (s_rate * 12 / 10)) {
						int res = diff[R.get(j) + 100];
						int pos = R.get(j) + 100;
						for (int t = R.get(j) + 100; t < (R.get(j + 1) - 100); t++) {
							if (res < diff[t]) {
								res = diff[t];
								pos = t;
							}
						}
						res = ecg[pos];
						int p_pos = pos;
						for (int t = pos - 5; t < pos + 5; t++) {
							if (res < ecg[t]) {
								res = ecg[t];
								p_pos = t;
							}
						}
						R.add(j + 1, p_pos);
						n++;
						j++;
					}
				}

				int RR[] = new int[n - 1];
				for (int j = 0; j < n - 1; j++) {
					RR[j] = R.get(j + 1) - R.get(j);
				}

				int size = RR.length;
				int sum = 0;
				for (int j = 0; j < size; j++) {
					sum = sum + RR[j];
				}
				int avel = sum / size;
				int rate = 60 * s_rate / avel;
				if (rate > 40 && rate < 150) {
					result = rate;
				} else {
					result = 0;
				}

			} catch (Exception e) {
				result = 0;
			}
			return result;
		}

		public int readByte(InputStream input) {
			int str = -1;
			return str;
		}

	}
}
