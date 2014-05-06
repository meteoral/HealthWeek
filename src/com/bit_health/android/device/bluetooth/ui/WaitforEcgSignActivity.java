package com.bit_health.android.device.bluetooth.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.activities.TestXinDianActivity;
import com.siat.healthweek.R;

public class WaitforEcgSignActivity extends BaseActivity {

	public static final String TAG_ACTIVIYT = "WaitforEcgSignActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.waitforsignlayout);
		Button btSwich = (Button) findViewById(R.id.switchbluetoothid);
		btSwich.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(TestXinDianActivity.RESULT_CODE_SWITCH_FLAG);
				WaitforEcgSignActivity.this.finish();
			}
		});
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (LinearLayout)findViewById(R.id.waitfoecgsignid);
	}
}
