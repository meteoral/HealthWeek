package com.mobilehealth.starting;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;
import com.siat.healthweek.ui.Capture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class FragmentQRCodeOperationMainPage extends Fragment{
	
	private RelativeLayout rlScanQRCode;
	private RelativeLayout rlGenerateQRCode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_qrcode_operation, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		init(view);
		
		((ChildPageMessageListener)getActivity()).childPageChanged(-1, 0);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void init(View layout)
	{
		rlScanQRCode = (RelativeLayout) layout.findViewById(R.id.rlScanQRCode);
		rlGenerateQRCode = (RelativeLayout) layout.findViewById(R.id.rlGenerateQRCode);

		rlScanQRCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//((ChildPageMessageListener)getActivity()).changeToPage(1);
				Intent intent = new Intent(getActivity(), Capture.class);
				startActivity(intent);
			}
		});
		
		rlGenerateQRCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getActivity()).changeToPage(2);
			}
		});
	}
}
