package com.mobilehealth.medicalkit;

import com.mobilehealth.core.MainFrameMessageListener;
import com.siat.healthweek.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FragmentCloudData extends Fragment implements MainFrameMessageListener{
	
	private RelativeLayout pageContainer;
	private ImageView ivPhysicalHealthResult;
	
	private int cur_view_index=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_container, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		init(view);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	
	private void init(View layout)
	{
		pageContainer=(RelativeLayout)layout.findViewById(R.id.rlMain);
		View new_page=null;
		if(cur_view_index==0)
		{
			new_page=View.inflate(this.getActivity(), R.layout.page_cloud_data, null);
		}else if(cur_view_index==1)
		{
			new_page=View.inflate(this.getActivity(), R.layout.physical_health_result, null);
		}
		pageContainer.addView(new_page,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		initEvent(new_page, cur_view_index);
	}
	
	private void initEvent(View new_page, int page_index) {
		if(page_index==0)
		{
			ivPhysicalHealthResult = (ImageView) new_page.findViewById(R.id.ivPhysicalHealthResult);

			ivPhysicalHealthResult.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					changeView(1);
				}
			});
		}
	}
	
	private boolean changeView(int index)
	{
		if(cur_view_index==index|index<0)
		{
			return false;
		}
		
		View old_page=pageContainer.getChildAt(0);
		Animation anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_disappear);
        old_page.startAnimation(anim);
        
        pageContainer.removeViewAt(0);
        
        View new_page=null;
		if(index==1)
		{
			new_page=View.inflate(this.getActivity(), R.layout.physical_health_result, null);
			pageContainer.addView(new_page,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_emerge);
			new_page.startAnimation(anim);
			
		}else if(index==0)
		{
			new_page=View.inflate(this.getActivity(), R.layout.page_cloud_data, null);
			pageContainer.addView(new_page,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			anim=AnimationUtils.loadAnimation(this.getActivity(), R.anim.view_emerge);
            new_page.startAnimation(anim);
		}
		initEvent(new_page,index);
		
		cur_view_index=index;
		return true;
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return changeView(cur_view_index-1);
	}
}
