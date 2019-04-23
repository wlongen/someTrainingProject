package com.wenlong.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wenlong.dao.UserDao;
import com.wenlong.dao.impl.UserDaoImpl;
import com.wenlong.entity.User;
import com.wenlong.service.UserService;
import com.wenlong.service.impl.UserServiceImpl;
import com.wenlong.utils.UUIDUtil;

public class RegiestServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		// 获取请求的URI地址信息
		String url = req.getRequestURI();
		// 截取其中的方法名
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		Method method = null;
		try {
			// 使用反射机制获取在本类中声明了的方法
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 执行方法
			method.invoke(this, req, resp);
		} catch (Exception e) {
			throw new RuntimeException("调用方法出错！");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@SuppressWarnings("unused")
	private void regiest(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");

		User user = new User();
		user.setCode(UUIDUtil.getUUID() + UUIDUtil.getUUID());
		user.setEmail(email);
		user.setNickname(nickname);
		user.setPassword(password);
		user.setState(0);
		user.setUsername(username);

		UserService us = new UserServiceImpl();
		us.regiest(user);
	}

	@SuppressWarnings("unused")
	private void activation(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String code = req.getParameter("code");
		// 通过code查找用户是否激活
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findbyCode(code);
		if (user.getState() == 1) {
			// 已经激活，提示用户
			req.setAttribute("message", "账号已激活，请直接登陆。");
			req.getRequestDispatcher("activation.jsp").forward(req, resp);
		} else {
			// 修改用户状态为1，已激活
			user.setState(1);
			// 修改数据库状态
			userDao.updateState(user);
			// 跳转到已激活页面
			req.setAttribute("message", "账号已激活，请返回登陆");
			req.getRequestDispatcher("activation.jsp").forward(req, resp);
		}
	}
}
