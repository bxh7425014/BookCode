package us.imnet.iceskysl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import us.imnet.iceskysl.util.PreferencesUtil;
import us.imnet.iceskysl.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Auth extends Activity {
	public final String TAG = "Auth";
	// Identifiers for option menu items
	private static final int MENU_DONE = Menu.FIRST + 1;
	private static final int MENU_REDO = MENU_DONE + 1;
	
	private static final int MENU_BOX = MENU_REDO + 1;
	private static final int MENU_RADIO = MENU_BOX + 1;
	private static final int MENU_REF = MENU_RADIO + 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getSessionInPerence().equalsIgnoreCase("")) {
			setContentView(R.layout.auth);
			setTitle("Yobo: 用户授权");
			showAlertDialog();
		} else {
			setContentView(R.layout.uinfo);
			setTitle("Yobo: 用户信息");
			findViews();
			restorePrefs();
		}
	}

	private TextView nickname_filed;
	private TextView url_filed;
	private TextView created_date_filed;
	private TextView homepage_filed;
	private ImageView pic_filed;

	private void findViews() {
		Log.d(TAG, "find Views");
		nickname_filed = (TextView) findViewById(R.id.nickname);
		url_filed = (TextView) findViewById(R.id.url);
		created_date_filed = (TextView) findViewById(R.id.created_date);
		homepage_filed = (TextView) findViewById(R.id.homepage);
		pic_filed = (ImageView) findViewById(R.id.pic);
	}

	// Restore preferences
	private void restorePrefs() {
		SharedPreferences settings = getSharedPreferences(
				PreferencesUtil.preferencesSetting, 0);
		String nickname = settings.getString(PreferencesUtil.nickname, "");
		String created_date = settings.getString(PreferencesUtil.created_date,
				"");
		String loved_singers = settings.getString(
				PreferencesUtil.loved_singers, "");
		String homepage = settings.getString(PreferencesUtil.homepage, "");
		String pic = settings.getString(PreferencesUtil.image, "");

		if (nickname != "") {
			nickname_filed.setTextSize(18);
			nickname_filed.setText("昵称:" + nickname);
		}
		if (loved_singers != "") {
			url_filed.setTextSize(18);
			url_filed.setText("喜欢歌手:" + loved_singers);
		}
		if (created_date != "") {
			created_date_filed.setTextSize(18);
			created_date_filed.setText("注册时间:" + created_date);
		}
		if (homepage != "") {
			homepage_filed.setTextSize(18);
			created_date_filed.setText("个人主页:" + homepage);
		}
		if (pic != "") {
			pic_filed.setImageBitmap(Tools.returnBitMap(pic));
		}
	}

	// 获取用户鉴权后的访问码：session
	public String getSession(String token) throws GeneralSecurityException {
		String session = "";
		// 1.构造需要的数据
		long timestamp = System.currentTimeMillis();
		String nonce = Tools.md5(String.valueOf(System.currentTimeMillis())
				+ PreferencesUtil.api_secret);
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("api_key", PreferencesUtil.api_key);
		params.put("nonce", nonce);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("token", token);
		// 2.对参数进行签名
		String api_sig = Tools.md5(Tools.sig_params(params)
				+ PreferencesUtil.api_secret);
		// 4.转换参数格式
		String params_str = Tools.paramsToString(params) + "api_sig=" + api_sig;
		// 5.提交数据，获得RequestToken反馈
		String url_get = PreferencesUtil.baseUrl + PreferencesUtil.sessionUrl;
		Log.i("url_get", url_get);
		String result = Tools.sendGetRequest(url_get, params_str);
		// 6.对反馈的XML进行解析，得到requestToken的值
		session = Tools.paxSession(result);
		Log.i(TAG, "session=" + session);
		// 7.把这个值存起来
		SharedPreferences settings = getSharedPreferences(
				PreferencesUtil.preferencesSetting, 0);
		settings.edit().putString(PreferencesUtil.session, session).commit();
		return session;
	}

	// 获取访问码
	private String getToken() throws NoSuchAlgorithmException {
		String token = "";
		// 1.构造需要的数据
		long timestamp = System.currentTimeMillis();
		String nonce = Tools.md5(String.valueOf(System.currentTimeMillis())
				+ PreferencesUtil.api_secret);
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("api_key", PreferencesUtil.api_key);
		params.put("nonce", nonce);
		params.put("timestamp", String.valueOf(timestamp));

		// 2.对参数进行签名
		String api_sig = Tools.md5(Tools.sig_params(params)
				+ PreferencesUtil.api_secret);
		// 3.添加api_sig
		// params.put("api_sig",api_sig);
		// 4.转换参数格式
		String params_str = Tools.paramsToString(params) + "api_sig="
				+ api_sig;
		// 5.提交数据，获得RequestToken反馈
		String url_get = PreferencesUtil.baseUrl + PreferencesUtil.tokenUrl;
		Log.i("url_get", url_get);
		String result = Tools.sendGetRequest(url_get, params_str);
		// 6.对反馈的XML进行解析，得到requestToken的值
		token = Tools.paxToken(result);
		// 7.把这个值存起来
		SharedPreferences settings = getSharedPreferences(
				PreferencesUtil.preferencesSetting, 0);
		settings.edit().putString(PreferencesUtil.token, token).commit();
		// // 8.构造用户验证的URL
		// url = PreferencesUtil.baseUrl + PreferencesUtil.authorizeUrl
		// + "?token=" + token;

		return token;
	}

	// 显示对话框
	private void showAlertDialog() {
		final AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("配置");
		alert.setMessage(getString(R.string.app_start));
		alert.setButton("授权", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				alert.dismiss();
				try {
					openWebBrowser(Tools.getAuthorizeUrl(getToken()));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
		});
		alert.setButton2("注册", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				alert.dismiss();
				try {
					openWebBrowser(Tools.getRegUrl(getToken()));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
		});
		alert.show();
	}

	// 判断SharedPreferences中是否已经存储了acessToken
	private String getSessionInPerence() {
		SharedPreferences settings = getSharedPreferences(
				PreferencesUtil.preferencesSetting, 0);
		String session = settings.getString(PreferencesUtil.session, "");
		if (session.equals(""))
			return "";
		else
			return session;
	}

	// 判断SharedPreferences中是否已经存储了acessToken
	private String getTokenInPerence() {
		Log.i(TAG, "getTokenInPerence..");
		SharedPreferences settings = getSharedPreferences(
				PreferencesUtil.preferencesSetting, 0);
		String token = settings.getString(PreferencesUtil.token, "");
		if (token.equals(""))
			return "";
		else
			return token;
	}

	// 打开浏览器，定位到指定的地址
	protected void openWebBrowser(String url) {
		Log.i(TAG, "about to launch browser, url: " + url);
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
		startActivityForResult(i, 0);
	}

	// 初始化菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (getSessionInPerence().equalsIgnoreCase("")) {
			menu.add(0, MENU_DONE, 0, R.string.menu_done).setIcon(
					R.drawable.menu_start).setAlphabeticShortcut('S');
			menu.add(0, MENU_REDO, 0, R.string.menu_redo).setIcon(
					R.drawable.menu_helps).setAlphabeticShortcut('H');
		} else {
			menu.add(0, MENU_BOX, 0, R.string.menu_box).setIcon(
					R.drawable.music_box).setAlphabeticShortcut('B');
			menu.add(0, MENU_RADIO, 0, R.string.menu_radio).setIcon(
					R.drawable.music_radio).setAlphabeticShortcut('R');
			menu.add(0, MENU_REF, 0, R.string.menu_ref).setIcon(
					R.drawable.refresh).setAlphabeticShortcut('R');
		}

		return true;
	}

	// 当一个菜单被选中的时候调用
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case MENU_DONE:
			try {
				String session = getSessionInPerence();
				if (session == "") {
					session = getSession(getTokenInPerence());
				}
				updateUserInfos(session);
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		case MENU_REDO:
			openWebBrowser(Tools.getAuthorizeUrl(getTokenInPerence()));
			break;
		case MENU_BOX:
			//我的音乐盒里列表
			intent.setClass(Auth.this, BoxActivty.class);
			startActivity(intent);
			return true;
		case MENU_RADIO:
			//我的电台列表
			intent.setClass(Auth.this, StationActivty.class);
			startActivity(intent);
			break;
		}
		return true;
		// return super.onOptionsItemSelected(item);
		
	}

	// 取用户的个人信息
	public void updateUserInfos(String session) {
		// 1.构造需要的数据
		long timestamp = System.currentTimeMillis();
		String nonce = "";
		nonce = Tools.md5(String.valueOf(System.currentTimeMillis())
				+ PreferencesUtil.api_secret);
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("api_key", PreferencesUtil.api_key);
		params.put("nonce", nonce);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("token", session);
		// 2.对参数进行签名
		String api_sig = "";
		api_sig = Tools.md5(Tools.sig_params(params)
				+ PreferencesUtil.api_secret);
		// 4.转换参数格式
		String params_str = Tools.paramsToString(params) + "api_sig=" + api_sig;
		// 5.提交数据，获得RequestToken反馈
		String url_get = PreferencesUtil.baseUrl + PreferencesUtil.user_info;
		Log.i("url_get", url_get);
//		InputStream result = Tools.sendGetRequest2(url_get, params_str);
		
		// Tools.parUinfo(url_get+"?id=825856");
		String result = Tools.sendGetRequest(url_get, params_str);
		Log.i(TAG, "result:" + result);
		// Tools.parUinfo(result);
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(result.getBytes());
			Document doc = dombuilder.parse(is);
			Element root = doc.getDocumentElement();
			System.out.println(root.getAttributes().getNamedItem("status")
					.getNodeValue());
			NodeList users = root.getChildNodes();
			if (users != null) {
				SharedPreferences settings = getSharedPreferences(
						PreferencesUtil.preferencesSetting, 0);
				Editor editor = settings.edit();
				for (int i = 0; i < users.getLength(); i++) {
					Node book = users.item(i);
					if (book.getNodeType() == Node.ELEMENT_NODE) {
						for (Node node = book.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								String value = node.getFirstChild()
										.getNodeValue();
								System.out.println(node.getNodeName() + ":"
										+ value);
								if (node.getNodeName().trim().equalsIgnoreCase(
										"nickname")) {
									editor.putString(PreferencesUtil.nickname,
											value);
								}
								if (node.getNodeName().equalsIgnoreCase(
										"loved_singers")) {
									editor.putString(
											PreferencesUtil.loved_singers,
											value);
								}
								if (node.getNodeName().equalsIgnoreCase(
										"created_date")) {
									editor
											.putString(
													PreferencesUtil.created_date,
													value);
								}
								if (node.getNodeName()
										.equalsIgnoreCase("image")) {
									editor.putString(PreferencesUtil.image,
											value);
								}
								if (node.getNodeName().equalsIgnoreCase(
										"homepage")) {
									editor.putString(PreferencesUtil.homepage,
											value);
								}

							}
						}
					}
				}
				editor.commit();
				restorePrefs();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
