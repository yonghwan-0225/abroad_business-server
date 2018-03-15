package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bean.UserBean;
import util.DBUtil;

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

	
	
	
	public static ArrayList<UserBean> selectAllUser() throws SQLException { // 전체 회원 검색
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
				user.setU_birth(rset.getString(4));
				user.setU_phone(rset.getString(5));
				user.setU_email(rset.getString(6));
				user.setU_address(rset.getString(7));
				user.setU_bank(rset.getString(8));
				user.setU_account(rset.getString(9));
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

	public static UserBean selectUser(String userId) throws SQLException { // 선택 유저 검색
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
				user.setU_birth(rset.getString(4));
				user.setU_phone(rset.getString(5));
				user.setU_email(rset.getString(6));
				user.setU_address(rset.getString(7));
				user.setU_bank(rset.getString(8));
				user.setU_account(rset.getString(9));
				return user;
			}
			return null;
		} finally {
			DBUtil.close(conn, pstmt, rset);
		}
	}

	public static boolean insertUser(UserBean user) throws SQLException { // 신규 회원 가입 , join 컨트롤러에 연동
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("insertUser"));
			pstmt.setString(1, user.getU_id());
			pstmt.setString(2, user.getU_pw());
			pstmt.setString(3, user.getU_name());
			pstmt.setString(4, user.getU_birth());
			pstmt.setString(5, user.getU_phone());
			pstmt.setString(6, user.getU_email());
			pstmt.setString(7, user.getU_address());
			pstmt.setString(8, user.getU_bank());
			pstmt.setString(9, user.getU_account());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}

	// 회원 정보 수정
	public static boolean updateUser(String userId, String password, String phone, String email, String address,
			String bank, String account) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("updateUser"));
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			pstmt.setString(5, phone);
			pstmt.setString(6, email);
			pstmt.setString(7, address);
			pstmt.setString(8, bank);
			pstmt.setString(9, account);

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}

	// 회원 삭제 기능
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

	// 로그인 검증 기능
	public int loginCheck(String id, String password) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String dbPW = "";
		int x = -1;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.getString("loginCheck"));
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				dbPW = rset.getString("pw");
				if (dbPW.equals(password)) {
					x = 1;
				} else {
					x = 0;
				}
			} else {
				x = -1;
			}
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return x;
	}
}