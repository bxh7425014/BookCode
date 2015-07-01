// NewScheme.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "NewScheme.h"
#include "MainFrm.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CNewScheme dialog


CNewScheme::CNewScheme(CWnd* pParent /*=NULL*/)
	: CDialog(CNewScheme::IDD, pParent)
{
	//{{AFX_DATA_INIT(CNewScheme)
	m_minRadius = 0;
	m_criterionMinR = 0;
	m_startLC = 0.0;
	m_maxSlopePd = 0.0f;
	m_maxSlopePddsc = 0.0f;
	m_minSLopePc = 0.0f;
	m_criterionminSlopePc = 0;
	m_criterionmaxSlopePD = 0.0f;
	m_criterionmaxSlopePddsc = 0.0f;
	m_endLC = 0.0;
	//}}AFX_DATA_INIT
}


void CNewScheme::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CNewScheme)
	DDX_Control(pDX, IDC_COMBO_BSSTYLE, m_CBbsStyle);
	DDX_Control(pDX, IDC_COMBO_LOCOMOTIVE, m_CBlocomotiveStyle);
	DDX_Control(pDX, IDC_COMBO_DFXEFFICIENTLENGTH, m_CBdfxefficientLength);
	DDX_Control(pDX, IDC_COMBO_TERRAINSTYLE, m_CBterrainStyle);
	DDX_Control(pDX, IDC_COMBO_ENGINEERINGCONDITION, m_CBengineeringCondition);
	DDX_Control(pDX, IDC_COMBO_DESIGNSPEED, m_CBdesignSpeed);
	DDX_Control(pDX, IDC_COMBO_SCHEMENAME, m_CBschemeName);
	DDX_Control(pDX, IDC_COMBO_GRADE, m_CBrailwayGrade);
	DDX_Control(pDX, IDC_COMBO_DRAUGHTSTYLE, m_CBdraughtStyle);
	DDX_Text(pDX, IDC_EDIT_MINRADIUS, m_minRadius);
	DDX_Text(pDX, IDC_EDIT_CRITERIONMINR, m_criterionMinR);
	DDX_Text(pDX, IDC_EDIT_STARTLC, m_startLC);
	DDX_Text(pDX, IDC_EDIT_MAXSLOPEPD, m_maxSlopePd);
	DDX_Text(pDX, IDC_EDIT_MAXSLOPEPDDSC, m_maxSlopePddsc);
	DDX_Text(pDX, IDC_EDIT_MINPC, m_minSLopePc);
	DDX_Text(pDX, IDC_EDIT_CRITERIONMINPC, m_criterionminSlopePc);
	DDX_Text(pDX, IDC_EDIT_CRITERIONMAXSLOPEPD, m_criterionmaxSlopePD);
	DDX_Text(pDX, IDC_EDIT_CRITERIONMAXSLOPEPDDSC, m_criterionmaxSlopePddsc);
	DDX_Text(pDX, IDC_EDIT_ENDLC, m_endLC);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CNewScheme, CDialog)
	//{{AFX_MSG_MAP(CNewScheme)
	ON_BN_CLICKED(IDC_BUTTON_SAVE, OnButtonSave)
	ON_CBN_SELCHANGE(IDC_COMBO_SCHEMENAME, OnSelchangeComboSchemename)
	ON_BN_CLICKED(IDC_BUTTON_EDIT, OnButtonEdit)
	ON_BN_CLICKED(IDC_BUTTON_EXIT, OnButtonExit)
	ON_CBN_SELCHANGE(IDC_COMBO_GRADE, OnSelchangeComboGrade)
	ON_CBN_SELCHANGE(IDC_COMBO_DESIGNSPEED, OnSelchangeComboDesignspeed)
	ON_CBN_SELCHANGE(IDC_COMBO_ENGINEERINGCONDITION, OnSelchangeComboEngineeringcondition)
	ON_BN_CLICKED(IDC_BUTTON_DELETE, OnButtonDelete)
	ON_WM_MOUSEMOVE()
	ON_CBN_SELCHANGE(IDC_COMBO_DRAUGHTSTYLE, OnSelchangeComboDraughtstyle)
	ON_CBN_SELCHANGE(IDC_COMBO_DFXEFFICIENTLENGTH, OnSelchangeComboDfxefficientlength)
	ON_CBN_SELCHANGE(IDC_COMBO_TERRAINSTYLE, OnSelchangeComboTerrainstyle)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CNewScheme message handlers

