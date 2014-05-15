package com.mobilehealth.core;

import java.util.HashMap;

import com.mobilehealth.medicalkit.FragmentPhysicalHealthResult;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class ActivityViewPager extends FragmentActivityEx{

	protected ViewPager vpContent;
	protected FragmentListAdapter vpAdapter;

	protected ImageView[] ivTabs;

	protected ImageView ivCurSubjectIcon;
	protected TextView tvCenterCaption;
	protected TextView tvRightCaption;
	protected ImageView ivBack;
	
	//必须注意此处，每当添加了一个page，就必须在centerCaptions中添加一个对应的centerCaption字符串
	protected HashMap<String, String> centerCaptions=new HashMap<String, String>();
	
	private String[] childPageNames;
	
	protected int[] bottom_tab_on_selectors;
	protected int[] bottom_tab_off_selectors;
	
	private int curTabIndex=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_frame_for_medicalkit);

		init();
	}

	protected void init() {
		vpContent = (ViewPager) findViewById(R.id.vpContent);

		ivTabs=new ImageView[3];
		ivTabs[0] = (ImageView) findViewById(R.id.ivCloudData);
		ivTabs[1] = (ImageView) findViewById(R.id.ivHealthKnowledge);
		ivTabs[2] = (ImageView) findViewById(R.id.ivTimeSpaceConnecting);
		tvCenterCaption = (TextView) findViewById(R.id.tvCenterCaption);
		tvRightCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);
		ivBack=(ImageView)findViewById(R.id.ivBack);

		ivTabs[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				curTabIndex=vpContent.getCurrentItem();
				if(curTabIndex!=0)
				{
					vpContent.setCurrentItem(0);
				}
			}
		});
		ivTabs[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				curTabIndex=vpContent.getCurrentItem();
				if(curTabIndex!=1)
				{
					vpContent.setCurrentItem(1);
				}
			}
		});
		ivTabs[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				curTabIndex=vpContent.getCurrentItem();
				if(curTabIndex!=2)
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
				if(arg0!=curTabIndex)
				{
					ActivityViewPager.this.tvCenterCaption.setText(centerCaptions.get(childPageNames[arg0]));
					bottomTabSelectionChanged(arg0);
				}
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
			centerCaptions.put(FragmentPhysicalHealthResult.class.getName(), getResources().getString(R.string.physical_health_capthion));
		}
		childPageNames=new String[]{"", "", ""};
		
	}
	
	private void bottomTabSelectionChanged(int selectedIndex)
	{
		ivTabs[curTabIndex].setImageResource(bottom_tab_off_selectors[curTabIndex]);
		
		ivTabs[selectedIndex].setImageResource(bottom_tab_on_selectors[selectedIndex]);
		
		curTabIndex=selectedIndex;
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
		boolean ret_val=((ParentFragment)vpAdapter.getItem(vpContent.getCurrentItem())).onBack();
		if(ret_val==true)
		{
			return true;
		}
		
		Intent intent=new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		
		return true;
	}

	public void childPageChanged(int firstLevelIndex, String className) {
		if(firstLevelIndex==curTabIndex)
		{
			childPageNames[curTabIndex]=className;
			
			if(centerCaptions.containsKey(className))
			{
				this.tvCenterCaption.setText(centerCaptions.get(className));
			}else
			{
				this.tvCenterCaption.setText("");
			}
		}
	}
}
