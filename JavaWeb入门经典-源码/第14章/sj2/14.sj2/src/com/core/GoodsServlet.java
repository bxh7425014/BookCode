package com.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.model.CommodityForm;
@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private Document doc;
	private int pagesize=2;
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType( "text/xml;charset=UTF-8" );//设置响应格式为test/xml
		//禁止缓存
		response.addHeader( "Cache-Control", "no-store,no-cache,must-revalidate" );
		response.addHeader( "Cache-Control", "post-check=0,pre-check=0" );
		response.addHeader( "Expires", "0" );
		response.addHeader( "Pragma", "no-cache" );
		PrintWriter out = response.getWriter();
		PageElement pageArgs = getPageArgs();              // 获取数据分页参数
		List goodses = null;                               // 创建数据集合
	    // 获取请求对象的分页参数
		int page=1;
		if(null!=request.getParameter("page")){
			page = Integer.parseInt(request.getParameter("page"));
		}
		String order = request.getParameter("order");
		pageArgs.setPageCount(page);
		out.println( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
		out.println( "<goodses>" );
		out.println( "<pageElement>" );
		out.println( "<pageNum>" +pageArgs.getPageNum()+"</pageNum>");
		out.println( "<maxPage>" +pageArgs.getMaxPage()+"</maxPage>");
		out.println( "<nextPage>" +pageArgs.getNextPage()+"</nextPage>");
		out.println( "<prePage>" +pageArgs.getPrePage()+"</prePage>");
		out.println( "</pageElement>" );
		try{
			goodses = getGoods(page,order);
		}catch(Exception e){
			e.printStackTrace();
		}
		for(int i=0;i<goodses.size();i++){
			 CommodityForm commodity = (CommodityForm) goodses.get(i);
			 out.println( "<goods>" );
			 out.println( "<id>"+commodity.getId()+"</id>" );
			 out.println( "<goodsName>"+commodity.getGoodsName()+"</goodsName>" );
			 out.println( "<introduce>"+commodity.getIntroduce()+"</introduce>" );
			 out.println( "<Price>"+commodity.getPrice()+"</Price>" );
			 out.println( "</goods>" );
		}
		out.println( "</goodses>" );
		out.close();
	}
	
    public List getGoods(final int page,String order) throws Exception {
        List list = new ArrayList();            // 创建保存分页数据的集合对象
        ConnDB conn = new ConnDB();            // 连接数据库
        CommodityForm f =null;
        int firstResult = (page-1) * pagesize;
        try {
            // 定义分页查询的SQL语句
        	String sql="";
        	if(order.equals("0")){
        		sql = "select * from tb_goods order by id asc limit "+firstResult+","+pagesize;
        	}
        	else if(order.equals("1")){
        		sql = "select * from tb_goods order by id desc limit "+firstResult+","+pagesize;
        	}
            ResultSet rs = conn.executeQuery(sql);   // 获取查询结果
            while (rs.next()) {         // 遍历查询结果集
                f = new CommodityForm();  // 创建分页对象
                f.setId(rs.getInt("id"));
                f.setGoodsName(rs.getString("name"));
                f.setIntroduce(rs.getString("introduce"));
                f.setPrice(rs.getFloat("price"));
                list.add(f);        // 添加分页数据对象到List集合
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        } finally {
            conn.close();
        }
        return list;
    }	
	
    public PageElement getPageArgs() {
        PageElement pag = null;                // 声明分页参数对象
        Statement stmt = null;
        ResultSet rs = null;
        ConnDB conn = new ConnDB();        // 连接数据库
        try {
            // 声明查询总数据量的SQL语句
            String sql = "SELECT count(*) FROM tb_goods";
            rs = conn.executeQuery(sql);    // 执行SQL查询
            if (rs.next()) {
                int count = rs.getInt(1);   // 获取查询结果
                pag = new PageElement();       // 初始化分页参数对象
                pag.setPageSize(pagesize);  // 设置分页大小
                pag.setMaxPage((count + pagesize - 1) / pagesize);// 设置最大页码
                pag.setPageCount(count);        // 设置当前页码
            }
        } catch (SQLException ex) {
            ex.getMessage();
        } finally {
            conn.close();
        }
        return pag;
    }


	public void init() throws ServletException {
		// Put your code here
	}

}
