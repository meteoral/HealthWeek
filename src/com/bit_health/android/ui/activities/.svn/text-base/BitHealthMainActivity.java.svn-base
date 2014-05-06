package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AnalyzeCath;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.content.Context;
import android.content.SharedPreferences;

/**********************************************************************
 * 类名：BitHealthMainActivity
 * 
 * @author 梁才学 主要功能：启动界面。这是本应用的入口，当第一次进入本应用，需要进入帮助指引界面； 在启动界面中停留3秒钟，再进行跳转到下一界面。
 *         创建日期：2013.12.5
 **********************************************************************/
public class BitHealthMainActivity extends BaseActivity {

	private static final int WAIT3SECOND = 3;

	private AndroidActivityMananger mMananger;
	private ProgressBar m_ProgressBar;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case BitHealthMainActivity.WAIT3SECOND:

				// 第一次进入本应用，需要进入帮助指引界面
				SharedPreferences preferences = getSharedPreferences(
						getPackageName(), Context.MODE_PRIVATE);
				boolean first_time = preferences.getBoolean(
						AndroidConfiguration.FIRST_TIME, true);
				if (first_time) {
					SharedPreferences.Editor edit = preferences.edit();
					edit.putBoolean(AndroidConfiguration.FIRST_TIME, false);
					edit.commit();
					mMananger.switchActivity("BitHealthMainActivity",
							GuiderActivity.class);
				} else {
					if (!TextUtils.isEmpty(AndroidConfiguration.getInstance(
							BitHealthMainActivity.this.getApplicationContext())
							.getAccountSession())) {
						// 已经登录
						if(!TextUtils.isEmpty(AndroidConfiguration.getInstance(
								BitHealthMainActivity.this.getApplicationContext())
								.getRoleId())){// 已经选择了角色
							mMananger.switchActivity("BitHealthMainActivity",
									FourModuleManangerActivity.class);
							
						}else{
							mMananger.switchActivity("BitHealthMainActivity",
									LoginIdentityActivity.class);
						}
					} else {
						m_ProgressBar.setVisibility(View.GONE);
						mMananger.switchActivity("BitHealthMainActivity",
								LoginActivity.class);
					}
					Thread.currentThread().interrupt();
				}
				break;

			}
			super.handleMessage(msg);
		}
	};

	protected boolean isProgress = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bit_health_main);

		mMananger = AndroidActivityMananger.getInstatnce();

		/***************** 显示进度条 start *******************************/
		m_ProgressBar = (ProgressBar) findViewById(R.id.ProgressBar01);
		m_ProgressBar.setIndeterminate(false);
		m_ProgressBar.setVisibility(View.VISIBLE);
		m_ProgressBar.setMax(100);
		m_ProgressBar.setProgress(0);

		// 通过线程来改变 ProgressBar 的值
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 102; i++) {
					try {
						m_ProgressBar.setProgress(i);
						Thread.sleep(10);
						
						if (i == 101) {
							Message m = handler.obtainMessage();
							m.what = BitHealthMainActivity.WAIT3SECOND;
							BitHealthMainActivity.this.handler.sendMessage(m);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		/***************** 显示进度条 end *******************************/
		
		RoleCatchInfo.getInstance(this.getApplicationContext()).getRoles();
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);	
		AnalyzeCath.getInstance(this);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	/**********************************************************************
	 * 方法描述：在点击返回键时让之失效
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 **********************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.ui.activities.BaseActivity#getMainLayout()
	 */
	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return null;
	}

}
