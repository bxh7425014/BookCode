package wyf.sj;
import static wyf.sj.Constant.BIGER_FACTER;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class ZhuiTi extends BNShape{
	private FloatBuffer mVertexBuffer; //顶点坐标数据缓冲
	private FloatBuffer mTextureBuffer;// 纹理坐标数据缓冲
	int textureId;//纹理ID
	int vCount;//顶点数量

	float mAngleY;
	float[] tempVerteices;//声明顶点数组
	float minX;//x轴最小位置
	float maxX;//x轴最大位置
	float minY;//y轴最小位置
	float maxY;//y轴最大位置
	float minZ;//z轴最小位置
	float maxZ;//z轴最大位置
	float midX;//中心点坐标
	float midY;
	float midZ;
	float xOffset;//在轴上移动的位置
	float yOffset;
	float zOffset;
	public ZhuiTi(int textureId,float length,float hight,float width,float xOffset,float yOffset,float zOffset)
	{
		this.textureId=textureId;
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		this.zOffset=zOffset;
		float UNIT_SIZE=1.4f;
		float WALL_HEIGHT=2.8f;
		float vScale=0.7f;
	    float hScale=0.2f;
		float[] verteices=
		{

				0,WALL_HEIGHT*vScale*hight,0,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,

	        	0,WALL_HEIGHT*vScale*hight,0,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,

	            0,WALL_HEIGHT*vScale*hight,0,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,

	            0,WALL_HEIGHT*vScale*hight,0,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,

	            0,0,0,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,

	            0,0,0,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,

	            0,0,0,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,-UNIT_SIZE*hScale*width,

	            0,0,0,
	        	UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,
	        	-UNIT_SIZE*hScale*length,WALL_HEIGHT*vScale/2*hight,UNIT_SIZE*hScale*width,
				
				
		};
		vCount=verteices.length/3;//获取定点数量
		ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		
		tempVerteices=verteices;
		
		 float textureCoors[]=new float[]
        {
        	0.5f,0f,  0f,1f,  1f,1f,
        	0.5f,0f,  0f,1f,  1f,1f,
        	0.5f,0f,  0f,1f,  1f,1f,
        	0.5f,0f,  0f,1f,  1f,1f,
        	0.5f,0f,  0f,1f,  1f,1f,
        	0.5f,0f,  0f,1f,  1f,1f,
        	0.5f,0f,  0f,1f,  1f,1f,
        	0.5f,0f,  0f,1f,  1f,1f,
        };
		ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建纹理坐标数据缓冲
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
		mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标
		mTextureBuffer.position(0);//设置缓冲区起始位置
		
	}
	@Override
	public void drawSelf(GL10 gl)
	{

		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glTranslatef(xOffset, yOffset, zOffset);
		if(hiFlag)
		{
			gl.glScalef(BIGER_FACTER, BIGER_FACTER, BIGER_FACTER);
		}	
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数组
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//绑定纹理
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
		
	}
	@Override
	public float[] findMid() {
		// TODO Auto-generated method stub
		midX=(minX+maxX)/2+xOffset;
		midY=(minY+maxY)/2+yOffset;
		midZ=(minZ+maxZ)/2+zOffset;
		Log.d("ZhuiTi midX=",""+midX);
		Log.d("ZhuiTi midY=",""+midY);
		Log.d("ZhuiTi midZ=",""+midZ);
		float[] mid={midX,midY,midZ};
		return mid;
	}
	@Override
	public float[] findMinMax() {
		// TODO Auto-generated method stub
		for(int i=0;i<tempVerteices.length/3;i++)
		{
			//判断X轴的最小和最大位置
			if(tempVerteices[i*3]<minX)
			{
				minX=tempVerteices[i*3];
			}
			if(tempVerteices[i*3]>maxX)
			{
				maxX=tempVerteices[i*3];
			}
			//判断Y轴的最小和最大位置
			if(tempVerteices[i*3+1]<minY)
			{
				minY=tempVerteices[i*3+1];
			}
			if(tempVerteices[i*3+1]>maxY)
			{
				maxY=tempVerteices[i*3+1];
			}
			//判断Z轴的最小和最大位置
			if(tempVerteices[i*3+2]<minZ)
			{
				minZ=tempVerteices[i*3+2];
			}
			if(tempVerteices[i*3+2]>maxZ)
			{
				maxZ=tempVerteices[i*3+2];
			}
		}
		Log.d("ZhuiTi minX=",""+(maxX-minX));
		Log.d("ZhuiTi minY=",""+(maxY-minY));
		Log.d("ZhuiTi minZ=",""+(maxZ-minZ));
		float[] length={maxX-minX,maxZ-minZ,maxY-minY};
		
		return length;
	}
	@Override
	public void setHilight(boolean flag)
	{
		this.hiFlag=flag;
		Log.d("cube",""+this.hiFlag);
	}
}
