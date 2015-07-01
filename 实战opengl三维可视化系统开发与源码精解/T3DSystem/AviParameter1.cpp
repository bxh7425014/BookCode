// AviParameter1.cpp : implementation file
//

#include "stdafx.h"
#include "T3DSystem.h"
#include "AviParameter1.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAviParameter dialog


CAviParameter::CAviParameter(CWnd* pParent /*=NULL*/)
	: CDialog(CAviParameter::IDD, pParent)
{
	//{{AFX_DATA_INIT(CAviParameter)
	m_AviFilename = _T("");
	m_AVIFrame = 0;
	m_AVIHeight = 0;
	m_AVIWidth = 0;
	//}}AFX_DATA_INIT
}


void CAviParameter::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAviParameter)
	DDX_Control(pDX, IDC_SLIDER_MOVIE_WIDTH, m_Slider_AVIWidth);
	DDX_Control(pDX, IDC_SLIDER_MOVIE_HEIGHT, m_Slider_AVIHeight);
	DDX_Control(pDX, IDC_SLIDER_FRAME, m_slider_frame);
	DDX_Text(pDX, IDC_EDIT_FILENAME, m_AviFilename);
	DDX_Text(pDX, IDC_EDIT_FRAME, m_AVIFrame);
	DDX_Text(pDX, IDC_EDIT_MOVIE_HEIGHT, m_AVIHeight);
	DDX_Text(pDX, IDC_EDIT_MOVIE_WIDTH, m_AVIWidth);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CAviParameter, CDialog)
	//{{AFX_MSG_MAP(CAviParameter)
	ON_BN_CLICKED(IDC_BUTTON_BROWSE, OnButtonBrowse)
	ON_WM_HSCROLL()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CAviParameter message handlers

//创建AVI文件
void CAviParameter::OnButtonBrowse() 
{
	CString s;
	s="Microsoft AVI (*.avi)|*.avi||";
	CFileDialog fd(FALSE,"avi",0,OFN_OVERWRITEPROMPT|OFN_HIDEREADONLY|OFN_PATHMUSTEXIST,s);
	if(fd.DoModal ()==IDOK) //如果文件对话框打开成功
	{
		m_AviFilename=fd.GetPathName();  //得到动画文件名
		this->UpdateData (FALSE); //数据更新
		if(m_AviFilename.IsEmpty ()) //如果动画文件名为空
		{
			this->MessageBox ("文件名不能为空!","动画文件参数",MB_OK|MB_ICONERROR);
			return; //返回
		}
	}
}

//响应IDOK按钮消息
void CAviParameter::OnOK() 
{
	if(this->UpdateData ()==TRUE )
	{
		if(m_AviFilename.IsEmpty ()) //如果视频动画文件名为空，返回
			return;
		this->EndDialog (IDOK); 
	}		
	
	CDialog::OnOK();
}

//初始化信息
BOOL CAviParameter::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	m_slider_frame.SetRange (1,30); //设置捕捉帧率范围
	m_slider_frame.SetPos (m_AVIFrame); //设置捕捉帧率初始值
	
	m_Slider_AVIWidth.SetRange (1,m_MoviemaxWidth);//设置捕捉区域宽度范围
	m_Slider_AVIHeight.SetRange (1,m_MoviemaxHeight);//设置捕捉区域宽度初始值
	
	m_Slider_AVIWidth.SetPos (m_MovieWidth);//设置捕捉区域高度范围
	m_Slider_AVIHeight.SetPos (m_MovieHeight);//设置捕捉区域高度初始值
	
	m_AVIHeight=m_MovieWidth; //动画高度
	m_AVIWidth=m_MovieHeight;//动画宽度
	
    this->UpdateData (FALSE); //更新控件数据

	return TRUE;  
}

//响应水平滚动条消息
void CAviParameter::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	this->UpdateData(TRUE); //更新变量数据
	
	switch(pScrollBar->GetDlgCtrlID())
	{
	case  IDC_SLIDER_FRAME: //如果是捕捉帧率
		m_AVIFrame=m_slider_frame.GetPos (); //得到捕捉帧率
		break;
	case  IDC_SLIDER_MOVIE_WIDTH://如果是捕捉区域宽度
		m_AVIWidth=m_Slider_AVIWidth.GetPos (); //得到AVI的宽度
		m_MovieWidth=m_AVIWidth;
		break;
	case  IDC_SLIDER_MOVIE_HEIGHT://如果是捕捉区域高度
		m_AVIHeight=m_Slider_AVIHeight.GetPos (); //得到AVI的高度
		m_MovieHeight=m_AVIHeight;
		break;
	}
	
    this->UpdateData(FALSE);//更新控件数据
	
	CDialog::OnHScroll(nSBCode, nPos, pScrollBar);
}
