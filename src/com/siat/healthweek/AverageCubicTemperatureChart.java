/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.siat.healthweek;

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

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 平均温度示例表格。
 * @author 刘清伟（http://www.liuqingwei.com）
 * @created 2014-04-06
 */
public class AverageCubicTemperatureChart extends Activity{

	private Timer timer = new Timer();
	private TimerTask task;
	private Handler handler;
	private XYMultipleSeriesDataset mDataset;
	private XYSeries series;
	private GraphicalView hello;
    private int addX = -1, addY;
    int[] xv = new int[100];
    int[] yv = new int[100];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bed_fragment);
		LayoutParams layout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT);
		layout.weight = 1;
		series = new XYSeries("new");
		mDataset = new XYMultipleSeriesDataset();
		mDataset.addSeries(series);
		int[] colors = new int[] { Color.RED};//线条的颜色
	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE};//坐标点的形状
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        setChartSettings(renderer,"Test","X", "Y", 0, 100, 0, 90, Color.WHITE, Color.WHITE);
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setFillPoints(true);
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(3f);
		LinearLayout linearView = (LinearLayout) findViewById(R.id.chart_show);
        hello = ChartFactory.getCubeLineChartView(this, mDataset, renderer,0.1f);
		linearView.addView(showDraw(this),layout);
		linearView.addView(hello,layout);
		handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //刷新图表
                updateChart(hello);
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
        //当结束程序时关掉Timer
        timer.cancel();
        super.onDestroy();
    }
  /**
   * 展示表格
   *
   * @param context the context
   * @return the built intent
   */
  public View showDraw(Context context) {
    String[] titles = new String[] { "上海", "广州", "深圳", "重庆" };
    List<double[]> x = new ArrayList<double[]>();
    for (int i = 0; i < titles.length; i++) {
      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
    }
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
        13.9 });
    values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14, 11 });
    values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });
    values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
    int[] colors = new int[] { Color.RED, Color.GREEN, Color.CYAN, Color.YELLOW };//线条的颜色
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,PointStyle.TRIANGLE, PointStyle.SQUARE };//坐标点的形状
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setLineWidth(3f);
    }
    setChartSettings(renderer, "平均气温", "月份", "气温", 0.5, 12.5, 0, 32,
        Color.LTGRAY, Color.LTGRAY);
    renderer.setXLabels(12);
    renderer.setYLabels(10);
    renderer.setShowGrid(false);
    renderer.setXLabelsAlign(Align.RIGHT);
    renderer.setYLabelsAlign(Align.RIGHT);
    renderer.setZoomButtonsVisible(false);
    renderer.setLabelsTextSize(24);

    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
    return ChartFactory.getCubeLineChartView(context, buildDataset(titles, x, values),renderer, 0.33f);
  }

  /**
   * 一下为处理数据集的函数
   *
   */

  protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,
	      List<double[]> yValues) {
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    addXYSeries(dataset, titles, xValues, yValues, 0);
	    return dataset;
	  }

	  public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
	      List<double[]> yValues, int scale) {
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
	   * @param colors the series rendering colors
	   * @param styles the series point styles
	   * @return the XY multiple series renderers
	   */
	  protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    setRenderer(renderer, colors, styles);
	    return renderer;
	  }

	  protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
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
	   * @param renderer the renderer to set the properties to
	   * @param title the chart title
	   * @param xTitle the title for the X axis
	   * @param yTitle the title for the Y axis
	   * @param xMin the minimum value on the X axis
	   * @param xMax the maximum value on the X axis
	   * @param yMin the minimum value on the Y axis
	   * @param yMax the maximum value on the Y axis
	   * @param axesColor the axes color
	   * @param labelsColor the labels color
	   */
	  protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
	      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
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
	   * @param titles the series titles
	   * @param xValues the values for the X axis
	   * @param yValues the values for the Y axis
	   * @return the XY multiple time dataset
	   */
	  protected XYMultipleSeriesDataset buildDateDataset(String[] titles, List<Date[]> xValues,
	      List<double[]> yValues) {
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
	   * @param titles the series titles
	   * @param values the values
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
	   * @param titles the series titles
	   * @param values the values
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
	   * @param colors the colors
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
	   * @param titles the series titles
	   * @param values the values
	   * @return the XY multiple bar dataset
	   */
	  protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
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
	   * @param colors the series renderers colors
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

	private void updateChart(View chart) {

		// 设置好下一个需要增加的节点
		addX = 0;
		addY = ((int) (Math.random() * 30))+40;

		// 移除数据集中旧的点集
		mDataset.removeSeries(series);

		// 判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100
		int length = series.getItemCount();
		if (length > 100) {
			length = 100;
		}

		// 将旧的点集中x和y的数值取出来放入backup中，并且将x的值加1，造成曲线向右平移的效果
		for (int i = 0; i < length; i++) {
			xv[i] = (int) series.getX(i) + 2;
			yv[i] = (int) series.getY(i);
		}

		// 点集先清空，为了做成新的点集而准备
		series.clear();

		// 将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
		// 这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点
		series.add(addX, addY);
		for (int k = 0; k < length; k++) {
			series.add(xv[k], yv[k]);
		}

		// 在数据集中添加新的点集
		mDataset.addSeries(series);

		// 视图更新，没有这一步，曲线不会呈现动态
		// 如果在非UI主线程中，需要调用postInvalidate()，具体参考api
		chart.invalidate();
	}
}
