package com.mobilehealth.starting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.siat.healthweek.R;

public class QRCodeShow extends Activity {

	protected ImageView pic;
	protected ImageView ivCurSubjectIcon;
	protected TextView tvRightCaption;
	protected ImageView ivBack;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.main_frame_for_qrcode);
		 Intent intent = getIntent();
		 String parm = intent.getStringExtra("name");
		 init(parm);
	}
	protected void init(String parm)
	{
		pic = (ImageView) findViewById(R.id.caption_pic);
		tvRightCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);
		ivBack=(ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				disposeBack();
			}
		});
		if(parm.equals("siat@achievement"))
		{
			pic.setImageResource(R.drawable.achievement);
			tvRightCaption.setText("小领带");
		}
		else if(parm.equals("siat@research"))
		{
			pic.setImageResource(R.drawable.research);
			tvRightCaption.setText("小眼睛");
		}
		else if(parm.equals("siat@popularization"))
		{
			pic.setImageResource(R.drawable.popularization);
			tvRightCaption.setText("小喇叭");
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(disposeBack()==true)
			{
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private boolean disposeBack()
	{
		this.finish();

		return true;
	}
}
