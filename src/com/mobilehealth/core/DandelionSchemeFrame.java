package com.mobilehealth.core;

import com.siat.healthweek.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DandelionSchemeFrame extends Activity {

	protected RelativeLayout rlContent;
	protected ImageView ivCurSubjectIcon;

	protected TextView tvCenterCaption;
	protected TextView tvRightCaption;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dandelion_scheme_frame);
		
		init();
	}
	
	private void init()
	{
		rlContent=(RelativeLayout)findViewById(R.id.rlContent);
		tvCenterCaption=(TextView)findViewById(R.id.tvCenterCaption);
		tvRightCaption=(TextView)findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon=(ImageView)findViewById(R.id.ivCurSubjectIcon);
		
		populateContent();
	}
	
	protected void populateContent()
	{
	}

}
