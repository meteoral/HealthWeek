package com.mobilehealth.medicalkit;

import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class FragmentPhysicalHealthResult extends Fragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_physical_health_result, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Animation anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_emerge);
		view.startAnimation(anim);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Animation anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_disappear);
		this.getView().startAnimation(anim);
		
		super.onDestroyView();
	}
}
