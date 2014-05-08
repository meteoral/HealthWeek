package com.mobilehealth.customizedview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageViewMovable extends ImageView{
	
	private int lastX, lastY;
	private int legalMovingWidth, legalMovingHeight;
	
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
			legalMovingWidth = ((View)this.getParent()).getWidth();
	        legalMovingHeight = ((View)this.getParent()).getHeight();
	        
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
			
			if(right>legalMovingWidth)
			{
				right=legalMovingWidth;
				left=right-v.getWidth();
			}
			
			if(top<0)
			{
				top=0;
				bottom=top+v.getHeight();
			}
			
			if(top>legalMovingHeight)
			{
				bottom=legalMovingHeight;
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
