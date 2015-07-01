package com.xmobilapp.linkup;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class Main extends Activity {
	/** Called when the activity is first created. */
	public static final int STATE_MENU = 0;

	public static final int STATE_GAME = 1;

	public static final int STATE_WIN = 2;

	public static final int STATE_LOSE = 3;

	public static final int STATE_PAUSE = 4;

	private static int state = STATE_MENU;

	private static GameController gameController = null;

	public static final int iconsCount = 27;

	public static Bitmap[] icons = new Bitmap[iconsCount];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadMenu();
	}

	private void loadMenu() {
		setContentView(R.layout.main);
		if (gameController == null) {
			gameController = new GameController();
			loadIcons();
		}
		final Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				state = STATE_GAME;
				setContentView(R.layout.game);
				gameController.startGame(Main.this);
			}

		});
		final Button exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Main.this.finish();
			}

		});
		final TextView title = (TextView) findViewById(R.id.title);
		if (state == STATE_WIN) {
			title.setText("恭喜您过关！");
			start.setText("再玩一遍");
		} else if (state == STATE_LOSE) {
			title.setText("英雄请重新来过！");
			start.setText("重试");
		}
		if (state == STATE_PAUSE) {
			start.setText("新游戏");
			Button resume = (Button) findViewById(R.id.resume);
			resume.setVisibility(View.VISIBLE);
			resume.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					state = STATE_GAME;
					setContentView(R.layout.game);
					gameController.resume(Main.this);
				}

			});
		}
	}

	private void loadIcons() {
		Resources r = getResources();
		for (int i = 0; i < icons.length; i++) {
			icons[i] = ((BitmapDrawable) r.getDrawable(R.drawable.i1 + i))
					.getBitmap();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (state == STATE_GAME) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_CENTER:
				gameController.autoErase();
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				gameController.pause();
				state = STATE_PAUSE;
				loadMenu();
				break;
			}
		}
		return true;
	}

	public void setState(int state) {
		this.state = state;
		if (state == STATE_WIN || state == STATE_LOSE) {
			loadMenu();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (state == STATE_GAME) {
			state = STATE_PAUSE;
			gameController.pause();
		}
	}

}