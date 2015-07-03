package com.supermario.stocker;

import java.lang.System;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;
//股票数据处理类
public class DataHandler {
	private static final String TAG="DataHandler";
	//股票查询网址
	private static final String QUERY_URL = "http://hq.sinajs.cn/list=";
	//股票K线图
	private static final String QUERY_IMG ="http://image.sinajs.cn/newchart/daily/n/";
	//存储股票代码的文件
	private static final String SYMBOL_FILE_NAME = "symbols.txt";
	private int BUF_SIZE = 16384;
	//存储股票代码的数组
	private ArrayList<String> stocks;
	//存储股票数据的数组
	private ArrayList<StockInfo> stockInfo=new ArrayList<StockInfo>();
	//各个股票信息在数组中的索引
	private final int NAME=0;
	private final int OPENING_PRICE=1;
	private final int CLOSING_PRICE=2;
	private final int CURRENT_PRICE=3;
	private final int MAX_PRICE=4;
	private final int MIN_PRICE=5;
	Context context;
	public DataHandler(Context mContext){
		super();
		//读取存储在文件中的股票代码信息
		this.readStockFromFile();
		context=mContext;
		if(stocks != null)
		{
			//更新股票数据
			refreshStocks();
		}
	}

	//读取存储在文件中的股票代码信息
	private void readStockFromFile(){
		File fullPath;
		if(stocks == null) 
		{
			//初始化股票代码数组
			stocks = new ArrayList<String>();
		}
		FileInputStream inStream;
		BufferedReader bReader;
		String quoteStr="";
		//获取存储股票的文件
		fullPath = new File("/data/data/com.supermario.stocker/files/symbols.txt");
			//读取文件
		try {
			inStream = new FileInputStream(fullPath);
			bReader = new BufferedReader(new InputStreamReader(inStream));			
			quoteStr = bReader.readLine();
			bReader.close();
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//如果文件中不包含股票代码数据
		if(quoteStr == "" || quoteStr == null)
		{
			//数组清空
			stocks.clear();
			return;
		}
		//将字符串切割成数组
		String strArray[] = quoteStr.split(",");
		int index, count = strArray.length;
		//重置数组
		stocks.clear();
		//将数组存储到数组中
		for(index = 0; index < count; index++) 
			stocks.add(strArray[index]);
	}
	//添加股票代码到文件中
	public synchronized void addSymbolsToFile(ArrayList<String> stockList){
		if(stockList != null){
			//如果股票数组中没有数据
			if(stocks == null || stocks.size() == 0){ 
				//直接将参数添加到股数组中
				stocks = new ArrayList<String>();
				stocks.addAll(stockList);
			} else {
				int i1, i2;
				//文件中股票数组大小
				int c1 = stocks.size();
				//欲添加的股票数组大小
				int c2 = stockList.size();
				ArrayList<String> newStocks = new ArrayList<String>();
				//判断欲添加的股票代码是否在原来的数组中
				boolean foundSymbol = false;
				//循环遍历，查找出新股票
				for(i2 = 0; i2 < c2; i2++){
					String newSymbol = stockList.get(i2);
					for(i1 = 0; i1 < c1; i1++){
						if(newSymbol.equals(stocks.get(i1))){
							foundSymbol = true;
							break;
						}
					}
					//若为新股票，则添加进新股票数组中
					if(!foundSymbol){
						newStocks.add(newSymbol);
					}
				}
				if(newStocks.size() > 0){
					__addQuotes(newStocks);
				}		
			}
			//保存股票
			savePortfolio();
		}
	}
	//根据股票代码数组添加股票
	protected void __addQuotes(ArrayList<String> stockSymbols){
		if(stockSymbols != null && stockSymbols.size() > 0){
			int index, count = stockSymbols.size();
			//取得http客户端实例
			HttpClient req = new DefaultHttpClient();
			//用于存放网址
			StringBuffer buf = new StringBuffer();
			//构建网址
			buf.append(QUERY_URL);
			buf.append(stockSymbols.get(0));
			//如果股票数超过1
			for(index = 1; index < count; index++){
				buf.append(",");	
				buf.append(stockSymbols.get(index));
			}
			try {
				//采用get方式获取网页数据
				HttpGet httpGet = new HttpGet(buf.toString());
				HttpResponse response = req.execute(httpGet);
				InputStream iStream = response.getEntity().getContent();
				//解析网页数据，若解析成功则添加股票
				if(parseQuotesFromStream(iStream))
				{
					for(index = 0; index < count; index++){
						stocks.add(stockSymbols.get(index));
					}
					Toast.makeText(context, "股票添加成功！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, "股票添加失败！", Toast.LENGTH_SHORT).show();
				}
			} catch (IOException e){
				Log.e(TAG, e.getMessage());
			}
		}
	}
	//将股票添加进文件中
	private void savePortfolio(){
		//将当前stocks中的值重新写入文件中
		if(stocks.size() > 0){
			FileOutputStream outStream = null;
			OutputStreamWriter oWriter;
			try {
				//打开文件
				outStream = context.openFileOutput(SYMBOL_FILE_NAME, Context.MODE_PRIVATE);
				oWriter = new OutputStreamWriter(outStream); 
				StringBuffer buf = new StringBuffer();
				int index, count = stocks.size();
				//构建字符串写入文件中
				buf.append(stocks.get(0));
				for(index = 1; index < count; index++){
					buf.append(",");
					buf.append(stocks.get(index));
				}
				String outStr = buf.toString();
				oWriter.write(outStr, 0, outStr.length());
				oWriter.close();
				outStream.close();
			} catch(IOException e){
				Log.e(TAG, e.getMessage());
			}
		}
	}
	//解析股票数据
	private boolean parseQuotesFromStream(InputStream aStream){
		boolean flag=false;
		if(aStream != null){
			stockInfo.clear();
			//读取数据
			BufferedInputStream iStream = new BufferedInputStream(aStream);
			InputStreamReader iReader=null;
			try {
				//使用GBK方式解析数据
				iReader = new InputStreamReader(iStream,"GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer strBuf = new StringBuffer();
			char buf[] = new char[BUF_SIZE];
			try {
				int charsRead;
				//将数据读取到StringBuffer中
				while((charsRead = iReader.read(buf, 0, buf.length)) != -1){
					strBuf.append(buf, 0, charsRead);
				}
				//匹配股票数据
				Pattern pattern=Pattern.compile("str_(.+)=\"(.+)\"");
				Matcher matcher=pattern.matcher(strBuf);
				while(matcher.find()){      
					//股票信息为第二个括号中对应的内容
		    		String result=matcher.group(2);
		    		String[] data=result.split(",");
		    		StockInfo mStockInfo=new StockInfo();
		    		//存储股票的代码
		    		mStockInfo.setNo(matcher.group(1));
		    		//存储股票名字
		    		mStockInfo.setName(data[NAME]);
		    		//存储股票今日开盘价
		    		mStockInfo.setOpening_price(data[OPENING_PRICE]);
		    		//存储股票昨日收盘价
		    		mStockInfo.setClosing_price(data[CLOSING_PRICE]);
		    		//存储股票当前价格
		    		mStockInfo.setCurrent_price(Double.parseDouble(data[CURRENT_PRICE])+0.01*(int)(10*Math.random())+"");
		    		//存储股票今日最高价格
		    		mStockInfo.setMax_price(data[MAX_PRICE]);
		    		//存储股票今日最低价格
		    		mStockInfo.setMin_price(data[MIN_PRICE]);		
		    		//将数据存储到数组中
		    		stockInfo.add(mStockInfo);
		    		flag=true;
		    	}
			}catch(IOException iox){
				Log.e(TAG, iox.getMessage());
			}
		}
		return flag;
	}
	//取得存储股票数据的数组
	protected ArrayList<StockInfo> getQuotesForArray(ArrayList<String> stockSymbols){
		if(stockSymbols != null && stockSymbols.size() > 0){
			//取得http客户端实例
			HttpClient req = new DefaultHttpClient();
			//用于存放网址
			StringBuffer buf = new StringBuffer();
			int index;
			//取得股票的总数
			int count = stockSymbols.size();
			//构建网址
			buf.append(QUERY_URL);
			buf.append(stockSymbols.get(0));
			//如果股票数超过1
			for(index = 1; index < count; index++){
				buf.append(",");	
				buf.append(stockSymbols.get(index));
			}
			try {
				//采用get方式获取网页数据
				HttpGet httpGet = new HttpGet(buf.toString());
				HttpResponse response = req.execute(httpGet);
				InputStream iStream = response.getEntity().getContent();
				//解析网页数据
				parseQuotesFromStream(iStream);
				//返回股票数据
				return stockInfo;
			} catch (IOException e){
				Log.e(TAG, e.getMessage());
			}
			return null;
		}
		return null;
	}
	//取得K线图
	public Bitmap getChartForSymbol(String symbol){
		try {	
			try {
				//构建股票K线图网址
				StringBuilder sb = new StringBuilder(QUERY_IMG);
				sb = sb.append(symbol+".gif");
				HttpClient req = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(sb.toString());
				//访问执行网址
				HttpResponse response = req.execute(httpGet);
				InputStream iStream;
				BitmapDrawable bitMap;
				//取得网址返回的内容
				iStream = response.getEntity().getContent();
				//将返回的内容解析成图片
				bitMap = new BitmapDrawable(iStream);
				iStream.close();
				iStream = null;
				return bitMap.getBitmap();
			} catch ( IOException iox ){
				Log.d(TAG, iox.getMessage());	
			}
		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
		}
		return null;
	}
	//更新股票数据
	public  void refreshStocks(){
		long startTime = System.currentTimeMillis();
		long endTime;
		//取得股票的最新数据
		getQuotesForArray(stocks);
		endTime = System.currentTimeMillis();
		Log.d(TAG, "Refresh ran for " + (endTime - startTime) + " millisenconds");
	}
	//返回股票数组大小
	public  int stocksSize(){
		if(stocks != null)
			return stocks.size();
		return 0;
	}
	//通过股票的索引删除股票
	public  void removeQuoteByIndex(int index){
		stocks.remove(index);
		//保存当前股票
		savePortfolio();
	}
	//返回指定位置的数组元素
	public  StockInfo getQuoteForIndex(int index){
		return stockInfo.get(index);
	}
}