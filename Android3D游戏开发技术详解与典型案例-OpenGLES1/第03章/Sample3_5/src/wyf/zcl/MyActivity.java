package wyf.zcl;
import java.io.ByteArrayOutputStream;		//引入相关包
import java.io.InputStream;					//引入相关包
import android.app.Activity;				//引入相关包
import android.os.Bundle;					//引入相关包
import android.view.View;					//引入相关包
import android.widget.Button;				//引入相关包
import android.widget.EditText;				//引入相关包
import android.widget.Toast;				//引入相关包
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private Button but;//打开按钮
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        but=(Button)findViewById(R.id.Button01);//打开按钮实例化
        but.setOnClickListener(new View.OnClickListener() {//打开按钮添加监听器
			@Override
			public void onClick(View v) {
				String contentResult=loadFromAssert("AndroidSummary.txt");
				EditText etContent=(EditText)findViewById(R.id.EditText01);	
				etContent.setText(contentResult);
			}
		});
    }
    public String loadFromAssert(String fileName){
    	String content=null;//结果字符串
    	try{
    		InputStream is=this.getResources().getAssets().open(fileName);//打开文件
    		int ch=0;
    		 ByteArrayOutputStream baos = new ByteArrayOutputStream();//实现了一个输出流
    		 while((ch=is.read())!=-1){
 		      	baos.write(ch);					// 将指定的字节写入此 byte 数组输出流。									
 		    }
    		 byte[] buff=baos.toByteArray();	//以 byte 数组的形式返回此输出流的当前内容
    		 baos.close();						//关闭流
    		 is.close();						//关闭流
    		 content=new String(buff,"UTF-8"); 	//设置字符串编码
    	}catch(Exception e){
    		Toast.makeText(this, "对不起，没有找到指定文件！", Toast.LENGTH_SHORT).show();
    	} 
    	return content;
    }
}