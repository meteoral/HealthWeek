package com.mobilehealth.sensibelbed;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.ClsUtils;
import com.siat.healthweek.R;

public class FragmentBreathFreq extends FragmentChildPage {

	private Timer timer = new Timer();
	private static final boolean D = true;
	private static final String TAG = "FragmentBreathFreq";
	private TimerTask task;
	private Handler handler;
	private Handler updateUI;
	private GraphicalView hearts, breath, body;
	private XYSeries heartSeries, breathSeries, bodySeries;
	private XYMultipleSeriesDataset heartDataset, breathDataset, bodyDataset;
	private boolean isConnected = false;
	private TextView bodytext;
	public TextView bodytextShow;
	private String bodyInt;
	private TextView breathtext;
	public TextView breathtextShow;
	private String breathInt;
	private TextView heartstext;
	public TextView heartstextShow;
	private String heartsInt;
	private LinearLayout bodyHorilayout;
	private LinearLayout bodyTextlayout;
	private LinearLayout breathHorilayout;
	private LinearLayout breathTextlayout;
	private LinearLayout heartsHorilayout;
	private LinearLayout heartsTextlayout;
	private LinearLayout linearView;
	private LayoutParams layout;
	private List<Integer> intbody;
	private List<Integer> intbreath;
	private List<Integer> intheart;

	private BluetoothDevice device = null;
	private BluetoothAdapter btAdapt = null;
	private BroadcastReceiver ListenDevices = null;
	private BluetoothAcceptThread bluetoothAcceptThread = null;
	private BluetoothServerSocket mBThServerSocket = null;
	private BluetoothSocket mBThSocket = null;
	private PrintStream mPrintStream = null;

	public FragmentBreathFreq() {
		// TODO Auto-generated constructor stub
		this.layoutId = R.layout.bed_fragment;
	}

