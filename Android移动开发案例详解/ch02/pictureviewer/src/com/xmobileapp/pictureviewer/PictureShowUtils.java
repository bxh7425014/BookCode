/* 
 * [程序名称] Android 图片浏览器
 * [作者] xmobileapp团队
 * [使用说明] 请在SD卡上建立pic目录以存放图片
 * [参考资料] http://code.google.com/p/androidslideshow/ 
 * [开源协议] MIT License (http://www.opensource.org/licenses/mit-license.php)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.xmobileapp.pictureviewer;

import java.io.File;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

class PictureShowUtils {
	// 图片存储位置，SD卡的pic目录下。
    private String dirName = Environment.getExternalStorageDirectory().toString() + "/pic/";
	// 用于记录目录下图片名称的字符串数组
    String[] filenames = null;
    
	// 在构造函数中初始化filenames
    PictureShowUtils () {
		try{
			filenames = new File(dirName).list();
		}
		catch(Exception e){
			filenames = null;
		}
	}
	
	// 获取图片数量
	public int getCount () {
        if(filenames == null)
        	return 0;
        return filenames.length;
    }
    
	// 获取指定索引的图片
    public Bitmap getImageAt (int i) {
    	String path = dirName;    	
    	if(i>=filenames.length)
    		return null;
    	path += filenames[i];
    	// 使用BitmapFactory.decodeFile读取图片内容
    	Bitmap b = BitmapFactory.decodeFile(path);
    	return b;    	
    }
}
