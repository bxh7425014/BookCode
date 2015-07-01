package wyf.sj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Stack;

import android.util.Log;
import android.view.SurfaceHolder;

public class Game{
	MySurfaceView mSurfaceView;//创建绘制类引用
	int[][] map=MapList.map[0];//需要搜索的地图
	int[] source=MapList.source;//出发点坐标
	int[] target=MapList.targetA[0];//目标点col,row
	int algorithmId=0;//算法代号，0--深度测试
	
	ArrayList<int[][]> searchProcess=new ArrayList<int[][]>();//搜索过程
	Stack<int[][]> stack=new Stack<int[][]>();//深度优先所用栈
	LinkedList<int[][]> queue=new LinkedList<int[][]>();//广度优先所用队列
	PriorityQueue<int[][]> astarQueue=new PriorityQueue<int[][]>(100,new AStarComparator(this));//A*优先级队列
	HashMap<String,int[][]> hm=new HashMap<String,int[][]>();//结果路径记录
	int[][] visited=new int[19][19];//0 未去过 1 已去过
	int[][] length=new int[19][19];//记录路径长度 for Dijkstra
	// 记录到每个点的最短路径for Dijkstra
	HashMap<String,ArrayList<int[][]>> hmPath=new HashMap<String,ArrayList<int[][]>>();
	
	boolean pathFlag=false;//true 找到路径
	int timeSpan=10;//时间间隔
	SurfaceHolder holder;
	int[][] sequence=
	{
		{0,1},{0,-1},
		{-1,0},{1,0},
		{-1,1},{-1,-1},
		{1,-1},{1,1}
	};
	
	int tempCount;//记录个搜索方法所用步数
	final int DFS_COUNT=1;//深度优先使用步数标志
	final int BFS_COUNT=2;//广度优先使用步数标志
	final int BFSASTAR_COUNT=3;//广度优先使用步数标志
	final int DIJKSTRA_COUNT=4;//Dijkstra使用步数标志
	final int DIJKSTRASTAR_COUNT=5;//DijkstraA*使用步数标志
	public Game(MySurfaceView mSurfaceView,SurfaceHolder holder)
	{
		this.mSurfaceView=mSurfaceView;
		this.holder=holder;
	}
	public void clearState()//初始化个引用
	{
		searchProcess.clear();//清空搜索过程列表
		stack.clear();//清空深度优先所用栈
		queue.clear();//清空广度优先所用队列
		astarQueue.clear();//清空A*优先级队列
		hm.clear();//清空结果路径记录
		visited=new int[19][19];//初始化数组
		pathFlag=false;	//寻找路经标志位
		hmPath.clear();//清空Dijkstra中记录到每个点的最短路径
		mSurfaceView.paint.setStrokeWidth(0);//初始化画笔
		for(int i=0;i<length.length;i++)
		{
			for(int j=0;j<length[0].length;j++)
			{
				length[i][j]=9999;//设置初始路径的长度（不可能这么大）
			}
		}	
	}
	public void runAlgorithm()
	{
		clearState();//调用初始化方法
		switch(algorithmId)
		{
			case 0://深度优先算法
			  DFS();
			break;
			case 1://广度优先算法			
			  BFS();
			break;
			case 2://广度优先A*算法
			  BFSAStar();
			break;
			case 3://Dijkstra算法
			  Dijkstra();
			  Log.d("Dijkstra", "algorithmId="+algorithmId);
			break;
			case 4://DijkstraA*算法
			  DijkstraAStar();
			break;
		}		
	}
	
