<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" include file="CheckLoggedIn.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<body bgcolor="#ffffff">



<h1>Problem Reporting System</h1>
<p>
You are currently logged in as <%= userName %>.
<p>
Please select an option:
<ul>
    <li><a href="GetMyProblems.jsp">
        Show Problems Assigned To <%= developer %></a>
    <li><a href="GetAllProblems.jsp">
        Show All Problems</a>
    <li><a href="EditProblem.jsp">Enter a New Problem</a>
</ul>
</body>
</html>