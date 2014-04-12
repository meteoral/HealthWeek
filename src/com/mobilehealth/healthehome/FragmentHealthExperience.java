package com.mobilehealth.healthehome;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class FragmentHealthExperience extends Fragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_health_experience, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		((ChildPageMessageListener)getParentFragment()).changeCenterCaption(getResources().getString(R.string.health_experience), View.VISIBLE);
		
		Animation anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_emerge);
		view.startAnimation(anim);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Animation anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_disappear);
		this.getView().startAnimation(anim);
		
		((ChildPageMessageListener)getParentFragment()).changeCenterCaption(getResources().getString(R.string.health_experience), View.INVISIBLE);
		
		super.onDestroyView();
	}
}
