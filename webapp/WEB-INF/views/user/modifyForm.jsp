<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %>

<%
	//HttpSession session = request.getSession(); //이 이름(session)으로 미리 담아놔서 이 이름으로 씀
	UserVo authUser = (UserVo)session.getAttribute("authUser");//세션에 있는 authUser를 꺼낸다./이것저것 다 올 수 있기때문에 형변환 해줘야함.
	UserVo userVo = (UserVo)session.getAttribute("userVo");
	
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite3/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite3/assets/css/user.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<!-- header -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<!-- //header -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>회원</h2>
				<ul>
					<li>회원정보</li>
					<li>로그인</li>
					<li>회원가입</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">
			
				<div id="content-head">
					<h3>회원정보</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>회원</li>
							<li class="last">회원정보</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				 <!-- //content-head -->
	
				<div id="user">
					<div id="modifyForm">
						<form action="/mysite3/user" method="">
							<input type="text" name="no" value="<%=authUser.getNo()%>">
							<input type="text" name="id" value="<%=userVo.getId()%>">
							<!-- 아이디 -->
							<div class="form-group">
								<label class="form-text" for="input-uid">아이디</label> 
								<span class="text-large bold"><%= userVo.getId() %></span>
							</div>
	
							<!-- 비밀번호 -->
							<div class="form-group">
								<label class="form-text" for="input-pass">패스워드</label> 
								<input type="text" id="input-pass" name="pw" value="<%= userVo.getPw() %>" placeholder="비밀번호를 입력하세요"	>
							</div>
	
							<!-- 이메일 -->
							<div class="form-group">
								<label class="form-text" for="input-name">이름</label> 
								<input type="text" id="input-name" name="name" value="<%= authUser.getName() %>" placeholder="이름을 입력하세요">
							</div>
							<!-- //나이 -->
							<div class="form-group">
								<span class="form-text">성별</span> 
								<label for="rdo-male">남</label> 
								<input type="radio" id="rdo-male" name="gender" value="male" checked="checked"> 
								
								<label for="rdo-female">여</label> 
								<input type="radio" id="rdo-female" name="gender" value="female" > 
	
							</div>
	
							<!-- 버튼영역 -->
							<div class="button-area">
								<button type="submit" id="btn-submit">회원정보수정</button>
							</div>
							<input type="text" name="action" value="modify"> 
						</form>
					
					
					</div>
					<!-- //modifyForm -->
				</div>
				<!-- //user -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->

		<!-- footer.jsp를 불러와라 -->
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		
		<!-- //footer -->
		
	</div>
	<!-- //wrap -->

</body>

</html>