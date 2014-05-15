package com.mobilehealth.core;

import com.siat.healthweek.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class ParentFragmentActivity extends FragmentActivityEx{
	
	protected int layoutId;
	protected int containerId;
	protected Class<?> backActivity;
	protected String curPageClassName;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(layoutId);

		init();
		
		displayInitPage();
	}
	
	private void displayInitPage()
	{
		Fragment newPage=getSupportFragmentManager().findFragmentByTag(curPageClassName);
		if(newPage==null)
		{
			FragmentTransaction transac=getSupportFragmentManager().beginTransaction();
			
			newPage=Fragment.instantiate(this, curPageClassName);
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
			transac.add(containerId, newPage, curPageClassName);
			
			transac.commit();
		}
	}
	
	protected abstract void init();

	public void changeToPage(Class<?> clazz) {
		changeToPageLocal(clazz.getName());
	}
	
	public void childPageChanged(String className) {
	}
	
	private boolean changeToPageLocal(String toPageClassName)
	{
		FragmentTransaction transac=getSupportFragmentManager().beginTransaction();

		Fragment newPage=Fragment.instantiate(this, toPageClassName);
		transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
		transac.replace(containerId, newPage, toPageClassName);
		transac.addToBackStack(null);
		transac.commit();
		
		curPageClassName=toPageClassName;

		return true;
	}
	
	protected boolean disposeBtnBack()
	{
		FragmentManager childFragManager=getSupportFragmentManager();
		if(childFragManager.getBackStackEntryCount()>0)
		{
			childFragManager.popBackStack();
			return true;
		}
		
		Intent intent=new Intent();
		intent.setClass(this, backActivity);
		startActivity(intent);

		return true;
	}
}
