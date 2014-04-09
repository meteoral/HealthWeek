package com.liuqingwei.healthweek;

import com.liuqingwei.healthweek.ui.Capture;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.liuqingwei.healthweek.R;
/**
 * 应用程序主界面：主界面
 * @author 刘清伟Meteoral（http://www.liuqingwei.com）
 * @created 2014-04-06
 * @version 1.0
 */
public class MainActivity extends Activity {
	private Button captureButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		captureButton = (Button) findViewById(R.id.buttoncapture);
		captureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this, Capture.class);
			     startActivity(intent);
			}
		});

	}


}
