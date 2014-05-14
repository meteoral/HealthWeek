package com.mobilehealth.core;

import com.siat.healthweek.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class ParentFragmentActivity extends FragmentActivity implements ChildPageListener{

	protected int layoutId;
	protected int containerId;
	protected Class<?> backActivity;
	protected String initPageClassName;

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
		Fragment newPage=getSupportFragmentManager().findFragmentByTag(initPageClassName);
		if(newPage==null)
		{
			FragmentTransaction transac=getSupportFragmentManager().beginTransaction();

			newPage=Fragment.instantiate(this, initPageClassName);
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
			transac.add(containerId, newPage, initPageClassName);

			transac.commit();
		}
	}

	protected abstract void init();

	@Override
	public void changeToPage(Class<?> clazz) {
		// TODO Auto-generated method stub
		changeToPageLocal(clazz.getName());
	}

	@Override
	public void childPageChanged(int firstLevelIndex, String className) {
		// TODO Auto-generated method stub
	}

	private boolean changeToPageLocal(String toPageClassName)
	{
		FragmentTransaction transac=getSupportFragmentManager().beginTransaction();

		Fragment newPage=Fragment.instantiate(this, toPageClassName);
		if(toPageClassName.equals("com.mobilehealth.starting.FragmentGenerationSucceed"))
		{
			transac.setCustomAnimations(R.anim.translucent_view_zoom_in, R.anim.translucent_view_zoom_out, 0, R.anim.view_disappear);
		}
		else{
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
		}
		transac.replace(containerId, newPage, toPageClassName);
		transac.addToBackStack(null);
		transac.commit();

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
