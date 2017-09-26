package common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import beans.UserBeans;
import beans.WorkSituationBeans;;

public class CSVFileWrite extends UtillLogic {

	public static void getSalary(List<UserBeans> userList, int year, int month) {

		try {
			// 出力先を作成する
			OutputStream fos = new FileOutputStream(
					"/Users/sakaguchimikiya/Documents/github/MyWebSite/CSV/" + year + "年" + month + "月.csv", false); // ※１
			fos.write( 0xef );
			fos.write( 0xbb );
			fos.write( 0xbf );
			PrintWriter pw = new PrintWriter(new OutputStreamWriter( fos, "UTF8" ));

			pw.print("ユーザー名");
			pw.print(",");
			pw.print("月給");
			pw.println();

			for (UserBeans user : userList) {
				if (user.getId() != 1) {
					// 内容を指定する
					pw.print(user.getName());
					pw.print(",");
					pw.print(getMonthlySalary(user.getLoginId(), year, month));
					pw.print("円");
					pw.println();
				}
			}

			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}

	public static void getMonthlyWorkSituation(List<WorkSituationBeans> workSituationList, String userName, int year,
			int month, String titalWorkTime, String titalOvertime) {

		try {
			// 出力先を作成する
			OutputStream fos = new FileOutputStream("/Users/sakaguchimikiya/Documents/github/MyWebSite/CSV/" + year + "年" + month
					+ "月" + userName + ".csv", false); // ※１
			fos.write( 0xef );
			fos.write( 0xbb );
			fos.write( 0xbf );
			PrintWriter pw = new PrintWriter(new OutputStreamWriter( fos, "UTF8" ));

			// 内容を指定する
			pw.print("日付");
			pw.print(",");
			pw.print("勤務状況");
			pw.print(",");
			pw.print("勤務開始時間");
			pw.print(",");
			pw.print("勤務終了時間");
			pw.print(",");
			pw.print("休憩時間");
			pw.print(",");
			pw.print("勤務時間");
			pw.print(",");
			pw.print("残業時間");
			pw.println();

			for (WorkSituationBeans workSituation : workSituationList) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd");
				String CreateDate = sdf.format(workSituation.getCreateDate());

				pw.print(CreateDate);
				pw.print(",");
				pw.print(workSituation.getWorkSitu());
				pw.print(",");
				pw.print(workSituation.getWorkStart());
				pw.print(",");
				pw.print(workSituation.getWorkEnd());
				pw.print(",");
				pw.print(workSituation.getBreakTime());
				pw.print(",");
				pw.print(workSituation.getWorkTime());
				pw.print(",");
				pw.print(workSituation.getOvertime());
				pw.println();
			}

			pw.print("総勤務時間");
			pw.print(",");
			pw.print(titalWorkTime);
			pw.println();

			pw.print("総残業時間");
			pw.print(",");
			pw.print(titalOvertime);
			pw.println();

			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}

	public static void getDailyWorkSituation(List<WorkSituationBeans> workSituationList, String userName, int year, int month, int date) {

		try {
			// 出力先を作成する
			OutputStream fos = new FileOutputStream("/Users/sakaguchimikiya/Documents/github/MyWebSite/CSV/" + year + "年" + month
					+ "月" + date + "日" +userName + ".csv", false); // ※１
			fos.write( 0xef );
			fos.write( 0xbb );
			fos.write( 0xbf );
			PrintWriter pw = new PrintWriter(new OutputStreamWriter( fos, "UTF8" ));

			// 内容を指定する
			pw.print("勤務状況");
			pw.print(",");
			pw.print("勤務開始時間");
			pw.print(",");
			pw.print("勤務終了時間");
			pw.print(",");
			pw.print("休憩時間");
			pw.print(",");
			pw.print("勤務時間");
			pw.print(",");
			pw.print("残業時間");
			pw.println();

			for (WorkSituationBeans workSituation : workSituationList) {

				pw.print(workSituation.getWorkSitu());
				pw.print(",");
				pw.print(workSituation.getWorkStart());
				pw.print(",");
				pw.print(workSituation.getWorkEnd());
				pw.print(",");
				pw.print(workSituation.getBreakTime());
				pw.print(",");
				pw.print(workSituation.getWorkTime());
				pw.print(",");
				pw.print(workSituation.getOvertime());
				pw.println();
			}

			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}

}
