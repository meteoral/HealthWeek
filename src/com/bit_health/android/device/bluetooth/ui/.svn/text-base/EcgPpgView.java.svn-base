package com.bit_health.android.device.bluetooth.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

/*
 * 该类用于画心电信号和脉搏信号的波形图，继承View
 */

public class EcgPpgView<AttributeSet> extends View {

	// static byte ecgDis = 2;
	// static double ppgDis = 0.4;
	private Paint ecgPaint, blackPaint, yollowPaint, ppgPaint, wangge_Paint,
			pixPaint;

	// 更新波形的下标
	// public int ecg_index = 0;
	// public int ppg_index = 0;
	// public static int SCREEN_WIDTH = AndroidDeviceConst.mScreenHeight >
	// AndroidDeviceConst.mScreenWidth ? AndroidDeviceConst.mScreenHeight
	// : AndroidDeviceConst.mScreenWidth;
	// // 波形长度
	// public static int ECG_POINT_LEN = SCREEN_WIDTH * ecgDis;
	// public static int PPG_POINT_LEN = (int) (SCREEN_WIDTH * ppgDis);
	private final int POINT_LEN = BluetoothData.SCREEN_WIDTH;// 取得屏幕的宽度以自适应

	// 波形纵横坐标数组
	// public float[] ecgView = new float[ECG_POINT_LEN];
	// public float[] ecgData = new float[ECG_POINT_LEN];
	// public float[] ppg_x = new float[PPG_POINT_LEN];
	// public float[] ppgData = new float[PPG_POINT_LEN];

	// public int mEcgppgMaxlen = 250;
	//
	// public float mfScale = 1;

	// 网格宽度
	private int GRID_WIDTH = 25;

	public EcgPpgView(Context context, android.util.AttributeSet attrs) {
		super(context, attrs);

		Log.d("EcgPpgView", "construct @ view");
		// 初始化参数
		ecgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画心电波
		ecgPaint.setStyle(Paint.Style.STROKE);
		ecgPaint.setStrokeWidth(2);
		ecgPaint.setTextSize(2);
		ecgPaint.setColor(Color.GREEN);

		blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		blackPaint.setStyle(Paint.Style.STROKE);
		blackPaint.setStrokeWidth(2);
		blackPaint.setTextSize(2);
		blackPaint.setColor(Color.BLACK);

		yollowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		yollowPaint.setStyle(Paint.Style.STROKE);
		yollowPaint.setStrokeWidth(1.0f);
		yollowPaint.setTextSize(0.3f);
		yollowPaint.setColor(Color.YELLOW);

		ppgPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 画脉搏波
		ppgPaint.setStyle(Paint.Style.STROKE);
		ppgPaint.setStrokeWidth(2);
		ppgPaint.setTextSize(2);
		ppgPaint.setColor(Color.RED);

		wangge_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		wangge_Paint.setStyle(Paint.Style.STROKE);
		wangge_Paint.setStrokeWidth(1.0f);
		wangge_Paint.setTextSize(0.5f);
		wangge_Paint.setColor(Color.DKGRAY);

		pixPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pixPaint.setStyle(Paint.Style.FILL);
		pixPaint.setStrokeWidth(2.0f);
		pixPaint.setTextSize(20.0f);
		pixPaint.setColor(Color.RED);

		for (int i = 0; i < BluetoothData.ECG_POINT_LEN; i++) {
			BluetoothData.getInsetance().ecgData[i] = 130;
		}

		for (int i = 0; i < BluetoothData.PPG_POINT_LEN; i++) {
			BluetoothData.getInsetance().ppgData[i] = 390;
		}

	}

	public void initEcgPpgView() {
		// int iWidth = getWidth();
		// ECG_POINT_LEN = iWidth * ecgDis;
		// PPG_POINT_LEN = (int) (iWidth * ppgDis);
		//
		// ecgView = new float[ECG_POINT_LEN];
		// ecgData = new float[ECG_POINT_LEN];
		// ppg_x = new float[PPG_POINT_LEN];
		// ppgData = new float[PPG_POINT_LEN];
		// for (int i = 0; i < ECG_POINT_LEN; i++) {
		// ecgData[i] = 130;
		// }
		//
		// for (int i = 0; i < PPG_POINT_LEN; i++) {
		// ppgData[i] = 390;
		// }
	}

