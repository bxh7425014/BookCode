//{{AFX_INCLUDES()
#include "msflexgrid.h"
//}}AFX_INCLUDES
#if !defined(AFX_ZDMJTLPPCPD_H__E00D80A5_A87F_431F_A5B3_A041626A3DED__INCLUDED_)
#define AFX_ZDMJTLPPCPD_H__E00D80A5_A87F_431F_A5B3_A041626A3DED__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ZdmJtLpPcPD.h : header file
 

 
// CZdmJtLpPcPD dialog
#include "ZdmDesign.h"

class CZdmJtLpPcPD : public CDialog
{
// Construction
public:
	float m_ZdmStartH;
	double  m_ZdmStartLC;

	float m_maxSlope;
	float m_minSlopeLength;
	float m_maxSlopePddsc;
	
	CZdmJtLpPcPD(CWnd* pParent = NULL);   
	
	BOOL m_bEditData;
	
// Dialog Data
	//{{AFX_DATA(CZdmJtLpPcPD)
	enum { IDD = IDD_DIALOG_ZDM_JTPCPD };
	CMSFlexGrid	m_grid;
	float	m_H;
	float	m_L;
	double	m_LC;
	float	m_pd;
 
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CZdmJtLpPcPD)
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
	//{{AFX_MSG(CZdmJtLpPcPD)
	afx_msg void OnClickMsflexgrid();
	virtual BOOL OnInitDialog();
	afx_msg void OnButtonEdit();
	afx_msg void OnButtonDelete();
	afx_msg void OnButtonExit();
	afx_msg void OnButtonOutput();
	afx_msg void OnButtonSave();
	afx_msg void OnRadioPcpd();
	afx_msg void OnRadioZhbg();
	afx_msg void OnButtonCheck();
	DECLARE_EVENTSINK_MAP()
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void WriteToExcel();
	void SaveData();
	void ReCalCurve();
	
	void LoadZdmDesignData();
	void InitGrid();
	CArray<PZdmSlope,PZdmSlope> ZDmJDCurveElements;
	
	int m_editStyle;  

};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ZDMJTLPPCPD_H__E00D80A5_A87F_431F_A5B3_A041626A3DED__INCLUDED_)
