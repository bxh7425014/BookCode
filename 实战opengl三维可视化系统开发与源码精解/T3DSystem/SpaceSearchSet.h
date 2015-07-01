#if !defined(AFX_SPACESEARCHSET_H__EBD6DD48_1D05_44B1_BA52_C02F76596DE1__INCLUDED_)
#define AFX_SPACESEARCHSET_H__EBD6DD48_1D05_44B1_BA52_C02F76596DE1__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// SpaceSearchSet.h : header file
 

 
// CSpaceSearchSet dialog

class CSpaceSearchSet : public CDialog
{
// Construction
public:
	CSpaceSearchSet(CWnd* pParent = NULL);   

	int m_QueryColorR,m_QueryColorG,m_QueryColorB;
	int m_QueryLineWidth;
// Dialog Data
	//{{AFX_DATA(CSpaceSearchSet)
	enum { IDD = IDD_DIALOG_SPACESELECTSET };
	CComboBox	m_combolWidth;
	int		m_shizxLength;
	int		m_shuzxHeight;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSpaceSearchSet)
	public:
	virtual BOOL PreTranslateMessage(MSG* pMsg);
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:
	CBrush pbrush;
//	CMyEdit myedit[2];
	// Generated message map functions
	//{{AFX_MSG(CSpaceSearchSet)
	afx_msg void OnButtonColor();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SPACESEARCHSET_H__EBD6DD48_1D05_44B1_BA52_C02F76596DE1__INCLUDED_)
