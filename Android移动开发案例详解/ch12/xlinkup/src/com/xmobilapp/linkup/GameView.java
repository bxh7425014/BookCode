package com.xmobilapp.linkup;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	// 被选中的图片坐标
	private List<Point> selected = new ArrayList<Point>();
	// 图片宽度
	private int iconWidth;
	// 图片高度
	private int iconHeight;
	// 连线的折点
	private Point[] path = null;
	// 图片点击事件的监听者
	private OnItemClickListener mOnClickListener;
	// 游戏布局数据
	private int[][] map;
	// 图片在横向上的偏移值
	private int offsetX;
	// 图片在纵向上的偏移值
	private int offsetY;
	// 图片资源
	private Bitmap[] icons;

	interface OnItemClickListener {
		public void onClick(Point position);
	}

	public List<Point> getSelected() {
		return selected;
	}

	public void setSelected(List<Point> selected) {
		this.selected = selected;
	}

	public void setIcons(Bitmap[] icons) {
		this.icons = icons;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnClickListener = l;
	}

	public void setMap(int[][] map) {
		this.map = map;
		invalidate();
	}

	public GameView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (Point position : selected) {
			Paint paint = new Paint();
			paint.setColor(Color.YELLOW);
			paint.setStyle(Style.FILL);
			Point p = index2screen(position.x, position.y);
			canvas.drawRect(p.x, p.y, p.x + iconWidth, p.y + iconHeight, paint);
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] != 0) {
					Bitmap bitmap = icons[map[i][j] - 1];
					Point p = index2screen(i, j);
					canvas.drawBitmap(bitmap, null, new Rect(p.x, p.y, p.x
							+ iconWidth, p.y + iconHeight), null);
				}
			}
		}
		if (path != null && path.length >= 2) {
			for (int i = 0; i < path.length - 1; i++) {
				Paint paint = new Paint();
				paint.setARGB(255, 60, 60, 200);
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(2);
				Point p1 = index2screen(path[i].x, path[i].y);
				Point p2 = index2screen(path[i + 1].x, path[i + 1].y);
				canvas.drawLine(p1.x + iconWidth / 2, p1.y + iconHeight / 2,
						p2.x + iconWidth / 2, p2.y + iconHeight / 2, paint);
			}
			Point p = path[0];
			map[p.x][p.y] = 0;
			p = path[path.length - 1];
			map[p.x][p.y] = 0;
			selected.clear();
			path = null;
		}
	}

	private Point screen2index(int x, int y) {
		return new Point((x + offsetX) / iconWidth, (y + offsetY) / iconHeight);
	}

	private Point index2screen(int x, int y) {
		return new Point(x * iconWidth - offsetX, y * iconHeight - offsetY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}
		int x = (int) event.getX();
		int y = (int) event.getY();
		Point p = screen2index(x, y);
		if (map[p.x][p.y] != 0)
			mOnClickListener.onClick(p);
		return true;
	}

	public void drawLine(Point[] path) {
		this.path = path;
		this.invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
			offsetX = iconWidth - (right - left - (map.length - 2) * iconWidth)
					/ 2;
			offsetY = iconHeight
					- (bottom - top - (map[0].length - 2) * iconHeight) / 2;
		}
	}

}
