package com.xmobileapp.utils;

import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class myMapview extends MapView{
	private List<GeoPoint> mListGeoPoint;
	public myMapview(Context context, String apiKey,List<GeoPoint> gp) {
		super(context, apiKey);
		mListGeoPoint = gp;
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		try
		{
			Projection projection = this.getProjection();
			if(mListGeoPoint.size()!=0)
			{
				if(mListGeoPoint.size()>1)
				{
					for(int ia =0;ia<mListGeoPoint.size()-1;ia++)
					{
						try
						{
							Point p1 = new Point();
							Point p2 = new Point();
							Point p0 = new Point();
							projection.toPixels(mListGeoPoint.get(0), p0);
							projection.toPixels(mListGeoPoint.get(ia), p1);
							projection.toPixels(mListGeoPoint.get(ia+1), p2);
							Paint paint = new Paint();
							paint.setColor(Color.RED);
							/*在起始点处画一个填充的红色小圆指示这里是你的起点*/
							canvas.drawCircle(p0.x, p0.y, 5, paint);
							/*连线临近的两个点*/
							canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
						}
						catch(Exception e)
						{
							e.toString();
						}
					}
				}
				else
				{
					Point p = new Point();
					projection.toPixels(mListGeoPoint.get(0), p);
					Paint paint = new Paint();
					paint.setColor(Color.RED);
					canvas.drawCircle(p.x, p.y, 5, paint);
				}
			}
		}
		catch(Exception e)
		{
			e.toString();
		}
	}
	/*重新设置列表*/
	public void setList(List<GeoPoint> gp)
	{
		mListGeoPoint = gp;
		this.invalidate();
	}
}
