package com.yarin.android.Examples_05_19;

import java.util.Vector;
import android.graphics.Bitmap;

public class GifFrame
{	
	/* 保存gif中所有帧的向量 */
    private Vector frames;
    
    /* 当前播放的帧的索引 */
    private int index;

    public GifFrame() 
    {
    	frames = new Vector(1);
        index = 0;
    }
    
    /* 添加一帧 */
    public void addImage(Bitmap image) 
    {
    	frames.addElement(image);
    }

    /* 返回帧数 */
    public int size() 
    {
        return frames.size();
    }

    /* 得到当前帧的图片 */
    public Bitmap getImage() 
    {
        if (size() == 0) 
        {
            return null;
        } 
        else 
        {
            return (Bitmap) frames.elementAt(index);
        }
    }

    /* 下一帧 */
    public void nextFrame() 
    {
        if (index + 1 < size()) 
        {
            index++;
        } 
        else 
        {
            index = 0;
        }
    }
    
    /* 创建GifFrame */
    public static GifFrame CreateGifImage(byte abyte0[]) 
    {
        try 
        {
        	GifFrame GF = new GifFrame();
        	Bitmap image = null;
            GifDecoder gifdecoder = new GifDecoder(abyte0);
            for (; gifdecoder.moreFrames(); gifdecoder.nextFrame()) 
            {
                try 
                {
                    image = gifdecoder.decodeImage();
                    if (GF != null && image != null) 
                    {
                        GF.addImage(image);
                    }
                    continue;
                }
                catch (Exception e) 
                {
                	e.printStackTrace();
                }
                break;
            }
            gifdecoder.clear();
            gifdecoder = null;
            return GF;
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
            return null;
        }
    } 
}

