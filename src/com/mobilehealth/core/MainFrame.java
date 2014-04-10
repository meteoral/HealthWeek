package com.mobilehealth.core;

import com.siat.healthweek.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainFrame extends Activity {

	protected RelativeLayout rlContent;
	protected ImageView ivCloudData;
	protected ImageView ivHealthKnowledge;
	protected ImageView ivTimeSpaceConnecting;
	
	protected ImageView ivCurSubjectIcon;
	
	protected TextView tvCenterCaption;
	protected TextView tvRightCaption;
	
	protected ViewFlipper vfBgFrame;
	private int curViewIndex=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_frame);

		init();
	}
	
	private void init()
	{
		rlContent=(RelativeLayout)findViewById(R.id.rlContent);
		ivCloudData=(ImageView)findViewById(R.id.ivCloudData);
		ivHealthKnowledge=(ImageView)findViewById(R.id.ivHealthKnowledge);
		ivTimeSpaceConnecting=(ImageView)findViewById(R.id.ivTimeSpaceConnecting);
		vfBgFrame=(ViewFlipper)findViewById(R.id.vfBgFrame);
		tvCenterCaption=(TextView)findViewById(R.id.tvCenterCaption);
		tvRightCaption=(TextView)findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon=(ImageView)findViewById(R.id.ivCurSubjectIcon);
		
		populateContent();
		
		ivCloudData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeBgFrame(0);
			}
		});
		ivHealthKnowledge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeBgFrame(1);
			}
		});
		ivTimeSpaceConnecting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeBgFrame(2);
			}
		});
	}
	
	protected void populateContent()
	{
	}
	
	private void changeBgFrame(int index)
	{
		if(index==curViewIndex)
		{
			return;
		}
		if((index-curViewIndex)==1)
		{
			vfBgFrame.showNext();
		}else if((index-curViewIndex)==2)
		{
			vfBgFrame.showNext();
			vfBgFrame.showNext();
		}else if((curViewIndex-index)==1)
		{
			vfBgFrame.showPrevious();
		}else if((curViewIndex-index)==2)
		{
			vfBgFrame.showPrevious();
			vfBgFrame.showPrevious();
		}
		curViewIndex=index;
	}
	
}
