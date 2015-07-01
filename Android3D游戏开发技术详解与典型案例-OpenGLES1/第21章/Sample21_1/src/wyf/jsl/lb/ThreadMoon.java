package wyf.jsl.lb;

public class ThreadMoon extends Thread
{
	GLGameView gl;
	boolean flag=true;
	
	public ThreadMoon(GLGameView gl)
	{
		this.gl=gl;
	}
	
	public void run()
	{
		while(flag)
		{
			gl.moonAngle=gl.moonAngle-0.1f;
			if(gl.moonAngle>=360)
			{
				gl.moonAngle=0;
			}
			try{
				Thread.sleep(50);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}