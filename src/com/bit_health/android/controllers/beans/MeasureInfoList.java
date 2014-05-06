package com.bit_health.android.controllers.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bit_health.android.constants.BusinessConst;

public class MeasureInfoList extends JsonBase {
	public static final String DATA_FLAG = "result";
	public static final String MEASURETYP_FLAG = "measure_type";
	public static final int MAX_DATA = 1024;
	private String mMeasureType = "";

	public List<JsonBase> mMeasureList = new ArrayList<JsonBase>();

	public MeasureInfoList(String type) {
		mMeasureType = type;
	}

	public void toMeasureInfoListBean(String jsonString) {

		if (mMeasureList.size() != 0) {
			mMeasureList.clear();
		}
		JSONObject jobj = super.toBean(jsonString);
		try {
			if (mRcode == BusinessConst.RT_OK) {
				// 数据正常
				for (int i = 0; i < MAX_DATA; i++) {
					JsonBase bean = null;
					String nodeType = mMeasureType;
					String orgString = jobj
							.getString(DATA_FLAG + i);
					if(BusinessConst.ALL_MESURE.equals(mMeasureType)){
						// 获取的是全部测量数据，需要把当前节点的数据类型取出来
						JSONObject infoJson = new JSONObject(orgString);
						nodeType = infoJson.getString(MEASURETYP_FLAG);
					} 
					
					if (BusinessConst.ECG_MESURE.equals(nodeType)) {
						// Ecg数据类型
						bean = new EcgInfoBean();
						((EcgInfoBean) bean).parserJson(jobj
								.getString(DATA_FLAG + i));
						((EcgInfoBean) bean).mType = BusinessConst.DATA_BRIEF;
					} else if (BusinessConst.PPG_MESURE.equals(nodeType)) {
						// ppg数据类型
						bean = new PpgInfoBean();
						((PpgInfoBean) bean).parserJson(jobj
								.getString(DATA_FLAG + i));
						((PpgInfoBean) bean).mType = BusinessConst.DATA_BRIEF;
					} else if (BusinessConst.BP_MESURE.equals(nodeType)) {
						// bp数据类型
						bean = new BpInfoBean();
						((BpInfoBean) bean).parserJson(jobj.getString(DATA_FLAG
								+ i));
						((BpInfoBean) bean).mType = BusinessConst.DATA_BRIEF;
					} else if (BusinessConst.BS_MESURE.equals(nodeType)) {
						// bs数据类型
						bean = new BsInfoBean();
						((BsInfoBean) bean).parserJson(jobj.getString(DATA_FLAG
								+ i));						
						((BsInfoBean) bean).mType = BusinessConst.DATA_BRIEF;
					}
					if (bean != null) {
						mMeasureList.add(bean);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}

	}
}
