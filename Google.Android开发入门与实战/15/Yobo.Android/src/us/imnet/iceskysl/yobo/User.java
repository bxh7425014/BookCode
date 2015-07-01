package us.imnet.iceskysl.yobo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import us.imnet.iceskysl.util.PreferencesUtil;
import us.imnet.iceskysl.util.Tools;

public class User {
	public final String TAG = "User";
	private String _nickname = null;
	private String _created_date = null;
	private String _homepage = null;
	private String _love_sigers = null;
	private int _boxes_count = 0;
	private int _stations_count = 0;
	private List<Box> _boxes_list;
	private List<Station> _stations_list;

	// public int addBoxMap(Map _item) {
	// _boxes_list_name.add(_item);
	// }

	public User() {
		_boxes_list = new Vector<Box>(0);
		_stations_list = new Vector<Station>(0);
	}

	// 按照这个session读取用户的音乐盒和电台以及朋友列表
	public User(String session) {
		_boxes_list = new Vector<Box>(0);
		_stations_list = new Vector<Station>(0);

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
		String url_get = PreferencesUtil.baseUrl + PreferencesUtil.user_boxes;
		Log.i("url_get", url_get);
		InputStream result = Tools.sendGetRequest2(url_get, params_str);
		// Log.i("result", result);
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			Document doc = dombuilder.parse(result);
			Element root = doc.getDocumentElement();
			System.out.println(root.getAttributes().getNamedItem("status")
					.getNodeValue());
			NodeList boxes = root.getElementsByTagName("box");
			if (boxes != null) {
				for (int i = 0; i < boxes.getLength(); i++) {
					Node box = boxes.item(i);
					Log.i(TAG, String.valueOf(box.getNodeType()));

					if (box.getNodeType() == Node.ELEMENT_NODE) {
						Box box_item = new Box();
						for (Node node = box.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							String nodeName = node.getNodeName();
							Log.i(TAG + "2", nodeName);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								String value = null;

								NodeList nlChilds = node.getChildNodes();
								for (int j = nlChilds.getLength() - 1; j >= 0; --j) {
									Node node3 = nlChilds.item(j);
									String value2 = node3.getNodeValue();
									if (value2 == null
											|| value2.trim().equals("")
											|| value2.trim().equals("\r")) {
										continue; // keep iterating
									} else {
										value = value2;
										break; // found the firstName!
									}
								}
								if (node.getNodeName().trim().equalsIgnoreCase(
										"id")) {
									box_item.set_id(value);
								}
								if (node.getNodeName().trim().equalsIgnoreCase(
										"name")) {
									box_item.setName(value);
								}
								if (node.getNodeName().equalsIgnoreCase(
										"songs_count")) {
									box_item.setItemCount(Integer
											.parseInt(value));
								}
								if (node.getNodeName().equalsIgnoreCase(
										"created_date")) {
									box_item.setDate(value);
								}
								Log.i("Box", node.getNodeName() + ":" + value);

							}
						}
						this.addBox(box_item);
					}
				}
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

	// 初始化用户电台
	public User(String session, String stations) {
		_boxes_list = new Vector<Box>(0);
		_stations_list = new Vector<Station>(0);
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
		// String url_get = "http://www.imnet.us/stations.xml";
		String url_get = PreferencesUtil.baseUrl
				+ PreferencesUtil.user_stations;
		InputStream result = Tools.sendGetRequest2(url_get, params_str);
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			Document doc = dombuilder.parse(result);
			Element root = doc.getDocumentElement();
			// System.out.println(root.getAttributes().getNamedItem("station")
			// .getNodeValue());
			NodeList stations1 = root.getElementsByTagName("station");
			if (stations1 != null) {
				for (int i = 0; i < stations1.getLength(); i++) {
					Node box = stations1.item(i);
					Log.i(TAG, String.valueOf(box.getNodeType()));

					if (box.getNodeType() == Node.ELEMENT_NODE) {
						Station station_item = new Station();
						for (Node node = box.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							String nodeName = node.getNodeName();
							Log.i(TAG + "2", nodeName);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								String value = null;

								NodeList nlChilds = node.getChildNodes();
								for (int j = nlChilds.getLength() - 1; j >= 0; --j) {
									Node node3 = nlChilds.item(j);
									String value2 = node3.getNodeValue();
									if (value2 == null
											|| value2.trim().equals("")
											|| value2.trim().equals("\r")) {
										continue; // keep iterating
									} else {
										value = value2;
										break; // found the firstName!
									}
								}
								if (node.getNodeName().trim().equalsIgnoreCase(
										"id")) {
									station_item.set_id(value);
								}
								if (node.getNodeName().trim().equalsIgnoreCase(
										"name")) {
									station_item.setName(value);
								}
								if (node.getNodeName().equalsIgnoreCase(
										"created_time")) {
									station_item.setDate(value);
								}
								Log.i("Station", node.getNodeName() + ":"
										+ value);

							}
						}
						this.addStation(station_item);
					}
				}
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

	public int addBox(Box _item) {
		_boxes_list.add(_item);
		_boxes_count++;
		return _boxes_count;
	}

	public int addStation(Station _item) {
		_stations_list.add(_item);
		_stations_count++;
		return _stations_count;
	}

	public Box getBox(int location) {
		return _boxes_list.get(location);
	}

	public Station getStation(int location) {
		return _stations_list.get(location);
	}

	public List<Box> getAllBoxes() {
		return _boxes_list;
	}

	public List<Station> getAllStations() {
		return _stations_list;
	}

	int getBoxCount() {
		return _boxes_count;
	}

	void setName(String nickname) {
		_nickname = nickname;
	}

	String getName() {
		return _nickname;
	}

	String getHomepage() {
		return _homepage;
	}

	void setHomepage(String homepage) {
		_homepage = homepage;
	}

	String getLoveSingers() {
		return _love_sigers;
	}

	void setLoveSingers(String love_sigers) {
		_love_sigers = love_sigers;
	}

	public void set_created_date(String _created_date) {
		this._created_date = _created_date;
	}

	public String get_created_date() {
		return _created_date;
	}

}
