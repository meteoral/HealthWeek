package com.mobilehealth.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siat.healthweek.R;

public class StartingDisplay extends Activity{

	protected RelativeLayout rlMain;
	protected ImageView ivClickEnter;
	protected TextView tvDescriptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medicalkit_starting_display);
		init();
	}

	protected void init()
	{
		rlMain=(RelativeLayout)findViewById(R.id.rlMain);
		ivClickEnter=(ImageView)findViewById(R.id.ivClickEnter);
		tvDescriptions=(TextView)findViewById(R.id.tvDescriptions);

		/*Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/fangzhenghuali.ttf");
		tvDescriptions.setTypeface(typeFace);*/
	}

	protected void startAnotherActivity(Class<?> clazz)
	{
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
}
