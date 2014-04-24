package com.mobilehealth.sensibelbed;

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
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.siat.healthweek.R;

public class FragmentBreathFreq extends Fragment {

	private Timer timer = new Timer();
	private TimerTask task;
	private Handler handler;
	private GraphicalView hearts, breath, body;
	private XYSeries heartSeries, breathSeries, bodySeries;
	private XYMultipleSeriesDataset heartDataset, breathDataset, bodyDataset;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.bed_fragment, container, false);

		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		init(view);

		super.onViewCreated(view, savedInstanceState);
	}

	@SuppressLint("HandlerLeak")
	private void init(View view) {
		LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layout.weight = 1;
		LinearLayout linearView = (LinearLayout) view
				.findViewById(R.id.chart_show);
		showBody(getActivity());
		showHeart(getActivity());
		showBreath(getActivity());
		linearView.addView(body, layout);
		linearView.addView(hearts, layout);
		linearView.addView(breath, layout);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 刷新图表
				updateBodyChart(body);
				updateHeartChart(hearts);
				updateBreathChart(breath);
				super.handleMessage(msg);
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
		timer.schedule(task, 500, 500);
	}

	@Override
	public void onDestroy() {
		// 当结束程序时关掉Timer
		timer.cancel();
		super.onDestroy();
	}

	/**
	 * 展示人体动态
	 *
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public View showBody(Context context) {
		bodySeries = new XYSeries("运动节律");
		bodyDataset = new XYMultipleSeriesDataset();
		bodyDataset.addSeries(bodySeries);
		int[] colors = new int[] { Color.RED };// 线条的颜色
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };// 坐标点的形状
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		setChartSettings(renderer, "人体翻身节律", "时间", "动量", 0, 100, 0, 90,
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
		setChartSettings(renderer, "心率图", "时间", "心率", 0, 100, 0, 90,
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
		int[] colors = new int[] { Color.CYAN};// 线条的颜色
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE};// 坐标点的形状
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		setChartSettings(renderer, "呼吸节律", "时间", "呼吸", 0, 100, 0, 90,
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

	private void updateBodyChart(View chart) {
		int[] xv = new int[100];
		int[] yv = new int[100];
		// 设置好下一个需要增加的节点
		int addX = 0;
		int addY = ((int) (Math.random() * 30)) + 40;
		// 移除数据集中旧的点集
		bodyDataset.removeSeries(bodySeries);
		// 判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100
		int length = bodySeries.getItemCount();
		if (length > 100) {
			length = 100;
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
		int addY = ((int) (Math.random() * 30)) + 40;
		// 移除数据集中旧的点集
		heartDataset.removeSeries(heartSeries);
		// 判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100
		int length = heartSeries.getItemCount();
		if (length > 100) {
			length = 100;
		}
		// 将旧的点集中x和y的数值取出来放入backup中，并且将x的值加1，造成曲线向右平移的效果
		for (int i = 0; i < length; i++) {
			xv[i] = (int) heartSeries.getX(i) + 2;
			yv[i] = (int) heartSeries.getY(i);
		}
		// 点集先清空，为了做成新的点集而准备
		heartSeries.clear();
		// 将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
		// 这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点
		heartSeries.add(addX, addY);
		for (int k = 0; k < length; k++) {
			heartSeries.add(xv[k], yv[k]);
		}
		// 在数据集中添加新的点集
		heartDataset.addSeries(heartSeries);
		// 视图更新，没有这一步，曲线不会呈现动态
		// 如果在非UI主线程中，需要调用postInvalidate()，具体参考api
		chart.invalidate();
	}

	private void updateBreathChart(View chart) {
		int[] xv = new int[100];
		int[] yv = new int[100];
		// 设置好下一个需要增加的节点
		int addX = 0;
		int addY = ((int) (Math.random() * 30)) + 40;
		// 移除数据集中旧的点集
		breathDataset.removeSeries(breathSeries);
		// 判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100
		int length = breathSeries.getItemCount();
		if (length > 100) {
			length = 100;
		}
		// 将旧的点集中x和y的数值取出来放入backup中，并且将x的值加1，造成曲线向右平移的效果
		for (int i = 0; i < length-1; i++) {
			xv[i] = (int) breathSeries.getX(i) + 2;
			yv[i] = (int) breathSeries.getY(i);
		}
		// 点集先清空，为了做成新的点集而准备
		breathSeries.clear();
		// 将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
		// 这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点
		breathSeries.add(addX, addY);
		for (int k = 0; k < length; k++) {
			breathSeries.add(xv[k], yv[k]);
		}
		// 在数据集中添加新的点集
		breathDataset.addSeries(breathSeries);
		// 视图更新，没有这一步，曲线不会呈现动态
		// 如果在非UI主线程中，需要调用postInvalidate()，具体参考api
		chart.invalidate();
	}

}
