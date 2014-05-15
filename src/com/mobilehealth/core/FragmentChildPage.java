package com.mobilehealth.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentChildPage extends Fragment{

	protected int layoutId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
			((ParentFragmentActivity)parentActivity).childPageChanged(this.getClass().getName());
		}else
		{
			((ParentFragment)parentFragment).childPageChanged(this.getClass().getName());
		}
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		
		saveData();
	}

	protected abstract void init(View layout);
	protected void saveData(){};
}
