package com.bit_health.android.task;

import android.content.Context;
import android.content.Intent;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.IServerBusiness;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.util.FileCatchConfigUtil;

public class GetHeadImageTask implements Runnable {
	private Context mContext;
	private String mRoleid;

	public GetHeadImageTask(String roleid, Context context) {
		this.mRoleid = roleid;
		this.mContext = context.getApplicationContext();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		RoleInfoBean role = RoleCatchInfo.getInstance(mContext).getRole(mRoleid);

		android.util.Log.i("GetHeadImageTask", "GetHeadImageTask.run called: "+mRoleid);
//		RoleInfoBean role = RoleCatchInfo.getInstance(mContext).getRole(mRoleid);

		String filePah = FileCatchConfigUtil.getRoleCatchPath(mRoleid)
				+ mRoleid + ".png";
		if (IServerBusiness.getInstance(mContext).DownLoadFile(role.mImage,
				filePah, null)) {
			mContext.sendBroadcast(new Intent(
					RoleInfoBean.ACTION_ROLEINFO_CHANGED));
			android.util.Log.i("GetHeadImageTask", "GetHeadImageTask.run called suceess: "+mRoleid);
		}else{
			// 下载失败
			role.mImage = "";
		}
	}
}
