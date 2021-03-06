package com.mobilehealth.starting;

import com.mobilehealth.core.FragmentChildPage;
import com.mobilehealth.core.ParentFragmentActivity;
import com.siat.healthweek.R;
import com.siat.healthweek.ui.Capture;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class FragmentQRCodeOperationMainPage extends FragmentChildPage{

	private RelativeLayout rlScanQRCode;
	private RelativeLayout rlGenerateQRCode;

	public FragmentQRCodeOperationMainPage() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.page_qrcode_operation;
	}

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		rlScanQRCode = (RelativeLayout) layout.findViewById(R.id.rlScanQRCode);
		rlGenerateQRCode = (RelativeLayout) layout.findViewById(R.id.rlGenerateQRCode);

		rlScanQRCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//((ChildPageMessageListener)getActivity()).changeToPage(FragmentScanQRCode.class);
				Intent intent = new Intent(getActivity(), Capture.class);
				startActivity(intent);
				getActivity().overridePendingTransition(0, R.anim.view_disappear);
			}
		});

		rlGenerateQRCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ParentFragmentActivity)getActivity()).changeToPage(FragmentGenerateQRCode.class);
			}
		});
	}
}
