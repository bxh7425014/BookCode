package com.guo.startAFR;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class AnotherActivity extends Activity{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);
        Button btnClose=(Button)findViewById(R.id.close);
        btnClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", "Hello,I'm back!");
                //设置返回数据
                AnotherActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                AnotherActivity.this.finish();
            }
        });        
    }
}
