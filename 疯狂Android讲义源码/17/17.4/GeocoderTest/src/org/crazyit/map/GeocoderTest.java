package org.crazyit.map;

import org.crazyit.map.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class GeocoderTest extends Activity
	implements OnClickListener
{
	Button parseBn, reverseBn;
	EditText etLng, etLat, etAddress, etResult;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取界面中的可视化组件
		parseBn = (Button) findViewById(R.id.parse);
		reverseBn = (Button) findViewById(R.id.reverse);
		etLng = (EditText) findViewById(R.id.lng);
		etLat = (EditText) findViewById(R.id.lat);
		etAddress = (EditText) findViewById(R.id.address);
		etResult = (EditText) findViewById(R.id.result);
		parseBn.setOnClickListener(this);
		reverseBn.setOnClickListener(this);
	}

	@Override
	public void onClick(View source)
	{
		switch (source.getId())
		{
			// 单击了“解析”按钮
			case R.id.parse:
				String address = etAddress.getText().toString().trim();
				if (address.equals(""))
				{
					Toast.makeText(this, "请输入有效的地址"
						, Toast.LENGTH_LONG).show();
				}
				else
				{
					double[] results = ConvertUtil.getLocationInfo(address);
					etResult.setText(address + "的经度是："
						+ results[0] + "\n纬度是："
						+ results[1]);
				}
				break;
			// 单击了“反向解析”按钮
			case R.id.reverse:
				String lng = etLng.getText().toString().trim();
				String lat = etLat.getText().toString().trim();
				if (lng.equals("") || lat.equals(""))
				{
					Toast.makeText(this, "请输入有效的经度、纬度!"
						, Toast.LENGTH_LONG)
						.show();
				}
				else
				{
					String result = ConvertUtil.getAddress(
						Double.parseDouble(lng), Double.parseDouble(lat));
					etResult.setText("经度:" + lng + "、纬度:"
						+ lat + "的地址为:\n"	+ result);
				}
				break;
		}
	}
}