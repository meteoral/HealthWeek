package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**********************************************************************
 * 类名：FeedbackFragment
 * 
 * 主要功能：菜单页中的 "意见反馈"选项
 * 
 * @author 梁才学 创建日期：2014.1.6
 **********************************************************************/
public class FeedbackFragment extends BaseFragment {

	private View view;
	private ImageView backIcon;
	private EditText suggestionEdit;// 意见和建议的编辑框
	private EditText contactEdit;// 联系方式编辑框
	private Button commitBtn;// 提交按钮
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.feedback, container, false);
		backIcon = (ImageView) view.findViewById(R.id.feedback_back_icon);
		commitBtn = (Button) view.findViewById(R.id.feedback_commit_btn);
		contactEdit = (EditText) view.findViewById(R.id.feedback_contact_edit);
		suggestionEdit = (EditText) view.findViewById(R.id.feedback_suggestion_edit);
		// 设置suggestionEdit不自动弹出软键盘，需要在Manifest.xml文件中对应的Activity添加这个语句:
		// android:windowSoftInputMode="adjustUnspecified|stateHidden"

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
		commitBtn.setOnClickListener(listener);

		SharedPreferences preference = getActivity().getSharedPreferences(
				getActivity().getPackageName(), Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.feedback_back_icon:
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;
				
			case R.id.feedback_commit_btn:
				
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
}
