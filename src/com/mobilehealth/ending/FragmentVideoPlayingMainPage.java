package com.mobilehealth.ending;

import com.mobilehealth.core.ChildPageMessageListener;
import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentVideoPlayingMainPage extends FragmentChildPage{

    private ImageView ivVideoPlayingPush;

	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
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
				((ChildPageMessageListener)getActivity()).changeToPage(1);
			}
		});
	}
}
