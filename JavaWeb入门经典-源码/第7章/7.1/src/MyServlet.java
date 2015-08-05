import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class MyServlet extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		// 业务处理
	}
	public void destroy() {
		// 业务处理
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws 
		ServletException, IOException {
		// 业务处理
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws 
		ServletException, IOException {
		// 业务处理
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws 
		ServletException, IOException {
		// 业务处理
	}
	
}