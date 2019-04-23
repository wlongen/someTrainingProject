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
		// ��ȡ�����URI��ַ��Ϣ
		String url = req.getRequestURI();
		// ��ȡ���еķ�����
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		Method method = null;
		try {
			// ʹ�÷�����ƻ�ȡ�ڱ����������˵ķ���
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// ִ�з���
			method.invoke(this, req, resp);
		} catch (Exception e) {
			throw new RuntimeException("���÷�������");
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
		// ͨ��code�����û��Ƿ񼤻�
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findbyCode(code);
		if (user.getState() == 1) {
			// �Ѿ������ʾ�û�
			req.setAttribute("message", "�˺��Ѽ����ֱ�ӵ�½��");
			req.getRequestDispatcher("activation.jsp").forward(req, resp);
		} else {
			// �޸��û�״̬Ϊ1���Ѽ���
			user.setState(1);
			// �޸����ݿ�״̬
			userDao.updateState(user);
			// ��ת���Ѽ���ҳ��
			req.setAttribute("message", "�˺��Ѽ���뷵�ص�½");
			req.getRequestDispatcher("activation.jsp").forward(req, resp);
		}
	}
}
