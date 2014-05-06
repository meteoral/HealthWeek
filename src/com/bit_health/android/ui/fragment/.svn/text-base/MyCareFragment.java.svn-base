package com.bit_health.android.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.adapter.MyCareListAdapter;
import com.bit_health.android.ui.adapter.MyCareListAdapter.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**********************************************************************
 * 类名：MyCareFragment
 * 
 * 主要功能：菜单中的 "我的关注"选项
 * 
 * @author 梁才学 创建日期：2013.12.10
 **********************************************************************/
public class MyCareFragment extends BaseFragment implements OnItemClickListener {

	private AndroidActivityMananger mMananger;
	private View view;
	private ImageView backIcon;
	private ListView myCareListView;
	private MyCareListAdapter adapter;
	private ViewHolder mHolder;
	private List<String> mCareList = new ArrayList<String>();
	public boolean isChangeCareList = false;// 是否改变了角色列表

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_care_fragment, container, false);
		myCareListView = (ListView) view.findViewById(R.id.my_care_listview);
		adapter = new MyCareListAdapter(getActivity());
		myCareListView.setAdapter(adapter);
		myCareListView.setOnItemClickListener(this); // 当点击某一条时，触发这个方法

		backIcon = (ImageView) view.findViewById(R.id.my_care_back_icon);
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 如果改变了角色列表，重启主界面的activity
				if (isChangeCareList) {
					isChangeCareList = false;
					mMananger = AndroidActivityMananger.getInstatnce();
					mMananger.switchActivity("MenuChildPageActivity",
							FourModuleManangerActivity.class);
					getActivity().overridePendingTransition(
							R.anim.slide_left_in_2, R.anim.slide_right_out);
				} else {
					getActivity().finish();
					getActivity().overridePendingTransition(
							R.anim.slide_left_in, R.anim.slide_right_out);
				}
			}
		});

		mCareList = RoleCatchInfo.getInstance(
				this.getActivity().getApplicationContext()).getCareList();

		updateAllRoleInfo();
		SharedPreferences preference = getActivity().getSharedPreferences(
				getActivity().getPackageName(), Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/**********************************************************************
	 * 方法描述：点击某一条时，跳转到编辑界面
	 * 
	 * @param arg0
	 *            代表当前的 ListView
	 * @param arg1
	 *            代表点击的item
	 * @param arg2
	 *            当前点击的item在适配中的位置,第一条为 0
	 * @param arg3
	 *            The row id of the item that was clicked.
	 * @return 无
	 **********************************************************************/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		mHolder = (ViewHolder) arg1.getTag();
		// holder.attentionStateCheckBox.toggle();// 改变CheckBox的状态
		showWaittingDialog("更新关注...");
		adapter.setCheckCare(arg2, !mHolder.attentionStateCheckBox.isChecked(),
				this);// 将CheckBox的选中状况记录下来
		listHadChanged();
	}

	/**********************************************************************
	 * 方法描述：如果改变了角色列表，则退出FourModuleManangerActivity，因为，要重新启动它
	 * 
	 **********************************************************************/
	private void listHadChanged() {
		// TODO Auto-generated method stub
		isChangeCareList = true;
		if (FourModuleManangerActivity.AppInstance != null) {
			FourModuleManangerActivity.AppInstance.finish();
		}
	}

	/**********************************************************************
	 * 方法描述：当ListView有修改时需要及时刷新
	 * 
	 * @param 无
	 * @return 无
	 * @see NoteListPage # dataChanged
	 **********************************************************************/
	private void dataChanged() {
		// TODO Auto-generated method stub
		adapter.notifyDataSetChanged();// 通知ListView刷新
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
					dataChanged();
				}
			});
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 功能：在“我的关注”中点击添加关注某个角色后，返回主界面时，刷新主界面的信息
		// List<String> afterCareList = RoleCatchInfo.getInstance(
		// this.getActivity().getApplicationContext()).getCareList();
		// if (mCareList.size() != afterCareList.size()
		// || !mCareList.containsAll(afterCareList)) {
		// FourModuleManangerActivity mainActivity =
		// (FourModuleManangerActivity) AndroidActivityMananger
		// .getInstatnce().getActivity(
		// FourModuleManangerActivity.class.getSimpleName());
		// if (mainActivity != null) {
		// mainActivity.mContentModuleFragment.refreshViewPager();
		// }
		// }
	}
}
