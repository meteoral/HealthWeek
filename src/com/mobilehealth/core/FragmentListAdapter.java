package com.mobilehealth.core;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentListAdapter extends FragmentPagerAdapter{
	
	private Context context;
	private ArrayList<WrappedFragment> fragList = new ArrayList<WrappedFragment>();

	public FragmentListAdapter(Context context, FragmentManager fm) {
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
	
	private static class WrappedFragment {
		
		private final Class<?> clazz;
		private final Bundle bundle;
		private Fragment frag;

		WrappedFragment(Class<?> clazz, Bundle bundle) {
			this.clazz = clazz;
			this.bundle = bundle;
		}
	}
}
