package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 内容界面 下面是Fragment的生命周期方法的执行顺序
 */
public class ContentFragment extends Fragment {
	String text = null;

	public ContentFragment() {
	}

	public ContentFragment(String text) {
		Log.e("Krislq", text);
		this.text = text;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Log.e("Krislq", "onCreate:" + text);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("Krislq", "onCreateView:" + text);
		// inflater the layout
		View view = inflater.inflate(R.layout.fragment_text, null);
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		((BaseActivity)getActivity()).setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.e("Krislq", "onActivityCreated:" + text);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.e("Krislq", "onStart:" + text);
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.e("Krislq", "onResume:" + text);
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.e("Krislq", "onPause:" + text);
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.e("Krislq", "onStop:" + text);
		super.onStop();
	}

	@Override
	public void onDestroy() {
		Log.e("Krislq", "onDestroy:" + text);
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.e("Krislq", "onDetach:" + text);
		super.onDetach();
	}
}
