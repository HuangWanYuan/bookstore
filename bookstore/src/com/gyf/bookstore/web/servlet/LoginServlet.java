package com.gyf.bookstore.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gyf.bookstore.exception.UserException;
import com.gyf.bookstore.model.User;
import com.gyf.bookstore.service.UserService;



@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//1.获取请求参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//2.调用service
		UserService us = new UserService();
		try {
			User user = us.login(username, password);
			//把user保存到session
			request.getSession().setAttribute("user", user);
			
			if("管理员".equals(user.getRole())){//进入后台界面
				response.sendRedirect(request.getContextPath() + "/admin/login/home.jsp");
			}else{//登录成功，回到首页index.jsp
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			
		} catch (UserException e) {
			e.printStackTrace();
			//登录失败，回到登录页面
			request.setAttribute("login_msg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}
}
