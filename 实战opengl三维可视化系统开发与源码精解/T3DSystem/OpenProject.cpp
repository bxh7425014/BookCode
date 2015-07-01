// OpenProject.cpp : implementation file
 

#include "stdafx.h"
#include "MainFrm.h"
#include "OpenProject.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// COpenProject dialog




COpenProject::COpenProject(CWnd* pParent /*=NULL*/)
	: CDialog(COpenProject::IDD, pParent)
{
	//{{AFX_DATA_INIT(COpenProject)
	//}}AFX_DATA_INIT
}


void COpenProject::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(COpenProject)
	DDX_Control(pDX, IDC_LIST, m_list);
	DDX_Control(pDX, IDC_PROGRESS, m_progress);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(COpenProject, CDialog)
	//{{AFX_MSG_MAP(COpenProject)
	ON_NOTIFY(NM_CLICK, IDC_LIST, OnClickList)
	ON_BN_CLICKED(IDC_BUTTON_DELETE, OnButtonDelete)
	ON_BN_CLICKED(IDC_BUTTON_OPEN, OnButtonOpen)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// COpenProject message handlers

BOOL COpenProject::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	InitListCtrl();//初始化 ListCtrl控件
	
	LoadData();//从数据库表中加载项目数据
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

//从数据库表中加载项目数据
void COpenProject::LoadData()
{
	CString strsql;

	//从存储项目名称、创建日期的Oralce用户RW3D中读取所有项目信息
	strsql="Provider=OraOLEDB.Oracle;User Id=RW3D;Password=aaa;";	//定义SQL字符串	
	try
	{
		HRESULT hr =m_Connection->Open(_bstr_t(strsql),"","",-1);//打开数据库
		if(!SUCCEEDED(hr)) //如果打开失败
		{
			MessageBox("数据库连接失败!","打开项目",MB_ICONINFORMATION);
			return ;//返回
		}
	}
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		MessageBox("数据库连接失败!","打开项目文件",MB_ICONINFORMATION|MB_OK);
		return ;//返回
	} 
	
	//以项目创建日期为升序从Project中读取所有项目信息
	strsql="select  *   from  Project ORDER BY 建立时间  ASC";  //定义SQL字符串	
	try
	{
		//打开数据表
		HRESULT	hr =m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(m_Connection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"装载数据",MB_ICONSTOP);
		return;
	} 
	
	//删除所有数据
	m_list.DeleteAllItems();
	mtotalProjects=0;//总项目数初始值=0
	
    CString tt[3];
	while(!m_Recordset->adoEOF)//如果没有到记录集尾
	{    
		Thevalue = m_Recordset->GetCollect("项目名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt[1]=Thevalue.bstrVal;//项目名称
		}
	
		Thevalue = m_Recordset->GetCollect("建立时间"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt[2]=Thevalue.bstrVal;//建立时间
		}

		tt[0].Format("%d",mtotalProjects+1);//序号

		//将项目名称,建立时间信息写入ListCtrl控件中
		m_list.InsertItem(mtotalProjects,tt[0]);      //序号
		m_list.SetItemText(mtotalProjects,1,tt[1]);   //项目名称
		m_list.SetItemText(mtotalProjects,2,tt[2]);	//项目建立时间
		
		mtotalProjects++;//总项目数+1

		m_Recordset->MoveNext();//记录集指针下移
	}  

	m_Recordset->Close();  //关闭记录集
	m_Connection->Close();//关闭数据库
	
	
}

//单击ListCtrl控件响应函数,用来得到选择的项目名称
void COpenProject::OnClickList(NMHDR* pNMHDR, LRESULT* pResult) 
{
	
	NMLISTVIEW    *pNMListView = (NMLISTVIEW    *)pNMHDR;
	
	if(pNMListView->iItem >= 0) //如果选择
    {
        m_nCurrentItem = pNMListView->iItem;//单击项目的索引
        m_nCurrentSubItem = pNMListView->iSubItem;
		m_strProjectname=m_list.GetItemText(m_nCurrentItem,1);//得到选择的项目名称
    }
	*pResult = 0;
}


