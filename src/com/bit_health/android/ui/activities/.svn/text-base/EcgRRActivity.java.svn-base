package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.ui.views.EcgRRView;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.TextView;

public class EcgRRActivity extends Activity {
    private TextView shunshi_ecg_rate;
    private EcgRRView<AttributeSet> ecgView;
    
	// 三角指数最大间期为设定1000
	private final int RR_Triangle = 1000;
	
	// 放大倍数
	private final int scale = 3;
	
	// 基数长度
	private final int length = 400;
	
	// 横坐标基数
	private final int base = 40;
	
	// 横坐标大小分段
	private final int len_50 = 50;
	private final int len_100 = 100;
	private final int len_200 = 200;
	private final int len_400 = 400;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ecg_rr_layout);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

//		final TextView leftText = (TextView) findViewById(R.id.title_left_text);
//		leftText.setText(R.string.elh_statistic);

		ecgView = (EcgRRView<AttributeSet>) findViewById(R.id.ecg_rr_View);
		shunshi_ecg_rate = (TextView) findViewById(R.id.shunshi_ecg_rate);
		
		// 取得RR间期
		Bundle bundle = getIntent().getExtras();
		int[] RR = bundle.getIntArray("rrList");
		ecgView.NN50 = bundle.getDouble("PNN50");
		ecgView.SDNN = bundle.getDouble("SDNN");
		ecgView.Triangle = bundle.getDouble("HRVI");
		
		// 计算NN50
		// countNN50(RR);
		// 计算三角指数
		// countSDNNandTriangle(RR);
		// 画统计图
		paintTongJiTu(RR);
	}

	// 计算NN50
	private void countNN50(int[] RR) {
		int len = RR.length;
		int NN50 = 0;
		for (int i = 0; i < len - 1; i++) {
			if (Math.abs(RR[i + 1] - RR[i]) > 50) {
				NN50++;
			}
		}
		ecgView.NN50 = NN50;
	}

	// 计算SDNN和三角指数
	private void countSDNNandTriangle(int[] RR) {
		int len = RR.length;
		int verl = 0;
		
		for (int i = 0; i < len; i++) {
			verl += RR[i];
		}
		
		verl = verl / len;
		double sum = 0;
		for (int i = 0; i < len; i++) {
			sum += Math.pow((RR[i] - verl), 2);
		}
		
		sum = Math.sqrt(sum / len);
		ecgView.SDNN = (int) sum;
		int[] tria = new int[RR_Triangle];
		
		for (int i = 0; i < RR_Triangle; i++) {
			tria[i] = 0;
		}
		
		for (int i = 0; i < len; i++) {
			if (RR[i] / 8 < 200)
				tria[RR[i] / 8]++;
		}
		
		int max = 0;
		for (int i = 0; i < RR_Triangle; i++) {
			if (tria[i] > max)
				max = tria[i];
		}
		int triangle = len / max;
		ecgView.Triangle = triangle;
	}

	// 画统计图
	private void paintTongJiTu(int[] RR) {
		int len = RR.length;
		int[] RR_Heart_Rate = new int[len];
		int num = 0;
		
		for (int i = 0; i < len; i++) {
			if (RR[i] > 0 && 60 * 1000 / RR[i] > 0 && 60 * 1000 / RR[i] < 200) {
				RR_Heart_Rate[num++] = 60 * 1000 / RR[i];
			}
		}
		
		len = num;
		num = 0;
		for (int i = 0; i < len; i++) {
			num += RR_Heart_Rate[i];
		}
		
		//心率平均值
		shunshi_ecg_rate.setText(getString(R.string.EcgRRA_hrAverage) + Integer.toString(num / len));

		if (len <= len_50) {
			ecgView.len = len;
			ecgView.x = new float[len];
			ecgView.y = new float[len];
			for (int i = 0; i < len; i++) {
				ecgView.x[i] = base + i * length / len_50;
				ecgView.y[i] = 440 - RR_Heart_Rate[i] * scale;
			}
		} else if (len <= len_100) {
			ecgView.len = len;
			ecgView.x = new float[len];
			ecgView.y = new float[len];
			for (int i = 0; i < len; i++) {
				ecgView.x[i] = base + i * length / len_100;
				ecgView.y[i] = 440 - RR_Heart_Rate[i] * scale;
			}
		} else if (len <= len_200) {
			ecgView.len = len;
			ecgView.x = new float[len];
			ecgView.y = new float[len];
			for (int i = 0; i < len; i++) {
				ecgView.x[i] = base + i * length / len_200;
				ecgView.y[i] = 440 - RR_Heart_Rate[i] * scale;
			}
		} else if (len <= len_400) {
			ecgView.len = len;
			ecgView.x = new float[len];
			ecgView.y = new float[len];
			for (int i = 0; i < len; i++) {
				ecgView.x[i] = base + i;
				ecgView.y[i] = 440 - RR_Heart_Rate[i] * scale;
			}
		} else {
			ecgView.len = 400;
			ecgView.x = new float[400];
			ecgView.y = new float[400];
			for (int i = 0; i < 400; i++) {
				ecgView.x[i] = base + i;
				ecgView.y[i] = 440 - RR_Heart_Rate[i] * scale;
			}
		}
	}
}

