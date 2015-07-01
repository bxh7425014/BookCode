/*
 * (c) Copyright 1993, Silicon Graphics, Inc.
 * ALL RIGHTS RESERVED 
 * Permission to use, copy, modify, and distribute this software for 
 * any purpose and without fee is hereby granted, provided that the above
 * copyright notice appear in all copies and that both the copyright notice
 * and this permission notice appear in supporting documentation, and that 
 * the name of Silicon Graphics, Inc. not be used in advertising
 * or publicity pertaining to distribution of the software without specific,
 * written prior permission. 
 *
 * THE MATERIAL EMBODIED ON THIS SOFTWARE IS PROVIDED TO YOU "AS-IS"
 * AND WITHOUT WARRANTY OF ANY KIND, EXPRESS, IMPLIED OR OTHERWISE,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY OR
 * FITNESS FOR A PARTICULAR PURPOSE.  IN NO EVENT SHALL SILICON
 * GRAPHICS, INC.  BE LIABLE TO YOU OR ANYONE ELSE FOR ANY DIRECT,
 * SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES OF ANY
 * KIND, OR ANY DAMAGES WHATSOEVER, INCLUDING WITHOUT LIMITATION,
 * LOSS OF PROFIT, LOSS OF USE, SAVINGS OR REVENUE, OR THE CLAIMS OF
 * THIRD PARTIES, WHETHER OR NOT SILICON GRAPHICS, INC.  HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH LOSS, HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, ARISING OUT OF OR IN CONNECTION WITH THE
 * POSSESSION, USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 * US Government Users Restricted Rights 
 * Use, duplication, or disclosure by the Government is subject to
 * restrictions set forth in FAR 52.227.19(c)(2) or subparagraph
 * (c)(1)(ii) of the Rights in Technical Data and Computer Software
 * clause at DFARS 252.227-7013 and/or in similar or successor
 * clauses in the FAR or the DOD or NASA FAR Supplement.
 * Unpublished-- rights reserved under the copyright laws of the
 * United States.  Contractor/manufacturer is Silicon Graphics,
 * Inc., 2011 N.  Shoreline Blvd., Mountain View, CA 94039-7311.
 *
 * OpenGL(TM) is a trademark of Silicon Graphics, Inc.
 */
/*
 *  alpha3D.c
 *  This program demonstrates how to intermix opaque and
 *  alpha blended polygons in the same scene, by using glDepthMask.
 *  Pressing the left mouse button toggles the eye position.
 */
#include <GL/gl.h>
#include <GL/glu.h>
#include <stdlib.h>
#include "aux.h"

void myinit(void)
{
    GLfloat mat_ambient[] = { 0.0, 0.0, 0.0, 0.15 };
    GLfloat mat_specular[] = { 1.0, 1.0, 1.0, 0.15 };
    GLfloat mat_shininess[] = { 15.0 };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);

    glEnable (GL_LIGHTING);
    glEnable (GL_LIGHT0);
    glDepthFunc(GL_LESS);
    glEnable(GL_DEPTH_TEST);
}

GLboolean eyePosition = GL_FALSE;

void toggleEye (AUX_EVENTREC *event)
{
    if (eyePosition)
	eyePosition = GL_FALSE;
    else
	eyePosition = GL_TRUE;
}

void display(void)
{
    GLfloat position[] = { 0.0, 0.0, 1.0, 1.0 };
    GLfloat mat_torus[] = { 0.75, 0.75, 0.0, 1.0 };
    GLfloat mat_cylinder[] = { 0.0, 0.75, 0.75, 0.15 };

    glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLightfv (GL_LIGHT0, GL_POSITION, position);
    glPushMatrix ();
	if (eyePosition)
	    gluLookAt (0.0, 0.0, 9.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); 
	else 
	    gluLookAt (0.0, 0.0, -9.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); 
	glPushMatrix ();
	glTranslatef (0.0, 0.0, 1.0);    
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_torus);
	auxSolidTorus (0.275, 0.85);
	glPopMatrix ();

	glEnable (GL_BLEND);
	glDepthMask (GL_FALSE);
	glBlendFunc (GL_SRC_ALPHA, GL_ONE);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_cylinder);
	glTranslatef (0.0, 0.0, -1.0);    
	auxSolidCylinder (1.0, 2.0);
	glDepthMask (GL_TRUE);
	glDisable (GL_BLEND);
    glPopMatrix ();

    glFlush ();
}

void myReshape(int w, int h)
{
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(30.0, (GLfloat) w/(GLfloat) h, 1.0, 20.0);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

/*  Main Loop
 *  Open window with initial window size, title bar, 
 *  RGBA display mode, and handle input events.
 */
int main(int argc, char** argv)
{
    auxInitDisplayMode (AUX_SINGLE | AUX_RGB | AUX_DEPTH);
    auxInitPosition (0, 0, 500, 500);
    auxInitWindow (argv[0]);
    auxMouseFunc (AUX_LEFTBUTTON, AUX_MOUSEDOWN, toggleEye);
    myinit();
    auxReshapeFunc (myReshape);
    auxMainLoop(display);
}