//初始化数据
void CNewScheme::InitData()
{
	m_pRecordset.CreateInstance("ADODB.Recordset");   
	
	//线路等级
	m_CBrailwayGrade.ResetContent();
	m_CBrailwayGrade.AddString("Ⅰ级铁路");
	m_CBrailwayGrade.AddString("Ⅱ级铁路");
	m_CBrailwayGrade.AddString("Ⅲ级铁路");
	m_CBrailwayGrade.SetCurSel(0); //设置选择项
	OnSelchangeComboGrade();//线路等级变化时

	//牵引类型
	m_CBdraughtStyle.ResetContent();
	m_CBdraughtStyle.AddString("电力");
	m_CBdraughtStyle.AddString("内燃");
	m_CBdraughtStyle.SetCurSel(0); //设置选择项
	OnSelchangeComboDraughtstyle();//牵引类型变化时
		
	//工程条件
	m_CBengineeringCondition.ResetContent();
	m_CBengineeringCondition.AddString("一般地段");
	m_CBengineeringCondition.AddString("困难地段");
	m_CBengineeringCondition.SetCurSel(0); //设置选择项
	OnSelchangeComboEngineeringcondition(); //工程条件变化时

	//地形分类
	m_CBterrainStyle.ResetContent();
	m_CBterrainStyle.AddString("平原");
	m_CBterrainStyle.AddString("丘陵");
	m_CBterrainStyle.AddString("山区");
	m_CBterrainStyle.SetCurSel(0); //设置选择项

	//到发线有效长度
	m_CBdfxefficientLength.ResetContent();
	m_CBdfxefficientLength.AddString("1050");
	m_CBdfxefficientLength.AddString("850");
	m_CBdfxefficientLength.AddString("750");
	m_CBdfxefficientLength.AddString("650");
	m_CBdfxefficientLength.AddString("550");
	m_CBdfxefficientLength.SetCurSel(0); //设置选择项
	OnSelchangeComboDfxefficientlength();//到发线长度条件变化时
	
	//闭塞方式
	m_CBbsStyle.ResetContent();
	m_CBbsStyle.AddString("半自动闭塞");
	m_CBbsStyle.AddString("自动闭塞");
	m_CBbsStyle.AddString("列控一体化CTC");
	m_CBbsStyle.SetCurSel(0); //设置选择项
	
	

	
/*
	if(!m_tooltip.Create(this))
	{
		TRACE("不能创建工具提示工具!");
	}
	else
	{
		CEdit *pedit=(CEdit*)GetDlgItem(IDC_EDIT_MINPC);
		m_tooltip.AddTool(pedit,IDS_RAILWAYGRADE);
		
		m_tooltip.Activate(TRUE);
	}*/


	
}

//初始化信息	
BOOL CNewScheme::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	InitData();//初始化数据
	LoadSchemeNmae("");	//根据项目名称从数据库中加载方案名称
	GetMaxSlopePdRules(); //从规范中获取中最大坡度值
	GetMaxPddscRules(); //从规范中获取中最大坡度代数差
	return TRUE; 
}

