package attendanceRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import beans.WorkSituationBeans;
import dao.UserInfoDao;
import dao.WorkSituationDao;
import dao.WorkSituationEditDao;

/**
 * Servlet implementation class WorkSituationDelete
 */
@WebServlet("/WorkSituationDelete")
public class WorkSituationDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkSituationDelete() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		// セッションにログイン情報があるかないかで分岐
		if ((UserBeans) session.getAttribute("loginUser") == null) {
			// LoginScreenへリダイレクト
			response.sendRedirect("LoginScreen");
		} else if (request.getParameter("date") == null) {

			// リクエストパラメータの取得
			int id = Integer.parseInt(request.getParameter("id"));
			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));

			// パラメータidに対応するUserBeans型のuserInfoインスタンスをリクエストスコープに保存
			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);

			// 勤務状況のリストを取得し、workSituationListインスタンスをリクエストスコープに保存
			String loginId = userInfo.getLoginId();
			List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
			workSituationList = WorkSituationDao.findAll(loginId, year, month);
			request.setAttribute("workSituationList", workSituationList);

			// リクエストスコープにパラメータを保存
			request.setAttribute("id", id);
			request.setAttribute("year", year);
			request.setAttribute("month", month);

			// 勤務状況のリストがない場合はエラーメッセージとともにMonthlyWorkCheckへフォワード
			if (workSituationList.size() == 0) {
				request.setAttribute("errMsg", "削除するデータがありません。");

				// MonthlyWorkCheckへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("MonthlyWorkCheck");
				dispatcher.forward(request, response);
				return;
			}

			// 削除確認メッセージをリクエストスコープに保存
			request.setAttribute("confMsg1", "本当に");
			request.setAttribute("confMsg2", year + "年" + month + "月");
			request.setAttribute("confMsg3", "のデータを全て削除してもよろしいでしょうか。");

			// workSituationDelete.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/workSituationDelete.jsp");
			dispatcher.forward(request, response);
			return;

		} else {

			// リクエストパラメータの取得
			int id = Integer.parseInt(request.getParameter("id"));
			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));
			int date = Integer.parseInt(request.getParameter("date"));

			// パラメータidに対応するUserBeans型のuserInfoインスタンスをリクエストスコープに保存
			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);

			// 勤務状況のリストを取得し、workSituationListインスタンスをリクエストスコープに保存
			String loginId = userInfo.getLoginId();
			List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
			workSituationList = WorkSituationDao.findAll(loginId, year, month, date);
			request.setAttribute("workSituationList", workSituationList);

			// リクエストスコープにパラメータを保存
			request.setAttribute("id", id);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("date", date);

			// 勤務状況のリストがない場合はエラーメッセージとともにDailyWorkCheckへフォワード
			if (workSituationList.size() == 0) {
				request.setAttribute("errMsg", "削除するデータがありません。");

				// DailyWorkCheckへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("DailyWorkCheck");
				dispatcher.forward(request, response);
				return;
			}

			// 削除確認メッセージをリクエストスコープに保存
			request.setAttribute("confMsg1", "本当に");
			request.setAttribute("confMsg2", year + "年" + month + "月" + date + "日");
			request.setAttribute("confMsg3", "のデータを削除してもよろしいでしょうか。");

			// workSituationDelete.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/workSituationDelete.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータの取得
		int id = Integer.parseInt(request.getParameter("id"));
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int date = Integer.parseInt(request.getParameter("date"));
		String workSituationIdList[] = request.getParameterValues("workSituationIdList[]");

		// ユーザーを探してuserInfoに代入
		UserBeans userInfo = UserInfoDao.findAll(id);

		// リクエストパラメータがnullかそうでないかで分岐
		if(request.getParameter("date") == null) {
			// 削除履歴を作成
			WorkSituationEditDao.setEditHistory(userInfo.getLoginId(), year, month);

			// 勤務状況を削除
			for (int i = 0; i < workSituationIdList.length; i++) {
				WorkSituationDao.situDel(workSituationIdList[i]);
			}
		}else {
			// 削除履歴を作成
			WorkSituationEditDao.setEditHistory(userInfo.getLoginId(), year, month, date);

			// 勤務状況を削除
			for (int i = 0; i < workSituationIdList.length; i++) {
				WorkSituationDao.situDel(workSituationIdList[i]);
			}
		}

		// リクエストスコープにパラメータを保存
		request.setAttribute("id", id);

		// UserListへリダイレクト
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserDetail");
		dispatcher.forward(request, response);

	}

}
