package com.supermario.rabit;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class BellCreator {
	private List<Bell> bell_pool;
	private int bell_xs[] = {133, 175, 113, 75, 125, 43, 88, 63, 34, 121, 80, 153, 221, 317, 261, 365, 314, 370, 262, 143, 212, 107};
	private int x_index = 0;
	public BellCreator(Context context){
		bell_pool = new ArrayList<Bell>();
		for(int i = 0; i < 10; ++i)
			bell_pool.add(new Bell(context));
	}
	//´´½¨Áåîõ
	public Bell createBell(){
		Bell bell = bell_pool.remove(0);
		bell.setCenter_x(bell_xs[x_index%bell_xs.length]);
		++x_index;
		bell.setCenter_y(10);
		bell.setState(Bell.BELL_OK);
		return bell;
	}
	
	public void recycle(Bell bell){
		bell_pool.add(bell);
	}
	
	public void init(){
		x_index = 0;
	}
}
