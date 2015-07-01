package net.blogjava.mobile.widget;

import net.blogjava.mobile.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabelEditText extends LinearLayout
{
	private TextView textView;
	private String labelText;
	private int labelFontSize;
	private String labelPosition;

	public LabelEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		//  读取labelText属性的资源ID
		int resourceId = attrs.getAttributeResourceValue(null, "labelText", 0);
		//  未获得资源ID，继续读取属性值
		if (resourceId == 0)
			labelText = attrs.getAttributeValue(null, "labelText");
		//  从资源文件中获得labelText属性的值
		else
			labelText = getResources().getString(resourceId);
		//  如果按两种方式都未获得labelTex属性的值，表示未设置该属性，抛出异常
		if (labelText == null)
		{
			throw new RuntimeException("必须设置labelText属性.");
		}
		//  获得labelFontSize属性的资源ID
		resourceId = attrs.getAttributeResourceValue(null, "labelFontSize", 0);
		//  继续读取labelFontSize属性的值，如果未设置该属性，将属性值设为14
		if (resourceId == 0)
			labelFontSize = attrs.getAttributeIntValue(null, "labelFontSize",
					14);
		//  从资源文件中获得labelFontSize属性的值
		else
			labelFontSize = getResources().getInteger(resourceId);
		//  获得labelPosition属性的资源ID
		resourceId = attrs.getAttributeResourceValue(null, "labelPosition", 0);
		//  继续读取labelPosition属性的值
		if (resourceId == 0)
			labelPosition = attrs.getAttributeValue(null, "labelPosition");
		//  从资源文件中获得labelPosition属性的值
		else
			labelPosition = getResources().getString(resourceId);
		//  如果未设置labelPosition属性值，将该属性值设为left
		if (labelPosition == null)
			labelPosition = "left";
		
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		//  获得LAYOUT_INFLATER_SERVICE服务
		li = (LayoutInflater) context.getSystemService(infService);
		LinearLayout linearLayout = null;
		//  根据labelPosition属性的值装载不同的布局文件
		if("left".equals(labelPosition))
			linearLayout = (LinearLayout)li.inflate(R.layout.labeledittext_horizontal, this);
		else if("top".equals(labelPosition))
			linearLayout = (LinearLayout)li.inflate(R.layout.labeledittext_vertical, this);
		else
			throw new RuntimeException("labelPosition属性的值只能是left或top.");
		
		//  下面的代码从相应的布局文件中获得了TextView对象，并根据LabelTextView的属性值设置TextView的属性
		textView = (TextView) findViewById(R.id.textview);
		textView.setTextSize((float)labelFontSize);			
		textView.setTextSize(labelFontSize);
		textView.setText(labelText);

	}

}