//对项目方案信息进行检查
BOOL CNewScheme::CheckData(BOOL bAdd)
{
	CString tt,strtilte;
	if(bAdd==TRUE)
	{
		strtilte="增加方案";
	}
	else
	{
		strtilte="修改方案";
	}
	int nsel=m_CBschemeName.GetCurSel();
	if(nsel<0)
	{
		m_CBschemeName.GetWindowText(m_schemename);
	}
	else
	{
		m_CBschemeName.GetLBText(nsel,m_schemename);
	}

	if(bAdd==TRUE)
	{
		for(int i=0;i<m_CBschemeName.GetCount();i++)
		{
			m_CBschemeName.GetLBText(i,tt);
			if(strcmp(tt,m_schemename)==0)
			{
				tt.Format("方案[%s]已存在!",m_schemename);
				MessageBox(tt,strtilte,MB_ICONSTOP);
				return FALSE;
			}
		}
	}
	else if(bAdd==FALSE)
	{
		for(int i=0;i<m_CBschemeName.GetCount();i++)
		{
			m_CBschemeName.GetLBText(i,tt);
			if(strcmp(tt,m_schemename)==0 &&strcmp(tt,m_oldschemename)!=0) 
			{
				tt.Format("方案[%s]已存在!",m_schemename);
				MessageBox(tt,strtilte,MB_ICONSTOP);
				return FALSE;
			}
		}
	}


	if(m_minRadius<m_criterionMinR)
	{
		int m_answer=MessageBox("最小曲线半径小于规范值,确定采用此半径值吗?","最小曲线半径检查",MB_ICONQUESTION||MB_YESNO);
		if(m_answer!=1) 
	
	
		{
			CEdit *pedit=(CEdit*)GetDlgItem(IDC_EDIT_MINRADIUS);
			pedit->SetSel(0,-1);
			pedit->SetFocus();
			return FALSE;
		}
	}		
	

	if(m_maxSlopePd>m_criterionmaxSlopePD)
	{
		MessageBox("最大坡度不能大于规范值",strtilte,MB_ICONSTOP);
		return FALSE;
	}

	if(m_maxSlopePddsc>m_criterionmaxSlopePddsc)
	{
		MessageBox("最大坡度差不能大于规范值",strtilte,MB_ICONSTOP);
		return FALSE;
	}
	
	if(m_minSLopePc<m_criterionminSlopePc)
	{
		MessageBox("最小坡长不能小于规范值",strtilte,MB_ICONSTOP);
		return FALSE;
	}


	return TRUE;

}

//保存方案 
void CNewScheme::OnButtonSave() 
{
	Add_Edit_Data(TRUE);//增加后保存方案信息到数据库中 
	
}


//方案名称变化时
void CNewScheme::OnSelchangeComboSchemename() 
{
	CString tt;
	if(m_CBschemeName.GetCount()>0)
	{
		m_CBschemeName.GetLBText(m_CBschemeName.GetCurSel(),m_oldschemename);
		LoadData(m_oldschemename);//根据方案名称,从数据库中加载项目数据
		GetMinRfromCriterion();//得到规范的最小曲线半径 
//		m_CBdesignSpeed.GetLBText(m_CBdesignSpeed.GetCurSel(),tt);
//		int mspeed=atoi(tt);
//		
//		m_criterionMinR=myDesingScheme.GetMinRfromCriterion(mspeed, m_CBengineeringCondition.GetCurSel());
		this->UpdateData(FALSE);
	}

}

//修改方案 
void CNewScheme::OnButtonEdit() 
{
	this->UpdateData();
	CString strSql;
	strSql.Format("DELETE  from  Scheme WHERE 方案名称='%s'",m_oldschemename);  
	try
	{
		hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 
		if(!SUCCEEDED(hr))
		{
			MessageBox("删除失败!","删除方案",MB_ICONINFORMATION);
			return;
		}
	}
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		MessageBox("删除失败!","删除方案",MB_ICONINFORMATION);
	} 	

	Add_Edit_Data(FALSE);//修改后保存方案信息到数据库中 
	
}

