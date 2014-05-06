/********************************************************
 * 类名：BluetoothUploadMiniholterEcg.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午4:57:38
 ********************************************************/
package com.bit_health.android.device.usb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.text.TextUtils;

import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.controllers.beans.upload.UploadDeviceFileBean;
import com.bit_health.android.database.FileDbBean;
import com.bit_health.android.database.FileDbTable;
import com.bit_health.android.database.RoleDatabase;
import com.bit_health.android.device.bluetooth.ConnectionDevice;
import com.bit_health.android.util.BluetoothReceiveUtil;
import com.bit_health.android.util.FileCatchConfigUtil;
import com.siat.healthweek.R;

/**
 * @author Administrator
 *
 */
public class BluetoothUploadMiniholterEcg extends UsbUploadMeasureFile {
//	private static final int MAX_REC_LENGTH = 16 * 1024;

	private BluetoothReceiveUtil mBluetoothConn;

	private String mConfigFile;

	private FileDbTable mFileTb;
	private RoleDatabase mDb;

	private String mDeviceAdress;
	protected int mFileIndex = 0;

	private int mId;

	private int get8byte(int len){
		return (len/8+1)*8;
	}

	private int caculateRequestTime(int iSize,float speed){
		float time = (iSize/ speed)/60;
		float time1 = (int)(time*10)/10;
		float time2 = time;
		if(time2 - time1 >= 0.5){
			time1 = time1 +1;
		}
		return (int) time1;
	}

	private float caculateSpeed(int iSize, long time) {
		float seconds = ((float) time) / 1000;
		float fSize = ((float) iSize) / 1024;
		return fSize / seconds;
	}

