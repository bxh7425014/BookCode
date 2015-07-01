// PLaneRL0.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "PLaneRL0.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CPLaneRL0 dialog


CPLaneRL0::CPLaneRL0(CWnd* pParent /*=NULL*/)
	: CDialog(CPLaneRL0::IDD, pParent)
{
	//{{AFX_DATA_INIT(CPLaneRL0)
	m_L0 = 0;
	m_minR = 0;
	m_minL0 = 0;
	m_strrangR = _T("");
	m_ID = _T("");
	//}}AFX_DATA_INIT
}


void CPLaneRL0::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPLaneRL0)
	DDX_Control(pDX, IDC_COMBO_R, m_CBRadius);
	DDX_Text(pDX, IDC_EDIT_L0, m_L0);
	DDX_Text(pDX, IDC_EDIT_MINR, m_minR);
	DDX_Text(pDX, IDC_EDIT_MINl0, m_minL0);
	DDX_Text(pDX, IDC_EDIT_RANGER, m_strrangR);
	DDX_Text(pDX, IDC_EDIT_ID, m_ID);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CPLaneRL0, CDialog)
	//{{AFX_MSG_MAP(CPLaneRL0)
	ON_CBN_SELCHANGE(IDC_COMBO_R, OnSelchangeComboR)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CPLaneRL0 message handlers


//初始化信息	
BOOL CPLaneRL0::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	//根据项目的设计速度，计算可以选择的曲线半径和曲线半径范围
	switch(	myDesingScheme.SchemeDatass[m_currentSchemeIndexs].designspeed)
	{
	case 160: //设计速度为160Km/h
		m_strrangR="2500～5000";//曲线半径范围
		m_CBRadius.ResetContent();
		m_CBRadius.AddString("1600");
		m_CBRadius.AddString("1800");
		m_CBRadius.AddString("2000");
		m_CBRadius.AddString("2500");
		m_CBRadius.AddString("2800");
		m_CBRadius.AddString("3000");
		m_CBRadius.AddString("3500");
		m_CBRadius.AddString("4000");
		m_CBRadius.AddString("4500");
		m_CBRadius.AddString("5000");
		m_CBRadius.AddString("6000");
		m_CBRadius.AddString("7000");
		m_CBRadius.AddString("8000");
		m_CBRadius.AddString("10000");
		m_CBRadius.AddString("12000");
		m_CBRadius.SetCurSel(0); //设置索引
		break;
	case 140:  //设计速度为140Km/h
		m_strrangR="2000～4000";
		m_CBRadius.ResetContent();
		m_CBRadius.AddString("1200");
		m_CBRadius.AddString("1400");
		m_CBRadius.AddString("1600");
		m_CBRadius.AddString("1800");
		m_CBRadius.AddString("2000");
		m_CBRadius.AddString("2500");
		m_CBRadius.AddString("2800");
		m_CBRadius.AddString("3000");
		m_CBRadius.AddString("3500");
		m_CBRadius.AddString("4000");
		m_CBRadius.AddString("4500");
		m_CBRadius.AddString("5000");
		m_CBRadius.AddString("6000");
		m_CBRadius.AddString("7000");
		m_CBRadius.AddString("8000");
		m_CBRadius.AddString("10000");
		m_CBRadius.AddString("12000");
		m_CBRadius.SetCurSel(0);
		break;
	case 120: //设计速度为120Km/h
		m_strrangR="1600～3000";
		m_CBRadius.ResetContent();
		m_CBRadius.AddString("800");
		m_CBRadius.AddString("1000");
		m_CBRadius.AddString("1200");
		m_CBRadius.AddString("1400");
		m_CBRadius.AddString("1600");
		m_CBRadius.AddString("1800");
		m_CBRadius.AddString("2000");
		m_CBRadius.AddString("2500");
		m_CBRadius.AddString("2800");
		m_CBRadius.AddString("3000");
		m_CBRadius.AddString("3500");
		m_CBRadius.AddString("4000");
		m_CBRadius.AddString("4500");
		m_CBRadius.AddString("5000");
		m_CBRadius.AddString("6000");
		m_CBRadius.AddString("7000");
		m_CBRadius.AddString("8000");
		m_CBRadius.AddString("10000");
		m_CBRadius.AddString("12000");
		m_CBRadius.SetCurSel(0);	
		break;
	case 100: //设计速度为100Km/h
		m_strrangR="1200～2500";
		m_CBRadius.ResetContent();
		m_CBRadius.AddString("600");
		m_CBRadius.AddString("700");
		m_CBRadius.AddString("800");
		m_CBRadius.AddString("1000");
		m_CBRadius.AddString("1200");
		m_CBRadius.AddString("1400");
		m_CBRadius.AddString("1600");
		m_CBRadius.AddString("1800");
		m_CBRadius.AddString("2000");
		m_CBRadius.AddString("2500");
		m_CBRadius.AddString("2800");
		m_CBRadius.AddString("3000");
		m_CBRadius.AddString("3500");
		m_CBRadius.AddString("4000");
		m_CBRadius.AddString("4500");
		m_CBRadius.AddString("5000");
		m_CBRadius.AddString("6000");
		m_CBRadius.AddString("7000");
		m_CBRadius.AddString("8000");
		m_CBRadius.AddString("10000");
		m_CBRadius.AddString("12000");
		break;
	case 80:  //设计速度为80Km/h
		m_strrangR="800～2000";
		m_CBRadius.ResetContent();
		m_CBRadius.AddString("550");
		m_CBRadius.AddString("600");
		m_CBRadius.AddString("700");
		m_CBRadius.AddString("800");
		m_CBRadius.AddString("1000");
		m_CBRadius.AddString("1200");
		m_CBRadius.AddString("1400");
		m_CBRadius.AddString("1600");
		m_CBRadius.AddString("1800");
		m_CBRadius.AddString("2000");
		m_CBRadius.AddString("2500");
		m_CBRadius.AddString("2800");
		m_CBRadius.AddString("3000");
		m_CBRadius.AddString("3500");
		m_CBRadius.AddString("4000");
		m_CBRadius.AddString("4500");
		m_CBRadius.AddString("5000");
		m_CBRadius.AddString("6000");
		m_CBRadius.AddString("7000");
		m_CBRadius.AddString("8000");
		m_CBRadius.AddString("10000");
		m_CBRadius.AddString("12000");
		break;
	}
 
	//根据选择的曲线半径,设置索引
	CString tt;
	m_minR=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].minRadius;
	for(int i=0;i<m_CBRadius.GetCount();i++)
	{
		m_CBRadius.GetLBText(i,tt);
		float mR=atof(tt);
		if(mR==m_minR)
		{
			m_CBRadius.SetCurSel(i);
			break; //退出
		}
	}

	m_L0=m_minL0;
	this->UpdateData(FALSE); //数据更新
	OnSelchangeComboR(); //当改变曲线半径时,计算缓和曲线长
	
	
	return TRUE;  
}

