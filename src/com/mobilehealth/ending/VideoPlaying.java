package com.mobilehealth.ending;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

public class VideoPlaying extends FragmentActivity implements ChildPageMessageListener{


	protected String[] childFragmentArray;

	protected int curPageIndex=0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_frame_for_ending);
		init();
	}

	protected void init() {
		this.childFragmentArray=new String[]{
				FragmentVideoPlayingMainPage.class.getName(),
				FragmentVideoPlayingStart.class.getName()};
		Fragment newPage=getSupportFragmentManager().findFragmentByTag(childFragmentArray[curPageIndex]);
		if(newPage==null)
		{
			FragmentTransaction transac=getSupportFragmentManager().beginTransaction();

			newPage=Fragment.instantiate(this, childFragmentArray[curPageIndex]);
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
			transac.add(R.id.rlContentEnding, newPage, childFragmentArray[curPageIndex]);

			transac.commit();
		}
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
		transac.replace(R.id.rlContentEnding, newPage, childFragmentArray[toIndex]);

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
}
