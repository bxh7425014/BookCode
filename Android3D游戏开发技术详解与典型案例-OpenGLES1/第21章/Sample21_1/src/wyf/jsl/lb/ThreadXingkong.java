package wyf.jsl.lb;

public class ThreadXingkong extends Thread
{
	GLGameView gl;
	boolean flag=true;
	
	public ThreadXingkong(GLGameView gl)
	{
		this.gl=gl;
	}
	
	@Override
	public void run()
	{
		while(flag)
		{
			gl.xiaoxingkongAngle=gl.xiaoxingkongAngle+0.3f;
			gl.daxingkongAngle=gl.daxingkongAngle+0.15f;
			if(gl.xiaoxingkongAngle>360)
			{
				gl.xiaoxingkongAngle=0;
			}
			if(gl.daxingkongAngle>360)
			{
				gl.daxingkongAngle=0;
			}
			try{
				Thread.sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}