	@Override
	public void onDraw(Canvas canvas) {

		canvas.drawColor(Color.BLACK);

		if (BluetoothData.getInsetance().mEcgppgMaxlen == 0) {
			BluetoothData.getInsetance().mEcgppgMaxlen = 250;
		}
		// 10行网格线
		GRID_WIDTH = (int) ((BluetoothData.getInsetance().mEcgppgMaxlen / 10) * BluetoothData
				.getInsetance().mfScale);
		// 背景网格x轴
		for (int i = 0; i < 11
				&& i * GRID_WIDTH < BluetoothData.getInsetance().mEcgppgMaxlen; i++) {
			canvas.drawLine(0, i * GRID_WIDTH, POINT_LEN - 1, i * GRID_WIDTH,
					wangge_Paint);
		}
		// 背景网格y轴
		for (int i = 0; i < BluetoothData.SCREEN_WIDTH / GRID_WIDTH + 1; i++) {
			canvas.drawLine(i * GRID_WIDTH, 0, i * GRID_WIDTH,
					BluetoothData.getInsetance().mEcgppgMaxlen, wangge_Paint);
		}

		// 画标尺
		canvas.drawLine(0, 125, 6, 125, pixPaint);
		canvas.drawLine(18, 125, 25, 125, pixPaint);
		canvas.drawLine(6, 75, 18, 75, pixPaint);
		canvas.drawLine(6, 75, 6, 125, pixPaint);
		canvas.drawLine(18, 75, 18, 125, pixPaint);
		canvas.drawText("10mm/mv", 30, 30, pixPaint);
		canvas.drawText("25mm/s", 180, 30, pixPaint);

		canvas.drawLine(0, BluetoothData.getInsetance().mEcgppgMaxlen,
				POINT_LEN - 1, BluetoothData.getInsetance().mEcgppgMaxlen,
				yollowPaint);
		// ppg
		makePPGPath(canvas);
		if (BluetoothData.getInsetance().ppg_index > 4) {
			blackPaint.setStyle(Style.FILL);
			canvas.drawCircle(
					(float) ((BluetoothData.getInsetance().ppg_index + 3) / BluetoothData.ppgDis),
					BluetoothData.getInsetance().ppgData[BluetoothData
							.getInsetance().ppg_index], 8, blackPaint);
			blackPaint.setStyle(Style.STROKE);
		} else {
			canvas.drawPoint(BluetoothData.getInsetance().ppg_index,
					BluetoothData.getInsetance().ppgData[BluetoothData
							.getInsetance().ppg_index], blackPaint);
		}

		// ecg
		makeECGPath(canvas);
		if (BluetoothData.getInsetance().ecg_index > 4) {
			blackPaint.setStyle(Style.FILL);
			canvas.drawCircle((BluetoothData.getInsetance().ecg_index + 3)
					/ BluetoothData.ecgDis,
					BluetoothData.getInsetance().ecgData[BluetoothData
							.getInsetance().ecg_index], 8, blackPaint);
			blackPaint.setStyle(Style.STROKE);
		} else {
			canvas.drawPoint(BluetoothData.getInsetance().ecg_index,
					BluetoothData.getInsetance().ecgData[BluetoothData
							.getInsetance().ecg_index], blackPaint);
		}
	}

	private void makeECGPath(Canvas canvas) {
		Path p = new Path();
		p.moveTo(0, BluetoothData.getInsetance().ecgData[0]);

		for (int i = 1; i < BluetoothData.getInsetance().ecg_index - 4; i++) {
			p.lineTo((i / BluetoothData.ecgDis),
					BluetoothData.getInsetance().ecgData[i]);
		}
		canvas.drawPath(p, ecgPaint);
		
		p.reset();
		
		p.moveTo((float) BluetoothData.getInsetance().ecg_index
				/ BluetoothData.ecgDis,
				BluetoothData.getInsetance().ecgData[BluetoothData
						.getInsetance().ecg_index]);
		for (int i = BluetoothData.getInsetance().ecg_index; i < BluetoothData.ECG_POINT_LEN; i++) {
			p.lineTo((i / BluetoothData.ecgDis),
					BluetoothData.getInsetance().ecgData[i]);
		}
		canvas.drawPath(p, ecgPaint);
	}

	private void makePPGPath(Canvas canvas) {
		Path p = new Path();
		p.moveTo(0, BluetoothData.getInsetance().ppgData[0]);

		for (int i = 1; i < BluetoothData.getInsetance().ppg_index - 4; i++) {
			p.lineTo((float) (i / BluetoothData.ppgDis),
					BluetoothData.getInsetance().ppgData[i]);
		}
		canvas.drawPath(p, ppgPaint);
		
		p.reset();
		
		p.moveTo(
				(float) (BluetoothData.getInsetance().ppg_index / BluetoothData.ppgDis),
				BluetoothData.getInsetance().ppgData[BluetoothData
						.getInsetance().ppg_index]);
		for (int i = BluetoothData.getInsetance().ppg_index; i < BluetoothData.PPG_POINT_LEN; i++) {
			p.lineTo((float) (i / BluetoothData.ppgDis),
					BluetoothData.getInsetance().ppgData[i]);
		}
		canvas.drawPath(p, ppgPaint);
	}
}
