package com.mobilehealth.ending;

import com.mobilehealth.core.ParentFragmentActivity;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

public class VideoPlaying extends ParentFragmentActivity{

	public VideoPlaying() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.main_frame_for_ending;
		this.containerId=R.id.rlContentEnding;
		this.backActivity=MainActivity.class;
		this.initPageClassName=FragmentVideoPlayingMainPage.class.getName();
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
	}
}
