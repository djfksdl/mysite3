<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %>

<%
	//HttpSession session = request.getSession(); //이 이름(session)으로 미리 담아놔서 이 이름으로 씀
	UserVo authUser = (UserVo)session.getAttribute("authUser");//세션에 있는 authUser를 꺼낸다./이것저것 다 올 수 있기때문에 형변환 해줘야함.
	
%>

<div id="header" class="clearfix">
	<h1>
		<a href="/mysite3/main">MySite</a>
	</h1>

	<% if(authUser !=null){%>
	<!-- 세션영역에 값이 있으면 로그인 성공한 사람-->	
	<ul>
		<li><%= authUser.getName() %> 님 안녕하세요^^</li>
		<li><a href="/mysite3/user?action=logout" class="btn_s">로그아웃</a></li>
		<li><a href="/mysite3/user?action=mform" class="btn_s">회원정보수정</a></li>
	</ul>
	
	
	<%}else{ %>
	<!-- 세션영역에 값이 없으면 로그인 안한사람(실패한경우, 안한경우)  -->
	<ul>
		<li><a href="/mysite3/user?action=loginform" class="btn_s">로그인</a></li>
		<li><a href="/mysite3/user?action=joinform" class="btn_s">회원가입</a></li>
	</ul>
	<%} %>
	
</div>
<div id="nav">
	<ul class="clearfix">
		<li><a href="">입사지원서</a></li>
		<li><a href="/mysite3/board">게시판</a></li>
		<li><a href="">갤러리</a></li>
		<li><a href="/mysite3/gbc">방명록</a></li>
	</ul>
</div>
<!-- //nav -->