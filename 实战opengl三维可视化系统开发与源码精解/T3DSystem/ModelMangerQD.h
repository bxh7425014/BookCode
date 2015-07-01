#if !defined(AFX_MODELMANGERQD_H__8606B3DB_460C_4B1E_9A07_98507E8E01B6__INCLUDED_)
#define AFX_MODELMANGERQD_H__8606B3DB_460C_4B1E_9A07_98507E8E01B6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ModelMangerQD.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CModelMangerQD dialog

class CModelMangerQD : public CDialog
{
// Construction
public:
	CString m_3DSfilename_QD;
	CModelMangerQD(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CModelMangerQD)
	enum { IDD = IDD_DIALOG_MODEL_QD };
	CListBox	m_list;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CModelMangerQD)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CModelMangerQD)
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeList();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void InitData();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MODELMANGERQD_H__8606B3DB_460C_4B1E_9A07_98507E8E01B6__INCLUDED_)
