package com.supermario.filemanager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.http.protocol.HTTP;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;
public class WebActivity extends Activity {
	//网页浏览器
	private WebView webView;
	//进度条布局和网页内容主体布局
	private RelativeLayout loadingLayout,webLayout;
	//放大缩小控制器
	private ZoomControls zoomControls;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		//初始化页面组件
		webView = (WebView)findViewById(R.id.webkit);
		loadingLayout = (RelativeLayout)findViewById(R.id.loadingLayout);
		webLayout = (RelativeLayout)findViewById(R.id.weblayout);
		zoomControls = (ZoomControls)findViewById(R.id.zoomControls);	
		WebSettings webSettings = webView.getSettings();
		//设置可以使用js脚本
		webSettings.setJavaScriptEnabled(true);
		//执行异步进程
		new MyAsyncTask().execute("");		
	}
	private void reading(){
		String filePath = getIntent().getStringExtra("filePath");
		if (filePath != null) {
			//读取文件
			webView.loadData(readWebDataToStringFromPath(filePath, new FileReadOverBack() {
				@Override
				public void fileReadOver() {
				}
			}), "text/html", HTTP.UTF_8);
		} else {
			new AlertDialog.Builder(WebActivity.this).setTitle("出错了").setMessage("获取文件路径出错!").setPositiveButton("返回", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					WebActivity.this.finish();
				}
			});
		}
	}
	//将网页数据读取到一个字符串变量中
	private String readWebDataToStringFromPath(String path,final FileReadOverBack fileReadOverBack){
		File file = new File(path);
		StringBuffer stringBuffer = new StringBuffer();
		try {
			//读取文件内容
			FileInputStream inputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int readCount = 0;
			while ((readCount = inputStream.read(bytes)) > 0) {
				stringBuffer.append(new String(bytes, 0, readCount));
			}
			fileReadOverBack.fileReadOver();
		} catch (FileNotFoundException e) {
			return "文件不存在!";
		} catch (IOException e) {
			return "文件读取错误!";
		}
		return stringBuffer.toString();
	}	
	interface FileReadOverBack{
		void fileReadOver();
	}
	//异步处理类
	class MyAsyncTask extends AsyncTask<String, String, String>{
		//首先执行的函数
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingLayout.setVisibility(View.VISIBLE);
			webLayout.setVisibility(View.GONE);
		}
		//后台执行
		@Override
		protected String doInBackground(String... params) {
			reading();
			return null;
		}	
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//设置载入进度条隐藏
			loadingLayout.setVisibility(View.GONE);
			//设置浏览器内容可见
			webLayout.setVisibility(View.VISIBLE);			
			// 放大按钮
			zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
				//将网页内容放大
				@Override
				public void onClick(View v) {
					webView.zoomIn();
				}
			});
			// 缩小按钮
			zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {		
				//将网页内容缩小
				@Override
				public void onClick(View v) {
					webView.zoomOut();
				}
			});
		}		
	}
}
