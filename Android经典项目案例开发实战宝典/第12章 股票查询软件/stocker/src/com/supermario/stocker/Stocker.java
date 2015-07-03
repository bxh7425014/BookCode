package com.supermario.stocker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
//主界面实现类
public class Stocker extends ListActivity implements View.OnClickListener, KeyEvent.Callback {
	//股票数据适配器
	private QuoteAdaptor quoteAdaptor;
	//股票代码输入框
	private EditText symbolText;
	//股票代码输入按钮
	private Button addButton;
	//返回按钮
	private Button cancelButton;
	//删除按钮
	private Button deleteButton;
	//对话框
	private Dialog dialog = null;
	//股票详细信息
	private TextView currentTextView,noTextView, openTextView, closeTextView, dayLowTextView, dayHighTextView;
	//日K线图
	private ImageView chartView;
	//股票数据处理类
	DataHandler mDataHandler;
	//当前Activity实例
	Stocker mContext;
	//当前选中的股票的序号
	int currentSelectedIndex;
	//初始化界面
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		mContext=this;
		//验证当前存放股票代码的文件是否存在
		File mFile =new File("/data/data/com.supermario.stocker/files/symbols.txt");
		if(mFile.exists())
		{
			Log.e("guojs","file exist");
		}else{
			try {
				//新建存放股票代码的文件
				FileOutputStream outputStream=openFileOutput("symbols.txt",MODE_PRIVATE);
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.e("guojs","file no exist");
		}
		//初始化股票代码处理类
		mDataHandler = new DataHandler(mContext);
		//如果adapter数据为空显示的内容
		getListView().setEmptyView(findViewById(R.id.empty));
		quoteAdaptor = new QuoteAdaptor(this, this,mDataHandler);
		//为列表设置适配器
		this.setListAdapter(quoteAdaptor);
		//添加股票按钮
		addButton = (Button) findViewById(R.id.add_symbols_button);
		//设置添加按钮监听器
		addButton.setOnClickListener(this);
		//股票输入文本框
		symbolText= (EditText) findViewById(R.id.stock_symbols);
	}
	//生命周期onCreate->onStart->onResume
	protected void onResume(){
		super.onResume();
		if(quoteAdaptor != null)
		{
			//开始更新界面
			quoteAdaptor.startRefresh();
		}
	}
	//界面不可见时，停止更新
	protected void onStop(){
		super.onStop();
		//停止更新界面
		quoteAdaptor.stopRefresh();
	}
	//列表元素被点击之后触发
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l,v, position, id);
		//取得点击位置的股票
		StockInfo quote = quoteAdaptor.getItem(position);
		//取得当前位置的序号
		currentSelectedIndex=position;
		if(dialog == null){
			dialog = new Dialog(mContext);
			dialog.setContentView(R.layout.quote_detail);
			//删除按钮
			deleteButton = (Button) dialog.findViewById(R.id.delete);
			//设置删除按钮监听器
			deleteButton.setOnClickListener(this);
			//返回主界面按钮
			cancelButton = (Button) dialog.findViewById(R.id.close);
			//设置返回按钮监听器
			cancelButton.setOnClickListener(this);
			//当前股票价格
			currentTextView = (TextView) dialog.findViewById(R.id.current);
			//当前股票编码
			noTextView = (TextView) dialog.findViewById(R.id.no);
			//昨日收盘价
			openTextView = (TextView) dialog.findViewById(R.id.opening_price);
			//今日收盘价
			closeTextView = (TextView) dialog.findViewById(R.id.closing_price);
			//今日最低价
			dayLowTextView = (TextView) dialog.findViewById(R.id.day_low);
			//今日最高价
			dayHighTextView = (TextView) dialog.findViewById(R.id.day_high);
			//股票K线图
			chartView = (ImageView)dialog.findViewById(R.id.chart_view);
		}
		//设置对话框标题
		dialog.setTitle(quote.getName());
		//设置股票当前价格
		double current=Double.parseDouble(quote.getCurrent_price());
		double closing_price=Double.parseDouble(quote.getClosing_price());
		//保留两位小数
		DecimalFormat df=new DecimalFormat("#0.00"); 
		String percent=df.format(((current-closing_price)*100/closing_price))+"%";
		//若股票价格超过昨日收盘价
		if(current > closing_price)
		{
			//设置字体颜色为红色
			currentTextView.setTextColor(0xffEE3B3B);			
		}
		else 
		{
			//设置字体颜色为绿色
			currentTextView.setTextColor(0xff2e8b57);
		}
		//设置TextView内容
		currentTextView.setText(df.format(current)+"  ("+percent+")");
		openTextView.setText(quote.opening_price);
		closeTextView.setText(quote.closing_price);
		dayLowTextView.setText(quote.min_price);
		dayHighTextView.setText(quote.max_price);
		noTextView.setText(quote.no);
		//设置K线图
		chartView.setImageBitmap(mDataHandler.getChartForSymbol(quote.no));
		dialog.show();
	}
	//判断回车键按下时添加股票
	public boolean onKeyUp(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_ENTER){
			//添加秃瓢
			addSymbol();
			return true;
		}
		return false;
	}
	//添加股票代码，以空格或者回车分隔多个股票
	private void addSymbol(){
		//获得文本框的输入内容
		String symbolsStr = symbolText.getText().toString();
		//将回车符替换成空格
		symbolsStr = symbolsStr.replace("\n", " ");
		//以空格分割字符串
		String symbolArray[] = symbolsStr.split(" ");
		int index, count = symbolArray.length;
		ArrayList<String> symbolList = new ArrayList<String>();
		for(index = 0; index < count; index++){
			symbolList.add(symbolArray[index]);
		}
		//将股票代码添加进文件中
		quoteAdaptor.addSymbolsToFile(symbolList);
		//设置文本框为空
		symbolText.setText(null);
	}
	//设置按键回调函数
	public void onClick(View view) {
		if(view == addButton){
			//添加股票到文件中
			addSymbol();
		} else if(view == cancelButton){
			//关闭对话框
			dialog.dismiss();
		} else if(view == deleteButton){
			//删除当前股票
			quoteAdaptor.removeQuoteAtIndex(currentSelectedIndex);
			dialog.dismiss();
		} else if(view.getParent() instanceof RelativeLayout){
			RelativeLayout rl = (RelativeLayout)view.getParent();
			this.onListItemClick(getListView(), rl, rl.getId()-33, rl.getId());
		} else if(view instanceof RelativeLayout){
			this.onListItemClick(getListView(), view, view.getId()-33, view.getId());
		}
	}    
	//股票数据适配器
	public class QuoteAdaptor extends BaseAdapter implements ListAdapter, Runnable {
		//当前显示的最大数量为10
		private static final int DISPLAY_COUNT = 10;
		public DataHandler dataHandler;
		//强制更新标志
		private boolean forceUpdate = false;
		//保存上下文
		Context context;
		//保存Activity实例
		Stocker stocker;
		LayoutInflater inflater;

		QuoteRefreshTask quoteRefreshTask = null;
		int progressInterval;
		//消息处理器
		Handler messageHandler = new Handler();


		public QuoteAdaptor(Stocker aController, Context mContext,DataHandler mdataHandler) {
			//保存当前的上下文和Activity实例
			context = mContext;
			stocker = aController;
			dataHandler = mdataHandler;
		}
		//取得股票数组的大小
		public int getCount() {
			return dataHandler.stocksSize();
		}
		//取得当前位置股票的对象
		public StockInfo getItem(int position) {
			return dataHandler.getQuoteForIndex(position);
		}
		//取得当前的位置
		public long getItemId(int position) {
			return position;
		}
		//生成视图
		public View getView(int position, View convertView, ViewGroup parent) {
			StockInfo quote;
			inflater = LayoutInflater.from(context);
			RelativeLayout cellLayout = (RelativeLayout)inflater.inflate(R.layout.quote_cell, null);
			cellLayout.setMinimumWidth(parent.getWidth());
			int color;
			stocker.setProgress(progressInterval*(position + 1));
			if(position % 2 > 0)
				color = Color.rgb(48,92,131);
			else 
				color = Color.rgb(119,138,170);
			cellLayout.setBackgroundColor(color);
			quote = dataHandler.getQuoteForIndex(position);
			TextView field = (TextView)cellLayout.findViewById(R.id.symbol);
			//设置股票的代码
			field.setText(quote.getNo());
			field.setClickable(true);
			field.setOnClickListener(stocker);

			//股票名字
			field = (TextView)cellLayout.findViewById(R.id.name);
			field.setClickable(true);
			field.setOnClickListener(stocker);
			field.setText(quote.getName());
			
			field = (TextView)cellLayout.findViewById(R.id.current);
			//设置股票当前价格
			double current=Double.parseDouble(quote.getCurrent_price());
			double closing_price=Double.parseDouble(quote.getClosing_price());
			//保留两位小数
			DecimalFormat df=new DecimalFormat("#0.00"); 
			String percent=df.format(((current-closing_price)*100/closing_price))+"%";
			field.setText(df.format(current));
			field.setClickable(true);
			field.setOnClickListener(stocker);		
			
			field = (TextView)cellLayout.findViewById(R.id.percent);
			//若股票价格超过昨日收盘价
			if(current > closing_price)
			{
				//设置字体颜色为红色
				field.setTextColor(0xffEE3B3B);			
			}
			else 
			{
				//设置字体颜色为绿色
				field.setTextColor(0xff2e8b57);
			}
			field.setText(percent);
			cellLayout.setId(position + 33);
			cellLayout.setClickable(true);
			cellLayout.setOnClickListener(stocker);
			return cellLayout;
		}
		//所有元素均可选中
		public boolean areAllItemsSelectable() {
			return true;
		}
		public boolean isSelectable(int arg0) {
			return true;
		}
		//停止更新股票
		public void stopRefresh(){
			quoteRefreshTask.cancelTimer();
			quoteRefreshTask = null;
		}
		//开始更新股票
		public void startRefresh(){
			if(quoteRefreshTask == null)
				quoteRefreshTask = new QuoteRefreshTask(this);
		}
		//更新适配器
		public void refreshQuotes(){
			messageHandler.post(this);		
		}
		//更新适配器内容
		public void run(){
			if(mDataHandler.stocksSize() > 0){
				if(forceUpdate ){
					forceUpdate = false;
					progressInterval = 10000/DISPLAY_COUNT;
					stocker.setProgressBarVisibility(true);
					stocker.setProgress(progressInterval);
					mDataHandler.refreshStocks();
				}
				//通知数据更改
				this.notifyDataSetChanged();
			}
		}
		//添加股票代码到文件中
		public void addSymbolsToFile(ArrayList<String> symbols){
			//强行更新页面数据
			forceUpdate = true;
			//添加股票到文件中
			mDataHandler.addSymbolsToFile(symbols);
			//添加消息到消息队列
			messageHandler.post(this);
		}
		//移除列表中的数据
		public void removeQuoteAtIndex(int index){
			forceUpdate = true;
			mDataHandler.removeQuoteByIndex(index);
			messageHandler.post(this);
		}
		//股票更新定时器
		public class QuoteRefreshTask extends TimerTask {
			QuoteAdaptor quoteAdaptor;
			Timer        refreshTimer;
			final static int  TENSECONDS = 10000;
			public QuoteRefreshTask(QuoteAdaptor anAdaptor){
				refreshTimer = new Timer("Quote Refresh Timer");
				refreshTimer.schedule(this, TENSECONDS, TENSECONDS);
				quoteAdaptor = anAdaptor;
			}

			public void run(){
				messageHandler.post(quoteAdaptor);
			}

			public void startTimer(){
				if(refreshTimer == null){
					refreshTimer = new Timer("Quote Refresh Timer");
					refreshTimer.schedule(this, TENSECONDS, TENSECONDS);
				}
			}
			//取消定时器
			public void cancelTimer(){
				this.cancel();
				refreshTimer.cancel();
				refreshTimer = null;
			}
		}
	}
}