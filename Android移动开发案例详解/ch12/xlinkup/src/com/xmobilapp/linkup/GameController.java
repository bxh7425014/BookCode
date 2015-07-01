package com.xmobilapp.linkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xmobilapp.linkup.GameView.OnItemClickListener;

public class GameController {

	// 列数
	private static final int countX = 10;
	// 行数
	private static final int countY = 12;
	// 图片大小
	private static final int iconSize = 35;

	private Main app;

	private GameView gameView;
	// 游戏布局
	private int[][] map;

	private List<Point> path = new ArrayList<Point>();

	private RefreshHandler redrawHandler = new RefreshHandler();

	class RefreshHandler extends Handler {

		public static final int UPDATE_TXT = 0;

		public static final int UPDATE_IMAGE = 1;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 更新文本显示
			case UPDATE_TXT:
				TextView t = (TextView) app.findViewById(R.id.timing);
				t.setText("剩余时间：" + remainTime + "秒 帮助：" + help + "次");
				if (remainTime <= 0) {
					timer.cancel();
					app.setState(Main.STATE_LOSE);
				}
				break;
			// 更新视图
			case UPDATE_IMAGE:
				gameView.invalidate();
				if (win()) {
					timer.cancel();
					app.setState(Main.STATE_WIN);
				} else if (die()) {
					shuffle();
				}
				break;
			}
		}

		// 延时刷新视图
		public void sleep(long delayMillis) {
			this.removeMessages(0);
			Message msg = new Message();
			msg.what = UPDATE_IMAGE;
			sendMessageDelayed(msg, delayMillis);
		}
	};

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onClick(Point position) {
			List<Point> selected = gameView.getSelected();
			// 如果已选中一个，判断是否可消除，否则选中当前点击图片
			if (selected.size() == 1) {
				if (link(selected.get(0), position)) {
					selected.add(position);
					gameView.drawLine(path.toArray(new Point[] {})); // 画线
					redrawHandler.sleep(500); // 500ms后更新视图，消除连线和图片
				} else {
					// 不可消除则更新选中图片
					selected.clear();
					selected.add(position);
					gameView.invalidate();
				}
			} else {
				selected.add(position);
				gameView.invalidate();
			}
		}

	};
	// 总时间
	private static final long totalTime = 200;
	// 剩余时间
	private long remainTime;
	// 开始时间
	private long startTime;
	// 计时器
	private Timer timer;
	// 可用帮助次数
	private int help;

	class GameTimerTask extends TimerTask {

		@Override
		public void run() {
			// 更新剩余时间
			remainTime = totalTime
					- ((System.currentTimeMillis() - startTime) / 1000);
			Message msg = new Message();
			msg.what = RefreshHandler.UPDATE_TXT;
			redrawHandler.sendMessage(msg);
		}

	};

	// 开始游戏，初始化界面和布局
	public void startGame(Main m) {
		generateMap();
		help = 5;
		remainTime = totalTime;
		resume(m);
	}

	public void pause() {
		timer.cancel();
	}

	public void resume(Main m) {
		app = m;
		gameView = new GameView(app);
		LinearLayout gameViewParent = (LinearLayout) app.findViewById(R.id.GameView);
		gameView.setIconWidth(iconSize);
		gameView.setIconHeight(iconSize);
		gameView.setIcons(Main.icons);
		gameView.setOnItemClickListener(itemClickListener);
		gameView.setMap(map);
		gameViewParent.addView(gameView, ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		startTime = System.currentTimeMillis() - (totalTime - remainTime)
				* 1000;
		timer = new Timer();
		timer.schedule(new GameTimerTask(), 0, 100);
	}

	// 生成初始化布局
	private void generateMap() {
		int x = 1;
		int y = 0;
		map = new int[countX][countY];
		for (int i = 1; i < countX - 1; i++) {
			for (int j = 1; j < countY - 1; j++) {
				map[i][j] = x;
				if (y == 1) {
					x++;
					y = 0;
					if (x == Main.iconsCount + 1) {
						x = 1;
					}
				} else {
					y = 1;
				}
			}
		}
		shuffle();
	}

	// 随机交换
	private void shuffle() {
		Random random = new Random();
		int tmpV, tmpX, tmpY;
		for (int y = 1; y < countY - 1; y++) {
			for (int x = 1; x < countX - 1; x++) {
				tmpV = map[x][y];
				tmpX = random.nextInt(countX - 2) + 1;
				tmpY = random.nextInt(countY - 2) + 1;
				map[x][y] = map[tmpX][tmpY];
				map[tmpX][tmpY] = tmpV;
			}
		}
		if (die()) {
			shuffle();
		}
	}

	List<Point> p1E = new ArrayList<Point>();
	List<Point> p2E = new ArrayList<Point>();

	private boolean link(Point p1, Point p2) {
		if (p1.equals(p2)) {
			return false;
		}
		path.clear();
		if (map[p1.x][p1.y] == map[p2.x][p2.y]) {
			// 直连
			if (linkD(p1, p2)) {
				path.add(p1);
				path.add(p2);
				return true;
			}
			// 一折
			Point p = new Point(p1.x, p2.y);
			if (map[p.x][p.y] == 0) {
				if (linkD(p1, p) && linkD(p, p2)) {
					path.add(p1);
					path.add(p);
					path.add(p2);
					return true;
				}
			}
			p = new Point(p2.x, p1.y);
			if (map[p.x][p.y] == 0) {
				if (linkD(p1, p) && linkD(p, p2)) {
					path.add(p1);
					path.add(p);
					path.add(p2);
					return true;
				}
			}
			// 二折
			// 横向搜索
			expandH(p1, p1E);
			expandH(p2, p2E);
			// 判断是否可连
			for (Point pt1 : p1E) {
				for (Point pt2 : p2E) {
					if (pt1.x == pt2.x) {
						if (linkD(pt1, pt2)) {
							path.add(p1);
							path.add(pt1);
							path.add(pt2);
							path.add(p2);
							return true;
						}
					}
				}
			}
			// 纵向搜索
			expandV(p1, p1E);
			expandV(p2, p2E);
			// 判断是否可连
			for (Point pt1 : p1E) {
				for (Point pt2 : p2E) {
					if (pt1.y == pt2.y) {
						if (linkD(pt1, pt2)) {
							path.add(p1);
							path.add(pt1);
							path.add(pt2);
							path.add(p2);
							return true;
						}
					}
				}
			}
			return false;
		}
		return false;
	}

	// 判断是否能直连
	private boolean linkD(Point p1, Point p2) {
		if (p1.x == p2.x) {
			int y1 = Math.min(p1.y, p2.y);
			int y2 = Math.max(p1.y, p2.y);
			boolean flag = true;
			for (int y = y1 + 1; y < y2; y++) {
				if (map[p1.x][y] != 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				return true;
			}
		}
		if (p1.y == p2.y) {
			int x1 = Math.min(p1.x, p2.x);
			int x2 = Math.max(p1.x, p2.x);
			boolean flag = true;
			for (int x = x1 + 1; x < x2; x++) {
				if (map[x][p1.y] != 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}

	// 横向寻找可达的点
	private void expandH(Point p, List<Point> l) {
		l.clear();
		for (int x = p.x + 1; x < countX; x++) {
			if (map[x][p.y] != 0) {
				break;
			}
			l.add(new Point(x, p.y));
		}
		for (int x = p.x - 1; x >= 0; x--) {
			if (map[x][p.y] != 0) {
				break;
			}
			l.add(new Point(x, p.y));
		}
	}

	// 纵向寻找可达的点
	private void expandV(Point p, List<Point> l) {
		l.clear();
		for (int y = p.y + 1; y < countY; y++) {
			if (map[p.x][y] != 0) {
				break;
			}
			l.add(new Point(p.x, y));
		}
		for (int y = p.y - 1; y >= 0; y--) {
			if (map[p.x][y] != 0) {
				break;
			}
			l.add(new Point(p.x, y));
		}
	}

	// 判断是否有可连的点
	private boolean die() {
		for (int y = 1; y < countY - 1; y++) {
			for (int x = 1; x < countX - 1; x++) {
				if (map[x][y] != 0) {
					for (int j = y; j < countY - 1; j++) {
						if (j == y) {
							for (int i = x + 1; i < countX - 1; i++) {
								if (map[i][j] == map[x][y]
										&& link(new Point(x, y),
												new Point(i, j))) {
									return false;
								}
							}
						} else {
							for (int i = 1; i < countX - 1; i++) {
								if (map[i][j] == map[x][y]
										&& link(new Point(x, y),
												new Point(i, j))) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	// 判断是否已完全消除
	private boolean win() {
		for (int x = 0; x < countX; x++) {
			for (int y = 0; y < countY; y++) {
				if (map[x][y] != 0)
					return false;
			}
		}
		return true;
	}

	// 点帮助时自动消除
	public void autoErase() {
		if (help == 0)
			return;
		help--;
		die();
		List<Point> l = gameView.getSelected();
		l.clear();
		l.add(path.get(0));
		l.add(path.get(path.size() - 1));
		gameView.drawLine(path.toArray(new Point[] {}));
		redrawHandler.sleep(500);
	}
}
