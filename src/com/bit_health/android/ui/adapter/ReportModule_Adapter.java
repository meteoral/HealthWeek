package com.bit_health.android.ui.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**********************************************************************
 * 类名：ReportModule_Adapter
 * 
 * @author 梁才学
 *	主要功能：报告模块中ViewPager的适配器
 *	创建日期：2013.12.19
 **********************************************************************/
public class ReportModule_Adapter extends PagerAdapter{
	private List<View> mViewPager_views;
	
	public ReportModule_Adapter(List<View> views) {
		this.mViewPager_views = views;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) 	{	
		container.removeView(mViewPager_views.get(position));
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {			
		 container.addView(mViewPager_views.get(position), 0);
		 return mViewPager_views.get(position);
	}

	@Override
	public int getCount() {			
		return  mViewPager_views.size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {			
		return arg0==arg1;
	}
}