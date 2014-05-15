package com.mobilehealth.core;

import java.util.HashMap;

import android.support.v4.app.FragmentActivity;

public abstract class FragmentActivityEx extends FragmentActivity{
	
	protected HashMap<String, String> tempDataStorage=new HashMap<String, String>();
	
	public String getData(String key)
	{
		if(tempDataStorage.containsKey(key))
		{
			return tempDataStorage.get(key);
		}else
		{
			return "";
		}
	}
	
	public String setData(String key, String value)
	{
		return tempDataStorage.put(key, value);
	}
}
