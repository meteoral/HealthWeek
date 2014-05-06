package com.bit_health.android.ui.activities;

import com.siat.healthweek.R;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**********************************************************************
 * 类名：GuiderActivity
 * 
 * @author 梁才学 主要功能：显示帮助指引界面 创建日期：2013.12.5
 **********************************************************************/
public class GuiderActivity extends BaseActivity {

	public static final int GUIDER_IMAGE_COUNT = 4;
	private ViewPager mViewPager;
	private ViewPagerAdapter adapter;
	private LayoutInflater mInflater;
	private View view;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mInflater = getLayoutInflater();
		view = mInflater.inflate(R.layout.guider_viewpager, null);
		setContentView(view);

		adapter = new ViewPagerAdapter(this);
		mViewPager = (ViewPager) findViewById(R.id.viewpager_id);
		mViewPager.setAdapter(adapter);

		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	/**********************************************************************
	 * 类名：ViewPagerAdapter
	 * 
	 * @author 梁才学 主要功能：ViewPager所需要的adapter 创建日期：2013.12.5
	 **********************************************************************/
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
			return GUIDER_IMAGE_COUNT;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {

			View userLayout = inflater.inflate(R.layout.guider_page_layout_1,
					view, false);
			guiderIamge = (ImageView) userLayout
					.findViewById(R.id.guider_image);
			Button button = (Button) userLayout.findViewById(R.id.begin_button);
			if (position == 0) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_1);
			} else if (position == 1) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_2);
			} else if (position == 2) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_3);
			} else if (position == 3) {
				guiderIamge.setBackgroundResource(R.drawable.guider_page_4);
				button.setVisibility(View.VISIBLE);
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(GuiderActivity.this,
								LoginActivity.class);
						startActivity(intent);

						finish();
					}
				});
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

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	/**********************************************************************
	 * 方法描述：点击返回键时，弹出对话框提示退出应用
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 **********************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			Builder builder = new AlertDialog.Builder(GuiderActivity.this);

			builder.setTitle("中科汇康");
			builder.setMessage("确定要退出程序吗？");
			builder.setPositiveButton("退出",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			builder.setNegativeButton("取消", null);
			builder.create();
			builder.show();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
