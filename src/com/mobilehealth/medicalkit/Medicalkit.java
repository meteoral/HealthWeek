package com.mobilehealth.medicalkit;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobilehealth.core.MainFrame;
import com.siat.healthweek.R;

public class Medicalkit extends MainFrame {

	private ImageView ivPhysicalHealthResult;
	
	@Override
	protected void popularContent() {
		// TODO Auto-generated method stub
		super.popularContent();

		View content=View.inflate(this, R.layout.medicalkit, null);
		rlContent.addView(content,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		init(content);
	}
	
	private void init(View content)
	{
		ivPhysicalHealthResult=(ImageView)content.findViewById(R.id.ivPhysicalHealthResult);
		
		ivPhysicalHealthResult.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Medicalkit.this, PhysicalHealthResult.class);
				startActivity(i);
			}
		});
	}

}
