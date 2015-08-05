package com.wgh.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 自定义制图工具类
 */
public class ChartUtil {
	// 字体
	private static final Font PLOT_FONT = new Font("宋体", Font.BOLD, 15);
	/**
	 * 创建数据集合
	 * @return CategoryDataset对象
	 */
	public static CategoryDataset createDataSet() {
		// 图例名称
		String[] line = { "工具软件", "游戏软件", "其他软件" };
		// 类别
		String[] category = { "2008年","2009年", "2010年", "2011年", "2012年" };
		Random random = new Random();	// 实例化Random对象
		// 实例化DefaultCategoryDataset对象
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		// 使用循环向数据集合中添加数据
		for (int i = 0; i < line.length; i++) {
			for (int j = 0; j < category.length; j++) {
				dataSet.addValue(1000 + random.nextInt(1000), line[i],
						category[j]);
			}
		}
		return dataSet;
	}
	/**
	 * 生成制图对象
	 * @param is3D 是否为3D效果
	 * @return JFreeChart对象
	 */
	public static JFreeChart createChart(boolean is3D) {
		JFreeChart chart = null;
		if(is3D){
			chart = ChartFactory.createLineChart3D(
					"2008-2012年智能手机软件下载数量统计", // 图表标题
					"软件类别", // X轴标题
					"下载数量", // Y轴标题
					createDataSet(), // 绘图数据集
					PlotOrientation.VERTICAL, // 绘制方向
					true, // 显示图例
					true, // 采用标准生成器
					false // 生成链接
					);
		}else{
			chart = ChartFactory.createLineChart("2008-2012年智能手机软件下载数量统计", // 图表标题
					"软件类别", // X轴标题
					"下载数量", // Y轴标题
					createDataSet(), // 绘图数据集
					PlotOrientation.VERTICAL, // 绘制方向
					true, // 显示图例
					true, // 采用标准生成器
					false // 生成链接
					);
		}
		
		// 设置标题字体
		chart.getTitle().setFont(new Font("隶书", Font.BOLD, 23));
		// 设置图例类别字体
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
		chart.setBackgroundPaint(new Color(192,228,106));	// 设置背景色
		// 获取绘图区对象
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setLabelFont(PLOT_FONT);		// 设置横轴字体
		plot.getDomainAxis().setTickLabelFont(PLOT_FONT);	// 设置坐标轴标尺值字体
		plot.getRangeAxis().setLabelFont(PLOT_FONT);		// 设置纵轴字体
		plot.setBackgroundPaint(Color.WHITE);	// 设置绘图区背景色
		plot.setRangeGridlinePaint(Color.RED);	// 设置水平方向背景线颜色
		plot.setRangeGridlinesVisible(true);	// 设置是否显示水平方向背景线,默认值为true
		plot.setDomainGridlinePaint(Color.RED);	// 设置垂直方向背景线颜色
		plot.setDomainGridlinesVisible(true);	// 设置是否显示垂直方向背景线,默认值为false
		// 获取折线对象
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
                    .getRenderer();
		BasicStroke realLine = new BasicStroke(1.6f);	// 设置实线
		float dashes[] = { 8.0f }; // 定义虚线数组
		BasicStroke brokenLine = new BasicStroke(1.6f, 	// 线条粗细
				BasicStroke.CAP_SQUARE, // 端点风格
				BasicStroke.JOIN_MITER, // 折点风格
				8.f, 	// 折点处理办法
				dashes, // 虚线数组
				0.0f); 	// 虚线偏移量
		renderer.setSeriesStroke(1, brokenLine); // 利用虚线绘制
		renderer.setSeriesStroke(2, brokenLine); // 利用虚线绘制
		renderer.setSeriesStroke(3, realLine); 	 // 利用实线绘制
		return chart;
	}
}
