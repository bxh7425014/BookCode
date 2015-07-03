package com.supermario.rabit;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//游戏主界面
public class GameSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	//兔子
	private Rabit rabit;
	//刷新类
	private RefurbishThread refurbishThread;
	//铃铛
	private List<Bell> bell_list = new ArrayList<Bell>();
	//背景
	private Backgroud bg;
	//上下文
	private Context context;
	//铃铛制造类
	private BellCreator bell_creator;
	//小鸟
	private Bird bird;
	private SurfaceHolder holder;
	private Bitmap bitmap_jump;
	//向上跳按钮是否被按下
	private boolean jump_button_clicked = false;
	private boolean jump_comm_flag = false;
	//是否播放音乐标志位
	private boolean audio_on = false;
	//音乐提供类
	private AudioProvider audioProvider;
	//分数
	private int score = 0;
	//最高分数
	private int highest_score;
	//撞击次数
	private int hit_count = 0;
	private Paint paint;
	//游戏结束标志
	private boolean game_over = false;
	//结束界面
	private Conclusion conclusion;
	//构造函数
	public GameSurfaceView(Context context) {
		super(context);
		this.context = context;
		//实例化小兔
		rabit = new Rabit(context);
		//实例化背景
		bg = new Backgroud(context);
		//实例化结束界面
		conclusion = new Conclusion(this);
		bitmap_jump = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.button);
		//初始化声音设置
		init_audio();
		//初始化最高分数显示
		init_highest_score();
		//初始化铃铛制造类
		bell_creator = new BellCreator(context);
		bird = new Bird(context);
		//刷新类
		refurbishThread = new RefurbishThread();
		//初始化铃铛列表
		init_bell_list();
		paint = new Paint();
		this.holder = this.getHolder();
		holder.addCallback(this);
		this.setFocusable(true);
	}

	private void init() {
		rabit.init();
		bg.init();
		init_bird();
		jump_button_clicked = false;
		jump_comm_flag = false;
		for (int i = 0; i < bell_list.size(); ++i) {
			bell_creator.recycle(bell_list.get(i));
		}
		bell_creator.init();
		init_bell_list();
		//重新设置最高分数
		highest_score = (highest_score > score) ? highest_score : score;
		score = 0;
		hit_count = 0;
	}
	//初始化小鸟
	private void init_bird() {
		bird.setState(Bird.BIRD_LEFT_FLY0);
		//设置小鸟初始位置
		bird.setCenter_x(240);
		bird.setCenter_y(30);
		//显示在屏幕中
		bird.setOn_screen(true);
	}
	//初始化最高分显示
	private void init_highest_score() {
		SharedPreferences settings = ((GameActivity) context)
				.getPreferences(Activity.MODE_PRIVATE);
		//取得存储在文件中的最高纪录
		highest_score = settings.getInt("highestscore", 0);
	}
	//初始化音乐类
	private void init_audio() {
		this.audio_on = ((GameActivity) context).isAudio_on();
		if (audio_on) {
			audioProvider = new AudioProvider(context);
		}
	}
	//初始化铃铛
	private void init_bell_list() {
		//制造出5个铃铛
		Bell bell0 = bell_creator.createBell();
		Bell bell1 = bell_creator.createBell();
		Bell bell2 = bell_creator.createBell();
		Bell bell3 = bell_creator.createBell();
		Bell bell4 = bell_creator.createBell();
		//设置铃铛的初始Y坐标
		bell0.setCenter_y(170);
		bell1.setCenter_y(130);
		bell2.setCenter_y(90);
		bell3.setCenter_y(50);
		bell4.setCenter_y(10);
		//将铃铛添加进铃铛列表中
		bell_list.removeAll(bell_list);
		bell_list.add(bell0);
		bell_list.add(bell1);
		bell_list.add(bell2);
		bell_list.add(bell3);
		bell_list.add(bell4);
	}
	//绘制界面
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		bg.onDraw(canvas);
		//如果为点击跳跃按钮，则在右下角显示跳跃按钮
		if (!jump_button_clicked) {
			canvas.drawBitmap(bitmap_jump, 333, 199, paint);
		}
		//绘制铃铛
		drawBell(canvas);
		//绘制小兔
		rabit.onDraw(canvas);
		//绘制分数
		drawScore(canvas);
		//如果游戏结束，则绘制结束界面
		if (game_over) {
			conclusion.onDraw(canvas);
		}
		//如果小鸟在屏幕则绘制小鸟
		if (bird.isOn_screen()) {
			bird.onDraw(canvas);
		}
	}
	//绘制铃铛
	private void drawBell(Canvas canvas) {
		for (int i = 0; i < bell_list.size(); ++i) {
			bell_list.get(i).onDraw(canvas);
		}
	}
	//绘制分数
	private void drawScore(Canvas canvas) {
		//设置字体颜色为白色
		paint.setColor(Color.WHITE);
		paint.setTextSize(15);
		canvas.drawText("" + score, 22, 22, paint);
	}
	//监听按键
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			this.refurbishThread.setGo_on(false);
		}
		return true;
	}
	//监听触摸事件
	public boolean onTouchEvent(MotionEvent event) {
		//获得触摸位置的xy坐标
		float x = event.getX();
		float y = event.getY();
		//如果游戏已经结束
		if (game_over) {
			//游戏结束后的触屏处理
			switch (event.getAction()) {
			//点击了按钮区域
			case MotionEvent.ACTION_DOWN:
				if (x >= 165 && x <= 237 && y >= 154 && y <= 180) {
					conclusion.setPressed(true);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			//点击了非按钮区域
			case MotionEvent.ACTION_UP:
				if (x >= 165 && x <= 237 && y >= 154 && y <= 180) {
					conclusion.setPressed(false);
					init();
					game_over = false;
				}
				break;
			default:
				break;
			}
		} else {
			//游戏中的触屏处理
			if (jump_button_clicked) {
				//设置小兔的x轴目标位置
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_UP:
					rabit.setX_destination(x);
					break;
				default:
					rabit.setX_destination(rabit.getX());
				}
			} else {
				//点击了跳跃按钮
				if (x > 327 && x < 364 && y > 196 && y < 230) {
					if(event.getAction() == MotionEvent.ACTION_UP){
						jump_comm_flag = true;
						jump_button_clicked = true;
					}
				} else {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_MOVE:
					case MotionEvent.ACTION_UP:
						rabit.setX_destination(x);
						break;
					default:
						rabit.setX_destination(rabit.getX());
					}
				}
			}
		}
		return true;
	}
	//界面改变
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	//界面初始化
	public void surfaceCreated(SurfaceHolder holder) {
		refurbishThread.setGo_on(true);
		//启动线程
		refurbishThread.start();
		if (audio_on) {
			//播放背景音乐
			audioProvider.play_bg();
		}
	}
	//界面销毁
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		refurbishThread.setGo_on(false);
		if (audio_on) {
			//释放媒体资源
			audioProvider.release();
		}
		//保存最高分
		highest_score = (highest_score > score) ? highest_score : score;
		SharedPreferences pre = ((GameActivity) context).getPreferences(0);
		SharedPreferences.Editor editor = pre.edit();
		editor.putInt("highestscore", highest_score);
	}
	//刷新进程
	class RefurbishThread extends Thread {
		private boolean go_on = false;
		//取得是否继续
		public boolean isGo_on() {
			return go_on;
		}
		//设置是否继续
		public void setGo_on(boolean goOn) {
			go_on = goOn;
		}
		//运行
		public void run() {
			while (go_on) {
				try {
					// Thread.sleep(50);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//更新所有组件
				update_all_components();
				synchronized (holder) {
					Canvas canvas = holder.lockCanvas();
					//绘制画面
					GameSurfaceView.this.onDraw(canvas);
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	//更新小兔的状态
	private void update_rabit(){
		//如果小兔在地面
		if (rabit.isOnGround()) {
			// 处理水平方向移动
			// rabit 到达目的位置
			if (Math.abs(rabit.getX() - rabit.getX_destination()) < 5) {
				//如果小兔面朝左
				if (rabit.isFaceLeft()) {
					//小兔状态为面朝左停下
					rabit.setPic_state(Rabit.RABIT_PIC_LEFT_STOP);
					//设置小兔朝向
					rabit.setFace_state(Rabit.RABIT_FACE_LEFT);
					//小兔在地面状态为停下
					rabit.setGround_state(Rabit.RABIT_LEFT_STOP);
				} else {
					//小兔状态为面朝右停下
					rabit.setPic_state(Rabit.RABIT_PIC_RIGHT_STOP);
					//设置小兔朝向
					rabit.setFace_state(Rabit.RABIT_FACE_RIGHT);
					//小兔在地面状态为停下
					rabit.setGround_state(Rabit.RABIT_RIGHT_STOP);
				}
			} else if (rabit.getX() - rabit.getX_destination() >= 5) {
				// rabit 在destination的右面，它将向左移动
				rabit.setFace_state(Rabit.RABIT_FACE_LEFT);
				rabit.setX(rabit.getX() - Rabit.SPEED_X);
					//小兔在地面上向左运动显示的图片0
				if (rabit.getGround_state() == Rabit.RABIT_LEFT_MOVE1_ON_GROUND) {
					rabit.setGround_state(Rabit.RABIT_LEFT_MOVE2_ON_GROUND);
					rabit.setPic_state(Rabit.RABIT_PIC_ON_GROUND_LEFT_JUMP1);
					//小兔在地面上向左运动显示的图片1
				} else if (rabit.getGround_state() == Rabit.RABIT_LEFT_MOVE2_ON_GROUND) {
					rabit.setGround_state(Rabit.RABIT_LEFT_MOVE1_ON_GROUND);
					rabit.setPic_state(Rabit.RABIT_PIC_ON_GROUND_LEFT_JUMP0);
					//小兔在地面向左运动一开始显示的图片
				} else {
					rabit.setGround_state(Rabit.RABIT_LEFT_MOVE1_ON_GROUND);
					rabit.setPic_state(Rabit.RABIT_PIC_ON_GROUND_LEFT_JUMP0);
				}

			} else if (rabit.getX_destination() - rabit.getX() > 5) {
				// rabit 在destination的左面，它将向右移动
				rabit.setX(rabit.getX() + Rabit.SPEED_X);
				rabit.setFace_state(Rabit.RABIT_FACE_RIGHT);
					//小兔在地面上向右运动显示的图片0
				if (rabit.getGround_state() == Rabit.RABIT_RIGHT_MOVE1_ON_GROUND) {
					rabit.setGround_state(Rabit.RABIT_RIGHT_MOVE2_ON_GROUND);
					rabit.setPic_state(Rabit.RABIT_PIC_ON_GROUND_RIGHT_JUMP1);
					//小兔在地面上向右运动显示的图片1
				} else if (rabit.getGround_state() == Rabit.RABIT_RIGHT_MOVE2_ON_GROUND) {
					rabit.setGround_state(Rabit.RABIT_RIGHT_MOVE1_ON_GROUND);
					rabit.setPic_state(Rabit.RABIT_PIC_ON_GROUND_RIGHT_JUMP0);
				} else {
					//小兔在地面向左运动一开始显示的图片
					rabit.setGround_state(Rabit.RABIT_RIGHT_MOVE1_ON_GROUND);
					rabit.setPic_state(Rabit.RABIT_PIC_ON_GROUND_RIGHT_JUMP0);
				}
			}
			//点击了跳跃按钮
			if (jump_comm_flag) {
				rabit.setAir_state(Rabit.RABIT_ON_AIR_UP0);
				rabit.setGround_state(Rabit.RABIT_NOT_ON_GROUND);
				rabit.setY(rabit.getY() - Rabit.SPEED_Y);
				//面朝左
				if (rabit.isFaceLeft()) {
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
				} else {
					//面朝右
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
				}
				jump_comm_flag = false;
			}

		} else {
			// rabit在空中
			// 处理水方向移动
			if (rabit.getX() - rabit.getX_destination() >= 10) {
				// rabit 在destination的右面，它将向左移动
				rabit.setFace_state(Rabit.RABIT_FACE_LEFT);
				rabit.setX(rabit.getX() - Rabit.SPEED_X_ON_AIR);
			} else if (rabit.getX_destination() - rabit.getX() > 10) {
				// rabit 在destination的左面，它将向右移动
				rabit.setX(rabit.getX() + Rabit.SPEED_X_ON_AIR);
				rabit.setFace_state(Rabit.RABIT_FACE_RIGHT);
			}
			// 处理垂直方向 移动
			if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_UP0) {
				// 小兔向上运动
				rabit_move_up();
				rabit.setAir_state(Rabit.RABIT_ON_AIR_UP1);
				//小兔朝左跳
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
				//小兔朝右
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
				//上升状态1
			} else if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_UP1) {
				// rabit.setY(rabit.getY()-Rabit.SPEED_Y);
				rabit_move_up();
				rabit.setAir_state(Rabit.RABIT_ON_AIR_UP2);
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
				//上升状态2
			} else if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_UP2) {
				// rabit.setY(rabit.getY()-Rabit.SPEED_Y);
				rabit_move_up();
				rabit.setAir_state(Rabit.RABIT_ON_AIR_UP3);
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
				//上升状态3
			} else if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_UP3) {
				// rabit.setY(rabit.getY()-Rabit.SPEED_Y);
				rabit_move_up();
				rabit.setAir_state(Rabit.RABIT_ON_AIR_UP4);
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
				//上升状态4
			} else if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_UP4) {
				// rabit.setY(rabit.getY()-Rabit.SPEED_Y);
				rabit_move_up();
				rabit.setAir_state(Rabit.RABIT_ON_AIR_UP5);
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
				//上升状态5
			} else if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_UP5) {
				rabit.setAir_state(Rabit.RABIT_ON_AIR_STOP);
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_STOP);
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_STOP);
				//停止上升
			} else if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_STOP) {
				rabit.setAir_state(Rabit.RABIT_ON_AIR_DOWN);
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_STOP);
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_STOP);
				//开始往下掉
			} else if (rabit.getAir_state() == Rabit.RABIT_ON_AIR_DOWN) {
				// rabit.setY(rabit.getY() + Rabit.SPEED_Y);
				rabit.setAir_state(Rabit.RABIT_ON_AIR_DOWN);
				if (rabit.isFaceLeft())
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_DOWN);
				else
					rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_DOWN);
				rabit_move_down();
			}
		}
	}
	//更新铃铛的状态
	private void update_bells(){
		for (int i = 0; i < bell_list.size(); ++i) {
			Bell bell = bell_list.get(i);
			//铃铛爆炸
			if (bell.isExplode()) {
				//爆炸状态0
				if (bell.getState() == Bell.BELL_EXPLODE0) {
					bell.setState(Bell.BELL_EXPLODE1);
				//爆炸状态1
				} else if (bell.getState() == Bell.BELL_EXPLODE1) {
					bell.setState(Bell.BELL_EXPLODE2);
				//爆炸状态2
				} else if (bell.getState() == Bell.BELL_EXPLODE2) {
					bell_list.remove(bell);
					bell_creator.recycle(bell);
					--i;
				}
			} else {
				if (rabit.isHitBell(bell)) {
					++hit_count;
					//分数累加
					score += hit_count * 10;
					//播放铃铛声音
					if(audio_on) audioProvider.play_bell_ding();
					bell.setState(Bell.BELL_EXPLODE0);
					rabit.setAir_state(Rabit.RABIT_ON_AIR_UP0);
					if (rabit.isFaceLeft()) {
						rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
					} else {
						rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
					}
				}
			}
		}
	}
	//更新小鸟的状态
	private void update_bird(){
		if (bird.isOn_screen()) {
			// 处理垂直
			if (bird.getCenter_y() > Constant.SCREEN_HEIGHT) {
				bird.setOn_screen(false);
			}
		}
		//若小鸟在屏幕
		if (bird.isOn_screen()) {
			// 小鸟被击中
			if (bird.isHited()) {
				if (bird.isFaceLeft()) {
					//加速
					bird.setCenter_x(bird.getCenter_x() - Bird.BIRD_SPEED_POWER);
					if (bird.getCenter_x() < -5) {
						bird.setOn_screen(false);
					}
				} else {
					//加速
					bird.setCenter_x(bird.getCenter_x()
									+ Bird.BIRD_SPEED_POWER);
					if (bird.getCenter_x() > Constant.SCREEN_WIDTH + 5) {
						bird.setOn_screen(false);
					}
				}
			} else {
				if (bird.isFaceLeft()) {
					//正常速度
					bird.setCenter_x(bird.getCenter_x() - Bird.BIRD_SPEED);
					if (bird.getCenter_x() < -5) {
						bird.setCenter_x(0);
						bird.setState(Bird.BIRD_RIGHT_FLY0);
					} else {
						if (rabit.isHitBird(bird)) {
							++hit_count;
							//分数翻倍
							score *= 2;
							//播放鸟叫
							if(audio_on) audioProvider.play_twitter();
							bird.setState(Bird.BIRD_LEFT_FLY_POWER);
							rabit.setAir_state(Rabit.RABIT_ON_AIR_UP0);
							if (rabit.isFaceLeft()) {
								rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
							} else {
								rabit.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
							}
						} else if (bird.getState() == Bird.BIRD_LEFT_FLY0) {
							bird.setState(Bird.BIRD_LEFT_FLY1);
						} else if (bird.getState() == Bird.BIRD_LEFT_FLY1) {
							bird.setState(Bird.BIRD_LEFT_FLY2);
						} else {
							bird.setState(Bird.BIRD_LEFT_FLY0);
						}
					}

				} else {
					bird.setCenter_x(bird.getCenter_x() + Bird.BIRD_SPEED);
					if (bird.getCenter_x() > Constant.SCREEN_WIDTH + 5) {
						bird.setCenter_x(Constant.SCREEN_WIDTH);
						bird.setState(Bird.BIRD_LEFT_FLY0);
					} else {
						if (rabit.isHitBird(bird)) {
							++hit_count;
							score *= 2;
							if(audio_on) audioProvider.play_twitter();
							bird.setState(Bird.BIRD_LEFT_FLY_POWER);
							rabit.setAir_state(Rabit.RABIT_ON_AIR_UP0);
							if (rabit.isFaceLeft()) {
								rabit
										.setPic_state(Rabit.RABIT_PIC_ON_AIR_LEFT_JUMP);
							} else {
								rabit
										.setPic_state(Rabit.RABIT_PIC_ON_AIR_RIGHT_JUMP);
							}
						} else if (bird.getState() == Bird.BIRD_RIGHT_FLY0) {
							bird.setState(Bird.BIRD_RIGHT_FLY1);
						} else if (bird.getState() == Bird.BIRD_RIGHT_FLY1) {
							bird.setState(Bird.BIRD_RIGHT_FLY2);
						} else {
							bird.setState(Bird.BIRD_RIGHT_FLY0);
						}
					}
				}
			}
		}
		// 添加一个bird的逻辑
		if (hit_count % 11 == 0 && bird.isOn_screen() == false
				&& rabit.getY() < 210) {
			init_bird();
		}
	}
	//更新所有组件
	private void update_all_components() {
		if (game_over)
			return;
		//更新小兔
		update_rabit();
		//更新铃铛
		update_bells();
		//更新小鸟
		update_bird();
	}
	//小兔向上运动
	private void rabit_move_up() {
		//如果小兔的位置在屏幕中线以下时，只更新小兔的坐标
		if (rabit.getCenter_y() > Constant.SCREEN_HEIGHT / 2) {
			rabit.setY(rabit.getY() - Rabit.SPEED_Y);
			return;
		}
		// 处理屏幕背景的变化
		// 处理bells的位置
		// 所有的bell下移	
		// rabit跳跃至屏幕1/2处~2/3时，处理整个场景Components变化
		if (rabit.getCenter_y() > 0.33 * Constant.SCREEN_HEIGHT) {
			for (int i = 0; i < bell_list.size(); ++i) {
				Bell bell = bell_list.get(i);
				//所有铃铛的位置下移
				bell.setCenter_y(bell.getCenter_y() + Rabit.SPEED_Y / 2);
			}
			//背景以半速下移
			bg.drag_down((int) Rabit.SPEED_Y / 2);
			//小鸟以半速下移
			bird.setCenter_y(bird.getCenter_y() + Rabit.SPEED_Y / 2);
			//小兔以半速上移
			rabit.setCenter_y(rabit.getCenter_y() - Rabit.SPEED_Y / 2);
		} else {
			// rabit跳跃比屏幕的2/3处还高时，处理整个场景的变化
			for (int i = 0; i < bell_list.size(); ++i) {
				Bell bell = bell_list.get(i);
				//铃铛以全速下移
				bell.setCenter_y(bell.getCenter_y() + Rabit.SPEED_Y);
			}
			bg.drag_down((int) Rabit.SPEED_Y);
			if (bird.isOn_screen()) {
				//小鸟以全速下移
				bird.setCenter_y(bird.getCenter_y() + Rabit.SPEED_Y);
			}
		}
		// 是否需要清理落地的bell, bell_list[0] 处于最底处，判断它是否落地
		Bell lowBell = bell_list.get(0);
		if (lowBell != null) {
			if (lowBell.getCenter_y() > 230) {
				bell_list.remove(lowBell);
				//清理消失在屏幕视野的铃铛
				bell_creator.recycle(lowBell);
			}
		}
		// 判断是否需要添加新的bell， bell_list[size-1] 处于最高处，通过它来判断
		if (bell_list.size() > 0) {
			Bell upBell = bell_list.get(bell_list.size() - 1);
			if (upBell.getCenter_y() > 50)
				bell_list.add(bell_creator.createBell());
		}

	}
	//小兔向下移动
	private void rabit_move_down() {
		if (rabit.getY() > 210) {
			//rabit已经下落至屏幕底部，不可能在碰到bell， rabit输了高空下坠时背景，bell，bird的位置处理
			if (bg.isLowest()) {
				game_over = true;
				rabit.setY(Constant.RABIT_INIT_Y);
				if (rabit.isFaceLeft()) {
					rabit.setPic_state(Rabit.RABIT_PIC_LEFT_STOP);
				} else {
					rabit.setPic_state(Rabit.RABIT_PIC_RIGHT_STOP);
				}
				bg.init();
			} else {
				// 背景向上拖动
				bg.drag_up((int) Rabit.SPEED_Y);
				//屏幕上的bird向上移动
				if (bird.isOn_screen()) {
					bird.setCenter_y(bird.getCenter_y() - Rabit.SPEED_Y);
					if (bird.getCenter_y() < 0) {
						bird.setOn_screen(false);
					}
				}
				// 所有的bell向上移动
				for (int i = 0; i < bell_list.size(); ++i) {
					Bell bell = bell_list.get(i);
					bell.setCenter_y(bell.getCenter_y() - Rabit.SPEED_Y);
				}
				// 处理移动到屏幕外的bell
				if (bell_list.size() > 0) {
					Bell bell_up = bell_list.get(bell_list.size() - 1);
					if (bell_up.getCenter_y() < -Bell.BELL_OK_HEIGHT / 2) {
						//移除铃铛
						bell_list.remove(bell_up);
						bell_creator.recycle(bell_up);
					}
				}
			}
		} else {
			// rabit 还没有玩完
			rabit.setY(rabit.getY() + Rabit.SPEED_Y);
		}
	}
	//取得分数
	public int getScore() {
		return score;
	}
	//设置分数
	public void setScore(int score) {
		this.score = score;
	}
	//取得最高分
	public int getHighest_score() {
		return highest_score;
	}
	//设置最高分
	public void setHighest_score(int highestScore) {
		highest_score = highestScore;
	}
}