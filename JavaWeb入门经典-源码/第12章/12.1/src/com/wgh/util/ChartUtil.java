package com.wgh.util;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * 自定义绘图工具类
 */
public class ChartUtil {
	/**
	 * 创建数据集合
	 * @return CategoryDataset对象
	 */
	public static CategoryDataset createDataSet() {
		//实例化DefaultCategoryDataset对象
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		//向数据集合中添加数据
		dataSet.addValue(550, "JAVA图书", "Java SE类");
		dataSet.addValue(100, "JAVA图书", "Java ME类");
		dataSet.addValue(960, "JAVA图书", "Java EE类");
		return dataSet;
	}
	/**
	 * 创建JFreeChart对象
	 * @return JFreeChart对象
	 */
	public static JFreeChart createChart() {
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");	//创建主题样式
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); 	//设置标题字体
		standardChartTheme.setRegularFont(new Font("微软雅黑", Font.PLAIN, 15)); 	//设置图例的字体
		standardChartTheme.setLargeFont(new Font("微软雅黑", Font.PLAIN, 15)); 		//设置轴向的字体
		ChartFactory.setChartTheme(standardChartTheme);	//设置主题样式
		//通过ChartFactory创建JFreeChart
		JFreeChart chart = ChartFactory.createBarChart3D(
				"JAVA图书销量统计", 			//图表标题
				"JAVA图书", 					//横轴标题
				"销量（本）", 						//纵轴标题
				createDataSet(),			//数据集合 
				PlotOrientation.VERTICAL, 	//图表方向
				false, 						//是否显示图例标识
				false, 						//是否显示tooltips
				false);						//是否支持超链接
		return chart;
	}
}
