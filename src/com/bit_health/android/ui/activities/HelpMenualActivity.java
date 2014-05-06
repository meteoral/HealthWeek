package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**********************************************************************
 * 类名：HelpMenualActivity
 * 
 * 主要功能：帮助手册
 * 
 * @author 梁才学 创建日期：2014.1.24
 **********************************************************************/
public class HelpMenualActivity extends BaseActivity {

	private ImageView backIcon;
	private LayoutInflater inflater;
	private View view;
	private View layout3N1;
	private View layoutMiniholter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.help_menual, null);
		setContentView(view);

		MyViewListener listener = new MyViewListener();
		backIcon = (ImageView) view.findViewById(R.id.help_menual_back_icon);
		layout3N1 = view.findViewById(R.id.layout_3n1);
		layoutMiniholter = view.findViewById(R.id.layout_miniholter);

		backIcon.setOnClickListener(listener);
		layout3N1.setOnClickListener(listener);
		layoutMiniholter.setOnClickListener(listener);
		
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.help_menual_back_icon:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.layout_3n1:
				switchToMoreInfoActivity(MoreInfoActivity.HELP_MENUAL_3N1);
				break;

			case R.id.layout_miniholter:
				switchToMoreInfoActivity(MoreInfoActivity.HELP_MENUAL_MINI_HOLTER);
				break;
			default:

			}

		}
	}

	private void switchToMoreInfoActivity(int fragment_index) {
		Intent intent = new Intent(getApplicationContext(),
				MoreInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("fragment_index", fragment_index);
		intent.putExtras(bundle);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
	}
}
