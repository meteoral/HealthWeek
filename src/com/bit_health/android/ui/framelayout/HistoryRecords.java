package com.bit_health.android.ui.framelayout;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.ui.activities.ReportActivity;
import com.bit_health.android.ui.activities.ReportsDetailActivity;
import com.bit_health.android.ui.adapter.HistoryRecordsAdapter;
import com.bit_health.android.util.ReportModuleExpandListView;
import com.bit_health.android.util.ReportModuleExpandListView.IXListViewListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

/**********************************************************************
 * 类名：XinDianHistoryRecords
 * 
 * @author 梁才学 主要功能：心电测试中的历史记录布局 创建日期：2013.12.9
 **********************************************************************/
public class HistoryRecords extends FrameLayout implements IXListViewListener {

	public static final String CLASS_NAME = "XinDianHistoryRecords";
	private Activity mActivity;
	private LayoutInflater inflater;

	ReportModuleExpandListView expandablelistview;
	private HistoryRecordsAdapter adapter;
	private List<JsonBase> mInfos;

	public HistoryRecords(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
	}

	public void historyRecordsCreate(List<JsonBase> mEcgInfos) {
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.history_records, this);
		initExpandableListView(mEcgInfos);
	}

	/****************************************************************
	 * 方法描述：初始化列表数据
	 * 
	 *****************************************************************/
	private void initExpandableListView(List<JsonBase> infos) {
		// TODO Auto-generated method stub
		this.mInfos = infos;
		expandablelistview = (ReportModuleExpandListView) findViewById(R.id.records_expandable_listview_of_xindian);
		adapter = new HistoryRecordsAdapter(mActivity, mActivity.getResources()
				.getString(R.string.unit_of_xindian), mInfos);
		expandablelistview.setAdapter(adapter);
		expandablelistview.setPullLoadEnable(true);
		expandablelistview.setXListViewListener(this);

		// 将所有项设置成默认展开
		int groupCount = expandablelistview.getCount();
		for (int i = 0; i < groupCount; i++) {
			expandablelistview.expandGroup(i);
		}

		// 监听分组展开
		expandablelistview
				.setOnGroupExpandListener(new OnGroupExpandListener() {
					public void onGroupExpand(int groupPosition) {

					}
				});

		// 监听分组关闭
		expandablelistview
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {
					public void onGroupCollapse(int groupPosition) {

					}
				});
		// 监听子项单击
		expandablelistview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				String roleId = AndroidConfiguration.getInstance(mActivity)
						.getRoleId();
				JsonBase bean = mInfos.get(0);
				if (bean instanceof EcgInfoBean) {
					bean = adapter.mAdapterDatas.get(groupPosition).second
							.get(childPosition);
					EcgInfoBean ecgInfo = (EcgInfoBean) bean;
					Intent intent = new Intent(mActivity
							.getApplicationContext(),
							ReportsDetailActivity.class);
					ArrayList<String> ids = new ArrayList<String>();
					ArrayList<String> types = new ArrayList<String>();
					ids.add(ecgInfo.mId);
					types.add(BusinessConst.ECG_MESURE);
					intent.putStringArrayListExtra("bean_id", ids);
					intent.putStringArrayListExtra("bean_type", types);
					intent.putExtra("role_id", roleId);
					intent.putExtra("anlasy_type", BusinessConst.ALL_ANLASY);
					mActivity.startActivity(intent);
				}
				
				if (bean instanceof PpgInfoBean) {
					bean = adapter.mAdapterDatas.get(groupPosition).second
							.get(childPosition);
					PpgInfoBean ppgInfo = (PpgInfoBean) bean;
					Intent intent = new Intent(mActivity
							.getApplicationContext(),
							ReportsDetailActivity.class);
					ArrayList<String> ids = new ArrayList<String>();
					ArrayList<String> types = new ArrayList<String>();
					ids.add(ppgInfo.mId);
					types.add(BusinessConst.PPG_MESURE);
					intent.putStringArrayListExtra("bean_id", ids);
					intent.putStringArrayListExtra("bean_type", types);
					intent.putExtra("role_id", roleId);
					intent.putExtra("anlasy_type", BusinessConst.ALL_ANLASY);
					mActivity.startActivity(intent);
				}

				if (bean instanceof BsInfoBean) {
					bean = adapter.mAdapterDatas.get(groupPosition).second
							.get(childPosition
									/ HistoryRecordsAdapter.BS_MESURE_COUNT);// 注意这里除以3
					BsInfoBean bsInfo = (BsInfoBean) bean;
					Intent intent = new Intent(mActivity
							.getApplicationContext(),
							ReportsDetailActivity.class);
					ArrayList<String> ids = new ArrayList<String>();
					ArrayList<String> types = new ArrayList<String>();
					ids.add(bsInfo.mId);
					types.add(BusinessConst.BS_MESURE);
					intent.putStringArrayListExtra("bean_id", ids);
					intent.putStringArrayListExtra("bean_type", types);
					intent.putExtra("role_id", roleId);
					intent.putExtra("anlasy_type", BusinessConst.ALL_ANLASY);
					mActivity.startActivity(intent);
				}

				if (bean instanceof BpInfoBean) {
					bean = adapter.mAdapterDatas.get(groupPosition).second
							.get(childPosition
									/ HistoryRecordsAdapter.BP_MESURE_COUNT);
					BpInfoBean bpInfo = (BpInfoBean) bean;
					Intent intent = new Intent(mActivity
							.getApplicationContext(),
							ReportsDetailActivity.class);
					ArrayList<String> ids = new ArrayList<String>();
					ArrayList<String> types = new ArrayList<String>();
					ids.add(bpInfo.mId);
					types.add(BusinessConst.BP_MESURE);
					intent.putStringArrayListExtra("bean_id", ids);
					intent.putStringArrayListExtra("bean_type", types);
					intent.putExtra("role_id", roleId);
					intent.putExtra("anlasy_type", BusinessConst.ALL_ANLASY);
					mActivity.startActivity(intent);
				}
				return false;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bit_health.android.util.ReportModuleExpandListView.IXListViewListener
	 * #onRefresh()
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		((ReportActivity) mActivity)
				.getDataFromService(ReportActivity.STATUS_REFRESH);
	}

	public void onLoad() {
		if (expandablelistview != null) {
			expandablelistview.stopRefresh();
			expandablelistview.stopLoadMore();
			expandablelistview.setRefreshTime("刚刚");
		}
	}

	public void notifydatachange() {
		if (adapter != null) {
			adapter.ChangeToAdapterData(mInfos);
			adapter.notifyDataSetChanged();
			// 将所有项设置成默认展开
			int groupCount = expandablelistview.getCount();
			for (int i = 0; i < groupCount; i++) {
				expandablelistview.expandGroup(i);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bit_health.android.util.ReportModuleExpandListView.IXListViewListener
	 * #onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		((ReportActivity) mActivity)
				.getDataFromService(ReportActivity.STATUS_LOADMORE);
	}
}
