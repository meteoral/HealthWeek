package com.mobilehealth.dandelionscheme;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.mobilehealth.core.ParentFragmentActivity;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

public class DandelionScheme extends ParentFragmentActivity{
	
	private ImageView ivBack;
	
	public DandelionScheme() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.main_frame_for_dandelion_scheme;
		this.containerId=R.id.rlContent;
		this.backActivity=MainActivity.class;
		this.curPageClassName=FragmentDandelionSchemeMainPage.class.getName();
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		ivBack = (ImageView) findViewById(R.id.ivBack);

		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				disposeBtnBack();
			}
		});
	}
}
