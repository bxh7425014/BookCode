package com.wgh.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.filefilter.SuffixFileFilter;
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uploadPath = this.getServletContext().getRealPath("/")+"upload";	//定义上传文件的服务器路径
		File uploadFolder = new File(uploadPath);					//根据该路径创建File对象
		if(!uploadFolder.exists())								//如果路径不存在，则创建
			uploadFolder.mkdirs();
		String message = "文件上传成功！";
		try{
			if(ServletFileUpload.isMultipartContent(request)){
				DiskFileItemFactory factory = new DiskFileItemFactory();	//创建磁盘工厂，用来配置上传组件ServletFileUpload
				factory.setSizeThreshold(20*1024); 				//设置内存中允许存储的字节数
				factory.setRepository(factory.getRepository());			//设置存放临时文件的目录
				ServletFileUpload upload = new ServletFileUpload(factory);	//创建新的上传文件句柄
				int maxSize = 5*1024*1024;					//定义上传文件的大小
				List<FileItem> files = upload.parseRequest(request);		//从请求中得到所有上传域列表
				String[] suffixs =new String[]{".exe",".bat",".jsp"};//限制上传的文件类型
				SuffixFileFilter filter = new SuffixFileFilter(suffixs);
				for(FileItem fileItem:files){					//遍历上传文件集合
					if(!fileItem.isFormField())	{				//忽略其他不是文件域的所有表单信息
						String filePath = fileItem.getName();	//获取文件全路径名
						String fileName="";
						int startIndex = filePath.lastIndexOf("\\");
						if(startIndex!=-1){						//对文件名进行截取
							fileName = filePath.substring(startIndex+1);
						}else{
							fileName=filePath;
						}
						if(fileItem.getSize()>maxSize){			//限制文件大小
							message = "上传文件不得超过5MB！";
							break;
						}
						if((fileName == null) ||(fileName.equals(""))&&(fileItem.getSize()==0))
							continue;			
						File file = new File(uploadPath,fileName);		//在上传路径创建文件对象
						boolean res = filter.accept(file);
						if(res){
							message = "禁止上传 *.exe、*.jsp、*.bat文件！";
							break;
						}else{
							fileItem.write(file);					//向文件写数据
						}
					}	
				}		
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		request.setAttribute("result", message);						//将提示信息保存在request对象中
		request.getRequestDispatcher("index.jsp").forward(request, response);

	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

}
