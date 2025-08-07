<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공</title>
</head>
<body>
	<%--
	<%
		String sid = (String) session.getAttribute("sid");
	
	%>
	--%>
	<%
		if(session.getAttribute("sid") == null) { //로그인을 하지 않은 유저가 본 페이지를 방문한 경우
			response.sendRedirect("login.do");
		};
	%>
	
	<h2>로그인 성공</h2>
	<hr>
	<h3>[${mid }]님 환영합니다!</h3>
	<a href="freeboard.do">게시판 바로 가기</a><br><br>
	<a href="member.do">회원 목록 바로 가기</a><br><br>
	<hr>
	<a href="logout.do">로그 아웃</a>
</body>
</html>