package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.LoginIdentityActivity;
import com.bit_health.android.ui.activities.OneKeyTestActivity;
import com.bit_health.android.util.HeadImageUtil;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：OneKeyStepOneFragment
 * 
 * 主要功能：一键测试的步骤一
 * 
 * @author 梁才学 创建日期：2014.1.8
 **********************************************************************/
public class OneKeyStepOneFragment extends BaseFragment {

	private View view;
	private Button nextStepBtn;
	private ImageView roleHeadImage;
	private TextView changeRoleText;
	private TextView roleUserNameText;
	private TextView currentRoleNameText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.one_key_test_step_one, container,
				false);
		initView();
		setRoleInfo();
		
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	/****************************************************************
	 * 方法描述：当点击更换角色时，返回本界面后重新设置角色信息
	 * 
	 *****************************************************************/
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setRoleInfo();
	}

	private void initView() {
		// TODO Auto-generated method stub

		roleHeadImage = (ImageView) view
				.findViewById(R.id.one_key_step_one_head_image);
		nextStepBtn = (Button) view.findViewById(R.id.step_one_next_step_btn);
		changeRoleText = (TextView) view
				.findViewById(R.id.step_one_change_role_text);
		roleUserNameText = (TextView) view
				.findViewById(R.id.one_key_role_username_text);
		currentRoleNameText = (TextView) view
				.findViewById(R.id.one_key_current_role_name_text);

		MyViewListener listener = new MyViewListener();
		nextStepBtn.setOnClickListener(listener);
		changeRoleText.setOnClickListener(listener);
	}

	/****************************************************************
	 * 方法描述：获取角色的信息
	 * 
	 *****************************************************************/
	private void setRoleInfo() {
		// TODO Auto-generated method stub
		String roleId = AndroidConfiguration.getInstance(
				getActivity().getApplicationContext()).getRoleId();
		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(getActivity());
		RoleInfoBean mRoleInfoBean = mRoleCatchInfo.getRole(roleId);
		if (mRoleInfoBean != null) {
			roleUserNameText.setText(mRoleInfoBean.mName);
			// roleHeadImage.setBackgroundResource(HeadImageUtil
			// .getLocalHeadimage(mRoleInfoBean.mFullName));
			Bitmap bmp = null;
			if ((bmp = HeadImageUtil.getHeadCatchImage(mRoleInfoBean.mId)) != null) {
				roleHeadImage.setImageBitmap(bmp);
			}else{
				roleHeadImage.setImageResource(HeadImageUtil
						.getLocalHeadimage(mRoleInfoBean.mFullName));
			}
			
			currentRoleNameText.setText(mRoleInfoBean.mFullName);
		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/**********************************************************************
	 * 类名：MyViewListener
	 * 
	 * 主要功能：一键测试中的步骤一的控件监听
	 * 
	 * @author 梁才学 创建日期：2014.1.8
	 **********************************************************************/
	class MyViewListener implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.step_one_next_step_btn:

				FragmentManager fragmentManager = getFragmentManager();
				ContentFragment contentFragment = (ContentFragment) fragmentManager
						.findFragmentByTag("ONE_KEY_STEP_TWO");
				fragmentManager
						.beginTransaction()
						.replace(
								R.id.one_key_test_fragment_container,
								contentFragment == null ? new OneKeyStepThreeFragment()
										: contentFragment, "ONE_KEY_STEP_TWO")
						.commit();

				OneKeyTestActivity.currentFragment = OneKeyTestActivity.STEP_THREE_FRAGMENT;

				break;

			case R.id.step_one_change_role_text:

				LoginIdentityActivity.isComeFromOneKeyTestActivity = true;
				AndroidActivityMananger.getInstatnce().switchActivityNoClose(
						"OneKeyTestActivity", LoginIdentityActivity.class);
				getActivity().overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_left_out);
				break;

			default:

			}

		}
	}
}
