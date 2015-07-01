package wyf.jsl.lb;

public class ThreadLight extends Thread
{
	GLGameView gl;
	boolean flag=true;
	
	public ThreadLight(GLGameView gl)
	{
		this.gl=gl;
	}
	
	public void run()
	{
		while(flag)
		{
			gl.lightAngle=gl.lightAngle+5;
			try{
				Thread.sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}