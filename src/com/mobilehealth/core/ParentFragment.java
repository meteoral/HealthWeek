package com.mobilehealth.core;

import java.util.List;

import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ParentFragment extends Fragment implements ChildPageMessageListener, ParentPageMessageListener{
	
	protected ImageView ivContainerFrameBg;
	
	protected String[] childFragmentArray;
	
	private int curPageIndex=0;
	
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
		
		removeAllChildFragments();
		
		FragmentTransaction transac=getChildFragmentManager().beginTransaction();
		
		Fragment newPage=Fragment.instantiate(getActivity(), childFragmentArray[curPageIndex]);
		transac.add(R.id.rlMain, newPage);
		
		transac.commit();
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	protected void init(View layout)
	{
		ivContainerFrameBg=(ImageView)layout.findViewById(R.id.ivContainerFrameBg);
	}
	
	private boolean changeToPageLocal(int toIndex)
	{
		if(toIndex==curPageIndex|toIndex<0)
		{
			return false;
		}
		curPageIndex=toIndex;
		
		FragmentTransaction transac=getChildFragmentManager().beginTransaction();
		
		Fragment newPage=Fragment.instantiate(getActivity(), childFragmentArray[curPageIndex]);
		transac.replace(R.id.rlMain, newPage);
		
		transac.commit();
		
		return true;
	}
	
	private void removeAllChildFragments()
	{
		List<Fragment> frags=getChildFragmentManager().getFragments();
		if(frags==null)
		{
			return;
		}
		
		FragmentTransaction transac=getChildFragmentManager().beginTransaction();
		for(int i=0;i<frags.size();i++)
		{
			transac.remove(frags.get(i));
		}
		transac.commit();
	}

	@Override
	public void changeToPage(int toIndex) {
		// TODO Auto-generated method stub
		changeToPageLocal(toIndex);
	}

	@Override
	public void changeCenterCaption(String str, int visibility) {
		// TODO Auto-generated method stub
		((ChildPageMessageListener)getActivity()).changeCenterCaption(str, visibility);
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return changeToPageLocal(curPageIndex-1);
	}
}
