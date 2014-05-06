package com.bit_health.android.util;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**********************************************************************
 * 类名：SetTextSizeClass
 * 
 * @author 梁才学 主要功能：设置字体大小的工具类 创建日期：2013.12.17
 **********************************************************************/
public class SetTextSizeClass {

	private Context mContext;

	public SetTextSizeClass(Context context) {
		mContext = context;
	}

	/****************************************************************
	 * 方法描述：通过递归来遍历给定布局的所有 Button 和 TextView，并设置字体大小
	 * 
	 * @param view
	 *            需要处理的布局
	 * @param wantToSetFontSize
	 *            需要改变的字体大小的幅度，比如增大5个像素、减小5个像素
	 * @return
	 ****************************************************************/
	public void setFontSizeOfApp(View view, float wantToSetFontSize) {
		if (view instanceof ListView) {// 注意 ListView 属于 ViewGroup，所以先判断它
			ListView listView = (ListView) view;
//			for (int i = 0; i < listView.getCount(); i++) {
//				if (listView.getAdapter().getView(i, null, null) instanceof ViewGroup) {
//					setFontSizeOfApp(
//							listView.getAdapter().getView(i, null, null),
//							wantToSetFontSize);
//				} else if (((ViewGroup) view).getChildAt(i) instanceof Button) {
//					float currentSize = ((Button) listView.getChildAt(i))
//							.getTextSize();
//					((Button) listView.getChildAt(i)).setTextSize(
//							TypedValue.COMPLEX_UNIT_PX, currentSize
//									+ wantToSetFontSize);
//				}
//				// TextView
//				if (((ViewGroup) view).getChildAt(i) instanceof TextView) {
//					float currentSize = ((TextView) listView.getChildAt(i))
//							.getTextSize();
//					((TextView) listView.getChildAt(i)).setTextSize(
//							TypedValue.COMPLEX_UNIT_PX, currentSize
//									+ wantToSetFontSize);
//				}
//			}	
			for (int i = 0; i < listView.getCount(); i++) {
				if (listView.getAdapter().getView(i, null, null) instanceof ViewGroup) {
					ViewGroup tempViewGroup = (ViewGroup) (listView.getAdapter().getView(i, null, null));
					for (int j = 0; j < tempViewGroup.getChildCount(); j++) {						
						if (tempViewGroup.getChildAt(j) instanceof ViewGroup) {
							setFontSizeOfApp(tempViewGroup.getChildAt(j),
									wantToSetFontSize);
						}			
					}
				} 
			}
		} else if (view instanceof ViewGroup) {
			ViewGroup tempViewGroup = (ViewGroup) view;
			for (int i = 0; i < tempViewGroup.getChildCount(); i++) {
				if (tempViewGroup.getChildAt(i) instanceof ViewGroup) {
					setFontSizeOfApp(tempViewGroup.getChildAt(i),
							wantToSetFontSize);
				} else if (((ViewGroup) view).getChildAt(i) instanceof Button) {					
					float currentSize = ((Button) tempViewGroup.getChildAt(i))
							.getTextSize();
					((Button) tempViewGroup.getChildAt(i)).setTextSize(
							TypedValue.COMPLEX_UNIT_PX, currentSize
									+ wantToSetFontSize);
				}
				if (((ViewGroup) view).getChildAt(i) instanceof TextView) {					
					float currentSize = ((TextView) tempViewGroup.getChildAt(i))
							.getTextSize();
					((TextView) tempViewGroup.getChildAt(i)).setTextSize(
							TypedValue.COMPLEX_UNIT_PX, currentSize
									+ wantToSetFontSize);
				}
			}
		} else if (view instanceof Button) {
			float currentSize = ((Button) view).getTextSize();
			((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize
					+ wantToSetFontSize);
		} else if (view instanceof TextView) {
			float currentSize = ((TextView) view).getTextSize();
			((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX,
					currentSize + wantToSetFontSize);
		}
	}
}
