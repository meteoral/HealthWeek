package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;
import com.bit_health.android.util.EmailAutoCompleteTextView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**********************************************************************
 * 类名：RegisterActivity
 * 
 * 主要功能：忘记密码界面
 * 
 * @author 梁才学 创建日期：2014.2.18
 **********************************************************************/
public class ForgetPasswordActivity extends BaseActivity {

	private LayoutInflater inflater;
	private View view;
	private ImageView backIcon;

	private View layoutAccount;// 输入邮箱的布局
	private View layoutInputPw;// 输入序列号和密码的整体布局
	private EmailAutoCompleteTextView fpAccountEdit;// 帐号
	private EditText fpVcodeStringEdit;// 验证码
	private EditText fpPwEdit;// 新密码
	private EditText fpPwComfirmEdit;// 确认密码
	private Button fpCommit;// 获取验证码的提交按钮
	private Button fpChangeCommit;// 修改密码的提交按钮

	String accountString;
	String vCodeString;
	String pwString;
	String pwComfirmString;
	String msg;// 回调返回的信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.login);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.forget_password, null);
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
				.findViewById(R.id.forget_password_back_image);

		layoutAccount = view.findViewById(R.id.layout_account);
		layoutInputPw = view.findViewById(R.id.layout_input_pw);
		fpAccountEdit = (EmailAutoCompleteTextView) view
				.findViewById(R.id.fp_account_edit);
		fpVcodeStringEdit = (EditText) view
				.findViewById(R.id.fp_serial_number_edit);
		fpPwEdit = (EditText) view.findViewById(R.id.fp_pw_edit);
		fpPwComfirmEdit = (EditText) view.findViewById(R.id.fp_pw_comfirm_edit);
		fpCommit = (Button) view.findViewById(R.id.fp_commit);
		fpChangeCommit = (Button) view.findViewById(R.id.fp_change_commit);

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
		fpCommit.setOnClickListener(listener);
		fpChangeCommit.setOnClickListener(listener);
	}

	/**********************************************************************
	 * 方法描述：判断是否可以提交获取验证码
	 * 
	 * @param
	 **********************************************************************/
	private boolean isCommitForSeriaNum() {

		accountString = fpAccountEdit.getText().toString();

		if (accountString.trim().equals("")) {
			fpAccountEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
					+ "帐号不能为空" + "</font>"));
			setErrorEditInput(fpAccountEdit);
			return false;
		}

		return true;
	}

	/**********************************************************************
	 * 方法描述：判断是否可以提交更改密码
	 * 
	 * @param
	 **********************************************************************/
	private boolean isCommitForChangePw() {

		vCodeString = fpVcodeStringEdit.getText().toString();
		pwString = fpPwEdit.getText().toString();
		pwComfirmString = fpPwComfirmEdit.getText().toString();

		if (vCodeString.trim().equals("")) {
			fpVcodeStringEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
					+ "验证码不能为空" + "</font>"));
			setErrorEditInput(fpVcodeStringEdit);
			return false;
		}

		if (pwString.trim().equals("")) {
			fpPwEdit.setError(Html.fromHtml("<font color=#FFFFFF>" + "新密码不能为空"
					+ "</font>"));
			setErrorEditInput(fpPwEdit);
			return false;
		} else if (pwComfirmString.trim().equals("")) {
			fpPwComfirmEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
					+ "确认密码不能为空" + "</font>"));
			setErrorEditInput(fpPwComfirmEdit);
			return false;
		} else if (!pwString.equals(pwComfirmString)) {
			fpPwEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
					+ "两次密码不一致，请重新输入！" + "</font>"));
			fpPwComfirmEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
					+ "两次密码不一致，请重新输入！" + "</font>"));
			fpPwEdit.setText("");
			fpPwComfirmEdit.setText("");
			setErrorEditInput(fpPwEdit);
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
	private void setErrorEditInput(EditText edit) {
		edit.requestFocus();// EditText获取焦点
		edit.setCursorVisible(true);// EditText显示光标
		// 弹出软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edit, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
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

			case R.id.forget_password_back_image:
				ForgetPasswordActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.fp_commit:
				if (isCommitForSeriaNum()) {
					goToGetSerialNum();
				} else {
					layoutInputPw.setVisibility(View.GONE);
				}
				break;

			case R.id.fp_change_commit:
				if (isCommitForChangePw()) {
					goToUpdatePassWord();
				}
				break;

			default:

			}
		}
	}

	/**********************************************************************
	 * 方法描述：到服务器获取验证码
	 * 
	 * @param
	 **********************************************************************/
	public void goToGetSerialNum() {
		// TODO Auto-generated method stub
		showWaittingDialog("正在处理请求，请稍等...");
		InterfaceService.getInstance(
				ForgetPasswordActivity.this.getApplicationContext())
				.fortgetPassword(accountString, "user",
						ForgetPasswordActivity.this);
	}

	/**********************************************************************
	 * 方法描述：到服务器进行更新密码
	 * 
	 * @param
	 **********************************************************************/
	public void goToUpdatePassWord() {
		// TODO Auto-generated method stub
		showWaittingDialog("正在处理请求，请稍等...");
		InterfaceService.getInstance(
				ForgetPasswordActivity.this.getApplicationContext())
				.updatePassword(accountString, "user", vCodeString, pwString,
						ForgetPasswordActivity.this);
	}
	
	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		super.callback(retCode, errorMsg);
		msg = errorMsg;
		hideWaittingDialog();

		switch (retCode) {
		case 0:
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (fpCommit.getVisibility() == View.VISIBLE) {// 获取验证码成功
						fpCommit.setVisibility(View.GONE);
						layoutInputPw.setVisibility(View.VISIBLE);
						fpAccountEdit.setEnabled(false);// 这时候邮箱不允许编辑了
						layoutAccount.setAlpha(0.3f);
					} else {// 更新密码成功

						Toast.makeText(ForgetPasswordActivity.this, "密码修改成功",
								Toast.LENGTH_SHORT).show();
						finish();
					}

				}
			});

			break;

		default:

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Builder builder = new AlertDialog.Builder(
							ForgetPasswordActivity.this);
					builder.setTitle("提示");
					builder.setMessage(msg);
					builder.setNegativeButton("确定", null);
					builder.create();
					builder.show();
				}
			});

			break;
		}
	}

}
