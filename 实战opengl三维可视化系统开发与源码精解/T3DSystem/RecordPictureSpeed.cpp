// RecordPictureSpeed.cpp : implementation file
//

#include "stdafx.h"
#include "T3DSystem.h"
#include "RecordPictureSpeed.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CRecordPictureSpeed dialog


CRecordPictureSpeed::CRecordPictureSpeed(CWnd* pParent /*=NULL*/)
	: CDialog(CRecordPictureSpeed::IDD, pParent)
{
	//{{AFX_DATA_INIT(CRecordPictureSpeed)
	m_recordPictSpeed = 0;
	//}}AFX_DATA_INIT
}


void CRecordPictureSpeed::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CRecordPictureSpeed)
	DDX_Control(pDX, IDC_SLIDER_RECORDPICTURE_SPEED, m_slider_recordpictSpeed);
	DDX_Text(pDX, IDC_EDIT_RECORDPICTURESPEED, m_recordPictSpeed);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CRecordPictureSpeed, CDialog)
	//{{AFX_MSG_MAP(CRecordPictureSpeed)
	ON_WM_HSCROLL()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CRecordPictureSpeed message handlers

//初始化信息
BOOL CRecordPictureSpeed::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	m_slider_recordpictSpeed.SetRange (1,2000); //设置采集图像的频率范围，即每隔多少毫秒录制一幅图像
	m_slider_recordpictSpeed.SetPos (m_recordPictSpeed); //设置当前采集图像的频率
    this->UpdateData (FALSE); //数据更新
	
	
	return TRUE;  
}

//响应水平滚动条消息
void CRecordPictureSpeed::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	this->UpdateData(TRUE); //更新变量数据
	
	//如果是IDC_SLIDER_RECORDPICTURE_SPEED控件
	if(pScrollBar->GetDlgCtrlID() ==IDC_SLIDER_RECORDPICTURE_SPEED)
	{
		m_recordPictSpeed=m_slider_recordpictSpeed.GetPos (); //得到所设置的采集图像频率
		this->UpdateData(FALSE);//数据更新
	}
	
	CDialog::OnHScroll(nSBCode, nPos, pScrollBar);
}
