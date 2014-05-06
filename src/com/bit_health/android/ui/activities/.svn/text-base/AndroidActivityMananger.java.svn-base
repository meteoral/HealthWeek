package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import com.siat.healthweek.R;
import com.bit_health.android.constants.DeviceConst;
import com.bit_health.android.util.SetDialogFontSize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**********************************************************************
 * 类名：AndroidActivityMananger
 * 
 * @author 梁才学 主要功能：所有 Activity 的统一管理类 创建日期：2013.12.5
 **********************************************************************/
public class AndroidActivityMananger {

	private static AndroidActivityMananger mMananger;
	private HashMap<String, Activity> acitivites = new HashMap<String, Activity>();

	// 获取单例，所以声明为 private
	private AndroidActivityMananger() {

	}

	/****************************************************************
	 * 方法描述：获取单例
	 * 
	 * @return AndroidActivityMananger的对象
	 ****************************************************************/
	public static AndroidActivityMananger getInstatnce() {
		if (mMananger == null) {
			mMananger = new AndroidActivityMananger();
		}
		return mMananger;
	}

	/****************************************************************
	 * 方法描述：通过key--value形式将目标 Activity存放到 HashMap
	 * 
	 * @param key
	 * @param value_activity
	 * @return
	 ****************************************************************/
	public void putActivity(String key, Activity value_activity) {
		acitivites.put(key, value_activity);
	}

	/****************************************************************
	 * 方法描述：通过key删除对应的Activity
	 * 
	 * @param key
	 * @return
	 ****************************************************************/
	public void removeActivity(String key) {
		acitivites.remove(key);
	}

	/****************************************************************
	 * 方法描述：通过key获取对应的Activity
	 * 
	 * @param key
	 * @return 返回与key对应的Activity
	 ****************************************************************/
	public Activity getActivity(String key) {
		return acitivites.get(key);
	}

	/****************************************************************
	 * 方法描述：Activity之间的跳转，跳转后退出之前的 Activity
	 * 
	 * @param finishClass
	 *            当前Activity
	 * @param newClass
	 *            目标Activity
	 * @return
	 ****************************************************************/
	public void switchActivity(String finishClass, Class<?> newClass) {
		Activity preActivity = getActivity(finishClass);
		if (preActivity != null) {
			preActivity.startActivity(new Intent(preActivity
					.getApplicationContext(), newClass));
			preActivity.finish();
		}
	}

	/****************************************************************
	 * 方法描述：Activity之间的跳转，跳转后退出之前的 Activity
	 * 
	 * @param finishClass
	 *            当前Activity
	 * @param newClass
	 *            目标Activity
	 * @return
	 ****************************************************************/
	public void switchActivity(String finishClass, Class<?> newClass,
			Hashtable<String, String> pairs) {
		Activity preActivity = getActivity(finishClass);
		if (preActivity != null) {
			Intent intent = new Intent(preActivity.getApplicationContext(),
					newClass);
			Enumeration<String> keys = pairs.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String vaule = pairs.get(key);
				intent.putExtra(key, vaule);
			}
			preActivity.startActivity(intent);
			preActivity.finish();
		}
	}

	/****************************************************************
	 * 方法描述：Activity之间的跳转，跳转后不退出之前的 Activity
	 * 
	 * @param finishClass
	 *            当前Activity
	 * @param newClass
	 *            目标Activity
	 * @return
	 ****************************************************************/
	public void switchActivityNoClose(String finishClass, Class<?> newClass) {
		Activity preActivity = getActivity(finishClass);
		if (preActivity != null) {
			preActivity.startActivity(new Intent(preActivity
					.getApplicationContext(), newClass));
		}
	}

	/****************************************************************
	 * 方法描述：Activity之间的跳转，跳转后不退出之前的 Activity
	 * 
	 * @param finishClass
	 *            当前Activity
	 * @param newClass
	 *            目标Activity
	 * @param pairs
	 *            入参
	 * @return
	 ****************************************************************/
	public void switchActivityNoClose(String finishClass, Class<?> newClass,
			Hashtable<String, String> pairs) {
		Activity preActivity = getActivity(finishClass);
		if (preActivity != null) {
			Intent intent = new Intent(preActivity.getApplicationContext(),
					newClass);
			Enumeration<String> keys = pairs.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String vaule = pairs.get(key);
				intent.putExtra(key, vaule);
			}
			preActivity.startActivity(intent);
		}
	}

	public void showSelectDeviceTestDialog(final Activity parent) {
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				LayoutInflater inflagter = LayoutInflater.from(parent);
				LinearLayout item = (LinearLayout) inflagter.inflate(
						R.layout.select_device_list, null);
				ImageView imageView = (ImageView) item
						.findViewById(R.id.device_select_imageviewid);
				TextView textView = (TextView) item
						.findViewById(R.id.device_select_textviewid);
				if (arg0 == 0) {
					imageView
							.setBackgroundResource(R.drawable.device_slect_3n1);
					textView.setText(R.string.device_select_3n1_string);
				}
				if (arg0 == 1) {
					imageView
							.setBackgroundResource(R.drawable.device_slect_miniholter);
					textView.setText(R.string.device_select_miniholter_string);
				}
				return item;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 2;
			}
		};
		ListView selectView = new ListView(parent);
		selectView.setAdapter(adapter);
		final AlertDialog alertDialog = new AlertDialog.Builder(parent)
				.setTitle(parent.getText(R.string.choice_device))
				.setView(selectView).create();

		alertDialog.setCanceledOnTouchOutside(false);
		selectView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				ArrayList<String> devicesNames = new ArrayList<String>();
				if (arg2 == 0) {
					devicesNames.add(DeviceConst.BT_3IN1E_NAME);
				} else if (arg2 == 1) {
					devicesNames.add(DeviceConst.BT_MINIHOLTER_NAME);
					devicesNames.add(DeviceConst.BT_MINIHOLTER_NAME_NEW);
				}
				Intent intent = new Intent(parent.getApplicationContext(),
						TestXinDianActivity.class);
				intent.putStringArrayListExtra(
						TestXinDianActivity.FLAG_INTENT_DEVCE_NAME,
						devicesNames);
				parent.startActivity(intent);
				alertDialog.dismiss();
			}
		});
		alertDialog.show();
		SetDialogFontSize.setDialogFontSize(alertDialog, parent.getResources().getDimension(R.dimen.text_size_30px));
	}
}
