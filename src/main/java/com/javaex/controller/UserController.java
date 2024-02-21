package com.javaex.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
	//생성자-안씀
	//메소드-gs
	
	//메소드-일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//실행되는지 확인
		System.out.println("UserController");

		//user에서 업무구분
		String action = request.getParameter("action");
//		System.out.println(action);
		
		
		
		
		if("joinForm".equals(action)) {
			System.out.println("user>joinForm");
			
			//포워드-회원가입폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
			
		}else if("join".equals(action)) {
			System.out.println("user>join");
			
			//파라미터에서 데이터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw"); //이름이 달라도 됨. 앞의건 내가 정하는 변수이고, 뒤에는 파라미터에 써진 값!
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			//확인용
//			System.out.println(id);
//			System.out.println(password);
//			System.out.println(name);
//			System.out.println(gender);
			
			//Dao로 넘길 예정인데 그 전에 데이터가 4개라 묶어서 보내는게 좋음. 데이터는 보통 Vo로 묶음
			//Vo로 묶기
			//1)방법-4개짜리 안만들고 넣을 경우
//			UserVo userVo = new UserVo(); 이걸 써주고 setter로 값 하나하나씩 넣으면 되는데 귀찮음
			//2)방법- 순서주의! 지금은 다 String이라 바뀌어도 오류 안잡음.
			UserVo userVo = new UserVo(id, password ,name ,gender);
			System.out.println(userVo);//println은 toString을 알아서 찾는다. 물론 메소드-일반에 만들어줘야함. 안그럼 @어쩌고로 나옴.
			
			//Dao의 메소드로 회원가입
			UserDao userDao = new UserDao();
			userDao.insertUser(userVo);
		
			
			//joinOk.jsp로 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
			
			
		}else if("lForm".equals(action)) {
//			System.out.println("lform일때");
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}
		else {
			System.out.println("action값을 다시 확인해주세요");
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
