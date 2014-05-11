package com.mobilehealth.ending;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.VideoView;

import com.siat.healthweek.R;

public class ActivityPlayingVideo extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.page_video_playing_start);
		
		init();
	}
	
	private void init()
	{
		VideoView mVideoView =(VideoView)findViewById(R.id.video_view);
		mVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video);
		mVideoView.start();
	}
}
