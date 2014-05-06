package com.bit_health.android.device.usb;

import java.io.File;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.controllers.beans.upload.UploadDeviceFileBean;
import com.bit_health.android.database.FileDbBean;
import com.bit_health.android.database.FileDbTable;
import com.bit_health.android.database.RoleDatabase;
import com.bit_health.android.device.AbsUploadDeviceFileDialog;

public class HistoryUploadFile extends AbsUploadDeviceFileDialog {

	public static final String INTENT_DATA_ID = "intent_data_id";
	private int mDataId;
	private String mUploadFileNameOrg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if(intent.getExtras() != null){
			mDataId = intent.getExtras().getInt(INTENT_DATA_ID);
		}
		super.onCreate(savedInstanceState);
	}

	
	
	@Override
	public void downloadError(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downloadSuccess(String localPath) {
		// TODO Auto-generated method stub

	}
	
	private boolean getConfigPar(String json){
		try {
			JSONObject jObj = new JSONObject(json);
			 Iterator<String> it = jObj.keys();
			 while(it.hasNext()){
				 String key = it.next();
				 String value = jObj.getString(key);
				 mUploadBean.mapPar.put(key, value);
			 }
			 return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean getFilePar(String json){
		try {
			JSONObject jObj = new JSONObject(json);
			 Iterator<String> it = jObj.keys();
			 while(it.hasNext()){
				 String key = it.next();
				 String value = jObj.getString(key);
				if (key.equals(UploadDeviceFileBean.ECG_TYPE)) {
					mUploadBean.mapFile.put(UploadDeviceFileBean.ECG_TYPE,
							new Pair<String, String>(
									UploadDeviceFileBean.ECG_FILE, value));
				}
				if (key.equals(UploadDeviceFileBean.PPG_TYPE)) {
					mUploadBean.mapFile.put(UploadDeviceFileBean.PPG_TYPE,
							new Pair<String, String>(
									UploadDeviceFileBean.PPG_FILE, value));
				}
			 }
			 return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean initUploadData() {
		// TODO Auto-generated method stub
		String roleid = AndroidConfiguration.getInstance(this).getRoleId();
		RoleDatabase db = new RoleDatabase(this, roleid);
		if (db.CreateDb()) {
			FileDbTable fileTb = db.openFileTable(RoleDatabase.FILE_TABLE_NAME);
			if (fileTb != null) {
				FileDbBean bean = fileTb.get(mDataId);
				if (bean != null) {
					if (getConfigPar(bean.mConfigPar)
							&& getFilePar(bean.mFileName)) {
						db.closeDb();
						return true;
					}
				}
			}
			db.closeDb();
		}
		return false;
	}
	
	@Override
	public void uploadSuccess(String message, String ecgid, String ppgid) {
		// TODO Auto-generated method stub
		super.uploadSuccess(message, ecgid, ppgid);
		String roleid = AndroidConfiguration.getInstance(this).getRoleId();
		RoleDatabase db = new RoleDatabase(this, roleid);
		if (db.CreateDb()) {
			FileDbTable fileTb = db.openFileTable(RoleDatabase.FILE_TABLE_NAME);
			if (fileTb != null) {
				fileTb.delete(mDataId);
			}
			db.closeDb();
		}
		
		Iterator<Pair<String, String>> files = mUploadBean.mapFile.values()
				.iterator();
		while (files.hasNext()) {
			Pair<String, String> pair = files.next();
			File file = new File(pair.second);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
		if(!TextUtils.isEmpty(mUploadFileNameOrg)){
			File file = new File(mUploadFileNameOrg);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
	}

}
