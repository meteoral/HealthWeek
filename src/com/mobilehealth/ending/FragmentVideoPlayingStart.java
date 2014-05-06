package com.mobilehealth.ending;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.siat.healthweek.R;

public class FragmentVideoPlayingStart extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().setContentView(R.layout.page_video_playing_start);
		VideoView mVideoView =(VideoView)getActivity().findViewById(R.id.video_view);
		Uri mUri = Uri.parse("/sdcard/video.mp4");
		mVideoView.setVideoURI(mUri);
		mVideoView.start();
		return inflater.inflate(R.layout.page_video_playing_start, container, false);

		//return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
}
