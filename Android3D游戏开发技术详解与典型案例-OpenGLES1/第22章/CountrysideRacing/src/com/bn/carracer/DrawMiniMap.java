package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.carracer.Constant.*;

public class DrawMiniMap
{	
	public final static float MAP_LENGHT=64;//地图长度
	public final static float MAP_HEIGHT=64;//地图高度
	
	public final static float WIDTH=MAP_LENGHT/MAP_LEVEL1[0].length;//纹理矩形半长度
	public final static float HEIGHT=MAP_HEIGHT/MAP_LEVEL1.length;//纹理矩形半高度
	
	Map map;
	float scale;
	public DrawMiniMap(float scale){
		this.scale=scale;
		map=new Map(scale);
	}

	public void drawSelf(GL10 gl, int texId,int number,int i,int j) {
		//扫描矩阵在相应位置绘制相应纹理矩形
		switch(number)
		{
		case -1:
			break;
		case 0:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();	
			break;
		case 1:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();	
			break;
		case 2:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();		
			break;
		case 3:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();	
			break;
		case 4:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();		
			break;
		case 5:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();	
			break;
		case 6:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();	
			break;
		case 7:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);
			gl.glPopMatrix();	
			break;
		case 8:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);  
			gl.glPopMatrix();	
			break;
		case 9:
			gl.glPushMatrix();
			gl.glTranslatef(-MAP_LENGHT*scale+(2*j+1)*scale*WIDTH,
					MAP_HEIGHT*scale-(2*i+1)*scale*HEIGHT, 0);
			map.drawSelf(gl, texId,number);  
			gl.glPopMatrix();	
			break;
		}
	}
	private class Map
	{
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer[] textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public Map(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-WIDTH*scale,WIDTH*scale,0,//1
										-WIDTH*scale,-WIDTH*scale,0,//2
										WIDTH*scale,WIDTH*scale,0,//4
					
										WIDTH*scale,WIDTH*scale,0,//4
										-WIDTH*scale,-WIDTH*scale,0,//2
										WIDTH*scale,-WIDTH*scale,0,//3
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			float[][]textures=new float[][]{//树的纹理坐标
					{//第一块路的纹理坐标 
					 0,			0,
					 0,			0.242f,
					 0.242f,	0,
					 
					 0.242f,	0,
					 0,			0.242f,
					 0.242f,	0.242f
					},
					{//第二块路的纹理坐标
					 0.25f,		0,
					 0.25f,		0.242f,
					 0.492f,	0, 
					 
					 0.492f,	0, 
					 0.25f,		0.242f,
					 0.492f,	0.242f
					},
					{//第三块路的纹理坐标
					 0.5f,		0,
					 0.5f,		0.242f,
					 0.742f,	0,
					 
					 0.742f,	0,
					 0.5f,		0.242f,
					 0.742f,	0.242f
					},
					{//第四块路的纹理坐标
					 0.75f,		0,
					 0.75f,		0.242f,
					 1,			0,
					 
					 1,			0,
					 0.75f,		0.242f,
					 1,			0.242f
					},
					{//第五块路的纹理坐标
					 0,			0.25f,
					 0,			0.5f,
					 0.242f,	0.25f,
					 
					 0.242f,	0.25f,
					 0,			0.5f,
					 0.242f,	0.5f
					},
					{//第六块路的纹理坐标
					 0.25f,		0.25f,
					 0.25f,		0.5f,
					 0.5f,		0.25f,
					 
					 0.5f,		0.25f,
					 0.25f,		0.5f,
					 0.5f,		0.5f,
					},		
					{//第七块路的纹理坐标
					 0.5f,		0,
					 0.5f,		0.242f,
					 0.742f,	0,
					 
					 0.742f,	0,
					 0.5f,		0.242f,
					 0.742f,	0.242f
					},
					{//第八块路的纹理坐标
					 0.75f,		0,
					 0.75f,		0.242f,
					 1,			0,
					 
					 1,			0,
					 0.75f,		0.242f,
					 1,			0.242f
					},		
					{//第九块路的纹理坐标
					 0,			0.25f,
					 0,			0.5f,
					 0.242f,	0.25f, 
					 
					 0.242f,	0.25f,
					 0,			0.5f,
					 0.242f,	0.5f
					},
					{//第十块路的纹理坐标
					 0.25f,		0.25f,
					 0.25f,		0.5f,
					 0.5f,		0.25f,
					 
					 0.5f,		0.25f,
					 0.25f,		0.5f,
					 0.5f,		0.5f,
					}
			};
			textureBuffer=new FloatBuffer[10];
			for(int i=0;i<textures.length;i++)
			{
				ByteBuffer tbb=ByteBuffer.allocateDirect(textures[i].length*4);
				tbb.order(ByteOrder.nativeOrder());
				textureBuffer[i]=tbb.asFloatBuffer();
				textureBuffer[i].put(textures[i]);
				textureBuffer[i].position(0);
			}
		}
		public void drawSelf(GL10 gl,int texId,int number) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0,vertexBuffer);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
			gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer[number]);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		}
	}
}
