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
import common.UtilLogic;

public class WorkSituationEditDao {

	/** ログインIDを受け取り、そのユーザーに関する勤務状況編集履歴のリストを受け取る
	 * @param loginId
	 * @return List<WorkSituationEditBeans>
	 */
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
			// WorkSituationEditBeansインスタンスに設定し、Listインスタンスに追加
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

	/** ログインIDと削除した履歴の年と月を受け取り、勤務状況編集履歴テーブルに挿入
	 * @param loginId
	 * @param createDateYear
	 * @param createDateMonth
	 */
	public static void setEditHistory(String loginId, int createDateYear, int createDateMonth) {
		Connection conn = null;

		//今日の日付と時間を"yyy-MM-dd HH:mm:ss"の形でtodayStringに代入
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
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

	/** ログインIDと削除した履歴の年と月と日を受け取り、勤務状況編集履歴テーブルに挿入
	 * @param loginId
	 * @param createDateYear
	 * @param createDateMonth
	 * @param createDateDate
	 */
	public static void setEditHistory(String loginId, int createDateYear, int createDateMonth, int createDateDate) {
		Connection conn = null;

		//今日の日付と時間を"yyy-MM-dd HH:mm:ss"の形でtodayStringに代入
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
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

	/** ログインIDを受け取り、編集履歴を勤務状況編集履歴テーブルに挿入
	 * @param loginId
	 * @param time
	 * @param timeBefore
	 * @param createDateYear
	 * @param createDateMonth
	 * @param createDateDate
	 * @param timeName
	 */
	public static void setEditHistory(String loginId, String time, String timeBefore, int createDateYear,
			int createDateMonth, int createDateDate, String timeName) {
		Connection conn = null;

		//テーブルへの挿入は時間を変更した時のみ行う
		if (!time.equals(timeBefore)) {

			//今日の日付と時間を"yyy-MM-dd HH:mm:ss"の形でtodayStringに代入
			Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
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
						+ UtilLogic.timeNameJa(timeName) + "を" + timeBefore + "から" + time + "に変更しました。");

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

	/** ログインIDを受け取り、対応する勤務状況編集履歴を消去する
	 * @param loginId
	 */
	/**
	 * @param loginId
	 */
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

	/**全てのユーザーの勤務状況編集履歴を消去する
	 *
	 */
	public static void allUserSituEditDel() {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// DELETE文を準備
			String sql = "DELETE FROM work_situation_edit";
			PreparedStatement pStmt = conn.prepareStatement(sql);

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
