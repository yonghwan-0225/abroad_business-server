package biz.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


 public class logintest extends javax.servlet.http.HttpServlet {

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
  String s_id = request.getParameter("id");
  String s_pwd = request.getParameter("pwd");
  
  //db에서 인증절차를 하기윈한 준비 작업
  Connection con = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  
  response.setContentType("text/html;charset=euc-kr");
  PrintWriter out = response.getWriter();
  
  try {
   InitialContext ic = new InitialContext();
   Context ctx = (Context) ic.lookup("java:comp/env");
   DataSource ds = (DataSource) ctx.lookup("jdbc/oracle");
   
   con = ds.getConnection();
   
   pstmt = con.prepareStatement("SELECT * FROM loginTest_T WHERE id=? and pwd=?");
   
   if(s_id != null && s_id.trim().length() > 0 && s_pwd != null && s_pwd.trim().length() > 0){
    pstmt.setString(1, s_id.trim());
    pstmt.setString(2, s_pwd.trim());
    rs = pstmt.executeQuery();
    
    //sql문장을 수행 했을 때 결과는 1개의 record 또는 비어잇는 ResultSet를 받게 된다.
    
    if(rs.next()){ //1개 일때 사용
     //인증된 자원일 경우 - 세션 처리
     String s_name = rs.getString("name");

     //db이라는 컬럼에 있는 자원을 가져와서 s_name이라는 변수에 대입한다.
     
     //세션 객체를 만들고, 속성을 설정
     HttpSession ss = request.getSession();
     ss.setAttribute("id", s_id); //id라는 속성명으로
     //s_id가 기억하고 있는 값을 속성값으로 설정
     ss.setAttribute("name", s_name);
     out.println("<body>");
     out.println(s_name + "환영합니다.");
     out.println("<input type='button' value='이동' style='cursor:hand' onClick='location.href=\"LoginServlet1\"'>");
    }else{
     out.println("id 또는 password가 틀렸습니다.");
     out.println("<input type='button' value='돌아가기' onClick='history.back(-1);'>");
    }
   }else{
     
   }
  } catch (Exception e) {
   // TODO: handle exception
  }finally{
   out.close();
   try {
    if(rs != null){
     rs.close();     
    }
    if(pstmt != null){
     pstmt.close();     
    }
    if(con != null){
     con.close();     
    }    
   } catch (Exception e) {
    // TODO: handle exception
   }
  }
 }           
}
