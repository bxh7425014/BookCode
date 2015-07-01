// BridgeSet.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "BridgeSet.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CBridgeSet dialog


CBridgeSet::CBridgeSet(CWnd* pParent /*=NULL*/)
	: CDialog(CBridgeSet::IDD, pParent)
{
	//{{AFX_DATA_INIT(CBridgeSet)
	m_Bridge_HLSpace = 0.0f;
	m_Bridge_HLWidth = 0.0f;
	m_Bridge_HLHeight = 0.0f;
	m_Bridge_QDSpace = 0.0f;
	m_Bridge_SetHeight = 0.0f;
	m_Bridge_HPangle = 0;
	//}}AFX_DATA_INIT
}


void CBridgeSet::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CBridgeSet)
	DDX_Text(pDX, IDC_EDIT_HLSPACE, m_Bridge_HLSpace);
	DDX_Text(pDX, IDC_EDIT_HLWIDTH, m_Bridge_HLWidth);
	DDX_Text(pDX, IDC_EDIT_HLHEIGHT, m_Bridge_HLHeight);
	DDX_Text(pDX, IDC_EDIT_QDSPACE, m_Bridge_QDSpace);
	DDX_Text(pDX, IDC_EDIT_SETBRIDGEHEIGHT, m_Bridge_SetHeight);
	DDX_Text(pDX, IDC_EDIT_HPANGLE, m_Bridge_HPangle);
	DDX_Control(pDX, IDC_MSFLEXGRID, m_gridColor);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CBridgeSet, CDialog)
	//{{AFX_MSG_MAP(CBridgeSet)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CBridgeSet message handlers

//响应“确定”按钮消息
void CBridgeSet::OnOK() 
{
	this->UpdateData(); //数据更新
	
	CDialog::OnOK();
}

//初始化信息
BOOL CBridgeSet::OnInitDialog() 
{
	CDialog::OnInitDialog();
	//初始化表格控件
	m_gridColor.SetColWidth(0,3000);//设置列宽
	m_gridColor.SetRowHeight(0,400);//设置行高
	m_gridColor.SetRow(0); //设置当前行为第0行
	m_gridColor.SetCol(0);//设置当前列为第0列
	m_gridColor.SetCellBackColor(RGB(m_bridgeColorR,m_bridgeColorG,m_bridgeColorB));//设置背景色
	
	return TRUE;  
}

BEGIN_EVENTSINK_MAP(CBridgeSet, CDialog)
    //{{AFX_EVENTSINK_MAP(CBridgeSet)
	ON_EVENT(CBridgeSet, IDC_MSFLEXGRID, -600 /* Click */, OnClickMsflexgrid, VTS_NONE)
	//}}AFX_EVENTSINK_MAP
END_EVENTSINK_MAP()

//响应单击表格控件消息
void CBridgeSet::OnClickMsflexgrid() 
{
	CColorDialog colorDlg;
	
	if(colorDlg.DoModal()==IDOK)
	{
		long m_color=colorDlg.GetColor(); //得到颜色
		m_bridgeColorR=GetRValue(m_color); //红色分量 
		m_bridgeColorG=GetGValue(m_color);//绿色分量 
		m_bridgeColorB=GetBValue(m_color);//蓝色分量 
		m_gridColor.SetRow(0); //设置当前行为第0行
		m_gridColor.SetCol(0);//设置当前列为第0列
		m_gridColor.SetCellBackColor(m_color);//设置背景色
	}	
	
}
