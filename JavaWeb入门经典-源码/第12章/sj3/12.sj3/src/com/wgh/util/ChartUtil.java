package com.wgh.util;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * 自定义制图工具类
 */
public class ChartUtil {
	// 字体
	private static final Font PLOT_FONT = new Font("黑体", Font.ITALIC , 18);
	/**
	 * 创建数据集合
	 * @return XYDataset对象
	 */
	public static XYDataset createDataset() {
		// 实例化TimeSeries对象
		TimeSeries timeseries = new TimeSeries("Data");
		Day day = new Day(1, 1, 2008);	// 实例化Day
		double d = 3000D;
		// 添加一年365天的数据
		for (int i = 0; i < 365; i++) { 
			d = d + (Math.random() - 0.5) * 10;	// 创建随机数据
			timeseries.add(day, d);	// 向数据集合中添加数据
			day = (Day) day.next();	
		}
		// 创建TimeSeriesCollection集合对象
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection(timeseries);
		// 返回数据集合对象
		return timeSeriesCollection;
	}
	/**
	 * 生成制图对象
	 * @return JFreeChart对象
	 */
	public static JFreeChart createChart(){
		// 创建时序图对象
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"编程词典全国销量统计", 	// 标题
				"销售月份", 				// 时间轴标签
				"销量（份）", 			// 数据轴标签
				createDataset(), 		// 数据集合
				false, 					// 是否显示图例标识
				false,					// 是否显示tooltips
				false);					// 是否支持超链接
		// 设置标题字体
		chart.getTitle().setFont(new Font("隶书", Font.BOLD, 26));
		// 设置背景色
		chart.setBackgroundPaint(new Color(252,175,134));
		XYPlot plot = chart.getXYPlot();		// 获取图表的绘制属性
		plot.setDomainGridlinesVisible(false);	// 设置网格不显示
		// 获取时间轴对象
		DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
		dateAxis.setLabelFont(PLOT_FONT);	// 设置时间轴字体
		// 设置时间轴字体标尺字体
		dateAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,12));
		dateAxis.setLowerMargin(0.0);	// 设置时间轴上的显示最小值
		// 获取数据轴对象
		ValueAxis valueAxis = plot.getRangeAxis();
		valueAxis.setLabelFont(PLOT_FONT);	// 设置数据字体
		DateFormat format = new SimpleDateFormat("MM月份");	// 创建日期格式对象
		// 创建DateTickUnit对象
		DateTickUnit dtu = new DateTickUnit(DateTickUnitType.DAY,29,format);
		dateAxis.setTickUnit(dtu);	// 设置日期轴的日期标签
		return chart;
	}
}
