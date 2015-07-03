package lee;

import java.util.*;

import javax.jws.WebService;
/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */

//定义Web Services接口的实现类，Web Services的名字
@WebService(endpointInterface = "lee.FirstWs",
            serviceName = "firstWs")
public class FirstWsImpl implements FirstWs 
{
	@Override
	public List<User> getUserList(String base) 
	{
		System.out.println("---调用getUserList方法---" + base);
		List<User> result = new ArrayList<User>();
		result.add(new User(1 , base + "crazyit" , "123" , 173));
		result.add(new User(2 , base + "leegang" , "456" , 178));
		return result;
	}
}