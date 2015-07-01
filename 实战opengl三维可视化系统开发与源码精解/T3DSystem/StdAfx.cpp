// stdafx.cpp : source file that includes just the standard includes
//	T3DSystem.pch will be the pre-compiled header
//	stdafx.obj will contain the pre-compiled type information

#include "stdafx.h"
COCIclass myOci;	//OCI类全局变量

CDesingScheme myDesingScheme;

//ADO数据库
_RecordsetPtr m_Recordset; 
variant_t RecordsAffected;
_variant_t Thevalue;  
_ConnectionPtr m_Connection;

CGetDemInsertValue m_demInsert;

int m_currentSchemeIndexs;   

CArray<int,int>m_SchemeGrade;    //线路方案等级
CArray<CString,CString>m_SchemeNames;  //方案名称 
CString m_currentSchemeNames;   
CString m_PrecurrentSchemeNames;   

CString GetFileDirectory(const CString &path)
{
	ASSERT(path.GetLength());
	int pos = path.ReverseFind('\\');
	if (pos >= 0)
		return path.Left(pos);
	return "";
}

CString GetFileExt(const CString &path)
{
	ASSERT(path.GetLength());
	int pos = path.ReverseFind('.');
	if (pos >= 0)
		return path.Right(path.GetLength() - pos - 1);
	return "";
}

CString GetFileName(const CString &path)
{
	ASSERT(path.GetLength());
	int pos = path.ReverseFind('\\');
	if (pos >= 0)
		return path.Right(path.GetLength() - pos - 1);
	return "";
}

CString GetFileTitle(const CString &path)
{
	ASSERT(path.GetLength());
	CString strResult = GetFileName(path);
	int pos = strResult.ReverseFind('.');
	if (pos >= 0)
		return strResult.Left(pos);
	return strResult;
}


 
void GetFilePath(char * filename)
{
	int len=strlen(filename);
	for(int i=len;i>=0;i--)
	{
		if(filename[i]!='\\') 
		{
			filename[i]='\0';
			continue;
		}
		break;
	}
}

 
void CreateTexture(UINT textureArray[], LPSTR strFileName, int textureID)
{
	AUX_RGBImageRec *pBitmap = NULL;
	
	if(!strFileName)									
		return;
	
	CString tt,ttfile;
	ttfile=(CString)strFileName;
	tt=ttfile.Right(3);
	tt.MakeUpper();
	if(tt=="JPG")
	{
		tt=ttfile.Left(ttfile.GetLength()-3)+"bmp";
		strFileName=(LPTSTR)(LPCTSTR)tt;
	}

	
	pBitmap = auxDIBImageLoad(strFileName);				
	
	if(pBitmap == NULL)									
		exit(0);
	
	
	glGenTextures(1, &textureArray[textureID]);
	
	
	glPixelStorei (GL_UNPACK_ALIGNMENT, 1);
	
	
	glBindTexture(GL_TEXTURE_2D, textureArray[textureID]);
	
	
	gluBuild2DMipmaps(GL_TEXTURE_2D, 3, pBitmap->sizeX, pBitmap->sizeY, GL_RGB, GL_UNSIGNED_BYTE, pBitmap->data);
	
	
	
	
	
	glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_NEAREST);
	glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR_MIPMAP_LINEAR);
	
	
	
	if (pBitmap)										
	{
		if (pBitmap->data)								
		{
			free(pBitmap->data);						
		}
		
		free(pBitmap);									
	}
}

void set_font(int height,CFont *font,char *name)
{
	
	LOGFONT lf;
	lf.lfHeight=20;          lf.lfWidth= 0;
	lf.lfEscapement=0;    lf.lfOrientation= 0;
	lf.lfWeight= 760;      lf.lfItalic= 0;
	lf.lfUnderline =0;      lf.lfStrikeOut =0;
	lf.lfCharSet =134; lf.lfOutPrecision =3;
	lf.lfClipPrecision =2; lf.lfQuality= 1;
	lf.lfPitchAndFamily =2; lstrcpy(lf.lfFaceName, "宋体");
	lf.lfOutPrecision =OUT_TT_ONLY_PRECIS;
	
	lf.lfHeight= height;   
	lstrcpy(lf.lfFaceName, name);
    if (font!=NULL)
    { 
		font->DeleteObject();
		font->CreateFontIndirect(&lf);
    } 
} 
