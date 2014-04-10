package com.mobilehealth.dandelionscheme;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.mobilehealth.core.DandelionSchemeFrame;
import com.siat.healthweek.R;

public class BodyCommu extends DandelionSchemeFrame{
	
	@Override
	protected void populateContent() {
		// TODO Auto-generated method stub
		super.populateContent();
		
		View content=View.inflate(this, R.layout.body_commu, null);
		rlContent.addView(content,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		init(content);
	}
	
	private void init(View content)
	{
		this.ivCurSubjectIcon.setImageResource(R.drawable.icon_dandelion_scheme);
		this.tvRightCaption.setText(R.string.creator_platform);
	}
}
