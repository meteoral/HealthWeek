package com.mobilehealth.core;

import java.util.ArrayList;

import com.mobilehealth.medicalkit.FragmentCloudData;
import com.mobilehealth.medicalkit.FragmentHealthKnowledge;
import com.mobilehealth.medicalkit.FragmentTimeSpaceConnecting;
import com.siat.healthweek.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFrameNew extends FragmentActivity{

	private ViewPager vpContent;
	private ViewPagerAdapter vpAdapter;

	protected ImageView ivCloudData;
	protected ImageView ivHealthKnowledge;
	protected ImageView ivTimeSpaceConnecting;

	protected ImageView ivCurSubjectIcon;
	protected TextView tvCenterCaption;
	protected TextView tvRightCaption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_frame_new);

		init();
	}

	private void init() {
		vpContent = (ViewPager) findViewById(R.id.vpContent);

		ivCloudData = (ImageView) findViewById(R.id.ivCloudData);
		ivHealthKnowledge = (ImageView) findViewById(R.id.ivHealthKnowledge);
		ivTimeSpaceConnecting = (ImageView) findViewById(R.id.ivTimeSpaceConnecting);
		tvCenterCaption = (TextView) findViewById(R.id.tvCenterCaption);
		tvRightCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);
        
		vpAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
		vpAdapter.addFragment(FragmentCloudData.class, null);
		vpAdapter.addFragment(FragmentHealthKnowledge.class, null);
		vpAdapter.addFragment(FragmentTimeSpaceConnecting.class, null);

		vpContent.setAdapter(vpAdapter);

		ivCloudData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(vpContent.getCurrentItem()!=0)
				{
					vpContent.setCurrentItem(0);
				}
			}
		});
		ivHealthKnowledge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(vpContent.getCurrentItem()!=1)
				{
					vpContent.setCurrentItem(1);
				}
			}
		});
		ivTimeSpaceConnecting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(vpContent.getCurrentItem()!=2)
				{
					vpContent.setCurrentItem(2);
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			Fragment curFragment=vpAdapter.getItem(vpContent.getCurrentItem());
			if (curFragment != null && curFragment.getChildFragmentManager().getBackStackEntryCount() > 0){
				curFragment.getChildFragmentManager().popBackStack();
				return true;
		    }
		}
		return super.onKeyDown(keyCode, event);
	}
	

	private static class ViewPagerAdapter extends FragmentPagerAdapter {
		private Context context;
		private ArrayList<WrappedFragment> fragList = new ArrayList<WrappedFragment>();

		public ViewPagerAdapter(Context context, FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			WrappedFragment wrappedFrag = fragList.get(position);
			if (wrappedFrag.frag == null) {
				wrappedFrag.frag = Fragment.instantiate(context,
						wrappedFrag.clazz.getName(), wrappedFrag.bundle);
			}
			return wrappedFrag.frag;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragList.size();
		}

		// --------------------------------------------

		public void addFragment(Class<?> clazz, Bundle bundle) {
			fragList.add(new WrappedFragment(clazz, bundle));
		}

		private static final class WrappedFragment {
			private final Class<?> clazz;
			private final Bundle bundle;
			private Fragment frag;

			WrappedFragment(Class<?> clazz, Bundle bundle) {
				this.clazz = clazz;
				this.bundle = bundle;
			}
		}

	}

}
