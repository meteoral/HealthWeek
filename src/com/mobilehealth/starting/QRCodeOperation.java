package com.mobilehealth.starting;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilehealth.core.ParentFragmentActivity;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

public class QRCodeOperation extends ParentFragmentActivity{

	private ImageView ivCurSubjectIconOnBottom;
	private ImageView ivCurSubjectIcon;
	private TextView tvCaption;
	private ImageView ivBack;
	
	public QRCodeOperation() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.main_frame_for_qrcode_operation;
		this.containerId=R.id.rlContent;
		this.backActivity=MainActivity.class;
		this.curPageClassName=FragmentQRCodeOperationMainPage.class.getName();
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		tvCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIconOnBottom=(ImageView)findViewById(R.id.ivCurSubjectIconInBottom);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);
		ivBack = (ImageView) findViewById(R.id.ivBack);

		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				disposeBtnBack();
			}
		});
	}

	@Override
	public void childPageChanged(String className) {
		// TODO Auto-generated method stub
		super.childPageChanged(className);
		
		if(className.equals(FragmentQRCodeOperationMainPage.class.getName()))
		{
			ivCurSubjectIcon.setImageResource(R.drawable.icon_starting);
			ivCurSubjectIconOnBottom.setVisibility(View.INVISIBLE);
			tvCaption.setText("");
		}else if(className.equals(FragmentScanQRCode.class.getName()))
		{
			ivCurSubjectIcon.setImageResource(R.drawable.scan_qrcode_icon);
			ivCurSubjectIconOnBottom.setVisibility(View.VISIBLE);
			tvCaption.setText(getResources().getString(R.string.scan_qrcode));
		}else if(className.equals(FragmentGenerateQRCode.class.getName()))
		{
			ivCurSubjectIcon.setImageResource(R.drawable.generate_qrcode_icon);
			ivCurSubjectIconOnBottom.setVisibility(View.VISIBLE);
			tvCaption.setText(getResources().getString(R.string.generate_qrcode));
		}
	}
}
