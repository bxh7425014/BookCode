// ToolBar.h : main header file for the TOOLBAR application
 

#if !defined(AFX_TOOLBAR_H__76CF28E4_005F_11D7_8F58_00E04C0BECE6__INCLUDED_)
#define AFX_TOOLBAR_H__76CF28E4_005F_11D7_8F58_00E04C0BECE6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       

 
 
// See ToolBar.cpp for the implementation of this class
 

class CToolBarApp : public CWinApp
{
public:
	CToolBarApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CToolBarApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation
	//{{AFX_MSG(CToolBarApp)
	afx_msg void OnAppAbout();
		// NOTE - the ClassWizard will add and remove member functions here.
		
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


 

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TOOLBAR_H__76CF28E4_005F_11D7_8F58_00E04C0BECE6__INCLUDED_)
