package biz.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import model.UserDAO;

@WebServlet("/api/signup")
public class JoinController extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		
		System.out.println(request.getParameter("id")+" "+ request.getParameter("pw") + "  " + request.getParameter("email") + " " + request.getParameter("phone"));
		
		try {
			UserDAO.insertUser(new UserBean(request.getParameter("id"), request.getParameter("pw"),
						request.getParameter("name"), request.getParameter("phone"),
						request.getParameter("email"), request.getParameter("address"), request.getParameter("bank"),
						request.getParameter("account")));
		} catch (SQLException e) {
			response.sendRedirect("join/joinfailView.jsp");
			e.printStackTrace();
		}
	}
}
