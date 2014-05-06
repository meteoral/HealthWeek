package com.bit_health.android.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.*;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.adapter.AddModuleLV_Adapter;
import com.slidingmenu.lib.SlidingMenu;

/**********************************************************************
 * 类名：AddModuleFragment
 * 
 * @author 梁才学 主要功能：添加模块的入口 创建日期：2013.12.9
 **********************************************************************/
public class AddModuleFragment extends BaseFragment implements
		OnItemClickListener {

	private View view;
	private ListView listView;
	private AddModuleLV_Adapter listViewAdapter;
	private List<Map<String, Object>> listItems;
	private Integer[] imgeIDs = { R.drawable.head_photo, R.drawable.head_photo,
			R.drawable.head_photo, R.drawable.head_photo,
			R.drawable.head_photo, R.drawable.head_photo };
	private String[] titleNames = { "心电", "血压", "血糖", "体温", "体重" };
	private String[] subTitle = { "进行测量查看数据报告", "进行测量或手动输入数据查看数据报告",
			"手动输入数据查看数据报告", "手动输入体温，查看体温报告", "手动输入数据查看数据报告" };
	private String[] info = { "你很健康", "你很健康", "你很健康", "你很健康", "你很健康" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// 在这个界面中不允许滑动到菜单界面
		((FourModuleManangerActivity) getActivity()).getSlidingMenu()
				.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.add_module, container, false);
		listView = (ListView) view.findViewById(R.id.add_module_listview);
		listItems = getListItems();
		listViewAdapter = new AddModuleLV_Adapter(
				(FourModuleManangerActivity) getActivity(), listItems);
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener(this);
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private List<Map<String, Object>> getListItems() {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < titleNames.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imgeIDs[i]);
			map.put("title", titleNames[i]);
			map.put("info", subTitle[i]);
			map.put("detail", info[i]);
			listItems.add(map);
		}
		return listItems;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Toast.makeText(getActivity(), "点击了第" + arg2 + "项", Toast.LENGTH_SHORT)
				.show();
	}
}
