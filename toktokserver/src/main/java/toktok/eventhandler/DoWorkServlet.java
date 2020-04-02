package toktok.eventhandler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

@SuppressWarnings("serial")
@MultipartConfig(maxFileSize=3145728, maxRequestSize=3145728)
public class DoWorkServlet extends HttpServlet{
	private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("c:/temp");
	private String param;
	private String[] values;
	private ArrayList<String> valueList;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String contentType = req.getContentType();
	    if(contentType != null && contentType.startsWith("multipart/")){
	           req.setAttribute(Request.MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
//	           각 파트 확인
//	           for(Part part: req.getParts()) {
//	        	   System.out.println(part.getName());
//	           } ;
	    }
		param = req.getParameter("user_id");
		System.out.println("param : " + param);
//		resp.addHeader("Access-Control-Allow-Origin", "*");
//		resp.addHeader("Access-Control-Allow-Credentials", "true");
//		resp.addHeader("Access-Control-Allow-Methods", "X-Requested-With,Content-Type,Accept,Origin");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write("뿌엥");
	}

}
