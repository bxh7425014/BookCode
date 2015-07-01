#if !defined(AFX_AVIPARAMETER_H__8015281F_D23D_49E4_98E1_271D471E322D__INCLUDED_)
#define AFX_AVIPARAMETER_H__8015281F_D23D_49E4_98E1_271D471E322D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// AviParameter1.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CAviParameter dialog

class CAviParameter : public CDialog
{
// Construction
public:
	int m_MovieWidth;  //AVI的宽度
	int m_MovieHeight; //AVI的高度 
	
	int m_MoviemaxWidth;  //AVI的最大宽度
	int m_MoviemaxHeight; //AVI的最大高度 

	CAviParameter(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CAviParameter)
	enum { IDD = IDD_DIALOG_AVIPARAMETER };
	CSliderCtrl	m_Slider_AVIWidth;
	CSliderCtrl	m_Slider_AVIHeight;
	CSliderCtrl	m_slider_frame;
	CString	m_AviFilename;
	int		m_AVIFrame;
	int		m_AVIHeight;
	int		m_AVIWidth;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAviParameter)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CAviParameter)
	afx_msg void OnButtonBrowse();
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_AVIPARAMETER_H__8015281F_D23D_49E4_98E1_271D471E322D__INCLUDED_)
