// ZdmStartLC_H.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "ZdmStartLC_H.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CZdmStartLC_H dialog


CZdmStartLC_H::CZdmStartLC_H(CWnd* pParent /*=NULL*/)
	: CDialog(CZdmStartLC_H::IDD, pParent)
{
	//{{AFX_DATA_INIT(CZdmStartLC_H)
	m_ZdmStartLC = 0.0;
	m_ZdmStartH = 0.0f;
	//}}AFX_DATA_INIT
}


void CZdmStartLC_H::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CZdmStartLC_H)
	DDX_Text(pDX, IDC_EDIT_STARTLC, m_ZdmStartLC);
	DDX_Text(pDX, IDC_EDIT_STARTH, m_ZdmStartH);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CZdmStartLC_H, CDialog)
	//{{AFX_MSG_MAP(CZdmStartLC_H)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CZdmStartLC_H message handlers

void CZdmStartLC_H::OnOK() 
{
	// TODO: Add extra validation here
	this->UpdateData();
	CDialog::OnOK();
}