	@SuppressLint("HandlerLeak")
	@Override
	protected void init(View view) {
		// TODO Auto-generated method stub
		Context btContext = getActivity().getApplicationContext();

		LayoutParams chartLayoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		chartLayoutParams.weight = 4;
		LayoutParams textLayoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		chartLayoutParams.weight = 1;
		LayoutParams textLayoutParamsOne = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		chartLayoutParams.weight = 1;
		LayoutParams textLayoutParamsTwo = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		chartLayoutParams.weight = 5;
		layout = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.weight = 1;
		linearView = (LinearLayout) view.findViewById(R.id.chart_show);

		bodyHorilayout = new LinearLayout(btContext);
		bodyTextlayout = new LinearLayout(btContext);
		bodyHorilayout.setBackgroundColor(Color.BLACK);
		bodyHorilayout.setOrientation(LinearLayout.HORIZONTAL);
		bodyTextlayout.setOrientation(LinearLayout.VERTICAL);

		heartsHorilayout = new LinearLayout(btContext);
		heartsTextlayout = new LinearLayout(btContext);
		heartsHorilayout.setBackgroundColor(Color.BLACK);
		heartsHorilayout.setOrientation(LinearLayout.HORIZONTAL);
		heartsTextlayout.setOrientation(LinearLayout.VERTICAL);

		breathHorilayout = new LinearLayout(btContext);
		breathTextlayout = new LinearLayout(btContext);
		breathHorilayout.setBackgroundColor(Color.BLACK);
		breathHorilayout.setOrientation(LinearLayout.HORIZONTAL);
		breathTextlayout.setOrientation(LinearLayout.VERTICAL);

		bodytext = new TextView(btContext);
		bodytext.setText("体动");
		bodytextShow = new TextView(btContext);
		bodytextShow.setText("");
		bodytextShow.setTextSize(42);
		bodytextShow.setTextColor(Color.RED);

		breathtext = new TextView(btContext);
		breathtext.setText("呼吸");
		breathtextShow = new TextView(btContext);
		breathtextShow.setText("");
		breathtextShow.setTextSize(42);
		breathtextShow.setTextColor(Color.CYAN);

		heartstext = new TextView(btContext);
		heartstext.setText("心跳脉搏");
		heartstextShow = new TextView(btContext);
		heartstextShow.setText("");
		heartstextShow.setTextSize(42);
		heartstextShow.setTextColor(Color.GREEN);

		showBody(getActivity());
		showHeart(getActivity());
		showBreath(getActivity());

		bodyTextlayout.addView(bodytext, textLayoutParamsOne);
		bodyTextlayout.addView(bodytextShow, textLayoutParamsTwo);
		bodyHorilayout.addView(body, chartLayoutParams);
		bodyHorilayout.addView(bodyTextlayout, textLayoutParams);

		breathTextlayout.addView(breathtext, textLayoutParamsOne);
		breathTextlayout.addView(breathtextShow, textLayoutParamsTwo);
		breathHorilayout.addView(breath, chartLayoutParams);
		breathHorilayout.addView(breathTextlayout, textLayoutParams);

		heartsTextlayout.addView(heartstext, textLayoutParamsOne);
		heartsTextlayout.addView(heartstextShow, textLayoutParamsTwo);
		heartsHorilayout.addView(hearts, chartLayoutParams);
		heartsHorilayout.addView(heartsTextlayout, textLayoutParams);

		btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能
		if (btAdapt.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
			startActivityForResult(discoverableIntent,
					BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
		}
		// 注册Receiver来获取蓝牙设备相关的结果
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		// 注册receiver监听配对
		ListenDevices = new PairingRequest();
		IntentFilter intent2 = new IntentFilter();
		intent2.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
		getActivity().registerReceiver(ListenDevices, intent2);
		bluetoothAcceptThread = new BluetoothAcceptThread(btAdapt, 29);
		bluetoothAcceptThread.start();

		if (!isConnected) {
			linearView.removeAllViews();
			TextView alert = new TextView(btContext);
			alert.setText("等待电脑端数据传输！");
			alert.setTextSize(60);
			alert.setTextColor(Color.RED);
			ImageView alertImg = new ImageView(btContext);
			alertImg.setImageResource(R.drawable.bluetooth_status_right);
			linearView.addView(alertImg);
			linearView.addView(alert);
		} else {
			linearView.removeAllViews();
			linearView.addView(bodyHorilayout, layout);
			linearView.addView(breathHorilayout, layout);
			linearView.addView(heartsHorilayout, layout);
		}
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 刷新图表
				updateBodyChart(body);
				bodytextShow.setText(bodyInt);
				updateHeartChart(hearts);
				heartstextShow.setText(heartsInt);
				updateBreathChart(breath);
				breathtextShow.setText(breathInt);
				super.handleMessage(msg);
			}
		};
		updateUI = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				intbody = new ArrayList<Integer>(100);
				intbreath = new ArrayList<Integer>(100);
				intheart = new ArrayList<Integer>(100);
				linearView.removeAllViews();
				linearView.addView(bodyHorilayout, layout);
				linearView.addView(breathHorilayout, layout);
				linearView.addView(heartsHorilayout, layout);
			}
		};

		task = new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};

	}

	@Override
	public void onDestroy() {
		// 当结束程序时关掉Timer
		timer.cancel();
		cancel();
		super.onDestroy();
	}

	public void cancel() {
		if (mBThServerSocket != null) {
			if (D)
				Log.d(TAG, "Socket Type" + mBThServerSocket + "cancel " + this);
			try {
				mBThServerSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "Socket Type" + mBThServerSocket
						+ "close() of server failed", e);
			}
		}

		if (mBThSocket != null) {
			if (D)
				Log.d(TAG, "Socket Type" + mBThSocket + "cancel " + this);
			try {
				mBThSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "Socket Type" + mBThSocket
						+ "close() of server failed", e);
			}
		}
	}

	/**
	 * 展示人体动态
	 *
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public View showBody(Context context) {
		bodySeries = new XYSeries("体动节律");
		bodyDataset = new XYMultipleSeriesDataset();
		bodyDataset.addSeries(bodySeries);
		int[] colors = new int[] { Color.RED };// 线条的颜色
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };// 坐标点的形状
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		setChartSettings(renderer, "体动节律节律", "时间", "次数", 0, 100, 0, 15,
				Color.WHITE, Color.WHITE);
		((XYSeriesRenderer) renderer.getSeriesRendererAt(0))
				.setFillPoints(true);
		((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(3f);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setShowGrid(true);
		renderer.setZoomEnabled(false);
		renderer.setShowLegend(true);
		renderer.setZoomButtonsVisible(false);
		renderer.setLabelsTextSize(20);
		renderer.setAxisTitleTextSize(20);
		body = ChartFactory.getCubeLineChartView(context, bodyDataset,
				renderer, 0.1f);
		return body;
	}

	/**
	 * 展示心跳表格
	 *
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public View showHeart(Context context) {
		heartSeries = new XYSeries("心率");
		heartDataset = new XYMultipleSeriesDataset();
		heartDataset.addSeries(heartSeries);
		int[] colors = new int[] { Color.GREEN };// 线条的颜色
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };// 坐标点的形状
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		setChartSettings(renderer, "心率图", "时间", "心率", 0, 100, 40, 150,
				Color.WHITE, Color.WHITE);
		((XYSeriesRenderer) renderer.getSeriesRendererAt(0))
				.setFillPoints(true);
		((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(3f);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setShowGrid(true);
		renderer.setZoomEnabled(false);
		renderer.setZoomButtonsVisible(false);
		renderer.setLabelsTextSize(20);
		renderer.setAxisTitleTextSize(20);
		hearts = ChartFactory.getCubeLineChartView(context, heartDataset,
				renderer, 0.1f);

		return hearts;
	}

	/**
	 * 展示呼吸表格
	 *
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public View showBreath(Context context) {
		breathSeries = new XYSeries("呼吸节律");
		breathDataset = new XYMultipleSeriesDataset();
		breathDataset.addSeries(breathSeries);
		int[] colors = new int[] { Color.CYAN };// 线条的颜色
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };// 坐标点的形状
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		setChartSettings(renderer, "呼吸节律", "时间", "呼吸", 0, 100, 0, 40,
				Color.WHITE, Color.WHITE);
		((XYSeriesRenderer) renderer.getSeriesRendererAt(0))
				.setFillPoints(true);
		((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(3f);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setShowGrid(true);
		renderer.setZoomEnabled(false);
		renderer.setZoomButtonsVisible(false);
		renderer.setLabelsTextSize(20);
		renderer.setAxisTitleTextSize(20);
		breath = ChartFactory.getCubeLineChartView(context, breathDataset,
				renderer, 0.1f);
		return breath;
	}

	/**
	 * 一下为处理数据集的函数
	 *
	 */

	protected XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
	}

	/**
	 * Builds an XY multiple series renderer.
	 *
	 * @param colors
	 *            the series rendering colors
	 * @param styles
	 *            the series point styles
	 * @return the XY multiple series renderers
	 */
	protected XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	/**
	 * Sets a few of the series renderer settings.
	 *
	 * @param renderer
	 *            the renderer to set the properties to
	 * @param title
	 *            the chart title
	 * @param xTitle
	 *            the title for the X axis
	 * @param yTitle
	 *            the title for the Y axis
	 * @param xMin
	 *            the minimum value on the X axis
	 * @param xMax
	 *            the maximum value on the X axis
	 * @param yMin
	 *            the minimum value on the Y axis
	 * @param yMax
	 *            the maximum value on the Y axis
	 * @param axesColor
	 *            the axes color
	 * @param labelsColor
	 *            the labels color
	 */
	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	/**
	 * Builds an XY multiple time dataset using the provided values.
	 *
	 * @param titles
	 *            the series titles
	 * @param xValues
	 *            the values for the X axis
	 * @param yValues
	 *            the values for the Y axis
	 * @return the XY multiple time dataset
	 */
	protected XYMultipleSeriesDataset buildDateDataset(String[] titles,
			List<Date[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			TimeSeries series = new TimeSeries(titles[i]);
			Date[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	/**
	 * Builds a category series using the provided values.
	 *
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the category series
	 */
	protected CategorySeries buildCategoryDataset(String title, double[] values) {
		CategorySeries series = new CategorySeries(title);
		int k = 0;
		for (double value : values) {
			series.add("Project " + ++k, value);
		}

		return series;
	}

	/**
	 * Builds a multiple category series using the provided values.
	 *
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the category series
	 */
	protected MultipleCategorySeries buildMultipleCategoryDataset(String title,
			List<String[]> titles, List<double[]> values) {
		MultipleCategorySeries series = new MultipleCategorySeries(title);
		int k = 0;
		for (double[] value : values) {
			series.add(2007 + k + "", titles.get(k), value);
			k++;
		}
		return series;
	}

	/**
	 * Builds a category renderer to use the provided colors.
	 *
	 * @param colors
	 *            the colors
	 * @return the category renderer
	 */
	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	/**
	 * Builds a bar multiple series dataset using the provided values.
	 *
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the XY multiple bar dataset
	 */
	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	/**
	 * Builds a bar multiple series renderer to use the provided colors.
	 *
	 * @param colors
	 *            the series renderers colors
	 * @return the bar multiple series renderer
	 */
	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	InputStream recvInputStream = null;

	private class BluetoothAcceptThread extends Thread {
		public BluetoothAcceptThread(BluetoothAdapter btAdapter, int port) {
			try {

				mBThServerSocket = ClsUtils.listenUsingRfcommOn(
						btAdapter.getClass(), btAdapter, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				mBThSocket = mBThServerSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				mBThServerSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Log.i("info", "computer is connected!");
			try {
				// mBufferReader = new BufferedReader(new
				// InputStreamReader(mBThSocket.getInputStream(),"utf8 "));
				recvInputStream = mBThSocket.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				mPrintStream = new PrintStream(mBThSocket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			byte[] buffer = "10002181".getBytes();
			try {
				mPrintStream.write(buffer);
			} catch (IOException e) {
				isConnected = false;
				try {
					mBThSocket.close();
					mPrintStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
			mPrintStream.flush();
			Log.i("info", "send---------!");
			isConnected = true;
			Thread receiveThread = new recvThread();
			receiveThread.start();
		}
	}

	private class recvThread extends Thread {
		public recvThread() {
			Message message = new Message();
			message.what = 1;
			updateUI.sendMessage(message);
		}

		public void run() {
			byte[] buffer = new byte[1024 * 2];
			int bytes;
			timer.schedule(task, 1000, 1000);
			while (isConnected) {
				try {
					// Read from the InputStream
					if ((bytes = recvInputStream.read(buffer, 0, 1024 * 2)) > 0) {
						byte[] buf_data = new byte[bytes];
						for (int i = 0; i < bytes; i++) {
							buf_data[i] = buffer[i];
						}
						String recvStr = new String(buf_data);
						if (!recvStr.isEmpty()) {
							String[] recedata = recvStr.split("@");
							String[] bodydata = recedata[0].split(",");
							String[] breathdata = recedata[1].split(",");
							String[] heartdata = recedata[2].split(",");
							addNode(intbody, Integer.parseInt(bodydata[0]));
							addNode(intbreath, Integer.parseInt(breathdata[0]));
							addNode(intheart, Integer.parseInt(heartdata[0]));
							bodyInt = bodydata[1];
							breathInt = breathdata[1];
							heartsInt = heartdata[1];
						}
						Log.i("info", "recvice---------!" + recvStr);
					}
				} catch (IOException e) {
					try {
						isConnected = false;
						mBThSocket.close();
						recvInputStream.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				}
			}
		}
	}

	private void addNode(List<Integer> list, int x) {
		if (list.size() < 100) {
			list.add(x);
		} else {
			for (int i = 0; i < 99; i++) {
				list.set(i, list.get(i + 1));
			}
			list.set(99, x);
		}
	}

	private void updateBodyChart(View chart) {
		int[] xv = new int[100];
		int[] yv = new int[100];
		// 设置好下一个需要增加的节点
		int addX = 0;
		int addY;
		// 移除数据集中旧的点集
		bodyDataset.removeSeries(bodySeries);
		// 判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100
		int length = bodySeries.getItemCount();
		if (length > 99) {
			length = 99;
		}
		if (intbody.size() > 0) {
			if (intbody.size() <= length) {
				addY = intbody.get(intbody.size() - 1);
			} else {
				addY = intbody.get(length);
			}
		} else {
			return;
		}
		// 将旧的点集中x和y的数值取出来放入backup中，并且将x的值加1，造成曲线向右平移的效果
		for (int i = 0; i < length; i++) {
			xv[i] = (int) bodySeries.getX(i) + 2;
			yv[i] = (int) bodySeries.getY(i);
		}
		// 点集先清空，为了做成新的点集而准备
		bodySeries.clear();
		// 将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
		// 这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点
		bodySeries.add(addX, addY);
		for (int k = 0; k < length; k++) {
			bodySeries.add(xv[k], yv[k]);
		}

		// 在数据集中添加新的点集
		bodyDataset.addSeries(bodySeries);
		// 视图更新，没有这一步，曲线不会呈现动态
		// 如果在非UI主线程中，需要调用postInvalidate()，具体参考api
		chart.invalidate();
	}

	private void updateHeartChart(View chart) {
		int[] xv = new int[100];
		int[] yv = new int[100];
		// 设置好下一个需要增加的节点
		int addX = 0;
		int addY;
		// 移除数据集中旧的点集
		heartDataset.removeSeries(heartSeries);
		// 判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100
		int length = heartSeries.getItemCount();
		if (length > 99) {
			length = 99;
		}
		if (intheart.size() > 0) {
			if (intheart.size() <= length) {
				addY = intheart.get(intheart.size() - 1);
			} else {
				addY = intheart.get(length);
			}
		} else {
			return;
		}
		for (int i = 0; i < length; i++) {
			xv[i] = (int) heartSeries.getX(i) + 2;
			yv[i] = (int) heartSeries.getY(i);
		}
		heartSeries.clear();
		heartSeries.add(addX, addY);
		for (int k = 0; k < length; k++) {
			heartSeries.add(xv[k], yv[k]);
		}
		heartDataset.addSeries(heartSeries);
		chart.invalidate();
	}

	private void updateBreathChart(View chart) {
		int[] xv = new int[100];
		int[] yv = new int[100];
		int addX = 0;
		int addY;
		breathDataset.removeSeries(breathSeries);
		int length = breathSeries.getItemCount();
		if (length > 99) {
			length = 99;
		}
		if (intbreath.size() > 0) {
			if (intbreath.size() <= length) {
				addY = intbreath.get(intbreath.size() - 1);
			} else {
				addY = intbreath.get(length);
			}
		} else {
			return;
		}
		for (int i = 0; i < length - 1; i++) {
			xv[i] = (int) breathSeries.getX(i) + 2;
			yv[i] = (int) breathSeries.getY(i);
		}
		breathSeries.clear();
		breathSeries.add(addX, addY);
		for (int k = 0; k < length; k++) {
			breathSeries.add(xv[k], yv[k]);
		}
		breathDataset.addSeries(breathSeries);
		chart.invalidate();
	}
}
