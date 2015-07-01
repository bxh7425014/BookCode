//{{AFX_INCLUDES()
#include "msflexgrid.h"
//}}AFX_INCLUDES
#if !defined(AFX_ZDMSLOPEDATA_H__4A01EA6A_129D_4890_AE10_E2D4767E6F1D__INCLUDED_)
#define AFX_ZDMSLOPEDATA_H__4A01EA6A_129D_4890_AE10_E2D4767E6F1D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ZdmSlopeData.h : header file
 

 
// CZdmSlopeData dialog

#include "ZdmDesign.h"

class CZdmSlopeData : public CDialog
{
// Construction
public:
	CZdmSlopeData(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CZdmSlopeData)
	enum { IDD = IDD_DIALOG_ZDM_SLOPEDATA };
	CMSFlexGrid	m_grid;
	float	m_E;
	float	m_H;
	float	m_L;
	double	m_LC;
	float	m_pd;
	long	m_R;
	float	m_T;
	float	m_pddsc;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CZdmSlopeData)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:
	int 	m_totalZDmJD;
	CString ID;

	_variant_t  Thevalue;  
	_RecordsetPtr   m_pRecordset;
	variant_t RecordsAffected;
	HRESULT hr;
	
	
	// Generated message map functions
	//{{AFX_MSG(CZdmSlopeData)
	afx_msg void OnClickMsflexgrid();
	virtual BOOL OnInitDialog();
	afx_msg void OnButtonExit();
	afx_msg void OnButtonOutput();
	DECLARE_EVENTSINK_MAP()
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void WriteToExcel();
	void LoadZdmDesignData();
	void InitGrid();

	CArray<PZdmSlope,PZdmSlope> ZDmJDCurveElements;
	
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ZDMSLOPEDATA_H__4A01EA6A_129D_4890_AE10_E2D4767E6F1D__INCLUDED_)