//打开项目
void COpenProject::OnButtonOpen() 
{
	CString strsql;

	if(mtotalProjects<=0)	
	{
		MessageBox("没有项目！","打开项目",MB_ICONINFORMATION|MB_OK);
		return;
	}
	
	if(m_nCurrentItem<0)	
	{
		MessageBox("请选择要打开的项目！","打开项目",MB_ICONINFORMATION|MB_OK);
		return;
	}
	
	if(	m_strProjectname.IsEmpty())
		return;
	
	BeginWaitCursor();

	
	theApp.m_username=m_strProjectname;
	theApp.m_userpassword="a";
	m_currentSchemeNames=m_strProjectname;
	
	if(theApp.m_pConnection->State) //如果数据库已打开
		theApp.m_pConnection->Close(); //关闭连接
	
	strsql="Provider=OraOLEDB.Oracle;User Id="+theApp.m_username+";Password="+theApp.m_userpassword+";";
	try
	{
		HRESULT hr =theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);
		if(!SUCCEEDED(hr))
		{
			MessageBox("数据库连接失败!","打开项目",MB_ICONINFORMATION);
			theApp.m_pConnection->Close();//关闭数据库连接
			return ;
		}
	}
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"打开项目",MB_ICONINFORMATION);
		theApp.m_pConnection->Close();//关闭数据库连接
		return ;
	} 

	
	//定义SQL字符串
	strsql="select *  from DEM_INFO ";  
	if(m_Recordset->State)	//如果m_Recordset已打开，先关闭
		m_Recordset->Close();
	
	try
	{
		//打开 DEM_INFO数据表,读取DEM相关信息
		HRESULT	hr =m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"装载数据",MB_ICONSTOP);
		m_Recordset->Close();
		return ;
	} 
 
    if(!m_Recordset->adoEOF)//如果没有到记录集末尾,表示有记录
	{
		Thevalue = m_Recordset->GetCollect("DEM起点坐标X"); 
		theApp.m_DemLeftDown_x=(double)Thevalue;//DEM左下角x坐标

		Thevalue = m_Recordset->GetCollect("DEM起点坐标Y"); 
		theApp.m_DemLeftDown_y=(double)Thevalue;//DEM左下角y坐标

	
		Thevalue = m_Recordset->GetCollect("格网间距X"); 	
		theApp.m_Cell_xwidth=(long)Thevalue ;//DEM在x方向上格网点间距

		Thevalue = m_Recordset->GetCollect("格网间距Y"); 
		theApp.m_Cell_ywidth=(long)Thevalue;//DEM在y方向上格网点间距
		
		Thevalue = m_Recordset->GetCollect("DEM分块大小"); 
		theApp.m_Dem_BlockSize=(long)Thevalue;//DEM分块大小,即一个地形子块的大小

		Thevalue = m_Recordset->GetCollect("DEM分块行数"); 
		theApp.m_BlockRows=(long)Thevalue;//DEM分块的总行数

		Thevalue = m_Recordset->GetCollect("DEM分块列数"); 
		theApp.m_BlockCols=(long)Thevalue;//DEM分块的总列数
		
		Thevalue = m_Recordset->GetCollect("原始DEM行数"); 
		theApp.m_Dem_Rows=(long)Thevalue;//DEM数据和总行数
 
		Thevalue = m_Recordset->GetCollect("原始DEM列数"); 
		theApp.m_Dem_cols=(long)Thevalue;//DEM数据和总列数
		
		Thevalue = m_Recordset->GetCollect("无效DEM值"); 
		theApp.m_DEM_IvalidValue=(long)Thevalue;//无效DEM数据点高程值
		
		//DEM地形子块的宽度(即一个地形块表示多少空间距离,若分块大小=33,
		//DEM在x方向上格网点间距=20,则DEM地形子块宽度=20*32=640米)
		theApp.m_Dem_BlockWidth=theApp.m_Cell_xwidth*(theApp.m_Dem_BlockSize-1);
		
		m_Recordset->Close();//关闭记录集
	
		//DEM右上角x坐标
		theApp.m_DemRightUp_x=theApp.m_DemLeftDown_x+theApp.m_Cell_xwidth*(theApp.m_Dem_cols-1);
		//DEM右上角y坐标
		theApp.m_DemRightUp_y=theApp.m_DemLeftDown_y+theApp.m_Cell_ywidth*(theApp.m_Dem_Rows-1);

	}
	else
	{
		m_Recordset->Close();//关闭记录集
		MessageBox("项目没有数模数据!","打开项目",MB_ICONINFORMATION);
		return ;	//返回
	}


	//定义SQL字符串
	strsql="select *  from IMAGERECT_INFO ";  
	try
	{
		//打开 IMAGERECT_INFO数据表,读取影像纹理相关信息
		HRESULT	hr =m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"装载数据",MB_ICONSTOP);
		m_Recordset->Close();//关闭记录集
		return ;	//返回
	} 
	
	
	if(!m_Recordset->adoEOF)	//如果没有到记录集末尾,表示有记录
	{
		Thevalue = m_Recordset->GetCollect("左下角坐标X"); 
		theApp.m_TexturLeftDown_x=(double)Thevalue;//纹理左下角x坐标
		
		Thevalue = m_Recordset->GetCollect("左下角坐标Y"); 
		theApp.m_TexturLeftDown_y=(double)Thevalue;//纹理左下角y坐标
		
		Thevalue = m_Recordset->GetCollect("右上角坐标X"); 
		theApp.m_TexturRightUp_x=(double)Thevalue ;//纹理右上角x坐标
		
		Thevalue = m_Recordset->GetCollect("右上角坐标Y"); 
		theApp.m_TexturRightUp_y=(double)Thevalue;//纹理右上角y坐标
		
		Thevalue = m_Recordset->GetCollect("影像金字塔总数"); 
		theApp.m_ImagePyramidCount=(long)Thevalue;//纹理影像金字塔总数,即多少LOD级别
	
		Thevalue = m_Recordset->GetCollect("一级影像分辨率"); 
		theApp.m_ImageResolution[1]=(float)Thevalue;//纹理影像一级影像分辨率

		Thevalue = m_Recordset->GetCollect("二级影像分辨率"); 
		theApp.m_ImageResolution[2]=(float)Thevalue;//纹理影像二级影像分辨率

		Thevalue = m_Recordset->GetCollect("三级影像分辨率"); 
		theApp.m_ImageResolution[3]=(float)Thevalue;//纹理影像三级影像分辨率
	
		Thevalue = m_Recordset->GetCollect("四级影像分辨率"); 
		theApp.m_ImageResolution[4]=(float)Thevalue;//纹理影像四级影像分辨率
	
		Thevalue = m_Recordset->GetCollect("五级影像分辨率"); 
		theApp.m_ImageResolution[5]=(float)Thevalue;//纹理影像五级影像分辨率

		m_Recordset->Close();//关闭记录集
		
	}
	else
	{
		m_Recordset->Close();//关闭记录集
	}
   
  	
	
	theApp.bLoginSucceed=TRUE;//数据加载成功
	myOci.Init_OCI();//初始化OCI
	
	//定义SQL字符串	
	strsql.Format("select * from Scheme ORDER BY 方案名称 ASC");
	if(m_Recordset->State)
		m_Recordset->Close();
	try
	{
		//打开 Scheme数据表,读取打开方案的相关信息
		m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)		//错误处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"打开数据库表",MB_ICONINFORMATION);
		m_Recordset->Close();//关闭记录集
		return  ;//返回
	} 	
	
	//GetCollect():Ado获取数据库字段值函数
	m_SchemeNames.RemoveAll(); //清空，存储方案名称的全局数组
	int index=0; //读取方案信息
	while(!m_Recordset->adoEOF)
	{
	
		Thevalue = m_Recordset->GetCollect("方案名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].Schemename=Thevalue.bstrVal;//方案名称
			m_SchemeNames.Add(myDesingScheme.SchemeDatass[index].Schemename);//存储方案名称到全局数组
		}

		Thevalue = m_Recordset->GetCollect("设计等级");
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strDesigngrade=Thevalue.bstrVal;//设计等级
			
		}
		
		Thevalue = m_Recordset->GetCollect("牵引种类"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strDraughtstyle=Thevalue.bstrVal;//牵引种类
		}
		

		Thevalue = m_Recordset->GetCollect("机车类型"); //机车类型
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strBlocomotiveStyle=Thevalue.bstrVal;
		}
		
		Thevalue = m_Recordset->GetCollect("闭塞方式"); //闭塞方式
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strCBbsStyle=Thevalue.bstrVal;
		}

		Thevalue = m_Recordset->GetCollect("地形地别");  //地形地别
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strTerrainstyle=Thevalue.bstrVal;
		}
		
		Thevalue = m_Recordset->GetCollect("工程条件"); //工程条件
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strEngineeringcondition=Thevalue.bstrVal;
		}
		
		Thevalue = m_Recordset->GetCollect("设计速度"); //设计速度
		myDesingScheme.SchemeDatass[index].designspeed=(short)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("最小曲线半径"); //最小曲线半径
		myDesingScheme.SchemeDatass[index].minRadius=(long)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("到发线有效长度"); //到发线有效长度
		myDesingScheme.SchemeDatass[index].dxfLength=(short)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("最小坡长"); //最小坡长
		myDesingScheme.SchemeDatass[index].minSlopeLength=(double)Thevalue;		
		
		Thevalue = m_Recordset->GetCollect("最大坡度"); //最大坡度
		myDesingScheme.SchemeDatass[index].maxSlope=(double)Thevalue;		
	
		Thevalue = m_Recordset->GetCollect("最大坡度差"); //最大坡度差
		myDesingScheme.SchemeDatass[index].maxSlopePddsc=(double)Thevalue;		
		
		Thevalue = m_Recordset->GetCollect("起点里程"); //起点里程
		myDesingScheme.SchemeDatass[index].StartLC=(double)Thevalue;		
		
		Thevalue = m_Recordset->GetCollect("终点里程"); //终点里程
		myDesingScheme.SchemeDatass[index].EndLC=(double)Thevalue;		
		
		index++;	
		m_Recordset->MoveNext();//记录集指针下移

	}
	m_Recordset->Close();//关闭记录集
	if(m_SchemeNames.GetSize()>0)
	{
		CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		pMainFrame->AddSchemeName();
	}
	MessageBox("打开项目成功!","打开项目",MB_ICONINFORMATION);
    EndWaitCursor();//将光标切换为默认光标,结束等待
	EndDialog(IDOK);
}


