#if !defined(AFX_CTexture_H__A7C41EA3_DB80_49DA_9A84_72E6251F9A21__INCLUDED_)
#define AFX_CTexture_H__A7C41EA3_DB80_49DA_9A84_72E6251F9A21__INCLUDED_


class CTexture
{
	GLuint	m_nTxt;	
public:
	inline GLint GetTxtID(){return m_nTxt;}
	int LoadGLTextures(char *Filename);
	CTexture()
	{	m_nTxt = -1;	}
	~CTexture()
	{	
		if(m_nTxt!=-1)
			glDeleteTextures(1,&m_nTxt);
	}
	bool MakeTextureBind(char* TextureFileName,bool bLinear,bool bMip);
	bool MakeScreenTextureBind();
	bool MakeAlphaTextureBind(char* TextureFileName,char *AlphaFileName);
	bool MakeAlphaTextureBind(char* TextureFileName);
	bool MakeSkinTextureBind(char* TextureFileName,bool bLinear,bool bMip);
	bool LoadTGA(char *filename)	;			

 

};




#endif
