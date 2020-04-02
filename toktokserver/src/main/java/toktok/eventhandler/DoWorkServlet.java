package toktok.eventhandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@MultipartConfig
public class DoWorkServlet extends HttpServlet{
	private String param;
	private String[] values;
	private ArrayList<String> valueList;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		param = req.getParameter("user_id");
		System.out.println(param);
//		resp.addHeader("Access-Control-Allow-Origin", "*");
//		resp.addHeader("Access-Control-Allow-Credentials", "true");
//		resp.addHeader("Access-Control-Allow-Methods", "X-Requested-With,Content-Type,Accept,Origin");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write("뿌엥");
	}

}
