package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.adapter.TimeRemindAdapter;
import com.bit_health.android.ui.adapter.TimeRemindAdapter.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**********************************************************************
 * 类名：RemindSetFragment
 * 
 * @author 梁才学 
 * 主要功能：菜单中的 "提醒设置"选项 
 * 创建日期：2013.12.10
 **********************************************************************/
public class RemindSetFragment extends BaseFragment implements OnItemClickListener {

	private View view;
	private ImageView backIcon;
	private CheckBox timeRemind_cb;
	private ListView timeListView;
	private TimeRemindAdapter timeAdapter;

	private String[] alarmTime = { "08:00", "12:00" };// 提醒的时间

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.remind_set_fragment, container, false);

		// 测试时间提醒的ListView
		timeListView = (ListView) view.findViewById(R.id.time_remind_listView);
		timeAdapter = new TimeRemindAdapter(getActivity(), alarmTime);
		timeListView.setAdapter(timeAdapter);
		timeListView.setOnItemClickListener(this);
		
		// 开关控件
		MyCheckBoxListener listener = new MyCheckBoxListener();
		timeRemind_cb = (CheckBox) view.findViewById(R.id.time_remind_cb);
		timeRemind_cb.setOnCheckedChangeListener(listener);
		
		// 返回图标
		backIcon = (ImageView) view.findViewById(R.id.remind_set_back_icon);
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
			}
		});
    	
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	/**********************************************************************
	 * 类名：MyCheckBoxListener
	 * 
	 * @author 梁才学 
	 * 主要功能：开关控件的监听
	 * 创建日期：2013.12.10
	 **********************************************************************/
	class MyCheckBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			switch (buttonView.getId()) {
			case R.id.time_remind_cb:

				if (isChecked) {
					timeListView.setVisibility(View.GONE);
				} else {
					timeListView.setVisibility(View.VISIBLE);
				}
				break;			
			}

		}
	}

	/**********************************************************************
	 * 方法描述：点击 ListView的item时，进行监听
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
		Toast.makeText(getActivity(), "选择第" + arg2 + "项", Toast.LENGTH_SHORT).show();
		
		ViewHolder holder = (ViewHolder) arg1.getTag();
		holder.mCheckBox.toggle();// 改变CheckBox的状态
		timeAdapter.getIsSelect().put(arg2, holder.mCheckBox.isChecked());// 将CheckBox的选中状况记录下来
		timeAdapter.notifyDataSetChanged();
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
