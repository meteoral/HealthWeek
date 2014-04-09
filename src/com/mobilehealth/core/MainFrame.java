package com.mobilehealth.core;

import com.liuqingwei.healthweek.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainFrame extends Activity {

	protected RelativeLayout rlContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_frame);

		init();
	}
	
	private void init()
	{
		rlContent=(RelativeLayout)findViewById(R.id.rlContent);
		popularContent();
	}
	
	protected void popularContent()
	{
	}
	
}
