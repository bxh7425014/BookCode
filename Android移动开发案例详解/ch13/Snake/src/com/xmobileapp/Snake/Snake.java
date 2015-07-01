/* 
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xmobileapp.Snake;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Snake: a simple game that everyone can enjoy.
 * 
 * This is an implementation of the classic Game "Snake", in which you control a
 * serpent roaming around the garden looking for apples. Be careful, though,
 * because when you catch one, not only will you become longer, but you'll move
 * faster. Running into yourself or the walls will end the game.
 * 
 */
public class Snake extends Activity implements OnClickListener {

	private final static int PLAY = 1;
	
	private final static int LEFT = 2;
	
	private final static int RIGHT= 3;
	
	private final static int UP= 4;
	
	private final static int DOWN= 5;
	
    private SnakeView mSnakeView;
    
    private static String ICICLE_KEY = "snake-view";

    private Button play;
    
    private ImageButton left;
    
    private ImageButton right;
    
    private ImageButton up;
    
    private ImageButton down;
    
    private UpdateStatus updateStatus;
    
    private Handler handler;
    
    protected static final int GUINOTIFIER = 0x1234;

    /**
     * Called when Activity is first created. Turns off the title bar, sets up
     * the content views, and fires up the SnakeView.
     * 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        // No Title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.snake_layout);

        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setTextView((TextView) findViewById(R.id.text));
        play = (Button)findViewById(R.id.play);
        play.setId(PLAY);
        play.setOnClickListener(this);
        play.setBackgroundColor(Color.argb(0, 0, 255, 0));
        left = (ImageButton)findViewById(R.id.left);
        left.setId(LEFT);
        left.setOnClickListener(this);
        left.setBackgroundColor(Color.argb(1, 1, 255, 1));
        left.setVisibility(View.GONE);
        
        right = (ImageButton)findViewById(R.id.right);
        right.setId(RIGHT);
        right.setOnClickListener(this);
        right.setBackgroundColor(Color.argb(1, 1, 255, 1));
        right.setVisibility(View.GONE);
        
        up = (ImageButton)findViewById(R.id.up);
        up.setId(UP);
        up.setOnClickListener(this);
        up.setBackgroundColor(Color.argb(1, 1, 255, 1));
        up.setVisibility(View.GONE);
        
        down = (ImageButton)findViewById(R.id.down);
        down.setId(DOWN);
        down.setOnClickListener(this);
        down.setBackgroundColor(Color.argb(1, 1, 255, 1));
        down.setVisibility(View.GONE);
        
        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mSnakeView.setMode(mSnakeView.READY);
        } else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }
        
        handler = new Handler()
        {
          public void handleMessage(Message msg) 
          {
        
            switch (msg.what)
            { 
              case Snake.GUINOTIFIER:
                       
                play.setVisibility(View.VISIBLE);
                left.setVisibility(View.GONE);
                right.setVisibility(View.GONE);
                up.setVisibility(View.GONE);
                down.setVisibility(View.GONE);
                break; 
            } 
            super.handleMessage(msg); 
          }
        };

        

        
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mSnakeView.setMode(SnakeView.PAUSE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case PLAY: 
                play.setVisibility(View.GONE);
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                up.setVisibility(View.VISIBLE);
                down.setVisibility(View.VISIBLE);
            if (mSnakeView.mMode == mSnakeView.READY | mSnakeView.mMode == mSnakeView.LOSE) {
                /*
                 * At the beginning of the game, or the end of a previous one,
                 * we should start a new game.
                 */
                mSnakeView.initNewGame();
                mSnakeView.setMode(mSnakeView.RUNNING);
                mSnakeView.update();
                updateStatus = new UpdateStatus();                
                updateStatus.start();
                break;
            }

            if (mSnakeView.mMode == mSnakeView.PAUSE) {
                /*
                 * If the game is merely paused, we should just continue where
                 * we left off.
                 */
                mSnakeView.setMode(mSnakeView.RUNNING);
                mSnakeView.update();
       
                break;
            }

            if (mSnakeView.mDirection != mSnakeView.SOUTH) {
                mSnakeView.mNextDirection = mSnakeView.NORTH;

                break;
            }
            
			
			break;
			
		case LEFT:	
		
            if (mSnakeView.mDirection != mSnakeView.EAST) {
                mSnakeView.mNextDirection = mSnakeView.WEST;
            }
		    break;
	  	    
		case RIGHT:	
			
            if (mSnakeView.mDirection != mSnakeView.WEST) {
                mSnakeView.mNextDirection = mSnakeView.EAST;
            }
		    break;  
		case UP:	
			
            if (mSnakeView.mDirection != mSnakeView.SOUTH) {
                mSnakeView.mNextDirection = mSnakeView.NORTH;
            }
		    break;      
		  
		case DOWN:	
			
            if (mSnakeView.mDirection != mSnakeView.NORTH) {
                mSnakeView.mNextDirection = mSnakeView.SOUTH;
            }
		    break;    
		    
	    default :
	    	
	    	break;
		    
		}
	}
	

    class UpdateStatus extends Thread{   	
    	@Override
		public void run() {
             
    		
    		super.run();
    		
    		
    
    			
    		  while(true){
    			  
    		   if(mSnakeView.mMode == mSnakeView.LOSE){
    			   
    			   
    			   
    	           Message m = new Message();
    	           m.what = Snake.GUINOTIFIER;
    	           Snake.this.handler.sendMessage(m);

    			   break;
    			   
    		      }	
        
                   
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    		 }
    	
    	}	
    		
    }
    
}
