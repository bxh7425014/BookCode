#if !defined(AFX_TEXTURELOAD_H__D20450E9_6F77_4C2E_95E2_03264E5419C6__INCLUDED_)
#define AFX_TEXTURELOAD_H__D20450E9_6F77_4C2E_95E2_03264E5419C6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextureLoad.h : header file
 

 
// CTextureLoad dialog

#include "ImageObject.h"	//影像分块处理头文件

class CTextureLoad : public CDialog
{
// Construction
public:
	CTextureLoad(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CTextureLoad)
	enum { IDD = IDD_DIALOG_LOADTEXTURE };
	CListBox	m_listfiles;
	CProgressCtrl	m_progress;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTextureLoad)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:
	// Generated message map functions
	//{{AFX_MSG(CTextureLoad)
	afx_msg void OnButtonBrowse();
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	afx_msg void OnDestroy();
	//}}AFX_MSG
private:
	BOOL GetTextureRange(CString tcrPathname);
	BOOL WriteLittleLodImageToDB(CString strFile);
	BOOL WriteImageToDB(CString strFile,int m_RowIndex,int m_ColIndex,int m_height,int m_width,int m_phramidLayer,int m_fg_width,int m_fg_height);
	void SeperateImage(CString mfilename, int m_phramidLayer,CString tempDirctory);
	
	
	CImageObject *m_pImageObject;//CImageObject类型变量，用于实现对影像纹理的分块处理
	int m_totalRows,m_totalCols;//存储纹理影像分块的总行数和总列数

	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTURELOAD_H__D20450E9_6F77_4C2E_95E2_03264E5419C6__INCLUDED_)










































