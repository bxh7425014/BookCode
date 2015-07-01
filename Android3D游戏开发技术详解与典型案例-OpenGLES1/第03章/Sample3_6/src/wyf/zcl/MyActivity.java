package wyf.zcl;
import java.util.Date;								//引入相关包
import android.app.Activity;						//引入相关包
import android.content.Context;						//引入相关包
import android.content.SharedPreferences;			//引入相关包
import android.os.Bundle;							//引入相关包
import android.widget.TextView;						//引入相关包
import android.widget.Toast;						//引入相关包
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences sp=this.getSharedPreferences("sharePre", Context.MODE_PRIVATE);
        //返回一个SharedPreferences实例，第一个参数是Preferences名字，第二个参数是使用默认的操作
        String lastLogin=sp.getString(				//从SharedPreferences中读取上次访问的时间
        		"ll",								//键值
        		null								//默认值
        );
        if(lastLogin==null){
        	lastLogin="欢迎您，您是第一次访问本Preferences";
        }else{
        	lastLogin="欢迎回来，您上次于"+lastLogin+"登录";
        }
        //向SharedPreferences中写回本次访问时间
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("ll", new Date().toLocaleString());	//向editor中放入现在的时间
        editor.commit();										//提交editor
        tv=(TextView)this.findViewById(R.id.TextView01);
        tv.setText(lastLogin);
    }
}