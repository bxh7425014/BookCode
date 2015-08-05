package com.wgh.util;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * 自定义制图工具类
 * @author Li Yong Qiang
 */
public class ChartUtil {
	// 字体
	private static final Font PLOT_FONT = new Font("宋体", Font.BOLD, 15);
	/**
	 * 创建数据集合
	 * @return CategoryDataset对象
	 */
	public static CategoryDataset createDataset() {
		// 实例化DefaultCategoryDataset对象
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		Random random = new Random();		// 创建Random对象
		// 向数据集合加入6个月的数据
		for (int i = 1; i < 7; i++) {
			defaultcategorydataset.addValue(random.nextInt(5000) + 5000, "新闻网站", i + "月份");
			defaultcategorydataset.addValue(random.nextInt(5000) + 5000, "娱乐网站", i + "月份");
			defaultcategorydataset.addValue(random.nextInt(5000) + 5000, "体育网站", i + "月份");
		}
		return defaultcategorydataset;
	}
	/**
	 * 生成制图对象
	 * @return JFreeChart对象
	 */
	public static JFreeChart createChart(){
		JFreeChart chart = ChartFactory.createAreaChart(
					"XX门户网站流量统计分析", 			// 图表标题
					"网站类别", 			// 横轴标题
					"流量（IP）", 			// 纵轴标题
					createDataset(), 	// 制图的数据集
					PlotOrientation.VERTICAL,		// 定义区域图的方向为纵向
					true, 				// 是否显示图例标识
					true, 				// 是否显示tooltips
					false);				// 是否支持超链接
		// 设置标题字体
		chart.getTitle().setFont(new Font("隶书", Font.BOLD, 25));
		// 设置图例类别字体
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
		// 设置背景色
		chart.setBackgroundPaint(new Color(160,214,248));
		// 获取绘图区对象
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setLabelFont(PLOT_FONT);		// 设置横轴字体
		plot.getDomainAxis().setTickLabelFont(PLOT_FONT);	// 设置坐标轴标尺值字体
		plot.getRangeAxis().setLabelFont(PLOT_FONT);		// 设置纵轴字体
		plot.setForegroundAlpha(0.4F);	// 设置透明度
		plot.setDomainGridlinesVisible(true); // 设置显示网格
		return chart;
	}
}
