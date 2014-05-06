package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

/**********************************************************************
 * 类名：OneKeyStepTwoFragment
 * 
 * 主要功能：一键测试的步骤二
 * 
 * @author 梁才学 创建日期：2014.1.8
 **********************************************************************/
public class OneKeyStepTwoFragment extends BaseFragment {

	private View view;
	private Button nextStepBtn;
	private CheckBox xindian_cb;
	private CheckBox xueya_cb;
	private CheckBox xuetang_cb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.one_key_test_step_two, container,
				false);
		initData();
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		MyViewListener listener = new MyViewListener();
		nextStepBtn = (Button) view.findViewById(R.id.step_two_next_step_btn);
		xindian_cb = (CheckBox) view.findViewById(R.id.step_two_xindian_cb);
		xueya_cb = (CheckBox) view.findViewById(R.id.step_two_xueya_cb);
		xuetang_cb = (CheckBox) view.findViewById(R.id.step_two_xuetang_cb);

		nextStepBtn.setOnClickListener(listener);
		xindian_cb.setOnClickListener(listener);
		xueya_cb.setOnClickListener(listener);
		xuetang_cb.setOnClickListener(listener);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private void adjustNextBtn() {
		if (xindian_cb.isChecked() || xueya_cb.isChecked()
				|| xuetang_cb.isChecked()) {
			nextStepBtn.setEnabled(true);
			nextStepBtn.setAlpha(1.0f);
		} else {
			nextStepBtn.setEnabled(false);
			nextStepBtn.setAlpha(0.3f);
		}
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：一键测试中的步骤二的控件监听
	 * 
	 * @author 梁才学 创建日期：2014.1.8
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.step_two_next_step_btn:

//				OneKeyTestActivity mainActivity = (OneKeyTestActivity)getActivity();
//				mainActivity.mHashTestItems.clear();
//				List<String> checks = new ArrayList<String>();
//				if(xindian_cb.isChecked()){
//					checks.add(BusinessConst.ECG_MESURE);
//					mainActivity.mHashTestItems.put(BusinessConst.ECG_MESURE, "");
//				}
//				if(xueya_cb.isChecked()){
//					checks.add(BusinessConst.BP_MESURE);
//					mainActivity.mHashTestItems.put(BusinessConst.BP_MESURE, "");
//				}
//				if(xuetang_cb.isChecked()){
//					checks.add(BusinessConst.BS_MESURE);
//					mainActivity.mHashTestItems.put(BusinessConst.BS_MESURE, "");
//				}
//				FragmentManager fragmentManager = getFragmentManager();
//				ContentFragment contentFragment = (ContentFragment) fragmentManager
//						.findFragmentByTag("ONE_KEY_STEP_THREE");
//				fragmentManager
//						.beginTransaction()
//						.replace(
//								R.id.one_key_test_fragment_container,
//								contentFragment == null ? new OneKeyStepThreeFragment(checks) : contentFragment,
//								"ONE_KEY_STEP_THREE").commit();
//				OneKeyTestActivity.currentFragment = OneKeyTestActivity.STEP_THREE_FRAGMENT;
				break;

			case R.id.step_two_xindian_cb:
				adjustNextBtn();
				break;
			case R.id.step_two_xueya_cb:
				adjustNextBtn();
				break;
			case R.id.step_two_xuetang_cb:
				adjustNextBtn();
				break;
			default:

			}

		}
	}
}
