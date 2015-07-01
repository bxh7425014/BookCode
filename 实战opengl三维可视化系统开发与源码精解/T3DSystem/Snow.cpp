// Snow.cpp: implementation of the Snow class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Snow.h"
#include "resource.h"       

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

Snow::Snow()
{
	m_nFloorIndex = 0;
	
}

Snow::~Snow()
{

}

void Snow::InitSnow()
{
	srand( GetTickCount() );
	
	for ( int nIndex = 0; nIndex < E_SNOWCOUNT; nIndex++ )
	{
		m_snow[ nIndex ].nIndexTexture = rand() % 3;
		
		m_snow[ nIndex ].x = GLfloat( rand() % 200 - 100 );
		m_snow[ nIndex ].y = GLfloat( rand() % 200 - 100 );
		m_snow[ nIndex ].z = 100.0f + GLfloat( rand() % 25 );
	}
	
	for ( int i = 0; i < E_SNOWCOUNT; i++ )
	{
		m_snow[ i ].nIndexTexture = rand() % 3;
		m_snow[ i ].x             = GLfloat( rand() % 200 - 100 );
		m_snow[ i ].z             = GLfloat( rand() % 200 - 100 );
		m_snow[ i ].y             = 100.0f + GLfloat( rand() % 25 );
		
		m_snow[ i ].xrot          = 0;
		m_snow[ i ].yrot          = 0;
		m_snow[ i ].zrot          = 0;
		
		m_snow[ i ].dropSpeed     = 0.01f * ( rand() % 50 + 2 );
		m_snow[ i ].rotSpeed      =  0.1f * ( rand() % 10 + 2 );
	}
}

BOOL Snow::LoadOpenGLTextures()
{
	glGenTextures( E_TEXTURECOUNT, & m_textureSnow[ 0 ] );
	LoadImageFromResID(IDB_SNOW_LARGE,  m_textureSnow[ 0 ] );
	LoadImageFromResID(IDB_SNOW_MIDDLE, m_textureSnow[ 1 ] );
	LoadImageFromResID(IDB_SNOW_SMALL,  m_textureSnow[ 2 ] );
	InitSnow();
	LoadImageFromResID( IDB_SNOW_MIDDLE, m_textureSnow[ 3 ] );
	
	return TRUE;
}

BOOL Snow::LoadImageFromResID(UINT nResID, GLuint &texture)
{
	ASSERT( nResID > 0 );
	
	HBITMAP hBitmap = ( HBITMAP ) LoadImage( \
		AfxGetInstanceHandle(), \
		MAKEINTRESOURCE( nResID ), \
		IMAGE_BITMAP, \
		0, 0, \
		LR_CREATEDIBSECTION );
	ASSERT( hBitmap != NULL );
	
	DIBSECTION ds;
	GetObject( hBitmap, sizeof( DIBSECTION ), ( LPVOID ) & ds );
	
	glBindTexture( GL_TEXTURE_2D, texture );
	SetDefaultTextureParams();
	
	glTexImage2D( GL_TEXTURE_2D, 0, 3, ds.dsBm.bmWidth, ds.dsBm.bmHeight, 0, GL_RGB, GL_UNSIGNED_BYTE, ds.dsBm.bmBits );
	DeleteObject( hBitmap );
	
	return TRUE;
}

void Snow::SetDefaultTextureParams()
{
	glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
	glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );
	
}

void Snow::DrawSnow()
{
	static GLfloat wAngleY = 10.0f;
	
	glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
	
	glLoadIdentity();
	glTranslatef( 0.0f, -30.0f, -150.0f );
	glRotatef( wAngleY, 0.0f, 1.0f, 0.0f );
	
	glBindTexture( GL_TEXTURE_2D, m_textureSnow[ m_nFloorIndex ] );
	glEnable(GL_TEXTURE_2D);
	glColor4f( 1.0f, 1.0f, 1.0f, 0.5 );
	
	glBegin( GL_QUADS );
	glNormal3f( 0.0f, 1.0f, 0.0f );
	glTexCoord2f( 0.0f, 0.0f ); glVertex3f(  100.0f, 0.0f, -100.0f );
	glTexCoord2f( 1.0f, 0.0f ); glVertex3f(  100.0f, 0.0f,  100.0f );
	glTexCoord2f( 1.0f, 1.0f ); glVertex3f( -100.0f, 0.0f,  100.0f );
	glTexCoord2f( 0.0f, 1.0f ); glVertex3f( -100.0f, 0.0f, -100.0f );
	glEnd();
	
	for ( int nIndex = 0; nIndex < E_SNOWCOUNT; nIndex++ )
	{
		glLoadIdentity();
		glTranslatef( 0.0f, -30.0f, -150.0f ); 
		glRotatef( wAngleY, 0.0f, 1.0f, 0.0f );
		
		glBindTexture( GL_TEXTURE_2D, m_textureSnow[ m_snow[ nIndex ].nIndexTexture ] );
		glTranslatef( m_snow[ nIndex ].x, m_snow[ nIndex ].y, m_snow[ nIndex ].z );
		
		glRotatef( m_snow[ nIndex ].xrot, 1.0f, 0.0f, 0.0f );
		glRotatef( m_snow[ nIndex ].yrot, 0.0f, 1.0f, 0.0f );
		glRotatef( m_snow[ nIndex ].zrot, 0.0f, 0.0f, 1.0f );
		
		glBegin( GL_QUADS );
		glNormal3f( 0.0f, 1.0f, 0.0f );
		glTexCoord2f( 0.0f, 0.0f ); glVertex3f(  1.0f, 0.0f, -1.0f );
		glTexCoord2f( 1.0f, 0.0f ); glVertex3f(  1.0f, 0.0f,  1.0f );
		glTexCoord2f( 1.0f, 1.0f ); glVertex3f( -1.0f, 0.0f,  1.0f );
		glTexCoord2f( 0.0f, 1.0f ); glVertex3f( -1.0f, 0.0f, -1.0f );
		glEnd();
		
		m_snow[ nIndex ].y -= m_snow[ nIndex ].dropSpeed;
		if( m_snow[ nIndex ].y < -33 )
			m_snow[ nIndex ].y = 125.0f;
		
		m_snow[ nIndex ].xrot += m_snow[ nIndex ].rotSpeed;
		m_snow[ nIndex ].yrot += m_snow[ nIndex ].rotSpeed;
		m_snow[ nIndex ].zrot += m_snow[ nIndex ].rotSpeed;
	}
	
	wAngleY += 0.2f;
	
	glFinish();
	SwapBuffers( wglGetCurrentDC() );
}
