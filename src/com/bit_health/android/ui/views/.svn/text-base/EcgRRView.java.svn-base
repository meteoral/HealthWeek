package com.bit_health.android.ui.views;

import com.siat.healthweek.R;
import com.bit_health.android.constants.AndroidDeviceConst;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.view.View;

/**********************心率值统计图******
 * 该类用于画心电信号的RR间期统计图，继承View
 */

public class EcgRRView<AttributeSet> extends View {

	private Paint mPaint, textPaint, wangge_Paint, redPaint;

	private Path mPath;

	private final int POINT_LEN = AndroidDeviceConst.mScreenWidth;//自适应屏幕的宽度

	public int len = 10;

	// SDNN
	public double SDNN = 0;

	// 三角指数
	public double Triangle = 0;

	// NN50
	public double NN50 = 0;

	public float[] x;

	public float[] y;

	private String tReference;

	private String tTrangle;

	private String tNn50;

	private String tSdnn;

	private String tInterval;

	private String tHeartRate;
	private float tHeartRateWidth;
	private float tIntervalWidth;
	
	public EcgRRView(Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
		mPath = new Path();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setTextSize(2);
		mPaint.setColor(Color.YELLOW);

		textPaint = new Paint();
		textPaint.setTextSize(20);
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setColor(Color.YELLOW);

		wangge_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		wangge_Paint.setStyle(Paint.Style.STROKE);
		wangge_Paint.setStrokeWidth(0.5f);
		wangge_Paint.setTextSize(0.5f);
		wangge_Paint.setColor(Color.YELLOW);

		redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		redPaint.setStyle(Paint.Style.STROKE);
		redPaint.setStrokeWidth(1f);
		redPaint.setTextSize(1f);
		redPaint.setColor(Color.RED);
		initText(context);
		
		tHeartRateWidth=(textPaint.measureText(tHeartRate)+10)/2;
		tIntervalWidth=AndroidDeviceConst.mScreenWidth-30-(textPaint.measureText(tInterval))/2;//画“间隔”两个字的坐标
	}

	@Override
	public void onDraw(Canvas canvas) {
		
		canvas.drawColor(Color.BLACK);

		for (int i = 0; i < 13; i++)//画网格
		{
			canvas.drawLine(0, i * 40, POINT_LEN - 1, i * 40, wangge_Paint);
		}
		for (int i = 0; i < AndroidDeviceConst.mScreenWidth/40+1; i++) {
			canvas.drawLine(i * 40, 0, i * 40, 480, wangge_Paint);
		}

		canvas.drawLine(40, 80, AndroidDeviceConst.mScreenWidth-40, 80, redPaint);//画红线
		canvas.drawLine(40, 260, AndroidDeviceConst.mScreenWidth-40, 260, redPaint);

		canvas.drawLine(40, 360, AndroidDeviceConst.mScreenWidth-40, 360, mPaint);//画X轴
		canvas.drawLine(40, 40, 40, 360, mPaint);//画Y轴

		canvas.drawLine(30, 50, 40, 40, mPaint);//画Y箭头
		canvas.drawLine(40, 40, 50, 50, mPaint);
		canvas.drawLine(AndroidDeviceConst.mScreenWidth-50, 350, AndroidDeviceConst.mScreenWidth-40, 360, mPaint);//画X轴的箭头上面的一条线
		canvas.drawLine(AndroidDeviceConst.mScreenWidth-40, 360, AndroidDeviceConst.mScreenWidth-50, 370, mPaint);//画Y轴的箭头下面的一条线

		// 30个像素点代表10心率值
		canvas.drawText("120", 18, 80, textPaint);//画坐标的刻度
		canvas.drawText("100", 18, 140, textPaint);
		canvas.drawText("80", 18, 200, textPaint);
		canvas.drawText("60", 18, 260, textPaint);
		canvas.drawText("40", 18, 320, textPaint);
		canvas.drawText(tHeartRate,tHeartRateWidth , 30, textPaint);
		canvas.drawText(tInterval, tIntervalWidth, 390, textPaint);
		textPaint.setTextAlign(Align.RIGHT);
		//画X轴下面的一锥字
		canvas.drawText(tNn50 + " " /*+ Integer.toString(NN50)*/, 200, 400,
				textPaint);
		canvas.drawText(tSdnn + " " /*+ Integer.toString(SDNN) + "  ("
				+ tReference + "：17~79)"*/, 200, 430, textPaint);
		canvas.drawText(tTrangle + ": "/* + Integer.toString(Triangle) + "  ("
				+ tReference + "：8~16)"*/, 200, 460, textPaint);
		textPaint.setTextAlign(Align.LEFT);
		canvas.drawText( Double.toString(NN50)+ "  ("
				+ tReference + "：8~16)", 200, 400,
				textPaint);
		canvas.drawText( Double.toString(SDNN) + "  ("
				+ tReference + "：17~79)", 200, 430, textPaint);
		canvas.drawText( Double.toString(Triangle) + "  ("
				+ tReference + "：20~52)", 200, 460, textPaint);
		mPath = makeFollowPathOne();
		canvas.drawPath(mPath, mPaint);

	}

	private void initText(Context context) {
		tReference = context.getString(R.string.EcgRRV_reference)/* 参考值 */;
		tTrangle = context.getString(R.string.EcgRRV_trangle)/* "三角指数值" */;
		tNn50 = context.getString(R.string.EcgRRV_nn50Value)/* NN50值 */;
		tSdnn = context.getString(R.string.EcgRRV_sdnnValue)/* SDNN */;
		tInterval = context.getString(R.string.common_INTERVAL)/* "间隔" */;
		tHeartRate = context.getString(R.string.EcgRRV_heartRate)/* "心率（次/分）" */;

	}

	public Path makeFollowPathOne() {

		Path p = new Path();
		p.moveTo(x[0], y[0]);

		for (int i = 1; i < len; i++) {
			p.lineTo(x[i], y[i]);
		}
		return p;
	}

}
