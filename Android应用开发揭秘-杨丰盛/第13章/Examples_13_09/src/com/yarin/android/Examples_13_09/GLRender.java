package com.yarin.android.Examples_13_09;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer
{
	public final static int MIN_DIVS = 1;
	public final static int MAX_DIVS =64;
	
	/*  Rotation about the Z axis */
	float      rotz=0.0f;
	/*  The bezier patch we're going to use (NEW) */
	BEZIER_PATCH mybezier = new BEZIER_PATCH();
	/*  Toggles displaying the control point grid (NEW) */
	boolean     showCPoints=true;
	/*  Number of intrapolations (conrols poly resolution) (NEW) */
	int          divs=7;
	
	/****************************************************************************/

	/*  Adds 2 points. Don't just use '+' ;) */
	POINT_3D pointAdd(POINT_3D p, POINT_3D q)
	{
	    p.x+=q.x;
	    p.y+=q.y;
	    p.z+=q.z;

	    return p;
	}

	/*  Multiplies a point and a constant. Don't just use '*' */
	POINT_3D pointTimes(float c, POINT_3D p)
	{
	    p.x*=c;
	    p.y*=c;
	    p.z*=c;

	    return p;
	}

	/*  Function for quick point creation */
	POINT_3D makePoint(float a, float b, float c)
	{
	    POINT_3D p = new POINT_3D();

	    p.x=a;
	    p.y=b;
	    p.z=c;

	    return p;
	}

	/*  Calculates 3rd degree polynomial based on array of 4 points */
	/*  and a single variable (u) which is generally between 0 and 1 */
	POINT_3D Bernstein(float u, POINT_3D[] p)
	{
	    POINT_3D a, b, c, d, r;

	    a=pointTimes((float)Math.pow(u,3.0f), p[0]);
	    b=pointTimes((float)(3*Math.pow(u,2.0f)*(1.0f-u)), p[1]);
	    c=pointTimes((float)(3*u*Math.pow((1.0f-u),2.0f)), p[2]);
	    d=pointTimes((float)Math.pow((1.0f-u),3.0f), p[3]);

	    r=pointAdd(pointAdd(a, b), pointAdd(c, d));

	    return r;
	}

	/*  Generates a arrays based on the data in the patch */
	/*  and the number of divisions */
	void genBezier(BEZIER_PATCH patch, int divs)
	{
	    int u=0;
	    int v;
	    float py, px, pyold; 
	    /*  make the arrays */
	    POINT_3D temp[]=new POINT_3D[4];
	    /*  array of points to mark the first line of polys */
	    POINT_3D[] last= new POINT_3D[divs+1];//(POINT_3D*)malloc(sizeof(POINT_3D)*(divs+1));

	    /*  the first derived curve (along x axis) */
	    temp[0]=patch.anchors[0][3];
	    temp[1]=patch.anchors[1][3];
	    temp[2]=patch.anchors[2][3];
	    temp[3]=patch.anchors[3][3];

	    /*  create the first line of points */
	    for (v=0; v<=divs; v++)
	    {
	        /*  percent along y axis */
	        px=((float)v)/((float)divs);
	        /*  use the 4 points from the derives curve to calculate the points along that curve */
	        last[v]=Bernstein(px, temp);
	    }
//！！！！！！！！！！！！
//	    patch.texcoords.clear();
//	    patch.vertices.clear();
	    for (u=1; u<=divs; u++)
	    {
	        /*  Percent along Y axis */
	        py=((float)u)/((float)divs);
	        /*  Percent along old Y axis */
	        pyold=((float)u-1.0f)/((float)divs);

	        /*  Calculate new bezier points */
	        temp[0]=Bernstein(py, patch.anchors[0]);
	        temp[1]=Bernstein(py, patch.anchors[1]);
	        temp[2]=Bernstein(py, patch.anchors[2]);
	        temp[3]=Bernstein(py, patch.anchors[3]);

	        /*  Begin a new triangle strip */
	        for (v=0; v<=divs; v++)
	        {
	            /*  Percent along the X axis */
	            px=((float)v)/((float)divs);

//	            /*  Apply the old texture coords */
//	            patch.texcoords.put((u-1)*2*(divs+1)+v*2,pyold);
//	            patch.texcoords.put((u-1)*2*(divs+1)+v*2+1,1.0f-px);
//	            /*  Old Point */
//	            patch.vertices.put((u-1)*2*(divs+1)+v*2,last[v].x);
//	            patch.vertices.put((u-1)*2*(divs+1)+v*2+1,last[v].y);
//	            patch.vertices.put((u-1)*2*(divs+1)+v*2+2,last[v].z);
//
//	            /*  Generate new point */
//	            last[v]=Bernstein(px, temp);
//	            /*  Apply the new texture coords */
//	            patch.texcoords.put((u-1)*2*(divs+1)+v*2+1,py);
//	            patch.texcoords.put((u-1)*2*(divs+1)+v*2+1+1,1.0f-px);
//	            /*  New Point */
//	            patch.vertices.put((u-1)*2*(divs+1)+v*2+1,last[v].x);
//	            patch.vertices.put((u-1)*2*(divs+1)+v*2+1+1,last[v].y);
//	            patch.vertices.put((u-1)*2*(divs+1)+v*2+1+2,last[v].z);
	            
	            /*  Apply the old texture coords */
	            patch.texcoords[((u-1)*2*(divs+1)+v*2)*2]=pyold;
	            patch.texcoords[((u-1)*2*(divs+1)+v*2)*2+1]=1.0f-px;
	            /*  Old Point */
	            patch.vertices[((u-1)*2*(divs+1)+v*2)*3]=last[v].x;
	            patch.vertices[((u-1)*2*(divs+1)+v*2)*3+1]=last[v].y;
	            patch.vertices[((u-1)*2*(divs+1)+v*2)*3+2]=last[v].z;

	            /*  Generate new point */
	            last[v]=Bernstein(px, temp);
	            /*  Apply the new texture coords */
	            patch.texcoords[((u-1)*2*(divs+1)+v*2+1)*2]=py;
	            patch.texcoords[((u-1)*2*(divs+1)+v*2+1)*2+1]=1.0f-px;
	            /*  New Point */
	            patch.vertices[((u-1)*2*(divs+1)+v*2+1)*3]=last[v].x;
	            patch.vertices[((u-1)*2*(divs+1)+v*2+1)*3+1]=last[v].y;
	            patch.vertices[((u-1)*2*(divs+1)+v*2+1)*3+2]=last[v].z;
	        }
	    }

	    /*  Free the old vertices array */
	    //free(last);

	    /*  Return the array */
	    return;
	}

	/****************************************************************************/
	
	void initBezier()
	{
	    /*  set the bezier vertices */
	    mybezier.anchors[0][0]=makePoint(-0.75f, -0.75f, -0.5f);
	    mybezier.anchors[0][1]=makePoint(-0.25f, -0.75f,  0.0f);
	    mybezier.anchors[0][2]=makePoint( 0.25f, -0.75f,  0.0f);
	    mybezier.anchors[0][3]=makePoint( 0.75f, -0.75f, -0.5f);
	    mybezier.anchors[1][0]=makePoint(-0.75f, -0.25f, -0.75f);
	    mybezier.anchors[1][1]=makePoint(-0.25f, -0.25f,  0.5f);
	    mybezier.anchors[1][2]=makePoint( 0.25f, -0.25f,  0.5f);
	    mybezier.anchors[1][3]=makePoint( 0.75f, -0.25f, -0.75f);
	    mybezier.anchors[2][0]=makePoint(-0.75f,  0.25f,  0.0f);
	    mybezier.anchors[2][1]=makePoint(-0.25f,  0.25f, -0.5f);
	    mybezier.anchors[2][2]=makePoint( 0.25f,  0.25f, -0.5f);
	    mybezier.anchors[2][3]=makePoint( 0.75f,  0.25f,  0.0f);
	    mybezier.anchors[3][0]=makePoint(-0.75f,  0.75f, -0.5f);
	    mybezier.anchors[3][1]=makePoint(-0.25f,  0.75f, -1.0f);
	    mybezier.anchors[3][2]=makePoint( 0.25f,  0.75f, -1.0f);
	    mybezier.anchors[3][3]=makePoint( 0.75f,  0.75f, -0.5f);
	}
	
	
	public int LoadGLTexture(GL10 gl)
	{
		int texture = -1;
		IntBuffer textureBuffer = IntBuffer.allocate(1);
		// 幹秀瞭尖
		gl.glGenTextures(1, textureBuffer);
		texture = textureBuffer.get();

        /* Typical Texture Generation Using Data From The Bitmap */
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

        /* Generate The Texture */
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);

        /* Linear Filtering */
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        return texture;
	}
	
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
	    int i, j;
	    FloatBuffer baseverts = FloatBuffer.wrap(new float[12]);

	    /*  Clear Screen And Depth Buffer */
	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	    /*  Reset The Current Modelview Matrix */
	    gl.glLoadIdentity();
	    /*  Move Left 1.5 Units And Into The Screen 6.0 */
	    gl.glTranslatef(0.0f, 0.0f, -6.0f);
	    gl.glRotatef(-75.0f, 1.0f, 0.0f, 0.0f);
	    /*  Rotate The Triangle On The Z axis ( NEW ) */
	    gl.glRotatef(rotz, 0.0f, 0.0f, 1.0f);

	    /*  Bind the texture */
	    gl.glBindTexture(GL10.GL_TEXTURE_2D, mybezier.texture);

	    /*  Draw the Bezier's array */
	    /*  this need only be updated when the patch changes */

	    /* Set pointers to vertices and texcoords */
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, FloatBuffer.wrap(mybezier.vertices) );
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0,FloatBuffer.wrap(mybezier.texcoords));

	    /* Enable vertices and texcoords arrays */
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    
	    
	    /* Draw bezier patch */
	    for (i=0; i<divs; i++)
	    {
	    	gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, i*(divs+1)*2, (divs+1)*2);
	    }
	    
	    /* Disable vertices and texcoords arrays */
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

	    /* Set pointers to vertices and texcoords */
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, baseverts);

	    /* Enable vertices array */
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    
	    
	    /*  If drawing the grid is toggled on */
	    if (showCPoints)
	    {
	    	gl.glDisable(GL10.GL_TEXTURE_2D);

	        /* Set color to Red for control points */
	    	gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

	        /*  draw the horizontal lines */
	        for(i=0; i<4; i++)
	        {
	        	baseverts.clear();
	            for(j=0; j<4; j++)
	            {
	            	baseverts.put(mybezier.anchors[i][j].x);
	            	baseverts.put(mybezier.anchors[i][j].y);
	            	baseverts.put(mybezier.anchors[i][j].z);
	            }
	            gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
	        }

	        /*  draw the vertical lines */
	        for(i=0; i<4; i++)
	        {
	        	baseverts.clear();
	            for(j=0; j<4; j++)
	            {
	            	baseverts.put(mybezier.anchors[j][i].x);
	            	baseverts.put(mybezier.anchors[j][i].y);
	            	baseverts.put(mybezier.anchors[j][i].z);
	            }
	            gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
	        }

	        /* Restore default color to avoid texture color modulation */
	        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	    }
	    
	    /* Disable vertices array */
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

	    gl.glFinish();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		float ratio;
		
	    /* Prevent A Divide By Zero By  */
	    if (height==0)
	    {
	        /* Making Height Equal One  */
	        height=1;
	    }
	    ratio=(float)width/(float)height;
	    /* Reset The Current Viewport   */
	    gl.glViewport(0, 0, width, height);

	    /* Select The Projection Matrix */
	    gl.glMatrixMode(GL10.GL_PROJECTION);
	    /* Reset The Projection Matrix  */
	    gl.glLoadIdentity();
	    
	    /* Calculate The Aspect Ratio Of The Window */
	    gl.glFrustumf(-ratio, ratio, -1, 1,4.0f, 200.0f);
	    //gluPerspective(45.0f, (GLfloat)width/(GLfloat)height, 0.1f, 100.0f);

	    /* Select The Modelview Matrix */
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    /* Reset The Modelview Matrix  */
	    gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
	    /*  Enable Texture Mapping */
		gl.glEnable(GL10.GL_TEXTURE_2D);
	    /*  Enable Smooth Shading */
		gl.glShadeModel(GL10.GL_SMOOTH);
	    /*  Black Background */
		gl.glClearColor(0.25f, 0.25f, 0.25f, 0.5f);
	    /*  Depth Buffer Setup */
		gl.glClearDepthf(1.0f);
	    /*  Enables Depth Testing */
		gl.glEnable(GL10.GL_DEPTH_TEST);
	    /*  The Type Of Depth Testing To Do */
		gl.glDepthFunc(GL10.GL_LEQUAL);
	    /*  Really Nice Perspective Calculations */
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		
	    /*  Initialize the Bezier's control grid */
	    initBezier();
	    /*  Load the texture */
	    mybezier.texture=LoadGLTexture(gl);
	    /*  Generate the patch */
	    genBezier(mybezier, divs);
	}

}


/*  Structure for a 3-dimensional point (NEW) */
class POINT_3D
{
	float x, y, z;
}
/*  Structure for a 3rd degree bezier patch (NEW) */
class BEZIER_PATCH
{
    POINT_3D    anchors[][]=new POINT_3D[4][4];    /*  4x4 grid of anchor points */
//	FloatBuffer vertices = FloatBuffer.wrap(new float[(GLRender.MAX_DIVS)*(GLRender.MAX_DIVS+1)*2*3]);
//	FloatBuffer texcoords = FloatBuffer.wrap(new float[(GLRender.MAX_DIVS)*(GLRender.MAX_DIVS+1)*2*2]);
//    public void f1()
//    {
//    }
    float     vertices[] = new float[(GLRender.MAX_DIVS)*(GLRender.MAX_DIVS+1)*2*3];
    float     texcoords[]=new float[(GLRender.MAX_DIVS)*(GLRender.MAX_DIVS+1)*2*2];
    
    int      texture;          /*  Texture for the patch */
}
