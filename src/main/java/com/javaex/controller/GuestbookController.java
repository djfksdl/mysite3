package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;

	//메소드-일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if("register".equals(action)) {//등록 버튼을 눌렀을때 
			//System.out.println("등록버튼을 눌렀을때");
			
			//파라미터 받은거 Vo로 담기
			String name = request.getParameter("name");
			String pw = request.getParameter("pw");
			String content = request.getParameter("content");
			String reg_date = request.getParameter("reg_date");
			
			GuestbookVo guestVo= new GuestbookVo(name, pw, content, reg_date);
			
			//DB관련-Dao연결
			GuestbookDao guestDao = new GuestbookDao();
			
			//Dao에 vo로 묶은 파라미터 넣어서 메소드 작동
			guestDao.insert(guestVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite3/gbc");
			
		}else if("dform".equals(action)) {//삭제 버튼을 눌렀을때
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		}else if("delete".equals(action)) {//삭제폼에서 삭제를 눌렀을때
			//파라미터 값 꺼내서 vo로 묶기
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pw");
			
			GuestbookVo guestVo = new GuestbookVo(no, pw);
			
			//DB관련
			GuestbookDao guestDao= new GuestbookDao();
			
			//Dao 메소드 사용()안에 묶은 파라미터 넣어주기
			guestDao.delete(guestVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite3/gbc");
			System.out.println("삭제성공");
			
		}
		else {
			//DB관련
			GuestbookDao guestDao = new GuestbookDao();
			
			//DB에서 정보(리스트) 불러오기 (메소드 쓰기)
			List<GuestbookVo> guestList= guestDao.guestSelect();
			
			//주소 전달(attribute)
			request.setAttribute("guestList", guestList);
			//System.out.println(guestList );
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
