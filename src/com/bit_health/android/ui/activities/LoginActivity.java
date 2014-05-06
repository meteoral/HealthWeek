package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.util.EmailAutoCompleteTextView;
import com.bit_health.android.util.EncryptionUtil;
import com.bit_health.android.util.SetDialogFontSize;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**********************************************************************
 * 类名：LoginActivity
 * 
 * 主要功能：登录注册界面
 * 
 * @author 梁才学 创建日期：2013.12.5
 **********************************************************************/
public class LoginActivity extends BaseActivity {

	public static String LOGIN_ACCOUNT = "";
	public static String LOGIN_PASSWORD = "";

	private final int RETCODE90001 = 90001;
	private final int RETCODE90002 = 90002;

	private AndroidActivityMananger mMananger;
	private Button loginButton;
	private TextView registerText;
	private TextView shiyongText;
	private TextView forgetPasswordText;
	private EmailAutoCompleteTextView loginAccount;// 帐号
	private EditText loginPassword;// 密码
	private LayoutInflater inflater;
	private View view;

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case RETCODE90001:
				loginAccount.setText("");// 帐号不正确时，将帐号编辑框清空
				loginPassword.setText("");// 将密码编辑框清空
				break;

			case RETCODE90002:
				loginPassword.setText("");// 密码不正确时，将密码编辑框清空
				break;

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.login);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.login, null);
		setContentView(view);
		mMananger = AndroidActivityMananger.getInstatnce();
		initData();
		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		loginButton = (Button) view.findViewById(R.id.login_btn);
		registerText = (TextView) view.findViewById(R.id.register_text);
		shiyongText = (TextView) view.findViewById(R.id.shiyong_text);
		forgetPasswordText = (TextView) view
				.findViewById(R.id.forget_password_text);
		loginAccount = (EmailAutoCompleteTextView) view
				.findViewById(R.id.account_name_edit);
		loginPassword = (EditText) view.findViewById(R.id.input_password_edit);
		loginAccount.setText(LOGIN_ACCOUNT);
		loginPassword.setText(LOGIN_PASSWORD);

		MyViewListener listener = new MyViewListener();
		loginButton.setOnClickListener(listener);
		registerText.setOnClickListener(listener);
		shiyongText.setOnClickListener(listener);
		forgetPasswordText.setOnClickListener(listener);
	}

	/**********************************************************************
	 * 方法描述：点击返回键时，弹出对话框提示退出应用
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 **********************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
					.create();
			dialog.setTitle("提示");
			dialog.setMessage("确定要退出程序吗？");
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "退出",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			dialog.show();
			SetDialogFontSize.setDialogFontSize(dialog, getResources()
					.getDimension(R.dimen.normal_textsize));

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		super.callback(retCode, errorMsg);
		hideWaittingDialog();
		switch (retCode) {
		case 0:
			AndroidConfiguration.getInstance(this.getApplicationContext())
					.setmAccountPwd(
							EncryptionUtil.getEncryptMd5_32(loginPassword
									.getText().toString()));
			mMananger.switchActivity("LoginActivity",
					LoginIdentityActivity.class);
			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * @author 梁才学 主要功能：登录注册界面中的按钮监听 创建日期：2013.12.5
	 **********************************************************************/
	class MyViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.login_btn:
				if (loginAccount.getText().toString().equals("")) {
					loginAccount.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "帐号不能为空" + "</font>"));
					loginAccount.requestFocus();
					loginAccount.setCursorVisible(true);
					return;
				}

				if (loginPassword.getText().toString().equals("")) {
					loginPassword.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "密码不能为空" + "</font>"));
					loginPassword.requestFocus();
					loginPassword.setCursorVisible(true);
					return;
				}
				LOGIN_ACCOUNT = loginAccount.getText().toString();
				LOGIN_PASSWORD = loginPassword.getText().toString();

				/******************** 连接服务器 start *******************/
				showWaittingDialog("登陆中...");
				InterfaceService.getInstance(
						LoginActivity.this.getApplicationContext()).Login(
						loginAccount.getText().toString(),
						loginPassword.getText().toString(), LoginActivity.this);
				/******************** 连接服务器 end *******************/
				break;

			case R.id.register_text:
				mMananger.switchActivityNoClose("LoginActivity",
						RegisterActivity.class);
				break;

			case R.id.shiyong_text:
				// Toast.makeText(LoginActivity.this, "试用", Toast.LENGTH_SHORT)
				// .show();
				// mMananger.switchActivity("LoginActivity",
				// FourModuleManangerActivity.class);
				break;

			case R.id.forget_password_text:
				mMananger.switchActivityNoClose("LoginActivity",
						ForgetPasswordActivity.class);
				break;

			default:

			}
		}
	}
}
