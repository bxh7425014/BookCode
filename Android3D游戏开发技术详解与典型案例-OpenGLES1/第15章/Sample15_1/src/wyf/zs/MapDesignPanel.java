package wyf.zs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapDesignPanel extends JPanel 
implements MouseListener,MouseMotionListener
{
	int row;
	int col;
	int span=32;
	MapDesigner md;
	
	int[][] mapData;
	
	int[][] diamondMap;
	
	int cameraCol;
	int cameraRow;
	boolean cameraFlag=false;
	
	public MapDesignPanel(int row,int col,MapDesigner md)
	{
		this.row=row;
		this.col=col;
		this.md=md;
		
		this.setPreferredSize
		(
			new Dimension(span*col,span*row)
		);
		
		mapData=new int[row][col];
		
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				mapData[i][j]=0;
			}
		}
		
		diamondMap=new int[row][col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				diamondMap[i][j]=0;
			}
		}
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,span*col,span*row);
		
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[0].length;j++)
			{
				if(mapData[i][j]==0)
				{//绘制白色格子
					g.setColor(Color.white);
					g.fillRect(j*span,i*span,span,span);
				}
			}
		}
		
		for(int i=0;i<diamondMap.length;i++)
		{
			for(int j=0;j<diamondMap[0].length;j++)
			{
				if(diamondMap[i][j]==1)
				{//绘制晶体			
					g.drawImage(md.icrystal,j*span+1,i*span+3,this);
				}
			}
		}
		
		//绘制摄像机
		if(this.cameraFlag)
		g.drawImage(md.iCamera,cameraCol*span+1,cameraRow*span+3,this);
		
		
		g.setColor(Color.green);		
		for(int i=0;i<row+1;i++)
		{
			g.drawLine(0,span*i,span*col,span*i);
		}
		
		for(int j=0;j<col+1;j++)
		{
			g.drawLine(span*j,0,span*j,span*row);
		}
	}	

	public void mouseClicked(MouseEvent e)
	{
		if(md.jrBlack.isSelected()||md.jrWhite.isSelected())
		{//设置地图可通过性
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapData[rowC][colC]=(mapData[rowC][colC]+1)%2;
			if(mapData[rowC][colC]==0)
			{//清除晶体与摄像机
				diamondMap[rowC][colC]=0;
				this.cameraFlag=false;
			}			
		}
		else if(md.jrCrystal.isSelected())
		{//摆放晶体
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			if(mapData[rowC][colC]==0)
			{
			   	JOptionPane.showMessageDialog
			   	(
			   		this,
			   		"不可通过处不能摆放晶体！",
			   		"摆放错误",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}
			diamondMap[rowC][colC]=(diamondMap[rowC][colC]+1)%2;
		}
		else if(md.jrCamera.isSelected())
		{//摆放摄像机
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(mapData[rowC][colC]==0)
			{
			   	JOptionPane.showMessageDialog
			   	(
			   		this,
			   		"不可通过处不能摆放摄像机！",
			   		"摆放错误",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}
			
			this.cameraCol=colC;
			this.cameraRow=rowC;
			this.cameraFlag=true;
		}
		
		this.repaint();
	}
	
	public void mousePressed(MouseEvent e)
	{
		
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}
	
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if(md.jrBlack.isSelected()||md.jrWhite.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapData[rowC][colC]=md.jrBlack.isSelected()?1:0;
			if(mapData[rowC][colC]==0)
			{//清除晶体与摄像机
				diamondMap[rowC][colC]=0;
				this.cameraFlag=false;
			}	
		}		
		this.repaint();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		
	}
	
	public static void main(String args[])
	{
		new MapColRowDialog();
	}
}
