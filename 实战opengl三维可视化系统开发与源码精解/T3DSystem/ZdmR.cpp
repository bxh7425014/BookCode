// ZdmR.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "ZdmR.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CZdmR dialog


CZdmR::CZdmR(CWnd* pParent /*=NULL*/)
	: CDialog(CZdmR::IDD, pParent)
{
	//{{AFX_DATA_INIT(CZdmR)
	m_R = 0;
	//}}AFX_DATA_INIT
}


void CZdmR::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CZdmR)
	DDX_Text(pDX, IDC_EDIT_R, m_R);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CZdmR, CDialog)
	//{{AFX_MSG_MAP(CZdmR)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CZdmR message handlers

void CZdmR::OnOK() 
{
	this->UpdateData();
	
	CDialog::OnOK();
}
