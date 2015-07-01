// MainFrm.h : interface of the CMainFrame class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_MAINFRM_H__206B0F62_55C1_4927_B5E5_764048885DD7__INCLUDED_)
#define AFX_MAINFRM_H__206B0F62_55C1_4927_B5E5_764048885DD7__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//CTextProgressCtrl类是通过对ProgressCtrl类的扩展,实现在进度条上显示文本
#include "TextProgressCtrl.h"
#include "MainToolBar.h"

class CMainFrame : public CFrameWnd
{
	
protected: // create from serialization only
	CMainFrame();
	DECLARE_DYNCREATE(CMainFrame)

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMainFrame)
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	//}}AFX_VIRTUAL

// Implementation
public:
	void AddSchemeName();
	CToolBar m_wndOrthoToolBar;
	void Set_BarText(int index,CString strText,int nPos);
	virtual ~CMainFrame();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:  // control bar embedded members
	CStatusBar  m_wndStatusBar;
	CMainToolBar    m_wndToolBar;
	CTextProgressCtrl	m_progress;
	
// Generated message map functions
protected:
	//{{AFX_MSG(CMainFrame)
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg void OnPaint();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void LoadSchemeData();
	void OnSelectScheme();
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MAINFRM_H__206B0F62_55C1_4927_B5E5_764048885DD7__INCLUDED_)
