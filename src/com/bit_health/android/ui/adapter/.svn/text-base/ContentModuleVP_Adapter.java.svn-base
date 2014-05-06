package com.bit_health.android.ui.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

//ViewPager的适配器
/**********************************************************************
 * 类名：ContentModuleVP_Adapter
 * 
 * @author 梁才学
 *	主要功能：内容模块中ViewPager的适配器
 *	创建日期：2013.12.9
 **********************************************************************/
public class ContentModuleVP_Adapter extends PagerAdapter{
	private List<View> mViewPager_views;
	
	public ContentModuleVP_Adapter(List<View> views) {
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