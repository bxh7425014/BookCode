#if !defined(AFX_TEXTURETUNNEL_H__4ED0DD5E_39CD_419F_B77B_D3634C5FA56E__INCLUDED_)
#define AFX_TEXTURETUNNEL_H__4ED0DD5E_39CD_419F_B77B_D3634C5FA56E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextureTunnel.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CTextureTunnel dialog

class CTextureTunnel : public CDialog
{
// Construction
public:
	CString m_TunneltextureName;
	CTextureTunnel(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CTextureTunnel)
	enum { IDD = IDD_DIALOG_TEXTURE_TUNNEL };
	CListBox	m_list;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTextureTunnel)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CTextureTunnel)
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeList();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void InitTexture();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTURETUNNEL_H__4ED0DD5E_39CD_419F_B77B_D3634C5FA56E__INCLUDED_)
