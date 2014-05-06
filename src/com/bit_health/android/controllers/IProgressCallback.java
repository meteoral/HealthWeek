package com.bit_health.android.controllers;

public interface IProgressCallback {
	
	public void notifyProgressChanged(int pos,int total,String message);
	
	public void uploadError(String message);
	public void downloadError(String message);
	public void downloadSuccess(String localPath);
	public void uploadSuccess(String message,String ecgid,String ppgid);
}
