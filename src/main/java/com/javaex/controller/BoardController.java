package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//필드
		//생성자
		//메소드-gs
		//메소드-일반

		String action = request.getParameter("action");
		if("read".equals(action)) {//해당 게시글 눌렀을때 이동
			//파라미터로 값 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			//Db관련
			BoardDao boardDao = new BoardDao();
			
			//메소드 시행(파라미터 넣어주기)-> 받아온것 변수로 넣기
			BoardVo boardVo =boardDao.selectOne(no);
			//attribute에 넣어주기
			request.setAttribute("boardVo", boardVo);

			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			System.out.println("들어갔습니다.");
			
		}else if("mform".equals(action)) {//수정버튼 눌렀을때 수정폼
			System.out.println("수정버튼 눌렀을때 수정폼");
			//파라미터 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//DB관련
			BoardDao boardDao= new BoardDao();
			
			//메소드써주기-파라미터 넣기--이전에 만들어놓은 하나 가져오는 Vo재사용
			BoardVo boardVo = boardDao.selectOne(no);
			
			//return된거 받아서 attribute에 넣기
			request.setAttribute("boardVo", boardVo) ;
			
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/board/modifyForm.jsp");
		}else if("modify".equals(action)) {//수정
			System.out.println("수정버튼 눌렀을때");
			//파라미터 받아오기
			int no = Integer.parseInt(request.getParameter("no")) ;
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//vo로 묶기
			BoardVo boardVo= new BoardVo(no, title, content);
			
			//Db관련
			BoardDao boardDao = new BoardDao();
			
			//메소드(vo)실행
			boardDao.update(boardVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite3/board");
			
		}
		else if("delete".equals(action)) {
			System.out.println("삭제버튼 누름");
			//session에서 no받아오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int num = authUser.getNo();
			//Dao관련
			BoardDao boardDao = new BoardDao();
			
			//메소드 실행
			boardDao.delete(num);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite3/board");
			
		}else if("writeForm".equals(action)) {//글쓰기 버튼 눌렀을때
			System.out.println("글쓰기 버튼 눌렀을때");
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}else if("write".equals(action)) {//등록버튼 눌렀을때
			System.out.println("등록버튼 눌렀을때");
			
			//파라미터 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//세션에서 no가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int num = authUser.getNo();
			
			//vo로 묶기
			BoardVo boardVo = new BoardVo(num,title,content);
			
			//db관련
			BoardDao boardDao = new BoardDao();
			
			//db메소드쓰기
			boardDao.insert(boardVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite3/board");
		}
		else {//메인 페이지
			//DB관련
			BoardDao boardDao= new BoardDao();
			
			//db메소드
			List<BoardVo> boardList = boardDao.selectAll();
			
			//attribute에 넣기
			request.setAttribute("boardList", boardList);
			//System.out.println("attribute에 넣기 성공");
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
