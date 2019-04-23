package com.wenlong.rinetd.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wenlong.rinetd.dao.UserDao;
import com.wenlong.rinetd.entity.IPAndPort;
import com.wenlong.rinetd.entity.UserInDB;
import com.wenlong.rinetd.util.AESUtil;
import com.wenlong.rinetd.util.ControlRinetdUtil;
import com.wenlong.rinetd.util.ImageUtil;

public class RinetdServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2551228779618599786L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的URI地址信息
		String url = request.getRequestURI();
		// 截取其中的方法名
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		Method method = null;
		try {
			// 使用反射机制获取在本类中声明了的方法
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 执行方法
			method.invoke(this, request, response);
		} catch (Exception e) {
			ControlRinetdUtil.closeConn();
			throw new RuntimeException("调用方法出错！");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	/**
	 * 登陆服务器操作，获取连接
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void breakon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ip = request.getParameter("ip");
		String username = request.getParameter("username");
		String beforepwd = request.getParameter("pwd");
		String pwd = AESUtil.desEncrypt(beforepwd);
		ControlRinetdUtil.Conn(ip, username, pwd);
		// 读取服务器配置文件并展示
		HttpSession session = request.getSession();
		session.setAttribute("ip", ip);
		session.setAttribute("username", username);
		session.setAttribute("pwd", pwd);
		System.out.println("connecting.......");
		show(request, response);
	}

	/**
	 * 断开服务器连接
	 * 
	 * @param request
	 * @param response
	 */
	public void breakoff(HttpServletRequest request, HttpServletResponse response) {
		try {
			ControlRinetdUtil.closeConn();
			HttpSession session = request.getSession();
			session.setAttribute("ip", "");
			session.setAttribute("username", "");
			session.setAttribute("pwd", "");
			System.out.println("connect break off.......");
			response.sendRedirect("list.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取并展示配置信息
	 * 
	 * @param request
	 * @param response
	 */
	public void show(HttpServletRequest request, HttpServletResponse response) {
		Map<Integer, IPAndPort> map = ControlRinetdUtil.seeConf();
		int page = 1;
		int totalPage = map.size() % 10 == 0 ? (map.size() / 10) : ((map.size() / 10) + 1);
		String currentPage = request.getParameter("page");
		page = currentPage == null ? page : Integer.valueOf(currentPage);
		Map<Integer, IPAndPort> pageMap = new LinkedHashMap<Integer, IPAndPort>();
		if (map.size() > 10) {
			for (int i = 1; i <= 10; i++) {
				if (map.get((page - 1) * 10 + i) == null) {
					break;
				}
				pageMap.put(((page - 1) * 10 + i), map.get((page - 1) * 10 + i));
			}
		} else {
			pageMap = map;
		}
		request.setAttribute("iapMap", pageMap);
		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);
		System.out.println("show message:" + pageMap);
		try {
			request.getRequestDispatcher("list.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 添加配置
	 * 
	 * @param request
	 * @param response
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) {
		String jumpIP = request.getParameter("jumpIP");
		String jumpPort = request.getParameter("jumpPort");
		String targetIP = request.getParameter("targetIP");
		String targetPort = request.getParameter("targetPort");
		IPAndPort iapAndPort = new IPAndPort(jumpIP, jumpPort, targetIP, targetPort);
		ControlRinetdUtil.addConf(iapAndPort);
		System.out.println("add config message:" + iapAndPort);
		show(request, response);
	}

	/**
	 * 删除配置
	 * 
	 * @param request
	 * @param response
	 */
	public void del(HttpServletRequest request, HttpServletResponse response) {
		String index = request.getParameter("key");
		ControlRinetdUtil.deleteConf(Integer.valueOf(index));
		System.out.println("delete config message: 第" + index);
		show(request, response);
	}

	/**
	 * 修改配置
	 * 
	 * @param request
	 * @param response
	 */
	public void modify(HttpServletRequest request, HttpServletResponse response) {
		String jumpIP = request.getParameter("jumpIP");
		String jumpPort = request.getParameter("jumpPort");
		String targetIP = request.getParameter("targetIP");
		String targetPort = request.getParameter("targetPort");
		String idnex = request.getParameter("key");
		IPAndPort iapAndPort = new IPAndPort(jumpIP, jumpPort, targetIP, targetPort);
		try {
			ControlRinetdUtil.modifyConf(Integer.valueOf(idnex), iapAndPort);
			System.out.println("modifyConf:" + iapAndPort);
			show(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 重启服务
	 * 
	 * @param request
	 * @param response
	 */
	public void restart(HttpServletRequest request, HttpServletResponse response) {
		ControlRinetdUtil.restart();
		System.out.println("restart rinetd......");
		show(request, response);
	}
	/**
	 * 生成验证码图片
	 * @param request
	 * @param response
	 */
	public void image(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/jpeg");
        //禁止图像缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();
        ImageUtil imageUtil = new ImageUtil(120, 40, 4,30);
        session.setAttribute("code", imageUtil.getCode());
        try {
			imageUtil.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 登陆
	 * @param request
	 * @param response
	 */
	public void login (HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		//用户输入的字符串
		String inputCode = request.getParameter("image").toLowerCase();
		//生成的字符串
		HttpSession session = request.getSession();
		String producedCode = ((String) session.getAttribute("code")).toLowerCase();
		
		//根据用户名查找数据库，找到密码和用户输入的比较
		UserInDB userInDB = UserDao.findByUsername(username);
		if(userInDB!=null && userInDB.getPwd()!=null && userInDB.getPwd().equals(pwd)) {
			//验证码校验
			if(inputCode.equals(producedCode)) {
				try {
					session.setAttribute("login", "login");
					request.getRequestDispatcher("list.jsp").forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					request.setCharacterEncoding("UTF-8");
					request.getRequestDispatcher("login.jsp?error=验证码错误.").forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			try {
				request.setCharacterEncoding("UTF-8");
				request.getRequestDispatcher("login.jsp?error=账号或者密码错误.").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void modifypwd(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		String newowdsec = request.getParameter("newowdsec");
		try {
			request.setCharacterEncoding("UTF-8");
			UserInDB userInDB = UserDao.findByUsername(username);
		if( userInDB!= null) {
			if(oldpwd.equals(userInDB.getPwd())) {
				if(newpwd.equals(newowdsec)) {
					UserDao.update(username, newpwd);
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("modifypwd.jsp?error=两次输入的确认密码不一致,请重新输入.").forward(request, response);
				}
			}else {
				request.getRequestDispatcher("modifypwd.jsp?error=旧密码错误.").forward(request, response);
			}
		}else {
			request.getRequestDispatcher("modifypwd.jsp?error=账号不存在.").forward(request, response);
		}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
