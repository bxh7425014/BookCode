// Snow.h: interface for the Snow class.
//
//////////////////////////////////////////////////////////////////////

#include <gl/gl.h>
#include <gl/glu.h>
#include <gl/glaux.h>

#if !defined(AFX_SNOW_H__0A0DDB82_974A_4047_8CDC_C095E7BCB390__INCLUDED_)
#define AFX_SNOW_H__0A0DDB82_974A_4047_8CDC_C095E7BCB390__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000



typedef enum {
	E_TEXTURECOUNT =    4,
		E_SNOWCOUNT    =  2010
};

//GLuint m_textureSnow[ E_TEXTURECOUNT ];

typedef struct tagSNOW {
	GLfloat x;
	GLfloat y;
	GLfloat z;
	
	GLfloat xrot;
	GLfloat yrot;
	GLfloat zrot;
	
	GLfloat dropSpeed;
	GLfloat rotSpeed;
	
	int     nIndexTexture;
} SNOW, * LPSNOW;


class Snow  
{
public:
	int m_nFloorIndex;
	void DrawSnow();
	BOOL LoadOpenGLTextures();
	void InitSnow();
	Snow();
	virtual ~Snow();

private:
	GLuint m_textureSnow[ E_TEXTURECOUNT ];
	SNOW m_snow[ E_SNOWCOUNT ];
	void SetDefaultTextureParams();
	BOOL LoadImageFromResID( UINT nResID, GLuint& texture );
};

#endif // !defined(AFX_SNOW_H__0A0DDB82_974A_4047_8CDC_C095E7BCB390__INCLUDED_)
