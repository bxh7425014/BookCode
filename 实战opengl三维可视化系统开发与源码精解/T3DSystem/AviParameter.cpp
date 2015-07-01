// AviParameter.cpp : implementation file
//

#include "stdafx.h"
#include "railway3d.h"
#include "AviParameter.h"

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

void CAviParameter::OnButtonBrowse() 
{
	CString s;
	s="Microsoft AVI (*.avi)|*.avi||";
	CFileDialog fd(FALSE,"avi",0,OFN_OVERWRITEPROMPT|OFN_HIDEREADONLY|OFN_PATHMUSTEXIST,s);
	if(fd.DoModal ()==IDOK)
	{
		m_AviFilename=fd.GetPathName(); 
		this->UpdateData (FALSE);
		if(m_AviFilename.IsEmpty ())
		{
			this->MessageBox ("文件名不能为空!","动画文件参数",MB_OK|MB_ICONERROR);
			return;
		}
	}
	
}

void CAviParameter::OnOK() 
{
	if(this->UpdateData ()==TRUE )
	{
		if(m_AviFilename.IsEmpty ()) return;
		this->EndDialog (IDOK); 
	}		
	
	CDialog::OnOK();
}

BOOL CAviParameter::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	m_slider_frame.SetRange (1,30);
	m_slider_frame.SetPos (m_AVIFrame);
	
	m_Slider_AVIWidth.SetRange (1,m_MoviemaxWidth);
	m_Slider_AVIHeight.SetRange (1,m_MoviemaxHeight);
	
	m_Slider_AVIWidth.SetPos (m_MovieWidth);
	m_Slider_AVIHeight.SetPos (m_MovieHeight);
	
	m_AVIHeight=m_MovieWidth;
	m_AVIWidth=m_MovieHeight;
	
    this->UpdateData (FALSE);

	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CAviParameter::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	this->UpdateData(TRUE); //更新变量数据
	
	switch(pScrollBar->GetDlgCtrlID())
	{
	case  IDC_SLIDER_FRAME:
		m_AVIFrame=m_slider_frame.GetPos (); //得到枕木间距系数
		break;
	case  IDC_SLIDER_MOVIE_WIDTH:
		m_AVIWidth=m_Slider_AVIWidth.GetPos (); //得到AVI的宽度
		m_MovieWidth=m_AVIWidth;
		break;
	case  IDC_SLIDER_MOVIE_HEIGHT:
		m_AVIHeight=m_Slider_AVIHeight.GetPos (); //得到AVI的)高度
		m_MovieHeight=m_AVIHeight;
		break;
	}
	
	
    this->UpdateData(FALSE);//更新控件数据
	
	CDialog::OnHScroll(nSBCode, nPos, pScrollBar);
}
