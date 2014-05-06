package com.bit_health.android.ui.fragment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.activities.AddMembersActivity;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.BasicInfoOfFamilyActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.activities.MenuChildPageActivity;
import com.bit_health.android.ui.adapter.MyFamilyAdapter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**********************************************************************
 * 类名：MyFamilyFragment
 * 
 * 主要功能：菜单中的 "家庭成员"选项
 * 
 * @author 梁才学 创建日期：2013.12.10
 **********************************************************************/
public class MyFamilyFragment extends BaseFragment {
	private AndroidActivityMananger mMananger;
	private View view;
	private ImageView backIcon;

	private ListView mListView;
	private MyFamilyAdapter adapter;
	private List<RoleInfoBean> mRoles = new ArrayList<RoleInfoBean>();
	private InterfaceService mInterfaceService;
	public boolean isDelRole = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mMananger = AndroidActivityMananger.getInstatnce();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_family_fragment, container, false);
		backIcon = (ImageView) view.findViewById(R.id.my_family_back_image);

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);

		mRoles = RoleCatchInfo.getInstance(
				this.getActivity().getApplicationContext()).getRoles();
		mListView = (ListView) view.findViewById(R.id.my_family_listview);
		adapter = new MyFamilyAdapter(getActivity(),
				mRoles);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == mRoles.size()) {
					mMananger.switchActivityNoClose("MenuChildPageActivity",
							AddMembersActivity.class);
					getActivity().overridePendingTransition(
							R.anim.slide_right_in, R.anim.slide_left_out);
				} else {
					Hashtable<String, String> pairs = new Hashtable<String, String>();
					pairs.put(BasicInfoOfFamilyActivity.FLAG_INTENT_ROLE_ID,
							mRoles.get(position).mId);
					mMananger.switchActivityNoClose("MenuChildPageActivity",
							BasicInfoOfFamilyActivity.class, pairs);

					getActivity().overridePendingTransition(
							R.anim.slide_right_in, R.anim.slide_left_out);
				}
			}
		});

		// 长按删除角色
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (position != mRoles.size()) {
					mInterfaceService = InterfaceService
							.getInstance(getActivity().getApplicationContext());

					final int pos = position;
					// TODO Auto-generated method stub
					Builder builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("删除角色");
					builder.setMessage("确定删除该角色吗？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									String currentRoleId = AndroidConfiguration
											.getInstance(getActivity())
											.getRoleId();// 获取当前角色的 id
									// 不允许删除当前角色
									if ((mRoles.get(pos).mId)
											.equals(currentRoleId)) {
										Toast.makeText(getActivity(),
												"当前角色不能删除", Toast.LENGTH_SHORT)
												.show();
									} else {
										mInterfaceService.deleteRole(
												mRoles.get(pos).mId,
												(MenuChildPageActivity) getActivity());

										isDelRole = true;
										showWaittingDialog("正在删除...");
									}
								}
							});
					builder.setNegativeButton("取消", null);
					builder.create();
					builder.show();
				}

				return true;
			}
		});

		// // 当点击菜单选项，等页面数据加载完毕之后，再滑动过去，就不出现滑动卡顿了
		// ((MenuChildPageActivity) getActivity()).getSlidingMenu().toggle();

		updateAllRoleInfo();
		SharedPreferences preference = getActivity().getSharedPreferences(
				getActivity().getPackageName(), Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	/** 删除角色之后，刷新本界面 **/
	public void refleshView() {
		if (isDelRole) {
			hideWaittingDialog();
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					// 如果进行了删除操作，则退出FourModuleManangerActivity，因为，要重新启动它
					if (FourModuleManangerActivity.AppInstance != null) {
						FourModuleManangerActivity.AppInstance.finish();
					}
					adapter.notifyDataSetChanged();
				}
			});
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void callbackOk() {
		// TODO Auto-generated method stub
		super.callbackOk();
		if (this.getActivity() != null) {
			this.getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					adapter.notifyDataSetChanged();
				}
			});
		}
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.my_family_back_image:

				// 如果进行了删除角色的操作，重启主界面的activity
				if (isDelRole) {
					isDelRole = false;
					mMananger.switchActivity("MenuChildPageActivity",
							FourModuleManangerActivity.class);
					getActivity().overridePendingTransition(
							R.anim.slide_left_in_2, R.anim.slide_right_out);
				} else {
					getActivity().finish();
					getActivity().overridePendingTransition(
							R.anim.slide_left_in, R.anim.slide_right_out);
				}

				break;

			default:

			}

		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.notifyDataSetChanged();
	}
}
