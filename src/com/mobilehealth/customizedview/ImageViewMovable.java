package com.mobilehealth.customizedview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageViewMovable extends ImageView{
	
	private int lastX, lastY;
	private int legalMovingLeft, legalMovingTop;
	private int legalMovingRight, legalMovingBottom;
	
	private MovingListener movingListener;
	
	public ImageViewMovable(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ImageViewMovable(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ImageViewMovable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
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
			
			if(left<legalMovingLeft)
			{
				left=legalMovingLeft;
				right=left+v.getWidth();
			}
			
			if(right>legalMovingRight)
			{
				right=legalMovingRight;
				left=right-v.getWidth();
			}
			
			if(top<legalMovingTop)
			{
				top=legalMovingTop;
				bottom=top+v.getHeight();
			}
			
			if(bottom>legalMovingBottom)
			{
				bottom=legalMovingBottom;
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
	
	public void setLegalMovingRect(int left, int top, int right, int bottom)
	{
		this.legalMovingLeft=left;
		this.legalMovingTop=top;
		this.legalMovingRight=right;
		this.legalMovingBottom=bottom;
	}
	
	public static interface MovingListener
	{
		void onMovingStarted(View v);
		void onMovingFinished(View v);
	}
}
