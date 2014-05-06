package com.bit_health.android.util;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

/**********************************************************************
 * 类名：ChartOfXindian
 *
 * 主要功能：报告记录模块中的心电记录图标
 *
 * @author 梁才学 创建日期：2014.1.20
 **********************************************************************/
public class ChartOfXindian {

	public static final int SHOW_COUNT_TOTLE = 10;
	private Activity mActivity;
	private LinearLayout layout;
	private GraphicalView chart;
	private double[] dataY;// 最后10项数据的数组
	private double sum_dataY;
	private int average_dataY = 0;
	private double min_dataY = 0;
	private double max_dataY = 0;
	private String unitType;
	private String testType;

	public ChartOfXindian(Activity activity, LinearLayout layout,
			double[] dataY, String unit_type, String test_type) {
		mActivity = activity;
		this.layout = layout;
		this.dataY = dataY;
		this.unitType = unit_type;
		this.testType = test_type;

		for (int i = 0; i < dataY.length; i++) {
			sum_dataY = sum_dataY + dataY[i];
		}
		for (int i = 0; i < dataY.length; i++) {

			if(min_dataY > dataY[i]){
				min_dataY = dataY[i];
			}
			if(max_dataY < dataY[i]){
				max_dataY = dataY[i];
			}
		}

		if (dataY.length != 0) {
			average_dataY = (int) (sum_dataY / dataY.length);
		}
	}

	public void drawChart() {
		// ================== 画两条线段 start ============================
		// String[] line_names = new String[] { "最低值", "平均值" };// 两条曲线名
		String[] line_names = new String[] { "平均值" };// 两条曲线名
		List<double[]> line_x = new ArrayList<double[]>();// 两条曲线的X轴值
		for (int i = 0; i < line_names.length; i++) {
			line_x.add(new double[] { 1, SHOW_COUNT_TOTLE });// 线段的起点和终点的X值
		}
		List<double[]> values = new ArrayList<double[]>();// 两条曲线的Y轴值，Y值都一样，则画出横线
		// values.add(new double[] { min_dataY, min_dataY });
		values.add(new double[] { average_dataY, average_dataY });// 线段的起点和终点的Y值

		// int[] colors = new int[] { Color.BLUE, Color.RED };// 两条曲线的颜色
		int[] colors = new int[] { Color.BLUE };// 两条曲线的颜色
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.DIAMOND };// 描点的样式
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);

		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = (XYSeriesRenderer) renderer
					.getSeriesRendererAt(i);
			r.setLineWidth(2);// 曲线的宽度
			r.setDisplayChartValues(true);// 是否给曲线标注数值
			r.setChartValuesTextSize(20);// 数值字体大小
			r.setFillPoints(true);
		}
		// ================== 画两条线段 end ============================

		// ================== 图表框架的属性设置 start ============================
		setChartSettings(renderer, "心电报告图表", "最后10项测量(单位：" + unitType + ")",
				"y轴的说明", 0.5, 12.5, min_dataY, max_dataY + 10, Color.BLACK, Color.BLACK);

		renderer.setXLabels(SHOW_COUNT_TOTLE);// 设置x轴上的下标数量
		renderer.setYLabels(SHOW_COUNT_TOTLE);// 设置y轴上的下标数量,设为0则不显示数值，设为10则显示10个数值
		renderer.setShowGrid(false);// 是否显示网格
		renderer.setXLabelsAlign(Align.RIGHT);// X轴坐标对齐
		renderer.setYLabelsAlign(Align.RIGHT);// Y轴坐标对齐
		renderer.setZoomButtonsVisible(false);// 缩放按钮是否可见
		renderer.setPanEnabled(false, false); // 设置是否允许XY轴方向移动
		// renderer.setPanLimits(new double[] { -10, 20, -10, 40 });//
		// 左右、上下移动的限制
		// renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });// 缩放的限制

		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		renderer.setBackgroundColor(Color.parseColor("#00AEEF"));// 设置背景色
		renderer.setMarginsColor(Color.parseColor("#00AEEF"));// 整个图表周围的颜色
		renderer.setShowLegend(true);// 设置是否显示图例
		renderer.setLegendTextSize(25); // 图例字体大小
		renderer.setFitLegend(true);// 调整合适的位置
		// ================== 图表框架的属性设置 end ============================

		// ================== 柱状的数据及样式设置 start ============================
		XYSeries waterSeries = new XYSeries(testType);// 图表的副标题说明，如：心电值、血压值、血糖值、脉搏值
		for (int i = 0; i < dataY.length; i++) {
			waterSeries.add(i + 1, dataY[i]);// 柱状的Y值数据
		}
		renderer.setBarSpacing(1.0);// 柱状的间距
		XYSeriesRenderer waterRenderer = new XYSeriesRenderer();
		waterRenderer.setColor(Color.parseColor("#FFFFFF"));// 柱状的颜色

		XYMultipleSeriesDataset dataset = buildDataset(line_names, line_x,
				values);
		dataset.addSeries(0, waterSeries);
		renderer.addSeriesRenderer(0, waterRenderer);
		waterRenderer.setDisplayChartValues(true);// 是否在柱状图中的每个柱子上显示数值
		waterRenderer.setChartValuesTextSize(20);// 柱子上的数字大小
		// ================== 柱状的数据及样式设置 end ============================

		// String[] types = new String[] { BarChart.TYPE, LineChart.TYPE,
		// LineChart.TYPE };// 图的类型，画一个图形就需要对应一个字段
		String[] types = new String[] { BarChart.TYPE, LineChart.TYPE };// 图的类型，画一个图形就需要对应一个字段
		chart = ChartFactory.getCombinedXYChartView(mActivity, dataset,
				renderer, types);
		layout.addView(chart, new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 380));
	}

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

	/**********************************************************************
	 * 方法描述：生成渲染器对象
	 *
	 **********************************************************************/
	private XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		// TODO Auto-generated method stub
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	/**********************************************************************
	 * 方法描述：设置图表的风格、样式一
	 *
	 **********************************************************************/
	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		// renderer.setChartTitle(title);// 图表标题
		renderer.setXTitle(xTitle);// x轴标题说明
		// renderer.setYTitle(yTitle);// Y轴标题说明
		renderer.setXAxisMin(0.5);// X 轴坐标的起始值
		renderer.setXAxisMax(10.5);// X 轴坐标的最后值
		renderer.setYAxisMin(yMin);// Y 轴坐标的起始值
		renderer.setYAxisMax(yMax);// Y 轴坐标的最后值
		renderer.setAxesColor(axesColor);// X Y 轴的颜色
		renderer.setLabelsColor(labelsColor);// X、Y 轴字体颜色
	}

	/**********************************************************************
	 * 方法描述：设置图表的风格、样式二
	 *
	 **********************************************************************/
	protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		renderer.setAxisTitleTextSize(25);// x轴标题字体大小
		// renderer.setChartTitleTextSize(25);// 图表标题字体大小
		renderer.setLabelsTextSize(15);// X 、Y 轴刻度字体大小
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);

		renderer.setXLabelsColor(Color.BLACK);// X 轴刻度字体颜色
		renderer.setYLabelsColor(0, Color.BLACK);// Y 轴刻度字体颜色
		renderer.setMargins(new int[] { 28, 20, 55, 5 });// 4边大小：上 ,左,下,右
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			// r.setPointStyle(styles[i]);// 设置曲线上的点的样式，不设置则不显示点
			renderer.addSeriesRenderer(r);
		}
	}
}