	private int caculateTime(int iSize){
		float time = ((float)iSize)/(512*1024);
		float time1 = (int)(time*10)/10;
		float time2 = time;
		if(time2 - time1 >= 0.5){
			time1 = time1 +1;
		}
		return (int) time1;
	}
	private String mapPartoJson(){
		JSONObject jobj = new JSONObject();
		 Iterator<String> keys = mUploadBean.mapPar.keySet().iterator();
		 while(keys.hasNext()){
			 String key = keys.next();
			 String value = mUploadBean.mapPar.get(key);
			 try {
				jobj.put(key, value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 return jobj.toString();
	}
	private String fileMapPartoJson(String EcgFileName){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(UploadDeviceFileBean.ECG_TYPE, EcgFileName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobj.toString();
	}
	private boolean readConfigFile() {
		OutputStream out = null;
		try {
			int iConfigLen = mBluetoothConn.getConfigFileLen();

			String roleid = AndroidConfiguration.getInstance(this).getRoleId();
			String path = FileCatchConfigUtil.getRoleCatchPath(roleid);
			if (path == null) {
				return false;
			}
			if (iConfigLen > 0) {
				android.util.Log.i("BluetoothUploadMiniholterEcg", "config file length : ->>"
						+ iConfigLen);
				mConfigFile = path
						+ FileCatchConfigUtil.generateFileName(m_sConfigPath);
				File file = new File(mConfigFile);
				file.createNewFile();
				// 增加 64字节，防止越界
				byte[] revdata = new byte[get8byte(iConfigLen)+64];
				out = new FileOutputStream(file);
				if (mBluetoothConn.getConfigFile(revdata, 0, iConfigLen)) {
					out.write(revdata);
				}
				out.close();
				out = null;
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out = null;
		}
		if (!TextUtils.isEmpty(mConfigFile)) {
			new File(mConfigFile).delete();
			mConfigFile = null;
		}
		return false;
	}

	private String getFileName(String FileName){
		JSONObject json;
		try {
			json = new JSONObject(FileName);
			return (String) json.get(UploadDeviceFileBean.ECG_TYPE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	private void checkFileExist(){
		String roleid = AndroidConfiguration.getInstance(this).getRoleId();
		// 是否存在还没有传输完成的数据文件
		mDb = new RoleDatabase(this, roleid);
		if(mDb.CreateDb()){
			mFileTb = mDb.openFileTable(RoleDatabase.FILE_TABLE_NAME);
			if(mFileTb != null){
				FileDbBean bean = mFileTb.get(mDeviceAdress, mFileIndex);
				if (bean != null) {
					String filename = getFileName(bean.mFileName);
					File f = new File(filename);
					if (f.isFile()) {
						// 判断是否还存在,并且序列号不一致
						mEcgFileNameOrg = filename;
					} else {
						mFileTb.delete(bean.mId);
					}
				}
			}
		}
	}
	private void updateFileUploadTb(int status){
		if(!TextUtils.isEmpty(mEcgFileNameOrg)){
			long filesize = -1;
			File file = new File(mEcgFileNameOrg);
			if(file.isFile()){
				FileInputStream in = null;
				try {
					in = new FileInputStream(file);
					filesize = in.available();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					in = null;
				}
			}
			if(mFileTb != null && filesize > 0){
				FileDbBean bean = mFileTb
						.get(mDeviceAdress, status, mFileIndex);
				if (bean != null) {
					bean.mTime = new Date().getTime();
					bean.mTimeLength = caculateTime((int)filesize);
					bean.mConfigPar = mapPartoJson();
					mFileTb.update(bean, bean.mId);
				}else{
					bean = new FileDbBean();
					bean.mDeviceMac = mDeviceAdress;
					bean.mFileName = fileMapPartoJson(mEcgFileNameOrg);
					bean.mStatus = status;
					bean.mFileIndex = mFileIndex;
					bean.mConfigPar = mapPartoJson();
					bean.mTimeLength = caculateTime((int)filesize);
					bean.mTime = new Date().getTime();
					mFileTb.insert(bean);
				}
			}
		}
	}
	private void deleteFileUploadTb(){
		if (mFileTb != null) {
			mFileTb.delete(fileMapPartoJson(mEcgFileNameOrg));
		}
	}
	private boolean readEcgHexFile() {
		OutputStream out = null;
		try {
			String showMsg = "正在接收心电数据";
			String roleid = AndroidConfiguration.getInstance(this).getRoleId();
			String path = FileCatchConfigUtil.getRoleCatchPath(roleid);
			if (path == null) {
				return false;
			}
			// 检测文件是否存在
			checkFileExist();
			int iEcgHexLen = mBluetoothConn.getEcgFileLen();
			if (iEcgHexLen > 0) {
				android.util.Log.i("BluetoothUploadMiniholterEcg", "ecg file length : ->>"
						+ iEcgHexLen);
				File file = null;
				int hasRevLen = 0;
				int pos = 0;
				if(TextUtils.isEmpty(mEcgFileNameOrg)){
					mEcgFileNameOrg = path
							+ FileCatchConfigUtil.generateFileName(m_sEcgBytePath);
					file = new File(mEcgFileNameOrg);
					file.createNewFile();
					out = new FileOutputStream(file);
				}else{
					file = new File(mEcgFileNameOrg);
					FileInputStream in = new FileInputStream(file);
					hasRevLen = in.available();
					pos = hasRevLen;
					out = new FileOutputStream(file,true);
				}

				// 测量时间，小时
				int time = caculateTime(iEcgHexLen);
				// 花费时间，分钟
				int reTime = caculateRequestTime(iEcgHexLen-hasRevLen,16*1024);
				showMsg = this.getString(R.string.alert_bl_upload_string);
				notifyProgressChanged(pos, iEcgHexLen, String.format(showMsg, time,reTime));

				int readLen = BluetoothReceiveUtil.MAX_PACKET_RECV;
				// 增加64字节，防止越界
				byte[] revdata = new byte[BluetoothReceiveUtil.MAX_PACKET_RECV+64];
				long timeBegin =  System.currentTimeMillis();
				while (hasRevLen < iEcgHexLen && mbRunning) {
					if (iEcgHexLen - hasRevLen > mBluetoothConn.getCurrentPktLength()) {
						readLen = mBluetoothConn.getCurrentPktLength();
					} else {
						readLen = iEcgHexLen - hasRevLen;
					}
					if (!mBluetoothConn.getEcgFile(revdata, pos, readLen)) {
						android.util.Log.e("BluetoothUploadMiniholterEcg",
								"read ecg hex file error!");
						out.close();
						out = null;
						return false;
					}
					if (revdata != null) {
						out.write(revdata, 0, readLen);
					}
					hasRevLen = hasRevLen + readLen;
					notifyProgressChanged(hasRevLen, iEcgHexLen, String.format(showMsg, time,reTime));
					pos = pos + readLen;
					android.util.Log.i("BluetoothUploadMiniholterEcg", "read ecg file packet ("
							+ hasRevLen + "/" + iEcgHexLen + ") bytes");
				}
				out.close();
				out = null;
				android.util.Log.i("BluetoothUploadMiniholterEcg", "read ecg file need->"+(System.currentTimeMillis()-timeBegin));
				return mbRunning?true:false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(out != null){
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out = null;
		}
		return false;
	}

	private void getFileIndex() {
		File f = new File(mConfigFile);
		if (f.isFile()) {
			Properties props = new Properties();
			InputStream in = null;
			try {
				in = new BufferedInputStream(new FileInputStream(f));
				props.load(in);
				Enumeration en = props.propertyNames();
				// 在这里遍历
				while (en.hasMoreElements()) {
					String key = en.nextElement().toString();// key值
					if (TextUtils.isEmpty(key.trim())) {
						continue;
					}
					String value = props.getProperty(key);
					if (TextUtils.isEmpty(value.trim())) {
						continue;
					}
					if (key.equalsIgnoreCase("Index")) {
						mFileIndex = Integer.valueOf(value);
						break;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in = null;
			}
		}
	}
	@Override
	public boolean initUploadData() {
		// TODO Auto-generated method stub
		BluetoothDevice device = ConnectionDevice.getInstance().getDevice();
		mDeviceAdress = device.getAddress();
		BluetoothSocket s = ConnectionDevice.getInstance().getSocket();
		mBluetoothConn = new BluetoothReceiveUtil(s,device);
		setText("正在读取配置文件");
		if (!readConfigFile() && mbRunning) {
			setText("正在读取配置文件失败");
			if (!TextUtils.isEmpty(mConfigFile)) {
				new File(mConfigFile).delete();
			}
			ConnectionDevice.getInstance().disconnectSocket();
			return false;
		}

		// 获取序列号
		getFileIndex();

		//
		if (!readEcgHexFile()) {
			setText("正在读取ECG文件失败");
			if (!TextUtils.isEmpty(mConfigFile)) {
				new File(mConfigFile).delete();
			}
			updateFileUploadTb(0);
			ConnectionDevice.getInstance().disconnectSocket();
			return false;
		}
		try {
			mBluetoothConn.requestEnd();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConnectionDevice.getInstance().disconnectSocket();
		mEcgFileName = gzCompressFile(mEcgFileNameOrg);
//		mEcgFileName = "/mnt/sdcard/bithealth/52d6267e94c2b2900800002d/ecghex20140307181708.txt";
		if (readConffigFile(mConfigFile) && mbRunning) {
			// 如果读取配置成功，则上传文件列表
			return true;
		}
		if (!TextUtils.isEmpty(mConfigFile)) {
			new File(mConfigFile).delete();
			mConfigFile = null;
		}
		updateFileUploadTb(1);
		return false;

	}

	@Override
	public void uploadEnd(String message) {
		// TODO Auto-generated method stub
		super.uploadEnd(message);
		if (mDb != null) {
			mDb.closeDb();
			mDb = null;
		}
	}

	@Override
	public void uploadSuccess(String message, String ecgid, String ppgid) {
		// TODO Auto-generated method stub
		deleteFileUploadTb();
		if (!TextUtils.isEmpty(mConfigFile)) {
			new File(mConfigFile).delete();
			mConfigFile = null;
		}
		if (!TextUtils.isEmpty(mEcgFileNameOrg)) {
			new File(mEcgFileNameOrg).delete();
			mEcgFileNameOrg = null;
		}
		if (!TextUtils.isEmpty(mEcgFileName)) {
			new File(mEcgFileName).delete();
			mEcgFileName = null;
		}
		super.uploadSuccess(message, ecgid, ppgid);
	}

	@Override
	public void uploadError(String message) {
		// TODO Auto-generated method stub
		// 记录下来
		updateFileUploadTb(1);
		if (!TextUtils.isEmpty(mConfigFile)) {
			new File(mConfigFile).delete();
			mConfigFile = null;
		}
		super.uploadError(message);
	}

}
