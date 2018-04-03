package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import bean.ExchangeBean;
import bean.TravelBean;
import bean.UserBean;
import util.DBUtil;

@Component
public class TravelDAO {
	public static ResourceBundle sql = null;
	private static TravelDAO instance;
	static {
		sql = DBUtil.getResourceBundle();
	}

	public static TravelDAO getInstance() {
		if (instance == null) {
			instance = new TravelDAO();
		}
		return instance;
	}

	// 신규 환전 요청
	public static boolean insertTravel(TravelBean travel) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql.getString("insertTravel"));
			pstmt.setString(1, travel.getU_id());
			pstmt.setString(2, travel.getDate_arrive());
			pstmt.setString(3, travel.getDate_depart());
			pstmt.setString(4, travel.getDestination());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			return false;
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}

}