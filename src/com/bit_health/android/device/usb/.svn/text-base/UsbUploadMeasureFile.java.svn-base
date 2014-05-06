package com.bit_health.android.device.usb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import com.bit_health.android.constants.DeviceConst;
import com.bit_health.android.controllers.beans.upload.UploadDeviceFileBean;
import com.bit_health.android.device.AbsUploadDeviceFileDialog;

public class UsbUploadMeasureFile extends AbsUploadDeviceFileDialog {
	// 所有可能的目录
	protected static String m_sConfigPath = "conffs.txt";// 配置文件
	protected static String m_sEcgBytePath = "ecghex.txt";// 十六进制文件
	protected String m_sPpgPath = "ppgdec.txt";
	protected String m_sNIBPPath;
	protected String m_sBGMPath;

	protected String mEcgFileNameOrg;
	protected String mPpgFileNameOrg;

	protected String mEcgFileName;
	protected String mPpgFileName;
	/*
	 * 检测并获取到设备文件路径
	 */
	public static String checkSiatDevice() {
		Map<String, String> map = System.getenv();
		String flagStorage = "STORAGE";
		Iterator<Entry<String, String>> its = map.entrySet().iterator();
		while (its.hasNext()) {
			Entry<String, String> entry = its.next();
			if (entry.getKey().contains(flagStorage)) {
				String splits[] = entry.getValue().split(":");
				for (String vaue : splits) {
					File file = new File(vaue + File.separator + m_sEcgBytePath);
					if (file.isFile()) {
						return vaue + File.separator;
					}
				}
			}
		}
		return "";
	}

	protected void setText(final String msg) {
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(mContent != null){
					mContent.setText(msg);
				}
			}
		});
	}

	@Override
	public boolean initUploadData() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = checkSiatDevice();
		// String path =
		// File.separator+"mnt"+File.separator+"sdcard"+File.separator+"siat"+File.separator;
		if (!TextUtils.isEmpty(path)) {
			setText("检测到设备路径:" + path);
			mEcgFileNameOrg = path + m_sEcgBytePath;
			mEcgFileName = gzCompressFile(mEcgFileNameOrg);
			mPpgFileNameOrg = path + m_sPpgPath;
			mPpgFileName = gzCompressFile(mPpgFileNameOrg);
			if (readConffigFile(path + m_sConfigPath)) {
				// 如果读取配置成功，则上传文件列表
				return true;
			}
		}
		return false;
	}

	protected void stopService() {
		this.stopService(new Intent(this.getApplicationContext(),
				UsbUploadService.class));
	}

	/**
	 * 
	 * 
	 * @param filePath
	 */
	public boolean readConffigFile(String configFileName) {
		/* 设置目前所在路径 */
		File f = new File(configFileName);
		boolean bReadConfigFile = false;
		if (f.isFile()) {
			Properties props = new Properties();
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(f));
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
					if (key.equals("DeviceInputTime")) {
						continue;
					}
					if (key.equals("Ver")) {
						continue;
					}
					mUploadBean.mapPar.put(key, value);
					if (key.equalsIgnoreCase("DeviceCode")) {
						if (value != null) {
							if (value.equals("SIAT3IN1_E")
									|| value.equals("SIATECGPPG")) {
								bReadConfigFile = true;
								// 三合一
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.EVENT_TYPE,
										DeviceConst.EVENT_3IN1E_TYPE);
								mUploadBean.mapFile.put(
										UploadDeviceFileBean.ECG_TYPE,
										new Pair<String, String>(
												UploadDeviceFileBean.ECG_FILE,
												mEcgFileName));
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.IS_GZ, "1");
								mUploadBean.mapFile.put(
										UploadDeviceFileBean.PPG_TYPE,
										new Pair<String, String>(
												UploadDeviceFileBean.PPG_FILE,
												mPpgFileName));
							}
							if (value.startsWith("SIATELECECG")
									|| value.equals("SIAT_ELECECG")) {
								// miniholter
								bReadConfigFile = true;
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.EVENT_TYPE,
										DeviceConst.EVENT_MINIHOLTER_TYPE);
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.ECG_FS, "150");
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.PPG_FS, "60");
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.SPO, "0");
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.IS_GZ, "1");
								mUploadBean.mapFile.put(
										UploadDeviceFileBean.ECG_TYPE,
										new Pair<String, String>(
												UploadDeviceFileBean.ECG_FILE,
												mEcgFileName));
								// tv.setText("您连接的设备为--MiniHolter");
							}
							if (value.equals("SIAT_NIBP")) {
								// 血压
								bReadConfigFile = true;
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.SPO, "0");
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.EVENT_TYPE, "2");
								mUploadBean.mapFile.put("bp",
										new Pair<String, String>("BPFile",
												m_sNIBPPath));
								// tv.setText("您连接的设备为--血压计");

							}
							if (value.equals("SIAT_BGM")) {
								// 血糖
								bReadConfigFile = true;
								mUploadBean.mapPar.put(
										UploadDeviceFileBean.EVENT_TYPE, "4");
								mUploadBean.mapFile.put("bgm",
										new Pair<String, String>("BGMFile",
												m_sBGMPath));
								// tv.setText("您连接的设备为--血糖仪");
							}
						}
					}
				}
				props.clear();
				in.close();
				if (!bReadConfigFile) {
					setText("没找到配置文件或者配置文件读取错误");
				}
				return bReadConfigFile;
			} catch (Exception e) {
				setText("读取配置异常:" + e.getMessage());
			}
		}
		return bReadConfigFile;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService();
	}

	@Override
	public void uploadEnd(String message) {
		// TODO Auto-generated method stub
		super.uploadEnd(message);
	}

	@Override
	public void uploadSuccess(String message, String ecgid, String ppgid) {
		// TODO Auto-generated method stub
		super.uploadSuccess(message, ecgid, ppgid);
		Iterator<Pair<String, String>> files = mUploadBean.mapFile.values()
				.iterator();
		while (files.hasNext()) {
			Pair<String, String> pair = files.next();
			File file = new File(pair.second);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}

		if (!TextUtils.isEmpty(mEcgFileNameOrg)) {
			new File(mEcgFileNameOrg).delete();
		}
		if (!TextUtils.isEmpty(mPpgFileNameOrg)) {
			new File(mPpgFileNameOrg).delete();
		}
	}

	@Override
	public void downloadError(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadSuccess(String localPath) {
		// TODO Auto-generated method stub
		
	}
}
