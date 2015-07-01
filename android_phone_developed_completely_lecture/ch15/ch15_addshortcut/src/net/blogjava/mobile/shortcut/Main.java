package net.blogjava.mobile.shortcut;

import net.blogjava.mobile.compass.Compass;
import net.blogjava.mobile.wifi.Wifi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener
{
	@Override
	public void onClick(View view)
	{
		Intent intent = null;
		switch (view.getId())
		{
			case R.id.btnCompass:
				intent = new Intent(this, Compass.class);
				startActivity(intent);
				break;
			case R.id.btnWifi:
				intent = new Intent(this, Wifi.class);
				startActivity(intent);
				break;
			case R.id.btnShortCut:
				// 安装电子罗盘快捷方式
				Intent installShortCut = new Intent(
						"com.android.launcher.action.INSTALL_SHORTCUT");
				installShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "电子罗盘");
				Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
						R.drawable.compass_shortcut);
				installShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
						icon);
				Intent compassIntent = new Intent(
						"net.blogjava.mobile.compass.COMPASS", Uri
								.parse("compass://host"));
				installShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
						compassIntent);
				sendBroadcast(installShortCut);

				// 安装无线网络快捷方式
				installShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "无线网络");
				icon = Intent.ShortcutIconResource.fromContext(this,
						R.drawable.wifi_shortcut);
				installShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
						icon);
				Intent wifiIntent = new Intent("net.blogjava.mobile.wifi.WIFI",
						Uri.parse("wifi://host"));
				installShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
						wifiIntent);
				sendBroadcast(installShortCut);
				Toast.makeText(this, "快捷方式安装成功", Toast.LENGTH_SHORT).show();
				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnCompass = (Button) findViewById(R.id.btnCompass);
		Button btnWifi = (Button) findViewById(R.id.btnWifi);
		Button btnShortCut = (Button) findViewById(R.id.btnShortCut);
		btnCompass.setOnClickListener(this);
		btnWifi.setOnClickListener(this);
		btnShortCut.setOnClickListener(this);
	}
}