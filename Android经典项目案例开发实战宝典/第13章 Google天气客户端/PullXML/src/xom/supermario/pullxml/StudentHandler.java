package xom.supermario.pullxml;

import java.util.List;   
import org.xml.sax.Attributes;  
import org.xml.sax.SAXException;  
import org.xml.sax.helpers.DefaultHandler;  
import android.util.Log;
  
public class StudentHandler extends DefaultHandler {  
    private String preTAG;    //用于存储xml节点的名称
    private List<Student> ListStudent;  
    private Student stu;  
    //无参数实例化类
    public StudentHandler() {  
        super();  
    }
    //带参数实例化类
    public StudentHandler(List<Student> listStudent) {  
        super();  
        ListStudent = listStudent;  
    }
    //开始解析文档
    public void startDocument() throws SAXException {  
        // TODO Auto-generated method stub   
    Log.i("------>", "文档开始");  
        super.startDocument();  
    }
    //开始解析文档的元素
    public void startElement(String uri, String localName, String qName,  
            Attributes attributes) throws SAXException {  
        Log.i("localName-------->", localName);  
        preTAG=localName;  //将当前元素的名称保存到preTAG
        if ("student".equals(localName)) {  
            stu=new Student();  //实例化一个student类
            //将ID信息保存到stu中
            stu.setId(Long.parseLong(attributes.getValue(0)));  
              
        for (int i = 0; i < attributes.getLength(); i++) {    
            Log.i("attributes-------->",String.valueOf(stu.getId()));  
        	}  
        }  
        //这句话记得要执行
        super.startElement(uri, localName, qName, attributes);  
    }  
  
    public void endDocument() throws SAXException {  
      
        Log.i("------>", "文档结束");  
        super.endDocument();  
    }  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        preTAG="";  
        if ("student".equals(localName)) {  
        ListStudent.add(stu);  
        Log.i("-------->", "一个元素解析完成");  
        }  
        super.endElement(uri, localName, qName);  
    }     
    //解析节点文本内容
    public void characters(char[] ch, int start, int length)  
        throws SAXException {  
      
        String str; 
        //找出元素中的“name”节点
       if ("name".equals(preTAG)) {  
    	   str=new String(ch,start,length);  
            stu.setName(str);  
            Log.i("name=", stu.getName());  
        //找出元素中的“speciality”节点
        }else if ("speciality".equals(preTAG)) {  
        	str=new String(ch,start,length);  
            stu.setSpeciality(str);  
            Log.i("speciality=", stu.getSpeciality());
        //找出元素中的“qq”节点
        }else if ("qq".equals(preTAG)) {  
        	str=new String(ch,start,length);  
            stu.setQQ(Long.parseLong((str)));  
            Log.i("QQ=", String.valueOf(stu.getQQ()));  
        } 
        super.characters(ch, start, length);  
    }                
	public List<Student> getListStudent() {  
	    return ListStudent;  
	}

	public void setListStudent(List<Student> listStudent) {  
	    ListStudent = listStudent;  
	}   
}  