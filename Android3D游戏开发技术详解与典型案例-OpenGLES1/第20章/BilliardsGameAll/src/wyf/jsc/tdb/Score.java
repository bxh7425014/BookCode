package wyf.jsc.tdb;
import javax.microedition.khronos.opengles.GL10;
import static wyf.jsc.tdb.Constant.*;
public class Score {

	MySurfaceView mv;
	TextureRect[] numbers=new TextureRect[10];
	
	public Score(int texId,MySurfaceView mv)
	{
		this.mv=mv;
		
		//生成0-9十个数字的纹理矩形
		for(int i=0;i<10;i++)
		{
			numbers[i]=new TextureRect
            (
            		
            		0.6f,0.6f,0,
            		texId,
           		 new float[]//设定纹理坐标
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),1,
		           	  0.1f*(i+1),1, 0.1f*(i+1),0,  0.1f*i,0
		             }
             ); 
		}
	}
	
	public void drawSelf(GL10 gl)//练习模式
	{		
		String scoreStr=score+"";
		for(int i=0;i<scoreStr.length();i++)
		{//将得分中的每个数字字符绘制
			char c=scoreStr.charAt(i);
			gl.glPushMatrix();
			gl.glTranslatef(i*ICON_WIDTH*0.7f, 0, 0);
			numbers[c-'0'].drawSelf(gl);
			gl.glPopMatrix();
		}
	}
	public void drawSelfNet(GL10 gl,int temp)//对战模式
	{
		switch(temp)
		{
		case 1://1号玩家得分
				String scoreOneStr=scoreOne+"";
				for(int i=0;i<scoreOneStr.length();i++)
				{
					char c=scoreOneStr.charAt(i);
					gl.glPushMatrix();
					gl.glTranslatef(i*ICON_WIDTH*0.7f, 0, 0);
					numbers[c-'0'].drawSelf(gl);
					gl.glPopMatrix();
				}
			break;
		case 2://2号玩家得分
				String scoreTwoStr=scoreTwo+"";
				for(int i=0;i<scoreTwoStr.length();i++)
				{
					char c=scoreTwoStr.charAt(i);
					gl.glPushMatrix();
					gl.glTranslatef(i*ICON_WIDTH*0.7f, 0, 0);
					numbers[c-'0'].drawSelf(gl);
					gl.glPopMatrix();
				}	
			break;
		}
		
	}
}
