package com.supermario.mycontentprovider;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
public class MyContentActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //插入两条记录
        insertRecord("Test");
        insertRecord("Guo");
        //显示记录
        displayRecords();
    }
    //插入记录
    private void insertRecord(String userName) {
        ContentValues values = new ContentValues();
        values.put(MyUser.User.USER_NAME, userName);
        getContentResolver().insert(MyUser.User.CONTENT_URI, values);
    }
    private void displayRecords() {
    	//构建一个字符串数组用于存放用户的记录
        String columns[] = new String[] { MyUser.User._ID,
                MyUser.User.USER_NAME };
        //设定ContentProvider的Uri
        Uri myUri = MyUser.User.CONTENT_URI;
        Cursor cur = managedQuery(myUri, columns, null, null, null);
        if (cur.moveToFirst()) {
            String id = null;
            String userName = null;
            do {
                id = cur.getString(cur.getColumnIndex(MyUser.User._ID));
                userName = cur.getString(cur
                        .getColumnIndex(MyUser.User.USER_NAME));
                //显示数据表中的数据
                Toast.makeText(this, id + " " + userName, Toast.LENGTH_LONG)
                        .show();
            } while (cur.moveToNext());
        }
    }
}