#if !defined(AFX_TUNNCELPROPERTYSET_H__C81B9201_9886_4C02_A99C_E32AFDAF734C__INCLUDED_)
#define AFX_TUNNCELPROPERTYSET_H__C81B9201_9886_4C02_A99C_E32AFDAF734C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TunncelPropertySet.h : header file
 

 
// CTunncelPropertySet dialog

class CTunncelPropertySet : public CDialog
{
// Construction
public:
	CTunncelPropertySet(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CTunncelPropertySet)
	enum { IDD = IDD_DIALOG_TUNNNELSET };
	float	m_tunnel_height;
	int		m_tunnel_ArcSegmentNumber;
	float	m_tunnel_Archeight;
	float	m_tunnel_WallHeight;
	float	m_tunnel_H;
	float	m_tunnel_L;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTunncelPropertySet)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CTunncelPropertySet)
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	afx_msg void OnChangeEditArcheight();
	afx_msg void OnChangeEditTunnelheight();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TUNNCELPROPERTYSET_H__C81B9201_9886_4C02_A99C_E32AFDAF734C__INCLUDED_)
