package com.mobilehealth.starting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.MainActivity;
import com.siat.healthweek.R;

public class QRCodeOperation extends FragmentActivity implements ChildPageMessageListener{

	private ImageView ivCurSubjectIconOnBottom;
	private ImageView ivCurSubjectIcon;
	private TextView tvCaption;
	private ImageView ivBack;
	
	private String[] childFragmentArray;

	private int curPageIndex = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_frame_for_qrcode_operation);
		
		init();
	}
	
	private void init()
	{
		tvCaption = (TextView) findViewById(R.id.tvRightCaption);
		ivCurSubjectIconOnBottom=(ImageView)findViewById(R.id.ivCurSubjectIconInBottom);
		ivCurSubjectIcon = (ImageView) findViewById(R.id.ivCurSubjectIcon);
		ivBack = (ImageView) findViewById(R.id.ivBack);

		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				disposeBack();
			}
		});
		
		this.childFragmentArray=new String[]{
				FragmentQRCodeOperationMainPage.class.getName(),
				FragmentScanQRCode.class.getName(),
				FragmentGenerateQRCode.class.getName(),
				FragmentGenerationSucceed.class.getName()};
		
		
		Fragment newPage=getSupportFragmentManager().findFragmentByTag(childFragmentArray[curPageIndex]);
		if(newPage==null)
		{
			FragmentTransaction transac=getSupportFragmentManager().beginTransaction();
			
			newPage=Fragment.instantiate(this, childFragmentArray[curPageIndex]);
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
			transac.add(R.id.rlContent, newPage, childFragmentArray[curPageIndex]);
			
			transac.commit();
		}
		
		childPageChanged(-1, curPageIndex);
	}
	
	private boolean disposeBack()
	{
		FragmentManager childFragManager=getSupportFragmentManager();
		if(childFragManager.getBackStackEntryCount()>0)
		{
			childFragManager.popBackStack();
			return true;
		}
		
		Intent intent=new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		this.finish();
		
		return true;
	}
	
	private boolean changeToPageLocal(int toIndex)
	{
		if(toIndex==curPageIndex|toIndex<0)
		{
			return false;
		}

		FragmentTransaction transac=getSupportFragmentManager().beginTransaction();
		
		Fragment newPage=Fragment.instantiate(this, childFragmentArray[toIndex]);
		if(toIndex>curPageIndex)
		{
			transac.setCustomAnimations(0, R.anim.view_disappear, 0, R.anim.view_disappear);
		}else
		{
			transac.setCustomAnimations(0, 0, 0, R.anim.view_disappear);
		}
		transac.replace(R.id.rlContent, newPage, childFragmentArray[toIndex]);
		
		if(toIndex>curPageIndex)
		{
			transac.addToBackStack(null);
		}
		
		curPageIndex=toIndex;
		
		transac.commit();
		
		
		return true;
	}
	
	@Override
	public void changeToPage(int toIndex) {
		// TODO Auto-generated method stub
		changeToPageLocal(toIndex);
		
		childPageChanged(-1, toIndex);
	}

	@Override
	public void childPageChanged(int firstLeveIndex, int secondLevelIndex) {
		// TODO Auto-generated method stub
		
		curPageIndex=secondLevelIndex;
		
		if(secondLevelIndex==0)
		{
			ivCurSubjectIcon.setImageResource(R.drawable.icon_starting);
			ivCurSubjectIconOnBottom.setVisibility(View.INVISIBLE);
			tvCaption.setText("");
		}else if(secondLevelIndex==1)
		{
			ivCurSubjectIcon.setImageResource(R.drawable.scan_qrcode_icon);
			ivCurSubjectIconOnBottom.setVisibility(View.VISIBLE);
			tvCaption.setText(getResources().getString(R.string.scan_qrcode));
		}else if(secondLevelIndex==2)
		{
			ivCurSubjectIcon.setImageResource(R.drawable.generate_qrcode_icon);
			ivCurSubjectIconOnBottom.setVisibility(View.VISIBLE);
			tvCaption.setText(getResources().getString(R.string.generate_qrcode));
		}
	}
	
	@Override
	public int getPageIndex(String className) {
		// TODO Auto-generated method stub
		for(int i=0;i<childFragmentArray.length;i++)
		{
			if(className.equals(childFragmentArray[i]))
			{
				return i;
			}
		}
		return -1;
	}

}
