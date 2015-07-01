package net.blogjava.mobile;

import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try
		{
			/*ServerSocket serverSocket = new ServerSocket(1234);
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
	        while (nis.hasMoreElements())
	            Log.d("a", nis.nextElement().toString());*/
			Socket socket= new Socket("192.168.17.156", 1234);
			setTitle(socket.getInetAddress().toString());
			
		}
		catch (Exception e)
		{
			setTitle(e.getMessage());
		}

	}
}