package com.supermario.ansynctest;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class AnsyncTestActivity extends Activity {
    /** Called when the activity is first created. */    
    TextView text =null;
    Button button=null;
    String str=null;
    AnsyTry anys=null;
    double result=0;   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //初始化界面元素
        text=(TextView) findViewById(R.id.text);
        button=(Button) findViewById(R.id.button);
        //随意设置一个参数
        str="flag";
        //设置监听器
        button.setOnClickListener(new OnClickListener() {           
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                anys=new AnsyTry(text);
                //执行线程
                anys.execute(str);                
            }
        });
    }       
    class AnsyTry extends AsyncTask<String, TextView, Double>{       
        TextView te=null;     
        public AnsyTry(TextView te) {
            super();
            this.te = te;
        }
        //执行后台进程
        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            double dou=0;
            if(params[0].equals("flag")){
            	//显示当前线程的名称
            	Log.e("ansync",Thread.currentThread().getName()+"");
                dou=100;
            }
            publishProgress(te);
            return dou;
        }
        //后台进行执行完毕
        @Override
        protected void onPostExecute(Double result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.e("ansync","postExecute---double---"+result);
        }
        //后台进程执行之前
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub\
        	Log.e("ansync","pretExecute------");
            super.onPreExecute();
        }
        //用于更新界面
        @Override
        protected void onProgressUpdate(TextView... values) {
            // TODO Auto-generated method stub
        	//更新TextView的内容
            values[0].setText(values[0].getText()+"A");
            super.onProgressUpdate(values);
        }        
    }
}