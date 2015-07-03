package com.supermario.filemanager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//文本编辑器
public class EditTxtActivity extends Activity implements OnClickListener{
	//显示打开的文本内容
	private EditText txtEditText;
	//显示打开的文件名
	private TextView txtTextTitle;
	//保存按钮
	private Button txtSaveButton;
	//取消按钮
	private Button txtCancleButton;
	private String txtTitle;
	private String txtData;
	private String txtPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_txt);
		//初始化界面
		initContentView();
		//获得文件路径
		txtPath = getIntent().getStringExtra("path");
		//获得文件名
		txtTitle = getIntent().getStringExtra("title");
		//获得文本数据
		txtData = getIntent().getStringExtra("data");
		try {
			txtData = new String(txtData.getBytes("ISO-8859-1"),"UTF-8");//转码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		txtTextTitle.setText(txtTitle);
		txtEditText.setText(txtData);
	}
	/**组件初始化*/
	private void initContentView(){
		txtEditText = (EditText)findViewById(R.id.EditTextDetail);
		txtTextTitle = (TextView)findViewById(R.id.TextViewTitle);
		txtSaveButton = (Button)findViewById(R.id.ButtonRefer);
		txtCancleButton = (Button)findViewById(R.id.ButtonBack);
		//设置保存按钮监听器
		txtSaveButton.setOnClickListener(this);
		//设置取消按钮监听器
		txtCancleButton.setOnClickListener(this);
	}
	/**点击事件监听*/
	public void onClick(View view) {
		if(view.getId() == txtSaveButton.getId()){
			//保存
			saveTxt();
		}else if(view.getId() == txtCancleButton.getId()){
			EditTxtActivity.this.finish();
		}
	}
	/**保存编辑后的文本信息*/
	private void saveTxt(){
		try {
			//取得编辑框内容
			String newData = txtEditText.getText().toString();
			BufferedWriter mBW = new BufferedWriter(new FileWriter(new File(txtPath)));
			//写入文件
			mBW.write(newData,0,newData.length());
			mBW.newLine();
			mBW.close();
			//提示
			Toast.makeText(EditTxtActivity.this, "成功保存!", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(EditTxtActivity.this, "存储文件时出现了异常!", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		this.finish();
	}
}
