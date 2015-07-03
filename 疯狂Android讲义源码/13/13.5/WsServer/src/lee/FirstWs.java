package lee;

import java.util.*;

import javax.xml.bind.annotation.adapters.*;
import javax.jws.*;

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
//以@WebService Annotation标注，表明该接口将对应一个Web Services
@WebService
public interface FirstWs 
{
	//定义一个方法，该方法将被暴露成一个Web Services操作
	List<User> getUserList(String base);
}