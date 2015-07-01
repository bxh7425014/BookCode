package net.blogjava.mobile.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnOpenBluetooth:
				// Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				// startActivityForResult(enableIntent, 0x1);

				bluetoothAdapter.enable();
				break;
			case R.id.btnCloseBluetooth:
				bluetoothAdapter.disable();
				break;
			case R.id.btnDiscoveryBluetooth:
                Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				startActivityForResult(discoveryIntent, 0x2);
				break;
			case R.id.btnSearchBluetooth:
				Intent searchIntent = new Intent(this, DiscoveryActivity.class);
				startActivity(searchIntent);
				break;
			case R.id.btnClientSocket:
				Intent clientSocketIntent = new Intent(this, ClientSocketActivity.class);
				startActivity(clientSocketIntent);
				break;
			case R.id.btnServerSocket:
				Intent serverSocketIntent = new Intent(this, ServerSocketActivity.class);
				startActivity(serverSocketIntent);
				break;
			case R.id.btnOBEX:
				Intent obexIntent = new Intent(this, OBEXActivity.class);
				startActivity(obexIntent);
				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnOpenBluetooth = (Button) findViewById(R.id.btnOpenBluetooth);
		Button btnCloseBluetooth = (Button) findViewById(R.id.btnCloseBluetooth);
		Button btnDiscoveryBluetooth = (Button) findViewById(R.id.btnDiscoveryBluetooth);
		Button btnSearchBluetooth = (Button) findViewById(R.id.btnSearchBluetooth);
		Button btnClientSocket = (Button) findViewById(R.id.btnClientSocket);
		Button btnServerSocket = (Button) findViewById(R.id.btnServerSocket);
		Button btnOBEX = (Button) findViewById(R.id.btnOBEX);
		btnOpenBluetooth.setOnClickListener(this);
		btnCloseBluetooth.setOnClickListener(this);
		btnDiscoveryBluetooth.setOnClickListener(this);
		btnSearchBluetooth.setOnClickListener(this);
		btnClientSocket.setOnClickListener(this);
		btnServerSocket.setOnClickListener(this);
		btnOBEX.setOnClickListener(this);
	}
}