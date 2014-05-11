package com.mobilehealth.ending;

import com.mobilehealth.core.ParentFragmentActivity;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

public class VideoPlaying extends ParentFragmentActivity{

	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
		this.layoutId=R.layout.main_frame_for_ending;
	}

	@Override
	protected void setContainer() {
		// TODO Auto-generated method stub
		this.containerId=R.id.rlContentEnding;
	}
	
	@Override
	protected void setBackActivity() {
		// TODO Auto-generated method stub
		this.backActivity=MainActivity.class;
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		this.childFragmentArray=new String[]{
				FragmentVideoPlayingMainPage.class.getName()};
	}
}
