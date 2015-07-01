// TextureBP.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "TextureBP.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CTextureBP dialog


CTextureBP::CTextureBP(CWnd* pParent /*=NULL*/)
	: CDialog(CTextureBP::IDD, pParent)
{
	//{{AFX_DATA_INIT(CTextureBP)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}


void CTextureBP::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CTextureBP)
	DDX_Control(pDX, IDC_LIST, m_list);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CTextureBP, CDialog)
	//{{AFX_MSG_MAP(CTextureBP)
	ON_LBN_SELCHANGE(IDC_LIST, OnSelchangeList)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CTextureBP message handlers

//初始化信息
BOOL CTextureBP::OnInitDialog() 
{
	CDialog::OnInitDialog();
	InitTexture();//初始化纹理名称，并填写到列表框控件中 
	return TRUE; 
}

//初始化纹理名称，并填写到列表框控件中 
void CTextureBP::InitTexture()
{
	CString tt;
	m_list.ResetContent(); //清空
	for(int i=1;i<=6;i++)  //纹理文件名称(可根据实际的纹理数进行更改)
	{
		tt.Format("拱形护坡%d.bmp",i); //纹理文件名称(可根据实际的纹理文件名称进行更改)
		m_list.AddString(tt);	//加入列表框控件中
	}
	for(i=1;i<=6;i++)  
	{
		tt.Format("菱形护坡%d.bmp",i);//纹理文件名称(可根据实际的纹理文件名称进行更改)
		m_list.AddString(tt);		//加入列表框控件中
	}
	m_list.AddString("菱形骨架.bmp");//加入列表框控件中
	m_list.AddString("平形护坡.bmp");//加入列表框控件中

	m_list.SetCurSel(0);//默认选择项为第1项纹理
	OnSelchangeList(); //响应纹理选择改变时的消息
}

//响应纹理选择改变时的消息
void CTextureBP::OnSelchangeList() 
{
	m_list.GetText(m_list.GetCurSel(),m_BPtextureName);//得到纹理文件名
	CDC *pdc=GetDC();
	CRect rect;
	GetDlgItem(IDC_STATIC_BMP)->GetWindowRect(rect); //得到IDC_STATIC_BMP控件的客户区大小
	this->ScreenToClient(rect); //将屏幕坐标转换成用户坐标
	CString BitmapFilePath;
	
	BitmapFilePath="./纹理\\边坡防护\\"+m_BPtextureName;//纹理文件全路径名称
	myDesingScheme.drawBitmapFormFile(BitmapFilePath,pdc,rect); //将选中的纹理影像在IDC_STATIC_BMP上绘制
}