//根据项目名称从数据库中加载方案名称
void CNewScheme::LoadSchemeNmae(CString mstrschemename)
{
	CString strSql="Select *   from  Scheme ORDER BY 方案名称 ASC";  
	try
	{
		m_pRecordset->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 
	
	
	CString tt;
	m_CBschemeName.ResetContent(); //清空
	while(!m_pRecordset->adoEOF)
	{
		Thevalue = m_pRecordset->GetCollect("方案名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			m_CBschemeName.AddString(tt);
		}
		m_pRecordset->MoveNext();
	}
	m_pRecordset->Close();
	if(m_CBschemeName.GetCount()>0)
	{
		if(mstrschemename.IsEmpty())
			m_CBschemeName.SetCurSel(0);
		else
		{
			for(int i=0;i<m_CBschemeName.GetCount();i++)
			{
				CString tt2;
				m_CBschemeName.GetLBText(i,tt2);
				if(strcmp(tt2,mstrschemename)==0)
				{
					m_CBschemeName.SetCurSel(i);
					break;
				}
			}
		}
		OnSelchangeComboSchemename();//方案名称变化时		
	}
}

//根据方案名称,从数据库中加载项目数据
void CNewScheme::LoadData(CString strschemeName)
{
	CString strSql;
	strSql.Format("Select *   from  Scheme WHERE 方案名称 ='%s'",strschemeName);  
	try
	{
		m_pRecordset->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	
	CString tt,tt2;
	int i;
	
	if(!m_pRecordset->adoEOF)
	{
		Thevalue = m_pRecordset->GetCollect("设计等级"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			for(i=0;i<m_CBrailwayGrade.GetCount();i++)
			{
				m_CBrailwayGrade.GetLBText(i,tt2);
				if(strcmp(tt,tt2)==0)
				{
					m_CBrailwayGrade.SetCurSel(i);
					break;
				}
			}
		}
	
		Thevalue = m_pRecordset->GetCollect("牵引种类"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			for(i=0;i<m_CBdraughtStyle.GetCount();i++)
			{
				m_CBdraughtStyle.GetLBText(i,tt2);
				if(strcmp(tt,tt2)==0)
				{
					m_CBdraughtStyle.SetCurSel(i);
					break;
				}
			}
		}

		Thevalue = m_pRecordset->GetCollect("机车类型"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			for(i=0;i<m_CBlocomotiveStyle.GetCount();i++)
			{
				m_CBlocomotiveStyle.GetLBText(i,tt2);
				if(strcmp(tt,tt2)==0)
				{
					m_CBlocomotiveStyle.SetCurSel(i);
					break;
				}
			}
		}
		
		Thevalue = m_pRecordset->GetCollect("闭塞方式"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			for(i=0;i<m_CBbsStyle.GetCount();i++)
			{
				m_CBbsStyle.GetLBText(i,tt2);
				if(strcmp(tt,tt2)==0)
				{
					m_CBbsStyle.SetCurSel(i);
					break;
				}
			}
		}


		Thevalue = m_pRecordset->GetCollect("地形地别"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			for(i=0;i<m_CBterrainStyle.GetCount();i++)
			{
				m_CBterrainStyle.GetLBText(i,tt2);
				if(strcmp(tt,tt2)==0)
				{
					m_CBterrainStyle.SetCurSel(i);
					break;
				}
			}
		}
	
		Thevalue = m_pRecordset->GetCollect("工程条件"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			for(i=0;i<m_CBengineeringCondition.GetCount();i++)
			{
				m_CBengineeringCondition.GetLBText(i,tt2);
				if(strcmp(tt,tt2)==0)
				{
					m_CBengineeringCondition.SetCurSel(i);
					break;
				}
			}
		}

		
		Thevalue = m_pRecordset->GetCollect("设计速度"); 
		int mspeed=(short)Thevalue;
		for(i=0;i<m_CBdesignSpeed.GetCount();i++)
		{
			m_CBdesignSpeed.GetLBText(i,tt);
			if(atoi(tt)==mspeed)
			{
				m_CBdesignSpeed.SetCurSel(i);
				break;
			}
		}

		Thevalue = m_pRecordset->GetCollect("最小曲线半径"); 
		m_minRadius=(long)Thevalue;
	
		Thevalue = m_pRecordset->GetCollect("到发线有效长度"); 
		int mdfxLength=(short)Thevalue;
		for(i=0;i<m_CBdfxefficientLength.GetCount();i++)
		{
			m_CBdfxefficientLength.GetLBText(i,tt);
			if(atoi(tt)==mdfxLength)
			{
				m_CBdfxefficientLength.SetCurSel(i);
				break;
			}
		}

		Thevalue = m_pRecordset->GetCollect("最小坡长"); 
		m_minSLopePc=(double)Thevalue;	
		
		Thevalue = m_pRecordset->GetCollect("最大坡度"); 
		m_maxSlopePd=(double)Thevalue;	

		Thevalue = m_pRecordset->GetCollect("最大坡度差"); 
		m_maxSlopePddsc=(double)Thevalue;	

		Thevalue = m_pRecordset->GetCollect("起点里程"); 
		m_startLC=(double)Thevalue;		
		
		Thevalue = m_pRecordset->GetCollect("终点里程"); 
		m_endLC=(double)Thevalue;		
		
	}
	m_pRecordset->Close(); //关闭记录集
 
}

//返回
void CNewScheme::OnButtonExit() 
{
	EndDialog(IDCANCEL);
	
}

//线路等级变化时
void CNewScheme::OnSelchangeComboGrade() 
{
	switch(m_CBrailwayGrade.GetCurSel())
	{
	case 0:
		m_CBdesignSpeed.ResetContent();
		m_CBdesignSpeed.AddString("160 km/h");
		m_CBdesignSpeed.AddString("140 km/h");
		m_CBdesignSpeed.AddString("120 km/h");
		m_CBdesignSpeed.SetCurSel(0);
		break;
	case 1:
		m_CBdesignSpeed.ResetContent();
		m_CBdesignSpeed.AddString("120 km/h");
		m_CBdesignSpeed.AddString("100 km/h");
		m_CBdesignSpeed.AddString("80 km/h");
		m_CBdesignSpeed.SetCurSel(0);
		break;
	case 2:
		
		
		m_CBdesignSpeed.ResetContent();
		m_CBdesignSpeed.AddString("60 km/h");
		m_CBdesignSpeed.AddString("50 km/h");
		m_CBdesignSpeed.AddString("40 km/h");
		m_CBdesignSpeed.SetCurSel(0);
		break;
	case 3:
		
		
		m_CBdesignSpeed.ResetContent();
		m_CBdesignSpeed.AddString("50 km/h");
		m_CBdesignSpeed.AddString("40 km/h");
		m_CBdesignSpeed.AddString("30 km/h");
		m_CBdesignSpeed.SetCurSel(0);
		break;
	}
	
	GetMaxSlopePdRules(); //从规范中获取中最大坡度值
	GetMaxPddscRules(); //从规范中获取中最大坡度代数差

}

//设计速度变化时
void CNewScheme::OnSelchangeComboDesignspeed() 
{
	this->UpdateData();
	m_criterionMinR=GetMinRfromCriterion();
	this->UpdateData(FALSE);
	
}

//得到规范的最小曲线半径 
int CNewScheme::GetMinRfromCriterion()
{
	
	CString tt;
	m_CBdesignSpeed.GetLBText(m_CBdesignSpeed.GetCurSel(),tt);
	int mspeed=atoi(tt);

	
    switch(mspeed)
	{
	case 160:
		if(m_CBengineeringCondition.GetCurSel()==0) 
			m_criterionMinR=2000;	
		else
			m_criterionMinR=1600;	
		break;
	case 140:
		if(m_CBengineeringCondition.GetCurSel()==0) 
			m_criterionMinR=1600;	
		else
			m_criterionMinR=1200;	
		break;
	case 120:
		if(m_CBengineeringCondition.GetCurSel()==0) 
			m_criterionMinR=1200;	
		else
			m_criterionMinR=800;	
		break;
	case 100:
		if(m_CBengineeringCondition.GetCurSel()==0) 
			m_criterionMinR=800;	
		else
			m_criterionMinR=600;	
		break;
		
	case 80:
		if(m_CBengineeringCondition.GetCurSel()==0) 
			m_criterionMinR=600;	
		else
			m_criterionMinR=500;	
		break;
	}

	this->UpdateData(FALSE);
	
	return m_criterionMinR;

}

//工程条件变化时
void CNewScheme::OnSelchangeComboEngineeringcondition() 
{
	
	this->UpdateData();
	m_criterionMinR=GetMinRfromCriterion();//得到规范的最小曲线半径 
	this->UpdateData(FALSE);
	GetMaxPddscRules();//得到规范的默认值 (最大坡度代数差)
}

//增加或修改后保存方案信息到数据库中 
void CNewScheme::Add_Edit_Data(BOOL bAddorEdit)
{
	this->UpdateData();
	if(CheckData(bAddorEdit)==FALSE)//对项目方案信息进行检查,如果失败,返回
		return;
	BeginWaitCursor();
	
	CString tt;
	CString strdesigngrade,strdraughtstyle,strCBbsStyle,strBlocomotiveStyle;
	CString strterrainstyle,strengineeringcondition;
	
	m_CBrailwayGrade.GetLBText(m_CBrailwayGrade.GetCurSel(),strdesigngrade);	//线路等级
	m_CBdraughtStyle.GetLBText(m_CBdraughtStyle.GetCurSel(),strdraughtstyle);	//牵引种类	
 
	m_CBlocomotiveStyle.GetLBText(m_CBlocomotiveStyle.GetCurSel(),strBlocomotiveStyle);	//机车类型
	m_CBbsStyle.GetLBText(m_CBbsStyle.GetCurSel(),strCBbsStyle); //闭塞方式
	m_CBterrainStyle.GetLBText(m_CBterrainStyle.GetCurSel(),strterrainstyle);	//地形类型
	m_CBengineeringCondition.GetLBText(m_CBengineeringCondition.GetCurSel(),strengineeringcondition); //工程条件
	
	
	int mdesignspeed;
	m_CBdesignSpeed.GetLBText(m_CBdesignSpeed.GetCurSel(),tt); //设计速度
	mdesignspeed=atoi(tt);
    

	int mdxflength;
	m_CBdfxefficientLength.GetLBText(m_CBdfxefficientLength.GetCurSel(),tt);//到发线有效长
	mdxflength=atoi(tt);
	
	tt.Format("INSERT INTO Scheme VALUES(\
	   '%s','%s','%s','%s','%s','%s','%s',\
	   %d,%d,%d,%.3f,%.3f,%.3f,%.3f,%.3f)",\
	   m_schemename,
	   strdesigngrade,
	   strdraughtstyle,
	   strBlocomotiveStyle,
	   strCBbsStyle,
	   strterrainstyle,
	   strengineeringcondition,
	   mdesignspeed,
	   m_minRadius,
	   mdxflength,
	   m_minSLopePc,
	   m_maxSlopePd,
	   m_maxSlopePddsc,
	   m_startLC,
	   0);
	try
	{
		hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
		if(!SUCCEEDED(hr))
		{
			if(bAddorEdit==TRUE)
			{
				MessageBox("方案增加失败!","增加新方案",MB_ICONINFORMATION);
				return;
			}
			else
			{
				MessageBox("方案修改失败!","修改方案",MB_ICONINFORMATION);
				return;
			}
		}
	}
	catch(_com_error& e)	//错误捕捉 
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"修改方案",MB_ICONINFORMATION);
		return;
	} 
	if(bAddorEdit==TRUE) //增加方案
	{
		MessageBox("方案保存成功!","增加新方案",MB_ICONINFORMATION);
		LoadSchemeNmae(m_schemename);	//根据项目名称从数据库中加载方案名称
	}
	else   //修改方案
	{
		MessageBox("方案修改成功!","修改方案",MB_ICONINFORMATION);
	}
}

