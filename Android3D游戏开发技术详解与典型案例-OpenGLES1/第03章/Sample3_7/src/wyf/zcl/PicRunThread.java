package wyf.zcl;
//该类是控制duke图片运动的类
public class PicRunThread extends Thread{
	MySurfaceView msv;									//MySurfaceView的引用
	private float picX=0;			//图片x坐标
	private float picY=Constant.SCREENHEIGHT-Constant.PICHEIGHT;			//图片y坐标
	boolean yRunFlag=false;		//y方向上的运动标记，false时y=y+speed，true时y=y-speed
	int picAlphaNum=0;					//图片变暗效果中画笔的alpha值
	public PicRunThread(MySurfaceView msv) {
		super();
		this.msv = msv;			//将该线程类的引用指向调用其的MySurfaceView的对象
	}
	@Override
	public void run() {
		super.run();
		while(true){
			//控制duke图片的运动
			while(this.picX<Constant.SCREENWIDTH){			//当图片的左边完全超过屏幕的右边时，循环结束
				msv.setPicX(picX);
				msv.setPicY(picY);
				picX=picX+Constant.PICXSPEED;
				if(yRunFlag){//应该向上运动，自减
					picY=picY-Constant.PICYSPEED;
				}else{//应该向下运动，自加
					picY=picY+Constant.PICYSPEED;
				}
				if(picY<=0){									//到达屏幕上沿
					yRunFlag=false;
				}else if(picY>Constant.SCREENHEIGHT-Constant.PICHEIGHT){		//到达屏幕下沿
					yRunFlag=true;
				}
				try{
					Thread.sleep(Constant.PICRUNSPEED);
				}catch(Exception e){e.printStackTrace();}
			}
			//图片变暗效果演示
			msv.picAlphaFlag=true;							//开启图片变暗效果
			for(picAlphaNum=0;picAlphaNum<=255;picAlphaNum++){
				if(picAlphaNum==255){
					msv.picAlphaFlag=false;					//当图片变暗效果结束，标记重置
					picX=0;			//图片x坐标
					picY=Constant.SCREENHEIGHT-Constant.PICHEIGHT;			//图片y坐标
					System.out.println(msv.picAlphaFlag+"picX:"+picX+"picY:"+picY);
				}
				msv.setPicAlphaNum(picAlphaNum);
				try{
					Thread.sleep(Constant.PICALPHASPEED);
				}catch(Exception e){e.printStackTrace();}
			}
		}
	}
}
