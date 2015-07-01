// NewProject.cpp : implementation file
 

#include "stdafx.h"
 
#include "NewProject.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CNewProject dialog




CNewProject::CNewProject(CWnd* pParent /*=NULL*/)
	: CDialog(CNewProject::IDD, pParent)
{
	//{{AFX_DATA_INIT(CNewProject)
	m_systemname = _T("");
	m_systempassword = _T("");
	m_projectname = _T("");
	m_servername = _T("");
	//}}AFX_DATA_INIT
}


void CNewProject::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CNewProject)
	DDX_Text(pDX, IDC_SYSTEM_NAME, m_systemname);
	DDX_Text(pDX, IDC_SYSTEM_PASSWORD, m_systempassword);
	DDX_Text(pDX, IDC_EDIT_PROJECTNAME, m_projectname);
	DDX_Text(pDX, IDC_SYSTEM_SERVERNAME, m_servername);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CNewProject, CDialog)
	//{{AFX_MSG_MAP(CNewProject)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CNewProject message handlers

BOOL CNewProject::CreateNewProject()
{
	_bstr_t Sql;

	CString strtable[200];//项目中最多可创建的数量

	CString strsql;//用于存储SQL字符串
	CString m_username,m_userpassword;	//存储新创建的用户名和密码

	this->UpdateData();//更新数据

	if(m_projectname.IsEmpty()) //项目名称不能为空
	{
		MessageBox("项目名称不能为空!","新建项目",MB_ICONINFORMATION);
		return FALSE; //返回
	}
	
	m_username=m_projectname;//新创建的Oracle用户名

	//新创建的Oracle用户密码,可任意设定,这里直接给一定默认值,是了为方便,
	//当打开项目时,就不需要输入项目的密码就可以直接打开项目)
	m_userpassword="a"; //新创建的Oracle用户密码

	theApp.m_username=m_username;//Oracle数据库系统管理员用户名
	theApp.m_userpassword=m_userpassword;//Oracle数据库系统管理员密码
	theApp.m_servername=m_servername;//Oracle数据库系统管理员服务器名
	
	if(FAILED(CoInitialize (NULL)))   //初始化ADO
	{
		MessageBox ("ADO初始化失败!","登录数据库",MB_ICONERROR);
		return FALSE;
	}
		
	//创建m_pConnection实例句柄
	HRESULT hr = theApp.m_pConnection.CreateInstance("ADODB.Connection");	
	
	if(theApp.m_pConnection->State) //如果数据库已打开
		theApp.m_pConnection->Close(); //关闭连接
	
	//创建SQL字符串
	strsql="Provider=OraOLEDB.Oracle;Data Source="+m_servername+";User Id="+m_systemname+";Password="+m_systempassword+";";
	try
	{
		hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);//打开Oracle系统管理员用户
		if(!SUCCEEDED(hr)) //打开失败
		{
			MessageBox("系统管理员数据库连接失败!","登录数据库",MB_ICONINFORMATION);
			return FALSE;	//返回
		}
	}
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		errormessage="系统管理员用户名、密码或服务器名错误!";
		MessageBox(errormessage,"登录数据库",MB_ICONINFORMATION);//给出错误诊断信息
		return FALSE;	//返回
	} 
 
    //创建一个新用户， 用于存储所建的项目名称
	CString tname,tpass;
	tname="RW3D";	//设定的用户名称(可任意用户名)
	tpass="aaa";	//设定的用户密码(可任意密码)
	strsql.Format("SELECT * FROM dba_users WHERE  username='%s'",tname);
	theApp.m_pRecordset.CreateInstance(_uuidof(Recordset));

	//从Oracle的dba_users表中选取是否有所设定的用户
	theApp.m_pRecordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	if(theApp.m_pRecordset->adoEOF) //如果没有该用户,则创建
	{
		theApp.m_pRecordset->Close();//关闭表
		strsql = "CREATE USER  " + tname + "  IDENTIFIED BY   " + tpass;
		hr=theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);//创建设定的用户
		
		strsql = "GRANT CREATE SESSION,CREATE TABLE ,UNLIMITED TABLESPACE ,DBA to " + tname;
		hr=theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);//分配创建表等权限
		theApp.m_pConnection->Close();//关闭数据库连接
		
		strsql="Provider=OraOLEDB.Oracle;Data Source="+m_servername+";User Id="+tname+";Password="+tpass+";";
		hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);//连接新创建的用户
		strsql= "CREATE TABLE Project(项目名称 VARCHAR2(50),建立时间 VARCHAR2(20))";
		hr=theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);//在新创建的用户下创建表Project
		theApp.m_pConnection->Close();//关闭数据库连接
	
		//重新连接Oracle系统管理员用户
		strsql="Provider=OraOLEDB.Oracle;Data Source="+m_servername+";User Id="+m_systemname+";Password="+m_systempassword+";";
		hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);
	}
	else //如果存储项目名称的用户已经存在,就不再需要创建了(即只是第一次新建项目时,会创建存储项目名称的用户和数据表)
		theApp.m_pRecordset->Close(); //关闭记录集

	
   //创建项目(根据项目名称创建Oracle新用户)
	strsql = "CREATE USER  " + m_username + "  IDENTIFIED BY   " + m_userpassword;
	try
	{
		hr=theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);//创建Oracle新用户
		if(!SUCCEEDED(hr)) //如果创建失败
		{
			MessageBox("项目创建失败!","创建新项目",MB_ICONINFORMATION);
			return FALSE;	//返回
		}

		//为创建项目的新用户分配创建任务(CREATE SESSION,),创建表(CREATE TABLE),
		//无限制表空间(UNLIMITED TABLESPACE)的权限
		strsql = "GRANT CREATE SESSION,CREATE TABLE ,UNLIMITED TABLESPACE to " + m_username;
		hr=theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);
		if(!SUCCEEDED(hr))//如果权限赋值失败
		{
			MessageBox("权限赋值失败!","创建新项目",MB_ICONINFORMATION);
			return FALSE;//返回
		}
		theApp.m_pConnection->Close();//关闭记录集
	} 		

	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		
		if(e.WCode()==3092) //如果项目已经存在
		{
			errormessage="项目 "+m_username+" 已经存在,是否删除?";	
			int nanswer=MessageBox(errormessage,"创建新项目",MB_ICONINFORMATION|MB_YESNO);
			if(nanswer==6)	//删除已有项目
			{
				strsql="Drop user "+m_username+"  cascade";
				hr =theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);//删除已有项目
				if(!SUCCEEDED(hr))	//如果删除项目失败
					MessageBox("删除项目失败","删除项目",MB_ICONINFORMATION);
				else
					MessageBox("删除项目完成","删除项目",MB_ICONINFORMATION);
				
			}
		}
		
		else	//其它错误信息
		{
			errormessage.Format("错误信息:%s",e.ErrorMessage());
			MessageBox(errormessage,"创建新项目",MB_ICONINFORMATION);
		}
		return FALSE;	//返回
	} 

	//连接根据项目名称创建的新用户
	strsql="Provider=OraOLEDB.Oracle;Data Source="+m_servername+";User Id="+m_username+";Password="+m_userpassword+";";
	try
	{
		hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);//连接根据项目名称创建的新用户
		if(!SUCCEEDED(hr))//如果连接新用户失败
		{
			MessageBox("数据库连接失败!","创建新项目",MB_ICONINFORMATION);
			return FALSE;	//返回
		}
	    	
	}
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		WORD nWcode=e.WCode(); 
		if(e.WCode()==0) //项目户名错误
		{	errormessage="项目户名错误!";
			MessageBox(errormessage,"创建新项目",MB_ICONINFORMATION);
			return FALSE;	//返回
		}
		else	//其它错误信息
		{	
			errormessage.Format("错误信息:%s",e.ErrorMessage());
			MessageBox(errormessage,"创建新项目",MB_ICONINFORMATION);
			return FALSE; 	//返回
		}
	} 

	//下面代码为在根据项目名称创建的新用户下创建数据表如DEM数据表,影像数据表等
	int index=-1;

	//1.DEM地形信息表
	index++;
	strtable[index]= "CREATE TABLE DEM_INFO(DEM起点坐标X NUMBER(10,3),\
		DEM起点坐标Y NUMBER(10,3),格网间距X NUMBER(3),\
		格网间距Y NUMBER(3),DEM分块大小 NUMBER(3),\
		DEM分块行数 NUMBER(6),DEM分块列数 NUMBER(6),\
		原始DEM行数 NUMBER(10),原始DEM列数 NUMBER(10),\
		DEM数据 BLOB,编号 NUMBER(2),无效DEM值 NUMBER(10))";	
	

	//2.DEM地形子块数据表
	index++;
	strtable[index]= "CREATE TABLE dem_block(行号 NUMBER(6),\
			列号 NUMBER(6),DEM数据 BLOB,编号 NUMBER(6),\
			中心坐标X NUMBER(10,3),中心坐标Y NUMBER(10,3))";	

	//3.DEM地形子块信息表
	index++;
	strtable[index]= "CREATE TABLE dem_subblock(行号 NUMBER(6),\
		列号 NUMBER(6),DEM数据 BLOB,所属块号 NUMBER(6),编号 NUMBER(6))";	
			
	//4.影像纹理表
	index++;
	strtable[index]= "CREATE TABLE texture(行号 NUMBER(6),\
		  列号 NUMBER(6),长度 NUMBER(6),宽度 NUMBER(6),\
		  纹理金子塔层号 NUMBER(6),纹理数据 BLOB,编号 NUMBER(6),\
		  左下角坐标X NUMBER(10,3),左下角坐标Y NUMBER(10,3),\
		  右上角坐标X NUMBER(10,3),右上角坐标Y NUMBER(10,3))";	
				
	//5.影像LOD信息表
	index++;
	strtable[index]= "CREATE TABLE IMAGERECT_INFO(左下角坐标X NUMBER(10,3),\
		 左下角坐标Y NUMBER(10,3),右上角坐标X NUMBER(10,3),\
		 右上角坐标Y NUMBER(10,3),影像金字塔总数 NUMBER(2),\
		 一级影像分辨率 NUMBER(6,2),二级影像分辨率 NUMBER(6,2),\
		 三级影像分辨率 NUMBER(6,2),\
		 四级影像分辨率 NUMBER(6,2),五级影像分辨率 NUMBER(6,2))";

	index++;
	
	//6.项目方案数据表
	strtable[index]= "CREATE TABLE Scheme (\
		 方案名称 VARCHAR2(100), 设计等级 VARCHAR2(20),\
		 牵引种类 VARCHAR2(10),机车类型 VARCHAR2(50),闭塞方式 VARCHAR2(50),\
		 地形地别 VARCHAR2(12), 工程条件 VARCHAR2(20),设计速度 NUMBER(6),\
		 最小曲线半径 NUMBER(6),到发线有效长度 NUMBER(6),\
		 最小坡长 NUMBER(6,2),最大坡度 NUMBER(6,2),最大坡度差 NUMBER(6,2),\
		 起点里程 NUMBER(20,3),终点里程 NUMBER(20,3))";
	
	index++;
	strtable[index]= "CREATE TABLE T3DLineZxCorrdinate (\
		  方案名称 VARCHAR2(100),序号 NUMBER(10), x坐标 NUMBER(20,3),\
		  y坐标 NUMBER(20,3),z坐标 NUMBER(20,3),坐标类型 VARCHAR2(30),\
		  地面高程 NUMBER(20,3),填挖高 NUMBER(20,3),里程 NUMBER(20,3))";
	

	index++;
	strtable[index]= "CREATE TABLE Scheme_plane_CureveData (\
		  方案名称 VARCHAR2(100),交点编号 VARCHAR2(50),\
		  转角 NUMBER(10,3),方位角1 NUMBER(10,3),方位角2 NUMBER(10,3),\
		  转向 NUMBER(2),缓和曲线长  NUMBER(6),\
		  切线长 NUMBER(15,3),曲线长 NUMBER(15,3),\
		  圆曲线长 NUMBER(15,3),直缓点里程 NUMBER(20,3),\
		  缓圆点里程 NUMBER(20,3),圆缓点里程 NUMBER(20,3),\
		  缓直点里程 NUMBER(20,3),外矢距 NUMBER(10,3),\
		  内移距 NUMBER(10,3),曲线半径 NUMBER(10),\
		  交点里程 NUMBER(20,3),交点x坐标 NUMBER(20,3),\
		  交点y坐标 NUMBER(20,3),交点z坐标 NUMBER(20,3),\
		  交点序号 NUMBER(10))";
	
	index++;
	strtable[index]= "CREATE TABLE Scheme_plane_CureveData_XY (\
		 方案名称 VARCHAR2(100),交点编号 VARCHAR2(50),\
		 圆心x坐标 NUMBER(20,3),圆心y坐标 NUMBER(20,3),\
		 直缓x坐标 NUMBER(20,3),直缓y坐标 NUMBER(20,3),\
		 缓圆x坐标 NUMBER(20,3),缓圆y坐标 NUMBER(20,3),\
		 圆缓x坐标 NUMBER(20,3),圆缓y坐标 NUMBER(20,3),\
		 缓直x坐标 NUMBER(20,3),缓直y坐标 NUMBER(20,3),\
		 交点序号 NUMBER(10))";
	

	index++;
	strtable[index]= "CREATE TABLE PriorityRadius (\
		行车速度 NUMBER(4), 曲线半径最小值 NUMBER(6),\
		曲线半径最大值 NUMBER(6))";
	
	index++;
	strtable[index]= "CREATE TABLE MinRadius (\
		行车速度 NUMBER(4), 一般地段 NUMBER(6),\
		困难地段 NUMBER(6))";
	
	index++;
	strtable[index]= "CREATE TABLE T3DLineZxCorrdinateZDM (\
		 方案名称 VARCHAR2(100),序号 NUMBER(10), x坐标 NUMBER(20,3),\
		 y坐标 NUMBER(20,3),z坐标 NUMBER(20,3),坐标类型 VARCHAR2(30),\
		 地面高程 NUMBER(20,3),填挖高 NUMBER(20,3),里程\
		 NUMBER(20,3),交点里程 NUMBER(20,3))";
	
	
	index++;
	strtable[index]= "CREATE TABLE ZDmJDCurve (\
		 方案名称 VARCHAR2(100), 坡长 NUMBER(20,3),\
		 坡率 NUMBER(20,3),变坡点桩号 NUMBER(20,3),标高 VARCHAR2(30),\
		 竖曲线半径 NUMBER(20,3),外矢距 NUMBER(20,3),切线长 NUMBER(20,3),\
		 坡度代数差 NUMBER(20,3),序号 NUMBER(10),转向类型 NUMBER(2),\
		 直缓点里程 NUMBER(20,3),直缓点标高 NUMBER(20,3),\
		 缓直点里程 NUMBER(20,3),缓直点标高 NUMBER(20,3))";
	index++;
	strtable[index]= "CREATE TABLE zdmSegmentGeoFeature (\
		   分段起始里程 NUMBER(10,3),分段终点里程 NUMBER(10,3),\
		   工程地质特征 VARCHAR2(2000),方案名称 VARCHAR2(100))";
	
	index++;
	strtable[index]= "CREATE TABLE Tunnel (\
			 隧道名称 VARCHAR2(400),隧道起点里程 NUMBER(20,3),\
			 隧道终点里程 NUMBER(20,3), 隧道长度 NUMBER(20,3),方案名称 VARCHAR2(200))";

	index++;
	strtable[index]= "CREATE TABLE Bridge (\
		 桥梁名称 VARCHAR2(400),桥梁起点里程 NUMBER(20,3),\
		 桥梁终点里程 NUMBER(20,3), 桥梁长度 NUMBER(20,3),\
		 方案名称 VARCHAR2(200))";

	index++;
	strtable[index]= "CREATE TABLE StationTable (\
		车站名称 VARCHAR2(200),车站类型 VARCHAR2(50),\
		里程 NUMBER(20,3), 方案名称 VARCHAR2(200))";

	/*
		还可以继续创建所需要的数据表
	*/

	//创建所有数据表
	for(int i=0;i<=index;i++)
	{

		HRESULT hr=theApp.m_pConnection->Execute (_bstr_t(strtable[i]),NULL,adCmdText);//创建表
		if(!SUCCEEDED(hr)) //如果表创建失败,则删除根据项目名称新创建的用户
		{
			MessageBox("表创建失败!","登录数据库",MB_ICONINFORMATION);
			strsql="Provider=OraOLEDB.Oracle;Data Source="+m_servername+";User Id="+m_systemname+";Password="+m_systempassword+";";
			hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);//重新连接Oracle系统管理员用户
			strsql = "DROP USER  " + m_username + "  CASCADE   " ;
			hr=theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);//删除新创建的用户			
			return FALSE;

		}
	}
	theApp.m_pConnection->Close();//关闭数据库连接

	
	
	//打开用于存储所建的项目名称的用户
	strsql="Provider=OraOLEDB.Oracle;Data Source="+m_servername+";User Id="+tname+";Password="+tpass+";";
	hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);
	if(!SUCCEEDED(hr))//打开失败
	{
		MessageBox("数据库打开错误!");
	}
	//下面得到项目创建的日期和时间信息
	CString stt;
	CTime time = CTime::GetCurrentTime();   
	int m_nYear = time.GetYear();     
	int m_nMonth = time.GetMonth();     
	int m_nDay = time.GetDay();     
	int m_nHour = time.GetHour();     
	int m_nMinute = time.GetMinute();   
	int m_nSecond = time.GetSecond();   
	stt.Format("%4d-%2d-%2d",m_nYear,m_nMonth,m_nDay);
	strsql.Format("INSERT INTO Project VALUES('%s','%s')",m_username,stt);
	hr=theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);//将项目创建的日期和时间信息写入Project表中
	theApp.m_pConnection->Close();//关闭数据库连接
	
	//重新连接根据项目名称创建的新用户
	strsql="Provider=OraOLEDB.Oracle;Data Source="+m_servername+";User Id="+m_username+";Password="+m_userpassword+";";
	hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);	//连接数据库
	theApp.bLoginSucceed=TRUE; //数据库登录成功
	MessageBox("项目创建成功!","创建新项目",MB_ICONINFORMATION);
	
	return TRUE;
}

BOOL CNewProject::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	m_servername="test";
	m_systemname="system";
	m_systempassword="manager";
	this->UpdateData(FALSE);
	
	return TRUE;  
}

//确定
void CNewProject::OnOK() 
{
	BeginWaitCursor();//将光标切换为等待光标
	if(CreateNewProject()==TRUE) //创建新项目
	{
		EndWaitCursor();//将光标切换为默认光标,结束等待
		CDialog::OnOK();//结束
	}
	else
	{
		EndWaitCursor();//将光标切换为默认光标,结束等待
	}
	
}
