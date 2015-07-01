// DialogInputData.cpp : implementation file
//

#include "stdafx.h"
#include "T3DSystem.h"
#include "DialogInputData.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CDialogInputData dialog


CDialogInputData::CDialogInputData(CWnd* pParent /*=NULL*/)
	: CDialog(CDialogInputData::IDD, pParent)
{
	//{{AFX_DATA_INIT(CDialogInputData)
	m_name = _T("");
	//}}AFX_DATA_INIT
}


void CDialogInputData::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CDialogInputData)
	DDX_Text(pDX, IDC_EDIT_NAME, m_name);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CDialogInputData, CDialog)
	//{{AFX_MSG_MAP(CDialogInputData)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CDialogInputData message handlers

void CDialogInputData::OnOK() 
{
	// TODO: Add extra validation here
	
	CDialog::OnOK();
}



BOOL CDialogInputData::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	this->SetWindowText(m_strTitle);

	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}
