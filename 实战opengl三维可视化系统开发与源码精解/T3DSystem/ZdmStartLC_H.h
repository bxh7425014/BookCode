#if !defined(AFX_ZDMSTARTLC_H_H__CEB46A3D_D686_4828_87F4_88E08DF4B532__INCLUDED_)
#define AFX_ZDMSTARTLC_H_H__CEB46A3D_D686_4828_87F4_88E08DF4B532__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ZdmStartLC_H.h : header file
 

 
// CZdmStartLC_H dialog

class CZdmStartLC_H : public CDialog
{
// Construction
public:
	CZdmStartLC_H(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CZdmStartLC_H)
	enum { IDD = IDD_DIALOG_ZDM_STARTLC_H };
	double	m_ZdmStartLC;
	float	m_ZdmStartH;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CZdmStartLC_H)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CZdmStartLC_H)
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ZDMSTARTLC_H_H__CEB46A3D_D686_4828_87F4_88E08DF4B532__INCLUDED_)
