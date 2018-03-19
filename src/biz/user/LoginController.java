package biz.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import model.UserDAO;

@WebServlet("/api/signin")
public class LoginController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		UserDAO dao = UserDAO.getInstance();
		UserBean check;
		try {
			check = dao.loginCheck(id, pw);

			if (check != null) { // 로그인 성공
				request.setAttribute("userData", check);
				RequestDispatcher rd = request.getRequestDispatcher("login/loginSuccess.jsp");
				rd.forward(request, response);
			} else{ // 비밀번호 오류로 인한 로그인 실패
				RequestDispatcher rd = request.getRequestDispatcher("login/loginfail.jsp");
				rd.forward(request, response);
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
