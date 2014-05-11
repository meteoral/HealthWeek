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
	
	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
		this.layoutId=R.layout.main_frame_for_qrcode_operation;
	}

	@Override
	protected void setContainer() {
		// TODO Auto-generated method stub
		this.containerId=R.id.rlContent;
	}
	
	@Override
	protected void setBackActivity() {
		// TODO Auto-generated method stub
		this.backActivity=MainActivity.class;
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
		
		this.childFragmentArray=new String[]{
				FragmentQRCodeOperationMainPage.class.getName(),
				FragmentScanQRCode.class.getName(),
				FragmentGenerateQRCode.class.getName(),
				FragmentGenerationSucceed.class.getName()};
	}

	@Override
	public void childPageChanged(int firstLevelIndex, String className) {
		// TODO Auto-generated method stub
		super.childPageChanged(firstLevelIndex, className);
		
		if(getCurPageIndex()==0)
		{
			ivCurSubjectIcon.setImageResource(R.drawable.icon_starting);
			ivCurSubjectIconOnBottom.setVisibility(View.INVISIBLE);
			tvCaption.setText("");
		}else if(getCurPageIndex()==1)
		{
			ivCurSubjectIcon.setImageResource(R.drawable.scan_qrcode_icon);
			ivCurSubjectIconOnBottom.setVisibility(View.VISIBLE);
			tvCaption.setText(getResources().getString(R.string.scan_qrcode));
		}else if(getCurPageIndex()==2)
		{
			ivCurSubjectIcon.setImageResource(R.drawable.generate_qrcode_icon);
			ivCurSubjectIconOnBottom.setVisibility(View.VISIBLE);
			tvCaption.setText(getResources().getString(R.string.generate_qrcode));
		}
	}
}
