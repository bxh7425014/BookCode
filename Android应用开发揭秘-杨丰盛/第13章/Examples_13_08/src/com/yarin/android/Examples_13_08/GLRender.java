package com.yarin.android.Examples_13_08;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;

public class GLRender implements Renderer
{
	public Random random = new Random();
	FloatBuffer vertices = FloatBuffer.wrap(new float[512*3*3]);
	FloatBuffer colors = FloatBuffer.wrap(new float[512*3*4]);
	
	float xrot, yrot, zrot,         // X, Y & Z Rotation
    xspeed, yspeed, zspeed,   // X, Y & Z Spin Speed
    cx, cy, cz=-15;           // X, Y & Z Position
	boolean morph=false; /* Default morph To False (Not Morphing) */
	int step=0, steps=200;    /* Step Counter And Maximum Number Of Steps        */
	int maxver; /* Will Eventually Hold The Maximum Number Of Vertices */
	
	/* Our 4 Morphable Objects (morph1,2,3 & 4) */
	OBJECT morph1 = new OBJECT(), morph2= new OBJECT(), morph3= new OBJECT(), morph4= new OBJECT(),
	       helper= new OBJECT(), sour, dest; /* Helper Object, Source Object, Destination Object */
	
	          
	@Override
	public void onDrawFrame(GL10 gl)
	{
		int i;
		/* Temp X, Y & Z Variables */
		float tx, ty, tz;
		/* Holds Returned Calculated Values For One Vertex */
		VERTEX q = new VERTEX(0,0,0);
		
	    /* Clear The Screen And The Depth Buffer */
	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	    /* Reset The View */
	    gl.glLoadIdentity();
	    /* Translate The The Current Position To Start Drawing */
	    gl.glTranslatef(cx, cy, cz);
	    /* Rotate On The X Axis By xrot */
	    gl.glRotatef(xrot, 1, 0, 0);
	    /* Rotate On The Y Axis By yrot */
	    gl.glRotatef(yrot, 0, 1, 0);
	    /* Rotate On The Z Axis By zrot */
	    gl.glRotatef(zrot, 0, 0, 1);

	    /* Increase xrot,yrot & zrot by xspeed, yspeed & zspeed */
	    xrot+=xspeed; yrot+=yspeed; zrot+=zspeed;
	    
	    /* Set pointers to vertex and texcoord arrays */
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
	    gl.glColorPointer(4, GL10.GL_FLOAT, 0, colors);

	    /* Enable vertex and color arrays */
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	    
	    
	    colors.clear();
	    vertices.clear();
	    /* Begin Drawing Points */
	    /* Loop Through All The Verts Of morph1 */
	    for(i=0; i<morph1.verts; i++)
	    {
	        /* If morph Is True Calculate Movement Otherwise Movement=0 */
	        if (morph)
	        {
	            q=calculate(i);
	        }
	        else
	        {
	            q.x=q.y=q.z=0;
	        }
	        
	        /* Subtract q.x Units From helper.points[i].x (Move On X Axis) */
	        helper.points.get(i).x-=q.x;
	        /* Subtract q.y Units From helper.points[i].y (Move On Y Axis) */
	        helper.points.get(i).y-=q.y;
	        /* Subtract q.z Units From helper.points[i].z (Move On Z Axis) */
	        helper.points.get(i).z-=q.z;
	        /* Make Temp X Variable Equal To Helper's X Variable */
	        tx=helper.points.get(i).x;
	        /* Make Temp Y Variable Equal To Helper's Y Variable */
	        ty=helper.points.get(i).y;
	        /* Make Temp Z Variable Equal To Helper's Z Variable */
	        tz=helper.points.get(i).z;
	        
	        /* Set Color To A Bright Shade Of Off Blue */
	        colors.put(0.0f); colors.put(1.0f); colors.put(1.0f); colors.put(1.0f);
	        /* Set A Point At The Current Temp Values (Vertex) */
	        vertices.put(tx); vertices.put(ty); vertices.put(tz);

	        /* Darken Color A Bit */
	        colors.put(0.0f); colors.put(0.5f); colors.put(1.0f); colors.put(1.0f);
	        /* Calculate Two Positions Ahead */
	        tx-=2*q.x; ty-=2*q.y; ty-=2*q.y;
	        // Draw A Second Point At The Newly Calculate Position
	        vertices.put(tx); vertices.put(ty); vertices.put(tz);

	        /* Set Color To A Very Dark Blue */
	        colors.put(0.0f); colors.put(0.0f); colors.put(1.0f); colors.put(1.0f);
	        /* Calculate Two More Positions Ahead */
	        tx-=2*q.x; ty-=2*q.y; ty-=2*q.y;
	        /* Draw A Third Point At The Second New Position */
	        vertices.put(tx); vertices.put(ty); vertices.put(tz);
	        /* This Creates A Ghostly Tail As Points Move */
	        
	    }
	    
	    /* Done Drawing Points */
	    gl.glDrawArrays(GL10.GL_POINTS, 0, morph1.verts*3);

	    /* Disable vertex and color arrays */
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	    /* If We're Morphing And We Haven't Gone Through All 200 Steps Increase */
	    /* Our Step Counter Otherwise Set Morphing To False, Make Source=Dest   */
	    /* And Set The Step Counter Back To Zero.                               */
	    if (morph && step<=steps)
	    {
	        step++;
	    }
	    else
	    {
	        morph=false;
	        sour=dest;
	        step=0;
	    }

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
	    gl.glFrustumf(-ratio, ratio, -1, 1, 2.0f, 100.0f);
	    //gluPerspective(45.0f, (GLfloat)width/(GLfloat)height, 0.1f, 100.0f);

	    /* Select The Modelview Matrix */
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    /* Reset The Modelview Matrix  */
	    gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		int i;

		/* Set The Blending Function For Translucency */
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		/* This Will Clear The Background Color To Black */
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		/* Enables Clearing Of The Depth Buffer */
		gl.glClearDepthf(1.0f);
		/* The Type Of Depth Test To Do */
		gl.glDepthFunc(GL10.GL_LESS);
		/* Enables Depth Testing */
		gl.glEnable(GL10.GL_DEPTH_TEST);
		/* Enables Smooth Color Shading */
		gl.glShadeModel(GL10.GL_SMOOTH);
		/* Really Nice Perspective Calculations */
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

	    /* Sets Max Vertices To 0 By Default             */
	    maxver=0;
	    /* Load The First Object Into morph1 From File sphere.txt */
	    objload("sphere.txt",morph1);
	    /* Load The Second Object Into morph2 From File torus.txt */
	    objload("torus.txt",morph2);
	    /* Load The Third Object Into morph3 From File tube.txt   */
	    objload("tube.txt",morph3);
	    
	    for(i=0; i<486; i++)
	    {
	        /* morph4 x Point Becomes A Random Float Value From -7 to 7 */
	        float xx=((float)(rand()%14000)/1000)-7;
	        /* morph4 y Point Becomes A Random Float Value From -7 to 7 */
	        float yy=((float)(rand()%14000)/1000)-7;
	        /* morph4 z Point Becomes A Random Float Value From -7 to 7 */
	        float zz=((float)(rand()%14000)/1000)-7;
	        
	        morph4.points.add(new VERTEX(xx,yy,zz));
	    }
	    
	    /* Load sphere.txt Object Into Helper (Used As Starting Point) */
	    objload("tube.txt", helper);
	    /* Source & Destination Are Set To Equal First Object (morph1) */
	    sour=dest=morph1;
	}
	
	
	public int rand()
	{
		return Math.abs(random.nextInt());
	}
	/* Reads A String From File (f) */
	public String readstr(BufferedReader br)
	{
		String str = "";
		try
		{
		    /* Do This */
		    do {
		        /* Gets A String Of 255 Chars Max From f (File) */
		        //fgets(string, 255, f);
		        str=br.readLine();
		        /* Until End Of Line Is Reached */
		    } while ((str.charAt(0) == '/')||(str.charAt(0)=='\n'));
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	    return str;
	}
	
	/* Loads Object From File (name) */
	void objload(String name, OBJECT k)
	{
	    int   ver = 0;           /* Will Hold Vertice Count                */
	    String  oneline;  /* Holds One Line Of Text (255 Chars Max) */
	    int   i;
	    
		BufferedReader br = new BufferedReader(new InputStreamReader(GLFile.getFile(name)));	
		//读出顶点数
		oneline = readstr(br);
		ver=Integer.valueOf(oneline).intValue();
		
		k.verts = ver;
		
		for (i=0; i<ver; i++)
		{
			oneline=readstr(br);
			String part[] = oneline.trim().split("\\s+");
			float x = Float.valueOf(part[0]);
			float y = Float.valueOf(part[1]);
			float z = Float.valueOf(part[2]);
			VERTEX vertex = new VERTEX(x, y, z);
			k.points.add(vertex);
		}
		
	    if (ver>maxver)
	    {
	        /* If ver Is Greater Than maxver Set maxver Equal To ver */
	        maxver=ver;
	    }
	    /* Keeps Track Of Highest Number Of Vertices Used In Any Of The Objects */
	}

	/* Calculates Movement Of Points During Morphing */
	VERTEX calculate(int i)
	{
	    /* Temporary Vertex Called a */
	    VERTEX a = new VERTEX(0,0,0);

	    /* a.x Value Equals Source x - Destination x Divided By Steps */
	    a.x=(sour.points.get(i).x-dest.points.get(i).x)/steps;
	    /* a.y Value Equals Source y - Destination y Divided By Steps */
	    a.y=(sour.points.get(i).y-dest.points.get(i).y)/steps;
	    /* a.z Value Equals Source z - Destination z Divided By Steps */
	    a.z=(sour.points.get(i).z-dest.points.get(i).z)/steps;

	    /* Return The Results */
	    return a;
	    /* This Makes Points Move At A Speed So They All Get To Their */
	    /* Destination At The Same Time                               */
	}
	
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_1:
				if (!morph)
				{
					morph = true;
					dest = morph1;
				}
				break;
			case KeyEvent.KEYCODE_2:
				if (!morph)
				{
					morph = true;
					dest = morph2;
				}
				break;
			case KeyEvent.KEYCODE_3:
				if (!morph)
				{
					morph = true;
					dest = morph3;
				}
				break;
			case KeyEvent.KEYCODE_4:
				if (!morph)
				{
					morph = true;
					dest = morph4;
				}
				break;
			case KeyEvent.KEYCODE_N:
				zspeed+=0.01f;//增加绕z轴旋转的速度
				break;
			case KeyEvent.KEYCODE_M:
				zspeed-=0.01f;//减少绕z轴旋转的速度
				break;
			case KeyEvent.KEYCODE_Q:
				cz-=0.01f;// 向屏幕里移动
				break;
			case KeyEvent.KEYCODE_Z:
				cz+=0.01f;// 向屏幕外移动
				break;
			case KeyEvent.KEYCODE_W:
				cy+=0.01f;// 向上移动
				break;
			case KeyEvent.KEYCODE_S:
				cy-=0.01f;// 向下移动
				break;
			case KeyEvent.KEYCODE_D:
				cx+=0.01f;// 向右移动
				break;
			case KeyEvent.KEYCODE_A:
				cx-=0.01f;// 向左移动
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				xspeed-=0.01f;// 减少绕x轴旋转的速度
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				xspeed+=0.01f;// 增加绕x轴旋转的速度
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				yspeed-=0.01f;// 减少沿y轴旋转的速度
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				yspeed+=0.01f;// 增加沿y轴旋转的速度
				break;
		}
		return false;
	}
	
}

class VERTEX
{
	float x, y, z;        /* X, Y & Z Points */
	public VERTEX(float x,float y,float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}

class OBJECT
{
    int     verts;        /* Number Of Vertices For The Object  */
    //VERTEX points;       /* One Vertice (Vertex x, y & z)      */
    List<VERTEX>	points	= new ArrayList<VERTEX>();
}



