// ModelMangerQD.cpp : implementation file
//

#include "stdafx.h"
#include "T3DSystem.h"
#include "ModelMangerQD.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CModelMangerQD dialog


CModelMangerQD::CModelMangerQD(CWnd* pParent /*=NULL*/)
	: CDialog(CModelMangerQD::IDD, pParent)
{
	//{{AFX_DATA_INIT(CModelMangerQD)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}


void CModelMangerQD::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CModelMangerQD)
	DDX_Control(pDX, IDC_LIST, m_list);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CModelMangerQD, CDialog)
	//{{AFX_MSG_MAP(CModelMangerQD)
	ON_LBN_SELCHANGE(IDC_LIST, OnSelchangeList)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CModelMangerQD message handlers

void CModelMangerQD::OnOK() 
{
	// TODO: Add extra validation here
	
	CDialog::OnOK();
}


//初始化信息
BOOL CModelMangerQD::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	InitData(); //初始化桥墩模型
	
	return TRUE;  
}

//初始化桥墩模型
void CModelMangerQD::InitData()
{
	m_list.ResetContent(); //清空
	m_list.AddString("版式柔性墩");
	m_list.AddString("单柱式桥墩");
	m_list.AddString("刚架式柔性墩");
	m_list.AddString("混合双柱式桥墩");
	m_list.AddString("排架桩柔性墩");
	m_list.AddString("上柔下刚墩");
	m_list.AddString("双柱式桥墩");
	m_list.AddString("哑铃式桥墩");
	m_list.AddString("重力式矩形桥墩");
	m_list.AddString("重力式圆端形桥墩");
	m_list.AddString("重力式圆形桥墩");
	m_list.AddString("板凳式拼装桥墩");
	m_list.SetCurSel(0); //默认选择项为第1项模型
	
}

//响应模型选择改变时的消息
void CModelMangerQD::OnSelchangeList() 
{
	CString BitmapFilePath,strFilename,tt;
	
	int index=m_list.GetCurSel(); //得到列表空件当前索引
	if(index<0) //如果当前索引<0，返回
		return;
	
	m_list.GetText(index,tt); //得到模型文件名
	
	strFilename.Format("%s\\%s.bmp",tt,tt);
	CDC *pdc=GetDC();
	CRect rect;
	GetDlgItem(IDC_STATIC_BMP)->GetWindowRect(rect);//得到IDC_STATIC_BMP控件的客户区大小
	this->ScreenToClient(rect);//将屏幕坐标转换成用户坐标
	
	BitmapFilePath=".\\模型\\桥梁墩台\\"+strFilename;
	m_3DSfilename_QD.Format(".\\模型\\桥梁墩台\\%s\\模型文件.3ds",tt);//得到模型包含完整路径的文件名

	//将选中的模型影像在IDC_STATIC_BMP上绘制
	myDesingScheme.drawBitmapFormFile(BitmapFilePath,pdc,rect);	
	
	
}
