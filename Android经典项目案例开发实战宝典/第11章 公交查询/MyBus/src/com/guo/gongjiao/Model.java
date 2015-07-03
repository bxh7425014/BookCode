package com.guo.gongjiao;

//import java.util.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

//import java.lang.*;

public class Model implements Runnable {

	// 这个hash表存储信息格式如下:
	// line name => line stations eg.
	// 1 => a-b-c-d-e-
	final  Hashtable<String, String> road = new Hashtable<String, String>();
	// 这个hash表存储信息格式如下：
	// line name => line time eg.
	// 1 =>{8:00 12:00} 起始站 {8:00 12:00} 终点站
	final  Hashtable<String, String> road_time = new Hashtable<String, String>();
	public final static int MAX_NUM = 50;

	private String file;
	private Traffic parent;

	public Model(Traffic parent, String file) {
		this.parent = parent;
		this.file = file;
		return;
	}

	@Override
	public void run() {
		initRoadHash();
		class CallBack implements Runnable {
			public void run() {
				parent.mDialog.dismiss();
			}
		}
		CallBack cb = new CallBack();
		parent.runOnUiThread(cb);
	}
	//根据线路名称获得当前线路所经过的所有站点，以字符串表示
	public String getSationsForOneRoad(String lineName) {
		return (String) (road.get((Object) lineName));

	}
	//取得线路的时间信息
	public String getTimeforOneRoad(String lineName) {
		String time = (String) (road_time.get((Object) lineName));
		//如果找不到，则尝试寻找带“a”字样的
		if (time == null) {
			time = (String) (road_time.get(lineName + "a"));
		}
		return time;
	}
	//根据路线名称获取所有建议路线
	public Vector getSuggestRoads(String s) {
		//用于存放路线集合
		Enumeration key_of_roads;
		String road;
		int i = 0;
		Vector temp = new Vector();	//用于查询存放结果

		String mmm = s.trim();
		key_of_roads = this.road.keys();
		//遍历集合中的元素
		while (key_of_roads.hasMoreElements()) {
			road = (String) key_of_roads.nextElement();
			// 跳过以“b”结尾的路线
			if (road.endsWith("b") == true)
				continue;
			//将开头是指定字符串的所有线路存储到元素temp中
			if (road.startsWith(mmm) == true) {
				if (road.endsWith("a") == true) {
					//去掉线路后面的“a”，只存储线路名称
					temp.addElement((Object) road.substring(0,
							road.length() - 1));
				} else {
					temp.addElement((Object) road.toString());
				}
				i++;
			}
		}

		return temp;
	}

	/**
	 * @param sa
	 *            :the name of road
	 * @raturn a vector array vector[0]is the sequnce of sation for start to
	 *         end,vector[1] is a stack ,whitch is the sequnce of station from
	 *         start to end, you can you popup() to get sequnce from end to
	 *         start
	 */
	//根据线路名称获得完整线路
	public String[] get_stations_for_one_road(String s) {
		//初始化字符串数组，用于存放去程和回程
		String[] vv = new String[2];
		String stations = null;

		s = s.trim();
		if (s.equals(""))
			return null;

		stations = this.getSationsForOneRoad(s);

		if (stations != null) {
			// 说明线路不是以'a' 或者 'b'结尾
			int index;
			String x;
			//新建一个栈用于存放线路经过的所有站点
			Stack down_road = new Stack();

			vv[0] = stations.toString();
			//将所有站依次添加到栈中
			while ((index = stations.indexOf("-")) > 0) {
				x = stations.substring(0, index);
				down_road.addElement(x);
				stations = stations.substring(index + 1);
			}
			
			String st = "";
			//将栈的元素pop，以得到一个以相反顺序显示站点信息的字符串
			while (!down_road.empty()) {
				if (st.trim().equals("")) {
					st = (String) down_road.pop();
				} else {
					st = st + "-" + (String) down_road.pop();
				}
			}
			vv[1] = st;
			return vv;

		} else {
			// 运行到这里说明线路去程和回程的路线不一致
			// 一个以“a”结尾，一个以“b”结尾
			vv[0] = getSationsForOneRoad(s + "a");
			if (vv[0] == null)
				return null;
			vv[1] = getSationsForOneRoad(s + "b");
			if (vv[1] == null)
				return null;
			return vv;
		}

	}

	/*
	 * just like get_stations_for_one_road function but just return all the
	 * stations this road have
	 */
	//不考虑上行下行，获取当前路线的经过的所有站点
	public Vector get_stations_for_one_road_not_care_direction(String s) {
		String stations = null;

		s = s.trim();
		if (s.equals(""))
			return null;
		//取得当前路线的所有站点
		stations = this.getSationsForOneRoad(s);
		//如果没找到，尝试寻找是否有该路线上行方向的路线
		if (stations == null) {
			// go here mean that hashtable has two entry for this road
			// one is end with a,another is end with b
			stations = getSationsForOneRoad(s + "a");

		}
		int index;
		String x;
		//将路线依次存储到down_load
		Vector down_road = new Vector();
		//通过“-”判断每个站点的名称
		while ((index = stations.indexOf("-")) > 0) {
			x = stations.substring(0, index);
			down_road.addElement(x);
			stations = stations.substring(index + 1);
		}
		//返回该路线经过的所有站点的信息
		return down_road;

	}

