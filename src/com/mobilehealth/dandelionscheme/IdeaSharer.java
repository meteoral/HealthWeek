package com.mobilehealth.dandelionscheme;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.mobilehealth.core.DandelionSchemeFrame;
import com.siat.healthweek.R;

public class IdeaSharer extends DandelionSchemeFrame{
	
	@Override
	protected void populateContent() {
		// TODO Auto-generated method stub
		super.populateContent();
		
		View content=View.inflate(this, R.layout.idea_sharer, null);
		rlContent.addView(content,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
}
