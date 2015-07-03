package com.supermario.dict;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
public class Dictionary extends Activity implements OnClickListener, TextWatcher
{
	//定义数据库的存放路径
	private final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/dictionary";
	//用户输入文本框
	private AutoCompleteTextView word;
	//定义数据库的名字
	private final String DATABASE_FILENAME = "dictionary.db";
	private SQLiteDatabase database;
	//搜索按钮
	private Button searchWord;
	//用户显示查询结果
	private TextView showResult;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//打开数据库
		database = openDatabase();
		searchWord = (Button) findViewById(R.id.searchWord);
		word = (AutoCompleteTextView) findViewById(R.id.word);
		//绑定监听器
		searchWord.setOnClickListener(this);
		//绑定文字改变监听器
		word.addTextChangedListener(this);
		showResult=(TextView)findViewById(R.id.result);
	}
	//自定义Adapter类
	public class DictionaryAdapter extends CursorAdapter
	{
		private LayoutInflater layoutInflater;
		@Override
		public CharSequence convertToString(Cursor cursor)
		{
			return cursor == null ? "" : cursor.getString(cursor
					.getColumnIndex("_id"));
		}
		//将单词信息显示到列表中
		private void setView(View view, Cursor cursor)
		{
			TextView tvWordItem = (TextView) view;
			tvWordItem.setText(cursor.getString(cursor.getColumnIndex("_id")));
		}
		//绑定选项到列表中
		@Override
		public void bindView(View view, Context context, Cursor cursor)
		{
			setView(view, cursor);
		}
		//生成新的选项
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent)
		{
			View view = layoutInflater.inflate(R.layout.word_list_item, null);
			setView(view, cursor);
			return view;
		}
		public DictionaryAdapter(Context context, Cursor c, boolean autoRequery)
		{
			super(context, c, autoRequery);
			layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
	}
	public void afterTextChanged(Editable s)
	{	
        //  必须将english字段的别名设为_id 
		Cursor cursor = database.rawQuery(
				"select english as _id from t_words where english like ?",
				new String[]
				{ s.toString() + "%" });
		//新建新的Adapter
		DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(this,
				cursor, true);
		//绑定适配器
		word.setAdapter(dictionaryAdapter);

	}
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
		// TODO Auto-generated method stub

	}
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// TODO Auto-generated method stub

	}
	public void onClick(View view)
	{
		//查询指定的单词
		String sql = "select chinese from t_words where english=?";		
		Cursor cursor = database.rawQuery(sql, new String[]
		{word.getText().toString()});
			String result = "未找到该单词.";
			//  如果查找单词，显示其中文的意思
			if (cursor.getCount() > 0)
			{
				//  必须使用moveToFirst方法将记录指针移动到第1条记录的位置
				cursor.moveToFirst();
				result = cursor.getString(cursor.getColumnIndex("chinese")).replace("&amp;", "&");
			}
			//将结果显示到TextView中
			showResult.setText(word.getText()+"\n"+result.toString());
	}
	private SQLiteDatabase openDatabase()
	{
		try
		{
			// 获得dictionary.db文件的绝对路径
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/dictionary目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/dictionary目录中不存在
			// dictionary.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/dictionary）
			if (!(new File(databaseFilename)).exists())
			{
				// 获得封装dictionary.db文件的InputStream对象
				InputStream is = getResources().openRawResource(
						R.raw.dictionary);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制dictionary.db文件
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}
				//关闭文件流
				fos.close();
				is.close();
			}
			// 打开/sdcard/dictionary目录中的dictionary.db文件
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
					databaseFilename, null);
			return database;
		}
		catch (Exception e)
		{
		}
		//如果打开出错，则返回null
		return null;
	}
}