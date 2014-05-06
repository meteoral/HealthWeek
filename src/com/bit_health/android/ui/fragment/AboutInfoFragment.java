package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**********************************************************************
 * 类名：AboutInfoFragment
 * 
 * 主要功能：菜单中的 "关于"选项
 * 
 * @author 梁才学 创建日期：2013.12.10
 **********************************************************************/
public class AboutInfoFragment extends BaseFragment {

	public static final int ABOUT_IMAGE_COUNT = 4;

	private View view;
	private ViewPager mViewPager;
	private ViewPagerAdapter adapter;
	private ImageView backIcon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.about_info_fragment, container, false);

		adapter = new ViewPagerAdapter(getActivity());
		mViewPager = (ViewPager) view.findViewById(R.id.about_viewpager);
		mViewPager.setAdapter(adapter);

		backIcon = (ImageView) view.findViewById(R.id.about_info_back_icon);
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
			}
		});
		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	private class ViewPagerAdapter extends PagerAdapter {

		private LayoutInflater inflater;
		private Context mContext;
		private ImageView guiderIamge;

		public ViewPagerAdapter(Context context) {
			inflater = LayoutInflater.from(context);
			mContext = context;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return ABOUT_IMAGE_COUNT;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {

			View userLayout = inflater.inflate(R.layout.guider_page_layout_1,
					view, false);
			guiderIamge = (ImageView) userLayout
					.findViewById(R.id.guider_image);

			if (position == 0) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_1);
			} else if (position == 1) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_2);
			} else if (position == 2) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_3);
			} else if (position == 3) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_4);
			}

			((ViewPager) view).addView(userLayout, 0);
			return userLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
}
