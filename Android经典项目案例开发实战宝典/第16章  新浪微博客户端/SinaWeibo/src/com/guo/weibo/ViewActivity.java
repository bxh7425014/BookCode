package com.guo.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import com.guo.weibo.HomeActivity.AsyncImageLoader;
import com.guo.weibo.HomeActivity.ImageCallback;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboParameters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends Activity{
	//保存当前上下文
	Context mContext;
	Weibo weibo;
	//用于显示用户昵称
	TextView username; 
	//用于显示微博文字内容
	TextView content;
	//用于显示用户头像
	ImageView icon; 
	//转发按钮
	Button btn_zf;
	//评论按钮
	Button btn_pl;
	//微博的id
	String key;
	//载入动画
	LinearLayout loading;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_detail);
		weibo=Common.weibo;
		mContext=this;
		//初始化界面
		username=(TextView)findViewById(R.id.username); 
		content=(TextView)findViewById(R.id.text); 
		icon=(ImageView)findViewById(R.id.usericon);
		//转发按钮
		btn_zf=(Button)findViewById(R.id.btn_gz);
		//评论按钮
		btn_pl=(Button)findViewById(R.id.btn_pl); 
		//刷新按钮
		Button btn_refresh=(Button)findViewById(R.id.btn_refresh);
		//收藏按钮
		Button btn_sc=(Button)findViewById(R.id.btn_sc);
		//顶部返回主页面的ImageView
		ImageButton btn_home=(ImageButton)findViewById(R.id.homeBtn);
		loading=(LinearLayout)findViewById(R.id.loadingLayout);
		//为评论按钮设置按键监听器
		btn_zf.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//膨胀出EditText视图
				LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
				final View view=inflater.inflate(R.layout.comment, null);
				//取得EditText
				final EditText retrans=(EditText)view.findViewById(R.id.comment);
				new AlertDialog.Builder(mContext)
				.setTitle("转发")
				.setView(view)
				.setPositiveButton("提交", new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub	
						String str=retrans.getText().toString();
						//转发
						JSONObject data=repost(mContext,weibo,key,str);
						if(!data.has("error"))
							Toast.makeText(ViewActivity.this, "转发成功！", Toast.LENGTH_SHORT).show();
					}})
				.setNegativeButton("取消",null).show();
			}			
		});
		//为评论按钮设置按键监听器
		btn_pl.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
				final View view=inflater.inflate(R.layout.comment, null);
				final EditText comment=(EditText)view.findViewById(R.id.comment);
				new AlertDialog.Builder(mContext)
				.setTitle("评论")
				.setView(view)
				.setPositiveButton("提交", new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub	
						String str=comment.getText().toString();
						if(str != null)
						{
							//评论
							JSONObject data=comment(mContext,weibo,key,str);
							if(!data.has("error"))
								Toast.makeText(ViewActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
						}
					}})
				.setNegativeButton("取消", null).show();				
			}			
		});
		//为刷新按钮设置按键监听器
		btn_refresh.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				view(key);
			}			
		});
		//为搜藏按钮设置按键监听器
		btn_sc.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//收藏
				JSONObject data=collect(mContext,weibo,key);
				if(!data.has("error"))
					Toast.makeText(ViewActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
			}			
		});
		//为返回主页面按钮设置按键监听器
		btn_home.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(ViewActivity.this,HomeActivity.class);
				startActivity(it);
				ViewActivity.this.finish();
			}			
		});
		//获取上一个页面传递过来的key，key为某一条微博的id  
		Intent i=this.getIntent();
		Bundle b=null;
		if(!i.equals(null)){
			b=i.getExtras();  
		}
		if(b!=null && b.containsKey("key")){
			key = b.getString("key");
			//初始化页面
			view(key);
		}
	}
	//初始化页面
	private void view(String id){  
		//根据微博id获得微博详细信息
		JSONObject data=getOneWeibo(mContext,weibo,id);
		if(data!=null){  
			JSONObject u;
			String userName="未知";
			String userIcon=null;
			String text=null;
			try {
				//获得微博对应用户信息
				u = data.getJSONObject("user");
				//取得昵称
				userName=u.getString("screen_name");  
				//取得用户头像地址
				userIcon=u.getString("profile_image_url");  
				Log.e("userIcon", userIcon); 
				Log.e("username","username"+userName);
				//取得微博内容
				text=data.getString("text");  
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//设置用户昵称
			username.setText(userName);
			//设置微博内容
			content.setText(text);  			  
			 //新建异步图像加载类
			AsyncImageLoader asyncImageLoader = new AsyncImageLoader();  
			//加载用户头像
			Drawable cachedImage = asyncImageLoader.loadDrawable(userIcon,icon, new ImageCallback(){  
				@Override  
				public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {  			  
					imageView.setImageDrawable(imageDrawable);  
				}  
			});  
			if (cachedImage == null)   
			{  
				//如果没取到用户头像，则设置成默认的头像
				icon.setImageResource(R.drawable.default_icon);  
			}  
			else  
			{  
				//设置用户头像
				icon.setImageDrawable(cachedImage);  
			}  
			//如果微博带图片则加载图片
			if(data.has("bmiddle_pic")){  
				String picurl=null;
				String picurl2=null;
				try {
					picurl = data.getString("bmiddle_pic");
					picurl2 = data.getString("original_pic");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				//同样适用异步图像加载类进行加载
				ImageView pic=(ImageView)findViewById(R.id.pic);
				Drawable cachedImage2 = asyncImageLoader.loadDrawable(picurl,pic, new ImageCallback(){  
					@Override  
					public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {  
						showImg(imageView,imageDrawable);  
					}  
				});  
				if (cachedImage2 == null)   
				{  
					//如果没下载到对应图像，则用默认图像代替
					pic.setImageResource(R.drawable.default_icon);  
				}  
				else  
				{
					//设置图像
					showImg(pic,cachedImage2);  
				}
			}  			 
			String rt=null;
			String comments=null;
			try {
				//取得转发数量
				rt = data.getString("reposts_count");
				//取得评论数量
				comments=data.getString("comments_count");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			btn_zf.setText(" 转发("+rt+")");  
			btn_pl.setText(" 评论("+comments+")");  
		}  
		loading.setVisibility(View.GONE);
	}  
	//调用API取得对应的微博详细信息
	public JSONObject getOneWeibo(Context mContext,Weibo weibo,String id)
	{
	      String url=Weibo.SERVER+"statuses/show.json";
		  WeiboParameters bundle=new WeiboParameters();
		  bundle.add("source", ConstParam.CONSUMER_KEY);
		  //传入微博id
		  bundle.add("id", id);
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
	//调用API进行转发
	public JSONObject repost(Context mContext,Weibo weibo,String id,String str)
	{
		//转发的API
	      String url=Weibo.SERVER+"statuses/repost.json";
		  WeiboParameters bundle=new WeiboParameters();
		  bundle.add("source", ConstParam.CONSUMER_KEY);
		  bundle.add("id", id);
		  //添加转发附言
		  bundle.add("status",str);
		  String rlt="";
			try {
				rlt = weibo.request(mContext, url, bundle, "POST", weibo.getAccessToken());
				return new JSONObject(rlt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;				
			}
	}
	//调用API进行收藏微博
	public JSONObject collect(Context mContext,Weibo weibo,String id)
	{
		//收藏的API
	      String url=Weibo.SERVER+"favorites/create.json";
		  WeiboParameters bundle=new WeiboParameters();
		  bundle.add("source", ConstParam.CONSUMER_KEY);
		  //传入微博的id
		  bundle.add("id", id);
		  String rlt="";
			try {
				rlt = weibo.request(mContext, url, bundle, "POST", weibo.getAccessToken());
				return new JSONObject(rlt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;				
			}
	}
	//调用API进行评论微博
	public JSONObject comment(Context mContext,Weibo weibo,String id,String comment)
	{
		  //评论的API
	      String url=Weibo.SERVER+"comments/create.json";
		  WeiboParameters bundle=new WeiboParameters();
		  bundle.add("source", ConstParam.CONSUMER_KEY);
		  //传入微博的id
		  bundle.add("id", id);
		  //传入评论的内容
		  bundle.add("comment", comment);
		  String rlt="";
			try {
				rlt = weibo.request(mContext, url, bundle, "POST", weibo.getAccessToken());
				return new JSONObject(rlt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;				
			}
	}
	//显示微博的图像
	private void showImg(ImageView view,Drawable img){  
		int w=img.getIntrinsicWidth();  
		int h=img.getIntrinsicHeight();  
		Log.e("w", w+"/"+h);  
		//如果宽度超过300，则进行缩放处理
		if(w>300)  
		{  
			int hh=300*h/w;  
			Log.e("hh", hh+"");  
			LayoutParams para=view.getLayoutParams();  
			para.width=300;  
			para.height=hh;  
			view.setLayoutParams(para);  
		}  
		//显示图像
		view.setImageDrawable(img);  
	}
}