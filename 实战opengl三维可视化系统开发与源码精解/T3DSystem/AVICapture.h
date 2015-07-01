// AVICapture.h: interface for the CAVICapture class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_AVICAPTURE_H__E09FA653_808C_4BE5_8D5C_1A743DA49F77__INCLUDED_)
#define AFX_AVICAPTURE_H__E09FA653_808C_4BE5_8D5C_1A743DA49F77__INCLUDED_

#include "gl\gl.h"
#include "gl\glu.h"


#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include <vfw.h>

class CAVICapture  
{
public:
	CAVICapture();
	virtual ~CAVICapture();

    bool start(CString filename, int w, int h, float fps);
    bool end();
    bool captureFrame();

    int getWidth() const;
    int getHeight() const;
    float getFrameRate() const;

 private:
    void cleanup();

 private:
    int width;
    int height;
    float frameRate;
    int frameCounter;
    bool capturing;
    PAVIFILE aviFile;
    PAVISTREAM aviStream;
    PAVISTREAM compAviStream;
    unsigned char* image;
};

#endif // !defined(AFX_AVICAPTURE_H__E09FA653_808C_4BE5_8D5C_1A743DA49F77__INCLUDED_)
