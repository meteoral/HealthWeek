package com.mobilehealth.customizedview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageViewMovable extends ImageView{
	
	private int lastX, lastY;
	private int screenWidth, screenHeight;
	
	private MovingListener movingListener;
	
	public ImageViewMovable(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public ImageViewMovable(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public ImageViewMovable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init()
	{
		DisplayMetrics dm = getResources().getDisplayMetrics();  
        screenWidth = dm.widthPixels;  
        screenHeight = dm.heightPixels - 80;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		View v=this;
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(movingListener!=null)
			{
				movingListener.onMovingStarted(v);
			}
			
			lastX=(int)event.getRawX();
			lastY=(int)event.getRawY();
		}else if(event.getAction()==MotionEvent.ACTION_MOVE)
		{
			int dx=(int)event.getRawX()-lastX;
			int dy=(int)event.getRawY()-lastY;
			
			int left=v.getLeft()+dx;
			int top=v.getTop()+dy;
			int right=v.getRight()+dx;
			int bottom=v.getBottom()+dy;
			
			if(left<0)
			{
				left=0;
				right=left+v.getWidth();
			}
			
			if(right>screenWidth)
			{
				right=screenWidth;
				left=right-v.getWidth();
			}
			
			if(top<0)
			{
				top=0;
				bottom=top+v.getHeight();
			}
			
			if(top>screenHeight)
			{
				bottom=screenHeight;
				top=bottom-v.getHeight();
			}
			
			v.layout(left, top, right, bottom);
			
			lastX=(int)event.getRawX();
			lastY=(int)event.getRawY();
			
		}else if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(movingListener!=null)
			{
				movingListener.onMovingFinished(v);
			}
		}
		return true;
		//return super.onTouchEvent(event);
	}
	
	public void setMovingListener(MovingListener l)
	{
		this.movingListener=l;
	}
	
	public static interface MovingListener
	{
		void onMovingStarted(View v);
		void onMovingFinished(View v);
	}
}
