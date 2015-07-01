package wyf.sj;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
public class MyActivity extends Activity {
	int[] searchMsgId=//各种算法
			{
			R.string.depthFirstSearch,R.string.breadthFirstSearch,
			R.string.breadthFirstSearchA,R.string.Dijkstra,
			R.string.DijkstraA
			};
	int[] targetId=//目标点
			{
					R.string.tA,R.string.tB,R.string.tC,R.string.tD,R.string.tE
			};
	MySurfaceView mySurfaceView;//声明引用
	Button button;//开始按钮
	TextView textViewSybz;//使用步数
	TextView textViewLjcd;//路径长度
	Handler hd;//消息处理器
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mySurfaceView = new MySurfaceView(this);
        //this.setContentView(mySurfaceView);
        LinearLayout ly=(LinearLayout)findViewById(R.id.LinearLayout02);
        ly.addView(mySurfaceView);
        
        button=(Button)findViewById(R.id.Button01);//通过ID获得Button
        textViewSybz=(TextView)findViewById(R.id.TextView01);//通过ID获得使用步骤TextView
        textViewLjcd=(TextView)findViewById(R.id.TextView02);//通过ID获得使用步骤TextView
        Spinner spinnerSearch=(Spinner)findViewById(R.id.Spinner01);//获得搜索方法下拉列表
        
        //为spinnerSearch准备内容适配器
        BaseAdapter ba=new BaseAdapter()
        {
			@Override
			public int getCount() {
				return 5;//总共五个选项
			}

			@Override
			public Object getItem(int arg0) { return null; }

			@Override
			public long getItemId(int arg0) { return 0; }

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				//初始化LinearLayout
				LinearLayout ll=new LinearLayout(MyActivity.this);
				ll.setOrientation(LinearLayout.HORIZONTAL);		//设置朝向	

				
				//初始化TextView
				TextView tv=new TextView(MyActivity.this);
				tv.setText(" "+getResources().getText(searchMsgId[arg0]));//设置内容
				tv.setTextSize(20);//设置字体大小
				tv.setTextColor(Color.BLACK);//设置字体黑色
				ll.addView(tv);//添加到LinearLayout中
				
				return ll;
			}
		};
		spinnerSearch.setAdapter(ba);//为Spinner设置内容适配器
		
		//设置选项选中的监听器
		spinnerSearch.setOnItemSelectedListener(
           new OnItemSelectedListener()
           {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {//重写选项被选中事件的处理方法
				mySurfaceView.game.algorithmId=arg2;
				Log.d(mySurfaceView.game.algorithmId+"", "mySurfaceView.game.algorithmId");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }        	   
           }
        );
        
        Spinner spinnerTarget=(Spinner)findViewById(R.id.Spinner02);//获得搜索方法下拉列表
        //为spinnerTarget准备内容适配器
        BaseAdapter baTarget=new BaseAdapter()
        {
			@Override
			public int getCount() {
				return 5;//总共五个选项
			}

			@Override
			public Object getItem(int arg0) { return null; }

			@Override
			public long getItemId(int arg0) { return 0; }

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				//初始化LinearLayout
				LinearLayout ll=new LinearLayout(MyActivity.this);
				ll.setOrientation(LinearLayout.HORIZONTAL);		//设置朝向	

				
				//初始化TextView
				TextView tv=new TextView(MyActivity.this);
				tv.setText(" "+getResources().getText(targetId[arg0]));//设置内容
				tv.setTextSize(20);//设置字体大小
				tv.setTextColor(Color.BLACK);//设置字体黑色
				ll.addView(tv);//添加到LinearLayout中
				
				return ll;
			}
		};
		spinnerTarget.setAdapter(baTarget);//为Spinner设置内容适配器
		//设置选项选中的监听器
		spinnerTarget.setOnItemSelectedListener(
           new OnItemSelectedListener()
           {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {//重写选项被选中事件的处理方法
				mySurfaceView.game.target=MapList.targetA[arg2];
				mySurfaceView.game.clearState();//清空状态
				mySurfaceView.repaint(mySurfaceView.getHolder());//重绘 
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }        	   
           }
        );
		button.setOnClickListener
		(
				new OnClickListener()
				{

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						mySurfaceView.game.runAlgorithm();//调用方法
						button.setClickable(false);//设置为不可点击
					}
				
					
				}
		);
		hd=new Handler()
		{
			 @Override
	        	public void handleMessage(Message msg)
				 {
					 super.handleMessage(msg);
					 switch(msg.what)
					 {
					 	case 1://更改使用步数
					 	case 2:
					 	case 3:
					 	case 4:
					 	case 5:textViewSybz.setText("使用步数："+mySurfaceView.game.tempCount);break;
					 	case 6:textViewLjcd.setText("路径长度："+mySurfaceView.LjcdCount);break;//更改路径长度
					 }
				 }
		 };
		 
		
    }
}