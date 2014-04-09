package com.liuqingwei.healthweek;

import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;

import com.liuqingwei.healthweek.ui.Capture;
/**
 * 应用程序主界面：主界面
 * @author 刘清伟Meteoral（http://www.liuqingwei.com）
 * @created 2014-04-06
 * @version 1.0
 */
public class MainActivity extends Activity {
	private Button btnMain,btnXu,btnEhome,btnBag,btnWei,btnTran,btnBed;
	private Animation animationTranslate, animationRotate, animationScale;
	private LayoutParams params = new LayoutParams(0, 0);
	private static Boolean isClick = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		initialButton();
	}

	private void initialButton(){
		btnMain = (Button) findViewById(R.id.main_button);
		btnXu = (Button) findViewById(R.id.main_button_xu);
		btnEhome = (Button) findViewById(R.id.main_button_ehome);
		btnBag = (Button) findViewById(R.id.main_button_bag);
		btnWei = (Button) findViewById(R.id.main_button_wei);
		btnTran = (Button) findViewById(R.id.main_button_tran);
		btnBed = (Button) findViewById(R.id.main_button_bed);

		Animation ani = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.translucent_zoom_in);
		btnMain.setAnimation(ani);
		ani.setDuration(2000);
		ani.start();
		btnMain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(false==isClick)
				{
					isClick = true;
					btnMain.startAnimation(animRotate(-45.0f, 0.5f, 0.45f));
					btnXu.startAnimation(animTranslate(0.0f, -180.0f, 10, 50 - 240, btnXu, 80));
					btnEhome.startAnimation(animTranslate(30.0f, -150.0f, 60, 50 - 230, btnEhome, 100));
					btnBag.startAnimation(animTranslate(70.0f, -120.0f, 110, 50 - 210, btnBag, 120));
					btnBed.startAnimation(animTranslate(80.0f, -110.0f, 150, 50 - 180, btnBed, 140));
					btnTran.startAnimation(animTranslate(90.0f, -60.0f, 175, 50 - 135, btnTran, 160));
					btnWei.startAnimation(animTranslate(170.0f, -30.0f, 190, 50 - 90, btnWei, 180));

				}

			}
		});
		btnXu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this, Capture.class);
			     startActivity(intent);
			}
		});
	}

	protected Animation setAnimScale(float toX, float toY)
	{
	// TODO Auto-generated method stub
	animationScale = new ScaleAnimation(1f, toX, 1f, toY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.45f);
	animationScale.setInterpolator(MainActivity.this, anim.accelerate_decelerate_interpolator);
	animationScale.setDuration(500);
	animationScale.setFillAfter(false);
	return animationScale;
	}

	protected Animation animRotate(float toDegrees, float pivotXValue, float pivotYValue)
	{
	// TODO Auto-generated method stub
	animationRotate = new RotateAnimation(0, toDegrees, Animation.RELATIVE_TO_SELF, pivotXValue, Animation.RELATIVE_TO_SELF, pivotYValue);
	animationRotate.setAnimationListener(new AnimationListener()
	{

	@Override
	public void onAnimationStart(Animation animation)
	{
	// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animation animation)
	{
	// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation)
	{
	// TODO Auto-generated method stub
	animationRotate.setFillAfter(true);
	}
	});
	return animationRotate;
	}
	//移动的动画效果
	/*
	* TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)
	*
	* float fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值；
	*
	　　 * float toXDelta, 这个参数表示动画结束的点离当前View X坐标上的差值；
	*
	　　 * float fromYDelta, 这个参数表示动画开始的点离当前View Y坐标上的差值；
	*
	　　 * float toYDelta)这个参数表示动画开始的点离当前View Y坐标上的差值；
	*/
	protected Animation animTranslate(float toX, float toY, final int lastX, final int lastY,
	final Button button, long durationMillis)
	{
	// TODO Auto-generated method stub
	animationTranslate = new TranslateAnimation(0, toX, 0, toY);
	animationTranslate.setAnimationListener(new AnimationListener()
	{

	@Override
	public void onAnimationStart(Animation animation)
	{
	// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animation animation)
	{
	// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation)
	{
	// TODO Auto-generated method stub
	params = new LayoutParams(0, 0);
	params.height = 50;
	params.width = 50;
	params.setMargins(lastX, lastY, 0, 0);
	button.setLayoutParams(params);
	button.clearAnimation();
	}
	});
	animationTranslate.setDuration(durationMillis);
	return animationTranslate;
	}
}
