package com.mobilehealth.starting;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class FragmentGenerateQRCode extends Fragment{
	
	private FrameLayout flInputBlockBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_generate_qrcode, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		init(view);
		
		((ChildPageMessageListener)getActivity()).childPageChanged(-1, 2);
	}
	
	private void init(View layout)
	{
		flInputBlockBox=(FrameLayout)layout.findViewById(R.id.flInputBlockBox);
		
		flInputBlockBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getActivity()).changeToPage(3);
			}
		});
	}
}