	public void DFS()//深度优先
	{
		new Thread()
		{
			public void run()
			{
				boolean flag=true;//线程标志位
				int[][] start=//初始化出发点坐标
				{
					{source[0],source[1]},
					{source[0],source[1]}
				};
				stack.push(start);//入栈
				int count=0;//使用步数技术器
				while(flag)
				{
					
					int[][] currentEdge=stack.pop();//从栈中取出边
					int[] tempTarget=currentEdge[1];//取出此边的目的点
					
					//判断目的点是否去过，若去过，则直接进入下次循环
					if(visited[tempTarget[1]][tempTarget[0]]==1)
					{
						continue;
					}
					count++;//计数器自加
					//表示目的点被访问过
					visited[tempTarget[1]][tempTarget[0]]=1;
					
					//将临时目标点加入搜索过程中
					searchProcess.add(currentEdge);
					//记录此临时节点的父节点
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
					//重绘画布
					mSurfaceView.repaint(holder);
					//线程睡眠一定时间
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					
					//判断是否到达目的点
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1])
					{
						break;
					}
					
					//将所有可能的边入栈
					int currCol=tempTarget[0];//取边节点
					int currRow=tempTarget[1];
					
					for(int[] rc:sequence)//扫描该点附近所有可能的边
					{
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}//若为0，0结束该次循环
						if(currRow+i>=0&&currRow+i<19&&currCol+j>=0&&currCol+j<19&&
						map[currRow+i][currCol+j]!=1)//若在地图内
						{
							int[][] tempEdge=
							{
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							stack.push(tempEdge);//入栈
						}
					}
				}
				pathFlag=true;	//标志位设为true
				mSurfaceView.repaint(holder);//重绘画布
				tempCount=count;//深度优先使用步数
				mSurfaceView.mActivity.hd.sendEmptyMessage(DFS_COUNT);//发送消息更改使用步数数量
				mSurfaceView.mActivity.button.setClickable(true);	//设置button可以点击
			}
		}.start();		
	}
	
	
	public void BFS()//广度优先
	{
		new Thread()
		{
			public void run()
			{
				int count=0;//计数器
				boolean flag=true;//循环标志位
				int[][] start=//开始状态
				{
					{source[0],source[1]},
					{source[0],source[1]}
				};
				queue.offer(start);//将开始点加入该队列的末尾
				
				while(flag)
				{					
					int[][] currentEdge=queue.poll();//获取并移除表头
					int[] tempTarget=currentEdge[1];//取出此边的目的点
					
					//判断是否去过，若去过则直接进入下次循环
					if(visited[tempTarget[1]][tempTarget[0]]==1)
					{
						continue;
					}
					count++;//计数器自加
					//将去过的点置为1
					visited[tempTarget[1]][tempTarget[0]]=1;
					
					//降临时目标点加入搜索过程
					searchProcess.add(currentEdge);
					//记录此临时节点的父节点
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
					//重绘画布
					mSurfaceView.repaint(holder);
					//线程睡眠一定时间
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					
					//判断是否为目的点
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1])
					{
						break;
					}
					
					//将所有可能的边入队列
					int currCol=tempTarget[0];
					int currRow=tempTarget[1];
					
					for(int[] rc:sequence)
					{
						int i=rc[1];
						int j=rc[0];
						
						if(i==0&&j==0){continue;}//若在地图外面，进入下一次循环
						if(currRow+i>=0&&currRow+i<19&&currCol+j>=0&&currCol+j<19&&
						map[currRow+i][currCol+j]!=1)//若为地图内的点
						{
							int[][] tempEdge=
							{
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							queue.offer(tempEdge);//将改点加入队列末尾
						}
					}
				}
				pathFlag=true;	//标志位设为true
				mSurfaceView.repaint(holder);//重绘画布
				tempCount=count;//广度优先使用步数
				mSurfaceView.mActivity.hd.sendEmptyMessage(BFS_COUNT);	//发送消息更改使用步数数量
				mSurfaceView.mActivity.button.setClickable(true);//设置button键可以点击
			}
		}.start();			
	}
	
	
	public void BFSAStar()//广度优先A*
	{
		new Thread()
		{
			public void run()
			{
				boolean flag=true;
				int[][] start=//开始状态
				{
					{source[0],source[1]},
					{source[0],source[1]}
				};
				astarQueue.offer(start);//将开始点加入队列末尾
				int count=0;//计数器
				while(flag)
				{					
					int[][] currentEdge=astarQueue.poll();//获取表头，并将表头移除
					int[] tempTarget=currentEdge[1];//取此边的目标点
					
					//判断是否去过，若去过则直接进入下次循环
					if(visited[tempTarget[1]][tempTarget[0]]!=0)
					{
						continue;
					}
					count++;
					//表示目标点为访问过
					visited[tempTarget[1]][tempTarget[0]]=visited[currentEdge[0][1]][currentEdge[0][0]]+1;				
					//将临时目标点加入搜索过程中
					searchProcess.add(currentEdge);
					//记录此临时节点的父节点
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
					//重绘画布
					mSurfaceView.repaint(holder);
					
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					
					//判断是否为目标点
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1])
					{
						break;
					}
					
					//将所有可能的边加入队列
					int currCol=tempTarget[0];
					int currRow=tempTarget[1];
					
					for(int[] rc:sequence)
					{
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}
						if(currRow+i>=0&&currRow+i<19&&currCol+j>=0&&currCol+j<19&&
						map[currRow+i][currCol+j]!=1)
						{
							int[][] tempEdge=
							{
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							astarQueue.offer(tempEdge);//加入队列末尾
						}						
					}
				}
				pathFlag=true;	
				mSurfaceView.repaint(holder);
				tempCount=count;//广度优先A*使用步数
				mSurfaceView.mActivity.hd.sendEmptyMessage(BFSASTAR_COUNT);//发送消息更改使用步数数量
				mSurfaceView.mActivity.button.setClickable(true);	//设置button为可点				
			}
		}.start();				
	}
	
	
	public void Dijkstra()//Dijkstra
	{
		new Thread()
		{
			public void run()
			{
				int count=0;//步骤计数器
				boolean flag=true;//搜索循环设置
				//开始点
				int[] start={source[0],source[1]};//col,row	
				visited[source[1]][source[0]]=1;
				//计算此点所有可以到达点的路径及长度
				for(int[] rowcol:sequence)
				{					
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>18||tcol<0||tcol>18)continue;
					if(map[trow][tcol]!=0)continue;
					
					//记录路径长度
					length[trow][tcol]=1;
					
					//计算路径					
					String key=tcol+":"+trow;
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					
					//将去过的点记录		
					searchProcess.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					count++;			
				}	
				mSurfaceView.repaint(holder);//重绘
				outer:while(flag)
				{					
					//找到当前扩展点K 要求扩展点K为从开始点到此点目前路径最短，且此点未考察过
					int[] k=new int[2];
					int minLen=9999;
					for(int i=0;i<visited.length;i++)
					{
						for(int j=0;j<visited[0].length;j++)
						{
							if(visited[i][j]==0)
							{
								if(minLen>length[i][j])
								{
									minLen=length[i][j];
									k[0]=j;//col
									k[1]=i;//row
								}
							}
						}
					}
					
					//设置去过的点
					visited[k[1]][k[0]]=1;					
					
					//	重绘				
					mSurfaceView.repaint(holder);
					
					//取出开始点到K的路径长度
					int dk=length[k[1]][k[0]];
					//取出开始点到K的路径
					ArrayList<int[][]> al=hmPath.get(k[0]+":"+k[1]);
					
					//循环计算所有K点能直接到的点到开始点的路径长度
					for(int[] rowcol:sequence)
					{
						//计算出新的要计算的点的坐标
						int trow=k[1]+rowcol[1];
						int tcol=k[0]+rowcol[0];
						
						//若要计算的点超出地图边界或地图上此位置为障碍物则舍弃考察此点
						if(trow<0||trow>18||tcol<0||tcol>18)continue;
						if(map[trow][tcol]!=0)continue;
						
						//取出开始点到此点的路径长度
						int dj=length[trow][tcol];
						//计算经K点到此点的路径长度				
						int dkPluskj=dk+1;
						
						//若经K点到此点的路径长度比原来的小则修改到此点的路径
						if(dj>dkPluskj)
						{
							String key=tcol+":"+trow;
							//克隆开始点到K的路径
							ArrayList<int[][]> tempal=(ArrayList<int[][]>)al.clone();
							//将路径中加上一步从K到此点
							tempal.add(new int[][]{{k[0],k[1]},{tcol,trow}});
							//将此路径设置为从开始点到此点的路径
							hmPath.put(key,tempal);
							//修改到从开始点到此点的路径长度						
							length[trow][tcol]=dkPluskj;
							
							//若此点从未计算过路径长度则将此点加入考察过程记录
							if(dj==9999)
							{
								//将去过的点记录		
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});
								count++;
							}
						}
						
						//看是否找到目的点
						if(tcol==target[0]&&trow==target[1])
						{
							pathFlag=true;
							tempCount=count;//Dijkstra使用步数
							mSurfaceView.mActivity.hd.sendEmptyMessage(DIJKSTRA_COUNT);	//发送消息更改使用步数
							mSurfaceView.mActivity.button.setClickable(true);
							mSurfaceView.repaint(holder);
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();	
		
	}
	public void DijkstraAStar()//Dijkstra A*算法
	{
		new Thread()
		{
			public void run()
			{
				int count=0;//步数计数器
				boolean flag=true;//搜索循环控制
				//开始点
				int[] start={source[0],source[1]};//col,row	
				visited[source[1]][source[0]]=1;
				//计算此点所有可以到达点的路径
				for(int[] rowcol:sequence)
				{					
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>18||tcol<0||tcol>18)continue;
					if(map[trow][tcol]!=0)continue;
					
					//记录路径长度
					length[trow][tcol]=1;
					
					//计算路径				
					String key=tcol+":"+trow;
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					
					//将去过的点记录			
					searchProcess.add(new int[][]{{start[0],start[1]},{tcol,trow}});					
					count++;			
				}				
				mSurfaceView.repaint(holder);
				outer:while(flag)
				{					
					int[] k=new int[2];
					int minLen=9999;
					boolean iniFlag=true;
					for(int i=0;i<visited.length;i++)
					{
						for(int j=0;j<visited[0].length;j++)
						{
							if(visited[i][j]==0)
							{
								//与普通Dijkstra算法的区别部分
								if(length[i][j]!=9999)
								{
									if(iniFlag)
									{//第一个找到的可能点
										minLen=length[i][j]+
										(int)Math.sqrt((j-target[0])*(j-target[0])+(i-target[1])*(i-target[1]));
										k[0]=j;//col
										k[1]=i;//row
										iniFlag=!iniFlag;
									}
									else
									{
										int tempLen=length[i][j]+
										(int)Math.sqrt((j-target[0])*(j-target[0])+(i-target[1])*(i-target[1]));
										if(minLen>tempLen)
										{
											minLen=tempLen;
											k[0]=j;//col
											k[1]=i;//row
										}
									}
								}
								//与普通Dijkstra算法区别部分
							}
						}
					}
					//设置去过的点
					visited[k[1]][k[0]]=1;					
					
					//重绘					
					mSurfaceView.repaint(holder);
					
					int dk=length[k[1]][k[0]];
					ArrayList<int[][]> al=hmPath.get(k[0]+":"+k[1]);
					//循环计算所有K点能直接到的点到开始点的路径长度
					for(int[] rowcol:sequence)
					{
						//计算出新的要计算的点的坐标
						int trow=k[1]+rowcol[1];
						int tcol=k[0]+rowcol[0];
						//若要计算的点超出地图边界或地图上此位置为障碍物则舍弃考察此点
						if(trow<0||trow>18||tcol<0||tcol>18)continue;
						if(map[trow][tcol]!=0)continue;
						//取出开始点到此点的路径长度
						int dj=length[trow][tcol];	
						//计算经K点到此点的路径长度
						int dkPluskj=dk+1;
						//若经K点到此点的路径长度比原来的小则修改到此点的路径
						if(dj>dkPluskj)
						{
							String key=tcol+":"+trow;
							ArrayList<int[][]> tempal=(ArrayList<int[][]>)al.clone();
							tempal.add(new int[][]{{k[0],k[1]},{tcol,trow}});
							hmPath.put(key,tempal);							
							length[trow][tcol]=dkPluskj;
							
							if(dj==9999)
							{
								//将去过的点记录		
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});								
								count++;
							}
						}
						
						//看是否找到目的点
						if(tcol==target[0]&&trow==target[1])
						{
							Log.d("target[0]="+target[0], "target[1]="+target[1]);
							pathFlag=true;	
							tempCount=count;//DijkstraA*使用步数
							mSurfaceView.mActivity.hd.sendEmptyMessage(DIJKSTRASTAR_COUNT);	//更改使用步数数量
							mSurfaceView.mActivity.button.setClickable(true);	
							mSurfaceView.repaint(holder);
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();					
	}
	
}
