package com.mobilehealth.ending;

import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentVideoPlayingMainPage extends FragmentChildPage{

    private ImageView ivVideoPlayingPush;
    
    public FragmentVideoPlayingMainPage() {
		// TODO Auto-generated constructor stub
    	this.layoutId=R.layout.page_video_playing;
	}

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		ivVideoPlayingPush = (ImageView) layout.findViewById(R.id.ivVideoPlayingPush);

		ivVideoPlayingPush.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//((ChildPageMessageListener)getActivity()).changeToPage(pageIndex+1);
				Intent intent = new Intent(getActivity(), ActivityPlayingVideo.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}
		});
	}
}
