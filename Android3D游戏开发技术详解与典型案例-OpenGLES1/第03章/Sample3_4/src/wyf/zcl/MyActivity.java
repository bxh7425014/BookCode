package wyf.zcl;
import java.io.File;				//引入相关包
import java.io.FileInputStream;		//引入相关包
import android.app.Activity;		//引入相关包
import android.os.Bundle;			//引入相关包
import android.view.View;			//引入相关包
import android.widget.Button;		//引入相关包
import android.widget.EditText;		//引入相关包
import android.widget.Toast;		//引入相关包
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	Button but;				//打开按钮引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        but=(Button)findViewById(R.id.Button01);
        								//打开按钮初始化
        but.setOnClickListener(new View.OnClickListener() {
        								//为打开按钮添加监听器
			@Override
			public void onClick(View v) {
				String contentResult=loadContentFromSDCard("AndroidSummary.txt");
										//调用读取文件方法，获得文件内容
				EditText etContent=(EditText)findViewById(R.id.EditText01);	
										//实例化EditText
				etContent.setText(contentResult);
										//设置EditText的内容
			}
		});
    }
    public String loadContentFromSDCard(String fileName){
    	//从SD卡读取内容
    	String content=null;		//sd卡 的内容字符串
    	try{
    		File f=new File("/sdcard/"+fileName);//待读取的文件
    		int length=(int)f.length();
    		byte[] buff=new byte[length];
    		FileInputStream fis=new FileInputStream(f);
    		fis.read(buff);	// 从此输入流中将 byte.length 个字节的数据读入一个 byte 数组中
    		fis.close();	//关闭此输入流并释放与此流关联的所有系统资源
    		content=new String(buff,"UTF-8");
    	}catch(Exception e){
    		Toast.makeText(this, "对不起，没有找到文件", 
    				Toast.LENGTH_SHORT).show();
    	}
		return content;
    }
}