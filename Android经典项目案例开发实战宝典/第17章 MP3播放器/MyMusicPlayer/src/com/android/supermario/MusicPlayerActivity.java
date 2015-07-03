package com.android.supermario;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 音乐播放界面主类
 * 强制竖屏：AndroidManifest中设置Activity的Screen Orientation为portrait
 */
public class MusicPlayerActivity extends Activity {
	//三个按钮：前一首、播放、后一首
	ImageButton left_ImageButton;
	public static ImageButton play_ImageButton;
	ImageButton right_ImageButton;
	//音乐列表
	ListView musicListView;
	//专辑列表
	GridView musicGridView;
	//上下文
	public static Context context;
	// 初始化歌词检索值
	public int Index = 0;
	// 为后台播放通知创建对象
	public static NotificationManager mNotificationManager;
	// 绑定SeekBar和各种属性TextView
	public static SeekBar seekbar;
	public static TextView time_left;
	public static TextView time_right;
	public static TextView music_Name;
	public static LrcView lrc_view;
	public static TextView music_Album;
	public static TextView music_Artist;
	public static ImageView music_AlbumArt;
	public static TextView music_number;
	//左侧动画
	public static RunGif runEql;
	// 为倒影创建对象
	public RelativeLayout relativeflac;
	public static ImageView reflaction;
	// 为歌曲时间和播放时间定义静态变量
	public static int song_time = 0;
	public static int play_time = 0;
	// 为类Music_infoAdapter声明静态变量
	public static Music_infoAdapter music_info;
	// 声明两个页面对象
	private BigDragableLuncher bigPage;
	private DragableLuncher smallPage;
	// 声明按钮及对应的图片
	private ImageButton[] blind_btn = new ImageButton[3];
	private int[] btn_id = new int[] { R.id.imageButton1, R.id.imageButton2,
			R.id.imageButton3 };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		bigPage = (BigDragableLuncher) findViewById(R.id.all_space);
		//中间滑动模块
		smallPage = (DragableLuncher) findViewById(R.id.space);
		// 绑定GIF动画界面
		runEql = (RunGif) findViewById(R.id.runGif1);
		// 倒影布局
		relativeflac = (RelativeLayout) findViewById(R.id.relativelayout1);
		reflaction = (ImageView) findViewById(R.id.inverted_view);
		// 创建对象获得系统服务
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 绑定歌曲列表界面
		musicListView = (ListView) findViewById(R.id.listView1);
		new MusicListView().disPlayList(musicListView, this);
		// 将获取的歌曲属性放到当前适配器中
		music_info = new Music_infoAdapter(this);
		// 绑定专辑列表界面
		musicGridView = (GridView) findViewById(R.id.gridview1);
		new MusicSpecialView().disPlaySpecial(musicGridView, this);
		// 监听播放列表点击事件
		musicListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//打开播放音乐服务
				Intent play_1 = new Intent(MusicPlayerActivity.this,
						ControlPlay.class);
				//将控制参数传递给音乐播放服务
				play_1.putExtra("control", "listClick");
				play_1.putExtra("musicId_1", arg2);
				startService(play_1);
				//点击后动画跳转播放界面
				bigPage.setAnimation(AnimationUtils.loadAnimation(
						MusicPlayerActivity.this, R.anim.alpha_x));
				bigPage.setToScreen(1);
				blind_btn[1]
						.setBackgroundResource(R.drawable.big_button_pressed);
				blind_btn[0].setBackgroundResource(R.drawable.big_button_style);
			}
		});
		// 监听专辑列表点击事件
		musicGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//打开音乐播放服务
				Intent play_2 = new Intent(MusicPlayerActivity.this,
						ControlPlay.class);
				//传递专辑信息和控制信息
				play_2.putExtra("control", "gridClick");
				play_2.putExtra("musicId_2", arg2);
				startService(play_2);
				// 点击后动画跳转播放界面
				bigPage.setAnimation(AnimationUtils.loadAnimation(
						MusicPlayerActivity.this, R.anim.alpha_x));
				bigPage.setToScreen(1);
				blind_btn[1].setBackgroundResource(R.drawable.big_button_pressed);
				blind_btn[2].setBackgroundResource(R.drawable.big_button_style);
			}
		});
		// 小页面允许触摸执行动画
		smallPage.isOpenTouchAnima(true);
		// 绑定ImageButton
		for (int k = 0; k < blind_btn.length; k++) {
			blind_btn[k] = (ImageButton) findViewById(btn_id[k]);
		}
		// 设置按钮被选中颜色和默认颜色
		bigPage.setBottomBarBg(blind_btn, Color.GREEN, Color.YELLOW);
		// 判断点击了哪个按钮并执行跳转界面
		android.view.View.OnClickListener ocl = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				//音乐列表
				case R.id.imageButton1:
					bigPage.setAnimation(AnimationUtils.loadAnimation(
							MusicPlayerActivity.this, R.anim.alpha_x));
					bigPage.setToScreen(0);
					//设置当前按钮的状态为按下
					v.setPressed(true);
					//设置三个按钮的背景图片
					blind_btn[0]
							.setBackgroundResource(R.drawable.big_button_pressed);
					blind_btn[1]
							.setBackgroundResource(R.drawable.big_button_style);
					blind_btn[2]
							.setBackgroundResource(R.drawable.big_button_style);
					break;
				//正在播放
				case R.id.imageButton2:
					bigPage.setAnimation(AnimationUtils.loadAnimation(
							MusicPlayerActivity.this, R.anim.alpha_x));
					bigPage.setToScreen(1);
					//设置当前按钮的状态为按下
					v.setPressed(true);
					//设置三个按钮的背景图片
					blind_btn[1]
							.setBackgroundResource(R.drawable.big_button_pressed);
					blind_btn[0]
							.setBackgroundResource(R.drawable.big_button_style);
					blind_btn[2]
							.setBackgroundResource(R.drawable.big_button_style);
					break;
				case R.id.imageButton3:
					bigPage.setAnimation(AnimationUtils.loadAnimation(
							MusicPlayerActivity.this, R.anim.alpha_x));
					bigPage.setToScreen(2);
					//设置当前按钮的状态为按下
					v.setPressed(true);
					//设置三个按钮的背景图片
					blind_btn[2]
							.setBackgroundResource(R.drawable.big_button_pressed);
					blind_btn[1]
							.setBackgroundResource(R.drawable.big_button_style);
					blind_btn[0]
							.setBackgroundResource(R.drawable.big_button_style);
				}
			}
		};
		// 初始化设置
		blind_btn[1].setBackgroundResource(R.drawable.big_button_pressed);
		// 三个按钮分别指定监听器
		blind_btn[0].setOnClickListener(ocl);
		blind_btn[1].setOnClickListener(ocl);
		blind_btn[2].setOnClickListener(ocl);
		// 初始化按钮
		left_ImageButton = (ImageButton) findViewById(R.id.ib1);
		play_ImageButton = (ImageButton) findViewById(R.id.ib2);
		right_ImageButton = (ImageButton) findViewById(R.id.ib3);
		//初始化界面其他元素
		time_left = (TextView) findViewById(R.id.time_tv1);
		time_right = (TextView) findViewById(R.id.time_tv2);
		music_Name = (TextView) findViewById(R.id.music_name);
		music_Album = (TextView) findViewById(R.id.music_album);
		music_Artist = (TextView) findViewById(R.id.music_artist);
		seekbar = (SeekBar) findViewById(R.id.player_seekbar);
		lrc_view = (LrcView) findViewById(R.id.LyricShow);
		music_AlbumArt = (ImageView) findViewById(R.id.music_AlbumArt);
		music_number = (TextView) findViewById(R.id.music_number);
		
		// 判断歌曲不能为空并且后缀为.mp3
		if (music_info.getCount() > 0
				&& Music_infoAdapter.musicList.get(ControlPlay.playing_id)
						.getMusicName().endsWith(".mp3")) {
			// 显示获取的歌曲时间
			time_right.setText(Music_infoAdapter
					.toTime(Music_infoAdapter.musicList.get(
							ControlPlay.playing_id).getMusicTime()));
			// 截取.mp3字符串
			String a = Music_infoAdapter.musicList.get(ControlPlay.playing_id)
					.getMusicName();
			int b = a.indexOf(".mp3");
			String c = a.substring(0, b);
			// 显示获取的歌曲名
			music_Name.setText(c);
			music_Name.setAnimation(AnimationUtils.loadAnimation(
					MusicPlayerActivity.this, R.anim.translate_z));

			// 显示播放当前第几首和歌曲总数
			int x = ControlPlay.playing_id + 1;
			music_number.setText("" + x + "/"
					+ Music_infoAdapter.musicList.size());

			// 显示获取的艺术家
			music_Artist.setText(Music_infoAdapter.musicList.get(
					ControlPlay.playing_id).getMusicSinger());
			// 获取专辑图片路径
			String url = MusicPlayerActivity.music_info
					.getAlbumArt(Music_infoAdapter.musicList.get(
							ControlPlay.playing_id).get_id());
			if (url != null) {
				// 显示获取的专辑图片
				music_AlbumArt.setImageURI(Uri.parse(url));
				music_AlbumArt.setAnimation(AnimationUtils.loadAnimation(
						context, R.anim.alpha_z));
				try {
					/* 为倒影创建位图 */
					Bitmap mBitmap = BitmapFactory.decodeFile(url);
					reflaction.setImageBitmap(createReflectedImage(mBitmap));
					reflaction.setAnimation(AnimationUtils.loadAnimation(
							context, R.anim.alpha_z));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				//设置显示为默认图片
				music_AlbumArt.setImageResource(R.drawable.album);
				music_AlbumArt.setAnimation(AnimationUtils.loadAnimation(
						context, R.anim.alpha_z));
				try {
					/* 为倒影创建位图 */
					Bitmap mBitmap = ((BitmapDrawable) getResources()
							.getDrawable(R.drawable.album)).getBitmap();
					reflaction.setImageBitmap(createReflectedImage(mBitmap));
					reflaction.setAnimation(AnimationUtils.loadAnimation(
							context, R.anim.alpha_z));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Toast.makeText(MusicPlayerActivity.this, "手机里木有找到歌曲哦！",
					Toast.LENGTH_LONG).show();
		}
		/**
		 * 监听拖动SeekBar事件
		 */
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				// 判断用户是否触拖SeekBar并且不为空才执行
				if (fromUser && ControlPlay.myMediaPlayer != null) {
					ControlPlay.myMediaPlayer.seekTo(progress);
				}
				time_left.setText(Music_infoAdapter.toTime(progress));
			}
		});
		/**
		 * 监听“上一首”并实现功能
		 */
		left_ImageButton.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//打开音乐播放服务
				Intent play_left = new Intent(MusicPlayerActivity.this,
						ControlPlay.class);
				play_left.putExtra("control", "front");
				startService(play_left);
			}
		});
		/**
		 * 监听“播放”并实现功能
		 */
		play_ImageButton.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//打开音乐播放服务
				Intent play_center = new Intent(MusicPlayerActivity.this,
						ControlPlay.class);
				play_center.putExtra("control", "play");
				startService(play_center);
			}

		});
		/**
		 * 监听“下一首”并实现功能
		 */
		right_ImageButton.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//打开音乐播放服务
				Intent play_right = new Intent(MusicPlayerActivity.this,
						ControlPlay.class);
				play_right.putExtra("control", "next");
				startService(play_right);
			}
		});

	}
	/**
	 * 倒影的实现方法
	 * 
	 * @param originalBitmap
	 * @return
	 */
	static Bitmap createReflectedImage(Bitmap originalBitmap) {
		// TODO Auto-generated method stub

		// 图片与倒影间隔距离
		final int reflectionGap = 4;
		// 图片的宽度
		int width = originalBitmap.getWidth();
		// 图片的高度
		int height = originalBitmap.getHeight();

		Matrix matrix = new Matrix();
		// 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
		matrix.preScale(1, -1);
		// 创建反转后的图片Bitmap对象，图片高是原图的一半。
		Bitmap reflectionBitmap = Bitmap.createBitmap(originalBitmap, 0,
				height / 2, width, height / 2, matrix, false);
		// 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
		Bitmap withReflectionBitmap = Bitmap.createBitmap(width, (height
				+ height / 2 + reflectionGap), Config.ARGB_8888);

		// 构造函数传入Bitmap对象，为了在图片上画图
		Canvas canvas = new Canvas(withReflectionBitmap);
		// 画原始图片
		canvas.drawBitmap(originalBitmap, 0, 0, null);

		// 画间隔矩形
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);

		// 画倒影图片
		canvas.drawBitmap(reflectionBitmap, 0, height + reflectionGap, null);

		// 实现倒影效果
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalBitmap.getHeight(), 0,
				withReflectionBitmap.getHeight(), 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		// 覆盖效果
		canvas.drawRect(0, height, width, withReflectionBitmap.getHeight(),
				paint);

		return withReflectionBitmap;
	}

	/**
	 * 按下“返回键”后提示用户是否退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Dialog dialog = new MyDialog(MusicPlayerActivity.this,
					R.style.MyDialog);
			// 设置触摸对话框以外的地方取消对话框
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 后台提示播放通知的方法
	 * 需要在AndroidManifest的C_MusicPlayerActivity中添加android:launchMode=
	 * "singleTop"才可以完全退出
	 * 
	 * @param tickerText
	 *            传入的歌曲名
	 * @param title
	 * @param content
	 * @param drawable
	 *            图片路径
	 */
	public static void setNotice(String tickerText, String title,
			String content, int drawable) {
		// 创建一个通知对象，传入相应的参数
		Notification notification = new Notification(drawable, tickerText,
				System.currentTimeMillis());
		// 设置通知不能被清除
		notification.flags = Notification.FLAG_NO_CLEAR;
		// 封装的Intent跳转，由系统来决定什么时候启动跳转Intent
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, MusicPlayerActivity.class), 0);
		// notification.setLatestEventInfo(this, "通知标题","通知内容", intent);
		notification.setLatestEventInfo(context, title, content, contentIntent);
		// 发送通知,代码应该放到最后，否则会报错,显示的Notification都有一个唯一的ID是1
		mNotificationManager.notify(1, notification);
	}

	/**
	 * 自定义对话框的类
	 */
	class MyDialog extends Dialog {
		Context context;
		//构造函数
		public MyDialog(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			this.context = context;
		}
		//构造函数
		public MyDialog(Context context, int theme) {
			super(context, theme);
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			this.setContentView(R.layout.dialog);
			//退出按钮和返回按钮
			Button exit_button, return_button;
			//初始化
			exit_button = (Button) findViewById(R.id.exit_button2);
			return_button = (Button) findViewById(R.id.return_button3);

			// 结束服务退出应用程序
			exit_button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//关掉音乐播放服务
					stopService(new Intent(MusicPlayerActivity.this,
							ControlPlay.class));
					mNotificationManager.cancel(1);
					//退出程序
					System.exit(0);
				}
			});

			// 返回
			return_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//关闭对话框
					dismiss();
				}
			});
		}
	}

}
