#if !defined(AFX_ZDMR_H__6F969820_6725_4975_AAC9_11AC48195953__INCLUDED_)
#define AFX_ZDMR_H__6F969820_6725_4975_AAC9_11AC48195953__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ZdmR.h : header file
 

 
// CZdmR dialog

class CZdmR : public CDialog
{
// Construction
public:
	CZdmR(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CZdmR)
	enum { IDD = IDD_DIALOG_ZDM_R };
	long	m_R;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CZdmR)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CZdmR)
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ZDMR_H__6F969820_6725_4975_AAC9_11AC48195953__INCLUDED_)
