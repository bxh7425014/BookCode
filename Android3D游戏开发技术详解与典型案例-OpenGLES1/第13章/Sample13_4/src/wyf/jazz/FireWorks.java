package wyf.jazz;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

//代表焰火粒子系统的类
public class FireWorks {
	 //用于绘制的各个颜色粒子组成的数组
     static ParticleForDraw[] pfdArray=new ParticleForDraw[]
     {
    	 new ParticleForDraw(2,0.9882f,0.9882f,0.8784f,0),
    	 new ParticleForDraw(2,0.9216f,0.2784f,0.2392f,0),
    	 new ParticleForDraw(2,1.0f,0.3686f,0.2824f,0),
    	 new ParticleForDraw(2,0.8157f,0.9882f,0.6863f,0),
    	 new ParticleForDraw(2,0.9922f,0.7843f,0.9882f,0),
    	 new ParticleForDraw(2,0.1f,1,0.3f,0)    	 
     };
     
     //所有焰火粒子的列表
     ArrayList<SingleParticle> al=new ArrayList<SingleParticle>();
     //定时运动所有焰火粒子的线程 
     FireWorksThread fwt;
     public FireWorks()
     {
    	 //初始化定时运动所有焰火粒子的线程并启动
    	 fwt=new FireWorksThread(this);
    	 fwt.start();
     }
     
     public void drawSelf(GL10 gl)
     {
		 int size=al.size();
		 //循环扫描所有焰火粒子的列表并绘制各个粒子
		 for(int i=0;i<size;i++)
    	 {
			 try
	    	 {
    		   al.get(i).drawSelf(gl);
	    	 }
			 catch(Exception e)
	    	 {
				e.printStackTrace();
	    	 }
    	 } 
     }
}
