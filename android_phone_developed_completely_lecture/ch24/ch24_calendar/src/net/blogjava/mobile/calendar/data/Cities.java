package net.blogjava.mobile.calendar.data;

import java.util.HashMap;
import java.util.Map;

public class Cities
{
	public static Map<String, String[]> cities = new HashMap<String, String[]>();
	public static Map<String, String> especialCities = new HashMap<String, String>();
	static
	{
		//////////////////////
		especialCities.put("朝阳", "/wap/54324/h24/");
		//////////////////////
		
		String[] hljCities = new String[]
		{ "哈尔滨", "齐齐哈尔", "鸡西", "鹤岗", "双鸭山", "大庆", "伊春", "佳木斯", "七台河", "牡丹江",
				"黑河", "绥化", "大兴安岭" };
		cities.put("黑龙江", hljCities);
		String[] jlCities = new String[]
		{ "长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城", "延边" };
		cities.put("吉林", jlCities);

		String[] lnCities = new String[]
		{ "沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦",
				"铁岭", "朝阳", "葫芦岛" };
		cities.put("辽宁", lnCities);

		String[] sdCities = new String[]
		{ "济南", "青岛", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照",
				"莱芜", "临沂", "德州", "聊城", "滨州", "菏泽" };
		cities.put("山东", sdCities);

		String[] sxCities = new String[]
		{ "太原", "大同", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "忻州", "临汾", "吕梁" };
		cities.put("山西", sxCities);

		String[] sx1Cities = new String[]
		{ "西安", "铜川", "宝鸡", "咸阳", "渭南", "延安", "汉中", "榆林", "安康", "商洛" };
		cities.put("陕西", sx1Cities);

		String[] hbCities = new String[]
		{ "石家庄", "唐山", "秦皇岛", "邯郸", "邢台", "保定", "张家口", "承德", "沧州", "廊坊", "衡水" };
		cities.put("河北", hbCities);

		String[] hnCities = new String[]
		{ "郑州", "开封", "洛阳", "平顶山", "安阳", "鹤壁", "新乡", "焦作", "济源", "濮阳", "许昌",
				"漯河", "三门峡", "南阳", "商丘", "信阳", "周口", "驻马店" };
		cities.put("河南", hnCities);

		String[] hb1Cities = new String[]
		{ "武汉", "黄石", "十堰", "宜昌", "襄樊", "鄂州", "荆门", "孝感", "荆州", "黄冈", "咸宁",
				"随州", "恩施", "仙桃", "潜江", "天门", "神农架" };
		cities.put("湖北", hb1Cities);

		String[] hn1Cities = new String[]
		{ "长沙", "株洲", "湘潭", "衡阳", "邵阳", "岳阳", "常德", "张家界", "益阳", "郴州", "永州",
				"怀化", "娄底", "湘西" };
		cities.put("湖南", hn1Cities);
		String[] jsCities = new String[]
		{ "南京", "无锡", "徐州", "常州", "苏州", "南通", "连云港", "淮安", "盐城", "扬州", "镇江",
				"泰州", "宿迁" };
		cities.put("江苏", jsCities);

		String[] jxCities = new String[]
		{ "南昌", "景德镇", "萍乡", "九江", "新余", "鹰潭", "赣州", "吉安", "宜春", "抚州", "上饶" };
		cities.put("江西", jxCities);

		String[] gdCities = new String[]
		{ "广州", "韶关", "深圳", "珠海", "汕头", "佛山", "江门", "湛江", "茂名", "肇庆", "惠州",
				"梅州", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮" };
		cities.put("广东", gdCities);

		String[] gxCities = new String[]
		{ "南宁", "柳州", "桂林", "梧州", "北海", "防城港", "钦州", "贵港", "玉林", "百色", "贺州",
				"河池", "来宾", "崇左" };
		cities.put("广西", gxCities);

		String[] ynCities = new String[]
		{ "昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "思茅", "临沧", "楚雄", "红河", "普洱",
				"文山", "大理", "德宏", "怒江" };
		cities.put("云南", ynCities);
		String[] gzCities = new String[]
		{ "贵阳", "六盘水", "遵义", "安顺", "铜仁", "毕节", "晴隆", "都匀", "凯里" };
		cities.put("贵州", gzCities);

		String[] scCities = new String[]
		{ "成都", "自贡", "攀枝花", "泸州", "德阳", "绵阳", "广元", "遂宁", "内江", "乐山", "南充",
				"宜宾", "广安", "达州", "巴中", "雅安", "眉山", "资阳", "阿坝", "甘孜", "凉山" };
		cities.put("四川", scCities);

		String[] nmgCities = new String[]
		{ "呼和浩特", "包头", "呼伦贝尔", "乌兰浩特", "通辽", "赤峰", "锡林浩特", "集宁", "鄂尔多斯", "临河",
				"乌海", "阿拉善左旗" };
		cities.put("内蒙古", nmgCities);

		String[] nxCities = new String[]
		{ "银川", "石嘴山", "中卫", "固原", "吴忠" };
		cities.put("宁夏", nxCities);

		String[] gsCities = new String[]
		{ "兰州", "金昌", "白银", "天水", "武威", "张掖", "酒泉", "平凉", "庆阳", "定西", "武都",
				"临夏", "临夏" };
		cities.put("甘肃", gsCities);

		String[] qhCities = new String[]
		{ "西宁", "海东", "海南", "海北", "海西", "黄南", "果洛", "玉树" };
		cities.put("青海", qhCities);

		String[] xzCities = new String[]
		{ "拉萨", "日喀则", "山南", "林芝", "昌都", "那曲", "阿里" };
		cities.put("西藏", xzCities);

		String[] xjCities = new String[]
		{ "乌鲁木齐", "克拉玛依", "吐鲁番", "哈密", "和田", "阿克苏", "喀什", "阿图什", "库尔勒", "昌吉",
				"博乐", "伊宁", "塔城", "阿勒泰", "石河子", "阿拉尔" };
		cities.put("新疆", xjCities);

		String[] ahCities = new String[]
		{ "合肥", "亳州", "淮北", "宿州", "阜阳", "蚌埠", "淮南", "滁州", "六安", "巢湖", "芜湖",
				"马鞍山", "安庆", "池州", "铜陵", "宣城", "黄山站" };
		cities.put("安徽", ahCities);

		String[] zjCities = new String[]
		{ "杭州", "宁波", "温州", "嘉兴", "湖州", "绍兴", "金华", "衢州", "舟山", "台州", "丽水" };
		cities.put("浙江", zjCities);

		String[] fjCities = new String[]
		{ "福州", "厦门", "莆田", "三明", "泉州", "漳州", "南平", "龙岩", "宁德" };
		cities.put("福建", fjCities);

	}
}
