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

public class JoinController extends HttpServlet {

//	protected void service(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		String join = request.getParameter("join");
//		String cancel = request.getParameter("cancel");
//		try {
//			if (join.equals("join")) {
//				UserDAO.joinUser(new UserBean(request.getParameter("id"), request.getParameter("pw"),
//						request.getParameter("name"), request.getParameter("birth"), request.getParameter("phone"),
//						request.getParameter("email"), request.getParameter("address"), request.getParameter("bank"),
//						request.getParameter("account")));
//				response.sendRedirect("ShelterEQ.html");
//			} else if (cancel.equals("cancel")) {
//				response.sendRedirect("ShelterEQ.html");
//			}
//		} catch (SQLException e) {
//			response.sendRedirect("join/joinfailView.jsp");
//			e.printStackTrace();
//		}
//	}
}
