package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import beans.WorkSituationBeans;
import common.UtilLogic;

public class WorkSituationDao {

	/** ログインIDと年と月を受け取り、対応するWorkSituationBeans型のListを返す
	 * @param loginId
	 * @param year
	 * @param month
	 * @return List<WorkSituationBeans>
	 */
	public static List<WorkSituationBeans> findAll(String loginId, int year, int month) {
		Connection conn = null;
		List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "SELECT * FROM work_situation where login_id='" + loginId + "' and YEAR(create_date)='" + year
					+ "' and MONTH(create_date)='" + month + "'";

			// SELECTを実行し、結果表を取得
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			// 結果表に格納されたレコードの内容を
			// WorkSituationBeansインスタンスに設定し、Listインスタンスに追加
			while (rs.next()) {
				int id = rs.getInt("id");
				Date createDate = rs.getDate("create_date");
				String workSitu = rs.getString("work_situ");
				Time workStart = rs.getTime("work_start");
				Time workEnd = rs.getTime("work_end");
				Time breakTime = rs.getTime("break_time");
				Time workTime = rs.getTime("work_time");
				Time overtime = rs.getTime("overtime");
				WorkSituationBeans workSituation = new WorkSituationBeans(id, loginId, createDate, workSitu, workStart,
						workEnd, breakTime, workTime, overtime);
				workSituationList.add(workSituation);
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
		return workSituationList;
	}

	/** ログインIDと年と月と日を受け取り、対応するWorkSituationBeans型のListを返す
	 * @param loginId
	 * @param year
	 * @param month
	 * @param date
	 * @return List<WorkSituationBeans>
	 */
	public static List<WorkSituationBeans> findAll(String loginId, int year, int month, int date) {
		Connection conn = null;
		List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "SELECT * FROM work_situation where login_id='" + loginId + "' and YEAR(create_date)='" + year
					+ "' and MONTH(create_date)='" + month + "' and DAY(create_date)='" + date + "'";

			// SELECTを実行し、結果表を取得
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// 結果表に格納されたレコードの内容を
			// WorkSituationBeansインスタンスに設定し、ArrayListインスタンスに追加
			while (rs.next()) {
				int id = rs.getInt("id");
				Date createDate = rs.getDate("create_date");
				String workSitu = rs.getString("work_situ");
				Time workStart = rs.getTime("work_start");
				Time workEnd = rs.getTime("work_end");
				Time breakTime = rs.getTime("break_time");
				Time workTime = rs.getTime("work_time");
				Time overtime = rs.getTime("overtime");
				WorkSituationBeans workSituation = new WorkSituationBeans(id, loginId, createDate, workSitu, workStart,
						workEnd, breakTime, workTime, overtime);
				workSituationList.add(workSituation);
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
		return workSituationList;
	}

	/** idを受け取り、対応する勤務状況を消去する
	 * @param id
	 */
	public static void situDel(String id) {
		Connection conn = null;

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// DELETE文を準備
			String sql = "DELETE FROM work_situation WHERE id =?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, Integer.parseInt(id));

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

	/** ログインIDと今日の日付と時間の名前を受け取り、対応する時間を返す
	 * @param loginId
	 * @param today
	 * @param timeName
	 * @return Time
	 */
	public static Time getTime(String loginId, Date today, String timeName) {
		Connection conn = null;
		Time time = new Time(Calendar.getInstance().getTimeInMillis());

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "SELECT " + timeName + " FROM work_situation where login_id='" + loginId + "'AND create_date='"
					+ today + "'";

			// SELECTを実行し、結果表を取得
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			//受け取ったレコードをtimeに代入
			while (rs.next()) {
				time = rs.getTime(timeName);
			}
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
		return time;
	}

	/** ログインIDと時間マスターテーブルの勤務開始時間を受けとり、出席状況と勤務開始時間をテーブルに記録
	 * @param loginId
	 * @param workStartMaster
	 */
	public static void workStart(String loginId, Time workStartMaster) {
		Connection conn = null;

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			//今日の日付を時間を取得
			Date today = new Date(Calendar.getInstance().getTimeInMillis());
			Time now = new Time(Calendar.getInstance().getTimeInMillis());

			//Time型の今の時刻nowと時間マスターテーブルの勤務開始時間workStartMasterをint型にする
			int nowInt = UtilLogic.timeToInt(now);
			int workStartMasterInt = UtilLogic.timeToInt(workStartMaster);

			//現在の時刻と時間マスターテーブルの勤務開始時間を比べ遅刻したか、通常の出席で分岐
			String workSituation;
			if (nowInt > workStartMasterInt) {
				workSituation = "遅刻";
			} else {
				workSituation = "出席";
			}

			// INSERT文を準備
			String sql = "INSERT INTO work_situation (login_id, create_date, work_situ ,work_start ,work_end ,break_time ,work_time , overtime) VALUES ('"
					+ loginId + "', '" + today + "','" + workSituation + "', '" + now + "', '', '', '', '')";

			// INSERTを実行し、結果表を取得
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// INSERTを実行
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

	/** ログインIDと休憩時間と時間マスターテーブルの勤務開始時間を受けとり、
	 *  勤務時間が正の値だったら、出席状況と勤務開始時間をテーブルに記録
	 * @param loginId
	 * @param breakTime
	 * @param workEndMaster
	 * @return boolean
	 */
	public static boolean workEnd(String loginId, String breakTime, Time workEndMaster) {
		Connection conn = null;
		boolean result = true;

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			//今日の日付を時間を取得
			Date today = new Date(Calendar.getInstance().getTimeInMillis());
			Time now = new Time(Calendar.getInstance().getTimeInMillis());

			//今日の日付と受け取ったログインIDに関する勤務開始時間をworkStartに代入
			Time workStart = getTime(loginId, today, "work_start");

			//id=1に対する時間マスターテーブルの勤務時間をworkTimeMasterに代入
			Time workTimeMaster = DaoUtil.getTime(1, "work_time");

			//workTimeIntを計算
			int workTimeInt = UtilLogic.timeSubtraction(
					UtilLogic.timeSubtraction(UtilLogic.timeToInt(now), UtilLogic.timeToInt(workStart)),
					UtilLogic.stringTimeToInt(breakTime));

			//計算したworkTimeIntが負の時、falseを返す
			if (workTimeInt < 0) {
				result = false;
				return result;
			}

			//int型の勤務時間をString型の勤務時間に変換(ex : 123456 → 12:34:56)
			String workTime = UtilLogic.intToStringTime(workTimeInt);

			//勤務時間が時間マスターテーブルの勤務時間を超えていたら、残業時間を計算し代入
			String overtime = "00:00:00";
			int workTimeMasterInt = UtilLogic.timeToInt(workTimeMaster);
			if (workTimeMasterInt < workTimeInt) {
				int overtimeInt;
				overtimeInt = UtilLogic.timeSubtraction(workTimeInt, workTimeMasterInt);
				overtime = UtilLogic.intToStringTime(overtimeInt);
			}


			int nowInt = UtilLogic.timeToInt(now);
			int workEndMasterInt = UtilLogic.timeToInt(workEndMaster);

			String yearAndMonthAndDate = new SimpleDateFormat("yyyy-MM-dd").format(today);

			List<WorkSituationBeans> workSituationList = findAll(loginId,
					UtilLogic.yearAndMonthAndDateToYear(yearAndMonthAndDate),
					UtilLogic.yearAndMonthAndDateToMonth(yearAndMonthAndDate),
					UtilLogic.yearAndMonthAndDateToDate(yearAndMonthAndDate));
			String workSituation = "";
			for (WorkSituationBeans w : workSituationList) {
				workSituation = w.getWorkSitu();
			}

			if (nowInt >= workEndMasterInt) {
				workSituation = workSituation + " → 帰宅";
			} else {
				workSituation = workSituation + " → 早退";
			}
			// INSERT文を準備
			String sql = "UPDATE work_situation SET work_situ = '" + workSituation + "' ,work_end = '" + now
					+ "' , break_time = '" + breakTime + "' ,  work_time = '" + workTime + "', overtime = '" + overtime
					+ "' WHERE login_id = '" + loginId + "' and create_date = '" + today + "'";
			// INSERTを実行し、結果表を取得
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// INSERTを実行
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
		return result;
	}

	public static boolean workSituationEdit(int workSituationId, String workStart, String workEnd, String breakTime) {
		Connection conn = null;
		boolean result = true;

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			int workStartInt = UtilLogic.stringTimeToInt(workStart);
			Time workStartMaster = DaoUtil.getTime(1, "work_start");
			int workStartMasterInt = UtilLogic.timeToInt(workStartMaster);
			int workEndInt = UtilLogic.stringTimeToInt(workEnd);
			Time workEndMaster = DaoUtil.getTime(1, "work_end");
			int workEndMasterInt = UtilLogic.timeToInt(workEndMaster);
			String workSituation;

			if (workEndInt == 0) {
				if (workStartInt > workStartMasterInt) {
					workSituation = "遅刻";
				} else {
					workSituation = "出席";
				}
			} else {
				if (workStartInt > workStartMasterInt) {
					if (workEndInt >= workEndMasterInt) {
						workSituation = "遅刻 → 退社";
					} else {
						workSituation = "遅刻 → 早退";
					}
				} else {
					if (workEndInt >= workEndMasterInt) {
						workSituation = "出席 → 退社";
					} else {
						workSituation = "出席 → 早退";
					}
				}
			}
			String overtime = "00:00:00";
			String workTime = "00:00:00";


			if (UtilLogic.stringTimeToInt(workEnd) != 0) {

				int workTimeInt = UtilLogic.timeSubtraction(
						UtilLogic.timeSubtraction(UtilLogic.stringTimeToInt(workEnd), workStartInt),
						UtilLogic.stringTimeToInt(breakTime));

				if (workTimeInt < 0) {
					result = false;
					return result;
				}
				workTime = UtilLogic.intToStringTime(workTimeInt);

				Time workTimeMaster = DaoUtil.getTime(1, "work_time");
				int workTimeMasterInt = UtilLogic.timeToInt(workTimeMaster);

				if (workTimeMasterInt < workTimeInt) {
					int overtimeInt;
					overtimeInt = UtilLogic.timeSubtraction(workTimeInt, workTimeMasterInt);
					overtime = UtilLogic.intToStringTime(overtimeInt);
				}
			}

			// INSERT文を準備
			String sql = "UPDATE work_situation SET work_situ = '" + workSituation + "', work_start = '" + workStart
					+ "' ,work_end = '" + workEnd + "' , break_time = '" + breakTime + "' , work_time = '" + workTime
					+ "' , overtime = '" + overtime + "' WHERE id = '" + workSituationId + "'";
			// INSERTを実行し、結果表を取得
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// INSERTを実行
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
		return result;
	}

	public static boolean isBeforeWorking(String loginId) {
		Connection conn = null;

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd");
		String todayString = d.format(today);

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select work_situ from work_situation where login_id = ? and create_date = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, todayString);

			// SELECTを実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し
			if (rs.next()) {
				return false;
			}
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
		return true;
	}

	public static boolean isWorking(String loginId) {
		Connection conn = null;

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd");
		String todayString = d.format(today);

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select work_situ from work_situation where login_id = ? and create_date = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, todayString);

			// SELECTを実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し
			if (rs.next()) {
				if (rs.getString("work_situ").length() == 2) {
					return true;
				}
			}
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
		return false;
	}

	public static boolean isAfterWorking(String loginId) {
		Connection conn = null;

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd");
		String todayString = d.format(today);

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select work_situ from work_situation where login_id = ? and create_date = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, todayString);

			// SELECTを実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し
			if (rs.next()) {
				if (rs.getString("work_situ").length() == 7) {
					return true;
				}
			}
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
		return false;
	}

	public static boolean isOverTime(String timeName) {
		Connection conn = null;
		Time now = new Time(Calendar.getInstance().getTimeInMillis());

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select " + timeName + " from time_master where id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, 1);

			// SELECTを実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し
			if (rs.next()) {
				String timeString = rs.getString(timeName);
				if (UtilLogic.timeToInt(now) > UtilLogic.stringTimeToInt(timeString)) {
					return true;
				}
			}
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
		return false;
	}

	public static void userSituDel(String loginId) {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// DELETE文を準備
			String sql = "DELETE FROM work_situation WHERE login_id =?";
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

	public static void allUserSituDel() {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// DELETE文を準備
			String sql = "DELETE FROM work_situation";
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