//当改变曲线半径时,计算缓和曲线长
void CPLaneRL0::OnSelchangeComboR() 
{
	this->UpdateData();

	CString tt;

	switch(	myDesingScheme.SchemeDatass[m_currentSchemeIndexs].designspeed)
	{
		case 160: //设计速度为160Km/h
			m_L0=myDesingScheme.m_minPriorityR160_L0[14-m_CBRadius.GetCurSel()];
			break;
		case 140: //设计速度为140Km/h
			m_L0=myDesingScheme.m_minPriorityR140_L0[16-m_CBRadius.GetCurSel()];
			break;
		case 120:  //设计速度为120Km/h
			m_L0=myDesingScheme.m_minPriorityR120_L0[18-m_CBRadius.GetCurSel()];
			break;
	}

	m_minL0=myDesingScheme.GetMinL0(m_CBRadius.GetCurSel()); //最小缓和曲线长
	this->UpdateData(FALSE); //数据更新
}


//确定
void CPLaneRL0::OnOK() 
{
	this->UpdateData(); //更新数据
	CString tt;
	
	if(m_ID.IsEmpty()) //如果交点ID为空
	{
		MessageBox("交点编号不能为空!","交点编号检查",MB_ICONSTOP);
		return;
	}

	m_CBRadius.GetLBText(m_CBRadius.GetCurSel(),tt); //曲线半径
	R=atoi(tt);
	if(R<m_minR) //如果曲线半径小于最小曲线半径
	{
		MessageBox("曲线半径小于最小曲线半径!","曲线半径检查",MB_ICONSTOP);
		return;
	}
	if(m_L0<m_minL0)
	{
		MessageBox("缓和曲线长度小于最小缓和曲线长度!","缓和曲线长度检查",MB_ICONSTOP);
		return;
	}

	CDialog::OnOK();
}





















