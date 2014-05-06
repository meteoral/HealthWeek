package com.bit_health.android.ui.fragment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.activities.OneKeyTestActivity;
import com.bit_health.android.ui.activities.ReportsDetailActivity;
import com.bit_health.android.ui.activities.TestXueTangActivity;
import com.bit_health.android.ui.activities.TestXueYaActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：OneKeyStepThreeFragment
 * 
 * 主要功能：一键测试的步骤三
 * 
 * @author 梁才学 创建日期：2014.1.8
 **********************************************************************/
public class OneKeyStepThreeFragment extends BaseFragment {

	private View view;
	private View oneKeyXindianTest;
	private View oneKeyXueyaTest;
	private View oneKeyXuetangTest;
	private Button nextStepBtn;
	private ImageView xdCompleteStateImage;
	private ImageView xyCompleteStateImage;
	private ImageView xtCompleteStateImage;
	private TextView xdCompleteStateText;
	private TextView xyCompleteStateText;
	private TextView xtCompleteStateText;
//	private List<String> mChecks;

//	public OneKeyStepThreeFragment(List<String> checks) {
//		this.mChecks = checks;
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.one_key_test_step_three, container,
				false);
		initData();
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	/**********************************************************************
	 * 方法描述：在一键测试中，当相应的测试完成回来时，将相应的图标设为完成状态
	 * 
	 * @param
	 * @return
	 **********************************************************************/
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		OneKeyTestActivity activity = (OneKeyTestActivity) getActivity();
		Enumeration<String> keys = activity.mHashTestItems.keys();

		while (keys.hasMoreElements()) {
			String type = keys.nextElement();
			String id = activity.mHashTestItems.get(type);

			if (!TextUtils.isEmpty(id)) {
				if (BusinessConst.ECG_MESURE.equals(type)) {
					oneKeyXindianTest.setEnabled(false);
					oneKeyXindianTest.setAlpha(0.3f);
					xdCompleteStateImage.setImageDrawable(getActivity()
							.getResources().getDrawable(
									R.drawable.one_key_test_complete));					
					xdCompleteStateText.setText(R.string.already_complete);
					xdCompleteStateText.setTextColor(Color.BLACK);
				}
				if (BusinessConst.BP_MESURE.equals(type)) {
					oneKeyXueyaTest.setEnabled(false);
					oneKeyXueyaTest.setAlpha(0.3f);
					xyCompleteStateImage.setImageDrawable(getActivity()
							.getResources().getDrawable(
									R.drawable.one_key_test_complete));					
					xyCompleteStateText.setText(R.string.already_complete);	
					xyCompleteStateText.setTextColor(Color.BLACK);

				}
				if (BusinessConst.BS_MESURE.equals(type)) {
					oneKeyXuetangTest.setEnabled(false);
					oneKeyXuetangTest.setAlpha(0.3f);
					xtCompleteStateImage.setImageDrawable(getActivity()
							.getResources().getDrawable(
									R.drawable.one_key_test_complete));
					xtCompleteStateText.setText(R.string.already_complete);
					xtCompleteStateText.setTextColor(Color.BLACK);
				}
				nextStepBtn.setEnabled(true);
				nextStepBtn.setAlpha(1.0f);				
			}
		}

	}

	private void initData() {
		// TODO Auto-generated method stub
		nextStepBtn = (Button) view.findViewById(R.id.step_three_next_step_btn);
		oneKeyXindianTest = view.findViewById(R.id.onekey_xindian_test);
		oneKeyXueyaTest = view.findViewById(R.id.onekey_xueya_test);
		oneKeyXuetangTest = view.findViewById(R.id.onekey_xuetang_test);

		xdCompleteStateImage = (ImageView) view
				.findViewById(R.id.xindian_complete_state_image);
		xyCompleteStateImage = (ImageView) view
				.findViewById(R.id.xueya_complete_state_image);
		xtCompleteStateImage = (ImageView) view
				.findViewById(R.id.xuetang_complete_state_image);
		xdCompleteStateText = (TextView) view
				.findViewById(R.id.xindian_complete_state_text);
		xyCompleteStateText = (TextView) view
				.findViewById(R.id.xueya_complete_state_text);
		xtCompleteStateText = (TextView) view
				.findViewById(R.id.xuetang_complete_state_text);

//		if (!mChecks.contains(BusinessConst.ECG_MESURE)) {
//			oneKeyXindianTest.setVisibility(View.GONE);
//		}
//		if (!mChecks.contains(BusinessConst.BP_MESURE)) {
//			oneKeyXueyaTest.setVisibility(View.GONE);
//		}
//		if (!mChecks.contains(BusinessConst.BS_MESURE)) {
//			oneKeyXuetangTest.setVisibility(View.GONE);
//		}

		MyViewListener listener = new MyViewListener();
		nextStepBtn.setOnClickListener(listener);
		oneKeyXindianTest.setOnClickListener(listener);
		oneKeyXueyaTest.setOnClickListener(listener);
		oneKeyXuetangTest.setOnClickListener(listener);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：一键测试中的步骤三的控件监听
	 * 
	 * @author 梁才学 创建日期：2014.1.8
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.step_three_next_step_btn:
				// 跳进报告详情界面
				OneKeyTestActivity activity = (OneKeyTestActivity) getActivity();
				Intent intent = new Intent(activity,
						ReportsDetailActivity.class);
				ArrayList<String> ids = new ArrayList<String>();
				ArrayList<String> types = new ArrayList<String>();

				Enumeration<String> keys = activity.mHashTestItems.keys();

				while (keys.hasMoreElements()) {
					String type = keys.nextElement();
					String id = activity.mHashTestItems.get(type);

					if (!TextUtils.isEmpty(id)) {
						ids.add(id);
						types.add(type);
					}
				}
				intent.putStringArrayListExtra("bean_id", ids);
				intent.putStringArrayListExtra("bean_type", types);
				String roleId = AndroidConfiguration
						.getInstance(getActivity()).getRoleId();
				intent.putExtra("role_id", roleId);
				activity.startActivity(intent);
				activity.finish();
				break;

			case R.id.onekey_xindian_test:
				// 心电测试
				AndroidActivityMananger.getInstatnce()
						.showSelectDeviceTestDialog(getActivity());
				break;

			case R.id.onekey_xueya_test:
				// 血压测试
				AndroidActivityMananger.getInstatnce().switchActivityNoClose(
						"OneKeyTestActivity", TestXueYaActivity.class);
				break;

			case R.id.onekey_xuetang_test:
				// 血糖测试
				AndroidActivityMananger.getInstatnce().switchActivityNoClose(
						"OneKeyTestActivity", TestXueTangActivity.class);
				break;

			default:

			}

		}
	}
}
