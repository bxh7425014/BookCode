package com.android.supermario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理歌词文件的类
 */
public class LrcProcess {
	//歌词类列表
	private List<LrcContent> LrcList;
	//用于存放每一句歌词
	private LrcContent mLrcContent;
	//初始化函数
	public LrcProcess() {
		//实例化类
		mLrcContent = new LrcContent();
		LrcList = new ArrayList<LrcContent>();
	}
	/**
	 * 读取歌词文件的内容
	 */
	public String readLRC(String song_path) {
		StringBuilder stringBuilder = new StringBuilder();
		//歌词文件与mp3文件在同一目录，并且名字要相同
		File f = new File(song_path.replace(".mp3", ".lrc"));
		try {
			FileInputStream fis = new FileInputStream(f);
			//以utf-8方式解析文字
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				// 替换字符
				s = s.replace("[", "");
				s = s.replace("]", "@");
				// 分离"@"字符
				String splitLrc_data[] = s.split("@");
				if (splitLrc_data.length > 1) {
					mLrcContent.setLrc(splitLrc_data[1]);
					// 处理歌词取得歌曲时间
					int LrcTime = TimeStr(splitLrc_data[0]);
					//设置歌词时间
					mLrcContent.setLrc_time(LrcTime);
					// 添加进列表数组
					LrcList.add(mLrcContent);
					// 创建对象
					mLrcContent = new LrcContent();
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stringBuilder.append("未找到歌词文件");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stringBuilder.append("木有读取到歌词啊！");
		}
		return stringBuilder.toString();
	}
	/**
	 * 解析歌曲时间处理类
	 */
	public int TimeStr(String timeStr) {
		timeStr = timeStr.replace(":", ".");
		timeStr = timeStr.replace(".", "@");
		//取得时间信息存放到数组中
		String timeData[] = timeStr.split("@");

		// 分离出分、秒并转换为整型
		int minute = Integer.parseInt(timeData[0]);
		int second = Integer.parseInt(timeData[1]);
		int millisecond = Integer.parseInt(timeData[2]);

		// 计算上一行与下一行的时间转换为毫秒数
		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
		return currentTime;
	}
	public List<LrcContent> getLrcContent() {
		return LrcList;
	}
	/**
	 * 获得歌词和时间并返回的类
	 */
	public class LrcContent {
		private String Lrc;
		private int Lrc_time;
		//获得歌词
		public String getLrc() {
			return Lrc;
		}
		//设置歌词
		public void setLrc(String lrc) {
			Lrc = lrc;
		}
		//获得歌词对应的时间
		public int getLrc_time() {
			return Lrc_time;
		}
		//设置歌词对应的时间
		public void setLrc_time(int lrc_time) {
			Lrc_time = lrc_time;
		}
	}
}