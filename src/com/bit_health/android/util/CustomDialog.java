package com.bit_health.android.util;

import com.siat.healthweek.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**********************************************************************
 * 类名：CustomDialog
 * 
 * 主要功能：自定义对话框，可以设置对话框显示的位置、大小、背景等属性
 * 
 * @author 梁才学 创建日期：2014.2.24
 **********************************************************************/
public class CustomDialog {
	private Context mContext;
	private Dialog dialog;
	private LayoutInflater inflaterDialog;
	private View dialogVeiw;
	private TextView dialogTitle;
	private TextView dialogContent;	
	
	// 获取单例，所以声明为 private
	public CustomDialog(Context context) {
		mContext = context;
	}

	public void initDialog() {
		inflaterDialog = LayoutInflater.from(mContext);
		dialogVeiw = inflaterDialog.inflate(R.layout.xindian_abnormal_dialog,
				null);
		dialogTitle = (TextView) dialogVeiw.findViewById(R.id.the_term_name);
		dialogContent = (TextView) dialogVeiw.findViewById(R.id.term_content);

		dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// 通过下边的代码去掉对话框的title
		dialog.setContentView(dialogVeiw);
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setAttributes(lp);
		dialogWindow.setBackgroundDrawable(mContext.getResources().getDrawable(
				R.drawable.ic_preference_single_normal));
		dialog.setCanceledOnTouchOutside(true);
	}

	public void showDialog(String title, String content) {
		dialogTitle.setText(title);
		dialogContent.setText(content);
		dialog.show();
	}

	public void closeDialog() {
		if (dialog != null) {
			dialog.cancel();
		}
	}

}