package net.blogjava.mobile.wsclient;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener
{
    
	@Override
	public void onClick(View view)
	{
		EditText etProductName = (EditText)findViewById(R.id.etProductName);
		TextView tvResult = (TextView)findViewById(R.id.tvResult);
		String serviceUrl = "http://192.168.17.156:8080/axis2/services/SearchProductService?wsdl";
		String methodName = "getProduct";
		SoapObject request = new SoapObject("http://service", methodName);
		request.addProperty("productName", etProductName.getText().toString());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		HttpTransportSE ht = new HttpTransportSE(serviceUrl);		
		try
		{
			ht.call(null, envelope);
			if (envelope.getResponse() != null)
			{
				SoapObject soapObject = (SoapObject) envelope.getResponse();
				String result = "产品名称：" + soapObject.getProperty("name") + "\n";
				result += "产品数量：" + soapObject.getProperty("productNumber") + "\n";
				result += "产品价格：" + soapObject.getProperty("price");
				tvResult.setText(result);
				
			}
			else {
				tvResult.setText("无此产品.");
			}
		}
		catch (Exception e)
		{
			
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);

	}
}