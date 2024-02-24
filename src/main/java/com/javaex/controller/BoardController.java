package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//필드
		//생성자
		//메소드-gs
		//메소드-일반

		String action = request.getParameter("action");
		if("delete".equals(action)) {
//			System.out.println("삭제버튼 누름");
		}else {
			//메인 페이지
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
