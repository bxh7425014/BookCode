package wyf.jsc.crtb;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
public class MapDesigner extends JFrame implements ActionListener{
	
	int row;
	int col;
	
	MapDesignPanel mdp;
	JScrollPane jsp;
	JButton jbGenerate=new JButton("生成地图");
	JButton jbGenerateB=new JButton("生成按钮位置");
	JButton jbGenerateS=new JButton("生成开始位置");
	JButton jbGenerateO=new JButton("生成结束位置");
	JRadioButton jrBlack=new JRadioButton("黑色",null,true);
	JRadioButton jrWhite=new JRadioButton("白色",null,false);
	JRadioButton jrButton=new JRadioButton("按钮",null,false);
	JRadioButton jrStart=new JRadioButton("起点",null,false);
	JRadioButton jrOver=new JRadioButton("终点",null,false);
	ButtonGroup bg=new ButtonGroup();
	
	Image Start;
	Image Over;
	Image Red;
	JPanel jp=new JPanel();
	public MapDesigner(int row,int col)
	{
		this.row=row;
		this.col=col;
		this.setTitle("地图设计器");
		
		Start=new ImageIcon("img/start.jpg").getImage();
		Over=new ImageIcon("img/over.jpg").getImage();
		Red=new ImageIcon("img/red.png").getImage();
		
		mdp=new MapDesignPanel(row,col,this);
		jsp=new JScrollPane(mdp);
		this.add(jsp);
		
		jp.add(jbGenerate);jp.add(jbGenerateB);jp.add(jbGenerateS);jp.add(jbGenerateO);
		jp.add(jrBlack);bg.add(jrBlack);
		jp.add(jrWhite);bg.add(jrWhite);
		jp.add(jrButton);bg.add(jrButton);
		jp.add(jrStart);bg.add(jrStart);
		jp.add(jrOver);bg.add(jrOver);
		this.add(jp,BorderLayout.NORTH);
		this.setBounds(10,10,800,800);
		
		jbGenerate.addActionListener(this);
		jbGenerateB.addActionListener(this);
		jbGenerateS.addActionListener(this);
		jbGenerateO.addActionListener(this);
		this.setVisible(true);
		this.mdp.requestFocus(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(e.getSource()==this.jbGenerate)
		    {//生成地图代码
		    	String s="public static final int[][] MAP=//0 不可通过 1可通过\n{";
				for(int i=0;i<mdp.row;i++)
				{
					s=s+"\n\t{";
					for(int j=0;j<mdp.col;j++)
					{
						s=s+mdp.mapDate[i][j]+",";
					}
					s=s.substring(0,s.length()-1)+"},";
				}
				s=s.substring(0,s.length()-1)+"\n};";
				
				new CodeFrame(s,"迷宫地图代码");
		    }
		  else if(e.getSource()==this.jbGenerateB)
		    {//生成按钮代码
		    	String s="public static final int[][] MAP_OBJECT=//表示可遇晶体位置的矩阵\n{";
				
				int ccount=0;
				
				for(int i=0;i<mdp.row;i++)
				{
					s=s+"\n\t{";
					for(int j=0;j<mdp.col;j++)
					{
						s=s+mdp.redButton[i][j]+",";
						if(mdp.redButton[i][j]==1)
						{
							ccount++;
						}
					}
					s=s.substring(0,s.length()-1)+"},";
				}
				s=s.substring(0,s.length()-1)+"\n};";
				s=s+"\npublic static final int OBJECT_COUNT="
				  +ccount+";//可遇按钮的数量";
				
				
				new CodeFrame(s,"按钮分布图代码");
		    }
			else if(e.getSource()==this.jbGenerateS)
			{
				String s="public static final int START_COL="
				+this.mdp.StartCol+";//开始位置\n"
				+"public static final int START_ROW="+this.mdp.StartRow+";";			
				new CodeFrame(s,"开始初始位置代码");
			}
			else if(e.getSource()==this.jbGenerateO)
			{
				String s="public static final int OVER_COL="
				+this.mdp.OverCol+";//终点位置\n"
				+"public static final int OVER_ROW="+this.mdp.OverRow+";";			
				new CodeFrame(s,"终点初始位置代码");
			}
	}
	public static void main(String[]args)
	{
		new MapColRowDialog();
	}
}