	/**
	 * check two vector whitch contain string object, check if a string is both
	 * in the first vector and the second vector
	 * 
	 * @param v1
	 *            the vector containning strings
	 * @param v2
	 *            the vector containning strings
	 */
	//检查2个向量中是否包含相同元素，若有则返回相同的元素，否则返回空
	private String is_there_has_a_same_string(Vector v1, Vector v2) {

		Enumeration seq1 = v1.elements();
		Enumeration seq2;
		String s1, s2;
		//遍历seq1的元素
		while (seq1.hasMoreElements()) {
			s1 = (String) seq1.nextElement();
			//这里seq2需要每次都重新初始化，将当前的指向元素重置
			seq2 = v2.elements();
			//遍历seq2
			while (seq2.hasMoreElements()) {
				s2 = (String) seq2.nextElement();
				//若找到相同的元素，则返回相同的元素
				if (s1.compareTo(s2) == 0) {
					return s1;
				}
			}
		}
		return null;
	}
	//获得所有直达方案
	private Vector get_all_the_same_string(Vector v1,Vector v2)
	{
		//用于存放直达的路线
		Vector temp=new Vector();
		Enumeration seq1 = v1.elements();
		Enumeration seq2;
		String s1, s2;
		//遍历seq1的元素
		while (seq1.hasMoreElements()) {
			s1 = (String) seq1.nextElement();
			//这里seq2需要每次都重新初始化，将当前的指向元素重置
			seq2 = v2.elements();
			//遍历seq2
			while (seq2.hasMoreElements()) {
				s2 = (String) seq2.nextElement();
				//若找到相同的元素，则添加该元素
				if (s1.compareTo(s2) == 0) {
					temp.add((Object)s1);
				}
			}
		}
		return temp;
	}
	/**
	 * according the abbriave string ,get a full name of one station
	 * 
	 * @param v1
	 *            the vector containning stations on one road
	 * @param s
	 *            the station abrrave
	 * @return the full name of this station
	 */
	//根据用户输入的名称获得站点的实际名称
	private String get_station_full_name(Vector v1, String s) {
		//获得线路的站点集合
		Enumeration seq1 = v1.elements();

		String s1, s2;
		s = s.trim();
		//遍历元素，判断是否有元素包含指定字符串
		while (seq1.hasMoreElements()) {
			s1 = (String) seq1.nextElement();
			//返回包含指定字符串的元素
			if (s1.indexOf(s) != -1) {
				return s1;
			}

		}
		//否则返回空
		return "";

	}

	/**
	 * @param sa
	 *            :the name of start station
	 * @param sb
	 *            :the name of end station
	 * @raturn a String array Vector[0]is the first road you should take
	 *         Vector[1] is the second road you should take Vector[2] is the
	 *         middle station you should take off from the first road and take
	 *         on the second road,if it's "same" means just need one road
	 */
	//根据起点站和终点站获得乘车路线
	public Vector[] get_road_for_inter_station(String sa, String sb) {
		//用于存放首先要乘坐的路线
		Vector road1 = new Vector();
		//用于存放最后要乘坐的路线
		Vector road2 = new Vector();
		//用于存放中间要乘坐的路线
		Vector road3 = new Vector();
		Vector[] vv = new Vector[5];
		Vector sta1 = new Vector();
		Vector sta2 = new Vector();
		String x = null;
		Enumeration  direct;
		Vector direct_vector=new Vector();
		int index;
		int j = 0;
		//如果起始站与终点站相同，直接返回空
		if (sa.trim().equals("") || sa.trim().equals("")) {
			return null;
		}
		vv[0] = road1;
		vv[1] = road2;
		vv[2] = road3;
		vv[3] = sta1;
		vv[4] = sta2;
		// 取得包含起始站的所有路线信息
		Vector tmpa = this.get_road_from_station_key(sa);

		// 取得包含终点站的所有路线信息
		Vector tmpb = this.get_road_from_station_key(sb);
		//取得所有直达路线保存到direct_vector中
		direct_vector = this.get_all_the_same_string(tmpa, tmpb);
		if(direct_vector.size() > 0)
		{
			//将向量direct_vector中的元素取出放到direct中
			direct=direct_vector.elements();
			//遍历查询direct中的元素
			while(direct.hasMoreElements())
			{
				String a=(String)direct.nextElement();
				road1.addElement((Object) a.toString());
				//如果满足条件，则在road3中添加元素“same”表明是直达路线
				road3.addElement((Object) "same");
			}
			return vv;
		}
		//否则表明需要转乘，seq1存储所有包含经过起始站的公交路线
		Enumeration seq1 = tmpa.elements();
		//seq2存储所有包含经过终点站的公交路线
		Enumeration seq2;
		// s1 the first road ,s2:the second road
		String s1, s2;
		Vector stations1, stations2;
		while (seq1.hasMoreElements()) {
			//依次获取seq1的每个公交路线
			s1 = (String) seq1.nextElement();
			seq2 = tmpb.elements();
			//检查是否有s1站点到终点站的直达方案，也就是转乘方案
			while (seq2.hasMoreElements()) {
				s2 = (String) seq2.nextElement();
				stations1 = get_stations_for_one_road_not_care_direction(s1);
				stations2 = get_stations_for_one_road_not_care_direction(s2);
				//判断这2个向量是否有交集，有则说明有转乘方案
				x = is_there_has_a_same_string(stations1, stations2);
				if (x != null) {
					j++;
					//最多返回5种转乘方案供选择
					if (j > 3) { 
						return vv;
					}
					//将此时的乘车方案存储起来
					road1.addElement((Object) s1.toString());//road1存储起始线路
					road2.addElement((Object) s2.toString());//road2存储转乘线路
					road3.addElement((Object) x);//road3存储中转站名称
					sta1.addElement((Object) get_station_full_name(stations1,
							sa.trim()));//sta1存储起始站点的全名，若果有的话
					sta2.addElement((Object) get_station_full_name(stations2,
							sb.trim()));//sta2存储终点站的全名，如果有的话
				}
			}

		}
		//如果raod3的元素大于一个，表明有到达方案，包括转乘和直达的
		if (road3.size() > 0) {
			return vv;
		} else {
			return null;
		}
	}

