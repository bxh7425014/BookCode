package wyf.jsc.rtb;





import javax.microedition.khronos.opengles.GL10;
import static wyf.jsc.rtb.Constant.*;

public class Number 
{
	MySurfaceView mv;
	TextureRect[] numbers=new TextureRect[10];
	
	public Number(int texId,MySurfaceView mv)
	{
		this.mv=mv;
		
		//生成0-9十个数字的纹理矩形
		for(int i=0;i<10;i++)
		{
			numbers[i]=new TextureRect
            (
            	// 0.3f, 
            	 // 0.3f,
            	ratio*0.45f,ratio*0.45f,
            	 texId,
           		 new float[]
		             {
		           	  0.1f*(i+1),0,0.1f*i,0,0.1f*i,1,
		           	  0.1f*i,1,0.1f*(i+1),1,0.1f*(i+1),0
		             }
             ); 
		}
	}
	 
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=mv.times+"";
		for(int i=0;i<scoreStr.length();i++)
		{//将得分中的每个数字字符绘制
			char c=scoreStr.charAt(i);
			gl.glPushMatrix();
			//gl.glTranslatef(i*2.25f*ratio, 0, 0);
			gl.glTranslatef(i*0.55f, 0, 0);
			numbers[c-'0'].drawSelf(gl);
			gl.glPopMatrix();
		}
		String levelStr=level+"";
		for(int i=0;i<levelStr.length();i++)
		{
			char c=levelStr.charAt(i);
			gl.glPushMatrix();
			gl.glTranslatef(i*2.25f*ratio+7.5f*ratio, 0, 0);
			numbers[c-'0'].drawSelf(gl);
			gl.glPopMatrix();
		}
	}
}

