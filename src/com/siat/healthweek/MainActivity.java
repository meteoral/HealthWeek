package com.siat.healthweek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.mobilehealth.dandelionscheme.DandelionScheme;
import com.mobilehealth.healthehome.HealthEHome;
import com.mobilehealth.medicalkit.MedicalKit;
import com.siat.healthweek.R;
import com.siat.healthweek.ui.Capture;

/**
 * 应用程序主界面：主界面
 *
 * @author 刘清伟Meteoral（http://www.liuqingwei.com）
 * @created 2014-04-06
 * @version 1.0
 */
public class MainActivity extends Activity {
	private Button btnMain, btnXu, btnEhome, btnBag, btnWei, btnTran, btnBed;
	private static Boolean isClick = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		initialButton();
	}

	private void initialButton() {
		btnMain = (Button) findViewById(R.id.main_button);
		btnXu = (Button) findViewById(R.id.main_button_xu);
		btnEhome = (Button) findViewById(R.id.main_button_ehome);
		btnBag = (Button) findViewById(R.id.main_button_bag);
		btnWei = (Button) findViewById(R.id.main_button_wei);
		btnTran = (Button) findViewById(R.id.main_button_tran);
		btnBed = (Button) findViewById(R.id.main_button_bed);

		btnXu.setVisibility(View.INVISIBLE);
		btnEhome.setVisibility(View.INVISIBLE);
		btnBag.setVisibility(View.INVISIBLE);
		btnWei.setVisibility(View.INVISIBLE);
		btnTran.setVisibility(View.INVISIBLE);
		btnBed.setVisibility(View.INVISIBLE);

		Animation ani = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.translucent_button_zoom_in);
		btnMain.setAnimation(ani);
		ani.setDuration(2000);
		ani.start();
		btnMain.setOnClickListener(new OnClickListener() {

			Animation income = (AnimationSet) AnimationUtils.loadAnimation(MainActivity.this,R.anim.translucent_button_zoom_in);
			@Override
			public void onClick(View v) {
					btnXu.setVisibility(View.VISIBLE);
					btnEhome.setVisibility(View.VISIBLE);
					btnBag.setVisibility(View.VISIBLE);
					btnWei.setVisibility(View.VISIBLE);
					btnTran.setVisibility(View.VISIBLE);
					btnBed.setVisibility(View.VISIBLE);
					btnXu.setAnimation(income);
					btnEhome.setAnimation(income);
					btnBag.setAnimation(income);
					btnWei.setAnimation(income);
					btnTran.setAnimation(income);
					btnBed.setAnimation(income);
					income.setDuration(1000);
					income.start();
			}
		});
		btnXu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Capture.class);
				startActivity(intent);
			}
		});

		btnBag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(MainActivity.this, Medicalkit.class);
				Intent intent = new Intent(MainActivity.this, MedicalKit.class);
				startActivity(intent);
			}
		});
		btnBed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AverageCubicTemperatureChart.class);
				startActivity(intent);
			}
		});
		btnEhome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, HealthEHome.class);
				startActivity(intent);
			}
		});
		btnTran.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DandelionScheme.class);
				startActivity(intent);
			}
		});
	}

}
