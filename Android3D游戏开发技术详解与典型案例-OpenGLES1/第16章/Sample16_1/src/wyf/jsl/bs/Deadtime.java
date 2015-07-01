package wyf.jsl.bs;

import javax.microedition.khronos.opengles.GL10;
import static wyf.jsl.bs.Constant.*;

public class Deadtime
{
	GLGameView mv;
	Panel[] numbers=new Panel[10];
	
	public Deadtime(int texId,GLGameView mv)
	{
		this.mv=mv;
		
		//生成0-9十个数字的纹理矩形
		for(int i=0;i<10;i++)
		{
			numbers[i]=new Panel
            (
            	SCORE_NUMBER_SPAN_X,
            	SCORE_NUMBER_SPAN_Y,
            	 texId,
            	 new float[]
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),0,
		           	  0.1f*(i+1),0, 0.1f*i,1,  0.1f*(i+1),1
		             }
             ); 
		}
	}
	
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=deadtimes+"";
		for(int i=0;i<scoreStr.length();i++)
		{//将得分中的每个数字字符绘制
			char c=scoreStr.charAt(i);
			gl.glPushMatrix();
			gl.glTranslatef(i*SCORE_NUMBER_SPAN_X, 0, 0);
			numbers[c-'0'].drawSelf(gl);
			gl.glPopMatrix();
		}
	}
}