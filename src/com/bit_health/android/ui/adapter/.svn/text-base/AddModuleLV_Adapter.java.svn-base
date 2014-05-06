package com.bit_health.android.ui.adapter;

import java.util.List;
import java.util.Map;

import com.siat.healthweek.R;
import com.bit_health.android.util.SetTextSizeClass;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：AddModuleLV_Adapter
 * 
 * @author 梁才学 
 * 主要功能：添加模块中 ListView 的适配器 
 * 创建日期：2013.12.9
 **********************************************************************/
public class AddModuleLV_Adapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> listItems;
	private LayoutInflater listContainer;
	
	public AddModuleLV_Adapter(Context context,
			List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context);
		this.listItems = listItems;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}	

	public View getView(int position, View convertView, ViewGroup parent) {
		final int selectID = position;
		ViewHolder listItemView = null;
		if (convertView == null) {
			listItemView = new ViewHolder();
			convertView = listContainer.inflate(R.layout.add_module_list_item, null);
			// 获得控件对象
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.imageitem);
			listItemView.title = (TextView) convertView
					.findViewById(R.id.textview);
			listItemView.sub_title = (TextView) convertView
					.findViewById(R.id.textview1);
			listItemView.addData = (Button) convertView
					.findViewById(R.id.add_data);
			// 设置空间集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ViewHolder) convertView.getTag();
		}
		// 设置文字图片
		listItemView.image.setBackgroundResource((Integer) listItems.get(
				position).get("image"));
		listItemView.title.setText((String) listItems.get(position)
				.get("title"));
		listItemView.sub_title.setText((String) listItems.get(position).get(
				"info"));
		listItemView.addData.setText("添加");
//		listItemView.addData.setTextSize(SetTextSizeClass.getTextFontSize());
		listItemView.addData.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDetailInfo(selectID);
			}
		});

		return convertView;
	}
	
	private void showDetailInfo(int clickID) {
		new AlertDialog.Builder(context)
				.setTitle("亲:" + listItems.get(clickID).get("title"))
				.setMessage(listItems.get(clickID).get("detail").toString())
				.setPositiveButton("确定", null).show();
	}

	class ViewHolder {
		public ImageView image;
		public TextView title;
		public TextView sub_title;
		public Button addData;
	}
}
