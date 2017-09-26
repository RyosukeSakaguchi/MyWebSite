package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.SalaryBeans;


public class SalaryMasterDao {

	public static SalaryBeans getSalaryInfo(int id) {
		Connection conn = null;
		SalaryBeans salaryInfo = new SalaryBeans();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();


			// SELECT文を準備
			String sql = "select * from salary_master where id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);

			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し、IDの値がidであるユーザーを探す
			while (rs.next()) {
				salaryInfo.setId(id);
				salaryInfo.setHourlyWage(rs.getInt("hourly_wage"));
				salaryInfo.setOvertimeHourlyWage(rs.getInt("overtime_hourly_wage"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return salaryInfo ;

	}

}
