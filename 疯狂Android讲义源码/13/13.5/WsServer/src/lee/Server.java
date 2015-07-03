package lee;

import javax.xml.ws.Endpoint;
import org.apache.cxf.interceptor.*;
import org.apache.cxf.jaxws.EndpointImpl;
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
public class Server
{
	public static void main(String[] args) 
	{
		System.out.println("启动服务器");
		//创建Web Services服务提供类的实例
		FirstWsImpl implementor = new FirstWsImpl();
		String address = "http://192.168.1.88:9999/crazyit";
		//将指定Web Services服务提供类的对象发布成Web Services
		EndpointImpl ep = (EndpointImpl)Endpoint.publish(address , implementor);

		//添加两个CXF自带的拦截器，用于跟踪SOAP消息
		ep.getServer().getEndpoint()
			.getInInterceptors().add(new LoggingInInterceptor());
		ep.getServer().getEndpoint()
			.getOutInterceptors().add(new LoggingOutInterceptor());
	}
}
