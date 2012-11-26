<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="inijsp.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="CheckLoggedIn.jsp" %>

<%
    Connection conn = null;

    IConnectionPool pool =
        (IConnectionPool) application.getAttribute("connectionPool");

    try
    {
        conn = pool.getConnection();

        ProblemReport prQuery = new ProblemReport();

        Vector v = prQuery.executeQuery(conn,
            "select distinct "+prQuery.getFieldList("problem_report")+
            ", problem_report_developer.problem_id as prdprobid"+
            " from problem_report, problem_report_developer "+
            " where problem_report.problem_id = "+
            " problem_report_developer.problem_id and "+
            " problem_report_developer.developer='"+developer+"'");

%>
<h1>Problems Assigned to <%= developer%></h1>
<table border="4">
<tr><th>System<th>Subsystem<th>Originator<th>Description
<th>Priority<th>Status<th>Developers<th>ECD
<%
        ProblemReportDeveloper prdQuery = new ProblemReportDeveloper();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        Enumeration e = v.elements();

        while (e.hasMoreElements())
        {
            ProblemReport problem = (ProblemReport) e.nextElement();
%>
<tr><td><%=problem.system%><td><%=problem.subsystem%>
<td><%=problem.originator%><td><%=problem.description%>
<td><%=problem.priority%><td><%=problem.status%>
<td>

<%
            Vector devs = prdQuery.getAll(conn,
                "problem_id="+problem.problemId);

            Enumeration de = devs.elements();
            boolean first = true;

            while (de.hasMoreElements())
            {
                ProblemReportDeveloper prd = (ProblemReportDeveloper)
                    de.nextElement();

                if (!first) out.print(",");
                first = false;

                out.print(prd.developer);
            }
%>
<td><%=format.format(problem.estimatedCompletionDate)%>
<td><a href="EditProblem.jsp?problemId=<%=problem.problemId%>">Edit</a>
<%
        }
     }
     finally
     {
         try
         {
             pool.releaseConnection(conn);
         } catch (Exception ignore) { }
     }
%>
</table>
<p>
<a href="ProblemReporting.jsp">Return to Main Page</a>
</body>
</html>