package com.wenlong.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class SearchServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static List<String> datas = new ArrayList<String>();
	static {
		datas.add("ajax1");
		datas.add("ajax2");
		datas.add("ajax3");
		datas.add("ajax4");
		datas.add("ajax5");
		datas.add("ajax6");
		datas.add("ajax7");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取前端发来的keywork
		req.setCharacterEncoding("UTF-8");
		String keyword  = req.getParameter("keyword");
		List<String> dataList = getDataList(keyword);
		//返回json格式
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(JSONArray.fromObject(dataList).toString());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private List<String> getDataList(String keyword){
		List<String> list = new ArrayList<String>();
		for (String string : datas) {
			if(string.contains(keyword)) {
				list.add(string);
			}
		}
		return list;
	}
}
