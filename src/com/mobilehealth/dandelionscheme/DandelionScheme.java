package com.mobilehealth.dandelionscheme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;

public class DandelionScheme extends FragmentActivity implements ChildPageMessageListener{

	/*private ImageView ivCurSubjectIcon;
	private TextView tvCenterCaption;
	private TextView tvRightCaption;*/
	
	private String[] childFragmentArray;
	
	private int curPageIndex=0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_frame_for_dandelion_scheme);
		
		init();
	}
	
	private void init()
	{
		/*tvCenterCaption = (TextView) findViewById(R.id.tvCenterCaption);
		tvRightCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);*/
		
		this.childFragmentArray=new String[]{
				FragmentDandelionSchemeMainPage.class.getName(),
				FragmentBodyCommu.class.getName(),
				FragmentIdeaSharer.class.getName()};
		
		Fragment newPage=getSupportFragmentManager().findFragmentByTag(childFragmentArray[curPageIndex]);
		if(newPage==null)
		{
			FragmentTransaction transac=getSupportFragmentManager().beginTransaction();
			
			newPage=Fragment.instantiate(this, childFragmentArray[curPageIndex]);
			transac.setCustomAnimations(R.anim.view_emerge, R.anim.view_disappear, R.anim.view_emerge, R.anim.view_disappear);
			transac.add(R.id.rlContent, newPage, childFragmentArray[curPageIndex]);
			
			transac.commit();
		}
	}
	
	private boolean changeToPageLocal(int toIndex)
	{
		if(toIndex==curPageIndex|toIndex<0)
		{
			return false;
		}

		FragmentTransaction transac=getSupportFragmentManager().beginTransaction();
		
		Fragment newPage=Fragment.instantiate(this, childFragmentArray[toIndex]);
		transac.setCustomAnimations(R.anim.view_emerge, R.anim.view_disappear, R.anim.view_emerge, R.anim.view_disappear);
		transac.replace(R.id.rlContent, newPage, childFragmentArray[toIndex]);
		
		if((toIndex-curPageIndex)==1)
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
	}

	@Override
	public void changeCenterCaption(String str, int visibility) {
		// TODO Auto-generated method stub
		
	}
	

}
