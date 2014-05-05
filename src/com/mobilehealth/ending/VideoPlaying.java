package com.mobilehealth.ending;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.mobilehealth.core.MainFrameForDandelionScheme;
import com.siat.healthweek.R;

public class VideoPlaying extends MainFrameForDandelionScheme{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		LayoutParams ivLayoutParam=ivCurSubjectIcon.getLayoutParams();
		ivLayoutParam.width=(int) getResources().getDimension(R.dimen.icon_width);
		ivLayoutParam.height=(int) getResources().getDimension(R.dimen.icon_height);
		ivCurSubjectIcon.setImageResource(R.drawable.icon_end);
		
		tvRightCaption.setVisibility(View.INVISIBLE);
		
		this.childFragmentArray=new String[]{
				FragmentVideoPlayingMainPage.class.getName(),
				FragmentVideoPlayingStart.class.getName()};
		
		Fragment newPage=getSupportFragmentManager().findFragmentByTag(childFragmentArray[curPageIndex]);
		if(newPage==null)
		{
			FragmentTransaction transac=getSupportFragmentManager().beginTransaction();
			
			newPage=Fragment.instantiate(this, childFragmentArray[curPageIndex]);
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
			transac.add(R.id.rlContent, newPage, childFragmentArray[curPageIndex]);
			
			transac.commit();
		}
	}
}
