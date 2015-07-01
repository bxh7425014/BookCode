package com.yarin.android.Examples_13_07;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer
{
	/* Number of textures to load */
	public final static int NUM_TEXTURES = 5;

	float xrot;                   /* X Rotation           */
	float yrot;                   /* Y Rotation           */
	float zrot;                   /* Z Rotation           */
	float roll;                   /* Rolling texture      */

	int texture[] = new int[NUM_TEXTURES];   /* Storage For Textures */

	boolean masking=true;  /* Masking toggle           */
	boolean scene=false;   /* Scene toggle             */

	int loop;                   /* Generic loop variable    */
	@Override
	public void onDrawFrame(GL10 gl)
	{
		ByteBuffer indices = ByteBuffer.wrap(new byte[]{1, 0, 2, 3});
		FloatBuffer vertices = FloatBuffer.wrap(new float[12]);
		FloatBuffer texcoords = FloatBuffer.wrap(new float[8]);
		
	    /* Clear The Screen And The Depth Buffer */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

	    /* Set pointers to vertices and texcoords */
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texcoords);

	    /* Enable vertices and texcoords arrays */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

	    /* Move Into The Screen 5 Units */
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -2.0f);
		
	    /* Select Our Logo Texture */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
		
	    /* Start Drawing A Textured Quad */
		texcoords.clear();
		texcoords.put(0.0f); texcoords.put(-roll+3.0f);
		texcoords.put(3.0f); texcoords.put(-roll+3.0f);
		texcoords.put(3.0f); texcoords.put(-roll+0.0f);
		texcoords.put(0.0f); texcoords.put(-roll+0.0f);
	    vertices.clear();
	    vertices.put(-1.1f); vertices.put(-1.1f); vertices.put(0.0f);
	    vertices.put(1.1f);  vertices.put(-1.1f); vertices.put(0.0f);
	    vertices.put(1.1f);  vertices.put(1.1f);  vertices.put(0.0f);
	    vertices.put(-1.1f); vertices.put(1.1f);  vertices.put(0.0f);
	    /* Draw one textured plane using two stripped triangles */
	    gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
	    
	    /* Enable Blending */
	    gl.glEnable(GL10.GL_BLEND);
	    /* Disable Depth Testing */
	    gl.glDisable(GL10.GL_DEPTH_TEST);

	    /* Is masking enables */
	    if (masking)
	    {
	    	gl.glBlendFunc(GL10.GL_DST_COLOR, GL10.GL_ZERO);
	    }
		
	    /* Draw the second scene? */
	    if (scene)
	    {
	        /* Translate Into The Screen One Unit */
	    	gl.glTranslatef(0.0f, 0.0f, -1.0f);
	        /* Rotate On The Z Axis 360 Degrees */
	    	gl.glRotatef(roll*360, 0.0f, 0.0f, 1.0f);

	        if (masking)
	        {
	            /* Select The Second Mask Texture */
	            gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[3]);

	            /* Start Drawing A Textured Quad */
	            texcoords.clear();
	            texcoords.put(0.0f); texcoords.put(1.0f);
	            texcoords.put(1.0f); texcoords.put(1.0f);
	            texcoords.put(1.0f); texcoords.put(0.0f);
	            texcoords.put(0.0f); texcoords.put(0.0f);
	            
	            vertices.clear();
	            vertices.put(-1.1f); vertices.put(-1.1f); vertices.put(0.0f);
	            vertices.put(1.1f);  vertices.put(-1.1f); vertices.put(0.0f);
	            vertices.put(1.1f);  vertices.put(1.1f);  vertices.put(0.0f);
	            vertices.put(-1.1f); vertices.put(1.1f);  vertices.put(0.0f);

	            /* Draw one textured plane using two stripped triangles */
	            gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
	        }

	        /* Copy Image 2 Color To The Screen */
	        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
	        /* Select The Second Image Texture */
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[4]);

	        /* Start Drawing A Textured Quad */
	        texcoords.clear();
	        texcoords.put(0.0f); texcoords.put(1.0f);
	        texcoords.put(1.0f); texcoords.put(1.0f);
	        texcoords.put(1.0f); texcoords.put(0.0f);
	        texcoords.put(0.0f); texcoords.put(0.0f);
	        
	        vertices.clear();
	        vertices.put(-1.1f); vertices.put(-1.1f); vertices.put(0.0f);
	        vertices.put(1.1f);  vertices.put(-1.1f); vertices.put(0.0f);
	        vertices.put(1.1f);  vertices.put(1.1f);  vertices.put(0.0f);
	        vertices.put(-1.1f); vertices.put(1.1f);  vertices.put(0.0f);

	        /* Draw one textured plane using two stripped triangles */
	        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
	    }
	    else
	    {
	        if (masking)
	        {
	            /* Select The First Mask Texture */
	        	gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);

	            /* Start Drawing A Textured Quad */
	        	texcoords.clear();
	        	texcoords.put(roll+0.0f); texcoords.put(4.0f);
	        	texcoords.put(roll+4.0f); texcoords.put(4.0f);
	        	texcoords.put(roll+4.0f); texcoords.put(0.0f);
	        	texcoords.put(roll+0.0f); texcoords.put(0.0f);
	        	vertices.clear();
	        	vertices.put(-1.1f); vertices.put(-1.1f); vertices.put(0.0f);
	        	vertices.put(1.1f);  vertices.put(-1.1f); vertices.put(0.0f);
	        	vertices.put(1.1f);  vertices.put(1.1f);  vertices.put(0.0f);
	        	vertices.put(-1.1f); vertices.put(1.1f);  vertices.put(0.0f);

	            /* Draw one textured plane using two stripped triangles */
	            gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
	        }

	        /* Copy Image 1 Color To The Screen */
	        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
	        /* Select The First Image Texture */
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);

	        /* Start Drawing A Textured Quad */
	        texcoords.clear();
	        texcoords.put(roll+0.0f); texcoords.put(4.0f);
	        texcoords.put(roll+4.0f); texcoords.put(4.0f);
	        texcoords.put(roll+4.0f); texcoords.put(0.0f);
	        texcoords.put(roll+0.0f); texcoords.put(0.0f);
	        vertices.clear();
	        vertices.put(-1.1f); vertices.put(-1.1f); vertices.put(0.0f);
	        vertices.put(1.1f);  vertices.put(-1.1f); vertices.put(0.0f);
	        vertices.put(1.1f);  vertices.put(1.1f);  vertices.put(0.0f);
	        vertices.put(-1.1f); vertices.put(1.1f);  vertices.put(0.0f);

	        /* Draw one textured plane using two stripped triangles */
	        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
	    }
	    
	    gl.glEnable(GL10.GL_DEPTH_TEST); /* Enable Depth Testing */
	    gl.glDisable(GL10.GL_BLEND);     /* Disable Blending     */

	    /* Disable texcoords and vertices arrays */
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

	    /* Draw it to the screen */
	    gl.glFinish();

	    /* Increase Our Texture Roll Variable */
	    roll+=0.002f; 
	    if (roll>1.0f)
	    {
	        roll-=1.0f;
	    }
	    
	    xrot+=0.3f; /* X Axis Rotation */
	    yrot+=0.2f; /* Y Axis Rotation */
	    zrot+=0.4f; /* Z Axis Rotation */
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
	    /* Height / width ration */
	    float ratio;

	    /* Protect against a divide by zero */
	    if (height==0)
	    {
	        height=1;
	    }

	    ratio=(float)width/(float)height;

	    /* Setup our viewport. */
	    gl.glViewport(0, 0, (int)width, (int)height);

	    /*
	     * change to the projection matrix and set
	     * our viewing volume.
	     */
	    gl.glMatrixMode(GL10.GL_PROJECTION);
	    gl.glLoadIdentity();

	    /* Set our perspective */
	    gl.glFrustumf(-ratio, ratio, -1, 1, 1.0f, 100.0f);

	    /* Make sure we're chaning the model view and not the projection */
	    gl.glMatrixMode(GL10.GL_MODELVIEW);

	    /* Reset The View */
	    gl.glLoadIdentity();
	}
	
	public void LoadGLTextures(GL10 gl)
	{
		IntBuffer textureBuffer = IntBuffer.allocate(5);
		// ¥¥Ω®Œ∆¿Ì
		gl.glGenTextures(5, textureBuffer);
		texture = textureBuffer.array();
		
        /* Typical Texture Generation Using Data From The Bitmap */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
        /* Generate The Texture */
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmapLogo, 0);
        /* Linear Filtering */
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        
        /* Typical Texture Generation Using Data From The Bitmap */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
        /* Generate The Texture */
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmapMask1, 0);
        /* Linear Filtering */
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        
        /* Typical Texture Generation Using Data From The Bitmap */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
        /* Generate The Texture */
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmapImage1, 0);
        /* Linear Filtering */
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        
        /* Typical Texture Generation Using Data From The Bitmap */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[3]);
        /* Generate The Texture */
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmapMask2, 0);
        /* Linear Filtering */
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        
        /* Typical Texture Generation Using Data From The Bitmap */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[4]);
        /* Generate The Texture */
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmapImage2, 0);
        /* Linear Filtering */
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		LoadGLTextures(gl);
		
	    /* Set the background black */
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

	    /* Depth buffer setup */
		gl.glClearDepthf(1.0f);

	    /* Enables Depth Testing */
		gl.glEnable(GL10.GL_DEPTH_TEST);

	    /* Enables Smooth Color Shading */
		gl.glShadeModel(GL10.GL_SMOOTH);

	    /* Enable 2D Texture Mapping */
		gl.glEnable(GL10.GL_TEXTURE_2D);
	}

}

