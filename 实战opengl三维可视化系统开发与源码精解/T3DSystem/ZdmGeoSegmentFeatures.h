//{{AFX_INCLUDES()
#include "msflexgrid.h"
//}}AFX_INCLUDES
#if !defined(AFX_ZDMGEOSEGMENTFEATURES_H__6C636728_7E03_49FA_8104_48F6BD2A236E__INCLUDED_)
#define AFX_ZDMGEOSEGMENTFEATURES_H__6C636728_7E03_49FA_8104_48F6BD2A236E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ZdmGeoSegmentFeatures.h : header file
 

 
// CZdmGeoSegmentFeatures dialog

class CZdmGeoSegmentFeatures : public CDialog
{
// Construction
public:
	CZdmGeoSegmentFeatures(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CZdmGeoSegmentFeatures)
	enum { IDD = IDD_DIALOG_ZDMGEOFEATURES };
	double	m_SegmentendLC;
	double	m_SegmentstartLC;
	CString	m_geoFeature;
	CMSFlexGrid	m_grid;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CZdmGeoSegmentFeatures)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:
	_RecordsetPtr m_pRecordset; 
	variant_t RecordsAffected;
	_variant_t Thevalue;  
	HRESULT hr;
	// Generated message map functions
	//{{AFX_MSG(CZdmGeoSegmentFeatures)
	afx_msg void OnButtonAdd();
	virtual BOOL OnInitDialog();
	afx_msg void OnClickMsflexgrid();
	afx_msg void OnButtonDelete();
	afx_msg void OnButtonExit();
	afx_msg void OnButtonEdit();
	DECLARE_EVENTSINK_MAP()
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	BOOL CheckValid();
	void InitGrid();
	void LoadData();
	
	int m_TotalNums;
	double	m_oldSegmentendLC;
	double	m_oldSegmentstartLC;
	
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ZDMGEOSEGMENTFEATURES_H__6C636728_7E03_49FA_8104_48F6BD2A236E__INCLUDED_)





















