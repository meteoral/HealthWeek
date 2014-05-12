package com.mobilehealth.dandelionscheme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.mobilehealth.core.MainFrameForDandelionScheme;
import com.siat.healthweek.R;

public class DandelionScheme extends MainFrameForDandelionScheme{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		this.childFragmentArray=new String[]{
				FragmentDandelionSchemeMainPage.class.getName(),
				FragmentBodyCommu.class.getName(),
				FragmentIdeaSharer.class.getName()};
		
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
