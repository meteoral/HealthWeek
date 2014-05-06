package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.util.BgSettingsImage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ViewSwitcher.ViewFactory;

/**********************************************************************
 * 类名：BgSettingsFragment
 * 
 * 主要功能：背景设置
 * 
 * @author 梁才学 创建日期：2014.2.17
 **********************************************************************/
public class BgSettingsFragment extends BaseFragment implements
		OnItemSelectedListener, ViewFactory {

	public static boolean isSettingsBg = false;
	private int choiceBgIndex = 0;

	private View view;
	private ImageView backIcon;
	private ImageSwitcher mImageSwitcher;
	private Gallery gallery;
	private View setBgBtn;
	private BgSettingsImage mBgSettingsImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mBgSettingsImage = new BgSettingsImage();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.bg_set_fragment, container, false);
		backIcon = (ImageView) view.findViewById(R.id.bg_settings_back_image);
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				exitPage();
			}
		});

		mImageSwitcher = (ImageSwitcher) view.findViewById(R.id.switcher);
		mImageSwitcher.setFactory(this);
		mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
				getActivity(), android.R.anim.fade_in));
		mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
				getActivity(), android.R.anim.fade_out));
		gallery = (Gallery) view.findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(getActivity()));
		gallery.setOnItemSelectedListener(this);
		setBgBtn = view.findViewById(R.id.set_bg_button);
		setBgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				BgSettingsFragment.isSettingsBg = true;
				
				SharedPreferences sp = getActivity().getSharedPreferences(
						getActivity().getPackageName(), Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = sp.edit();
				edit.putInt("choiceBgIndex", choiceBgIndex);
				edit.commit();		
				
				exitPage();
			}
		});

		SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	private void exitPage() {
		getActivity().finish();
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_right_out);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(getActivity());
		imageView.setBackgroundResource(R.drawable.white);// 刚刚初始化时，给ImageSwitcher一个背景
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);// 全屏显示
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		return imageView;
	}

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return mBgSettingsImage.getArraysLength();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(mBgSettingsImage.getBgImage(position));
			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);// 全屏显示
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));

			return imageView;
		}

		private Context mContext;

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		mImageSwitcher.setImageResource(mBgSettingsImage.getBgImage(position));
		choiceBgIndex = position;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}