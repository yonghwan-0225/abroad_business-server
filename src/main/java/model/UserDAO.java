package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import bean.UserBean;
import util.DBUtil;


@Component
public class UserDAO {
	public static ResourceBundle sql = null;
	private static UserDAO instance;
	static {
		sql = DBUtil.getResourceBundle();
	}

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	// 전체 회원 검색
	public static ArrayList<UserBean> selectAllUser() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<UserBean> all = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("selectAllUser"));
			rset = pstmt.executeQuery();
			all = new ArrayList<UserBean>();

			while (rset.next()) {
				UserBean user = new UserBean();
				user.setU_id(rset.getString(1));
				user.setU_pw(rset.getString(2));
				user.setU_name(rset.getString(3));
				user.setU_phone(rset.getString(4));
				user.setU_email(rset.getString(5));
				user.setU_address(rset.getString(6));
				all.add(user);
			}
			if (all.size() != 0) {
				return all;
			} else {
				return null;
			}
		} finally {
			DBUtil.close(conn, pstmt, rset);
		}
	}

	// 선택 유저 검색
	public static UserBean selectUser(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("selectUser"));
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				UserBean user = new UserBean();
				user.setU_id(rset.getString(1));
				user.setU_pw(rset.getString(2));
				user.setU_name(rset.getString(3));
				user.setU_phone(rset.getString(4));
				user.setU_email(rset.getString(5));
				user.setU_address(rset.getString(6));
				return user;
			}
		} finally {
			DBUtil.close(conn, pstmt, rset);
		}
		return null;
	}

	// 신규 회원 가입 , joinController 연동
	public static boolean joinUser(UserBean user) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("joinUser"));
			pstmt.setString(1, user.getU_id());
			pstmt.setString(2, user.getU_pw());
			pstmt.setString(3, user.getU_name());
			pstmt.setString(4, user.getU_phone());
			pstmt.setString(5, user.getU_email());
			pstmt.setString(6, user.getU_address());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}

	// 회원 정보 수정 ModifyController 연동
	public static boolean updateUser(String userId, String password, String name, String phone, String email, String address) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("updateUser"));
			pstmt.setString(1, password);
			pstmt.setString(2, phone);
			pstmt.setString(3, address);
			pstmt.setString(4, email);
			pstmt.setString(5, userId);

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}

	// 회원 삭제 기능 RemoveController
	public static boolean deleteUser(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("deleteUser"));
			pstmt.setString(1, userId);

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}

	// 로그인 검증 기능 LoginController
	public UserBean loginCheck(String id, String password) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String dbPW = "";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.getString("loginCheck"));
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				dbPW = rset.getString(2);
				if (dbPW.equals(password)) {
					return new UserBean(rset.getString(1),rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), rset.getString(6));
				}
			} 
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return null;
	}

}