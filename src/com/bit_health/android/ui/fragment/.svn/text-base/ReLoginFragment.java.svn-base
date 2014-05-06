package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;


/**********************************************************************
 * 类名：ReLoginFragment
 * 
 * @author 梁才学
 *	主要功能：菜单中的 "重新登录"选项
 *	创建日期：2013.12.10
 **********************************************************************/
public class ReLoginFragment extends BaseFragment {
  
	private View view;
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
    	view = inflater.inflate(R.layout.re_login_fragment, container, false);
    	backIcon = (ImageView) view.findViewById(R.id.re_login_back_icon);
    	backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((FourModuleManangerActivity)getActivity()).getSlidingMenu().toggle();
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
}
