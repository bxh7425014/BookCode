// TextureTunnelDm.cpp : implementation file
//

#include "stdafx.h"
#include "T3DSystem.h"
#include "TextureTunnelDm.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CTextureTunnelDm dialog


CTextureTunnelDm::CTextureTunnelDm(CWnd* pParent /*=NULL*/)
	: CDialog(CTextureTunnelDm::IDD, pParent)
{
	//{{AFX_DATA_INIT(CTextureTunnelDm)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}


void CTextureTunnelDm::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CTextureTunnelDm)
	DDX_Control(pDX, IDC_LIST, m_list);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CTextureTunnelDm, CDialog)
	//{{AFX_MSG_MAP(CTextureTunnelDm)
	ON_LBN_SELCHANGE(IDC_LIST, OnSelchangeList)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CTextureTunnelDm message handlers

void CTextureTunnelDm::InitTexture()
{
	CString tt;
	m_list.ResetContent();//清空
	for(int i=1;i<=4;i++)  //纹理文件名称(可根据实际的纹理数进行更改)
	{
		tt.Format("%d.bmp",i);
		m_list.AddString(tt);		//加入列表框控件中		
	}
	
	m_list.SetCurSel(0);//默认选择项为第1项纹理
	OnSelchangeList(); //响应纹理选择改变时的消息
}

BOOL CTextureTunnelDm::OnInitDialog() 
{
	CDialog::OnInitDialog();
	InitTexture();//初始化纹理名称，并填写到列表框控件中 
	return TRUE;  
}

//响应纹理选择改变时的消息
void CTextureTunnelDm::OnSelchangeList() 
{
	m_TextureIndex=m_list.GetCurSel()+1;
	m_list.GetText(m_list.GetCurSel(),m_TunnelDmtextureName);//得到纹理文件名
	CDC *pdc=GetDC();
	CRect rect;
	GetDlgItem(IDC_STATIC_BMP)->GetWindowRect(rect);//得到IDC_STATIC_BMP控件的客户区大小
	this->ScreenToClient(rect);
	CString BitmapFilePath;//将屏幕坐标转换成用户坐标
	BitmapFilePath="./纹理\\隧道洞门\\"+m_TunnelDmtextureName;//纹理文件全路径名称
	myDesingScheme.drawBitmapFormFile(BitmapFilePath,pdc,rect);//将选中的纹理影像在IDC_STATIC_BMP上绘制
	
}
