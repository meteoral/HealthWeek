package com.mobilehealth.dandelionscheme;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.mobilehealth.core.ParentFragmentActivity;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

public class DandelionScheme extends ParentFragmentActivity{
	
	private ImageView ivBack;
	
	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
		this.layoutId=R.layout.main_frame_for_dandelion_scheme;
	}
	
	@Override
	protected void setContainer() {
		// TODO Auto-generated method stub
		this.containerId=R.id.rlContent;
	}
	
	@Override
	protected void setBackActivity() {
		// TODO Auto-generated method stub
		this.backActivity=MainActivity.class;
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		ivBack = (ImageView) findViewById(R.id.ivBack);

		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				disposeBtnBack();
			}
		});
		
		this.childFragmentArray=new String[]{
				FragmentDandelionSchemeMainPage.class.getName(),
				FragmentBodyCommu.class.getName(),
				FragmentIdeaSharer.class.getName()};
	}
}
