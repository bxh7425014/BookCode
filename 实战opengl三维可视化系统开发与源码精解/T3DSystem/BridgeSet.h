//{{AFX_INCLUDES()
#include "msflexgrid.h"
//}}AFX_INCLUDES
#if !defined(AFX_BRIDGESET_H__CFB22371_D807_4F96_9830_5A2335472659__INCLUDED_)
#define AFX_BRIDGESET_H__CFB22371_D807_4F96_9830_5A2335472659__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// BridgeSet.h : header file
 

 
// CBridgeSet dialog

class CBridgeSet : public CDialog
{
// Construction
public:
	int m_bridgeColorR,m_bridgeColorG,m_bridgeColorB;

	CBridgeSet(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CBridgeSet)
	enum { IDD = IDD_DIALOG_BRIDGESET };
	float	m_Bridge_HLSpace;
	float	m_Bridge_HLWidth;
	float	m_Bridge_HLHeight;
	float	m_Bridge_QDSpace;
	float	m_Bridge_SetHeight;
	int		m_Bridge_HPangle;
	CMSFlexGrid	m_gridColor;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CBridgeSet)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CBridgeSet)
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	afx_msg void OnClickMsflexgrid();
	DECLARE_EVENTSINK_MAP()
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_BRIDGESET_H__CFB22371_D807_4F96_9830_5A2335472659__INCLUDED_)
