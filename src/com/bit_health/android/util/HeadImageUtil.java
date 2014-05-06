/********************************************************
 * 类名：HeadImageUtil.java
 *
 * 作者：陈建平
 * 主要功能：获取角色头像
 * 创建日期：下午3:07:55
 ********************************************************/
package com.bit_health.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.siat.healthweek.R;

/**
 * @author Administrator
 *
 */
public class HeadImageUtil {
	
	static public int getLocalHeadimage(String fullname) {
		if (fullname.contains("爸爸")) {
			return R.drawable.father;
		} else if (fullname.contains("妈妈")) {
			return R.drawable.mather;
		} else if (fullname.contains("爷爷")) {
			return R.drawable.grandpa;
		} else if (fullname.contains("奶奶")) {
			return R.drawable.grandma;
		} else if (fullname.contains("儿子")) {
			return R.drawable.sun;
		} else if (fullname.contains("女儿")) {
			return R.drawable.daughter;
		} else {
			return R.drawable.sun;
		}
	}

	static public String saveHeadimage(String roleid, Bitmap head) {
		String file_path = FileCatchConfigUtil.getRoleCatchPath(roleid)
				+ roleid + ".png";
		File file = new File(file_path);
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
			head.compress(Bitmap.CompressFormat.PNG, 85, fOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fOut != null) {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file_path;
	}
	
	static public void deleteHeadimage(String roleid) {
		String file_path = FileCatchConfigUtil.getRoleCatchPath(roleid)
				+ roleid + ".png";
		File file = new File(file_path);
		file.delete();
	}
	
	static public Bitmap getHeadCatchImage(String roleid) {
		String file_path = FileCatchConfigUtil.getRoleCatchPath(roleid)
				+ roleid + ".png";
		return BitmapFactory.decodeFile(file_path);
	}
}
