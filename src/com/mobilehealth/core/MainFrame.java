package com.mobilehealth.core;

import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFrame extends FragmentActivity implements ChildPageMessageListener{

	protected ViewPager vpContent;
	protected FragmentListAdapter vpAdapter;

	protected ImageView ivTab0;
	protected ImageView ivTab1;
	protected ImageView ivTab2;

	protected ImageView ivCurSubjectIcon;
	protected TextView tvCenterCaption;
	protected TextView tvRightCaption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_frame);

		init();
	}

	protected void init() {
		vpContent = (ViewPager) findViewById(R.id.vpContent);

		ivTab0 = (ImageView) findViewById(R.id.ivCloudData);
		ivTab1 = (ImageView) findViewById(R.id.ivHealthKnowledge);
		ivTab2 = (ImageView) findViewById(R.id.ivTimeSpaceConnecting);
		tvCenterCaption = (TextView) findViewById(R.id.tvCenterCaption);
		tvRightCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);

		ivTab0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(vpContent.getCurrentItem()!=0)
				{
					vpContent.setCurrentItem(0);
				}
			}
		});
		ivTab1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(vpContent.getCurrentItem()!=1)
				{
					vpContent.setCurrentItem(1);
				}
			}
		});
		ivTab2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(vpContent.getCurrentItem()!=2)
				{
					vpContent.setCurrentItem(2);
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			boolean ret_val=((ParentPageMessageListener)vpAdapter.getItem(vpContent.getCurrentItem())).onBack();
			if(ret_val==true)
			{
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void changeToPage(int toIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeCenterCaption(String str, int visibility) {
		// TODO Auto-generated method stub
		this.tvCenterCaption.setText(str);
		this.tvCenterCaption.setVisibility(visibility);
	}
}
