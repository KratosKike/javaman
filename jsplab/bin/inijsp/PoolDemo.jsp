<%@ page language="java" import="java.sql.*, java.util.*, inijsp.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String aConnectionURL; String aUserName; String aPassword;
    Connection conn = null;
    try{
    	conn = connectionPool.getConnection();
    	Statement statement = conn.createStatement();
    	ResultSet results = statement.executeQuery("SELECT * FROM person");
    	Vector v = new Vector();
    	while (results.next()){
    		v.addElement(new Person(results));
    	}
    	request.setAttribute("people", v);
    	statement.close();
    }
    finally{
    	if (conn != null){
    		connectionPool.releaseConnection(conn);
    	}
    }
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor="#fffff">
<jsp:include page="/servlet/inijsp.TableServlet" flush="true">
<jsp:param value="people" name="data" />

<jsp:param name="tableOptions" value="BORDER=4" />

<jsp:param value="name" name="column"/>
<jsp:param value="data" name="ColumnType"/>
<jsp:param value="Name" name="columnHeader"/>

<jsp:param value="age" name="column"/>
<jsp:param value="data" name="ColumnType"/>
<jsp:param value="Age" name="columnHeader"/>

<jsp:param value="city" name="column"/>
<jsp:param value="data" name="ColumnType"/>
<jsp:param value="City" name="columnHeader"/>

<jsp:param value="state" name="column"/>
<jsp:param value="data" name="ColumnType"/>
<jsp:param value="State" name="columnHeader"/>

<jsp:param value="country" name="column"/>
<jsp:param value="data" name="ColumnType"/>|		
<jsp:param value="Country" name="columnHeader"/>

<jsp:param value="postalCode" name="column"/>
<jsp:param value="data" name="ColumnType"/>
<jsp:param value="Postal Code" name="columnHeader"/>

<jsp:param value="email" name="column"/>
<jsp:param value="data" name="ColumnType"/>
<jsp:param value="E-Mail" name="columnHeader"/>
</jsp:include>
</body>
</html>

<%!
protected IconnectionPool connectionPool;

public void jspInit(){
	try{
		Class.forName("interbase.interclient.Driver");
	} catch (Exception ignore){}
	connectionPool = new SimpleConnectionPool("jdbc:interbase://localhost/h:/jspbook/database/ibexample", "root", "3193115");
}
%>