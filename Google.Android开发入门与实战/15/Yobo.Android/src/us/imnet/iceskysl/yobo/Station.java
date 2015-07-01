package us.imnet.iceskysl.yobo;

import java.util.List;
import java.util.Vector;

public class Station {
	private String _id = null;
	private String _name = null;
	private String _created_date = null;
	private int _itemcount = 0;
	private List<Song> _itemlist;

	public Station() {
		_itemlist = new Vector<Song>(0);
	}

	public int addItem(Song _item) {
		_itemlist.add(_item);
		_itemcount++;
		return _itemcount;
	}

	public Song getItem(int location) {
		return _itemlist.get(location);
	}

	public List<Song> getAllItems() {
		return _itemlist;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_id() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	void setName(String name) {
		_name = name;
	}

	public void setDate(String value) {
		// TODO Auto-generated method stub

	}

	public String toString() {
		// limit how much text we display
		if (_name == null) {
			return "unkown";
		} else if (_name.length() > 42) {
			return _name.substring(0, 42) + "...";
		} else {
			return _name ;
		}
	}

}
