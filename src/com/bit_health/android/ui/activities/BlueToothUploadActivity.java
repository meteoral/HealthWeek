package com.bit_health.android.ui.activities;

import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.database.FileDbBean;
import com.bit_health.android.database.FileDbTable;
import com.bit_health.android.database.RoleDatabase;
import com.bit_health.android.device.usb.UsbUploadService;
import com.bit_health.android.ui.adapter.BluetoothUploadAdapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.TextView;

/**********************************************************************
 * 类名：BlueToothUploadActivity
 * 
 * 主要功能：蓝牙一键上传
 * 
 * @author 梁才学 创建日期：2013.3.11
 **********************************************************************/
public class BlueToothUploadActivity extends BaseActivity implements
		OnItemClickListener {

	private LayoutInflater inflater;
	private View view;
	private ImageView backIcon;
	private TextView noDataText;

	private ListView mListView;
	private BluetoothUploadAdapter adapter;
	private List<FileDbBean> listData;
	private FileDbTable mFileTb;
	private RoleDatabase mDb;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (FileDbTable.ACTION_DATABASE_CHANGED.equals(intent.getAction())) {
				if (mFileTb != null) {
					listData.clear();
					List<FileDbBean> datas = mFileTb.getFailUploadFiles();
					listData.addAll(datas);
					adapter.notifyDataSetChanged();
				}
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.login);

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.bluetooth_upload, null);
		setContentView(view);
		initView();
		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		IntentFilter filter = new IntentFilter();
		filter.addAction(FileDbTable.ACTION_DATABASE_CHANGED);
		this.registerReceiver(mReceiver, filter);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		backIcon = (ImageView) view
				.findViewById(R.id.bluetooth_upload_back_icon);
		noDataText = (TextView) view.findViewById(R.id.no_data_text);

		// 当前角色 id
		String roleid = AndroidConfiguration.getInstance(this).getRoleId();
		// 是否存在还没有传输完成的数据文件
		mDb = new RoleDatabase(this, roleid);
		if (mDb.CreateDb()) {
			mFileTb = mDb.openFileTable(RoleDatabase.FILE_TABLE_NAME);
			if (mFileTb != null) {
				listData = mFileTb.getFailUploadFiles();
			}
		}

		mListView = (ListView) view
				.findViewById(R.id.bluetooth_upload_listview);

		adapter = new BluetoothUploadAdapter(this, listData);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this); // 当点击某一条时，触发这个方法
		isShowTextview();

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
	}

	private void isShowTextview() {
		if (listData.size() != 0) {
			noDataText.setVisibility(View.GONE);
		} else {
			noDataText.setVisibility(View.VISIBLE);
		}
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：注册界面中的控件监听
	 * 
	 * @author 梁才学 创建日期：2013.12.5
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.bluetooth_upload_back_icon:
				BlueToothUploadActivity.this.finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;			

			default:

			}
		}
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
		FileDbBean bean = listData.get(arg2);
		Intent intent = new Intent(BlueToothUploadActivity.this,
				UsbUploadService.class);
		intent.setAction(UsbUploadService.ACTION_START_HISTORY_UPLOAD);
		intent.putExtra(UsbUploadService.INTENT_UPLOAD_DATA_ID, (int)bean.mId);
		startService(intent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mDb != null) {
			mDb.closeDb();
			mDb = null;
		}
		unregisterReceiver(mReceiver);
	}
}
