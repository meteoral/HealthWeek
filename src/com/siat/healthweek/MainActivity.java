package com.siat.healthweek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.mobilehealth.dandelionscheme.StartingDisplayBodyCommu;
import com.mobilehealth.ending.ActivityPlayingVideo;
import com.mobilehealth.healthehome.StartingDisplayHealthEHome;
import com.mobilehealth.medicalkit.StartingDisplayMedicalkit;
import com.mobilehealth.sensibelbed.StartingDisplaySensibleBed;
import com.mobilehealth.starting.QRCodeOperation;

/**
 * 应用程序主界面：主界面
 *
 * @author 刘清伟Meteoral（http://www.liuqingwei.com）
 * @created 2014-04-06
 * @version 1.0
 */
public class MainActivity extends Activity {
	private Button btnHealthpaper, btnXu, btnEhome, btnBag, btnWei, btnTran,
			btnBed, btnHaodou, btnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		initialButton();
	}

	private void initialButton() {
		btnHealthpaper = (Button) findViewById(R.id.main_button_healthpaper);
		btnXu = (Button) findViewById(R.id.main_button_xu);
		btnEhome = (Button) findViewById(R.id.main_button_ehome);
		btnBag = (Button) findViewById(R.id.main_button_bag);
		btnWei = (Button) findViewById(R.id.main_button_wei);
		btnTran = (Button) findViewById(R.id.main_button_tran);
		btnBed = (Button) findViewById(R.id.main_button_bed);
		btnHaodou = (Button) findViewById(R.id.main_button_haodou);
		btnAbout = (Button) findViewById(R.id.main_button_about);
		Animation income = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.translucent_button_zoom_in);
		btnXu.setAnimation(income);
		btnEhome.setAnimation(income);
		btnBag.setAnimation(income);
		btnWei.setAnimation(income);
		btnTran.setAnimation(income);
		btnBed.setAnimation(income);
		btnHaodou.setAnimation(income);
		btnHealthpaper.setAnimation(income);
		btnAbout.setAnimation(income);
		income.setDuration(1000);
		income.start();
		btnXu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * Intent intent = new Intent(MainActivity.this, Capture.class);
				 * startActivity(intent);
				 */
				/*
				 * Intent intent = new Intent(MainActivity.this,
				 * QRCodeOperation.class); startActivity(intent);
				 */

				startChildApp(QRCodeOperation.class);
			}
		});

		btnBag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startChildApp(StartingDisplayMedicalkit.class);
			}
		});

		btnBed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startChildApp(StartingDisplaySensibleBed.class);
			}
		});

		btnEhome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startChildApp(StartingDisplayHealthEHome.class);
			}
		});

		btnTran.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startChildApp(StartingDisplayBodyCommu.class);
			}
		});

		btnWei.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * Intent intent = new Intent(MainActivity.this,
				 * VideoPlaying.class); startActivity(intent);
				 */

				startChildApp(ActivityPlayingVideo.class);
			}
		});
		btnHaodou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startChildApp(HaoDou.class);
			}
		});
		btnHealthpaper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startChildApp(HealthPaper.class);
			}
		});
		btnAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startChildApp(AboutUs.class);
			}
		});
	}

	private void startChildApp(Class<?> clazz) {
		Intent intent = new Intent(MainActivity.this, clazz);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

}
