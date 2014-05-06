package com.bit_health.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.bit_health.android.device.bluetooth.ConnectBluetooth;
import com.bit_health.android.device.bluetooth.ConnectionDevice;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;

public class BluetoothReceiveUtil {
	private BluetoothSocket mSocket;
	private BluetoothDevice mDevice;
	private InputStream mIn;
	private static final int READ_PKT_LENGTH = 1024;
	public static final  int MAX_PACKET_RECV = 16*1024;
	public static final  int MIN_PACKET_RECV = 1024;
	
	private OutputStream mOut;
	private static final int FLAG_LENGTH = 2;
	
	// 请求文件长度指令
	private static final byte CMMD_REQ_FILE_LEN = 0x01;
	// 请求数据传输指令
	private static final byte CMMD_REQ_DATA = 0x02;
	
	// 数据传输结束
	private static final byte CMMD_REQ_DATA_END = 0x03;

	// 恢复实时模式
	private static final byte CMMD_REQ_DATA_ONLINE = 0x04;
	
	// 配置文件标志
	private static final byte FLAG_CONFIG_FILE = 0x01;
	
	// ecghex.txt文件标志
    private static final byte FLAG_ECGHEX_FILE = 0x02;
    
    private Timer mTimer;
    
//    private boolean bReadTimeout = false;
	private int mCurrentPktLength = MAX_PACKET_RECV;
	private byte[] byteRequest = new byte[64];
	public BluetoothReceiveUtil(BluetoothSocket s,BluetoothDevice device){
		this.mDevice = device;
		this.mSocket = s;
		try {
			mIn = this.mSocket.getInputStream();
			mOut = this.mSocket.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public int getCurrentPktLength() {
		return mCurrentPktLength;
	}

	public boolean reconnect() {
		try {
			if (mSocket != null) {
				mSocket.close();
				mSocket = null;
			}
			if (mIn != null) {
				mIn.close();
				mIn = null;
			}
			if (mOut != null) {
				mOut.close();
				mOut = null;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean conneted = ConnectionDevice.getInstance().connect(mDevice);
		if (conneted) {
			mSocket = ConnectionDevice.getInstance().getSocket();

			try {
				mIn = mSocket.getInputStream();
				mOut = mSocket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				conneted = false;
			}
		}
		return conneted;
	}
	
	// 检测命令合法性
	private boolean checkCommand(byte[] revdata,int length) {
		if (revdata[0] == (byte)0xfe && revdata[1] == (byte)0xfd
				&& revdata[length - 2] == (byte)0xfd
				&& revdata[length - 1] == (byte)0xfe) {
			return true;
		}
		return false;
	}
	
	// 获取配置文件的长度
	public int getConfigFileLen() throws IOException {
		return requestFileLen(FLAG_CONFIG_FILE);
	}
	
	// 请求结束，删除文件，重新启动
	public void requestEnd() throws IOException {
		// 包头
		byteRequest[0] = (byte) 0xfe;
		byteRequest[1] = (byte) 0xfd;

		byteRequest[FLAG_LENGTH] = CMMD_REQ_DATA_END;

		// 结束
		byteRequest[FLAG_LENGTH + 1] = (byte) 0xfd;
		byteRequest[FLAG_LENGTH + 2] = (byte) 0xfe;
		mOut.write(byteRequest, 0, 5); // 6 bytes
	}
	
	// 获取在线模式
	public void recoverOnline() throws IOException {
		// 包头
		byteRequest[0] = (byte) 0xfe;
		byteRequest[1] = (byte) 0xfd;
		
		byteRequest[FLAG_LENGTH] = CMMD_REQ_DATA_ONLINE;
		
		// 结束
		byteRequest[FLAG_LENGTH + 1] = (byte) 0xfd;
		byteRequest[FLAG_LENGTH + 2] = (byte) 0xfe;
		mOut.write(byteRequest, 0, 5); // 5 bytes
	}

	// 配置文件读取结束
	public void revConfigFileEnd() throws IOException {
		requestFileEnd(FLAG_CONFIG_FILE);
	}

	// ecg文件读取结束
	public void revEcgFileEnd() throws IOException {
		requestFileEnd(FLAG_ECGHEX_FILE);
	}

	// 分包接收，每个包控制在READ_PKT_LENGTH长度
	private boolean read(byte[] revBytes, int byteOffset, int byteCount)
			throws IOException {
		int revNeed = byteCount;
		int hasRevLen = 0;
		int pos = byteOffset;
		while (revNeed - hasRevLen > 0) {
			int toReadLen = (revNeed - hasRevLen) > READ_PKT_LENGTH ? READ_PKT_LENGTH
					: (revNeed - hasRevLen);
			if ((toReadLen = recvData(revBytes, pos, toReadLen)) == -1) {
				return false;
			}
			hasRevLen = hasRevLen + toReadLen;
			pos = pos + toReadLen;
		}
		return true;
	}

//	// 接收指定大小数据，设置超时
//	private boolean recvData(byte[] revBytes, int byteOffset, int byteCount) {
//		try {
//			int revNeed = byteCount;
//			int pos = byteOffset;
//			int readLen = 0;
//			while ((readLen = mIn.read(revBytes, pos, revNeed)) > 0) {
//				if ((revNeed = revNeed - readLen) <= 0) {
//					break;
//				}
//				pos = pos + readLen;
//			}
//			if (revNeed <= 0) {
//				return true;
//			}
//			return false;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//
//	}
	private void cancelTimer(){
		if(mTimer != null){
			mTimer.cancel();
			mTimer = null;
		}
	}
//// 接收指定大小数据，设置超时
//	private boolean recvData(byte[] revBytes,int byteOffset,int byteCount) throws IOException{
//		android.util.Log.i("recvData", "recvData begin byteOffset = "+byteOffset+" byteCount = "+byteCount);
//		bReadTimeout = false;
//		mTimer = new Timer();
//		mTimer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					bReadTimeout = true;
////					mIn.close();
////					mOut.close();
//					if(mSocket != null){
//						mSocket.close();
//						mSocket = null;
//					}
//					cancelTimer();
//					android.util.Log.e("recvData", "read packet time out->>>>>1111");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					cancelTimer();
//				}
//			}
//		}, 3000, 3000);
//		int revNeed = byteCount;
//		int pos = byteOffset;
//		try {
//			int readLen = 0;
//			while ((readLen = mIn.read(revBytes, pos, revNeed)) > 0) {
//				if ((revNeed = revNeed - readLen) <= 0) {
//					break;
//				}
//				pos = pos + readLen;
//				android.util.Log.i("recvData", "recvData temp pos = "+pos+"; revNeed = "+revNeed);
//			}
//			cancelTimer();
//			if (revNeed <= 0) {
//				android.util.Log.i("recvData", "recvData end ");
//				return true;
//			}
//			return false;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			if (bReadTimeout) {
//				// 超时了
////				android.util.Log.e("BluetoothReceiveUtil", "recvData->>>IOException read packet time out");
//				bReadTimeout = true;
//				return false;
//			} else {
//				cancelTimer();
//				throw e;
//			}
//		}
//	}
	// 获取ECG文件的长度
	public int getEcgFileLen() throws IOException {
		return requestFileLen(FLAG_ECGHEX_FILE);
	}

	public boolean getEcgFile(byte[] data, int pos, int length)
			throws IOException {
//		android.util.Log.i("BluetoothReceiveUtil", "getEcgFile begin pos = "+pos+"; length = "+length);
		int iPos = pos;
		int iNeddRev = length;
		int dataPos = 0;
		int readLen = getFileData(FLAG_ECGHEX_FILE, data,dataPos,iPos, iNeddRev);
		if (readLen == -1) {
			return false;
		}
		dataPos = dataPos + readLen;
		while ((iNeddRev = iNeddRev - readLen) > 0) {
			readLen = getFileData(FLAG_ECGHEX_FILE, data,dataPos,iPos + readLen, iNeddRev);
			if (readLen == -1) {
				return false;
			}
			dataPos = dataPos + readLen;
		}
//		android.util.Log.i("BluetoothReceiveUtil", "getEcgFile end");
		return true;
	}
	
	public boolean getConfigFile(byte[] data, int pos, int length)
			throws IOException {
		int iPos = pos;
		int iNeddRev = length;
		int dataPos = 0;
		int readLen = getFileData(FLAG_CONFIG_FILE, data, dataPos, iPos,
				iNeddRev);
		if (readLen == -1) {
			return false;
		}
		dataPos = dataPos + readLen;
		while ((iNeddRev = iNeddRev - readLen) > 0) {
			readLen = getFileData(FLAG_CONFIG_FILE, data, dataPos, iPos
					+ readLen, iNeddRev);
			if (readLen == -1) {
				return false;
			}
			dataPos = dataPos + readLen;
		}
		return true;
	}
	
	private int get8byte(int len){
		return (len/8+1)*8;
	}
	
	// 清除实时数据
	private int clearBluetoothCatch(byte []values,int length) throws IOException {
		byte[] data = new byte[READ_PKT_LENGTH];
		while (true) {
			int len = recvData(data, 0, READ_PKT_LENGTH);
			if (len > 0) {
				for (int i = 0; i < len - 1; i++) {
					if (data[i] == (byte) 0xfe && data[i + 1] == (byte) 0xfd) {
						System.arraycopy(data, i, values, 0, (len - i)>length?length:(len - i));
						return (len - i)>length?length:(len - i);
					}
				}
			} else {
				return len;
			}
		}
	}

//	// 接收不定大小的数据，设置超时
//	private int recvData(byte[] revBytes, int length) {
//		int revLength = -1;
//		try {
//			revLength = mIn.read(revBytes, 0, length);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return revLength;
//	}
	// 接收不定大小的数据，设置超时
//	private int recvData(byte[] revBytes, int length) throws IOException {
//		bReadTimeout = false;
//		int revLength = -1;
//		mTimer = new Timer();
//		mTimer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					mIn.close();
//					mOut.close();
//					bReadTimeout = true;
//					cancelTimer();
//					 android.util.Log.e("recvData",
//					 "read packet time out->>>>>>22222");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					cancelTimer();
//				}
//			}
//		}, 3000, 3000);
//		try {
//			revLength = mIn.read(revBytes, 0, length);
//			cancelTimer();
//			return revLength;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			if (bReadTimeout) {
//				// 超时了
//				bReadTimeout = false;
//				return revLength;
//			} else {
//				cancelTimer();
//				throw e;
//			}
//		}
//	}
	
	private int getFileData(byte fileFlg, byte[] data,int dstPos,int pos, int length)
			throws IOException{
//		android.util.Log.i("getFileData", "getFileData begin dstPos = "+dstPos+"; length = "+length);
		int i = 0;
		int actLength = -1;
		int recvPktLen = length;
		while ((actLength = getFileDataAtemp(fileFlg, data, dstPos, pos, recvPktLen)) == -1
				&& i < 5) {
			if(recvPktLen > MIN_PACKET_RECV){
				recvPktLen = recvPktLen - MIN_PACKET_RECV;
			}
			// 重试三次
			if (!reconnect()) {
				break;
			}
			i++;
		}
//		if (actLength > 0 && i==0 && mCurrentPktLength < MAX_PACKET_RECV) {
//			mCurrentPktLength = mCurrentPktLength + MIN_PACKET_RECV;
//		}
		if (actLength > 0) {
			// 获取数据成功
			if (i == 0) {
				// 一次通过
				if (mCurrentPktLength <= recvPktLen) {
					if (recvPktLen + MIN_PACKET_RECV <= MAX_PACKET_RECV) {
						mCurrentPktLength = recvPktLen + MIN_PACKET_RECV;
					}
				}
			} else {
				if(recvPktLen >= MIN_PACKET_RECV){
					mCurrentPktLength = recvPktLen;
				}else{
					mCurrentPktLength = MIN_PACKET_RECV;
				}
			}
		}
//		android.util.Log.i("getFileData", "getFileData end i = "+i+" mCurrentPktLength = "+mCurrentPktLength);
		return actLength;
	}
	private  int recvData(final byte[] b,final int pos,final int count) {
		int readByte = -1;
		// Read data with timeout
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Callable<Integer> readTask = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return mIn.read(b, pos, count);
			}
		};
		Future<Integer> future = executor.submit(readTask);
		try {
			readByte = future.get(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		future.cancel(true);
		executor.shutdown();
	
		return readByte;
	}
	
	private int getFileDataAtemp(byte fileFlg, byte[]data,int destPos,int pos, int length)
			throws IOException {
		// 包头
		byteRequest[0] = (byte) 0xfe;
		byteRequest[1] = (byte) 0xfd;
		// 请求文件长度类型
		byteRequest[FLAG_LENGTH] = CMMD_REQ_DATA;
		
		// 请求文件标志
		byteRequest[FLAG_LENGTH + 1] = fileFlg;
		
		// 偏移
		byteRequest[FLAG_LENGTH + 2] = ((byte)(pos>>24));
		byteRequest[FLAG_LENGTH + 3] = ((byte)(pos>>16));
		byteRequest[FLAG_LENGTH + 4] = ((byte)(pos>>8));
		byteRequest[FLAG_LENGTH + 5] = ((byte)(pos));
		
		// 请求数据长度
		byteRequest[FLAG_LENGTH + 6] = ((byte)(length>>24));
		byteRequest[FLAG_LENGTH + 7] = ((byte)(length>>16));
		byteRequest[FLAG_LENGTH + 8] = ((byte)(length>>8));
		byteRequest[FLAG_LENGTH + 9] = ((byte)(length));
		
		// 结束
		byteRequest[FLAG_LENGTH + 10] = (byte) 0xfd;
		byteRequest[FLAG_LENGTH + 11] = (byte) 0xfe;
		mOut.write(byteRequest, 0, 14);
		if (!read(byteRequest, 0, 12)) {
//			android.util.Log.e("getFileDataAtemp", "getFileDataAtemp end");
			return -1;
		}
		int dataLen = (((int) (byteRequest[8]&0xff)) << 24)
				| (((int) (byteRequest[9]&0xff)) << 16) | (((int) (byteRequest[10]&0xff)) << 8)
				| ((int) (byteRequest[11]&0xff));
		
		if (!read(data, destPos, dataLen)) {
//			android.util.Log.e("getFileDataAtemp", "getFileDataAtemp1 end dataLen = "+dataLen);
			return -1;
		}
		
		if (!read(byteRequest, 0, 2)) {
//			android.util.Log.e("getFileDataAtemp", "getFileDataAtemp2 end dataLen = "+dataLen);
			return -1;
		}
		if (byteRequest[0] == (byte) 0xfd
				&& byteRequest[1] == (byte) 0xfe) {
			return dataLen;
		}
		android.util.Log.e("getFileDataAtemp", "getFileDataAtemp3 end dataLen = "+dataLen);
		return -1;
	}
	private void requestFileEnd(byte fileFlg) throws IOException {
		// 包头
		byteRequest[0] = (byte) 0xfe;
		byteRequest[1] = (byte) 0xfd;
		
		// 请求文件长度类型
		byteRequest[FLAG_LENGTH] = CMMD_REQ_DATA_END;

		// 请求文件标志
		byteRequest[FLAG_LENGTH + 1] = fileFlg;
		
		// 结束
		byteRequest[FLAG_LENGTH + 2] = (byte) 0xfd;
		byteRequest[FLAG_LENGTH + 3] = (byte) 0xfe;
		mOut.write(byteRequest,0,6); // 6 bytes
	}

	private int requestFileLen(byte fileFlg) throws IOException {
		int datalen = -1;
		int i = 0;
		while ((datalen = requestFileLenAtemp(fileFlg)) <= 0 && i < 3) {
			// 重试三次
			if (!reconnect()) {
				break;
			}
			i++;
		}
		return datalen;
	}
	private int requestFileLenAtemp(byte fileFlg) throws IOException {
		// 包头
		byteRequest[0] = (byte) 0xfe;
		byteRequest[1] = (byte) 0xfd;

		// 请求文件长度类型
		byteRequest[FLAG_LENGTH] = CMMD_REQ_FILE_LEN;

		// 请求文件标志
		byteRequest[FLAG_LENGTH + 1] = fileFlg;
		
		// 结束
		byteRequest[FLAG_LENGTH + 2] = (byte) 0xfd;
		byteRequest[FLAG_LENGTH + 3] = (byte) 0xfe;
		mOut.write(byteRequest,0,6); // 6 bytes
		
		// 清楚缓存先
		int revLength = clearBluetoothCatch(byteRequest, byteRequest.length);
		if (revLength <= 0) {
			return revLength;
		}
		if (!read(byteRequest, revLength, 10 - revLength)) {
			return -1;
		}

		if (checkCommand(byteRequest,10)) {
			// 合法的数据包
			int length = (((int) (byteRequest[4]&0xff)) << 24)
					| (((int) (byteRequest[5]&0xff)) << 16)
					| (((int) (byteRequest[6]&0xff)) << 8) | ((int) (byteRequest[7]&0xff));
			return length;		
		}
		return -1;
	}
}
