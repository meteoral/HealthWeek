package com.mobilehealth.ending;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentVideoPlayingMainPage extends Fragment{

private ImageView ivVideoPlayingPush;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.page_video_playing, container, false);

		//return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		init(view);

		((ChildPageMessageListener)getActivity()).childPageChanged(-1, 0);

		super.onViewCreated(view, savedInstanceState);
	}

	private void init(View layout)
	{
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
