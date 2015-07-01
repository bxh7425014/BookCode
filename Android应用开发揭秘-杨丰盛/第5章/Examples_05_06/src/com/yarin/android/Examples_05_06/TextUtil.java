/**
 * 实现文字自动换行
 * 自动翻页
 */
package com.yarin.android.Examples_05_06;

import java.util.Vector;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.view.KeyEvent;

public class TextUtil
{
	int		m_iTextPosX;	//绘制的x点
	int		m_iTextPosY;	//绘制的y点
	int		m_iTextWidth;	//绘制宽度
	int		m_iTextHeight;	//绘制高度

	int		m_iFontHeight;	//字体高度

	int		m_ipageLineNum;	//每一页显示的行数

	int		m_iTextBGColor; // 背景颜色
	int		m_iTextColor;	// 字体颜色
	int		m_iAlpha;		//Alpha值

	int		m_iRealLine;	// 字符串真实的行数
	int		m_iCurLine;		//当前行

	String	m_strText;		

	Vector	m_String;

	Paint	m_paint;

	int		m_iTextSize;


	public TextUtil()
	{
		m_paint = new Paint();
		m_String = new Vector();
	}


	public TextUtil(String strText, int x, int y, int w, int h, int bgcolor, int txetcolor, int a, int iTextSize)
	{
		m_paint = new Paint();
		m_String = new Vector();

		m_strText = strText;

		m_iTextPosX = x;
		m_iTextPosY = y;
		m_iTextWidth = w;
		m_iTextHeight = h;

		m_iTextBGColor = bgcolor;
		m_iTextColor = txetcolor;

		m_iTextSize = iTextSize;

		m_iAlpha = a;

	}

	/**
	 * 初始化
	 * @param strText	要显示的字符串
	 * @param x			x
	 * @param y			y
	 * @param w			w
	 * @param h			h	
	 * @param bgcolor	背景颜色
	 * @param txetcolor	文字的颜色
	 * @param a			Alpha
	 * @param iTextSize	字体大小
	 */
	public void InitText(String strText, int x, int y, int w, int h, int bgcolor, int txetcolor, int a, int iTextSize)
	{
		m_iCurLine = 0;
		m_ipageLineNum = 0;
		m_iRealLine = 0;
		m_strText = "";
		m_iTextPosX = 0;
		m_iTextPosY = 0;
		m_iTextWidth = 0;
		m_iTextHeight = 0;
		m_iTextBGColor = 0;
		m_iTextColor = 0;
		m_iTextSize = 0;
		m_iAlpha = 0;

		m_String.clear();

		SetText(strText);
		SetRect(x, y, w, h);
		SetBGColor(bgcolor);
		SetTextColor(txetcolor);
		SetFontSize(iTextSize);
		SetAlpha(a);

		SetPaint();

		GetTextIfon();
	}

	/**
	 * 设置Alpha
	 * @param a	Alpha值
	 */
	public void SetAlpha(int a)
	{
		m_iAlpha = a;
	}

	/**
	 * 对Paint属性的设置
	 */
	public void SetPaint()
	{
		m_paint.setARGB(m_iAlpha, Color.red(m_iTextColor), Color.green(m_iTextColor), Color.blue(m_iTextColor));
		m_paint.setTextSize(m_iTextSize);
	}

	/**
	 * 设置字体尺寸
	 * @param iTextSize
	 */
	public void SetFontSize(int iTextSize)
	{
		m_iTextSize = iTextSize;
	}

	/**
	 * 设置显示文本的区域
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void SetRect(int x, int y, int w, int h)
	{
		m_iTextPosX = x;
		m_iTextPosY = y;
		m_iTextWidth = w;
		m_iTextHeight = h;
	}

	/**
	 * 设置背景颜色
	 * @param bgcolor
	 */
	public void SetBGColor(int bgcolor)
	{
		m_iTextBGColor = bgcolor;
	}

	/**
	 * 设置字体颜色
	 * @param txetcolor
	 */
	public void SetTextColor(int txetcolor)
	{
		m_iTextColor = txetcolor;
	}

	/**
	 * 色绘制要显示的字符串
	 * @param strText
	 */
	public void SetText(String strText)
	{
		m_strText = strText;
	}

	/**
	 * 得到字符串的信息
	 * 包括：行数、页数等信息
	 * 内部调用
	 */
	public void GetTextIfon()
	{
		char ch;
		int w = 0;
		int istart = 0;
		FontMetrics fm = m_paint.getFontMetrics();
		
		m_iFontHeight = (int) Math.ceil(fm.descent - fm.top) + 2;

		m_ipageLineNum = m_iTextHeight / m_iFontHeight;

		for (int i = 0; i < m_strText.length(); i++)
		{
			ch = m_strText.charAt(i);
			float[] widths = new float[1];
			String srt = String.valueOf(ch);
			m_paint.getTextWidths(srt, widths);

			if (ch == '\n')
			{
				m_iRealLine++;
				m_String.addElement(m_strText.substring(istart, i));
				istart = i + 1;
				w = 0;
			}
			else
			{
				w += (int) (Math.ceil(widths[0]));
				if (w > m_iTextWidth)
				{
					m_iRealLine++;
					m_String.addElement(m_strText.substring(istart, i));
					istart = i;
					i--;
					w = 0;
				}
				else
				{
					if (i == (m_strText.length() - 1))
					{
						m_iRealLine++;
						m_String.addElement(m_strText.substring(istart, m_strText.length()));
					}
				}
			}
		}
	}

	/**
	 * 绘制字符串
	 * @param canvas
	 */
	public void DrawText(Canvas canvas)
	{
		for (int i = m_iCurLine, j = 0; i < m_iRealLine; i++, j++)
		{
			if (j > m_ipageLineNum)
			{
				break;
			}
			canvas.drawText((String) (m_String.elementAt(i)), m_iTextPosX, m_iTextPosY + m_iFontHeight * j, m_paint);
		}
	}

	/**
	 * 翻页等按键处理
	 * @param keyCode
	 * @param event
	 * @return
	 */
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
		{
			if (m_iCurLine > 0)
			{
				m_iCurLine--;
			}
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
		{
			if ((m_iCurLine + m_ipageLineNum) < (m_iRealLine - 1))
			{
				m_iCurLine++;
			}
		}
		return false;
	}
}
