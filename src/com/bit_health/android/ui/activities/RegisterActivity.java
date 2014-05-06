package com.bit_health.android.ui.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;
import com.bit_health.android.util.EmailAutoCompleteTextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：RegisterActivity
 * 
 * 主要功能：注册界面
 * 
 * @author 梁才学 创建日期：2013.12.5
 **********************************************************************/
public class RegisterActivity extends BaseActivity {

	static final int SHOW_LINK_TO_TEXTVIEW = 1;

	private LayoutInflater inflater;
	private View view;
	private View layoutLink;
	private ImageView backIcon;
	private Button register_btn;
	private EmailAutoCompleteTextView registerAccount;// 注册帐号
	private EditText registerPassword;// 注册密码
	private EditText confirmationPassword;// 再次输入密码
	private TextView registerLinkText;// 注册时系统返回的链接
	String firstPw;
	String secondPw;
	String account;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SHOW_LINK_TO_TEXTVIEW:
				layoutLink.setVisibility(View.VISIBLE);
				registerLinkText.setText(msg.obj.toString());
				hideWaittingDialog();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.login);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.register, null);
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
				.findViewById(R.id.register_title_bar_back_icon);
		register_btn = (Button) view.findViewById(R.id.register_btn);
		registerAccount = (EmailAutoCompleteTextView) view.findViewById(R.id.register_account);
		registerPassword = (EditText) view.findViewById(R.id.register_password);
		confirmationPassword = (EditText) view
				.findViewById(R.id.confirmation_password);
		registerLinkText = (TextView) view
				.findViewById(R.id.register_link_text);
		layoutLink = view.findViewById(R.id.layout_link);

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
		register_btn.setOnClickListener(listener);
		registerLinkText.setOnClickListener(listener);
	}

	@Override
	public void registerCallback(int retCode, String errorMsg, String retMsg) {
		// TODO Auto-generated method stub
		super.callback(retCode, errorMsg);
		// hideWaittingDialog();
		switch (retCode) {
		case 0:
			Message msg = mHandler.obtainMessage();
			msg.what = SHOW_LINK_TO_TEXTVIEW;
			msg.obj = retMsg;
			mHandler.sendMessage(msg);
			break;
		case -2:
			showAlert("无网络连接。请连接至Wi-Fi网络，或启用移动数据并重试。");
			break;
		default:
			break;
		}
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：注册界面中的控件监听
	 * 
	 * @author 梁才学 创建日期：2013.12.5
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.register_title_bar_back_icon:
				RegisterActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.register_btn:
				if (isCanRegister()) {

					showWaittingDialog("正在处理请求，请稍等...");
					InterfaceService.getInstance(
							RegisterActivity.this.getApplicationContext())
							.register(account, secondPw, RegisterActivity.this);

					// mMananger.switchActivity("RegisterActivity",
					// RegisterIdentifyCodeActivity.class);
				}
				break;

			case R.id.register_link_text:
				// 点击链接跳到浏览器进行激活
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(registerLinkText.getText()
						.toString());
				intent.setData(content_url);
				startActivity(intent);
				break;
			default:

			}

		}

		/**********************************************************************
		 * 方法描述：判断注册密码和确认密码是否一致
		 * 
		 * @param
		 **********************************************************************/
		private boolean isCanRegister() {
			// TODO Auto-generated method stub

			firstPw = registerPassword.getText().toString();
			secondPw = confirmationPassword.getText().toString();
			account = registerAccount.getText().toString();

			if (account.trim().equals("")) {				
				registerAccount.setError(Html.fromHtml("<font color=#FFFFFF>"
	                    + "帐号不能为空" + "</font>"));
				setErrorEditInput(registerAccount);
				return false;
			} else if (!isEmail(account)) {
				registerAccount.setError(Html.fromHtml("<font color=#FFFFFF>"
	                    + "帐号格式不正确，请重新输入！" + "</font>"));
				setErrorEditInput(registerAccount);
				return false;
			}

			if (firstPw.trim().equals("")) {
				registerPassword.setError(Html.fromHtml("<font color=#FFFFFF>"
	                    + "密码不能为空" + "</font>"));
				setErrorEditInput(registerPassword);
				return false;
			} else if(secondPw.trim().equals("")){
				confirmationPassword.setError(Html.fromHtml("<font color=#FFFFFF>"
	                    + "确认密码不能为空" + "</font>"));
				setErrorEditInput(confirmationPassword);
				return false;
			}else if (!firstPw.equals(secondPw)) {
				registerPassword.setError(Html.fromHtml("<font color=#FFFFFF>"
	                    + "两次密码不一致，请重新输入！" + "</font>"));
				confirmationPassword.setError(Html.fromHtml("<font color=#FFFFFF>"
	                    + "两次密码不一致，请重新输入！" + "</font>"));
				registerPassword.setText("");
				confirmationPassword.setText("");				
				setErrorEditInput(registerPassword);						
				return false;
			} else {
				return true;
			}
		}

		/**********************************************************************
		 * 方法描述：EditText 输入不正确时，提示用户，并弹出软键盘
		 * 
		 * @param
		 **********************************************************************/
		private void setErrorEditInput(EditText edit){
			edit.requestFocus();// EditText获取焦点
			edit.setCursorVisible(true);// EditText显示光标
			// 弹出软键盘
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(edit, InputMethodManager.RESULT_SHOWN);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);		
		}
		
		/**********************************************************************
		 * 方法描述：判断帐号邮箱合法性
		 * 
		 * @param
		 **********************************************************************/
		public boolean isEmail(String email) {
			String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(email);

			return m.matches();
		}

		/**********************************************************************
		 * 方法描述：判断手机格式是否正确
		 * 
		 * @param
		 **********************************************************************/
		public boolean isMobileNO(String mobiles) {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);

			return m.matches();
		}
	}
}
