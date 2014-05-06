package com.bit_health.android.ui.views;
 

import com.bit_health.android.constants.AndroidDeviceConst;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/***********************************
 * 该类用于画心电信号的混沌图，继承View     *
 * **********************************
 */

public class EcgChaosView<AttributeSet> extends View {
 
    private Paint mPaint, blackPaint; 
    public int len;
    public float[] x;
    public float[] y;
  
	 public EcgChaosView(Context context, android.util.AttributeSet attrs) {
	     super(context, attrs);  
	     //初始化参数
         mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); 
		 mPaint.setStyle(Paint.Style.FILL);
         mPaint.setStrokeWidth(1); 
         mPaint.setTextSize(2);
         mPaint.setColor(Color.BLACK);
         
         blackPaint = new Paint(); 
         blackPaint.setAntiAlias(true);
         blackPaint.setTextSize(20);
         blackPaint.setTextAlign(Paint.Align.CENTER);
         blackPaint.setColor(Color.BLACK);
         
	 }
 
        @Override 
        public void onDraw(Canvas canvas) {
        	
            canvas.drawColor(Color.argb(255, 255, 255, 224));     
            
            canvas.drawLine(40, 440, AndroidDeviceConst.mScreenWidth-30, 440, mPaint);//画横坐标
            canvas.drawLine(40, 40, 40, 440, mPaint);//竖坐标
            
            canvas.drawLine(30, 50, 40, 40, mPaint);//画y轴箭头
            canvas.drawLine(40, 40, 50, 50, mPaint);
            canvas.drawLine(AndroidDeviceConst.mScreenWidth-30, 430, AndroidDeviceConst.mScreenWidth-20, 440, mPaint);//画x轴箭头
            canvas.drawLine(AndroidDeviceConst.mScreenWidth-30, 450, AndroidDeviceConst.mScreenWidth-20, 440, mPaint);
            
            canvas.drawLine(40, 80, 45, 80, mPaint);
            canvas.drawLine(40, 170, 45, 170, mPaint);
            canvas.drawLine(40, 260, 45, 260, mPaint);
            canvas.drawLine(40, 350, 45, 350, mPaint);
          //画X轴的刻度
            canvas.drawLine((AndroidDeviceConst.mScreenWidth/2+40)/2, 432,(AndroidDeviceConst.mScreenWidth/2+40)/2, 440, mPaint);
            canvas.drawLine(AndroidDeviceConst.mScreenWidth/2, 432, AndroidDeviceConst.mScreenWidth/2, 440, mPaint);
            canvas.drawLine(AndroidDeviceConst.mScreenWidth/2+(AndroidDeviceConst.mScreenWidth/2-40)/2, 
            		432, AndroidDeviceConst.mScreenWidth/2+(AndroidDeviceConst.mScreenWidth/2-40)/2, 440, mPaint);
            canvas.drawLine(AndroidDeviceConst.mScreenWidth/2+(AndroidDeviceConst.mScreenWidth/2-40), 432, 
            		AndroidDeviceConst.mScreenWidth/2+(AndroidDeviceConst.mScreenWidth/2-40),440, mPaint);
            
            //30个像素点代表10心率值
/*            canvas.drawText("120", 20, 80, blackPaint); 
            canvas.drawText("100", 20, 140, blackPaint); 
            canvas.drawText("80", 20, 200, blackPaint); 
            canvas.drawText("60", 20, 260, blackPaint); 
            canvas.drawText("40", 20, 320, blackPaint);
            canvas.drawText("20", 20, 380, blackPaint); 
            canvas.drawText("0", 20, 440, blackPaint);*/
            canvas.drawText("2", 15, 85, blackPaint);
            canvas.drawText("1", 20, 175, blackPaint);
            canvas.drawText("0", 20, 265, blackPaint);
            canvas.drawText("-1", 20, 355, blackPaint);
            canvas.drawText("-2", 20, 442, blackPaint);
            
            canvas.drawText("-2.5", 45, 465, blackPaint);
            canvas.drawText("-1.25",(AndroidDeviceConst.mScreenWidth/2+40)/2 , 465, blackPaint);
            canvas.drawText("0", AndroidDeviceConst.mScreenWidth/2, 465, blackPaint);
            canvas.drawText("1.25",AndroidDeviceConst.mScreenWidth/2+(AndroidDeviceConst.mScreenWidth/2-40)/2, 465, blackPaint);
            canvas.drawText("2.5", AndroidDeviceConst.mScreenWidth/2+(AndroidDeviceConst.mScreenWidth/2-50), 465, blackPaint);
            
            
            
            
            canvas.drawText("chaos(mv)", 60, 30, blackPaint);
            canvas.drawText("(mv)", AndroidDeviceConst.mScreenWidth-20, 463, blackPaint);
            
            for(int i=0; i<len; i++)
            {
            	canvas.drawPoint(x[i], y[i], mPaint);
            }
        }
        
 
        
}
