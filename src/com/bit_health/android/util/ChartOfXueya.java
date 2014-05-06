package com.bit_health.android.util;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;

/**********************************************************************
 * 类名：ChartOfXueya
 * 
 * 主要功能：血压图表
 * 
 * @author 梁才学 创建日期：2014.1.20
 **********************************************************************/
public class ChartOfXueya {

	private static final int SERIES_NR = 2;// 曲线数量
	
	private Activity mActivity;
	private LinearLayout layout;
	private GraphicalView chart;
	private XYMultipleSeriesRenderer renderer;
	private double[] dataY;// 最后10项数据的数组
	
//	private String[] dates = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" };
	private String[] charts_item_name = { "收缩压", "舒张压" };
	
	public ChartOfXueya(Activity activity, LinearLayout layout, double[] dataY){
		mActivity = activity;
		this.layout = layout;
		this.dataY = dataY;
	}
	
	public void drawChart(){
		renderer = getBarChartRenderer();
		chart = ChartFactory.getBarChartView(mActivity, getBarChartDataset(),
				renderer, Type.DEFAULT);// 生成图表
		layout.addView(chart, new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 380));
	}
	
	/*****************************************************************
	 * 方法描述：获取图表显示的数据，即:Y轴的值
	 * 
	 * @param
	 * @return
	 ****************************************************************/
	private XYMultipleSeriesDataset getBarChartDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < SERIES_NR; i++) {
			CategorySeries series = new CategorySeries(charts_item_name[i]);// 图例说明的字符串
			for (int k = 0; k < 10; k++) {
				series.add(dataY[k]);		
			}
			// 必须把CategorySeries 对象转换为二维坐标,才能 add 到 XYMultipleSeriesDataset
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	/*****************************************************************
	 * 方法描述：柱状图表的绘制，即设置一系列参数
	 * 
	 * @param
	 * @return
	 ****************************************************************/
	public XYMultipleSeriesRenderer getBarChartRenderer() {

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();// 显示的渲染图对象
//		renderer.setChartTitle("血压报告图表");// 图表标题
//		renderer.setChartTitleTextSize(25);// 图表标题字体大小
		renderer.setAxisTitleTextSize(25);// x轴标题字体大小
		renderer.setLabelsTextSize(20);// X 、Y 轴刻度字体大小

		renderer.setXLabelsColor(Color.BLACK);// X 轴刻度字体颜色
		renderer.setYLabelsColor(0, Color.BLACK);// Y 轴刻度字体颜色

		renderer.setXLabels(10);// 设置x轴上的下标数量, 如果不想x轴有数值，设为0即可
		renderer.setYLabels(0); //设置y轴上的下标数量, 0表示不显示

		renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		renderer.setBackgroundColor(Color.parseColor("#2B974D"));// 设置背景色
		renderer.setMargins(new int[] { 28, 20, 55, 5 });// 4边大小：上 ,左,下,右
		renderer.setMarginsColor(Color.parseColor("#2B974D"));// 整个图表周围的颜色
		renderer.setLabelsColor(Color.BLACK);// X、Y 轴字体颜色

		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.parseColor("#FFFFFF"));// 柱状颜色设置方法一
		// r.setColor(Color.argb(250, 0, 210, 250));// 柱状颜色设置方法二
		renderer.addSeriesRenderer(r);// 添加第一个柱状图

		/******** 添加第2个柱状图 start *********/
		r = new SimpleSeriesRenderer();
		r.setColor(Color.parseColor("#FFFF00"));
		renderer.addSeriesRenderer(r);
		/******** 添加第2个柱状图 end *********/

		renderer.setShowLegend(true);// 设置是否显示图例
		renderer.setLegendTextSize(20); // 图例字体大小

		renderer.setXTitle("最后10项测量（单位：mmHg）");// x轴标题说明
		// renderer.setYTitle("bpm");// y轴标题说明

		renderer.setXAxisMin(0.5);// X 轴开始显示的数字
		renderer.setXAxisMax(10.5);// X 轴最后显示的数字
//		renderer.setYAxisMin(50);// Y 轴开始显示的数字
//		renderer.setYAxisMax(260);// Y 轴最后显示的数字
		// renderer.setXLabels(6); //
		// 设置x轴显示6个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		// renderer.setYLabels(6); //
		// 设置y轴显示6个点,根据setChartSettings的最大值和最小值自动计算点的间隔

		renderer.setBarSpacing(0.5);// 设置柱状的间距
		renderer.setFitLegend(true);// 调整合适的位置

		renderer.setPanEnabled(false, false); // 设置是否允许XY轴方向移动
//		renderer.setPanEnabled(true, true); // 设置是否允许XY轴方向移动
//		renderer.setPanLimits(new double[] { -5, 16, -15, 300 });// 设置XY轴方向移动的上、下限{-x,
																	// x, -y, y}
		// =========设置X轴不显示数字（改用我们手动添加的文字标签）start======
		// renderer.setXLabels(0);
		// int i = 0;
		// for (String date : dates) {
		// renderer.addTextLabel(i, date); // 修改X轴显示坐标
		// i++;
		// }
		// =========设置X轴不显示数字（改用我们手动添加的文字标签）end======

		// renderer.setZoomEnabled(true, false);// 是否支持图表缩放

		return renderer;
	}
}
