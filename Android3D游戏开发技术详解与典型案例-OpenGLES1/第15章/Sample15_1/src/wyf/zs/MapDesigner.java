package wyf.zs;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapDesigner extends JFrame
implements ActionListener
{
	int row;
	int col;
	
	MapDesignPanel mdp;
	JScrollPane jsp;
	JButton jbGenerate=new JButton("生成地图");
	JButton jbGenerateD=new JButton("生成晶体位置");
	JButton jbGenerateC=new JButton("生成摄像机位置");
	JRadioButton jrBlack=new JRadioButton("黑色",null,true);
	JRadioButton jrWhite=new JRadioButton("白色",null,false);
	JRadioButton jrCrystal=new JRadioButton("晶体",null,false);
	JRadioButton jrCamera=new JRadioButton("摄像机",null,false);
	ButtonGroup bg=new ButtonGroup();
	
	Image icrystal;
	Image iCamera;
	
	JPanel jp=new JPanel();
	
	public MapDesigner(int row,int col)
	{
		this.row=row;
		this.col=col;		
		this.setTitle("简单的地图设计器");
		
		icrystal=new ImageIcon("img/Diamond.png").getImage();
		iCamera=new ImageIcon("img/camera.png").getImage();
		
		
		mdp=new MapDesignPanel(row,col,this);
		jsp=new JScrollPane(mdp);
		
		this.add(jsp);
		
		jp.add(jbGenerate);jp.add(jbGenerateD);jp.add(jbGenerateC);
		jp.add(jrBlack);bg.add(jrBlack);
		jp.add(jrWhite);bg.add(jrWhite);
		jp.add(jrCrystal);bg.add(jrCrystal);
		jp.add(jrCamera);bg.add(jrCamera);		
		this.add(jp,BorderLayout.NORTH);
		jbGenerate.addActionListener(this);
		jbGenerateD.addActionListener(this);
		jbGenerateC.addActionListener(this);
		
		this.setBounds(10,10,800,600);
		this.setVisible(true);
		this.mdp.requestFocus(true);	
	}
	
	public void actionPerformed(ActionEvent e)
	{		
	    if(e.getSource()==this.jbGenerate)
	    {//生成地图代码
	    	String s="public static final int[][] MAP=//0 不可通过 1可通过\n{";
			for(int i=0;i<mdp.row;i++)
			{
				s=s+"\n\t{";
				for(int j=0;j<mdp.col;j++)
				{
					s=s+mdp.mapData[i][j]+",";
				}
				s=s.substring(0,s.length()-1)+"},";
			}
			s=s.substring(0,s.length()-1)+"\n};";
			
			new CodeFrame(s,"迷宫地图代码");			
	    }
	    else if(e.getSource()==this.jbGenerateD)
	    {//生成晶体代码
	    	String s="public static final int[][] MAP_OBJECT=//表示可遇晶体位置的矩阵\n{";
			
			int ccount=0;
			
			for(int i=0;i<mdp.row;i++)
			{
				s=s+"\n\t{";
				for(int j=0;j<mdp.col;j++)
				{
					s=s+mdp.diamondMap[i][j]+",";
					if(mdp.diamondMap[i][j]==1)
					{
						ccount++;
					}
				}
				s=s.substring(0,s.length()-1)+"},";
			}
			s=s.substring(0,s.length()-1)+"\n};";
			s=s+"\npublic static final int OBJECT_COUNT="
			  +ccount+";//可遇晶体的数量";
			
			
			new CodeFrame(s,"晶体分布图代码");
	    }
		else if(e.getSource()==this.jbGenerateC)
		{//生成摄像机位置
			String s="public static final int CAMERA_COL="
			+this.mdp.cameraCol+";//初始时Camera位置\n"
			+"public static final int CAMERA_ROW="+this.mdp.cameraRow+";";			
			new CodeFrame(s,"摄像机初始位置代码");
		}
	}
	
	public static void main(String args[])
	{
		new MapColRowDialog();
	}
}