//删除项目
void COpenProject::OnButtonDelete() 
{
	HRESULT hr;
	CString trsql;
	
	
	if(mtotalProjects<=0)	//如果项目的总数<=0，表示还没有建立项目，不能删除 
	{
		MessageBox("没有项目！","删除项目",MB_ICONINFORMATION|MB_OK);
		return;
	}

	if(m_nCurrentItem<0)	//如果没有选中ListCtrl控件的项目，返回
	{
		MessageBox("请选择要删除的项目！","删除项目",MB_ICONINFORMATION|MB_OK);
		return;
	}

	//当前打开项目不能删除
	if(strcmp(theApp.m_username,m_strProjectname)==0)
	{
		MessageBox("当前打开项目为"+m_strProjectname+",不能删除!","删除项目",MB_ICONSTOP|MB_OK);
		return;
	}

	//如果m_Connection已处于连接状态，先关闭，否则再次连接时将出错
	if(m_Connection->State)
		m_Connection->Close();
	
	BeginWaitCursor();//调用本函数显示沙漏光标,告诉用户系统忙
	
	//定义SQL字符串
	CString strsql="Provider=OraOLEDB.Oracle;User Id=RW3D;Password=aaa;";
	try
	{
		hr =m_Connection->Open(_bstr_t(strsql),"","",-1);//连接存储项目名称、创建日期的Oralce用户RW3D
		
		if(!SUCCEEDED(hr))//如果数据库连接失败
		{
			MessageBox("数据库连接失败!","删除项目",MB_ICONINFORMATION);
			m_Connection->Close();//关闭连接
			EndWaitCursor();//撤消沙漏光标,并恢复以前的光标
			return ;//返回
		}
	}
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"删除项目",MB_ICONINFORMATION);
		m_Connection->Close();//关闭连接
		EndWaitCursor();//撤消沙漏光标,并恢复以前的光标
		return ;//返回
	} 
 
	int manwer=MessageBox("是否删除项目?","删除项目",MB_ICONINFORMATION|MB_YESNO);
	if(manwer==7)//如果不删除项目，返回
		return ;

