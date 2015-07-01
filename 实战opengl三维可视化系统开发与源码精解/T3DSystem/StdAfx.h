// stdafx.h : include file for standard system include files,
//  or project specific include files that are used frequently, but
//      are changed infrequently
//

#if !defined(AFX_STDAFX_H__BD8FF39F_D5C6_4316_A31B_EB7ED84348B4__INCLUDED_)
#define AFX_STDAFX_H__BD8FF39F_D5C6_4316_A31B_EB7ED84348B4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#define VC_EXTRALEAN		// Exclude rarely-used stuff from Windows headers

#include <afxwin.h>         // MFC core and standard components
#include <afxext.h>         // MFC extensions
#include <afxdisp.h>        // MFC Automation classes
#include <afxdtctl.h>		// MFC support for Internet Explorer 4 Common Controls
#ifndef _AFX_NO_AFXCMN_SUPPORT
#include <afxcmn.h>			// MFC support for Windows Common Controls
#endif // _AFX_NO_AFXCMN_SUPPORT

//加入对OpenGL头文件的引用
#include <gl/glew.h>   //OpenGL扩展,只要包含一个glew.h头文件，就能使用gl,glu,glext,wgl,glx的全部函数
#include <gl/glut.h>   //glut.h要放在glew.h的后面
#include <gl/glaux.h> //glaux.h是辅助头文件

//声明程序调用数学函数库,一些数学计算的公式的具体实现是放在math.h里的,
//所以你就可以直接用公式,而不用关心是怎么实现公式的
#include <math.h> //数学函数库头文件

#include "SkinPPWTL.h"  //皮肤文件


//加入axftmp1.h是收集类模板(MFC模板类)的头文件,倘若在程序中用到了CArray,
// CObList等数据结构时,那么就得加载该文件。
#include  <afxtempl.h> 


//引入ADO库文件，以使编译器能正确编译
#import "c:\program files\common files\system\ado\msado15.dll" no_namespace rename("EOF","adoEOF")
#include "icrsint.h"	
#include <adoid.h>		
#include <afxdb.h> 

#include "alCulateF.h"

//加入对全局变量theApp的的引用
#include "T3DSystem.h"
extern CT3DSystemApp theApp;

//加入对OCI的的引用
#include <oci.h>   
#include "OCIclass.h"
extern COCIclass myOci;//定义OCI类全局变量

//ADO数据库
extern _RecordsetPtr m_Recordset; 
extern variant_t RecordsAffected;
extern _variant_t Thevalue;  
extern _ConnectionPtr m_Connection;


#include "DesingScheme.h"
extern CDesingScheme myDesingScheme;

#include "GetDemInsertValue.h"
extern CGetDemInsertValue m_demInsert;

extern int m_currentSchemeIndexs;   

#include <mmsystem.h> //windows中与多媒体有关的大多数接口，刷新频率计算用的时间计算函数（timeGetTime()）也在mmsystem.h中

extern CArray<int,int>m_SchemeGrade;   
extern CArray<CString,CString>m_SchemeNames;  
extern CString m_currentSchemeNames;   
extern CString m_PrecurrentSchemeNames;   


extern char g_sAppPath[512];
extern char g_sMediaPath[512];
extern UINT g_Texture[100];

void CreateTexture(UINT textureArray[], LPSTR strFileName, int textureID);
void GetFilePath(char * filename);

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_STDAFX_H__BD8FF39F_D5C6_4316_A31B_EB7ED84348B4__INCLUDED_)
