package com.guo.weibo;

import java.net.URL;
import org.json.JSONObject;
import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
//用户授权认证
public class AuthorizeActivity extends Activity{
	DataHelper dbHelper;
	//保存当前授权成功的用户信息
	UserInfo userInfo;
	//保存当前用户的Access_Token
	String token;
	//用户保存当前授权成功的用户id
	String user_id;
	//实例化weibo类
	Weibo weibo;
	//保存当前的上下文
	Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mContext=this;
		dbHelper=new DataHelper(this);
		//实例化weibo
		weibo = Weibo.getInstance();
		userInfo=new UserInfo();
		//设置App_Key和App_Secret
		weibo.setupConsumerConfig(ConstParam.CONSUMER_KEY, ConstParam.CONSUMER_SECRET);
		// Oauth2.0
		// 隐式授权认证方式
		// 此处回调页内容应该替换为与appkey对应的应用回调页
		weibo.setRedirectUrl(ConstParam.CALLBACK_URL);
		// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
		// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
		// 应用回调页不可为空
		weibo.authorize(this,new AuthDialogListener());
	}
	  class AuthDialogListener implements WeiboDialogListener {
			@Override
			public void onComplete(Bundle values) {
				//获得Access_Token
				token = values.getString("access_token");
				//获得过期时间
				String expires_in = values.getString("expires_in");
				user_id = values.getString("uid");
				Log.e("sinaweibo","access_token : " + token + "  expires_in: "
						+ expires_in+" uid :"+user_id);
				AccessToken accessToken = new AccessToken(token, ConstParam.CONSUMER_SECRET);
				accessToken.setExpiresIn(expires_in);
				//设置Access_Token
				weibo.setAccessToken(accessToken);
				//保存用户信息
				userInfo.setToken(token);
				userInfo.setUserId(user_id);
				userInfo.setTokenSecret(ConstParam.CONSUMER_SECRET);
				if(dbHelper.HaveUserInfo(user_id))
				{
					//更新用户信息
					dbHelper.UpdateUserInfo(userInfo);
				}else{
					//添加用户信息
					dbHelper.SaveUserInfo(userInfo);
				}
				new Thread(mRunnable).start();
				//进入登陆界面
				Intent it=new Intent();
		        it.setClass(AuthorizeActivity.this, LoginActivity.class);
		        startActivity(it);
			}
			//授权异常
			@Override
			public void onWeiboException(WeiboException e) {
				// TODO Auto-generated method stub
				Toast.makeText(AuthorizeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				AuthorizeActivity.this.finish();
			}
			//授权出错
			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub
				Toast.makeText(AuthorizeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				AuthorizeActivity.this.finish();
			}
			//授权被取消
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				AuthorizeActivity.this.finish();
			}
	    }
	  //获得昵称和用户图标
	  Runnable mRunnable=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				JSONObject json=getUserDetail(mContext,weibo,user_id);
				//获得用户昵称
				String nickName = json.getString("screen_name");
				Log.e("guojs","nickname:"+nickName);
				Bitmap bm=Common.getBm(new URL(json.getString("profile_image_url")));
				//更新用户信息
				dbHelper.UpdateUserInfo(nickName,bm,user_id);
				//关闭数据库
				dbHelper.Close();
			} catch (Exception e) {
				Log.e("guojs",e.getMessage());
			}
		}  
	  };
	  //调用api，获得用户信息
	  public JSONObject getUserDetail(Context mContext,Weibo weibo,String uid)
	  {
		  //获取用户信息API
	      String url=Weibo.SERVER+"users/show.json";
	      WeiboParameters bundle=new WeiboParameters();
	      bundle.add("source", ConstParam.CONSUMER_KEY);
	      //添加用户id作为参数
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