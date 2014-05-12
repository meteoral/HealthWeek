package com.mobilehealth.core;

import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class ParentFragment extends Fragment implements ChildPageListener, ParentPageListener{
	
	protected ImageView ivContainerFrameBg;
	
	protected String initPageClassName;
	
	protected int firstLevelIndex=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_container, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		init(view);
		
		displayInitPage();
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void displayInitPage()
	{
		Fragment newPage=getChildFragmentManager().findFragmentByTag(initPageClassName);
		if(newPage==null)
		{
			FragmentTransaction transac=getChildFragmentManager().beginTransaction();
			
			newPage=Fragment.instantiate(getActivity(), initPageClassName);
			transac.setCustomAnimations(0, 0, 0, R.anim.view_disappear);
			transac.add(R.id.rlMain, newPage, initPageClassName);
			
			transac.commit();
		}
	}
	
	protected void init(View layout)
	{
		ivContainerFrameBg=(ImageView)layout.findViewById(R.id.ivContainerFrameBg);
		
		firstLevelIndex=0;
	}
	
	private boolean changeToPageLocal(String toPageClassName)
	{
		FragmentTransaction transac=getChildFragmentManager().beginTransaction();
		
		Fragment newPage=Fragment.instantiate(getActivity(), toPageClassName);
		transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
		transac.replace(R.id.rlMain, newPage, toPageClassName);
		transac.addToBackStack(null);
		transac.commit();
		
		return true;
	}
	
	@Override
	public void changeToPage(Class<?> clazz) {
		// TODO Auto-generated method stub
		changeToPageLocal(clazz.getName());
	}

	@Override
	public void childPageChanged(int firstLevelIndex, String className) {
		// TODO Auto-generated method stub
		firstLevelIndex=this.firstLevelIndex;
		((ChildPageListener)getActivity()).childPageChanged(firstLevelIndex, className);
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		FragmentManager childFragManager=getChildFragmentManager();
		if(childFragManager.getBackStackEntryCount()>0)
		{
			childFragManager.popBackStack();
			return true;
		}
		return false;
	}
}
