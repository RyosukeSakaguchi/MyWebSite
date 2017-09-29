package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.SalaryBeans;


public class SalaryMasterDao {

	/** 給料マスターテーブルのpositionに対応するSalaryBeansを返す
	 * @param position
	 * @return SalaryBeans
	 */
	public static SalaryBeans getSalaryInfo(String position) {
		Connection conn = null;
		SalaryBeans salaryInfo = new SalaryBeans();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select * from salary_master where position = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, position);

			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコードの内容を
			// SalaryBeansインスタンスに設定し、SalaryBeansインスタンスに追加
			while (rs.next()) {
				salaryInfo.setId(rs.getInt("id"));
				salaryInfo.setPosition(rs.getString("position"));
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
