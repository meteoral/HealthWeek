package com.mobilehealth.sensibelbed;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

public class FragmentHealthCheck extends ParentFragment{
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_left_bg);
		
		this.curPageClassName=FragmentHealthCheckMainPage.class.getName();
		
		this.firstLevelIndex=0;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		Fragment curPage=getChildFragmentManager().findFragmentByTag(curPageClassName);
		if(curPage!=null)
		{
			curPage.onActivityResult(requestCode, resultCode, data);
		}
		
		//super.onActivityResult(requestCode, resultCode, data);
	}
}
