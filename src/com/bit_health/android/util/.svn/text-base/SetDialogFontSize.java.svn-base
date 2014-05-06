package com.bit_health.android.util;

import android.app.Dialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**********************************************************************
 * 类名：SetDialogFontSize
 * 
 * 主要功能：设置对话框的字体大小，包括title和button
 * 
 * @author 梁才学 创建日期：2014.3.29
 **********************************************************************/
public class SetDialogFontSize {

	public static void setDialogFontSize(Dialog dialog, float size) {
		Window window = dialog.getWindow();
		View view = window.getDecorView();
		setViewFontSize(view, size);
	}

	private static void setViewFontSize(View view, float size) {
		if (view instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) view;
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				setViewFontSize(parent.getChildAt(i), size);
			}
		} else if (view instanceof TextView) {
			TextView textview = (TextView) view;
			textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		}
	}
}
