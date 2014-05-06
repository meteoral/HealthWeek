/********************************************************
 * 类名：FileCatchConfigUtil.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午5:09:34
 ********************************************************/
package com.bit_health.android.util;

import java.io.File;

import android.os.Environment;

/**
 * @author Administrator
 *
 */
public class FileCatchConfigUtil {
	private static final String DEFAULT_DIR = "bithealth";

	static public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}
		return null;
	}
	// 获取角色指定的缓存文件路径
	static public String getRoleCatchPath(String roleid){
		String sdDir = getSDPath();
		if(sdDir == null){
			return null;
		}
		String rolePath = sdDir + File.separator + DEFAULT_DIR + File.separator
				+ roleid + File.separator;
		File file = new File(rolePath);
		if(!file.isDirectory()){
			file.mkdirs();
		}
		return rolePath;
	}
	
	// 获取默认缓存目录
	static public String getCatchPath() {
		String sdDir = getSDPath();
		if (sdDir == null) {
			return null;
		}
		String path = sdDir + File.separator + DEFAULT_DIR + File.separator;
		File file = new File(path);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		return path;
	}
	
	// 获取需要保存的文件名，在文件名后缀前加当前的日期时间
	static public String generateFileName(String sourcename){
		String spritName[] = sourcename.split("\\.");
		if (spritName.length == 1) {
			return sourcename + "_" + TimeFormatUtil.getNowTime() + ".txt";
		} else {
			String genFileName = "";
			for (int i = 0; i < spritName.length - 1; i++) {
				genFileName = genFileName + spritName[i];
			}
			genFileName = genFileName + TimeFormatUtil.getNowTime() + "."
					+ spritName[spritName.length - 1];
			return genFileName;
		}
	}
}
