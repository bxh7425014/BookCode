package com.guo.weibo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import com.weibo.net.AccessToken;
import com.weibo.net.Oauth2AccessTokenHeader;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboParameters;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
public class LoginActivity extends Activity{
	//数据库操作类
	DataHelper dbHelper;
	//用户信息数据适配器
	UserAdapter mUserAdapter;
	//用于保存数据库中所有用户的信息
	List<UserInfo> userList;
	//用于显示当前用户的昵称
	EditText username;
	//保存当前的上下文
	Context mContext;
	//当前选中的用户信息
	UserInfo selectedUser;
	Weibo weibo;
	//当前选中的用户id
	String user_id;
	//当前选中用户的图标
	ImageView icon;
	//底部菜单栏
	GridView mGridView;
	//底部菜单对应的标题
	String[] gridview_menu_title={"添加","退出","删除"};
	AlertDialog alertDialog;
	//底部菜单对应的图标
	int[] girdview_menu_image={R.drawable.add,R.drawable.exit,R.drawable.del};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mContext=this;
		//实例化数据库操作类
		dbHelper = new DataHelper(this); 
		icon=(ImageView)findViewById(R.id.icon);
		ImageButton select=(ImageButton)findViewById(R.id.iconSelectBtn);
		Button login=(Button)findViewById(R.id.login);
		username=(EditText)findViewById(R.id.iconSelect);
		//绑定监听器
		select.setOnClickListener(selectClickListener);
		login.setOnClickListener(loginClickListener);
		//初始化底部菜单
		initGridViewMenu();
		//初始化菜单监听器
        initMenuListener();
		//初始化用户信息
		initUser();
	}
	OnClickListener selectClickListener =new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		        AlertDialog.Builder builder;	
		        //膨胀出用户列表视图
		        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		        View layout = inflater.inflate(R.layout.user_list,null);
		        ListView myListView = (ListView) layout.findViewById(R.id.userList);
		        //为列表元素绑定按键监听器
		        myListView.setOnItemClickListener(listener);
		        Log.e("LoginActivity","userlist-size:"+userList.size());
		        //设置数据适配器
		        UserAdapter adapter = new UserAdapter(mContext, userList);
		        myListView.setAdapter(adapter);
		        builder = new AlertDialog.Builder(mContext);
		        builder.setView(layout);
		        alertDialog = builder.create();
		        //显示对话框
		        alertDialog.show();
		}		
	};
	OnClickListener loginClickListener =new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//设置认证方式
    		Utility.setAuthorization(new Oauth2AccessTokenHeader());
    		//设置access_token
    		String token=selectedUser.getToken();
    		AccessToken accessToken = new AccessToken(token, ConstParam.CONSUMER_SECRET);
    		Common.weibo.setAccessToken(accessToken);
    		weibo=Common.weibo;
    		user_id=selectedUser.getUserId();
    		//更新当前用户的昵称和图标
    		if(updateNicknameAndIcon())
    		{
    			//跳转到用户主界面
				GoHome();
				LoginActivity.this.finish();
    		}
		}		
	};
	OnItemClickListener listener=new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//将当前选中的用户的信息保存到selectedUser
			selectedUser = userList.get(arg2);
			//设置当前显示的用户昵称和图标
			setIconAndText();
			alertDialog.dismiss();
		}		
	};
	//初始化用户信息
	private void initUser(){  
		//获取账号列表   
		userList = dbHelper.GetUserList(false);  
		if(userList.isEmpty())  
		{  
        	Builder mBuilder=new AlertDialog.Builder(mContext);
        	mBuilder.setTitle("提示");
        	mBuilder.setMessage("您还未创建任何账户，是否现在创建？");
        	mBuilder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();  
			        intent.setClass(LoginActivity.this, AuthorizeActivity.class);  
			        startActivity(intent);  
			        LoginActivity.this.finish();
				}      		
        	}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					LoginActivity.this.finish();
				}      		
        	});
        	mBuilder.create().show(); 
		}  
		else  
		{  
			//记录当前选择的用户名称
			SharedPreferences preferences = getSharedPreferences("select_name", Activity.MODE_PRIVATE);  
			String str= preferences.getString("name", "");  
			if(str!="")  
			{  
				//从数据库中选择对应的数据
				selectedUser=dbHelper.GetUserByName(str);  
			}  
			//设置当前显示的用户昵称和图标
			setIconAndText();
		}  
	}
	//设置当前显示的用户昵称和图标
	public void setIconAndText()
	{
		if(selectedUser == null)  
		{  
			//如果当前选择的是空，则取数据库的第一条数据
			selectedUser=userList.get(0);  
		}  
		if(selectedUser.getUserName() == null)
		{
			//若数据库中没有相应用户信息，则设置为“未知”
			username.setText("未知");
		}else{
			username.setText(selectedUser.getUserName()); 
		}
		if(selectedUser.getUserIcon() != null)
		{
			icon.setImageDrawable(selectedUser.getUserIcon());  
		}else{
			//若数据库中没有相应用户的图标则设置用户图标为默认图标
			icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_icon));
		}	
	}
    /**为GridView配饰菜单资源*/
    private void initGridViewMenu(){
    	 mGridView = (GridView)findViewById(R.id.gridview_toolbar);
    	 //设置选中时候的背景图片
    	 mGridView.setSelector(R.drawable.menu_item_selected);
         //设置背景图片
    	 mGridView.setBackgroundResource(R.drawable.menu_background);
    	 //设置选中后背景图片
    	 mGridView.setSelector(R.drawable.menu_item_selected);
         //设置列数
    	 mGridView.setNumColumns(3);
         //设置剧中对齐
    	 mGridView.setGravity(Gravity.CENTER);
         //设置水平，垂直间距为10
    	 mGridView.setVerticalSpacing(10);
    	 mGridView.setHorizontalSpacing(10);
         //设置适配器
    	 mGridView.setAdapter(getMenuAdapter(gridview_menu_title,girdview_menu_image));
    }
    /**菜单适配器*/
    private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
    	//数组列表用于存放映射表
		ArrayList<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> mMap = new HashMap<String, Object>();
			//将“image”映射成图片资源
			mMap.put("image", imageResourceArray[i]);
			//将“title”映射成标题
			mMap.put("title", menuNameArray[i]);		
			mData.add(mMap);
		}
		//新建简单适配器，设置适配器的布局文件，映射关系
		SimpleAdapter mAdapter = new SimpleAdapter(this, mData,R.layout.login_item_menu, new String[] { "image", "title" },new int[] { R.id.item_image, R.id.item_text });
		return mAdapter;
	}
    /**菜单项的监听*/
    protected void initMenuListener(){
    	mGridView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				//添加
				case 0:
					Intent intent = new Intent();  
					intent.setClass(LoginActivity.this, AuthorizeActivity.class);  
					startActivity(intent);
					LoginActivity.this.finish();
					break;
				//退出
				case 1:
					LoginActivity.this.finish();
					break;
				//删除
				case 2:
					delUser();
					break;
				}
			}  		
    	});
    }
    //删除当前用户
    public void delUser()
    {
    	if(selectedUser.getUserId() != null)
    	{
    		dbHelper.DelUserInfo(selectedUser.getUserId());
    		//更新当前界面
    		initUser();
    	}
    }
    //在该Activity摧毁是关闭数据库
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	dbHelper.Close();
    }
    //在该Activity停止时保存当前选择的用户信息到文件中
    @Override  
    protected void onStop() {  
	    //获得SharedPreferences对象  
	    SharedPreferences MyPreferences = getSharedPreferences("select_name", Activity.MODE_PRIVATE);  
	    //获得SharedPreferences.Editor对象  
	    SharedPreferences.Editor editor = MyPreferences.edit();  
	    //保存组件中的值  
	    editor.putString("name", username.getText().toString());  
	    editor.commit();  
	    super.onStop();  
    } 
    //进入用户首页  
    private void GoHome(){  
	    if(userList!=null)  
	    {  
		    if(selectedUser !=null)  
		    {  
		    	//获取当前选择的用户并且保存  
		    	Common.login_user=selectedUser;
			    //进入用户首页  
			    Intent intent = new Intent();  
			    intent.setClass(LoginActivity.this, HomeActivity.class);  
			    startActivity(intent);  
		    }  
	    } 
    }
    //获得昵称和用户图标
	  public boolean updateNicknameAndIcon()
	  {
		try {
			//获得用户的详细信息
			JSONObject json=getUserDetail(mContext,weibo,user_id);
			Log.e("guojs-json","ddd"+json.toString());
			//如果返回的数据含有“error”，并且错误代码为“21315”，说明授权已过期
			if(json.has("error") && json.getString("error_code") == "21315")
    		{
    			Toast.makeText(mContext, "授权过期，请重新授权！", Toast.LENGTH_LONG).show();
    			return false;
    			//其他错误类型
    		}else if(json.has("error")){
    			Toast.makeText(mContext, "出错了，错误代码为："+json.getString("error_code"), Toast.LENGTH_LONG).show();
    			return false;
    			//如果没有包含错误信息，说明信息正确
    		}else{
    			//获得用户昵称
				String nickName = json.getString("screen_name");
				Log.e("guojs","nickname:"+nickName);
				//获得用户图标地址，并下载图标
				Bitmap bm=Common.getBm(new URL(json.getString("profile_image_url")));
				//更新数据库的昵称和图标信息
				dbHelper.UpdateUserInfo(nickName,bm,user_id);
				dbHelper.Close();
				return true;
    		}
		} catch (Exception e) {
			Log.e("guojs",e.getMessage());
			return false;
		}
	}
	  //调用api，获得用户信息
	  public JSONObject getUserDetail(Context mContext,Weibo weibo,String uid)
	  {
	      String url=Weibo.SERVER+"users/show.json";
	      WeiboParameters bundle=new WeiboParameters();
	      //添加source和uid参数
	      bundle.add("source", ConstParam.CONSUMER_KEY);
	      bundle.add("uid",uid);
	      String rlt="";
			try {
				rlt = weibo.request(mContext, url, bundle, "GET", weibo.getAccessToken());
				return new JSONObject(rlt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;				
			}
	  }
}