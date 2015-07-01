package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
class DrawHouse extends BNShape{

	public static final float HOUSE_WIDTH=10.13f;//房子的宽度
	public static final float HOUSE_HEIGHT=10f;//房子的高度
	public static final float HOUSE_HEIGHT_DOWN=5.95f;//矩形高度
	public static final float HOUSE_HEIGHT_UP=(HOUSE_HEIGHT-HOUSE_HEIGHT_DOWN);//三角形高度
	public static final float HOUSE_LENGTH=1.5f*HOUSE_WIDTH;//房子的长度
	public static final float ROOF_TIMES=1.25f;//屋顶宽度和屋子宽度的倍数
	public static final float ROOF_TIMES_LENGTH=1.1f;//屋顶长度和屋子长度的
	
	public static final float ROOF_WIDTH_TIMES=0.8f;//屋顶的厚度
	public static final float ROOF_WIDTH=0.9f;//屋顶上顶面到下顶面的厚度
	
	House_Front houseFront;//房子前面
	House_Right houseRight;//房子右侧
	House_Left  houseLeft;//房子的左侧
	House_Roof houseRoof;//屋顶
	public float yAngle;
	public DrawHouse(float scale) {
		super(scale);
		houseFront=new House_Front(scale);
		houseRight=new House_Right(scale);
		houseLeft=new House_Left(scale);
		houseRoof=new House_Roof(scale);
	}

	@Override
	public void drawSelf(GL10 gl, int texId,int number) {
		gl.glRotatef(yAngle, 0, 1, 0);
		//绘制房子前面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, HOUSE_LENGTH/2*scale);
		houseFront.drawSelf(gl, texId,0);
		gl.glPopMatrix();
		//绘制房子右面
		gl.glPushMatrix();
		gl.glTranslatef(HOUSE_WIDTH/2*scale, HOUSE_HEIGHT_DOWN/2*scale, HOUSE_LENGTH/2*scale);
		gl.glRotatef(-90, 0, 0, 1);
		houseRight.drawSelf(gl, texId);
		gl.glPopMatrix();
		//绘制房子左面
		gl.glPushMatrix();
		gl.glTranslatef(-HOUSE_WIDTH/2*scale,HOUSE_HEIGHT_DOWN/2*scale, HOUSE_LENGTH/2*scale);
		gl.glRotatef(90, 0, 0, 1);
		houseLeft.drawSelf(gl, texId);
		gl.glPopMatrix();
		//绘制房子后面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, HOUSE_LENGTH/2*scale-HOUSE_LENGTH*scale);
		gl.glRotatef(180, 0, 1, 0);
		houseFront.drawSelf(gl, texId,1);
		gl.glPopMatrix();
		//绘制屋顶
		gl.glPushMatrix();
		gl.glTranslatef(0, (HOUSE_HEIGHT+ROOF_WIDTH)*scale, (HOUSE_LENGTH*ROOF_TIMES_LENGTH)/2*scale);
		houseRoof.drawSelf(gl, texId);
		gl.glPopMatrix();
	}
	private class House_Front{//房子前面部分的内部类
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer[]textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public House_Front(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										0,HOUSE_HEIGHT*scale,0,
										-HOUSE_WIDTH/2*scale,HOUSE_HEIGHT_DOWN*scale,0,
										HOUSE_WIDTH/2*scale,HOUSE_HEIGHT_DOWN*scale,0,
	                
										-HOUSE_WIDTH/2*scale,HOUSE_HEIGHT_DOWN*scale,0,
										-HOUSE_WIDTH/2*scale,0,0,
										HOUSE_WIDTH/2*scale,HOUSE_HEIGHT_DOWN*scale,0,
	                
										HOUSE_WIDTH/2*scale,HOUSE_HEIGHT_DOWN*scale,0,
										-HOUSE_WIDTH/2*scale,0,0,
										HOUSE_WIDTH/2*scale,0,0	                	
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			
			
			float[][]textures=new float[][]{
						{//房子的前墙
						 0.75f,0,
						 0.5f,0.203f,
						 1,0.203f,
			
						 0.5f,0.203f,
						 0.5f,0.5f,
						 1,0.203f,
			
						 1,0.203f,
						 0.5f,0.5f, 
						 1,0.5f			
						},
						{//房子的后墙
						 0.25f,0,
						 0,0.203f,
						 0.5f,0.203f,
						 
						 0,0.203f,
						 0,0.5f,
						 0.5f,0.203f,
						 
						 0.5f,0.203f,
						 0,0.5f,
						 0.5f,0.5f
						}
						
											};
			textureBuffer=new FloatBuffer[2];
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
	private class House_Right{//绘制房子右侧
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public House_Right(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-HOUSE_HEIGHT_DOWN/2*scale,0,-HOUSE_LENGTH*scale,
										-HOUSE_HEIGHT_DOWN/2*scale,0,0,
										HOUSE_HEIGHT_DOWN/2*scale,0,-HOUSE_LENGTH*scale,
										
										HOUSE_HEIGHT_DOWN/2*scale,0,-HOUSE_LENGTH*scale,
										-HOUSE_HEIGHT_DOWN/2*scale,0,0,
										HOUSE_HEIGHT_DOWN/2*scale,0,0
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			
			float[]textures=new float[]{			
				
				1,0.5f,
				0,0.5f,
				1,1,
				
				1,1,
				0,0.5f,
				0,1
				
					
			};
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			textureBuffer=tbb.asFloatBuffer();
			textureBuffer.put(textures);
			textureBuffer.position(0);
		}

		public void drawSelf(GL10 gl,int texId) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0,vertexBuffer);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
			gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		}

	}
	
