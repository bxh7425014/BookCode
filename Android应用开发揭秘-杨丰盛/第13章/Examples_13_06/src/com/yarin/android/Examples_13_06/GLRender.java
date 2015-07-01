package com.yarin.android.Examples_13_06;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer
{
	
	public final static int MAX_PARTICLES =1000;
	boolean rainbow=true;  /* Toggle rainbow effect                         */
	Random random = new Random();
	float slowdown=0.5f;   /* Slow Down Particles                                */
	float xspeed=1;        /* Base X Speed (To Allow Keyboard Direction Of Tail) */
	float yspeed=3;        /* Base Y Speed (To Allow Keyboard Direction Of Tail) */
	float zoom=-30.0f;     /* Used To Zoom Out                                   */

	int loop;           /* Misc Loop Variable                                 */
	int col=0;          /* Current Color Selection                            */
	int delay;          /* Rainbow Effect Delay                               */
	int texture;     /* Storage For Our Particle Texture                   */
	
	/* Rainbow of colors */
	static float colors[][]=
	{
	   {1.0f,  0.5f,  0.5f},
	   {1.0f,  0.75f, 0.5f},
	   {1.0f,  1.0f,  0.5f},
	   {0.75f, 1.0f,  0.5f},
	   {0.5f,  1.0f,  0.5f},
	   {0.5f,  1.0f,  0.75f},
	   {0.5f,  1.0f,  1.0f},
	   {0.5f,  0.75f, 1.0f},
	   {0.5f,  0.5f,  1.0f},
	   {0.75f, 0.5f,  1.0f},
	   {1.0f,  0.5f,  1.0f},
	   {1.0f,  0.5f,  0.75f}
	};
	
	/* Our beloved array of particles */
	particle particles[] = new particle[MAX_PARTICLES];
	
	
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		FloatBuffer vertices = FloatBuffer.wrap(new float[12]);
		FloatBuffer texcoords = FloatBuffer.wrap(new float[8]);

		/* Clear The Screen And The Depth Buffer */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		/* Enable vertices and texcoords arrays */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		/* Set pointers to vertices and texcoords */
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texcoords);

		gl.glLoadIdentity();

		/* Modify each of the particles */
		for (loop = 0; loop < MAX_PARTICLES; loop++)
		{
			if (particles[loop].active)
			{
				/* Grab Our Particle X Position */
				float x = particles[loop].x;

				/* Grab Our Particle Y Position */
				float y = particles[loop].y;

				/* Particle Z Position + Zoom */
				float z = particles[loop].z + zoom;

				/*
				 * Draw The Particle Using Our RGB Values, Fade The Particle
				 * Based On It's Life
				 */
				gl.glColor4f(particles[loop].r, particles[loop].g, particles[loop].b, particles[loop].life);

				texcoords.clear();
				vertices.clear();
				/* Top Right */
				texcoords.put(1.0f);
				texcoords.put(1.0f);
				vertices.put(x + 0.5f);
				vertices.put(y + 0.5f);
				vertices.put(z);

				/* Top Left */
				texcoords.put(0.0f);
				texcoords.put(1.0f);
				vertices.put(x - 0.5f);
				vertices.put(y + 0.5f);
				vertices.put(z);

				/* Bottom Right */
				texcoords.put(1.0f);
				texcoords.put(0.0f);
				vertices.put(x + 0.5f);
				vertices.put(y - 0.5f);
				vertices.put(z);

				/* Bottom Left */
				texcoords.put(0.0f);
				texcoords.put(0.0f);
				vertices.put(x - 0.5f);
				vertices.put(y - 0.5f);
				vertices.put(z);

				/* Build Quad From A Triangle Strip */
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				/* Move On The X Axis By X Speed */
				particles[loop].x += particles[loop].xi / (slowdown * 1000);
				/* Move On The Y Axis By Y Speed */
				particles[loop].y += particles[loop].yi / (slowdown * 1000);
				/* Move On The Z Axis By Z Speed */
				particles[loop].z += particles[loop].zi / (slowdown * 1000);

				/* Take Pull On X Axis Into Account */
				particles[loop].xi += particles[loop].xg;
				/* Take Pull On Y Axis Into Account */
				particles[loop].yi += particles[loop].yg;
				/* Take Pull On Z Axis Into Account */
				particles[loop].zi += particles[loop].zg;

				/* Reduce Particles Life By 'Fade' */
				particles[loop].life -= particles[loop].fade;

				/* If the particle dies, revive it */
				if (particles[loop].life < 0.0f)
				{
					float xi, yi, zi;

					xi = xspeed + (float) ((rand() % 60) - 32.0f);
					yi = yspeed + (float) ((rand() % 60) - 30.0f);
					zi = (float) ((rand() % 60) - 30.0f);
					ResetParticle(loop, col, xi, yi, zi);
				}
			}
		}
		   /* Disable texcoords and vertices arrays */
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		/* Draw it to the screen */
		gl.glFinish();
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

	    /* change to the projection matrix and set our viewing volume. */
	    gl.glMatrixMode(GL10.GL_PROJECTION);
	    gl.glLoadIdentity();

	    /* Set our perspective */
	    gl.glFrustumf(-ratio, ratio, -1, 1, 1.0f, 200.0f);

	    /* Make sure we're chaning the model view and not the projection */
	    gl.glMatrixMode(GL10.GL_MODELVIEW);

	    /* Reset The View */
	    gl.glLoadIdentity();
	}
	
	public void LoadGLTextures(GL10 gl)
	{
		IntBuffer textureBuffer = IntBuffer.allocate(1);
		// 创建纹理
		gl.glGenTextures(1, textureBuffer);
		texture = textureBuffer.get();

        /* Typical Texture Generation Using Data From The Bitmap */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		
        /* Linear Filtering */
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        
        /* Generate The Texture */
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		LoadGLTextures(gl);

		/* Enable smooth shading */
		gl.glShadeModel(GL10.GL_SMOOTH);

		/* Set the background black */
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		/* Depth buffer setup */
		gl.glClearDepthf(1.0f);

		/* Enables Depth Testing */
		gl.glDisable(GL10.GL_DEPTH_TEST);

		/* Enable Blending */
		gl.glEnable(GL10.GL_BLEND);

		/* Type Of Blending To Perform */
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);

		/* Really Nice Perspective Calculations */
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		/* Really Nice Point Smoothing */
		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);

		/* Enable Texture Mapping */
		gl.glEnable(GL10.GL_TEXTURE_2D);

		/* Select Our Texture */
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

		/* Reset all the particles */
		for (loop = 0; loop < MAX_PARTICLES; loop++)
		{
			int color = loop*(12/MAX_PARTICLES);
			float xi, yi, zi;

			xi = (float) ((rand() % 50) - 26.0f) * 10.0f;
			yi = zi = (float) ((rand() % 50) - 25.0f) * 10.0f;

			ResetParticle(loop, color, xi, yi, zi);
		}
	}

	public int rand()
	{
		return Math.abs(random.nextInt(1000));
	}
	
	public void ResetParticle(int num, int color, float xDir, float yDir, float zDir)
	{
		particle tmp = new particle();
	    /* Make the particels active */
		tmp.active=true;
	    /* Give the particles life */
		tmp.life=1.0f;
	    /* Random Fade Speed */
		tmp.fade=(float)(rand()%100)/1000.0f+0.003f;
	    /* Select Red Rainbow Color */
		tmp.r=colors[color][0];
	    /* Select Green Rainbow Color */
		tmp.g=colors[color][1];
	    /* Select Blue Rainbow Color */
		tmp.b=colors[color][2];
	    /* Set the position on the X axis */
		tmp.x=0.0f;
	    /* Set the position on the Y axis */
		tmp.y=0.0f;
	    /* Set the position on the Z axis */
		tmp.z=0.0f;
	    /* Random Speed On X Axis */
		tmp.xi=xDir;
	    /* Random Speed On Y Axi */
		tmp.yi=yDir;
	    /* Random Speed On Z Axis */
		tmp.zi=zDir;
	    /* Set Horizontal Pull To Zero */
	    tmp.xg=0.0f;
	    /* Set Vertical Pull Downward */
	    tmp.yg=-0.5f;
	    /* Set Pull On Z Axis To Zero */
	    tmp.zg=0.0f;

	    
	    particles[num] = tmp;
	    return;
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event)
//	{
//		switch (keyCode)
//		{
//			case KeyEvent.KEYCODE_8:
//				if (particles[loop].yg < 1.5f)
//				{
//					particles[loop].yg += 0.01f;
//				}
//				break;
//			case KeyEvent.KEYCODE_2:
//				if (particles[loop].yg > -1.5f)
//				{
//					particles[loop].yg -= 0.01f;
//				}
//				break;
//			case KeyEvent.KEYCODE_6:
//				if ( particles[loop].xg<1.5f )
//				{
//					particles[loop].xg+=0.01f;
//				}
//				break;
//			case KeyEvent.KEYCODE_4:
//				if ( particles[loop].xg>-1.5f )
//				{
//					 particles[loop].xg-=0.01f;
//				}
//				break;
//			case KeyEvent.KEYCODE_TAB:// 按Tab键，使粒子回到原点
//				particles[loop].x=0.0f;					
//				particles[loop].y=0.0f;					
//				particles[loop].z=0.0f;					
//				particles[loop].xi=(float)((rand()%50)-26.0f)*10.0f;	// 随机生成速度
//				particles[loop].yi=(float)((rand()%50)-25.0f)*10.0f;	
//				particles[loop].zi=(float)((rand()%50)-25.0f)*10.0f;
//				break;
//			case KeyEvent.KEYCODE_D://+
//				if ( slowdown>1.0f )
//				{
//					slowdown-=0.01f;		// 按+号，加速粒子
//				}
//				break;
//			case KeyEvent.KEYCODE_A://-
//				if ( slowdown<4.0f )
//				{
//					slowdown+=0.01f;	// 按-号，减速粒子
//				}
//				break;
//			case KeyEvent.KEYCODE_W:
//				zoom+=0.1f;		// 让粒子靠近视点
//				break;
//			case KeyEvent.KEYCODE_S:
//				zoom-=0.1f;		// 让粒子远离视点
//				break;
//			case KeyEvent.KEYCODE_ENTER:
//				if ( !rp )
//				{
//					rp=true;			
//					rainbow=!rainbow;
//				}
//				break;
//			case KeyEvent.KEYCODE_SPACE:
//				if ( !sp || rainbow && (delay>25) )
//				{
//					rainbow=false;
//					sp=true;			
//					delay=0;			
//					col++;
//					if (col>11) col=0;
//				}
//				break;
//			case KeyEvent.KEYCODE_DPAD_UP:
//				if ( yspeed<200 )
//				{
//					// 按上增加粒子Y轴正方向的速度
//					yspeed+=1.0f;
//				}
//				break;
//			case KeyEvent.KEYCODE_DPAD_DOWN:
//				if ( yspeed>-200 )
//				{
//					// 按下减少粒子Y轴正方向的速度
//					yspeed-=1.0f;
//				}
//				break;
//			case KeyEvent.KEYCODE_DPAD_RIGHT:
//				if ( xspeed<200 )
//				{
//					// 按右增加粒子X轴正方向的速度
//					xspeed+=1.0f;
//				}
//				break;
//			case KeyEvent.KEYCODE_DPAD_LEFT:
//				if ( xspeed>-200 )
//				{
//					// 按左减少粒子X轴正方向的速度
//					xspeed-=1.0f;
//				}
//				break;
//			
//		}
//		return false;
//	}
//	
//	public boolean onKeyUp(int keyCode, KeyEvent event)
//	{
//		switch ( keyCode )
//		{
//			case KeyEvent.KEYCODE_ENTER:
//				rp=false;
//				break;
//			case KeyEvent.KEYCODE_SPACE:
//				sp=false;		// 如果释放空格键，记录这个状态
//				break;
//		}
//		return false;
//	}
}


