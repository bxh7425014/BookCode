#if !defined(AFX_RECORDPICTURESPEED_H__2F016194_94AB_4D85_A4B0_E7B8E356C8E0__INCLUDED_)
#define AFX_RECORDPICTURESPEED_H__2F016194_94AB_4D85_A4B0_E7B8E356C8E0__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// RecordPictureSpeed.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CRecordPictureSpeed dialog

class CRecordPictureSpeed : public CDialog
{
// Construction
public:
	CRecordPictureSpeed(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CRecordPictureSpeed)
	enum { IDD = IDD_DIALOG_RECORDPICTURESPEED };
	CSliderCtrl	m_slider_recordpictSpeed;
	int		m_recordPictSpeed;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CRecordPictureSpeed)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CRecordPictureSpeed)
	virtual BOOL OnInitDialog();
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_RECORDPICTURESPEED_H__2F016194_94AB_4D85_A4B0_E7B8E356C8E0__INCLUDED_)