	private class House_Left{//绘制房子左侧
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public House_Left(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-HOUSE_HEIGHT_DOWN/2*scale,0,-HOUSE_LENGTH*scale,
										-HOUSE_HEIGHT_DOWN/2*scale,0,0,
										HOUSE_HEIGHT_DOWN/2*scale,0,-HOUSE_LENGTH*scale,
										
										HOUSE_HEIGHT_DOWN/2*scale,0,-HOUSE_LENGTH*scale,
										-HOUSE_HEIGHT_DOWN/2*scale,0,0,
										HOUSE_HEIGHT_DOWN/2*scale,0,0
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);

			float[]textures=new float[]{			
				
				0,1,
				1,1,
				0,0.5f,
				
				0,0.5f,
				1,1,
				1,0.5f
				
					
			};
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			textureBuffer=tbb.asFloatBuffer();
			textureBuffer.put(textures);
			textureBuffer.position(0);
		}

		public void drawSelf(GL10 gl,int texId) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0,vertexBuffer);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
			gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		}

	}
	
	
	private class House_Roof{//房顶的内部类
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public House_Roof(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
					//上顶面
					0,0,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,//1
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,//2
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,//3
					
					0,0,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,//1
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,//3
					0,0,0,//4
					
					0,0,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,//1
					0,0,0,//4
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,//6
					
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,//6
					0,0,0,//4
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,//5
	                //屋顶前面
					//左侧
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					0,0,0,
					
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					0,-ROOF_WIDTH*scale,0,
					0,0,0,
					//右侧
					0,-ROOF_WIDTH*scale,0,
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					0,0,0,
					0,-ROOF_WIDTH*scale,0,
					
					//屋顶后面
					//左侧
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,
					0,0,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,
					
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,
					0,0,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					0,-ROOF_WIDTH*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					
					//右侧
					0,-ROOF_WIDTH*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					0,0,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					0,-ROOF_WIDTH*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					
					//下顶面
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,
					0,-ROOF_WIDTH*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					
					0,-ROOF_WIDTH*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					0,-ROOF_WIDTH*scale,0,
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					
					0,-ROOF_WIDTH*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					0,-ROOF_WIDTH*scale,0,
					
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					0,-ROOF_WIDTH*scale,0,
					
					//右底面
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					
					-HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*ROOF_TIMES_LENGTH*scale,
					-HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,
					
					//左底面
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,//5
					
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,-HOUSE_LENGTH*scale*ROOF_TIMES_LENGTH,
					HOUSE_WIDTH/2*ROOF_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0,//5
					HOUSE_WIDTH/2*ROOF_TIMES*ROOF_WIDTH_TIMES*scale,-HOUSE_HEIGHT_UP*ROOF_TIMES*scale,0
					
			};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);

			float[]textures=new float[]{//纹理坐标数组
				//上顶面纹理数组
				0.578f,0.004f,//1
				0.5f,0.07f,//2
				0.5f,0.16f,//3
				
				0.578f,0.004f,//1
				0.5f,0.16f,//3
				0.578f,0.078f,//4
				
				0.918f,0.004f,//1
				0.918f,0.078f,//4
				1,0.07f,//6
				
				1,0.07f,//6
				0.918f,0.078f,//4
				1,0.16f,//5
				//屋顶前面
				//左侧
				0.52f,0.152f,
				0.52f,0.168f,
				0.695f,0.012f,
				
				0.52f,0.168f,
				0.695f,0.027f,
				0.695f,0.012f,
				//右侧
				0.52f,0.168f,
				0.695f,0.012f,
				0.695f,0.027f,
				
				0.52f,0.168f,
				0.695f,0.027f,
				0.695f,0.012f,
				//屋顶后面
				//左侧
				0.52f,0.152f,
				0.695f,0.012f,
				0.52f,0.168f,
				
				0.52f,0.168f,
				0.695f,0.012f,
				0.695f,0.027f,
				//右侧
				0.52f,0.168f,
				0.695f,0.027f,
				0.695f,0.012f,
				
				0.52f,0.168f,
				0.695f,0.012f,
				0.695f,0.027f,
				
				//下顶面纹理数组
				0.5f,0.07f,//2
				0.578f,0.004f,//1
				0.5f,0.16f,//3
				
				0.578f,0.004f,//1
				0.578f,0.078f,//4
				0.5f,0.16f,//3
				
				0.918f,0.004f,//1
				1,0.07f,//6
				0.918f,0.078f,//4
				  
				1,0.07f,//6
				1,0.16f,//5
				0.918f,0.078f,//4
				
				//右底面
				0.52f,0.168f,
				0.695f,0.012f,
				0.695f,0.027f,
				
				0.52f,0.168f,
				0.695f,0.027f,
				0.695f,0.012f,
				//左底面
				0.52f,0.168f,
				0.695f,0.012f,
				0.695f,0.027f,
				
				0.52f,0.168f,
				0.695f,0.027f,
				0.695f,0.012f
				
			};
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			textureBuffer=tbb.asFloatBuffer();
			textureBuffer.put(textures);
			textureBuffer.position(0);
		}
		public void drawSelf(GL10 gl,int texId) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0,vertexBuffer);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
			gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		}

	}
}
