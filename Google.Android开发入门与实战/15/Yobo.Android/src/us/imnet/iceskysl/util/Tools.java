package us.imnet.iceskysl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

//工具类
public class Tools {

	// 判断SharedPreferences中是否已经存储了acessToken
	public static String getSessionInPerence(Activity act) {
		SharedPreferences settings = act.getSharedPreferences(
				PreferencesUtil.preferencesSetting, 0);
		String session = settings.getString(PreferencesUtil.session, "");
		Log.i("getSessionInPerence", "session" + session);
		if (session.equals(""))
			return "";
		else
			return session;
	}

	// 构造用户注册URL
	public static String getRegUrl(String token) {
		String url = "";
		url = PreferencesUtil.baseUrl + PreferencesUtil.regUrl + "?token="
				+ token;
		return url;
	}

	// 构造用户授权URL
	public static String getAuthorizeUrl(String token) {
		String url = "";
		url = PreferencesUtil.baseUrl + PreferencesUtil.authorizeUrl
				+ "?token=" + token;
		return url;
	}

	// 解析RequestToken返回的XML，得到Token的指
	public static String paxToken(String xml) {
		String regEx = "<token>(.+?)</token>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(xml);
		boolean rs = m.find();
		if (rs) {
			System.out.println("开始位置是：" + m.start() + "结束位置是:" + m.end()
					+ "字串是:" + m.group());
		}
		return m.group(1);
	}

	// 解析获取session的返回数据
	public static String paxSession(String xml) {
		String regEx = "<name>(.+?)</name>.*?<key>(.+?)</key>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(xml);
		boolean rs = m.find();
		if (rs) {
			System.out.println("开始位置是：" + m.start() + "结束位置是:" + m.end()
					+ "字串是:" + m.group());
		}
		return m.group(2);
	}

	// MD5加密
	public static String md5(String s)  {
		String  md5_str = "";
		try{
			System.out.println(System.currentTimeMillis());
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(), 0, s.length());
		    md5_str = new BigInteger(1, m.digest()).toString(16);
			Log.i("MD5", md5_str);
			if (md5_str.length() < 32) {
				md5_str = "0".concat(md5_str);
			}
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5_str;
	}

	// 对需要传递的参数进行签名
	// 返回签名加密串
	public static String sig_params(Hashtable<String, ?> params) {
		Vector<String> v = new Vector<String>(params.keySet());
		Collections.sort(v);
		// Display (sorted) hashtable.
		String result = "";
		for (Enumeration<String> e = v.elements(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String val = (String) params.get(key);
			result += key;
			result += val;
			Log.i("sig_params", "Key: " + key + ",Val: " + val);
		}
		Log.i("sig_params", "result:" + result);
		return result;
	}

	/**
	 * Sends an HTTP GET request to a url
	 * 
	 * @param endpoint
	 *            - The URL of the server. (Example:
	 *            " http://www.yahoo.com/search")
	 * @param requestParameters
	 *            - all the request parameters (Example:
	 *            "param1=val1&param2=val2"). Note: This method will add the
	 *            question mark (?) to the request - DO NOT add it yourself
	 * @return - The response from the end point
	 */
	public static String sendGetRequest(String endpoint,
			String requestParameters) {
		Log.i("sendGetRequest", endpoint);
		String result = null;
		if (endpoint.startsWith("http://")) {
			// Send a GET request to the servlet
			try {
				// Construct data
				StringBuffer data = new StringBuffer();
				// Send data
				String urlStr = endpoint;
				if (requestParameters != null && requestParameters.length() > 0) {
					urlStr += "?" + requestParameters;
				}
				Log.i("urlStr", urlStr);
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				result = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.i("sendGetRequest", result);
		return result;
	}

	public static InputStream sendGetRequest2(String endpoint,
			String requestParameters) {
		Log.i("sendGetRequest", endpoint);
		InputStream is = null;
		if (endpoint.startsWith("http://")) {
			// Send a GET request to the servlet
			try {
				// Send data
				String urlStr = endpoint;
				if (requestParameters != null && requestParameters.length() > 0) {
					urlStr += "?" + requestParameters;
				}
				Log.i("urlStr", urlStr);
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				is = conn.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return is;
	}

	public static String paramsToString(Hashtable<String, ?> params) {
		Vector<String> v = new Vector<String>(params.keySet());
		Collections.sort(v);
		// Display (sorted) hashtable.
		String result = "";
		for (Enumeration<String> e = v.elements(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String val = (String) params.get(key);
			result += key;
			result += "=";
			result += val;
			result += "&";
			Log.i("sig_params", "Key: " + key + ",Val: " + val);
		}
		Log.i("sig_params", "result:" + result);
		return result;
	}

	// 显示网络上的图片
	public static Bitmap returnBitMap(String url) {
		Log.i("returnBitMap","url="+url);
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
