package com.mobilehealth.core;

import java.util.ArrayList;

import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFrameForMedicalKit extends FragmentActivity implements ChildPageMessageListener{

	protected ViewPager vpContent;
	protected FragmentListAdapter vpAdapter;

	protected ImageView ivTab0;
	protected ImageView ivTab1;
	protected ImageView ivTab2;

	protected ImageView ivCurSubjectIcon;
	protected TextView tvCenterCaption;
	protected TextView tvRightCaption;
	protected ImageView ivBack;
	
	protected ArrayList<ArrayList<String>> centerCaptions=new ArrayList<ArrayList<String>>();
	private int[] childPageIndexes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_frame_for_medicalkit);

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
		ivBack=(ImageView)findViewById(R.id.ivBack);

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
		
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				disposeBack();
			}
		});
		
		vpContent.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				MainFrameForMedicalKit.this.tvCenterCaption.setText(centerCaptions.get(vpContent.getCurrentItem()).get(childPageIndexes[vpContent.getCurrentItem()]));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		{
			ArrayList<String> temp=new ArrayList<String>();
			temp.add("");
			temp.add(getResources().getString(R.string.physical_health_capthion));
			centerCaptions.add(temp);
			
			temp=new ArrayList<String>();
			temp.add("");
			centerCaptions.add(temp);
			
			temp=new ArrayList<String>();
			temp.add("");
			centerCaptions.add(temp);
		}
		childPageIndexes=new int[]{0, 0, 0};
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(disposeBack()==true)
			{
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private boolean disposeBack()
	{
		boolean ret_val=((ParentPageMessageListener)vpAdapter.getItem(vpContent.getCurrentItem())).onBack();
		if(ret_val==true)
		{
			return true;
		}
		
		Intent intent=new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		this.finish();
		
		return true;
	}

	@Override
	public void changeToPage(int toIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void childPageChanged(int firstLevelIndex, int secondLevelIndex) {
		// TODO Auto-generated method stub
		if(firstLevelIndex==vpContent.getCurrentItem())
		{
			childPageIndexes[firstLevelIndex]=secondLevelIndex;
			this.tvCenterCaption.setText(centerCaptions.get(firstLevelIndex).get(secondLevelIndex));
		}
	}
}
