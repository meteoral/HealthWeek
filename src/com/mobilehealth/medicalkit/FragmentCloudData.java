package com.mobilehealth.medicalkit;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCloudData extends Fragment implements ChildPageMessageListener{
	
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
		
		FragmentTransaction transac=getChildFragmentManager().beginTransaction();

		Fragment newPage=Fragment.instantiate(getActivity(), FragmentCloudDataMainPage.class.getName());
		transac.add(R.id.rlMain, newPage);
		
		transac.commit();
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void changeToPageLocal(int toIndex)
	{
		if(toIndex==1)
		{
			FragmentTransaction transac=getChildFragmentManager().beginTransaction();
			
			Fragment newPage=Fragment.instantiate(getActivity(), FragmentPhysicalHealthResult.class.getName());
			transac.replace(R.id.rlMain, newPage);
			
			transac.addToBackStack(null);
			
			transac.commit();
		}else if(toIndex==0)
		{
            FragmentTransaction transac=getChildFragmentManager().beginTransaction();

			Fragment newPage=Fragment.instantiate(getActivity(), FragmentCloudDataMainPage.class.getName());
			transac.replace(R.id.rlMain, newPage);
			
			transac.commit();
		}
	}

	@Override
	public void changeToPage(int toIndex) {
		// TODO Auto-generated method stub
		changeToPageLocal(toIndex);
	}
}
