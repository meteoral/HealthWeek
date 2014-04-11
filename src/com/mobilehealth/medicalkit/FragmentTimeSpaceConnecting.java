package com.mobilehealth.medicalkit;

import com.mobilehealth.core.MainFrameMessageListener;
import com.siat.healthweek.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FragmentTimeSpaceConnecting extends Fragment implements MainFrameMessageListener{
	
	private ImageView ivContainerFrameBg;
	private RelativeLayout pageContainer;
	
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
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void init(View layout)
	{
		ivContainerFrameBg=(ImageView)layout.findViewById(R.id.ivContainerFrameBg);
		pageContainer=(RelativeLayout)layout.findViewById(R.id.rlMain);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_right_bg);

		View new_page=View.inflate(this.getActivity(), R.layout.page_timespace_connecting, null);
		pageContainer.addView(new_page,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return false;
	}
}
