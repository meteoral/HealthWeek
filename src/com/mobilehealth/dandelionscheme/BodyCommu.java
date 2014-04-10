package com.mobilehealth.dandelionscheme;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.mobilehealth.core.DandelionSchemeFrame;
import com.siat.healthweek.R;

public class BodyCommu extends DandelionSchemeFrame{
	
	private RelativeLayout rlIdeaSharer;
	
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
		rlIdeaSharer=(RelativeLayout)content.findViewById(R.id.rlBodyCommuBlock1);
		rlIdeaSharer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(BodyCommu.this, IdeaSharer.class);
				startActivity(i);
			}
		});
	}
}
