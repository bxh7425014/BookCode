#if !defined(AFX_OPENPROJECT_H__5585D5D6_28BF_4D05_A2CE_913F85485B5A__INCLUDED_)
#define AFX_OPENPROJECT_H__5585D5D6_28BF_4D05_A2CE_913F85485B5A__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// OpenProject.h : header file
 

 
// COpenProject dialog

// #include "SortListCtrl.h"


class COpenProject : public CDialog
{
// Construction
public:
	CString m_strProjectname;
	BOOL OpenProjects(int NewOrOpen);
	COpenProject(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(COpenProject)
	enum { IDD = IDD_DIALOG_OPENPROJECT };
	CListCtrl	m_list;
	CProgressCtrl	m_progress;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(COpenProject)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(COpenProject)
	virtual BOOL OnInitDialog();
	afx_msg void OnClickList(NMHDR* pNMHDR, LRESULT* pResult);
	afx_msg void OnButtonDelete();
	afx_msg void OnButtonOpen();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void InitListCtrl();
	
	int  m_nCurrentItem;
	int  m_nCurrentSubItem;

	int mtotalProjects;
 


	void LoadData();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_OPENPROJECT_H__5585D5D6_28BF_4D05_A2CE_913F85485B5A__INCLUDED_)



