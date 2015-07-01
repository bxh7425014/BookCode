package net.blogjava.mobile.wifi;

import java.net.Inet4Address;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Main extends Activity implements
		OnCheckedChangeListener
{
	private WifiManager wifiManager;
	private WifiInfo wifiInfo;
	private CheckBox chkOpenCloseWifiBox;

	private List<WifiConfiguration> wifiConfigurations;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();
		chkOpenCloseWifiBox = (CheckBox) findViewById(R.id.chkOpenCloseWifi);
		TextView tvWifiConfigurations = (TextView) findViewById(R.id.tvWifiConfigurations);
		TextView tvWifiInfo = (TextView) findViewById(R.id.tvWifiInfo);		
		chkOpenCloseWifiBox.setOnCheckedChangeListener(this);
  

		if (wifiManager.isWifiEnabled())
		{
			chkOpenCloseWifiBox.setText("Wifi已开启");
			chkOpenCloseWifiBox.setChecked(true);
		}
		else
		{
			chkOpenCloseWifiBox.setText("Wifi已关闭");
			chkOpenCloseWifiBox.setChecked(false);
		}


		// 获得Wifi信息
		StringBuffer sb = new StringBuffer();
		sb.append("Wifi信息\n");
		sb.append("MAC地址：" + wifiInfo.getMacAddress() + "\n");
		sb.append("接入点的BSSID：" + wifiInfo.getBSSID() + "\n");

		sb.append("IP地址（int）：" + wifiInfo.getIpAddress() + "\n");
		sb.append("IP地址（Hex）：" + Integer.toHexString(wifiInfo.getIpAddress())
				+ "\n");
		sb.append("IP地址：" + ipIntToString(wifiInfo.getIpAddress()) + "\n");
		sb.append("网络ID：" + wifiInfo.getNetworkId() + "\n");
		tvWifiInfo.setText(sb.toString());

		// 得到配置好的网络
		wifiConfigurations = wifiManager.getConfiguredNetworks();
		tvWifiConfigurations.setText("已连接的无线网络\n");
		for (WifiConfiguration wifiConfiguration : wifiConfigurations)
		{
			tvWifiConfigurations.setText(tvWifiConfigurations.getText()
					+ wifiConfiguration.SSID + "\n");
		}
	}

	private String ipIntToString(int ip)
	{
		try
		{
			byte[] bytes = new byte[4];
			bytes[0] = (byte) (0xff & ip);
			bytes[1] = (byte) ((0xff00 & ip) >> 8);
			bytes[2] = (byte) ((0xff0000 & ip) >> 16);
			bytes[3] = (byte) ((0xff000000 & ip) >> 24);
			return Inet4Address.getByAddress(bytes).getHostAddress();
		}
		catch (Exception e)
		{
			return "";
		}

	}



	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (isChecked)
		{
			wifiManager.setWifiEnabled(true);
			chkOpenCloseWifiBox.setText("Wifi已开启");
		}
		else
		{
			wifiManager.setWifiEnabled(false);
			chkOpenCloseWifiBox.setText("Wifi已关闭");
		}
		
		
		
	}

}