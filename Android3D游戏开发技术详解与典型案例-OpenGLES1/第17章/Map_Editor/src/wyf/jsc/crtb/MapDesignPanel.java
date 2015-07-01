package wyf.jsc.crtb;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
public class MapDesignPanel extends JPanel implements MouseListener,MouseMotionListener{

	int row;
	 int col;
	 final int span=32;
	 MapDesigner md;
	 int[][]mapDate;
	 int[][]redButton;
	 int StartRow;
	 int StartCol;
	 int OverRow;
	 int OverCol;
	 boolean StartFlag=false;
	 boolean OverFlag=false;
	 public MapDesignPanel(int row,int col,MapDesigner md)
	 {
		 this.row=row;
		 this.col=col;
		 this.md=md;
		 this.setPreferredSize(new Dimension(span*row,span*col));
		 mapDate=new int[row][col];
		 redButton=new int[row][col];
		 for(int i=0;i<row;i++)
		 {
			 for(int j=0;j<col;j++)
			 {
				 mapDate[i][j]=0;
			 }
		 }
		 for(int i=0;i<row;i++)
		 {
			 for(int j=0;j<col;j++)
			 {
				 redButton[i][j]=0;
			 }
		 }
		 this.addMouseListener(this);
		 this.addMouseMotionListener(this);
	 }
	 public  void paint(Graphics g)
	 {
		 g.setColor(Color.black);
		 g.fillRect(0, 0, span*row, span*col);
		 
		 for(int i=0;i<mapDate.length;i++)
		 {
			 for(int j=0;j<mapDate[0].length;j++)
			 {
				 if(mapDate[i][j]==0)
				 {
					 g.setColor(Color.white);
					 g.fillRect(j*span, i*span, span, span);
				 }
			 }
		 }
			for(int i=0;i<redButton.length;i++)
			{
				for(int j=0;j<redButton[0].length;j++)
				{
					if(redButton[i][j]==1)
					{		
						g.drawImage(md.Red,j*span+1,i*span+3,this);
					}
				}
			}
			if(this.StartFlag)
			{
				g.drawImage(md.Start,StartCol*span+1,StartRow*span+3,this);
			}
			if(this.OverFlag)
			{
				g.drawImage(md.Over,OverCol*span+1,OverRow*span+3,this);
			}
		 g.setColor(Color.green);
		 for(int j=0;j<col+1;j++)
		 {
			 g.drawLine(span*j,0,span*j,span*row);
		 }
		 for(int i=0;i<row+1;i++)
		 {
			 g.drawLine(0, span*i, span*col, span*i);
		 }
	 }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(md.jrBlack.isSelected()||md.jrWhite.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			int rowC=y/span;
			int colC=x/span;
			if(rowC>=row|colC>=col)
			{
				return;
			}
			mapDate[rowC][colC]=(mapDate[rowC][colC]+1)%2;
			if(mapDate[rowC][colC]==0)
			{
				redButton[rowC][colC]=0;
				this.StartFlag=false;
				this.OverFlag=false;
			}
		}
		else if(md.jrOver.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			int rowC=y/span;
			int colC=x/span;
			if(rowC>=row|colC>=col)
			{
				return;
			}
			if(mapDate[rowC][colC]==1)
			{
			   	JOptionPane.showMessageDialog
			   	(
			   		this,
			   		"不可通过处不能摆放物品！",
			   		"摆放错误",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}
			this.OverFlag=true;
			OverRow=rowC;
			OverCol=colC;
		}
		else if(md.jrStart.isSelected())
			{
				int x=e.getX();
				int y=e.getY();
				int rowC=y/span;
				int colC=x/span;
				if(rowC>=row|colC>=col)
				{
					return;
				}
				if(mapDate[rowC][colC]==0)
				{
				   	JOptionPane.showMessageDialog
				   	(
				   		this,
				   		"不可通过处不能摆放物品！",
				   		"摆放错误",
				   		JOptionPane.ERROR_MESSAGE
				   	);
				   	return;
				}
				this.StartFlag=true;
				StartRow=rowC;
				StartCol=colC;
			}
		else 
		{
			int x=e.getX();
			int y=e.getY();
			int rowC=y/span;
			int colC=x/span;
			if(rowC>=row|colC>=col)
			{
				return;
			}
			if(mapDate[rowC][colC]==0)
			{
			   	JOptionPane.showMessageDialog
			   	(
			   		this,
			   		"不可通过处不能摆放按钮！",
			   		"摆放错误",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}
			redButton[rowC][colC]=(redButton[rowC][colC]+1)%2;
		}
	
		
		this.repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
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
			
			mapDate[rowC][colC]=md.jrBlack.isSelected()?1:0;
			if(mapDate[rowC][colC]==0)
			{
				redButton[rowC][colC]=0;
				this.StartFlag=false;
				this.OverFlag=false;
			}	
		}		
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
