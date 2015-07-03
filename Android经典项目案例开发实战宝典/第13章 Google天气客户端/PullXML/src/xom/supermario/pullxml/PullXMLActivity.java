package xom.supermario.pullxml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class PullXMLActivity extends Activity {
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
	    //	List<Student> students=null;
		   //枚举数组中的元素
		   for (Iterator iterator = students.iterator(); iterator.hasNext();) {  
			   Student student = (Student) iterator.next();  
			   //将类的内容转换成字符串，依次存储到list中
			   list.add(String.valueOf(student.getId())+" "+student.getName()+" "+student.getSpeciality()+" "+String.valueOf((student.getQQ())));  
		}  
		   //新建一个适配器daapter用于给listview提供数据
		   ArrayAdapter<String> adapter=new ArrayAdapter<String>(PullXMLActivity.this, android.R.layout.simple_list_item_1, list);  
		  //为listview绑定适配器
		   listView.setAdapter(adapter);  
	    }  
	   
	      
	}  
	
	  //解析xml文件
	private List<Student> parserXMl()  
	{
		//初始化一个List<student>变量，用于将所有student成员
		List<Student> students=null;
		//初始化一个student变量，用于存储每一个节点的信息
		Student stu=null;
		try{
			//打开资源文件student.xml
			InputStream inputstream=PullXMLActivity.this.getResources().getAssets().open("student.xml");
			//创建XmlParser有两种方式
			//方式一：使用工厂类XmlPullParserFactory
			XmlPullParserFactory pullFactory=XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser=pullFactory.newPullParser();
			//方式二：使用Android提供的实用工具类android.util.Xml
			//XmlPullParser xmlPullParser=Xml.newPullParser();
			//设置输入字节流为inputstream，并设置编码方式为“UTF-8”
			xmlPullParser.setInput(inputstream, "UTF-8");
			//取得事件类型，用于开始解析时的判断
			int eventType=xmlPullParser.getEventType();
			//循环遍历整个文件直到解析完毕
			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				/*打印log显示事件类型
				*START_DOCUMENT:0
				*END_DOCUMENT:1
				*START_TAG:2
				*END_TAG:3
				*TEXT:4
				*/
				Log.e("guojs--->event",eventType+"");
				//用于存储节点名称
				String localName=null;
				switch(eventType)
				{
				case XmlPullParser.START_DOCUMENT:
					//碰到文档开头则实例化students变量，并打印log
					students=new ArrayList<Student>();
					Log.e("guojs","start document!");
					break;
				case XmlPullParser.START_TAG:
				{
					localName=xmlPullParser.getName();
					if ("student".equals(xmlPullParser.getName())) {  
			            stu=new Student();  //实例化一个student类
			            //将ID信息保存到stu中
			            stu.setId(Long.parseLong(xmlPullParser.getAttributeValue(0)));  
			            Log.e("guojs",stu.getId()+"");
					}
					else if(stu != null)
					{
						//声明一个变量用于存储节点文本
						String currentData=null;
						if("name".equals(xmlPullParser.getName()))
						{
							/*注意这里nextText()的使用：当前事件为START_TAG，
							 * 如果接下去是文本，就会返回当前的文本内容；如果下一个事件是END_TAG
							 * 就会返回空字符串；否则抛出一个异常。
							 */
							currentData=xmlPullParser.nextText();
							//存储”name“的信息
							stu.setName(currentData);
						}
						else if("speciality".equals(xmlPullParser.getName()))
						{
							currentData=xmlPullParser.nextText();
							//存储专业信息
							stu.setSpeciality(currentData);
						}else if("qq".equals(xmlPullParser.getName()))
						{
							currentData=xmlPullParser.nextText();
							//存储QQ信息
							stu.setQQ(Long.parseLong(currentData));
						}
					}
				}
				break;
				case XmlPullParser.END_TAG:
				{
					localName=xmlPullParser.getName();
					Log.e("guojs--end tag",localName);
					if("student".equals(localName) && stu != null)
					{
						//将stu添加进students数组列表中
						students.add(stu);
						//设置stu为空
						stu = null;
					}
				}
				break;
				default:
					break;
				}
				//解析下一个事件
				eventType=xmlPullParser.next();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return students;
	}  
}