//	drop tablespace XXXX INCLUDING CONTENTS; 
//	drop user XXXX cascade;
//	Oracle中drop user和drop user cascade的区别
//		drop user ； 仅仅是删除用户，
//		drop user ××  cascade ；会删除此用户名下的所有表和视图。加参数就是为了删除这个用户下的所有对象! 

	//创建SQL字符串
//	strsql="Provider=OraOLEDB.Oracle;Data Source="+theApp.m_servername+";User Id="+theApp.m_systemname+";Password="+theApp.m_userpassword+";";
//	theApp.m_pConnection->Open(_bstr_t(strsql),"","",-1);//打开Oracle系统管理员用户
//	strsql="Drop user "+m_strProjectname+"  cascade";
//	theApp.m_pConnection->Execute (_bstr_t(strsql),NULL,adCmdText);

	
	//定义SQL字符串,(删除用户)
	//删除项目用户和此用户名下的所有表和视图(参数 cascade就是为了删除这个用户下的所有对象!)
	strsql="Drop user "+m_strProjectname+"  cascade";
	try
	{
		hr =m_Connection->Execute (_bstr_t(strsql),NULL,adCmdText);//删除项目
		if(!SUCCEEDED(hr))
		{
			MessageBox("删除项目失败!","删除项目",MB_ICONINFORMATION);
			m_Connection->Close();//关闭连接
			EndWaitCursor();//撤消沙漏光标,并恢复以前的光标
			return ;//返回
		}
	}
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		m_Connection->Close();//关闭连接
		MessageBox(errormessage,"删除项目",MB_ICONINFORMATION);
		EndWaitCursor();//撤消沙漏光标,并恢复以前的光标
		return ;//返回
	} 

	strsql.Format("Delete  from Project WHERE 项目名称='%s'",m_strProjectname);
	hr =m_Connection->Execute (_bstr_t(strsql),NULL,adCmdText);
	m_Connection->Close();//关闭连接
	
	MessageBox("项目删除成功!","删除项目",MB_ICONINFORMATION);

	m_nCurrentItem=-1; //恢复为选中状态
	LoadData(); //重新加载项目数据
	EndWaitCursor();//撤消沙漏光标,并恢复以前的光标
	
}



