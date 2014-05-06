package com.bit_health.android.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bit_health.android.constants.AndroidDeviceConst;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.siat.healthweek.R;

public class TestMainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.test_main);
		AndroidDeviceConst.Initialization(this.getApplicationContext());
		Button btTestXindian = (Button)findViewById(R.id.testxindianid);
		btTestXindian.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.testxindianid){
			AndroidActivityMananger.getInstatnce().showSelectDeviceTestDialog(this);
		}
	}
}
