package net.blogjava.mobile.complex.type.aidl;


import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service
{ 
	
	public class MyServiceImpl extends IMyService.Stub
	{

		@Override
		public Product getProduct() throws RemoteException
		{
			
			Product product = new Product();
			product.setId(1234);
			product.setName("Æû³µ");
			product.setPrice(31000); 
			return product;
		}

		@Override
		public Map getMap(String country, Product product)
				throws RemoteException
		{
			Map map = new HashMap<String, String>();
			map.put("country", country);
			map.put("id", product.getId());
			map.put("name", product.getName());
			map.put("price", product.getPrice());
			map.put("product", product);
			return map;
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{		
		return new MyServiceImpl();
	}

	
}
