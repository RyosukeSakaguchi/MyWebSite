package attendanceRecord;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import common.UtilLogic;
import dao.DaoUtil;
import dao.WorkSituationDao;

/**
 * Servlet implementation class WorkSituationRegistration
 */
@WebServlet("/WorkSituationRegistration")
public class WorkSituationRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkSituationRegistration() {
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
		UserBeans loginUser = (UserBeans) session.getAttribute("loginUser");
		// セッションにログイン情報があるかないかで分岐
		if (loginUser == null) {
			// LoginScreenへリダイレクト
			response.sendRedirect("LoginScreen");
		} else if(loginUser.getId() == 1) {
			// LoginScreenへリダイレクト
			response.sendRedirect("UserList");
		}else {
			// ユーザーが勤務前の時はresult1がtrue(他はfalse)
			// ユーザーが勤務中の時はresult2がtrue(他はfalse)
			// ユーザーが帰宅の時はresult3がtrue(他はfalse)
			// これらをリクエストスコープに保存
			boolean result1 = WorkSituationDao.isBeforeWorking(loginUser.getLoginId());
			boolean result2 = WorkSituationDao.isWorking(loginUser.getLoginId());
			boolean result3 = WorkSituationDao.isAfterWorking(loginUser.getLoginId());
			request.setAttribute("result1", result1);
			request.setAttribute("result2", result2);
			request.setAttribute("result3", result3);

			// ユーザーが帰宅中の時、現在の時間が時間マスターの勤務開始時間より遅れていたら確認メッセージを保存
			if(WorkSituationDao.isOverTime("work_start")) {
				request.setAttribute("overStartTimeMsg", "今日の勤務開始時間がまだ入力されていません");
			}

			// 勤務中の時、現在の時間が時間マスターの勤務終了時間より遅れていたら確認メッセージを保存
			if(WorkSituationDao.isOverTime("work_end")) {
				request.setAttribute("overEndTimeMsg", "今日の勤務終了時間と休憩時間がまだ入力されていません");
			}

			// 今月の勤務時間が50時間を超えていたら、確認メッセージを保存
			Date now = new Date();
			SimpleDateFormat y = new SimpleDateFormat("yyyy");
			SimpleDateFormat m = new SimpleDateFormat("MM");
			int year = Integer.parseInt(y.format(now));
			int month = Integer.parseInt(m.format(now));
			String titalOvertime = UtilLogic.totalOvertime(WorkSituationDao.findAll(loginUser.getLoginId(), year, month));
			int titalOvertimeInt = UtilLogic.stringTimeToInt(titalOvertime);
			if(titalOvertimeInt >= 500000) {
				request.setAttribute("confMsg", "今月の残業時間が50時間を超えています");
			}

			// workSituationRegistration.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/workSituationRegistration.jsp");
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
		String situation = request.getParameter("situation");

		// HttpSessionインスタンスの取得
		HttpSession session = request.getSession();
		UserBeans loginUser = (UserBeans) session.getAttribute("loginUser");
		String loginId = loginUser.getLoginId();

		// 勤務開始なのか終了なのかで分岐
		if (situation.equals("start")) {
			// 勤務状況をテーブルに保存
			Time workStartMaster = DaoUtil.getTime(1, "work_start");
			WorkSituationDao.workStart(loginId, workStartMaster);

			// RegistrationCompleteへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrationComplete?situation=start");
			dispatcher.forward(request, response);
			return;
		} else if (situation.equals("end")) {
			// 入力が正しいとき、勤務状況をテーブルに保存
			String breakTime = request.getParameter("breakTime");
			Time workEndMaster = DaoUtil.getTime(1, "work_end");
			boolean result = WorkSituationDao.workEnd(loginId, breakTime, workEndMaster);

			// 入力が正しくない時はエラーメッセージを保存し、doGetメソッドを実行
			if (result) {
				// RegistrationCompleteへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrationComplete?situation=end");
				dispatcher.forward(request, response);
				return;
			}else {
				request.setAttribute("errMsg", "入力内容に誤りがあります。");
				doGet(request, response);
				return;
			}

		}

	}

}
