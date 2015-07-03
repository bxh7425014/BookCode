package com.supermario.saxxml;

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
import javax.xml.parsers.SAXParserFactory;  
import org.xml.sax.InputSource;  
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SaxXMLActivity extends Activity {  

	//新建一个按键
	private Button button;  
	//新建一个列表
	private ListView listView;
	//新建一个数组列表用于存放字符串数组
	private ArrayList<String> list=new ArrayList<String>();  
public void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.main); 
    button=(Button)findViewById(R.id.btn1);  
    listView=(ListView) findViewById(R.id.listView1);
    //为按键绑定监听器
    button.setOnClickListener(new ButtonListener());  
}  

class ButtonListener implements OnClickListener{  

    @Override  
    public void onClick(View v) {  
	  //将解析后的结果存储到students中   
	   List<Student> students=parserXMl();
	   //枚举数组中的元素
	   for (Iterator iterator = students.iterator(); iterator.hasNext();) {  
		   Student student = (Student) iterator.next();  
		   //将类的内容转换成字符串，依次存储到list中
		   list.add(String.valueOf(student.getId())+" "+student.getName()+" "+student.getSpeciality()+" "+String.valueOf((student.getQQ())));  
	}  
	   //新建一个适配器daapter用于给listview提供数据
	   ArrayAdapter<String> adapter=new ArrayAdapter<String>(SaxXMLActivity.this, android.R.layout.simple_list_item_1, list);  
	  //为listview绑定适配器
	   listView.setAdapter(adapter);  
    }  
   
      
}  
  //解析xml文件
private List<Student> parserXMl()  
{  
	//实例化一个SAX解析工厂
    SAXParserFactory factory=SAXParserFactory.newInstance();  
    List<Student>students=null;   
    try {
    //获取xml解析器
	XMLReader reader=factory.newSAXParser().getXMLReader();  
	students=new ArrayList<Student>();  
	reader.setContentHandler(new StudentHandler(students));  
	//解析Assets下的student.xml文件
	reader.parse(new InputSource(SaxXMLActivity.this.getResources().getAssets().open("student.xml"))); 
    } catch (Exception e) {  
	// TODO: handle exception   
    }
    return students;  
}  
}  