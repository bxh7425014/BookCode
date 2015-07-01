#if !defined(AFX_PLANERL0_H__85007E57_0323_4CFB_B8F0_E559B1098143__INCLUDED_)
#define AFX_PLANERL0_H__85007E57_0323_4CFB_B8F0_E559B1098143__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// PLaneRL0.h : header file
 

 
// CPLaneRL0 dialog

class CPLaneRL0 : public CDialog
{
// Construction
public:
	int R; //曲线半径
	int L0; //缓和曲线长
	
	CPLaneRL0(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CPLaneRL0)
	enum { IDD = IDD_DIALOG_PLANE_R_l0 };
	CComboBox	m_CBRadius;
	int		m_L0;
	int		m_minR;
	int		m_minL0;
	CString	m_strrangR;
	CString	m_ID;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CPLaneRL0)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CPLaneRL0)
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeComboR();
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_PLANERL0_H__85007E57_0323_4CFB_B8F0_E559B1098143__INCLUDED_)
