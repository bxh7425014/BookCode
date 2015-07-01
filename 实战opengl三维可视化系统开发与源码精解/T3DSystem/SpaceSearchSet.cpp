// SpaceSearchSet.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "SpaceSearchSet.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CSpaceSearchSet dialog


CSpaceSearchSet::CSpaceSearchSet(CWnd* pParent /*=NULL*/)
	: CDialog(CSpaceSearchSet::IDD, pParent)
{
	//{{AFX_DATA_INIT(CSpaceSearchSet)
	m_shizxLength = 5;
	m_shuzxHeight =20;
	//}}AFX_DATA_INIT
}


void CSpaceSearchSet::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CSpaceSearchSet)
	DDX_Control(pDX, IDC_COMBOL_WIDTH, m_combolWidth);
	DDX_Text(pDX, IDC_EDIT_SHIZXLENGTH, m_shizxLength);
	DDX_Text(pDX, IDC_EDIT_SHUZXHEIGHT, m_shuzxHeight);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CSpaceSearchSet, CDialog)
	//{{AFX_MSG_MAP(CSpaceSearchSet)
	ON_BN_CLICKED(IDC_BUTTON_COLOR, OnButtonColor)
	ON_WM_CTLCOLOR()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CSpaceSearchSet message handlers

// 颜色按钮响应函数
void CSpaceSearchSet::OnButtonColor() 
{
	CColorDialog colorDlg;//定义颜色对话框变量
	if(colorDlg.DoModal()==IDOK)//如果颜色对话框打开成功
	{
		long m_color=colorDlg.GetColor();//得到所选择的颜色
		pbrush.DeleteObject ();//删除pbrush画刷对象
		pbrush.CreateSolidBrush (m_color);//根据所选择的颜色重新创建画刷
		CButton *pbutton=(CButton*)GetDlgItem(IDC_BUTTON_COLOR);
		CRect rect;
		pbutton->GetClientRect(rect);
		pbutton->InvalidateRect(rect,TRUE);//刷新颜色按钮响,
		
		m_QueryColorR=GetRValue(m_color);//得到所选择的颜色的红色
		m_QueryColorG=GetGValue(m_color);//得到所选择的颜色的绿色
		m_QueryColorB=GetBValue(m_color);//得到所选择的颜色的蓝色
	
	}		
}

//根据颜色按钮选择的颜色填充其背景
HBRUSH CSpaceSearchSet::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor) 
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);
	
	if(pWnd->GetDlgCtrlID()== IDC_BUTTON_COLOR)//如果控件ID=IDC_BUTTON_COLOR,用pbrush画刷填按钮背景
		return pbrush;
	else //否则,返回默认画刷
		return hbr;
	
}

//信息初始化
BOOL CSpaceSearchSet::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
//	myedit[0].SubclassDlgItem(IDC_EDIT_SHIZXLENGTH,this);
//	myedit[1].SubclassDlgItem(IDC_EDIT_SHUZXHEIGHT,this);
		
	m_combolWidth.ResetContent();//存储标志线宽度的下拉框清空
	for(int i=1;i<=20;i++)//线宽最大宽度为20
	{
		CString tt;
		tt.Format("%d",i);
		m_combolWidth.AddString(tt);
	}

	//设置下拉框的当前选择项
	if(m_QueryLineWidth>0)
		m_combolWidth.SetCurSel(m_QueryLineWidth-1);
	else
		m_combolWidth.SetCurSel(0);

	this->UpdateData(FALSE);//数据变量更新
	
	//根据标志线颜色创建画刷
	pbrush.CreateSolidBrush(RGB(m_QueryColorR,m_QueryColorG,m_QueryColorB));
	
	return TRUE;  
}

BOOL CSpaceSearchSet::PreTranslateMessage(MSG* pMsg) 
{
 
	if(pMsg->wParam == VK_RETURN)//如果按钮是回车键
	{
		CDialog *pWnd=(CDialog*)GetParent();
		pWnd->NextDlgCtrl ();
		return FALSE;
	}	
	else
		return CDialog::PreTranslateMessage(pMsg);
}


//确定扫钮响应函数
void CSpaceSearchSet::OnOK() 
{
	this->UpdateData();//更新数据
	CString tt;
	m_combolWidth.GetLBText(m_combolWidth.GetCurSel(),tt);//得到
	m_QueryLineWidth=m_combolWidth.GetCurSel();//得到标志线宽度
	CDialog::OnOK();//以IDOK模式关闭退出对话框
}
