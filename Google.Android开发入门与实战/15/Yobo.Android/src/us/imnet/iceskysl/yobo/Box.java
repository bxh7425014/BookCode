package us.imnet.iceskysl.yobo;

import java.util.List;
import java.util.Vector;

public class Box {
	private String _id = null;
	private String _name = null;
	private String _created_date = null;
	private int _itemcount = 0;
	private List<Song> _itemlist;

	public Box() {
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
	
	public int getCount() {
		return _itemcount;
	}
	
	public String getCreated() {
		return _created_date;
	}

	public List<Song> getAllItems() {
		return _itemlist;
	}

	int getItemCount() {
		return _itemcount;
	}

	void setName(String name) {
		_name = name;
	}

	void setItemCount(int itemcount) {
		_itemcount = itemcount;
	}

	void setDate(String created_date) {
		_created_date = created_date;
	}

	public String getName() {
		return _name;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_id() {
		return _id;
	}

	String getDate() {
		return _created_date;
	}

	public String toString() {
		// limit how much text we display
		if (_name == null) {
			return "unkown";
		} else if (_name.length() > 42) {
			return _name.substring(0, 42) + "...";
		}else{
			return  _name + "(" + _itemcount + ")";
		}
 

	}

}
