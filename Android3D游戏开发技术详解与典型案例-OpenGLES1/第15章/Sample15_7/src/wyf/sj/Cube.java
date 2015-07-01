package wyf.sj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.util.Log;
import static wyf.sj.Constant.*;

public class Cube extends BNShape{
	private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer mTextureBuffer;//纹理坐标数据缓冲
	public float mAngleY;//绕Y轴旋转
	float scale;//立方体高度			
	int vCount;//顶点数量
	int textureId;//纹理ID
	float[] tempVerteices;
	float minX;//x轴最小位置
	float maxX;//x轴最大位置
	float minY;//y轴最小位置
	float maxY;//y轴最大位置
	float minZ;//z轴最小位置
	float maxZ;//z轴最大位置
	float midX;//中心点坐标
	float midY;
	float midZ;
	public float xOffset;//在轴上移动的位置
	public float yOffset;
	public float zOffset;	
	public Cube(int textureId,float scale,float length,float width,float xOffset,float yOffset,float zOffset)
	{
		this.scale=scale;
		this.textureId=textureId;
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		this.zOffset=zOffset;
		vCount=36;
		float UNIT_SIZE=0.5f;//单位长度
		float UNIT_HIGHT=0.5f;
		float[] verteices=
		{
				
				//顶面
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				//后面
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				//前面
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				//下面
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				//左面
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				//右面
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width
						
		};
		tempVerteices=verteices;
		ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		float[] textureCoors=new float[vCount*2];
		for(int i=0;i<vCount/6;i++)//个顶点纹理坐标
		{
			textureCoors[i*12]=0;
			textureCoors[(i*12)+1]=0;
			
			textureCoors[(i*12)+2]=0;
			textureCoors[(i*12)+3]=1;
			
			textureCoors[(i*12)+4]=1;
			textureCoors[(i*12)+5]=0;
			
			textureCoors[(i*12)+6]=1;
			textureCoors[(i*12)+7]=0;
			
			textureCoors[(i*12)+8]=0;
			textureCoors[(i*12)+9]=1;
			
			textureCoors[(i*12)+10]=1;
			textureCoors[(i*12)+11]=1;		

		}
		
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建顶点坐标数据缓冲
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
		mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标数据
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
		Log.d("Cube midX=",""+midX);
		Log.d("Cube midY=",""+midY);
		Log.d("Cube midZ=",""+midZ);
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
		Log.d("Cube minX="+minX,"maxX="+(maxX-minX));
		Log.d("Cube minY="+minY,"maxY="+(maxZ-minZ));
		Log.d("Cube minZ="+minZ,"maxZ="+(maxY-minY));
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