	private static final int MAX_STATION = 20;
	//根据站点获得经过此站点的所有路线
	public Vector get_road_from_station_key(String s) {
		//用于存放路线名称
		Enumeration key_of_roads;
		String roads;
		int i = 0;
		//用于存放包含指定站点的路线名称
		Vector temp = new Vector();

		String mmm = s.trim();
		//取得线路名称的集合
		key_of_roads = this.road.keys();
		//遍历集合
		while (key_of_roads.hasMoreElements()) {
			roads = (String) key_of_roads.nextElement();
			//如果是下行线路则跳过
			if (roads.endsWith("b") == true)
				continue;
			//如果包含执行线路则存储到temp中
			if (getSationsForOneRoad(roads).indexOf(mmm) != -1) {
				if (roads.endsWith("a") == true) {
					//只存储路线名称，不包含上行下行信息
					temp.addElement((Object) roads.substring(0,
							roads.length() - 1));
				} else {
					temp.addElement((Object) roads.toString());
				}
				i++;
			}
		}
		return temp;
	}

	//初始化特定城市的公交线路信息
	private void initRoadHash() {
		try {
			String b = null;
			String name = null; //用于存放公交线路名，如“121路”
			String line = null;	//用于存放公交路线
			String time = "";	//用于存放时间等其他信息
			String encode = CharacterEnding.getFileEncode(new FileInputStream(file));//获得文件的编码方式
			Log.v(Globals.TAG, "encode is:" + encode);
			if(encode.equals("GB18030"))
				encode = "GBK";
			Log.v(Globals.TAG, "1");
			//将InputStreamReader包装成Bufferedreader
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encode));
			int i=0;
			Log.v(Globals.TAG, "2");
			//每次读取一行
			while ((b = buf.readLine()) != null) {
				// System.out.println(b);
				Log.v(Globals.TAG, ""+i);
				b.trim();
				//若得到的数据为空，继续读取下一条
				if (b.length() == 0) {
					continue;
				}
				//如果字符串以“.”开头，表明这是一条注释，继续读取下一条
				if (b.startsWith(".")) {
					continue;
				}
				//若不包含空格，则继续读取下一条
				final int spaceIndex = b.indexOf(' ');
				if (spaceIndex == -1)
					continue;
					
				// 线路名为开始到第一个空格处之间的字符串
				name = b.substring(0, spaceIndex);
				if (name.trim().length() == 0)
					continue;
				//↑ 换成 a，↓ 换乘 b，用于表示公交线路上行下行
				name = name.replace(Character.toChars(8593)[0], 'a');
				name = name.replace(Character.toChars(8595)[0], 'b');

				// 如果没有“：”或者“：”的位置不对，则继续读取下一条
				final int colonIndex = b.indexOf(':');
//				Log.v(Globals.TAG, "line" + colonIndex);
				if (colonIndex == -1 || spaceIndex > colonIndex)
					continue;
				//线路为第一个空格到“：”之间的字符串
				line = b.substring(spaceIndex + 1, colonIndex);
				if (line.trim().length() == 0)
					continue;
				line = line + "-";// 添加一个“-”，方便其他函数的功能实现


				time = b.substring(colonIndex + 1, b.length());
				//将路线站点信息放到road
				road.put(name, line);
				//将线路时间信息放到road_time中
				road_time.put(name, time);
				i++;
				//若行数超过0xFFF即4096条则退出
				if(i > 0xfff)
					break;
			}
			//关闭文件
			buf.close();
			Log.v(Globals.TAG, "total count:"+i);
			// road.put("aa","fff-gg-aa-");//路线站点信息采用这样的形式
		} catch (Exception e) {
			Log.v(Globals.TAG, "exception"+e.getMessage());
			e.printStackTrace();
		}
	}

}
