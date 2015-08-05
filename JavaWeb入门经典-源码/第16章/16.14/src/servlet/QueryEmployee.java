package servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.wgh.hibernate.HibernateUtil;

import employee.Employee;

/**
 * 分组统计员工信息
 */
@WebServlet("/")
public class QueryEmployee extends HttpServlet {

	private static final long serialVersionUID = 2272389768111606782L;

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	Session session = null;								// 实例化session对象
    	try {
    		session = HibernateUtil.getSession();				// 获得session对象
    	  String hql = "select emp.sex,count(*) from Employee emp group by emp.sex";														// 条件查询HQL语句
    	     Query q = session.createQuery(hql);				// 执行查询操作
    	     List<Employee> emplist = q.list();
    	     Iterator it = emplist.iterator();//使用迭代器输出返回的对象数组
    	  while(it.hasNext()) {
    			Object[] results = (Object[])it.next();
    	             System.out.print("员工性别: " + results[0] + "————");
    	             System.out.println("人数: " + results[1]);
    	     }
    	} catch (HibernateException e) {
    	     e.printStackTrace();
    	} finally {
    	     HibernateUtil.closeSession();					// 关闭session
    	}

    }
}
