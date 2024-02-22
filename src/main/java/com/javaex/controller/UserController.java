package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		
		
		
		if("joinform".equals(action)) {
			System.out.println("user>joinform");
			
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
			
			
			
		}else if("loginform".equals(action)) {
//			System.out.println("lform일때");
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(action)) {
//			System.out.println("login일때");
			
			//파라미터에서 데이터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			
			//Vo로 묶기
			UserVo userVo = new UserVo(id , password);
			
			//데이터 관련
			UserDao userDao = new UserDao();
			
			//Dao에서 메소드 쓰기
			UserVo authUser= userDao.selectUserByIdPw(userVo); //나중에 수정할때 selectUser를 써야할때있음. 근데 받아오는 파라미터가 다름.그래서 이름 다르게 해야함/ 여기 userVo는 id pw가지고있음
			//authUser는 no, name을 가지고있음
			
			if(authUser !=null) {//null이 아니면 로그인 성공
				
				//세션에 주소 전달
				HttpSession session = request.getSession(); //reuqest에서 주소달라고함. 
				session.setAttribute("authUser", authUser); // (별명, 주소) /위에서 받은 주소를 찾을 별명과 함께 session에 넣어줌.
				session.setAttribute("userVo", userVo);
				
				//리다이렉트
				WebUtil.redirect(request, response, "/mysite3/main");
				System.out.println(authUser);
				System.out.println(userVo);
				
			}else { //null이면 로그인 실패
				System.out.println("로그인 실패");
				
				//리다이렉트
				WebUtil.redirect(request, response, "/mysite3/user?action=loginform");
			}		
			
		}else if("logout".equals(action)) {
			System.out.println("user>logout");
			
			HttpSession session = request.getSession();//request한테 주소달라고함
			session.invalidate();//그 주소가 날라감
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite3/main");
			
		}else if("mform".equals(action)) {
			System.out.println("user>mform");
			
			//request한테 주소받고, 그 안에서 no값 꺼내기
			HttpSession session = request.getSession();
//			System.out.println(session);
			UserVo num = (UserVo)session.getAttribute("authUser" ) ;
			
//			System.out.println(num.getNo());
			
			//db관련
			UserDao userDao = new UserDao();
			
			//db에 메소드 사용
			userDao.getUser(num);
			
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/modifyForm.jsp");
			
		}else if("modify".equals(action)) {
			System.out.println("user>modify");
			
			//값가져오기
			HttpSession session = request.getSession();
//			System.out.println(session);
			UserVo num = (UserVo)session.getAttribute("authUser") ;
			int no = num.getNo();
			String name = num.getName();
			UserVo vo = (UserVo)session.getAttribute("userVo");
//			String id = request.getParameter("id");
//			String pw = request.getParameter("pw");
//			String gender = request.getParameter("gender");
			String id= vo.getId();
			String pw = vo.getPw();
			String gender = vo.getGender();
			
			//vo로 묶기
			UserVo userVo = new UserVo(no,name,id,pw,gender);
			//데이터 관련
			UserDao userDao = new UserDao();
			//데이터에서 메소드 쓰기
			userDao.update(userVo);
			//포워드
			WebUtil.forward(request, response, "/mysite3/main");
		}
		else {
			System.out.println("action값을 다시 확인해주세요");
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
