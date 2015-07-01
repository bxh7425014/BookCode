 
 
 
#include "CheckComboBox.h"

#if !defined(AFX_MAINTOOLBAR_H__76CF28F4_005F_11D7_8F58_00E04C0BECE6__INCLUDED_)
#define AFX_MAINTOOLBAR_H__76CF28F4_005F_11D7_8F58_00E04C0BECE6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CMainToolBar : public CToolBar   
{
public:
	CMainToolBar();
	virtual ~CMainToolBar();

public:
	CComboBox m_wndScheme,m_wndScheme_plane;
	CComboBox m_wndScheme_ZDM;
	CSpinButtonCtrl  *m_wndZDM_XSpinButton;
	CSpinButtonCtrl *m_wndZDM_YSpinButton;
	CEdit  *m_wndZDM_XScaleTEXT,*m_wndZDM_YScaleTEXT;
	CEdit  *m_wndZDM_LCHTEXT;
	CProgressCtrl	*m_progress;

 
	
};
 
#endif // !defined(AFX_MAINTOOLBAR_H__76CF28F4_005F_11D7_8F58_00E04C0BECE6__INCLUDED_)















