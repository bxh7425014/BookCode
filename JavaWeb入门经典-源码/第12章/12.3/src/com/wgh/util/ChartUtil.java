package com.wgh.util;

import java.awt.Font;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;
import org.jfree.data.jdbc.JDBCPieDataset;
/**
 * 自定义制图工具类
 */
public class ChartUtil {
	/**
	 * 查询数据库并初始化数据集合
	 * @return PieDataset对象
	 */
	public static PieDataset initPieData() {
		// 数据库驱动
		String driverName = "com.mysql.jdbc.Driver";
		// 数据库连接url
		String url = "jdbc:mysql://localhost:3306/db_database12";
		String user = "root";	// 数据库用户名
		String password = "111";// 数据库密码
		JDBCPieDataset dataset = null;	//数据集合
		try {
			// 通过JDBC创建数据集合
			dataset = new JDBCPieDataset(url, driverName, user, password);
			// SQL语句
			String query = "select category,val from tb_shop";
			// 查询并向数据集合中添加数据
			dataset.executeQuery(query);
			//关闭数据库连接
			dataset.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return dataset;
	}
	/**
	 * 创建饼形图实例
	 * @return JFreeChart对象
	 */
	public static JFreeChart createChart() {
		// 创建3D饼型图表
		JFreeChart chart = ChartFactory.createPieChart3D(
				"XX商城月销量统计", 	// 图表的标题
				initPieData(), 		// 饼形图的数据集对象
				true, 				// 是否显示图例
				true, 				// 是否显示提示文本
				false); 			// 是否生成超链接
		// 设置标题字体
		chart.getTitle().setFont(new Font("隶书",Font.BOLD,25));
		// 设置图例类别字体
		chart.getLegend().setItemFont(new Font("宋体",Font.BOLD,15));
		// 获得绘图区对象
		PiePlot plot = (PiePlot) chart.getPlot(); 
		plot.setForegroundAlpha(0.5f); 	// 设置前景透明度
		// 设置分类标签的字体
		plot.setLabelFont(new Font("宋体",Font.PLAIN,12));
		plot.setCircular(true); // 设置饼形为正圆
		// 设置分类标签的格式
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={2}",
				NumberFormat.getNumberInstance(),
				NumberFormat.getPercentInstance()));
		return chart;
	}
}
