#if !defined(AFX_TEXTUREBP_H__84295723_0724_4370_BC80_A45CF3D78A56__INCLUDED_)
#define AFX_TEXTUREBP_H__84295723_0724_4370_BC80_A45CF3D78A56__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextureBP.h : header file
 

 
// CTextureBP dialog

class CTextureBP : public CDialog
{
// Construction
public:
	CString m_BPtextureName;
	CTextureBP(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CTextureBP)
	enum { IDD = IDD_DIALOG_TEXTURE_BP };
	CListBox	m_list;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTextureBP)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CTextureBP)
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeList();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void InitTexture();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTUREBP_H__84295723_0724_4370_BC80_A45CF3D78A56__INCLUDED_)
