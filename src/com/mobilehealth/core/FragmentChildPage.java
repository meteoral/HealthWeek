package com.mobilehealth.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentChildPage extends Fragment{
	
	protected int layoutId;
	protected int pageIndex=-1;
	
	protected abstract void setLayout();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setLayout();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(layoutId, container, false);
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		init(view);
		
		Fragment parentFragment=getParentFragment();
		if(parentFragment==null)
		{
			Activity parentActivity=getActivity();
			pageIndex=((ChildPageMessageListener)parentActivity).getPageIndex(this.getClass().getName());
			((ChildPageMessageListener)parentActivity).childPageChanged(-1, pageIndex);
		}else
		{
			pageIndex=((ChildPageMessageListener)parentFragment).getPageIndex(this.getClass().getName());
			((ChildPageMessageListener)parentFragment).childPageChanged(-1, pageIndex);
		}
	}
	
	protected abstract void init(View layout);
}
