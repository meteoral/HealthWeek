package com.bit_health.android.ui.fragment;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.adapter.MyRoleListAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


/**********************************************************************
 * 类名：MyRoleFragment
 * 
 * @author 梁才学
 *	主要功能：菜单中的 "我的角色"选项
 *	创建日期：2013.12.10
 **********************************************************************/
public class MyRoleFragment extends BaseFragment {
  
	private View view;
	private ImageView backIcon;
	private ListView myRoleListView;
	private String[] roleNameData = { "爸爸", "妈妈", "爷爷", "奶奶", "乖女儿", "龟儿子" };
	private MyRoleListAdapter adapter;
	
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
    }  
      
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        // TODO Auto-generated method stub
    	view = inflater.inflate(R.layout.my_role_fragment, container, false);
    	myRoleListView = (ListView) view.findViewById(R.id.my_role_listview);
		adapter = new MyRoleListAdapter(getActivity(), roleNameData);
		myRoleListView.setAdapter(adapter);
		myRoleListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				Toast.makeText(getActivity(), "选择第" + arg2 + "条", Toast.LENGTH_SHORT).show();
				
			}
		});
    	
    	backIcon = (ImageView) view.findViewById(R.id.my_role_back_icon);
    	backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((FourModuleManangerActivity)getActivity()).getSlidingMenu().toggle();
			}
		});    	
    	// 当点击菜单选项，等页面数据加载完毕之后，再滑动过去，就不出现滑动卡顿了
    	((FourModuleManangerActivity)getActivity()).getSlidingMenu().toggle();
    	
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