//打开项目
BOOL  COpenProject::OpenProjects(int NewOrOpen)
{
	CString strsql;

	
	BeginWaitCursor();
	
	//定义SQL字符串
	strsql="select *  from DEM_INFO ";  
	if(m_Recordset->State)	//如果m_Recordset已打开，先关闭
		m_Recordset->Close();
	
	try
	{
		//打开 DEM_INFO数据表,读取DEM相关信息
		HRESULT	hr =m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"装载数据",MB_ICONSTOP);
		return FALSE;
	} 
 
    if(!m_Recordset->adoEOF)//如果没有到记录集末尾,表示有记录
	{
		Thevalue = m_Recordset->GetCollect("DEM起点坐标X"); 
		theApp.m_DemLeftDown_x=(double)Thevalue;//DEM左下角x坐标

		Thevalue = m_Recordset->GetCollect("DEM起点坐标Y"); 
		theApp.m_DemLeftDown_y=(double)Thevalue;//DEM左下角y坐标

	
		Thevalue = m_Recordset->GetCollect("格网间距X"); 	
		theApp.m_Cell_xwidth=(long)Thevalue ;//DEM在x方向上格网点间距

		Thevalue = m_Recordset->GetCollect("格网间距Y"); 
		theApp.m_Cell_ywidth=(long)Thevalue;//DEM在y方向上格网点间距
		
		Thevalue = m_Recordset->GetCollect("DEM分块大小"); 
		theApp.m_Dem_BlockSize=(long)Thevalue;//DEM分块大小,即一个地形子块的大小

		Thevalue = m_Recordset->GetCollect("DEM分块行数"); 
		theApp.m_BlockRows=(long)Thevalue;//DEM分块的总行数

		Thevalue = m_Recordset->GetCollect("DEM分块列数"); 
		theApp.m_BlockCols=(long)Thevalue;//DEM分块的总列数
		
		Thevalue = m_Recordset->GetCollect("原始DEM行数"); 
		theApp.m_Dem_Rows=(long)Thevalue;//DEM数据和总行数
 
		Thevalue = m_Recordset->GetCollect("原始DEM列数"); 
		theApp.m_Dem_cols=(long)Thevalue;//DEM数据和总列数
		
		Thevalue = m_Recordset->GetCollect("无效DEM值"); 
		theApp.m_DEM_IvalidValue=(long)Thevalue;//无效DEM数据点高程值
		
		//DEM地形子块的宽度(即一个地形块表示多少空间距离,若分块大小=33,
		//DEM在x方向上格网点间距=20,则DEM地形子块宽度=20*32=640米)
		theApp.m_Dem_BlockWidth=theApp.m_Cell_xwidth*(theApp.m_Dem_BlockSize-1);
		
		m_Recordset->Close();//关闭记录集
	
		//DEM右上角x坐标
		theApp.m_DemRightUp_x=theApp.m_DemLeftDown_x+theApp.m_Cell_xwidth*(theApp.m_Dem_cols-1);
		//DEM右上角y坐标
		theApp.m_DemRightUp_y=theApp.m_DemLeftDown_y+theApp.m_Cell_ywidth*(theApp.m_Dem_Rows-1);

	}
	else
	{
		m_Recordset->Close();//关闭记录集
		MessageBox("项目没有数模数据!","打开项目",MB_ICONINFORMATION);
		return FALSE;	//返回
	}


	//定义SQL字符串
	strsql="select *  from IMAGERECT_INFO ";  
	try
	{
		//打开 IMAGERECT_INFO数据表,读取影像纹理相关信息
		HRESULT	hr =m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	//错误处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"装载数据",MB_ICONSTOP);
		m_Recordset->Close();//关闭记录集
		return FALSE;	//返回
	} 
	
	
	if(!m_Recordset->adoEOF)	//如果没有到记录集末尾,表示有记录
	{
		Thevalue = m_Recordset->GetCollect("左下角坐标X"); 
		theApp.m_TexturLeftDown_x=(double)Thevalue;//纹理左下角x坐标
		
		Thevalue = m_Recordset->GetCollect("左下角坐标Y"); 
		theApp.m_TexturLeftDown_y=(double)Thevalue;//纹理左下角y坐标
		
		Thevalue = m_Recordset->GetCollect("右上角坐标X"); 
		theApp.m_TexturRightUp_x=(double)Thevalue ;//纹理右上角x坐标
		
		Thevalue = m_Recordset->GetCollect("右上角坐标Y"); 
		theApp.m_TexturRightUp_y=(double)Thevalue;//纹理右上角y坐标
		
		Thevalue = m_Recordset->GetCollect("影像金字塔总数"); 
		theApp.m_ImagePyramidCount=(long)Thevalue;//纹理影像金字塔总数,即多少LOD级别
	
		Thevalue = m_Recordset->GetCollect("一级影像分辨率"); 
		theApp.m_ImageResolution[1]=(float)Thevalue;//纹理影像一级影像分辨率

		Thevalue = m_Recordset->GetCollect("二级影像分辨率"); 
		theApp.m_ImageResolution[2]=(float)Thevalue;//纹理影像二级影像分辨率

		Thevalue = m_Recordset->GetCollect("三级影像分辨率"); 
		theApp.m_ImageResolution[3]=(float)Thevalue;//纹理影像三级影像分辨率
	
		Thevalue = m_Recordset->GetCollect("四级影像分辨率"); 
		theApp.m_ImageResolution[4]=(float)Thevalue;//纹理影像四级影像分辨率
	
		Thevalue = m_Recordset->GetCollect("五级影像分辨率"); 
		theApp.m_ImageResolution[5]=(float)Thevalue;//纹理影像五级影像分辨率

		m_Recordset->Close();//关闭记录集
		
	}
	else
	{
		m_Recordset->Close();//关闭记录集
	}
   
  	
	
	theApp.bLoginSucceed=TRUE;//数据加载成功
	myOci.Init_OCI();//初始化OCI
	
	//定义SQL字符串	
	strsql.Format("select * from Scheme ORDER BY 方案名称 ASC");
	if(m_Recordset->State)
		m_Recordset->Close();
	try
	{
		//打开 Scheme数据表,读取打开方案的相关信息
		m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)		//错误处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"打开数据库表",MB_ICONINFORMATION);
		m_Recordset->Close();//关闭记录集
		return  FALSE;//返回
	} 	
	
	m_SchemeNames.RemoveAll();	
	//GetCollect():Ado获取数据库字段值函数
	int index=0; //读取方案信息
	while(!m_Recordset->adoEOF)
	{
	
		Thevalue = m_Recordset->GetCollect("方案名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].Schemename=Thevalue.bstrVal;//方案名称
			m_SchemeNames.Add(myDesingScheme.SchemeDatass[index].Schemename);
			
		}

		Thevalue = m_Recordset->GetCollect("设计等级");
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strDesigngrade=Thevalue.bstrVal;//设计等级
			
		}
		
		Thevalue = m_Recordset->GetCollect("牵引种类"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strDraughtstyle=Thevalue.bstrVal;//牵引种类
		}
		

		Thevalue = m_Recordset->GetCollect("机车类型"); //机车类型
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strBlocomotiveStyle=Thevalue.bstrVal;
		}
		
		Thevalue = m_Recordset->GetCollect("闭塞方式"); //闭塞方式
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strCBbsStyle=Thevalue.bstrVal;
		}

		Thevalue = m_Recordset->GetCollect("地形地别");  //地形地别
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strTerrainstyle=Thevalue.bstrVal;
		}
		
		Thevalue = m_Recordset->GetCollect("工程条件"); //工程条件
		if(Thevalue.vt!=VT_NULL) 
		{
			myDesingScheme.SchemeDatass[index].strEngineeringcondition=Thevalue.bstrVal;
		}
		
		Thevalue = m_Recordset->GetCollect("设计速度"); //设计速度
		myDesingScheme.SchemeDatass[index].designspeed=(short)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("最小曲线半径"); //最小曲线半径
		myDesingScheme.SchemeDatass[index].minRadius=(long)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("到发线有效长度"); //到发线有效长度
		myDesingScheme.SchemeDatass[index].dxfLength=(short)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("最小坡长"); //最小坡长
		myDesingScheme.SchemeDatass[index].minSlopeLength=(double)Thevalue;		
		
		Thevalue = m_Recordset->GetCollect("最大坡度"); //最大坡度
		myDesingScheme.SchemeDatass[index].maxSlope=(double)Thevalue;		
	
		Thevalue = m_Recordset->GetCollect("最大坡度差"); //最大坡度差
		myDesingScheme.SchemeDatass[index].maxSlopePddsc=(double)Thevalue;		
		
		Thevalue = m_Recordset->GetCollect("起点里程"); //起点里程
		myDesingScheme.SchemeDatass[index].StartLC=(double)Thevalue;		
		
		Thevalue = m_Recordset->GetCollect("终点里程"); //终点里程
		myDesingScheme.SchemeDatass[index].EndLC=(double)Thevalue;		
		
		index++;	
		m_Recordset->MoveNext();//记录集指针下移

	}
	m_Recordset->Close();//关闭记录集
	
	if(m_SchemeNames.GetSize()>0)
	{
		CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		pMainFrame->AddSchemeName();
	}

//	if(NewOrOpen==1) //	1:打开项目  -1:新建项目
	MessageBox("打开项目成功!","打开项目",MB_ICONINFORMATION);
    
    EndWaitCursor();//将光标切换为默认光标,结束等待

	return TRUE; //返回
	
}

//初始化 ListCtrl控件
void COpenProject::InitListCtrl()
{
	// 	1.新加ListControl 控件，属性中的style属性页下的View选择Report。
	m_list.InsertColumn(0,"序号");          //插入列
	m_list.InsertColumn(1,"项目名称");
	m_list.InsertColumn(2,"项目创建日期");

	CRect rect;
	m_list.GetClientRect(rect);    //获得当前客户区信息                 
	m_list.SetColumnWidth(0,rect.Width()/5);   //设置列的宽度     
	m_list.SetColumnWidth(1,rect.Width()/3);
	m_list.SetColumnWidth(2,rect.Width()/3);
	
	//设置ListControl 控件风格为网格和整行选择模式
	m_list.SetExtendedStyle(m_list.GetExtendedStyle()|LVS_EX_GRIDLINES|   LVS_EX_FULLROWSELECT);
}

