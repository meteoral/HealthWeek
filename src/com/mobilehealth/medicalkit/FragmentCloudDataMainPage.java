package com.mobilehealth.medicalkit;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FragmentCloudDataMainPage extends Fragment{
	
	private ImageView ivPhysicalHealthResult;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_cloud_data, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		init(view);
		
		Animation anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_emerge);
		view.startAnimation(anim);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void init(View layout)
	{
		ivPhysicalHealthResult = (ImageView) layout.findViewById(R.id.ivPhysicalHealthResult);

		ivPhysicalHealthResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getParentFragment()).changeToPage(1);
			}
		});
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Animation anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_disappear);
		this.getView().startAnimation(anim);
		
		super.onDestroyView();
	}
	
	
}
