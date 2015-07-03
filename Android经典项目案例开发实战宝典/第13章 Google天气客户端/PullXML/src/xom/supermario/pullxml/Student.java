package xom.supermario.pullxml;

public class Student {  
	long Id;  //用于存放id信息
	String Name;  //用于存放Name信息
	String Speciality;  //用于存放专业信息
	long QQ;  	//用于存放QQ信息
	//带参数构造函数，用于初始化类
	public Student(long id, String name, String speciality, long qQ) {  
	    super();  
	    Id = id;  
	    Name = name;  
	    Speciality = speciality;  
	    QQ = qQ;  
	}  
	//不带参数构造函数
	public Student() {  
	    super();  
	}  
	//取得id
	public long getId() {  
	    return Id;  
	} 
	//取得Name
	public String getName() {  
	    return Name;  
	}  
	//取得QQ
	public long getQQ() {  
	    return QQ;  
	}  
	//取得专业信息
	public String getSpeciality() {  
	    return Speciality;  
	}  
	//设置id
	public void setId(long id) {  
	    Id = id;  
	}  
	//设置姓名
	public void setName(String name) {  
	    Name = name;  
	}  
	//设置QQ
	public void setQQ(long qQ) {  
	    QQ = qQ;  
	}  
	//设置专业
	public void setSpeciality(String speciality) {  
	    Speciality = speciality;  
	}  
	}  