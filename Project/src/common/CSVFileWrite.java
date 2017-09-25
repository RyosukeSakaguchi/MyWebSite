package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import beans.UserBeans;;

public class CSVFileWrite extends UtillLogic {

	public static void main(List<UserBeans> userList, int year, int month) {

		try {
			// 出力先を作成する
			FileWriter fw = new FileWriter(
					"/Users/sakaguchimikiya/Documents/github/MyWebSite/CSV/" + year + "年" + month + "月.csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			for (UserBeans user : userList) {
				if (user.getId() != 1) {
					// 内容を指定する
					pw.print(user.getLoginId());
					pw.print(",");
					pw.print(getMonthlySalary(user.getLoginId(), year, month));
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

}
