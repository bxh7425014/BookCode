package com.wgh.bean;

public class StringUtil {
	private String str;//要判断的字符串
	private boolean valid;//是否有效
	private String cue;//提示信息
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public boolean isValid() {
		char cArr[] =str.toCharArray();		//字符串转换为字符数组 
		int firstChar =(int) cArr[0];		//第一个字符的ASCII码
		StringBuffer sb = new StringBuffer("");
		//判断首字符是否为字母
		if((firstChar>=65&&firstChar<=90)||(firstChar>=97&&firstChar<=122)){
			for(int i=1;i<cArr.length;i++){
				int ascii =cArr[i];			//获得字符的ASCII码
				//判断字符是否为字母、数字或下划线，下划线的ASCII码为95
				if((ascii>=48&&ascii<=57)||(ascii>=65&&ascii<=90)||
						(ascii==95)||(ascii>=97&&ascii<=122)){
					sb.append(cArr[i]);		//如果条件满足，将字符添加到StringBuffer字符串的末尾
				}
			}
			int length = cArr.length-sb.toString().length();
			//如果被判断字符串长度与StringBuffer字符串记录的长度差1（即去掉首字符的长度）
			if(length==1){
				this.setCue("用户名格式正确！");
				valid=true;
				return valid;
			}else{
				this.setCue("用户名格式错误，只能由字母、数字或下划线组成！");
				valid=false;
				return valid;
			}
		}else{								//如果首字符不是字母，直接返回false
			this.setCue("用户名格式不对，首字符必须为字母！");
			valid=false;
			return valid;
		}
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getCue() {
		return cue;
	}
	public void setCue(String cue) {
		this.cue = cue;
	}
	
}
