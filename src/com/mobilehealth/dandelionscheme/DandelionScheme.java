package com.mobilehealth.dandelionscheme;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobilehealth.core.DandelionSchemeFrame;
import com.siat.healthweek.R;

public class DandelionScheme extends DandelionSchemeFrame{
	
	private ImageView ivBodyCommu;
	
	@Override
	protected void populateContent() {
		// TODO Auto-generated method stub
		super.populateContent();
		
		View content=View.inflate(this, R.layout.dandelion_scheme, null);
		rlContent.addView(content,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		init(content);
	}
	
	private void init(View content)
	{
		this.ivBodyCommu=(ImageView)content.findViewById(R.id.ivBodyCommu);
		
		this.ivBodyCommu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(DandelionScheme.this, BodyCommu.class);
				startActivity(i);
			}
		});
	}
}
