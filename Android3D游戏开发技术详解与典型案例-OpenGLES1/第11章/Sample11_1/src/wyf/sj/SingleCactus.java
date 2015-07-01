package wyf.sj;
import javax.microedition.khronos.opengles.GL10;
import static wyf.sj.MySurfaceView.*;
public class SingleCactus implements Comparable<SingleCactus>
{
	float x;//x坐标
	float z;//z坐标
	float yAngle;//标志版朝向
	CactusGroup cg;
	
	public SingleCactus(float x,float z,float yAngle,CactusGroup cg)
	{
		this.x=x;
		this.z=z;
		this.yAngle=yAngle;
		this.cg=cg;
	}
	
	public void calculateBillboardDirection()
	{//根据摄像机位置计算仙人掌面朝向
		float xspan=x-cx;
		float zspan=z-cz;
		
		if(zspan<=0)
		{
			yAngle=(float)Math.toDegrees(Math.atan(xspan/zspan));	
		}
		else
		{
			yAngle=180+(float)Math.toDegrees(Math.atan(xspan/zspan));
		}
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();		
		gl.glTranslatef(x, 0, z);
		gl.glRotatef(yAngle, 0, 1, 0);
		cg.cfd.drawSelf(gl);		
		gl.glPopMatrix();
	}

	@Override
	public int compareTo(SingleCactus another) 
	{		
		//重写的比较两个仙人掌离摄像机距离的方法
		float xs=x-cx;
		float zs=z-cz;
		
		float xo=another.x-cx;
		float zo=another.z-cz;
		
		float disA=(float)Math.sqrt(xs*xs+zs*zs);
		float disB=(float)Math.sqrt(xo*xo+zo*zo);
		
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;  
	}
}
