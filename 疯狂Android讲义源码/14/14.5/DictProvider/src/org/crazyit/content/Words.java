/**
 * 
 */
package org.crazyit.content;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public final class Words
{
	// 定义该ContentProvider的Authority
	public static final String AUTHORITY 
		= "org.crazyit.providers.dictprovider";
	//定义一个静态内部类
	public static final class Word implements BaseColumns
	{
		// 定义Content所允许操作的3个数据列
		public final static String _ID = "_id";
		public final static String WORD = "name";
		public final static String DETAIL = "detail";
		// 定义该Content提供服务的两个Uri
		public final static Uri DICT_CONTENT_URI = 
			Uri.parse("content://" +  AUTHORITY + "/words");
		public final static Uri WORD_CONTENT_URI = 
			Uri.parse("content://" +  AUTHORITY + "/word");		
	}
}
