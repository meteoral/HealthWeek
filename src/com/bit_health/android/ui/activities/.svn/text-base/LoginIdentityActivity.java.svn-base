package com.bit_health.android.ui.activities;

import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.BusinessException;
import com.bit_health.android.controllers.IServerBusiness;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.adapter.LoginIdentityGV_Adapter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**********************************************************************
 * 类名：LoginIdentityActivity
 * 
 * 主要功能：选择登录身份界面
 * 
 * @author 梁才学 创建日期：2013.12.25
 **********************************************************************/
public class LoginIdentityActivity extends BaseActivity {
	public static final int INITDATA_FOR_ROLEINFO = 1;
	public static boolean isComeFromMenuFragment = false;// 判断是否从侧滑菜单中跳转进来的
	public static boolean isComeFromOneKeyTestActivity = false;// 判断是否一键测试中的"更改角色"跳转进来的
	public static final int REFRESH_LoginIdentityActivity = 2;

	private GridView gridview;
	// private List<Pair<String, String>> itemTitle = new ArrayList<Pair<String,
	// String>>();

	private View rechoiceLoginIdentity;// 界面标题栏的布局
	private ImageView backImage;// 界面标题栏布局中的返回图标
	private TextView choiceLoginIdentityTitle;// 界面的title

	private AndroidActivityMananger mMananger;
	private LoginIdentityGV_Adapter mAdapter;
	private String mCurrentSelectId;
	private boolean isDelRole = false;
	List<RoleInfoBean> roles;
	InterfaceService mInterfaceService;
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (RoleInfoBean.ACTION_ROLEINFO_CHANGED.equals(intent.getAction())) {
				if (mAdapter != null) {
					mAdapter.notifyDataSetChanged();
				}
			}
		}
	};
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case INITDATA_FOR_ROLEINFO:
				initListView();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_identity);

		mMananger = AndroidActivityMananger.getInstatnce();
		setTitleContent();
		getRoleInfoFromCatch();
		getDataFromService();
		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));

		IntentFilter filter = new IntentFilter();
		filter.addAction(RoleInfoBean.ACTION_ROLEINFO_CHANGED);
		registerReceiver(mReceiver, filter);
	}

	/**********************************************************************
	 * 方法描述：根据进入本界面的不同来源，设置相应的标题栏内容
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void setTitleContent() {
		// TODO Auto-generated method stub
		rechoiceLoginIdentity = findViewById(R.id.layout_rechoice_login_identity);
		choiceLoginIdentityTitle = (TextView) findViewById(R.id.choice_login_identity_title);

		if (isComeFromMenuFragment || isComeFromOneKeyTestActivity) {
			choiceLoginIdentityTitle.setVisibility(View.GONE);
			rechoiceLoginIdentity.setVisibility(View.VISIBLE);
			backImage = (ImageView) findViewById(R.id.login_identity_back_icon);
			backImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isComeFromOneKeyTestActivity) {
						finish();
					} else {
						mMananger.switchActivity("LoginIdentityActivity",
								FourModuleManangerActivity.class);
					}
					overridePendingTransition(R.anim.slide_left_in_2,
							R.anim.slide_right_out);
				}
			});
		} else {
			rechoiceLoginIdentity.setVisibility(View.GONE);
			choiceLoginIdentityTitle.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void getRoleInfoListCallback(int retCode, String errorMsg,
			List<RoleInfoBean> beans) {
		// TODO Auto-generated method stub
		super.getRoleInfoListCallback(retCode, errorMsg, beans);
		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			mHandler.sendEmptyMessage(INITDATA_FOR_ROLEINFO);
			setRoleInfoToCatch(beans);
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

			if (isDelRole) {// 如果是执行了删除操作，删除成功后，不需要跳转到下一界面
				isDelRole = false;
				// 这种方法是在子线程中，发消息给主线程进行刷新VIEW
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mAdapter.notifyDataSetChanged();
					}
				});
			} else {
				try {

					// IServerBusiness.getInstance(this.getApplicationContext()).getCareRoleList();
					// mMananger.switchActivity("LoginIdentityActivity",
					// FourModuleManangerActivity.class);
					IServerBusiness.getInstance(this.getApplicationContext())
							.getCareRoleList();
					if (isComeFromOneKeyTestActivity) {
						finish();
					} else {
						mMananger.switchActivity("LoginIdentityActivity",
								FourModuleManangerActivity.class);
					}
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					showAlert(e.getMessage());
				}
			}

			break;
		case -5:
			showAlert("这是用户本身的角色，不能删除！");
			break;
		case 90002:
			// showAlertDialog("密码错误");
			passwordVerify();
			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}

	/**********************************************************************
	 * 方法描述：初始化选择角色列表
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void initListView() {
		// TODO Auto-generated method stub

		if (gridview == null) {
			gridview = (GridView) findViewById(R.id.login_identity_gridview);
		}

		if (mAdapter == null) {
			mAdapter = new LoginIdentityGV_Adapter(this);
			gridview.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				List<RoleInfoBean> roles = RoleCatchInfo.getInstance(
						LoginIdentityActivity.this).getRoles();
				if (position == roles.size()) {
					// 添加的图标
					Intent intent = new Intent();
					intent.setClass(LoginIdentityActivity.this,
							AddMembersActivity.class);
					startActivityForResult(intent,
							REFRESH_LoginIdentityActivity);

				} else {
					showWaittingDialog("选择角色中...");
					InterfaceService.getInstance(
							LoginIdentityActivity.this.getApplicationContext())
							.roleLogin(roles.get(position).mId, "",
									LoginIdentityActivity.this);
					mCurrentSelectId = roles.get(position).mId;
				}
			}
		});

		// 长安删除角色
		gridview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				roles = RoleCatchInfo.getInstance(LoginIdentityActivity.this)
						.getRoles();
				mInterfaceService = InterfaceService
						.getInstance(LoginIdentityActivity.this
								.getApplicationContext());

				if (position == roles.size()) {
					return false;// 如果选择的是"添加"图标，直接返回
				}

				final int pos = position;
				// TODO Auto-generated method stub
				Builder builder = new AlertDialog.Builder(
						LoginIdentityActivity.this);
				builder.setTitle("删除角色");
				builder.setMessage("确定删除该角色吗？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								String currentRoleId = AndroidConfiguration
										.getInstance(LoginIdentityActivity.this)
										.getRoleId();// 获取当前角色的 id
								// 不允许删除当前角色
								if ((roles.get(pos).mId).equals(currentRoleId)) {
									Toast.makeText(LoginIdentityActivity.this,
											"当前角色不能删除", Toast.LENGTH_SHORT)
											.show();
								} else {
									mInterfaceService.deleteRole(
											roles.get(pos).mId,
											LoginIdentityActivity.this);

									isDelRole = true;
									showWaittingDialog("正在删除...");
								}
							}
						});
				builder.setNegativeButton("取消", null);
				builder.create();
				builder.show();

				return true;
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// 从添加角色界面返回时，刷新界面
		if (requestCode == REFRESH_LoginIdentityActivity && resultCode == 1) {
			// 触发列表重新从缓存中读取数据
			mAdapter.notifyDataSetChanged();
		}
	}

	/**********************************************************************
	 * 方法描述：从缓存中读取的角色列表信息
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void getRoleInfoFromCatch() {
		// TODO Auto-generated method stub
		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(this
				.getApplicationContext());
		mRoleCatchInfo.getRoles();
		initListView();
	}

	/**********************************************************************
	 * 方法描述：把角色列表信息写入缓存中
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void setRoleInfoToCatch(List<RoleInfoBean> beans) {
		// TODO Auto-generated method stub
		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(this
				.getApplicationContext());
		mRoleCatchInfo.setRolesAllData(beans);
	}

	/**********************************************************************
	 * 方法描述：获取的角色列表信息
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void getDataFromService() {
		// TODO Auto-generated method stub
		showWaittingDialog("更新角色列表...");
		InterfaceService.getInstance(this.getApplicationContext())
				.getRoleInfoList(this);

	}

	/**********************************************************************
	 * 方法描述：显示对话框验证密码，这个方法暂时保留，以后可能用到
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	private void passwordVerify() {
		LayoutInflater inflater = LayoutInflater
				.from(LoginIdentityActivity.this);
		final LinearLayout linearLayout = (LinearLayout) inflater.inflate(
				R.layout.password_verify_dialog, null);

		Builder builder = new AlertDialog.Builder(LoginIdentityActivity.this);
		builder.setMessage("密码错误，请输入密码");
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				showWaittingDialog("角色登陆中...");
				EditText editText = (EditText) linearLayout
						.findViewById(R.id.input_password_edit);
				InterfaceService.getInstance(
						LoginIdentityActivity.this.getApplicationContext())
						.roleLogin(mCurrentSelectId,
								editText.getText().toString(),
								LoginIdentityActivity.this);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.setView(linearLayout);
		builder.create();
		builder.show();
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

			if (isComeFromMenuFragment) {
				mMananger.switchActivity("LoginIdentityActivity",
						FourModuleManangerActivity.class);
				overridePendingTransition(R.anim.slide_left_in_2,
						R.anim.slide_right_out);
			} else if (isComeFromOneKeyTestActivity) {
				finish();
				overridePendingTransition(R.anim.slide_left_in_2,
						R.anim.slide_right_out);
			} else {
				Builder builder = new AlertDialog.Builder(
						LoginIdentityActivity.this);

				builder.setTitle("中科汇康");
				builder.setMessage("确定要退出程序吗？");
				builder.setPositiveButton("退出",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						});
				builder.setNegativeButton("取消", null);
				builder.create();
				builder.show();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isComeFromMenuFragment = false;
		isComeFromOneKeyTestActivity = false;
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}
	}
}