//删除方案
void CNewScheme::OnButtonDelete() 
{
	this->UpdateData();

	int manwer=MessageBox("是否删除方案?","删除方案",MB_ICONINFORMATION|MB_YESNO);
	if(manwer==6)
		DeleteSchemeTableData();//删除所有与删除方案对应的数据表中数据

	LoadSchemeNmae("");//根据项目名称从数据库中加载方案名称
	
}

//删除项目对应的数据表中的数据
void CNewScheme::DeleteSchemeTableData()
{
	CString strSql;
	strSql.Format("DELETE  from  Scheme WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	strSql.Format("DELETE  from  Scheme_plane_CureveData WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 
	
	strSql.Format("DELETE  from  Scheme_plane_CureveData_XY WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	strSql.Format("DELETE  from  T3DLineZxCorrdinate WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	strSql.Format("DELETE  from  T3DLineZxCorrdinateZDM WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 
	
	strSql.Format("DELETE  from  ZDmJDCurve WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 
	
	strSql.Format("DELETE  from  zdmSegmentGeoFeature WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	strSql.Format("DELETE  from  Tunnel WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	strSql.Format("DELETE  from  Bridge WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	strSql.Format("DELETE  from  StationTable WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	strSql.Format("DELETE  from  SchemeDesignResult WHERE 方案名称='%s'",m_oldschemename);  
	hr=theApp.m_pConnection->Execute((_bstr_t)strSql,&RecordsAffected,adCmdText); 

	//数组清空
	myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_RailwayBP3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.TunnelInfor[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.BridgeInfor[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_RailwayZhenMu[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_RailwayTieGui[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.JDCurveElementss[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].RemoveAll();
	
	
	if(SUCCEEDED(hr))
	{
		MessageBox("方案删除成功!","删除方案",MB_ICONINFORMATION);
		theApp.b_haveDeletScheme=TRUE;//标识是否删除方案,如果删除,则要求视图重新绘制
	}
	else
		MessageBox("方案删除失败!","删除方案",MB_ICONINFORMATION);
		
}


BOOL CNewScheme::PreTranslateMessage(MSG* pMsg) 
{
//	m_tooltip.RelayEvent(pMsg);
	return CDialog::PreTranslateMessage(pMsg);
}

void CNewScheme::OnMouseMove(UINT nFlags, CPoint point) 
{
	CString strText;
	switch(m_CBrailwayGrade.GetCurSel())
	{
	case 0:
		strText="Ⅰ级铁路,铁路网中起骨干作用的铁路,或近期年客货运量大于或等于20Mt";
		break;
	case 1:
		strText="Ⅱ级铁路,铁路网中起联络、辅助作用的铁路,或近期年客货运量小于20Mt且大于10Mt";
		break;
	case 2:
		strText="Ⅲ级铁路,为某一地区或企业服务的铁路,或近期年客货运量小于10Mt且大于5Mt";
		break;
	case 3:
		strText="Ⅳ级铁路,为某一地区或企业服务的铁路,或近期年客货运量小于5Mt";
		break;
	}
/*
	CComboBox *pComboBox=(CComboBox*)GetDlgItem(IDC_COMBO_GRADE);
	CRect rect;
	pComboBox->GetWindowRect(&rect);   
	this->SetCapture();
	ScreenToClient(&rect);   
	if(point.x>=rect.left-1 && point.x<=rect.right+1 && point.y<=rect.bottom+1 && point.y>=rect.top-1)
	{   
		m_tooltip.AddTool(pComboBox,strText);
		
	}   
	ReleaseCapture();
*/

	
	CDialog::OnMouseMove(nFlags, point);
}

BOOL CNewScheme::DestroyWindow() 
{
	_RecordsetPtr   m_Recordset;
	_variant_t Thevalue;  
	variant_t RecordsAffected;
	m_Recordset.CreateInstance("ADODB.Recordset");   
	CString strsql;
	
	strsql.Format("select * from Scheme ORDER BY 方案名称 ASC");
	if(m_Recordset->State)
		m_Recordset->Close();
	try
	{
		m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"打开数据库表",MB_ICONINFORMATION);
		m_Recordset->Close();
		return 0 ;
	} 
	
	m_SchemeNames.RemoveAll();
	while(!m_Recordset->adoEOF)
	{
		Thevalue = m_Recordset->GetCollect("方案名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			CString tt=Thevalue.bstrVal;
			m_SchemeNames.Add(tt);
		}
		m_Recordset->MoveNext();
	}
	m_Recordset->Close();
	
	if(m_SchemeNames.GetSize()>0)
	{
		CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		pMainFrame->AddSchemeName();
	}

	return CDialog::DestroyWindow();
}


//牵引类型变化时 
void CNewScheme::OnSelchangeComboDraughtstyle() 
{
	
	m_CBlocomotiveStyle.ResetContent();
	switch(m_CBdraughtStyle.GetCurSel())
	{
	case 0: 
		m_CBlocomotiveStyle.AddString("SS1");
		m_CBlocomotiveStyle.AddString("SS3");
		m_CBlocomotiveStyle.AddString("SS4");
		m_CBlocomotiveStyle.AddString("SS4B");
		m_CBlocomotiveStyle.AddString("SS6B");
		m_CBlocomotiveStyle.AddString("SS7");
		m_CBlocomotiveStyle.AddString("SS8");
		break;
	case 1:	
		m_CBlocomotiveStyle.AddString("DF4");
		m_CBlocomotiveStyle.AddString("DF4B");
		m_CBlocomotiveStyle.AddString("DF4E");
		m_CBlocomotiveStyle.AddString("DF6");
		m_CBlocomotiveStyle.AddString("DF8");
		m_CBlocomotiveStyle.AddString("DF10D");
		m_CBlocomotiveStyle.AddString("DF11");
		break;
		
	}
	m_CBlocomotiveStyle.SetCurSel(0);

	GetMaxSlopePdRules(); //从规范中获取中最大坡度值
	
	  
}


//到发线长度条件变化时
void CNewScheme::OnSelchangeComboDfxefficientlength() 
{

	int index=m_CBdfxefficientLength.GetCurSel();
	switch(index)
	{
		case 0:
			m_criterionminSlopePc=400;
			break;
		case 1:
			m_criterionminSlopePc=350;
			break;
		case 2:
			m_criterionminSlopePc=300;
			break;
		case 3:
			m_criterionminSlopePc=250;
			break;
		default:
			m_criterionminSlopePc=200;
			break;
	}

	this->UpdateData(FALSE);
	GetMaxPddscRules(); //从规范中获取中最大坡度代数差
	
	
}

//地形类别条件变化时
void CNewScheme::OnSelchangeComboTerrainstyle() 
{
	GetMaxSlopePdRules(); //从规范中获取中最大坡度值
	GetMaxPddscRules(); //从规范中获取中最大坡度代数差
	
}


//从规范中获取中最大坡度代数差
void CNewScheme::GetMaxPddscRules()
{
	int m_grade=m_CBrailwayGrade.GetCurSel(); 
	
	
	int  CBdfxefficientLength=m_CBdfxefficientLength.GetCurSel();
	
	int m_engineeringCondition=m_CBengineeringCondition.GetCurSel();

	
	switch(m_grade)
	{
	case  0:  
	case 1:
		switch(CBdfxefficientLength) 
		{
		case 0:
			if(m_engineeringCondition==0)
				m_criterionmaxSlopePddsc=8;
			else
				m_criterionmaxSlopePddsc=10;
			break;
		case 1:
			if(m_engineeringCondition==0)
				m_criterionmaxSlopePddsc=10;
			else
				m_criterionmaxSlopePddsc=12;
			break;
		case 2:
			if(m_engineeringCondition==0)
				m_criterionmaxSlopePddsc=12;
			else
				m_criterionmaxSlopePddsc=15;
			break;
		case 3:
			if(m_engineeringCondition==0)
				m_criterionmaxSlopePddsc=15;
			else
				m_criterionmaxSlopePddsc=18;
			break;
		}
		break;
		case 2:
			switch(CBdfxefficientLength) 
			{
			case 0:
				if(m_engineeringCondition==0)
					m_criterionmaxSlopePddsc=10;
				else
					m_criterionmaxSlopePddsc=12;
				break;
			case 1:
				if(m_engineeringCondition==0)
					m_criterionmaxSlopePddsc=12;
				else
					m_criterionmaxSlopePddsc=15;
				break;
			case 2:
				if(m_engineeringCondition==0)
					m_criterionmaxSlopePddsc=15;
				else
					m_criterionmaxSlopePddsc=18;
				break;
			case 3:
				if(m_engineeringCondition==0)
					m_criterionmaxSlopePddsc=18;
				else
					m_criterionmaxSlopePddsc=20;
			case 4:
				if(m_engineeringCondition==0)
					m_criterionmaxSlopePddsc=20;
				else
					m_criterionmaxSlopePddsc=25;
				break;
			}
			break;
	}
	this->UpdateData(FALSE);

}


 //从规范中获取中最大坡度值
void CNewScheme::GetMaxSlopePdRules()
{
	int m_grade=m_CBrailwayGrade.GetCurSel(); 
 

	int m_terrainstyle=m_CBterrainStyle.GetCurSel(); 
 
	int m_draughtStyle=m_CBdraughtStyle.GetCurSel();
 

	float MaxSlopePdRules;

	switch(m_grade)
	{
	case  0:  
		switch(m_terrainstyle)//地形地别
		{
		case 0: 
			MaxSlopePdRules=6.0;
			break;
		case 1:
			if(m_draughtStyle==0) 
				MaxSlopePdRules=12.0;
			else
				MaxSlopePdRules=9.0;
			break;
		case 2:
			if(m_draughtStyle==0) 
				MaxSlopePdRules=15.0;
			else
				MaxSlopePdRules=12.0;
			break;
		}
		break;
	case  1:  
			switch(m_terrainstyle)
			{
			case 0: 
				MaxSlopePdRules=6.0;
				break;
			case 1:
				if(m_draughtStyle==0) 
					MaxSlopePdRules=15.0;
				else
					MaxSlopePdRules=9.0;
				break;
			case 2:
				if(m_draughtStyle==0) 
					MaxSlopePdRules=20.0;
				else
					MaxSlopePdRules=15.0;
				break;
			}
			break;
		case  2:  
				switch(m_terrainstyle)
				{
				case 0: 
					if(m_draughtStyle==0) 
						MaxSlopePdRules=9.0;
					else
						MaxSlopePdRules=8.0;
					break;					break;
				case 1:
					if(m_draughtStyle==0) 
						MaxSlopePdRules=18.0;
					else
						MaxSlopePdRules=12.0;
					break;
				case 2:
					if(m_draughtStyle==0) 
						MaxSlopePdRules=25.0;
					else
						MaxSlopePdRules=18.0;
					break;
				}
				break;
	}

	m_criterionmaxSlopePD=MaxSlopePdRules;
	this->UpdateData(FALSE);
	
}


