package com.bit_health.android.ui.activities;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.framelayout.HomePageContentOne;
import com.bit_health.android.util.BirthdayToAge;
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
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**********************************************************************
 * 类名：BasicInfoOfFamilyActivity
 * 
 * 主要功能："家庭成员"中的资料详情界面
 * 
 * @author 梁才学 创建日期：2013.12.12
 **********************************************************************/
public class BasicInfoOfFamilyActivity extends BaseActivity implements
		OnDateSetListener {
	public static final String FLAG_INTENT_ROLE_ID = "role_id";
	public static boolean isRefresh = false;// 标记是否更新本界面的数据
	public static final int BASIC_INFO_DATE_DIALOG_ID = 1;// 日期
	private static final int Pick_FROM_PHOTO = 2;
	private static final int Pick_FROM_CAMERA = 3;
	private static final int TAKE_THE_Pick = 4;

	private int mYear;
	private int mMonth;
	private int mDay;

	private LayoutInflater listContainer;
	private View view;
	private View basicInfoText;
	private View basicInfoEdit;
	private ImageView backIcon;// 标题栏返回图片
	private ImageView editBasicInfoImage;// 编辑图标
	private TextView editBasicInfoCommit;// 编辑状态下的提交图标
	public int care_lable = 0;

	private RoleInfoBean mRoleInfoBean;

	// 逻辑按钮
	private LinearLayout mLockButton;
	private LinearLayout mCareButton;
	private MyViewListener listener;

	// 角色信息相关变量
	private ImageView headPhoto;
	private TextView fullName;
	private TextView userName;
	private TextView gender;
	private TextView age;
	private TextView birthday;
	private TextView height;
	private TextView weight;

	// 编辑状态下的角色信息相关变量
	private ImageView headPhotoEdit;
	private TextView fullNameText;
	private EditText userNameEdit;
	private EditText heightEdit;
	private EditText weightEdit;
	private TextView birthdayEdit;
	private RadioGroup genderRadio;
	private String[] dialogItem = { "选择本地图片", "拍照" };
	private Bitmap mPhoto;// 剪裁之后得到的图片

	private String role_id;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == 1) {
				infoOfRole(role_id);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		listContainer = LayoutInflater.from(this);
		view = listContainer.inflate(R.layout.basic_info_of_family, null);
		setContentView(view);

		final Calendar calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);

		listener = new MyViewListener();
		backIcon = (ImageView) view.findViewById(R.id.basic_info_back_image);
		editBasicInfoImage = (ImageView) view
				.findViewById(R.id.edit_basic_info_image);
		editBasicInfoCommit = (TextView) view
				.findViewById(R.id.edit_basic_info_commit);

		mLockButton = (LinearLayout) view
				.findViewById(R.id.basic_info_role_lock);
		mCareButton = (LinearLayout) view
				.findViewById(R.id.basic_info_role_care);
		basicInfoText = view.findViewById(R.id.basic_info_of_textview);
		basicInfoEdit = view.findViewById(R.id.basic_info_of_edittext);

		headPhotoEdit = (ImageView) view
				.findViewById(R.id.basic_info_head_edit);
		fullNameText = (TextView) view.findViewById(R.id.set_fullname_text);
		userNameEdit = (EditText) view.findViewById(R.id.role_user_name_edit);
		birthdayEdit = (TextView) view.findViewById(R.id.role_birthday_choice);
		heightEdit = (EditText) view.findViewById(R.id.role_height_edit);
		weightEdit = (EditText) view.findViewById(R.id.role_weight_edit);
		genderRadio = (RadioGroup) view.findViewById(R.id.radiogroup_gender);

		((RadioButton) genderRadio.getChildAt(0)).toggle();// 设置默认RadioButton为第一个

		backIcon.setOnClickListener(listener);
		editBasicInfoImage.setOnClickListener(listener);
		editBasicInfoCommit.setOnClickListener(listener);
		birthdayEdit.setOnClickListener(listener);
		headPhotoEdit.setOnTouchListener(onTouchListener);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();// 如果 bundle 为空，表示当前角色为本人
		String currentRoleId = AndroidConfiguration.getInstance(
				getApplicationContext()).getRoleId();// 获取当前角色的 id
		if (bundle == null) {
			// setInfoForEditText(currentRoleId);
			infoOfRole(currentRoleId);
			role_id = currentRoleId;// edit_basic_info_image
		} else {
			String roleId = intent.getExtras().getString(FLAG_INTENT_ROLE_ID);// 获取非当前角色的
			// setInfoForEditText(roleId); // id
			infoOfRole(roleId);
			role_id = roleId;
		}

		if (!role_id.equals(currentRoleId)) {
			// 如果不是当前角色，隐藏编辑按钮
			editBasicInfoImage.setVisibility(View.GONE);
			mLockButton.setVisibility(View.GONE);
			// headPhoto.setClickable(false);
		} else {
			mCareButton.setVisibility(View.GONE);
			// headPhoto.setClickable(true);
			// headPhoto.setOnClickListener(listener);

			headPhoto.setOnTouchListener(onTouchListener);
		}
		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	// 给编辑界面赋值
	private void setInfoForEditText(String roleId) {
		// TODO Auto-generated method stub

		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(this
				.getApplicationContext());
		mRoleInfoBean = mRoleCatchInfo.getRole(roleId);
		Bitmap bmp = null;
		if ((bmp = HeadImageUtil.getHeadCatchImage(mRoleInfoBean.mId)) != null) {
			headPhotoEdit.setImageBitmap(bmp);
		} else {
			headPhotoEdit.setImageResource(HeadImageUtil
					.getLocalHeadimage(mRoleInfoBean.mFullName));
		}

		fullNameText.setText(mRoleInfoBean.mFullName);
		userNameEdit.setText(mRoleInfoBean.mName);
		birthdayEdit.setText(mRoleInfoBean.mBirthday);
		heightEdit.setText(mRoleInfoBean.mHeight);
		weightEdit.setText(mRoleInfoBean.mWeight);

		if (mRoleInfoBean.mGendar.equals("男")) {
			((RadioButton) genderRadio.getChildAt(0)).toggle();
		} else {
			((RadioButton) genderRadio.getChildAt(1)).toggle();
		}

	}

	/************************************************************************
	 * 方法描述：角色信息
	 ************************************************************************/
	private void infoOfRole(String roleId) {
		// TODO Auto-generated method stub
		headPhoto = (ImageView) view.findViewById(R.id.basic_info_head_image);
		fullName = (TextView) view.findViewById(R.id.role_full_name_text);
		userName = (TextView) view.findViewById(R.id.role_user_name_text);
		gender = (TextView) view.findViewById(R.id.role_gender_text);
		age = (TextView) view.findViewById(R.id.role_age_text);
		birthday = (TextView) view.findViewById(R.id.role_birthday_text);
		height = (TextView) view.findViewById(R.id.role_height_text);
		weight = (TextView) view.findViewById(R.id.role_weight_text);

		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(this
				.getApplicationContext());
		mRoleInfoBean = mRoleCatchInfo.getRole(roleId).clone();

		// headPhoto.setImageResource(HeadImageUtil
		// .getLocalHeadimage(mRoleInfoBean.mFullName));
		if ((mPhoto = HeadImageUtil.getHeadCatchImage(roleId)) != null) {
			headPhoto.setImageBitmap(mPhoto);
		} else {
			headPhoto.setImageResource(HeadImageUtil
					.getLocalHeadimage(mRoleInfoBean.mFullName));
		}

		fullName.setText(mRoleInfoBean.mFullName);
		userName.setText(mRoleInfoBean.mName);
		gender.setText(mRoleInfoBean.mGendar);
		birthday.setText(mRoleInfoBean.mBirthday);
		height.setText(mRoleInfoBean.mHeight);
		weight.setText(mRoleInfoBean.mWeight);

		BirthdayToAge mBirthdayToAge = new BirthdayToAge();
		String ageData = mBirthdayToAge.getAge(mRoleInfoBean.mBirthday);
		age.setText(ageData);
	}

	/************************************************************************
	 * 方法描述：提交编辑之后的角色信息
	 ************************************************************************/
	private void commitRoleEditInfo() {
		// TODO Auto-generated method stub
		showWaittingDialog("正在更新角色信息...");
		mRoleInfoBean.mFullName = fullNameText.getText().toString();
		mRoleInfoBean.mName = userNameEdit.getText().toString();
		mRoleInfoBean.mBirthday = birthdayEdit.getText().toString();
		mRoleInfoBean.mHeight = heightEdit.getText().toString();
		mRoleInfoBean.mWeight = weightEdit.getText().toString();
		if (genderRadio.getCheckedRadioButtonId() == R.id.gender_man) {
			mRoleInfoBean.mGendar = "男";
		} else {
			mRoleInfoBean.mGendar = "女";
		}

		InterfaceService mInterfaceService = InterfaceService
				.getInstance(getApplicationContext());
		mInterfaceService.updateRoleInfo(mRoleInfoBean, this);
	}

	/**********************************************************************
	 * 类名：监听改变角色名的选项
	 * 
	 **********************************************************************/
	public void changeRoleName(View view) {
		Intent intent = new Intent();
		intent.setClass(BasicInfoOfFamilyActivity.this,
				ChangeRoleNameActivity.class);
		startActivityForResult(intent,
				ChangeRoleNameActivity.CHANGE_ROLE_FULL_NAME);
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ChangeRoleNameActivity.CHANGE_ROLE_FULL_NAME
				&& resultCode == ChangeRoleNameActivity.CHANGE_ROLE_FULL_NAME) {
			String result = data.getExtras().getString("changeFullName");
			fullNameText.setText(result);

			int headPhotoId = data.getExtras().getInt("changeHeadPhoto_id");
			// headPhoto.setBackgroundResource(headPhotoId);
			headPhoto.setImageResource(headPhotoId);
		}

		switch (requestCode) {
		// 如果是直接从相册获取
		case Pick_FROM_PHOTO:
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// 如果是调用相机拍照
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

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			BasicInfoOfFamilyActivity.this.finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);

			if (isRefresh) {
				setResult(HomePageContentOne.GO_TO_BasicInfoOfFamilyActivity);
				isRefresh = false;
			}

			return false;
		}
		return super.onKeyDown(keyCode, event);
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
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (editBasicInfoImage.getVisibility() == View.VISIBLE) {
						headPhoto.setImageBitmap(mPhoto);
					} else if (editBasicInfoCommit.getVisibility() == View.VISIBLE) {
						headPhotoEdit.setImageBitmap(mPhoto);
					}
					BasicInfoOfFamilyActivity.this.sendBroadcast(new Intent(
							RoleInfoBean.ACTION_ROLEINFO_CHANGED));
				}
			});
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
		case 0:
			// 编辑角色信息，并提交更新后，回调到这里
			RoleCatchInfo.getInstance(this.getApplicationContext()).addRole(
					mRoleInfoBean);
			isRefresh = true;
			handler.sendEmptyMessage(1);
			break;
		default:
			showAlert(errorMsg);
			break;
		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case BASIC_INFO_DATE_DIALOG_ID:

			if (!birthdayEdit.getText().toString().isEmpty()) {
				getYmd(birthdayEdit.getText().toString());
			}

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

	// 获取年、月、日的各个具体数值
	private void getYmd(String source) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date date = sdf.parse(source); // 获取Date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date); // 转为新Calendar
			mYear = cal.get(Calendar.YEAR); // 年
			mMonth = cal.get(Calendar.MONTH); // 月
			mDay = cal.get(Calendar.DAY_OF_MONTH); // 日
		} catch (ParseException e) {
			e.printStackTrace();
			// 异常处理操作
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case BASIC_INFO_DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {
		birthdayEdit.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
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
			mPhoto = extras.getParcelable("data");// 剪裁之后得到的图片
			InterfaceService mInterfaceService = InterfaceService
					.getInstance(getApplicationContext());
			String filepath = HeadImageUtil.saveHeadimage(role_id, mPhoto);// 将裁剪到的图片保存到SD卡的指定目录下
			mInterfaceService.updateRoleHead(filepath, this);// 将头像上传到服务器
			showWaittingDialog("正在更新头像");
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

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.basic_info_back_image:
				BasicInfoOfFamilyActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				if (isRefresh) {
					setResult(HomePageContentOne.GO_TO_BasicInfoOfFamilyActivity);
					isRefresh = false;
				}
				break;

			case R.id.edit_basic_info_image:
				basicInfoText.setVisibility(View.GONE);
				basicInfoEdit.setVisibility(View.VISIBLE);
				editBasicInfoImage.setVisibility(View.GONE);
				editBasicInfoCommit.setVisibility(View.VISIBLE);
				setInfoForEditText(role_id);
				break;

			case R.id.edit_basic_info_commit:
				if (TextUtils.isEmpty(fullNameText.getText().toString())) {
					fullNameText.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "角色不能为空" + "</font>"));
					fullNameText.requestFocus();
					fullNameText.setCursorVisible(true);
					return;
				}

				if (TextUtils.isEmpty(userNameEdit.getText().toString())) {
					userNameEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "姓名不能为空" + "</font>"));
					userNameEdit.requestFocus();
					userNameEdit.setCursorVisible(true);
					return;
				}

				if (TextUtils.isEmpty(birthdayEdit.getText().toString())) {
					birthdayEdit.setError(Html.fromHtml("<font color=#FFFFFF>"
							+ "出生日期不能为空" + "</font>"));
					birthdayEdit.requestFocus();
					birthdayEdit.setCursorVisible(true);
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

				commitRoleEditInfo();// 向服务器中提交更改信息

				basicInfoEdit.setVisibility(View.GONE);
				basicInfoText.setVisibility(View.VISIBLE);

				editBasicInfoImage.setVisibility(View.VISIBLE);
				editBasicInfoCommit.setVisibility(View.GONE);
				break;

			case R.id.role_birthday_choice:
				showDialog(BASIC_INFO_DATE_DIALOG_ID);
				break;

			// case R.id.basic_info_head_image:
			// showPickDialog();
			// break;

			}
		}
	}

	private OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				if (view.getId() == R.id.basic_info_head_image) {
					changeLight(view, -50);
				} else if (view.getId() == R.id.basic_info_head_edit) {
					changeLight(view, -50);
				}

				break;

			case MotionEvent.ACTION_MOVE:
				break;

			case MotionEvent.ACTION_UP:
				if (view.getId() == R.id.basic_info_head_image) {
					changeLight(view, 0);
				} else if (view.getId() == R.id.basic_info_head_edit) {
					changeLight(view, 0);
				}
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
