/********************************************************
 * 类名：DababaseBean.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：上午10:14:32
 ********************************************************/
package com.bit_health.android.database;

/**
 * @author Administrator
 * 
 */
public class FileDbBean {
	public long mId = -1;
	public long mTime;// 测量时间
	public long mTimeLength;// 测量时长
	public String mConfigPar;
	public String mDeviceMac; // 设备MAC地址
	public String mFileName; // 缓存文件名
	public int mStatus; // 状态
	public int mFileIndex; // 文件序列号
}
