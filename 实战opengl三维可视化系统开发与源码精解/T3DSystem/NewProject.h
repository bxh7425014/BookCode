#if !defined(AFX_NEWPROJECT_H__8B056A93_3E95_486A_9A2F_26B9D4CEAF2D__INCLUDED_)
#define AFX_NEWPROJECT_H__8B056A93_3E95_486A_9A2F_26B9D4CEAF2D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// NewProject.h : header file
 

 
// CNewProject dialog

class CNewProject : public CDialog
{
// Construction
public:
	CNewProject(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CNewProject)
	enum { IDD = IDD_DIALOG_NEWPROJECT };
	CString	m_systemname;
	CString	m_systempassword;
	CString	m_projectname;
	CString	m_servername;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CNewProject)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CNewProject)
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	BOOL CreateNewProject();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_NEWPROJECT_H__8B056A93_3E95_486A_9A2F_26B9D4CEAF2D__INCLUDED_)
