package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import bean.ExchangeBean;
import bean.UserBean;
import util.DBUtil;

@Component
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

	// 전체 환전 요청
	
	public static ArrayList<ExchangeBean> selectAllReq() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<ExchangeBean> all = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("selectAllReq"));
			rset = pstmt.executeQuery();
			all = new ArrayList<ExchangeBean>();

			while (rset.next()) {
				ExchangeBean Exchange = new ExchangeBean();
				Exchange.setT_id(rset.getString(1));
				Exchange.setU_id(rset.getString(2));
				Exchange.setT_time(rset.getString(3));
				Exchange.setT_type(rset.getString(4));
				Exchange.setT_status(rset.getString(5));
				Exchange.setT_amount(rset.getString(6));
				Exchange.setT_exchange_rate(rset.getString(7));
				Exchange.setT_total(rset.getString(8));

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


	
	// 요청 유형별 검색
		public static ExchangeBean selectTypeReq(String Reqtype) throws SQLException {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;

			try {
				conn = DBUtil.getConnection();
				pstmt = conn.prepareStatement(sql.getString("selectTypeReq"));
				pstmt.setString(1, Reqtype);
				rset = pstmt.executeQuery();

				if (rset.next()) {
					ExchangeBean Exchange = new ExchangeBean();
					Exchange.setT_id(rset.getString(1));
					Exchange.setU_id(rset.getString(2));
					Exchange.setT_time(rset.getString(3));
					Exchange.setT_type(rset.getString(4));
					Exchange.setT_status(rset.getString(5));
					Exchange.setT_amount(rset.getString(6));
					Exchange.setT_exchange_rate(rset.getString(7));
					Exchange.setT_total(rset.getString(8));
					return Exchange;
				}
				return null;
			} finally {
				DBUtil.close(conn, pstmt, rset);
			}
		}

	// 신규 환전 요청
	public static boolean insertReq(ExchangeBean exchange) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("insertExchange"));
			pstmt.setString(1, exchange.getU_id());
			pstmt.setString(2, exchange.getT_time());
			pstmt.setString(3, exchange.getT_type());
			pstmt.setString(4, exchange.getT_status());
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
	// 선택 유저 검색
	public static String selectT_id(String userId , String time) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String data = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("orderIdReq"));
			pstmt.setString(1, userId);
			pstmt.setString(2, time);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				data = rset.getString(1);
				
			}
		} finally {
			DBUtil.close(conn, pstmt, rset);
		}
		return data;
	}
	
//	public static String selectUserReq(String userId) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rset = null;
//		String data = null;
//
//		try {
//			conn = DBUtil.getConnection();
//			pstmt = conn.prepareStatement(sql.getString("selectUserReq"));
//			pstmt.setString(1, userId);
//			rset = pstmt.executeQuery();
//
//			if (rset.next()) {
//				data = rset.getString(1);
//				data = rset.getString(2);
//				data = rset.getString(3);
//				data = rset.getString(4);
//				data = rset.getString(5);
//				data = rset.getString(6);
//				data = rset.getString(7);
//			}
//		} finally {
//			DBUtil.close(conn, pstmt, rset);
//		}
//		return data;
//	}
//	
	// 선택 유저의 요청 검색
	
	public static ArrayList selectUserReq(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("selectUserReq"));
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			ArrayList all =  new ArrayList();
			
			while (rset.next()) {
				ExchangeBean Exchange = new ExchangeBean();
				Exchange.setT_id(rset.getString(1));
				Exchange.setT_type(rset.getString(2));
				Exchange.setT_exchange_rate(rset.getString(3));
				Exchange.setT_amount(rset.getString(4));
				Exchange.setT_total(rset.getString(5));
				Exchange.setT_time(rset.getString(6));
				Exchange.setT_status(rset.getString(7));
				all.add(Exchange);
			}
			return all;
		} finally {
			DBUtil.close(conn, pstmt, rset);
		}
	}
	

}