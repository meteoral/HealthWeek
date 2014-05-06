package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**********************************************************************
 * 类名：RegisterIdentifyCodeActivity
 * 
 * 主要功能：验证码界面
 * 
 * @author 梁才学  创建日期：2013.1.6
 **********************************************************************/
public class RegisterIdentifyCodeActivity extends BaseActivity {

	private LayoutInflater inflater;
	private View view;
	private ImageView backIcon;
	private Button complete_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.login);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.register_identify_code, null);
		setContentView(view);

		initData();
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		backIcon = (ImageView) view
				.findViewById(R.id.identify_code_title_bar_back_icon);
		complete_btn = (Button) view
				.findViewById(R.id.complete_btn);
		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
		complete_btn.setOnClickListener(listener);
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：验证码界面中的控件监听
	 * 
	 * @author 梁才学 创建日期：2013.12.5
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.identify_code_title_bar_back_icon:
				RegisterIdentifyCodeActivity.this.finish();
				break;
				
			case R.id.complete_btn:
				RegisterIdentifyCodeActivity.this.finish();
				break;

			default:

			}

		}
	}
}
