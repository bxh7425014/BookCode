package com.guo.weibo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.weibo.net.ShareActivity;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity{
	//保存当前上下文
	Context mContext;
	//存储所有微博信息
	private List<WeiboInfo> wbList;  
	//载入动画
	LinearLayout loadingLayout;
	Weibo weibo;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		loadingLayout=(LinearLayout)findViewById(R.id.loadingLayout);
		//写微博
		ImageButton btn_write=(ImageButton)findViewById(R.id.writeBtn);
		btn_write.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					//写微博
					weibo.share2weibo(HomeActivity.this, weibo.getAccessToken().getToken(), weibo.getAccessToken()
					        .getSecret(), "abc", "");
				} catch (WeiboException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		});
		//刷新界面
		ImageButton refresh=(ImageButton)findViewById(R.id.refreshBtn);
		refresh.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadList();
			}			
		});
		mContext=this;
		//初始化界面
		loadList();
	}
	 
	//微博列表Adapater  
	public class WeiboAdapater extends BaseAdapter{  
		 //获取列表总行数
		@Override  
		public int getCount() {  
			return wbList.size();  
		}		
		//取得当前选中的微博
		@Override  
		public Object getItem(int position) {  
			return wbList.get(position);  
		}  
		//取得当前的位置
		@Override  
		public long getItemId(int position) {  
			return position;  
		}  
		 //得到每一行的视图
		@Override  
		public View getView(int position, View convertView, ViewGroup parent) {  
			//异步获取图片类
			AsyncImageLoader asyncImageLoader = new AsyncImageLoader();  
			//膨胀出微博视图
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.weibo, null);  
			WeiboHolder wh = new WeiboHolder();  
			//取得微博界面的元素
			wh.wbicon = (ImageView) convertView.findViewById(R.id.wbicon);  
			wh.wbtext = (TextView) convertView.findViewById(R.id.wbtext);  
			wh.wbtime = (TextView) convertView.findViewById(R.id.wbtime);  
			wh.wbuser = (TextView) convertView.findViewById(R.id.wbuser);  
			wh.wbimage=(ImageView) convertView.findViewById(R.id.wbimage);  
			//取得微博信息
			WeiboInfo wb = wbList.get(position);  
			if(wb!=null){ 
				//将微博的id保存到标签汇总
				convertView.setTag(wb.getId());  
				wh.wbuser.setText(wb.getUserName());  
				wh.wbtime.setText(wb.getTime());  
				wh.wbtext.setText(textHighlight(wb.getText()), TextView.BufferType.SPANNABLE);
				//如果微博内容含有图片，则显示标志图片
				if(wb.getHaveImage()){  
					wh.wbimage.setImageResource(R.drawable.images);  
				}  
				//取得用户图标
				Drawable cachedImage = asyncImageLoader.loadDrawable(wb.getUserIcon(),wh.wbicon, new ImageCallback(){		
					//调用接口显示图片
					@Override  
					public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {  
						imageView.setImageDrawable(imageDrawable);  
					}  		  
				});  
				if (cachedImage == null) {  
					wh.wbicon.setImageResource(R.drawable.default_icon);
				}else{  
					wh.wbicon.setImageDrawable(cachedImage);  
				}
			}  			  
			return convertView;  
		}  
		//用于存放微博视图元素的类
		class WeiboHolder{
			//微博是否包含图片标志
			public ImageView wbimage;
			//用户昵称
			public TextView wbuser;
			//微博发布时间
			public TextView wbtime;
			//微博内容
			public TextView wbtext;
			//用户图标
			public ImageView wbicon;		
		}		
	}
	public static class AsyncImageLoader {  
		//SoftReference是软引用，是为了更好的为了系统回收变量  
		private HashMap<String, SoftReference<Drawable>> imageCache;  
		public AsyncImageLoader() {  
			imageCache = new HashMap<String, SoftReference<Drawable>>();  
		}  
		  
		public Drawable loadDrawable(final String imageUrl,final ImageView imageView, final ImageCallback imageCallback){  
			if (imageCache.containsKey(imageUrl)) {  
				//从缓存中获取  
				SoftReference<Drawable> softReference = imageCache.get(imageUrl);  
				Drawable drawable = softReference.get();  
				if (drawable != null) {
					return drawable;  
				}  
			}  
			final Handler handler = new Handler() {  
			public void handleMessage(Message message) { 
				//调用接口显示图片
				imageCallback.imageLoaded((Drawable) message.obj, imageView,imageUrl);  
			}};  
			//建立新一个新的线程下载图片  
			new Thread() {  
				@Override  
				public void run() {  
					Drawable drawable = loadImageFromUrl(imageUrl);  
					//将图片放入缓存
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));  
					Message message = handler.obtainMessage(0, drawable);  
					//发送消息更新界面
					handler.sendMessage(message);  
				}  
			}.start();  
			return null;  
		}  		  
		//从网上下载图片
		public static Drawable loadImageFromUrl(String url){  
			URL m;  
			InputStream i = null;  
			try {  
				m = new URL(url);  
				//取得图片的数据转换成InputStream
				i = (InputStream) m.getContent();  
			} catch (MalformedURLException e1) {  
				e1.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
			//生成一个Drawable对象
			Drawable d = Drawable.createFromStream(i, "src");  
			return d;  
		}      
	}  
	//回调接口  
	public interface ImageCallback {  
		public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl);  
	}
	//高亮显示TextView的关键文字
	public SpannableStringBuilder textHighlight(String str)
	{
		//高亮显示‘#’和‘#’之间的内容
    	Pattern pattern =Pattern.compile("#[^#]+#");
    	Matcher matcher = pattern.matcher(str);
    	SpannableStringBuilder style=new SpannableStringBuilder(str);
    	//对所有匹配的字符串进行处理
    	while(matcher.find()){      
    		String temp=matcher.group();
    		Log.e("guojs","start"+str.indexOf(matcher.group()));
    		//设置关键字颜色为蓝色
    		style.setSpan(new ForegroundColorSpan(Color.BLUE),str.indexOf(temp),str.indexOf(temp)+temp.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
    	}
    	//高亮显示‘@’和‘：’之间的文字
    	pattern=Pattern.compile("@[\\w\\W]:");
    	matcher = pattern.matcher(str);
    	while(matcher.find()){      
    		String temp=matcher.group();
    		Log.e("guojs","start"+str.indexOf(matcher.group()));
    		//设置关键字的颜色为蓝色
    		style.setSpan(new ForegroundColorSpan(Color.BLUE),str.indexOf(temp),str.indexOf(temp)+temp.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
    	}
    	//高亮显示http://开头或者https://开头的文字
    	pattern=Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
    	matcher = pattern.matcher(str);
    	while(matcher.find()){      
    		String temp=matcher.group();
    		Log.e("guojs","start"+str.indexOf(matcher.group()));
    		//设置关键字颜色为蓝色
    		style.setSpan(new ForegroundColorSpan(Color.BLUE),str.indexOf(temp),str.indexOf(temp)+temp.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
    	}
    	return style;
	}
	//初始化界面显示
	private void loadList(){  
		//如果当前没有用户登录直接返回
        if(Common.login_user==null)  
        {  
              return;
        }  
        else  
        {  
        	//取得当前登陆的用户
            UserInfo user=Common.login_user;  
            //显示当前用户名称  
            TextView showName=(TextView)findViewById(R.id.showName);  
            showName.setText(user.getUserName());          
    		weibo=Common.weibo;
    		//取得当前用户关注的用户的最新微博信息
    		JSONObject json = getFriendsTimeline(mContext,weibo);
    		Log.e("guojs",json.toString());
	        JSONArray data;
			try {
				//判断access_token是否过期
				if(json.has("error") && json.getString("error_code") == "21315")
	    		{
	    			Toast.makeText(mContext, "授权过期，请重新授权！", Toast.LENGTH_LONG).show();
	    			//如果过期则引导用户重新授权
	    			Intent it=new Intent(HomeActivity.this,LoginActivity.class);
	    			startActivity(it);
	    			HomeActivity.this.finish();
	    			//判断返回值是否出错
	    		}else if(json.has("error")){
	    			Toast.makeText(mContext, "出错了，错误代码为："+json.getString("error_code"), Toast.LENGTH_LONG).show();
	    			//出错则引导用户重新的登陆
	    			Intent it=new Intent(HomeActivity.this,LoginActivity.class);
	    			startActivity(it);
	    			HomeActivity.this.finish();
	    		}else{
	    			//取得statuses信息
					data = new JSONArray(json.getString("statuses")); 
					Log.e("home-length","changdu :"+data.length());
					//遍历每个信息
			        for(int i=0;i<data.length();i++)  
			        {  
			            JSONObject d=data.getJSONObject(i);  
			            if(d!=null){  
			            	//取得每条信息中包含的用户的信息
			                JSONObject u=d.getJSONObject("user");                    
			                //微博id  
			                String id=d.getString("id");  
			                //取得用户id
			                String userId=u.getString("id");  
			                //取得用户昵称
			                String userName=u.getString("screen_name");  
			                //取得用户图标地址
			                String userIcon=u.getString("profile_image_url");  
			                Log.e("userIcon", userIcon);  
			                //取得微博发布时间
			                String time=d.getString("created_at");  
			                //取得微博内容
			                String text=d.getString("text");  
			                Boolean haveImg=false;  
			                //判断当前微博内容是否包含图片
			                if(d.has("thumbnail_pic")){  
			                	//如果有图片，则设置图片标志位
			                    haveImg=true;  
			                }                  
			                Date date=new Date(time); 
			                //转换成几分钟前、几小时前。。。
			                time=getTimeDiff(date);  
			                if(wbList == null){  
			                	//如果还没有初始化微博列表，则进行初始化
			                    wbList = new ArrayList<WeiboInfo>();  
			                }  
			                //实例化一个WeiboInfo类，用于保存微博的信息
			                WeiboInfo w=new WeiboInfo();  
			                w.setId(id);  
			                w.setUserId(userId);  
			                w.setUserName(userName);  
			                w.setTime(time);  
			                w.setText(text);  
			                  
			                w.setHaveImage(haveImg);  
			                w.setUserIcon(userIcon);  
			                //将信息添加到微博数组中
			                wbList.add(w);  
			            }  
			        }
	    		}
	        } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	        }   
	        //判断微博列表是否为空
            if(wbList!=null)  
            {  
            	//创建微博信息列表的适配器
                WeiboAdapater adapater = new WeiboAdapater();  
                ListView Msglist=(ListView)findViewById(R.id.Msglist);  
                //设置列表的按键监听器
                Msglist.setOnItemClickListener(new OnItemClickListener(){  
                    @Override  
                    public void onItemClick(AdapterView<?> arg0, View view,int arg2, long arg3) {  
                    	//获得视图绑定的tag信息
                        Object obj=view.getTag();  
                        if(obj!=null){  
                            String id=obj.toString();  
                            //启动查看微博详细信息的视图
                            Intent intent = new Intent(HomeActivity.this,ViewActivity.class);  
                            //将微博的id绑定到b中发送给下一个Activity
                            Bundle b=new Bundle();  
                            b.putString("key", id);  
                            intent.putExtras(b);  
                            startActivity(intent); 
                            HomeActivity.this.finish();
                        }  
                    }  
                      
                });  
                Msglist.setAdapter(adapater);  
            }  
        }  
        //隐藏载入进度条
        loadingLayout.setVisibility(View.GONE);  
    }  
	//获得用户关注的用户的最新微博信息
	public JSONObject getFriendsTimeline(Context mContext,Weibo weibo)
	{
	      String url=Weibo.SERVER+"statuses/friends_timeline.json";
		  WeiboParameters bundle=new WeiboParameters();
		  //传入source
		  bundle.add("source", ConstParam.CONSUMER_KEY);
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
	//取得传入时间与当前时间的时间差
	public String getTimeDiff(Date date) {
	        Calendar cal = Calendar.getInstance();
	        long diff = 0;
	        Date dnow = cal.getTime();
	        String str = "";
	        diff = dnow.getTime() - date.getTime();
	        //30 * 24 * 60 * 60 * 1000=2592000000 毫秒
	        if  (diff > 2592000000L) {
	            str="1个月前";
	          //21 * 24 * 60 * 60 * 1000=1814400000 毫秒
	        } else if  (diff > 1814400000) {
	            str="3周前";
	          //14 * 24 * 60 * 60 * 1000=1209600000 毫秒
	        } else if  (diff > 1209600000) {
	            str="2周前";
	          //7 * 24 * 60 * 60 * 1000=604800000 毫秒
	        } else if  (diff > 604800000) {
	            str="1周前";
	            //24 * 60 * 60 * 1000=86400000 毫秒
	        } else if (diff > 86400000) {  
	        	// 60 * 60 * 1000=3600000 毫秒
	            str=(int)Math.floor(diff/86400000f) + "天前";
	        } else if (diff > 3600000 ) {
	        	//1 * 60 * 1000=60000 毫秒
	            str=(int)Math.floor(diff/3600000f) + "小时前";
	        } else if (diff > 60000) {
	            str=(int)Math.floor(diff/60000) +"分钟前";
	        }else{
	            str=(int)Math.floor(diff/1000) +"秒前";
	        }
	        return str;
	    }
}
