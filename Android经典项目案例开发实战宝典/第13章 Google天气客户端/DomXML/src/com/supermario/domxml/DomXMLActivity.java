package com.supermario.domxml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DomXMLActivity extends Activity {
	//新建一个按钮用于响应用户按键
	private Button start;
	//新建一个TextView用于存放结果
	private TextView show;
	//Assets中的xml文件名称
	private String fileName="fruit.xml";
    InputStream inStream=null;
    /** 首次创建界面时运行*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        show=(TextView)findViewById(R.id.show);
        start=(Button)findViewById(R.id.start);

		try {
			//从Assets中获取文件
			inStream = this.getAssets().open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//为按键绑定事件
        start.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//用于存放结果字符串
				String result="";
				//解析字符流
				result=parse(inStream);
				//将结果显示到界面上
				show.setText(result);
			}	
        });
    }
    //解析字符流
    public String parse(InputStream inStream)
    {
    	String result="";
    	//实例化一个DocumentBuilderFactory类
    	DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder=null;
    	Document doc=null;
    	try {
    		//实例化一个DocumentBuilder用于解析字符流
			builder=dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//解析字符流
			doc=builder.parse(inStream);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element ele=doc.getDocumentElement();
		//获取所有的“fruit”节点
		NodeList nl=ele.getElementsByTagName("fruit");
		if(nl != null && nl.getLength() != 0)
		{
			for(int i=0;i<nl.getLength();i++)
			{
				Element entry=(Element)nl.item(i);
				//用于获取属性
				result += "name:"+entry.getAttribute("name")+"-->";
				//用于获取文本内容
				result += entry.getTextContent()+"\n";
			}
		}
    	return result;
    }
}