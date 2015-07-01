package wyf.tzz.gdl;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class BoxGroup
{
	
	Box box;							//箱子
	
	ArrayList<SingleBox> al=new ArrayList<SingleBox>();
	
	SingleBox sb;						//单个箱子
	
	public BoxGroup(int drawableId)
	{
		box=new Box(drawableId);		//初始化箱子
		
		sb=new SingleBox(this);		
		
		al.add(sb);						//将单个箱子加入到al里面
	}
	
	
	public void drawSelf(GL10 gl)		//绘制列表中的每个箱子
    {
    	for(int i=0;i<al.size();i++)
    	{
    		al.get(i).drawSelf(gl);		//绘制箱子
    	}
    }
	
}