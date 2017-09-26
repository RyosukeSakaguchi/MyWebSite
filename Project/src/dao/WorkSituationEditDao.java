package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beans.WorkSituationEditBeans;
import common.UtillLogic;

public class WorkSituationEditDao {

	public static List<WorkSituationEditBeans> findAll(String loginId) {
		Connection conn = null;
		List<WorkSituationEditBeans> workSituationEditList = new ArrayList<WorkSituationEditBeans>();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "SELECT * FROM work_situation_edit where login_id='" + loginId + "'";

			// SELECTを実行し、結果表を取得
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			// 結果表に格納されたレコードの内容を
			// WorkSituationBeansインスタンスに設定し、ArrayListインスタンスに追加
			while (rs.next()) {
				int id = rs.getInt("id");
				Timestamp editDate = rs.getTimestamp("edit_time");
				String editContent = rs.getString("edit_content");
				WorkSituationEditBeans workSituationEdit = new WorkSituationEditBeans(id, loginId, editDate,
						editContent);
				workSituationEditList.add(workSituationEdit);
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
		return workSituationEditList;
	}

	public static void setEditHistory(String loginId, int createDateYear, int createDateMonth) {
		Connection conn = null;

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd");
		String todayString = d.format(today);

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// INSERT文を準備
			String sql = "INSERT INTO work_situation_edit (login_id, edit_time, edit_content) VALUES (?, ?, ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, todayString);
			pStmt.setString(3, createDateYear + "年" + createDateMonth + "月の全勤務データを削除しました。");

			// DELETEを実行
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void setEditHistory(String loginId, int createDateYear, int createDateMonth, int createDateDate) {
		Connection conn = null;

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd");
		String todayString = d.format(today);

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// INSERT文を準備
			String sql = "INSERT INTO work_situation_edit (login_id, edit_time, edit_content) VALUES (?, ?, ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, todayString);
			pStmt.setString(3, createDateYear + "年" + createDateMonth + "月" + createDateDate + "日の勤務データを削除しました。");

			// DELETEを実行
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void setEditHistory(String loginId, String time, String timeBefore, int createDateYear,
			int createDateMonth, int createDateDate, String timeName) {
		Connection conn = null;

		if (!time.equals(timeBefore)) {
			Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd");
			String todayString = d.format(today);

			try {
				// データベースへ接続
				conn = DBManager.getConnection();

				// INSERT文を準備
				String sql = "INSERT INTO work_situation_edit (login_id, edit_time, edit_content) VALUES (?, ?, ?)";

				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, loginId);
				pStmt.setString(2, todayString);
				pStmt.setString(3, createDateYear + "年" + createDateMonth + "月" + createDateDate + "日の"
						+ UtillLogic.timeNameJa(timeName) + "を" + timeBefore + "から" + time + "に変更しました。");

				// DELETEを実行
				pStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// データベース切断
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void userSituEditDel(String loginId) {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// DELETE文を準備
			String sql = "DELETE FROM work_situation_edit WHERE login_id =?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);

			// DELETEを実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
