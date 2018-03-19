package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bean.ExchangeBean;
import bean.UserBean;
import util.DBUtil;

public class ExchangeDAO {
	public static ResourceBundle sql = null;
	private static ExchangeDAO instance;
	static {
		sql = DBUtil.getResourceBundle();
	}

	public static ExchangeDAO getInstance() {
		if (instance == null) {
			instance = new ExchangeDAO();
		}
		return instance;
	}

	// 전체 요청
	public static ArrayList<ExchangeBean> selectAllReq() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<ExchangeBean> all = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("selectAllUser"));
			rset = pstmt.executeQuery();
			all = new ArrayList<ExchangeBean>();

			while (rset.next()) {
				ExchangeBean Exchange = new ExchangeBean();
				Exchange.setT_id(rset.getString(1));
				Exchange.setU_id(rset.getString(2));
				Exchange.setT_time(rset.getString(3));
				Exchange.setT_type(rset.getString(4));
				Exchange.setT_amount(rset.getString(5));
				Exchange.setT_exchange_rate(rset.getString(6));
				Exchange.setT_total(rset.getString(7));

				all.add(Exchange);
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

	// 선택 유저의 요청 검색
	public static ExchangeBean selectUser(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("selectUser"));
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				ExchangeBean Exchange = new ExchangeBean();
				Exchange.setT_id(rset.getString(1));
				Exchange.setU_id(rset.getString(2));
				Exchange.setT_time(rset.getString(3));
				Exchange.setT_type(rset.getString(4));
				Exchange.setT_amount(rset.getString(5));
				Exchange.setT_exchange_rate(rset.getString(6));
				Exchange.setT_total(rset.getString(7));
				return Exchange;
			}
			return null;
		} finally {
			DBUtil.close(conn, pstmt, rset);
		}
	}

	// 신규 회원 가입 , joinController 연동
	public static boolean insertReq(ExchangeBean exchange) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("insertExchange"));
			pstmt.setString(1, exchange.getT_id());
			pstmt.setString(2, exchange.getU_id());
			pstmt.setString(3, exchange.getT_time());
			pstmt.setString(4, exchange.getT_type());
			pstmt.setString(5, exchange.getT_amount());
			pstmt.setString(6, exchange.getT_exchange_rate());
			pstmt.setString(7, exchange.getT_total());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}

}