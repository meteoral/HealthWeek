package com.mobilehealth.core;

import com.siat.healthweek.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class ParentFragmentActivity extends FragmentActivity implements ChildPageMessageListener{
	
	protected int layoutId;
	protected int containerId;
	protected Class<?> backActivity;
	protected String[] childFragmentArray;
	private int curPageIndex = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setLayout();
		
		setContentView(layoutId);

		init();
		
		setContainer();
		setBackActivity();
		
		Fragment newPage=getSupportFragmentManager().findFragmentByTag(childFragmentArray[curPageIndex]);
		if(newPage==null)
		{
			FragmentTransaction transac=getSupportFragmentManager().beginTransaction();
			
			newPage=Fragment.instantiate(this, childFragmentArray[curPageIndex]);
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
			transac.add(containerId, newPage, childFragmentArray[curPageIndex]);
			
			transac.commit();
		}
	}
	
	protected void setLayout(){};
	protected void setContainer(){};
	protected void setBackActivity(){};
	
	protected int getCurPageIndex()
	{
		return curPageIndex;
	}
	
	protected abstract void init();

	@Override
	public void changeToPage(Class<?> clazz) {
		// TODO Auto-generated method stub
        int toIndex=getPageIndex(clazz.getName());
		
		changeToPageLocal(toIndex);
	}
	
	@Override
	public void childPageChanged(int firstLevelIndex, int secondLevelIndex) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getPageIndex(String className) {
		// TODO Auto-generated method stub
		for(int i=0;i<childFragmentArray.length;i++)
		{
			if(className.equals(childFragmentArray[i]))
			{
				return i;
			}
		}
		return -1;
	}
	
	private boolean changeToPageLocal(int toIndex)
	{
		if(toIndex==curPageIndex|toIndex<0)
		{
			return false;
		}

		FragmentTransaction transac=getSupportFragmentManager().beginTransaction();

		Fragment newPage=Fragment.instantiate(this, childFragmentArray[toIndex]);
		if(toIndex>curPageIndex)
		{
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
		}else
		{
			transac.setCustomAnimations(0, 0, 0, R.anim.view_disappear);
		}
		transac.replace(containerId, newPage, childFragmentArray[toIndex]);

		if(toIndex>curPageIndex)
		{
			transac.addToBackStack(null);
		}

		transac.commit();
		
		curPageIndex=toIndex;

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
