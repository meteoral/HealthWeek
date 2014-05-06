package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.upload.UploadBpBean;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**********************************************************************
 * 类名：TestXueYaActivity
 * 
 * 主要功能：血压测试
 * 
 * @author 梁才学 创建日期：2013.12.11
 **********************************************************************/
public class TestXueYaActivity extends BaseActivity implements
		OnDateSetListener {

	static final int XUE_YA_DATE_DIALOG_ID = 1;// 日期

	private int mYear;
	private int mMonth;
	private int mDay;

	private LayoutInflater listContainer;
	private View view;
	private View layoutDateText;
	private ImageView backIcon;
	private TextView dateText;

	private Button mButtonCmmt;

	private EditText mEditBsleepssy;// 睡前收缩压
	private EditText mEditBsleepszy;// 睡前舒张压
	private EditText mEditBsleepml;// 睡前脉率
	private EditText mEditWakeupssy;// 起床收缩压
	private EditText mEditWakeupszy;// 起床舒张压
	private EditText mEditWakeupml;// 起床脉率

	private UploadBpBean mUploadBean;
	private ScrollView mScrollView;
	private int mScreenHeight;// 屏幕高度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.test_xue_ya);

		listContainer = LayoutInflater.from(this);
		view = listContainer.inflate(R.layout.test_xue_ya, null);
		setContentView(view);

		// 获取当前的年月日
		final Calendar calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);

		MyViewListener listener = new MyViewListener();
		backIcon = (ImageView) view.findViewById(R.id.test_xueya_back_icon);
		dateText = (TextView) view.findViewById(R.id.date_text);
		dateText.setText(new StringBuilder().append(mYear).append("-")
				.append(format(mMonth + 1)).append("-").append(format(mDay)));
		layoutDateText = view.findViewById(R.id.layout_date_text);
		mButtonCmmt = (Button) view.findViewById(R.id.xueya_commit_btn);
		mEditBsleepssy = (EditText) view
				.findViewById(R.id.bsleep_value_shousuoyaid);
		mEditBsleepszy = (EditText) view
				.findViewById(R.id.bsleep_value_shuzhangyaid);
		mEditBsleepml = (EditText) view.findViewById(R.id.bsleep_value_mailvid);
		mEditWakeupssy = (EditText) view
				.findViewById(R.id.wakeup_value_shousuoyaid);
		mEditWakeupszy = (EditText) view
				.findViewById(R.id.wakeup_value_shuzhangyayaid);
		mEditWakeupml = (EditText) view.findViewById(R.id.wakeup_value_mailvid);

		mEditBsleepssy.addTextChangedListener(textWatcher);
		mEditBsleepszy.addTextChangedListener(textWatcher);
		mEditBsleepml.addTextChangedListener(textWatcher);
		mEditWakeupssy.addTextChangedListener(textWatcher);
		mEditWakeupszy.addTextChangedListener(textWatcher);
		mEditWakeupml.addTextChangedListener(textWatcher);

		backIcon.setOnClickListener(listener);
		layoutDateText.setOnClickListener(listener);
		mButtonCmmt.setOnClickListener(listener);		
		
		DisplayMetrics dm = new DisplayMetrics();// 用来获取手机屏幕的属性，如分辨率
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenHeight = dm.heightPixels;// 获取屏幕高度
		
		mScrollView = (ScrollView) view.findViewById(R.id.scrollView_id);
		mEditBsleepssy.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {// 此处为得到焦点时的处理内容
					mScrollView.setScrollY(mScreenHeight);// 当“睡觉前收缩压”编辑框得到焦点时，让ScrollView滚到底部，以显示“提交”按钮
				} else {// 此处为失去焦点时的处理内容

				}
			}
		});

		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));		
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case XUE_YA_DATE_DIALOG_ID:
			DatePickerDialog mDialog = new DatePickerDialog(this, this, mYear,
					mMonth, mDay);
			Calendar cal = Calendar.getInstance();
			cal.set(1900, 0, 1);// 设置一个年月日:1920年1月1日
			long time = cal.getTimeInMillis();// 获取该年月日的毫秒数
			mDialog.getDatePicker().setMinDate(time);// 设置最小的日期是：1920年1月1日
			mDialog.getDatePicker().setMaxDate(new Date().getTime());// 设置最大的日期是当前的年月日
			return mDialog;
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case XUE_YA_DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	private void updateContent(final BpInfoBean bean) {
		if (!this.isFinishing()) {
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (bean.mSleepSpb == 0) {
						mEditBsleepssy.setText("");
					} else {
						mEditBsleepssy.setText("" + bean.mSleepSpb);
					}

					if (bean.mSleepDpb == 0) {
						mEditBsleepszy.setText("");
					} else {
						mEditBsleepszy.setText("" + bean.mSleepDpb);
					}

					if (bean.mSleepPR == 0) {
						mEditBsleepml.setText("");
					} else {
						mEditBsleepml.setText("" + bean.mSleepPR);
					}

					if (bean.mWakeSpb == 0) {
						mEditWakeupssy.setText("");
					} else {
						mEditWakeupssy.setText("" + bean.mWakeSpb);
					}

					if (bean.mWakeDpb == 0) {
						mEditWakeupszy.setText("");
					} else {
						mEditWakeupszy.setText("" + bean.mWakeDpb);
					}

					if (bean.mWakePR == 0) {
						mEditWakeupml.setText("");
					} else {
						mEditWakeupml.setText("" + bean.mWakePR);
					}
				}
			});
		}
	}

	@Override
	public void getMeasureDetailCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub
		super.getMeasureDetailCallback(retCode, errorMsg, bean);

		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			updateContent((BpInfoBean) bean);
			break;
		case 90036:
			// 没有记录
			updateContent(new BpInfoBean());
			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}

	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		super.callback(retCode, errorMsg);
		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			OneKeyTestActivity activity = (OneKeyTestActivity) AndroidActivityMananger
					.getInstatnce().getActivity(
							OneKeyTestActivity.class.getSimpleName());
			if (activity == null) {
				// 之前的做法
				// Intent intent = new Intent(TestXueYaActivity.this,
				// ReportDetailOfXueYaActivity.class);
				// intent.putExtra("bpInfo_id", mUploadBean.mId);
				// startActivity(intent);

				// 跳进报告详情界面
				Intent intent = new Intent(TestXueYaActivity.this,
						ReportsDetailActivity.class);
				ArrayList<String> ids = new ArrayList<String>();
				ArrayList<String> types = new ArrayList<String>();
				ids.add(mUploadBean.mId);
				types.add(BusinessConst.BP_MESURE);
				intent.putStringArrayListExtra("bean_id", ids);
				intent.putStringArrayListExtra("bean_type", types);
				String roleId = AndroidConfiguration.getInstance(this)
						.getRoleId();
				intent.putExtra("role_id", roleId);
				startActivity(intent);

			} else {
				activity.mHashTestItems.put(BusinessConst.BP_MESURE,
						mUploadBean.mId);
			}
			finish();
			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}

	private void commitDataToServer() {
		mUploadBean = new UploadBpBean();
		mUploadBean.date = dateText.getText().toString();
		if (!TextUtils.isEmpty(mEditBsleepssy.getText().toString())) {
			mUploadBean.mSleepSBP = Integer.valueOf(mEditBsleepssy.getText()
					.toString());
		}
		if (!TextUtils.isEmpty(mEditBsleepszy.getText().toString())) {
			mUploadBean.mSleepDBP = Integer.valueOf(mEditBsleepszy.getText()
					.toString());
		}
		if (!TextUtils.isEmpty(mEditBsleepml.getText().toString())) {
			mUploadBean.mSleepPulseRate = Integer.valueOf(mEditBsleepml
					.getText().toString());
		}
		if (!TextUtils.isEmpty(mEditWakeupssy.getText().toString())) {
			mUploadBean.mWakeSBP = Integer.valueOf(mEditWakeupssy.getText()
					.toString());
		}
		if (!TextUtils.isEmpty(mEditWakeupszy.getText().toString())) {
			mUploadBean.mWakeDBP = Integer.valueOf(mEditWakeupszy.getText()
					.toString());
		}
		if (!TextUtils.isEmpty(mEditWakeupml.getText().toString())) {
			mUploadBean.mWakePulseRate = Integer.valueOf(mEditWakeupml
					.getText().toString());
		}
		this.showWaittingDialog("上传血压测量数据");
		InterfaceService.getInstance(this).UploadBp(mUploadBean, this);
	}

	private String format(int date) {
		if (date < 10) {
			return "0" + date;
		} else {
			return "" + date;
		}
	}

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {
		dateText.setText(new StringBuilder().append(mYear).append("-")
				.append(format(mMonth + 1)).append("-").append(format(mDay)));
		this.showWaittingDialog("获取当天的血压记录");
		String roleid = AndroidConfiguration.getInstance(this).getRoleId();
		InterfaceService.getInstance(this).getMeasureDetail(roleid,
				dateText.getText().toString(), BusinessConst.BP_MESURE, this);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/**********************************************************************
	 * 方法描述：监听编辑框是否有变化
	 * 
	 **********************************************************************/
	private TextWatcher textWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (!TextUtils.isEmpty(mEditBsleepssy.getText().toString())
					|| !TextUtils.isEmpty(mEditBsleepszy.getText().toString())
					|| !TextUtils.isEmpty(mEditBsleepml.getText().toString())
					|| !TextUtils.isEmpty(mEditWakeupssy.getText().toString())
					|| !TextUtils.isEmpty(mEditWakeupszy.getText().toString())
					|| !TextUtils.isEmpty(mEditWakeupml.getText().toString())) {
				mButtonCmmt.setAlpha(1.0f);
				mButtonCmmt.setEnabled(true);
			} else {
				mButtonCmmt.setAlpha(0.3f);
				mButtonCmmt.setEnabled(false);
			}

		}
	};

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.test_xueya_back_icon:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.layout_date_text:
				showDialog(XUE_YA_DATE_DIALOG_ID);
				break;
			case R.id.xueya_commit_btn:
				commitDataToServer();
				break;
			default:

			}

		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		updateDateDisplay();
	}
}
