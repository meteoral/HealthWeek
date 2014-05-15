package com.mobilehealth.starting;

import com.mobilehealth.core.FragmentChildPage;
import com.mobilehealth.core.ParentFragmentActivity;
import com.mobilehealth.customizedview.ImageViewMovable;
import com.siat.healthweek.R;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class FragmentGenerateQRCode extends FragmentChildPage implements ImageViewMovable.MovingListener{
	
	private FrameLayout flInputBlockBox;
	private ImageViewMovable ivCharacter1;
	private ImageViewMovable ivCharacter2;
	private ImageViewMovable ivCharacter3;
	
	private int characterAdded=0;
	private int originalLeft, originalTop;
	
	public FragmentGenerateQRCode() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.page_generate_qrcode;
	}

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		flInputBlockBox=(FrameLayout)layout.findViewById(R.id.flInputBlockBox);
		ivCharacter1=(ImageViewMovable)layout.findViewById(R.id.ivCharacter1);
		ivCharacter2=(ImageViewMovable)layout.findViewById(R.id.ivCharacter2);
		ivCharacter3=(ImageViewMovable)layout.findViewById(R.id.ivCharacter3);
		
		ivCharacter1.setMovingListener(this);
		ivCharacter2.setMovingListener(this);
		ivCharacter3.setMovingListener(this);
		
		characterAdded=0;
	}

	@Override
	public void onMovingFinished(final View v) {
		// TODO Auto-generated method stub
		if(v.getLeft()>flInputBlockBox.getLeft()&&
				v.getRight()<flInputBlockBox.getRight()&&
				v.getTop()>flInputBlockBox.getTop()&&
				v.getBottom()<flInputBlockBox.getBottom())
		{
			Animation anim=AnimationUtils.loadAnimation(this.getActivity(), android.R.anim.fade_out);
			anim.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					v.setVisibility(View.INVISIBLE);
					
					characterAdded++;
					
					if(characterAdded==1)
					{
						flInputBlockBox.setBackgroundResource(R.drawable.generate_qrcode_block_bg2);
					}else if(characterAdded==2)
					{
						flInputBlockBox.setBackgroundResource(R.drawable.generate_qrcode_block_bg3);
					}
					
					v.layout(originalLeft, originalTop, originalLeft+v.getWidth(), originalTop+v.getHeight());
					
					if(characterAdded>=3)
					{
						((ParentFragmentActivity)getActivity()).changeToPage(FragmentGenerationSucceed.class);
					}
				}
			});
			v.startAnimation(anim);
		}else
		{
			v.layout(originalLeft, originalTop, originalLeft+v.getWidth(), originalTop+v.getHeight());
		}
	}

	@Override
	public void onMovingStarted(View v) {
		// TODO Auto-generated method stub
		originalLeft=v.getLeft();
		originalTop=v.getTop();
		v.bringToFront();
		
		((ImageViewMovable)v).setLegalMovingRect(ivCharacter2.getLeft(), ivCharacter1.getTop(), ivCharacter3.getRight(), ivCharacter3.getBottom());
	}
}
