package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.upload.UploadBsBean;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**********************************************************************
 * 类名：TestXueTangActivity
 * 
 * 主要功能：血糖测试
 * 
 * @author 梁才学 创建日期：2013.12.11
 **********************************************************************/
public class TestXueTangActivity extends BaseActivity implements
		OnDateSetListener {

	static final int XUE_TANG_DATE_DIALOG_ID = 1;// 日期

	private int mYear;
	private int mMonth;
	private int mDay;

	private TextView dateText;

	private LayoutInflater inflater;
	private View view;
	private View layoutDateText;
	private ImageView backIcon;// 返回图片
	private Button commitBtn;// 提交按钮

	private EditText mEditLimosis;
	private EditText mEditBlunch;
	private EditText mEditAlunch;
	private EditText mEditBsleep;

	private UploadBsBean mUploadBean;
	private ScrollView mScrollView;
	private int mScreenHeight;// 屏幕高度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.test_xue_tang, null);
		setContentView(view);
		final Calendar calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);

		MyViewListener listener = new MyViewListener();
		dateText = (TextView) view.findViewById(R.id.xuetang_date_text);
		dateText.setText(new StringBuilder().append(mYear).append("-")
				.append(format(mMonth + 1)).append("-").append(format(mDay)));
		backIcon = (ImageView) view.findViewById(R.id.test_xuetang_back_icon);
		commitBtn = (Button) view.findViewById(R.id.test_xuetang_commit_button);
		layoutDateText = view.findViewById(R.id.layout_date_of_xuetang);
		mEditLimosis = (EditText) view.findViewById(R.id.bs_value_limosisid);
		mEditBlunch = (EditText) view
				.findViewById(R.id.bs_before_lunch_valueid);
		mEditAlunch = (EditText) view.findViewById(R.id.bs_after_lunch_valueid);
		mEditBsleep = (EditText) view
				.findViewById(R.id.bs_before_sleep_valueid);

		mEditLimosis.addTextChangedListener(textWatcher);
		mEditBlunch.addTextChangedListener(textWatcher);
		mEditAlunch.addTextChangedListener(textWatcher);
		mEditBsleep.addTextChangedListener(textWatcher);

		backIcon.setOnClickListener(listener);
		commitBtn.setOnClickListener(listener);
		layoutDateText.setOnClickListener(listener);

		DisplayMetrics dm = new DisplayMetrics();// 用来获取手机屏幕的属性，如分辨率
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenHeight = dm.heightPixels;// 获取屏幕高度

		mScrollView = (ScrollView) view.findViewById(R.id.scrollView_id);
		mEditAlunch.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {// 此处为得到焦点时的处理内容
					mScrollView.setScrollY(mScreenHeight);// 当“午餐后2小时”编辑框得到焦点时，让ScrollView滚到底部，以显示“提交”按钮
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
		case XUE_TANG_DATE_DIALOG_ID:
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
		case XUE_TANG_DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	private void updateContent(final BsInfoBean bean) {
		if (!this.isFinishing()) {
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mEditLimosis.setText("" + bean.mLimosis);
					mEditBlunch.setText("" + bean.mBLunch);
					mEditAlunch.setText("" + bean.mALunch);
					mEditBsleep.setText("" + bean.mBSleep);
				}
			});
		}
	}

	private void commitDataToServer() {
		mUploadBean = new UploadBsBean();
		mUploadBean.date = dateText.getText().toString();
		if (!TextUtils.isEmpty(mEditAlunch.getText().toString())) {
			mUploadBean.mALunchValue = mEditAlunch.getText().toString();
		} else {
			mUploadBean.mALunchValue = 0 + "";
		}
		if (!TextUtils.isEmpty(mEditBlunch.getText().toString())) {
			mUploadBean.mBLunchValue = mEditBlunch.getText().toString();
		} else {
			mUploadBean.mBLunchValue = 0 + "";
		}
		if (!TextUtils.isEmpty(mEditBsleep.getText().toString())) {
			mUploadBean.mBSleepValue = mEditBsleep.getText().toString();
		} else {
			mUploadBean.mBSleepValue = 0 + "";
		}
		if (!TextUtils.isEmpty(mEditLimosis.getText().toString())) {
			mUploadBean.mLimosisValue = mEditLimosis.getText().toString();
		} else {
			mUploadBean.mLimosisValue = 0 + "";
		}
		this.showWaittingDialog("上传血糖测量数据");
		InterfaceService.getInstance(this).UploadBs(mUploadBean, this);
	}

	@Override
	public void getMeasureDetailCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub
		super.getMeasureDetailCallback(retCode, errorMsg, bean);

		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			updateContent((BsInfoBean) bean);
			break;
		case 90036:
			// 没有记录
			updateContent(new BsInfoBean());
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
				// Intent intent = new Intent(TestXueTangActivity.this,
				// ReportDetailOfXueTangActivity.class);
				// intent.putExtra("bsInfo_id", mUploadBean.mId);
				// startActivity(intent);

				Intent intent = new Intent(TestXueTangActivity.this,
						ReportsDetailActivity.class);
				ArrayList<String> ids = new ArrayList<String>();
				ArrayList<String> types = new ArrayList<String>();
				ids.add(mUploadBean.mId);
				types.add(BusinessConst.BS_MESURE);
				intent.putStringArrayListExtra("bean_id", ids);
				intent.putStringArrayListExtra("bean_type", types);
				String roleId = AndroidConfiguration.getInstance(this)
						.getRoleId();
				intent.putExtra("role_id", roleId);
				startActivity(intent);

			} else {
				activity.mHashTestItems.put(BusinessConst.BS_MESURE,
						mUploadBean.mId);
			}
			finish();
			break;
		default:
			showAlert(errorMsg);
			break;
		}
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
		String roleid = AndroidConfiguration.getInstance(this).getRoleId();
		this.showWaittingDialog("获取当天的血糖记录");
		InterfaceService.getInstance(this).getMeasureDetail(roleid,
				dateText.getText().toString(), BusinessConst.BS_MESURE, this);

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
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			if (!TextUtils.isEmpty(mEditLimosis.getText().toString())
					|| !TextUtils.isEmpty(mEditBlunch.getText().toString())
					|| !TextUtils.isEmpty(mEditAlunch.getText().toString())
					|| !TextUtils.isEmpty(mEditBsleep.getText().toString())) {
				commitBtn.setAlpha(1.0f);
				commitBtn.setEnabled(true);
			} else {
				commitBtn.setAlpha(0.3f);
				commitBtn.setEnabled(false);
			}

		}
	};

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.test_xuetang_back_icon:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
			case R.id.test_xuetang_commit_button:
				commitDataToServer();
				break;
			case R.id.layout_date_of_xuetang:
				showDialog(XUE_TANG_DATE_DIALOG_ID);
				break;
			default:

			}

		}
	}
}
