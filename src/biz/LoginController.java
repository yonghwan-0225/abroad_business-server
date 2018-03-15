package biz;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDAO;

public class LoginController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");

		UserDAO dao = UserDAO.getInstance();
		int check = 0;
		try {
			check = dao.loginCheck(id, pw);

			if (check == 1) { // 로그인 성공
				session.setAttribute("id", id);
				session.setAttribute("pw", pw);
				RequestDispatcher rd = request.getRequestDispatcher("ShelterEQ_Login.html");
				rd.forward(request, response);
			} else if (check == 0) { // 비밀번호 오류로 인한 로그인 실패
				request.setAttribute("ErrorPW", "ErrorPW");
				RequestDispatcher rd = request.getRequestDispatcher("login/loginfail.jsp");
				rd.forward(request, response);
			} else { // 아이디 오류로 인한 로그인 실패
				request.setAttribute("ErrorId", "ErrorId");
				RequestDispatcher rd = request.getRequestDispatcher("login/loginfail.jsp");
				rd.forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
