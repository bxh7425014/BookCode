package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class DrawTree extends BNShape{
	public final static float TREE_WIDTH=20f;//树长度
	public final static float TREE_HEIGHT=20f;//树高度
	Tree tree;
	float direction=0;//Tree 的朝向
	public DrawTree(float scale){
		super(scale);
		tree=new Tree(scale);
	}
	public void calculateDirection(float xOffset,float zOffset,float cx,float cz)
	{//根据摄像机位置计算树朝向
		float xspan=xOffset-cx;
		float zspan=zOffset-cz;
		if(zspan<=0)
		{
			direction=(float)Math.toDegrees(Math.atan(xspan/zspan));	
		}
		else
		{ 
			direction=180+(float)Math.toDegrees(Math.atan(xspan/zspan));
		}
	} 
	@Override
	public void drawSelf(GL10 gl, int texId,int number) {
		
		gl.glPushMatrix();
		gl.glTranslatef(0, scale*TREE_WIDTH/2, 0);//TREE_HEIGHT/2*scale
		gl.glRotatef(direction, 0, 1, 0);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		tree.drawSelf(gl, texId,number);
		gl.glDisable(GL10.GL_BLEND);
		gl.glPopMatrix();	
	}
	private class Tree{
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer[] textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public Tree(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-TREE_WIDTH/2*scale,TREE_WIDTH/2*scale,0,//1
										-TREE_WIDTH/2*scale,-TREE_WIDTH/2*scale,0,//2
										TREE_WIDTH/2*scale,TREE_WIDTH/2*scale,0,//4
					
										TREE_WIDTH/2*scale,TREE_WIDTH/2*scale,0,//4
										-TREE_WIDTH/2*scale,-TREE_WIDTH/2*scale,0,//2
										TREE_WIDTH/2*scale,-TREE_WIDTH/2*scale,0,//3
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			float[][]textures=new float[][]{//树的纹理坐标
					{//第一个树的纹理坐标 
					 0,			0,
					 0,			0.7343f,
					 0.1484f,	0,
					 
					 0.1484f,	0,
					 0,			0.7343f,
					 0.1484f,	0.7343f
					},
					{//第二棵树的纹理坐标
					 0.1484f,	0,
					 0.1484f,	0.7343f,
					 0.2968f,	0,
					 
					 0.2968f,	0,
					 0.1484f,	0.7343f,
					 0.2968f,	0.7343f
					},
					{//第三棵树的纹理坐标
					 0.2972f,	0,
					 0.2972f,	0.7343f,
					 0.4453f,	0,
					 
					 0.4453f,	0,
					 0.2972f,	0.7343f,
					 0.4453f,	0.7343f
					},
					{//第四棵树的纹理坐标
					 0.4453f,	0,
					 0.4453f,	0.7343f,
					 0.5933f,	0,
					 
					 0.5933f,	0,
					 0.4453f,	0.7343f,
					 0.5933f,	0.7343f
					},
					{//第五棵树的纹理坐标
					 0.5937f,	0,
					 0.5937f,	0.7343f,
					 0.7422f,	0,
					 
					 0.7422f,	0,
					 0.5937f,	0.7343f,
					 0.7422f,	0.7343f
					},
					{//第六棵树的纹理坐标
					 0.7424f,	0,
					 0.7424f,	0.7343f,
					 0.8906f,	0,
					 
					 0.8906f,	0,
					 0.7424f,	0.7343f,
					 0.8906f,	0.7343f,
					}					
			};
			textureBuffer=new FloatBuffer[6];
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
