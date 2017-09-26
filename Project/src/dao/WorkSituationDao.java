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
import common.UtillLogic;

public class WorkSituationDao {

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

	public static void workStart(String loginId, Time workStartMaster) {
		Connection conn = null;

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			Date today = new Date(Calendar.getInstance().getTimeInMillis());
			Time now = new Time(Calendar.getInstance().getTimeInMillis());

			int nowInt = Integer.parseInt(now.toString().replaceAll(":", ""));
			int workStartMasterInt = Integer.parseInt(workStartMaster.toString().replaceAll(":", ""));

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

	public static boolean workEnd(String loginId, String breakTime, Time workEndMaster) {
		Connection conn = null;
		boolean result = true;

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			Date today = new Date(Calendar.getInstance().getTimeInMillis());
			Time now = new Time(Calendar.getInstance().getTimeInMillis());

			Time workStart = getTime(loginId, today, "work_start");
			Time workTimeMaster = DaoUtil.getTime(1, "work_time");

			int workTimeInt = UtillLogic.timeSubtraction(
					UtillLogic.timeSubtraction(UtillLogic.timeToInt(now), UtillLogic.timeToInt(workStart)),
					UtillLogic.stringTimeToInt(breakTime));

			if (workTimeInt < 0) {
				result = false;
				return result;
			}

			String workTime = UtillLogic.intToStringTime(workTimeInt);

			String overtime = "00:00:00";

			int workTimeMasterInt = UtillLogic.timeToInt(workTimeMaster);

			if (workTimeMasterInt < workTimeInt) {
				int overtimeInt;
				overtimeInt = UtillLogic.timeSubtraction(workTimeInt, workTimeMasterInt);
				overtime = UtillLogic.intToStringTime(overtimeInt);
			}

			int nowInt = Integer.parseInt(now.toString().replaceAll(":", ""));
			int workEndMasterInt = Integer.parseInt(workEndMaster.toString().replaceAll(":", ""));

			String yearAndMonthAndDate = new SimpleDateFormat("yyyy-MM-dd").format(today);

			List<WorkSituationBeans> workSituationList = findAll(loginId, UtillLogic.yearAndMonthAndDateToYear(yearAndMonthAndDate), UtillLogic.yearAndMonthAndDateToMonth(yearAndMonthAndDate), UtillLogic.yearAndMonthAndDateToDate(yearAndMonthAndDate)) ;
			String workSituation = "";
			for(WorkSituationBeans w : workSituationList) {
				workSituation = w.getWorkSitu();
			}

			if(nowInt >= workEndMasterInt) {
				workSituation = workSituation + " → 帰宅";
			}else {
				workSituation = workSituation + " → 早退";
			}
			// INSERT文を準備
			String sql = "UPDATE work_situation SET work_situ = '" + workSituation + "' ,work_end = '" + now + "' , break_time = '" + breakTime
					+ "' ,  work_time = '" + workTime + "', overtime = '" + overtime + "' WHERE login_id = '" + loginId
					+ "' and create_date = '" + today + "'";
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

			int workStartInt = UtillLogic.stringTimeToInt(workStart);
			Time workStartMaster = DaoUtil.getTime(1, "work_start");
			int workStartMasterInt = UtillLogic.timeToInt(workStartMaster);
			int workEndInt = UtillLogic.stringTimeToInt(workEnd);
			Time workEndMaster = DaoUtil.getTime(1, "work_end");
			int workEndMasterInt = UtillLogic.timeToInt(workEndMaster);
			String workSituation;
			if (workStartInt > workStartMasterInt) {
				if(workEndInt >= workEndMasterInt) {
					workSituation = "遅刻 → 退社";
				}else {
					workSituation = "遅刻 → 早退";
				}
			} else {
				if(workEndInt >= workEndMasterInt) {
					workSituation = "出席 → 退社";
				}else {
					workSituation = "出席 → 早退";
				}
			}
			String overtime = "00:00:00";
			String workTime = "00:00:00";

			if (UtillLogic.stringTimeToInt(workEnd) != 0) {
				int workTimeInt = UtillLogic.timeSubtraction(
						UtillLogic.timeSubtraction(UtillLogic.stringTimeToInt(workEnd), workStartInt),
						UtillLogic.stringTimeToInt(breakTime));

				if (workTimeInt < 0) {
					result = false;
					return result;
				}

				workTime = UtillLogic.intToStringTime(workTimeInt);



				Time workTimeMaster = DaoUtil.getTime(1, "work_time");
				int workTimeMasterInt = UtillLogic.timeToInt(workTimeMaster);

				if (workTimeMasterInt < workTimeInt) {
					int overtimeInt;
					overtimeInt = UtillLogic.timeSubtraction(workTimeInt, workTimeMasterInt);
					overtime = UtillLogic.intToStringTime(overtimeInt);
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
				if(rs.getString("work_situ").length() == 2) {
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

	public static boolean isGetTodayWorkTime(String loginId) {
		Connection conn = null;

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat d = new SimpleDateFormat("yyy-MM-dd");
		String todayString = d.format(today);

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select work_time from work_situation where login_id = ? and create_date = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, todayString);

			// SELECTを実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し
			if (rs.next()) {
				if (rs.getString("work_time").equals("00:00:00")) {
					return false;
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
		return true;
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
				if (UtillLogic.timeToInt(now) > UtillLogic.stringTimeToInt(timeString)) {
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
			System.out.println(pStmt);

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
