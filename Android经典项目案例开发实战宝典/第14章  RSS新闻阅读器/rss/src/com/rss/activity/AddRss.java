package com.rss.activity;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import com.rss.data.ChannelDataHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//RSS源添加界面
public class AddRss extends Activity {
	//添加按钮
	private Button addRss;
	//检验按钮
	private Button verifyRss;
	//退出按钮
	private Button quit;
	//RSS源地址
	private EditText rssText;
	//RSS源数据库操作类
	ChannelDataHelper mChannelData;
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_rss_dialog);
		mContext=this;
		//实例化RSS源数据库操作类
		mChannelData=new ChannelDataHelper(this);
		//初始化界面元素
		addRss = (Button)findViewById(R.id.add_rss);
		verifyRss = (Button)findViewById(R.id.verify_rss);
		quit = (Button)findViewById(R.id.quit_rss);
		rssText = (EditText)findViewById(R.id.rss_add);
		//设置按键监听器
		addRss.setOnClickListener(clickListener);
		verifyRss.setOnClickListener(clickListener);
		quit.setOnClickListener(clickListener);
	}
	//按键监听器
	OnClickListener clickListener = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//添加按钮
			case R.id.add_rss:
				saveRss();
				break;
			//检验地址
			case R.id.verify_rss:
				if(verifyRss(rssText.getText().toString()) != null 
						&& verifyRss(rssText.getText().toString()) != "默认")
					Toast.makeText(mContext, "验证通过", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(mContext, "验证失败", Toast.LENGTH_SHORT).show();
				break;
			//退出添加界面
			case R.id.quit_rss:
				Intent it=new Intent();
				it.setClass(AddRss.this, SelectChannel.class);
				//进入RSS源选择界面
				startActivity(it);
				AddRss.this.finish();
				break;
			default:
				break;
			}
			
		}
	};
	//保存RSS源
	protected void saveRss() {
		String rssAddress = rssText.getText().toString().trim();
		String rssName= verifyRss(rssAddress);	
		//RSS源地址正确并正确保存
		if(rssName != null && rssName != "默认" && (-1 != mChannelData.SaveChannelInfo(rssName, rssAddress)))
		{
			Toast.makeText(mContext, "保存成功!", Toast.LENGTH_SHORT).show();
			rssText.setText("");
		}else{
			Toast.makeText(mContext, "保存失败！", Toast.LENGTH_SHORT).show();
		}
	}
	//验证RSS源地址的正确性，返回RSS源的标题
    private String verifyRss(String urlString) {
    	try {
    		URL url = new URL(urlString);
    		//实例化SAX解析工厂类
    		SAXParserFactory factory = SAXParserFactory.newInstance();
    		//SAX解析器
    		SAXParser parser = factory.newSAXParser();
    		XMLReader xmlReader = parser.getXMLReader();
    		//SAX处理类
    		getRssChannel rssHandler = new getRssChannel();
    		xmlReader.setContentHandler(rssHandler);
    		//取得url网址内容
    		InputSource is = new InputSource(url.openStream());
    		//解析url网址内容
    		xmlReader.parse(is); 		
    		Log.e("channelName",rssHandler.getChannel());
    		return rssHandler.getChannel();
    	}catch (Exception e) {
    		Log.e("channnelName",e.toString());
			return null;
		}
    }
    class getRssChannel extends DefaultHandler {
    	//标志位，用于表示RSS源的位置
    	int flag=1;
    	String channel="默认";
    	public getRssChannel() { 		
    	} 	
    	//返回channel信息
    	public String getChannel()
    	{
    		return channel;
    	}
    	//文档开始
    	@Override
    	public void startDocument()throws SAXException {
    	}
    	//文档结尾
    	@Override
    	public void endDocument() throws SAXException {
    		super.endDocument();
    	}
    	//开始解析标签
    	@Override
    	public void startElement(String uri, String localName, String qName,
    			Attributes attributes) throws SAXException {
    		super.startElement(uri, localName, qName, attributes);
    		if(localName.equals("channel")) {
    			flag = 1;
    			return;
    		}   	
    		//一旦进入了item，则将标志为设置为0
    		if(localName.equals("item")) {
    			flag=0;
    			return;
    		}
    		//在item外面遇到title标签将标志位设置为2
    		if(flag == 1)
    		{
    			if(localName.equals("title"))
    			{
    				flag = 2;
    			}
    		}
    	}
    	//标签结束
    	@Override
    	public void endElement(String uri, String localName, String qName)	
    			throws SAXException {    		
    	}
    	//解析标签内容
    	@Override
    	public void characters(char[] ch, int start, int length)
    			throws SAXException {
    		if(length <= 5) return ;
    		String theString = new String(ch, start, length);
    		//当标志位为2表明当前正在 解析RSS源标题的标签
    		if(flag == 2)
    		{
    			//保存RSS源标题
    			Log.e("channel",theString);
    			channel=theString;
    			flag =0;
    		}   		
    	}
    }
}