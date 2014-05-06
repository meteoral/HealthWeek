package com.bit_health.android.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.DataCatchInterface;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.activities.ReportOfMaiboActivity;
import com.bit_health.android.ui.activities.ReportOfXinDianActivity;
import com.bit_health.android.ui.activities.ReportOfXueTangActivity;
import com.bit_health.android.ui.activities.ReportOfXueYaActivity;
import com.bit_health.android.ui.activities.ReportsDetailActivity;
import com.bit_health.android.ui.adapter.ReportModuleListViewAdapter;
import com.bit_health.android.util.ReportModuleListView;
import com.bit_health.android.util.ReportModuleListView.IXListViewListener;
import com.slidingmenu.lib.SlidingMenu;

/**********************************************************************
 * 类名：ReportModuleFragment
 * 
 * 主要功能：报告模块
 * 
 * @author 梁才学 创建日期：2013.12.23
 **********************************************************************/
public class ReportModuleFragment extends BaseFragment implements
		IXListViewListener, OnItemClickListener {

	private View view;
	private ReportModuleListView mListView;
	private ReportModuleListViewAdapter adapter;
	private AndroidActivityMananger mMananger;
	// private Handler mHandler;
	// private String[] names;
	// private String[] contents;

	private List<JsonBase> mInfos;
	// title栏的相关变量
	private Animation showAction, hideAction;
	private LinearLayout reportModuleTopBar;
	private int lastY, curY;
	private int delatY;
	private boolean isTitleBarShown = true;// 记录标题栏是否已经显示, true: 显示；false: 未显示
	private boolean isLoadMore = false; // 目前状态是加载更多?

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 在这个界面中不允许滑动到菜单界面
		((FourModuleManangerActivity) getActivity()).getSlidingMenu()
				.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}

	@Override
	public void getMeasuresCallback(int retCode, String errorMsg,
			List<JsonBase> beans) {
		// TODO Auto-generated method stub
		super.getMeasuresCallback(retCode, errorMsg, beans);
		hideWaittingDialog();
		switch (retCode) {
		case 0:
			final List<JsonBase> fbeans = beans;
			if (getActivity() != null && !getActivity().isFinishing()) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (isLoadMore) {
							for (JsonBase bean : fbeans) {
								mInfos.add(bean);
							}
							adapter.notifyDataSetChanged();
							isLoadMore = false; // 置位
						} else {
							mInfos = fbeans;
							adapter = new ReportModuleListViewAdapter(
									(FourModuleManangerActivity) getActivity(),
									mInfos);
							mListView.setAdapter(adapter);
						}

					}
				});
			}
			break;
		default:
			showAlert(errorMsg);
			isLoadMore = false; // 置位
			break;
		}
		if (getActivity() != null && !getActivity().isFinishing()) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					onLoad();
				}
			});
		}

	}

	private List<JsonBase> getDataFromCath() {
		String roleId = AndroidConfiguration.getInstance(getActivity())
				.getRoleId();
		String table = DataCatchInterface.getTableName(
				BusinessConst.ALL_MESURE, BusinessConst.AF_ANLASY_SUCCESS);
		return DataCatchInterface.getInstance(this.getActivity()).getItems(
				roleId, table, 0, 10);
	}

	private void refreshFromServer() {
		showWaittingDialog("正在获取数据...");
		String roleId = AndroidConfiguration.getInstance(getActivity())
				.getRoleId();
		InterfaceService.getInstance(this.getActivity()).getMeasures(roleId, 0,
				10, BusinessConst.AF_ANLASY_SUCCESS, BusinessConst.ALL_MESURE,
				this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mMananger = AndroidActivityMananger.getInstatnce();
		view = inflater.inflate(R.layout.report_module, container, false);
		MyViewListener listener = new MyViewListener();
		view.findViewById(R.id.layout_xindian).setOnClickListener(listener);
		view.findViewById(R.id.layout_xueya).setOnClickListener(listener);
		view.findViewById(R.id.layout_xuetang).setOnClickListener(listener);
		view.findViewById(R.id.layout_maibo).setOnClickListener(listener);
		mListView = (ReportModuleListView) view
				.findViewById(R.id.report_module_listview);

		// adapter = new ReportModuleListViewAdapter(
		// (FourModuleManangerActivity) getActivity(), names, contents);
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);

		mListView.setOnItemClickListener(this);
		mListView.setOnTouchListener(listViewOnTouchListener);
		mListView.setPullLoadEnable(true);

		/********************* title bar 的动画 start ***********************/
		showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		showAction.setDuration(300);
		hideAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
		hideAction.setDuration(300);
		reportModuleTopBar = (LinearLayout) view
				.findViewById(R.id.report_module_top_bar);
		/********************* title bar 的动画 end ***********************/

		mInfos = getDataFromCath();
		if (mInfos != null && mInfos.size() > 0) {
			// 存在缓存数据
			adapter = new ReportModuleListViewAdapter(
					(FourModuleManangerActivity) getActivity(), mInfos);
			mListView.setAdapter(adapter);
		} else {
			refreshFromServer();
		}

		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	OnTouchListener listViewOnTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				lastY = (int) event.getY();
				return false;
			}
			case MotionEvent.ACTION_MOVE: {
				curY = (int) event.getY();
				delatY = curY - lastY;
				return false;
			}
			case MotionEvent.ACTION_UP: {

				if (delatY < -30) {// 手势从下往上滑
					if (isTitleBarShown) {
						reportModuleTopBar.startAnimation(hideAction);
						reportModuleTopBar.setVisibility(View.GONE);
//						mListView.setPadding(0, 0, 0, 0);

						isTitleBarShown = false;
					}
				} else if (delatY > 30) {// 手势从上往下滑

					if (!isTitleBarShown) {
						reportModuleTopBar.startAnimation(showAction);
						reportModuleTopBar.setVisibility(View.VISIBLE);
//						mListView.setPadding(0, 0, 0, 0);

						isTitleBarShown = true;
					}
				}
				curY = 0;
				lastY = 0;
				return false;
			}
			}
			return false;

		}
	};

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	// 处理下拉刷新数据
	@Override
	public void onRefresh() {
		String roleId = AndroidConfiguration.getInstance(getActivity())
				.getRoleId();
		InterfaceService.getInstance(this.getActivity()).getMeasures(roleId, 0,
				10, BusinessConst.AF_ANLASY_SUCCESS, BusinessConst.ALL_MESURE,
				this);
	}

	// 处理下拉加载数据
	@Override
	public void onLoadMore() {
		isLoadMore = true;
		int pos = (mInfos == null || mInfos.size() == 0) ? 0 : mInfos.size();
		String roleId = AndroidConfiguration.getInstance(getActivity())
				.getRoleId();
		InterfaceService.getInstance(this.getActivity()).getMeasures(roleId,
				pos, 10, BusinessConst.AF_ANLASY_SUCCESS,
				BusinessConst.ALL_MESURE, this);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.layout_xindian:
				mMananger.switchActivityNoClose("FourModuleManangerActivity",
						ReportOfXinDianActivity.class);
				getActivity().overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;

			case R.id.layout_xueya:
				mMananger.switchActivityNoClose("FourModuleManangerActivity",
						ReportOfXueYaActivity.class);
				getActivity().overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;
			case R.id.layout_xuetang:
				mMananger.switchActivityNoClose("FourModuleManangerActivity",
						ReportOfXueTangActivity.class);
				getActivity().overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;
			case R.id.layout_maibo:
				mMananger.switchActivityNoClose("FourModuleManangerActivity",
						ReportOfMaiboActivity.class);
				getActivity().overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;

			default:

			}

		}
	}

	/**********************************************************************
	 * 方法描述：点击ListView某一条时，监听之
	 * 
	 * @param arg0
	 *            代表当前的 ListView
	 * @param arg1
	 *            代表点击的item
	 * @param arg2
	 *            当前点击的item在适配中的位置,第一条为 0
	 * @param arg3
	 *            The row id of the item that was clicked.
	 **********************************************************************/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (arg2 <= mInfos.size()) {
			String roleId = AndroidConfiguration.getInstance(getActivity())
					.getRoleId();
			JsonBase node = mInfos.get(arg2 - 1);
			if (node instanceof EcgInfoBean) {
				EcgInfoBean ecgInfo = (EcgInfoBean) node;
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), ReportsDetailActivity.class);
				ArrayList<String> ids = new ArrayList<String>();
				ArrayList<String> types = new ArrayList<String>();
				ids.add(ecgInfo.mId);
				types.add(BusinessConst.ECG_MESURE);
				intent.putStringArrayListExtra("bean_id", ids);
				intent.putStringArrayListExtra("bean_type", types);
				intent.putExtra("role_id", roleId);
				intent.putExtra("anlasy_type", BusinessConst.AF_ANLASY_SUCCESS);
				startActivity(intent);
			}
			if (node instanceof PpgInfoBean) {
				PpgInfoBean ppgInfo = (PpgInfoBean) node;

				Intent intent = new Intent(getActivity()
						.getApplicationContext(), ReportsDetailActivity.class);
				ArrayList<String> ids = new ArrayList<String>();
				ArrayList<String> types = new ArrayList<String>();
				ids.add(ppgInfo.mId);
				types.add(BusinessConst.PPG_MESURE);
				intent.putStringArrayListExtra("bean_id", ids);
				intent.putStringArrayListExtra("bean_type", types);
				intent.putExtra("role_id", roleId);
				intent.putExtra("anlasy_type", BusinessConst.AF_ANLASY_SUCCESS);
				startActivity(intent);
			}
			if (node instanceof BsInfoBean) {
				BsInfoBean bsInfo = (BsInfoBean) node;
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), ReportsDetailActivity.class);
				ArrayList<String> ids = new ArrayList<String>();
				ArrayList<String> types = new ArrayList<String>();
				ids.add(bsInfo.mId);
				types.add(BusinessConst.BS_MESURE);
				intent.putStringArrayListExtra("bean_id", ids);
				intent.putStringArrayListExtra("bean_type", types);
				intent.putExtra("role_id", roleId);
				intent.putExtra("anlasy_type", BusinessConst.AF_ANLASY_SUCCESS);
				getActivity().startActivity(intent);
			}
			if (node instanceof BpInfoBean) {
				BpInfoBean bpInfo = (BpInfoBean) node;
				// 跳进报告详情界面
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), ReportsDetailActivity.class);
				ArrayList<String> ids = new ArrayList<String>();
				ArrayList<String> types = new ArrayList<String>();
				ids.add(bpInfo.mId);
				types.add(BusinessConst.BP_MESURE);
				intent.putStringArrayListExtra("bean_id", ids);
				intent.putStringArrayListExtra("bean_type", types);
				intent.putExtra("role_id", roleId);
				intent.putExtra("anlasy_type", BusinessConst.AF_ANLASY_SUCCESS);
				startActivity(intent);
			}
		}
	}
}
