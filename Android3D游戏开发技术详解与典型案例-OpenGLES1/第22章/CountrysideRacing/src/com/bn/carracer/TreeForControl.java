package com.bn.carracer;
public class TreeForControl implements Comparable<TreeForControl>
{
	float xOffset;
	float yOffset;
	float zOffset;
	int id;//»æÖÆÊ÷µÄ±àºÅ
	float[] cameraPosition;
	
	public TreeForControl(float xOffset,float yOffset,float zOffset,int id)
	{
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		this.zOffset=zOffset;
		this.id=id;
	}
	
	public void setCameraPosition(float[] cameraPosition)
	{
		this.cameraPosition=cameraPosition;		
	}

	public int compareTo(TreeForControl another) 
	{
		double disOther=Math.sqrt(
		  (another.xOffset-cameraPosition[0])*(another.xOffset-cameraPosition[0])+
		  (another.zOffset-cameraPosition[2])*(another.zOffset-cameraPosition[2])
		);
	
		double disSelf=Math.sqrt(
			(this.xOffset-cameraPosition[0])*(this.xOffset-cameraPosition[0])+
			(this.zOffset-cameraPosition[2])*(this.zOffset-cameraPosition[2])
		);
		
		if(disOther>disSelf)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}