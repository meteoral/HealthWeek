package com.bit_health.android.ui.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.BusinessException;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.util.HeadImageUtil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**********************************************************************
 * 类名：AddMembersActivity
 * 
 * 主要功能：添加成员界面
 * 
 * @author 梁才学 创建日期：2014.1.9
 **********************************************************************/
public class AddMembersActivity extends BaseActivity implements
		OnDateSetListener {

	static final int ADD_MEMBERS_DATE_DIALOG_ID = 1;// 日期
	private static final int Pick_FROM_PHOTO = 2;
	private static final int Pick_FROM_CAMERA = 3;
	private static final int TAKE_THE_Pick = 4;

	private LayoutInflater inflater;
	private View view;
	private ImageView backImage;
	private Calendar calendar;
	private int mYear;
	private int mMonth;
	private int mDay;
	private Button commitText;

	// 添加成员界面中编辑状态下的角色信息相关变量
	private ImageView headPhoto;
	private EditText dateText;
	private EditText fullNameEdit;
	private EditText userNameEdit;
	private EditText heightEdit;
	private EditText weightEdit;
	private RadioGroup genderRadio;
	private RoleInfoBean mRoleInfoBean;

	private MyViewListener listener;
	private String[] dialogItem = { "选择本地图片", "拍照" };
	private Bitmap mPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.add_members, null);
		setContentView(view);
		initDate();
		initAddMember();

		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	private void initAddMember() {
		// TODO Auto-generated method stub
		fullNameEdit = (EditText) view
				.findViewById(R.id.add_members_role_fullname_text);
		fullNameEdit.setInputType(InputType.TYPE_NULL); // 强制不弹出软键盘
		fullNameEdit.setOnClickListener(listener);
		userNameEdit = (EditText) view
				.findViewById(R.id.add_members_username_edit);
		heightEdit = (EditText) view.findViewById(R.id.add_members_height_edit);
		weightEdit = (EditText) view.findViewById(R.id.add_members_weight_edit);
		genderRadio = (RadioGroup) view
				.findViewById(R.id.add_member_radiogroup_gender);
		((RadioButton) genderRadio.getChildAt(0)).toggle();// 设置默认RadioButton为第一个
	}

	private void initDate() {
		// TODO Auto-generated method stub
		calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);

		listener = new MyViewListener();
		backImage = (ImageView) view.findViewById(R.id.add_members_back_icon);
		headPhoto = (ImageView) view
				.findViewById(R.id.add_member_headphoto_image);
		dateText = (EditText) view.findViewById(R.id.add_member_date_text);
		dateText.setInputType(InputType.TYPE_NULL); // 强制不弹出软键盘
		commitText = (Button) view.findViewById(R.id.add_members_commit_btn);
		commitText.setOnClickListener(listener);
		backImage.setOnClickListener(listener);
		dateText.setOnClickListener(listener);
		headPhoto.setOnTouchListener(onTouchListener);
		
		view.findViewById(R.id.layout_choice_date).setOnClickListener(listener);
		view.findViewById(R.id.layout_username_edit).setOnClickListener(
				listener);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case ADD_MEMBERS_DATE_DIALOG_ID:
			DatePickerDialog mDialog = new DatePickerDialog(this, this, mYear,
					mMonth, mDay);
			Calendar cal = Calendar.getInstance();
			cal.set(1915, 0, 1);// 设置一个年月日作为最小的选择日期:1915年1月1日
			long time = cal.getTimeInMillis();// 获取该年月日的毫秒数
			mDialog.getDatePicker().setMinDate(time);// 设置最小的日期是：1915年1月1日
			// mDialog.getDatePicker().setMaxDate(new Date().getTime());//
			// 设置最大的日期是当前的年月日

			Calendar cal2 = Calendar.getInstance();
			cal2.set(getCurrentYear(), 11, 31);
			long time2 = cal2.getTimeInMillis();
			mDialog.getDatePicker().setMaxDate(time2);// 设置最大的日期是去年的年末

			return mDialog;
		}
		return null;
	}

	// 获取去年的年份，如：2013
	public int getCurrentYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date d = new Date();
		String t = sdf.format(d);
		String y = t.substring(0, 4);
		return Integer.valueOf(y) - 1;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case ADD_MEMBERS_DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	/**
	 * 点击对话框中的"设定"按钮时，执行这里.这个方法需要实现这个借口：OnDateSetListener
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		updateDateDisplay();
	}

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {
		dateText.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
		dateText.setError(null, null);// 清除自动提示的信息
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/************************************************************************
	 * 方法描述：提交编辑之后的角色信息
	 ************************************************************************/
	private void commitAddMemberInfo() {
		// TODO Auto-generated method stub
		showWaittingDialog("正在添加角色信息...");
		mRoleInfoBean = new RoleInfoBean();
		mRoleInfoBean.mFullName = fullNameEdit.getText().toString();
		mRoleInfoBean.mName = userNameEdit.getText().toString();
		mRoleInfoBean.mBirthday = dateText.getText().toString();
		mRoleInfoBean.mHeight = heightEdit.getText().toString();
		mRoleInfoBean.mWeight = weightEdit.getText().toString();
		if (genderRadio.getCheckedRadioButtonId() == R.id.add_member_gender_man) {
			mRoleInfoBean.mGendar = "男";
		} else {
			mRoleInfoBean.mGendar = "女";
		}

		InterfaceService mInterfaceService = InterfaceService
				.getInstance(getApplicationContext());
		mInterfaceService.addRole(mRoleInfoBean, this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mPhoto != null) {
			mPhoto.recycle();
			mPhoto = null;
		}
	}

	@Override
	public void updateHeadback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		super.updateHeadback(retCode, errorMsg);
		hideWaittingDialog();
		switch (retCode) {
		case 0:
			break;
		default:
			showAlert(BusinessException.getErrorMessage(this, retCode));
			HeadImageUtil.deleteHeadimage(mRoleInfoBean.mId);
			break;
		}

		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo
				.getInstance(AddMembersActivity.this.getApplicationContext());
		mRoleCatchInfo.getRoles().add(mRoleInfoBean);
		mRoleCatchInfo.commit();
		// 通过 setResult(1);来通知 LoginIdentityActivity 刷新列表，并退出当前界面
		setResult(1);
		AddMembersActivity.this.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

	}

	@Override
	public void addroleCallback(int retCode, String errorMsg, String roleid) {
		// TODO Auto-generated method stub
		super.callback(retCode, errorMsg);
		switch (retCode) {
		case 0:
			// 添加成功后，把数据添加到缓存
			RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(this
					.getApplicationContext());
			mRoleInfoBean.mId = roleid;
			if (mPhoto != null) {
				InterfaceService mInterfaceService = InterfaceService
						.getInstance(getApplicationContext());
				String filepath = HeadImageUtil.saveHeadimage(roleid, mPhoto);
				mInterfaceService.updateRoleHead(filepath, this);
			} else {
				mRoleCatchInfo.getRoles().add(mRoleInfoBean);
				mRoleCatchInfo.commit();
				// 通过 setResult(1);来通知 LoginIdentityActivity 刷新列表，并退出当前界面
				setResult(1);
				AddMembersActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
			}
			break;
		default:
			showAlert(BusinessException.getErrorMessage(this, retCode));
			break;
		}
	}

	/**********************************************************************
	 * 类名：监听选择角色的选项
	 * 
	 **********************************************************************/
	public void choiceRoleName(View view) {
		Intent intent = new Intent();
		intent.setClass(AddMembersActivity.this,
				AddMembersOfEditInfoActivity.class);
		startActivityForResult(intent,
				AddMembersOfEditInfoActivity.ROLE_FULL_NAME_AND_HEAD_PHOTO);

		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == AddMembersOfEditInfoActivity.ROLE_FULL_NAME_AND_HEAD_PHOTO
				&& resultCode == 1) {
			String result = data.getExtras().getString("fullName");
			fullNameEdit.setText(result);
			fullNameEdit.setError(null, null);// 清除自动提示的信息

			int headPhotoId = data.getExtras().getInt("headPhoto_id");
			headPhoto.setImageResource(headPhotoId);

		}

		switch (requestCode) {
		// 如果是直接从相册获取
		case Pick_FROM_PHOTO:
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// 如果是调用相机拍照时
		case Pick_FROM_CAMERA:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/BitHealth.jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		// 取得裁剪后的图片
		case TAKE_THE_Pick:
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 裁剪图片方法实现
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, TAKE_THE_Pick);
	}

	/**
	 * 保存裁剪之后的图片数据
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			if (mPhoto != null) {
				mPhoto.recycle();
				mPhoto = null;
			}
			mPhoto = extras.getParcelable("data");

			/*
			 * InterfaceService mInterfaceService = InterfaceService
			 * .getInstance(getApplicationContext()); String roleid =
			 * AndroidConfiguration.getInstance(this).getRoleId(); String
			 * filepath = HeadImageUtil.saveHeadimage(roleid, mPhoto);
			 * mInterfaceService.updateRoleHead(filepath, this);
			 * showWaittingDialog("正在更新头像");
			 */
			headPhoto.setImageBitmap(mPhoto);
		}
	}

	private void showPickDialog() {
		AlertDialog mAlertDialog = new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(dialogItem, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {// 相册
							dialog.dismiss();
							Intent intent = new Intent(Intent.ACTION_PICK, null);
							intent.setDataAndType(
									MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									"image/*");// 设置数据类型,如果要限制上传到服务器的图片类型时可以直接写
												// "image/jpg 、 image/png"等类型
							startActivityForResult(intent, Pick_FROM_PHOTO);
						} else if (which == 1) {// 拍照
							dialog.dismiss();
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 下面这句指定调用相机拍照后的照片存储的路径
							intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
									.fromFile(new File(Environment
											.getExternalStorageDirectory(),
											"BitHealth.jpg")));
							startActivityForResult(intent, Pick_FROM_CAMERA);
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).show();

		mAlertDialog.setCanceledOnTouchOutside(false);
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：添加登陆身份界面中的控件监听
	 * 
	 * @author 梁才学 创建日期：2014.1.9
	 **********************************************************************/
	class MyViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_members_back_icon:
				AddMembersActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.add_member_date_text:
				showDialog(ADD_MEMBERS_DATE_DIALOG_ID);
				break;

			case R.id.add_members_role_fullname_text:

				choiceRoleName(v);

				break;

			case R.id.add_members_commit_btn:// 提交

				/*
				 * 一点说明：在判断EditText为空的时候给出提示，用android自带的setError()函数，
				 * 发现文本的颜色和背景的颜色相同，即都是白色，从而导致看不见文本， 解决的方法是：使用 Html 的方式来设置文本的颜色
				 */
				if (TextUtils.isEmpty(fullNameEdit.getText().toString())) {
					fullNameEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "角色不能为空" + "</font>"));
					fullNameEdit.requestFocus();
					fullNameEdit.setCursorVisible(true);
					return;
				}

				if (TextUtils.isEmpty(userNameEdit.getText().toString())) {
					userNameEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "姓名不能为空" + "</font>"));
					userNameEdit.requestFocus();
					userNameEdit.setCursorVisible(true);
					return;
				}

				if (TextUtils.isEmpty(dateText.getText().toString())) {
					dateText.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "出生日期不能为空" + "</font>"));
					dateText.requestFocus();
					dateText.setCursorVisible(true);
					return;
				}

				String heightText = heightEdit.getText().toString();
				if (TextUtils.isEmpty(heightText)) {
					heightEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "身高不能为空" + "</font>"));
					heightEdit.requestFocus();
					heightEdit.setCursorVisible(true);
					return;
				} else {
					if (Float.valueOf(heightText) < 100
							|| Float.valueOf(heightText) > 210) {
						heightEdit.setError(Html
								.fromHtml("<font color=#FFFFFF>"
										+ "请输入100~210之间的数据" + "</font>"));
						heightEdit.requestFocus();
						heightEdit.setCursorVisible(true);
						return;
					}
				}

				if (TextUtils.isEmpty(weightEdit.getText().toString())) {
					weightEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "体重不能为空" + "</font>"));
					weightEdit.requestFocus();
					weightEdit.setCursorVisible(true);
					return;
				}
				commitAddMemberInfo();
				break;

			case R.id.layout_choice_date:
				showDialog(ADD_MEMBERS_DATE_DIALOG_ID);
				break;

			case R.id.layout_username_edit:
				// 弹出软键盘
				userNameEdit.requestFocus();
				userNameEdit.setCursorVisible(true);// 显示光标
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(userNameEdit, InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
				break;

			default:
				break;
			}
		}
	}
	
	private OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				changeLight(view, -50);
				break;

			case MotionEvent.ACTION_MOVE:
				break;

			case MotionEvent.ACTION_UP:
				changeLight(view, 0);
				showPickDialog();
				break;

			default:
				break;
			}
			return true;
		}
	};

	// 点击图片时，设置点击变暗的效果
	private void changeLight(View imageView, int brightness) {

		if (imageView instanceof ImageView) {
			ColorMatrix cMatrix = new ColorMatrix();
			cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
					brightness,// 改变亮度
					0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
			((ImageView) imageView).setColorFilter(new ColorMatrixColorFilter(
					cMatrix));
		}
	}
}
