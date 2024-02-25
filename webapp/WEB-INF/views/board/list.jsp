<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.javaex.vo.BoardVo" %>
<%@ page import="com.javaex.vo.UserVo" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite3/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite3/assets/css/board.css" rel="stylesheet" type="text/css">
<%
	List<BoardVo> boardList = (List<BoardVo>)request.getAttribute("boardList");
	UserVo authUser = (UserVo)session.getAttribute("authUser");
	
%>
</head>


<body>
	<div id="wrap">

		<!-- header -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<!-- //header -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>게시판</h2>
				<ul>
					<li><a href="">일반게시판</a></li>
					<li><a href="">댓글게시판</a></li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head">
					<h3>일반게시판</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>게시판</li>
							<li class="last">일반게시판</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="board">
					<div id="list">
						<form action="" method="">
							<div class="form-group text-right">
								<input type="text">
								<button type="submit" id=btn_search>검색</button>
							</div>
						</form>
						<table >
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>글쓴이</th>
									<th>조회수</th>
									<th>작성일</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>
							<% for(int i=0; i<boardList.size(); i++ ){ %>
								<tr class="last">
									<td><%= boardList.get(i).getNo() %></td>
									<td class="text-left"><a href="/mysite3/board?action=read&no=<%= boardList.get(i).getNo() %>"><%= boardList.get(i).getTitle() %></a></td>
									<td><%= boardList.get(i).getName() %></td>
									<td><%= boardList.get(i).getHit() %></td>
									<td><%= boardList.get(i).getReg_date() %></td>
									<%if(authUser != null && authUser.getName().equals(boardList.get(i).getName()) ){ %>
									<!-- 세션영역에 값이 있으면 로그인 성공한 사람 && 세션에 Name이랑 list에 name이랑 같을때-->
									<td><a href="/mysite3/board?action=delete">[삭제]</a></td>
									<%}else{ %>
									<td><a href=""></a></td>
									<%} %>
								</tr>
								<% } %>
							</tbody>
						</table>
			
						<div id="paging">
							<ul>
								<li><a href="">◀</a></li>
								<li><a href="">1</a></li>
								<li><a href="">2</a></li>
								<li><a href="">3</a></li>
								<li><a href="">4</a></li>
								<li class="active"><a href="">5</a></li>
								<li><a href="">6</a></li>
								<li><a href="">7</a></li>
								<li><a href="">8</a></li>
								<li><a href="">9</a></li>
								<li><a href="">10</a></li>
								<li><a href="">▶</a></li>
							</ul>
							
							
							<div class="clear"></div>
						</div>
						<%if(authUser != null){ %>
							<a id="btn_write" href="/mysite3/board?action=writeForm">글쓰기</a>
						<%}else{ %>
							<!-- 로그인안했을때는 버튼 안보임 -->
						<%} %>
					</div>
					<!-- //list -->
				</div>
				<!-- //board -->
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
