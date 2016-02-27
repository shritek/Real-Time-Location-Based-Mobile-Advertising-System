<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%
String userid=request.getParameter("user"); 
session.putValue("userid",userid); 
String pwd=request.getParameter("pwd"); 
Class.forName("com.mysql.jdbc.Driver"); 
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BranchDB","root","root"); 
Statement st= con.createStatement(); 
ResultSet rs=st.executeQuery("select * from LoginData where LoginID='"+userid+"'"); 
if(rs.next()) 
{ 
if(rs.getString(2).equals(pwd)) 
{ 
out.println("welcome"+userid); 

} 
else 
{ 
out.println("Invalid password try again"); 
} 
} 
else 
%>
