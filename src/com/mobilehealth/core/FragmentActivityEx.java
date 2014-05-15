package com.mobilehealth.core;

import java.util.HashMap;

import android.support.v4.app.FragmentActivity;

public abstract class FragmentActivityEx extends FragmentActivity{
	
	protected HashMap<String, Object> tempDataStorage=new HashMap<String, Object>();
	
	public Object getData(String key)
	{
		if(tempDataStorage.containsKey(key))
		{
			return tempDataStorage.get(key);
		}else
		{
			return "";
		}
	}
	
	public Object setData(String key, Object value)
	{
		return tempDataStorage.put(key, value);
	}
}
