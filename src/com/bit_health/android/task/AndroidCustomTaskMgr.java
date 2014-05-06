package com.bit_health.android.task;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;

public class AndroidCustomTaskMgr {
	private static AndroidCustomTaskMgr instance;
	private Context mContext;
	private HashMap<String, Runnable> mTasks = new HashMap<String, Runnable>();
	private ExecutorService mExecutor;

	private AndroidCustomTaskMgr(Context context) {
		this.mContext = context.getApplicationContext();
		mExecutor = Executors.newFixedThreadPool(3);
	}

	public static AndroidCustomTaskMgr getInstance(Context context) {
		if (instance == null) {
			instance = new AndroidCustomTaskMgr(context);
		}
		return instance;
	}

	public void addSheduleTask(Runnable runnable, String taskName) {
		if (mTasks.get(taskName) == null) {
			mTasks.put(taskName, runnable);
		}
		excuteTask(taskName);
	}

	public void removeSheduleTask(String taskName) {
		mTasks.remove(taskName);
		if (mTasks.size() == 0) {
			mExecutor.shutdown();// 退出线程池   
			mExecutor = null;
		}
	}

	public void excuteTask(String taskName) {
		if (mExecutor == null) {
			mExecutor = Executors.newFixedThreadPool(3);// 设置线程池中最多3个线程在跑
		}
		Runnable runnable = mTasks.get(taskName);
		if (runnable != null) {
			mExecutor.submit(runnable);
		}
	}
}
