package com.bit_health.android.device.bluetooth.ui;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Pair;

import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.constants.DeviceConst;
import com.bit_health.android.controllers.beans.upload.UploadDeviceFileBean;
import com.bit_health.android.database.FileDbBean;
import com.bit_health.android.database.FileDbTable;
import com.bit_health.android.database.RoleDatabase;
import com.bit_health.android.device.AbsUploadDeviceFileDialog;
import com.bit_health.android.device.bluetooth.ConnectionDevice;
import com.bit_health.android.device.bluetooth.GetBlueDataService;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.OneKeyTestActivity;

public class UploadEcgPggDialog extends AbsUploadDeviceFileDialog {

	private FileDbTable mFileTb;
	private RoleDatabase mDb;
	private static final String mDeviceAdress = "00:00:00:00:00";
	@Override
	public boolean initUploadData() {
		mUploadBean.mapPar.put(UploadDeviceFileBean.BP_Fs, "0");
		mUploadBean.mapPar.put(UploadDeviceFileBean.IS_GZ, "1");
		String bluetoothname = ConnectionDevice.getInstance().getDeviceName();
		if (DeviceConst.BT_3IN1E_NAME.equals(bluetoothname)) {
			mUploadBean.mapPar.put(UploadDeviceFileBean.EVENT_TYPE,
					DeviceConst.EVENT_3IN1E_TYPE);
			mUploadBean.mapPar.put(UploadDeviceFileBean.DEVICE_CODE,
					DeviceConst.DEVICE_3IN1E_CODE);
			mUploadBean.mapFile
					.put(UploadDeviceFileBean.ECG_TYPE,
							new Pair<String, String>(
									UploadDeviceFileBean.ECG_FILE,
									mContext.getFileStreamPath(
											GetBlueDataService.mEcgFilename)
											.toString()));
			mUploadBean.mapFile
					.put(UploadDeviceFileBean.PPG_TYPE,
							new Pair<String, String>(
									UploadDeviceFileBean.PPG_FILE,
									mContext.getFileStreamPath(
											GetBlueDataService.mPpgFilename)
											.toString()));
		} else if (bluetoothname.startsWith(DeviceConst.BT_MINIHOLTER_NAME)
				|| bluetoothname.startsWith(DeviceConst.BT_MINIHOLTER_NAME_NEW)) {
			mUploadBean.mapPar.put(UploadDeviceFileBean.EVENT_TYPE,
					DeviceConst.EVENT_MINIHOLTER_TYPE);
			mUploadBean.mapPar.put(UploadDeviceFileBean.DEVICE_CODE,
					DeviceConst.DEVICE_MINIHOLTER_CODE);
			mUploadBean.mapFile
					.put(UploadDeviceFileBean.ECG_TYPE,
							new Pair<String, String>(
									UploadDeviceFileBean.ECG_FILE,
									gzCompressFile(mContext.getFileStreamPath(
											GetBlueDataService.mEcgFilename)
											.toString())));
		} else {
			return false;
		}
		// 读取参数
		try {
			DataInputStream dis = new DataInputStream(
					mContext.openFileInput(AndroidConfiguration.getInstance(
							mContext.getApplicationContext()).getRoleId()
							+ ".mapPar.txt"));
			mUploadBean.mapPar.put(UploadDeviceFileBean.TIME_STAMP,
					dis.readLine());
			mUploadBean.mapPar.put(UploadDeviceFileBean.TIME_LENGTH,
					dis.readLine());
			mUploadBean.mapPar.put(UploadDeviceFileBean.SPO, dis.readLine());
			mUploadBean.mapPar.put(UploadDeviceFileBean.ECG_FS, dis.readLine());
			dis.close();
		} catch (FileNotFoundException e) {
			System.out.println("Upload_FileNotFoundException");
			return false;
		} catch (IOException e2) {
			System.out.println("Upload_IOException");
			return false;
		}
		mUploadBean.mapPar.put(UploadDeviceFileBean.PPG_FS, "60");
		
		openDatabase();
		return true;
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
	private String fileMapPartoJson(){
		JSONObject jobj = new JSONObject();
		 Iterator<String> keys = mUploadBean.mapFile.keySet().iterator();
		 while(keys.hasNext()){
			 String key = keys.next();
			 Pair<String, String> value = mUploadBean.mapFile.get(key);
			 try {
				jobj.put(key, value.second);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 return jobj.toString();
	}
	private void deleteFileUploadTb(){
		if (mFileTb != null) {
			mFileTb.delete(fileMapPartoJson());
		}
	}
	private void openDatabase(){
		String roleid = AndroidConfiguration.getInstance(this).getRoleId();
		mDb = new RoleDatabase(this, roleid);
		if(mDb.CreateDb()){
			mFileTb = mDb.openFileTable(RoleDatabase.FILE_TABLE_NAME);
			if(mFileTb != null){
				FileDbBean bean = mFileTb.get(mDeviceAdress, -1);
				if (bean != null) {
					mFileTb.delete(bean.mId);
				}
			}
		}
	}
	private void updateFileUploadTb(int status){
		FileDbBean bean = new FileDbBean();
		bean.mDeviceMac = mDeviceAdress;
		bean.mFileName = fileMapPartoJson();
		bean.mStatus = status;
		bean.mFileIndex = -1;
		bean.mConfigPar = mapPartoJson();
		bean.mTimeLength = 0;
		bean.mTime = new Date().getTime();
		mFileTb.insert(bean);
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
		OneKeyTestActivity activity = (OneKeyTestActivity) AndroidActivityMananger
				.getInstatnce().getActivity(
						OneKeyTestActivity.class.getSimpleName());
		if (activity != null) {
			activity.mHashTestItems.put(BusinessConst.ECG_MESURE, ecgid);
			if (!TextUtils.isEmpty(ppgid)) {
				activity.mHashTestItems.put(BusinessConst.PPG_MESURE, ppgid);
			}
		}
		deleteFileUploadTb();
		
		super.uploadSuccess(message, ecgid, ppgid);
	}
	
	@Override
	public void uploadError(String message) {
		// TODO Auto-generated method stub
		updateFileUploadTb(1);
		super.uploadError(message);
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
