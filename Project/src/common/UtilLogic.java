package common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import beans.SalaryBeans;
import beans.UserBeans;
import beans.WorkSituationBeans;
import dao.SalaryMasterDao;
import dao.WorkSituationDao;

public class UtilLogic {

	/**
	 * パスワードを暗号化するメソッド
	 *
	 * @param password
	 * @return 暗号化されたパスワード
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrpt(String password) throws NoSuchAlgorithmException {
		// ハッシュを生成したい元の文字列
		String source = password;
		// ハッシュ生成前にバイト配列に置き換える際のCharset
		Charset charset = StandardCharsets.UTF_8;
		// ハッシュアルゴリズム
		String algorithm = "MD5";

		// ハッシュ生成処理
		byte[] bytes = MessageDigest.getInstance(algorithm).digest(source.getBytes(charset));
		String result = DatatypeConverter.printHexBinary(bytes);

		return result;
	}

	/**
	 * + * 渡された文字列がnullまたは空文字だった場合trueを返す
	 * * @param str
	 * * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (null == str || str == "") {
			return true;
		}
		return false;
	}

	public static int timeToInt(Time time) {

		return  Integer.parseInt(time.toString().replaceAll(":", ""));

	}

	public static int stringTimeToInt(String numString) {

		return  Integer.parseInt(numString.replaceAll(":", ""));

	}

	public static String intToStringTime(int timeInt) {
		String timeString = String.valueOf(timeInt);
		while(true) {
			if(timeString.length() == 6) {
				break;
			}
			timeString = "0" + timeString;
		}
		StringBuilder timeStringBuilder = new StringBuilder(timeString);
		if(Integer.parseInt(timeStringBuilder.substring(4).toString()) >= 60) {
			timeInt = Integer.parseInt(timeStringBuilder.toString()) - 40;
			timeString = String.valueOf(timeInt);
			while(true) {
				if(timeString.length() == 6) {
					break;
				}
				timeString = "0" + timeString;
			}
			timeStringBuilder = new StringBuilder(timeString);
		}
		if(Integer.parseInt(timeStringBuilder.substring(2, 4).toString()) >= 60) {
			timeInt = Integer.parseInt(timeStringBuilder.toString()) + 4000;
			timeString = String.valueOf(timeInt);
			while(true) {
				if(timeString.length() == 6) {
					break;
				}
				timeString = "0" + timeString;
			}
			timeStringBuilder = new StringBuilder(timeString);
		}
		timeStringBuilder.reverse();
		timeStringBuilder.insert(2, ":");
		timeStringBuilder.insert(5, ":");
		timeStringBuilder.reverse();
		return timeStringBuilder.toString();
	}

	public static int timeSubtraction(int timeInt1, int timeInt2) {
		int underTwo1 = timeInt1 % 100;
		int underTwo2 = timeInt2 % 100;
		int middle1 = (timeInt1 - underTwo1) % 10000;
		int middle2 = (timeInt2 - underTwo2) % 10000;

		if(underTwo1 < underTwo2 && middle1 < middle2) {
			return  timeInt1 - timeInt2 - 4040;
		}else if(middle1 < middle2) {
			return  timeInt1 - timeInt2 - 4000;
		}else if(underTwo1 < underTwo2) {
			return  timeInt1 - timeInt2 - 40;
		}else {
			return  timeInt1 - timeInt2;
		}
	}
	public static int timeAddition(int timeInt1, int timeInt2) {
		int underTwo1 = timeInt1 % 100;
		int underTwo2 = timeInt2 % 100;
		int middle1 = (timeInt1 - underTwo1) % 10000;
		int middle2 = (timeInt2 - underTwo2) % 10000;

		if(underTwo1 + underTwo2 >= 60 && middle1 + middle2 >= 5900) {
			return  timeInt1 + timeInt2 + 4040;
		}else if(middle1 + middle2 >= 6000) {
			return  timeInt1 + timeInt2 + 4000;
		}else if(underTwo1 + underTwo2 >= 60) {
			return  timeInt1 + timeInt2 + 40;
		}else {
			return  timeInt1 + timeInt2;
		}
	}

	public static String totalWorkTime(List<WorkSituationBeans> workSituationList) {
		int totalIntTime = 0;
		for(WorkSituationBeans workSituation : workSituationList){
			int WorkTimeInt = timeToInt(workSituation.getWorkTime());
			totalIntTime = timeAddition(totalIntTime , WorkTimeInt);
		}
		return intToStringTime(totalIntTime);
	}

	public static String totalOvertime(List<WorkSituationBeans> workSituationList) {
		int totalIntTime = 0;
		for(WorkSituationBeans workSituation : workSituationList){
			int OvertimeInt = timeToInt(workSituation.getOvertime());
			totalIntTime = timeAddition(totalIntTime , OvertimeInt);
		}
		return intToStringTime(totalIntTime);
	}

	public static String timeNameJa(String timeName) {
		String timeNameJa = "";
		if(timeName.equals("work_start")) {
			timeNameJa = "勤務開始時間";
		}else if(timeName.equals("work_end")) {
			timeNameJa = "勤務終了時間";
		}else if(timeName.equals("break_time")) {
			timeNameJa = "休憩時間";
		}

		return timeNameJa;
	}

	public static int getMonthlySalary(String loginId, String position, int year, int month){
		List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
		workSituationList = WorkSituationDao.findAll(loginId, year, month);
		String totalWorkTime = totalWorkTime(workSituationList);
		String totalOvertime = totalOvertime(workSituationList);
		int totalWorkTimeInt = stringTimeToInt(totalWorkTime);
		int totalOvertimeInt = stringTimeToInt(totalOvertime);
		int diffTotalTimeInt = timeSubtraction(totalWorkTimeInt, totalOvertimeInt);
		int salary = (int)(calSalary(diffTotalTimeInt, position) + calOvertimeSalary(totalOvertimeInt, position));

		return salary;
	}

	public static double calSalary(int timeInt, String position){
		int underTwo = timeInt % 100;
		int middle = (timeInt - underTwo) % 10000;
		int middleTwo = middle / 100;
		int topTwo = (timeInt - middle - underTwo) / 10000;
		SalaryBeans salaryInfo = SalaryMasterDao.getSalaryInfo(position);
		return (salaryInfo.getHourlyWage()) * ( topTwo + middleTwo / 60.0 + underTwo / 3600.0);
	}

	public static double calOvertimeSalary(int timeInt, String position){
		int underTwo = timeInt % 100;
		int middle = (timeInt - underTwo) % 10000;
		int middleTwo = middle / 100;
		int topTwo = (timeInt - middle - underTwo) / 10000;
		SalaryBeans salaryInfo = SalaryMasterDao.getSalaryInfo(position);
		return (salaryInfo.getOvertimeHourlyWage()) * ( topTwo + middleTwo / 60.0 + underTwo / 3600.0);
	}

	public static int yearAndMonthToYear(String yearAndMonth) {
		int yearAndMonthInt = Integer.parseInt(yearAndMonth.replaceAll("-", ""));
		int year = (yearAndMonthInt - yearAndMonthToMonth(yearAndMonth)) / 100;
		return  year;
	}

	public static int yearAndMonthToMonth(String yearAndMonth) {
		int yearAndMonthInt = Integer.parseInt(yearAndMonth.replaceAll("-", ""));
		int month = yearAndMonthInt % 100;
		return month;
	}

	public static int yearAndMonthAndDateToYear(String yearAndMonthAndDate) {
		int yearAndMonthAndDateInt = Integer.parseInt(yearAndMonthAndDate.replaceAll("-", ""));
		int year = (yearAndMonthAndDateInt - yearAndMonthAndDateToMonth(yearAndMonthAndDate) - yearAndMonthAndDateToDate(yearAndMonthAndDate)) / 10000;
		return  year;
	}

	public static int yearAndMonthAndDateToMonth(String yearAndMonthAndDate) {
		int yearAndMonthAndDateInt = Integer.parseInt(yearAndMonthAndDate.replaceAll("-", ""));
		int month = ((yearAndMonthAndDateInt - yearAndMonthAndDateToDate(yearAndMonthAndDate)) % 10000) / 100;
		return  month;
	}

	public static int yearAndMonthAndDateToDate(String yearAndMonthAndDate) {
		int yearAndMonthAndDateInt = Integer.parseInt(yearAndMonthAndDate.replaceAll("-", ""));
		int date = yearAndMonthAndDateInt % 100;
		return  date;
	}

	public static List<UserBeans> userListSort(List<UserBeans> userList) {

		ArrayList<UserBeans> users = new ArrayList<UserBeans>();
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		String yearAndMonthAndDate = new SimpleDateFormat("yyyy-MM-dd").format(today);

		for(UserBeans user : userList) {
			List<WorkSituationBeans> workSituationList = WorkSituationDao.findAll(user.getLoginId(), UtilLogic.yearAndMonthAndDateToYear(yearAndMonthAndDate), UtilLogic.yearAndMonthAndDateToMonth(yearAndMonthAndDate), UtilLogic.yearAndMonthAndDateToDate(yearAndMonthAndDate)) ;
			for(WorkSituationBeans w : workSituationList) {
				if(w.getWorkSitu().length() == 2) {
					users.add(user);
				}
			}
		}

		for(UserBeans user1 : userList) {
			boolean result = true;
			for(UserBeans user2 : users) {
				if(user1.equals(user2)) {
					result = false;
				}
			}
			if(result) {
				users.add(user1);
			}
		}

		return users;
	}


}
