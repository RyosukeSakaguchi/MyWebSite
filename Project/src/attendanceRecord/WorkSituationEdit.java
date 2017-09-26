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
 * Servlet implementation class WorkSituationEdit
 */
@WebServlet("/WorkSituationEdit")
public class WorkSituationEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkSituationEdit() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();


		// セッションにログイン情報があるかないかで分岐
		if ((UserBeans) session.getAttribute("loginUser") == null) {
			// LoginScreenへリダイレクト
			response.sendRedirect("LoginScreen");
		} else {
			// リクエストパラメータの取得
			int id = Integer.parseInt(request.getParameter("id"));
			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));
			int date = Integer.parseInt(request.getParameter("date"));

			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);
			request.setAttribute("name", userInfo.getName());


			String loginId = userInfo.getLoginId();

			List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
			workSituationList = WorkSituationDao.findAll(loginId, year, month, date);
			request.setAttribute("workSituationList", workSituationList);

			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("date", date);

			if(workSituationList.size() == 0) {
				request.setAttribute("errMsg", "編集するデータがありません。");
				// workSituationEdit.jspへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("DailyWorkCheck");
				dispatcher.forward(request, response);
				return;
			}

			// workSituationEdit.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/workSituationEdit.jsp");
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
		int workSituationId = Integer.parseInt(request.getParameter("workSituationId"));
		String workStart = request.getParameter("workStart");
		String workEnd = request.getParameter("workEnd");
		String breakTime = request.getParameter("breakTime");
		String workStartBefore = request.getParameter("workStartBefore");
		String workEndBefore = request.getParameter("workEndBefore");
		String breakTimeBefore = request.getParameter("breakTimeBefore");

		boolean result = WorkSituationDao.workSituationEdit(workSituationId, workStart, workEnd, breakTime);

		request.setAttribute("id", id);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("date", date);

		// ユーザーを探してuserInfoに代入
		UserBeans userInfo = UserInfoDao.findAll(id);
		request.setAttribute("userInfo", userInfo);

		if (result) {
			WorkSituationEditDao.setEditHistory(userInfo.getLoginId(), workStart, workStartBefore, year, month, date, "work_start");
			WorkSituationEditDao.setEditHistory(userInfo.getLoginId(), workEnd, workEndBefore, year, month, date, "work_end");
			WorkSituationEditDao.setEditHistory(userInfo.getLoginId(), breakTime, breakTimeBefore, year, month, date, "break_time");
			request.setAttribute("scsMsg", "変更しました");

			DailyWorkCheck dailyWorkCheck = new DailyWorkCheck();
			dailyWorkCheck.doGet(request, response);
			return;
		}else {
			request.setAttribute("errMsg", "入力内容に誤りがあります");
			// workSituationEdit.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/workSituationEdit.jsp");
			dispatcher.forward(request, response);
		}


	}

}
