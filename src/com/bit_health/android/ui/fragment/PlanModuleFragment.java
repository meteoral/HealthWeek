package com.bit_health.android.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.siat.healthweek.R;
import com.bit_health.android.ui.activities.BaseActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.adapter.PlanModuleLV_Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**********************************************************************
 * 类名：PlanModuleFragment
 * 
 * @author 梁才学
 *	主要功能：计划模块
 *	创建日期：2013.12.10
 **********************************************************************/
public class PlanModuleFragment extends BaseFragment {
  
	private View view;
	private ImageView editIcon;
	private ImageView addIcon;
	private ListView planListView;
	private ListView completeListView;
	private List<Map<String,Object>> datasList;
	private PlanModuleLV_Adapter adapter;
	
	private String[] planName = {"血压计划", "体重计划"};
	private String[] planNumber = {"-20mmHg", "-5KG"};
	private String[] totalDay = {"30天", "60天"};
	private String[] remainingDay = {"9天 剩余", "30天 剩余"};
		
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
    }  
      
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        // TODO Auto-generated method stub
    	view = inflater.inflate(R.layout.plan_module, container, false);
    	
    	editIcon = (ImageView) view.findViewById(R.id.plan_module_edit);
    	addIcon = (ImageView) view.findViewById(R.id.plan_module_add);
    	ImageListener listener = new ImageListener();
    	editIcon.setOnClickListener(listener);
    	addIcon.setOnClickListener(listener);
    	
    	planListView = (ListView) view.findViewById(R.id.plan_listview);
    	completeListView = (ListView) view.findViewById(R.id.complete_listview);    	
    	datasList = getListItems();    	
    	adapter = new PlanModuleLV_Adapter((FourModuleManangerActivity)getActivity(), datasList);
    	planListView.setAdapter(adapter);
    	completeListView.setAdapter(adapter);// 注意：已完成的计划数据需要另行获取，这里未处理
    	
    	SharedPreferences preference = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
        return view;   
    }
    
    private List<Map<String,Object>> getListItems(){
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	for(int i = 0; i < planName.length; i++){
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("key_planName", planName[i]);
    		map.put("key_planNumber", planNumber[i]);
    		map.put("key_totalDay", totalDay[i]);
    		map.put("key_remainingDay", remainingDay[i]);
    		list.add(map);
    	}
    	return list;
    }
    
    class ImageListener implements OnClickListener {
		public void onClick(View v) {
			
			switch(v.getId()){
			case R.id.plan_module_edit:
				
				Toast.makeText(getActivity(), "点击进行编辑", Toast.LENGTH_SHORT).show();
				
				break;
			case R.id.plan_module_add:
							
				Toast.makeText(getActivity(), "点击进行添加", Toast.LENGTH_SHORT).show();
				
				break;
			
			default:
			
			}
			
		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
