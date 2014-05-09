package com.mobilehealth.core;

import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class MainFrameForDandelionScheme extends FragmentActivity implements ChildPageMessageListener{

	protected ImageView ivCurSubjectIcon;
	protected TextView tvRightCaption;
	protected ImageView ivBack;

	protected String[] childFragmentArray;

	protected int curPageIndex=0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_frame_for_dandelion_scheme);

		init();
	}

	protected void init() {
		tvRightCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);
		ivBack = (ImageView) findViewById(R.id.ivBack);

		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				disposeBack();
			}
		});
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
		FragmentManager childFragManager=getSupportFragmentManager();
		if(childFragManager.getBackStackEntryCount()>0)
		{
			childFragManager.popBackStack();
			return true;
		}

		Intent intent=new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		this.finish();

		return true;
	}

	private boolean changeToPageLocal(int toIndex)
	{
		if(toIndex==curPageIndex|toIndex<0)
		{
			return false;
		}

		FragmentTransaction transac=getSupportFragmentManager().beginTransaction();

		Fragment newPage=Fragment.instantiate(this, childFragmentArray[toIndex]);
		if(toIndex>curPageIndex)
		{
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
		}else
		{
			transac.setCustomAnimations(0, 0, 0, R.anim.view_disappear);
		}
		transac.replace(R.id.rlContent, newPage, childFragmentArray[toIndex]);

		if(toIndex>curPageIndex)
		{
			transac.addToBackStack(null);
		}

		curPageIndex=toIndex;

		transac.commit();


		return true;
	}

	@Override
	public void changeToPage(int toIndex) {
		// TODO Auto-generated method stub
		changeToPageLocal(toIndex);

		childPageChanged(-1, toIndex);
	}

	@Override
	public void childPageChanged(int firstLeveIndex, int secondLevelIndex) {
		// TODO Auto-generated method stub
		curPageIndex=secondLevelIndex;
	}

	@Override
	public int getPageIndex(String className) {
		// TODO Auto-generated method stub
		for(int i=0;i<childFragmentArray.length;i++)
		{
			if(className.equals(childFragmentArray[i]))
			{
				return i;
			}
		}
		return -1;
	}
}
