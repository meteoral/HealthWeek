package com.bit_health.android.ui.activities;


import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;
import com.bit_health.android.ui.views.EcgChaosView;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.TextView;

public class EcgChaosActivity extends Activity {
    public TextView chaosState;
    public EcgChaosView<AttributeSet> chaosView;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ecg_chaos);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

		final TextView leftText = (TextView) findViewById(R.id.title_left_text);
		leftText.setText(R.string.elh_chaos);

		chaosView = (EcgChaosView<AttributeSet>) findViewById(R.id.ecg_chaos_View);
		chaosState = (TextView) findViewById(R.id.chaos_state);
		
		// 取得ecg数据
		Bundle bundle = getIntent().getExtras();
		int[] ecg = bundle.getIntArray("ECG");
		int len = bundle.getInt("NUM");
		
		// 画混沌图
		paintChaosTu(ecg, len);
	}

	private void paintChaosTu(int[] ecg, int len) {
		long sum = 0;
		
		for (int i = 0; i < len; i++) {
			sum += ecg[i];
		}

		sum = sum / len;

		chaosView.x = new float[len - 6];
		chaosView.y = new float[len - 6];
		chaosView.len = len - 6;

		for (int i = 0; i < chaosView.len; i++) {
			chaosView.x[i] = 220 + (ecg[i] - sum) / 2;
			chaosView.y[i] = 220 + (ecg[i + 6] - sum) / 2;
		}
	}
}

