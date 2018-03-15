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

	static {
		sql = DBUtil.getResourceBundle();
	}

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
				user.setU_birth(rset.getString(4));
				user.setU_phone(rset.getString(5));
				user.setU_address(rset.getString(6));
				user.setU_bank(rset.getString(7));
				user.setU_account(rset.getString(8));
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
				user.setU_birth(rset.getString(4));
				user.setU_phone(rset.getString(5));
				user.setU_address(rset.getString(6));
				user.setU_bank(rset.getString(7));
				user.setU_account(rset.getString(8));
				return user;
			}
			return null;
		} finally {
			DBUtil.close(conn, pstmt, rset);
		}
	}

	public static boolean insertUser(UserBean user) throws SQLException {
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
			pstmt.setString(6, user.getU_address());
			pstmt.setString(7, user.getU_bank());
			pstmt.setString(8, user.getU_account());
			
			
			if (pstmt.executeUpdate()==1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}
	
	public static boolean updateUser(String userId, String password, String phone, String address, String bank, String account) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("updateUser"));
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			pstmt.setString(5, phone);
			pstmt.setString(6, address);
			pstmt.setString(7, bank);
			pstmt.setString(8, account);
			
			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}
	

	
	public static int deleteUser(String userId, String password) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String dbpw = "";
		int x = -1;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.getString("loginCheck"));
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				dbpw = rset.getString("password");
				if (dbpw.equals(password)) {
					pstmt = con.prepareStatement(sql.getString("deletePerson"));
					pstmt.setString(1, userId);
					pstmt.executeUpdate();
					con.commit();
					x = 1; // 삭제 성공
				} else {
					x = 0; // 비밀번호 다름
				}
			}
			return x;

		} catch (Exception sqle) {
			try {
				con.rollback(); // 오류시 롤백
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException(sqle.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
		
}