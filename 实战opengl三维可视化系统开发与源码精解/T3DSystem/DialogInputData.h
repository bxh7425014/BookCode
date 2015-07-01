#if !defined(AFX_DIALOGINPUTDATA_H__1104E828_1C8A_4DFB_9D46_50A65CC000A4__INCLUDED_)
#define AFX_DIALOGINPUTDATA_H__1104E828_1C8A_4DFB_9D46_50A65CC000A4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// DialogInputData.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CDialogInputData dialog

class CDialogInputData : public CDialog
{
// Construction
public:
	CString m_strTitle;
	CDialogInputData(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CDialogInputData)
	enum { IDD = IDD_DIALOG_GZWNAME };
	CString	m_name;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CDialogInputData)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CDialogInputData)
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_DIALOGINPUTDATA_H__1104E828_1C8A_4DFB_9D46_50A65CC000A4__INCLUDED_)
